package army.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.Task;
import army.service.TaskService;
import utils.ServerResponse;

@Controller
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService taskService;

	@Value("${tomact_dir}")
	private String tomact_dir;

	    //发布任务
		@RequestMapping("insertTask.do")
		@ResponseBody
		public ServerResponse insertTask(HttpServletRequest request, HttpServletResponse response, Task task,
				Model model) {
			if(taskService.insertTask(task)) {
				return ServerResponse.createBySuccess("任务发布成功");
			}
			
			return ServerResponse.createByError("任务发布失败");
		}
		
		 //任务状态修改
		@RequestMapping("changeTaskStatus.do")
		@ResponseBody
		public ServerResponse changeTaskStatus(HttpServletRequest request, HttpServletResponse response, Task task,
				Model model) {
			
			return ServerResponse.createByError("任务发布失败");
		}
		
		 //任务分页查询
		@RequestMapping("getAllTask.do")
		@ResponseBody
		public ServerResponse getAllTask(HttpServletRequest request, HttpServletResponse response, int pageNumber,int pageSize,
				Model model) {
			
			return ServerResponse.createByError("任务获取失败");
		}
		
		 //任务模糊查询
		@RequestMapping("getTaskByCondition.do")
		@ResponseBody
		public ServerResponse getTaskByCondition(HttpServletRequest request, HttpServletResponse response, Task task,
				Model model) {
			
			return ServerResponse.createByError("任务获取失败");
		}




}
