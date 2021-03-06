package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.TaskMapper;
import army.db.pojo.OrderModel;
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
	
	public boolean deleteTask(int  id) {
		return taskDao.deleteByPrimaryKey(id)==1?true:false;
	}
    
	public Task selectByPrimaryKey(int id) {
		return taskDao.selectByPrimaryKey(id);
	}
	
	
	public List<Task> getAllTask(int state,int level) {
		return taskDao.getAllTask(state,level);
	}
	
	public List<Task> getTaskByCondition(Task task) {
		return taskDao.getTaskByCondition(task);
	}
	
	public List<Task> getUserTaskByState(int userId,int state){
		return taskDao.getUserTaskByState(userId, state);
	}
	
	public List<Task> getReleaseTask(int userId,int state){
		return taskDao.getReleaseTask(userId,state);
	}
	
	public List<Task> getReleaseTaskByState(int userId,int state){
		return taskDao.getReleaseTaskByState(userId, state);
	}

	public List<OrderModel> getOrder(OrderModel orderModel){
		return taskDao.getOrderByCondition(orderModel);
	}
	
	public boolean updateTask(Task task) {
		return taskDao.updateByPrimaryKeySelective(task)==1?true:false;
	}
	

}
