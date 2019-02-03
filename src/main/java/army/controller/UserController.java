package army.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.User;
import army.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired 
	private UserService userService;
	
	@RequestMapping("register.do")
	@ResponseBody
	public String registerUser(User user,Model model) {
		if(userService.insertUser(user)) {
			return "register success";
		}else {
			return "register failed";
		}
		
	}
}
