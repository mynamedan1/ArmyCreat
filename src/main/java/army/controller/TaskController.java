package army.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import army.db.pojo.Task;
import army.db.pojo.User;
import army.service.TaskService;
import utils.ServerResponse;
import utils.TimeUntils;

@Controller
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService taskService;

	@Value("${tomact_dir}")
	private String tomact_dir;

	// admin发布任务
	@RequestMapping("insertTask.do")
	@ResponseBody
	public ServerResponse insertTask(HttpServletRequest request, HttpServletResponse response, Task task,
			MultipartFile partFile, Model model) {
		if (null != partFile) {
			if (!partFile.isEmpty()) {
				String filePath = tomact_dir + "/task/" + task.getId() + ".jpg";
				String setPath = "/task/" + task.getId() + ".jpg";
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					partFile.transferTo(file);
					task.setImgurl(setPath);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					return ServerResponse.createByError("文件上传失败");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					return ServerResponse.createByError("文件上传失败");
				}
			}
		}
		task.setReleaseby(((User) request.getAttribute("currentUser")).getId());
		task.setUpdatetime(TimeUntils.dataToStringForDate(new Date()));
		if (taskService.insertTask(task)) {
			return ServerResponse.createBySuccess("任务发布成功");
		}

		return ServerResponse.createByError("任务发布失败");
	}

	// admin实战任务更新
	@RequestMapping("changeTaskStatus.do")
	@ResponseBody
	public ServerResponse changeTaskStatus(HttpServletRequest request, HttpServletResponse response, Task task,
			Model model) {
		if (taskService.claimTask(task)) {
			return ServerResponse.createBySuccess("任务状态更新成功");
		}
		return ServerResponse.createByError("任务状态修改失败");
	}

	// 任务查询，app端传入state 1
	@RequestMapping("getAllTask.do")
	@ResponseBody
	public ServerResponse getAllTask(HttpServletRequest request, HttpServletResponse response, int state, Model model) {
		return ServerResponse.createBySuccess("用户列表", taskService.getAllTask(state));

	}

	// admin任务模糊查询
	@RequestMapping("getTaskByCondition.do")
	@ResponseBody
	public ServerResponse getTaskByCondition(HttpServletRequest request, HttpServletResponse response, Task task,
			Model model) {
		return ServerResponse.createBySuccess("任务获取成功", taskService.getTaskByCondition(task));
	}

	// ----------------------------------------------app端接口-------------------------------------------------------
	// 2已认领，3代支付，4完成
	@RequestMapping("getUserTask.do")
	@ResponseBody
	public ServerResponse getUserTask(HttpServletRequest request, HttpServletResponse response, int userId, int state,
			Model model) {
		return ServerResponse.createBySuccess("任务列表", taskService.getUserTaskByState(userId, state));
	}

	// 我的发布 全部 待审批
	@RequestMapping("getReleaseTask.do")
	@ResponseBody
	public ServerResponse getReleaseTask(HttpServletRequest request, HttpServletResponse response, int userId,Integer state,
			Model model) {
		return ServerResponse.createBySuccess("我的发布", taskService.getReleaseTask(userId,state));
	}

	// 我的发布按照状态查询  2已认领，3代支付，4已完成，
	@RequestMapping("getReleaseTaskByState.do")
	@ResponseBody
	public ServerResponse getReleaseTaskByState(HttpServletRequest request, HttpServletResponse response, int userId,
			int state, Model model) {
		return ServerResponse.createBySuccess("任务列表", taskService.getReleaseTaskByState(userId, state));
	}
}
