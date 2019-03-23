package army.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.Certificate;
import army.service.CertificateService;
import utils.ServerResponse;

@Controller
@RequestMapping("/certificate")
public class CertificateController {
	@Autowired
	private CertificateService certificateService;
	
	    // 购买证书
		@RequestMapping("buyCertificate.do")
		@ResponseBody
		public ServerResponse getAllUser(HttpServletRequest request, HttpServletResponse response,Certificate certificate,Model model) {
			if(certificateService.buyCertificate(certificate)) {
				return ServerResponse.createBySuccess("购买成功");
			}else {
				return ServerResponse.createBySuccess("购买失败，请稍后重试！");
			}

		}
		
	// 查询用户证书
	@RequestMapping("getUserCertificate.do")
	@ResponseBody
	public ServerResponse getUserCertificate(HttpServletRequest request, HttpServletResponse response,int userId) {
		return ServerResponse.createBySuccess("用户证书",certificateService.getUserCertificate(userId));
	}

}
