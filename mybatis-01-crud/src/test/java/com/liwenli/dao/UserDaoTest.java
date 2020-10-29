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

    public void testgetUserList() {
        SqlSession session = MybatisUtils.getSession();
        //方法一:
        //List<User> users = session.selectList("com.liwenli.mapper.UserMapper.selectUser");
        //方法二:
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> users = mapper.getUserList();

        for (User user: users){
            System.out.println(user);
        }
        session.close();
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
    public void testUpdateUser() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserById(5);
        user.setName("王淑玮");
        user.setPwd("wangshuwei");
        int i = mapper.updateUser(user);
        System.out.println("成功修改"+i+"个条目");
        session.commit(); //提交事务,重点!不写的话不会提交到数据库
        session.close();
    }


    @Test
    public void testDeleteUser() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        int i = mapper.deleteUser(5);
        System.out.println("成功删除"+i+"个条目");
//        session.commit(); //提交事务,重点!不写的话不会提交到数据库
        session.close();
    }


}

