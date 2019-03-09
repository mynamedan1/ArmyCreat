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
		return taskDao.updateByPrimaryKey(task)==1?true:false;
	}
	
	public boolean changeTaskStatus(Task task) {
		return taskDao.updateByPrimaryKey(task)==1?true:false;
	}
	
	public List<Task> getAllTask(int pageNumber,int pageSize) {
		return null;
	}
	
	public List<Task> getTaskByCondition(Task task) {
		return null;
	}

}
