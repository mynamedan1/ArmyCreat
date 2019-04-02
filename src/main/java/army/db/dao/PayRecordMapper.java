package army.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import army.db.pojo.PayRecord;

public interface PayRecordMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(PayRecord record);

	int insertSelective(PayRecord record);

	PayRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PayRecord record);

	int updateByPrimaryKey(PayRecord record);

	List<PayRecord> getPayRecordList(@Param("userId") Integer userId, @Param("type") Integer type);
}