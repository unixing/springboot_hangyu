package org.ldd.ssm.crm.utils;

import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.SalesReport;
import org.ldd.ssm.crm.mapper.FormulaUtilMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.FormulaUtilQuery;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统计算公式组装类
 * @Title:FormulaUtil 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-25 下午2:21:48
 */
@Component
public class FormulaUtil {
	@Autowired
	private FormulaUtilMapper formulaUtilMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	/**
	 * 根据日期，起始地、经停地等得到总飞行时间
	 * @Title: getTotalTime 
	 * @Description:  
	 * @param @param formulaUtilQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public  String getTotalTime(FormulaUtilQuery formulaUtilQuery) {
		double retStr=0.0;
		String pas_stpCode = null;
		if(formulaUtilQuery.getPas_stp()!=null){
			pas_stpCode = outPortMapper.getAirCodeByName(formulaUtilQuery.getPas_stp());
		}
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(formulaUtilQuery.getDpt_AirPt_Cd());
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(formulaUtilQuery.getArrv_Airpt_Cd());
		FormulaUtilQuery formulaUtilQuerynew  = new FormulaUtilQuery ();
		try {
			formulaUtilQuerynew = (FormulaUtilQuery) formulaUtilQuery.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		formulaUtilQuerynew.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		formulaUtilQuerynew.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		formulaUtilQuerynew.setPas_stp(pas_stpCode);
		List<SalesReport> salesReportList  = formulaUtilMapper.getTotalTime(formulaUtilQuerynew);
		for (SalesReport salesReport2 : salesReportList) {
			if(TextUtil.isEmpty(pas_stpCode)){
				Double dpttime = salesReport2.getLcl_Dpt_Tm();
				Double arrtime = salesReport2.getLcl_Arrv_Tm();
				Double temptime = (arrtime - dpttime);
				if(dpttime<0){
					temptime = temptime + 24*1000*3600;
				}
				retStr = retStr +  temptime/1000/3600;
			}else{
				if(salesReport2.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&salesReport2.getArrv_Airpt_Cd().equals(pas_stpCode)){
					Double dpttime = salesReport2.getLcl_Dpt_Tm();
					Double arrtime = salesReport2.getLcl_Arrv_Tm();
					Double temptime = (arrtime - dpttime);
					if(dpttime<0){
						temptime = temptime + 24*1000*3600;
					}
					retStr = retStr +  temptime/1000/3600;
				}
				if(salesReport2.getDpt_AirPt_Cd().equals(pas_stpCode)&&salesReport2.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					Double dpttime = salesReport2.getLcl_Dpt_Tm();
					Double arrtime = salesReport2.getLcl_Arrv_Tm();
					Double temptime = (arrtime - dpttime);
					if(dpttime<0){
						temptime = temptime + 24*1000*3600;
					}
					retStr = retStr +  temptime/1000/3600;
				}
				if(salesReport2.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&salesReport2.getArrv_Airpt_Cd().equals(pas_stpCode)){
					Double dpttime = salesReport2.getLcl_Dpt_Tm();
					Double arrtime = salesReport2.getLcl_Arrv_Tm();
					Double temptime = (arrtime - dpttime);
					if(dpttime<0){
						temptime = temptime + 24*1000*3600;
					}
					retStr = retStr +  temptime/1000/3600;
				}
				if(salesReport2.getDpt_AirPt_Cd().equals(pas_stpCode)&&salesReport2.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					Double dpttime = salesReport2.getLcl_Dpt_Tm();
					Double arrtime = salesReport2.getLcl_Arrv_Tm();
					Double temptime = (arrtime - dpttime);
					if(dpttime<0){
						temptime = temptime + 24*1000*3600;
					}
					retStr = retStr +  temptime/1000/3600;
				}
			}
			
		}
		java.text.DecimalFormat  df   =new   java.text.DecimalFormat("0.00"); 
		return df.format(retStr);
	}
	/**
	 * 根据日期，起始地、经停地等得到座公里收入
	 * @Title: getSiteIne 
	 * @Description:  
	 * @param @param formulaUtilQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public  String getSiteIne(FormulaUtilQuery formulaUtilQuery) {
		return "";
	}
	/**
	 * 根据日期、座位数、角色id得到小时成本
	 * @Title: getHourPrice 
	 * @Description:  
	 * @param @param formulaUtilQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public  String getHourPrice(FormulaUtilQuery formulaUtilQuery) {
		DailyParameters dailyParameters  = formulaUtilMapper.getHourPrice(formulaUtilQuery);
		String price = null;
		if(dailyParameters!=null){
			price = dailyParameters.getHour_price();
		}
		return price;
	}
	/**
	 * 根据上客人数、座位数得到平均客座率
	 * @Title: getAvgSitePerson 
	 * @Description:  
	 * @param @param formulaUtilQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public  String getAvgSitePerson(FormulaUtilQuery formulaUtilQuery) {
		return "";
	}
	/**
	 * 根据总飞行时间、小时成本、散团收入得到补贴
	 * @Title: getSubsidy 
	 * @Description:  
	 * @param @param formulaUtilQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public  String getSubsidy(FormulaUtilQuery formulaUtilQuery) {
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00"); 
		double toalTime = Double.parseDouble(formulaUtilQuery.getTotalTime()==null?"0":formulaUtilQuery.getTotalTime());
		double hourPrice = Double.parseDouble(formulaUtilQuery.getHourPrice()==null?"0":formulaUtilQuery.getHourPrice());
		double personTeemIne = Double.parseDouble(formulaUtilQuery.getPersonTeemIne()==null?"0":formulaUtilQuery.getPersonTeemIne());
		double butie =Double.parseDouble(df.format(toalTime * hourPrice - personTeemIne)) ;
		if(butie<0){
			butie = 0 ;
		}
		return butie+"";
	}
	/**
	 * @param formulaUtilMapper the formulaUtilMapper to set
	 */
	public void setFormulaUtilMapper(FormulaUtilMapper formulaUtilMapper) {
		this.formulaUtilMapper = formulaUtilMapper;
	}
	/**
	 * @param outPortMapper the outPortMapper to set
	 */
	public void setOutPortMapper(OutPortMapper outPortMapper) {
		this.outPortMapper = outPortMapper;
	}
	/**
	 * @return the formulaUtilMapper
	 */
	public FormulaUtilMapper getFormulaUtilMapper() {
		return formulaUtilMapper;
	}
	/**
	 * @return the outPortMapper
	 */
	public OutPortMapper getOutPortMapper() {
		return outPortMapper;
	}
	
}
