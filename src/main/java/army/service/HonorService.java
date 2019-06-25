package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.HonorRecordMapper;
import army.db.pojo.HonorRecord;

@Service
public class HonorService {
	@Autowired
	private HonorRecordMapper honorRecordMapper;

	public boolean addHonorRecord(HonorRecord honor) {
		return honorRecordMapper.insert(honor) == 1 ? true : false;
	}

	public List<HonorRecord> getHonorList(int userId,Integer type) {
		return honorRecordMapper.getHonorList(userId,type);
	}

	public boolean checkTodayLock(int userId, String time) {
		if (honorRecordMapper.checkTodayLock(userId, time) != null&& honorRecordMapper.checkTodayLock(userId, time).size() != 0)
			return false;
		else
			return true;
	}
}
