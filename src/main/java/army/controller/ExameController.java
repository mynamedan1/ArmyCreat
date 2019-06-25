package army.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import army.db.pojo.AnswerRecord;
import army.db.pojo.Exam;
import army.db.pojo.HonorRecord;
import army.db.pojo.Question;
import army.db.pojo.User;
import army.service.AnswerRecordService;
import army.service.ExameService;
import army.service.HonorService;
import army.service.UserService;
import utils.ResponseCode;
import utils.ServerResponse;
import utils.TimeUntils;

@Controller
@RequestMapping("/exam")
public class ExameController {
	@Autowired
	private ExameService exameService;
	@Autowired
	private UserService userService;
	@Autowired
	private HonorService honorService;
	@Autowired
	private AnswerRecordService answerRecordService;

	// 添加题目之前先添加试卷，获取examId
	@RequestMapping("addExam.do")
	@ResponseBody
	public ServerResponse addExam(HttpServletRequest request, HttpServletResponse response, Exam exam, Model model) {
		ServerResponse serverResponse;
		exam.setUpdatetime(TimeUntils.dataToString(new Date()));
		if (exameService.insertExam(exam) != -1) {
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			hashMap.put("examId", exam.getId());
			serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(), hashMap, "试卷添加成功");
		} else {
			serverResponse = new ServerResponse(ResponseCode.ERROR.getCode(), "试卷添加失败");
		}
		return serverResponse;
	}

	// 添加题目 examId=1
	@RequestMapping("addQuestion.do")
	@ResponseBody
	public ServerResponse addQuestion(HttpServletRequest request, HttpServletResponse response, String questions,
			Model model) {
		List<Question> ts = (List<Question>) JSONArray.parseArray(questions, Question.class);
		return exameService.insertQuestion(ts);
	}

	// app端查询是否已经参加考核，如已参加考核，则弹出信息answerRecord.extra
	@RequestMapping("getAnswerRecord.do")
	@ResponseBody
	public ServerResponse getAnswerRecord(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = (User) request.getAttribute("currentUser");
		AnswerRecord answerRecord = answerRecordService.getAnswerRecord(user.getId());
		return new ServerResponse(ResponseCode.SUCCESS.getCode(), answerRecord, "");
	}

	// app端查询题目
	@RequestMapping("queryExam.do")
	@ResponseBody
	public ServerResponse queryExam(HttpServletRequest request, HttpServletResponse response) {
		ServerResponse serverResponse;
		List<Question> questions = exameService.getQuestions();
		if (questions.size() > 0) {
			return serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(), questions, "试卷添加成功");

		} else {
			return serverResponse = new ServerResponse(ResponseCode.ERROR.getCode(), "暂无考核内容");

		}
	}
	

	// 删除题目
	@RequestMapping("deleteQuestion.do")
	@ResponseBody
	public ServerResponse deleteQuestion(HttpServletRequest request, HttpServletResponse response,int id) {
		ServerResponse serverResponse;
		if (exameService.deleteQuestion(id)) {
			return serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(),"题目删除成功");

		} else {
			return serverResponse = new ServerResponse(ResponseCode.ERROR.getCode(), "题目删除失败");

		}
	}
	

	// app端提交答案，对比答案，给出评分
	@RequestMapping("checkAnswer.do")
	@ResponseBody
	public ServerResponse checkAnswer(HttpServletRequest request, HttpServletResponse response, String useranswers,
			int examId) {
		ServerResponse serverResponse;
		int point = 0;
		int rightAnswer = 0;
		String[] userAnw = useranswers.split("@");
		// String[] rightAnw = exameService.getAnswer().split(",");
		System.out.println(userAnw.length);
		List<Question> questions = exameService.getQuestions();
		for (int i = 0; i < userAnw.length; i++) {
			if (questions.get(i).getAnswer().equals(userAnw[i])) {
				point += questions.get(i).getPoint();
				rightAnswer += 1;
			}
		}
		String smg = "本次测试" + userAnw.length + "题,您共答对" + rightAnswer + "题,获得" + point + "点荣誉积分";

		// 更新用户积分
		User user = userService.getUserById(((User) request.getAttribute("currentUser")).getId());;
		user.setPointcount(user.getPointcount() + point);
		userService.updateUser(user);
		// 生成一条答题记录
		AnswerRecord answerRecord = new AnswerRecord();
		answerRecord.setAnswer(useranswers);
		answerRecord.setExamid(examId);
		answerRecord.setExtra(smg);
		answerRecord.setUserid(user.getId());
		answerRecordService.addAnswerRecord(answerRecord);
		// 生成一条积分记录
		HonorRecord honorRecord = new HonorRecord();
		honorRecord.setPoint(point);
		honorRecord.setTime(TimeUntils.dataToString(new Date()));
		honorRecord.setType(3);
		honorRecord.setTypeexpense("完成测试 获取" + point + "积分");
		honorRecord.setUserid(user.getId());
		honorService.addHonorRecord(honorRecord);
		return serverResponse = new ServerResponse(ResponseCode.SUCCESS.getCode(), null, smg);
	}

}
