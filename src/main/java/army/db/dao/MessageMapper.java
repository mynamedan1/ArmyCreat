package army.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import army.db.pojo.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    
    List<Message> getMessage(@Param("userId")int userId,@Param("type")int type);
}