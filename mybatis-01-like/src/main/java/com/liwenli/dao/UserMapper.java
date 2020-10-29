package com.liwenli.dao;

import com.liwenli.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
   //模糊查询like
   public List<User> getUserLike(String name);

}
