package org.ldd.ssm.crm.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.crm.domain.AirLineAllInfo;
import org.ldd.ssm.crm.domain.Airdiscount;
import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.EveryDuanInfo;
import org.ldd.ssm.crm.domain.FltDyDetial;
import org.ldd.ssm.crm.domain.OtherSalesReport;
import org.ldd.ssm.crm.domain.SalesReport;
import org.ldd.ssm.crm.domain.SalesReportDayInfo;
import org.ldd.ssm.crm.domain.YearSalesReport;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.FormulaUtilMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.mapper.SalesReportServiceMapper;
import org.ldd.ssm.crm.query.FlyNum;
import org.ldd.ssm.crm.query.FormulaUtilQuery;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.service.SalesReportService;
import org.ldd.ssm.crm.utils.FormulaUtil;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
/**
 * 销售报表实现
 * @Title:SalesReportServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 上午10:23:27
 */
@Service
public class SalesReportServiceImpl implements SalesReportService {
	@Autowired
	private SalesReportServiceMapper salesReportServiceMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	@Autowired
	private FormulaUtilMapper formulaUtilMapper;
	@Autowired
	private FormulaUtil formulaUtil;
	
	
	public List<SalesReport> getDailyReportList(SalesReportQuery salesReportQuery) {
		List<SalesReport> salesReportList = new ArrayList<SalesReport>();
		String dpt_AirPt_CdName = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdName = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpName = salesReportQuery.getPas_stp();
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(dpt_AirPt_CdName);
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(arrv_Airpt_CdName);
		String pas_stpCode = outPortMapper.getAirCodeByName(pas_stpName);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setPas_stp(pas_stpCode);
		if(TextUtil.isEmpty(pas_stpCode)){
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+arrv_Airpt_CdCode);
			salesReportQuery.setFlt_Rte_Cd2(arrv_Airpt_CdCode+dpt_AirPt_CdCode);
		}else{
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+pas_stpCode+arrv_Airpt_CdCode);
			salesReportQuery.setFlt_Rte_Cd2(arrv_Airpt_CdCode+pas_stpCode+dpt_AirPt_CdCode);
		}
		DecimalFormat  df   =new   DecimalFormat("0.00");
		List<SalesReport> salesReportAll = salesReportServiceMapper.getDailyReportListNew(salesReportQuery);
		if(TextUtil.isEmpty(pas_stpName)){
			salesReportList.add(getSalesReport(salesReportQuery,dpt_AirPt_CdCode,arrv_Airpt_CdCode,dpt_AirPt_CdName,arrv_Airpt_CdName,salesReportAll));
			//返程
			salesReportList.add(getSalesReport(salesReportQuery,arrv_Airpt_CdCode,dpt_AirPt_CdCode,arrv_Airpt_CdName,dpt_AirPt_CdName,salesReportAll));
		}else{
			List<SalesReport> salesReportListAll = salesReportServiceMapper.getDataByFlyNumList(salesReportQuery);
			//座公里经停计算,先得到航班号，根据航班号来查询对应数据。
			SalesReportQuery salesReportQueryTemp = new SalesReportQuery();
			salesReportQueryTemp = salesReportQuery;
			if(salesReportQuery.getFlt_nbr_Count()!=null){
//				//长段数据
				SalesReport salesReportC = null;
				for (SalesReport salesReport : salesReportListAll) {
					String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
					String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
					String flt_Nbr = salesReport.getFlt_Nbr();
					if(dpt_AirPt_Cd.equals(dpt_AirPt_CdCode)&&arrv_Airpt_Cd.equals(arrv_Airpt_CdCode)&&flt_Nbr.equals(salesReportQuery.getGoNum())){
						salesReportC = salesReport;
					}
				}
				double zysC = Double.parseDouble((salesReportC==null||salesReportC.getHbys()==null)?"0.00":salesReportC.getHbys());
				double siteC = Double.parseDouble(salesReportC==null||salesReportC.getCount_set()==null?"0.00":salesReportC.getCount_set());
				double egs_Lod_FtsC = Double.parseDouble(salesReportC==null||salesReportC.getEgs_Lod_Fts()==null?"0.00":salesReportC.getEgs_Lod_Fts());
				//短段数据1
				SalesReport salesReportD = null;
				for (SalesReport salesReport : salesReportListAll) {
					String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
					String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
					String flt_Nbr = salesReport.getFlt_Nbr();
					if(dpt_AirPt_Cd.equals(dpt_AirPt_CdCode)&&arrv_Airpt_Cd.equals(pas_stpCode)&&flt_Nbr.equals(salesReportQuery.getGoNum())){
						salesReportD = salesReport;
					}
				}
				double zysD1 = Double.parseDouble(salesReportD==null||salesReportD.getHbys()==null?"0.00":salesReportD.getHbys());
				double hangjuD1 = Double.parseDouble(salesReportD==null||salesReportD.getHangju()==null?"0.00":salesReportD.getHangju());
				double egs_Lod_FtsD1 = Double.parseDouble(salesReportD==null||salesReportD.getEgs_Lod_Fts()==null?"0.00":salesReportD.getEgs_Lod_Fts());
				//短段数据2
				SalesReport salesReportD2 = null;
				for (SalesReport salesReport : salesReportListAll) {
					String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
					String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
					String flt_Nbr = salesReport.getFlt_Nbr();
					if(dpt_AirPt_Cd.equals(pas_stpCode)&&arrv_Airpt_Cd.equals(arrv_Airpt_CdCode)&&flt_Nbr.equals(salesReportQuery.getGoNum())){
						salesReportD2 = salesReport;
					}
				}
				double zysD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getHbys()==null?"0.00":salesReportD2.getHbys());
				double hangjuD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getHangju()==null?"0.00":salesReportD2.getHangju());
				double egs_Lod_FtsD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getEgs_Lod_Fts()==null?"0.00":salesReportD2.getEgs_Lod_Fts());
				//座公里收入
				String zglsr = df.format((zysC + zysD1 + zysD2)/siteC/(hangjuD1+hangjuD2));
				//平均客座率
				String pjkzl = df.format((egs_Lod_FtsC+egs_Lod_FtsD1+egs_Lod_FtsD2)/3);
				SalesReport salesReport1 = getSalesReport(salesReportQuery,dpt_AirPt_CdCode,pas_stpCode,dpt_AirPt_CdName,pas_stpName,salesReportAll);
				salesReport1.setSet_Ktr_Ine(zglsr);
				salesReport1.setPjkzl(pjkzl);
				SalesReport salesReport2 = getSalesReport(salesReportQuery,dpt_AirPt_CdCode,arrv_Airpt_CdCode,dpt_AirPt_CdName,arrv_Airpt_CdName,salesReportAll);
				salesReport2.setSet_Ktr_Ine(zglsr);
				salesReport2.setPjkzl(pjkzl);
				SalesReport salesReport3 = getSalesReport(salesReportQuery,pas_stpCode,arrv_Airpt_CdCode,pas_stpName,arrv_Airpt_CdName,salesReportAll);
				salesReport3.setSet_Ktr_Ine(zglsr);
				salesReport3.setPjkzl(pjkzl);
				salesReportList.add(salesReport1);
				salesReportList.add(salesReport2);
				salesReportList.add(salesReport3);
				//返程数据						 
