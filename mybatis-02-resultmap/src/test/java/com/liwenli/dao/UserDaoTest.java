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

