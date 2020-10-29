package com.liwenli.dao;

import com.liwenli.pojo.User;

import java.util.List;

public interface UserMapper {
   public List<User> getUserList();
    /*
    mybatis中不再需要去实现这个接口了，直接通过配置文件相当于实现这个接口，简化了代码

     */
}
