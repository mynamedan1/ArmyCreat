package army.service;

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
}
