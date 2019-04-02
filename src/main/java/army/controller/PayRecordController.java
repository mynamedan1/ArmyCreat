package army.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import army.db.pojo.PayRecord;
import army.service.PayRecordService;
import utils.ServerResponse;

@Controller
@RequestMapping("/payRecord")
public class PayRecordController {
	@Autowired
	private PayRecordService payRecordService;

	@RequestMapping("getUserPayRecord")
	@ResponseBody
	public ServerResponse getPayRecordList(int userId, int type) {
		return ServerResponse.createBySuccess("支付记录", payRecordService.getPayRecordList(userId, type));

	}

}
