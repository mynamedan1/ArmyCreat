package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import army.db.dao.ExamMapper;
import army.db.dao.QuestionMapper;
import army.db.pojo.Exam;
import army.db.pojo.Question;
import utils.ResponseCode;
import utils.ServerResponse;

@Service
public class ExameService {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private ExamMapper examMapper;

	// 添加题目
	@Transactional
	public ServerResponse insertQuestion(List<Question> question) {
		try {
			for (int i = 0; i < question.size(); i++) {
				questionMapper.insert(question.get(i));
			}
		} catch (Exception e) {
			return new ServerResponse(ResponseCode.ERROR.getCode(), "题目添加失败");
		}
		return new ServerResponse(ResponseCode.SUCCESS.getCode(), "题目添加成功");
	}

	// 增加试卷
	public int insertExam(Exam exam) {
		if (examMapper.insert(exam) == 1) {
			return exam.getId();
		} else {
			return -1;
		}
	}
	// 题目查询

	public List<Question> getQuestions() {
		return questionMapper.getQuestions();
	}

	// 获取试卷答案
	public String getAnswer() {
		return questionMapper.getAnswers();
	}

}
