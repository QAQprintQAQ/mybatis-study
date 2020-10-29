package com.liwenli.dao;

import com.liwenli.pojo.Blog;
import com.liwenli.utils.IDUtils;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyTest {


    @Test
    public void testIDUtils() {
        System.out.println(IDUtils.genId());//IDUtils测试成功
        System.out.println("IDUtils efficence");
    }
 @Test
   public void testQueryBlogIf(){
       SqlSession session = MybatisUtils.getSession();
       BlogMapper mapper = session.getMapper(BlogMapper.class);

       HashMap<String, String> map = new HashMap<String, String>();
//       map.put("title","Mybatis如此简单");
//       map.put("author","李文利");
       List<Blog> blogs = mapper.queryBlogIf(map);

       System.out.println(blogs);

       session.close();
   }


    @Test
    public void testQueryBlogChoose(){
        SqlSession session = MybatisUtils.getSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);

        HashMap<String, String> map = new HashMap<String, String>();
//       map.put("title","Mybatis如此简单");
//       map.put("author","李文利");
        map.put("views", "9999");//这是必须要有的,因为这是ortherwise里面的内容
        List<Blog> blogs = mapper.queryBlogChoose(map);

        System.out.println(blogs);

        session.close();
    }


    @Test
    public void testUpdateBlog(){
        SqlSession session = MybatisUtils.getSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);

        HashMap<String,Object> map = new HashMap<String, Object>();
//       map.put("title","Mybatis如此简单");
//       map.put("author","李文利");
        map.put("views", "999");//这是必须要有的,因为这是ortherwise里面的内容
        map.put("id", "64bceed33dc542a589470981f8a9c42d");
        int blogs = mapper.updateBlog(map);

        System.out.println("修改了" + blogs + "个条目");
//        session.commit();//暂时没有提交，觉得没有必要

        session.close();
    }
}
