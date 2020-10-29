#mybatis-01-base

##创建maven工程
###在mysql中建立mybatis数据库,建立一个user表
~~~~
CREATE TABLE `user` (
`id` int(20) NOT NULL,
`name` varchar(30) DEFAULT NULL,
`pwd` varchar(30) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `user`(`id`,`name`,`pwd`) values (1,'李文利','liwenli'),(2,'张三','zhangsan'),(3,'李四','lisi');
~~~~
### 创建父工程
1.创建maven工程

将groupid 设置为com.liwenli
artifctid 设置为mybatis-study
~~~~
 <groupId>com.liwenli</groupId>
 <artifactId>mybatis-study</artifactId>
~~~~
2.将src删除 

表示该工程为父工程

3.导入相关依赖 刷新maven自动下载相关依赖 网速不好可能花费的时间就多一点刷新一次就行

* mybatis
~~~~
   <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.5</version>

        </dependency>
~~~~

mysql
~~~~
 <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.21</version>
        </dependency>
~~~~

* junit 用于测试@Test
~~~~
 <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>
~~~~

* log4j  用于查看日志
~~~~

   <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
        </dependency>
~~~~

***
***

### 创建子工程
1.右键mybatis-study,新建moudle, 将artifactId设为mybatis-01-base
~~~~
<artifactId>mybatis-01-base</artifactId>
~~~~
2.因为子工程继承父工程的pom.xml，所以子工程是不需要再导入依赖坐标的，这就是父工程创建的好处

##编写mybatis工具类
1.在src->main->java中新建包com.liwenli.utils

2.并在该包下新建java类MybatisUtils.java

3.编写MybatisUtils.java
~~~~
package com.liwenli.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            /*在最前面的时候静态声明过了，如果再写sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
           创建了两个SqlSessionFactory sqlSessionFactory就会报空指针异常！这里要注意的
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession连接
    public static SqlSession getSession(){
        return sqlSessionFactory.openSession();
    }

}
~~~~
4.常见的错误
* com.liwenli.utils未展开

  方法：将工程下面的open exclude files 
* 有些Java代码为红色

  方法：按alt+enter自动导入相关的包


##idea连接mysql数据库
1.点击idea右侧的Database

2.点击'+' (表示新建一个数据库连接)

3.点击datasource->mysql

4.填写username(liwenli) password(liwenli) database (mybatis)
~~~~
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf8
username=liwenli
password=liwenli
~~~~
5.点击Test Connect

6.注意事项
* 可能会报错 Server returns invalid timezone. Go to 'Advanced' tab and set 'serverTimezone' property manually.

  方法: a.点击右侧Advenced->serverTimezon 修改为UTC-> GMT
  
  b.进入命令窗口（Win + R），连接数据库 mysql -hlocalhost -uroot -p，回车，输入密码，回车。
  继续输入 show variables like’%time_zone’; （注意不要漏掉后面的分号），回车。
  显示 SYSTEM 就是没有设置时区 现在我们来设置时区。输入set time_zone = ‘+8:00’; (注意不要漏掉后面的分号)，回车                                                                                                                     
* 一定要在database 那个栏目填写mybatis这个数据库，方便后面的字段自动补全！  

##编写mybatis核心配置文件
1.在src->main->resource 新建一个mybatis-config.xml文件

2.配置文件的示例[mybatis](https://mybatis.org/mybatis-3/zh/index.html)


3.编写mybatis-config.xml文件
~~~~       
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <properties resource="db.properties"/>
<!--    导入数据库的配置文件-->

    <settings>
        <setting name="logImpl" value="log4j"/>
<!--        实现log4j-->
    </settings>


    <environments default="development">
<!--        有两套环境，可以通过默认环境更改环境，一下对应于两种环境的方式，一种是通过配置文件db.propeties文件导入，另外一种就是直接通过写在mybatis-config.xml的直接命名中-->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>


        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
<!--                '&'需要用'&amp;'转义一下-->
                <property name="username" value="liwenli"/>
                <property name="password" value="liwenli"/>
            </dataSource>
        </environment>


    </environments>
    <mappers>
        <mapper resource="com/liwenli/dao/UserMapper.xml"/>
        <!--是resource="com/liwenli/dao/UserMapper.xml" 而不是resource="com.liwenli.dao.UserMapper.xml"-->

    </mappers>
</configuration>

~~~~                                                                                               

##编写实体类
1.在src->main->java中新建包com.liwenli.pojo

2.新建User.java类

3.编写User.java类
~~~~
package com.liwenli.pojo;

public class User {

    private int id;  //id
    private String name;   //姓名
    private String pwd;   //密码

    //构造,有参,无参

    public User() {

    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    //set/get

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    //toString()


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
~~~~

4.注意事项
* 实体类的字段（属性名）必须和数据库表中的字段名一致
* mysql->mybatis->user这张表中的字段为id name pwd  则User.java中的属性名也为 id name pwd
* 另外实体类User.java 一定要有无参构造，有参构造，set get方法，tostring方法 

##编写mapper接口
1.在src->main->java中新建包com.liwenli.dao

2.新建UserMapper.java这个接口(注意是接口，不是类)

3.编写UserMapper.java这个接口
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.User;

import java.util.List;

public interface UserMapper {
  public  List<User> getUserList();
    /*
    mybatis中不再需要去实现这个接口了，直接通过配置文件相当于实现这个接口，简化了代码

     */
}
~~~~
4.注意事项
* 接口方法不需要实现
* 使用mybatis后不再实现该接口 (jdbc是需要实现该接口)