//				//长段数据
				SalesReport salesReportCH = null;
				for (SalesReport salesReport : salesReportListAll) {
					String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
					String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
					String flt_Nbr = salesReport.getFlt_Nbr();
					if(dpt_AirPt_Cd.equals(arrv_Airpt_CdCode)&&arrv_Airpt_Cd.equals(dpt_AirPt_CdCode)&&flt_Nbr.equals(salesReportQuery.getHuiNum())){
						salesReportCH = salesReport;
					}
				}
				double zysCH = Double.parseDouble(salesReportCH==null||salesReportCH.getHbys()==null?"0.00":salesReportCH.getHbys());
				double siteCH = Double.parseDouble(salesReportCH==null||salesReportCH.getCount_set()==null?"0.00":salesReportCH.getCount_set());
				double egs_Lod_FtsCH = Double.parseDouble(salesReportCH==null||salesReportCH.getEgs_Lod_Fts()==null?"0.00":salesReportCH.getEgs_Lod_Fts());
				//短段数据1
				SalesReport salesReportDH = null;
				for (SalesReport salesReport : salesReportListAll) {
					String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
					String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
					String flt_Nbr = salesReport.getFlt_Nbr();
					if(dpt_AirPt_Cd.equals(arrv_Airpt_CdCode)&&arrv_Airpt_Cd.equals(pas_stpCode)&&flt_Nbr.equals(salesReportQuery.getHuiNum())){
						salesReportDH = salesReport;
					}
				}
				double zysD1H = Double.parseDouble(salesReportDH==null||salesReportDH.getHbys()==null?"0.00":salesReportDH.getHbys());
				double hangjuD1H = Double.parseDouble(salesReportDH==null||salesReportDH.getHangju()==null?"0.00":salesReportDH.getHangju());
				double egs_Lod_FtsD1H = Double.parseDouble(salesReportDH==null||salesReportDH.getEgs_Lod_Fts()==null?"0.00":salesReportDH.getEgs_Lod_Fts());
				//短段数据2
				SalesReport salesReportD2H = null;
				for (SalesReport salesReport : salesReportListAll) {
					String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
					String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
					String flt_Nbr = salesReport.getFlt_Nbr();
					if(dpt_AirPt_Cd.equals(pas_stpCode)&&arrv_Airpt_Cd.equals(dpt_AirPt_CdCode)&&flt_Nbr.equals(salesReportQuery.getHuiNum())){
						salesReportD2H = salesReport;
					}
				}
				double zysD2H = Double.parseDouble(salesReportD2H==null||salesReportD2H.getHbys()==null?"0.00":salesReportD2H.getHbys());
				double hangjuD2H = Double.parseDouble(salesReportD2H==null||salesReportD2H.getHangju()==null?"0.00":salesReportD2H.getHangju());
				double egs_Lod_FtsD2H = Double.parseDouble(salesReportD2H==null||salesReportD2H.getEgs_Lod_Fts()==null?"0.00":salesReportD2H.getEgs_Lod_Fts());
				//座公里收入
				String zglsrH = df.format((zysCH + zysD1H + zysD2H)/siteCH/(hangjuD1H+hangjuD2H));
				//平均客座率
				String pjkzlH = df.format((egs_Lod_FtsCH+egs_Lod_FtsD1H+egs_Lod_FtsD2H)/3);
				SalesReport salesReport4 = getSalesReport(salesReportQuery,arrv_Airpt_CdCode,pas_stpCode,arrv_Airpt_CdName,pas_stpName,salesReportAll);
				salesReport4.setSet_Ktr_Ine(zglsrH);
				salesReport4.setPjkzl(pjkzlH);
				SalesReport salesReport5 = getSalesReport(salesReportQuery,arrv_Airpt_CdCode,dpt_AirPt_CdCode,arrv_Airpt_CdName,dpt_AirPt_CdName,salesReportAll);
				salesReport5.setSet_Ktr_Ine(zglsrH);
				salesReport5.setPjkzl(pjkzlH);
				SalesReport salesReport6 = getSalesReport(salesReportQuery,pas_stpCode,dpt_AirPt_CdCode,pas_stpName,dpt_AirPt_CdName,salesReportAll);
				salesReport6.setSet_Ktr_Ine(zglsrH);
				salesReport6.setPjkzl(pjkzlH);
				salesReportList.add(salesReport4);
				salesReportList.add(salesReport5);
				salesReportList.add(salesReport6);
			}else{
				//
				//得到所有航班号
				SalesReportQuery salesReportQuery1 = salesReportQuery;
				salesReportQuery1.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
				salesReportQuery1.setPas_stp(pas_stpCode);
				salesReportQuery1.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
				List<Z_Airdata> FlyNumAll = salesReportServiceMapper.getHbhAll(salesReportQuery1);
				List<String> flyNumList1 = new ArrayList<String>();
				List<String> flyNumList2 = new ArrayList<String>();
				List<String> flyNumList3 = new ArrayList<String>();
				List<String> flyNumList6 = new ArrayList<String>();
				List<String> flyNumList4 = new ArrayList<String>();
				List<String> flyNumList5 = new ArrayList<String>();
				for (Z_Airdata zairdata : FlyNumAll) {
					if(dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd())){
						flyNumList1.add(zairdata.getFlt_Nbr());
					}
					if(dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd())){
						flyNumList2.add(zairdata.getFlt_Nbr());
					}
					if(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd())){
						flyNumList3.add(zairdata.getFlt_Nbr());
					}
					if(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd())){
						flyNumList6.add(zairdata.getFlt_Nbr());
					}
					if(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd())){
						flyNumList4.add(zairdata.getFlt_Nbr());
					}
					if(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd())){
						flyNumList5.add(zairdata.getFlt_Nbr());
					}
				}
				Double zglsrAll = 0.00;
				Double pjkzlAll = 0.00;
				if(flyNumList1!=null&&flyNumList1.size()>0&&flyNumList2!=null&&flyNumList2.size()>0&&flyNumList3!=null&&flyNumList3.size()>0){
				for (String flyNum : flyNumList1) {
					salesReportQueryTemp.setGoNum(flyNum);
					//长段数据
					SalesReport salesReportC = null;
					for (SalesReport salesReport : salesReportListAll) {
						String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
						String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
						String flt_Nbr = salesReport.getFlt_Nbr();
						if(dpt_AirPt_Cd.equals(dpt_AirPt_CdCode)&&arrv_Airpt_Cd.equals(arrv_Airpt_CdCode)&&flt_Nbr.equals(flyNum)){
							salesReportC = salesReport;
						}
					}
					double zysC = Double.parseDouble((salesReportC==null||salesReportC.getHbys()==null)?"0.00":salesReportC.getHbys());
					double siteC = Double.parseDouble(salesReportC==null||salesReportC.getCount_set()==null?"0.00":salesReportC.getCount_set());
					double egs_Lod_FtsC = Double.parseDouble(salesReportC==null||salesReportC.getEgs_Lod_Fts()==null?"0.00":salesReportC.getEgs_Lod_Fts());
					//短段数据1
					SalesReport salesReportD = null;
					for (SalesReport salesReport : salesReportListAll) {
						String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
						String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
						String flt_Nbr = salesReport.getFlt_Nbr();
						if(dpt_AirPt_Cd.equals(dpt_AirPt_CdCode)&&arrv_Airpt_Cd.equals(pas_stpCode)&&flt_Nbr.equals(flyNum)){
							salesReportD = salesReport;
						}
					}
					double zysD1 = Double.parseDouble(salesReportD==null||salesReportD.getHbys()==null?"0.00":salesReportD.getHbys());
					double hangjuD1 = Double.parseDouble(salesReportD==null||salesReportD.getHangju()==null?"0.00":salesReportD.getHangju());
					double egs_Lod_FtsD1 = Double.parseDouble(salesReportD==null||salesReportD.getEgs_Lod_Fts()==null?"0.00":salesReportD.getEgs_Lod_Fts());
					//短段数据2
					SalesReport salesReportD2 = null;
					for (SalesReport salesReport : salesReportListAll) {
						String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
						String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
						String flt_Nbr = salesReport.getFlt_Nbr();
						if(dpt_AirPt_Cd.equals(pas_stpCode)&&arrv_Airpt_Cd.equals(arrv_Airpt_CdCode)&&flt_Nbr.equals(flyNum)){
							salesReportD2 = salesReport;
						}
					}
					double zysD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getHbys()==null?"0.00":salesReportD2.getHbys());
					double hangjuD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getHangju()==null?"0.00":salesReportD2.getHangju());
					double egs_Lod_FtsD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getEgs_Lod_Fts()==null?"0.00":salesReportD2.getEgs_Lod_Fts());
					//座公里收入
					double zglsr = (zysC + zysD1 + zysD2)/siteC/(hangjuD1+hangjuD2);
					//平均客座率
					double pjkzl = (egs_Lod_FtsC+egs_Lod_FtsD1+egs_Lod_FtsD2)/3;
					zglsrAll = zglsrAll + zglsr;
					pjkzlAll = pjkzlAll + pjkzl;
				}
				String zglsr = df.format(zglsrAll/flyNumList1.size());
				String pjkzl = df.format(pjkzlAll/flyNumList1.size());
				SalesReport salesReport1 = getSalesReport(salesReportQuery,dpt_AirPt_CdCode,pas_stpCode,dpt_AirPt_CdName,pas_stpName,salesReportAll);
				salesReport1.setSet_Ktr_Ine(zglsr);
				salesReport1.setPjkzl(pjkzl);
				SalesReport salesReport2 = getSalesReport(salesReportQuery,dpt_AirPt_CdCode,arrv_Airpt_CdCode,dpt_AirPt_CdName,arrv_Airpt_CdName,salesReportAll);
				salesReport2.setSet_Ktr_Ine(zglsr);
				salesReport2.setPjkzl(pjkzl);
				SalesReport salesReport3 = getSalesReport(salesReportQuery,pas_stpCode,arrv_Airpt_CdCode,pas_stpName,arrv_Airpt_CdName,salesReportAll);
				salesReport3.setSet_Ktr_Ine(zglsr);
				salesReport3.setPjkzl(pjkzl);
				salesReportList.add(salesReport1);
				salesReportList.add(salesReport2);
				salesReportList.add(salesReport3);
				}else{
					SalesReport salesReport1 = getSalesReport(salesReportQuery,dpt_AirPt_CdCode,pas_stpCode,dpt_AirPt_CdName,pas_stpName,salesReportAll);
					SalesReport salesReport2 = getSalesReport(salesReportQuery,dpt_AirPt_CdCode,arrv_Airpt_CdCode,dpt_AirPt_CdName,arrv_Airpt_CdName,salesReportAll);
					SalesReport salesReport3 = getSalesReport(salesReportQuery,pas_stpCode,arrv_Airpt_CdCode,pas_stpName,arrv_Airpt_CdName,salesReportAll);
					salesReportList.add(salesReport1);
					salesReportList.add(salesReport2);
					salesReportList.add(salesReport3);
				}
				
				Double zglsrAllH = 0.00;
				Double pjkzlAllH = 0.00;
				if(flyNumList6!=null&&flyNumList6.size()>0&&flyNumList4!=null&&flyNumList4.size()>0&&flyNumList5!=null&&flyNumList5.size()>0){
					for (String flyNum : flyNumList6) {
//						//长段数据
						SalesReport salesReportC = null;
						for (SalesReport salesReport : salesReportListAll) {
							String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
							String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
							String flt_Nbr = salesReport.getFlt_Nbr();
							if(dpt_AirPt_Cd.equals(arrv_Airpt_CdCode)&&arrv_Airpt_Cd.equals(dpt_AirPt_CdCode)&&flt_Nbr.equals(flyNum)){
								salesReportC = salesReport;
							}
						}
						double zysC = Double.parseDouble((salesReportC==null||salesReportC.getHbys()==null)?"0.00":salesReportC.getHbys());
						double siteC = Double.parseDouble(salesReportC==null||salesReportC.getCount_set()==null?"0.00":salesReportC.getCount_set());
						double egs_Lod_FtsC = Double.parseDouble(salesReportC==null||salesReportC.getEgs_Lod_Fts()==null?"0.00":salesReportC.getEgs_Lod_Fts());
						//短段数据1
						SalesReport salesReportD = null;
						for (SalesReport salesReport : salesReportListAll) {
							String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
							String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
							String flt_Nbr = salesReport.getFlt_Nbr();
							if(dpt_AirPt_Cd.equals(arrv_Airpt_CdCode)&&arrv_Airpt_Cd.equals(pas_stpCode)&&flt_Nbr.equals(flyNum)){
								salesReportD = salesReport;
							}
						}
						
						double zysD1 = Double.parseDouble(salesReportD==null||salesReportD.getHbys()==null?"0.00":salesReportD.getHbys());
						double hangjuD1 = Double.parseDouble(salesReportD==null||salesReportD.getHangju()==null?"0.00":salesReportD.getHangju());
						double egs_Lod_FtsD1 = Double.parseDouble(salesReportD==null||salesReportD.getEgs_Lod_Fts()==null?"0.00":salesReportD.getEgs_Lod_Fts());
						//短段数据2
						SalesReport salesReportD2 = null;
						for (SalesReport salesReport : salesReportListAll) {
							String dpt_AirPt_Cd = salesReport.getDpt_AirPt_Cd();
							String arrv_Airpt_Cd = salesReport.getArrv_Airpt_Cd();
							String flt_Nbr = salesReport.getFlt_Nbr();
							if(dpt_AirPt_Cd.equals(pas_stpCode)&&arrv_Airpt_Cd.equals(dpt_AirPt_CdCode)&&flt_Nbr.equals(flyNum)){
								salesReportD2 = salesReport;
							}
						}
						double zysD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getHbys()==null?"0.00":salesReportD2.getHbys());
						double hangjuD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getHangju()==null?"0.00":salesReportD2.getHangju());
						double egs_Lod_FtsD2 = Double.parseDouble(salesReportD2==null||salesReportD2.getEgs_Lod_Fts()==null?"0.00":salesReportD2.getEgs_Lod_Fts());
						//座公里收入
						double zglsrH = (zysC + zysD1 + zysD2)/siteC/(hangjuD1+hangjuD2);
						//平均客座率
						double pjkzlH = (egs_Lod_FtsC+egs_Lod_FtsD1+egs_Lod_FtsD2)/3;
						zglsrAllH = zglsrAllH + zglsrH;
						pjkzlAllH = pjkzlAllH + pjkzlH;
					}
					String zglsrH = df.format(zglsrAllH/flyNumList6.size());
					String pjkzlH = df.format(pjkzlAllH/flyNumList6.size());
					SalesReport salesReport4 = getSalesReport(salesReportQuery,arrv_Airpt_CdCode,pas_stpCode,arrv_Airpt_CdName,pas_stpName,salesReportAll);
					salesReport4.setSet_Ktr_Ine(zglsrH);
					salesReport4.setPjkzl(pjkzlH);
					SalesReport salesReport5 = getSalesReport(salesReportQuery,arrv_Airpt_CdCode,dpt_AirPt_CdCode,arrv_Airpt_CdName,dpt_AirPt_CdName,salesReportAll);
					salesReport5.setSet_Ktr_Ine(zglsrH);
					salesReport5.setPjkzl(pjkzlH);
					SalesReport salesReport6 = getSalesReport(salesReportQuery,pas_stpCode,dpt_AirPt_CdCode,pas_stpName,dpt_AirPt_CdName,salesReportAll);
					salesReport6.setSet_Ktr_Ine(zglsrH);
					salesReport6.setPjkzl(pjkzlH);
					salesReportList.add(salesReport4);
					salesReportList.add(salesReport5);
					salesReportList.add(salesReport6);
				}else{
					SalesReport salesReport4 = getSalesReport(salesReportQuery,arrv_Airpt_CdCode,pas_stpCode,arrv_Airpt_CdName,pas_stpName,salesReportAll);
					SalesReport salesReport5 = getSalesReport(salesReportQuery,arrv_Airpt_CdCode,dpt_AirPt_CdCode,arrv_Airpt_CdName,dpt_AirPt_CdName,salesReportAll);
					SalesReport salesReport6 = getSalesReport(salesReportQuery,pas_stpCode,dpt_AirPt_CdCode,pas_stpName,dpt_AirPt_CdName,salesReportAll);
					salesReportList.add(salesReport4);
					salesReportList.add(salesReport5);
					salesReportList.add(salesReport6);
				}
			}
		}
		if(salesReportList!=null&&salesReportList.size()>0){
			return heJiData(salesReportList,salesReportQuery,dpt_AirPt_CdCode,arrv_Airpt_CdCode,pas_stpCode);
		}else{
			return salesReportList;
		}
		
	}
	//得到日报
	public SalesReport getSalesReport(SalesReportQuery salesReportQuery,String dpt_AirPt_CdCode,String arrv_Airpt_CdCode,String dpt_AirPt_CdName,String arrv_Airpt_CdName,List<SalesReport> salesReportAll){
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		SalesReport salesReport = null;
		for (SalesReport salesReport2 : salesReportAll) {
			String dpt_AirPt_Cd = salesReport2.getDpt_AirPt_Cd();
			String arrv_Airpt_Cd = salesReport2.getArrv_Airpt_Cd();
			if(dpt_AirPt_Cd.equals(dpt_AirPt_CdCode)&&arrv_Airpt_Cd.equals(arrv_Airpt_CdCode)){
				salesReport = salesReport2;
			}
		}
		if(salesReport==null){
			salesReport = new SalesReport();
		}
		salesReport.setAirPort(dpt_AirPt_CdName+"-"+arrv_Airpt_CdName); 
		salesReport.setDate(salesReportQuery.getDay());
		return salesReport;
	}
	
	public List<FlyNum> getHbh(SalesReportQuery salesReportQuery) {
		if(TextUtil.isEmpty(salesReportQuery.getIsIncludePasDpt())){
			salesReportQuery.setIsIncludePasDpt(null);
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		List<FlyNum> FlyNums = salesReportServiceMapper.getHbh(salesReportQuery);
		return FlyNums;
	}
	public Map<String, List<OtherSalesReport>> getWeeklyReportData(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdName = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdName = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpName = salesReportQuery.getPas_stp();
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(dpt_AirPt_CdName);
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(arrv_Airpt_CdName);
		String pas_stpCode = outPortMapper.getAirCodeByName(pas_stpName);
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		salesReportQuery.setPas_stp(pas_stpCode);
		String [] str = salesReportQuery.getWeekly().split("-");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, List<OtherSalesReport>> retMap = new HashMap<String, List<OtherSalesReport>>();
		Map<String, List<String>>  dateMap = TextUtil.getWeekOfMonth(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
		Iterator it = dateMap.entrySet().iterator();
		salesReportQuery.setStartTime(Integer.parseInt(str[0])+"-"+Integer.parseInt(str[1])+"-01");
		salesReportQuery.setEndTime(Integer.parseInt(str[0])+"-"+Integer.parseInt(str[1])+"-31");
		List<Z_Airdata> zairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		  while (it.hasNext()) {
		   Entry entry = (Entry) it.next();
		   String key = (String) entry.getKey();
		   List<String> value = (List<String>) entry.getValue();
		   List<OtherSalesReport> otherSalesReportList = new ArrayList<OtherSalesReport>();
		   for (String day : value) {
			   salesReportQuery.setStartTime(day);
			   salesReportQuery.setEndTime(day);
			   OtherSalesReport otherSalesReport = getOtherSalesReportt(salesReportQuery,zairdataListAll);
			  if(otherSalesReport!=null){
				 try {
					otherSalesReport.setDate(day+"<br/>"+TextUtil.getWeekOfDate(df.parse(day)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				 otherSalesReport.setWeekly("第"+key+"周");
				 otherSalesReportList.add(otherSalesReport);
			  }
		   }
		   retMap.put(key, getWeeklyHeJi(otherSalesReportList,salesReportQuery));
		  }
		return retMap;
	}
	
	public List<OtherSalesReport> getMonthlyReportData(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdName = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdName = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpName = salesReportQuery.getPas_stp();
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(dpt_AirPt_CdName);
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(arrv_Airpt_CdName);
		String pas_stpCode = outPortMapper.getAirCodeByName(pas_stpName);
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		salesReportQuery.setPas_stp(pas_stpCode);
		String montht = salesReportQuery.getMonth();
		String [] datee = montht.split("-");
		Calendar cal = Calendar.getInstance();
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		cal.set(Calendar.YEAR,Integer.parseInt(datee[0])) ;
		cal.set(Calendar.MONTH, Integer.parseInt(datee[1]));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();//当前月最后一天
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = df.format(lastDate);
		String [] date = str.split("-");
		List<OtherSalesReport> otherSalesReportList = new ArrayList<OtherSalesReport>();
		salesReportQuery.setStartTime(Integer.parseInt(date[0])+"-"+Integer.parseInt(date[1])+"-01");
		salesReportQuery.setEndTime(Integer.parseInt(date[0])+"-"+Integer.parseInt(date[1])+"-31");
		List<Z_Airdata> zairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		for(int i = 1;i<=Integer.parseInt(date[2]);i++){
			salesReportQuery.setStartTime(datee[0]+"-"+datee[1]+"-"+i);
			salesReportQuery.setEndTime(datee[0]+"-"+datee[1]+"-"+i);
			OtherSalesReport otherSalesReport = getOtherSalesReportt(salesReportQuery,zairdataListAll);
			if(otherSalesReport!=null){
				otherSalesReport.setWeekly(datee[0]+"年"+datee[1]+"月");
				otherSalesReport.setDate(i+"号");
				otherSalesReportList.add(otherSalesReport);
			}
		}
		return getWeeklyHeJi(otherSalesReportList,salesReportQuery);
	}

	public List<List<YearSalesReport>> getHalfYearReportData(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdName = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdName = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpName = salesReportQuery.getPas_stp();
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(dpt_AirPt_CdName);
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(arrv_Airpt_CdName);
		String pas_stpCode = outPortMapper.getAirCodeByName(pas_stpName);
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		salesReportQuery.setPas_stp(pas_stpCode);
		List<List<YearSalesReport>> yearSalesReportListList = new ArrayList<List<YearSalesReport>>();
		int year = Integer.parseInt(salesReportQuery.getYear());
		//得到夏秋航季开始结束日期
		Map<String,String> timeMapXQ = getXQDate(year);
		Map<String,String> timeMapDC = getDCDate(year);
		List<YearSalesReport> yearSalesReportList = new ArrayList<YearSalesReport>();
		
		salesReportQuery.setStartTime(timeMapXQ.get("startTime"));
		salesReportQuery.setEndTime(timeMapDC.get("endTime"));
		List<Z_Airdata> zairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		
		
		salesReportQuery.setStartTime(timeMapXQ.get("startTime"));
		salesReportQuery.setEndTime(year+"-3"+"-31");
		YearSalesReport yearSalesReport1 = getYearSalesReportt(salesReportQuery,year,3,zairdataListAll);
		yearSalesReport1.setMonth("3月");
		yearSalesReport1.setQury("夏秋<br/>"+timeMapXQ.get("startTime")+"至"+timeMapXQ.get("endTime"));
		yearSalesReportList.add(yearSalesReport1);
		for(int i=4;i<10;i++){
			String str1 = year+"-"+i+"-"+"01";
			String str2 = year+"-"+i+"-"+"31";
			salesReportQuery.setStartTime(str1);
			salesReportQuery.setEndTime(str2);
			YearSalesReport yearSalesReport =  getYearSalesReportt(salesReportQuery,year,i,zairdataListAll);
			yearSalesReport.setMonth(i+"月");
			yearSalesReport.setQury("夏秋<br/>"+timeMapXQ.get("startTime")+"至"+timeMapXQ.get("endTime"));
			yearSalesReportList.add(yearSalesReport);
		}
		salesReportQuery.setStartTime(year+"-10"+"-1");
		salesReportQuery.setEndTime(timeMapXQ.get("endTime"));
		YearSalesReport yearSalesReport2 =  getYearSalesReportt(salesReportQuery,year,10,zairdataListAll);
		yearSalesReport2.setMonth("10月");
		yearSalesReport2.setQury("夏秋<br/>"+timeMapXQ.get("startTime")+"至"+timeMapXQ.get("endTime"));
		yearSalesReportList.add(yearSalesReport2);
		yearSalesReportListList.add(getYearlyHeJi(yearSalesReportList));
		
		//得到冬春航季开始结束日期
		
		List<YearSalesReport> yearSalesReportList2 = new ArrayList<YearSalesReport>();
		salesReportQuery.setStartTime(timeMapDC.get("startTime"));
		salesReportQuery.setEndTime(year+"-10"+"-31");
		YearSalesReport yearSalesReport3 =  getYearSalesReportt(salesReportQuery,year,10,zairdataListAll);
		yearSalesReport3.setMonth("10月");
		yearSalesReport3.setQury("冬春<br/>"+timeMapDC.get("startTime")+"至"+timeMapDC.get("endTime"));
		yearSalesReportList2.add(yearSalesReport3);
		for(int i=11;i<13;i++){
			String str1 = year+"-"+i+"-"+"01";
			String str2 = year+"-"+i+"-"+"31";
			salesReportQuery.setStartTime(str1);
			salesReportQuery.setEndTime(str2);
			YearSalesReport yearSalesReport =  getYearSalesReportt(salesReportQuery,year,i,zairdataListAll);
			yearSalesReport.setMonth(i+"月");
			yearSalesReport.setQury("冬春<br/>"+timeMapDC.get("startTime")+"至"+timeMapDC.get("endTime"));
			yearSalesReportList2.add(yearSalesReport);
		}
		for(int i=1;i<3;i++){
			String str1 = (year+1)+"-"+i+"-"+"01";
			String str2 = (year+1)+"-"+i+"-"+"31";
			salesReportQuery.setStartTime(str1);
			salesReportQuery.setEndTime(str2);
			YearSalesReport yearSalesReport =  getYearSalesReportt(salesReportQuery,(year+1),10,zairdataListAll);
			yearSalesReport.setMonth(i+"月");
			yearSalesReport.setQury("冬春<br/>"+timeMapDC.get("startTime")+"至"+timeMapDC.get("endTime"));
			yearSalesReportList2.add(yearSalesReport);
		}
		salesReportQuery.setStartTime((year+1)+"-3"+"-1");
		salesReportQuery.setEndTime(timeMapDC.get("endTime"));
		YearSalesReport yearSalesReport4 =  getYearSalesReportt(salesReportQuery,(year+1),3,zairdataListAll);
		yearSalesReport4.setMonth("3月");
		yearSalesReport4.setQury("冬春<br/>"+timeMapDC.get("startTime")+"至"+timeMapDC.get("endTime"));
		yearSalesReportList2.add(yearSalesReport4);
		yearSalesReportListList.add(getYearlyHeJi(yearSalesReportList2));
		return yearSalesReportListList;
	}
	
	public List<List<YearSalesReport>> getYearlyReportData(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdName = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdName = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpName = salesReportQuery.getPas_stp();
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(dpt_AirPt_CdName);
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(arrv_Airpt_CdName);
		String pas_stpCode = outPortMapper.getAirCodeByName(pas_stpName);
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		salesReportQuery.setPas_stp(pas_stpCode);
		List<List<YearSalesReport>> yearSalesReportListList = new ArrayList<List<YearSalesReport>>();
		int year = Integer.parseInt(salesReportQuery.getYear());
		salesReportQuery.setStartTime((year-2)+"-01"+"01");
		salesReportQuery.setEndTime(year+"-12"+"31");
		List<Z_Airdata> zairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		for(int i = year;i>=year-2;i--){
			List<YearSalesReport> yearSalesReportList = new ArrayList<YearSalesReport>();
			for(int month = 1;month<13;month++){
				String startTime = i+"-"+month+"-"+"01";
				int maxDay = TextUtil.getMaxDayOfMonth(startTime);
				String endTime = i+"-"+month+"-"+maxDay;
				salesReportQuery.setStartTime(startTime);
				salesReportQuery.setEndTime(endTime);
				yearSalesReportList.add(getYearSalesReportt(salesReportQuery,i,month,zairdataListAll));
			}
			yearSalesReportListList.add(getYearlyHeJi(yearSalesReportList));
		}
		return yearSalesReportListList;
	}
	
	
	//向日报添加合计数据
	public List<SalesReport> heJiData(List<SalesReport> salesReportList,SalesReportQuery salesReportQuery,String dpt_AirPt_CdCode, String arrv_Airpt_CdCode, String pas_stpCode ){
		SalesReport SalesReportHeJi = new SalesReport();
		String flyTime = salesReportQuery.getFlyTime();
		String hourPrice = salesReportQuery.getHourPrice();
		salesReportQuery.setPas_stp(pas_stpCode);
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		String dpt_AirPt_CdName = outPortMapper.getNameByCode(dpt_AirPt_CdCode);
		String arrv_Airpt_CdName = outPortMapper.getNameByCode(arrv_Airpt_CdCode);
		String pas_stpName = outPortMapper.getNameByCode(pas_stpCode);
		DecimalFormat   df   =new   DecimalFormat("0.00");
		double salesAll = 0.0 ;
		double pjkzl = 0.0 ;
		String site_num = "1";
		String date = null;
		for (SalesReport salesReport : salesReportList) {
			SalesReportHeJi.setAirPort("合计");
			SalesReportHeJi.setyPrice("");
			date = salesReport.getDate();
			salesAll = salesAll +Double.parseDouble(salesReport.getyPrice()==null?"0":salesReport.getyPrice())*Double.parseDouble(salesReport.getPgs_Per_Cls()==null?"0":salesReport.getPgs_Per_Cls());
			pjkzl = pjkzl +	Double.parseDouble(salesReport.getPjkzl()==null?"0":salesReport.getPjkzl());
			SalesReportHeJi.setCutPriceTeam("");
			SalesReportHeJi.setTal_Nbr_Set(Double.parseDouble(salesReport.getTal_Nbr_Set()==null?"0":salesReport.getTal_Nbr_Set())+Double.parseDouble(SalesReportHeJi.getTal_Nbr_Set()==null?"0":SalesReportHeJi.getTal_Nbr_Set())+"");
			SalesReportHeJi.setTwo_Tak_Ppt(Double.parseDouble(salesReport.getTwo_Tak_Ppt()==null?"0":salesReport.getTwo_Tak_Ppt())+Double.parseDouble(SalesReportHeJi.getTwo_Tak_Ppt()==null?"0":SalesReportHeJi.getTwo_Tak_Ppt())+"");
			SalesReportHeJi.setFul_Pce_Ppt(Double.parseDouble(salesReport.getFul_Pce_Ppt()==null?"0":salesReport.getFul_Pce_Ppt())+Double.parseDouble(SalesReportHeJi.getFul_Pce_Ppt()==null?"0":SalesReportHeJi.getFul_Pce_Ppt())+"");
			SalesReportHeJi.setNne_Dnt_Ppt(Double.parseDouble(salesReport.getNne_Dnt_Ppt()==null?"0":salesReport.getNne_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getNne_Dnt_Ppt()==null?"0":SalesReportHeJi.getNne_Dnt_Ppt())+"");
			SalesReportHeJi.setEht_Five_Dnt_Ppt(Double.parseDouble(salesReport.getEht_Five_Dnt_Ppt()==null?"0":salesReport.getEht_Five_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getEht_Five_Dnt_Ppt()==null?"0":SalesReportHeJi.getEht_Five_Dnt_Ppt())+"");
			SalesReportHeJi.setEht_Dnt_Ppt(Double.parseDouble(salesReport.getEht_Dnt_Ppt()==null?"0":salesReport.getEht_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getEht_Dnt_Ppt()==null?"0":SalesReportHeJi.getEht_Dnt_Ppt())+"");
			SalesReportHeJi.setSen_Five_Dnt_Ppt(Double.parseDouble(salesReport.getSen_Five_Dnt_Ppt()==null?"0":salesReport.getSen_Five_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getSen_Five_Dnt_Ppt()==null?"0":SalesReportHeJi.getSen_Five_Dnt_Ppt())+"");
			SalesReportHeJi.setSen_Dnt_Ppt(Double.parseDouble(salesReport.getSen_Dnt_Ppt()==null?"0":salesReport.getSen_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getSen_Dnt_Ppt()==null?"0":SalesReportHeJi.getSen_Dnt_Ppt())+"");
			SalesReportHeJi.setSix_Five_Dnt_Ppt(Double.parseDouble(salesReport.getSix_Five_Dnt_Ppt()==null?"0":salesReport.getSix_Five_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getSix_Five_Dnt_Ppt()==null?"0":SalesReportHeJi.getSix_Five_Dnt_Ppt())+"");
			SalesReportHeJi.setSix_Dnt_Ppt(Double.parseDouble(salesReport.getSix_Dnt_Ppt()==null?"0":salesReport.getSix_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getSix_Dnt_Ppt()==null?"0":SalesReportHeJi.getSix_Dnt_Ppt())+"");
			SalesReportHeJi.setFve_Fve_Dnt_Ppt(Double.parseDouble(salesReport.getFve_Fve_Dnt_Ppt()==null?"0":salesReport.getFve_Fve_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getFve_Fve_Dnt_Ppt()==null?"0":SalesReportHeJi.getFve_Fve_Dnt_Ppt())+"");
			SalesReportHeJi.setFve_Dnt_Ppt(Double.parseDouble(salesReport.getFve_Dnt_Ppt()==null?"0":salesReport.getFve_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getFve_Dnt_Ppt()==null?"0":SalesReportHeJi.getFve_Dnt_Ppt())+"");
			SalesReportHeJi.setFur_Fve_Dnt_Ppt(Double.parseDouble(salesReport.getFur_Fve_Dnt_Ppt()==null?"0":salesReport.getFur_Fve_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getFur_Fve_Dnt_Ppt()==null?"0":SalesReportHeJi.getFur_Fve_Dnt_Ppt())+"");
			SalesReportHeJi.setFur_Dnt_Ppt(Double.parseDouble(salesReport.getFur_Dnt_Ppt()==null?"0":salesReport.getFur_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getFur_Dnt_Ppt()==null?"0":SalesReportHeJi.getFur_Dnt_Ppt())+"");
			SalesReportHeJi.setThr_Fve_Dnt_Ppt(Double.parseDouble(salesReport.getThr_Fve_Dnt_Ppt()==null?"0":salesReport.getThr_Fve_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getThr_Fve_Dnt_Ppt()==null?"0":SalesReportHeJi.getThr_Fve_Dnt_Ppt())+"");
			SalesReportHeJi.setThr_Dnt_Ppt(Double.parseDouble(salesReport.getThr_Dnt_Ppt()==null?"0":salesReport.getThr_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getThr_Dnt_Ppt()==null?"0":SalesReportHeJi.getThr_Dnt_Ppt())+"");
			SalesReportHeJi.setTwo_Fve_Dnt_Ppt(Double.parseDouble(salesReport.getTwo_Fve_Dnt_Ppt()==null?"0":salesReport.getTwo_Fve_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getTwo_Fve_Dnt_Ppt()==null?"0":SalesReportHeJi.getTwo_Fve_Dnt_Ppt())+"");
			SalesReportHeJi.setTwo_Dnt_Ppt(Double.parseDouble(salesReport.getTwo_Dnt_Ppt()==null?"0":salesReport.getTwo_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getTwo_Dnt_Ppt()==null?"0":SalesReportHeJi.getTwo_Dnt_Ppt())+"");
			SalesReportHeJi.setOne_Fve_Dnt_Ppt(Double.parseDouble(salesReport.getOne_Fve_Dnt_Ppt()==null?"0":salesReport.getOne_Fve_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getOne_Fve_Dnt_Ppt()==null?"0":SalesReportHeJi.getOne_Fve_Dnt_Ppt())+"");
			SalesReportHeJi.setOne_Dnt_Ppt(Double.parseDouble(salesReport.getOne_Dnt_Ppt()==null?"0":salesReport.getOne_Dnt_Ppt())+Double.parseDouble(SalesReportHeJi.getOne_Dnt_Ppt()==null?"0":SalesReportHeJi.getOne_Dnt_Ppt())+"");
			SalesReportHeJi.setGrp_Nbr(Double.parseDouble(salesReport.getGrp_Nbr()==null?"0":salesReport.getGrp_Nbr())+Double.parseDouble(SalesReportHeJi.getGrp_Nbr()==null?"0":SalesReportHeJi.getGrp_Nbr())+"");
			SalesReportHeJi.setSal_Tak_Ppt(Double.parseDouble(salesReport.getSal_Tak_Ppt()==null?"0":salesReport.getSal_Tak_Ppt())+Double.parseDouble(SalesReportHeJi.getSal_Tak_Ppt()==null?"0":SalesReportHeJi.getSal_Tak_Ppt())+"");
			SalesReportHeJi.setPgs_Per_Cls(Double.parseDouble(salesReport.getPgs_Per_Cls()==null?"0":salesReport.getPgs_Per_Cls())+Double.parseDouble(SalesReportHeJi.getPgs_Per_Cls()==null?"0":SalesReportHeJi.getPgs_Per_Cls())+"");
			SalesReportHeJi.setWak_tol_Nbr(Double.parseDouble(salesReport.getWak_tol_Nbr()==null?"0":salesReport.getWak_tol_Nbr())+Double.parseDouble(SalesReportHeJi.getWak_tol_Nbr()==null?"0":SalesReportHeJi.getWak_tol_Nbr())+"");
			SalesReportHeJi.setWak_tol_Ine(df.format(Double.parseDouble(salesReport.getWak_tol_Ine()==null?"0":salesReport.getWak_tol_Ine())+Double.parseDouble(SalesReportHeJi.getWak_tol_Ine()==null?"0":SalesReportHeJi.getWak_tol_Ine()))+"");
			SalesReportHeJi.setGrp_Ine(Double.parseDouble(salesReport.getGrp_Ine()==null?"0":salesReport.getGrp_Ine())+Double.parseDouble(SalesReportHeJi.getGrp_Ine()==null?"0":SalesReportHeJi.getGrp_Ine())+"");
			if(salesReport.getSet_Ktr_Ine()!=null&&TextUtil.isNum(salesReport.getSet_Ktr_Ine())){
				SalesReportHeJi.setSet_Ktr_Ine(Double.parseDouble(salesReport.getSet_Ktr_Ine())+Double.parseDouble(SalesReportHeJi.getSet_Ktr_Ine()==null?"0":SalesReportHeJi.getSet_Ktr_Ine())+"");
			}
		}
		
		SalesReportHeJi.setSet_Ktr_Ine(df.format(Double.parseDouble(SalesReportHeJi.getSet_Ktr_Ine()==null?"0.00":SalesReportHeJi.getSet_Ktr_Ine())/(salesReportList==null?1:salesReportList.size()))+"");
		
		//合计部分
		SalesReportHeJi.setStzsr(Double.parseDouble(SalesReportHeJi.getWak_tol_Ine())+Double.parseDouble(SalesReportHeJi.getGrp_Ine())+"");
		//小时收入
		SalesReportHeJi.setXssr(df.format(Double.parseDouble(SalesReportHeJi.getStzsr())/Double.parseDouble("0.00".equals(salesReportQuery.getFlyTime())?"1":salesReportQuery.getFlyTime()))+"");
		
		
		SalesReportHeJi.setPjkzl(df.format(pjkzl/salesReportList.size())+"");
		//计算小时成本
		String hourpriceData;
		FormulaUtilQuery formulaUtilQuery = new FormulaUtilQuery();
		formulaUtilQuery.setLcl_Dpt_Day(salesReportQuery.getDay());
		site_num = df.format(Double.parseDouble(SalesReportHeJi.getTal_Nbr_Set())/salesReportList.size());
		formulaUtilQuery.setSite_num(site_num);
		formulaUtilQuery.setRoleId(UserContext.getCompanyId());
		if(TextUtil.isEmpty(hourPrice)){
			hourpriceData = formulaUtil.getHourPrice(formulaUtilQuery);
		}else{
			hourpriceData = hourPrice;
		}
		SalesReportHeJi.setMbxssr(df.format(Double.parseDouble(hourpriceData==null?"0":hourpriceData)));
		SalesReportHeJi.setAvg_Dct(df.format(Double.parseDouble(SalesReportHeJi.getStzsr())/salesAll)+"");
		//补贴
		formulaUtilQuery.setPersonTeemIne(SalesReportHeJi.getStzsr());
		formulaUtilQuery.setHourPrice(hourpriceData);
		formulaUtilQuery.setTotalTime(salesReportQuery.getFlyTime());
		String butie = formulaUtil.getSubsidy(formulaUtilQuery);
		SalesReportHeJi.setBbbt(df.format(Double.parseDouble(butie==null?"0.0":butie)));
		//本月累计补贴
		String [] str = date.split("-");
		String newDate = "";
		double toalBuTie=0.0;
		salesReportQuery.setStartTime(str[0]+"-"+str[1]+"-01");
		salesReportQuery.setEndTime(date);
		List<SalesReport> salesReportList2 = salesReportServiceMapper.getAllButieByDate(salesReportQuery);
		
		for(int i = 1;i<=Integer.parseInt(str[2]);i++){
			newDate = str[0]+"-"+str[1]+"-"+i;
			salesReportQuery.setDay(newDate);
			formulaUtilQuery.setArrv_Airpt_Cd(dpt_AirPt_CdName);
			formulaUtilQuery.setDpt_AirPt_Cd(arrv_Airpt_CdName);
			formulaUtilQuery.setDta_Sce(salesReportQuery.getDta_Sce());
			formulaUtilQuery.setLcl_Dpt_Day(salesReportQuery.getDay());
			formulaUtilQuery.setPas_stp(pas_stpName);
			if(!TextUtil.isEmpty(flyTime)&&Integer.parseInt(str[2])==i){
				SalesReport  salesReportbutie =  null;
				for (SalesReport salesReport : salesReportList2) {
					String date1 = salesReport.getDate();
					String [] date2 = date1.split("-");
					if(Integer.parseInt(date2[2])==i){
						salesReportbutie = salesReport;
					}
				}
				if(salesReportbutie!=null){
					double butie2 = (Double.parseDouble(flyTime)*Double.parseDouble(hourpriceData==null?"0.00":hourpriceData)-Double.parseDouble(salesReportbutie.getStzsr()==null?"0.0":salesReportbutie.getStzsr()));
					if(butie2>0){
						toalBuTie = toalBuTie + butie2;
					}
				}
			}else{
				formulaUtilQuery.setIsIncludeExceptionData(salesReportQuery.getIsIncludeExceptionData());
				formulaUtilQuery.setIsIncludePasDpt(salesReportQuery.getIsIncludePasDpt());
				String allTime = formulaUtil.getTotalTime(formulaUtilQuery);
				SalesReport  salesReportbutie =  null;
				for (SalesReport salesReport : salesReportList2) {
					String date1 = salesReport.getDate();
					String [] date2 = date1.split("-");
					if(Integer.parseInt(date2[2])==i){
						salesReportbutie = salesReport;
					}
				}
				if(salesReportbutie!=null&&!TextUtil.isEmpty(allTime)){
					double butie2 = (Double.parseDouble(allTime)*Double.parseDouble(hourpriceData==null?"0.00":hourpriceData)-Double.parseDouble(salesReportbutie.getStzsr()==null?"0.0":salesReportbutie.getStzsr()));
					if(butie2>0){
						toalBuTie = toalBuTie + butie2;
					}
				}
			}
			
			
		}
		SalesReportHeJi.setByljbt(df.format(toalBuTie));
		salesReportList.add(SalesReportHeJi);
		return salesReportList;
	}
	//向周报添加合计数据
	public List<OtherSalesReport> getWeeklyHeJi(List<OtherSalesReport> list,SalesReportQuery salesReportQuery){
		OtherSalesReport otherSalesReportHeJi =new OtherSalesReport();
		DecimalFormat   df   =new   DecimalFormat("0.00");
		NumberFormat nf=NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		if(list==null||list.size()==0){
			otherSalesReportHeJi.setDate("没有相关数据");
		}else{
			otherSalesReportHeJi.setDate("合计");
			for (OtherSalesReport otherSalesReport : list) {
				if(otherSalesReport!=null){
					otherSalesReportHeJi.setHbys(nf.format(Double.parseDouble(otherSalesReport.getHbys()==null?"0.0":otherSalesReport.getHbys())+Double.parseDouble(otherSalesReportHeJi.getHbys()==null?"0.0":otherSalesReportHeJi.getHbys())));
					otherSalesReportHeJi.setXsys(nf.format(Double.parseDouble(otherSalesReport.getXsys()==null?"0.0":otherSalesReport.getXsys())+Double.parseDouble(otherSalesReportHeJi.getXsys()==null?"0.0":otherSalesReportHeJi.getXsys())));
					otherSalesReportHeJi.setRs(nf.format(Double.parseDouble(otherSalesReport.getRs()==null?"0.0":otherSalesReport.getRs())+Double.parseDouble(otherSalesReportHeJi.getRs()==null?"0.0":otherSalesReportHeJi.getRs())));
					otherSalesReportHeJi.setSk(nf.format(Double.parseDouble(otherSalesReport.getSk()==null?"0.0":otherSalesReport.getSk())+Double.parseDouble(otherSalesReportHeJi.getSk()==null?"0.0":otherSalesReportHeJi.getSk())));
					otherSalesReportHeJi.setTd(nf.format(Double.parseDouble(otherSalesReport.getTd()==null?"0.0":otherSalesReport.getTd())+Double.parseDouble(otherSalesReportHeJi.getTd()==null?"0.0":otherSalesReportHeJi.getTd())));
					otherSalesReportHeJi.setKzl(nf.format(Double.parseDouble(otherSalesReport.getKzl()==null?"0.0":otherSalesReport.getKzl())+Double.parseDouble(otherSalesReportHeJi.getKzl()==null?"0.0":otherSalesReportHeJi.getKzl())));
					otherSalesReportHeJi.setPjzk(nf.format(Double.parseDouble(otherSalesReport.getPjzk()==null?"0.0":otherSalesReport.getPjzk())+Double.parseDouble(otherSalesReportHeJi.getPjzk()==null?"0.0":otherSalesReportHeJi.getPjzk())));
					otherSalesReportHeJi.setZgl(nf.format(Double.parseDouble(otherSalesReport.getZgl()==null?"0.0":otherSalesReport.getZgl())+Double.parseDouble(otherSalesReportHeJi.getZgl()==null?"0.0":otherSalesReportHeJi.getZgl())));
					otherSalesReportHeJi.setBt(nf.format(Double.parseDouble(otherSalesReport.getBt()==null?"0.0":otherSalesReport.getBt())+Double.parseDouble(otherSalesReportHeJi.getBt()==null?"0.0":otherSalesReportHeJi.getBt()))+"");
					otherSalesReportHeJi.setBthdl(nf.format(Double.parseDouble(otherSalesReport.getBthdl()==null?"0.0":otherSalesReport.getBthdl())+Double.parseDouble(otherSalesReportHeJi.getBthdl()==null?"0.0":otherSalesReportHeJi.getBthdl()))+"");
					otherSalesReportHeJi.setMbdcl(nf.format(Double.parseDouble(otherSalesReport.getMbdcl()==null?"0.0":otherSalesReport.getMbdcl())+Double.parseDouble(otherSalesReportHeJi.getMbdcl()==null?"0.0":otherSalesReportHeJi.getMbdcl())));
				}
			}
			otherSalesReportHeJi.setXsys(df.format(Double.parseDouble((otherSalesReportHeJi.getXsys()==null?"0.0":otherSalesReportHeJi.getXsys()))/list.size())+"");
			otherSalesReportHeJi.setKzl(df.format(Double.parseDouble((otherSalesReportHeJi.getKzl()==null?"0.0":otherSalesReportHeJi.getKzl()))/list.size())+"");
			otherSalesReportHeJi.setPjzk(df.format(Double.parseDouble(otherSalesReportHeJi.getPjzk()==null?"0.0":otherSalesReportHeJi.getPjzk())/list.size())+"");
			otherSalesReportHeJi.setZgl(df.format(Double.parseDouble(otherSalesReportHeJi.getZgl()==null?"0.0":otherSalesReportHeJi.getZgl())/list.size())+"");
			otherSalesReportHeJi.setMbdcl(df.format(Double.parseDouble(otherSalesReportHeJi.getMbdcl()==null?"0.0":otherSalesReportHeJi.getMbdcl())/list.size())+"");
			list.add(otherSalesReportHeJi);
		}
		return list;
	}
	//向年报添加合计数据
	public List<YearSalesReport> getYearlyHeJi(List<YearSalesReport> list){
		YearSalesReport yearSalesReportHeJi =new YearSalesReport();
		DecimalFormat   df   =new   DecimalFormat("0.00");
		NumberFormat nf=NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		yearSalesReportHeJi.setMonth("合计");
		for (YearSalesReport yearSalesReport : list) {
			yearSalesReportHeJi.setBc(nf.format(Double.parseDouble(yearSalesReport.getBc()==null?"0.0":yearSalesReport.getBc())+Double.parseDouble(yearSalesReportHeJi.getBc()==null?"0.0":yearSalesReportHeJi.getBc())));
			yearSalesReportHeJi.setYys(nf.format(Double.parseDouble(yearSalesReport.getYys()==null?"0.0":yearSalesReport.getYys())+Double.parseDouble(yearSalesReportHeJi.getYys()==null?"0.0":yearSalesReportHeJi.getYys())));
			yearSalesReportHeJi.setZrs(nf.format(Double.parseDouble(yearSalesReport.getZrs()==null?"0.0":yearSalesReport.getZrs())+Double.parseDouble(yearSalesReportHeJi.getZrs()==null?"0.0":yearSalesReportHeJi.getZrs())));
			yearSalesReportHeJi.setJbrs(nf.format(Double.parseDouble(yearSalesReport.getJbrs()==null?"0.0":yearSalesReport.getJbrs())+Double.parseDouble(yearSalesReportHeJi.getJbrs()==null?"0.0":yearSalesReportHeJi.getJbrs())));
			yearSalesReportHeJi.setJbkzl(nf.format(Double.parseDouble(yearSalesReport.getJbkzl()==null?"0.0":yearSalesReport.getJbkzl())+Double.parseDouble(yearSalesReportHeJi.getJbkzl()==null?"0.0":yearSalesReportHeJi.getJbkzl())));
			yearSalesReportHeJi.setZglsr(nf.format(Double.parseDouble(yearSalesReport.getZglsr()==null?"0.0":yearSalesReport.getZglsr())+Double.parseDouble(yearSalesReportHeJi.getZglsr()==null?"0.0":yearSalesReportHeJi.getZglsr())));
			yearSalesReportHeJi.setJbxsys(nf.format(Double.parseDouble(yearSalesReport.getJbxsys()==null?"0.0":yearSalesReport.getJbxsys())+Double.parseDouble(yearSalesReportHeJi.getJbxsys()==null?"0.0":yearSalesReportHeJi.getJbxsys())));
			yearSalesReportHeJi.setZbt(nf.format(Double.parseDouble(yearSalesReport.getZbt()==null?"0.0":yearSalesReport.getZbt())+Double.parseDouble(yearSalesReportHeJi.getZbt()==null?"0.0":yearSalesReportHeJi.getZbt())));
			yearSalesReportHeJi.setJbbt(nf.format(Double.parseDouble(yearSalesReport.getJbbt()==null?"0.0":yearSalesReport.getJbbt())+Double.parseDouble(yearSalesReportHeJi.getJbbt()==null?"0.0":yearSalesReportHeJi.getJbbt())));
			yearSalesReportHeJi.setBthdlf(nf.format(Double.parseDouble(yearSalesReport.getBthdlf()==null?"0.0":yearSalesReport.getBthdlf())+Double.parseDouble(yearSalesReportHeJi.getBthdlf()==null?"0.0":yearSalesReportHeJi.getBthdlf())));
		}
		yearSalesReportHeJi.setJbxsys(df.format(Double.parseDouble((yearSalesReportHeJi.getJbxsys()==null?"0.0":yearSalesReportHeJi.getJbxsys()))/list.size())+"");
		yearSalesReportHeJi.setJbrs(df.format(Double.parseDouble((yearSalesReportHeJi.getJbrs()==null?"0.0":yearSalesReportHeJi.getJbrs()))/list.size())+"");
		yearSalesReportHeJi.setJbkzl(df.format(Double.parseDouble(yearSalesReportHeJi.getJbkzl()==null?"0.0":yearSalesReportHeJi.getJbkzl())/list.size())+"");
		yearSalesReportHeJi.setZglsr(df.format(Double.parseDouble(yearSalesReportHeJi.getZglsr()==null?"0.0":yearSalesReportHeJi.getZglsr())/list.size())+"");
		list.add(yearSalesReportHeJi);
		return list;
	}
	//得到夏秋起始日期
	public Map<String,String> getXQDate(int year){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 	Map<String,String> timeMap = new HashMap<String, String>();
	 	String startTime = "";
		String endTime = "";
		Map<String, List<String>>  dateMap = TextUtil.getWeekOfMonth(year, 3);
		List<String> dateStr = dateMap.get("5");
		for (String date : dateStr) {
			try {
				String week = TextUtil.getWeekOfDate(df.parse(date));
				if("星期日".equals(week)){
					startTime = date;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if("".equals(startTime)){
			List<String> dateStr2 = dateMap.get("4");
			for (String date : dateStr2) {
				try {
					String week = TextUtil.getWeekOfDate(df.parse(date));
					if("星期日".equals(week)){
						startTime = date;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		Map<String, List<String>>  dateMap2 = TextUtil.getWeekOfMonth(year, 10);
		List<String> dateStr3 = dateMap2.get("5");
		for (String date : dateStr3) {
			try {
				String week = TextUtil.getWeekOfDate(df.parse(date));
				if("星期日".equals(week)){
					endTime = date;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if("".equals(endTime)){
			List<String> dateStr4 = dateMap2.get("4");
			for (String date : dateStr4) {
				try {
					String week = TextUtil.getWeekOfDate(df.parse(date));
					if("星期日".equals(week)){
						endTime = date;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		String [] str = endTime.split("-");
		endTime = str[0]+"-"+str[1]+"-"+((Integer.parseInt(str[2])-1)+"");
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);
		return timeMap;
	}
	//得到冬春起始数据
	public Map<String,String> getDCDate(int year){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> timeMap = new HashMap<String, String>();
		String startTime = "";
		String endTime = "";
		Map<String, List<String>>  dateMap = TextUtil.getWeekOfMonth(year, 10);
		List<String> dateStr = dateMap.get("5");
		for (String date : dateStr) {
			try {
				String week = TextUtil.getWeekOfDate(df.parse(date));
				if("星期日".equals(week)){
					startTime = date;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if("".equals(startTime)){
			List<String> dateStr2 = dateMap.get("4");
			for (String date : dateStr2) {
				try {
					String week = TextUtil.getWeekOfDate(df.parse(date));
					if("星期日".equals(week)){
						startTime = date;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		Map<String, List<String>>  dateMap2 = TextUtil.getWeekOfMonth(year+1, 3);
		List<String> dateStr3 = dateMap2.get("5");
		for (String date : dateStr3) {
			try {
				String week = TextUtil.getWeekOfDate(df.parse(date));
				if("星期日".equals(week)){
					endTime = date;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if("".equals(endTime)){
			List<String> dateStr4 = dateMap2.get("4");
			for (String date : dateStr4) {
				try {
					String week = TextUtil.getWeekOfDate(df.parse(date));
					if("星期日".equals(week)){
						endTime = date;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		String [] str = endTime.split("-");
		endTime = str[0]+"-"+str[1]+"-"+((Integer.parseInt(str[2])-1)+"");
		timeMap.put("startTime", startTime);
		timeMap.put("endTime", endTime);
		return timeMap;
	}
	/**
	 * 根据时间段及查询条件得到对应时间的YearSalesReport汇总
	 * @Title: getYearSalesReportt 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @param year	
	 * @param @param month
	 * @param @return    
	 * @return YearSalesReport     
	 * @throws
	 */
	public YearSalesReport getYearSalesReportt(SalesReportQuery salesReportQuery,int year,int month,List<Z_Airdata> zairdataListAll){
		String dpt_AirPt_CdCode= salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		FormulaUtilQuery formulaUtilQuery = new FormulaUtilQuery();
		formulaUtilQuery.setRoleId(UserContext.getCompanyId());
		List<DailyParameters> dailyParametersList = formulaUtilMapper.getHourPriceList(formulaUtilQuery);
		DecimalFormat dff   =new   DecimalFormat("0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//List<Z_Airdata> zairdataList2 =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//包括异常数据的所有数据
		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
		//不包括异常数据的所有数据
		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
		//异常数据
		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
		
		
		String sdate = salesReportQuery.getStartTime();
		String edate = salesReportQuery.getEndTime();
		Date ssdate = null;
		Date esdate = null;
		try {
			ssdate = sdf.parse(sdate);
			esdate = sdf.parse(edate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (Z_Airdata z_Airdata : zairdataListAll) {
			Date date = z_Airdata.getLcl_Dpt_Day();
			if(date.getTime()>=ssdate.getTime()&&date.getTime()<=esdate.getTime()){
				zairdataListA.add(z_Airdata);
			}
		}
		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
		list.addAll(zairdataListA);
		//找出异常数据
		for (Z_Airdata z_Airdata : zairdataListA) {
			if(z_Airdata.getCount_Set()<=0){
				String fltNum = z_Airdata.getFlt_Nbr();
				Date date = z_Airdata.getLcl_Dpt_Day();
				for (Z_Airdata z_Airdata2 : zairdataListA) {
					String fltNum2 = z_Airdata2.getFlt_Nbr();
					Date date2 = z_Airdata2.getLcl_Dpt_Day();
					if(fltNum.equals(fltNum2)&&date.getTime()==date2.getTime()){
						if(!zairdataListC.contains(z_Airdata2)){
							zairdataListC.add(z_Airdata2);
						}
					}
				}
			}
		}
		for (Z_Airdata z_Airdatac : zairdataListC) {
			zairdataListA.remove(z_Airdatac);
		}
		//判断是否包含异常数据
		if(TextUtil.isEmpty(salesReportQuery.getIsIncludeExceptionData())){
			zairdataListB = zairdataListA;
		}else{
			zairdataListB = list;
		}
		YearSalesReport yearSalesReport = new YearSalesReport();
		yearSalesReport.setYear(year+"年");
		yearSalesReport.setMonth(month+"月");
		if(zairdataListB!=null&&zairdataListB.size()>0){
			//有经停
			List<String> flyNumList = new ArrayList<String>();
			List<String> dateList = new ArrayList<String>();
			for (Z_Airdata zairdata : zairdataListB) {
				boolean flag = true;
				for (String flyNum : flyNumList) {
					if(flyNum.equals(zairdata.getFlt_Nbr())){
						flag = false;
					}
				}
				if(flag){
					flyNumList.add(zairdata.getFlt_Nbr());
				}
				boolean flagdate = true;
				for (String datee : dateList) {
					if(datee.equals(sdf.format(zairdata.getLcl_Dpt_Day()))){
						flagdate = false;
					}
				}
				if(flagdate){
					dateList.add(sdf.format(zairdata.getLcl_Dpt_Day()));
				}
			}
			double yystal = 0.00; 
			double zfsjtal = 0.00; 
			double bc = 0;
			double zrstal = 0.00;
			
			if(!TextUtil.isEmpty(pas_stpCode)){
				//设置班次
				bc =zairdataListB.size()/3;
				yearSalesReport.setBc(dff.format(bc));
				for (Z_Airdata zairdata : zairdataListB) {
					//总收入
					yystal = yystal + zairdata.getTotalNumber();
					//总飞时间 （两个短段时间差之和）
					if((dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))||(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))){
						zfsjtal = zfsjtal + ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
					}
					//总人数
					zrstal = zrstal + zairdata.getPgs_Per_Cls();
				}
				//一个班次加24分钟
				zfsjtal = zfsjtal + 0.4*bc;
				//设置月营收
				yearSalesReport.setYys(dff.format(yystal));
				
				//设置总人数
				yearSalesReport.setZrs(dff.format(zrstal));
				//设置均班人数
				if(zrstal!=0.00&&bc!=0){
					yearSalesReport.setJbrs(dff.format(zrstal/bc));
				}
				//设置客座率
				double zgltal = 0.00;
				double kzltal = 0.00;
				double bttal = 0.00;
				double bthdltal = 0.00;
				double jbxsystala = 0.00;
				
				for (String datestr : dateList) {
					double zgl = 0.00;
					double kzl = 0.00;
					double bt = 0.00;
					double bthdl = 0.00;
					double jbxsys = 0.00;
					double jbxsystal = 0.00;
					for (String flyNumstr : flyNumList) {
						double ddrsh = 0.00;
						double ddhch = 0.00;
						double cdrsh = 0.00;
						double zws = 0.00;
						double zsr = 0.00;
						double fxsj = 0.00;
						for (Z_Airdata zairdata : zairdataListB) {
							if(flyNumstr.equals(zairdata.getFlt_Nbr())&&datestr.equals(sdf.format(zairdata.getLcl_Dpt_Day()))){
								if((dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))||(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))){
									//同一时间的同一个航班的短段人数之和，航程之和
									kzl += zairdata.getEgs_Lod_Fts().doubleValue();
									ddrsh = ddrsh + zairdata.getPgs_Per_Cls();
									ddhch = ddhch + zairdata.getSailingDistance();
									zsr = zsr + zairdata.getTotalNumber();
									fxsj = fxsj + ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if((dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))||(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))){
									//同一时间的同一个航班的长段座位数之和，航程之和
									zws =  zairdata.getCount_Set();
									cdrsh = cdrsh + zairdata.getPgs_Per_Cls();
									zsr = zsr + zairdata.getTotalNumber();
									kzl += zairdata.getEgs_Lod_Fts().doubleValue();
								}
							}
						}
						
						//同一时间的一个航程的座公里
						if(zsr!=0.00&&zws!=0.00&&ddhch!=0.00){
							zgl =zgl + zsr/zws/ddhch;
						}
						
						//同一时间的一个航程的客座率
							kzl =kzl/3;
						//同一时间的一个航程的小时收入
						if(fxsj!=0.00&&zsr!=0.00){
							jbxsys =zsr/fxsj;
						}
						double hourprice = 0.00;
						double agenceprice = 0.00;
						for (DailyParameters dailyParameters : dailyParametersList) {
							if(Double.parseDouble(dailyParameters.getFly_site_min()) <=zws&&Double.parseDouble(dailyParameters.getFly_site())>=zws){
								hourprice = Double.parseDouble(dailyParameters.getHour_price());
								agenceprice = Double.parseDouble(dailyParameters.getAgence_price());
							}
						}
						if(fxsj*hourprice-zsr>0){
							bt =bt+ (fxsj*hourprice-zsr);
							bthdl =bthdl+ ((fxsj*hourprice-zsr)+(zsr*agenceprice));
						}
						jbxsystal =  jbxsystal + jbxsys;
					}
					zgltal = zgltal + zgl;
					kzltal = kzltal + kzl;
					bttal = bttal + bt;
					bthdltal =bthdltal + bthdl;
					jbxsystala =  jbxsystala + jbxsystal;
				}
				if(kzltal!=0.00&&bc!=0){
					yearSalesReport.setJbkzl(dff.format(kzltal/bc));
				}
				//设置均班小时营收
				if(jbxsystala!=0.00&&bc!=0){
					yearSalesReport.setJbxsys(dff.format(jbxsystala/bc));
				}
				//设置座公里收入
				if(zgltal!=0.00&&bc!=0){
					yearSalesReport.setZglsr(dff.format(zgltal/bc));
				}
				//设置总补贴,均班补贴，补贴含代理
				if(bttal!=0.00&&bc!=0&&bthdltal!=0.00){
					yearSalesReport.setZbt(dff.format(bttal));
					yearSalesReport.setJbbt(dff.format(bttal/bc));
					yearSalesReport.setBthdlf(dff.format(bthdltal));
				}
			}else{
				//没有经停
				//设置班次
				bc = zairdataListB.size();
				yearSalesReport.setBc(dff.format(bc));
				double kzll = 0.00;
				double zwss = 0.00;
				double zgll = 0.00;
				double bt = 0.00;
				double bthdl = 0.00;
				double xsys = 0.00;
				for (Z_Airdata zairdata : zairdataListB) {
					//总收入
					yystal = yystal + zairdata.getTotalNumber();
					//总飞时间 
					zfsjtal = zfsjtal +0.2+ ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
					//总人数
					zrstal = zrstal + zairdata.getPgs_Per_Cls();
					zwss = zairdata.getCount_Set();
					kzll =kzll +  zairdata.getPgs_Per_Cls()/zwss;
					zgll = zgll + zairdata.getTotalNumber()/zwss/zairdata.getSailingDistance();
					double hourprice = 0.00;
					double agenceprice = 0.00;
					for (DailyParameters dailyParameters : dailyParametersList) {
						if(Double.parseDouble(dailyParameters.getFly_site_min()) <=zwss&&Double.parseDouble(dailyParameters.getFly_site())>=zwss){
							hourprice = Double.parseDouble(dailyParameters.getHour_price());
							agenceprice = Double.parseDouble(dailyParameters.getAgence_price());
						}
					}
					double bttemp = ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000)*hourprice-zairdata.getTotalNumber();
					if(bttemp>0){
						bt = bt + bttemp;
						bthdl = bthdl + (bttemp + bttemp*agenceprice);
					}
					//小时营收
					xsys = xsys + zairdata.getTotalNumber()/((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
				}
				//设置月营收
				yearSalesReport.setYys(dff.format(yystal));
				//设置均班小时营收
				if(xsys!=0.00&&bc!=0){
					yearSalesReport.setJbxsys(dff.format(xsys/bc));
				}
				//设置总人数
				yearSalesReport.setZrs(dff.format(zrstal));
				//设置均班人数
				if(zrstal!=0.00&&bc!=0){
					yearSalesReport.setJbrs(dff.format(zrstal/bc));
				}
				if(kzll!=0.00&&bc!=0){
					yearSalesReport.setJbkzl(dff.format(kzll/bc*100));
				}
				
				//设置座公里收入
				if(zgll!=0.00&&bc!=0){
					yearSalesReport.setZglsr(dff.format(zgll/bc));
				}
				//设置总补贴,均班补贴，补贴含代理
				if(bt!=0.00&&bc!=0&&bthdl!=0.00){
					yearSalesReport.setZbt(dff.format(bt));
					yearSalesReport.setJbbt(dff.format(bt/bc));
					yearSalesReport.setBthdlf(dff.format(bthdl));
				}
			}
		}
		return yearSalesReport;
	}
	public OtherSalesReport getOtherSalesReportt(SalesReportQuery salesReportQuery,List<Z_Airdata> zairdataListAll){
		String dpt_AirPt_CdCode= salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		FormulaUtilQuery formulaUtilQuery = new FormulaUtilQuery();
		formulaUtilQuery.setRoleId(UserContext.getCompanyId());
		List<DailyParameters> dailyParametersList = formulaUtilMapper.getHourPriceList(formulaUtilQuery);
		DecimalFormat dff   =new   DecimalFormat("0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//包括异常数据的所有数据
		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
		//不包括异常数据的所有数据
		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
		//异常数据
		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
		for (Z_Airdata z_Airdata : zairdataListAll) {
			Date date = z_Airdata.getLcl_Dpt_Day();
			String sdate = salesReportQuery.getStartTime();
			Date ssdate = null;
			try {
				ssdate = sdf.parse(sdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(sdf.format(ssdate).equals(sdf.format(date))){
				zairdataListA.add(z_Airdata);
			}
		}
		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
		list.addAll(zairdataListA);
		//找出异常数据
		for (Z_Airdata z_Airdata : zairdataListA) {
			if(z_Airdata.getCount_Set()<=0){
				String fltNum = z_Airdata.getFlt_Nbr();
				Date date = z_Airdata.getLcl_Dpt_Day();
				for (Z_Airdata z_Airdata2 : zairdataListA) {
					String fltNum2 = z_Airdata2.getFlt_Nbr();
					Date date2 = z_Airdata2.getLcl_Dpt_Day();
					if(fltNum.equals(fltNum2)&&date.getTime()==date2.getTime()){
						if(!zairdataListC.contains(z_Airdata2)){
							zairdataListC.add(z_Airdata2);
						}
					}
				}
			}
		}
		for (Z_Airdata z_Airdatac : zairdataListC) {
			zairdataListA.remove(z_Airdatac);
		}
		//判断是否包含异常数据
		if(TextUtil.isEmpty(salesReportQuery.getIsIncludeExceptionData())){
			zairdataListB = zairdataListA;
		}else{
			zairdataListB = list;
		}
		OtherSalesReport otherSalesReport = new OtherSalesReport();
		if(zairdataListB!=null&&zairdataListB.size()>0){
			List<String> flyNumList = new ArrayList<String>();
			for (Z_Airdata zairdata : zairdataListB) {
				boolean flag = true;
				for (String flyNum : flyNumList) {
					if(flyNum.equals(zairdata.getFlt_Nbr())){
						flag = false;
					}
				}
				if(flag){
					flyNumList.add(zairdata.getFlt_Nbr());
				}
			}
			double yystal = 0.00; 
			double zfsjtal = 0.00; 
			double bc = 0;
			double zrstal = 0.00;
			//有经停
			if(!TextUtil.isEmpty(pas_stpCode)){
				//设置班次
				bc =zairdataListB.size()/3;
				for (Z_Airdata zairdata : zairdataListB) {
					//总收入
					yystal = yystal + zairdata.getTotalNumber();
					//总飞时间 （两个短段时间差之和）
					if((dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))||(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))){
						zfsjtal = zfsjtal + ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
					}
					//总人数
					zrstal = zrstal + zairdata.getPgs_Per_Cls();
				}
				//设置营收
				otherSalesReport.setHbys(dff.format(yystal));
				//设置人数
				otherSalesReport.setRs(dff.format(zrstal));
				//设置客座率
				double zgltal = 0.00;
				double kzltal = 0.00;
				double bttal = 0.00;
				double bthdltal = 0.00;
				double xssrtal = 0.00;
				double sktal = 0.00;
				double tdtal = 0.00;
				double moneytal = 0.00;
				double mbdcltal = 0.00;
				for (String flyNumstr : flyNumList) {
					double ddrsh = 0.00;
					double ddhch = 0.00;
					double cdrsh = 0.00;
					double zws = 0.00;
					double zsr = 0.00;
					double fxsj = 0.00;
					double pjzk = 0.00;
					double sk = 0.00;
					double td = 0.00;
					double hourprice = 0.00;
					double agenceprice = 0.00;
					double money = 0.00;
					for (Z_Airdata zairdata : zairdataListB) {
						if(flyNumstr.equals(zairdata.getFlt_Nbr())){
							if((dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))||(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&pas_stpCode.equals(zairdata.getArrv_Airpt_Cd()))||(pas_stpCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))){
								//同一个航班的短段人数之和，航程之和
								ddrsh = ddrsh + zairdata.getPgs_Per_Cls();
								ddhch = ddhch + zairdata.getSailingDistance();
								zsr = zsr + zairdata.getTotalNumber();
								fxsj = fxsj + ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000)+0.2;
								sk = sk + (zairdata.getPgs_Per_Cls() - zairdata.getGrp_Nbr());
								td = td + zairdata.getGrp_Nbr();
								pjzk = pjzk + Double.parseDouble(zairdata.getAvg_Dct()+"");
								money = money +  zairdata.getPgs_Per_Cls()*zairdata.getyBFare();
							}
							if((dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))||(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))){
								//同一时间的同一个航班的长段座位数之和，航程之和
								zws =  zairdata.getCount_Set();
								cdrsh = cdrsh + zairdata.getPgs_Per_Cls();
								zsr = zsr + zairdata.getTotalNumber();
								sk = sk + (zairdata.getPgs_Per_Cls() - zairdata.getGrp_Nbr());
								td = td + zairdata.getGrp_Nbr();
								pjzk = pjzk + Double.parseDouble(zairdata.getAvg_Dct()+"");
								money = money +  zairdata.getPgs_Per_Cls()*zairdata.getyBFare();
							}
							for (DailyParameters dailyParameters : dailyParametersList) {
								if(Double.parseDouble(dailyParameters.getFly_site_min()) <=zws&&Double.parseDouble(dailyParameters.getFly_site())>=zws){
									hourprice = Double.parseDouble(dailyParameters.getHour_price());
									agenceprice = Double.parseDouble(dailyParameters.getAgence_price());
								}
							}
							
						}
					}
					//一个航程的小时收入
					if(fxsj!=0.00&&zsr!=0.00){
						fxsj = fxsj + 0.4;
						xssrtal = xssrtal + zsr/fxsj;
					}
					//同一时间的一个航程的客座率
					if(ddrsh!=0.00&&zws!=0.00&&cdrsh!=0.00){
						kzltal = kzltal + (ddrsh+2*cdrsh)/(2*zws);
					}
					//同一时间的一个航程的座公里
					if(zsr!=0.00&&zws!=0.00&&ddhch!=0.00){
						zgltal = zgltal+  zsr/zws/ddhch;
					}
					if(fxsj!=0.00&&zsr!=0.00&&hourprice>0){
						mbdcltal = mbdcltal +  zsr/fxsj/hourprice; 
					}
					moneytal = moneytal + money;
				    sktal= sktal + sk; 
				    tdtal = tdtal+ td; 
				    if(fxsj*hourprice-zsr>0){
					    bttal = bttal+ fxsj*hourprice-zsr; 
					    bthdltal = bthdltal+ (fxsj*hourprice-zsr + (fxsj*hourprice-zsr)*agenceprice); 
				    }
				}
				if(kzltal!=0.00&&bc!=0){
					otherSalesReport.setKzl(dff.format(kzltal/bc*100));
				}
				//设置小时营收
				if(xssrtal!=0.00&&bc!=0){
					otherSalesReport.setXsys(dff.format(xssrtal/bc));
				}
				//设置平均折扣
				if(moneytal!=0.00&&yystal!=0){
					otherSalesReport.setPjzk(dff.format(yystal/moneytal));
				}
				//设置目标达成率
				if(mbdcltal!=0.00){
					otherSalesReport.setMbdcl(dff.format(mbdcltal/bc));
				}
				//设置散客
				if(sktal!=0.00){
					otherSalesReport.setSk(dff.format(sktal));
				}
				//设置团队
				if(tdtal!=0.00){
					otherSalesReport.setTd(dff.format(tdtal));
				}
				//设置座公里收入
				if(zgltal!=0.00&&bc!=0){
					otherSalesReport.setZgl(dff.format(zgltal/bc));
				}
				//设置总补贴,均班补贴，补贴含代理
				if(bttal!=0.00&&bthdltal!=0.00){
					otherSalesReport.setBt(dff.format(bttal));
					otherSalesReport.setBthdl(dff.format(bthdltal));
				}
			}else{
				bc = zairdataListB.size();

				for (Z_Airdata zairdata : zairdataListB) {
					//总收入
					yystal = yystal + zairdata.getTotalNumber();
					//总人数
					zrstal = zrstal + zairdata.getPgs_Per_Cls();
				}
				//设置营收
				otherSalesReport.setHbys(dff.format(yystal));
				//设置人数
				otherSalesReport.setRs(dff.format(zrstal));
				//设置客座率
				double zgltal = 0.00;
				double kzltal = 0.00;
				double bttal = 0.00;
				double bthdltal = 0.00;
				double xssrtal = 0.00;
				double sktal = 0.00;
				double tdtal = 0.00;
				double mbdcltal = 0.00;
				double moneytal = 0.00;
				for (String flyNumstr : flyNumList) {
					double hj = 0.00;
					double zws = 0.00;
					double zsr = 0.00;
					double zrs = 0.00;
					double fxsj = 0.00;
					double pjzk = 0.00;
					double sk = 0.00;
					double td = 0.00;
					double hourprice = 0.00;
					double agenceprice = 0.00;
					double money = 0.00;
					for (Z_Airdata zairdata : zairdataListB) {
						if(flyNumstr.equals(zairdata.getFlt_Nbr())){
							hj = hj + zairdata.getSailingDistance();
							fxsj = fxsj + ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
							sk = sk + (zairdata.getPgs_Per_Cls() - zairdata.getGrp_Nbr());
							td = td + zairdata.getGrp_Nbr();
							pjzk = pjzk + Double.parseDouble(zairdata.getAvg_Dct()+"");
							zws =  zairdata.getCount_Set();
							zsr = zsr + zairdata.getTotalNumber();
							zrs = zrs + zairdata.getPgs_Per_Cls();
							money = money +  zairdata.getPgs_Per_Cls()*zairdata.getyBFare();
						for (DailyParameters dailyParameters : dailyParametersList) {
							if(Double.parseDouble(dailyParameters.getFly_site_min()) <=zws&&Double.parseDouble(dailyParameters.getFly_site())>=zws){
								hourprice = Double.parseDouble(dailyParameters.getHour_price());
								agenceprice = Double.parseDouble(dailyParameters.getAgence_price());
							}
						}
						}
					}
					//一个航程的小时收入
					if(fxsj!=0.00&&zsr!=0.00){
						fxsj = fxsj + 0.2;
						xssrtal = xssrtal + zsr/fxsj;
					}
					//同一时间的一个航程的客座率
					if(zrs!=0.00&&zws!=0.00){
						kzltal = kzltal + zrs/zws;
					}
					//同一时间的一个航程的座公里
					if(zsr!=0.00&&zws!=0.00&&hj!=0.00){
						zgltal = zgltal+  zsr/zws/hj;
					}
					if(fxsj!=0.00&&zsr!=0.00&&hourprice>0){
						mbdcltal = mbdcltal +  zsr/fxsj/hourprice; 
					}
					moneytal = moneytal + money;
				    sktal= sktal + sk; 
				    tdtal = tdtal+ td; 
				    if(fxsj*hourprice-zsr>0){
				    	 bttal = bttal+ fxsj*hourprice-zsr; 
						 bthdltal = bthdltal+ (fxsj*hourprice-zsr + (fxsj*hourprice-zsr)*agenceprice); 
				    }
				   
				}
				if(kzltal!=0.00&&bc!=0){
					otherSalesReport.setKzl(dff.format(kzltal/bc*100));
				}
				//设置小时营收
				if(xssrtal!=0.00&&bc!=0){
					otherSalesReport.setXsys(dff.format(xssrtal/bc));
				}
				//设置平均折扣
				if(moneytal!=0.00&&yystal!=0){
					otherSalesReport.setPjzk(dff.format(yystal/moneytal));
				}
				//设置目标达成率
				if(mbdcltal!=0.00){
					otherSalesReport.setMbdcl(dff.format(mbdcltal/bc));
				}
				//设置散客
				if(sktal!=0.00){
					otherSalesReport.setSk(dff.format(sktal));
				}
				//设置团队
				if(tdtal!=0.00){
					otherSalesReport.setTd(dff.format(tdtal));
				}
				//设置座公里收入
				if(zgltal!=0.00&&bc!=0){
					otherSalesReport.setZgl(dff.format(zgltal/bc));
				}
				//设置总补贴,均班补贴，补贴含代理
				if(bttal!=0.00&&bthdltal!=0.00){
					otherSalesReport.setBt(dff.format(bttal));
					otherSalesReport.setBthdl(dff.format(bthdltal));
				}
			}
		}else{
			otherSalesReport = null;
		}
		return otherSalesReport;
	}
	@Override
	public String getNewDate(SalesReportQuery salesReportQuery) {
		String newDate = salesReportServiceMapper.getNewDate(salesReportQuery);
		return newDate;
	}
	@Override
	public SalesReportQuery getSalesReportQueryChange(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		if(TextUtil.isEmpty(pas_stpCode)){
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+arrv_Airpt_CdCode);
		}else{
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+pas_stpCode+arrv_Airpt_CdCode);
		}
		String fltcont = salesReportQuery.getFlt_nbr_Count();
		List<String> flyNumList = salesReportServiceMapper.getHbhSingle(salesReportQuery);
		if (flyNumList!=null&&flyNumList.size()>0) {
			for (String string : flyNumList) {
				if(fltcont.contains(string)&&string.length()>=6){
					int i =Integer.parseInt(string.substring(5, 6));
					if(i%2==0){
						salesReportQuery.setDpt_AirPt_Cd(arrv_Airpt_CdCode);
						salesReportQuery.setArrv_Airpt_Cd(dpt_AirPt_CdCode);
					}
				}
			}
		}
		return salesReportQuery;
	}
	@Override
	public SalesReportQuery getSalesReportQueryChangeDay(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		if(TextUtil.isEmpty(pas_stpCode)){
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+arrv_Airpt_CdCode);
		}else{
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+pas_stpCode+arrv_Airpt_CdCode);
		}
		String fltcont = salesReportQuery.getFlt_nbr_Count();
		List<String> flyNumList = salesReportServiceMapper.getHbhSingleday(salesReportQuery);
		if (flyNumList!=null&&flyNumList.size()>0) {
			for (String string : flyNumList) {
				if(fltcont.contains(string)&&string.length()>=6){
					int i =Integer.parseInt(string.substring(5, 6));
					if(i%2==0){
						salesReportQuery.setDpt_AirPt_Cd(arrv_Airpt_CdCode);
						salesReportQuery.setArrv_Airpt_Cd(dpt_AirPt_CdCode);
					}
				}
			}
		}else{
				if(TextUtil.isEmpty(pas_stpCode)){
					salesReportQuery.setFlt_Rte_Cd(arrv_Airpt_CdCode+dpt_AirPt_CdCode);
				}else{
					salesReportQuery.setFlt_Rte_Cd(arrv_Airpt_CdCode+pas_stpCode+dpt_AirPt_CdCode);
				}
				 flyNumList = salesReportServiceMapper.getHbhSingleday(salesReportQuery);
				if (flyNumList!=null&&flyNumList.size()>0) {
					for (String string : flyNumList) {
						if(fltcont.contains(string)&&string.length()>=6){
							int i =Integer.parseInt(string.substring(5, 6));
							if(i%2==0){
								salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
								salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
							}else{
								salesReportQuery.setDpt_AirPt_Cd(arrv_Airpt_CdCode);
								salesReportQuery.setArrv_Airpt_Cd(dpt_AirPt_CdCode);
							}
						}
					}
				}
		}
		return salesReportQuery;
	}
	
	
	public Map<String,Object> getDailyReportIncomeInfoData(SalesReportQuery salesReportQuery,List<Z_Airdata> zairdataListAllToday) {
//		String ourSelfAirLineFlag= "";
//		String ourSelfAirLineFlag1= "";
//		String ourSelfAirLineFlag2 = "";
//		ourSelfAirLineFlag1 = salesReportServiceMapper.getOurSelfAirLineList(salesReportQuery.getGoNum());
//		ourSelfAirLineFlag2 = salesReportServiceMapper.getOurSelfAirLineList(salesReportQuery.getHuiNum());
//		if(!TextUtil.isEmpty(ourSelfAirLineFlag1)||!TextUtil.isEmpty(ourSelfAirLineFlag2)){
//			ourSelfAirLineFlag = "1";
//		}
		Map<String,Object> retMapNew = new TreeMap<String,Object>();
		String pas_stpCode = salesReportQuery.getPas_stp();
		//是否包含异常数据
		List<Z_Airdata> zairdataListB = getIsIncludeExceptionData(zairdataListAllToday,salesReportQuery);
		String company = salesReportQuery.getFlt_nbr_Count().substring(0, 2);
		List<Airdiscount> airdiscounts = outPortMapper.getAirdiscountByCompany(company);
		Map<String, SalesReport> objMap = new HashMap<String, SalesReport>();
		DecimalFormat df = new DecimalFormat("0.00");
		if(TextUtil.isEmpty(pas_stpCode)){
			for (Z_Airdata z_Airdata : zairdataListB) {
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				SalesReport salesReport = new SalesReport();
				salesReport.setFlt_Nbr(z_Airdata.getFlt_Nbr());
				salesReport.setDpt_AirPt_Cd(dpt);
				salesReport.setArrv_Airpt_Cd(arr);
				salesReport.setyPrice(z_Airdata.getyBFare()+"");
				if(z_Airdata.getTal_Nbr_Set()>0){
					salesReport.setSection_ket_ine(df.format(z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100) +"");
				}
				salesReport.setPgs_Per_Cls(z_Airdata.getPgs_Per_Cls()+"");//散团总人数
				salesReport.setAvg_Dct(df.format(z_Airdata.getAvg_Dct().doubleValue())+"");//平均折扣
				salesReport.setGrp_Dct(df.format(z_Airdata.getGrp_Dct().doubleValue())+"");//团队平均折扣
				salesReport.setAirdiscounts(TextUtil.getAirdiscounts(z_Airdata,airdiscounts));//新加的方法,将舱位动态化
				salesReport.setTwo_Tak_Ppt(z_Airdata.getTwo_Tak_Ppt()+"");
				salesReport.setFul_Pce_Ppt(z_Airdata.getFul_Pce_Ppt()+"");
				salesReport.setNne_Dnt_Ppt(z_Airdata.getNne_Dnt_Ppt()+"");
				salesReport.setEht_Five_Dnt_Ppt(z_Airdata.getEht_Five_Dnt_Ppt()+"");
				salesReport.setEht_Dnt_Ppt(z_Airdata.getEht_Dnt_Ppt()+"");
				salesReport.setSen_Five_Dnt_Ppt(z_Airdata.getSen_Five_Dnt_Ppt()+"");
				salesReport.setSen_Dnt_Ppt(z_Airdata.getSen_Dnt_Ppt()+"");
				salesReport.setSix_Five_Dnt_Ppt(z_Airdata.getSix_Five_Dnt_Ppt()+"");
				salesReport.setSix_Dnt_Ppt(z_Airdata.getSix_Dnt_Ppt()+"");
				salesReport.setFve_Fve_Dnt_Ppt(z_Airdata.getFve_Fve_Dnt_Ppt()+"");
				salesReport.setFve_Dnt_Ppt(z_Airdata.getFve_Dnt_Ppt()+"");
				salesReport.setFur_Fve_Dnt_Ppt(z_Airdata.getFur_Fve_Dnt_Ppt()+"");
				salesReport.setFur_Dnt_Ppt(z_Airdata.getFur_Dnt_Ppt()+"");
				salesReport.setThr_Fve_Dnt_Ppt(z_Airdata.getThr_Fve_Dnt_Ppt()+"");
				salesReport.setThr_Dnt_Ppt(z_Airdata.getThr_Dnt_Ppt()+"");
				salesReport.setTwo_Fve_Dnt_Ppt(z_Airdata.getTwo_Fve_Dnt_Ppt()+"");
				salesReport.setTwo_Dnt_Ppt(z_Airdata.getTwo_Dnt_Ppt()+"");
				salesReport.setGrp_Nbr(z_Airdata.getGrp_Nbr()+"");//团队人数
				salesReport.setSal_Tak_Ppt(z_Airdata.getSal_Tak_Ppt()+"");
				salesReport.setWak_tol_Nbr(z_Airdata.getTotalNumber()-z_Airdata.getGrp_Ine().doubleValue()+"");//散客营收
				salesReport.setGrp_Ine(z_Airdata.getGrp_Ine().doubleValue()+"");//团队总营收
				//是否为自营航班标识
				if(TextUtil.isEmpty(z_Airdata.getEterm_account_id())){
					salesReport.setEterm_account_id("0");
				}else{
					salesReport.setEterm_account_id("1");
				}
				objMap.put(outPortMapper.getairportNameByCode(dpt)+"-"+outPortMapper.getairportNameByCode(arr), salesReport);
			}
		}else{
			//经停
			for (Z_Airdata z_Airdata : zairdataListB) {
				SalesReport salesReport = new SalesReport();
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				salesReport.setFlt_Nbr(z_Airdata.getFlt_Nbr());
				salesReport.setDpt_AirPt_Cd(dpt);
				salesReport.setArrv_Airpt_Cd(arr);
				salesReport.setyPrice(z_Airdata.getyBFare()+"");
				if(z_Airdata.getTal_Nbr_Set()>0){
					salesReport.setSection_ket_ine(df.format(z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100) +"");
				}
				salesReport.setPgs_Per_Cls(z_Airdata.getPgs_Per_Cls()+"");//散团总人数
				salesReport.setAvg_Dct(df.format(z_Airdata.getAvg_Dct().doubleValue())+"");//平均折扣
				salesReport.setGrp_Dct(df.format(z_Airdata.getGrp_Dct().doubleValue())+"");//团队平均折扣
				salesReport.setAirdiscounts(TextUtil.getAirdiscounts(z_Airdata,airdiscounts));//新加的方法,将舱位动态化
				salesReport.setTwo_Tak_Ppt(z_Airdata.getTwo_Tak_Ppt()+"");
				salesReport.setFul_Pce_Ppt(z_Airdata.getFul_Pce_Ppt()+"");
				salesReport.setNne_Dnt_Ppt(z_Airdata.getNne_Dnt_Ppt()+"");
				salesReport.setEht_Five_Dnt_Ppt(z_Airdata.getEht_Five_Dnt_Ppt()+"");
				salesReport.setEht_Dnt_Ppt(z_Airdata.getEht_Dnt_Ppt()+"");
				salesReport.setSen_Five_Dnt_Ppt(z_Airdata.getSen_Five_Dnt_Ppt()+"");
				salesReport.setSen_Dnt_Ppt(z_Airdata.getSen_Dnt_Ppt()+"");
				salesReport.setSix_Five_Dnt_Ppt(z_Airdata.getSix_Five_Dnt_Ppt()+"");
				salesReport.setSix_Dnt_Ppt(z_Airdata.getSix_Dnt_Ppt()+"");
				salesReport.setFve_Fve_Dnt_Ppt(z_Airdata.getFve_Fve_Dnt_Ppt()+"");
				salesReport.setFve_Dnt_Ppt(z_Airdata.getFve_Dnt_Ppt()+"");
				salesReport.setFur_Fve_Dnt_Ppt(z_Airdata.getFur_Fve_Dnt_Ppt()+"");
				salesReport.setFur_Dnt_Ppt(z_Airdata.getFur_Dnt_Ppt()+"");
				salesReport.setThr_Fve_Dnt_Ppt(z_Airdata.getThr_Fve_Dnt_Ppt()+"");
				salesReport.setThr_Dnt_Ppt(z_Airdata.getThr_Dnt_Ppt()+"");
				salesReport.setTwo_Fve_Dnt_Ppt(z_Airdata.getTwo_Fve_Dnt_Ppt()+"");
				salesReport.setTwo_Dnt_Ppt(z_Airdata.getTwo_Dnt_Ppt()+"");
				salesReport.setGrp_Nbr(z_Airdata.getGrp_Nbr()+"");//团队人数
				salesReport.setSal_Tak_Ppt(z_Airdata.getSal_Tak_Ppt()+"");
				salesReport.setWak_tol_Nbr(z_Airdata.getTotalNumber()-z_Airdata.getGrp_Ine().doubleValue()+"");//散客营收
				salesReport.setGrp_Ine(z_Airdata.getGrp_Ine().doubleValue()+"");//团队总营收
				if(TextUtil.isEmpty(z_Airdata.getEterm_account_id())){
					salesReport.setEterm_account_id("0");
				}else{
					salesReport.setEterm_account_id("1");
				}
				objMap.put(outPortMapper.getairportNameByCode(dpt)+"-"+outPortMapper.getairportNameByCode(arr), salesReport);
			}
		}
		retMapNew.put("data_person", objMap);
		return retMapNew;
	}
	@Override
	public Map<String ,Object>  getDailyReportIncomeInfo_New(SalesReportQuery salesReportQuery) {
		List<SalesReportDayInfo> SalesReportDayInfoList = new ArrayList<SalesReportDayInfo>();
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Map<String, Object>> retMap = new HashMap<String,Map<String, Object>>();
		Map<String,Object> retMapNew = new TreeMap<String,Object>();
		Map<String,Object> retMapNewTemp = new TreeMap<String,Object>();
		List<String> days = new ArrayList<String>();
		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
		String today = "";
		try {
			today = sdf.format(sdf.parse(salesReportQuery.getDay()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		salesReportQuery.setEndTime(salesReportQuery.getDay());
		salesReportQuery.setStartTime(salesReportQuery.getDay());
		List<Z_Airdata> zairdataListAllToday =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		boolean flage = false;
		String exceptionFlag = "1";
		List<Z_Airdata> zairdataListAll = new ArrayList<Z_Airdata>();
		if(zairdataListAllToday!=null&&zairdataListAllToday.size()>0){
			retMapNewTemp = getDailyReportIncomeInfoData(salesReportQuery,zairdataListAllToday);
			days = salesReportServiceMapper.getDaysIncomeInfo(salesReportQuery);
			salesReportQuery.setEndTime(days.get(0));
			salesReportQuery.setStartTime(days.get(days.size()-1));
			zairdataListAll = salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
			//是否包含异常数据
			Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionDataDay(zairdataListAll, salesReportQuery.getIsIncludeExceptionData(),days.get(0));
			List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
			String dataFlage = (String) dataMap.get("flage");
			//是否包含异常航段
			String strrfalge = "true";
			Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduanDay(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge,days.get(0));
			zairdataListB = (List<Z_Airdata>) dataMap2.get("zairdataListB");
			String dataFlage2 = (String) dataMap2.get("flage");
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
				if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
					//是否包含异常数据
					if("true".equals(dataFlage)){
						exceptionFlag = "2";
					}
				}else{
					if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
						//是否包含异常航段
						if("true".equals(dataFlage2)){
							exceptionFlag = "3";
						}
					}
				}
			}
			if(TextUtil.isEmpty(pas_stpCode)){
				//直飞
				for (String day : days) {
					Map<String, Object> objMap = new HashMap<String, Object>();
					SalesReport goSalesReport = new SalesReport();
					SalesReport backSalesReport = new SalesReport();
					SalesReport goAndBackSalesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					for (Z_Airdata z_Airdata : zairdataListB) {
						if(sdf.format(z_Airdata.getLcl_Dpt_Day()).equals(today)){
							flage = true; 
						}
					}
					airLineAllInfo = TextUtil.getAirLineAllInfo(zairdataListB,day,dpt_AirPt_CdCode,"",arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					DecimalFormat df = new DecimalFormat("0.00");
					goSalesReport.setStzsr(df.format(everyDuanInfo.getGo_sr()));
					backSalesReport.setStzsr(df.format(everyDuanInfo.getBack_sr()));
					goAndBackSalesReport.setStzsr(df.format(everyDuanInfo.getGoAndBack_sr()));
					goSalesReport.setXssr(df.format(everyDuanInfo.getGo_xssr()));
					backSalesReport.setXssr(df.format(everyDuanInfo.getBack_xssr()));
					goAndBackSalesReport.setXssr(df.format(everyDuanInfo.getGoAndBack_xssr()));
					goSalesReport.setSet_Ktr_Ine(df.format(everyDuanInfo.getGo_zgl()));
					backSalesReport.setSet_Ktr_Ine(df.format(everyDuanInfo.getBack_zgl())); 
					goAndBackSalesReport.setSet_Ktr_Ine(df.format(everyDuanInfo.getGoAndBack_zgl())); 
					goSalesReport.setEgs_Lod_Fts(df.format(everyDuanInfo.getGo_kzl()));
					backSalesReport.setEgs_Lod_Fts(df.format(everyDuanInfo.getBack_kzl()));
					goAndBackSalesReport.setEgs_Lod_Fts(df.format(everyDuanInfo.getGoAndBack_kzl()));
					goSalesReport.setTalTime(df.format(everyDuanInfo.getGo_fxsj()));
					backSalesReport.setTalTime(df.format(everyDuanInfo.getBack_fxsj()));
					goAndBackSalesReport.setTalTime(df.format(everyDuanInfo.getGoAndBack_fxsj()));
					objMap.put("go", goSalesReport);
					objMap.put("back", backSalesReport);
					objMap.put("goAndBack", goAndBackSalesReport);
					objMap.put("bjzwsq", airLineAllInfo.getGo_cap());
					objMap.put("bjzwsh", airLineAllInfo.getBack_cap());
					retMap.put(day, objMap);
				}
			}else{
				//经停
				for (String day : days) {
					Map<String, Object> objMap = new HashMap<String, Object>();
					SalesReport goSalesReport = new SalesReport();
					SalesReport backSalesReport = new SalesReport();
					SalesReport goAndBackSalesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					for (Z_Airdata z_Airdata : zairdataListB) {
						if(sdf.format(z_Airdata.getLcl_Dpt_Day()).equals(today)){
							flage = true; 
						}
					}
					airLineAllInfo = TextUtil.getAirLineAllInfo(zairdataListB,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					DecimalFormat df = new DecimalFormat("0.00");
					goSalesReport.setStzsr(df.format(everyDuanInfo.getGo_sr()));
					backSalesReport.setStzsr(df.format(everyDuanInfo.getBack_sr()));
					goAndBackSalesReport.setStzsr(df.format(everyDuanInfo.getGoAndBack_sr()));
					goSalesReport.setXssr(df.format(everyDuanInfo.getGo_xssr()));
					backSalesReport.setXssr(df.format(everyDuanInfo.getBack_xssr()));
					goAndBackSalesReport.setXssr(df.format(everyDuanInfo.getGoAndBack_xssr()));
					goSalesReport.setSet_Ktr_Ine(df.format(everyDuanInfo.getGo_zgl()));
					backSalesReport.setSet_Ktr_Ine(df.format(everyDuanInfo.getBack_zgl())); 
					goAndBackSalesReport.setSet_Ktr_Ine(df.format(everyDuanInfo.getGoAndBack_zgl())); 
					goSalesReport.setEgs_Lod_Fts(df.format(everyDuanInfo.getGo_kzl()));
					backSalesReport.setEgs_Lod_Fts(df.format(everyDuanInfo.getBack_kzl()));
					goAndBackSalesReport.setEgs_Lod_Fts(df.format(everyDuanInfo.getGoAndBack_kzl()));
					goSalesReport.setTalTime(df.format(everyDuanInfo.getGo_fxsj()));
					backSalesReport.setTalTime(df.format(everyDuanInfo.getBack_fxsj()));
					goAndBackSalesReport.setTalTime(df.format(everyDuanInfo.getGoAndBack_fxsj()));
					objMap.put("go", goSalesReport);
					objMap.put("back", backSalesReport);
					objMap.put("goAndBack", goAndBackSalesReport);
					objMap.put("bjzwsq", airLineAllInfo.getGo_cap());
					objMap.put("bjzwsh", airLineAllInfo.getBack_cap());
					retMap.put(day, objMap);
				}
			}
		}
		retMapNew.put("date", retMap);
		retMapNew.put("hasData", flage);
		if(TextUtil.isEmpty(pas_stpCode)){
			//直飞
			retMapNew.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMapNew);
			SalesReportDayInfoList.add(salesReportDayInfo1);
		}else{
			retMapNew.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(pas_stpCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+pas_stpCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMapNew);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"="+pas_stpCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataListB) {
				if((z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode))||(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode))){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getDailyReportIncomeInfoData(false,dpt_AirPt_CdCode,pas_stpCode,zairdataListB1,days,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo3.setFlyCode(dpt_AirPt_CdCode+"="+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataListB) {
				if((z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode))||(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode))){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getDailyReportIncomeInfoData(true,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB2,days,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
			
			SalesReportDayInfo salesReportDayInfo4 = new SalesReportDayInfo();
			salesReportDayInfo4.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo4.setFlyCode(pas_stpCode+"="+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB3 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataListB) {
				if((z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode))||(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode))){
					zairdataListB3.add(z_Airdata);
				}
			}
			salesReportDayInfo4.setDataMap(getDailyReportIncomeInfoData(false,pas_stpCode,arrv_Airpt_CdCode,zairdataListB3,days,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo4);
		}
		Map<String ,Object> rettm = new HashMap<String ,Object>();
		rettm.put("data_person", retMapNewTemp.get("data_person"));
		rettm.put("everyList", SalesReportDayInfoList);
		return rettm;
	}
	public Map<String,Object> getDailyReportIncomeInfoData(boolean isLong ,String dptCode,String arrCode,List<Z_Airdata> zairdataListAll,List<String> days,SalesReportQuery salesReportQuery) {
		//是否包含异常数据
		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
		String exceptionFlag = "1";
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(zairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "single_true";
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		zairdataListB = (List<Z_Airdata>) dataMap2.get("zairdataListB");
		String dataFlage2 = (String) dataMap2.get("flage");
		
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Map<String, SalesReport>> retMap = new HashMap<String,Map<String, SalesReport>>();
		Map<String,Object> retMapNew = new TreeMap<String,Object>();
		boolean flage = false;
		for (String day : days) {
			Map<String, SalesReport> objMap = new HashMap<String, SalesReport>();
			SalesReport goSalesReport = new SalesReport();
			SalesReport backSalesReport = new SalesReport();
			SalesReport goAndBackSalesReport = new SalesReport();
			Double dhjq= 0.0;
			Double dhjh = 0.0;
			Double zwsq= 0.0;
			Double zwsh = 0.0;
			Double sjq= 0.0;
			Double sjh = 0.0;
			Double egs_Lod_Ftsq = 0.0;
			Double egs_Lod_Ftsh = 0.0;
			Double qstsr = 0.0;
			Double hstsr = 0.0;
			double tdsrq = 0.0;
			double tdsrh = 0.0;
			int zwsblq = 0;
			int zwsblh = 0;
			double zglq = 0.0;
			double zglh = 0.0;
			int goindex = 0;
			int backindex = 0;
			boolean goHasData = false;
			boolean backHasData = false;
			for (Z_Airdata z_Airdata : zairdataListB) {
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
					if(isLong){
						if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
							double dates = 0;
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
							}else{
								dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
							}
							if(dates<0){
								dates = dates + 24;
							}
							goindex ++;
							sjq = sjq + dates;
						}
					}
					if(dpt.equals(dptCode)&&arr.equals(arrCode)){
						goHasData = true;
						qstsr = qstsr + z_Airdata.getTotalNumber();
						tdsrq = tdsrq + z_Airdata.getGrp_Ine().doubleValue();
						if(zwsblq<z_Airdata.getTal_Nbr_Set()){
							zwsblq = z_Airdata.getTal_Nbr_Set();
						}
						if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
							zglq = (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
						}
						dhjq =  (double) z_Airdata.getSailingDistance();
						zwsq = (double) z_Airdata.getCount_Set();
						if(zwsq<z_Airdata.getTal_Nbr_Set()){
							zwsq = (double) z_Airdata.getTal_Nbr_Set();
						}
						if(z_Airdata.getTal_Nbr_Set()>0){
							egs_Lod_Ftsq =  z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
						}
						flage = true;
						if(!isLong){
							double dates = 0;
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
							}else{
								dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
							}
							if(dates<0){
								dates = dates + 24;
							}
							sjq = sjq + dates;
						}
						
					}
					//返程
					if(isLong){
						if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
							double dates = 0;
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
							}else{
								dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
							}
							if(dates<0){
								dates = dates + 24;
							}
							sjh = sjh + dates;
							backindex++;
						}
					}
					if(dpt.equals(arrCode)&&arr.equals(dptCode)){
						backHasData = true;
						hstsr = hstsr + z_Airdata.getTotalNumber();
						tdsrh = tdsrh + z_Airdata.getGrp_Ine().doubleValue();
						if(zwsblh<z_Airdata.getTal_Nbr_Set()){
							zwsblh = z_Airdata.getTal_Nbr_Set();
						}
						if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
							zglh = (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
						}
						dhjh =(double) z_Airdata.getSailingDistance();
						zwsh = (double) z_Airdata.getCount_Set();
						if(zwsh<z_Airdata.getTal_Nbr_Set()){
							zwsh = (double) z_Airdata.getTal_Nbr_Set();
						}
						if(!isLong){
							double dates = 0;
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
							}else{
								dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
							}
							if(dates<0){
								dates = dates + 24;
							}
							sjh = sjh + dates;
						}
						if(z_Airdata.getTal_Nbr_Set()>0){
							egs_Lod_Ftsh = z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
						}
						
						flage = true;
					}
				}
			}
			DecimalFormat df = new DecimalFormat("0.00");
			if(dhjq<=0.0&&dhjh<=0.0){
				dhjq = 1.0;
			}
			if(dhjq<=0.0){dhjq = dhjh;}
			if(zwsq<=0.0||zwsq>zwsblq){zwsq = (double) zwsblq;}
			if(dhjh<=0.0){dhjh = dhjq;}
			if(zwsh<=0.0||zwsh>zwsblh){zwsh = (double) zwsblh;}
			if(zwsh<=0.0){
				zwsh = 1.0;
			}
			if(zwsq<=0.0){
				zwsq = 1.0;
			}
			goSalesReport.setStzsr(df.format(qstsr));
			goSalesReport.setTalTime(df.format(sjq));
			goSalesReport.setHasData(goHasData+"");
			backSalesReport.setHasData(backHasData+"");
			backSalesReport.setStzsr(df.format(hstsr));
			backSalesReport.setTalTime(df.format(sjh));
			goAndBackSalesReport.setStzsr(df.format(Double.parseDouble(TextUtil.isEmpty(goSalesReport.getStzsr())?"0.0":goSalesReport.getStzsr())+Double.parseDouble(TextUtil.isEmpty(backSalesReport.getStzsr())?"0.0":backSalesReport.getStzsr()))+"");
			goAndBackSalesReport.setTalTime(df.format(sjh+sjq));
			if(isLong){
				if(sjq>0&&goindex>1){
					goSalesReport.setXssr(df.format(Double.parseDouble(goSalesReport.getStzsr()==null?"0.0":goSalesReport.getStzsr())/sjq)+"");
				}else{
					goSalesReport.setXssr("--");
				}
				if(sjh>0&&backindex>1){
					backSalesReport.setXssr(df.format(Double.parseDouble(backSalesReport.getStzsr()==null?"0.0":backSalesReport.getStzsr())/sjh)+"");
				}else{
					backSalesReport.setXssr("--");
				}
				if(sjq>0&&sjh>0&&goindex>1&&backindex>1){
					goAndBackSalesReport.setXssr(df.format((qstsr+hstsr)/(sjq+sjh)));
				}else{
					goAndBackSalesReport.setXssr("--");
				}
			}else{
				if(sjq>0){
					goSalesReport.setXssr(df.format(Double.parseDouble(goSalesReport.getStzsr()==null?"0.0":goSalesReport.getStzsr())/sjq)+"");
				}else{
					goSalesReport.setXssr("--");
				}
				if(sjh>0){
					backSalesReport.setXssr(df.format(Double.parseDouble(backSalesReport.getStzsr()==null?"0.0":backSalesReport.getStzsr())/sjh)+"");
				}else{
					backSalesReport.setXssr("--");
				}
				if(sjq>0&&sjh>0){
					goAndBackSalesReport.setXssr(df.format((qstsr+hstsr)/(sjq+sjh)));
				}else{
					goAndBackSalesReport.setXssr("--");
				}
			}
			goSalesReport.setSet_Ktr_Ine(df.format(Double.parseDouble(TextUtil.isEmpty(goSalesReport.getStzsr())?"0.0":goSalesReport.getStzsr())/dhjq/zwsq)+"");
			backSalesReport.setSet_Ktr_Ine(df.format(Double.parseDouble(TextUtil.isEmpty(backSalesReport.getStzsr())?"0.0":backSalesReport.getStzsr())/dhjh/zwsh)+"");
			if(zglh>0&&zglq>0){
				goAndBackSalesReport.setSet_Ktr_Ine(df.format((zglh+zglq) /2)); 
			}else{
				if(zglh>0){
					goAndBackSalesReport.setSet_Ktr_Ine(df.format((zglh))); 
				}
				if(zglq>0){
					goAndBackSalesReport.setSet_Ktr_Ine(df.format((zglq))); 
				}
			}
			goSalesReport.setEgs_Lod_Fts(df.format(egs_Lod_Ftsq));
			backSalesReport.setEgs_Lod_Fts(df.format(egs_Lod_Ftsh));
			if(egs_Lod_Ftsq>0&&egs_Lod_Ftsh>0){
				goAndBackSalesReport.setEgs_Lod_Fts(df.format((Double.parseDouble(TextUtil.isEmpty(goSalesReport.getEgs_Lod_Fts())?"0.00":goSalesReport.getEgs_Lod_Fts())+Double.parseDouble(TextUtil.isEmpty(backSalesReport.getEgs_Lod_Fts())?"0.00":backSalesReport.getEgs_Lod_Fts()))/2)+"");
			}else{
				if(egs_Lod_Ftsq>0){
					goAndBackSalesReport.setEgs_Lod_Fts(goSalesReport.getEgs_Lod_Fts());
				}else{
					goAndBackSalesReport.setEgs_Lod_Fts(backSalesReport.getEgs_Lod_Fts());
				}
			}
			goSalesReport.setGrp_Ine(tdsrq+"");
			backSalesReport.setGrp_Ine(tdsrh+"");
			goAndBackSalesReport.setGrp_Ine(tdsrq+tdsrh+"");
			objMap.put("go", goSalesReport);
			objMap.put("back", backSalesReport);
			objMap.put("goAndBack", goAndBackSalesReport);
			retMap.put(day, objMap);
		}
		retMapNew.put("date", retMap);
		retMapNew.put("hasData", flage);
		retMapNew.put("exceptionFlag", exceptionFlag);
		return retMapNew;
	
	}
	@Override
	public Map<String,Object> getDailyReportIncomeInfo(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Map<String, SalesReport>> retMap = new HashMap<String,Map<String, SalesReport>>();
		Map<String,Object> retMapNew = new TreeMap<String,Object>();
		salesReportQuery.setEndTime(salesReportQuery.getDay());
		salesReportQuery.setStartTime(salesReportQuery.getDay());
		double zws = 0.0;
		List<Z_Airdata> zairdataListAllToday =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		if(zairdataListAllToday!=null&&zairdataListAllToday.size()>0){
			retMapNew = getDailyReportIncomeInfoData(salesReportQuery,zairdataListAllToday);
			List<String> days = salesReportServiceMapper.getDaysIncomeInfo(salesReportQuery);
			salesReportQuery.setEndTime(days.get(0));
			salesReportQuery.setStartTime(days.get(days.size()-1));
			
			List<Z_Airdata> zairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
			//是否包含异常数据
			List<Z_Airdata> zairdataListB = getIsIncludeExceptionData(zairdataListAll,salesReportQuery);
			if(TextUtil.isEmpty(pas_stpCode)){
				//直飞
				for (String day : days) {
					Map<String, SalesReport> objMap = new HashMap<String, SalesReport>();
					SalesReport goSalesReport = new SalesReport();
					SalesReport backSalesReport = new SalesReport();
					SalesReport goAndBackSalesReport = new SalesReport();
					Double dhjq= 0.0;
					Double dhjh = 0.0;
					Double zwsq= 0.0;
					Double zwsh = 0.0;
					Double sjq= 0.0;
					Double sjh = 0.0;
					Double stsrq = 0.0;
					Double stsrh = 0.0;
					Double kzlq = 0.0;
					Double kzlh = 0.0;
					for (Z_Airdata z_Airdata : zairdataListB) {
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							if(dhjq<=0){
								dhjq = (double) z_Airdata.getSailingDistance();
							}
							//去程
							if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))){
								//散团收入
								stsrq = (double) z_Airdata.getTotalNumber();
								
								zwsq = (double) z_Airdata.getCount_Set();
								if(zwsq<0){
									zwsq = (double) z_Airdata.getTal_Nbr_Set();
								}
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									sjq = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(sjq<0){
									sjq = sjq + 24;
								}
								//收益客座率
								if(z_Airdata.getTal_Nbr_Set()>0){
									kzlq = z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
							}
							//返程
							if((dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))){
								stsrh = (double) z_Airdata.getTotalNumber();
								
								dhjh = (double) z_Airdata.getSailingDistance();
								zwsh = (double) z_Airdata.getCount_Set();
								if(zwsh<0){
									zwsh = (double) z_Airdata.getTal_Nbr_Set();
								}
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									sjh = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(sjh<0){
									sjh = sjh + 24;
								}
								if(z_Airdata.getTal_Nbr_Set()>0){
									kzlh = z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
							}
						}
					}
					DecimalFormat df = new DecimalFormat("0.00");
					double allsj = sjq + sjh;
					double allsr = stsrq + stsrh;
					double allzw = zwsq + zwsh;
					if(dhjq<=0.0){dhjq = 1.0;}
					if(zwsq<=0.0){zwsq = 1.0;}
					if(dhjh<=0.0){dhjh = 1.0;}
					if(zwsh<=0.0){zwsh = 1.0;}
					if(allzw<=0){
						allzw = zws;
					}else{
						zws = allzw;
					}
					
					goSalesReport.setStzsr(df.format(stsrq));
					backSalesReport.setStzsr(df.format(stsrh));
					goAndBackSalesReport.setStzsr(df.format(Double.parseDouble(goSalesReport.getStzsr()==null?"0.0":goSalesReport.getStzsr())+Double.parseDouble(backSalesReport.getStzsr()==null?"0.0":backSalesReport.getStzsr()))+"");
					
					if(sjq>0){
						goSalesReport.setXssr(df.format(Double.parseDouble(goSalesReport.getStzsr()==null?"0.0":goSalesReport.getStzsr())/sjq)+"");
					}else{
						goSalesReport.setXssr("--");
					}
					if(sjh>0){
						backSalesReport.setXssr(df.format(Double.parseDouble(backSalesReport.getStzsr()==null?"0.0":backSalesReport.getStzsr())/sjh)+"");
					}else{
						backSalesReport.setXssr("--");
					}
					if(allsj>0){
						goAndBackSalesReport.setXssr(df.format(new BigDecimal(Double.parseDouble(TextUtil.isEmpty(goSalesReport.getStzsr())?"0.0":goSalesReport.getStzsr())+Double.parseDouble(TextUtil.isEmpty(backSalesReport.getStzsr())?"0.00":backSalesReport.getStzsr())).divide(new BigDecimal(allsj),2,BigDecimal.ROUND_HALF_UP))+"");
					}else{
						goAndBackSalesReport.setXssr("--");
					}
					goSalesReport.setEgs_Lod_Fts(df.format(kzlq));
					goSalesReport.setSet_Ktr_Ine(df.format(Double.parseDouble(goSalesReport.getStzsr()==null?"0.00":goSalesReport.getStzsr())/dhjq/zwsq)+"");
					
					backSalesReport.setSet_Ktr_Ine(df.format(Double.parseDouble(backSalesReport.getStzsr()==null?"0.00":backSalesReport.getStzsr())/dhjh/zwsh)+"");
					backSalesReport.setEgs_Lod_Fts(df.format(kzlh));
					
					goAndBackSalesReport.setSet_Ktr_Ine(df.format(allsr/allzw/dhjq)+"");
					goAndBackSalesReport.setEgs_Lod_Fts(df.format((Double.parseDouble(TextUtil.isEmpty(goSalesReport.getEgs_Lod_Fts())?"0.00":goSalesReport.getEgs_Lod_Fts())+Double.parseDouble(TextUtil.isEmpty(backSalesReport.getEgs_Lod_Fts())?"0.00":backSalesReport.getEgs_Lod_Fts()))/2)+"");
					
					objMap.put("go", goSalesReport);
					objMap.put("back", backSalesReport);
					objMap.put("goAndBack", goAndBackSalesReport);
					retMap.put(day, objMap);
				}
			}else{
				//经停
				for (String day : days) {
					Map<String, SalesReport> objMap = new HashMap<String, SalesReport>();
					SalesReport goSalesReport = new SalesReport();
					SalesReport backSalesReport = new SalesReport();
					SalesReport goAndBackSalesReport = new SalesReport();
					Double dhjq= 0.0;
					Double dhjh = 0.0;
					Double zwsq= 0.0;
					Double zwsh = 0.0;
					Double sjq= 0.0;
					Double sjh = 0.0;
					Double egs_Lod_Ftsq = 0.0;
					Double egs_Lod_Ftsh = 0.0;
					Double qstsr = 0.0;
					Double hstsr = 0.0;
					int indexq = 0;
					int indexh = 0;
					int zwsblq = 0;
					int zwsblh = 0;
					double zglq = 0.0;
					double zglh = 0.0;
					for (Z_Airdata z_Airdata : zairdataListB) {
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							//去程
							if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
								qstsr = qstsr + z_Airdata.getTotalNumber();
								if(zwsblq<z_Airdata.getTal_Nbr_Set()){
									zwsblq = z_Airdata.getTal_Nbr_Set();
								}
								if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
									zglq = zglq + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
								}
								//短段
								if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
									dhjq = dhjq + z_Airdata.getSailingDistance();
									zwsq = (double) z_Airdata.getCount_Set();
									if(zwsq<z_Airdata.getTal_Nbr_Set()){
										zwsq = (double) z_Airdata.getTal_Nbr_Set();
									}
									double dates = 0;
									if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
									}else{
										dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
									}
									if(dates<0){
										dates = dates + 24;
									}
									sjq = sjq + dates;
								}
								if(z_Airdata.getTal_Nbr_Set()>0){
									egs_Lod_Ftsq = egs_Lod_Ftsq + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								indexq ++;
							}
							if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
								hstsr = hstsr + z_Airdata.getTotalNumber();
								if(zwsblh<z_Airdata.getTal_Nbr_Set()){
									zwsblh = z_Airdata.getTal_Nbr_Set();
								}
								if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
									zglh = zglh + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
								}
								//短段
								if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
									dhjh = dhjh + z_Airdata.getSailingDistance();
									zwsh = (double) z_Airdata.getCount_Set();
									if(zwsh<z_Airdata.getTal_Nbr_Set()){
										zwsh = (double) z_Airdata.getTal_Nbr_Set();
									}
									double dates = 0;
									if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
									}else{
										dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
									}
									if(dates<0){
										dates = dates + 24;
									}
									sjh = sjh + dates;
								}
								if(z_Airdata.getTal_Nbr_Set()>0){
									egs_Lod_Ftsh = egs_Lod_Ftsh + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								indexh ++;
							}
						}
					}
					DecimalFormat df = new DecimalFormat("0.00");
					if(dhjq<=0.0&&dhjh<=0.0){
						dhjq = 1.0;
					}
					if(dhjq<=0.0){dhjq = dhjh;}
					if(zwsq<=0.0){zwsq = (double) zwsblq;}
					if(dhjh<=0.0){dhjh = dhjq;}
					if(zwsh<=0.0){zwsh = (double) zwsblh;}
					int duans = indexq+indexh;
					if(indexq<=0){indexq = 1;}
					if(indexh<=0){indexh = 1;}
					
					if(zwsh<=0.0){
						zwsh = 1.0;
					}
					if(zwsq<=0.0){
						zwsq = 1.0;
					}
					goSalesReport.setStzsr(df.format(qstsr));
					backSalesReport.setStzsr(df.format(hstsr));
					goAndBackSalesReport.setStzsr(df.format(Double.parseDouble(TextUtil.isEmpty(goSalesReport.getStzsr())?"0.0":goSalesReport.getStzsr())+Double.parseDouble(TextUtil.isEmpty(backSalesReport.getStzsr())?"0.0":backSalesReport.getStzsr()))+"");
					if(sjq>0){
						goSalesReport.setXssr(df.format(Double.parseDouble(goSalesReport.getStzsr()==null?"0.0":goSalesReport.getStzsr())/sjq)+"");
					}else{
						goSalesReport.setXssr("--");
					}
					if(sjh>0){
						backSalesReport.setXssr(df.format(Double.parseDouble(backSalesReport.getStzsr()==null?"0.0":backSalesReport.getStzsr())/sjh)+"");
					}else{
						backSalesReport.setXssr("--");
					}
					if(sjq+sjh>0){
						goAndBackSalesReport.setXssr(df.format((qstsr+hstsr)/(sjq+sjh)));
					}else{
						goAndBackSalesReport.setXssr("--");
					}
					
					if(indexq<3){
						goSalesReport.setSet_Ktr_Ine(df.format(zglq / indexq));
					}else{
						goSalesReport.setSet_Ktr_Ine(df.format(Double.parseDouble(TextUtil.isEmpty(goSalesReport.getStzsr())?"0.0":goSalesReport.getStzsr())/dhjq/zwsq)+"");
					}
					if(indexh<3){
						backSalesReport.setSet_Ktr_Ine(df.format(zglh /indexh)); 
					}else{
						backSalesReport.setSet_Ktr_Ine(df.format(Double.parseDouble(TextUtil.isEmpty(backSalesReport.getStzsr())?"0.0":backSalesReport.getStzsr())/dhjh/zwsh)+"");
					}
					if(indexq<3||indexh<3){
						if(duans<1){
							duans = 1;
						}
						goAndBackSalesReport.setSet_Ktr_Ine(df.format((zglh+zglq) /duans)); 
					}else{
						goAndBackSalesReport.setSet_Ktr_Ine(df.format((qstsr+hstsr)/(zwsq+zwsh)/(dhjq)));
					}
					goSalesReport.setEgs_Lod_Fts(df.format(egs_Lod_Ftsq/indexq));
					backSalesReport.setEgs_Lod_Fts(df.format(egs_Lod_Ftsh/indexh));
					goAndBackSalesReport.setEgs_Lod_Fts(df.format((Double.parseDouble(TextUtil.isEmpty(goSalesReport.getEgs_Lod_Fts())?"0.0":goSalesReport.getEgs_Lod_Fts())+Double.parseDouble(TextUtil.isEmpty(backSalesReport.getEgs_Lod_Fts())?"0.0":backSalesReport.getEgs_Lod_Fts()))/2)+"");
					objMap.put("go", goSalesReport);
					objMap.put("back", backSalesReport);
					objMap.put("goAndBack", goAndBackSalesReport);
					retMap.put(day, objMap);
				}
			}
		}
		retMapNew.put("date", retMap);
		return retMapNew;
	}
	
	

	@Override
	public Map<String, Object> getWeekReportIncomeInfo_New(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		//得到本周有数据的日期
		List<String> days = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		//得到本周数据
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		
		salesReportQuery.setStartTime(TextUtil.addDateDay(salesReportQuery.getStartTime(), -7));
		salesReportQuery.setEndTime(TextUtil.addDateDay(salesReportQuery.getEndTime(), -7));
		//得到上周有数据的日期
		List<String> daysold = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		//得到上一周数据
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		
		//是否包含异常数据
		String exceptionFlag = "1";
		List<Z_Airdata> thisWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		List<Z_Airdata> lastWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(thisWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "true";
		if(TextUtil.isEmpty(salesReportQuery.getGoNum())||TextUtil.isEmpty(salesReportQuery.getHuiNum())){
			strrfalge = "false";
		}
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		thisWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2.get("zairdataListB");
		String dataFlage2 = (String) dataMap2.get("flage");
		
		//历史数据判断异常数据
		Map<String,Object> dataMapp = TextUtil.getIsIncludeExceptionData(lastWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1p = (List<Z_Airdata>) dataMapp.get("zairdataListB");
		//是否包含异常航段
		String strrfalgep = "true";
		Map<String,Object> dataMap2p = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1p, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalgep);
		lastWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2p.get("zairdataListB");
		double allBc = TextUtil.getExecClass(thisWeekZairdataListAllNew);
		double oldallBc = TextUtil.getExecClass(lastWeekZairdataListAllNew);
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(true){
			double zys = 0.0;
			double kzl = 0.0;
			double zk = 0.0;
			double tdzk = 0.0;
			double stzrs = 0.0;
			double skzrs = 0.0;
			double dayzgls = 0.0;
			double dayxxysh = 0.0;
			double tdsr = 0.0; 
			int xsysindex = 0;
			int kzlindex = 0;
			int zkindex = 0;
			int tdzkindex = 0;
			int zglindex = 0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(days!=null&&days.size()>0){
				for (String day : days) {
					SalesReport salesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					airLineAllInfo = TextUtil.getAirLineAllInfo(thisWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					salesReport.setXssr(df1.format(everyDuanInfo.getGoAndBack_xssr()));
					if(everyDuanInfo.getGoAndBack_xssr()>0){
						dayxxysh = dayxxysh + everyDuanInfo.getGoAndBack_xssr();
						xsysindex++;
					}
					salesReport.setHbys(df1.format(everyDuanInfo.getGoAndBack_sr()));
					zys = zys + everyDuanInfo.getGoAndBack_sr();
					salesReport.setSet_Ktr_Ine(df1.format(everyDuanInfo.getGoAndBack_zgl()));
					if(everyDuanInfo.getGoAndBack_zgl()>0){
						dayzgls = dayzgls + everyDuanInfo.getGoAndBack_zgl();
						zglindex++;
					}
					salesReport.setPjkzl(df1.format(everyDuanInfo.getGoAndBack_kzl()));
					if(everyDuanInfo.getGoAndBack_kzl()>0){
						kzl = kzl + everyDuanInfo.getGoAndBack_kzl();
						kzlindex++;
					}
					salesReport.setAvg_Dct(df1.format(everyDuanInfo.getGoAndBack_zk()));
					if(everyDuanInfo.getGoAndBack_zk()>0){
						zk = zk + everyDuanInfo.getGoAndBack_zk();
						zkindex++;
					}
					salesReport.setGrp_Dct(df1.format(everyDuanInfo.getGoAndBack_tdzk()));
					if(everyDuanInfo.getGoAndBack_tdzk()>0){
						tdzk = tdzk + everyDuanInfo.getGoAndBack_tdzk();
						tdzkindex++;
					}
					salesReport.setWak_tol_Nbr(df1.format(everyDuanInfo.getGoAndBack_rs()));
					stzrs = stzrs + everyDuanInfo.getGoAndBack_rs();
					salesReport.setGrp_Nbr(df1.format(everyDuanInfo.getGoAndBack_tdrs()));
					skzrs = skzrs + (everyDuanInfo.getGoAndBack_rs() - everyDuanInfo.getGoAndBack_tdrs());
					salesReport.setGrp_Ine(df1.format(everyDuanInfo.getGoAndBack_tdsr()));
					tdsr = tdsr + everyDuanInfo.getGoAndBack_tdsr();
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);
					}
				}
				retMap.put("data", objMap);
				retMap.put("hbys", df2.format(zys));
				retMap.put("allBc", allBc);
				retMap.put("tdsr", df2.format(tdsr));
				if(xsysindex>0){
					retMap.put("xsys", df2.format(dayxxysh/xsysindex));
				}else{
					retMap.put("xsys", "0.00");
				}
				if(zglindex>0){
					retMap.put("zgl", df2.format(dayzgls/zglindex));
				}else{
					retMap.put("zgl", "0.00");
				}
				if(kzlindex>0){
					retMap.put("kzl", df2.format(kzl/kzlindex));
				}else{
					retMap.put("kzl", "0.00");
				}
				if(zkindex>0){
					retMap.put("zk", df2.format(zk/zkindex));
				}else{
					retMap.put("zk", "0.00");
				}
				if(tdzkindex>0){
					retMap.put("tdzk", df2.format(tdzk/tdzkindex));
				}else{
					retMap.put("tdzk", "0.00");
				}
				retMap.put("stzrs", stzrs-skzrs);
				retMap.put("skzrs", skzrs);
			}
			//经停上年同期数据
			if(daysold!=null&&daysold.size()>0){
				for (String day : daysold) {
					SalesReport salesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					//每一天的航班营收
					salesReport.setHbys(df1.format(everyDuanInfo.getGoAndBack_sr()));
					//每一天的客座率
					salesReport.setPjkzl(df1.format(everyDuanInfo.getGoAndBack_kzl()));
					//每一天的平均折扣
					salesReport.setAvg_Dct(df1.format(everyDuanInfo.getGoAndBack_zk()));
					salesReport.setGrp_Dct(df1.format(everyDuanInfo.getGoAndBack_tdzk()));
					salesReport.setWak_tol_Nbr(df1.format(everyDuanInfo.getGoAndBack_rs()));
					salesReport.setGrp_Nbr(df1.format(everyDuanInfo.getGoAndBack_tdrs()));
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMapOld.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);
					}
					}
					retMap.put("olddata", objMapOld);
				}
		}
		//历史数据表格需要
		if(days!=null&&days.size()>0){
			double zys = 0.0;
			double kzl = 0.0;
			double zk = 0.0;
			double tdzk = 0.0;
			double stzrs = 0.0;
			double skzrs = 0.0;
			double dayzgls = 0.0;
			double dayxxysh = 0.0;
			double tdsr = 0.0;
			int xsysindex = 0;
			int kzlindex = 0;
			int zkindex = 0;
			int tdzkindex = 0;
			int zglindex = 0;
			Map<String, Object> oldexcelMap = new TreeMap<String, Object>();
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			if(daysold!=null&&daysold.size()>0){
				for (String day : daysold) {
					SalesReport salesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					salesReport.setXssr(df1.format(everyDuanInfo.getGoAndBack_xssr()));
					if(everyDuanInfo.getGoAndBack_xssr()>0){
						dayxxysh = dayxxysh + everyDuanInfo.getGoAndBack_xssr();
						xsysindex++;
					}
					salesReport.setHbys(df1.format(everyDuanInfo.getGoAndBack_sr()));
					zys = zys + everyDuanInfo.getGoAndBack_sr();
					salesReport.setSet_Ktr_Ine(df1.format(everyDuanInfo.getGoAndBack_zgl()));
					if(everyDuanInfo.getGoAndBack_zgl()>0){
						dayzgls = dayzgls + everyDuanInfo.getGoAndBack_zgl();
						zglindex++;
					}
					salesReport.setPjkzl(df1.format(everyDuanInfo.getGoAndBack_kzl()));
					if(everyDuanInfo.getGoAndBack_kzl()>0){
						kzl = kzl + everyDuanInfo.getGoAndBack_kzl();
						kzlindex++;
					}
					salesReport.setAvg_Dct(df1.format(everyDuanInfo.getGoAndBack_zk()));
					if(everyDuanInfo.getGoAndBack_zk()>0){
						zk = zk + everyDuanInfo.getGoAndBack_zk();
						zkindex++;
					}
					salesReport.setGrp_Dct(df1.format(everyDuanInfo.getGoAndBack_tdzk()));
					if(everyDuanInfo.getGoAndBack_tdzk()>0){
						tdzk = tdzk + everyDuanInfo.getGoAndBack_tdzk();
						tdzkindex++;
					}
					salesReport.setWak_tol_Nbr(df1.format(everyDuanInfo.getGoAndBack_rs()));
					stzrs = stzrs + everyDuanInfo.getGoAndBack_rs();
					salesReport.setGrp_Nbr(df1.format(everyDuanInfo.getGoAndBack_tdrs()));
					skzrs = skzrs + (everyDuanInfo.getGoAndBack_rs() - everyDuanInfo.getGoAndBack_tdrs());
					salesReport.setGrp_Ine(df1.format(everyDuanInfo.getGoAndBack_tdsr()));
					tdsr = tdsr + everyDuanInfo.getGoAndBack_tdsr();
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);
					}
				}
				oldexcelMap.put("data", objMap);
				oldexcelMap.put("hbys", df2.format(zys));
				oldexcelMap.put("allBc", allBc);
				oldexcelMap.put("tdsr", df2.format(tdsr));
				if(xsysindex>0){
					oldexcelMap.put("xsys", df2.format(dayxxysh/xsysindex));
				}else{
					oldexcelMap.put("xsys", "0.00");
				}
				if(zglindex>0){
					oldexcelMap.put("zgl", df2.format(dayzgls/zglindex));
				}else{
					oldexcelMap.put("zgl", "0.00");
				}
				if(kzlindex>0){
					oldexcelMap.put("kzl", df2.format(kzl/kzlindex));
				}else{
					oldexcelMap.put("kzl", "0.00");
				}
				if(zkindex>0){
					oldexcelMap.put("zk", df2.format(zk/zkindex));
				}else{
					oldexcelMap.put("zk", "0.00");
				}
				if(tdzkindex>0){
					oldexcelMap.put("tdzk", df2.format(tdzk/tdzkindex));
				}else{
					oldexcelMap.put("tdzk", "0.00");
				}
				oldexcelMap.put("stzrs", stzrs-skzrs);
				oldexcelMap.put("skzrs", skzrs);
				retMap.put("oldexcelMap", oldexcelMap);
			}
		}
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		List<SalesReportDayInfo> SalesReportDayInfoList = new ArrayList<SalesReportDayInfo>();
		if(TextUtil.isEmpty(pas_stpCode)){
			//直飞
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getWeekReportIncomeInfoData(false,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(arrv_Airpt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getWeekReportIncomeInfoData(false,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
		}else{
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(pas_stpCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+pas_stpCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getWeekReportIncomeInfoData(false,dpt_AirPt_CdCode,pas_stpCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(pas_stpCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getWeekReportIncomeInfoData(false,pas_stpCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
			
			SalesReportDayInfo salesReportDayInfo4 = new SalesReportDayInfo();
			salesReportDayInfo4.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo4.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB3 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB3.add(z_Airdata);
				}
			}
			salesReportDayInfo4.setDataMap(getWeekReportIncomeInfoData(true,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB3,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo4);
			
			SalesReportDayInfo salesReportDayInfo5 = new SalesReportDayInfo();
			salesReportDayInfo5.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo5.setFlyCode(arrv_Airpt_CdCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB4 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB4.add(z_Airdata);
				}
			}
			salesReportDayInfo5.setDataMap(getWeekReportIncomeInfoData(true,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB4,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo5);
			
			SalesReportDayInfo salesReportDayInfo6 = new SalesReportDayInfo();
			salesReportDayInfo6.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo6.setFlyCode(pas_stpCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB5 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB5.add(z_Airdata);
				}
			}
			salesReportDayInfo6.setDataMap(getWeekReportIncomeInfoData(false,pas_stpCode,arrv_Airpt_CdCode,zairdataListB5,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo6);
			
			SalesReportDayInfo salesReportDayInfo7 = new SalesReportDayInfo();
			salesReportDayInfo7.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo7.setFlyCode(arrv_Airpt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB6 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB6.add(z_Airdata);
				}
			}
			salesReportDayInfo7.setDataMap(getWeekReportIncomeInfoData(false,arrv_Airpt_CdCode,pas_stpCode,zairdataListB6,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo7);
			
		}
		Map<String ,Object> rettm = new HashMap<String ,Object>();
		rettm.put("everyList", SalesReportDayInfoList);
		return rettm;
	}
	
	public Map<String,Object> getWeekReportIncomeInfoData(boolean isLong ,String dptCode,String arrCode,List<Z_Airdata> zairdataListAll,List<Z_Airdata> thisWeekZairdataListAllNew,List<Z_Airdata> lastWeekZairdataListAllNew,List<String> days,List<String> daysold,SalesReportQuery salesReportQuery) {
		//是否包含异常数据
		String exceptionFlag = "1";
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(zairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "single_false";
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		String dataFlage2 = (String) dataMap2.get("flage");
		
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		boolean flage = false;
		double allBc = TextUtil.getExecClass(thisWeekZairdataListAllNew);
		Map<String, Object> retMap = new TreeMap<String, Object>();
		double zys = 0.0;
		double kzl = 0.0;
		double zk = 0.0;
		double stzrs = 0.0;
		double skzrs = 0.0;
		double fxsj = 0.0;
		double zws = 0.0;
		double hj = 0.0;
		double dayzgls = 0.0;
		double dayxxysh = 0.0;
		double tdsr = 0.0;
		int length = days.size();
		Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
		Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
		if(days!=null&&days.size()>0){
			for (String day : days) {
				Double dayhbys = 0.0;
				Double daytimes = 0.0;
				Double dayxsys = 0.0;
				Double dayzgl = 0.0;
				Double dayzwsq = 0.0;
				Double dayzwsh = 0.0;
				Double dayzwsall = 0.0;
				Double dayhjsingle = 0.0;
				Double daykzl = 0.0;
				Double daykzlall = 0.0;
				Double dayzk = 0.0;
				Double dayzkall = 0.0;
				
				Double dayskperson = 0.0;
				Double daytdperson = 0.0;
				Double daytdsr = 0.0;
				int index = 0;
				Double daytdzk = 0.0;
				Double daytdzkall = 0.0;
				int tdindex = 0;
				double zgl = 0;
				SalesReport salesReport = new SalesReport();
				for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
					if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if(dpt.equals(dptCode)&&arr.equals(arrCode)){
							flage = true;
							dayhbys = (double) z_Airdata.getTotalNumber();
							if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								zgl = (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
							}
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
							}else{
								daytimes =daytimes + ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
							}
							if(daytimes<0){
								daytimes = daytimes + 24;
							}
							
							dayzwsq = (double) z_Airdata.getCount_Set();
							//短段航距之和
							dayhjsingle = dayhjsingle + z_Airdata.getSailingDistance();
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
							
							//每一天的团队折扣之和
							if(z_Airdata.getGrp_Dct()!=null&&z_Airdata.getGrp_Dct().doubleValue()>0){
								tdindex++;
								daytdzkall = daytdzkall + z_Airdata.getGrp_Dct().doubleValue();
							}
							
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							daytdsr = daytdsr + z_Airdata.getGrp_Ine().doubleValue();
							index ++;
						}
					}
				}
				fxsj =fxsj + daytimes ; 
				zys = zys + dayhbys;
				tdsr = tdsr + daytdsr;
				stzrs = stzrs  + daytdperson;
				skzrs = skzrs + dayskperson ;
				
				//往返座位数之和
				dayzwsall = dayzwsq+dayzwsh;
				zws = zws + dayzwsall;
				if(dayhbys<=0.0){dayhbys=0.0;}
				if(daytimes<=0.0){
					dayxsys = 0.0;
					length -- ;
				}else{
					dayxsys = dayhbys / daytimes;
					dayxxysh = dayxsys + dayxxysh;
				}
				if(dayzwsall<=0.0){dayzwsall=1.0;}
				if(dayhjsingle<=0.0){dayhjsingle=1.0;}
				if(index<=0){index=1;}
				//每一天的小时营收
				salesReport.setXssr(df1.format(dayxsys));
				salesReport.setHbys(df1.format(dayhbys));
				salesReport.setGrp_Ine(daytdsr+"");
				hj = dayhjsingle;
				//每一天的座公里
				if(index<6||dayzwsall<=1){
					dayzgl = zgl/index;
				}else{
					dayzgl = dayhbys / dayzwsall / dayhjsingle;
				}
				dayzgls = dayzgls + dayzgl;
				salesReport.setSet_Ktr_Ine(df1.format(dayzgl));
				//每一天的客座率
				daykzl = daykzlall / index; 
				salesReport.setPjkzl(df1.format(daykzl));
				//每一天的平均折扣
				dayzk = dayzkall / index;
				salesReport.setAvg_Dct(df1.format(dayzk));
				//每一天的平均团队折扣
				if(tdindex>0){
					daytdzk = daytdzkall / tdindex;
				}else{
					daytdzk = daytdzkall ;
				}
				salesReport.setGrp_Dct(df1.format(daytdzk));
				salesReport.setWak_tol_Nbr(dayskperson+"");
				salesReport.setGrp_Nbr(daytdperson+"");
				kzl = kzl + daykzl; 
				zk = zk + dayzk;
				String [] dayarr = day.split("-");
				Date dd = new Date();
				try {
					dd = sdf.parse(day);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(Double.parseDouble(salesReport.getHbys())>0){
					objMap.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);
				}
			}
			//航班营收合计
			retMap.put("data", objMap);
			retMap.put("hasData", flage);
			retMap.put("hbys", df2.format(zys));
			retMap.put("tdsr", df2.format(tdsr));
			if(length>0){
				retMap.put("xsys", df2.format(dayxxysh/length));
			}else{
				retMap.put("xsys", df2.format(0.00));
			}
			if(zws<=0.0){zws=1;}
				retMap.put("zgl", df1.format(dayzgls/allBc));
				retMap.put("kzl", df1.format(kzl/allBc));
				retMap.put("zk", df1.format(zk/allBc));
				retMap.put("stzrs", stzrs);
				retMap.put("skzrs", skzrs);
			}
			if(daysold!=null&&daysold.size()>0){
			for (String day : daysold) {
				Double dayhbys = 0.0;
				Double daykzl = 0.0;
				Double daykzlall = 0.0;
				Double dayzk = 0.0;
				Double dayzkall = 0.0;
				Double dayskperson = 0.0;
				Double daytdperson = 0.0;
				int index = 0;
				SalesReport salesReport = new SalesReport();
				for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
					if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if(dpt.equals(dptCode)&&arr.equals(arrCode)){
							//每一天的航班营收之和
							dayhbys = dayhbys + z_Airdata.getTotalNumber();
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							index ++;
						}
					}
					
				}
				if(dayhbys<=0.0){dayhbys=0.0;}
				if(index<=0){index=1;}
				//每一天的小时营收
				salesReport.setHbys(df1.format(dayhbys));
				//每一天的客座率
				daykzl = daykzlall / index; 
				salesReport.setPjkzl(df1.format(daykzl));
				//每一天的平均折扣
				dayzk = dayzkall / index;
				salesReport.setAvg_Dct(df1.format(dayzk));
				salesReport.setWak_tol_Nbr(dayskperson+"");
				salesReport.setGrp_Nbr(daytdperson+"");
				String [] dayarr = day.split("-");
				Date dd = new Date();
				try {
					dd = sdf.parse(day);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(Double.parseDouble(salesReport.getHbys())>0){
					objMapOld.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);	
				}
			}
			retMap.put("olddata", objMapOld);
		}
		retMap.put("exceptionFlag", exceptionFlag);
		return retMap;
	}
	@Override
	public Map<String, Object> getWeekReportIncomeInfo(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		//得到本周有数据的日期
		List<String> days = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		//得到本周数据
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		double allBc = TextUtil.getExecClass(thisWeekZairdataListAll);
		salesReportQuery.setStartTime(TextUtil.addDateDay(salesReportQuery.getStartTime(), -7));
		salesReportQuery.setEndTime(TextUtil.addDateDay(salesReportQuery.getEndTime(), -7));
		//得到上周有数据的日期
		List<String> daysold = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		//得到上一周数据
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//是否包含异常数据
		List<Z_Airdata> thisWeekZairdataListAllNew = getIsIncludeExceptionData(thisWeekZairdataListAll,salesReportQuery);
		List<Z_Airdata> lastWeekZairdataListAllNew = getIsIncludeExceptionData(lastWeekZairdataListAll,salesReportQuery);
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(!TextUtil.isEmpty(pas_stpCode)){
			double zys = 0.0;
			double kzl = 0.0;
			double zk = 0.0;
			double stzrs = 0.0;
			double skzrs = 0.0;
			double fxsj = 0.0;
			double zws = 0.0;
			double hj = 0.0;
			double dayzgls = 0.0;
			double dayxxysh = 0.0;
			int length = days.size();
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(days!=null&&days.size()>0){
				for (String day : days) {
					Double dayhbys = 0.0;
					Double daytimes = 0.0;
					Double dayxsys = 0.0;
					Double dayzgl = 0.0;
					Double dayzwsq = 0.0;
					Double dayzwsh = 0.0;
					Double dayzwsall = 0.0;
					Double dayhjsingle = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double dayzk = 0.0;
					Double dayzkall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					int index = 0;
					double zgl = 0;
					SalesReport salesReport = new SalesReport();
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							//每一天的航班营收之和
							dayhbys = dayhbys + z_Airdata.getTotalNumber();
							if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								zgl = zgl + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
							}
							String dpt = z_Airdata.getDpt_AirPt_Cd();
							String arr = z_Airdata.getArrv_Airpt_Cd();
							if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
								//去程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								dayzwsq = (double) z_Airdata.getCount_Set();
								//短段航距之和
								dayhjsingle = dayhjsingle + z_Airdata.getSailingDistance();
							}
							if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
								//回程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								dayzwsh = (double) z_Airdata.getCount_Set();
							}
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							index ++;
						}
					}
					fxsj =fxsj + daytimes ; 
					zys = zys + dayhbys;
					stzrs = stzrs  + daytdperson;
					skzrs = skzrs + dayskperson ;
					
					//往返座位数之和
					dayzwsall = dayzwsq+dayzwsh;
					zws = zws + dayzwsall;
					if(dayhbys<=0.0){dayhbys=0.0;}
					if(daytimes<=0.0){
						dayxsys = 0.0;
						length -- ;
					}else{
						dayxsys = dayhbys / daytimes;
						dayxxysh = dayxsys + dayxxysh;
					}
					if(dayzwsall<=0.0){dayzwsall=1.0;}
					if(dayhjsingle<=0.0){dayhjsingle=1.0;}
					if(index<=0){index=1;}
					//每一天的小时营收
					salesReport.setXssr(df1.format(dayxsys));
					salesReport.setHbys(df1.format(dayhbys));
					hj = dayhjsingle;
					//每一天的座公里
					if(index<6||dayzwsall<=1){
						dayzgl = zgl/index;
					}else{
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
					}
					dayzgls = dayzgls + dayzgl;
					salesReport.setSet_Ktr_Ine(df1.format(dayzgl));
					//每一天的客座率
					daykzl = daykzlall / index; 
					salesReport.setPjkzl(df1.format(daykzl));
					//每一天的平均折扣
					dayzk = dayzkall / index;
					salesReport.setAvg_Dct(df1.format(dayzk));
					salesReport.setWak_tol_Nbr(dayskperson+"");
					salesReport.setGrp_Nbr(daytdperson+"");
					kzl = kzl + daykzl; 
					zk = zk + dayzk;
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					objMap.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);
				}
				//航班营收合计
				retMap.put("data", objMap);
				retMap.put("hbys", df2.format(zys));
				if(fxsj<=0.0){fxsj=1;}
				if(length>0){
					retMap.put("xsys", df2.format(dayxxysh/length));
				}else{
					retMap.put("xsys", df2.format(0.00));
				}
				if(zws<=0.0){zws=1;}
				retMap.put("zgl", df1.format(dayzgls/allBc));
				retMap.put("kzl", df1.format(kzl/allBc));
				retMap.put("zk", df1.format(zk/allBc));
				retMap.put("stzrs", stzrs);
				retMap.put("skzrs", skzrs);
				}
				if(daysold!=null&&daysold.size()>0){
				for (String day : daysold) {
					Double dayhbys = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double dayzk = 0.0;
					Double dayzkall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					int index = 0;
					SalesReport salesReport = new SalesReport();
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							//每一天的航班营收之和
							dayhbys = dayhbys + z_Airdata.getTotalNumber();
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							index ++;
						}
						
					}
					if(dayhbys<=0.0){dayhbys=0.0;}
					if(index<=0){index=1;}
					//每一天的小时营收
					salesReport.setHbys(df1.format(dayhbys));
					//每一天的客座率
					daykzl = daykzlall / index; 
					salesReport.setPjkzl(df1.format(daykzl));
					//每一天的平均折扣
					dayzk = dayzkall / index;
					salesReport.setAvg_Dct(df1.format(dayzk));
					salesReport.setWak_tol_Nbr(dayskperson+"");
					salesReport.setGrp_Nbr(daytdperson+"");
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					objMapOld.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);				}
				retMap.put("olddata", objMapOld);
			}
			}else{
				//直飞
				double zys = 0.0;
				double kzl = 0.0;
				double zk = 0.0;
				double stzrs = 0.0;
				double skzrs = 0.0;
				double fxsj = 0.0;
				double zws = 0.0;
				double hj = 0.0;
				double dayzgls = 0.0;
				double dayxxysh = 0.0;
				int length = days.size();
				Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
				Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
				if(days!=null&&days.size()>0){
					for (String day : days) {
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayzk = 0.0;
						Double dayzkall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						int index = 0;
						SalesReport salesReport = new SalesReport();
						for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								if(z_Airdata.getCount_Set()<=0){
									dayzwsall = dayzwsall + z_Airdata.getTal_Nbr_Set();
								}else{
									dayzwsall =  dayzwsall + (double) z_Airdata.getCount_Set();
								}
								dayhjsingle = (double) z_Airdata.getSailingDistance();
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的折扣之和
								dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						fxsj =fxsj + daytimes ; 
						zys = zys + dayhbys;
						stzrs = stzrs  + daytdperson;
						skzrs = skzrs + dayskperson ;
						
						//往返座位数之和
						zws = zws + dayzwsall;
						if(dayhbys<=0.0){dayhbys=0.0;}
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length -- ;
						}else{
							dayxsys = dayhbys / daytimes;
							dayxxysh = dayxsys + dayxxysh;
						}
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){dayhjsingle=1.0;}
						if(index<=0){index=1;}
						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
						salesReport.setXssr(df1.format(dayxsys));
						salesReport.setHbys(df1.format(dayhbys));
						hj = dayhjsingle;
						//每一天的座公里
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
						dayzgls = dayzgls + dayzgl;
						salesReport.setSet_Ktr_Ine(df1.format(dayzgl));
						//每一天的客座率
						daykzl = daykzlall / index; 
						salesReport.setPjkzl(df1.format(daykzl));
						//每一天的平均折扣
						dayzk = dayzkall / index;
						salesReport.setAvg_Dct(df1.format(dayzk));
						salesReport.setWak_tol_Nbr(dayskperson+"");
						salesReport.setGrp_Nbr(daytdperson+"");
						kzl = kzl + daykzl; 
						zk = zk + dayzk;
						String [] dayarr = day.split("-");
						Date dd = new Date();
						try {
							dd = sdf.parse(day);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						objMap.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);
					}
					//航班营收合计
					retMap.put("data", objMap);
					retMap.put("hbys", df2.format(zys));
					if(fxsj<=0.0){fxsj=1;}
					if(length>0){
						retMap.put("xsys", df2.format(dayxxysh/length));
					}else{
						retMap.put("xsys", df2.format(0.00));
					}
					if(zws<=0.0){zws=1;}
					retMap.put("zgl", df1.format(dayzgls/allBc));
					retMap.put("kzl", df1.format(kzl/allBc));
					retMap.put("zk", df1.format(zk/allBc));
					retMap.put("stzrs", stzrs);
					retMap.put("skzrs", skzrs);
					}
					if(daysold!=null&&daysold.size()>0){
					for (String day : daysold) {
						Double dayhbys = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayzk = 0.0;
						Double dayzkall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						int index = 0;
						SalesReport salesReport = new SalesReport();
						for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的折扣之和
								dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
							
						}
						if(dayhbys<=0.0){dayhbys=0.0;}
						if(index<=0){index=1;}
						//每一天的小时营收
						salesReport.setHbys(df1.format(dayhbys));
						//每一天的客座率
						daykzl = daykzlall / index; 
						salesReport.setPjkzl(df1.format(daykzl));
						//每一天的平均折扣
						dayzk = dayzkall / index;
						salesReport.setAvg_Dct(df1.format(dayzk));
						salesReport.setWak_tol_Nbr(dayskperson+"");
						salesReport.setGrp_Nbr(daytdperson+"");
						String [] dayarr = day.split("-");
						Date dd = new Date();
						try {
							dd = sdf.parse(day);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						objMapOld.put(TextUtil.getWeekOfDate2(dd)+"-"+Integer.parseInt(dayarr[1])+"."+Integer.parseInt(dayarr[2]),salesReport);
					}
					retMap.put("olddata", objMapOld);
				}
		}
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		return retMap;
	}
	
	

	@Override
	public Map<String, Object> getMonthReportIncomeInfo_New(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		List<String> days = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		salesReportQuery.setStartTime(TextUtil.addDateMonth(salesReportQuery.getStartTime(), -1)+"-01");
		salesReportQuery.setEndTime(TextUtil.addDateMonth(salesReportQuery.getEndTime(), -1)+"-31");
		List<String> daysold = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//是否包含异常数据
		String exceptionFlag = "1";
		List<Z_Airdata> thisWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		List<Z_Airdata> lastWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(thisWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "true";
		if(TextUtil.isEmpty(salesReportQuery.getGoNum())||TextUtil.isEmpty(salesReportQuery.getHuiNum())){
			strrfalge = "false";
		}
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		thisWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2.get("zairdataListB");
		String dataFlage2 = (String) dataMap2.get("flage");
		double allBc = TextUtil.getExecClass(thisWeekZairdataListAllNew);
		//历史数据判断异常数据
		Map<String,Object> dataMapp = TextUtil.getIsIncludeExceptionData(lastWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1p = (List<Z_Airdata>) dataMapp.get("zairdataListB");
		//是否包含异常航段
		String strrfalgep = "true";
		Map<String,Object> dataMap2p = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1p, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalgep);
		lastWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2p.get("zairdataListB");
		double allBcold = TextUtil.getExecClass(lastWeekZairdataListAllNew);
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(true){
			double zys = 0.0;
			double kzl = 0.0;
			double zk = 0.0;
			double tdzk = 0.0;
			double stzrs = 0.0;
			double skzrs = 0.0;
			double dayzgls = 0.0;
			double dayxxysh = 0.0;
			double tdsr = 0.0;
			int xsysindex = 0;
			int zglindex = 0;
			int kzlindex = 0;
			int zkindex = 0;
			int tdzkindex = 0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(days!=null&&days.size()>0){
				for (String day : days) {
					SalesReport salesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					airLineAllInfo = TextUtil.getAirLineAllInfo(thisWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					salesReport.setXssr(df1.format(everyDuanInfo.getGoAndBack_xssr()));
					if(everyDuanInfo.getGoAndBack_xssr()>0){
						dayxxysh = dayxxysh + everyDuanInfo.getGoAndBack_xssr();
						xsysindex++;
					}
					salesReport.setHbys(df1.format(everyDuanInfo.getGoAndBack_sr()));
					zys = zys + everyDuanInfo.getGoAndBack_sr();
					salesReport.setSet_Ktr_Ine(df1.format(everyDuanInfo.getGoAndBack_zgl()));
					if(everyDuanInfo.getGoAndBack_zgl()>0){
						dayzgls = dayzgls + everyDuanInfo.getGoAndBack_zgl();
						zglindex++;
					}
					salesReport.setPjkzl(df1.format(everyDuanInfo.getGoAndBack_kzl()));
					if(everyDuanInfo.getGoAndBack_kzl()>0){
						kzl = kzl + everyDuanInfo.getGoAndBack_kzl();
						kzlindex++;
					}
					salesReport.setAvg_Dct(df1.format(everyDuanInfo.getGoAndBack_zk()));
					if(everyDuanInfo.getGoAndBack_zk()>0){
						zk = zk + everyDuanInfo.getGoAndBack_zk();
						zkindex++;
					}
					salesReport.setGrp_Dct(df1.format(everyDuanInfo.getGoAndBack_tdzk()));
					if(everyDuanInfo.getGoAndBack_tdzk()>0){
						tdzk = tdzk + everyDuanInfo.getGoAndBack_tdzk();
						tdzkindex++;
					}
					salesReport.setWak_tol_Nbr(df1.format(everyDuanInfo.getGoAndBack_rs()));
					stzrs = stzrs + everyDuanInfo.getGoAndBack_rs();
					salesReport.setGrp_Nbr(df1.format(everyDuanInfo.getGoAndBack_tdrs()));
					skzrs = skzrs + (everyDuanInfo.getGoAndBack_rs() - everyDuanInfo.getGoAndBack_tdrs());
					salesReport.setGrp_Ine(df1.format(everyDuanInfo.getGoAndBack_tdsr()));
					tdsr = tdsr + everyDuanInfo.getGoAndBack_tdsr();
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(dayarr[1]+"."+dayarr[2],salesReport);
					}
				}
				retMap.put("data", objMap);
				retMap.put("hbys", df2.format(zys));
				retMap.put("allBc", allBc);
				retMap.put("tdsr", df2.format(tdsr));
				if(xsysindex>0){
					retMap.put("xsys", df2.format(dayxxysh/xsysindex));
				}else{
					retMap.put("xsys", "0.00");
				}
				if(zglindex>0){
					retMap.put("zgl", df2.format(dayzgls/zglindex));
				}else{
					retMap.put("zgl", "0.00");
				}
				if(kzlindex>0){
					retMap.put("kzl", df2.format(kzl/kzlindex));
				}else{
					retMap.put("kzl", "0.00");
				}
				if(zkindex>0){
					retMap.put("zk", df2.format(zk/zkindex));
				}else{
					retMap.put("zk", "0.00");
				}
				if(tdzkindex>0){
					retMap.put("tdzk", df2.format(tdzk/tdzkindex));
				}else{
					retMap.put("tdzk", "0.00");
				}
				retMap.put("stzrs", stzrs-skzrs);
				retMap.put("skzrs", skzrs);
			}
			if(daysold!=null&&daysold.size()>0){
				for (String day : daysold) {
					SalesReport salesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					//每一天的航班营收
					salesReport.setHbys(df1.format(everyDuanInfo.getGoAndBack_sr()));
					//每一天的客座率
					salesReport.setPjkzl(df1.format(everyDuanInfo.getGoAndBack_kzl()));
					//每一天的平均折扣
					salesReport.setAvg_Dct(df1.format(everyDuanInfo.getGoAndBack_zk()));
					salesReport.setGrp_Dct(df1.format(everyDuanInfo.getGoAndBack_tdzk()));
					salesReport.setWak_tol_Nbr(df1.format(everyDuanInfo.getGoAndBack_rs()));
					salesReport.setGrp_Nbr(df1.format(everyDuanInfo.getGoAndBack_tdrs()));
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMapOld.put(dayarr[1]+"."+dayarr[2],salesReport);		
					}
				}
				retMap.put("olddata", objMapOld);
			}
		}
		//历史表格数据
		if(days!=null&&days.size()>0){
			double zys = 0.0;
			double kzl = 0.0;
			double zk = 0.0;
			double tdzk = 0.0;
			double stzrs = 0.0;
			double skzrs = 0.0;
			double dayzgls = 0.0;
			double dayxxysh = 0.0;
			double tdsr = 0.0;
			int xsysindex = 0;
			int zglindex = 0;
			int kzlindex = 0;
			int zkindex = 0;
			int tdzkindex = 0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, Object> oldexcelMap = new TreeMap<String, Object>();
			if(daysold!=null&&daysold.size()>0){
				for (String day : daysold) {
					SalesReport salesReport = new SalesReport();
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					salesReport.setXssr(df1.format(everyDuanInfo.getGoAndBack_xssr()));
					if(everyDuanInfo.getGoAndBack_xssr()>0){
						dayxxysh = dayxxysh + everyDuanInfo.getGoAndBack_xssr();
						xsysindex++;
					}
					salesReport.setHbys(df1.format(everyDuanInfo.getGoAndBack_sr()));
					zys = zys + everyDuanInfo.getGoAndBack_sr();
					salesReport.setSet_Ktr_Ine(df1.format(everyDuanInfo.getGoAndBack_zgl()));
					if(everyDuanInfo.getGoAndBack_zgl()>0){
						dayzgls = dayzgls + everyDuanInfo.getGoAndBack_zgl();
						zglindex++;
					}
					salesReport.setPjkzl(df1.format(everyDuanInfo.getGoAndBack_kzl()));
					if(everyDuanInfo.getGoAndBack_kzl()>0){
						kzl = kzl + everyDuanInfo.getGoAndBack_kzl();
						kzlindex++;
					}
					salesReport.setAvg_Dct(df1.format(everyDuanInfo.getGoAndBack_zk()));
					if(everyDuanInfo.getGoAndBack_zk()>0){
						zk = zk + everyDuanInfo.getGoAndBack_zk();
						zkindex++;
					}
					salesReport.setGrp_Dct(df1.format(everyDuanInfo.getGoAndBack_tdzk()));
					if(everyDuanInfo.getGoAndBack_tdzk()>0){
						tdzk = tdzk + everyDuanInfo.getGoAndBack_tdzk();
						tdzkindex++;
					}
					salesReport.setWak_tol_Nbr(df1.format(everyDuanInfo.getGoAndBack_rs()));
					stzrs = stzrs + everyDuanInfo.getGoAndBack_rs();
					salesReport.setGrp_Nbr(df1.format(everyDuanInfo.getGoAndBack_tdrs()));
					skzrs = skzrs + (everyDuanInfo.getGoAndBack_rs() - everyDuanInfo.getGoAndBack_tdrs());
					salesReport.setGrp_Ine(df1.format(everyDuanInfo.getGoAndBack_tdsr()));
					tdsr = tdsr + everyDuanInfo.getGoAndBack_tdsr();
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(dayarr[1]+"."+dayarr[2],salesReport);
					}
				}
				oldexcelMap.put("data", objMap);
				oldexcelMap.put("hbys", df2.format(zys));
				oldexcelMap.put("allBc", allBc);
				oldexcelMap.put("tdsr", df2.format(tdsr));
				if(xsysindex>0){
					oldexcelMap.put("xsys", df2.format(dayxxysh/xsysindex));
				}else{
					oldexcelMap.put("xsys", "0.00");
				}
				if(zglindex>0){
					oldexcelMap.put("zgl", df2.format(dayzgls/zglindex));
				}else{
					oldexcelMap.put("zgl", "0.00");
				}
				if(kzlindex>0){
					oldexcelMap.put("kzl", df2.format(kzl/kzlindex));
				}else{
					oldexcelMap.put("kzl", "0.00");
				}
				if(zkindex>0){
					oldexcelMap.put("zk", df2.format(zk/zkindex));
				}else{
					oldexcelMap.put("zk", "0.00");
				}
				if(zkindex>0){
					oldexcelMap.put("tdzk", df2.format(tdzk/tdzkindex));
				}else{
					oldexcelMap.put("tdzk", "0.00");
				}
				oldexcelMap.put("stzrs", stzrs-skzrs);
				oldexcelMap.put("skzrs", skzrs);
				retMap.put("oldexcelMap", oldexcelMap);
			}
		}
		List<SalesReportDayInfo> SalesReportDayInfoList = new ArrayList<SalesReportDayInfo>();
		if(TextUtil.isEmpty(pas_stpCode)){
			//直飞
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getMonthReportIncomeInfoData(false,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(arrv_Airpt_CdCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getMonthReportIncomeInfoData(false,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
		}else{
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(pas_stpCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+pas_stpCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getMonthReportIncomeInfoData(false,dpt_AirPt_CdCode,pas_stpCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(pas_stpCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getMonthReportIncomeInfoData(false,pas_stpCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
			
			SalesReportDayInfo salesReportDayInfo4 = new SalesReportDayInfo();
			salesReportDayInfo4.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo4.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB3 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB3.add(z_Airdata);
				}
			}
			salesReportDayInfo4.setDataMap(getMonthReportIncomeInfoData(true,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB3,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo4);
			
			SalesReportDayInfo salesReportDayInfo5 = new SalesReportDayInfo();
			salesReportDayInfo5.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo5.setFlyCode(arrv_Airpt_CdCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB4 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB4.add(z_Airdata);
				}
			}
			salesReportDayInfo5.setDataMap(getMonthReportIncomeInfoData(true,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB4,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo5);
			
			SalesReportDayInfo salesReportDayInfo6 = new SalesReportDayInfo();
			salesReportDayInfo6.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo6.setFlyCode(pas_stpCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB5 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB5.add(z_Airdata);
				}
			}
			salesReportDayInfo6.setDataMap(getMonthReportIncomeInfoData(false,pas_stpCode,arrv_Airpt_CdCode,zairdataListB5,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo6);
			
			SalesReportDayInfo salesReportDayInfo7 = new SalesReportDayInfo();
			salesReportDayInfo7.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo7.setFlyCode(arrv_Airpt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB6 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB6.add(z_Airdata);
				}
			}
			salesReportDayInfo7.setDataMap(getMonthReportIncomeInfoData(false,arrv_Airpt_CdCode,pas_stpCode,zairdataListB6,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,days,daysold,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo7);
			
		}
		Map<String ,Object> rettm = new HashMap<String ,Object>();
		rettm.put("everyList", SalesReportDayInfoList);
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		return rettm;
	}
	public Map<String,Object> getMonthReportIncomeInfoData(boolean isLong ,String dptCode,String arrCode,List<Z_Airdata> zairdataListAll,List<Z_Airdata> thisWeekZairdataListAllNew,List<Z_Airdata> lastWeekZairdataListAllNew,List<String> days,List<String> daysold,SalesReportQuery salesReportQuery) {
		//是否包含异常数据
		String exceptionFlag = "1";
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(zairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "single_false";
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		String dataFlage2 = (String) dataMap2.get("flage");
		
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		boolean flage = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		double allBc = TextUtil.getExecClass(thisWeekZairdataListAllNew);
		Map<String, Object> retMap = new TreeMap<String, Object>();
		//直飞
		double zys = 0.0;
		double kzl = 0.0;
		double zk = 0.0;
		double tdzk = 0.0;
		double stzrs = 0.0;
		double skzrs = 0.0;
		double fxsj = 0.0;
		double zws = 0.0;
		double hj = 0.0;
		double dayzgls = 0.0;
		double dayxxysh = 0.0;
		double tdsr = 0.0;
		int length = days.size();
		Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
		Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
		if(days!=null&&days.size()>0){
			for (String day : days) {
				Double dayhbys = 0.0;
				Double daytimes = 0.0;
				Double dayxsys = 0.0;
				Double dayzgl = 0.0;
				Double dayzwsall = 0.0;
				Double dayhjsingle = 0.0;
				Double daykzl = 0.0;
				Double daykzlall = 0.0;
				Double dayzk = 0.0;
				Double daytdzk = 0.0;
				Double dayzkall = 0.0;
				Double daytdzkall = 0.0;
				Double dayskperson = 0.0;
				Double daytdperson = 0.0;
				Double daytdsr = 0.0;
				int index = 0;
				int tdindex = 0;
				SalesReport salesReport = new SalesReport();
				int dateIndex = 0;
				for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
					if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if((dpt.equals(dptCode)&&arr.equals(arrCode))){
							flage = true;
							//每一天的航班营收之和
							dayhbys = dayhbys + z_Airdata.getTotalNumber();
							dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
							dayhjsingle = (double) z_Airdata.getSailingDistance();
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
							}else{
								daytimes =daytimes + ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
							}
							if(daytimes<0){
								daytimes = daytimes + 24;
							}
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();

							//每一天的团队折扣之和
							if(z_Airdata.getGrp_Dct()!=null&&z_Airdata.getGrp_Dct().doubleValue()>0){
								tdindex++;
								daytdzkall = daytdzkall + z_Airdata.getGrp_Dct().doubleValue();
							}
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							daytdsr = daytdsr + z_Airdata.getGrp_Ine().doubleValue();
							index ++;
						}
					}
				}
				fxsj =fxsj + daytimes ; 
				zys = zys + dayhbys;
				tdsr = tdsr + daytdsr;
				stzrs = stzrs  + daytdperson;
				skzrs = skzrs + dayskperson ;
				
				//往返座位数之和
				zws = zws + dayzwsall;
				if(dayhbys<=0.0){dayhbys=0.0;}
				if(daytimes<=0.0){
					dayxsys = 0.0;
					length -- ;
				}else{
					dayxsys = dayhbys / daytimes;
					dayxxysh = dayxsys + dayxxysh;
				}
				if(dayzwsall<=0.0){dayzwsall=1.0;}
				if(dayhjsingle<=0.0){dayhjsingle=1.0;}
				if(index<=0){index=1;}
				salesReport.setXssr(df1.format(dayxsys));
				salesReport.setHbys(df1.format(dayhbys));
				hj = dayhjsingle;
				//每一天的座公里
				dayzgl = dayhbys / dayzwsall / dayhjsingle;
				salesReport.setSet_Ktr_Ine(df1.format(dayzgl));
				dayzgls = dayzgls + dayzgl;
				//每一天的客座率
				daykzl = daykzlall / index; 
				salesReport.setPjkzl(df1.format(daykzl));
				//每一天的平均折扣
				dayzk = dayzkall / index;
				salesReport.setAvg_Dct(df1.format(dayzk));
				if(tdindex>0){
					daytdzk = daytdzkall / tdindex;
				}else{
					daytdzk = daytdzkall ;
				}
				salesReport.setGrp_Dct(df1.format(daytdzk));
				salesReport.setWak_tol_Nbr(dayskperson+"");
				salesReport.setGrp_Nbr(daytdperson+"");
				salesReport.setGrp_Ine(daytdsr+"");
				kzl = kzl + daykzl; 
				zk = zk + dayzk;
				tdzk = tdzk + daytdzk;
				String [] dayarr = day.split("-");
				Date dd = new Date();
				try {
					dd = sdf.parse(day);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(Double.parseDouble(salesReport.getHbys())>0){
					objMap.put(dayarr[1]+"."+dayarr[2],salesReport);
				}
			}
			//航班营收合计
			retMap.put("data", objMap);
			retMap.put("hasData", flage);
			retMap.put("hbys", df2.format(zys));
			retMap.put("tdsr", df2.format(tdsr));
			if(fxsj<=0.0){fxsj=1;}
			if(length>0){
				retMap.put("xsys", df2.format(dayxxysh/length));
			}else{
				retMap.put("xsys", df2.format(0.00));
			}
			if(zws<=0.0){zws=1;}
				retMap.put("zgl", df1.format(dayzgls/allBc));
				retMap.put("kzl", df1.format(kzl/allBc));
				retMap.put("zk", df1.format(zk/allBc));
				retMap.put("tdzk", df1.format(tdzk/allBc));
				retMap.put("stzrs", stzrs);
				retMap.put("skzrs", skzrs);
			}
			if(daysold!=null&&daysold.size()>0){
			for (String day : daysold) {
				Double dayhbys = 0.0;
				Double daykzl = 0.0;
				Double daykzlall = 0.0;
				Double dayzk = 0.0;
				Double dayzkall = 0.0;
				Double dayskperson = 0.0;
				Double daytdperson = 0.0;
				int index = 0;
				Double daytdzk = 0.0;
				Double daytdzkall = 0.0;
				int tdindex = 0;
				SalesReport salesReport = new SalesReport();
				for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
					if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if((dpt.equals(dptCode)&&arr.equals(arrCode))){
							//每一天的航班营收之和
							dayhbys = dayhbys + z_Airdata.getTotalNumber();
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
							//每一天的团队折扣之和
							if(z_Airdata.getGrp_Dct()!=null&&z_Airdata.getGrp_Dct().doubleValue()>0){
								tdindex++;
								daytdzkall = daytdzkall + z_Airdata.getGrp_Dct().doubleValue();
							}
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							index ++;
						}
					}
					
				}
				if(dayhbys<=0.0){dayhbys=0.0;}
				if(index<=0){index=1;}
				//每一天的小时营收
				salesReport.setHbys(df1.format(dayhbys));
				//每一天的客座率
				daykzl = daykzlall / index; 
				salesReport.setPjkzl(df1.format(daykzl));
				//每一天的平均折扣
				dayzk = dayzkall / index;
				salesReport.setAvg_Dct(df1.format(dayzk));
				if(tdindex>0){
					daytdzk = daytdzkall / tdindex;
				}else{
					daytdzk = daytdzkall ;
				}
				salesReport.setGrp_Dct(df1.format(daytdzk));
				salesReport.setWak_tol_Nbr(dayskperson+"");
				salesReport.setGrp_Nbr(daytdperson+"");
				String [] dayarr = day.split("-");
				Date dd = new Date();
				try {
					dd = sdf.parse(day);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(Double.parseDouble(salesReport.getHbys())>0){
					objMapOld.put(dayarr[1]+"."+dayarr[2],salesReport);
				}
			}
			retMap.put("olddata", objMapOld);
		}
		retMap.put("exceptionFlag", exceptionFlag);
		return retMap;
	}
	@Override
	public Map<String, Object> getMonthReportIncomeInfo(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		List<String> days = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		double allBc = TextUtil.getExecClass(thisWeekZairdataListAll);
		salesReportQuery.setStartTime(TextUtil.addDateMonth(salesReportQuery.getStartTime(), -1)+"-01");
		salesReportQuery.setEndTime(TextUtil.addDateMonth(salesReportQuery.getEndTime(), -1)+"-31");
		List<String> daysold = salesReportServiceMapper.getWeekIncomeInfo(salesReportQuery);
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		
		//是否包含异常数据
		List<Z_Airdata> thisWeekZairdataListAllNew = getIsIncludeExceptionData(thisWeekZairdataListAll,salesReportQuery);
		List<Z_Airdata> lastWeekZairdataListAllNew = getIsIncludeExceptionData(lastWeekZairdataListAll,salesReportQuery);
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(!TextUtil.isEmpty(pas_stpCode)){
			double zys = 0.0;
			double kzl = 0.0;
			double zk = 0.0;
			double stzrs = 0.0;
			double skzrs = 0.0;
			double fxsj = 0.0;
			double zws = 0.0;
			double hj = 0.0;
			double dayzgls = 0.0;
			double dayxxysh = 0.0;
			int length = days.size();
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(days!=null&&days.size()>0){
				for (String day : days) {
					Double dayhbys = 0.0;
					Double daytimes = 0.0;
					Double dayxsys = 0.0;
					Double dayzgl = 0.0;
					Double dayzwsq = 0.0;
					Double dayzwsh = 0.0;
					Double dayzwsall = 0.0;
					Double dayhjsingle = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double dayzk = 0.0;
					Double dayzkall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					int index = 0;
					double zgl = 0.0;
					SalesReport salesReport = new SalesReport();
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							//每一天的航班营收之和
							if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								zgl = zgl + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
							}
							dayhbys = dayhbys + z_Airdata.getTotalNumber();
							String dpt = z_Airdata.getDpt_AirPt_Cd();
							String arr = z_Airdata.getArrv_Airpt_Cd();
							if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
								//去程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								dayzwsq = (double) z_Airdata.getCount_Set();
								//短段航距之和
								dayhjsingle = dayhjsingle + z_Airdata.getSailingDistance();
							}
							if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
								//回程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								dayzwsh = (double) z_Airdata.getCount_Set();
							}
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							index ++;
						}
					}
					fxsj =fxsj + daytimes ; 
					zys = zys + dayhbys;
					stzrs = stzrs  + daytdperson;
					skzrs = skzrs + dayskperson ;
					
					//往返座位数之和
					dayzwsall = dayzwsq+dayzwsh;
					zws = zws + dayzwsall;
					if(dayhbys<=0.0){dayhbys=0.0;}
					if(daytimes<=0.0){
						dayxsys = 0.0;
						length -- ;
					}else{
						dayxsys = dayhbys / daytimes;
						dayxxysh = dayxsys + dayxxysh;
					}
					if(dayzwsall<=0.0){dayzwsall=1.0;}
					if(dayhjsingle<=0.0){dayhjsingle=1.0;}
					if(index<=0){index=1;}
					//每一天的小时营收
					salesReport.setXssr(df1.format(dayxsys));
					salesReport.setHbys(df1.format(dayhbys));
					hj = dayhjsingle;
					//每一天的座公里
					if(index<6||dayzwsall<=1){
						dayzgl = zgl/index;
					}else{
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
					}
					salesReport.setSet_Ktr_Ine(df1.format(dayzgl));
					dayzgls = dayzgls + dayzgl;
					//每一天的客座率
					daykzl = daykzlall / index; 
					salesReport.setPjkzl(df1.format(daykzl));
					//每一天的平均折扣
					dayzk = dayzkall / index;
					salesReport.setAvg_Dct(df1.format(dayzk));
					salesReport.setWak_tol_Nbr(dayskperson+"");
					salesReport.setGrp_Nbr(daytdperson+"");
					kzl = kzl + daykzl; 
					zk = zk + dayzk;
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(dayarr[1]+"."+dayarr[2],salesReport);
					}
				}
				//航班营收合计
				retMap.put("data", objMap);
				retMap.put("hbys", df2.format(zys));
				if(fxsj<=0.0){fxsj=1;}
				if(length>0){
					retMap.put("xsys", df2.format(dayxxysh/length));
				}else{
					retMap.put("xsys", df2.format(0.00));
				}
				
				if(zws<=0.0){zws=1;}
				retMap.put("zgl", df1.format(dayzgls/allBc));
				retMap.put("kzl", df1.format(kzl/allBc));
				retMap.put("zk", df1.format(zk/allBc));
				retMap.put("stzrs", stzrs);
				retMap.put("skzrs", skzrs);
				}
				if(daysold!=null&&daysold.size()>0){
				for (String day : daysold) {
					Double dayhbys = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double dayzk = 0.0;
					Double dayzkall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					int index = 0;
					SalesReport salesReport = new SalesReport();
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							//每一天的航班营收之和
							dayhbys = dayhbys + z_Airdata.getTotalNumber();
							//每一天的综合客座率之和
							if(z_Airdata.getTal_Nbr_Set()>0){
								daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
							}
							//每一天的折扣之和
							dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
							//每一天的散客人数
							dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
							//每一天的团队人数
							daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
							index ++;
						}
						
					}
					if(dayhbys<=0.0){dayhbys=0.0;}
					if(index<=0){index=1;}
					//每一天的小时营收
					salesReport.setHbys(df1.format(dayhbys));
					//每一天的客座率
					daykzl = daykzlall / index; 
					salesReport.setPjkzl(df1.format(daykzl));
					//每一天的平均折扣
					dayzk = dayzkall / index;
					salesReport.setAvg_Dct(df1.format(dayzk));
					salesReport.setWak_tol_Nbr(dayskperson+"");
					salesReport.setGrp_Nbr(daytdperson+"");
					String [] dayarr = day.split("-");
					Date dd = new Date();
					try {
						dd = sdf.parse(day);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMapOld.put(dayarr[1]+"."+dayarr[2],salesReport);		
					}
				}
				retMap.put("olddata", objMapOld);
			}
			}else{
				//直飞
				double zys = 0.0;
				double kzl = 0.0;
				double zk = 0.0;
				double stzrs = 0.0;
				double skzrs = 0.0;
				double fxsj = 0.0;
				double zws = 0.0;
				double hj = 0.0;
				double dayzgls = 0.0;
				double dayxxysh = 0.0;
				int length = days.size();
				Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
				Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
				if(days!=null&&days.size()>0){
					for (String day : days) {
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayzk = 0.0;
						Double dayzkall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						int index = 0;
						SalesReport salesReport = new SalesReport();
						for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								if(z_Airdata.getCount_Set()<=0){
									dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								}else{
									dayzwsall = dayzwsall + (double) z_Airdata.getCount_Set();
								}
								dayhjsingle = (double) z_Airdata.getSailingDistance();
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的折扣之和
								dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						fxsj =fxsj + daytimes ; 
						zys = zys + dayhbys;
						stzrs = stzrs  + daytdperson;
						skzrs = skzrs + dayskperson ;
						
						//往返座位数之和
						zws = zws + dayzwsall;
						if(dayhbys<=0.0){dayhbys=0.0;}
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length -- ;
						}else{
							dayxsys = dayhbys / daytimes;
							dayxxysh = dayxsys + dayxxysh;
						}
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){dayhjsingle=1.0;}
						if(index<=0){index=1;}
						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
						salesReport.setXssr(df1.format(dayxsys));
						salesReport.setHbys(df1.format(dayhbys));
						hj = dayhjsingle;
						//每一天的座公里
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
						salesReport.setSet_Ktr_Ine(df1.format(dayzgl));
						dayzgls = dayzgls + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						salesReport.setPjkzl(df1.format(daykzl));
						//每一天的平均折扣
						dayzk = dayzkall / index;
						salesReport.setAvg_Dct(df1.format(dayzk));
						salesReport.setWak_tol_Nbr(dayskperson+"");
						salesReport.setGrp_Nbr(daytdperson+"");
						kzl = kzl + daykzl; 
						zk = zk + dayzk;
						String [] dayarr = day.split("-");
						Date dd = new Date();
						try {
							dd = sdf.parse(day);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if(Double.parseDouble(salesReport.getHbys())>0){
							objMap.put(dayarr[1]+"."+dayarr[2],salesReport);
						}
					}
					//航班营收合计
					retMap.put("data", objMap);
					retMap.put("hbys", df2.format(zys));
					if(fxsj<=0.0){fxsj=1;}
					if(length>0){
						retMap.put("xsys", df2.format(dayxxysh/length));
					}else{
						retMap.put("xsys", df2.format(0.00));
					}
					if(zws<=0.0){zws=1;}
					retMap.put("zgl", df1.format(dayzgls/allBc));
					retMap.put("kzl", df1.format(kzl/allBc));
					retMap.put("zk", df1.format(zk/allBc));
					retMap.put("stzrs", stzrs);
					retMap.put("skzrs", skzrs);
					}
					if(daysold!=null&&daysold.size()>0){
					for (String day : daysold) {
						Double dayhbys = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayzk = 0.0;
						Double dayzkall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						int index = 0;
						SalesReport salesReport = new SalesReport();
						for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的折扣之和
								dayzkall = dayzkall + z_Airdata.getAvg_Dct().doubleValue();
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
							
						}
						if(dayhbys<=0.0){dayhbys=0.0;}
						if(index<=0){index=1;}
						//每一天的小时营收
						salesReport.setHbys(df1.format(dayhbys));
						//每一天的客座率
						daykzl = daykzlall / index; 
						salesReport.setPjkzl(df1.format(daykzl));
						//每一天的平均折扣
						dayzk = dayzkall / index;
						salesReport.setAvg_Dct(df1.format(dayzk));
						salesReport.setWak_tol_Nbr(dayskperson+"");
						salesReport.setGrp_Nbr(daytdperson+"");
						String [] dayarr = day.split("-");
						Date dd = new Date();
						try {
							dd = sdf.parse(day);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						if(Double.parseDouble(salesReport.getHbys())>0){
							objMapOld.put(dayarr[1]+"."+dayarr[2],salesReport);
						}
					}
					retMap.put("olddata", objMapOld);
				}
		}
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		return retMap;
	
	}
	@Override
	public String getPlanClass(SalesReportQuery salesReportQuery) {
		//这儿应该调用张科的接口，但系统中现在没有使用这个值了，暂时没写。http://192.168.11.11:5000/fcz_aircraft_schedule/get_news_days_by_field/
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		salesReportQuery.setDpt_AirPt_Cd(outPortMapper.getNameByCode(dpt_AirPt_CdCode));
		salesReportQuery.setArrv_Airpt_Cd(outPortMapper.getNameByCode(arrv_Airpt_CdCode));
		String gonum = salesReportQuery.getGoNum();
		String huinum = salesReportQuery.getHuiNum();
		Double calsses = 0.0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		int dayss = TextUtil.daysBetween(startTime, endTime);
		Gson gson=new Gson();
		if(!TextUtil.isEmpty(gonum)&&!TextUtil.isEmpty(huinum)){
			//往返：两个单程的班次求平均
			List<String> plans1  = new ArrayList<String>();
			salesReportQuery.setTempNum(gonum);
			plans1 = salesReportServiceMapper.getPlanClass(salesReportQuery);
			List<String> plans2  = new ArrayList<String>();
			salesReportQuery.setTempNum(huinum);
			plans2 = salesReportServiceMapper.getPlanClass(salesReportQuery);
			String lastClassq = "";
			String lastClassh = "";
			for (String str : plans1) {
				lastClassq = lastClassq + str;
			}
			//去除重复的班期
			String banqiCharq = TextUtil.removeRepeated(lastClassq);
			for (String str : plans2) {
				lastClassh = lastClassh + str;
			}
			//去除重复的班期
			String banqiCharh = TextUtil.removeRepeated(lastClassh);
			double calssesq = 0.0;
			double calssesh = 0.0;
			for(int i=0;i<dayss;i++){
				String nowTime = TextUtil.addDateDay(startTime, i);
				try {
					String weekTime = TextUtil.getWeekOfDate3(df.parse(nowTime));
					if(banqiCharq.contains(weekTime)){
						calssesq = calssesq+1;
					}
					if(banqiCharh.contains(weekTime)){
						calssesh = calssesh+1;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			calsses = (double) (calssesq+calssesh)/2;
		}else{
			List<String> plans  = new ArrayList<String>();
			if(!TextUtil.isEmpty(gonum)){
				//单程：一个单程的班次
				salesReportQuery.setTempNum(gonum);
				plans = salesReportServiceMapper.getPlanClass(salesReportQuery);
			}
			if(!TextUtil.isEmpty(huinum)){
				//单程：一个单程的班次
				salesReportQuery.setTempNum(huinum);
				plans = salesReportServiceMapper.getPlanClass(salesReportQuery);
			}
			String lastClass = "";
			for (String str : plans) {
				lastClass = lastClass + str;
			}
			//去除重复的班期
			String banqiChar = TextUtil.removeRepeated(lastClass);
			for(int i=0;i<dayss;i++){
				String nowTime = TextUtil.addDateDay(startTime, i);
				try {
					String weekTime = TextUtil.getWeekOfDate3(df.parse(nowTime));
					if(banqiChar.contains(weekTime)){
						calsses = calsses+1;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		return calsses+"";
	}
	@Override
	public String getExecutiveClass(SalesReportQuery salesReportQuery) {
		//得到某段时间有数据的日期
		String gonum = salesReportQuery.getGoNum();
		String huinum = salesReportQuery.getHuiNum();
		Double calsses = 0.0;
		if(!TextUtil.isEmpty(gonum)&&!TextUtil.isEmpty(huinum)){
			//往返：两个单程的班次求平均
			List<String> plans1  = new ArrayList<String>();
			salesReportQuery.setTempNum(gonum);
			plans1 = salesReportServiceMapper.getHavingDataDayListBySingleFlyNum(salesReportQuery);
			List<String> plans2  = new ArrayList<String>();
			List<String> banciList  = new ArrayList<String>();
			salesReportQuery.setTempNum(huinum);
			plans2 = salesReportServiceMapper.getHavingDataDayListBySingleFlyNum(salesReportQuery);
//			if(plans1.size()>0&&plans2.size()>0){
//				calsses = (double)(plans1.size()+plans2.size())/2.0;
//			}else{
//				if(plans1.size()>0){
//					calsses = (double)plans1.size();
//				}else{
//					calsses = (double)plans2.size();
//				}
//			}
			banciList.addAll(plans1);
			for (String str : plans2) {
				if(!banciList.contains(str)){
					banciList.add(str);	
				}
			}
			calsses = (double)banciList.size();
		}else{
			List<String> plans  = new ArrayList<String>();
			if(!TextUtil.isEmpty(gonum)){
				//单程：一个单程的班次
				salesReportQuery.setTempNum(gonum);
				plans = salesReportServiceMapper.getHavingDataDayListBySingleFlyNum(salesReportQuery);
			}
			if(!TextUtil.isEmpty(huinum)){
				//单程：一个单程的班次
				salesReportQuery.setTempNum(huinum);
				plans = salesReportServiceMapper.getHavingDataDayListBySingleFlyNum(salesReportQuery);
			}
			calsses = (double) plans.size();
		}
		
		return calsses+"";
	}
	@Override
	public Map<String, Object> getHalfYearReportIncomeInfo_New(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		//得到当前航季度的年、月份
		List<String> datenowList = new ArrayList<String>();
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//设置上一年的航季
		Map<String,String> dateMap = new HashMap<String,String>();
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			String [] str = stratTime.split("-");
			Map<String,String> dateMaptemp = new HashMap<String,String>();
			dateMaptemp = TextUtil.getDCDate(Integer.parseInt(str[0]));
			String st = dateMaptemp.get("startTime");
			if(st.equals(stratTime)){
				//冬春航季
				dateMap = TextUtil.getDCDate(Integer.parseInt(str[0])-1);
			}else{
				//夏秋航季
				dateMap = TextUtil.getXQDate(Integer.parseInt(str[0])-1);
			}
		}
		salesReportQuery.setStartTime(dateMap.get("startTime"));
		salesReportQuery.setEndTime(dateMap.get("endTime"));
		List<String> dateOldList = new ArrayList<String>();
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//得到当前航季的年月列表
		String [] strstart = null;
		String [] endstart = null;
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			strstart = stratTime.split("-");
			endstart = endTime.split("-");
		}
		if(strstart!=null&&endstart!=null){
			if(Integer.parseInt(endstart[0])>Integer.parseInt(strstart[0])){
				//冬春航季
				datenowList.add(strstart[0]+"10");
				datenowList.add(strstart[0]+"11");
				datenowList.add(strstart[0]+"12");
				datenowList.add(endstart[0]+"01");
				datenowList.add(endstart[0]+"02");
				datenowList.add(endstart[0]+"03");
			}else{
				//夏秋航季
				datenowList.add(strstart[0]+"03");
				datenowList.add(strstart[0]+"04");
				datenowList.add(strstart[0]+"05");
				datenowList.add(strstart[0]+"06");
				datenowList.add(strstart[0]+"07");
				datenowList.add(strstart[0]+"08");
				datenowList.add(strstart[0]+"09");
				datenowList.add(strstart[0]+"10");
			}
		}
		//得到上一年的航季的年月列表
		String stratTimeold = salesReportQuery.getStartTime();
		String endTimeold = salesReportQuery.getEndTime();
		String [] strstartold = null;
		String [] endstartold = null;
		if(!TextUtil.isEmpty(stratTimeold)&&!TextUtil.isEmpty(endTimeold)){
			strstartold = stratTimeold.split("-");
			endstartold = endTimeold.split("-");
		}
		if(strstartold!=null&&endstartold!=null){
			if(Integer.parseInt(endstartold[0])>Integer.parseInt(strstartold[0])){
				//冬春航季
				dateOldList.add(strstartold[0]+"10");
				dateOldList.add(strstartold[0]+"11");
				dateOldList.add(strstartold[0]+"12");
				dateOldList.add(endstartold[0]+"01");
				dateOldList.add(endstartold[0]+"02");
				dateOldList.add(endstartold[0]+"03");
			}else{
				//夏秋航季
				dateOldList.add(strstartold[0]+"03");
				dateOldList.add(strstartold[0]+"04");
				dateOldList.add(strstartold[0]+"05");
				dateOldList.add(strstartold[0]+"06");
				dateOldList.add(strstartold[0]+"07");
				dateOldList.add(strstartold[0]+"08");
				dateOldList.add(strstartold[0]+"09");
				dateOldList.add(strstartold[0]+"10");
			}
		}
		//是否包含异常数据
		
		String exceptionFlag = "1";
		List<Z_Airdata> thisWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		List<Z_Airdata> lastWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(thisWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "true";
		if(TextUtil.isEmpty(salesReportQuery.getGoNum())||TextUtil.isEmpty(salesReportQuery.getHuiNum())){
			strrfalge = "false";
		}
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		thisWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2.get("zairdataListB");
		String dataFlage2 = (String) dataMap2.get("flage");
		
		//历史数据判断异常数据
		Map<String,Object> dataMapp = TextUtil.getIsIncludeExceptionData(lastWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1p = (List<Z_Airdata>) dataMapp.get("zairdataListB");
		//是否包含异常航段
		String strrfalgep = "true";
		if(TextUtil.isEmpty(salesReportQuery.getGoNum())||TextUtil.isEmpty(salesReportQuery.getHuiNum())){
			strrfalgep = "false";
		}
		Map<String,Object> dataMap2p = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1p, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalgep);
		lastWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2p.get("zairdataListB");
		
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(true){
			double zys = 0.0;
			double kzl = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			double pjzk = 0.0;
			double pjtdzk = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(datenowList!=null&&datenowList.size()>0){
				int xsysindexmonth =0;
				int zglindexmonth =0;
				int kzlindexmonth =0;
				int zkindexmonth =0;
				int tdzkindexmonth =0;
				int personindexmonth =0;
				for (String month : datenowList) {
					List<Z_Airdata> tempListgo = new ArrayList<Z_Airdata>(); 
					List<Z_Airdata> tempListback = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getGoNum().equals(z_Airdata.getFlt_Nbr())){
							tempListgo.add(z_Airdata);
						}
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getHuiNum().equals(z_Airdata.getFlt_Nbr())){
							tempListback.add(z_Airdata);
						}
					}
					if((tempListgo.size()>0&&tempListback.size()>0)&&(!TextUtil.isEmpty(salesReportQuery.getGoNum())&&!TextUtil.isEmpty(salesReportQuery.getHuiNum()))){
						List<String> dyss = new ArrayList<String>();
						for (Z_Airdata z1 : tempListgo) {
							if(!dyss.contains(sdf.format(z1.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z1.getLcl_Dpt_Day()));
							}
						}
						for (Z_Airdata z2 : tempListback) {
							if(!dyss.contains(sdf.format(z2.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z2.getLcl_Dpt_Day()));
							}
						}
						monthClases = (double) dyss.size();
					}else{
						if(tempListgo.size()>0){
							monthClases = TextUtil.getExecClass(tempListgo);
						}else if(tempListback.size()>0){
							monthClases = TextUtil.getExecClass(tempListback);
						}
					}
					Double monthhbys = 0.0;
					Double monthxsys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					Double monthpjzk = 0.0;
					Double monthpjtdzk = 0.0;
					int xsysindex =0;
					int zglindex =0;
					int kzlindex =0;
					int zkindex =0;
					int tdzkindex =0;
					int personindex =0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
						airLineAllInfo = TextUtil.getAirLineAllInfo(thisWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
						EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
						if(everyDuanInfo.getGoAndBack_xssr()>0){
							monthxsys = monthxsys + everyDuanInfo.getGoAndBack_xssr();
							xsysindex++;
						}
						monthhbys = monthhbys + everyDuanInfo.getGoAndBack_sr();
						if(everyDuanInfo.getGoAndBack_zgl()>0){
							monthzgl = monthzgl + everyDuanInfo.getGoAndBack_zgl();
							zglindex++;
						}
						if(everyDuanInfo.getGoAndBack_kzl()>0){
							monthkzl = monthkzl + everyDuanInfo.getGoAndBack_kzl();
							kzlindex++;
						}
						if(everyDuanInfo.getGoAndBack_zk()>0){
							monthpjzk = monthpjzk + everyDuanInfo.getGoAndBack_zk();
							zkindex++;
						}
						if(everyDuanInfo.getGoAndBack_tdzk()>0){
							monthpjtdzk = monthpjtdzk + everyDuanInfo.getGoAndBack_tdzk();
							tdzkindex++;
						}
						if(everyDuanInfo.getGoAndBack_rs()>0){
							monthperson = monthperson + everyDuanInfo.getGoAndBack_rs();
							personindex++;
						}
					}
					double bcc = monthClases;
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(xsysindex>0){
						salesReport.setXssr(df2.format(monthxsys/xsysindex));
					}else{
						salesReport.setXssr("0");
					}
					if(zglindex>0){
						salesReport.setSet_Ktr_Ine(df1.format(monthzgl/zglindex));
					}else{
						salesReport.setSet_Ktr_Ine("0");
					}
					if(kzlindex>0){
						salesReport.setPjkzl(df1.format(monthkzl/kzlindex));
					}else{
						salesReport.setPjkzl("0");
					}
					if(zkindex>0){
						salesReport.setPjzk(df1.format(monthpjzk/zkindex));
					}else{
						salesReport.setPjzk("0");
					}
					if(tdzkindex>0){
						salesReport.setGrp_Dct(df1.format(monthpjtdzk/tdzkindex));
					}else{
						salesReport.setGrp_Dct("0");
					}
					if(personindex>0){
						salesReport.setJbrs(df1.format(monthperson/personindex));
					}else{
						salesReport.setJbrs("0");
					}
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						zys = zys + Double.parseDouble(salesReport.getHbys());
					}
					if(Double.parseDouble(salesReport.getXssr())>0){
						xsys = xsys + Double.parseDouble(salesReport.getXssr());
						xsysindexmonth++;
					}
					if(Double.parseDouble(salesReport.getSet_Ktr_Ine())>0){
						zgl = zgl + Double.parseDouble(salesReport.getSet_Ktr_Ine());
						zglindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjkzl())>0){
						kzl = kzl + Double.parseDouble(salesReport.getPjkzl());
						kzlindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjzk())>0){
						pjzk = pjzk + Double.parseDouble(salesReport.getPjzk());
						zkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getGrp_Dct())>0){
						pjtdzk = pjtdzk + Double.parseDouble(salesReport.getGrp_Dct());
						tdzkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getJbrs())>0){
						jbrs = jbrs + Double.parseDouble(salesReport.getJbrs());
						personindexmonth++;
					}
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
				}
				retMap.put("monthData", objMap);
				retMap.put("zys", df2.format(zys));
				retMap.put("xsys", df2.format(xsys/xsysindexmonth));
				retMap.put("zgl", df1.format(zgl/zglindexmonth));
				retMap.put("kzl", df1.format(kzl/kzlindexmonth));
				retMap.put("zrs", zrs);
				retMap.put("zbc", df1.format(zbc));
				retMap.put("jbrs", df1.format(jbrs/personindexmonth));
				retMap.put("pjzk", df1.format(pjzk/zkindexmonth));
				retMap.put("pjtdzk", df1.format(pjtdzk/tdzkindexmonth));
			}
			//去年同期数据
			if(dateOldList!=null&&dateOldList.size()>0){
				for (String month : dateOldList) {
					List<Z_Airdata> tempListgo = new ArrayList<Z_Airdata>(); 
					List<Z_Airdata> tempListback = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getGoNum().equals(z_Airdata.getFlt_Nbr())){
							tempListgo.add(z_Airdata);
						}
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getHuiNum().equals(z_Airdata.getFlt_Nbr())){
							tempListback.add(z_Airdata);
						}
					}
					if((tempListgo.size()>0&&tempListback.size()>0)&&(!TextUtil.isEmpty(salesReportQuery.getGoNum())&&!TextUtil.isEmpty(salesReportQuery.getHuiNum()))){
						List<String> dyss = new ArrayList<String>();
						for (Z_Airdata z1 : tempListgo) {
							if(!dyss.contains(sdf.format(z1.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z1.getLcl_Dpt_Day()));
							}
						}
						for (Z_Airdata z2 : tempListback) {
							if(!dyss.contains(sdf.format(z2.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z2.getLcl_Dpt_Day()));
							}
						}
						monthClases = (double) dyss.size();
					}else{
						if(tempListgo.size()>0){
							monthClases = TextUtil.getExecClass(tempListgo);
						}else if(tempListback.size()>0){
							monthClases = TextUtil.getExecClass(tempListback);
						}
					}
					Double monthhbys = 0.0;
					Double monthxsys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					Double monthpjzk = 0.0;
					Double monthpjtdzk = 0.0;
					int xsysindex =0;
					int zglindex =0;
					int kzlindex =0;
					int zkindex =0;
					int tdzkindex =0;
					int personindex =0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
						airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
						EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
						if(everyDuanInfo.getGoAndBack_xssr()>0){
							monthxsys = monthxsys + everyDuanInfo.getGoAndBack_xssr();
							xsysindex++;
						}
						monthhbys = monthhbys + everyDuanInfo.getGoAndBack_sr();
						if(everyDuanInfo.getGoAndBack_zgl()>0){
							monthzgl = monthzgl + everyDuanInfo.getGoAndBack_zgl();
							zglindex++;
						}
						if(everyDuanInfo.getGoAndBack_kzl()>0){
							monthkzl = monthkzl + everyDuanInfo.getGoAndBack_kzl();
							kzlindex++;
						}
						if(everyDuanInfo.getGoAndBack_zk()>0){
							monthpjzk = monthpjzk + everyDuanInfo.getGoAndBack_zk();
							zkindex++;
						}
						if(everyDuanInfo.getGoAndBack_tdzk()>0){
							monthpjtdzk = monthpjtdzk + everyDuanInfo.getGoAndBack_tdzk();
							tdzkindex++;
						}
						if(everyDuanInfo.getGoAndBack_rs()>0){
							monthperson = monthperson + everyDuanInfo.getGoAndBack_rs();
							personindex++;
						}
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(xsysindex>0){
						salesReport.setXssr(df2.format(monthxsys/xsysindex));
					}else{
						salesReport.setXssr("0");
					}
					if(zglindex>0){
						salesReport.setSet_Ktr_Ine(df1.format(monthzgl/zglindex));
					}else{
						salesReport.setSet_Ktr_Ine("0");
					}
					if(kzlindex>0){
						salesReport.setPjkzl(df1.format(monthkzl/kzlindex));
					}else{
						salesReport.setPjkzl("0");
					}
					if(zkindex>0){
						salesReport.setPjzk(df1.format(monthpjzk/zkindex));
					}else{
						salesReport.setPjzk("0");
					}
					if(tdzkindex>0){
						salesReport.setGrp_Dct(df1.format(monthpjtdzk/tdzkindex));
					}else{
						salesReport.setGrp_Dct("0");
					}
					if(personindex>0){
						salesReport.setJbrs(df1.format(monthperson/personindex));
					}else{
						salesReport.setJbrs("0");
					}
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(monthClases)+"");
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					}
				}
				retMap.put("lastmonthData", objMapOld);
			}
		}
		//历史表格数据
		if(datenowList!=null&&datenowList.size()>0){
			double zys = 0.0;
			double kzl = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			double pjzk = 0.0;
			double pjtdzk = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, Object> excelMapOld  = new TreeMap<String, Object>();
			if(dateOldList!=null&&dateOldList.size()>0){
				int xsysindexmonth =0;
				int zglindexmonth =0;
				int kzlindexmonth =0;
				int zkindexmonth =0;
				int tdzkindexmonth =0;
				int personindexmonth =0;
				for (String month : dateOldList) {
					List<Z_Airdata> tempListgo = new ArrayList<Z_Airdata>(); 
					List<Z_Airdata> tempListback = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getGoNum().equals(z_Airdata.getFlt_Nbr())){
							tempListgo.add(z_Airdata);
						}
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getHuiNum().equals(z_Airdata.getFlt_Nbr())){
							tempListback.add(z_Airdata);
						}
					}
					if((tempListgo.size()>0&&tempListback.size()>0)&&(!TextUtil.isEmpty(salesReportQuery.getGoNum())&&!TextUtil.isEmpty(salesReportQuery.getHuiNum()))){
						List<String> dyss = new ArrayList<String>();
						for (Z_Airdata z1 : tempListgo) {
							if(!dyss.contains(sdf.format(z1.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z1.getLcl_Dpt_Day()));
							}
						}
						for (Z_Airdata z2 : tempListback) {
							if(!dyss.contains(sdf.format(z2.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z2.getLcl_Dpt_Day()));
							}
						}
						monthClases = (double) dyss.size();
					}else{
						if(tempListgo.size()>0){
							monthClases = TextUtil.getExecClass(tempListgo);
						}else if(tempListback.size()>0){
							monthClases = TextUtil.getExecClass(tempListback);
						}
					}
					Double monthhbys = 0.0;
					Double monthxsys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					Double monthpjzk = 0.0;
					Double monthpjtdzk = 0.0;
					int xsysindex =0;
					int zglindex =0;
					int kzlindex =0;
					int zkindex =0;
					int tdzkindex =0;
					int personindex =0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
						airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
						EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
						if(everyDuanInfo.getGoAndBack_xssr()>0){
							monthxsys = monthxsys + everyDuanInfo.getGoAndBack_xssr();
							xsysindex++;
						}
						monthhbys = monthhbys + everyDuanInfo.getGoAndBack_sr();
						if(everyDuanInfo.getGoAndBack_zgl()>0){
							monthzgl = monthzgl + everyDuanInfo.getGoAndBack_zgl();
							zglindex++;
						}
						if(everyDuanInfo.getGoAndBack_kzl()>0){
							monthkzl = monthkzl + everyDuanInfo.getGoAndBack_kzl();
							kzlindex++;
						}
						if(everyDuanInfo.getGoAndBack_zk()>0){
							monthpjzk = monthpjzk + everyDuanInfo.getGoAndBack_zk();
							zkindex++;
						}
						if(everyDuanInfo.getGoAndBack_tdzk()>0){
							monthpjtdzk = monthpjtdzk + everyDuanInfo.getGoAndBack_tdzk();
							tdzkindex++;
						}
						if(everyDuanInfo.getGoAndBack_rs()>0){
							monthperson = monthperson + everyDuanInfo.getGoAndBack_rs();
							personindex++;
						}
					}
					double bcc = monthClases;
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(xsysindex>0){
						salesReport.setXssr(df2.format(monthxsys/xsysindex));
					}else{
						salesReport.setXssr("0");
					}
					if(zglindex>0){
						salesReport.setSet_Ktr_Ine(df1.format(monthzgl/zglindex));
					}else{
						salesReport.setSet_Ktr_Ine("0");
					}
					if(kzlindex>0){
						salesReport.setPjkzl(df1.format(monthkzl/kzlindex));
					}else{
						salesReport.setPjkzl("0");
					}
					if(zkindex>0){
						salesReport.setPjzk(df1.format(monthpjzk/zkindex));
					}else{
						salesReport.setPjzk("0");
					}
					if(tdzkindex>0){
						salesReport.setGrp_Dct(df1.format(monthpjtdzk/tdzkindex));
					}else{
						salesReport.setGrp_Dct("0");
					}
					if(personindex>0){
						salesReport.setJbrs(df1.format(monthperson/personindex));
					}else{
						salesReport.setJbrs("0");
					}
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						zys = zys + Double.parseDouble(salesReport.getHbys());
					}
					if(Double.parseDouble(salesReport.getXssr())>0){
						xsys = xsys + Double.parseDouble(salesReport.getXssr());
						xsysindexmonth++;
					}
					if(Double.parseDouble(salesReport.getSet_Ktr_Ine())>0){
						zgl = zgl + Double.parseDouble(salesReport.getSet_Ktr_Ine());
						zglindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjkzl())>0){
						kzl = kzl + Double.parseDouble(salesReport.getPjkzl());
						kzlindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjzk())>0){
						pjzk = pjzk + Double.parseDouble(salesReport.getPjzk());
						zkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getGrp_Dct())>0){
						pjtdzk = pjtdzk + Double.parseDouble(salesReport.getGrp_Dct());
						tdzkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getJbrs())>0){
						jbrs = jbrs + Double.parseDouble(salesReport.getJbrs());
						personindexmonth++;
					}
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
				}
				excelMapOld.put("monthData", objMap);
				excelMapOld.put("zys", df2.format(zys));
				excelMapOld.put("xsys", df2.format(xsys/xsysindexmonth));
				excelMapOld.put("zgl", df1.format(zgl/zglindexmonth));
				excelMapOld.put("kzl", df1.format(kzl/kzlindexmonth));
				excelMapOld.put("zrs", zrs);
				excelMapOld.put("zbc", df1.format(zbc));
				excelMapOld.put("jbrs", df1.format(jbrs/personindexmonth));
				excelMapOld.put("pjzk", df1.format(pjzk/zkindexmonth));
				excelMapOld.put("pjtdzk", df1.format(pjtdzk/tdzkindexmonth));
				retMap.put("excelMapOld", excelMapOld);
			}
		}
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		List<SalesReportDayInfo> SalesReportDayInfoList = new ArrayList<SalesReportDayInfo>();
		if(TextUtil.isEmpty(pas_stpCode)){
			//直飞
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getHalfYearReportIncomeInfoData(false,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(arrv_Airpt_CdCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getHalfYearReportIncomeInfoData(false,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
		}else{
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(pas_stpCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+pas_stpCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getHalfYearReportIncomeInfoData(false,dpt_AirPt_CdCode,pas_stpCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(pas_stpCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getHalfYearReportIncomeInfoData(false,pas_stpCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
			
			SalesReportDayInfo salesReportDayInfo4 = new SalesReportDayInfo();
			salesReportDayInfo4.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo4.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB3 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB3.add(z_Airdata);
				}
			}
			salesReportDayInfo4.setDataMap(getHalfYearReportIncomeInfoData(true,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB3,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo4);
			
			SalesReportDayInfo salesReportDayInfo5 = new SalesReportDayInfo();
			salesReportDayInfo5.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo5.setFlyCode(arrv_Airpt_CdCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB4 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB4.add(z_Airdata);
				}
			}
			salesReportDayInfo5.setDataMap(getHalfYearReportIncomeInfoData(true,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB4,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo5);
			
			SalesReportDayInfo salesReportDayInfo6 = new SalesReportDayInfo();
			salesReportDayInfo6.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo6.setFlyCode(pas_stpCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB5 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB5.add(z_Airdata);
				}
			}
			salesReportDayInfo6.setDataMap(getHalfYearReportIncomeInfoData(false,pas_stpCode,arrv_Airpt_CdCode,zairdataListB5,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo6);
			
			SalesReportDayInfo salesReportDayInfo7 = new SalesReportDayInfo();
			salesReportDayInfo7.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo7.setFlyCode(arrv_Airpt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB6 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB6.add(z_Airdata);
				}
			}
			salesReportDayInfo7.setDataMap(getHalfYearReportIncomeInfoData(false,arrv_Airpt_CdCode,pas_stpCode,zairdataListB6,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo7);
			
		}
		Map<String ,Object> rettm = new HashMap<String ,Object>();
		rettm.put("everyList", SalesReportDayInfoList);
		return rettm;
	}
	public Map<String,Object> getHalfYearReportIncomeInfoData(boolean isLong ,String dptCode,String arrCode,List<Z_Airdata> zairdataListAll,List<Z_Airdata> thisWeekZairdataListAllNew,List<Z_Airdata> lastWeekZairdataListAllNew,List<String> datenowList,List<String> dateOldList,SalesReportQuery salesReportQuery) {
		//是否包含异常数据
		String exceptionFlag = "1";
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(zairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "single_false";
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		String dataFlage2 = (String) dataMap2.get("flage");
		
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		boolean flage = false;
		//是否包含异常数据
		Map<String, Object> retMap = new TreeMap<String, Object>();
		double zys = 0.0;
		double kzl = 0.0;
		double pjzk = 0.0;
		double pjtdzk = 0.0;
		double xsys = 0.0;
		double zgl = 0.0;
		double zrs = 0.0;
		double zbc = 0.0;
		//有数据的日期
		List<String> bcDateList = new ArrayList<String>();
		for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
			String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
			String dpt = z_Airdata.getDpt_AirPt_Cd();
			String arr = z_Airdata.getArrv_Airpt_Cd();
			if(dpt.equals(dptCode)&&arr.equals(arrCode)){
				if(!bcDateList.contains(day)){
					bcDateList.add(day);
				}
			}
		}
		double jbrs = 0.0;
		Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
		Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
		if(datenowList!=null&&datenowList.size()>0){
			int havingMonths = 0 ;
			for (String month : datenowList) {
				List<String> bcDateListmonth = new ArrayList<String>();
				List<Z_Airdata> tempList = new ArrayList<Z_Airdata>(); 
				SalesReport salesReport = new SalesReport();
				Double monthClases = 0.0;
				//找到当前月有多少班次 根据有时间的来计算
				for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
					String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
					String dpt = z_Airdata.getDpt_AirPt_Cd();
					String arr = z_Airdata.getArrv_Airpt_Cd();
					if(month.equals(day.split("-")[0]+day.split("-")[1])&&dpt.equals(dptCode)&&arr.equals(arrCode)){
						tempList.add(z_Airdata);
						if(!bcDateListmonth.contains(day)){
							bcDateListmonth.add(day);
						}
					}
				}
				monthClases = TextUtil.getExecClass(tempList);
				Double monthhbys = 0.0;
				Double monthxxys = 0.0;
				Double monthzgl = 0.0;
				Double monthkzl = 0.0;
				Double monthpjzk = 0.0;
				Double monthpjtdzk = 0.0;
				Double monthperson = 0.0;
				int length = 0;
				for(int i=1;i<=31;i++){
					String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
					Double dayhbys = 0.0;
					Double daytimes = 0.0;
					Double dayxsys = 0.0;
					Double dayzgl = 0.0;
					Double dayzwsall = 0.0;
					Double dayhjsingle = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double daypjzk = 0.0;
					Double daypjtdzk = 0.0;
					Double daypjzkall = 0.0;
					Double daypjtdzkall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					Double dayperson = 0.0;
					int index = 0;
					int dateIndex = 0;
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							String dpt = z_Airdata.getDpt_AirPt_Cd();
							String arr = z_Airdata.getArrv_Airpt_Cd();
							if(dpt.equals(dptCode)&&arr.equals(arrCode)){
								flage = true;
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//去程两个短段数据
								dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									daytimes =daytimes + ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(daytimes<0){
									daytimes = daytimes + 24;
								}
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								daypjzkall = daypjzkall + z_Airdata.getAvg_Dct().doubleValue();
								daypjtdzkall = daypjtdzkall + z_Airdata.getGrp_Dct().doubleValue();
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
					}
					//往返座位数之和
					if(dayzwsall<=0.0){dayzwsall=1.0;}
					if(dayhjsingle<=0.0){dayhjsingle=1.0;}
					if(index<=0){index=1;}
					//天营收之和
					monthhbys = monthhbys + dayhbys;
					if(daytimes<=0.0){
						dayxsys = 0.0;
						length++;
					}else{
						dayxsys = dayhbys / daytimes;
						monthxxys = dayxsys + monthxxys;
					}
					//每一天的座公里
					dayzgl = dayhbys / dayzwsall / dayhjsingle;
					//天座公里之和
					monthzgl = monthzgl + dayzgl;
					//每一天的客座率
					daykzl = daykzlall / index; 
					daypjzk = daypjzkall / index; 
					//天客座率之和
					monthkzl = monthkzl + daykzl;
					monthpjzk = monthpjzk + daypjzk;
					monthpjtdzk = monthpjtdzk + daypjtdzk;
					//每一天总人数
					dayperson =  dayskperson + daytdperson;
					//天总人数之和
					monthperson = monthperson + dayperson;
				}
				double bcc = monthClases;
				if(monthClases<=0){
					monthClases = 1.0;
				}
				//一个航班号强烈按照一天一班来算
				salesReport.setHbys(df1.format(monthhbys));
				if(bcc-(length-(31-bcc))>0){
					salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
					xsys = xsys + monthxxys/(bcc-(length-(31-bcc)));
				}else{
					salesReport.setXssr(df1.format(monthxxys));
				}
				salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
				salesReport.setPjkzl(df1.format(monthkzl/monthClases));
				salesReport.setPjzk(df1.format(monthpjzk/monthClases));
				salesReport.setGrp_Dct(df1.format(monthpjtdzk/monthClases));
				salesReport.setStzsr(monthperson+"");
				salesReport.setBanci(df1.format(bcc)+"");
				salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
				salesReport.setBcDateList(bcDateListmonth);
				if(Double.parseDouble(salesReport.getHbys())>0){
					objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
				zys = zys + monthhbys;
				zgl = zgl + monthzgl/monthClases;
				kzl = kzl + monthkzl/monthClases;
				pjzk = pjzk + monthpjzk/monthClases;
				zrs = zrs + monthperson;
				zbc = zbc + bcc;
				jbrs = jbrs + monthperson/monthClases;
				if(monthhbys>0){
					havingMonths ++ ;
				}
			}
			if(havingMonths<=0){
				havingMonths = 1;
			}
			retMap.put("monthData", objMap);
			retMap.put("hasData", flage);
			
			retMap.put("zys", df2.format(zys));
			retMap.put("xsys", df2.format(xsys/havingMonths));
			retMap.put("zgl", df1.format(zgl/havingMonths));
			retMap.put("kzl", df1.format(kzl/havingMonths));
			retMap.put("pjzk", df1.format(pjzk/havingMonths));
			retMap.put("pjtdzk", df1.format(pjtdzk/havingMonths));
			retMap.put("zrs", zrs);
			retMap.put("zbc", df1.format(zbc));
			retMap.put("bcDateList", bcDateList);
			retMap.put("jbrs", df1.format(jbrs/havingMonths));
		}
		//去年同期数据
		if(dateOldList!=null&&dateOldList.size()>0){
			for (String month : dateOldList) {
				List<Z_Airdata> tempList = new ArrayList<Z_Airdata>(); 
				List<String> oldbcDateList = new ArrayList<String>(); 
				SalesReport salesReport = new SalesReport();
				Double monthClases = 0.0;
				//找到当前月有多少班次 根据有时间的来计算
				for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
					String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
					String dpt = z_Airdata.getDpt_AirPt_Cd();
					String arr = z_Airdata.getArrv_Airpt_Cd();
					if(month.equals(day.split("-")[0]+day.split("-")[1])&&dpt.equals(dptCode)&&arr.equals(arrCode)){
						tempList.add(z_Airdata);
						if(!oldbcDateList.contains(day)){
							oldbcDateList.add(day);
						}
					}
				}
				monthClases = TextUtil.getExecClass(tempList);
				Double monthhbys = 0.0;
				Double monthxxys = 0.0;
				Double monthzgl = 0.0;
				Double monthkzl = 0.0;
				Double monthperson = 0.0;
				int length = 0;
				for(int i=1;i<=31;i++){
					String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
					Double dayhbys = 0.0;
					Double daytimes = 0.0;
					Double dayxsys = 0.0;
					Double dayzgl = 0.0;
					Double dayzwsall = 0.0;
					Double dayhjsingle = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					Double dayperson = 0.0;
					int index = 0;
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							String dpt = z_Airdata.getDpt_AirPt_Cd();
							String arr = z_Airdata.getArrv_Airpt_Cd();
							if(dpt.equals(dptCode)&&arr.equals(arrCode)){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//去程两个短段数据
								dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								//回程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
					}
					//往返座位数之和
					
					if(dayzwsall<=0.0){dayzwsall=1.0;}
					if(dayhjsingle<=0.0){dayhjsingle=1.0;}
					if(index<=0){index=1;}
					//天营收之和
					monthhbys = monthhbys + dayhbys;
					if(daytimes<=0.0){
						dayxsys = 0.0;
						length++;
					}else{
						dayxsys = dayhbys / daytimes;
						monthxxys = dayxsys + monthxxys;
					}
					//每一天的座公里
					dayzgl = dayhbys / dayzwsall / dayhjsingle;
					//天座公里之和
					monthzgl = monthzgl + dayzgl;
					//每一天的客座率
					daykzl = daykzlall / index; 
					//天客座率之和
					monthkzl = monthkzl + daykzl;
					//每一天总人数
					dayperson =  dayskperson + daytdperson;
					//天总人数之和
					monthperson = monthperson + dayperson;
				}
				double bcc = monthClases;
				if(monthClases<=0){
					monthClases = 1.0;
				}
				//一个航班号强烈按照一天一班来算
				salesReport.setHbys(df1.format(monthhbys));
				if(bcc-(length-(31-bcc))>0){
					salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
				}else{
					salesReport.setXssr(df1.format(monthxxys));
				}
				salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
				salesReport.setPjkzl(df1.format(monthkzl/monthClases));
				salesReport.setStzsr(monthperson+"");
				salesReport.setBanci(df1.format(bcc)+"");
				salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
				salesReport.setBcDateList(oldbcDateList);
				if(Double.parseDouble(salesReport.getHbys())>0){	
					objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
			}
			retMap.put("lastmonthData", objMapOld);
		}
		retMap.put("exceptionFlag", exceptionFlag);
		return retMap;
	}
	@Override
	public Map<String, Object> getHalfYearReportIncomeInfo(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		//得到当前航季度的年、月份
		List<String> datenowList = new ArrayList<String>();
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//设置上一年的航季
		Map<String,String> dateMap = new HashMap<String,String>();
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			String [] str = stratTime.split("-");
			Map<String,String> dateMaptemp = new HashMap<String,String>();
			dateMaptemp = TextUtil.getDCDate(Integer.parseInt(str[0]));
			String st = dateMaptemp.get("startTime");
			if(st.equals(stratTime)){
				//冬春航季
				dateMap = TextUtil.getDCDate(Integer.parseInt(str[0])-1);
			}else{
				//夏秋航季
				dateMap = TextUtil.getXQDate(Integer.parseInt(str[0])-1);
			}
		}
		salesReportQuery.setStartTime(dateMap.get("startTime"));
		salesReportQuery.setEndTime(dateMap.get("endTime"));
		List<String> dateOldList = new ArrayList<String>();
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//得到当前航季的年月列表
		String [] strstart = null;
		String [] endstart = null;
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			strstart = stratTime.split("-");
			endstart = endTime.split("-");
		}
		if(strstart!=null&&endstart!=null){
			if(Integer.parseInt(endstart[0])>Integer.parseInt(strstart[0])){
				//冬春航季
				datenowList.add(strstart[0]+"10");
				datenowList.add(strstart[0]+"11");
				datenowList.add(strstart[0]+"12");
				datenowList.add(endstart[0]+"01");
				datenowList.add(endstart[0]+"02");
				datenowList.add(endstart[0]+"03");
			}else{
				//夏秋航季
				datenowList.add(strstart[0]+"03");
				datenowList.add(strstart[0]+"04");
				datenowList.add(strstart[0]+"05");
				datenowList.add(strstart[0]+"06");
				datenowList.add(strstart[0]+"07");
				datenowList.add(strstart[0]+"08");
				datenowList.add(strstart[0]+"09");
				datenowList.add(strstart[0]+"10");
			}
		}
		//得到上一年的航季的年月列表
		String stratTimeold = salesReportQuery.getStartTime();
		String endTimeold = salesReportQuery.getEndTime();
		String [] strstartold = null;
		String [] endstartold = null;
		if(!TextUtil.isEmpty(stratTimeold)&&!TextUtil.isEmpty(endTimeold)){
			strstartold = stratTimeold.split("-");
			endstartold = endTimeold.split("-");
		}
		if(strstartold!=null&&endstartold!=null){
			if(Integer.parseInt(endstartold[0])>Integer.parseInt(strstartold[0])){
				//冬春航季
				dateOldList.add(strstartold[0]+"10");
				dateOldList.add(strstartold[0]+"11");
				dateOldList.add(strstartold[0]+"12");
				dateOldList.add(endstartold[0]+"01");
				dateOldList.add(endstartold[0]+"02");
				dateOldList.add(endstartold[0]+"03");
			}else{
				//夏秋航季
				dateOldList.add(strstartold[0]+"03");
				dateOldList.add(strstartold[0]+"04");
				dateOldList.add(strstartold[0]+"05");
				dateOldList.add(strstartold[0]+"06");
				dateOldList.add(strstartold[0]+"07");
				dateOldList.add(strstartold[0]+"08");
				dateOldList.add(strstartold[0]+"09");
				dateOldList.add(strstartold[0]+"10");
			}
		}
		//是否包含异常数据
		List<Z_Airdata> thisWeekZairdataListAllNew = getIsIncludeExceptionData(thisWeekZairdataListAll,salesReportQuery);
		List<Z_Airdata> lastWeekZairdataListAllNew = getIsIncludeExceptionData(lastWeekZairdataListAll,salesReportQuery);
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(!TextUtil.isEmpty(pas_stpCode)){
			double zys = 0.0;
			double kzl = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(datenowList!=null&&datenowList.size()>0){
				int havingMonths = 0 ;
				for (String month : datenowList) {
					List<Z_Airdata> tempList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							tempList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(tempList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length =0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsq = 0.0;
						Double dayzwsh = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double dayhjsingleq = 0.0;
						Double dayhjlongq = 0.0;
						Double dayhjsingleh = 0.0;
						Double dayhjlongh = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						double zgld = 0.0;
						for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
									zgld = zgld + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
								}
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								String dpt = z_Airdata.getDpt_AirPt_Cd();
								String arr = z_Airdata.getArrv_Airpt_Cd();
								if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))||(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))){
									if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
										//去程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										//短段航距之和
										dayhjsingleq = dayhjsingleq + z_Airdata.getSailingDistance();
									}else{
										dayhjlongq = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsq = (double) z_Airdata.getCount_Set();
									}
								}
								if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))){
									if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
									//回程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										dayhjsingleh = dayhjsingleh + z_Airdata.getSailingDistance();
									}else{
										dayhjlongh = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsh = (double) z_Airdata.getCount_Set();
									}
								}
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								dayzwsh = (double) z_Airdata.getCount_Set();
								index ++;
							}
						}
						//往返座位数之和
						dayzwsall = dayzwsq+dayzwsh;
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=1.0;
						}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
						//天小时营收和
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++ ;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
//						monthxxys = monthxxys + dayxsys;
						//每一天的座公里
						if(index<6||dayzwsall<=1){
							dayzgl = zgld/index;
						}else{
							dayzgl = dayhbys / dayzwsall / dayhjsingle;
						}
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
						xsys = xsys + monthxxys/(bcc-(length-(31-bcc)));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/(Math.round(monthClases))));
					salesReport.setPjkzl(df1.format(monthkzl/Math.round(monthClases)));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
					objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					zys = zys + monthhbys;
					zgl = zgl + monthzgl/Math.round(monthClases);
					kzl = kzl + monthkzl/Math.round(monthClases);
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
					jbrs = jbrs + monthperson/Math.round(monthClases);
					if(monthhbys>0){
						havingMonths ++ ;
					}
				}
				if(havingMonths<=0){
					havingMonths = 1;
				}
				retMap.put("monthData", objMap);
				retMap.put("zys", df2.format(zys));
				retMap.put("xsys", df2.format(xsys/havingMonths));
				retMap.put("zgl", df1.format(zgl/havingMonths));
				retMap.put("kzl", df1.format(kzl/havingMonths));
				retMap.put("zrs", zrs);
				retMap.put("zbc", df1.format(zbc));
				retMap.put("jbrs", df1.format(jbrs/havingMonths));
			}
			//去年同期数据
			if(dateOldList!=null&&dateOldList.size()>0){
				for (String month : dateOldList) {
					List<Z_Airdata> tempList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							tempList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(tempList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length  = 0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsq = 0.0;
						Double dayzwsh = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double dayhjsingleq = 0.0;
						Double dayhjlongq = 0.0;
						Double dayhjsingleh = 0.0;
						Double dayhjlongh = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						double zgld = 0.0;
						for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
									zgld = zgld + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
								}
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								String dpt = z_Airdata.getDpt_AirPt_Cd();
								String arr = z_Airdata.getArrv_Airpt_Cd();
								if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))||(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))){
									if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
										//去程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										//短段航距之和
										dayhjsingleq = dayhjsingleq + z_Airdata.getSailingDistance();
									}else{
										dayhjlongq = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsq = (double) z_Airdata.getCount_Set();
									}
								}
								if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))){
									if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
										//回程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										dayhjsingleh = dayhjsingleh + z_Airdata.getSailingDistance();
									}else{
										dayhjlongh = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsh = (double) z_Airdata.getCount_Set();
									}
								}
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						//往返座位数之和
						dayzwsall = dayzwsq+dayzwsh;
						
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=1.0;
						}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
						//每一天的小时营收
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
						//天小时营收和
