package com.liwenli.dao;

import com.liwenli.pojo.User;

import java.util.List;

public interface UserMapper {
   public List<User> getUserList();
    /*
    mybatis中不再需要去实现这个接口了，直接通过配置文件相当于实现这个接口，简化了代码

     */
    //根据id查询用户,在jdbc中新增一个抽象方法就要增加一个实现类，但是在mybatis中只需要在UserMapper.xml中增加一个标签即可非常方便
   public User selectUserById(int id);

   public int addUser(User user);

   public int updateUser(User user);

   public int deleteUser(int id);

}
