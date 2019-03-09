package army.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import army.db.pojo.User;
import army.service.RedisTokenManager;
import army.service.UserService;
import utils.ImageCreat;
import utils.JWT;
import utils.MD5Utils;
import utils.ResponseCode;
import utils.ServerResponse;
import utils.TimeUntils;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Value("${tomact_dir}")
	private String tomact_dir;

	@Autowired
	private RedisTokenManager redisRokenManager;

	// 用户注册
	@RequestMapping("register.do")
	@ResponseBody
	public ServerResponse registerUser(HttpServletRequest request, HttpServletResponse response, User user,
			Model model) {
		user.setUpdatetime(TimeUntils.dataToString(new Date()));
		ServerResponse serverResponse;
		user.setPassword(MD5Utils.stringMD5(user.getPassword()));
		if (userService.insertUser(user)) {
			serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(), "用户注册成功");
		} else {
			serverResponse = new ServerResponse(ResponseCode.ERROR.getCode(), "用户注册失败");
		}
		return serverResponse;
	}

	// 更新用户信息，未完成
	@RequestMapping("updateuser.do")
	@ResponseBody
	public ServerResponse updateUser(MultipartFile partFile,User user, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (!partFile.isEmpty()) {
			String filePath = tomact_dir + "/army/person/" + user.getCertificatenumber() + ".jpg";
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				partFile.transferTo(file);
				user.setImgurl(filePath);
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
		user.setId(((User) request.getAttribute("currentUser")).getId());
		if (userService.updateUser(user)) {
			return ServerResponse.createBySuccess("用户更新成功");
		} else {
			return ServerResponse.createByError("用户更新失败");
		}
	}

	// 用户登陆
	@RequestMapping("login.do")
	@ResponseBody
	public ServerResponse login(HttpServletRequest request, HttpServletResponse response, String cardCode,
			String password) {
		password = MD5Utils.stringMD5(password);
		User user = userService.checkLogin(cardCode, password);
		ServerResponse serverResponse;
		if (user != null) {
			redisRokenManager.setToken(user);
			response.setHeader("key", MD5Utils.stringMD5(user.getId() + ""));
			return ServerResponse.createBySuccess("登录成功", user);
		} else {
			return ServerResponse.createByError("账号或密码错误！");
		}

	}

	// 用户登出
	@RequestMapping("logout.do")
	@ResponseBody
	public ServerResponse logout(HttpServletRequest request, HttpServletResponse response) {
		redisRokenManager.deleteToken(((User) request.getAttribute("currentUser")).getId() + "");
		request.removeAttribute("currentUser");
		return ServerResponse.createBySuccess("登出");
	}

	// 修改密码
	@RequestMapping("changePassowrd.do")
	@ResponseBody
	public ServerResponse changePassowrd(HttpServletRequest request, HttpServletResponse response, String oldePwd,
			String newPwd) {
		User user = (User) request.getAttribute("currentUser");
		if (user.getPassword().equals(MD5Utils.stringMD5(oldePwd))) {
			user.setPassword(MD5Utils.stringMD5(newPwd));
			if (userService.changePassword(user)) {
				request.setAttribute("currentUser", user);
				return ServerResponse.createBySuccess("密码修改成功");
			} else {
				return ServerResponse.createByError("密码修改失败");
			}
		} else {
			return ServerResponse.createByError("原始密码错误");
		}

	}

	// 用户列表查询分页查询
	@RequestMapping("getAllUser.do")
	@ResponseBody
	public ServerResponse getAllUser(HttpServletRequest request, HttpServletResponse response, int pageNumber,
			int pageSize) {
		return ServerResponse.createBySuccess("用户列表", userService.getAllUser(pageNumber, pageSize));

	}

	// 用户模糊查询
	@RequestMapping("getUserByCondition.do")
	@ResponseBody
	public ServerResponse getUserByCondition(HttpServletRequest request, HttpServletResponse response, User user,
			Model model, Integer stratPoint, Integer endPoint) {
		return ServerResponse.createBySuccess("用户列表", userService.getUserByCondition(user, stratPoint, endPoint));

	}

	// 用户模糊查询
	@RequestMapping("deleteUser.do")
	@ResponseBody
	public ServerResponse deleteUser(HttpServletRequest request, HttpServletResponse response, int id) {
		if (userService.deleteUser(id)) {
			return ServerResponse.createBySuccess("用户删除成功");
		} else {
			return ServerResponse.createByError("用户删除失败");
		}

	}

	// 获取验证码
	@RequestMapping("getVerifyCode.do")
	public void generate(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String verifyCodeValue = new ImageCreat().drawImg(output);
		System.out.println(verifyCodeValue);
		// 将校验码保存到session中
		session.setAttribute("verifyCodeValue", verifyCodeValue);
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 验证验证码
	@RequestMapping("verification.do")
	@ResponseBody
	public ServerResponse verification(HttpServletResponse response, HttpSession session, String verifyCode) {
		String exitCode = (String) session.getAttribute("verifyCodeValue");
		ServerResponse serverResponse;
		if (verifyCode.equals(exitCode)) {
			serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(), "验证码正确");
		} else {
			serverResponse = new ServerResponse(ResponseCode.ERROR.getCode(), "验证码错误");
		}
		return serverResponse;
	}

	// excel数据导入
	@RequestMapping("fileUpload.do")
	@ResponseBody
	public ServerResponse UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return userService.ajaxUploadExcel(request, response);
	}
}
