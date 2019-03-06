package utils;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
	public static String stringMD5(String key) {
		    String text = "armycreat91026202";
		    String encodeStr=DigestUtils.md5Hex(key + text);
	        return encodeStr;
	}
	
}
