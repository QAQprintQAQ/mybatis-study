package com.liwenli.dao;

import com.liwenli.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

   public User selectUserById(int id);

   public int selectUserById2(Map<String,Object>map);//注意是返回int类型

   public int addUser(User user);

   public int addUser2(Map<String,Object>map);//只需要传入一个map

}
