package army.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import army.db.pojo.Task;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    int updateByPrimaryKey(Task record);
    
    List<Task> getAllTask(@Param("pageNumber")int pageNumber,@Param("pageSize") int pageSize);
    
    List<Task> getTaskByCondition(Task  task);
}