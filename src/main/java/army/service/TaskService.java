package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.TaskMapper;
import army.db.pojo.Task;

@Service
public class TaskService {
	@Autowired
	private TaskMapper taskDao;
	
	public boolean insertTask(Task task) {
		return taskDao.insert(task)==1?true:false;
	}
	//更新任务状态
	public boolean claimTask(Task task) {
		return taskDao.updateByPrimaryKeySelective(task)==1?true:false;
	}
	
	
	public List<Task> getAllTask(int pageNumber,int pageSize) {
		return taskDao.getAllTask((pageNumber-1)*pageSize, pageSize);
	}
	
	public List<Task> getTaskByCondition(Task task) {
		return null;
	}

}
