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
}