##编写Mapper.xml配置文件
1.在包com.liwenli.dao中新建UserMapper.xml(在src->main->resources中新建UserMapper.xml是更加规范的)

2.编写UserMapper.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.UserMapper">
 <!--是namespace="com.liwenli.dao.UserMapper" 而不是namespace="com/liwenli/dao/UserMapper"
<!--    namespace绑定一个UserMapper接口,就是只写抽象函数的那个接口，这个UserMapper.xml文件绑定那个UserMapper.这就相当于实现了UserMapper的接口一样-->
    <select id="getUserList" resultType="com.liwenli.pojo.User">
    <!-- id对应于UserMapper中的getUserList方法名 resultType对应于getUserList中返回的类名-->

    select * from user

  </select>
</mapper>
~~~~
3.配置文件的示例[mybatis](https://mybatis.org/mybatis-3/zh/index.html)

4.注意事项
* UserMapper.xml中的namespace是非常重要的
~~~~
<mapper namespace="com.liwenli.dao.UserMapper">
<!--    namespace绑定一个UserMapper接口,就是只写抽象函数的那个接口，这个UserMapper.xml文件绑定那个UserMapper.这就相当于实现了UserMapper的接口一样-->
~~~~
* UserMapper.xml中的id 是实体类UserMapper.java中的抽象方法名
* UserMapper.xml中的resultType 对应于getUserList中返回的类
~~~~
  <!-- id对应于UserMapper中的getUserList方法名 resultType对应于getUserList中返回的类名-->
~~~~


*  select * from user 是普通的sql语句


##小组件
1.db.properties
* 在src->main->resource下新建db.properties

* 编写db.properties
~~~~
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf8
username=liwenli
password=liwenli

~~~~
* 在mybatis核心配置文件mybatis-config.xml中加入代码
~~~~
  <properties resource="db.properties"/>
<!--    导入数据库的配置文件-->
~~~~

2.log4j.properties
* 在src->main->resource下新建log4j.properties
* 编写log4j.properties
~~~~
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/liwenli.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
~~~~
*  在mybatis核心配置文件mybatis-config.xml中加入代码
~~~~
 <settings>
        <setting name="logImpl" value="LOG4J"/>
<!--        实现log4j-->
    </settings>

~~~~

##编写测试类
1.src->test中新建包com.liwenli.dao(或者省略这一步)

2.新建UserDaoTest.java(或者直接在src->test下直接建立UserDaoTest.java)

3.编写UserDaoTest.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.User;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    public static void main(String[] args) {



    }

    @Test
   //@Test是junit的方法，使用@Test需要导入依赖junit.另外可以不需要junit而直接写main方法来测试
    public void getUserList() {
        SqlSession session = MybatisUtils.getSession();
        //方法一:
        //List<User> users = session.selectList("com.liwenli.mapper.UserMapper.selectUser");
        //方法二:
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> users = mapper.getUserList();

        for (User user: users){
            System.out.println(user);
        }
        session.close();
    }
}

~~~~
4.运行测试方法

5.实用小技巧
* psvm
~~~~
    public static void main(String[] args) {
    }
~~~~
* user.sout 
~~~~
        for (User user: users){
            System.out.println(user);
        }
~~~~

##可能出现的问题 
1.Server returns invalid timezone. Go to 'Advanced' tab and set 'serverTimezone' property manually.
* 点击右侧Advenced->serverTimezon 修改为UTC-> GMT
    
* 进入命令窗口（Win + R），连接数据库 mysql -hlocalhost -uroot -p，回车，输入密码，回车。
    继续输入 show variables like’%time_zone’; （注意不要漏掉后面的分号），回车。
    显示 SYSTEM 就是没有设置时区 现在我们来设置时区。输入set time_zone = ‘+8:00’; (注意不要漏掉后面的分号)，回车
       
2.错误“ Java：不支持发行版本5”的正确解决方案
* 您可以将上述代码粘贴到构建配置文件pom.xml中，以设置目标语言级别。
~~~~
<!--jdk 1.8-->
<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>

<!--jdk 11-->
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
</properties>

~~~~

3.maven 静态资源过滤问题，原因:idea不能识别java目录下的非java文件
* 把所有的非java文件全部放到src->resources文件下
* 在mybatis-study父工程下的pom.xml文件下加上如下代码
~~~~
<!--directory：指定文件所在的目录，目录地址是相对pom.xml而言
    includes：指定要包含哪些文件
    filtering：false不过滤
-->
<build>
       <resources>
           <resource>
               <directory>src/main/java</directory>
               <includes>
                   <include>**/*.properties</include>
                   <include>**/*.xml</include>
               </includes>
               <filtering>false</filtering>
           </resource>
           <resource>
               <directory>src/main/resources</directory>
               <includes>
                   <include>**/*.properties</include>
                   <include>**/*.xml</include>
               </includes>
               <filtering>false</filtering>
           </resource>
       </resources>
   </build>

~~~~

4.IDEA--如何解决Java:程序包xxxx不存在
* 当出现如题所示的错误时候，不用着急，这是因为配置Java的程序包这块出现了错误，同时可能你还没有设置让IDEA自动加载Jar包，才会报出这种错误的。解决方式如下：
  File—>Setting—>Build,Execution,Deployment—>Maven—>Importing
* 点击Automatically download: Sources  这个这个框勾上 ，然后点击apply 
* 刷新maven工程

