package army.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.HonorRecord;
import army.db.pojo.User;
import army.service.HonorService;
import army.service.UserService;
import utils.MD5Utils;
import utils.ServerResponse;
import utils.TimeUntils;

@Controller
@RequestMapping("/honor")
public class HonorController {
	@Autowired
	private HonorService honorService;
	@Autowired
	private UserService userService;

	// 打卡，添加荣誉点
	@RequestMapping("clock.do")
	@ResponseBody
	public ServerResponse login(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute("currentUser");
		if (!honorService.checkTodayLock(user.getId(), TimeUntils.dataToStringForDate(new Date()))) {
			return ServerResponse.createBySuccess("今日已打卡");
		} else {
			HonorRecord honorRecord = new HonorRecord();
			honorRecord.setPoint(1);
			honorRecord.setTime(TimeUntils.dataToStringForDate(new Date()));
			honorRecord.setType(1);
			honorRecord.setTypeexpense("完成当日打卡 获得1个荣誉");
			honorRecord.setUserid(((User) request.getAttribute("currentUser")).getId());
			if (honorService.addHonorRecord(honorRecord)) {
				user.setPointcount(user.getPointcount() + 1);
				userService.updateUser(user);
				return ServerResponse.createBySuccess("打卡成功");
			} else {
				return ServerResponse.createByError("打开失败，请重新打卡！");
			}
		}
	}

	// 查询荣誉点记录
	@RequestMapping("gethonors.do")
	@ResponseBody
	public ServerResponse gethonors(HttpServletRequest request, HttpServletResponse response) {
		int userId = ((User) request.getAttribute("currentUser")).getId();
		return ServerResponse.createBySuccess("荣誉记录", honorService.getHonorList(userId));

	}

}
