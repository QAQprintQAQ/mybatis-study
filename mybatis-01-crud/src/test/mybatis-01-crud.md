 #mybatis-01-crud
 ##select
 * 在mybatis-01-base的基础上增删改查只需要修改
 UserMapper.java,UserMapper.xml,UserDaoTest.java这个三个文件 
 
1.在UserMapper.java中添加对应方法(需求：根据id查询用户)
~~~~
public interface UserMapper {
   //查询全部用户
  public List<User> selectUser();
   //根据id查询用户
  public User selectUserById(int id);
}
~~~~
 
2.在UserMapper.xml中添加Select语句
 ~~~~
<select id="selectUserById" resultType="com.liwenli.pojo.User">
   select * from user where id = #{id}
   </select>

~~~~

3.测试类中测试
~~~~
 @Test
  public void tsetSelectUserById() {
     SqlSession session = MybatisUtils.getSession();  //获取SqlSession连接
     UserMapper mapper = session.getMapper(UserMapper.class);
     User user = mapper.selectUserById(1);
     System.out.println(user);
     session.close();
  }
~~~~

##insert
1.在UserMapper.java接口中添加对应的方法(需求：给数据库增加一个用户)

~~~~
public interface UserMapper {
   
   //添加一个用户
  public int addUser(User user);

}
~~~~

2.在UserMapper.xml中添加insert语句
~~~~
 <insert id="addUser" parameterType="com.liwenli.pojo.User">
    insert into user (id,name,pwd) values (#{id},#{name},#{pwd})
</insert>
~~~~
 
3.测试类中测试
  ~~~~
  @Test
      public void testAddUser() {
          SqlSession session = MybatisUtils.getSession();
          UserMapper mapper = session.getMapper(UserMapper.class);
          User user = new User(5,"wangshuwei","wangshuwei");
          int i = mapper.addUser(user);
          System.out.println("成功增加"+i+"个条目");
          session.commit(); //提交事务,重点!不写的话不会提交到数据库
          session.close();
      }
  ~~~~

##update
1.在UserMapper.java接口中添加对应的方法(需求：修改用户的信息)
~~~~
  
public interface UserMapper {
   
   //修改一个用户
    public int updateUser(User user);

}
~~~~

2.在UserMapper.xml中添加update语句
  ~~~~
   <update id="updateUser" parameterType="com.liwenli.pojo.User">
    update user set name=#{name},pwd=#{pwd} where id = #{id}
  </update>
  ~~~~
3.测试类中测试
  ~~~~
   @Test
     public void testUpdateUser() {
         SqlSession session = MybatisUtils.getSession();
         UserMapper mapper = session.getMapper(UserMapper.class);
         User user = mapper.selectUserById(5);
         user.setName("王淑玮");
         user.setPwd("wangshuwei");
         int i = mapper.updateUser(user);
         System.out.println("成功修改"+i+"个条目");
         session.commit(); //提交事务,重点!不写的话不会提交到数据库
         session.close();
     }

  ~~~~

##delete
1.在UserMapper.java接口中添加对应的方法(需求：根据删除一个用户的信息)
~~~~
  
public interface UserMapper {
   
   //根据删除一个用户的信息
    public int deleteUser(int id);

}
~~~~

2.在UserMapper.xml中添加delete语句
  ~~~~
  <delete id="deleteUser" parameterType="int">
    delete from user where id = #{id}
  </delete>
  ~~~~
3.测试类中测试
  ~~~~
   @Test
      public void testDeleteUser() {
          SqlSession session = MybatisUtils.getSession();
          UserMapper mapper = session.getMapper(UserMapper.class);
          int i = mapper.deleteUser(5);
          System.out.println("成功删除"+i+"个条目");
  //        session.commit(); //提交事务,重点!不写的话不会提交到数据库
          session.close();
      }
  ~~~~


