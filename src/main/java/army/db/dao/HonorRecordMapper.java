package army.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import army.db.pojo.HonorRecord;

public interface HonorRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HonorRecord record);

    int insertSelective(HonorRecord record);

    HonorRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HonorRecord record);

    int updateByPrimaryKey(HonorRecord record);
    
    List<HonorRecord> getHonorList(@Param("userId")Integer userId,@Param("type")Integer type);
    
    List<HonorRecord> checkTodayLock(@Param("userId")int userId,@Param("time")String time);
}