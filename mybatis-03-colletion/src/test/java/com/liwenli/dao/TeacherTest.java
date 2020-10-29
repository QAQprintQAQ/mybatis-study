package com.liwenli.dao;

import com.liwenli.pojo.Teacher;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TeacherTest {

    @Test
    public void testGetTeacherList() {
        SqlSession session = MybatisUtils.getSession();

        TeacherMapper mapper = session.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);

        session.close();
    }

}


