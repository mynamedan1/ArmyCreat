package army.db.dao;

import army.db.pojo.Study;

public interface StudyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Study record);

    int insertSelective(Study record);

    Study selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Study record);

    int updateByPrimaryKeyWithBLOBs(Study record);

    int updateByPrimaryKey(Study record);
}