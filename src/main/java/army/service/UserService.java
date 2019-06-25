package army.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if(user.getPointcount()!=null) {
			HashMap<String, String> hashMap = (HashMap<String, String>) getLevelInfo(user.getPointcount());
			user.setLevelvalue(Integer.parseInt(hashMap.get("levelValue")));
			user.setLavelname(hashMap.get("levelName"));
		}
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
	
	public List<User> getUserByCondition(User user) {
		return userDao.getUserByCondition(user);
	}

	public boolean deleteUser(int id) {
		return userDao.deleteByPrimaryKey(id) == 1 ? true : false;
	}
	
	public User getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
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
				user.setPhonenumber(String.valueOf(lo.get(3)));
			} catch (Exception e) {
				return ServerResponse.createByError("数据错误，请检查数据");
			}
			user.setPassword(MD5Utils.stringMD5("123456"));// 默认密码123456
			user.setPointcount(0);// 初使积分0
			user.setImporttype("批量添加");// 添加方式：批量添加
			user.setState(1);// 激活状态
			user.setLevelvalue(1);
			user.setLavelname("士兵");
			user.setUpdatetime(TimeUntils.dataToString(new Date()));
			userList.add(user);
		}
		for(int i=0;i<userList.size();i++) {
			try {
			   userDao.insertSelective(userList.get(i));
			} catch (Exception e) {
			  return ServerResponse.createByError("批量插入失败，请检查第"+(i+1)+"条数据");
			}

		}
//		try {
//		insertUserByList(userList);
//		} catch (Exception e) {
//			return ServerResponse.createByError("批量插入失败，请检查数据");
//		}
		return ServerResponse.createBySuccess("插入成功");
	}
	
	public Map<String, String> getLevelInfo(int point) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		if (0 <= point && point < 50) {
			hashMap.put("levelValue", "1");
			hashMap.put("levelName", "士兵");
		}
		if (50 <= point && point < 100) {
			hashMap.put("levelValue", "2");
			hashMap.put("levelName", "二等兵");
		}
		if (100 <= point && point < 200) {
			hashMap.put("levelValue", "3");
			hashMap.put("levelName", "一等兵");
		}
		if (200 <= point && point < 500) {
			hashMap.put("levelValue", "4");
			hashMap.put("levelName", "下士");
		}
		if (500 <= point && point < 1000) {
			hashMap.put("levelValue", "5");
			hashMap.put("levelName", "中士");
		}
		if (1000 <= point && point < 3000) {
			hashMap.put("levelValue", "6");
			hashMap.put("levelName", "上士");
		}
		if (3000 <= point && point < 5000) {
			hashMap.put("levelValue", "7");
			hashMap.put("levelName", "少尉");
		}
		if (5000 <= point && point < 10000) {
			hashMap.put("levelValue", "8");
			hashMap.put("levelName", "中尉");
		}
		if (10000 <= point && point < 300000) {
			hashMap.put("levelValue", "9");
			hashMap.put("levelName", "上尉");
		}
		if (30000 <= point && point < 50000) {
			hashMap.put("levelValue", "10");
			hashMap.put("levelName", "少校");
		}
		if (50000 <= point && point < 100000) {
			hashMap.put("levelValue", "11");
			hashMap.put("levelName", "中校");
		}
		if (100000 <= point && point < 110000) {
			hashMap.put("levelValue", "12");
			hashMap.put("levelName", "上校");
		}
		if (110000 <= point && point < 200000) {
			hashMap.put("levelValue", "13");
			hashMap.put("levelName", "少将");
		}
		if (200000 <= point && point < 300000) {
			hashMap.put("levelValue", "14");
			hashMap.put("levelName", "中将");
		}
		if (300000 <= point && point < 588900) {
			hashMap.put("levelValue", "15");
			hashMap.put("levelName", "上将");
		}
		if (588900 <= point) {
			hashMap.put("levelValue", "16");
			hashMap.put("levelName", "大将军");
		}
		return hashMap;
	}

}
