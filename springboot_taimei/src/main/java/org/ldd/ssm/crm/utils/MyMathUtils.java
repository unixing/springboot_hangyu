package org.ldd.ssm.crm.utils;

import java.math.BigDecimal;

/**
 * 日报表算法工具
 */
public class MyMathUtils {
	/***
	 * 散团人数合计
	 */
	public static Integer getSumPerson(int... x){
		int y = 0;
		for (int i = 0; i < x.length; i++) {
			y+=x[i];
		}
		return y;
	}
	/**
	 * 散客总收入
	 * @param x 所有的舱位
	 * @return
	 */
	//=C4*(E3*E4+F3*F4+G3*G4+H3*H4+I3*I4+J3*J4+K3*K4+L3*L4+M3*M4+N3*N4+O3*O4+P3*P4+Q3*Q4+S3*S4+T3*T4+U3*U4+V3*V4)
	public static double getIndividualIncome(int... x){
		double[] h = {2,1,0.9,0.85,0.80,0.75,0.70,0.60,0.50,0.45,0.40,0.20,0.10,0,0,0,0,0};
		double y = 0;
		double j = x[0];
		for (int i = 1; i < x.length; i++) {
				y += x[i]*h[i-1];
		}
		y = j*y;
		return y;
	}
	/**
	 * 散客总人数
	 * @param combinedGroup 散团人数合计
	 * @param g G号舱位的人数
	 * @return
	 */
	public static Integer getTotalNumber(Integer combinedGroup, Integer g) {
		return combinedGroup - g;
	}
	/**
	 * 团队总收入
	 */
	public static Integer getTeamTotalRevenue(Integer combinedGroup, Integer g) {
		return combinedGroup + g;
	}
	/**
	 * 座公里收入
	 * @param IndividualIncome 散客总收入
	 * @param TeamTotalRevenue 团队总收入
	 * @param seat 座位
	 * @param mile 航距
	 * @return
	 */
	public static BigDecimal getKilometerIncome(Integer IndividualIncome,Integer TeamTotalRevenue,Integer seat,Integer  mile){
//		=(Y4+Z4)/收益汇总表!$D$3/收益汇总表!$K$2
		double d =(double)(IndividualIncome+TeamTotalRevenue)/seat/mile;
		return new BigDecimal(d);
	}
	/**
	 * 平均  折扣
	 * @param combinedGroup  散团人数合计
	 * @param IndividualIncome 散客总收入
	 * @param TeamTotalRevenue 团队总收入
	 * @param yPrice Y舱价格
	 * @return
	 */
	public static BigDecimal getAverageDiscount(Integer combinedGroup,Integer IndividualIncome,Integer TeamTotalRevenue,Integer yPrice){
		//=IF(W4>0,(Y4+Z4)/W4/C4,"0")
		if(combinedGroup>0){
			return new BigDecimal((double)(IndividualIncome+TeamTotalRevenue)/combinedGroup/yPrice);
		}
		return new BigDecimal(0) ;
	}
	
	/**
	 * 获取月份天数
	 * @param month
	 * @return
	 */
	public static int getMonthDays(int year,int month){
		int monthDays = 0;
		switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
        	monthDays = 31;
            break;
            //对于2月份需要判断是否为闰年
        case 2:
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            	monthDays = 29;
                break;
            } else {
            	monthDays = 28;
                break;
            }
        case 4:
        case 6:
        case 9:
        case 11:
        	monthDays = 30;
            break;
        }
		return monthDays;
	}
}
