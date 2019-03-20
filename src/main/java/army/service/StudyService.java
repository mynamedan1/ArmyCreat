package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.StudyMapper;
import army.db.pojo.Study;

@Service
public class StudyService {
	@Autowired
	StudyMapper studyMapper;

	public boolean addStudy(Study study) {
		return studyMapper.insert(study) == 1 ? true : false;
	}
	
	public boolean updateStudy(Study study) {
		return studyMapper.updateByPrimaryKeySelective(study)== 1 ? true : false;
	}
	
	public boolean deleteStudy(Integer id) {
		return studyMapper.deleteByPrimaryKey(id)== 1 ? true : false;
	}
	
	public int selectMaxId() {
		return studyMapper.selectMaxId();
	}
	
	public List<Study> selectStudy(int pageNumber,int pageSize,int type) {
		return studyMapper.getStudyList((pageNumber-1)*pageSize,pageSize,type);
	}

}
