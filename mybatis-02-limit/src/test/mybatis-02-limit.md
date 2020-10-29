#mybatis-02-limit
* 如果查询大量数据的时候，我们往往使用分页进行查询
##语法
~~~~
  SELECT * FROM table LIMIT stratIndex，pageSize
  
  SELECT * FROM table LIMIT 5,10; // 检索记录行 6-15  
  
  #为了检索从某一个偏移量到记录集的结束所有的记录行，可以指定第二个参数为 -1：   
  SELECT * FROM table LIMIT 95,-1; // 检索记录行 96-last.  
  
  #如果只给定一个参数，它表示返回最大的记录行数目：   
  SELECT * FROM table LIMIT 5; //检索前 5 个记录行  
  
  #换句话说，LIMIT n 等价于 LIMIT 0,n。 
~~~~

##步骤
1.Mapper.java接口，参数为map
~~~~
package com.liwenli.dao;

import com.liwenli.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
   public List<User> selectUserByLimit(Map<String,Object> map);
    /*
    mybatis中不再需要去实现这个接口了，直接通过配置文件相当于实现这个接口，简化了代码

     */
}

~~~~
2.修改Mapper.xml文件
~~~~

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liwenli.dao.UserMapper">
    <!--是namespace="com.liwenli.dao.UserMapper" 而不是namespace="com/liwenli/dao/UserMapper"-->





<!--    <select id="selectUserByLimit" resultType="com.liwenli.pojo.User" parameterType="java.util.Map">-->
<!--        &lt;!&ndash; id对应于UserMapper中的getUserList方法名 resultType对应于getUserList中返回的类名,user是"com.liwenli.pojo.User的别名&ndash;&gt;-->

<!--        select * from user limit #{startIndex},#{pageSize};-->


<!--    </select>-->






    <resultMap id="UserMap" type="user">
        <id column="id" property="id"/>
        <id column="name" property="name"/>
        <id column="pwd" property="pwd"/>
    </resultMap>

    <select id="selectUserByLimit" parameterType="map" resultMap="UserMap">
        <!--map是java.util.Map的别名,这是mybatis默认的别名,就像Integer的别名是int一样,而基本类型int的别名是_int,这是需要我们注意的-->
                select * from user limit #{startIndex},#{pageSize};

    </select>
</mapper>


~~~~

3.修改UserDaoTest.java
~~~~

package com.liwenli.dao;

import com.liwenli.pojo.User;
import com.liwenli.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class UserDaoTest {


    @Test
    public void testselectUserByList() {
        SqlSession session = MybatisUtils.getSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("startIndex", 3);
        map.put("pageSize", 2);
        List<User> users = mapper.selectUserByLimit(map);
        for (User user: users){
            System.out.println(user);
        }
        session.close();
    }
}

~~~~

