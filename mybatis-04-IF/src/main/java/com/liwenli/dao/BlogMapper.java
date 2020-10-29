package com.liwenli.dao;

import com.liwenli.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //新增一个博客
    int addBlog(Blog blog);

    List<Blog> queryBlogIf(Map map);

}
