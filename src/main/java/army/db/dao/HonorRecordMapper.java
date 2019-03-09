package army.db.dao;

import army.db.pojo.HonorRecord;

public interface HonorRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HonorRecord record);

    int insertSelective(HonorRecord record);

    HonorRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HonorRecord record);

    int updateByPrimaryKey(HonorRecord record);
}