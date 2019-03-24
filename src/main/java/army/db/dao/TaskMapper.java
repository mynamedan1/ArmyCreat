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
    
    //任务分页查询
    List<Task> getAllTask(@Param("pageNumber")int pageNumber,@Param("pageSize") int pageSize,@Param("state") int state);
    //任务模糊查询
    List<Task> getTaskByCondition(@Param("task")Task  task);
    //
    List<Task> getUserTaskByState(@Param("userId")Integer userId,@Param("state")Integer state);
    
    List<Task> getReleaseTask(Integer userId);
    
    
}