 #mybatis-02-log
 * 能够有更快的排错效率
 ##mybatis中的log(日志)
 1. Mybatis内置的日志工厂提供日志功能，具体的日志实现有以下几种工具
 
 SLF4J
 
 Apache Commons Logging
 
 Log4j 2
 
 Log4j
 
 JDK logging

* 具体选择哪个日志实现工具由MyBatis的内置日志工厂确定。它会使用最先找到的（按上文列举的顺序查找）。如果一个都未找到，日志功能就会被禁用。


* 指定 MyBatis 应该使用哪个日志记录实现

~~~~
<!--在mybatis-config.xml核心配置文件中实现标准log,注意settings标签顺序-->
<settings>
       <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>

~~~~

##LOG4J
1、导入log4j的包
* 父工程(mybatis-study)pom.xml中导入log4j的依赖坐标(导包)
~~~~
    <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
        </dependency>

~~~~
2.在src->main->resources中新建一个log4j.properties
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

3、setting设置日志实现
~~~~
<!--mybatis-config.xml中导入LOG4J配置-->
<settings>
   <setting name="logImpl" value="LOG4J"/>
</settings>

~~~~

4、在UserDaoTest.java中使用Log4j进行输出！
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.User;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    static Logger logger = Logger.getLogger(UserDaoTest.class);

    @Test
    public void testselectUserByList() {
        logger.info("info：进入selectUser方法");
        logger.debug("debug：进入selectUser方法");
        logger.error("error: 进入selectUser方法");
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> users = mapper.getUserList();
        for (User user: users){
            System.out.println(user);
        }
        session.close();
    }
}

~~~~
5.注意事项
* IDEA删除子模块并重建后MAVEN无法识别,本子模块可能被忽略掉了

 问题解决: file -> settings ->maven -> ignored Files 看看里面本子模块是不是本勾选了，去掉即可