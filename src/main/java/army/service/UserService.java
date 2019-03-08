package army.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import army.db.dao.UserMapper;
import army.db.pojo.User;
import utils.ExcelUtil;
import utils.MD5Utils;
import utils.ResponseCode;
import utils.ServerResponse;
import utils.TimeUntils;

@Service
public class UserService {
	@Autowired
	private UserMapper userDao;

	public boolean insertUser(User user) {
		return userDao.insert(user) == 1 ? true : false;
	}

	public boolean updateUser(User user) {
		return userDao.updateByPrimaryKeySelective(user) == 1 ? true : false;
	}

	public User checkLogin(String cardCode, String password) {
		return userDao.checkLogin(cardCode, password);
	}

	public boolean changePassword(User user) {
		return userDao.updatePassword(user.getId(), user.getPassword()) == 1 ? true : false;
	}

	public List<User> getAllUser(int pageNumber, int pageSize) {
		return userDao.getAllUser((pageNumber-1)*pageSize, pageSize);
	}
	
	public List<User> getUserByCondition(User user,int stratPoint,int endPoint) {
		return userDao.getUserByCondition(user,stratPoint,endPoint);
	}

	public boolean deleteUser(int id) {
		return userDao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	

	// 任务完成，增加积分
	public void changePointCount() {

	}

	@Transactional
	public boolean insertUserByList(List<User> userList) {
		return userDao.inserByList(userList) == 1 ? true : false;
	}

	// 读取excel,插入数据库
	public ServerResponse ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<User> userList = new ArrayList<User>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile file = multipartRequest.getFile("upfile");
		if (file.isEmpty()) {
			try {
				throw new Exception("文件不存在！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		InputStream in = null;
		try {
			in = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<List<Object>> listob = null;
		try {
			listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < listob.size(); i++) {
			List<Object> lo = listob.get(i);
			User user = new User();
			user.setName(String.valueOf(lo.get(0)).length() == 0 ? null : String.valueOf(lo.get(0)));
			user.setCertificatenumber(String.valueOf(lo.get(1)).length() == 0 ? null : String.valueOf(lo.get(1)));
			user.setIdcard(String.valueOf(lo.get(2)).length() == 0 ? null : String.valueOf(lo.get(2)));
			try {
				user.setPhonenumber(Integer.parseInt(String.valueOf(lo.get(3))));
			} catch (Exception e) {
				return ServerResponse.createByError("数据错误，请检查数据");
			}
			user.setPassword(MD5Utils.stringMD5("123456"));// 默认密码123456
			user.setPointcount(0);// 初使积分0
			user.setImporttype("批量添加");// 添加方式：批量添加
			user.setState(1);// 激活状态
			user.setUpdatetime(TimeUntils.dataToString(new Date()));
			userList.add(user);
		}
		try {
			insertUserByList(userList);
		} catch (Exception e) {
			return ServerResponse.createByError("批量插入失败，请检查数据");
		}
		return ServerResponse.createBySuccess("插入成功");
	}
}
