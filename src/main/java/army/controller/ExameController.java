package army.controller;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.Exam;
import army.db.pojo.Question;
import army.db.pojo.User;
import army.service.ExameService;
import utils.MD5Utils;
import utils.ResponseCode;
import utils.ServerResponse;
import utils.TimeUntils;

@Controller
@RequestMapping("/exam")
public class ExameController {
	@Autowired
	private ExameService exameService;

	// 添加题目
	@RequestMapping("addQuestion.do")
	@ResponseBody
	public ServerResponse addQuestion(HttpServletRequest request, HttpServletResponse response, Question question,
			Model model) {
		ServerResponse serverResponse;
		if (exameService.insertQuestion(question)) {
			serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(), "题目添加成功");
		} else {
			serverResponse = new ServerResponse(ResponseCode.ERROR.getCode(), "题目添加失败");
		}
		return serverResponse;
	}
	
	//添加题目之前先添加试卷
	@RequestMapping("addExam.do")
	@ResponseBody
	public ServerResponse addExam(HttpServletRequest request, HttpServletResponse response, Exam exam,
			Model model) {
		ServerResponse serverResponse;
		exam.setUpdatetime(TimeUntils.dataToString(new Date()));
		if (exameService.insertExam(exam)!=-1) {
			HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
			hashMap.put("examId", exam.getId());
			serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(),hashMap, "题目添加成功");
		} else {
			serverResponse = new ServerResponse(ResponseCode.ERROR.getCode(), "题目添加失败");
		}
		return serverResponse;
	}


}
