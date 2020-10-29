 #mybatis-03-collection
 ##创建实体类(和环境搭建的实体类有所不同，因为要实现一对多)
 * Teacher.java (private List<Student> students;//一个老师有多个学生，所以需要在老师的属性名中增加一个学生的列表
) 这是不同的地方!
 ~~~~
package com.liwenli.pojo;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class Teacher {
    private int id;
    private String name;
    private List<Student> students;//一个老师有多个学生，所以需要在老师的属性名中增加一个学生的列表

    public Teacher() {

    }

    public Teacher(int id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
~~~~
* Student.java
~~~~
package com.liwenli.pojo;

public class Student {
    private int id;
    private String name;
    private int tid;//一个学生只有一个老师，所以只需要tid表示老师

    public Student() {

    }

    public Student(int id, String name, int tid) {
        this.id = id;
        this.name = name;
        this.tid = tid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tid=" + tid +
                '}';
    }
}

~~~~

##mapper.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Teacher;

import java.util.List;

public interface TeacherMapper {

    public Teacher getTeacher(int id);
    public Teacher getTeacher2(int id);

}

~~~~

##Mapper.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.TeacherMapper">
    <!--
    思路:
        1. 从学生表和老师表中查出学生id，学生姓名，老师姓名
        2. 对查询出来的操作做结果集映射
            1. 集合的话，使用collection！
                JavaType和ofType都是用来指定对象类型的
                JavaType是用来指定pojo中属性的类型
                ofType指定的是映射到list集合属性中pojo的类型。
    -->
    <select id="getTeacher" resultMap="TeacherStudent">
      select s.id sid, s.name sname , t.name tname, t.id tid
      from student s,teacher t
      where s.tid = t.id and t.id=#{id}
   </select>

    <resultMap id="TeacherStudent" type="com.liwenli.pojo.Teacher">
        <result  property="name" column="tname"/>
        <collection property="students" ofType="com.liwenli.pojo.Student">
            <result property="id" column="sid" />
            <result property="name" column="sname" />
            <result property="tid" column="tid" />
        </collection>
    </resultMap>



    <select id="getTeacher2" resultMap="TeacherStudent2">
select * from teacher where id = #{id}
</select>
    <resultMap id="TeacherStudent2" type="Teacher">
        <!--column是一对多的外键 , 写的是一的主键的列名-->
        <id column="id" property="id"/>
        <id column="name" property="name"/>
        <!--因为上面的teacher表和Teaccher类的属性字段是同名的,所以可以省略不写上面的那两行-->
        <collection property="students" javaType="ArrayList" ofType="Student" column="id" select="getStudentByTeacherId"/>
    </resultMap>
    <select id="getStudentByTeacherId" resultType="Student">
    <!--id="getStudentByTeacherId"可以随便些什么,只需要和上面的 select="getStudentByTeacherId"保持一致即可-->
  select * from student where tid = #{id}
</select>

</mapper>
~~~~

##MyTest.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Teacher;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyTest {

    @Test
    public void testGetTeacher() {
        SqlSession session = MybatisUtils.getSession();

        TeacherMapper mapper = session.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);

        session.close();
    }

    @Test
    public void testGetTeacher2() {
        SqlSession session = MybatisUtils.getSession();

        TeacherMapper mapper = session.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);

        session.close();
    }


}
~~~~

##小结
* 关联-association
  
* 集合-collection
  
* 所以association是用于一对一和多对一，而collection是用于一对多的关系
  
* JavaType和ofType都是用来指定对象类型的
  
* JavaType是用来指定pojo中属性的类型
  
* ofType指定的是映射到list集合属性中pojo的类型。
