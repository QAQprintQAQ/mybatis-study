package com.liwenli.dao;

import com.liwenli.pojo.User;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class UserDaoTest {

    public static void main(String[] args) {


    }

    @Test
    public void testGetUserLike() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
       // List<User> users = mapper.getUserLike("%李%");// 方式一 :一定要添加"%李%"通配符
        List<User> users = mapper.getUserLike("李");// 方式二 :不要添加"%李%"通配符,前面的sql语句已经加上通配符了


        for (User user : users) {
            System.out.println(user);

        }
        session.close();

    }

}

