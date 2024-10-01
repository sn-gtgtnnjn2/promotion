package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class GeneralFormatter {
	String dtLocalFormatString = "yyyy-MM-dd'T'HH:mm";
	String onSiteFormatString = "yyyy-MM-dd HH:mm";
	
	
	public static java.sql.Date convDateToSqlDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDateStr = sdf.format(date);
		java.sql.Date convertedDate = java.sql.Date.valueOf(formattedDateStr);
		return convertedDate;
	}
	
	public static Timestamp convDateToSqlTimestamp(Date date){
		Timestamp timestamp = null;
		if(!Objects.isNull(date)) {
			timestamp = new Timestamp(date.getTime());
		}
		return timestamp;
	}
	
	public static Date convDtLocalToDate(String datetimeLocal) throws ParseException {
		System.out.println(datetimeLocal);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return formatter.parse(datetimeLocal);
	}
	
    public static String toISO8601(Date date) {
    	String ret = null;
    	
    	if(!Objects.isNull(date)) { 		
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    		ret = sdf.format(date);
    	}
        return ret;
    }

    public static String toUsualString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(date);
    }
    
    public static String changeNullToEmpChar(String str) {
    	if(Objects.isNull(str)) {
    		return "";
    	}
    	return str;
    }
}
