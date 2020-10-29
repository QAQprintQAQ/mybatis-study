 #mybatis-04-trim
 ##where
 ~~~~
<select id="queryBlogIf" parameterType="map" resultType="blog">
        select * from blog
        <where>
        <!--
        “where”标签会知道如果它包含的标签中有返回值的话，它就插入一个‘where’。
        此外，如果标签返回的内容是以AND 或OR 开头的，则它会剔除掉。
        如果条件一为真,即title有值,则该下面语句等价于 select * from blog where title=#{title}
        如果条件一为假，二为真,即title没有值，author有值。则下面sql等价于 select * from blog where author=#{author}
"
        -->


            <if test="title != null">
                title = #{title}
            </if>
            <if test="author != null">
                and author = #{author}
            </if>
        </where>

    </select>
~~~~

##set
1.BlogMapper.java
~~~~
   public int updateBlog(Map map);
~~~~
2.BlogMapper.xml
~~~~
<update id="updateBlog" parameterType="java.util.Map">
        update blog
        <set>
        <!--
        set标签去除最后一个语句的","号
        如果不修改views,则"author=#{author},"末尾不应该有","。
        这时候就需要set标签将末尾的这条修改语句的","删掉
        update blog set  title = #{title}, author = #{author} where id =#{id}
        如果第一个title不要修改,则变为 update blog set author = #{author} where id =#{id}
        -->
            <if test="title != null">
                title = #{title},

            </if>

            <if test="author != null">
                author = #{author},

            </if>

            <if test="views != null">
                views = #{views}

            </if>

        </set>

        where id = #{id};

    </update>
~~~~
3.MyTest测试
~~~~
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
~~~~

##choose
1.BlogMapper.java
~~~~
    public List<Blog> queryBlogChoose(Map map);
~~~~
2.BlogMapper.xml
~~~~

    <select id="queryBlogChoose" parameterType="map" resultType="blog">
        select * from blog
        <where>
            <choose>
                <when test="title != null">
                    title = #{title}
                </when>
                <when test="author != null">
                    and author = #{author}
                </when>
                <otherwise>
                    and views = #{views}
                </otherwise>
            </choose>
        </where>
    </select>

~~~~
3.MyTest测试
~~~~

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


~~~~