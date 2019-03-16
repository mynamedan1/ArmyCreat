package army.db.dao;

import army.db.pojo.UserTask;

public interface UserTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTask record);

    int insertSelective(UserTask record);

    UserTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTask record);

    int updateByPrimaryKey(UserTask record);
}