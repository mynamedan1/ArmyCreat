package army.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.UserTaskMapper;
import army.db.pojo.UserTask;

@Service
public class UserTaskService {
	@Autowired
	private UserTaskMapper userTaskDao;

	public boolean addUserTask(UserTask userTask) {
		return userTaskDao.insert(userTask) == 1 ? true : false;
	}

	public boolean updateUserTask(UserTask userTask) {
		return userTaskDao.updateByPrimaryKeySelective(userTask) == 1 ? true : false;
	}

}
