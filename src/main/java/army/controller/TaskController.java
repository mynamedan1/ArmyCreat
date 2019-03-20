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
				String filePath = tomact_dir + "/army/study/" + task.getId() + ".jpg";
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					partFile.transferTo(file);
					task.setImgurl(filePath);
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
		task.setUpdatetime(TimeUntils.dataToString(new Date()));
		if (taskService.insertTask(task)) {
			return ServerResponse.createBySuccess("任务发布成功");
		}

		return ServerResponse.createByError("任务发布失败");
	}

	// admin任务状态修改
	@RequestMapping("changeTaskStatus.do")
	@ResponseBody
	public ServerResponse changeTaskStatus(HttpServletRequest request, HttpServletResponse response, Task task,
			MultipartFile partFile, Model model) {
		if (task.getState() == 3) {
			if (null != partFile) {
				if (partFile.isEmpty()) {
					ServerResponse.createByError("请上传支付二维码");
				} else {
					String filePath = tomact_dir + "/army/pay/" + task.getId() + ".jpg";
					File file = new File(filePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					try {
						partFile.transferTo(file);
						task.setExtra(filePath);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						return ServerResponse.createByError("二维码上传失败");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						return ServerResponse.createByError("二维码上传失败");
					}
				}
			}else {
				ServerResponse.createByError("请上传支付二维码");
			}
		}
		if (taskService.claimTask(task)) {
			return ServerResponse.createBySuccess("任务状态更新成功");
		}

		return ServerResponse.createByError("任务状态修改失败");
	}

	// admin任务分页查询
	@RequestMapping("getAllTask.do")
	@ResponseBody
	public ServerResponse getAllTask(HttpServletRequest request, HttpServletResponse response, int pageNumber,
			int pageSize, Model model) {
		return ServerResponse.createBySuccess("用户列表", taskService.getAllTask(pageNumber, pageSize));

	}

	// admin任务模糊查询
	@RequestMapping("getTaskByCondition.do")
	@ResponseBody
	public ServerResponse getTaskByCondition(HttpServletRequest request, HttpServletResponse response, Task task,
			Model model) {
		return ServerResponse.createByError("任务获取失败");
	}

	// ----------------------------------------------------普通用户任务管理---------------------------------------------

	// 个人app端任务查询，根据状态查询 //-1下架，0待审批，1待认领，2已认领，3代支付，4已支付，5已完成
	@RequestMapping("getAllTaskByUser.do")
	@ResponseBody
	public ServerResponse getAllTaskByUser(HttpServletRequest request, HttpServletResponse response,
			String certificatenumber, int state, Model model) {

		return ServerResponse.createByError("任务获取失败");
	}

	// 我的发布
	@RequestMapping("getAllTaskByRelwase.do")
	@ResponseBody
	public ServerResponse getAllTaskByRelwase(HttpServletRequest request, HttpServletResponse response,
			String certificatenumber, Model model) {
		return ServerResponse.createByError("任务获取失败");
	}

}
