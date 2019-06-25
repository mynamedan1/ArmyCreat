package army.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
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

import army.db.pojo.HonorRecord;
import army.db.pojo.PayRecord;
import army.db.pojo.Task;
import army.db.pojo.User;
import army.db.pojo.UserTask;
import army.service.HonorService;
import army.service.PayRecordService;
import army.service.TaskService;
import army.service.UserService;
import army.service.UserTaskService;
import utils.HttpRequest;
import utils.SendInfo;
import utils.ServerResponse;
import utils.TimeUntils;


@Controller
@RequestMapping("/usertask")
public class UserTaskController {
	private static final Logger logger = LogManager.getLogger(UserTaskController.class);
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HonorService honorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PayRecordService payRecordService;


	@Value("${tomact_dir}")
	private String tomact_dir;

	// 用户领取任务 userTask状态设置为2
	@RequestMapping("claimTask.do")
	@ResponseBody
	public ServerResponse claimTask(HttpServletRequest request, HttpServletResponse response, UserTask userTask,
			Model model) {
		if (userTaskService.addUserTask(userTask)) {
			Task task = new Task();
			task.setId(userTask.getTaskid());
			task.setState(11);
			taskService.updateTask(task);
			return ServerResponse.createBySuccess("任务领取成功");
		} else {
			return ServerResponse.createByError("任务领取失败");
		}
	}

	@RequestMapping("pay.do")
	@ResponseBody
	public String changeUserTaskStatus(HttpServletRequest request, HttpServletResponse response, SendInfo sendInfo,
			Model model,int point,int changecount) {
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
		sendInfo.setNotifyUrl("http://119.3.111.196:8080//ArmyCreate/usertask/notify.do?soid="+((User) request.getAttribute("currentUser")).getId()+"&point="+point+"&changecount="+changecount);
		sendInfo.setRedirectUrl("http://119.3.111.196:8080//army/#/paySuccess?taskId="+sendInfo.getOutTradeNo().split("_")[1]);//+ "&device=" + sendInfo.getDevice()
		String stringA = "accountNo=" + sendInfo.getAccountNo()  + "&notifyUrl="
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
		System.out.println("-------"+sr);
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
	public String notify(HttpServletRequest request, HttpServletResponse response,int soid,int point,int changecount) throws IOException {
		logger.info("------------进入异步--------------------");
		logger.info("------------进入异步111"+soid+"--------------------");
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
        JSONObject jsonObject = JSON.parseObject(inputString.toString());
        //----------更新任务状态已完成--------------------------------------
        logger.info("----2222222222222--"+Integer.parseInt(jsonObject.getString("outTradeNo").split("_")[1]));
        UserTask userTask = new UserTask();
        userTask.setId(Integer.parseInt(jsonObject.getString("outTradeNo").split("_")[1]));
        userTask.setState(4);
        logger.info("----111111111--"+JSON.toJSONString(userTask));
        userTaskService.updateUserTask(userTask);
        //----------生成支付记录--------------------------------------------
//        UserTask relaUserTask = userTaskService.selectByTaskId(Integer.parseInt(jsonObject.getString("outTradeNo").split("_")[1]));
//        logger.info("----3333333333333--"+JSON.toJSONString(relaUserTask));
        
        logger.info("----444444--"+Float.parseFloat(jsonObject.getString("totalAmount")));
        //收款方
        PayRecord recive = new PayRecord();
        recive.setUserid(userTaskService.selectById(userTask.getId()).getUserid());
        recive.setMoney(Float.parseFloat(jsonObject.getString("totalAmount")));
        recive.setTime(TimeUntils.dataToStringForDate(new Date()));
        recive.setType(1);
        recive.setTaskid(jsonObject.getString("outTradeNo").split("_")[1]);
        payRecordService.addPayRecord(recive);
        logger.info("----45555554--");
        //付款方
//        Task releaseTask = taskService.selectByPrimaryKey(Integer.parseInt(jsonObject.getString("outTradeNo").split("_")[1]));
        PayRecord release = new PayRecord();
        release.setUserid(soid);
        release.setMoney(Float.parseFloat(jsonObject.getString("totalAmount")));
        release.setTime(TimeUntils.dataToStringForDate(new Date()));
        release.setType(-1);
        release.setTaskid(jsonObject.getString("outTradeNo").split("_")[1]);
        payRecordService.addPayRecord(release);
        logger.info("----45454364664--");
        User user = new User();
        user.setId(userTaskService.selectById(userTask.getId()).getUserid());
        if(user.getChangecount()!=null) {
        	  user.setChangecount(user.getChangecount()+changecount);
        }else {
          user.setChangecount(changecount);
        }
       
        
        HonorRecord honorRecord = new HonorRecord();
		honorRecord.setPoint(point);
		honorRecord.setTime(TimeUntils.dataToString(new Date()));
		honorRecord.setType(4);
		honorRecord.setTypeexpense("完成任务获取"+point+"积分");
		honorRecord.setUserid(userTaskService.selectById(userTask.getId()).getUserid());
		if(honorService.addHonorRecord(honorRecord)) {
			user.setPointcount(user.getPointcount() + point);
		}
		userService.updateUser(user);
	        //--------------------------------------------------------------------------
        }catch(Exception e){
         	e.printStackTrace();
        }
      return null;
	}

}
