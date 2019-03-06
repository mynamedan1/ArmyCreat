package filter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import army.db.pojo.User;
import army.service.RedisTokenManager;
import utils.JWT;
import utils.MD5Utils;
import utils.ServerResponse;

public class TokenInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisTokenManager redisRokenManager;

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception arg3) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
			throws Exception {
	}

	// 拦截每个请求
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String key = request.getHeader("key");
		// token不存在
		if (null != key) {
			String token = redisRokenManager.getToken(key);
			User loginUser = JWT.unsign(token, User.class);
			// 解密token后的loginId与用户传来的loginId不一致，一般都是token过期
			if (null != key && null != loginUser) {
				if (key.equals(MD5Utils.stringMD5(loginUser.getId()+""))) {
					request.setAttribute("currentUser", loginUser);
					return true;
				} else {
					responseMessage(response, response.getWriter(), ServerResponse.createByNeedLogin());
					return false;
				}
			} else {
				responseMessage(response, response.getWriter(), ServerResponse.createByNeedLogin());
				return false;
			}
		} else {
			responseMessage(response, response.getWriter(), ServerResponse.createByNeedLogin());
			return false;
		}
	}

	// 请求不通过，返回错误信息给客户端
	private void responseMessage(HttpServletResponse response, PrintWriter out, ServerResponse responseData) {
		response.setContentType("application/json; charset=utf-8");
		String json = JSONObject.toJSONString(responseData);
		out.print(json);
		out.flush();
		out.close();
	}

}