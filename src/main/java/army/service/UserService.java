package army.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.UserMapper;
import army.db.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userDao;
	
	public boolean insertUser(User user) {
		return userDao.insert(user)==1?true:false;	
	}
}
