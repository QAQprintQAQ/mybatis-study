package com.liwenli.dao;

import com.liwenli.pojo.User;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class UserDaoTest {


    @Test
    public void testselectUserByList() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("startIndex", 3);
        map.put("pageSize", 2);
        List<User> users = mapper.selectUserByLimit(map);
        for (User user: users){
            System.out.println(user);
        }
        session.close();
    }
}


