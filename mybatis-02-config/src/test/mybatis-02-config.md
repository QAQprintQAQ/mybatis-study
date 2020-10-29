 #mybatis-02-config
 ##核心配置文件
 ###MyBatis 的配置文件(mybatis-config.xml)包含了会深深影响 MyBatis 行为的设置和属性信息。
 ####能配置的内容如下：
 
 configuration（配置）(重要)

 properties（属性）(重要)
 
 settings（设置）(重要)
 
 typeAliases（类型别名）(重要)
 
 typeHandlers（类型处理器）
 
 objectFactory（对象工厂）
 
 plugins（插件）
 
 environments（环境配置）(重要)
 
 environment（环境变量）(重要)
 
 transactionManager（事务管理器）
 
 dataSource（数据源）
 
 databaseIdProvider（数据库厂商标识）
 
 mappers（映射器）(重要)
 ~~~~
 <!-- 注意元素节点的顺序！顺序不对会报错 -->
我们可以阅读 mybatis-config.xml 上面的dtd的头文件！
<!ELEMENT configuration (properties?, settings?, typeAliases?, typeHandlers?, objectFactory?, objectWrapperFactory?, reflectorFactory?, plugins?, environments?, databaseIdProvider?, mappers?)>

~~~~

##environments优化
1.环境配置
~~~~

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
~~~~
2.子元素节点：transactionManager  事务管理器 
~~~~
  <!-- 语法 -->
  <transactionManager type="[ JDBC | MANAGED ]"/>
~~~~
3.配置MyBatis的多套运行环境，将SQL映射到多个不同的数据库上，必须指定其中一个为默认运行环境（通过default指定）
 
4.子元素节点：environment
 
5.dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。

6.有三种内建的数据源类型，数据源是必须的
 * type="[UNPOOLED|POOLED|JNDI]"）
 * unpooled：这个数据源的实现只是每次被请求时打开和关闭连接。
 * pooled：这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来 , 这是一种使得并发 Web 应用快速响应请求的流行处理方式。
 * jndi：这个数据源的实现是为了能在如 Spring 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的引用。
 * 数据源也有很多第三方的实现，比如dbcp，c3p0，druid等等....

##Properties优化
####数据库这些属性都是可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置，亦可通过 properties 元素的子元素来传递。
* 优化数据库连接

1.在src->main->resources目录下新建一个db.properties
~~~~
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf8
username=root
password=123456
~~~~

2.将db.properties文件导入核心配置文件(mybatis-config.xml)
~~~~
<configuration>
   <!--导入properties文件-->
   <properties resource="db.properties"/>


   <environments default="development">
       <environment id="development">
           <transactionManager type="JDBC"/>
           <dataSource type="POOLED">
               <property name="driver" value="${driver}"/>
               <property name="url" value="${url}"/>
               <property name="username" value="${username}"/>
               <property name="password" value="${password}"/>
           </dataSource>
       </environment>
   </environments>
 
~~~~

##typeAliases优化
###配置别名有两种方法
* 第一种方法
~~~~
<!--配置别名,注意顺序,放在setting标签的后面-->
<typeAliases>
   <typeAlias type="com.kuang.pojo.User" alias="User"/>
</typeAliases>
<!--User可以用在任何使用com.liwenli.pojo.User的地方。-->
~~~~

* 第二种方法
~~~~
<!--扫描包名,把每个类的小写字母变为小写即得到类的别名 如下:User->user  user为User类的别名-->
<typeAliases>
   <package name="com.liwenli.pojo"/>
</typeAliases>

~~~~

##mapper映射
~~~~
    <mappers>
<!-- 方式一xml映射,没有什么限制,比较推荐使用！-->
<!--         <mapper resource="com/liwenli/dao/UserMapper.xml"/>-->
<!-- 方式二类名映射   需要配置文件名称和接口名称一致，并且位于同一目录下-->
<!--         <mapper class="com.liwenli.dao.UserMapper"/>-->
<!-- 方式三扫描包映射   将包内的映射器接口实现全部注册为映射器 但是需要配置文件名称和接口名称一致，并且位于同一目录下 -->
             <package name="com.liwenli.dao"/>

    </mappers>
~~~~