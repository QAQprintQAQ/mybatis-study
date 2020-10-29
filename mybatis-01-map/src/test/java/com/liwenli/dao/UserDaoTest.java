package com.liwenli.dao;

import com.liwenli.pojo.User;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;

import java.util.Map;

public class UserDaoTest {

    public static void main(String[] args) {



    }



    @Test
    public void tsetSelectUserById() {
        SqlSession session = MybatisUtils.getSession();  //获取SqlSession连接
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserById(1);
        System.out.println(user);
        session.close();
    }

    @Test
    public void testSelectUserById2() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<String, Object>();
        /*
        总结：如果参数过多，我们可以考虑直接使用Map实现，如果参数比较少，直接传递参数即可

         */
         map.put("id",5);
         map.put("name", "王淑玮");
//         这个查询的例子是错误的！
        Integer i = mapper.selectUserById2(map);
        System.out.println("查询到"+i+"条目");
        System.out.println(mapper.selectUserById2(map));
        session.close();
    }


    @Test
    public void testAddUser() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User(5,"wangshuwei","wangshuwei");
        int i = mapper.addUser(user);
        System.out.println("成功增加"+i+"个条目");
        session.commit(); //提交事务,重点!不写的话不会提交到数据库
        session.close();
    }


    @Test
    public void testAddUser2() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<String, Object>();
        /*
        总结：如果参数过多，我们可以考虑直接使用Map实现，如果参数比较少，直接传递参数即可

         */
        map.put("id", 7);
        map.put("name", "李远胜");
        map.put("pwd", "liyuansheng");
        int i = mapper.addUser2(map);
        System.out.println("成功增加"+i+"个条目");
        session.commit(); //提交事务,重点!不写的话不会提交到数据库
        session.close();
    }

}

