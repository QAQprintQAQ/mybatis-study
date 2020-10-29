package com.liwenli.dao;

import com.liwenli.pojo.Student;
import com.liwenli.pojo.Teacher;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class StudentTest {

    @Test
    //@Test是junit的方法，使用@Test需要导入依赖junit.另外可以不需要junit而直接写main方法来测试

    public void testGetStudentList() {
        SqlSession session = MybatisUtils.getSession();

        StudentMapper mapper = session.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.getStudentList();
        for (Student student : studentList) {
            System.out.println(student);
        }
        session.close();
    }
}
