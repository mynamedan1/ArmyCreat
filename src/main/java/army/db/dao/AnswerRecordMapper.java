package army.db.dao;

import org.apache.ibatis.annotations.Param;

import army.db.pojo.AnswerRecord;

public interface AnswerRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnswerRecord record);

    int insertSelective(AnswerRecord record);

    AnswerRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnswerRecord record);

    int updateByPrimaryKey(AnswerRecord record);
    
    AnswerRecord selectByUserId(Integer userid);
    
}