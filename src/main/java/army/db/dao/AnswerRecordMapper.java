package army.db.dao;

import army.db.pojo.AnswerRecord;

public interface AnswerRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnswerRecord record);

    int insertSelective(AnswerRecord record);

    AnswerRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnswerRecord record);

    int updateByPrimaryKeyWithBLOBs(AnswerRecord record);

    int updateByPrimaryKey(AnswerRecord record);
}