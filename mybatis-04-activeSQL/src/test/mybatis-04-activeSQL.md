 #mybatis-04-activeSQL
 ##建立blog表
~~~~
 CREATE TABLE `blog`(
`id` VARCHAR(50) NOT NULL COMMENT '博客id',
`title` VARCHAR(100) NOT NULL COMMENT '博客标题',
`author` VARCHAR(30) NOT NULL COMMENT '博客作者',
`create_time` DATETIME NOT NULL COMMENT '创建时间',
`views` INT(30) NOT NULL COMMENT '浏览量'
)ENGINE=INNODB DEFAULT CHARSET=utf8
~~~~

##建立IDUtils.java工具类
1. 在com.liwenli.utils下新建IDUtils.java

~~~~
package com.liwenli.utils;

import java.util.UUID;

public class IDUtils {
    
    public static String genId(){
        
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

~~~~

2.IDUtils.java的作用
* 生成一个不重复的id


##建立pojo实体类
1.在com.liwenli.pojo下新建实体类Blog.java
~~~~

package com.liwenli.pojo;

import java.util.Date;

public class Blog {

    private String id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
    //set，get....
//set方法用于赋值

    public Blog() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", views=" + views +
                '}';
    }


}

~~~~

##Mapper.java
在com.liwenli.dao下面新建BlogMapper.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Blog;

public interface BlogMapper {
    //新增一个博客
    int addBlog(Blog blog);

}

~~~~

* int addBlog(Blog blog); 抽象方法是为blog表进行初始化

##Mapper.xml
在com.liwenli.dao下面新建BlogMapper.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.BlogMapper">

    <insert id="addBlog" parameterType="blog">
           insert into blog (id, title, author, create_time, views)
           values (#{id},#{title},#{author},#{createTime},#{views});
    </insert>

</mapper>
~~~~

##核心配置mybatis-config.xml

~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <properties resource="db.properties"/>
<!--    导入数据库的配置文件-->

    <settings>
        <!--        实现LOG4J-->

        <setting name="logImpl" value="LOG4J"/>

<!--        下划线驼峰自动转换-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
<typeAliases>
<!--    通过扫描包起别名,别名为对应类的首字母小写形式-->
    <package name="com.liwenli.pojo"/>

</typeAliases>

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
        <mapper resource="com/liwenli/dao/BlogMapper.xml"/>
<!--        
为什么是com/liwenli/dao/Blog.xml ?而不是 com.liwenli.dao.Blog.xml呢？
我个人猜想:
xml配置文件按理说是应该放在resources目录下面,正如我们所说,resources是一个目录,它不是包。所以我们需要把它用"/" 而不是"."
同时,maven会自动的将非java文件从java包下过滤到相对应的resources目录下面
如:java.com.liwenl.dao.BlogMapper.xml会被maven过滤到resources/com/liwenli/dao/BlogMapper.xml。纯属猜想！！
-->

    </mappers>
</configuration>
~~~~

##MyTest
* test下面com.liwenli.dao下面新建MyTest.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Blog;
import com.liwenli.utils.IDUtils;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;

public class MyTest {


    @Test
    public void testIDUtils() {
        System.out.println(IDUtils.genId());//IDUtils测试成功
        System.out.println("IDUtils efficence");
    }
   @Test
    public void addInitBlog(){
        SqlSession session = MybatisUtils.getSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
/*
     Blog.java 中的set方法就是用来赋值的！
 */
        Blog blog = new Blog();
        blog.setId(IDUtils.genId());
        blog.setTitle("Mybatis如此简单");
        blog.setAuthor("李文利");
        blog.setCreateTime(new Date());
        blog.setViews(9999);

        mapper.addBlog(blog);

        blog.setId(IDUtils.genId());
        blog.setTitle("Java如此简单");
        mapper.addBlog(blog);

        blog.setId(IDUtils.genId());
        blog.setTitle("Spring如此简单");
        mapper.addBlog(blog);

        blog.setId(IDUtils.genId());
        blog.setTitle("微服务如此简单");
        mapper.addBlog(blog);
        session.commit();
        session.close();
    }
}

~~~~

* 对blog进行初始化
