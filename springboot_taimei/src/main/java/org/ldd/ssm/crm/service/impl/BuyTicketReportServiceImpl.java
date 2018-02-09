package org.ldd.ssm.crm.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.map.LinkedMap;
import org.ldd.ssm.crm.domain.BuyTicketReport;
import org.ldd.ssm.crm.domain.GustStrate;
import org.ldd.ssm.crm.mapper.BuyTicketReportMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.BuyTicketReportQuery;
import org.ldd.ssm.crm.service.BuyTicketReportService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 上客进度表接口实现类
 * @Title:BuyTicketReportServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-19 下午12:00:08
 */
@Service
public class BuyTicketReportServiceImpl implements BuyTicketReportService {
	@Autowired
	private BuyTicketReportMapper buyTicketReportMapper;
	@Autowired
	private OutPortMapper outPortMapper;

	public Map<String, List<BuyTicketReport>> getBuyTicketReportData(BuyTicketReportQuery buyTicketReportQuery) {
		String flyNbr = buyTicketReportQuery.getFlt_nbr_Count();
		String flyNbrBNew = "";
		if(!TextUtil.isEmpty(flyNbr)&&flyNbr.length()>5){
			String numold2 = flyNbr.substring(0, 4);
			String lastTwoold = flyNbr.substring(4, 5);
			String numlast = flyNbr.substring(5, 6);
			if(Integer.parseInt(numlast)%2==0){
				if(Integer.parseInt(numlast)==0){
					flyNbrBNew =numold2 + (Integer.parseInt(lastTwoold)-1) + "9";
				}else{
					flyNbrBNew =numold2 + lastTwoold + (Integer.parseInt(numlast)-1);
				}
			}else{
				if(Integer.parseInt(numlast)==9){
					flyNbrBNew = numold2 + (Integer.parseInt(lastTwoold)+1) + "0";
				}else{
					flyNbrBNew = numold2 + lastTwoold + + (Integer.parseInt(numlast)+1);
				}
			}
		}
		Map<String, List<BuyTicketReport>> buyTicketReportListMap = new TreeMap<String, List<BuyTicketReport>>();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		List<GustStrate> gustStrateList =  buyTicketReportMapper.getHuangduan(buyTicketReportQuery);
		
		List<GustStrate> gustGutDateListAll =  buyTicketReportMapper.getGutDteData(buyTicketReportQuery);
		List<GustStrate> gustGustStrateListAll =  buyTicketReportMapper.getGustStrateDataNew(buyTicketReportQuery);
		if(gustStrateList!=null){
			for (GustStrate gustStrate : gustStrateList) {
				List<BuyTicketReport> BuyTicketReportList = new ArrayList<BuyTicketReport>();
				buyTicketReportQuery.setDpt_AirPt_Cd(gustStrate.getDpt_AirPt_Cd());
				buyTicketReportQuery.setArrv_Airpt_Cd(gustStrate.getArrv_Airpt_Cd());
				List<GustStrate> gustGutDateList =  gustGutDateListAll;
				List<GustStrate> gustGustStrateList =  new ArrayList<GustStrate>();
				for (GustStrate gustStrate2 : gustGustStrateListAll) {
					if(gustStrate.getDpt_AirPt_Cd().equals(gustStrate2.getDpt_AirPt_Cd())&&gustStrate.getArrv_Airpt_Cd().equals(gustStrate2.getArrv_Airpt_Cd())){
						gustGustStrateList.add(gustStrate2);
					}
				}
				for (GustStrate gustStrate2 : gustGutDateList) {
					BuyTicketReport BuyTicketReport = new BuyTicketReport();
					String datee = gustStrate2.getOn_Gut_Dte();
					String [] str = datee.split("-");
					List<GustStrate> gustGustStrateList2 = new ArrayList<GustStrate>();
					for (GustStrate gustStrate3 : gustGustStrateList) {
						String datee2 = gustStrate3.getOn_Gut_Dte();
						String [] str2 = datee2.split("-");
						if((Integer.parseInt(str[0])==Integer.parseInt(str2[0]))&&(Integer.parseInt(str[1])==Integer.parseInt(str2[1]))&&(Integer.parseInt(str[2])==Integer.parseInt(str2[2]))){
							gustGustStrateList2.add(gustStrate3);
						}
					}
					try {
						Date gutDate = df.parse(datee);
						for (GustStrate gustStrate3 : gustGustStrateList2) {
							Date dtaDate =  df.parse(gustStrate3.getDta_Sce());
							int chazhi = (int) ((gutDate.getTime() - dtaDate.getTime())/(24*60*60*1000));
							if(chazhi>30){
								chazhi =31;
							}
							switch(chazhi){
								case -1: BuyTicketReport.setProgressf1(gustStrate3.getGut_Rae()+"%"); break;
								case 0: BuyTicketReport.setProgress0(gustStrate3.getGut_Rae()+"%"); break;
								case 1: BuyTicketReport.setProgress1(gustStrate3.getGut_Rae()+"%");break;
								case 2: BuyTicketReport.setProgress2(gustStrate3.getGut_Rae()+"%");break;
								case 3: BuyTicketReport.setProgress3(gustStrate3.getGut_Rae()+"%");break;
								case 4: BuyTicketReport.setProgress4(gustStrate3.getGut_Rae()+"%");break;
								case 5: BuyTicketReport.setProgress5(gustStrate3.getGut_Rae()+"%");break;
								case 6: BuyTicketReport.setProgress6(gustStrate3.getGut_Rae()+"%");break;
								case 7: BuyTicketReport.setProgress7(gustStrate3.getGut_Rae()+"%");break;
								case 8: BuyTicketReport.setProgress8(gustStrate3.getGut_Rae()+"%");break;
								case 9: BuyTicketReport.setProgress9(gustStrate3.getGut_Rae()+"%");break;
								case 10: BuyTicketReport.setProgress10(gustStrate3.getGut_Rae()+"%");break;
								case 11: BuyTicketReport.setProgress11(gustStrate3.getGut_Rae()+"%");break;
								case 12: BuyTicketReport.setProgress12(gustStrate3.getGut_Rae()+"%");break;
								case 13: BuyTicketReport.setProgress13(gustStrate3.getGut_Rae()+"%");break;
								case 14: BuyTicketReport.setProgress14(gustStrate3.getGut_Rae()+"%");break;
								case 15: BuyTicketReport.setProgress15(gustStrate3.getGut_Rae()+"%");break;
								case 16: BuyTicketReport.setProgress16(gustStrate3.getGut_Rae()+"%");break;
								case 17: BuyTicketReport.setProgress17(gustStrate3.getGut_Rae()+"%");break;
								case 18: BuyTicketReport.setProgress18(gustStrate3.getGut_Rae()+"%");break;
								case 19: BuyTicketReport.setProgress19(gustStrate3.getGut_Rae()+"%");break;
								case 20: BuyTicketReport.setProgress20(gustStrate3.getGut_Rae()+"%");break;
								case 21: BuyTicketReport.setProgress21(gustStrate3.getGut_Rae()+"%");break;
								case 22: BuyTicketReport.setProgress22(gustStrate3.getGut_Rae()+"%");break;
								case 23: BuyTicketReport.setProgress23(gustStrate3.getGut_Rae()+"%");break;
								case 24: BuyTicketReport.setProgress24(gustStrate3.getGut_Rae()+"%");break;
								case 25: BuyTicketReport.setProgress25(gustStrate3.getGut_Rae()+"%");break;
								case 26: BuyTicketReport.setProgress26(gustStrate3.getGut_Rae()+"%");break;
								case 27: BuyTicketReport.setProgress27(gustStrate3.getGut_Rae()+"%");break;
								case 28: BuyTicketReport.setProgress28(gustStrate3.getGut_Rae()+"%");break;
								case 29: BuyTicketReport.setProgress29(gustStrate3.getGut_Rae()+"%");break;
								case 30: BuyTicketReport.setProgress30(gustStrate3.getGut_Rae()+"%");break;
								case 31: BuyTicketReport.setProgressd30(gustStrate3.getGut_Rae()+"%");break;
							}
						}
						BuyTicketReport.setDow(TextUtil.getWeekOfDate(df.parse(datee)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					BuyTicketReport.setFltNumDate(datee);
					BuyTicketReport.setProgress02(Double.parseDouble(BuyTicketReport.getProgress0()==null?"0":BuyTicketReport.getProgress0().replace("%", ""))-Double.parseDouble(BuyTicketReport.getProgress2()==null?"0":BuyTicketReport.getProgress2().replace("%", ""))+"%");
					BuyTicketReport.setProgress06(Double.parseDouble(BuyTicketReport.getProgress0()==null?"0":BuyTicketReport.getProgress0().replace("%", ""))-Double.parseDouble(BuyTicketReport.getProgress6()==null?"0":BuyTicketReport.getProgress6().replace("%", ""))+"%");
					BuyTicketReport.setProgress011(Double.parseDouble(BuyTicketReport.getProgress0()==null?"0":BuyTicketReport.getProgress0().replace("%", ""))-Double.parseDouble(BuyTicketReport.getProgress11()==null?"0":BuyTicketReport.getProgress11().replace("%", ""))+"%");
					BuyTicketReportList.add(BuyTicketReport);
				}
				buyTicketReportListMap.put(buyTicketReportQuery.getFlt_nbr_Count()+outPortMapper.getNameByCode(gustStrate.getDpt_AirPt_Cd())+"-"+outPortMapper.getNameByCode(gustStrate.getArrv_Airpt_Cd()), BuyTicketReportList);
			}
		}
		buyTicketReportQuery.setFlt_nbr_Count(flyNbrBNew);
		List<GustStrate> gustStrateList2 =  buyTicketReportMapper.getHuangduan(buyTicketReportQuery);
		List<GustStrate> gustGutDateListAll2 =  buyTicketReportMapper.getGutDteData(buyTicketReportQuery);
		List<GustStrate> gustGustStrateListAll2 =  buyTicketReportMapper.getGustStrateDataNew(buyTicketReportQuery);
		if(gustStrateList2!=null){
			for (GustStrate gustStrate : gustStrateList2) {
				List<BuyTicketReport> BuyTicketReportList = new ArrayList<BuyTicketReport>();
				buyTicketReportQuery.setDpt_AirPt_Cd(gustStrate.getDpt_AirPt_Cd());
				buyTicketReportQuery.setArrv_Airpt_Cd(gustStrate.getArrv_Airpt_Cd());
				List<GustStrate> gustGutDateList =  gustGutDateListAll2;
				List<GustStrate> gustGustStrateList =  new ArrayList<GustStrate>();
				for (GustStrate gustStrate2 : gustGustStrateListAll2) {
					if(gustStrate.getDpt_AirPt_Cd().equals(gustStrate2.getDpt_AirPt_Cd())&&gustStrate.getArrv_Airpt_Cd().equals(gustStrate2.getArrv_Airpt_Cd())){
						gustGustStrateList.add(gustStrate2);
					}
				}
				for (GustStrate gustStrate2 : gustGutDateList) {
					BuyTicketReport BuyTicketReport = new BuyTicketReport();
					String datee = gustStrate2.getOn_Gut_Dte();
					String [] str = datee.split("-");
					List<GustStrate> gustGustStrateList2 = new ArrayList<GustStrate>();
					for (GustStrate gustStrate3 : gustGustStrateList) {
						String datee2 = gustStrate3.getOn_Gut_Dte();
						String [] str2 = datee2.split("-");
						if((Integer.parseInt(str[0])==Integer.parseInt(str2[0]))&&(Integer.parseInt(str[1])==Integer.parseInt(str2[1]))&&(Integer.parseInt(str[2])==Integer.parseInt(str2[2]))){
							gustGustStrateList2.add(gustStrate3);
						}
					}
					try {
						Date gutDate = df.parse(datee);
						for (GustStrate gustStrate3 : gustGustStrateList2) {
							Date dtaDate =  df.parse(gustStrate3.getDta_Sce());
							int chazhi = (int) ((gutDate.getTime() - dtaDate.getTime())/(24*60*60*1000));
							if(chazhi>30){
								chazhi =31;
							}
							switch(chazhi){
								case -1: BuyTicketReport.setProgressf1(gustStrate3.getGut_Rae()+"%"); break;
								case 0: BuyTicketReport.setProgress0(gustStrate3.getGut_Rae()+"%"); break;
								case 1: BuyTicketReport.setProgress1(gustStrate3.getGut_Rae()+"%");break;
								case 2: BuyTicketReport.setProgress2(gustStrate3.getGut_Rae()+"%");break;
								case 3: BuyTicketReport.setProgress3(gustStrate3.getGut_Rae()+"%");break;
								case 4: BuyTicketReport.setProgress4(gustStrate3.getGut_Rae()+"%");break;
								case 5: BuyTicketReport.setProgress5(gustStrate3.getGut_Rae()+"%");break;
								case 6: BuyTicketReport.setProgress6(gustStrate3.getGut_Rae()+"%");break;
								case 7: BuyTicketReport.setProgress7(gustStrate3.getGut_Rae()+"%");break;
								case 8: BuyTicketReport.setProgress8(gustStrate3.getGut_Rae()+"%");break;
								case 9: BuyTicketReport.setProgress9(gustStrate3.getGut_Rae()+"%");break;
								case 10: BuyTicketReport.setProgress10(gustStrate3.getGut_Rae()+"%");break;
								case 11: BuyTicketReport.setProgress11(gustStrate3.getGut_Rae()+"%");break;
								case 12: BuyTicketReport.setProgress12(gustStrate3.getGut_Rae()+"%");break;
								case 13: BuyTicketReport.setProgress13(gustStrate3.getGut_Rae()+"%");break;
								case 14: BuyTicketReport.setProgress14(gustStrate3.getGut_Rae()+"%");break;
								case 15: BuyTicketReport.setProgress15(gustStrate3.getGut_Rae()+"%");break;
								case 16: BuyTicketReport.setProgress16(gustStrate3.getGut_Rae()+"%");break;
								case 17: BuyTicketReport.setProgress17(gustStrate3.getGut_Rae()+"%");break;
								case 18: BuyTicketReport.setProgress18(gustStrate3.getGut_Rae()+"%");break;
								case 19: BuyTicketReport.setProgress19(gustStrate3.getGut_Rae()+"%");break;
								case 20: BuyTicketReport.setProgress20(gustStrate3.getGut_Rae()+"%");break;
								case 21: BuyTicketReport.setProgress21(gustStrate3.getGut_Rae()+"%");break;
								case 22: BuyTicketReport.setProgress22(gustStrate3.getGut_Rae()+"%");break;
								case 23: BuyTicketReport.setProgress23(gustStrate3.getGut_Rae()+"%");break;
								case 24: BuyTicketReport.setProgress24(gustStrate3.getGut_Rae()+"%");break;
								case 25: BuyTicketReport.setProgress25(gustStrate3.getGut_Rae()+"%");break;
								case 26: BuyTicketReport.setProgress26(gustStrate3.getGut_Rae()+"%");break;
								case 27: BuyTicketReport.setProgress27(gustStrate3.getGut_Rae()+"%");break;
								case 28: BuyTicketReport.setProgress28(gustStrate3.getGut_Rae()+"%");break;
								case 29: BuyTicketReport.setProgress29(gustStrate3.getGut_Rae()+"%");break;
								case 30: BuyTicketReport.setProgress30(gustStrate3.getGut_Rae()+"%");break;
								case 31: BuyTicketReport.setProgressd30(gustStrate3.getGut_Rae()+"%");break;
							}
						}
						BuyTicketReport.setDow(TextUtil.getWeekOfDate(df.parse(datee)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					BuyTicketReport.setFltNumDate(datee);
					BuyTicketReport.setProgress02(Double.parseDouble(BuyTicketReport.getProgress0()==null?"0":BuyTicketReport.getProgress0().replace("%", ""))-Double.parseDouble(BuyTicketReport.getProgress2()==null?"0":BuyTicketReport.getProgress2().replace("%", ""))+"%");
					BuyTicketReport.setProgress06(Double.parseDouble(BuyTicketReport.getProgress0()==null?"0":BuyTicketReport.getProgress0().replace("%", ""))-Double.parseDouble(BuyTicketReport.getProgress6()==null?"0":BuyTicketReport.getProgress6().replace("%", ""))+"%");
					BuyTicketReport.setProgress011(Double.parseDouble(BuyTicketReport.getProgress0()==null?"0":BuyTicketReport.getProgress0().replace("%", ""))-Double.parseDouble(BuyTicketReport.getProgress11()==null?"0":BuyTicketReport.getProgress11().replace("%", ""))+"%");
					BuyTicketReportList.add(BuyTicketReport);
				}
				buyTicketReportListMap.put(buyTicketReportQuery.getFlt_nbr_Count()+outPortMapper.getNameByCode(gustStrate.getDpt_AirPt_Cd())+"-"+outPortMapper.getNameByCode(gustStrate.getArrv_Airpt_Cd()), BuyTicketReportList);
			}
		}
		return buyTicketReportListMap;
	}

	@Override
	public List<String> getBuyTicketReportLine(BuyTicketReportQuery buyTicketReportQuery) {
		String flyNbr = buyTicketReportQuery.getFlt_nbr_Count();
		String flyNbrBNew = "";
		String tempNbr = "";
		if(!TextUtil.isEmpty(flyNbr)&&flyNbr.length()>5){
			String numold2 = flyNbr.substring(0, 4);
			String lastTwoold = flyNbr.substring(4, 5);
			String numlast = flyNbr.substring(5, 6);
			if(Integer.parseInt(numlast)%2==0){
				if(Integer.parseInt(numlast)==0){
					flyNbrBNew =numold2 + (Integer.parseInt(lastTwoold)-1) + "9";
				}else{
					flyNbrBNew =numold2 + lastTwoold + (Integer.parseInt(numlast)-1);
				}
				tempNbr = flyNbr;
				flyNbr = flyNbrBNew;
				flyNbrBNew = tempNbr;
			}else{
				if(Integer.parseInt(numlast)==9){
					flyNbrBNew = numold2 + (Integer.parseInt(lastTwoold)+1) + "0";
				}else{
					flyNbrBNew = numold2 + lastTwoold + + (Integer.parseInt(numlast)+1);
				}
			}
		}
		buyTicketReportQuery.setFlt_nbr_Count(flyNbr);
		List<String> strList = new ArrayList<String>();
		List<GustStrate> gustStrateList =  buyTicketReportMapper.getHuangduan(buyTicketReportQuery);
		buyTicketReportQuery.setCompanyId(UserContext.getCompanyId());
		List<String> qstrList = buyTicketReportMapper.getfltEctByFlyNum(buyTicketReportQuery);
		String qstr = "";
		if(qstrList!=null&&qstrList.size()>0){
			for (String string : qstrList) {
				qstr = string;
			}
		}
		if(gustStrateList!=null&&gustStrateList.size()>0){
			if(!TextUtil.isEmpty(qstr)){
				if(qstr.length()==9){
					strList.add(outPortMapper.getNameByCode(qstr.substring(0, 3))+"-"+outPortMapper.getNameByCode(qstr.substring(3, 6))+"-"+outPortMapper.getNameByCode(qstr.substring(6, 9)));
				}
			}
		}
		for (GustStrate gustStrate : gustStrateList) {
			String dptstr = gustStrate.getDpt_AirPt_Cd();
			String arrstr = gustStrate.getArrv_Airpt_Cd();
			strList.add(outPortMapper.getNameByCode(dptstr)+"-"+outPortMapper.getNameByCode(arrstr));
		}
		buyTicketReportQuery.setFlt_nbr_Count(flyNbrBNew);
		List<GustStrate> gustStrateList2 =  buyTicketReportMapper.getHuangduan(buyTicketReportQuery);
		List<String> hstrList = buyTicketReportMapper.getfltEctByFlyNum(buyTicketReportQuery);
		String hstr = "";
		if(hstrList!=null&&hstrList.size()>0){
			for (String string : hstrList) {
				hstr = string;
			}
		}
		if(gustStrateList2!=null&&gustStrateList2.size()>0){
			if(!TextUtil.isEmpty(hstr)){
				if(hstr.length()==9){
					strList.add(outPortMapper.getNameByCode(hstr.substring(0, 3))+"-"+outPortMapper.getNameByCode(hstr.substring(3, 6))+"-"+outPortMapper.getNameByCode(hstr.substring(6, 9)));
				}
			}
		}
		for (GustStrate gustStrate : gustStrateList2) {
			String dptstr = gustStrate.getDpt_AirPt_Cd();
			String arrstr = gustStrate.getArrv_Airpt_Cd();
			strList.add(outPortMapper.getNameByCode(dptstr)+"-"+outPortMapper.getNameByCode(arrstr));
		}
		return strList;
	}

	@Override
	public Map<String,Object> getBuyTicketReportLineData(BuyTicketReportQuery buyTicketReportQuery,Map<String,Object> flyListMap) {
		//算单段的
		List<String> flyList = (List<String>) flyListMap.get("hangxianList");
		String [] strs = buyTicketReportQuery.getAirLine().split("=");
		if(strs.length>2){
		    String	dptcode = outPortMapper.getAirportCodeByAirportName(strs[0]);
		    String	pstcode = outPortMapper.getAirportCodeByAirportName(strs[1]);
		    String	arrcode = outPortMapper.getAirportCodeByAirportName(strs[2]);
		    buyTicketReportQuery.setDpt_AirPt_Cd(dptcode);
			buyTicketReportQuery.setArrv_Airpt_Cd(arrcode);
			buyTicketReportQuery.setPst_cd(pstcode);
		}else{
			String	dptcode = outPortMapper.getAirportCodeByAirportName(strs[0]);
		    String	arrcode = outPortMapper.getAirportCodeByAirportName(strs[2]);
		    buyTicketReportQuery.setDpt_AirPt_Cd(dptcode);
			buyTicketReportQuery.setArrv_Airpt_Cd(arrcode);
			buyTicketReportQuery.setPst_cd(null);
		}
		buyTicketReportQuery.setFlt_nbr_Count((String)flyListMap.get("fltNumq"));
		buyTicketReportQuery.setFlt_nbr_Counth((String)flyListMap.get("fltNumh"));
		Map<String,Object> retMap = new HashMap<String,Object>();
		List<GustStrate> gustGutDateList = new ArrayList<GustStrate>();
		List<GustStrate> gustGustStrateList_temp = new ArrayList<GustStrate>();
		gustGutDateList =  buyTicketReportMapper.getGutDteData(buyTicketReportQuery);
		gustGustStrateList_temp =   buyTicketReportMapper.getGustStrateDataNew(buyTicketReportQuery);
		for (String flt : flyList) {
			String [] arr = flt.split(",");
			String [] fltStr = arr[1].split("-");
			String fltt = "";
			Map<String,Object> retMapTemp = new HashMap<String,Object>();
			if(fltStr.length<=2){
				String dptc = fltStr[0];
				String arrc = fltStr[1];
				String flyNbr = arr[0];
    			fltt = fltStr[0]+"-"+ fltStr[1];
    			List<GustStrate> gustGustStrateList = new ArrayList<GustStrate>();
    			for (GustStrate gustStrate : gustGustStrateList_temp) {
    				String dpttemp = gustStrate.getDpt_AirPt_Cd();
    				String arrtemp = gustStrate.getArrv_Airpt_Cd();
    				String flytemp = gustStrate.getFlt_Nbr();
    				if(dpttemp.equals(dptc)&&arrtemp.equals(arrc)&&flytemp.equals(flyNbr)){
    					gustGustStrateList.add(gustStrate);
    				}
    			}
    			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
    			List<BuyTicketReport> BuyTicketReportList = new ArrayList<BuyTicketReport>();
    			for (GustStrate gustStrate2 : gustGutDateList) {
    				BuyTicketReport BuyTicketReport = new BuyTicketReport();
    				String datee = gustStrate2.getOn_Gut_Dte();
    				String [] str = datee.split("-");
    				List<GustStrate> gustGustStrateList2 = new ArrayList<GustStrate>();
    				for (GustStrate gustStrate3 : gustGustStrateList) {
    					String datee2 = gustStrate3.getOn_Gut_Dte();
    					String [] str2 = datee2.split("-");
    					if((Integer.parseInt(str[0])==Integer.parseInt(str2[0]))&&(Integer.parseInt(str[1])==Integer.parseInt(str2[1]))&&(Integer.parseInt(str[2])==Integer.parseInt(str2[2]))){
    						gustGustStrateList2.add(gustStrate3);
    					}
    				}
    				try {
    					Date gutDate = df.parse(datee);
    					String temp=df.format(gutDate)+",0,0";
    					String temptoday=df.format(gutDate)+",0,0";
    					int tem = 100;
    					String [] temparr;
    					String count_Set = "";
    					//今天当做差值最小这天
    					int today = 100;
    					for (GustStrate gustStrate3 : gustGustStrateList2) {
    						count_Set = gustStrate3.getCount_Set();
    						Date dtaDate =  df.parse(gustStrate3.getDta_Sce());
    						int chazhi = (int) ((gutDate.getTime() - dtaDate.getTime())/(24*60*60*1000));
    						if(chazhi>30){
    							chazhi =31;
    						}
    						if(today>chazhi){
    							today = chazhi;
    						}
    						temparr = temp.split(",");
    						if(gustStrate3.getGut_Rae()>=Integer.parseInt(temparr[1])){
    							String datee2 = gustStrate3.getDta_Sce();
    							temp = datee2+","+gustStrate3.getGut_Rae()+","+ gustStrate3.getPersons();
    						}
    						if(gustStrate3.getDta_Sce().equals(df.format(new Date()))){
    							BuyTicketReport.setTodaydate(TextUtil.addDateDay(datee, -today)+","+gustStrate3.getGut_Rae()+","+gustStrate3.getPersons());
    						}
    						if(tem>chazhi){
    							tem = chazhi;
    							temptoday = TextUtil.addDateDay(datee, -chazhi)+","+gustStrate3.getGut_Rae()+","+gustStrate3.getPersons();
    						}
    						switch(chazhi){
    							case -1: BuyTicketReport.setProgressf1(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons()); break;
    							case 0: BuyTicketReport.setProgress0(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons()); break;
    							case 1: BuyTicketReport.setProgress1(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 2: BuyTicketReport.setProgress2(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 3: BuyTicketReport.setProgress3(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 4: BuyTicketReport.setProgress4(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 5: BuyTicketReport.setProgress5(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 6: BuyTicketReport.setProgress6(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 7: BuyTicketReport.setProgress7(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 8: BuyTicketReport.setProgress8(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 9: BuyTicketReport.setProgress9(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 10: BuyTicketReport.setProgress10(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 11: BuyTicketReport.setProgress11(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 12: BuyTicketReport.setProgress12(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 13: BuyTicketReport.setProgress13(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 14: BuyTicketReport.setProgress14(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 15: BuyTicketReport.setProgress15(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 16: BuyTicketReport.setProgress16(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 17: BuyTicketReport.setProgress17(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 18: BuyTicketReport.setProgress18(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 19: BuyTicketReport.setProgress19(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 20: BuyTicketReport.setProgress20(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 21: BuyTicketReport.setProgress21(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 22: BuyTicketReport.setProgress22(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 23: BuyTicketReport.setProgress23(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 24: BuyTicketReport.setProgress24(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 25: BuyTicketReport.setProgress25(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 26: BuyTicketReport.setProgress26(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 27: BuyTicketReport.setProgress27(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 28: BuyTicketReport.setProgress28(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 29: BuyTicketReport.setProgress29(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 30: BuyTicketReport.setProgress30(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    							case 31: BuyTicketReport.setProgressd30(gustStrate3.getGut_Rae()+"%,"+gustStrate3.getPersons());break;
    						}
    					}
    					BuyTicketReport.setDow(TextUtil.getWeekOfDate(df.parse(datee)));
    					BuyTicketReport.setCount_Set(count_Set);
    					BuyTicketReport.setMaxDayandDate(temp);
    					if(TextUtil.isEmpty(BuyTicketReport.getTodaydate())){
    						BuyTicketReport.setTodaydate(temptoday);
    					}
    				} catch (ParseException e) {
    					e.printStackTrace();
    				}
    				BuyTicketReport.setFltNumDate(datee);
    				BuyTicketReportList.add(BuyTicketReport);
    			}
    			retMapTemp.put("flyNum", flyNbr);
    			retMapTemp.put("list", BuyTicketReportList);
    		}
			if(!TextUtil.isEmpty(fltt)){
				retMap.put(fltt, retMapTemp);
			}
		}
		return retMap;
	}

	

	@Override
	public Map<String, Object> getBuyTicketReportSingleLineData(List<String> flyList, Map<String, Object> retMap) {
		Map<String ,Object> retMapNew = new LinkedMap();
		DecimalFormat df = new DecimalFormat("##");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		for (String flt : flyList) {
			String [] arr = flt.split(",");
			String fltNbr = arr[0];
			String [] fltStr = arr[1].split("-");
			if(fltStr.length>2){
				String flttemp = fltStr[0]+"-"+fltStr[1]+"-"+fltStr[2];
				//短1
				String strd1 = fltStr[0]+"-"+fltStr[1];
				Map<String, Object> strd1Map = (Map<String, Object>) retMap.get(strd1);
				List<BuyTicketReport> strd1List = (List<BuyTicketReport>) strd1Map.get("list");
				//短2
				String strd2 = fltStr[1]+"-"+fltStr[2];
				Map<String, Object> strd2Map = (Map<String, Object>) retMap.get(strd2);
				List<BuyTicketReport> strd2List = (List<BuyTicketReport>) strd2Map.get("list");
				//长段
				String strcd = fltStr[0]+"-"+fltStr[2];
				Map<String, Object> strcdMap = (Map<String, Object>) retMap.get(strcd);
				List<BuyTicketReport> strcdList = (List<BuyTicketReport>) strcdMap.get("list");
				//单程数据
				String zwsTemp = "";
				Map<String, Object> dcMap = new HashMap<String, Object>();
				List<BuyTicketReport> dcList = new ArrayList<BuyTicketReport>();
				for (BuyTicketReport buyTicketReport1 : strd1List) {
					String datee1 = buyTicketReport1.getFltNumDate();
					for (BuyTicketReport buyTicketReport2 : strd2List) {
						String datee2 = buyTicketReport2.getFltNumDate();
						for (BuyTicketReport buyTicketReport3 : strcdList) {
							String datee3 = buyTicketReport3.getFltNumDate();
							if(datee1.equals(datee2)&&datee1.equals(datee3)){
								BuyTicketReport buyTicketReport = new BuyTicketReport();
								buyTicketReport.setFltNumDate(datee3);
								String zws = buyTicketReport1.getCount_Set();
								if(TextUtil.isEmpty(zws)){
									zws = buyTicketReport2.getCount_Set();
								}
								if(TextUtil.isEmpty(zws)){
									zws = buyTicketReport3.getCount_Set();
								}
								if(TextUtil.isEmpty(zws)){
									zws = zwsTemp;
								}else{
									zwsTemp = zws;
								}
								if(!TextUtil.isEmpty(buyTicketReport1.getTodaydate())&&!TextUtil.isEmpty(buyTicketReport2.getTodaydate())&&!TextUtil.isEmpty(buyTicketReport3.getTodaydate())){
									String todaydate1 = buyTicketReport1.getTodaydate().split(",")[0];
									String todaydate2 = buyTicketReport2.getTodaydate().split(",")[0];
									String todaydate3 = buyTicketReport3.getTodaydate().split(",")[0];
									if(todaydate1.equals(todaydate2)&&todaydate1.equals(todaydate3)){
										String d1rs = buyTicketReport1.getTodaydate().split(",")[2];
										String d2rs = buyTicketReport2.getTodaydate().split(",")[2];
										String crs = buyTicketReport3.getTodaydate().split(",")[2];
										if(TextUtil.isEmpty(d1rs)){
											d1rs = "0.0";
										}
										if(TextUtil.isEmpty(d2rs)){
											d2rs = "0.0";
										}
										if(TextUtil.isEmpty(crs)){
											crs = "0.0";
										}
										
										double gutRae = ((Double.parseDouble(d1rs)+Double.parseDouble(d2rs))+(2*Double.parseDouble(crs)))/(2*Double.parseDouble(zws))*100;
										int rs = Integer.parseInt(d1rs) + Integer.parseInt(d2rs) + Integer.parseInt(crs);
										buyTicketReport.setTodaydate(buyTicketReport3.getTodaydate().split(",")[0]+","+df.format(gutRae)+","+rs);
									}else{
										//找最大的今天
										try {
											Date dtaDate1 = sdf.parse(todaydate1);
											Date dtaDate2 =  sdf.parse(todaydate2);
											Date dtaDate3 =  sdf.parse(todaydate3);
											if(dtaDate1.getTime()>dtaDate2.getTime()&&dtaDate1.getTime()>dtaDate3.getTime()){
												String d1rs = buyTicketReport1.getTodaydate().split(",")[2];
												double gutRae = (Double.parseDouble(d1rs))/(2*Double.parseDouble(zws))*100;
												int rs = Integer.parseInt(d1rs);
												buyTicketReport.setTodaydate(buyTicketReport1.getTodaydate().split(",")[0]+","+df.format(gutRae)+","+rs);
											}
											if(dtaDate2.getTime()>dtaDate1.getTime()&&dtaDate2.getTime()>dtaDate3.getTime()){
												String d1rs = buyTicketReport2.getTodaydate().split(",")[2];
												double gutRae = (Double.parseDouble(d1rs))/(2*Double.parseDouble(zws))*100;
												int rs = Integer.parseInt(d1rs);
												buyTicketReport.setTodaydate(buyTicketReport2.getTodaydate().split(",")[0]+","+df.format(gutRae)+","+rs);
											}
											if(dtaDate3.getTime()>dtaDate2.getTime()&&dtaDate3.getTime()>dtaDate1.getTime()){
												String d1rs = buyTicketReport3.getTodaydate().split(",")[2];
												double gutRae = (Double.parseDouble(d1rs))/(2*Double.parseDouble(zws))*100;
												int rs = Integer.parseInt(d1rs);
												buyTicketReport.setTodaydate(buyTicketReport3.getTodaydate().split(",")[0]+","+df.format(gutRae)+","+rs);
											}
											
										} catch (ParseException e) {
											e.printStackTrace();
										}
										
									}
									
								}
								String maxDate = "";
								double marGutRae = 0.0;
								int maxRs = 0;
								for(int chazhi =-1;chazhi<32;chazhi++){
									switch(chazhi){
										case -1:if(!TextUtil.isEmpty(buyTicketReport1.getProgressf1())&&!TextUtil.isEmpty(buyTicketReport2.getProgressf1())&&!TextUtil.isEmpty(buyTicketReport3.getProgressf1())){
												String d1rsf1 = buyTicketReport1.getProgressf1().split(",")[1];
												String d2rsf1 = buyTicketReport2.getProgressf1().split(",")[1];
												String crsf1 = buyTicketReport3.getProgressf1().split(",")[1];
												if(TextUtil.isEmpty(d1rsf1)){d1rsf1 = "0.0";}
												if(TextUtil.isEmpty(d2rsf1)){d2rsf1 = "0.0";}
												if(TextUtil.isEmpty(crsf1)){crsf1 = "0.0";}
												double gutRaef1 = ((Double.parseDouble(d1rsf1)+Double.parseDouble(d2rsf1))+(2*Double.parseDouble(crsf1)))/(2*Double.parseDouble(zws))*100;
												int rsf1 = Integer.parseInt(d1rsf1) + Integer.parseInt(d2rsf1) + Integer.parseInt(crsf1);
												buyTicketReport.setProgressf1(df.format(gutRaef1)+"%,"+rsf1);
												if(marGutRae < gutRaef1){
													maxRs = rsf1;
													marGutRae = gutRaef1;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}
										}break;
										case 0: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress0())&&!TextUtil.isEmpty(buyTicketReport2.getProgress0())&&!TextUtil.isEmpty(buyTicketReport3.getProgress0())){
												String d1rs0 = buyTicketReport1.getProgress0().split(",")[1];
												String d2rs0 = buyTicketReport2.getProgress0().split(",")[1];
												String crs0 = buyTicketReport3.getProgress0().split(",")[1];
												if(TextUtil.isEmpty(d1rs0)){d1rs0 = "0.0";}
												if(TextUtil.isEmpty(d2rs0)){d2rs0 = "0.0";}
												if(TextUtil.isEmpty(crs0)){crs0 = "0.0";}
												double gutRae0 = ((Double.parseDouble(d1rs0)+Double.parseDouble(d2rs0))+(2*Double.parseDouble(crs0)))/(2*Double.parseDouble(zws))*100;
												int rs0 = Integer.parseInt(d1rs0) + Integer.parseInt(d2rs0) + Integer.parseInt(crs0);
												buyTicketReport.setProgress0(df.format(gutRae0)+"%,"+rs0);
												if(marGutRae < gutRae0){
													maxRs = rs0;
													marGutRae = gutRae0;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}
											}break;
										case 1: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress1())&&!TextUtil.isEmpty(buyTicketReport2.getProgress1())&&!TextUtil.isEmpty(buyTicketReport3.getProgress1())){
												String d1rs1 = buyTicketReport1.getProgress1().split(",")[1];
												String d2rs1 = buyTicketReport2.getProgress1().split(",")[1];
												String crs1 = buyTicketReport3.getProgress1().split(",")[1];
												if(TextUtil.isEmpty(d1rs1)){d1rs1 = "0.0";}
												if(TextUtil.isEmpty(d2rs1)){d2rs1 = "0.0";}
												if(TextUtil.isEmpty(crs1)){crs1 = "0.0";}
												double gutRae1 = ((Double.parseDouble(d1rs1)+Double.parseDouble(d2rs1))+(2*Double.parseDouble(crs1)))/(2*Double.parseDouble(zws))*100;
												int rs1 = Integer.parseInt(d1rs1) + Integer.parseInt(d2rs1) + Integer.parseInt(crs1);
												buyTicketReport.setProgress1(df.format(gutRae1)+"%,"+rs1);
												if(marGutRae < gutRae1){
													maxRs = rs1;
													marGutRae = gutRae1;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}
											}break;
										case 2: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress2())&&!TextUtil.isEmpty(buyTicketReport2.getProgress2())&&!TextUtil.isEmpty(buyTicketReport3.getProgress2())){
												String d1rs2 = buyTicketReport1.getProgress2().split(",")[1];
												String d2rs2 = buyTicketReport2.getProgress2().split(",")[1];
												String crs2 = buyTicketReport3.getProgress2().split(",")[1];
												if(TextUtil.isEmpty(d1rs2)){d1rs2 = "0.0";}
												if(TextUtil.isEmpty(d2rs2)){d2rs2 = "0.0";}
												if(TextUtil.isEmpty(crs2)){crs2 = "0.0";}
												double gutRae2 = ((Double.parseDouble(d1rs2)+Double.parseDouble(d2rs2))+(2*Double.parseDouble(crs2)))/(2*Double.parseDouble(zws))*100;
												int rs2 = Integer.parseInt(d1rs2) + Integer.parseInt(d2rs2) + Integer.parseInt(crs2);
												buyTicketReport.setProgress2(df.format(gutRae2)+"%,"+rs2);
												if(marGutRae < gutRae2){
													maxRs = rs2;
													marGutRae = gutRae2;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 3: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress3())&&!TextUtil.isEmpty(buyTicketReport2.getProgress3())&&!TextUtil.isEmpty(buyTicketReport3.getProgress3())){
												String d1rs3 = buyTicketReport1.getProgress3().split(",")[1];
												String d2rs3 = buyTicketReport2.getProgress3().split(",")[1];
												String crs3 = buyTicketReport3.getProgress3().split(",")[1];
												if(TextUtil.isEmpty(d1rs3)){d1rs3 = "0.0";}
												if(TextUtil.isEmpty(d2rs3)){d2rs3 = "0.0";}
												if(TextUtil.isEmpty(crs3)){crs3 = "0.0";}
												double gutRae3 = ((Double.parseDouble(d1rs3)+Double.parseDouble(d2rs3))+(2*Double.parseDouble(crs3)))/(2*Double.parseDouble(zws))*100;
												int rs3 = Integer.parseInt(d1rs3) + Integer.parseInt(d2rs3) + Integer.parseInt(crs3);
												buyTicketReport.setProgress3(df.format(gutRae3)+"%,"+rs3);
												if(marGutRae <gutRae3){
													maxRs = rs3;
													marGutRae = gutRae3;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 4: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress4())&&!TextUtil.isEmpty(buyTicketReport2.getProgress4())&&!TextUtil.isEmpty(buyTicketReport3.getProgress4())){
												String d1rs4 = buyTicketReport1.getProgress4().split(",")[1];
												String d2rs4 = buyTicketReport2.getProgress4().split(",")[1];
												String crs4 = buyTicketReport3.getProgress4().split(",")[1];
												if(TextUtil.isEmpty(d1rs4)){d1rs4 = "0.0";}
												if(TextUtil.isEmpty(d2rs4)){d2rs4 = "0.0";}
												if(TextUtil.isEmpty(crs4)){crs4 = "0.0";}
												double gutRae4 = ((Double.parseDouble(d1rs4)+Double.parseDouble(d2rs4))+(2*Double.parseDouble(crs4)))/(2*Double.parseDouble(zws))*100;
												int rs4 = Integer.parseInt(d1rs4) + Integer.parseInt(d2rs4) + Integer.parseInt(crs4);
												buyTicketReport.setProgress4(df.format(gutRae4)+"%,"+rs4);
												if(marGutRae <gutRae4){
													maxRs = rs4;
													marGutRae = gutRae4;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 5: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress5())&&!TextUtil.isEmpty(buyTicketReport2.getProgress5())&&!TextUtil.isEmpty(buyTicketReport3.getProgress5())){
												String d1rs5 = buyTicketReport1.getProgress5().split(",")[1];
												String d2rs5 = buyTicketReport2.getProgress5().split(",")[1];
												String crs5 = buyTicketReport3.getProgress5().split(",")[1];
												if(TextUtil.isEmpty(d1rs5)){d1rs5 = "0.0";}
												if(TextUtil.isEmpty(d2rs5)){d2rs5 = "0.0";}
												if(TextUtil.isEmpty(crs5)){crs5 = "0.0";}
												double gutRae5 = ((Double.parseDouble(d1rs5)+Double.parseDouble(d2rs5))+(2*Double.parseDouble(crs5)))/(2*Double.parseDouble(zws))*100;
												int rs5 = Integer.parseInt(d1rs5) + Integer.parseInt(d2rs5) + Integer.parseInt(crs5);
												buyTicketReport.setProgress5(df.format(gutRae5)+"%,"+rs5);
												if(marGutRae <gutRae5){
													maxRs = rs5;
													marGutRae = gutRae5;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 6: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress6())&&!TextUtil.isEmpty(buyTicketReport2.getProgress6())&&!TextUtil.isEmpty(buyTicketReport3.getProgress6())){
												String d1rs6 = buyTicketReport1.getProgress6().split(",")[1];
												String d2rs6 = buyTicketReport2.getProgress6().split(",")[1];
												String crs6 = buyTicketReport3.getProgress6().split(",")[1];
												if(TextUtil.isEmpty(d1rs6)){d1rs6 = "0.0";}
												if(TextUtil.isEmpty(d2rs6)){d2rs6 = "0.0";}
												if(TextUtil.isEmpty(crs6)){crs6 = "0.0";}
												double gutRae6 = ((Double.parseDouble(d1rs6)+Double.parseDouble(d2rs6))+(2*Double.parseDouble(crs6)))/(2*Double.parseDouble(zws))*100;
												int rs6 = Integer.parseInt(d1rs6) + Integer.parseInt(d2rs6) + Integer.parseInt(crs6);
												buyTicketReport.setProgress6(df.format(gutRae6)+"%,"+rs6);
												if(marGutRae <gutRae6){
													maxRs = rs6;
													marGutRae = gutRae6;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 7: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress7())&&!TextUtil.isEmpty(buyTicketReport2.getProgress7())&&!TextUtil.isEmpty(buyTicketReport3.getProgress7())){
												String d1rs7 = buyTicketReport1.getProgress7().split(",")[1];
												String d2rs7 = buyTicketReport2.getProgress7().split(",")[1];
												String crs7 = buyTicketReport3.getProgress7().split(",")[1];
												if(TextUtil.isEmpty(d1rs7)){d1rs7 = "0.0";}
												if(TextUtil.isEmpty(d2rs7)){d2rs7 = "0.0";}
												if(TextUtil.isEmpty(crs7)){crs7 = "0.0";}
												double gutRae7 = ((Double.parseDouble(d1rs7)+Double.parseDouble(d2rs7))+(2*Double.parseDouble(crs7)))/(2*Double.parseDouble(zws))*100;
												int rs7 = Integer.parseInt(d1rs7) + Integer.parseInt(d2rs7) + Integer.parseInt(crs7);
												buyTicketReport.setProgress7(df.format(gutRae7)+"%,"+rs7);
												if(marGutRae <gutRae7){
													maxRs = rs7;
													marGutRae = gutRae7;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 8: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress8())&&!TextUtil.isEmpty(buyTicketReport2.getProgress8())&&!TextUtil.isEmpty(buyTicketReport3.getProgress8())){
												String d1rs8 = buyTicketReport1.getProgress8().split(",")[1];
												String d2rs8 = buyTicketReport2.getProgress8().split(",")[1];
												String crs8 = buyTicketReport3.getProgress8().split(",")[1];
												if(TextUtil.isEmpty(d1rs8)){d1rs8 = "0.0";}
												if(TextUtil.isEmpty(d2rs8)){d2rs8 = "0.0";}
												if(TextUtil.isEmpty(crs8)){crs8 = "0.0";}
												double gutRae8 = ((Double.parseDouble(d1rs8)+Double.parseDouble(d2rs8))+(2*Double.parseDouble(crs8)))/(2*Double.parseDouble(zws))*100;
												int rs8 = Integer.parseInt(d1rs8) + Integer.parseInt(d2rs8) + Integer.parseInt(crs8);
												buyTicketReport.setProgress8(df.format(gutRae8)+"%,"+rs8);
												if(marGutRae <gutRae8){
													maxRs = rs8;
													marGutRae = gutRae8;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 9: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress9())&&!TextUtil.isEmpty(buyTicketReport2.getProgress9())&&!TextUtil.isEmpty(buyTicketReport3.getProgress9())){
												String d1rs9 = buyTicketReport1.getProgress9().split(",")[1];
												String d2rs9 = buyTicketReport2.getProgress9().split(",")[1];
												String crs9 = buyTicketReport3.getProgress9().split(",")[1];
												if(TextUtil.isEmpty(d1rs9)){d1rs9 = "0.0";}
												if(TextUtil.isEmpty(d2rs9)){d2rs9 = "0.0";}
												if(TextUtil.isEmpty(crs9)){crs9 = "0.0";}
												double gutRae9 = ((Double.parseDouble(d1rs9)+Double.parseDouble(d2rs9))+(2*Double.parseDouble(crs9)))/(2*Double.parseDouble(zws))*100;
												int rs9 = Integer.parseInt(d1rs9) + Integer.parseInt(d2rs9) + Integer.parseInt(crs9);
												buyTicketReport.setProgress9(df.format(gutRae9)+"%,"+rs9);
												if(marGutRae <gutRae9){
													maxRs = rs9;
													marGutRae = gutRae9;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 10: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress10())&&!TextUtil.isEmpty(buyTicketReport2.getProgress10())&&!TextUtil.isEmpty(buyTicketReport3.getProgress10())){
												String d1rs10 = buyTicketReport1.getProgress10().split(",")[1];
												String d2rs10 = buyTicketReport2.getProgress10().split(",")[1];
												String crs10 = buyTicketReport3.getProgress10().split(",")[1];
												if(TextUtil.isEmpty(d1rs10)){d1rs10 = "0.0";}
												if(TextUtil.isEmpty(d2rs10)){d2rs10 = "0.0";}
												if(TextUtil.isEmpty(crs10)){crs10 = "0.0";}
												double gutRae10 = ((Double.parseDouble(d1rs10)+Double.parseDouble(d2rs10))+(2*Double.parseDouble(crs10)))/(2*Double.parseDouble(zws))*100;
												int rs10 = Integer.parseInt(d1rs10) + Integer.parseInt(d2rs10) + Integer.parseInt(crs10);
												buyTicketReport.setProgress10(df.format(gutRae10)+"%,"+rs10);
												if(marGutRae <gutRae10){
													maxRs = rs10;
													marGutRae = gutRae10;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 11: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress11())&&!TextUtil.isEmpty(buyTicketReport2.getProgress11())&&!TextUtil.isEmpty(buyTicketReport3.getProgress11())){
												String d1rs11 = buyTicketReport1.getProgress11().split(",")[1];
												String d2rs11 = buyTicketReport2.getProgress11().split(",")[1];
												String crs11 = buyTicketReport3.getProgress11().split(",")[1];
												if(TextUtil.isEmpty(d1rs11)){d1rs11 = "0.0";}
												if(TextUtil.isEmpty(d2rs11)){d2rs11 = "0.0";}
												if(TextUtil.isEmpty(crs11)){crs11 = "0.0";}
												double gutRae11 = ((Double.parseDouble(d1rs11)+Double.parseDouble(d2rs11))+(2*Double.parseDouble(crs11)))/(2*Double.parseDouble(zws))*100;
												int rs11 = Integer.parseInt(d1rs11) + Integer.parseInt(d2rs11) + Integer.parseInt(crs11);
												buyTicketReport.setProgress11(df.format(gutRae11)+"%,"+rs11);
												if(marGutRae <gutRae11){
													maxRs = rs11;
													marGutRae = gutRae11;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 12: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress12())&&!TextUtil.isEmpty(buyTicketReport2.getProgress12())&&!TextUtil.isEmpty(buyTicketReport3.getProgress12())){
												String d1rs12 = buyTicketReport1.getProgress12().split(",")[1];
												String d2rs12 = buyTicketReport2.getProgress12().split(",")[1];
												String crs12 = buyTicketReport3.getProgress12().split(",")[1];
												if(TextUtil.isEmpty(d1rs12)){d1rs12 = "0.0";}
												if(TextUtil.isEmpty(d2rs12)){d2rs12 = "0.0";}
												if(TextUtil.isEmpty(crs12)){crs12 = "0.0";}
												double gutRae12 = ((Double.parseDouble(d1rs12)+Double.parseDouble(d2rs12))+(2*Double.parseDouble(crs12)))/(2*Double.parseDouble(zws))*100;
												int rs12 = Integer.parseInt(d1rs12) + Integer.parseInt(d2rs12) + Integer.parseInt(crs12);
												buyTicketReport.setProgress12(df.format(gutRae12)+"%,"+rs12);
												if(marGutRae <gutRae12){
													maxRs = rs12;
													marGutRae = gutRae12;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 13: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress13())&&!TextUtil.isEmpty(buyTicketReport2.getProgress13())&&!TextUtil.isEmpty(buyTicketReport3.getProgress13())){
												String d1rs13 = buyTicketReport1.getProgress13().split(",")[1];
												String d2rs13 = buyTicketReport2.getProgress13().split(",")[1];
												String crs13 = buyTicketReport3.getProgress13().split(",")[1];
												if(TextUtil.isEmpty(d1rs13)){d1rs13 = "0.0";}
												if(TextUtil.isEmpty(d2rs13)){d2rs13 = "0.0";}
												if(TextUtil.isEmpty(crs13)){crs13 = "0.0";}
												double gutRae13 = ((Double.parseDouble(d1rs13)+Double.parseDouble(d2rs13))+(2*Double.parseDouble(crs13)))/(2*Double.parseDouble(zws))*100;
												int rs13 = Integer.parseInt(d1rs13) + Integer.parseInt(d2rs13) + Integer.parseInt(crs13);
												buyTicketReport.setProgress13(df.format(gutRae13)+"%,"+rs13);
												if(marGutRae <gutRae13){
													maxRs = rs13;
													marGutRae = gutRae13;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 14: 
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress14())&&!TextUtil.isEmpty(buyTicketReport2.getProgress14())&&!TextUtil.isEmpty(buyTicketReport3.getProgress14())){
												String d1rs14 = buyTicketReport1.getProgress14().split(",")[1];
												String d2rs14 = buyTicketReport2.getProgress14().split(",")[1];
												String crs14 = buyTicketReport3.getProgress14().split(",")[1];
												if(TextUtil.isEmpty(d1rs14)){d1rs14 = "0.0";}
												if(TextUtil.isEmpty(d2rs14)){d2rs14 = "0.0";}
												if(TextUtil.isEmpty(crs14)){crs14 = "0.0";}
												double gutRae14 = ((Double.parseDouble(d1rs14)+Double.parseDouble(d2rs14))+(2*Double.parseDouble(crs14)))/(2*Double.parseDouble(zws))*100;
												int rs14 = Integer.parseInt(d1rs14) + Integer.parseInt(d2rs14) + Integer.parseInt(crs14);
												buyTicketReport.setProgress14(df.format(gutRae14)+"%,"+rs14);
												if(marGutRae <gutRae14){
													maxRs = rs14;
													marGutRae = gutRae14;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 15:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress15())&&!TextUtil.isEmpty(buyTicketReport2.getProgress15())&&!TextUtil.isEmpty(buyTicketReport3.getProgress15())){
												String d1rs15 = buyTicketReport1.getProgress15().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress15().split(",")[1];
												String crs15 = buyTicketReport3.getProgress15().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress15(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 16:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress16())&&!TextUtil.isEmpty(buyTicketReport2.getProgress16())&&!TextUtil.isEmpty(buyTicketReport3.getProgress16())){
												String d1rs16 = buyTicketReport1.getProgress16().split(",")[1];
												String d2rs16 = buyTicketReport2.getProgress16().split(",")[1];
												String crs16 = buyTicketReport3.getProgress16().split(",")[1];
												if(TextUtil.isEmpty(d1rs16)){d1rs16 = "0.0";}
												if(TextUtil.isEmpty(d2rs16)){d2rs16 = "0.0";}
												if(TextUtil.isEmpty(crs16)){crs16 = "0.0";}
												double gutRae16 = ((Double.parseDouble(d1rs16)+Double.parseDouble(d2rs16))+(2*Double.parseDouble(crs16)))/(2*Double.parseDouble(zws))*100;
												int rs16 = Integer.parseInt(d1rs16) + Integer.parseInt(d2rs16) + Integer.parseInt(crs16);
												buyTicketReport.setProgress16(df.format(gutRae16)+"%,"+rs16);
												if(marGutRae <gutRae16){
													maxRs = rs16;
													marGutRae = gutRae16;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 17:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress17())&&!TextUtil.isEmpty(buyTicketReport2.getProgress17())&&!TextUtil.isEmpty(buyTicketReport3.getProgress17())){
												String d1rs17 = buyTicketReport1.getProgress17().split(",")[1];
												String d2rs17 = buyTicketReport2.getProgress17().split(",")[1];
												String crs17 = buyTicketReport3.getProgress17().split(",")[1];
												if(TextUtil.isEmpty(d1rs17)){d1rs17 = "0.0";}
												if(TextUtil.isEmpty(d2rs17)){d2rs17 = "0.0";}
												if(TextUtil.isEmpty(crs17)){crs17 = "0.0";}
												double gutRae17 = ((Double.parseDouble(d1rs17)+Double.parseDouble(d2rs17))+(2*Double.parseDouble(crs17)))/(2*Double.parseDouble(zws))*100;
												int rs17 = Integer.parseInt(d1rs17) + Integer.parseInt(d2rs17) + Integer.parseInt(crs17);
												buyTicketReport.setProgress17(df.format(gutRae17)+"%,"+rs17);
												if(marGutRae <gutRae17){
													maxRs = rs17;
													marGutRae = gutRae17;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 18:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress18())&&!TextUtil.isEmpty(buyTicketReport2.getProgress18())&&!TextUtil.isEmpty(buyTicketReport3.getProgress18())){
												String d1rs18 = buyTicketReport1.getProgress18().split(",")[1];
												String d2rs18 = buyTicketReport2.getProgress18().split(",")[1];
												String crs18 = buyTicketReport3.getProgress18().split(",")[1];
												if(TextUtil.isEmpty(d1rs18)){d1rs18 = "0.0";}
												if(TextUtil.isEmpty(d2rs18)){d2rs18 = "0.0";}
												if(TextUtil.isEmpty(crs18)){crs18 = "0.0";}
												double gutRae18 = ((Double.parseDouble(d1rs18)+Double.parseDouble(d2rs18))+(2*Double.parseDouble(crs18)))/(2*Double.parseDouble(zws))*100;
												int rs18 = Integer.parseInt(d1rs18) + Integer.parseInt(d2rs18) + Integer.parseInt(crs18);
												buyTicketReport.setProgress18(df.format(gutRae18)+"%,"+rs18);
												if(marGutRae <gutRae18){
													maxRs = rs18;
													marGutRae = gutRae18;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 19:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress19())&&!TextUtil.isEmpty(buyTicketReport2.getProgress19())&&!TextUtil.isEmpty(buyTicketReport3.getProgress19())){
												String d1rs19 = buyTicketReport1.getProgress19().split(",")[1];
												String d2rs19 = buyTicketReport2.getProgress19().split(",")[1];
												String crs19 = buyTicketReport3.getProgress19().split(",")[1];
												if(TextUtil.isEmpty(d1rs19)){d1rs19 = "0.0";}
												if(TextUtil.isEmpty(d2rs19)){d2rs19 = "0.0";}
												if(TextUtil.isEmpty(crs19)){crs19 = "0.0";}
												double gutRae19 = ((Double.parseDouble(d1rs19)+Double.parseDouble(d2rs19))+(2*Double.parseDouble(crs19)))/(2*Double.parseDouble(zws))*100;
												int rs19 = Integer.parseInt(d1rs19) + Integer.parseInt(d2rs19) + Integer.parseInt(crs19);
												buyTicketReport.setProgress19(df.format(gutRae19)+"%,"+rs19);
												if(marGutRae <gutRae19){
													maxRs = rs19;
													marGutRae = gutRae19;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 20:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress20())&&!TextUtil.isEmpty(buyTicketReport2.getProgress20())&&!TextUtil.isEmpty(buyTicketReport3.getProgress20())){
												String d1rs15 = buyTicketReport1.getProgress20().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress20().split(",")[1];
												String crs15 = buyTicketReport3.getProgress20().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress20(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 21:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress21())&&!TextUtil.isEmpty(buyTicketReport2.getProgress21())&&!TextUtil.isEmpty(buyTicketReport3.getProgress21())){
												String d1rs15 = buyTicketReport1.getProgress21().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress21().split(",")[1];
												String crs15 = buyTicketReport3.getProgress21().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress21(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 22:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress22())&&!TextUtil.isEmpty(buyTicketReport2.getProgress22())&&!TextUtil.isEmpty(buyTicketReport3.getProgress22())){
												String d1rs15 = buyTicketReport1.getProgress22().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress22().split(",")[1];
												String crs15 = buyTicketReport3.getProgress22().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress22(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 23:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress23())&&!TextUtil.isEmpty(buyTicketReport2.getProgress23())&&!TextUtil.isEmpty(buyTicketReport3.getProgress23())){
												String d1rs15 = buyTicketReport1.getProgress23().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress23().split(",")[1];
												String crs15 = buyTicketReport3.getProgress23().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress23(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 24:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress24())&&!TextUtil.isEmpty(buyTicketReport2.getProgress24())&&!TextUtil.isEmpty(buyTicketReport3.getProgress24())){
												String d1rs15 = buyTicketReport1.getProgress24().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress24().split(",")[1];
												String crs15 = buyTicketReport3.getProgress24().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress24(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 25:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress25())&&!TextUtil.isEmpty(buyTicketReport2.getProgress25())&&!TextUtil.isEmpty(buyTicketReport3.getProgress25())){
												String d1rs15 = buyTicketReport1.getProgress25().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress25().split(",")[1];
												String crs15 = buyTicketReport3.getProgress25().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress25(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 26:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress26())&&!TextUtil.isEmpty(buyTicketReport2.getProgress26())&&!TextUtil.isEmpty(buyTicketReport3.getProgress26())){
												String d1rs15 = buyTicketReport1.getProgress26().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress26().split(",")[1];
												String crs15 = buyTicketReport3.getProgress26().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress26(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 27:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress27())&&!TextUtil.isEmpty(buyTicketReport2.getProgress27())&&!TextUtil.isEmpty(buyTicketReport3.getProgress27())){
												String d1rs15 = buyTicketReport1.getProgress27().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress27().split(",")[1];
												String crs15 = buyTicketReport3.getProgress27().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress27(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 28:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress28())&&!TextUtil.isEmpty(buyTicketReport2.getProgress28())&&!TextUtil.isEmpty(buyTicketReport3.getProgress28())){
												String d1rs15 = buyTicketReport1.getProgress28().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress28().split(",")[1];
												String crs15 = buyTicketReport3.getProgress28().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress28(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 29:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress29())&&!TextUtil.isEmpty(buyTicketReport2.getProgress29())&&!TextUtil.isEmpty(buyTicketReport3.getProgress29())){
												String d1rs15 = buyTicketReport1.getProgress29().split(",")[1];
												String d2rs15 = buyTicketReport2.getProgress29().split(",")[1];
												String crs15 = buyTicketReport3.getProgress29().split(",")[1];
												if(TextUtil.isEmpty(d1rs15)){d1rs15 = "0.0";}
												if(TextUtil.isEmpty(d2rs15)){d2rs15 = "0.0";}
												if(TextUtil.isEmpty(crs15)){crs15 = "0.0";}
												double gutRae15 = ((Double.parseDouble(d1rs15)+Double.parseDouble(d2rs15))+(2*Double.parseDouble(crs15)))/(2*Double.parseDouble(zws))*100;
												int rs15 = Integer.parseInt(d1rs15) + Integer.parseInt(d2rs15) + Integer.parseInt(crs15);
												buyTicketReport.setProgress29(df.format(gutRae15)+"%,"+rs15);
												if(marGutRae <gutRae15){
													maxRs = rs15;
													marGutRae = gutRae15;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 30:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgress30())&&!TextUtil.isEmpty(buyTicketReport2.getProgress30())&&!TextUtil.isEmpty(buyTicketReport3.getProgress30())){
												String d1rs30 = buyTicketReport1.getProgress30().split(",")[1];
												String d2rs30 = buyTicketReport2.getProgress30().split(",")[1];
												String crs30 = buyTicketReport3.getProgress30().split(",")[1];
												if(TextUtil.isEmpty(d1rs30)){d1rs30 = "0.0";}
												if(TextUtil.isEmpty(d2rs30)){d2rs30 = "0.0";}
												if(TextUtil.isEmpty(crs30)){crs30 = "0.0";}
												double gutRae30 = ((Double.parseDouble(d1rs30)+Double.parseDouble(d2rs30))+(2*Double.parseDouble(crs30)))/(2*Double.parseDouble(zws))*100;
												int rs30 = Integer.parseInt(d1rs30) + Integer.parseInt(d2rs30) + Integer.parseInt(crs30);
												buyTicketReport.setProgress30(df.format(gutRae30)+"%,"+rs30);
												if(marGutRae <gutRae30){
													maxRs = rs30;
													marGutRae = gutRae30;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
										case 31:
											if(!TextUtil.isEmpty(buyTicketReport1.getProgressd30())&&!TextUtil.isEmpty(buyTicketReport2.getProgressd30())&&!TextUtil.isEmpty(buyTicketReport3.getProgressd30())){
												String d1rs30 = buyTicketReport1.getProgressd30().split(",")[1];
												String d2rs30 = buyTicketReport2.getProgressd30().split(",")[1];
												String crs30 = buyTicketReport3.getProgressd30().split(",")[1];
												if(TextUtil.isEmpty(d1rs30)){d1rs30 = "0.0";}
												if(TextUtil.isEmpty(d2rs30)){d2rs30 = "0.0";}
												if(TextUtil.isEmpty(crs30)){crs30 = "0.0";}
												double gutRae30 = ((Double.parseDouble(d1rs30)+Double.parseDouble(d2rs30))+(2*Double.parseDouble(crs30)))/(2*Double.parseDouble(zws))*100;
												int rs30 = Integer.parseInt(d1rs30) + Integer.parseInt(d2rs30) + Integer.parseInt(crs30);
												buyTicketReport.setProgressd30(df.format(gutRae30)+"%,"+rs30);
												if(marGutRae <gutRae30){
													maxRs = rs30;
													marGutRae = gutRae30;
													maxDate = TextUtil.addDateDay(datee3, -chazhi);
												}	
											}break;
									}
								}
								
								//最大的天
								buyTicketReport.setMaxDayandDate(maxDate+","+df.format(marGutRae)+","+maxRs);
								dcList.add(buyTicketReport);
							}
						}
					}
				}
				dcMap.put("list", dcList);
				dcMap.put("flyNum", fltNbr);
				retMapNew.put(flttemp, dcMap);
				retMapNew.put(strd1, strd1Map);
				retMapNew.put(strd2, strd2Map);
				retMapNew.put(strcd, strcdMap);
			}
		}
		return retMapNew;
	}
	
	@Override
	public Map<String, Object> getBuyTicketReportLineNew(BuyTicketReportQuery buyTicketReportQuery) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String flyNbr = buyTicketReportQuery.getFlt_nbr_Count();
		String airlineq = buyTicketReportQuery.getAirLine();
//		String airline = "";
		String flyNbrBNew = "";
		String Nbr1 = flyNbr;
		String Nbr2 = "";
		if(!TextUtil.isEmpty(flyNbr)&&flyNbr.replace(" ", "").length()==6){
			String numold2 = flyNbr.substring(0, 4);
			String lastTwoold = flyNbr.substring(4, 5);
			String numlast = flyNbr.substring(5, 6);
			if(TextUtil.isNum(numlast)){
				if(Integer.parseInt(numlast)%2==0){
//					huiNbr = flyNbr;
					if(Integer.parseInt(numlast)==0){
						flyNbrBNew =numold2 + (Integer.parseInt(lastTwoold)-1) + "9";
					}else{
						flyNbrBNew =numold2 + lastTwoold + (Integer.parseInt(numlast)-1);
					}
					Nbr2 = flyNbrBNew;
//					String [] str = airlineq.split("=");
//					if(str.length>2){
//						airline = str[2]+"="+str[1]+"="+str[0];
//					}else{
//						airline = str[1]+"="+str[0];
//					}
				}else{
//					goNbr = flyNbr;
					if(Integer.parseInt(numlast)==9){
						flyNbrBNew = numold2 + (Integer.parseInt(lastTwoold)+1) + "0";
					}else{
						flyNbrBNew = numold2 + lastTwoold + + (Integer.parseInt(numlast)+1);
					}
					Nbr2 = flyNbrBNew;
//					airline = airlineq;
				}
			}
		}
		List<String> strList = new ArrayList<String>();
		String [] str = airlineq.split("=");
		if(str.length>2){
		    String	dptcode = outPortMapper.getAirportCodeByAirportName(str[0]);
		    String	pstcode = outPortMapper.getAirportCodeByAirportName(str[1]);
		    String	arrcode = outPortMapper.getAirportCodeByAirportName(str[2]);
		    strList.add(Nbr1+","+dptcode+"-"+pstcode+"-"+arrcode);
		    strList.add(Nbr1+","+dptcode+"-"+pstcode);
		    strList.add(Nbr1+","+dptcode+"-"+arrcode);
			strList.add(Nbr1+","+pstcode+"-"+arrcode);
			strList.add(Nbr2+","+arrcode+"-"+pstcode+"-"+dptcode);
			strList.add(Nbr2+","+arrcode+"-"+pstcode);
			strList.add(Nbr2+","+arrcode+"-"+dptcode);
			strList.add(Nbr2+","+pstcode+"-"+dptcode);
			retMap.put("dptName",dptcode ) ;
			retMap.put("pstName", pstcode) ;
			retMap.put("arrName", arrcode) ;
		}else{
				String	dptcode = outPortMapper.getAirportCodeByAirportName(str[0]);
			    String	arrcode = outPortMapper.getAirportCodeByAirportName(str[1]);
			    strList.add(Nbr1+","+dptcode+"-"+arrcode);
				strList.add(Nbr2+","+arrcode+"-"+dptcode);
				retMap.put("dptName",dptcode ) ;
				retMap.put("pstName", "") ;
				retMap.put("arrName", arrcode) ;
		}
		retMap.put("hangxianList", strList) ;
		retMap.put("fltNumq", flyNbr) ;
		retMap.put("fltNumh", flyNbrBNew) ;
		return retMap;
	}

	@Override
	public List<String> getAirLine(BuyTicketReportQuery buyTicketReportQuery) {
		//判断是否有销售动态，如果有在查询航线，否则不用查询
		List<String> flyNbrsList = buyTicketReportMapper.getIshavingData(buyTicketReportQuery);
		List<String> strList = new ArrayList<String>();
		if(flyNbrsList!=null&&flyNbrsList.size()>0){
			List<String> airlineList =  buyTicketReportMapper.getAirLine(buyTicketReportQuery);
			if(airlineList!=null&&airlineList.size()>0){
				for (String string : airlineList) {
					if(string.length()>6){
						strList.add(outPortMapper.getairportNameByCode(string.substring(0,3))+"="+outPortMapper.getairportNameByCode(string.substring(3,6))+"="+outPortMapper.getairportNameByCode(string.substring(6,9)));
					}else{
						strList.add(outPortMapper.getairportNameByCode(string.substring(0,3))+"="+outPortMapper.getairportNameByCode(string.substring(3,6)));
					}
				}
			}
		}
		return strList;
	}

	/* (non-Javadoc)
	 * @see org.ldd.ssm.crm.service.BuyTicketReportService#getBuyTicketReportLineDataNewDate(org.ldd.ssm.crm.query.BuyTicketReportQuery)
	 */
	@Override
	public String getBuyTicketReportLineDataNewDate(BuyTicketReportQuery buyTicketReportQuery) {
		String str = buyTicketReportMapper.getNewDate(buyTicketReportQuery);
		return str;
	}
	
	
}
