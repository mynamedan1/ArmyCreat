package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.PayRecordMapper;
import army.db.pojo.PayRecord;

@Service
public class PayRecordService {
	@Autowired
	private PayRecordMapper payRecordMapper;
	
	public boolean addPayRecord(PayRecord payRecord) {
		return payRecordMapper.insert(payRecord)==1?true:false;
	}
	
	public List<PayRecord> getPayRecordList(int userId,int type){
		return payRecordMapper.getPayRecordList(userId, type);
	}
	public PayRecord getPaySuccess(String taskId,int userId, int type) {
		return payRecordMapper.getPaySuccess(taskId, userId, type);
	}

}
