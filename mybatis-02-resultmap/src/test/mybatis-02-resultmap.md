 #mybatis-02-resultMap
 * 解决属性名和字段名不一致的问题
 ##建立新的pojo实体类User
 ~~~~
package com.liwenli.pojo;

public class User {

    private int id;  //id
    private String name;   //姓名
    private String password;   //密码和数据库user表中的字段名不一致

    //构造,有参,无参
    public User() {

    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

~~~~

##UserMapper.java编写
~~~~

package com.liwenli.dao;

import com.liwenli.pojo.User;


public interface UserMapper {

   public User selectUserById(int id);
}


~~~~

##UserMapper.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.UserMapper">
    
    

<!--    <select id="selectUserById" resultType="user">-->
<!--    &lt;!&ndash; id对应于UserMapper中的getUserList方法名 resultType对应于getUserList中返回的类名,user是"com.liwenli.pojo.User的别名&ndash;&gt;-->

<!--        select id , name , pwd as password from user where id = #{id}-->

<!--  </select>-->



<!--    -->
<!--    <resultMap id="UserMap" type="user">-->
<!--        &lt;!&ndash; id为主键 &ndash;&gt;-->
<!--        <id column="id" property="id"/>-->
<!--        &lt;!&ndash; column是数据库表的列名 , property是对应实体类的属性名 &ndash;&gt;-->
<!--        <result column="name" property="name"/>-->
<!--        <result column="pwd" property="password"/>-->
<!--    </resultMap>-->
<!--&lt;!&ndash; select语句中的resultMap指向上面resultMap的id = UserMap&ndash;&gt;-->
<!--    <select id="selectUserById" resultMap="UserMap">-->
<!--  select id , name , pwd from user where id = #{id}-->
<!--</select>-->

<!--    -->




    <resultMap id="UserMap" type="user">
        <!-- column是数据库表的列名 , property是对应实体类的属性名 -->
        <result column="pwd" property="password"/>
    </resultMap>

    <select id="selectUserById" resultMap="UserMap">
  select id , name , pwd from user where id = #{id}
</select>
 
    
</mapper>

~~~~


##UserDaoTest.java
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

    public void testSelectUserById() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserById(3);
        System.out.println(user);//打印user需要toString方法
        session.close();

/*
 mybatis会根据这些查询的列名(会将列名转化为小写,数据库不区分大小写) , 去对应的实体类中查找相应列名的set方法设值 , 由于找不到setPwd() , 所以password返回null ; 【自动映射】


    解决方法:

    方案一：为列名指定别名 , 别名和java实体类的属性名一致 .
    <select id="selectUserById" resultType="User">
       select id , name , pwd as password from user where id = #{id}
    </select>


    方案二：使用结果集映射->ResultMap 【推荐】

<resultMap id="UserMap" type="User">
   <!-- id为主键 -->
   <id column="id" property="id"/>
   <!-- column是数据库表的列名 , property是对应实体类的属性名 -->
   <result column="name" property="name"/>
   <result column="pwd" property="password"/>
</resultMap>

<select id="selectUserById" resultMap="UserMap">
  select id , name , pwd from user where id = #{id}
</select>


对于那些pojo实体类User中属性名和mysql->mybatis->user表中的同名的字段(列),我们不必把他们写在resultMap中，因为那些相同的字段还是会实现自动映射
User类     id   name  password
user表     id   name  pwd
所以id和name 属性字段是不需要写在resultMap中的，他们会自动映射 。而password则需要用resultMap进行手动映射,代码可以简化如下：

<resultMap id="UserMap" type="User">
   <!-- column是数据库表的列名 , property是对应实体类的属性名 -->
   <result column="pwd" property="password"/>
</resultMap>

<select id="selectUserById" resultMap="UserMap">
  select id , name , pwd from user where id = #{id}
</select>


 */

    }
}

~~~~

##结果集映射远比这复杂
* 数据库中，存在一对多，多对一的情况，我们之后会使用到一些高级的结果集映射，association，collection这些.