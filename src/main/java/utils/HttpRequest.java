package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.series.Map;

public class HttpRequest {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map map = (Map) connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : ((java.util.Map<String, List<String>>) map).keySet()) {
				System.out.println(key + "--->" + ((java.util.Map<String, List<String>>) map).get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// 发送 GET 请求
//		String s = HttpRequest.sendGet("http://localhost:8080/Home/RequestString", "key=123&v=456");
//		System.out.println(s);
         SendInfo sendInfo = new SendInfo();
         sendInfo.setAccountNo("1900000109");
         sendInfo.setSecretKey("W@X6E9tu9ijMSSO450LSE7RZI3V!PglVF5St66");
         sendInfo.setOutTradeNo("68237893399782489");
         sendInfo.setOrderAmount("0.01");
         sendInfo.setDevice("alih5");
         sendInfo.setVersion("2.1");
         long timeStampSec = System.currentTimeMillis()/1000;
         String timestamp = String.format("%010d", timeStampSec);
         sendInfo.setTimestamp(timestamp);
         sendInfo.setNotifyUrl("http://148.70.49.238:8080/army/#/");
         sendInfo.setRedirectUrl("http://148.70.49.238:8080/army/#/");
         String stringA="accountNo="+sendInfo.getAccountNo()+"&device="+sendInfo.getDevice()+"&notifyUrl="+sendInfo.getNotifyUrl()+"&orderAmount="+sendInfo.getOrderAmount()+
        		                       "&outTradeNo="+sendInfo.getOutTradeNo()+"&redirectUrl="+sendInfo.getRedirectUrl()+"&timestamp="+sendInfo.getTimestamp()+"&version=2.1&"+sendInfo.getSecretKey();
         System.out.println(stringA);
         sendInfo.setSign(DigestUtils.md5Hex(stringA).toUpperCase());
         System.out.println(DigestUtils.md5Hex(stringA).toUpperCase());
         String param = JSON.toJSONString(sendInfo);
         System.out.println(param);
		// 发送 POST 请求
		String sr = HttpRequest.sendPost("http://www.zl-pay.com/open/pay/", param);
		System.out.println(sr);
	}
}
