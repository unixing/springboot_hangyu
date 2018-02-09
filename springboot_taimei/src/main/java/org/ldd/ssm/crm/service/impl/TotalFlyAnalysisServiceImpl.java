/**   
 * @Title: TotalFlyAnalysisServiceImpl.java 
 * @Package org.ldd.ssm.crm.service.impl 
 * @Description: TODO
 * @author taimei-gds   
 * @date 2016-4-7 下午3:14:55 
 * @version V1.0   
 */ 
package org.ldd.ssm.crm.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.TotalFly;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.mapper.TotalFlyAnalysisMapper;
import org.ldd.ssm.crm.query.TotalFlyAnalysisQuery;
import org.ldd.ssm.crm.service.TotalFlyAnalysisService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 共飞运营分析业务实现层
 * @Title:TotalFlyAnalysisServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-7 下午3:14:55
 */
@Service
public class TotalFlyAnalysisServiceImpl implements TotalFlyAnalysisService {
	@Autowired
	private TotalFlyAnalysisMapper totalFlyAnalysisMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	
	public List<List<OutPort>> getFlowCountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
		String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
		String pas_stp = totalFlyAnalysisQuery.getPas_stp();
		String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
		//如果没有选择经停则不包含经停数据
		if(TextUtil.isEmpty(pas_stp)){
			pas_stp = "";
		}
		totalFlyAnalysisQuery.setFlt_Rte_Cd1(dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
		totalFlyAnalysisQuery.setFlt_Rte_Cd2(arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
		List<List<OutPort>> outPortListList = new ArrayList<List<OutPort>>();
		String [] endtime = totalFlyAnalysisQuery.getEndDate().split("-");
		String [] starttime = totalFlyAnalysisQuery.getStartDate().split("-");
		int startYear = Integer.parseInt(starttime[0]);
		int endYear = Integer.parseInt(endtime[0]);
		int chazhi = endYear - startYear;
		//判断是否有经停
		if(TextUtil.isEmpty(totalFlyAnalysisQuery.getPas_stp())){
			outPortListList = getData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
		}else{
			List<List<OutPort>> outPortListList1 = new ArrayList<List<OutPort>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(pas_stp);
			outPortListList1 = getData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
			for (List<OutPort> list : outPortListList1) {
				outPortListList.add(list);
			}
			List<List<OutPort>> outPortListList2 = new ArrayList<List<OutPort>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			outPortListList2 = getData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
			for (List<OutPort> list : outPortListList2) {
				outPortListList.add(list);
			}
			List<List<OutPort>> outPortListList3 = new ArrayList<List<OutPort>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(pas_stp);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			outPortListList3 = getData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
			for (List<OutPort> list : outPortListList3) {
				outPortListList.add(list);
			}
		}
		totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
		totalFlyAnalysisQuery.setPas_stp(pas_stp);
		return outPortListList;
	}
	public List<List<OutPort>> getData(String [] endtime,String [] starttime,int startYear,int chazhi,TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		//显示三年的月趋势
		String dptname = outPortMapper.getNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd());
		String arrname = outPortMapper.getNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd());
		List<List<OutPort>> outPortListList = new ArrayList<List<OutPort>>();
		totalFlyAnalysisQuery.setStartDate(starttime[0]+"-01"+"-01");
		totalFlyAnalysisQuery.setEndDate(endtime[0]+"-12"+"-31");
		List<OutPort> outPortAll = totalFlyAnalysisMapper.getFlowCountData(totalFlyAnalysisQuery);
				for(int j=0;j<=chazhi;j++){
					List<OutPort> outPortList = new ArrayList<OutPort>();
					for(int i=1;i<13;i++){
						totalFlyAnalysisQuery.setYear(startYear+j);
						totalFlyAnalysisQuery.setMonth(i);
						OutPort outPort =  new OutPort();
						for (OutPort outPortnew : outPortAll) {
							if((""+i).equals(outPortnew.getMonth())&&(""+(startYear+j)).equals(outPortnew.getYear())){
								outPort = outPortnew;
							}
						}
						outPort.setHangduan(dptname+"-"+arrname);
						outPort.setMonth(i+"");
						outPort.setYear(startYear+j+"");
						outPortList.add(outPort);
					}
					outPortListList.add(outPortList);
					if(j>=2){
						break;
					}
				}
				return outPortListList;
		}
	
	
	
	public List<List<EvenPort>> getAllAmountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
		String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
		String pas_stp = totalFlyAnalysisQuery.getPas_stp();
		String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
		if(TextUtil.isEmpty(pas_stp)){
			pas_stp = "";
		}
		totalFlyAnalysisQuery.setFlt_Rte_Cd1(dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
		totalFlyAnalysisQuery.setFlt_Rte_Cd2(arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
		List<List<EvenPort>> evenPortListList = new ArrayList<List<EvenPort>>();
		
		String [] endtime = totalFlyAnalysisQuery.getEndDate().split("-");
		String [] starttime = totalFlyAnalysisQuery.getStartDate().split("-");
		int startYear = Integer.parseInt(starttime[0]);
		int endYear = Integer.parseInt(endtime[0]);
		int chazhi = endYear - startYear;
		//判断是否有经停
		if(TextUtil.isEmpty(totalFlyAnalysisQuery.getPas_stp())){
			evenPortListList = getAmountData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
		}else{
			List<List<EvenPort>> evenPortListList1 = new ArrayList<List<EvenPort>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(pas_stp);
			evenPortListList1 = getAmountData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
			for (List<EvenPort> list : evenPortListList1) {
				evenPortListList.add(list);
			}
			List<List<EvenPort>> evenPortListList2 = new ArrayList<List<EvenPort>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			evenPortListList2 = getAmountData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
			for (List<EvenPort> list : evenPortListList2) {
				evenPortListList.add(list);
			}
			List<List<EvenPort>> evenPortListList3 = new ArrayList<List<EvenPort>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(pas_stp);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			evenPortListList3 = getAmountData(endtime,starttime,startYear, chazhi, totalFlyAnalysisQuery);
			for (List<EvenPort> list : evenPortListList3) {
				evenPortListList.add(list);
			}
		}
		totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
		totalFlyAnalysisQuery.setPas_stp(pas_stp);
	    return evenPortListList;	
	}
		public List<List<EvenPort>> getAmountData(String [] endtime,String [] starttime,int startYear,int chazhi,TotalFlyAnalysisQuery totalFlyAnalysisQuery){
			//显示三年的均班流量趋势
			String dptname = outPortMapper.getNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd());
			String arrname = outPortMapper.getNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd());
			List<List<EvenPort>> evenPortListList = new ArrayList<List<EvenPort>>();
			List<EvenPort> evenPortAll = totalFlyAnalysisMapper.getAllAmountData(totalFlyAnalysisQuery);
				for(int j=0;j<=chazhi;j++){
					List<EvenPort> evenPortList = new ArrayList<EvenPort>();
					for(int i=1;i<13;i++){
						totalFlyAnalysisQuery.setYear(startYear+j);
						totalFlyAnalysisQuery.setMonth(i);
						EvenPort evenPort = new EvenPort();
						for (EvenPort evenPortnew : evenPortAll) {
							if((""+i).equals(evenPortnew.getMonth())&&(""+(startYear+j)).equals(evenPortnew.getYear())){
								evenPort = evenPortnew;
							}
						}
						evenPort.setMonth(i+"月");
						evenPort.setHangduan(dptname+"-"+arrname);
						evenPort.setYear(startYear+j+"");
						evenPortList.add(evenPort);
					}
					evenPortListList.add(evenPortList);
					if(j>=2){
						break;
					}
				}
			return evenPortListList;
		}
		public List<List<TotalFly>> getFlightNumData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
			String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
			String pas_stp = totalFlyAnalysisQuery.getPas_stp();
			String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
			String dpt_AirPt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd());
			String pas_stp_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp());
			String arrv_Airpt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd());
			if(TextUtil.isEmpty(pas_stp)){
				pas_stp = "";
			}
			totalFlyAnalysisQuery.setFlt_Rte_Cd1(dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setFlt_Rte_Cd2(arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListList = new ArrayList<List<TotalFly>>();
			//判断是否有经停
			if(TextUtil.isEmpty(totalFlyAnalysisQuery.getPas_stp())){
				List<TotalFly> totalFlyList = new ArrayList<TotalFly>(); 
				totalFlyList = totalFlyAnalysisMapper.getFlightNumData(totalFlyAnalysisQuery);
				if(totalFlyList.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList.add(totalFly);
				}else{
					for (TotalFly totalFly : totalFlyList) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					}
				}
				
				totalFlyListList.add(totalFlyList);
			}else{
				List<TotalFly> totalFlyList1 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(pas_stp);
				totalFlyList1 = totalFlyAnalysisMapper.getFlightNumData(totalFlyAnalysisQuery);
				if(totalFlyList1.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
					totalFlyList1.add(totalFly);
				}else{
					for (TotalFly totalFly : totalFlyList1) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
					}
					
				}
				List<TotalFly> totalFlyList2 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList2 = totalFlyAnalysisMapper.getFlightNumData(totalFlyAnalysisQuery);
				if(totalFlyList2.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList2.add(totalFly);
				}else{
					for (TotalFly totalFly : totalFlyList2) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					}
					
				}
				List<TotalFly> totalFlyList3 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(pas_stp);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList3 = totalFlyAnalysisMapper.getFlightNumData(totalFlyAnalysisQuery);
				if(totalFlyList3.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList3.add(totalFly);
				}else{
					for (TotalFly totalFly : totalFlyList3) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
					}
					
				}
				totalFlyListList.add(totalFlyList1);
				totalFlyListList.add(totalFlyList2);
				totalFlyListList.add(totalFlyList3);
			}
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setPas_stp(pas_stp);
			return totalFlyListList;
		}
		
		public List<List<TotalFly>> getRaskRankingData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
			String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
			String pas_stp = totalFlyAnalysisQuery.getPas_stp();
			String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
			String dpt_AirPt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd());
			String pas_stp_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp());
			String arrv_Airpt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd());
			
			if(TextUtil.isEmpty(pas_stp)){
				pas_stp = "";
			}
			totalFlyAnalysisQuery.setFlt_Rte_Cd1(dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setFlt_Rte_Cd2(arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListList = new ArrayList<List<TotalFly>>();
			//判断是否有经停
			if(TextUtil.isEmpty(totalFlyAnalysisQuery.getPas_stp())){
				List<TotalFly> totalFlyList = new ArrayList<TotalFly>(); 
				totalFlyList = totalFlyAnalysisMapper.getRaskRankingData(totalFlyAnalysisQuery);
				if(totalFlyList.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList.add(totalFly);
				}else{
					TotalFly totalFlyjb = totalFlyAnalysisMapper.getPJRaskRankingData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbys(totalFlyjb.getTal_Nbr_num());
						totalFly.setJbzglsr(totalFlyjb.getSet_Ktr_Ine_num());
					}
				}
				
				totalFlyListList.add(totalFlyList);
			}else{
				List<TotalFly> totalFlyList1 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(pas_stp);
				totalFlyList1 = totalFlyAnalysisMapper.getRaskRankingData(totalFlyAnalysisQuery);
				if(totalFlyList1.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
					totalFlyList1.add(totalFly);
				}else{
					TotalFly totalFlyjb = totalFlyAnalysisMapper.getPJRaskRankingData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList1) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
						totalFly.setJbys(totalFlyjb.getTal_Nbr_num());
						totalFly.setJbzglsr(totalFlyjb.getSet_Ktr_Ine_num());
					}
				}
				List<TotalFly> totalFlyList2 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList2 = totalFlyAnalysisMapper.getRaskRankingData(totalFlyAnalysisQuery);
				if(totalFlyList2.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList2.add(totalFly);
				}else{
					TotalFly totalFlyjb = totalFlyAnalysisMapper.getPJRaskRankingData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList2) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbys(totalFlyjb.getTal_Nbr_num());
						totalFly.setJbzglsr(totalFlyjb.getSet_Ktr_Ine_num());
					}
				}
				List<TotalFly> totalFlyList3 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(pas_stp);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList3 = totalFlyAnalysisMapper.getRaskRankingData(totalFlyAnalysisQuery);
				if(totalFlyList3.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList3.add(totalFly);
				}else{
					TotalFly totalFlyjb = totalFlyAnalysisMapper.getPJRaskRankingData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList3) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbys(totalFlyjb.getTal_Nbr_num());
						totalFly.setJbzglsr(totalFlyjb.getSet_Ktr_Ine_num());
					}
				}
				totalFlyListList.add(totalFlyList1);
				totalFlyListList.add(totalFlyList2);
				totalFlyListList.add(totalFlyList3);
			}
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setPas_stp(pas_stp);
			return totalFlyListList;
		}
		
		public List<List<TotalFly>> getPassengerRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
			String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
			String pas_stp = totalFlyAnalysisQuery.getPas_stp();
			String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
			String dpt_AirPt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd());
			String pas_stp_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp());
			String arrv_Airpt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd());
			if(TextUtil.isEmpty(pas_stp)){
				pas_stp = "";
			}
			totalFlyAnalysisQuery.setFlt_Rte_Cd1(dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setFlt_Rte_Cd2(arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListList = new ArrayList<List<TotalFly>>();
			//判断是否有经停
			if(TextUtil.isEmpty(totalFlyAnalysisQuery.getPas_stp())){
				List<TotalFly> totalFlyList = new ArrayList<TotalFly>(); 
				totalFlyList = totalFlyAnalysisMapper.getPassengerRankData(totalFlyAnalysisQuery);
				if(totalFlyList.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList.add(totalFly);
				}else{
					   TotalFly totalFlyPj = totalFlyAnalysisMapper.getPJPassengerRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbkl(totalFlyPj.getPgs_Per_Cls_num());
					}
				}
				
				totalFlyListList.add(totalFlyList);
			}else{
				List<TotalFly> totalFlyList1 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(pas_stp);
				totalFlyList1 = totalFlyAnalysisMapper.getPassengerRankData(totalFlyAnalysisQuery);
				if(totalFlyList1.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
					totalFlyList1.add(totalFly);
				}else{
					TotalFly totalFlyPj = totalFlyAnalysisMapper.getPJPassengerRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList1) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
						totalFly.setJbkl(totalFlyPj.getPgs_Per_Cls_num());
					}
				}
				List<TotalFly> totalFlyList2 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList2 = totalFlyAnalysisMapper.getPassengerRankData(totalFlyAnalysisQuery);
				if(totalFlyList2.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList2.add(totalFly);
				}else{
					TotalFly totalFlyPj = totalFlyAnalysisMapper.getPJPassengerRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList2) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbkl(totalFlyPj.getPgs_Per_Cls_num());
					}
				}
				List<TotalFly> totalFlyList3 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(pas_stp);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList3 = totalFlyAnalysisMapper.getPassengerRankData(totalFlyAnalysisQuery);
				if(totalFlyList3.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList3.add(totalFly);
				}else{
					TotalFly totalFlyPj = totalFlyAnalysisMapper.getPJPassengerRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList3) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbkl(totalFlyPj.getPgs_Per_Cls_num());
					}
				}
				totalFlyListList.add(totalFlyList1);
				totalFlyListList.add(totalFlyList2);
				totalFlyListList.add(totalFlyList3);
			}
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setPas_stp(pas_stp);
			return totalFlyListList;
		}
		public List<List<TotalFly>> getAllClassRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
			String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
			String pas_stp = totalFlyAnalysisQuery.getPas_stp();
			String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
			String dpt_AirPt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd());
			String pas_stp_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp());
			String arrv_Airpt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd());
			if(TextUtil.isEmpty(pas_stp)){
				pas_stp = "";
			}
			totalFlyAnalysisQuery.setFlt_Rte_Cd1(dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setFlt_Rte_Cd2(arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListList = new ArrayList<List<TotalFly>>();
			//判断是否有经停
			if(TextUtil.isEmpty(totalFlyAnalysisQuery.getPas_stp())){
				List<TotalFly> totalFlyList = new ArrayList<TotalFly>(); 
				totalFlyList = totalFlyAnalysisMapper.getAllClassRankData(totalFlyAnalysisQuery);
				if(totalFlyList.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList.add(totalFly);
				}else{
					TotalFly totalFlyPJ = totalFlyAnalysisMapper.getPJAllClassRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbzw(totalFlyPJ.getTal_Nbr_Set_num());
						totalFly.setJblk(totalFlyPJ.getPgs_Per_num());
						totalFly.setJbtd(totalFlyPJ.getGrp_Nbr_num());
					}
				}
				
				totalFlyListList.add(totalFlyList);
			}else{
				List<TotalFly> totalFlyList1 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(pas_stp);
				totalFlyList1 = totalFlyAnalysisMapper.getAllClassRankData(totalFlyAnalysisQuery);
				if(totalFlyList1.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
					totalFlyList1.add(totalFly);
				}else{
					TotalFly totalFlyPJ = totalFlyAnalysisMapper.getPJAllClassRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList1) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+pas_stp_Name);
						totalFly.setJbzw(totalFlyPJ.getTal_Nbr_Set_num());
						totalFly.setJblk(totalFlyPJ.getPgs_Per_num());
						totalFly.setJbtd(totalFlyPJ.getGrp_Nbr_num());
					}
				}
				List<TotalFly> totalFlyList2 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList2 = totalFlyAnalysisMapper.getAllClassRankData(totalFlyAnalysisQuery);
				if(totalFlyList2.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList2.add(totalFly);
				}else{
					TotalFly totalFlyPJ = totalFlyAnalysisMapper.getPJAllClassRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList2) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(dpt_AirPt_Cd_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbzw(totalFlyPJ.getTal_Nbr_Set_num());
						totalFly.setJblk(totalFlyPJ.getPgs_Per_num());
						totalFly.setJbtd(totalFlyPJ.getGrp_Nbr_num());
					}
				}
				List<TotalFly> totalFlyList3 = new ArrayList<TotalFly>();
				totalFlyAnalysisQuery.setDpt_AirPt_Cd(pas_stp);
				totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				totalFlyList3 = totalFlyAnalysisMapper.getAllClassRankData(totalFlyAnalysisQuery);
				if(totalFlyList3.size()==0){
					TotalFly totalFly = new TotalFly(); 
					totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
					totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
					totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
					totalFlyList3.add(totalFly);
				}else{
					TotalFly totalFlyPJ = totalFlyAnalysisMapper.getPJAllClassRankData(totalFlyAnalysisQuery);
					for (TotalFly totalFly : totalFlyList3) {
						totalFly.setStartDate(totalFlyAnalysisQuery.getStartDate());
						totalFly.setEndDate(totalFlyAnalysisQuery.getEndDate());
						totalFly.setHangduan(pas_stp_Name+"-"+arrv_Airpt_Cd_Name);
						totalFly.setJbzw(totalFlyPJ.getTal_Nbr_Set_num());
						totalFly.setJblk(totalFlyPJ.getPgs_Per_num());
						totalFly.setJbtd(totalFlyPJ.getGrp_Nbr_num());
					}
				}
				totalFlyListList.add(totalFlyList1);
				totalFlyListList.add(totalFlyList2);
				totalFlyListList.add(totalFlyList3);
			}
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setPas_stp(pas_stp);
			return totalFlyListList;
		}
		@Override
		public Map<String, Object> getTotalDataNew(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
			Map<String,Object> retMap = new LinkedMap();
			String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
			String pas_stp = totalFlyAnalysisQuery.getPas_stp();
			String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
			String dpt_AirPt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd());
			String pas_stp_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp());
			String arrv_Airpt_Cd_Name = outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd());
			String startDate = totalFlyAnalysisQuery.getStartDate();
			String endDate = totalFlyAnalysisQuery.getEndDate();
			//所有数据
			List<Z_Airdata> airdataListAll = totalFlyAnalysisMapper.getTotalFlyDataNewList(totalFlyAnalysisQuery);
			
			for(Z_Airdata airdata : airdataListAll){
				String flyNbr = airdata.getFlt_Nbr();
				if(flyNbr.length()==6){
					if(!TextUtil.isNum(flyNbr.substring(5, 6))){
						flyNbr = flyNbr.substring(0,5) + TextUtil.HbhCharater(flyNbr.substring(5, 6));
					}
				}
				airdata.setFlt_Nbr(flyNbr);
			}
			
			//exceptionFlag 1表示没有包含异常数据和异常航段，2表示只包含异常数据，3表示只包含异常航段，4表示既包含异常数据又包含异常航段
			String exceptionFlag = "1";
			List<Z_Airdata> airdataListNew = new ArrayList<Z_Airdata>();
			//是否包含异常数据
			Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(airdataListAll, totalFlyAnalysisQuery.getIsIncludeExceptionData());
			List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
			String dataFlage = (String) dataMap.get("flage");
			//是否包含异常航段
			
			String strrfalge = "false";
			if(TextUtil.isEmpty(pas_stp)){
				if("on".equals(totalFlyAnalysisQuery.getIsGoAndBack())){
					strrfalge = "single_true";
				}else{
					strrfalge = "single_false";
				}
			}else{
				if("on".equals(totalFlyAnalysisQuery.getIsGoAndBack())){
					strrfalge = "true";
				}else{
					strrfalge = "false";
				}
			}
			Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, totalFlyAnalysisQuery.getIsIncludeExceptionHuangduan(),strrfalge);
			airdataListNew = (List<Z_Airdata>) dataMap2.get("zairdataListB");
			String dataFlage2 = (String) dataMap2.get("flage");
			if("on".equals(totalFlyAnalysisQuery.getIsIncludeExceptionData())&&"on".equals(totalFlyAnalysisQuery.getIsIncludeExceptionHuangduan())){
				if("true".equals(dataFlage)&&"true".equals(dataFlage2)){
					exceptionFlag = "4";
				}else{
					if("true".equals(dataFlage)){
						exceptionFlag = "2";
					}else{
						if("true".equals(dataFlage2)){
							exceptionFlag = "3";
						}
					}
				}
			}else{
				if("on".equals(totalFlyAnalysisQuery.getIsIncludeExceptionData())){
					//是否包含异常数据
					if("true".equals(dataFlage)){
						exceptionFlag = "2";
					}
				}else{
					if("on".equals(totalFlyAnalysisQuery.getIsIncludeExceptionHuangduan())){
						//是否包含异常航段
						if("true".equals(dataFlage2)){
							exceptionFlag = "3";
						}
					}
				}
			}
			retMap.put("exceptionFlag", exceptionFlag);
			String flag = "0";
			if(airdataListNew!=null&&airdataListNew.size()>0){
				flag = "1";
			}
			//短1
			List<Z_Airdata> d1List = new ArrayList<Z_Airdata>();
			//短2
			List<Z_Airdata> d2List = new ArrayList<Z_Airdata>();
			//长段
			List<Z_Airdata> cdList = new ArrayList<Z_Airdata>();
			//航班量前十
			Map<String,Object> retHblMap = new LinkedMap();
			//坐收前十
			Map<String,Object> retzsMap = new LinkedMap();
			//客量前十
			Map<String,Object> retklMap = new LinkedMap();
			//客量占比
			Map<String,Object> retklzbMap = new LinkedMap();
			//均班客量前十
			Map<String,Object> retjbklMap = new LinkedMap();
			if("on".equals(totalFlyAnalysisQuery.getIsGoAndBack())){
				//短1航班号
				List<String> d1HbhList = new ArrayList<String>();
				//短2航班号
				List<String> d2HbhList = new ArrayList<String>();
				//长段航班号
				List<String> cdHbhList = new ArrayList<String>();
				if(!TextUtil.isEmpty(pas_stp)){
					for (Z_Airdata z_Airdata : airdataListNew) {
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						String fltNbr = z_Airdata.getFlt_Nbr();
						if((dpt.equals(dpt_AirPt_Cd)&&arr.equals(pas_stp))||(dpt.equals(pas_stp)&&arr.equals(dpt_AirPt_Cd))){
							//短1
							if(!d1HbhList.contains(fltNbr)){
								d1HbhList.add(fltNbr);
							}
							d1List.add(z_Airdata);
						}
						if((dpt.equals(dpt_AirPt_Cd)&&arr.equals(arrv_Airpt_Cd))||(dpt.equals(arrv_Airpt_Cd)&&arr.equals(dpt_AirPt_Cd))){
							//长段
							if(!cdHbhList.contains(fltNbr)){
								cdHbhList.add(fltNbr);
							}
							cdList.add(z_Airdata);
						}
						if((dpt.equals(pas_stp)&&arr.equals(arrv_Airpt_Cd))||(dpt.equals(arrv_Airpt_Cd)&&arr.equals(pas_stp))){
							//短2
							if(!d2HbhList.contains(fltNbr)){
								d2HbhList.add(fltNbr);
							}
							d2List.add(z_Airdata);
						}
					}
					//每段的航班号进行配对
					d1HbhList = TextUtil.getHbhLHAll(d1HbhList);
					d2HbhList = TextUtil.getHbhLHAll(d2HbhList);
					cdHbhList = TextUtil.getHbhLHAll(cdHbhList);
					
				
					retHblMap = getHblDatalh(d1HbhList,retHblMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retHblMap = getHblDatalh(cdHbhList,retHblMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retHblMap = getHblDatalh(d2HbhList,retHblMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					
					retzsMap = getzsDatalh(d1HbhList,retzsMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retzsMap = getzsDatalh(cdHbhList,retzsMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retzsMap = getzsDatalh(d2HbhList,retzsMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					
					retklMap = getklDatalh(d1HbhList,retklMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retklMap = getklDatalh(cdHbhList,retklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklMap = getklDatalh(d2HbhList,retklMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					
					retklzbMap = getklzbDatalh(d1HbhList,retklzbMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retklzbMap = getklzbDatalh(cdHbhList,retklzbMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklzbMap = getklzbDatalh(d2HbhList,retklzbMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					
					retjbklMap = getjbklDatalh(d1HbhList,retjbklMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retjbklMap = getjbklDatalh(cdHbhList,retjbklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retjbklMap = getjbklDatalh(d2HbhList,retjbklMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					
				}else{
					for (Z_Airdata z_Airdata : airdataListNew) {
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						String fltNbr = z_Airdata.getFlt_Nbr();
						if((dpt.equals(dpt_AirPt_Cd)&&arr.equals(arrv_Airpt_Cd))||(dpt.equals(arrv_Airpt_Cd)&&arr.equals(dpt_AirPt_Cd))){
							//长段
							cdList.add(z_Airdata);
							if(!cdHbhList.contains(fltNbr)){
								cdHbhList.add(fltNbr);
							}
						}
					}
					cdHbhList = TextUtil.getHbhLHAll(cdHbhList);
					retHblMap = getHblDatalh(cdHbhList,retHblMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retzsMap = getzsDatalh(cdHbhList,retzsMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklMap = getklDatalh(cdHbhList,retklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklzbMap = getklzbDatalh(cdHbhList,retklzbMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retjbklMap = getjbklDatalh(cdHbhList,retjbklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
				}
			}else{
				if(!TextUtil.isEmpty(pas_stp)){
					for (Z_Airdata z_Airdata : airdataListNew) {
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if(dpt.equals(dpt_AirPt_Cd)&&arr.equals(pas_stp)){
							d1List.add(z_Airdata);
						}
						if(dpt.equals(dpt_AirPt_Cd)&&arr.equals(arrv_Airpt_Cd)){
							cdList.add(z_Airdata);
						}
						if(dpt.equals(pas_stp)&&arr.equals(arrv_Airpt_Cd)){
							d2List.add(z_Airdata);
						}
					}
					retHblMap = getHblData(retHblMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retHblMap = getHblData(retHblMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retHblMap = getHblData(retHblMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					retzsMap = getzsData(retzsMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retzsMap = getzsData(retzsMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retzsMap = getzsData(retzsMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					retklMap = getklData(retklMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retklMap = getklData(retklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklMap = getklData(retklMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					retklzbMap = getklzbData(retklzbMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retklzbMap = getklzbData(retklzbMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklzbMap = getklzbData(retklzbMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					retjbklMap = getjbklData(retjbklMap,d1List,dpt_AirPt_Cd_Name,pas_stp_Name);
					retjbklMap = getjbklData(retjbklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retjbklMap = getjbklData(retjbklMap,d2List,pas_stp_Name,arrv_Airpt_Cd_Name);
					
				}else{
					for (Z_Airdata z_Airdata : airdataListNew) {
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if(dpt.equals(dpt_AirPt_Cd)&&arr.equals(arrv_Airpt_Cd)){
							cdList.add(z_Airdata);
						}
					}
					retHblMap = getHblData(retHblMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retzsMap = getzsData(retzsMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklMap = getklData(retklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retklzbMap = getklzbData(retklzbMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
					retjbklMap = getjbklData(retjbklMap,cdList,dpt_AirPt_Cd_Name,arrv_Airpt_Cd_Name);
				}
			}
			retMap.put("startDate", startDate);
			retMap.put("endDate", endDate);
			retMap.put("hbl", retHblMap);
			retMap.put("zs", retzsMap);
			retMap.put("kl", retklMap);
			retMap.put("klzb", retklzbMap);
			retMap.put("jbkl", retjbklMap);
			retMap.put("flag", flag);
			return retMap;
			
	}
	//均班客量前十
	public 	Map<String,Object> getjbklData(Map<String,Object> retjbklMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		List<String> hbhList = new ArrayList<String>();
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		DecimalFormat df = new DecimalFormat("#.##");
		for (Z_Airdata z_Airdata : list) {
			String fltNum = z_Airdata.getFlt_Nbr();
			if(!hbhList.contains(fltNum)){
				hbhList.add(fltNum);
			}
		}
		for (String string : hbhList) {
			double bc = 0;
			int mbzw = 0;
			int mblk = 0;
			int mbtd = 0;
			double tdzk = 0.00;
			double skzk = 0.00;
			double pjzk = 0.00;
			for (Z_Airdata z_Airdata : list) {
				String fltNum = z_Airdata.getFlt_Nbr();
				if(fltNum.equals(string)){
					bc++;
					mbzw = mbzw + z_Airdata.getTal_Nbr_Set();
					mblk = mblk + z_Airdata.getPgs_Per_Cls();
					mbtd = mbtd + z_Airdata.getGrp_Nbr();
					tdzk = tdzk + z_Airdata.getGrp_Dct().doubleValue();
					skzk = skzk + z_Airdata.getIdd_Dct().doubleValue();
					pjzk = pjzk + z_Airdata.getAvg_Dct().doubleValue();
				}
			}
			TotalFly tf = new TotalFly();
			tf.setFlt_Nbr(string);
			tf.setTal_Nbr_Set_num(Double.parseDouble(df.format(mbzw/bc)));
			tf.setPgs_Per_num(Double.parseDouble(df.format(mblk/bc)));
			tf.setGrp_Nbr_num(Double.parseDouble(df.format(mbtd/bc)));
			tf.setTdzk(Double.parseDouble(df.format(tdzk/bc)));
			tf.setSkzk(Double.parseDouble(df.format(skzk/bc)));
			tf.setPjzk(Double.parseDouble(df.format(pjzk/bc)));
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Pgs_Per_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retjbklMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name, totalFlyList);
		retjbklMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name+"-all", totalFlyTemp);
		return retjbklMap;
	}
	//均班客量前十来回
	public 	Map<String,Object> getjbklDatalh(List<String> fltNbrList,Map<String,Object> retjbklMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		DecimalFormat df = new DecimalFormat("#.##");
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		for (String string : fltNbrList) {
			int mbzw = 0;
			int mblk = 0;
			int mbtd = 0;
			double tdzk = 0.00;
			double skzk = 0.00;
			double pjzk = 0.00;
			int tdzkindex = 0;
			int skzkindex = 0;
			int pjzkindex = 0;
			List<String> days = new ArrayList<String>();
			for (Z_Airdata z_Airdata : list) {
				String fltNum = z_Airdata.getFlt_Nbr();
				String day  = sdf.format(z_Airdata.getLcl_Dpt_Day());
				if(string.contains(fltNum)){
					if(!days.contains(day)){
						days.add(day);
					}
					mbzw = mbzw + z_Airdata.getTal_Nbr_Set();
					mblk = mblk + z_Airdata.getPgs_Per_Cls();
					mbtd = mbtd + z_Airdata.getGrp_Nbr();
					if(z_Airdata.getGrp_Dct().doubleValue()>0){
						tdzkindex++;
						tdzk = tdzk + z_Airdata.getGrp_Dct().doubleValue();
					}
					if(z_Airdata.getIdd_Dct().doubleValue()>0){
						skzkindex++;
						skzk = skzk + z_Airdata.getIdd_Dct().doubleValue();
					}
					if(z_Airdata.getAvg_Dct().doubleValue()>0){
						pjzkindex++;
						pjzk = pjzk + z_Airdata.getAvg_Dct().doubleValue();
					}
				}
			}
			double bc = days.size();
			TotalFly tf = new TotalFly();
			String [] strarr = string.split("/");
			String  strtemp = "";
			if(strarr.length>1){
				strtemp = strarr[0]+"/"+strarr[1].subSequence(strarr[1].length()-2, strarr[1].length());
			}
			tf.setFlt_Nbr(strtemp);
			tf.setTal_Nbr_Set_num(Double.parseDouble(df.format(mbzw/bc)));
			tf.setPgs_Per_num(Double.parseDouble(df.format(mblk/bc)));
			tf.setGrp_Nbr_num(Double.parseDouble(df.format(mbtd/bc)));
			if(tdzkindex>0){
				tf.setTdzk(Double.parseDouble(df.format(tdzk/tdzkindex)));
			}else{
				tf.setTdzk(0.00);
			}
			if(skzkindex>0){
				tf.setSkzk(Double.parseDouble(df.format(skzk/skzkindex)));
			}else{
				tf.setSkzk(0.00);
			}
			if(pjzkindex>0){
				tf.setPjzk(Double.parseDouble(df.format(pjzk/pjzkindex)));
			}else{
				tf.setPjzk(0.00);
			}
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Pgs_Per_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retjbklMap.put(dpt_AirPt_Cd_Name+"="+arr_Name,totalFlyList );
		retjbklMap.put(dpt_AirPt_Cd_Name+"="+arr_Name+"-all", totalFlyTemp );
		return retjbklMap;
	}
	//客量占比
	public 	Map<String,Object> getklzbData(Map<String,Object> retklzbMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		Map<String,Integer> hblMap = new HashMap<String,Integer>();
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		for (Z_Airdata z_Airdata : list) {
			String fltNum = z_Airdata.getFlt_Nbr();
			int kl =  z_Airdata.getPgs_Per_Cls();
			if(hblMap.containsKey(fltNum)){
				Integer i =hblMap.get(fltNum);
				i = i + kl;
				hblMap.put(fltNum, i);
			}else{
				hblMap.put(fltNum,kl);
			}
		}
		for (String flyNbr : hblMap.keySet()) {
				TotalFly tf = new TotalFly();
				tf.setName(flyNbr);
				tf.setValue(hblMap.get(flyNbr));
				hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "value", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		TotalFly totalFlyqita = new TotalFly();
		double vale = 0.0;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<9){
				totalFlyList.add(totalFly);
			}else{
				vale = vale + totalFly.getValue();
			}
			i++;
		}
		if(i>=9){
			totalFlyqita.setValue(vale);
			totalFlyqita.setName("其他");
			totalFlyList.add(totalFlyqita);
		}
		retklzbMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name,totalFlyList );
		retklzbMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name+"-all", totalFlyTemp );
		return retklzbMap;
	}
	//客量占比来回
	public 	Map<String,Object> getklzbDatalh(List<String> fltNbrList,Map<String,Object> retklzbMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		for (String str : fltNbrList) {
			int zkl = 0;
			for (Z_Airdata z_Airdata : list) {
				String fltNum = z_Airdata.getFlt_Nbr();
				int kl =  z_Airdata.getPgs_Per_Cls();
				if(str.contains(fltNum)){
					zkl = zkl + kl;
				}
			}
			TotalFly tf = new TotalFly();
			String [] strarr = str.split("/");
			String  strtemp = "";
			if(strarr.length>1){
				strtemp = strarr[0]+"/"+strarr[1].subSequence(strarr[1].length()-2, strarr[1].length());
			}
			tf.setName(strtemp);
			tf.setValue(zkl);
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "value", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		TotalFly totalFlyqita = new TotalFly();
		double vale = 0.0;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<9){
				totalFlyList.add(totalFly);
			}else{
				vale = vale + totalFly.getValue();
			}
			i++;
		}
		if(i>=9){
			totalFlyqita.setValue(vale);
			totalFlyqita.setName("其他");
			totalFlyList.add(totalFlyqita);
		}
		retklzbMap.put(dpt_AirPt_Cd_Name+"="+arr_Name, totalFlyList);
		retklzbMap.put(dpt_AirPt_Cd_Name+"="+arr_Name+"-all", totalFlyTemp);
		return retklzbMap;
	}
	//客量前十
	public 	Map<String,Object> getklData(Map<String,Object> retklMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		Map<String,Integer> hblMap = new HashMap<String,Integer>();
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		for (Z_Airdata z_Airdata : list) {
			String fltNum = z_Airdata.getFlt_Nbr();
			int kl =  z_Airdata.getPgs_Per_Cls();
			if(hblMap.containsKey(fltNum)){
				Integer i =hblMap.get(fltNum);
				i = i + kl;
				hblMap.put(fltNum, i);
			}else{
				hblMap.put(fltNum,kl);
			}
		}
		for (String flyNbr : hblMap.keySet()) {
			TotalFly tf = new TotalFly();
			tf.setFlt_Nbr(flyNbr);
			tf.setPgs_Per_num(hblMap.get(flyNbr));
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Pgs_Per_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retklMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name,totalFlyList);
		retklMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name+"-all", totalFlyTemp);
		return retklMap;
	}
	//客量前十来回
	public 	Map<String,Object> getklDatalh(List<String> fltNbrList,Map<String,Object> retklMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		for (String str : fltNbrList) {
			int zkl = 0;
			for (Z_Airdata z_Airdata : list) {
				String fltNum = z_Airdata.getFlt_Nbr();
				int kl =  z_Airdata.getPgs_Per_Cls();
				if(str.contains(fltNum)){
					zkl = zkl + kl;
				}
			}
			TotalFly tf = new TotalFly();
			String [] strarr = str.split("/");
			String  strtemp = "";
			if(strarr.length>1){
				strtemp = strarr[0]+"/"+strarr[1].subSequence(strarr[1].length()-2, strarr[1].length());
			}
			tf.setFlt_Nbr(strtemp);
			tf.setPgs_Per_num(zkl);
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Pgs_Per_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retklMap.put(dpt_AirPt_Cd_Name+"="+arr_Name, totalFlyList);
		retklMap.put(dpt_AirPt_Cd_Name+"="+arr_Name+"-all", totalFlyTemp);
		return retklMap;
	}
	//坐收前十
	public 	Map<String,Object> getzsData(Map<String,Object> retzsMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		Map<String,TotalFly> zsMap = new HashMap<String,TotalFly>();
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		List<String> fltList = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("#.##");
		for (Z_Airdata z_Airdata : list) {
			String fltNum = z_Airdata.getFlt_Nbr();
			if(!fltList.contains(fltNum)){
				fltList.add(fltNum);
			}
		}
		for (String flt : fltList) {
			double bc = 0;
			double zsall = 0.0;
			double yysrall = 0.0;
			double tdysh = 0.0;
			int zsindex = 0;
			int ysindex = 0;
			TotalFly tf = new TotalFly();
			for (Z_Airdata z_Airdata : list) {
				String fltNum = z_Airdata.getFlt_Nbr();
				double yysr = z_Airdata.getTotalNumber();
				double zws = z_Airdata.getTal_Nbr_Set();
				double hj = z_Airdata.getSailingDistance(); 
				double tdys = z_Airdata.getGrp_Ine().doubleValue();
				if(flt.equals(fltNum)){
					yysrall = yysrall + yysr;
					tdysh = tdysh + tdys;
					ysindex ++;
					if(yysr>0&&zws>0&&hj>0){
						zsall = zsall + (yysr/zws/hj);
						zsindex++;
					}
					bc++;
				}
			}
			tf.setFlt_Nbr(flt);
			tf.setTal_Nbr_num(Double.parseDouble(df.format(yysrall/(double)ysindex)));
			tf.setSet_Ktr_Ine_num(Double.parseDouble(df.format(zsall/(double)zsindex)));
			tf.setJbtdsr(tdysh/ysindex);
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Set_Ktr_Ine_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
	
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retzsMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name, totalFlyList);
		retzsMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name+"-all", totalFlyTemp);
		return retzsMap;
	}
	//坐收前十来回
	public 	Map<String,Object> getzsDatalh(List<String> fltNbrList,Map<String,Object> retzsMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		DecimalFormat df = new DecimalFormat("#.##");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String str : fltNbrList) {
			double zs = 0.0;
			double ys = 0.0;
			double tdysh = 0.0;
			int zsindex = 0;
			int ysindex = 0;
			List<String> days = new ArrayList<String>();
			for (Z_Airdata z_Airdata : list) {
				String fltNum = z_Airdata.getFlt_Nbr();
				double yysr = z_Airdata.getTotalNumber();
				double zws = z_Airdata.getTal_Nbr_Set();
				double hj = z_Airdata.getSailingDistance();
				double tdys = z_Airdata.getGrp_Ine().doubleValue();
				String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
				if(str.contains(fltNum)){
					if(zws>0&&hj>0){
						zs = zs + (yysr/zws/hj);
						zsindex ++;
					}
					ys = ys + yysr;
					tdysh = tdysh + tdys;
					if(!days.contains(day)){
						ysindex ++;
						days.add(day);
					}
				}
			}
			TotalFly tf = new TotalFly();
			String [] strarr = str.split("/");
			String  strtemp = "";
			if(strarr.length>1){
				strtemp = strarr[0]+"/"+strarr[1].subSequence(strarr[1].length()-2, strarr[1].length());
			}
			tf.setFlt_Nbr(strtemp);
			tf.setTal_Nbr_num(Double.parseDouble(df.format(ys/ysindex)));
			tf.setSet_Ktr_Ine_num(Double.parseDouble(df.format(zs/zsindex)));
			
			tf.setJbtdsr(tdysh/ysindex);
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Set_Ktr_Ine_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retzsMap.put(dpt_AirPt_Cd_Name+"="+arr_Name, totalFlyList);
		retzsMap.put(dpt_AirPt_Cd_Name+"="+arr_Name+"-all", totalFlyTemp);
		return retzsMap;
	}
	//航班量前十
	public 	Map<String,Object> getHblData(Map<String,Object> retHblMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		Map<String,Integer> hblMap = new HashMap<String,Integer>();
		Map<String,Double> ybMap = new HashMap<String,Double>();
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		for (Z_Airdata z_Airdata : list) {
			String fltNum = z_Airdata.getFlt_Nbr();
			double ybfare = 0;
			if(hblMap.containsKey(fltNum)){
				Integer i =hblMap.get(fltNum);
				i++;
				hblMap.put(fltNum, i);
			}else{
				ybfare = z_Airdata.getyBFare();
				hblMap.put(fltNum,1);
				ybMap.put(fltNum,ybfare);
			}
		}
		for (String flyNbr : hblMap.keySet()) {
			TotalFly tf = new TotalFly();
			tf.setFlt_Nbr(flyNbr);
			tf.setFlt_Nbr_num(hblMap.get(flyNbr));
			tf.setyBFare(ybMap.get(flyNbr));
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Flt_Nbr_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retHblMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name, totalFlyList);
		retHblMap.put(dpt_AirPt_Cd_Name+"-"+arr_Name+"-all", totalFlyTemp);
		return retHblMap;
	}
	//航班量前十来回
	public 	Map<String,Object> getHblDatalh(List<String> fltNbrList,Map<String,Object> retHblMap,List<Z_Airdata> list,String dpt_AirPt_Cd_Name ,String arr_Name){
		List<TotalFly> hblList = new ArrayList<TotalFly>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String str : fltNbrList) {
			double ybfare = 0;
			List<String> days = new ArrayList<String>();
			for (Z_Airdata z_Airdata : list) {
				String fltNum = z_Airdata.getFlt_Nbr();
				String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
				if(str.contains(fltNum)){
					if(!days.contains(day)){
						days.add(day);
					}
					ybfare = z_Airdata.getyBFare();
				}
			}
			TotalFly tf = new TotalFly();
			String [] strarr = str.split("/");
			String  strtemp = "";
			if(strarr.length>1){
				strtemp = strarr[0]+"/"+strarr[1].subSequence(strarr[1].length()-2, strarr[1].length());
			}
			tf.setFlt_Nbr(strtemp);
			tf.setFlt_Nbr_num(days.size());
			tf.setyBFare(ybfare);
			hblList.add(tf);
		}
		List<TotalFly> totalFlyTemp =  TextUtil.sort(hblList, "Flt_Nbr_num", "DESC");
		List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
		int i = 0 ;
		for (TotalFly totalFly : totalFlyTemp) {
			if(i<10){
				totalFlyList.add(totalFly);
			}
			i++;
		}
		retHblMap.put(dpt_AirPt_Cd_Name+"="+arr_Name, totalFlyList);
		retHblMap.put(dpt_AirPt_Cd_Name+"="+arr_Name+"-all", totalFlyTemp);
		return retHblMap;
	}
	@Override
	public String getNewDate(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
		Date str = totalFlyAnalysisMapper.getNewDate(totalFlyAnalysisQuery);
		if(str==null){
			str = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(str);
	}
	
	
	
}
