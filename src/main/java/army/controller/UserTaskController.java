package army.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.UserTask;
import army.service.UserTaskService;
import utils.ServerResponse;

@Controller
@RequestMapping("/usertask")
public class UserTaskController {
	@Autowired
	private UserTaskService userTaskService;

	// 用户领取任务 userTask状态设置为2
	@RequestMapping("claimTask.do")
	@ResponseBody
	public ServerResponse claimTask(HttpServletRequest request, HttpServletResponse response, UserTask userTask,
			Model model) {
		if (userTaskService.addUserTask(userTask)) {
			return ServerResponse.createBySuccess("任务领取成功");
		} else {
			return ServerResponse.createByError("任务领取失败");
		}
	}

	// 用户任务状态更改


}
