#mybatis-03-anno
* mybatis注解不再需要Mapper.xml文件,因此mybaits-config.xml实现映射需要用class方法绑定
~~~~
 <mappers>
        <mapper class="com.liwenli.dao.UserMapper"/>
        <!-- 用class 绑定接口 -->
    </mappers>
~~~~


##mybatis-annotation
##@Select

1.编写Mapper.java注解接口
~~~~
 @Select("select * from user")//直接在接口名上写注解sql
   public List<User> getUserList();
   @Select("select * from user where id = #{id}")
   public User selectUserById(int id);
//   public User selectUserByIdNa(@Param("int") int id,@Param("String")String name);
//假如多个参数,则需要用"@Para("参数类型") 参数类型 变量"  的格式
~~~~
2.测试
~~~~
 @Test
    //@Test是junit的方法，使用@Test需要导入依赖junit.另外可以不需要junit而直接写main方法来测试

    public void testgetUserList() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> users = mapper.getUserList();
        for (User user: users){
            System.out.println(user);
        }
        session.close();
    }


    @Test
    public void tsetSelectUserById() {
        SqlSession session = MybatisUtils.getSession();  //获取SqlSession连接
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserById(1);
        System.out.println(user);
        session.close();
    }
~~~~

##@Insert

1.编写Mapper.java注解接口
~~~~
  @Insert("insert into user (id , name , pwd) values(#{id},#{name},#{pwd})")
    public int addUser(User user);
~~~~
2.测试
~~~~
  @Test
    public void testAddUser() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User(8,"zhengshenlin","zhengshenlin");
        int i = mapper.addUser(user);
        System.out.println("成功增加"+i+"个条目");
        session.commit(); //不能重复提交,数据库的主键会发生冲突,也就是说数据库中已经存在该用户了
        session.close();
    }
~~~~

##@Update

1.编写Mapper.java注解接口
~~~~
   @Update("update user set name = #{name},pwd = #{pwd} where id = #{id}")
    public int updateUser(User user);
~~~~
2.测试
~~~~
   @Test
     public void testUpdateUser() {
         SqlSession session = MybatisUtils.getSession();
         UserMapper mapper = session.getMapper(UserMapper.class);
         User user = mapper.selectUserById(8);
         user.setName("郑身林");
         user.setPwd("zhengshenlin");
         int i = mapper.updateUser(user);
         System.out.println("成功修改"+i+"个条目");
         session.commit(); //提交事务,重点!不写的话不会提交到数据库
         session.close();
     }
~~~~

  ##@Delete
  
  1.编写Mapper.java注解接口
  ~~~~
      @Delete("delete from user where id = #{id}")
      public int deleteUser(int id);

  ~~~~
  2.测试
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
  
##总结
* 注解本质:反射机制实现
* 注解底层:动态代理
