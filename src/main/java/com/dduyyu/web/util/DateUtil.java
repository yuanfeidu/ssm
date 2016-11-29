package com.dduyyu.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
//	public static void main(String[] args) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date d = null;
//		Date d1 = null;
//		Date d2 = null;
//		try {
//			d = format.parse("2016-1-30");
//			d1 = format.parse("2016-10-30");
//			d2 = format.parse("2017-11-1");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Calendar cDay1 = Calendar.getInstance();
//		cDay1.setTime(d1);
//		long time1 = cDay1.getTimeInMillis();
//		cDay1.setTime(d2);
//		long time2 = cDay1.getTimeInMillis();
//		System.out.println((time2-time1)/(1000*3600*24));
////	    cDay1.setTime(d);
////	    cDay1.add(Calendar.MONTH, 1);//计算下一个月的日期
////	    cDay1.add(Calendar.DAY_OF_YEAR, 1);
////        Date lastDate = cDay1.getTime();
////        System.out.println(format.format(lastDate));
////        System.out.println(cDay1.get(Calendar.MONTH)+1);
//		List<Date> list = new ArrayList<Date>();
//		
//		for (int i = 1; i < 13; i++) {
//			System.out.println(format.format(getNextPayDate(d,i)));
//			list.add(getNextPayDate(d,i));
//		}
//		System.out.println("--------------分割线-----------------");
//		list = adjustDate(list);
//		for (Date date : list) {
//			System.out.println(format.format(date));
//		}
//	}
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int lockTime = 0;
		Date investDate = null;
		Date adjustDate = null;
		try {
			investDate = format.parse("2016-11-10");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//计算最后一期的付息时间
		for (int i = 1; i < 13; i++) {
			adjustDate = getNextPayDate(investDate, i, adjustDate);
		}
		System.out.println(format.format(adjustDate));
		//计算投资日与最后一起付息时间相差多少天
		lockTime = (int) countDays(investDate, adjustDate)-1;
		System.out.println(lockTime);
				
	}
	public static List<Date> adjustDate(List<Date> list){
		SimpleDateFormat format = new SimpleDateFormat("MM");//截取月份
		Calendar cDay1 = Calendar.getInstance();
		for (int i = 0; i < list.size(); i++) {
			int j = i+1 >= list.size()?list.size()-1:i+1;
			Date nextDate = list.get(j);
			Date date = list.get(i);
			//下个月的时间(nextDate)与前个时间(date)进行比较 逻辑：如果月份差为0则 nextDate为下个月第一天 如果差为2 则nextDate为上个月最后一天，其他不做处理。
			int nextMonth = Integer.valueOf(format.format(nextDate));
			int month = Integer.valueOf(format.format(date));
			cDay1.setTime(nextDate);
			if(i != j && nextMonth - month == 0){
				cDay1.add(Calendar.MONTH, 1);
				cDay1.set(Calendar.DAY_OF_MONTH,1);
				list.set(j, cDay1.getTime());
			}
			if(i != j && nextMonth - month == 2){
				cDay1.add(Calendar.MONTH, -1);
				cDay1.set(Calendar.DAY_OF_MONTH, cDay1.getActualMaximum(Calendar.DAY_OF_MONTH));
				list.set(j, cDay1.getTime());
			}
		}
		return list;
	}
	
	/**
	 * 计算下个付息日      
	 * 规则：用户在2016年5月15日投资，则每月16日凌晨00:00进行返息。
	 * 若没有相等的返息日，则在月份的最后一天在加一天进行返息。如在2016年1月31日投资，则返息日是每月31日。遇特殊月份没有31号则在当月最后一天加一天返息。
	 * @param sDate1
	 * @return
	 */
	public static Date getNextPayDate(Date payDate,int i){
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(payDate);
        cDay1.add(Calendar.MONTH, i);//计算下一个月的日期
        cDay1.add(Calendar.DAY_OF_YEAR, 1);
        Date lastDate = cDay1.getTime();  
        return lastDate;  
	}
	
	/**
	 * 付息日的规律，按用户投资后的计息日开始，
	 * 从下个月按相应日期返息，计息日一般是T+1，
	 * 有个特例就是如果是30号投资的，不管当月有没有31号，计息日都是从下个月1号开始; 
	 * 另外还有一个就是如果是某月28号或29号投资的，遇到2月份付息统一为2月28日付息
	 * @param payDate
	 * @param i
	 * @param adjustDate
	 * @return
	 */
	public static Date getNextPayDate(Date payDate,int i,Date adjustDate){
		int j = i;
		SimpleDateFormat format = new SimpleDateFormat("MM");
		SimpleDateFormat formatMD = new SimpleDateFormat("MM-dd");
		SimpleDateFormat formatDD = new SimpleDateFormat("dd");
		String specialDate = "02-29";//特殊日期2月29
		String specialDate2 = "02-28";//特殊日期2月28
		String specialDay = "30";//30投资的
        Calendar cDay1 = Calendar.getInstance();
    	cDay1.setTime(payDate);//投资日
    	cDay1.add(Calendar.MONTH, j);//计算下i个月的日期
    	String dateTemp = formatMD.format(cDay1.getTime());
    	String dayTemp = formatDD.format(payDate);
    	//如果是特殊日期2月29和2月28，则设置为2月28 排除投资日是30号的
    	if(!specialDay.equals(dayTemp) && (specialDate.equals(dateTemp)||specialDate2.equals(dateTemp))){
    		cDay1.set(Calendar.MONTH, 1);//设置月份为2月
    		cDay1.set(Calendar.DAY_OF_MONTH, 28);//设置日期为28号
    	}else{
    		cDay1.add(Calendar.DAY_OF_YEAR, 1);//加一天
    	}
    	
    	if(adjustDate != null){
    		Date currentMonth = cDay1.getTime();//第i个月的日期
    		//当前时间(currentM)与前个月时间(lastM)进行比较 逻辑：如果月份差为0则 currentM为下个月第一天 如果差为2 则lastM为上个月最后一天，其他不做处理。
    		int currentM = Integer.valueOf(format.format(currentMonth));//截取月份
    		int lastM = Integer.valueOf(format.format(adjustDate));//截取月份
    		
    		if(currentM - lastM == 0){
    			cDay1.add(Calendar.MONTH, 1);
    			cDay1.set(Calendar.DAY_OF_MONTH,1);
    		}
    		if(currentM - lastM == 2){
    			cDay1.add(Calendar.MONTH, -1);
    			cDay1.set(Calendar.DAY_OF_MONTH, cDay1.getActualMaximum(Calendar.DAY_OF_MONTH));
    		}
    	}
        String md = formatMD.format(cDay1.getTime());
        //如果日期是2月29号则变为2月28号
        if(specialDate.equals(md)){
        	cDay1.add(Calendar.DAY_OF_YEAR, -1);//2月29号则向后退一天 变为2月28号
        }
        Date lastDate = cDay1.getTime();
        return lastDate;  
	}
	
	public static long countDays(Date investDay,Date lastPayInterestDay){
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(investDay);
		long time1 = cDay1.getTimeInMillis();
		cDay1.setTime(lastPayInterestDay);
		long time2 = cDay1.getTimeInMillis();
		return (time2-time1)/(1000*3600*24);
	}
}
