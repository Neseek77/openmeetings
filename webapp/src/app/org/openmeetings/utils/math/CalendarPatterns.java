package org.openmeetings.utils.math;

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sebastian Wagner
 * 27.08.2005 - 19:24:25
 *
 */
public class CalendarPatterns {
	
	private static final Logger log = LoggerFactory.getLogger(CalendarPatterns.class);
	
	public static SimpleDateFormat dateFormat__ddMMyyyyHHmmss = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	public static SimpleDateFormat dateFormat__ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy");
    
    public static String getDateByMiliSeconds(Date t){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date dateOld = new Date();
        long timeAdv = t.getTime();
        dateOld.setTime(timeAdv);
        String result = sdf.format(dateOld);
        return result;
    }
    
    public static String getDateWithTimeByMiliSeconds(Date t){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateOld = new Date();
        long timeAdv = t.getTime();
        dateOld.setTime(timeAdv);
        String result = sdf.format(dateOld);
        return result;
    }
    
    public static String getTimeForStreamId(Date t){
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Date dateOld = new Date();
        long timeAdv = t.getTime();
        dateOld.setTime(timeAdv);
        String result = sdf.format(dateOld);
        return result;
    }    
    
    public static Date parseDate(String dateString) {
    	try {
    		return dateFormat__ddMMyyyy.parse(dateString);
    	} catch (Exception e) {
    		log.error("parseDate",e);
    	}
    	return null;
    }
    
    public static Date parseDateWithHour(String dateString) {
    	try {
    		return dateFormat__ddMMyyyyHHmmss.parse(dateString);
    	} catch (Exception e) {
    		log.error("parseDate",e);
    	}
    	return null;
    }
    
}
