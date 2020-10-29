 #mybatis-01-map
 ##map
1.在UserMapper.java接口中添加对应的方法(需求：给数据库增加一个用户)

~~~~
public interface UserMapper {
   
   //添加一个用户
   public int addUser2(Map<String,Object>map);//只需要传入一个map


}
~~~~

2.在UserMapper.xml中添加insert语句
~~~~

<insert id="addUser2" parameterType="java.util.Map">
    <!--总结：如果参数过多，我们可以考虑直接使用Map实现，如果参数比较少，直接传递参数即可-->
    insert into user (id,name,pwd) values (#{id},#{name},#{pwd})
</insert>

~~~~
 
3.测试类中测试
  ~~~~
  @Test
    public void testAddUser2() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<String, Object>();
        /*
        总结：如果参数过多，我们可以考虑直接使用Map实现，如果参数比较少，直接传递参数即可

         */
        map.put("id", 7);
        map.put("name", "李远胜");
        map.put("pwd", "liyuansheng");
        int i = mapper.addUser2(map);
        System.out.println("成功增加"+i+"个条目");
        session.commit(); //提交事务,重点!不写的话不会提交到数据库
        session.close();
    }
  ~~~~

4.注意事项
* public int addUser2(Map<String,Object>map); 返回值是int(Integer)
~~~~
   public int addUser2(Map<String,Object>map);//只需要传入一个map
~~~~
* parameterType = "java.util.Map"
~~~~
<insert id="addUser2" parameterType="java.util.Map">
~~~~
* 尝试过通过用map的查询以失败告终
