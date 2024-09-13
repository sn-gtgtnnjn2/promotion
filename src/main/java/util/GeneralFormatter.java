package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralFormatter {
	public static java.sql.Date convDateToSqlDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDateStr = sdf.format(date);
		java.sql.Date convertedDate = java.sql.Date.valueOf(formattedDateStr);
		return convertedDate;
	}
	
	public static Timestamp convDateToSqlTimestamp(Date date){		
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
}
