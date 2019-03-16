package army.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.AnswerRecordMapper;
import army.db.pojo.AnswerRecord;

@Service
public class AnswerRecordService {
	@Autowired
	private AnswerRecordMapper answerRecordMapper;

	public boolean addAnswerRecord(AnswerRecord answerRecord) {
		return answerRecordMapper.insert(answerRecord) == 1 ? true : false;
	}
	
	public AnswerRecord getAnswerRecord(int userId) {
       	return answerRecordMapper.selectByUserId(userId);	
	}

}
