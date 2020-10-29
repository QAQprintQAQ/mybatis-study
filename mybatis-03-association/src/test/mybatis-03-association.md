#mybatis-03-association
##复杂多对一查询
* 使用association

1.StudentMapper.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Student;

import java.util.List;

public interface StudentMapper {
    public List<Student> getStudentList();
    public List<Student> getStudentList2();
}

~~~~

2.StudentMapper.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.StudentMapper">
    <!--
需求：获取所有学生及对应老师的信息
思路：
    1. 获取所有学生的信息
    2. 根据获取的学生信息的老师ID->获取该老师的信息
    3. 思考问题，这样学生的结果集中应该包含老师，该如何处理呢，数据库中我们一般使用关联查询？
        1. 做一个结果集映射：StudentTeacher
        2. StudentTeacher结果集的类型为 Student
        3. 学生中老师的属性为teacher，对应数据库中为tid。
           多个 [1,...）学生关联一个老师=> 一对一，一对多
        4. 查看官网找到：association – 一个复杂类型的关联；使用它来处理关联查询
-->
    <select id="getStudentList" resultMap="StudentTeacher">
    select * from student
   </select>
    <resultMap id="StudentTeacher" type="com.liwenli.pojo.Student">
        <!--association关联属性 property属性名 javaType属性类型 column在多的一方的表中的列名-->
        <association property="teacher"  column="tid" javaType="com.liwenli.pojo.Teacher" select="getTeacherList"/>
        <!--getTeacherList换成其他的也是没问题的！-->
    </resultMap>
    <!--
    这里传递过来的id，只有一个属性的时候，下面可以写任何值
    association中column多参数配置：
        column="{key=value,key=value}"
        其实就是键值对的形式，key是传给下个sql的取值名称，value是片段一中sql查询的字段名。
    -->
    <select id="getTeacherList" resultType="com.liwenli.pojo.Teacher">
      select * from teacher where id = #{id}
   </select>








    <select id="getStudentList2" resultMap="StudentTeacher2" >
  select s.id as  sid, s.name as sname , t.name as  tname
  from student s,teacher t
  where s.tid = t.id
</select>

    <resultMap id="StudentTeacher2" type="com.liwenli.pojo.Student">
        <id property="id" column="sid"/>
        <result property="name" column="sname"/>
        <!--关联对象property 关联对象在Student实体类中的属性-->
        <association property="teacher" javaType="com.liwenli.pojo.Teacher">
            <result property="name" column="tname"/>
        </association>
    </resultMap>

</mapper>
~~~~


3.MyTest.java
* 测试
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Student;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyTest {

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



     @Test
    public void testGetStudentList2() {
        SqlSession session = MybatisUtils.getSession();

        StudentMapper mapper = session.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.getStudentList2();
        for (Student student : studentList) {
            System.out.println(student);
        }
        session.close();
    }
}

~~~~

##小结
* 按照查询进行嵌套处理就像SQL中的子查询
 
* 按照结果进行嵌套处理就像SQL中的联表查询
 
 
