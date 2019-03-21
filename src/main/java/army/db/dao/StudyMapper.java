package army.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import army.db.pojo.Study;

public interface StudyMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Study record);

	int insertSelective(Study record);

	Study selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Study record);

	int updateByPrimaryKeyWithBLOBs(Study record);

	int updateByPrimaryKey(Study record);

	int selectMaxId();

	List<Study> getStudyList(@Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize,@Param("type") Integer type);

	List<Study> getStudyListByCon(@Param("study") Study study);
}