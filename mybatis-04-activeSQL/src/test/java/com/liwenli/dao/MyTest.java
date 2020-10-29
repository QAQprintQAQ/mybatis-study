package com.liwenli.dao;

import com.liwenli.pojo.Blog;
import com.liwenli.utils.IDUtils;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;

public class MyTest {


    @Test
    public void testIDUtils() {
        System.out.println(IDUtils.genId());//IDUtils测试成功
        System.out.println("IDUtils efficence");
    }
   @Test
    public void addInitBlog(){
        SqlSession session = MybatisUtils.getSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
/*
     Blog.java 中的set方法就是用来赋值的！
 */
        Blog blog = new Blog();
        blog.setId(IDUtils.genId());
        blog.setTitle("Mybatis如此简单");
        blog.setAuthor("李文利");
        blog.setCreateTime(new Date());
        blog.setViews(9999);

        mapper.addBlog(blog);

        blog.setId(IDUtils.genId());
        blog.setTitle("Java如此简单");
        mapper.addBlog(blog);

        blog.setId(IDUtils.genId());
        blog.setTitle("Spring如此简单");
        mapper.addBlog(blog);

        blog.setId(IDUtils.genId());
        blog.setTitle("微服务如此简单");
        mapper.addBlog(blog);
        session.commit();
        session.close();
    }
}
