package army.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.ExamMapper;
import army.db.dao.QuestionMapper;
import army.db.pojo.Question;

@Service
public class ExameService {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private ExamMapper examMapper;

	public boolean insertQuestion(Question question) {
		return questionMapper.insert(question) == 1 ? true : false;
	}

}
