package com.qzsoft.tah.tools;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtils {

	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
	public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
	public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";
	public static final String DATE_FORMAT_YYMMDD = "yyMMdd";
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String getNowDate() {
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		return dateFromat.format(new Date());
	}

	public static String getNowDateTime() {
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
		return dateFromat.format(new Date());
	}

	public static String getFormatNowDateTime() {
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
		return dateFromat.format(new Date());
	}

	public static String getNowTime() {
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE_FORMAT_HH_MM_SS);
		return dateFromat.format(new Date());
	}

	public static long getSimpleId() {
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
		String format = dateFromat.format(new Date());
		return Long.parseLong(format);
	}

	/**
	 * 日期格式转换
	 * 
	 * @param dateStr
	 * @param formatStrFrom
	 * @param formatStrTo
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String formatDateStr(String dateStr, String formatStrFrom, String formatStrTo) {
		try {
			SimpleDateFormat sdfFrom = new SimpleDateFormat(formatStrFrom);
			Date date = sdfFrom.parse(dateStr);
			// 如果包含小时，则赋值当前时间
			if (formatStrTo != null && formatStrTo.contains("HH")) {
				Date curDate = new Date();
				date.setHours(curDate.getHours());
				date.setMinutes(curDate.getMinutes());
				date.setSeconds(curDate.getSeconds());
			}
			SimpleDateFormat sdfTo = new SimpleDateFormat(formatStrTo);
			return sdfTo.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDate(String dateStr) {
		try {
			SimpleDateFormat sdfFrom = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
			Date date = sdfFrom.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Date getDateYYYYMMDDHHMMSS(String dateStr) {
		try {
			SimpleDateFormat sdfFrom = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
			Date date = sdfFrom.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String getFormatTime(String timeStr) {
		try {
			if (null != timeStr && !"".equals(timeStr)) {
				SimpleDateFormat dateFromat = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
				Date date = dateFromat.parse(timeStr);
				return dateFromat.format(date);
			} else {
				return null;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		return getDateStrFormat(date, null);
	}

	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Calendar getCalendar(String dateStr) {
		Date date = getDate(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 
	 * @param cal
	 * @return
	 */
	public static String getDateStrByCal(Calendar cal) {
		Date date = cal.getTime();
		return getDateStr(date);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStrFormat(Date date, String formatStr) {
		try {
			if (date == null) {
				return null;
			}
			// 默认10位日期格式
			if (formatStr == null || "".equals(formatStr)) {
				formatStr = DATE_FORMAT_YYYY_MM_DD;
			}
			SimpleDateFormat sdfFrom = new SimpleDateFormat(formatStr);
			return sdfFrom.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算俩个字符串时间之间的差
	 * 
	 * @param smdate较小的日期
	 * @param bdate较大的日期
	 * @return
	 */
	public static int daysBetween(String startDt, String endDt) {
		try {
			Calendar cal = getCalendar(startDt);
			long time1 = cal.getTimeInMillis();
			cal = getCalendar(endDt);
			long time2 = cal.getTimeInMillis();
			long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(betweenDays));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 比较两个日期大小
	 * 
	 * @param sdate
	 * @param bdate
	 * @return
	 */
	public static int compareDate(String sdate, String bdate) {
		Date sDt = getDate(sdate);
		Date bDt = getDate(bdate);
		return sDt.compareTo(bDt);
	}

	/**
	 * 计算俩个Date类型的日期相差的十分秒
	 * 
	 * @param smDate较小的日期
	 * @param bgDate较大的日期
	 * @return
	 */
	public static String calcTime(Date bgDate, Date smDate) {
		long diff = (bgDate.getTime() - smDate.getTime()) / 1000; // 得到2个时间相差的秒数
		int day = (int) (diff / 86400);
		int hour = (int) ((diff % 86400) / 3600);
		int min = (int) ((diff % 3600) / 60);
		return day + "天" + hour + "小时" + min + "分";
	}

	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param parttern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
		return new SimpleDateFormat(parttern);
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param parttern
	 *            日期格式
	 * @return 日期
	 */
	public static Date StringToDate(String date, String parttern) {
		Date myDate = null;
		if (date != null) {
			try {
				myDate = getDateFormat(parttern).parse(date);
			} catch (Exception e) {
			}
		}
		return myDate;
	}

	/**
	 * 几分钟之前的时间
	 * @param before
	 * @return
	 */
	public static Date beforeMinutes(int before){
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MINUTE, -before);
		Date beforeTimeTime = beforeTime.getTime();
		return beforeTimeTime;
	}

}
