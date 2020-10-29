 #mybatis-04-IF
 * 环境基于mybatis-04-activeSQL
##BlogMapper.java
~~~~
//需求1
List<Blog> queryBlogIf(Map map);
~~~~

##BlogMapper.xml
~~~~

<!--
需求：
根据作者名字和博客名字来查询博客！
如果作者名字为空，那么只根据博客名字查询，反之，则根据作者名来查询
select * from blog where title = #{title} and author = #{author}
-->
    <select id="queryBlogIf" parameterType="map" resultType="blog">
        select * from blog where 1=1
        <!-- "where 1=1" 用来保证永远都能查到东西 相当于"where true"-->
        <if test="title != null">
            and title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </select>
 
~~~~

##MyTest 测试
~~~~
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
~~~~