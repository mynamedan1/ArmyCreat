package army.service;

import utils.JWT;
import utils.MD5Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import army.db.pojo.User;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * 通过Redis存储和验证token的实现类
 * 
 * @author ScienJus
 * @date 2015/7/31.
 */
@Repository
public class RedisTokenManager{
    
   @Autowired(required = true)
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
	public void setRedis(RedisTemplate<String,String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
		// 泛型设置成Long后必须更改对应的序列化方案
        //redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
	}
    
	public void setToken(User user) {
		// 使用jwt生成token
		String token;
		try {
			token = JWT.sign(user, 60L * 1000L * 30L);
			redisTemplate.boundValueOps(MD5Utils.stringMD5(user.getId()+"")).set(token,60, TimeUnit.DAYS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 存储到redis并设置过期时间
	}

	public String getToken(String key) {
		return (String) redisTemplate.boundValueOps(key).get();
	}

	// public boolean checkToken(TokenModel model) {
	// if (model == null) {
	// return false;
	// }
	// String token = (String) redis.boundValueOps(model.getUserId()).get();
	// if (token == null || !token.equals(model.getToken())) {
	// return false;
	// }
	// // 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
	// //redis.boundValueOps(model.getUserId()).expire(72, TimeUnit.HOURS);
	// return true;
	// }

	public void deleteToken(String userId) {
		redisTemplate.delete(userId);
	}

}
