 #mybatis-03-aocomplex
 ##新建student,teacher表
 ~~~~
CREATE TABLE `teacher` (
`id` INT(10) NOT NULL,
`name` VARCHAR(30) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO teacher(`id`, `name`) VALUES (1, '李老师');

CREATE TABLE `student` (
`id` INT(10) NOT NULL,
`name` VARCHAR(30) DEFAULT NULL,
`tid` INT(10) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `fktid` (`tid`),
CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8


INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('1', '小明', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('2', '小红', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('3', '小张', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('4', '小李', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('5', '小王', '1');

~~~~
 ##建立pojo实体类
 1.在src->main->java目录下建立com.liwenli.pojo包
 2.在pojo(普通Java对象)建立Teacher ,Student实体类
 * Teacher.java
 ~~~~
package com.liwenli.pojo;

import java.util.concurrent.TimeoutException;

public class Teacher {
    private int id;
    private String name;

    public Teacher() {


    }

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
    private Teacher teacher;


    public Student() {

    }

    public Student(int id, String name, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}


~~~~

##建立Mapper.java(dao层)
1.在src->main->java目录下新建com.liwenli.dao包
2.在com.liwenli.dao下新建TeacherMapper.java,StudentMapper.java
* TeacherMapper.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Teacher;

import java.util.List;

public interface TeacherMapper {

    public Teacher getTeacher(int id);
}

~~~~

* StudentMapper.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Student;

import java.util.List;

public interface StudentMapper {
    public List<Student> getStudentList();
}

~~~~

##Mapper.xml
1.在src->main->sources下新建com/liwenli/dao目录 
2.在com/liwenli/dao目录下新建TeacherMapper.xml,StudentMapper.xml
* TeacherMapper.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.TeacherMapper">
    <select id="getTeacher" resultType="com.liwenli.pojo.Teacher">
        select * from teacher;
    </select>
</mapper>
~~~~

* StudentMapper.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.StudentMapper">
        <select id="getStudentList" resultType="com.liwenli.pojo.Student">
            select * from student;

        </select>
</mapper>

~~~~

##mybatis-config.xml
1.在src->main->sources下新建mybatis-config.xml
2.编写mybatis-config.xml
~~~~
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <properties resource="db.properties"/>
<!--    导入数据库的配置文件-->

    <settings>
        <!--        实现LOG4J-->

        <setting name="logImpl" value="LOG4J"/>
    </settings>
<typeAliases>
<!--    通过扫描包起别名,别名为对应类的首字母小写形式-->
    <package name="com.liwenli.pojo"/>

</typeAliases>

    <environments default="development">
<!--        有两套环境，可以通过默认环境更改环境，一下对应于两种环境的方式，一种是通过配置文件db.propeties文件导入，另外一种就是直接通过写在mybatis-config.xml的直接命名中-->
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>

        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
<!--                '&'需要用'&amp;'转义一下-->
                <property name="username" value="liwenli"/>
                <property name="password" value="liwenli"/>
            </dataSource>
        </environment>


    </environments>

    <mappers>
        <mapper resource="com/liwenli/dao/TeacherMapper.xml"/>
        <mapper resource="com/liwenli/dao/StudentMapper.xml"/>

    </mappers>
</configuration>

~~~~


##测试类
1.在src->test下新建com.liwenli.dao包
* 新建TeacherTest.java
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.Teacher;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TeacherTest {

    @Test
    public void testGetTeacher() {
        SqlSession session = MybatisUtils.getSession();

        TeacherMapper mapper = session.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);

        session.close();
    }

}

~~~~
* 新建StudentTest.java
~~~~
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

~~~~

##总结
* 复杂查询的环境和普通查询差不多，就是多一个类而已

