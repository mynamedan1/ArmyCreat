package army.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.Message;
import army.db.pojo.User;
import army.service.MessageService;
import army.service.RedisTokenManager;
import utils.ServerResponse;

@Controller
@RequestMapping("/message")
public class MessageController {
	   @Autowired
	   private MessageService messageService;
	   
	   @Autowired
		private RedisTokenManager redisRokenManager;
	
	    // 用户中心获取消息 type=1 用户消息 type=2 系统消息
		@RequestMapping("getMessage.do")
		@ResponseBody
		public ServerResponse getMessage(HttpServletRequest request, HttpServletResponse response,int type) {
			return ServerResponse.createBySuccess("信息",messageService.getMessage(((User) request.getAttribute("currentUser")).getId(),type));
		}
		//更新消息状态，变为已读
		@RequestMapping("updateMessage.do")
		@ResponseBody
		public ServerResponse updateMessage(HttpServletRequest request, HttpServletResponse response,Message message,Model model) {
			return ServerResponse.createBySuccess("跟新信息状态",messageService.updateMessage(message));
		}

		


}
