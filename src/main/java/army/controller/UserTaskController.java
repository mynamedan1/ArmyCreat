package army.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import army.db.pojo.Task;
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
	// admin实战任务更新
//		@RequestMapping("changeTaskStatus.do")
//		@ResponseBody
//		public ServerResponse changeTaskStatus(HttpServletRequest request, HttpServletResponse response, Task task,
//				MultipartFile partFile, Model model) {
//			if (task.getState() == 3) {
//				if (null != partFile) {
//					if (partFile.isEmpty()) {
//						ServerResponse.createByError("请上传支付二维码");
//					} else {
//						String filePath = tomact_dir + "/army/pay/" + task.getId() + ".jpg";
//						File file = new File(filePath);
//						if (!file.exists()) {
//							file.mkdirs();
//						}
//						try {
//							partFile.transferTo(file);
//							task.setExtra(filePath);
//						} catch (IllegalStateException e) {
//							// TODO Auto-generated catch block
//							// e.printStackTrace();
//							return ServerResponse.createByError("二维码上传失败");
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							// e.printStackTrace();
//							return ServerResponse.createByError("二维码上传失败");
//						}
//					}
//				}else {
//					ServerResponse.createByError("请上传支付二维码");
//				}
//			}
//			if (taskService.claimTask(task)) {
//				return ServerResponse.createBySuccess("任务状态更新成功");
//			}
//
//			return ServerResponse.createByError("任务状态修改失败");
//		}



}
