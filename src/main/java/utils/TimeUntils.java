package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUntils {
	
	public static String dataToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String dataToStringForDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}


}
