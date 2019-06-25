package army.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import army.db.pojo.HonorRecord;
import army.db.pojo.Study;
import army.db.pojo.User;
import army.service.HonorService;
import army.service.StudyService;
import army.service.UserService;
import utils.ServerResponse;
import utils.TimeUntils;

@Controller
@RequestMapping("/study")
public class StudyController {
	@Value("${tomact_dir}")
	private String tomact_dir;

	@Autowired
	private StudyService studyService;

	@Autowired
	private HonorService honorService;

	@Autowired
	private UserService userService;

	// 添加学习任务
	@RequestMapping("addStudy.do")
	@ResponseBody
	public ServerResponse addStudy(HttpServletRequest request, HttpServletResponse response, Study study,
			MultipartFile partFile, Model model) {
		if (null != partFile) {
			if (!partFile.isEmpty()) {
				String filePath = tomact_dir + "/study/" + (studyService.selectMaxId() + 1) + ".jpg";
				String setPath = "/study/" + (studyService.selectMaxId() + 1) + ".jpg";
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					partFile.transferTo(file);
					study.setImgurl(setPath);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					return ServerResponse.createByError("文件上传失败");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					return ServerResponse.createByError("文件上传失败");
				}
			}
		}
		study.setTime(TimeUntils.dataToStringForDate(new Date()));
		if (studyService.addStudy(study)) {
			return ServerResponse.createBySuccess("学习任务发布成功");
		}

		return ServerResponse.createByError("学习任务发布失败");
	}

	// 更改学习任务
	@RequestMapping("updateStudy.do")
	@ResponseBody
	public ServerResponse updateStudy(HttpServletRequest request, HttpServletResponse response, Study study,
			Model model) {
		if (studyService.updateStudy(study)) {
			return ServerResponse.createBySuccess("学习任务更新成功");
		} else {
			return ServerResponse.createByError("学习任务更新失败");
		}

	}

	// 删除学习任务

	@RequestMapping("deleteStudy.do")
	@ResponseBody
	public ServerResponse deleteStudy(HttpServletRequest request, HttpServletResponse response, int id, Model model) {
		if (studyService.deleteStudy(id)) {
			return ServerResponse.createBySuccess("删除成功");
		} else {
			return ServerResponse.createByError("删除失败");
		}

	}

	// 查询学习任务分页查询
	@RequestMapping("getStudyList.do")
	@ResponseBody
	public ServerResponse getStudyList(HttpServletRequest request, HttpServletResponse response, int type) {
		return ServerResponse.createBySuccess("学习任务列表", studyService.selectStudy(type));
	}

	// 查询学习任务
	@RequestMapping("getStudyListByCon.do")
	@ResponseBody
	public ServerResponse getStudyListByCon(HttpServletRequest request, HttpServletResponse response, Study study,
			Model model) {
		return ServerResponse.createBySuccess("学习任务列表", studyService.selectStudy(study));
	}

	// 查询id查询学习任务
	@RequestMapping("getStudyById.do")
	@ResponseBody
	public ServerResponse getStudyById(HttpServletRequest request, HttpServletResponse response, int id,
			Model model) {
		return ServerResponse.createBySuccess("学习任务列表", studyService.getStudyById(id));
	}

	// 完成学习任务，添加荣誉点
	@RequestMapping("completeStudy.do")
	@ResponseBody
	public ServerResponse completeStudy(HttpServletRequest request, HttpServletResponse response, int point) {
		User user = userService.getUserById(((User) request.getAttribute("currentUser")).getId());
		HonorRecord honorRecord = new HonorRecord();
		honorRecord.setPoint(point);
		honorRecord.setTime(TimeUntils.dataToString(new Date()));
		honorRecord.setType(2);
		honorRecord.setTypeexpense("完成学习 获取" + point + "积分");
		honorRecord.setUserid(user.getId());
		if (honorService.addHonorRecord(honorRecord)) {
			user.setPointcount(user.getPointcount() + point);
			userService.updateUser(user);
			return ServerResponse.createBySuccess("完成学习", honorRecord);
		} else {
			return ServerResponse.createByError("系统异常，请稍后重试");
		}
	}

}
