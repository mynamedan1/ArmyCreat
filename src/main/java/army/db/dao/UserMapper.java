package army.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import army.db.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int updatePassword(@Param("id") Integer id,@Param("password") String password);
    //登陆验证
    User checkLogin(@Param("cardCode") String cardCode,@Param("password") String password);
    //批量插入
    int inserByList(List<User> userList);
    //分页查询用户
    List<User> getAllUser(@Param("pageNumber")int pageNumber,@Param("pageSize") int pageSize);
    //根据条件模糊查询
    List<User> getUserByCondition(@Param("user")User user,@Param("startPonit")Integer startPonit,@Param("endPonit")Integer endPonit);
}