//						monthxxys = monthxxys + dayxsys;
						//每一天的座公里
						if(index<6||dayzwsall<=1){
							dayzgl = zgld/index;
						}else{
							dayzgl = dayhbys / dayzwsall / dayhjsingle;
						}
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/Math.round(monthClases)));
					salesReport.setPjkzl(df1.format(monthkzl/Math.round(monthClases)));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/Math.round(monthClases))+"");
					objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
				retMap.put("lastmonthData", objMapOld);
			}
		}else{
			//直飞	
			double zys = 0.0;
			double kzl = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(datenowList!=null&&datenowList.size()>0){
				int havingMonths = 0 ;
				for (String month : datenowList) {
					List<Z_Airdata> banciList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							banciList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(banciList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length = 0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//去程两个短段数据
								if(z_Airdata.getCount_Set()>0){
									dayzwsall = dayzwsall + (double) z_Airdata.getCount_Set();
								}else{
									dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								}
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						//往返座位数之和
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){dayhjsingle=1.0;}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
//						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
//						//天小时营收和
//						monthxxys = monthxxys + dayxsys;
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
						//每一天的座公里
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
						xsys = xsys + monthxxys/(bcc-(length-(31-bcc)));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
					salesReport.setPjkzl(df1.format(monthkzl/monthClases));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
					objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					zys = zys + monthhbys;
					zgl = zgl + monthzgl/monthClases;
					kzl = kzl + monthkzl/monthClases;
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
					jbrs = jbrs + monthperson/monthClases;
					if(monthhbys>0){
						havingMonths ++ ;
					}
				}
				if(havingMonths<=0){
					havingMonths = 1;
				}
				retMap.put("monthData", objMap);
				
				retMap.put("zys", df2.format(zys));
				retMap.put("xsys", df2.format(xsys/havingMonths));
				retMap.put("zgl", df1.format(zgl/havingMonths));
				retMap.put("kzl", df1.format(kzl/havingMonths));
				retMap.put("zrs", zrs);
				retMap.put("zbc", df1.format(zbc));
				retMap.put("jbrs", df1.format(jbrs/havingMonths));
			}
			//去年同期数据
			if(dateOldList!=null&&dateOldList.size()>0){
				for (String month : dateOldList) {
					List<Z_Airdata> banciList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							banciList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(banciList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length = 0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//去程两个短段数据
								if(z_Airdata.getCount_Set()>0){
									dayzwsall = dayzwsall + (double) z_Airdata.getCount_Set();
								}else{
									dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								}
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								//回程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						//往返座位数之和
						
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){dayhjsingle=1.0;}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
						//每一天的座公里
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
					salesReport.setPjkzl(df1.format(monthkzl/monthClases));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
					objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
				retMap.put("lastmonthData", objMapOld);
			}
		}
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		return retMap;
	}
	@Override
	public Map<String, Object> getYearReportIncomeInfo_New(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		//得到当前航季度的年、月份
		List<String> datenowList = new ArrayList<String>();
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			String [] str = stratTime.split("-");
			String [] str2 = endTime.split("-");
			salesReportQuery.setStartTime((Integer.parseInt(str[0])-1)+"-"+str[1]+"-"+str[2]);
			salesReportQuery.setEndTime((Integer.parseInt(str[0])-1)+"-"+str2[1]+"-"+str2[2]);
		}
		
		List<String> dateOldList = new ArrayList<String>();
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//得到当前航季的年月列表
		String [] strstart = null;
		String [] endstart = null;
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			strstart = stratTime.split("-");
			endstart = endTime.split("-");
		}
		if(strstart!=null&&endstart!=null){
				datenowList.add(strstart[0]+"01");
				datenowList.add(strstart[0]+"02");
				datenowList.add(strstart[0]+"03");
				datenowList.add(strstart[0]+"04");
				datenowList.add(strstart[0]+"05");
				datenowList.add(strstart[0]+"06");
				datenowList.add(strstart[0]+"07");
				datenowList.add(strstart[0]+"08");
				datenowList.add(strstart[0]+"09");
				datenowList.add(strstart[0]+"10");
				datenowList.add(strstart[0]+"11");
				datenowList.add(strstart[0]+"12");
		}
		String stratTimeold = salesReportQuery.getStartTime();
		String endTimeold = salesReportQuery.getEndTime();
		String [] strstartold = null;
		String [] endstartold = null;
		if(!TextUtil.isEmpty(stratTimeold)&&!TextUtil.isEmpty(endTimeold)){
			strstartold = stratTimeold.split("-");
			endstartold = endTimeold.split("-");
		}
		if(strstartold!=null&&endstartold!=null){
				dateOldList.add(strstartold[0]+"01");
				dateOldList.add(strstartold[0]+"02");
				dateOldList.add(strstartold[0]+"03");
				dateOldList.add(strstartold[0]+"04");
				dateOldList.add(strstartold[0]+"05");
				dateOldList.add(strstartold[0]+"06");
				dateOldList.add(strstartold[0]+"07");
				dateOldList.add(strstartold[0]+"08");
				dateOldList.add(strstartold[0]+"09");
				dateOldList.add(strstartold[0]+"10");
				dateOldList.add(strstartold[0]+"11");
				dateOldList.add(strstartold[0]+"12");
		}
		//是否包含异常数据
		String exceptionFlag = "1";
		List<Z_Airdata> thisWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		List<Z_Airdata> lastWeekZairdataListAllNew = new ArrayList<Z_Airdata>();
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(thisWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "true";
		if(TextUtil.isEmpty(salesReportQuery.getGoNum())||TextUtil.isEmpty(salesReportQuery.getHuiNum())){
			strrfalge = "false";
		}
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		thisWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2.get("zairdataListB");
		String dataFlage2 = (String) dataMap2.get("flage");
		
		//历史数据判断异常数据
		Map<String,Object> dataMapp = TextUtil.getIsIncludeExceptionData(lastWeekZairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1p = (List<Z_Airdata>) dataMapp.get("zairdataListB");
		//是否包含异常航段
		String strrfalgep = "true";
		if(TextUtil.isEmpty(salesReportQuery.getGoNum())||TextUtil.isEmpty(salesReportQuery.getHuiNum())){
			strrfalgep = "false";
		}
		Map<String,Object> dataMap2p = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1p, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalgep);
		lastWeekZairdataListAllNew = (List<Z_Airdata>) dataMap2p.get("zairdataListB");
		
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		
		String flag = "0";
		if((thisWeekZairdataListAllNew!=null&&thisWeekZairdataListAllNew.size()>0)){
			flag = "1";
		}
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(true){
			double zys = 0.0;
			double kzl = 0.0;
			double pjzk = 0.0;
			double pjtdzk = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(datenowList!=null&&datenowList.size()>0){
				int xsysindexmonth =0;
				int zglindexmonth =0;
				int kzlindexmonth =0;
				int zkindexmonth =0;
				int tdzkindexmonth =0;
				int personindexmonth =0;
				for (String month : datenowList) {
					List<Z_Airdata> tempListgo = new ArrayList<Z_Airdata>(); 
					List<Z_Airdata> tempListback = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getGoNum().equals(z_Airdata.getFlt_Nbr())){
							tempListgo.add(z_Airdata);
						}
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getHuiNum().equals(z_Airdata.getFlt_Nbr())){
							tempListback.add(z_Airdata);
						}
					}
					if((tempListgo.size()>0&&tempListback.size()>0)&&(!TextUtil.isEmpty(salesReportQuery.getGoNum())&&!TextUtil.isEmpty(salesReportQuery.getHuiNum()))){
						List<String> dyss = new ArrayList<String>();
						for (Z_Airdata z1 : tempListgo) {
							if(!dyss.contains(sdf.format(z1.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z1.getLcl_Dpt_Day()));
							}
						}
						for (Z_Airdata z2 : tempListback) {
							if(!dyss.contains(sdf.format(z2.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z2.getLcl_Dpt_Day()));
							}
						}
						monthClases = (double) dyss.size();
					}else{
						if(tempListgo.size()>0){
							monthClases = TextUtil.getExecClass(tempListgo);
						}else if(tempListback.size()>0){
							monthClases = TextUtil.getExecClass(tempListback);
						}
					}
					Double monthhbys = 0.0;
					Double monthxsys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthpjzk = 0.0;
					Double monthpjtdzk = 0.0;
					Double monthperson = 0.0;
					int xsysindex =0;
					int zglindex =0;
					int kzlindex =0;
					int zkindex =0;
					int tdzkindex =0;
					int personindex =0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
						airLineAllInfo = TextUtil.getAirLineAllInfo(thisWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
						EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
						if(everyDuanInfo.getGoAndBack_xssr()>0){
							monthxsys = monthxsys + everyDuanInfo.getGoAndBack_xssr();
							xsysindex++;
						}
						monthhbys = monthhbys + everyDuanInfo.getGoAndBack_sr();
						if(everyDuanInfo.getGoAndBack_zgl()>0){
							monthzgl = monthzgl + everyDuanInfo.getGoAndBack_zgl();
							zglindex++;
						}
						if(everyDuanInfo.getGoAndBack_kzl()>0){
							monthkzl = monthkzl + everyDuanInfo.getGoAndBack_kzl();
							kzlindex++;
						}
						if(everyDuanInfo.getGoAndBack_zk()>0){
							monthpjzk = monthpjzk + everyDuanInfo.getGoAndBack_zk();
							zkindex++;
						}
						if(everyDuanInfo.getGoAndBack_tdzk()>0){
							monthpjtdzk = monthpjtdzk + everyDuanInfo.getGoAndBack_tdzk();
							tdzkindex++;
						}
						if(everyDuanInfo.getGoAndBack_rs()>0){
							monthperson = monthperson + everyDuanInfo.getGoAndBack_rs();
							personindex++;
						}
					}
					double bcc = monthClases;
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(xsysindex>0){
						salesReport.setXssr(df2.format(monthxsys/xsysindex));
					}else{
						salesReport.setXssr("0");
					}
					if(zglindex>0){
						salesReport.setSet_Ktr_Ine(df1.format(monthzgl/zglindex));
					}else{
						salesReport.setSet_Ktr_Ine("0");
					}
					if(kzlindex>0){
						salesReport.setPjkzl(df1.format(monthkzl/kzlindex));
					}else{
						salesReport.setPjkzl("0");
					}
					if(zkindex>0){
						salesReport.setPjzk(df1.format(monthpjzk/zkindex));
					}else{
						salesReport.setPjzk("0");
					}
					if(tdzkindex>0){
						salesReport.setGrp_Dct(df1.format(monthpjtdzk/tdzkindex));
					}else{
						salesReport.setGrp_Dct("0");
					}
					if(personindex>0){
						salesReport.setJbrs(df1.format(monthperson/personindex));
					}else{
						salesReport.setJbrs("0");
					}
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						zys = zys + Double.parseDouble(salesReport.getHbys());
					}
					if(Double.parseDouble(salesReport.getXssr())>0){
						xsys = xsys + Double.parseDouble(salesReport.getXssr());
						xsysindexmonth++;
					}
					if(Double.parseDouble(salesReport.getSet_Ktr_Ine())>0){
						zgl = zgl + Double.parseDouble(salesReport.getSet_Ktr_Ine());
						zglindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjkzl())>0){
						kzl = kzl + Double.parseDouble(salesReport.getPjkzl());
						kzlindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjzk())>0){
						pjzk = pjzk + Double.parseDouble(salesReport.getPjzk());
						zkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getGrp_Dct())>0){
						pjtdzk = pjtdzk + Double.parseDouble(salesReport.getGrp_Dct());
						tdzkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getJbrs())>0){
						jbrs = jbrs + Double.parseDouble(salesReport.getJbrs());
						personindexmonth++;
					}
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
				}
				retMap.put("monthData", objMap);
				retMap.put("zys", df2.format(zys));
				retMap.put("xsys", df2.format(xsys/xsysindexmonth));
				retMap.put("zgl", df1.format(zgl/zglindexmonth));
				retMap.put("kzl", df1.format(kzl/kzlindexmonth));
				retMap.put("zrs", zrs);
				retMap.put("zbc", df1.format(zbc));
				retMap.put("jbrs", df1.format(jbrs/personindexmonth));
				retMap.put("pjzk", df1.format(pjzk/zkindexmonth));
				retMap.put("pjtdzk", df1.format(pjtdzk/tdzkindexmonth));
			}
			//去年同期数据
			if(dateOldList!=null&&dateOldList.size()>0){
				for (String month : dateOldList) {
					List<Z_Airdata> tempListgo = new ArrayList<Z_Airdata>(); 
					List<Z_Airdata> tempListback = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getGoNum().equals(z_Airdata.getFlt_Nbr())){
							tempListgo.add(z_Airdata);
						}
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getHuiNum().equals(z_Airdata.getFlt_Nbr())){
							tempListback.add(z_Airdata);
						}
					}
					if((tempListgo.size()>0&&tempListback.size()>0)&&(!TextUtil.isEmpty(salesReportQuery.getGoNum())&&!TextUtil.isEmpty(salesReportQuery.getHuiNum()))){
						List<String> dyss = new ArrayList<String>();
						for (Z_Airdata z1 : tempListgo) {
							if(!dyss.contains(sdf.format(z1.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z1.getLcl_Dpt_Day()));
							}
						}
						for (Z_Airdata z2 : tempListback) {
							if(!dyss.contains(sdf.format(z2.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z2.getLcl_Dpt_Day()));
							}
						}
						monthClases = (double) dyss.size();
					}else{
						if(tempListgo.size()>0){
							monthClases = TextUtil.getExecClass(tempListgo);
						}else if(tempListback.size()>0){
							monthClases = TextUtil.getExecClass(tempListback);
						}
					}
					Double monthhbys = 0.0;
					Double monthxsys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					Double monthpjzk = 0.0;
					Double monthpjtdzk = 0.0;
					int xsysindex =0;
					int zglindex =0;
					int kzlindex =0;
					int zkindex =0;
					int tdzkindex =0;
					int personindex =0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
						airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
						EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
						if(everyDuanInfo.getGoAndBack_xssr()>0){
							monthxsys = monthxsys + everyDuanInfo.getGoAndBack_xssr();
							xsysindex++;
						}
						monthhbys = monthhbys + everyDuanInfo.getGoAndBack_sr();
						if(everyDuanInfo.getGoAndBack_zgl()>0){
							monthzgl = monthzgl + everyDuanInfo.getGoAndBack_zgl();
							zglindex++;
						}
						if(everyDuanInfo.getGoAndBack_kzl()>0){
							monthkzl = monthkzl + everyDuanInfo.getGoAndBack_kzl();
							kzlindex++;
						}
						if(everyDuanInfo.getGoAndBack_zk()>0){
							monthpjzk = monthpjzk + everyDuanInfo.getGoAndBack_zk();
							zkindex++;
						}
						if(everyDuanInfo.getGoAndBack_tdzk()>0){
							monthpjtdzk = monthpjtdzk + everyDuanInfo.getGoAndBack_tdzk();
							tdzkindex++;
						}
						if(everyDuanInfo.getGoAndBack_rs()>0){
							monthperson = monthperson + everyDuanInfo.getGoAndBack_rs();
							personindex++;
						}
					}
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(xsysindex>0){
						salesReport.setXssr(df2.format(monthxsys/xsysindex));
					}else{
						salesReport.setXssr("0");
					}
					if(zglindex>0){
						salesReport.setSet_Ktr_Ine(df1.format(monthzgl/zglindex));
					}else{
						salesReport.setSet_Ktr_Ine("0");
					}
					if(kzlindex>0){
						salesReport.setPjkzl(df1.format(monthkzl/kzlindex));
					}else{
						salesReport.setPjkzl("0");
					}
					if(zkindex>0){
						salesReport.setPjzk(df1.format(monthpjzk/zkindex));
					}else{
						salesReport.setPjzk("0");
					}
					if(tdzkindex>0){
						salesReport.setGrp_Dct(df1.format(monthpjtdzk/tdzkindex));
					}else{
						salesReport.setGrp_Dct("0");
					}
					if(personindex>0){
						salesReport.setJbrs(df1.format(monthperson/personindex));
					}else{
						salesReport.setJbrs("0");
					}
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(monthClases)+"");
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					}
				}
				retMap.put("lastmonthData", objMapOld);
				retMap.put("flag", flag);
			}
		}
		//历史表格数据
		if(datenowList!=null&&datenowList.size()>0){
			double zys = 0.0;
			double kzl = 0.0;
			double pjzk = 0.0;
			double pjtdzk = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, Object> excelMapOld = new TreeMap<String, Object>();
			if(dateOldList!=null&&dateOldList.size()>0){
				int xsysindexmonth =0;
				int zglindexmonth =0;
				int kzlindexmonth =0;
				int zkindexmonth =0;
				int tdzkindexmonth =0;
				int personindexmonth =0;
				for (String month : dateOldList) {
					List<Z_Airdata> tempListgo = new ArrayList<Z_Airdata>(); 
					List<Z_Airdata> tempListback = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getGoNum().equals(z_Airdata.getFlt_Nbr())){
							tempListgo.add(z_Airdata);
						}
						if(month.equals(day.split("-")[0]+day.split("-")[1])&&salesReportQuery.getHuiNum().equals(z_Airdata.getFlt_Nbr())){
							tempListback.add(z_Airdata);
						}
					}
					if((tempListgo.size()>0&&tempListback.size()>0)&&(!TextUtil.isEmpty(salesReportQuery.getGoNum())&&!TextUtil.isEmpty(salesReportQuery.getHuiNum()))){
						List<String> dyss = new ArrayList<String>();
						for (Z_Airdata z1 : tempListgo) {
							if(!dyss.contains(sdf.format(z1.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z1.getLcl_Dpt_Day()));
							}
						}
						for (Z_Airdata z2 : tempListback) {
							if(!dyss.contains(sdf.format(z2.getLcl_Dpt_Day()))){
								dyss.add(sdf.format(z2.getLcl_Dpt_Day()));
							}
						}
						monthClases = (double) dyss.size();
					}else{
						if(tempListgo.size()>0){
							monthClases = TextUtil.getExecClass(tempListgo);
						}else if(tempListback.size()>0){
							monthClases = TextUtil.getExecClass(tempListback);
						}
					}
					Double monthhbys = 0.0;
					Double monthxsys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthpjzk = 0.0;
					Double monthpjtdzk = 0.0;
					Double monthperson = 0.0;
					int xsysindex =0;
					int zglindex =0;
					int kzlindex =0;
					int zkindex =0;
					int tdzkindex =0;
					int personindex =0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
						airLineAllInfo = TextUtil.getAirLineAllInfo(lastWeekZairdataListAllNew,day,dpt_AirPt_CdCode,pas_stpCode,arrv_Airpt_CdCode);
						EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
						if(everyDuanInfo.getGoAndBack_xssr()>0){
							monthxsys = monthxsys + everyDuanInfo.getGoAndBack_xssr();
							xsysindex++;
						}
						monthhbys = monthhbys + everyDuanInfo.getGoAndBack_sr();
						if(everyDuanInfo.getGoAndBack_zgl()>0){
							monthzgl = monthzgl + everyDuanInfo.getGoAndBack_zgl();
							zglindex++;
						}
						if(everyDuanInfo.getGoAndBack_kzl()>0){
							monthkzl = monthkzl + everyDuanInfo.getGoAndBack_kzl();
							kzlindex++;
						}
						if(everyDuanInfo.getGoAndBack_zk()>0){
							monthpjzk = monthpjzk + everyDuanInfo.getGoAndBack_zk();
							zkindex++;
						}
						if(everyDuanInfo.getGoAndBack_tdzk()>0){
							monthpjtdzk = monthpjtdzk + everyDuanInfo.getGoAndBack_tdzk();
							tdzkindex++;
						}
						if(everyDuanInfo.getGoAndBack_rs()>0){
							monthperson = monthperson + everyDuanInfo.getGoAndBack_rs();
							personindex++;
						}
					}
					double bcc = monthClases;
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(xsysindex>0){
						salesReport.setXssr(df2.format(monthxsys/xsysindex));
					}else{
						salesReport.setXssr("0");
					}
					if(zglindex>0){
						salesReport.setSet_Ktr_Ine(df1.format(monthzgl/zglindex));
					}else{
						salesReport.setSet_Ktr_Ine("0");
					}
					if(kzlindex>0){
						salesReport.setPjkzl(df1.format(monthkzl/kzlindex));
					}else{
						salesReport.setPjkzl("0");
					}
					if(zkindex>0){
						salesReport.setPjzk(df1.format(monthpjzk/zkindex));
					}else{
						salesReport.setPjzk("0");
					}
					if(tdzkindex>0){
						salesReport.setGrp_Dct(df1.format(monthpjtdzk/tdzkindex));
					}else{
						salesReport.setGrp_Dct("0");
					}
					if(personindex>0){
						salesReport.setJbrs(df1.format(monthperson/personindex));
					}else{
						salesReport.setJbrs("0");
					}
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					if(Double.parseDouble(salesReport.getHbys())>0){
						objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					}
					if(Double.parseDouble(salesReport.getHbys())>0){
						zys = zys + Double.parseDouble(salesReport.getHbys());
					}
					if(Double.parseDouble(salesReport.getXssr())>0){
						xsys = xsys + Double.parseDouble(salesReport.getXssr());
						xsysindexmonth++;
					}
					if(Double.parseDouble(salesReport.getSet_Ktr_Ine())>0){
						zgl = zgl + Double.parseDouble(salesReport.getSet_Ktr_Ine());
						zglindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjkzl())>0){
						kzl = kzl + Double.parseDouble(salesReport.getPjkzl());
						kzlindexmonth++;
					}
					if(Double.parseDouble(salesReport.getPjzk())>0){
						pjzk = pjzk + Double.parseDouble(salesReport.getPjzk());
						zkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getGrp_Dct())>0){
						pjtdzk = pjtdzk + Double.parseDouble(salesReport.getGrp_Dct());
						tdzkindexmonth++;
					}
					if(Double.parseDouble(salesReport.getJbrs())>0){
						jbrs = jbrs + Double.parseDouble(salesReport.getJbrs());
						personindexmonth++;
					}
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
				}
				excelMapOld.put("monthData", objMap);
				excelMapOld.put("zys", df2.format(zys));
				excelMapOld.put("xsys", df2.format(xsys/xsysindexmonth));
				excelMapOld.put("zgl", df1.format(zgl/zglindexmonth));
				excelMapOld.put("kzl", df1.format(kzl/kzlindexmonth));
				excelMapOld.put("zrs", zrs);
				excelMapOld.put("zbc", df1.format(zbc));
				excelMapOld.put("jbrs", df1.format(jbrs/personindexmonth));
				excelMapOld.put("pjzk", df1.format(pjzk/zkindexmonth));
				excelMapOld.put("pjtdzk", df1.format(pjtdzk/tdzkindexmonth));
				retMap.put("excelMapOld", excelMapOld);
			}
		}
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		List<SalesReportDayInfo> SalesReportDayInfoList = new ArrayList<SalesReportDayInfo>();
		if(TextUtil.isEmpty(pas_stpCode)){
			//直飞
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getYearReportIncomeInfoData(false,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(arrv_Airpt_CdCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getYearReportIncomeInfoData(false,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
		}else{
			retMap.put("exceptionFlag", exceptionFlag);
			SalesReportDayInfo salesReportDayInfo1 = new SalesReportDayInfo();
			salesReportDayInfo1.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"="+outPortMapper.getairportNameByCode(pas_stpCode)+"="+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo1.setFlyCode(dpt_AirPt_CdCode+"="+pas_stpCode+"="+arrv_Airpt_CdCode);
			salesReportDayInfo1.setDataMap(retMap);
			SalesReportDayInfoList.add(salesReportDayInfo1);
			
			SalesReportDayInfo salesReportDayInfo2 = new SalesReportDayInfo();
			salesReportDayInfo2.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo2.setFlyCode(dpt_AirPt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			salesReportDayInfo2.setDataMap(getYearReportIncomeInfoData(false,dpt_AirPt_CdCode,pas_stpCode,zairdataListB1,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo2);
			
			SalesReportDayInfo salesReportDayInfo3 = new SalesReportDayInfo();
			salesReportDayInfo3.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo3.setFlyCode(pas_stpCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			salesReportDayInfo3.setDataMap(getYearReportIncomeInfoData(false,pas_stpCode,dpt_AirPt_CdCode,zairdataListB2,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo3);
			
			SalesReportDayInfo salesReportDayInfo4 = new SalesReportDayInfo();
			salesReportDayInfo4.setFlyName(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo4.setFlyCode(dpt_AirPt_CdCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB3 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB3.add(z_Airdata);
				}
			}
			salesReportDayInfo4.setDataMap(getYearReportIncomeInfoData(true,dpt_AirPt_CdCode,arrv_Airpt_CdCode,zairdataListB3,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo4);
			
			SalesReportDayInfo salesReportDayInfo5 = new SalesReportDayInfo();
			salesReportDayInfo5.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode));
			salesReportDayInfo5.setFlyCode(arrv_Airpt_CdCode+"-"+dpt_AirPt_CdCode);
			List<Z_Airdata> zairdataListB4 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB4.add(z_Airdata);
				}
			}
			salesReportDayInfo5.setDataMap(getYearReportIncomeInfoData(true,arrv_Airpt_CdCode,dpt_AirPt_CdCode,zairdataListB4,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo5);
			
			SalesReportDayInfo salesReportDayInfo6 = new SalesReportDayInfo();
			salesReportDayInfo6.setFlyName(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode));
			salesReportDayInfo6.setFlyCode(pas_stpCode+"-"+arrv_Airpt_CdCode);
			List<Z_Airdata> zairdataListB5 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB5.add(z_Airdata);
				}
			}
			salesReportDayInfo6.setDataMap(getYearReportIncomeInfoData(false,pas_stpCode,arrv_Airpt_CdCode,zairdataListB5,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo6);
			
			SalesReportDayInfo salesReportDayInfo7 = new SalesReportDayInfo();
			salesReportDayInfo7.setFlyName(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode));
			salesReportDayInfo7.setFlyCode(arrv_Airpt_CdCode+"-"+pas_stpCode);
			List<Z_Airdata> zairdataListB6 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : thisWeekZairdataListAll) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB6.add(z_Airdata);
				}
			}
			salesReportDayInfo7.setDataMap(getYearReportIncomeInfoData(false,arrv_Airpt_CdCode,pas_stpCode,zairdataListB6,thisWeekZairdataListAllNew,lastWeekZairdataListAllNew,datenowList,dateOldList,salesReportQuery));
			SalesReportDayInfoList.add(salesReportDayInfo7);
			
		}
		Map<String ,Object> rettm = new HashMap<String ,Object>();
		rettm.put("everyList", SalesReportDayInfoList);
		return rettm;
	}
	public Map<String,Object> getYearReportIncomeInfoData(boolean isLong ,String dptCode,String arrCode,List<Z_Airdata> zairdataListAll,List<Z_Airdata> thisWeekZairdataListAllNew,List<Z_Airdata> lastWeekZairdataListAllNew,List<String> datenowList,List<String> dateOldList,SalesReportQuery salesReportQuery) {
		//是否包含异常数据
		String exceptionFlag = "1";
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(zairdataListAll, salesReportQuery.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "single_false";
		Map<String,Object> dataMap2 = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, salesReportQuery.getIsIncludeExceptionHuangduan(),strrfalge);
		String dataFlage2 = (String) dataMap2.get("flage");
		
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())&&"on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(salesReportQuery.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		boolean flage = false;
		String flag = "0";
		if((thisWeekZairdataListAllNew!=null&&thisWeekZairdataListAllNew.size()>0)){
			flag = "1";
		}
		Map<String, Object> retMap = new TreeMap<String, Object>();
		//直飞	
		double zys = 0.0;
		double kzl = 0.0;
		double pjzk = 0.0;
		double pjtdzk = 0.0;
		double xsys = 0.0;
		double zgl = 0.0;
		double zrs = 0.0;
		double zbc = 0.0;
		//有数据的日期
		List<String> bcDateList = new ArrayList<String>();
		for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
			String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
			String dpt = z_Airdata.getDpt_AirPt_Cd();
			String arr = z_Airdata.getArrv_Airpt_Cd();
			if(dpt.equals(dptCode)&&arr.equals(arrCode)){
				if(!bcDateList.contains(day)){
					bcDateList.add(day);
				}
			}
		}
		double jbrs = 0.0;
		Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
		Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
		if(datenowList!=null&&datenowList.size()>0){
			int	havingMonths =0 ;
			for (String month : datenowList) {
				List<Z_Airdata> tempList = new ArrayList<Z_Airdata>(); 
				List<String> bcDateListmonth = new ArrayList<String>(); 
				SalesReport salesReport = new SalesReport();
				Double monthClases = 0.0;
				//找到当前月有多少班次 根据有时间的来计算
				for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
					String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
					String dpt = z_Airdata.getDpt_AirPt_Cd();
					String arr = z_Airdata.getArrv_Airpt_Cd();
					if(month.equals(day.split("-")[0]+day.split("-")[1])&&dpt.equals(dptCode)&&arr.equals(arrCode)){
						tempList.add(z_Airdata);
						if(!bcDateListmonth.contains(day)){
							bcDateListmonth.add(day);
						}
					}
				}
				monthClases = TextUtil.getExecClass(tempList);
				Double monthhbys = 0.0;
				Double monthxxys = 0.0;
				Double monthzgl = 0.0;
				Double monthkzl = 0.0;
				Double monthpjzk = 0.0;
				Double monthpjtdzk = 0.0;
				Double monthperson = 0.0;
				int length = 0;
				for(int i=1;i<=31;i++){
					String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
					Double dayhbys = 0.0;
					Double daytimes = 0.0;
					Double dayxsys = 0.0;
					Double dayzgl = 0.0;
					Double dayzwsall = 0.0;
					Double dayhjsingle = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double daypjzk = 0.0;
					Double daypjzkall = 0.0;
					Double daypjtdzk = 0.0;
					Double daypjtdzkall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					Double dayperson = 0.0;
					int index = 0;
					int dateIndex =0;
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							String dpt = z_Airdata.getDpt_AirPt_Cd();
							String arr = z_Airdata.getArrv_Airpt_Cd();
							if(dpt.equals(dptCode)&&arr.equals(arrCode)){
								flage = true;
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dateIndex++;
									daytimes =daytimes + ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(daytimes<0){
									daytimes = daytimes + 24;
								}
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								daypjzkall = daypjzkall + z_Airdata.getAvg_Dct().doubleValue();
								daypjtdzkall = daypjtdzkall + z_Airdata.getGrp_Dct().doubleValue();
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
					}
					//往返座位数之和
					if(dayzwsall<=0.0){dayzwsall=1.0;}
					if(dayhjsingle<=0.0){dayhjsingle=1.0;}
					if(index<=0){index=1;}
					//天营收之和
					monthhbys = monthhbys + dayhbys;
					if(daytimes<=0.0){
						dayxsys = 0.0;
						length++;
					}else{
						dayxsys = dayhbys / daytimes;
						monthxxys = dayxsys + monthxxys;
					}
					//每一天的座公里
					dayzgl = dayhbys / dayzwsall / dayhjsingle;
					//天座公里之和
					monthzgl = monthzgl + dayzgl;
					//每一天的客座率
					daykzl = daykzlall / index; 
					daypjzk = daypjzkall / index; 
					daypjtdzk = daypjtdzkall / index; 
					//天客座率之和
					monthkzl = monthkzl + daykzl;
					monthpjzk = monthpjzk + daypjzk;
					monthpjtdzk = monthpjtdzk + daypjtdzk;
					//每一天总人数
					dayperson =  dayskperson + daytdperson;
					//天总人数之和
					monthperson = monthperson + dayperson;
				}
				double bcc = monthClases;
				if(monthClases<=0){
					monthClases = 1.0;
				}
				//一个航班号强烈按照一天一班来算
				salesReport.setHbys(df1.format(monthhbys));
				if(bcc-(length-(31-bcc))>0){
					salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
					xsys = xsys + monthxxys/(bcc-(length-(31-bcc)));
				}else{
					salesReport.setXssr(df1.format(monthxxys));
				}
				salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
				salesReport.setPjkzl(df1.format(monthkzl/monthClases));
				salesReport.setPjzk(df1.format(monthpjzk/monthClases));
				salesReport.setGrp_Dct(df1.format(monthpjtdzk/monthClases));
				salesReport.setStzsr(monthperson+"");
				salesReport.setBanci(df1.format(bcc)+"");
				salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
				salesReport.setBcDateList(bcDateListmonth);
				if(Double.parseDouble(salesReport.getHbys())>0){
					objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
				zys = zys + monthhbys;
				zgl = zgl + monthzgl/monthClases;
				kzl = kzl + monthkzl/monthClases;
				pjzk = pjzk + monthpjzk/monthClases;
				pjtdzk = pjtdzk + monthpjtdzk/monthClases;
				zrs = zrs + monthperson;
				zbc = zbc + bcc;
				jbrs = jbrs + monthperson/monthClases;
				if(monthhbys>0){
					havingMonths ++ ;
				}
			}
			if(havingMonths<=0){
				havingMonths = 1;
			}
			retMap.put("monthData", objMap);
			retMap.put("hasData", flage);
			retMap.put("zys", df2.format(zys));
			retMap.put("xsys", df2.format(xsys/havingMonths));
			retMap.put("zgl", df1.format(zgl/havingMonths));
			retMap.put("kzl", df1.format(kzl/havingMonths));
			retMap.put("pjzk", df1.format(pjzk/havingMonths));
			retMap.put("pjtdzk", df1.format(pjtdzk/havingMonths));
			retMap.put("zrs", zrs);
			retMap.put("zbc", df1.format(zbc));
			retMap.put("bcDateList", bcDateList);
			retMap.put("jbrs", df1.format(jbrs/havingMonths));
		}
		//去年同期数据
		if(dateOldList!=null&&dateOldList.size()>0){
			for (String month : dateOldList) {
				List<Z_Airdata> tempList = new ArrayList<Z_Airdata>(); 
				List<String> oldbcDateList = new ArrayList<String>(); 
				SalesReport salesReport = new SalesReport();
				Double monthClases = 0.0;
				//找到当前月有多少班次 根据有时间的来计算
				for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
					String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
					String dpt = z_Airdata.getDpt_AirPt_Cd();
					String arr = z_Airdata.getArrv_Airpt_Cd();
					if(month.equals(day.split("-")[0]+day.split("-")[1])&&dpt.equals(dptCode)&&arr.equals(arrCode)){
						tempList.add(z_Airdata);
						if(!oldbcDateList.contains(day)){
							oldbcDateList.add(day);
						}
					}
				}
				monthClases = TextUtil.getExecClass(tempList);
				Double monthhbys = 0.0;
				Double monthxxys = 0.0;
				Double monthzgl = 0.0;
				Double monthkzl = 0.0;
				Double monthperson = 0.0;
				int length = 0;
				for(int i=1;i<=31;i++){
					String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
					Double dayhbys = 0.0;
					Double daytimes = 0.0;
					Double dayxsys = 0.0;
					Double dayzgl = 0.0;
					Double dayzwsall = 0.0;
					Double dayhjsingle = 0.0;
					Double daykzl = 0.0;
					Double daykzlall = 0.0;
					Double dayskperson = 0.0;
					Double daytdperson = 0.0;
					Double dayperson = 0.0;
					int index = 0;
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
							String dpt = z_Airdata.getDpt_AirPt_Cd();
							String arr = z_Airdata.getArrv_Airpt_Cd();
							if(dpt.equals(dptCode)&&arr.equals(arrCode)){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//去程两个短段数据
								if(z_Airdata.getCount_Set()>0){
									dayzwsall = dayzwsall + (double) z_Airdata.getCount_Set();
								}else{
									dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								}
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								//回程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
					}
					//往返座位数之和
					if(dayzwsall<=0.0){dayzwsall=1.0;}
					if(dayhjsingle<=0.0){dayhjsingle=1.0;}
					if(index<=0){index=1;}
					//天营收之和
					monthhbys = monthhbys + dayhbys;
					if(daytimes<=0.0){
						dayxsys = 0.0;
						length++;
					}else{
						dayxsys = dayhbys / daytimes;
						monthxxys = dayxsys + monthxxys;
					}
					//每一天的座公里
					dayzgl = dayhbys / dayzwsall / dayhjsingle;
					//天座公里之和
					monthzgl = monthzgl + dayzgl;
					//每一天的客座率
					daykzl = daykzlall / index; 
					//天客座率之和
					monthkzl = monthkzl + daykzl;
					//每一天总人数
					dayperson =  dayskperson + daytdperson;
					//天总人数之和
					monthperson = monthperson + dayperson;
				}
				double bcc = monthClases;
				if(monthClases<=0){
					monthClases = 1.0;
				}
				//一个航班号强烈按照一天一班来算
				salesReport.setHbys(df1.format(monthhbys));
				if(bcc-(length-(31-bcc))>0){
					salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
				}else{
					salesReport.setXssr(df1.format(monthxxys));
				}
				salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
				salesReport.setPjkzl(df1.format(monthkzl/monthClases));
				salesReport.setStzsr(monthperson+"");
				salesReport.setBanci(df1.format(bcc)+"");
				salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
				salesReport.setBcDateList(oldbcDateList);
				if(Double.parseDouble(salesReport.getHbys())>0){
					objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
			}
			retMap.put("lastmonthData", objMapOld);
			retMap.put("flag", flag);
		}
		retMap.put("exceptionFlag", exceptionFlag);
		return retMap;
	}
	@Override
	public Map<String, Object> getYearReportIncomeInfo(SalesReportQuery salesReportQuery) {
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		String stratTime = salesReportQuery.getStartTime();
		String endTime = salesReportQuery.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00");
		//得到当前航季度的年、月份
		List<String> datenowList = new ArrayList<String>();
		List<Z_Airdata> thisWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			String [] str = stratTime.split("-");
			String [] str2 = endTime.split("-");
			salesReportQuery.setStartTime((Integer.parseInt(str[0])-1)+"-"+str[1]+"-"+str[2]);
			salesReportQuery.setEndTime((Integer.parseInt(str[0])-1)+"-"+str2[1]+"-"+str2[2]);
		}
		
		List<String> dateOldList = new ArrayList<String>();
		List<Z_Airdata> lastWeekZairdataListAll =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
		//得到当前航季的年月列表
		String [] strstart = null;
		String [] endstart = null;
		if(!TextUtil.isEmpty(stratTime)&&!TextUtil.isEmpty(endTime)){
			strstart = stratTime.split("-");
			endstart = endTime.split("-");
		}
		if(strstart!=null&&endstart!=null){
				datenowList.add(strstart[0]+"01");
				datenowList.add(strstart[0]+"02");
				datenowList.add(strstart[0]+"03");
				datenowList.add(strstart[0]+"04");
				datenowList.add(strstart[0]+"05");
				datenowList.add(strstart[0]+"06");
				datenowList.add(strstart[0]+"07");
				datenowList.add(strstart[0]+"08");
				datenowList.add(strstart[0]+"09");
				datenowList.add(strstart[0]+"10");
				datenowList.add(strstart[0]+"11");
				datenowList.add(strstart[0]+"12");
		}
		String stratTimeold = salesReportQuery.getStartTime();
		String endTimeold = salesReportQuery.getEndTime();
		String [] strstartold = null;
		String [] endstartold = null;
		if(!TextUtil.isEmpty(stratTimeold)&&!TextUtil.isEmpty(endTimeold)){
			strstartold = stratTimeold.split("-");
			endstartold = endTimeold.split("-");
		}
		if(strstartold!=null&&endstartold!=null){
				dateOldList.add(strstartold[0]+"01");
				dateOldList.add(strstartold[0]+"02");
				dateOldList.add(strstartold[0]+"03");
				dateOldList.add(strstartold[0]+"04");
				dateOldList.add(strstartold[0]+"05");
				dateOldList.add(strstartold[0]+"06");
				dateOldList.add(strstartold[0]+"07");
				dateOldList.add(strstartold[0]+"08");
				dateOldList.add(strstartold[0]+"09");
				dateOldList.add(strstartold[0]+"10");
				dateOldList.add(strstartold[0]+"11");
				dateOldList.add(strstartold[0]+"12");
		}
		//是否包含异常数据
		List<Z_Airdata> thisWeekZairdataListAllNew = getIsIncludeExceptionData(thisWeekZairdataListAll,salesReportQuery);
		List<Z_Airdata> lastWeekZairdataListAllNew = getIsIncludeExceptionData(lastWeekZairdataListAll,salesReportQuery);
		String flag = "0";
		if((thisWeekZairdataListAllNew!=null&&thisWeekZairdataListAllNew.size()>0)){
			flag = "1";
		}
		Map<String, Object> retMap = new TreeMap<String, Object>();
		if(!TextUtil.isEmpty(pas_stpCode)){
			double zys = 0.0;
			double kzl = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(datenowList!=null&&datenowList.size()>0){
				int havingMonths = 0 ;
				for (String month : datenowList) {
					List<Z_Airdata> banciList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							banciList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(banciList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length = 0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsq = 0.0;
						Double dayzwsh = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double dayhjsingleq = 0.0;
						Double dayhjlongq = 0.0;
						Double dayhjsingleh = 0.0;
						Double dayhjlongh = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						double zgld = 0.0;
						for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
									zgld = zgld + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
								}
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								String dpt = z_Airdata.getDpt_AirPt_Cd();
								String arr = z_Airdata.getArrv_Airpt_Cd();
								if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))||(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))){
									if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
										//去程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										//短段航距之和
										dayhjsingleq = dayhjsingleq + z_Airdata.getSailingDistance();
									}else{
										dayhjlongq = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsq = (double) z_Airdata.getCount_Set();
									}
									
								}
								if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))){
									if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
									//回程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										dayhjsingleh = dayhjsingleh + z_Airdata.getSailingDistance();
									}else{
										dayhjlongh = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsh = (double) z_Airdata.getCount_Set();
									}
								}
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								dayzwsh = (double) z_Airdata.getCount_Set();
								index ++;
							}
						}
						//往返座位数之和
						dayzwsall = dayzwsq+dayzwsh;
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=1.0;
						}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
						//天小时营收和
