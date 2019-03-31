package army.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import army.db.pojo.UserTask;
import army.service.UserTaskService;
import utils.HttpRequest;
import utils.SendInfo;
import utils.ServerResponse;


@Controller
@RequestMapping("/usertask")
public class UserTaskController {
	private static final Logger logger = LogManager.getLogger(UserTaskController.class);
	@Autowired
	private UserTaskService userTaskService;

	@Value("${tomact_dir}")
	private String tomact_dir;

	// 用户领取任务 userTask状态设置为2
	@RequestMapping("claimTask.do")
	@ResponseBody
	public ServerResponse claimTask(HttpServletRequest request, HttpServletResponse response, UserTask userTask,
			Model model) {
		if (userTaskService.addUserTask(userTask)) {
			return ServerResponse.createBySuccess("任务领取成功");
		} else {
			return ServerResponse.createByError("任务领取失败");
		}
	}

	@RequestMapping("pay.do")
	@ResponseBody
	public String changeUserTaskStatus(HttpServletRequest request, HttpServletResponse response, SendInfo sendInfo,
			Model model) {
		sendInfo.setAccountNo("1900000109");
		sendInfo.setSecretKey("W@X6E9tu9ijMSSO450LSE7RZI3V!PglVF5St66");
		// sendInfo.setOutTradeNo("68237893399782489");
		// sendInfo.setOrderAmount("0.01");
		// sendInfo.setDevice("alih5");
		sendInfo.setVersion("2.1");
		sendInfo.setPACKAGE("{mobile:15026923456,realName:dandan}");
		long timeStampSec = System.currentTimeMillis() / 1000;
		String timestamp = String.format("%010d", timeStampSec);
		sendInfo.setTimestamp(timestamp);
		sendInfo.setNotifyUrl("http://148.70.49.238:8080/ArmyCreate/usertask/notify.do");
		sendInfo.setRedirectUrl("http://148.70.49.238:8080/army/#/paySuccess");
		String stringA = "accountNo=" + sendInfo.getAccountNo() + "&device=" + sendInfo.getDevice() + "&notifyUrl="
				+ sendInfo.getNotifyUrl() + "&orderAmount=" + sendInfo.getOrderAmount() + "&outTradeNo="
				+ sendInfo.getOutTradeNo() + "&redirectUrl=" + sendInfo.getRedirectUrl() + "&timestamp="
				+ sendInfo.getTimestamp() + "&version=2.1&" + sendInfo.getSecretKey();
		System.out.println(stringA);
		sendInfo.setSign(DigestUtils.md5Hex(stringA).toUpperCase());
		System.out.println(DigestUtils.md5Hex(stringA).toUpperCase());
		String param = JSON.toJSONString(sendInfo).replace("pACKAGE", "package");
		System.out.println(param);
		// 发送 POST 请求
		String sr = HttpRequest.sendPost("http://www.zl-pay.com/open/pay/", param);
		System.out.println(sr);
		return sr;
	}

	// 用户任务状态更改 2已认领，3代支付，4已完成，
	@RequestMapping("changeUserTaskStatus.do")
	@ResponseBody
	public ServerResponse changeUserTaskStatus(HttpServletRequest request, HttpServletResponse response,
			UserTask userTask, Model model) {
		// if (userTask.getState() == 3) {
		// if (null != partFile) {
		// if (partFile.isEmpty()) {
		// ServerResponse.createByError("请上传支付二维码");
		// } else {
		// String filePath = tomact_dir + "/army/pay/" + userTask.getId() + ".jpg";
		// String setPath = "/army/pay/" + userTask.getId() + ".jpg";
		// File file = new File(filePath);
		// if (!file.exists()) {
		// file.mkdirs();
		// }
		// try {
		// partFile.transferTo(file);
		// userTask.setPayimageurl(setPath);
		// } catch (IllegalStateException e) {
		// // TODO Auto-generated catch block
		// // e.printStackTrace();
		// return ServerResponse.createByError("二维码上传失败");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// // e.printStackTrace();
		// return ServerResponse.createByError("二维码上传失败");
		// }
		// }
		// } else {
		// ServerResponse.createByError("请上传支付二维码");
		// }
		// }
		if (userTaskService.updateUserTask(userTask)) {
			return ServerResponse.createBySuccess("任务状态更新成功");
		}

		return ServerResponse.createByError("任务状态修改失败");
	}
    
	//支付异步回调
	@RequestMapping("notify.do")
	@ResponseBody
	public String notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("------------进入异步--------------------");
		BufferedReader reader = request.getReader();
        String line = "";
        StringBuffer inputString = new StringBuffer();
        try{
        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        request.getReader().close();
        logger.info("----接收到的报文---"+inputString.toString());
		//告诉服务器，我收到信息了，不要在调用回调action了
        response.getWriter().write("SUCCESS");
        }catch(Exception e){
         	e.printStackTrace();
        }
      return  null;
	}

}
