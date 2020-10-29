 #mybatis-01-like
 ##like查询
1.在UserMapper.java接口中添加对应的方法(需求：给数据库增加一个用户)

~~~~
public interface UserMapper {
//模糊查询like
  public List<User> getUserLike(String name);

}
~~~~

2.在UserMapper.xml中添加select语句
~~~~
<!--

   <select id="getUserLike" resultType="com.liwenli.pojo.User">

    select * from user where name like #{name};

   </select>

-->

<!--方式一和二只能配置一个，两个同时配置就会出错！-->
    <select id="getUserLike" resultType="com.liwenli.pojo.User">
        <!--方式二-->
        <!--需要返回类型 resultType="com.liwenli.pojo.User"-->
        select * from user where name like "%"#{name}"%";

    </select>



~~~~
 
3.测试类中测试
  ~~~~
    @Test
      public void testGetUserLike() {
          SqlSession session = MybatisUtils.getSession();
          UserMapper mapper = session.getMapper(UserMapper.class);
         // List<User> users = mapper.getUserLike("%李%");// 方式一 :一定要添加"%李%"通配符 
          List<User> users = mapper.getUserLike("李");// 方式二 :不要添加"%李%"通配符,前面的sql语句已经加上通配符了
  
  
          for (User user : users) {
              System.out.println(user);
  
          }
          session.close();
  
      }

  ~~~~

4.注意事项
* 方式一在测试类一定要添加"%李%"通配符
~~~~
    List<User> users = mapper.getUserLike("%李%");//一定要添加"%李%"通配符
~~~~

* 方式二在测试类中不能添加"%"通配符
~~~~

   List<User> users = mapper.getUserLike("李");// 方式二 :不要添加"%李%"通配符,前面的sql语句已经加上通配符了

~~~~

* 两种方式只能存在一种，另外一个必须注释掉，不然会报错

5.总结
* Java代码执行的时候，传递通配符% %
* 在sq|拼接中使用通配符!
 