//						monthxxys = monthxxys + dayxsys;
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
						//每一天的座公里
						if(index<6||dayzwsall<=1){
							dayzgl = zgld/index;
						}else{
							dayzgl = dayhbys / dayzwsall / dayhjsingle;
						}
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
						xsys = xsys + monthxxys/(bcc-(length-(31-bcc)));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/Math.round(monthClases)));
					salesReport.setPjkzl(df1.format(monthkzl/Math.round(monthClases)));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/Math.round(monthClases))+"");
					objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					zys = zys + monthhbys;
					zgl = zgl + monthzgl/Math.round(monthClases);
					kzl = kzl + monthkzl/Math.round(monthClases);
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
					jbrs = jbrs + monthperson/Math.round(monthClases);
					if(monthhbys>0){
						havingMonths ++ ;
					}
				}
				if(havingMonths<=0){
					havingMonths = 1;
				}
				retMap.put("monthData", objMap);
				retMap.put("zys", df2.format(zys));
				retMap.put("xsys", df2.format(xsys/havingMonths));
				retMap.put("zgl", df1.format(zgl/havingMonths));
				retMap.put("kzl", df1.format(kzl/havingMonths));
				retMap.put("zrs", zrs);
				retMap.put("zbc", df1.format(zbc));
				retMap.put("jbrs", df1.format(jbrs/havingMonths));
			}
			//去年同期数据
			if(dateOldList!=null&&dateOldList.size()>0){
				for (String month : dateOldList) {
					List<Z_Airdata> banciList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							banciList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(banciList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length = 0 ;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsq = 0.0;
						Double dayzwsh = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double dayhjsingleq = 0.0;
						Double dayhjlongq = 0.0;
						Double dayhjsingleh = 0.0;
						Double dayhjlongh = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						double zgld = 0.0;
						for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								if(z_Airdata.getTotalNumber()>0&&z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
									zgld = zgld + (double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance();
								}
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								String dpt = z_Airdata.getDpt_AirPt_Cd();
								String arr = z_Airdata.getArrv_Airpt_Cd();
								if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))||(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))){
									if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
										//去程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										//短段航距之和
										dayhjsingleq = dayhjsingleq + z_Airdata.getSailingDistance();
									}else{
										dayhjlongq = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsq = (double) z_Airdata.getCount_Set();
									}
								}
								if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))){
									if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
										//回程两个短段数据
										double dates = 0;
										if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
										}else{
											dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
										}
										if(dates<0){
											dates = dates + 24;
										}
										daytimes = daytimes + dates;
										dayhjsingleh = dayhjsingleh + z_Airdata.getSailingDistance();
									}else{
										dayhjlongh = (double) z_Airdata.getSailingDistance();
									}
									if(z_Airdata.getCount_Set()>0){
										dayzwsh = (double) z_Airdata.getCount_Set();
									}
								}
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						//往返座位数之和
						dayzwsall = dayzwsq+dayzwsh;
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjsingleh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongq;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=dayhjlongh;
						}
						if(dayhjsingle<=0.0){
							dayhjsingle=1.0;
						}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
						//天小时营收和
