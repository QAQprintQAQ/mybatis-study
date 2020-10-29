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
