package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.CertificateMapper;
import army.db.pojo.Certificate;

@Service
public class CertificateService {
	@Autowired
	private CertificateMapper certificateMapper;
	
	public boolean buyCertificate(Certificate certificate) {
		return certificateMapper.insert(certificate)==1?true:false;
	}
	
	public List<Certificate> getUserCertificate(int userId){
		return certificateMapper.getUserCertificate(userId);
	}

}