//						monthxxys = monthxxys + dayxsys;
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
						//每一天的座公里
						if(index<6||dayzwsall<=1){
							dayzgl = zgld/index;
						}else{
							dayzgl = dayhbys / dayzwsall / dayhjsingle;
						}
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/Math.round(monthClases)));
					salesReport.setPjkzl(df1.format(monthkzl/Math.round(monthClases)));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/Math.round(monthClases))+"");
					objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
				retMap.put("lastmonthData", objMapOld);
				retMap.put("flag", flag);
			}
		}else{
			//直飞	
			double zys = 0.0;
			double kzl = 0.0;
			double xsys = 0.0;
			double zgl = 0.0;
			double zrs = 0.0;
			double zbc = 0.0;
			double jbrs = 0.0;
			Map<String, SalesReport> objMap = new TreeMap<String, SalesReport>();
			Map<String, SalesReport> objMapOld = new TreeMap<String, SalesReport>();
			if(datenowList!=null&&datenowList.size()>0){
				int	havingMonths =0 ;
				for (String month : datenowList) {
					List<Z_Airdata> banciList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							banciList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(banciList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length = 0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						for (Z_Airdata z_Airdata : thisWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//去程两个短段数据
								if(z_Airdata.getCount_Set()>0){
									dayzwsall = dayzwsall + (double) z_Airdata.getCount_Set();
								}else{
									dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								}
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						//往返座位数之和
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){dayhjsingle=1.0;}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
//						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
//						//天小时营收和
//						monthxxys = monthxxys + dayxsys;
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
						//每一天的座公里
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
						xsys = xsys + monthxxys/(bcc-(length-(31-bcc)));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
					salesReport.setPjkzl(df1.format(monthkzl/monthClases));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
					objMap.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
					zys = zys + monthhbys;
					zgl = zgl + monthzgl/monthClases;
					kzl = kzl + monthkzl/monthClases;
					zrs = zrs + monthperson;
					zbc = zbc + bcc;
					jbrs = jbrs + monthperson/monthClases;
					if(monthhbys>0){
						havingMonths ++ ;
					}
				}
				if(havingMonths<=0){
					havingMonths = 1;
				}
				retMap.put("monthData", objMap);
				retMap.put("zys", df2.format(zys));
				retMap.put("xsys", df2.format(xsys/havingMonths));
				retMap.put("zgl", df1.format(zgl/havingMonths));
				retMap.put("kzl", df1.format(kzl/havingMonths));
				retMap.put("zrs", zrs);
				retMap.put("zbc", df1.format(zbc));
				retMap.put("jbrs", df1.format(jbrs/havingMonths));
			}
			//去年同期数据
			if(dateOldList!=null&&dateOldList.size()>0){
				for (String month : dateOldList) {
					List<Z_Airdata> banciList = new ArrayList<Z_Airdata>(); 
					SalesReport salesReport = new SalesReport();
					Double monthClases = 0.0;
					//找到当前月有多少班次 根据有时间的来计算
					for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						if(month.equals(day.split("-")[0]+day.split("-")[1])){
							banciList.add(z_Airdata);
						}
					}
					monthClases = TextUtil.getExecClass(banciList);
					Double monthhbys = 0.0;
					Double monthxxys = 0.0;
					Double monthzgl = 0.0;
					Double monthkzl = 0.0;
					Double monthperson = 0.0;
					int length = 0;
					for(int i=1;i<=31;i++){
						String day = month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6))))+"-"+(i>9?i:("0"+i));
						Double dayhbys = 0.0;
						Double daytimes = 0.0;
						Double dayxsys = 0.0;
						Double dayzgl = 0.0;
						Double dayzwsall = 0.0;
						Double dayhjsingle = 0.0;
						Double daykzl = 0.0;
						Double daykzlall = 0.0;
						Double dayskperson = 0.0;
						Double daytdperson = 0.0;
						Double dayperson = 0.0;
						int index = 0;
						for (Z_Airdata z_Airdata : lastWeekZairdataListAllNew) {
							if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
								//每一天的航班营收之和
								dayhbys = dayhbys + z_Airdata.getTotalNumber();
								//去程两个短段数据
								if(z_Airdata.getCount_Set()>0){
									dayzwsall = dayzwsall + (double) z_Airdata.getCount_Set();
								}else{
									dayzwsall = dayzwsall + (double) z_Airdata.getTal_Nbr_Set();
								}
								//短段航距之和
								if(z_Airdata.getSailingDistance()>0){
									dayhjsingle = (double) z_Airdata.getSailingDistance();
								}
								//回程两个短段数据
								double dates = 0;
								if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm()))){
								}else{
									dates = ((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000);
								}
								if(dates<0){
									dates = dates + 24;
								}
								daytimes = daytimes + dates;
								//每一天的综合客座率之和
								if(z_Airdata.getTal_Nbr_Set()>0){
									daykzlall = daykzlall + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								//每一天的散客人数
								dayskperson = dayskperson + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
								//每一天的团队人数
								daytdperson = daytdperson + z_Airdata.getGrp_Nbr();
								index ++;
							}
						}
						//往返座位数之和
						if(dayzwsall<=0.0){dayzwsall=1.0;}
						if(dayhjsingle<=0.0){dayhjsingle=1.0;}
						if(index<=0){index=1;}
						//天营收之和
						monthhbys = monthhbys + dayhbys;
