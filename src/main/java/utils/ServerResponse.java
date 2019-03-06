package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

//保证序列化json时，如果是null的对象，key也会消失
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> {
	// 状态码
	private int status;
	// 数据
	private T data;
	// 描述信息
	private String msg;

//	public int getstatus() {
//		return status;
//	}
//
//	public void setstatus(int status) {
//		this.status = status;
//	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ServerResponse() {
		super();
	}

	public ServerResponse(int status) {
		super();
		this.status = status;
	}

	public ServerResponse(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public ServerResponse(int status, T data, String msg) {
		super();
		this.status = status;
		this.data = data;
		this.msg = msg;
	}

	// 使之不在序列化结果中
	@JsonIgnore
	public boolean checkIsSuccess() {
		return this.status== ResponseCode.SUCCESS.getCode();
	}

	// 泛型方法
	/**
	 * 成功返回数据
	 * 
	 * @param msg
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data, msg);
	}
	//成功不返回数据
	public static <T> ServerResponse<T> createBySuccess(String msg) {
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
	}


	/**
	 * 用于校验登录信息
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> ServerResponse<T> createByCheckSuccess() {
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
	}

	/**
	 * 返回错误信息
	 * 
	 * @param msg
	 * @param <T>
	 * @return
	 */
	public static <T> ServerResponse<T> createByError(String msg) {
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(), msg);
	}

	public static <T> ServerResponse<T> createByNeedLogin() {
		return new ServerResponse<T>(ResponseCode.NEED_LOG.getCode(), "请先登录！");
	}

}
