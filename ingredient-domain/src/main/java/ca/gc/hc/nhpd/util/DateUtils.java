package ca.gc.hc.nhpd.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils {

    private static Log log = LogFactory.getLog(DateUtils.class);
    
    public static final String YMD_SLASHES_PATTERN = "yyyy/MM/dd";
	public static final String YMD_DASHES_PATTERN = "yyyy-MM-dd";
	public static final String DMY_DASHES_PATTERN = "dd-MMM-yyyy";  //text month
    public static final String GOC_STANDARD_PATTERN = YMD_DASHES_PATTERN;
	
	/*
	 * returns standard yyyy/mm/dd String format for the passed Date. 
	 */
	public static String formatDateSlashes(Date date) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(YMD_SLASHES_PATTERN);
		return dateFormatter.format(date);
	}
	
	public static String formatDateDashes(Date date) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(YMD_DASHES_PATTERN);
		return dateFormatter.format(date);
	}
	
	/*
	 * builds java.util.Date from yyyy-mm-dd String format. 
	 */
	public static Date formatDateDashes(String dateString, String pattern) {
		
		try {
			return new SimpleDateFormat(YMD_DASHES_PATTERN).parse(dateString);
		}
		catch (ParseException ex) {
			log.error("DateUtils.formatDate: Could not convert date string: "
					+ dateString);
			return null;
		}
		
	}
	
	/**
	 * Formats a date using the specified date pattern.  This class provides
	 * pattern Strings such as DMY_PATTERN that can be used to specify the 
	 *  
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date formatDateForPattern(String dateString, String pattern) {
		
		if (dateString == null || pattern == null) {
			return null;
		}
		
		try {
			return new SimpleDateFormat(pattern).parse(dateString);
		}
		catch (Exception ex) {
			log.error("DateUtils.formatDate: Could not convert date string: "
					+ dateString);
			return null;
		}
		
	}
			
}