//						//每一天的小时营收
//						dayxsys = dayhbys / daytimes;
//						//天小时营收和
//						monthxxys = monthxxys + dayxsys;
						if(daytimes<=0.0){
							dayxsys = 0.0;
							length++;
						}else{
							dayxsys = dayhbys / daytimes;
							monthxxys = dayxsys + monthxxys;
						}
						//每一天的座公里
						dayzgl = dayhbys / dayzwsall / dayhjsingle;
						//天座公里之和
						monthzgl = monthzgl + dayzgl;
						//每一天的客座率
						daykzl = daykzlall / index; 
						//天客座率之和
						monthkzl = monthkzl + daykzl;
						//每一天总人数
						dayperson =  dayskperson + daytdperson;
						//天总人数之和
						monthperson = monthperson + dayperson;
					}
					double bcc = monthClases;
					if(monthClases<=0){
						monthClases = 1.0;
					}
					//一个航班号强烈按照一天一班来算
					salesReport.setHbys(df1.format(monthhbys));
					if(bcc-(length-(31-bcc))>0){
						salesReport.setXssr(df1.format(monthxxys/(bcc-(length-(31-bcc)))));
					}else{
						salesReport.setXssr(df1.format(monthxxys));
					}
					salesReport.setSet_Ktr_Ine(df1.format(monthzgl/monthClases));
					salesReport.setPjkzl(df1.format(monthkzl/monthClases));
					salesReport.setStzsr(monthperson+"");
					salesReport.setBanci(df1.format(bcc)+"");
					salesReport.setJbrs(df1.format(monthperson/monthClases)+"");
					objMapOld.put(month.substring(0,4)+"-"+(Integer.parseInt(month.substring(4,6))>9?month.substring(4,6):("0"+Integer.parseInt(month.substring(4,6)))), salesReport);
				}
				retMap.put("lastmonthData", objMapOld);
				retMap.put("flag", flag);
			}
		}
		salesReportQuery.setStartTime(stratTime);
		salesReportQuery.setEndTime(endTime);
		return retMap;
	}
	
	
	@Override
	public Map<String,Object> getDates(SalesReportQuery salesReportQuery) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> list = null;
		try {
			if(salesReportQuery.getMonth()==null||"".equals(salesReportQuery.getMonth())){
				String newEstMonth = salesReportServiceMapper.getNewestMonth(salesReportQuery);
				if(StringUtils.isEmpty(newEstMonth)||"".equals(newEstMonth)){
					map.put("month", null);
					map.put("list", list);
					return map;
				}else{
					salesReportQuery.setMonth(newEstMonth);
					map.put("month", newEstMonth);
				}
			}
			list = salesReportServiceMapper.getDates(salesReportQuery);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public Map<String, Object> getMonths(SalesReportQuery salesReportQuery) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Integer> list = null;
		try {
			if(salesReportQuery.getYear()==null||"".equals(salesReportQuery.getYear())){
				String newEstYear = salesReportServiceMapper.getNewestYear(salesReportQuery);
				if(StringUtils.isEmpty(newEstYear)||"".equals(newEstYear)){
					map.put("month", null);
					map.put("list", list);
					return map;
				}else{
					salesReportQuery.setYear(newEstYear);
					map.put("year", newEstYear);
				}
			}
			list = salesReportServiceMapper.getMonths(salesReportQuery);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public Map<String, Object> getSeasons(SalesReportQuery salesReportQuery) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> list = new ArrayList<String>();
		List<String> years = salesReportServiceMapper.getYears(salesReportQuery);
		if(years!=null&&years.size()>0){
			int a = (Integer.valueOf(years.get(0)));
			int b = (Integer.valueOf(years.get(years.size()-1)));
			years.add(0,(a-1)+"");//添加数据年份最小值上一年
			years.add((b+1)+"");
			for (String str : years) {
				Integer i = Integer.parseInt(str);
				Map<String,String> dateMap = TextUtil.getXQDate(i);
				salesReportQuery.setStartTime(dateMap.get("startTime"));
				salesReportQuery.setEndTime(dateMap.get("endTime"));
				List<String> dateList = salesReportServiceMapper.getDateByHJ(salesReportQuery);
				if(dateList!=null&&dateList.size()>0){
					list.add(i+"");
				}
				Map<String,String> dateMap2 = TextUtil.getDCDate(i);
				salesReportQuery.setStartTime(dateMap2.get("startTime"));
				salesReportQuery.setEndTime(dateMap2.get("endTime"));
				List<String> dateList2 = salesReportServiceMapper.getDateByHJ(salesReportQuery);
				if(dateList2!=null&&dateList2.size()>0){
					list.add(i+"-"+(i+1));
				}
			}
		}
		map.put("list", list);
		return map;
	}
	@Override
	public Map<String, Object> getYears(SalesReportQuery salesReportQuery) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> list = new ArrayList<String>();
		list = salesReportServiceMapper.getYears(salesReportQuery);
		map.put("list", list);
		return map;
	}
	@Override
	public List<String> getHavingDataDayList(SalesReportQuery salesReportQuery) {
		List<String> dateList = salesReportServiceMapper.getHavingDataDayList(salesReportQuery);
		List<String> dateListNew = new ArrayList<String>();
		for (String date : dateList) {
			String []  str = date.split("-");
			dateListNew.add(str[0]+"-"+Integer.parseInt(str[1])+"-"+Integer.parseInt(str[2]));
		}
		return dateListNew;
	}
	
	/**
	 * 是否包含异常数据
	 * @Title: getIsIncludeExceptionData 
	 * @Description:  
	 * @param @param allList
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<Z_Airdata>     
	 * @throws
	 */
	public List<Z_Airdata>	 getIsIncludeExceptionData(List<Z_Airdata> allList, SalesReportQuery salesReportQuery){
	 	//包括异常数据的所有数据
		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
		
		zairdataListA = allList;
		//返回集合
		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
		//异常数据
		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
		//最终存放数据的list
		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		list.addAll(zairdataListA);
		//找出异常数据
		for (Z_Airdata z_Airdata : zairdataListA) {
			if(z_Airdata.getCount_Set()<=0||TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())){
				String fltNum = z_Airdata.getFlt_Nbr();
				Date date = z_Airdata.getLcl_Dpt_Day();
				for (Z_Airdata z_Airdata2 : zairdataListA) {
					String fltNum2 = z_Airdata2.getFlt_Nbr();
					Date date2 = z_Airdata2.getLcl_Dpt_Day();
					if(fltNum.equals(fltNum2)&&sdf.format(date).equals(sdf.format(date2))){
						if(!zairdataListC.contains(z_Airdata2)){
							zairdataListC.add(z_Airdata2);
						}
					}
				}
			}
		}
		for (Z_Airdata z_Airdatac : zairdataListC) {
			//a就是正常数据
			list.remove(z_Airdatac);
		}
		//判断是否包含异常数据
		if("on".equals(salesReportQuery.getIsIncludeExceptionData())){
			zairdataListB = allList;
		}else{
			zairdataListB = zairdataListA;
		}
		return zairdataListB;
 }
	@Override
	public List<FltDyDetial> getFltDyDetialList(SalesReportQuery salesReportQuery, List<String> datesList) {
		List<FltDyDetial> fltDyDetialList = new ArrayList<FltDyDetial>();
		List<String> datesListtemp = new ArrayList<String>();
		Gson gson=new Gson();
		List<Yesterday_ZD> yesterday_ZDList  = new ArrayList<Yesterday_ZD>();
		//调用外部接口
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_fields/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(salesReportQuery.getDpt_AirPt_Cd())){
				connect.data("dep_airport_code", salesReportQuery.getDpt_AirPt_Cd());
			}
			if(!TextUtil.isEmpty(salesReportQuery.getPas_stp())){
				connect.data("stop_airport_code", salesReportQuery.getPas_stp());
			}
			if(!TextUtil.isEmpty(salesReportQuery.getArrv_Airpt_Cd())){
				connect.data("arr_airport_code", salesReportQuery.getArrv_Airpt_Cd());
			}
			if(!TextUtil.isEmpty(salesReportQuery.getGoNum())){
				connect.data("go_fltnbr", salesReportQuery.getGoNum());
			}
			if(!TextUtil.isEmpty(salesReportQuery.getHuiNum())){
				connect.data("back_fltnbr", salesReportQuery.getHuiNum());
			}
			if(!TextUtil.isEmpty(salesReportQuery.getStartTime())){
				connect.data("start_date", salesReportQuery.getStartTime());
			}
			if(!TextUtil.isEmpty(salesReportQuery.getEndTime())){
				connect.data("end_date", salesReportQuery.getEndTime());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			yesterday_ZDList = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<Yesterday_ZD>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		List<Yesterday_ZD> yesterday_ZDList = salesReportServiceMapper.getYesterday_ZDList(salesReportQuery);
		for (Yesterday_ZD yesterday_ZD : yesterday_ZDList) {
			if(!datesListtemp.contains(yesterday_ZD.getAir_date())){
				datesListtemp.add(yesterday_ZD.getAir_date());
			}
		}
		Map<String,List<Yesterday_ZD>> tmap = new HashMap<String,List<Yesterday_ZD>>();
		if(datesListtemp!=null&&datesListtemp.size()>0){
			for (String date : datesListtemp) {
				List<Yesterday_ZD> tlist = new ArrayList<Yesterday_ZD>();
				for (Yesterday_ZD yesterday_ZD : yesterday_ZDList) {
					if(date.equals(yesterday_ZD.getAir_date())){
						tlist.add(yesterday_ZD);
					}
				}
				tmap.put(date, tlist);
			}
		}
		for (Entry<String,List<Yesterday_ZD>> entry : tmap.entrySet()) {
			FltDyDetial fltDyDetial = new FltDyDetial();
			String date = entry.getKey();
			List<Yesterday_ZD> tt = entry.getValue();
			String status = "0";
			String status1 = "0";
			String status2 = "0";
			String content = "";
			for (Yesterday_ZD yesterday_ZD : tt) {
				if("提前取消".equals(yesterday_ZD.getState())){
					status1 = "1";
					content = content +  yesterday_ZD.getDpt_AirPt_Cd() + "-" +yesterday_ZD.getArrv_Airpt_Cd()+"提前取消,";
				}
				if("取消".equals(yesterday_ZD.getState())){
					status1 = "1";
					content = content +  yesterday_ZD.getDpt_AirPt_Cd() + "-" +yesterday_ZD.getArrv_Airpt_Cd()+"取消,";
				}
				if("备降后取消".equals(yesterday_ZD.getState())){
					status1 = "1";
					content = content +  yesterday_ZD.getDpt_AirPt_Cd() + "-" +yesterday_ZD.getArrv_Airpt_Cd()+"备降后取消,";
				}
				if("延误".equals(yesterday_ZD.getState())){
					status2 = "1";
					content = content +  yesterday_ZD.getDpt_AirPt_Cd() + "-" +yesterday_ZD.getArrv_Airpt_Cd()+"延误,";
				}
			}
			if("1".equals(status1)&&"1".equals(status2)){
				status = "3";//既延误又取消
			}else{
				if("0".equals(status1)&&"0".equals(status2)){
					status = "0";//正常
				}else{
					if("1".equals(status1)&&"0".equals(status2)){
						status = "2";//提前取消
					}else{
						if("0".equals(status1)&&"1".equals(status2)){
							status = "1";//延误
						}
					}
				}
			}
			fltDyDetial.setStatus(status);
			if(content.length()>0){
				content = content.substring(0, content.length()-1);
			}
			fltDyDetial.setContent(content);
			fltDyDetial.setFlyDate(date);
			fltDyDetialList.add(fltDyDetial);
			for (FltDyDetial fltDyDetialtemp : fltDyDetialList) {
				if(!TextUtil.isEmpty(fltDyDetialtemp.getContent())){
					salesReportQuery.setEndTime(fltDyDetialtemp.getFlyDate());
					salesReportQuery.setStartTime(fltDyDetialtemp.getFlyDate());
					List<Z_Airdata> zairdataListAllToday =salesReportServiceMapper.getYearSalesReportNew(salesReportQuery);
					if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
						if(zairdataListAllToday.size()>1){
							//采集回来的数据和原有的数据冲突，有无数据的冲突
							fltDyDetialtemp.setContent("");
							fltDyDetialtemp.setStatus("0");
						}else{
							//采集回来的数据和原有的数据冲突，各个段的冲突
						}
					}else{
						if(zairdataListAllToday.size()>5){
							//采集回来的数据和原有的数据冲突，有无数据的冲突
							fltDyDetialtemp.setContent("");
							fltDyDetialtemp.setStatus("0");
						}else{
							//采集回来的数据和原有的数据冲突，各个段的冲突
						}
					}
				}
			}
		}
		return fltDyDetialList;
	}
	
}
