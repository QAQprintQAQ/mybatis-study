package com.liwenli.dao;

import com.liwenli.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //新增一个博客
    public int addBlog(Blog blog);

    public List<Blog> queryBlogIf(Map map);
//更新语句
    public int updateBlog(Map map);
    public List<Blog> queryBlogChoose(Map map);


}
