package army.db.dao;

import java.util.List;

import army.db.pojo.Certificate;

public interface CertificateMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Certificate record);

	int insertSelective(Certificate record);

	Certificate selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Certificate record);

	int updateByPrimaryKey(Certificate record);

	List<Certificate> getUserCertificate(Integer userId);
}