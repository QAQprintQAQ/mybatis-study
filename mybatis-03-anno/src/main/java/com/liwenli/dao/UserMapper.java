package com.liwenli.dao;

import com.liwenli.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
   @Select("select * from user")//直接在接口名上写注解sql
   public List<User> getUserList();
   @Select("select * from user where id = #{id}")
   public User selectUserById(int id);
//   public User selectUserByIdNa(@Param("int") int id,@Param("String")String name);
//假如多个参数,则需要用"@Para("参数类型") 参数类型 变量"  的格式

   @Insert("insert into user (id , name , pwd) values(#{id},#{name},#{pwd})")
   public int addUser(User user);
   @Update("update user set name = #{name},pwd = #{pwd} where id = #{id}")
   public int updateUser(User user);
   @Delete("delete from user where id = #{id}")
   public int deleteUser(int id);

}
