package org.ldd.ssm.crm.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.AirData;
import org.ldd.ssm.crm.domain.AirLineData;
import org.ldd.ssm.crm.domain.AllClzIncome;
import org.ldd.ssm.crm.domain.CabinSeatSet;
import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.DivideSeason;
import org.ldd.ssm.crm.domain.DivideSeasonRowMapper;
import org.ldd.ssm.crm.domain.DivideTime;
import org.ldd.ssm.crm.domain.DivideTimeRowMapper;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.IncomeCount;
import org.ldd.ssm.crm.domain.IncomeCountTime;
import org.ldd.ssm.crm.domain.MonthSalePlan;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.SalePlanData;
import org.ldd.ssm.crm.domain.TimeCount;
import org.ldd.ssm.crm.domain.TimeQuery;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.domain.Z_AirdataRowMapper;
import org.ldd.ssm.crm.domain.ZdataRowMapper;
import org.ldd.ssm.crm.mapper.AirLineMapper;
import org.ldd.ssm.crm.mapper.BasicDataMapper;
import org.ldd.ssm.crm.mapper.CabinSeatSetMapper;
import org.ldd.ssm.crm.mapper.DOWMapper;
import org.ldd.ssm.crm.mapper.DivideSeasonMapper;
import org.ldd.ssm.crm.mapper.DivideTimeMapper;
import org.ldd.ssm.crm.mapper.FormulaUtilMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.mapper.SalePlanDataMapper;
import org.ldd.ssm.crm.mapper.SalesReportServiceMapper;
import org.ldd.ssm.crm.query.AirLineQuery;
import org.ldd.ssm.crm.query.AirLineQueryNew;
import org.ldd.ssm.crm.query.CabinSeatSetQuery;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.FormulaUtilQuery;
import org.ldd.ssm.crm.query.MonthParameterQuery;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.service.IAirLineService;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.MyMathUtils;
import org.ldd.ssm.crm.utils.ParameterUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.ConnectDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AirLineServiceImpl implements IAirLineService {
	
	Logger log = Logger.getLogger(AirLineServiceImpl.class);
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DOWMapper dowMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	@Autowired
	private AirLineMapper airLineMapper;
	@Autowired
	private DivideSeasonMapper seasonMapper;
	@Autowired
	private DivideTimeMapper timeMapper;
	@Autowired
	private SalePlanDataMapper dataMapper;
	@Autowired
	private CabinSeatSetMapper setMapper;
	@Autowired
	private BasicDataMapper basicMapper;
	@Autowired
	private FormulaUtilMapper formulaUtilMapper;
	@Autowired
	private SalesReportServiceMapper salesReportServiceMapper;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 拿到图表所需要统计数据
	 */
	public List<OutPort> getOutPort(DOWQuery dta_Sce,List<Z_Airdata> airdatas) {
		List<OutPort> rows = new ArrayList<OutPort>();
		//根据查询条件,获得查询条件内的所有数据
		List<Z_Airdata> new_OutPort = ParameterUtils.getZ_airDataByPara(dta_Sce,airdatas);
		//将数据进行分月排序
		List<List<Z_Airdata>> method_Z_Airdata = ParameterUtils.getMethod_Z_Airdata(new_OutPort);
		for (List<Z_Airdata> list : method_Z_Airdata) {
			if(list.size()>0){
				rows.add(ParameterUtils.getCountData(list));
			}
		}
		return rows;
	}

	/**
	 * 拿到年数据的所有月份的均班统计数据
	 */
	public List<EvenPort> getEvenPort(List<Z_Airdata> airdatas,DOWQuery dta_Sce) {
		List<EvenPort> rows = new ArrayList<EvenPort>();
		//根据查询条件,获得查询条件内的所有数据
		List<Z_Airdata> new_OutPort = ParameterUtils.getZ_airDataByPara(dta_Sce,airdatas);//分辨出查询条件内的数据
		List<List<Z_Airdata>> method_Z_Airdata = ParameterUtils.getMethod_Z_Airdata(new_OutPort);//对数据进行分月
		for (List<Z_Airdata> list : method_Z_Airdata) {
			if(list.size()>0){
				rows.add(ParameterUtils.getCountData_Two(list));
			}
		}

		return rows;
	}
	public List<DOWQuery> getArrayParameter(DOWQuery dow) {
		// 装去程航线的航段容器
		List<String[]> toList = new ArrayList<String[]>();
		// 装反程航线的航段容器
		List<String[]> returnList = new ArrayList<String[]>();
		// 暂时缓存
		List<DOWQuery> dowQueries = new ArrayList<DOWQuery>();
		// 新数据存放
		List<DOWQuery> newQueries = new ArrayList<DOWQuery>();
		String dpt_AirPt_Cd = outPortMapper.getAirCodeByName(dow.getDpt_AirPt_Cd());
		String arrv_Airpt_Cd = outPortMapper.getAirCodeByName(dow.getArrv_Airpt_Cd());
		String pas_stp = outPortMapper.getAirCodeByName(dow.getPas_stp());
		//航班号
		String[] split = {};
		if(!TextUtil.isEmpty(dow.getFlt_nbr())&&!dow.getFlt_nbr().equals("汇总")){
			String flt_nbr = dow.getFlt_nbr();
			String replaceAll = flt_nbr.replaceAll("航班号", "");
			split = replaceAll.split("/");
		}
		if (dow.getPas_stp() != "" && dow.getPas_stp() != null) {
			// 去程
			toList.add(new String[] { dow.getDpt_AirPt_Cd(),dow.getArrv_Airpt_Cd() });
			toList.add(new String[] { dow.getDpt_AirPt_Cd(), dow.getPas_stp() });
			toList.add(new String[] { dow.getPas_stp(), dow.getArrv_Airpt_Cd() });
			// 返程
			returnList.add(new String[] { dow.getArrv_Airpt_Cd(),dow.getPas_stp() });
			returnList.add(new String[] { dow.getPas_stp(),dow.getDpt_AirPt_Cd() });
			returnList.add(new String[] { dow.getArrv_Airpt_Cd(),dow.getDpt_AirPt_Cd() });
			// 创建去程3个查询对象
			DOWQuery dowQuery;
			for (int i = 0; i < 3; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(dpt_AirPt_Cd + pas_stp + arrv_Airpt_Cd);
				if(split.length>0){
					dowQuery.setFlt_nbr(split[0]);
				}
				dowQueries.add(dowQuery);
			}
			// 创建返程三个查询对象
			for (int i = 0; i < 3; i++) {
				dowQuery = new DOWQuery();
				if(split.length>0){
					dowQuery.setFlt_nbr(split[1]);
				}
				dowQuery.setFlt_Rte_Cd(arrv_Airpt_Cd + pas_stp + dpt_AirPt_Cd);
				dowQueries.add(dowQuery);
			}
			for (int i = 0; i < 3; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(toList.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(toList.get(i)[1]);
				newQueries.add(dowQuery2);
			}

			for (int i = 0; i < 3; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i + 3);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(returnList.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(returnList.get(i)[1]);
				newQueries.add(dowQuery2);
			}
			return newQueries;
		} else {
			// 去程
			toList.add(new String[] { dow.getDpt_AirPt_Cd(),
					dow.getArrv_Airpt_Cd() });
			// 返程
			returnList.add(new String[] {dow.getArrv_Airpt_Cd(),
					dow.getDpt_AirPt_Cd() });
			// 创建去程1个查询对象
			DOWQuery dowQuery;
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(dpt_AirPt_Cd + arrv_Airpt_Cd);
				if(split.length>0){
					dowQuery.setFlt_nbr(split[0]);
				}
				dowQueries.add(dowQuery);
			}
			// 创建返程三个查询对象
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(arrv_Airpt_Cd + dpt_AirPt_Cd);
				if(split.length>0){
					dowQuery.setFlt_nbr(split[1]);
				}
				dowQueries.add(dowQuery);
			}
			for (int i = 0; i < 1; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(toList.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(toList.get(i)[1]);
				newQueries.add(dowQuery2);
			}

			for (int i = 0; i < 1; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i + 1);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(returnList.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(returnList.get(i)[1]);
				newQueries.add(dowQuery2);
			}
			return newQueries;
		}
	}
	public List<String> getFlt_Nbr(DOWQuery dowData) {
		String airCodeByName = outPortMapper.getAirCodeByName(dowData
				.getDpt_AirPt_Cd());
		String airCodeByName2 = outPortMapper.getAirCodeByName(dowData
				.getArrv_Airpt_Cd());
		dowData.setDpt_AirPt_Cd(airCodeByName);
		dowData.setArrv_Airpt_Cd(airCodeByName2);
		return airLineMapper.getFlt_Nbr(dowData);
	}
	public List<String> getMonthFltNum(DOWQuery dowData) {
		String airCodeByName = outPortMapper.getAirCodeByName(dowData
				.getDpt_AirPt_Cd());
		String airCodeByName2 = outPortMapper.getAirCodeByName(dowData
				.getArrv_Airpt_Cd());
		dowData.setDpt_AirPt_Cd(airCodeByName);
		dowData.setArrv_Airpt_Cd(airCodeByName2);
		return airLineMapper.getMonthFltNum(dowData);
	}
	/**
	 *获得查询往返航班号的参数
	 */
	public List<DOWQuery> getParameter(DOWQuery dow) {
		// 装去程航线的航段容器
		List<String[]> toList = new ArrayList<String[]>();
		// 装反程航线的航段容器
		List<String[]> returnList = new ArrayList<String[]>();
		// 暂时缓存
		List<DOWQuery> dowQueries = new ArrayList<DOWQuery>();
		// 新数据存放
		List<DOWQuery> newQueries = new ArrayList<DOWQuery>();
		String dpt_AirPt_Cd = outPortMapper.getAirCodeByName(dow.getDpt_AirPt_Cd());
		String arrv_Airpt_Cd = outPortMapper.getAirCodeByName(dow.getArrv_Airpt_Cd());
		String pas_stp = outPortMapper.getAirCodeByName(dow.getPas_stp());
		// 去程
		toList.add(new String[] { dow.getDpt_AirPt_Cd(), dow.getArrv_Airpt_Cd() });
		// 返程
		returnList.add(new String[] { dow.getArrv_Airpt_Cd(),dow.getDpt_AirPt_Cd() });
		if (dow.getPas_stp() != "" && dow.getPas_stp() != null) {
			// 创建去程3个查询对象
			DOWQuery dowQuery;
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(dpt_AirPt_Cd + pas_stp + arrv_Airpt_Cd);
				dowQueries.add(dowQuery);
			}
			// 创建返程三个查询对象
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(arrv_Airpt_Cd + pas_stp + dpt_AirPt_Cd);
				dowQueries.add(dowQuery);
			}
		} else {

			// 创建去程1个查询对象
			DOWQuery dowQuery;
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(dpt_AirPt_Cd + arrv_Airpt_Cd);
				dowQueries.add(dowQuery);
			}
			// 创建返程三个查询对象
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(arrv_Airpt_Cd + dpt_AirPt_Cd);
				dowQueries.add(dowQuery);
			}

		}
		for (int i = 0; i < 1; i++) {
			DOWQuery dowQuery2 = dowQueries.get(i);
			dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
			dowQuery2.setDta_Sce(dow.getDta_Sce());
			dowQuery2.setDpt_AirPt_Cd(toList.get(i)[0]);
			dowQuery2.setArrv_Airpt_Cd(toList.get(i)[1]);
			newQueries.add(dowQuery2);
		}

		for (int i = 0; i < 1; i++) {
			DOWQuery dowQuery2 = dowQueries.get(i + 1);
			dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
			dowQuery2.setDta_Sce(dow.getDta_Sce());
			dowQuery2.setDpt_AirPt_Cd(returnList.get(i)[0]);
			dowQuery2.setArrv_Airpt_Cd(returnList.get(i)[1]);
			newQueries.add(dowQuery2);
		}
		return newQueries;
	}

	public String getTimeCount(DOWQuery dowData) {
		String pas_stpCode = outPortMapper.getAirCodeByName(dowData.getPas_stp());
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(dowData.getDpt_AirPt_Cd());
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(dowData.getArrv_Airpt_Cd());
		dowData.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		dowData.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		dowData.setPas_stp(pas_stpCode);
		TimeQuery timeQuery = airLineMapper.getTimeCount(dowData);
		double pjTime = 1;
		if(timeQuery!=null){
			if(pas_stpCode!=null&&!TextUtil.isEmpty(timeQuery.getBc())){
				double bc = Double.parseDouble(timeQuery.getBc())/3;
				pjTime = Double.parseDouble(timeQuery.getAllTime())/bc+0.4;
			}else if(!TextUtil.isEmpty(timeQuery.getBc())){
				double bc = Double.parseDouble(timeQuery.getBc())/2;
				pjTime = Double.parseDouble(timeQuery.getAllTime())/bc+0.2;
			}
		}
		
		return pjTime+"";
	}

	public TimeCount getTimeCountData(DOWQuery dowData) {
		return airLineMapper.getTimeCountData(dowData);
	}

	public String save(TimeCount timecount) {
		airLineMapper.save(timecount);
		return "保存";
	}

	public String updata(TimeCount timecount) {
		airLineMapper.updata(timecount);
		return "更新";
	}

	public List<List<AllClzIncome>> getTableData(DOWQuery dowData) {
		List<List<AllClzIncome>> lists = new ArrayList<List<AllClzIncome>>();
		String pas_stpCode = outPortMapper.getAirCodeByName(dowData.getPas_stp());
		String dpt_AirPt_CdCode = outPortMapper.getAirCodeByName(dowData.getDpt_AirPt_Cd());
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(dowData.getArrv_Airpt_Cd());
		if(dowData.getPas_stp()==""){
			dowData.setPas_stp(null);
		}
		if(dowData.getCpy_Nm()==""){
			dowData.setCpy_Nm(null);
		}
		List<DOWQuery> arrayParameter = getArrayParameter(dowData);
		arrayParameter = getIfFlt_Nbr(arrayParameter,dowData);
//		dowData.setDpt_AirPt_Cd(arrayParameter.get(0).getDpt_AirPt_Cd());
//		dowData.setArrv_Airpt_Cd(arrayParameter.get(0).getArrv_Airpt_Cd());
//		List<DOW> method = dowMapper.getMethod(dowData);
		
		DOWQuery dowQueryNew = new DOWQuery();
		dowQueryNew.setDta_Sce(dowData.getDta_Sce());
		dowQueryNew.setLcl_Dpt_Day(dowData.getLcl_Dpt_Day());
		if(!TextUtil.isEmpty(dowData.getFlt_nbr())&&!dowData.getFlt_nbr().equals("汇总")){
			String flt_nbr = dowData.getFlt_nbr();
			String replaceAll = flt_nbr.replaceAll("航班号", "");
			String [] split = replaceAll.split("/");
			dowQueryNew.setGoNum(split[0]);
			dowQueryNew.setArrNum(split[1]);
		}else{
			dowQueryNew.setFlt_nbr(null);
		}
		dowQueryNew.setCpy_Nm(dowData.getCpy_Nm());
		dowQueryNew.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		dowQueryNew.setPas_stp(pas_stpCode);
		dowQueryNew.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		List<Z_Airdata> z_AirdataList = airLineMapper.getDataNew(dowQueryNew);
		List<Z_Airdata> grp_zAirdataList = airLineMapper.getgrp_DataNew(dowQueryNew);
		for (int i = 1;i<13 ;i++) {
			List<AllClzIncome> list = new ArrayList<AllClzIncome>();
			for (DOWQuery dowQuery : arrayParameter) {
				Z_Airdata z_Airdata = null;
				Z_Airdata grp_zAirdata = null;
				String dptCode = outPortMapper.getAirCodeByName(dowQuery.getDpt_AirPt_Cd());
				String arrCode = outPortMapper.getAirCodeByName(dowQuery.getArrv_Airpt_Cd());
				for (Z_Airdata z_Airdata1 : z_AirdataList) {
					String dptCodeTemp = z_Airdata1.getDpt_AirPt_Cd();
					String arrCodeTemp = z_Airdata1.getArrv_Airpt_Cd();
					int month = Integer.parseInt(z_Airdata1.getMonth());
					if(dptCode.equals(dptCodeTemp)&&arrCode.equals(arrCodeTemp)&&(i==month)){
						z_Airdata = z_Airdata1;
					}
				}
				for (Z_Airdata z_Airdata2 : grp_zAirdataList) {
					String dptCodeTemp = z_Airdata2.getDpt_AirPt_Cd();
					String arrCodeTemp = z_Airdata2.getArrv_Airpt_Cd();
					int month = Integer.parseInt(z_Airdata2.getMonth());
					if(dptCode.equals(dptCodeTemp)&&arrCode.equals(arrCodeTemp)&&(i==month)){
						grp_zAirdata = z_Airdata2;
					}
				}
				if(z_Airdata!=null){
					z_Airdata.setMethod(i+"");
					z_Airdata.setFlt_Rte_Cd(dowQuery.getFlt_Rte_Cd());
					z_Airdata.setDpt_AirPt_Cd(dowQuery.getDpt_AirPt_Cd());
					z_Airdata.setArrv_Airpt_Cd(dowQuery.getArrv_Airpt_Cd());
					// 根据航程拿到YB运价
					AllClzIncome income = DataUtils.SortData(z_Airdata,z_Airdata.getyBFare(),grp_zAirdata);
					list.add(income);
				}
			}
			lists.add(list);
		}
		return lists;
	}
	//判断是否使用了航班号作为查询对象
	private List<DOWQuery> getIfFlt_Nbr(List<DOWQuery> arrayParameter,
			DOWQuery dowData) {
		if(arrayParameter.size()==6){
			if(dowData.getFlt_nbr().length()>2){
				String[] split = dowData.getFlt_nbr().substring(3, dowData.getFlt_nbr().length()).split("/");
				for (int i = 0; i < 3; i++) {
					arrayParameter.get(i).setFlt_nbr(split[0]);
				}
				for (int i = 3; i < 6; i++) {
					arrayParameter.get(i).setFlt_nbr(split[1]);
				}
			}else{
				for (DOWQuery dowQuery : arrayParameter) {
					dowQuery.setFlt_nbr(null);
				}
			}
		}else{
			if(dowData.getFlt_nbr().length()>2){
				String[] split = dowData.getFlt_nbr().substring(3, dowData.getFlt_nbr().length()).split("/");
				for (int i = 0; i < 2; i++) {
					arrayParameter.get(i).setFlt_nbr(split[i]);
				}
			}else{
				for (DOWQuery dowQuery : arrayParameter) {
					dowQuery.setFlt_nbr(null);
				}
			}
		}
		return arrayParameter;
	}

	public IncomeCountTime getDateAndCost(IncomeCountTime countTime) {
		return airLineMapper.getDateAndCost(countTime);
	}

	public void updataParameter(IncomeCountTime countTime) {
		airLineMapper.updataParameter(countTime);
	}

	public void saveParameter(IncomeCountTime countTime) {
		airLineMapper.saveParameter(countTime);
	}
	/**
	 * 航线收入统计业务逻辑方法入口
	 * @param countTime 人为输入参数对象
	 */
	public List<IncomeCount> getDataTable_old(IncomeCountTime countTime) {
		List<IncomeCount> counts = new ArrayList<IncomeCount>();
		DOWQuery dta_Sce = new DOWQuery();
		dta_Sce.setLcl_Dpt_Day(countTime.getLcl_Dpt_Day());
		dta_Sce.setDta_Sce(countTime.getDta_Sce());
		dta_Sce.setDpt_AirPt_Cd(countTime.getDpt_AirPt_Cd());
		dta_Sce.setArrv_Airpt_Cd(countTime.getArrv_Airpt_Cd());
		dta_Sce.setFlt_nbr(countTime.getFlt_nbr());
		dta_Sce.setCpy_Nm(countTime.getCpy_Nm());
		dta_Sce.setPas_stp(countTime.getPas_stp());
		dta_Sce.setFlt_Rte_Cd(countTime.getFlt_Rte_Cd());
		List<List<AllClzIncome>> tableData = getTableData(dta_Sce);
		for (List<AllClzIncome> list : tableData) {
			if(list.size()!=0){
				int clz = getClz(countTime.getDta_Sce(),countTime.getLcl_Dpt_Day(),list.get(0).getDate(),countTime.getDpt_AirPt_Cd(),countTime.getPas_stp(),countTime.getArrv_Airpt_Cd(),countTime.getFlt_nbr());
				//这里判断是经停还是直飞,大于2表示经停
				if(list.size()>2){
					counts.add(getSixIncomeCount(list,clz,countTime));
				}else{
					//装载每次返回的数据
					counts.add(getTwoIncomeCount(list,clz,countTime));
				}
			}
			
		}
		//此处为统计计数据方法
		counts.add(getCount(counts,countTime.getAgy_Rte()));
		//此处为代理费用计算方法
		counts.add(getAgy_Rte(counts,countTime.getAgy_Rte(),countTime.getTim_Cout()));
		return counts;
	}
	public List<IncomeCount> getDataTable(IncomeCountTime countTime) {
		String dpt_AirPt_CdCode= outPortMapper.getAirCodeByName(countTime.getDpt_AirPt_Cd());
		String arrv_Airpt_CdCode = outPortMapper.getAirCodeByName(countTime.getArrv_Airpt_Cd());
		String pas_stpCode = outPortMapper.getAirCodeByName(countTime.getPas_stp());
		FormulaUtilQuery formulaUtilQuery = new FormulaUtilQuery();
		formulaUtilQuery.setRoleId(UserContext.getCompanyId());
		List<DailyParameters> dailyParametersList = formulaUtilMapper.getHourPriceList(formulaUtilQuery);
		DecimalFormat dff   =new   DecimalFormat("0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SalesReportQuery salesReportQuery = new SalesReportQuery();
		List<IncomeCount> incomeCountList = new ArrayList<IncomeCount>();
		salesReportQuery.setArrv_Airpt_Cd(arrv_Airpt_CdCode);
		salesReportQuery.setDpt_AirPt_Cd(dpt_AirPt_CdCode);
		salesReportQuery.setPas_stp(pas_stpCode);
		salesReportQuery.setHangsi(TextUtil.isEmpty(countTime.getCpy_Nm())?null:countTime.getCpy_Nm());
		salesReportQuery.setFlt_nbr_Count(countTime.getFlt_nbr());
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			str = str.substring(3, str.length());
			String [] nums = str.split("/");
			salesReportQuery.setGoNum(nums[0]);
			salesReportQuery.setHuiNum(nums[1]);
		}
		salesReportQuery.setDta_Sce(countTime.getDta_Sce());
		String year = countTime.getLcl_Dpt_Day();
		int yearr = Integer.parseInt(year.substring(0, 4));
		salesReportQuery.setStartTime(yearr+"-01"+"-01");
		salesReportQuery.setEndTime(yearr+"-12"+"-31");
		
		List<Z_Airdata> zairdataListAll =airLineMapper.getYearSalesReportNew(salesReportQuery);
		for(int i =1;i<=12;i++){
			//包括异常数据的所有数据
			List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
			//不包括异常数据的所有数据
			List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
			//异常数据
			List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataListAll) {
				Date date = z_Airdata.getLcl_Dpt_Day();
				String dt = sdf.format(date);
				String [] dtt = dt.split("-");
				if(i==Integer.parseInt(dtt[1])){
					zairdataListA.add(z_Airdata);
				}
			}
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
			zairdataListB = zairdataListA;
			IncomeCount incomeCount = new IncomeCount();
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
				double bc = 0.00;
				double zrstal = 0.00;
				
				if(!TextUtil.isEmpty(pas_stpCode)){
					//设置班次
					incomeCount.setMethod(i+"");
					bc =zairdataListB.size()/3;
					incomeCount.setClazz(dff.format(bc));
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
					//设置月营收
					incomeCount.setIncome(dff.format(yystal));
					//设置客座率
					double zgltal = 0.00;
					double kzltal = 0.00;
					double bttal = 0.00;
					double bthdltal = 0.00;
					double jbxsystala = 0.00;
					double jbhourpriceMonth = 0.00;
					double jbagencepriceMonth = 0.00;
					double fxsjtalMonth = 0.00;
					for (String datestr : dateList) {
						double zgl = 0.00;
						double kzl = 0.00;
						double bt = 0.00;
						double bthdl = 0.00;
						double jbxsys = 0.00;
						double jbxsystal = 0.00;
						double jbhourprice = 0.00;
						double jbagenceprice = 0.00;
						double fxsjtal = 0.00;
						for (String flyNumstr : flyNumList) {
							double ddrsh = 0.00;
							double ddhch = 0.00;
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
										zws += zairdata.getCount_Set();
										zsr = zsr + zairdata.getTotalNumber();
										fxsj = fxsj + ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
									}
									if((dpt_AirPt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&arrv_Airpt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))||(arrv_Airpt_CdCode.equals(zairdata.getDpt_AirPt_Cd())&&dpt_AirPt_CdCode.equals(zairdata.getArrv_Airpt_Cd()))){
										//同一时间的同一个航班的长段座位数之和，航程之和
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
							jbhourprice = jbhourprice + hourprice;
							jbagenceprice = jbagenceprice + agenceprice;
							fxsjtal = fxsjtal + fxsj;
						}
						jbhourpriceMonth =jbhourpriceMonth + jbhourprice;
						jbagencepriceMonth =jbagencepriceMonth + jbagenceprice;
						zgltal = zgltal + zgl;
						kzltal = kzltal + kzl;
						bttal = bttal + bt;
						bthdltal =bthdltal + bthdl;
						jbxsystala =  jbxsystala + jbxsystal;
						fxsjtalMonth =  fxsjtalMonth + fxsjtal;
					}
					if(kzltal>0.00&&bc>0){
						incomeCount.setParlor(dff.format(kzltal/bc));
					}
					//设置均班小时营收
					if(jbxsystala>0.00&&bc>0){
						incomeCount.setIncomeHour(dff.format(jbxsystala/bc));
					}
					//设置总补贴,均班补贴
					if(bttal>0.00&&bc>0){
						incomeCount.setSubsidyClz(dff.format(bttal/bc));
						incomeCount.setSubsidyMethod(dff.format(bttal));
					}
					//设置小时成本
					if(jbhourpriceMonth>0.00&&bc>0){
						incomeCount.setCostHour(dff.format(jbhourpriceMonth/3/bc));
					}
					//设置代理费率
					if(jbagencepriceMonth>0.00&&bc>0){
						incomeCount.setAgencePrice(dff.format(jbagencepriceMonth/bc));
					}
					//设置均班小时数
					if(fxsjtalMonth>0.00&&bc>0){
						incomeCount.setHour(dff.format(fxsjtalMonth/bc));
					}
					//设置成本班
					if(fxsjtalMonth>0.00&&jbhourpriceMonth>0.00&&bc>0){
						incomeCount.setCostClz(dff.format(fxsjtalMonth*(jbhourpriceMonth/3/bc)));
					}
					//设置固定成本月
					if(fxsjtalMonth>0.00&&jbhourpriceMonth>0.00&&bc>0){
						incomeCount.setFixedCose(dff.format(fxsjtalMonth*(jbhourpriceMonth/3/bc)*bc));
					}
				}else{
					//没有经停
					//设置班次
					incomeCount.setMethod(i+"");
					bc = zairdataListB.size();
					incomeCount.setClazz(dff.format(bc));
					double kzll = 0.00;
					double zwss = 0.00;
					double zgll = 0.00;
					double bt = 0.00;
					double bthdl = 0.00;
					double xsys = 0.00;
					double jbhourpriceMonth = 0.00;
					double jbagencepriceMonth = 0.00;
					for (Z_Airdata zairdata : zairdataListB) {
						//总收入
						yystal = yystal + zairdata.getTotalNumber();
						//总飞时间 
						zfsjtal = zfsjtal + ((Double.parseDouble(zairdata.getLcl_Arrv_Tm()==null?"1":zairdata.getLcl_Arrv_Tm()) - Double.parseDouble(zairdata.getLcl_Dpt_Tm()))/3600/1000);
						//总人数
						zrstal = zrstal + zairdata.getPgs_Per_Cls();
						zwss = zairdata.getCount_Set();
						kzll =kzll +  zairdata.getEgs_Lod_Fts().doubleValue();
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
						jbhourpriceMonth = jbhourpriceMonth + hourprice;
						jbagencepriceMonth = jbagencepriceMonth + agenceprice;
					}
					//设置月营收
					incomeCount.setIncome(dff.format(yystal));
					//设置均班小时营收
					if(xsys>0.00&&bc>0){
						incomeCount.setIncomeHour(dff.format(xsys/bc));
					}
					
					//设置座公里收入
					if(kzll>0.00&&bc>0){
						incomeCount.setParlor(dff.format(kzll/bc*100));
					}
					//设置总补贴,均班补贴
					if(bt>0.00&&bc>0){
						incomeCount.setSubsidyClz(dff.format(bt/bc));
						incomeCount.setSubsidyMethod(dff.format(bt));
					}
					//设置小时成本
					if(jbhourpriceMonth>0.00&&bc>0){
						incomeCount.setCostHour(dff.format(jbhourpriceMonth/bc));
					}
					//设置代理费率
					if(jbagencepriceMonth>0.00&&bc>0){
						incomeCount.setAgencePrice(dff.format(jbagencepriceMonth/bc));
					}
					//设置均班小时数
					if(zfsjtal>0.00&&bc>0){
						incomeCount.setHour(dff.format(zfsjtal/bc));
					}
					//设置成本班
					if(zfsjtal>0.00&&jbhourpriceMonth>0.00&&bc>0){
						incomeCount.setCostClz(dff.format(zfsjtal*(jbhourpriceMonth/bc)));
					}
					//设置固定成本月
					if(zfsjtal>0.00&&jbhourpriceMonth>0.00&&bc>0){
						incomeCount.setFixedCose(dff.format(zfsjtal*(jbhourpriceMonth/bc)*bc));
					}
				}
			}else{
				incomeCount = null;
			}
			if(incomeCount!=null){
				incomeCountList.add(incomeCount);
			}
			
		}
		
		return addHJIncomeCountList(incomeCountList);
	}
	/**
	 * 总收益添加合计数据
	 * @Title: addHJIncomeCountList 
	 * @Description:  
	 * @param @param incomeCountList
	 * @param @return    
	 * @return List<IncomeCount>     
	 * @throws
	 */
	public List<IncomeCount> addHJIncomeCountList(List<IncomeCount> incomeCountList){
		DecimalFormat dff   =new   DecimalFormat("0.00");
		if(incomeCountList!=null&&incomeCountList.size()>0){
			double bcTal = 0.0;//总班次
			double gdcbTal = 0.0;//总固定成本
			double jysrTal = 0.0;//总收入
			double btTal = 0.0;//总补贴
			double jbbtTal = 0.0;//总均班补贴
			double jbxsysTal = 0.0;//总均班小时收入
			double jbdlfTal = 0.0;//总均班代理费
			for (IncomeCount incomeCount : incomeCountList) {
				bcTal = bcTal + Double.parseDouble(incomeCount.getClazz()==null?"0":incomeCount.getClazz());
				gdcbTal = gdcbTal + Double.parseDouble(incomeCount.getFixedCose()==null?"0":incomeCount.getFixedCose());
				jysrTal = jysrTal + Double.parseDouble(incomeCount.getIncome()==null?"0":incomeCount.getIncome());
				btTal = btTal + Double.parseDouble(incomeCount.getSubsidyMethod()==null?"0":incomeCount.getSubsidyMethod());
				jbbtTal = jbbtTal + Double.parseDouble(incomeCount.getSubsidyClz()==null?"0":incomeCount.getSubsidyClz());
				jbxsysTal = jbxsysTal + Double.parseDouble(incomeCount.getIncomeHour()==null?"0":incomeCount.getIncomeHour());
				jbdlfTal = jbdlfTal + Double.parseDouble(incomeCount.getAgencePrice()==null?"0":incomeCount.getAgencePrice());
			}
			if(bcTal>0&&jysrTal>0){
				IncomeCount IncomeCount1 = new  IncomeCount();
				IncomeCount IncomeCount2 = new  IncomeCount();
				IncomeCount1.setMethod("总计");
				IncomeCount1.setClazz(dff.format(bcTal)+"");
				IncomeCount1.setCostClz("");
				IncomeCount1.setFixedCose(dff.format(gdcbTal)+"");
				IncomeCount1.setHour("");
				IncomeCount1.setCostHour("");
				IncomeCount1.setIncomeHour("年经营收入");
				IncomeCount1.setIncome(dff.format(jysrTal)+"");
				IncomeCount1.setSubsidyClz("年补贴 (含促销费)");
				IncomeCount1.setSubsidyMethod(dff.format(jbbtTal+jbdlfTal)+"");
				IncomeCount1.setParlor("");
				
				IncomeCount2.setMethod("代理费率");
				IncomeCount2.setClazz(dff.format(jbdlfTal/incomeCountList.size()*100)+"");
				IncomeCount2.setCostClz("代理费");
				IncomeCount2.setFixedCose(dff.format(jbdlfTal/bcTal*jysrTal)+"");
				IncomeCount2.setHour("均班小时营收");
				IncomeCount2.setCostHour(dff.format(jbxsysTal/incomeCountList.size())+"");
				IncomeCount2.setIncomeHour("均班营收");
				IncomeCount2.setIncome(dff.format(jysrTal/bcTal)+"");
				IncomeCount2.setSubsidyClz("补贴/班");
				IncomeCount2.setSubsidyMethod(dff.format(jbbtTal/incomeCountList.size())+"");
				IncomeCount2.setParlor("");
				incomeCountList.add(IncomeCount1);
				incomeCountList.add(IncomeCount2);
			}
			
		}
		return incomeCountList;
	}
	private IncomeCount getAgy_Rte(List<IncomeCount> counts,String agy_Rte,String tim) {
		IncomeCount count = new IncomeCount();
		count.setMethod("代理费率");
		count.setClazz(agy_Rte+"%");
		count.setCostClz("代理费");
		count.setFixedCose((Double.valueOf(counts.get(counts.size()-1).getIncome())*(Double.valueOf(agy_Rte)/100)+""));
		count.setHour("均班小时营收");
		Integer clazz = Integer.valueOf(counts.get(counts.size()-1).getClazz());
		Integer income = Integer.valueOf(counts.get(counts.size()-1).getIncome());
		Integer time = Integer.valueOf(tim);
		count.setCostHour(new BigDecimal(income).divide(new BigDecimal(clazz),0,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(time),0,BigDecimal.ROUND_HALF_UP).toString());
		count.setIncomeHour("均班营收");
		count.setIncome(new BigDecimal(income).divide(new BigDecimal(clazz),0,BigDecimal.ROUND_HALF_UP).toString());
		count.setSubsidyClz("补贴/班");
		String subsidyMethod = counts.get(counts.size()-1).getSubsidyMethod();
		count.setSubsidyMethod(new BigDecimal(subsidyMethod).divide(new BigDecimal(clazz),0,BigDecimal.ROUND_HALF_UP).toString());
		count.setParlor("");
		return count;
	}

	/**
	 * 此处为统计计数据方法
	 * @param counts 需要统计的数据集合
	 * @return 返回前台需要的集合对象
	 */
	private IncomeCount getCount(List<IncomeCount> counts,String agy_Rte) {
		IncomeCount count = new IncomeCount();
		count.setMethod("总计");
		count.setIncomeHour("年经营收入");
		count.setSubsidyClz("年补贴 (含促销费)");
		for (IncomeCount incomeCount : counts) {
			count.setClazz(Integer.valueOf(count.getClazz())+Integer.valueOf(incomeCount.getClazz())+"");
			count.setCostClz("");
			count.setFixedCose(Integer.valueOf(count.getFixedCose())+Integer.valueOf(incomeCount.getFixedCose())+"");
			count.setHour("");
			count.setCostHour("");
//			count.setIncomeHour(Integer.valueOf(count.getIncomeHour())+Integer.valueOf(incomeCount.getIncomeHour())+"");
			count.setIncome(Integer.valueOf(count.getIncome())+Integer.valueOf(incomeCount.getIncome())+"");
//			count.setSubsidyClz(Integer.valueOf(count.getSubsidyClz())+Integer.valueOf(incomeCount.getSubsidyClz())+"");
			count.setSubsidyMethod(Integer.valueOf(count.getSubsidyMethod())+Integer.valueOf(incomeCount.getSubsidyMethod())+"");
			count.setParlor(Integer.valueOf(count.getParlor())+Integer.valueOf(incomeCount.getParlor().replace("%", ""))+"");
		}
		double subsidyMethod = Integer.valueOf(count.getIncome())*Double.parseDouble(agy_Rte);
		count.setSubsidyMethod(Integer.valueOf(count.getSubsidyMethod())+subsidyMethod+"");
		count.setParlor(Integer.valueOf(count.getParlor().replace("%", ""))/counts.size()/100+"%");
		return count;
	}

	/**
	 * 经停航班数据计算逻辑方法
	 * @param list 经停的6个数据集合
	 * @param clz 班次
	 * @param countTime 人为输入参数对象
	 * @return
	 */
	private IncomeCount getSixIncomeCount(List<AllClzIncome> list, int clz,IncomeCountTime countTime) {
		int time_Count = Integer.valueOf(countTime.getTim_Cout());//飞时间
		int time_Cost = Integer.valueOf(countTime.getTim_Cot());//小时成本
		int costClz = time_Count*time_Cost;//成本/班
		int fixedCose = clz*costClz;//固定成本
		IncomeCount incomeCount = new IncomeCount();
		incomeCount.setMethod(list.get(0).getDate());//月份
		incomeCount.setClazz(clz+"");//班次
		incomeCount.setCostClz(costClz+"");//成本班
		incomeCount.setFixedCose(fixedCose+"");//固定成本
		incomeCount.setHour(time_Count+"");//小时数
		incomeCount.setCostHour(time_Cost+"");
		int divide = 0;//散团收入
		int countPerson = 0;//人数
		int countTal = 0;
		for (AllClzIncome allClzIncome : list) {
			divide += (Integer.valueOf(allClzIncome.getWak_tol_Ine())+Integer.valueOf(allClzIncome.getGrp_Ine()));
			countPerson += Integer.valueOf(allClzIncome.getPgs_Per_Cls());
			countTal += Integer.valueOf(allClzIncome.getTal_Nbr_Set());
		}
		incomeCount.setIncomeHour(new BigDecimal(divide).divide(new BigDecimal(time_Count),0,BigDecimal.ROUND_HALF_UP).toString());//营收
		int income = clz*time_Count*divide;//经营收入
		int subsidyMethod = fixedCose-income;
		if(divide>0){
			incomeCount.setSubsidyClz(subsidyMethod/clz+"");//补贴班
		}else{
			incomeCount.setSubsidyClz("0");
		}
		incomeCount.setSubsidyMethod(subsidyMethod+"");//补贴月
		incomeCount.setParlor((int)countPerson/countTal+"%");//客座
		return incomeCount;
	}
	/**
	 * 直飞航班数据计算逻辑方法
	 * @param list 直飞的2个数据集合
	 * @param clz 班次
	 * @param countTime 人为输入参数对象
	 * @return
	 */
	private IncomeCount getTwoIncomeCount(List<AllClzIncome> list,int clz,IncomeCountTime countTime) {
		int time_Count = Integer.valueOf(countTime.getTim_Cout());//飞时间
		int time_Cost = Integer.valueOf(countTime.getTim_Cot());//小时成本
		int costClz = time_Count*time_Cost;//成本/班
		int fixedCose = clz*costClz;//固定成本
		IncomeCount incomeCount = new IncomeCount();
		incomeCount.setMethod(list.get(0).getDate());//月份
		incomeCount.setClazz(clz+"");//班次
		incomeCount.setCostClz(costClz+"");//成本班
		incomeCount.setFixedCose(fixedCose+"");//固定成本
		incomeCount.setHour(countTime.getTim_Cout());//小时数
		incomeCount.setCostHour(time_Cost+"");
		if(list.size()==2){
			BigDecimal wak_tol_Ine = new BigDecimal(list.get(0).getWak_tol_Ine()).add(new BigDecimal(list.get(1).getWak_tol_Ine()));
			BigDecimal grp_Ine = new BigDecimal(list.get(0).getGrp_Ine()).add(new BigDecimal(list.get(1).getGrp_Ine()));
			BigDecimal divide = wak_tol_Ine.add(grp_Ine).divide(new BigDecimal(countTime.getTim_Cout()),0,BigDecimal.ROUND_HALF_UP);
			incomeCount.setIncomeHour(divide.toString());//营收
			int income = clz*time_Count*divide.intValue();//经营收入
			int subsidyMethod = fixedCose-income;
			incomeCount.setIncome(income+"");
			if(divide.intValue()>0){
				incomeCount.setSubsidyClz(subsidyMethod/clz+"");//补贴班
			}else{
				incomeCount.setSubsidyClz("0");
			}
			incomeCount.setSubsidyMethod(subsidyMethod+"");//补贴月
			double countPgs = Integer.valueOf(list.get(0).getPgs_Per_Cls())+Integer.valueOf(list.get(1).getPgs_Per_Cls());//人数
			double countTal = Integer.valueOf(list.get(0).getTal_Nbr_Set())+Integer.valueOf(list.get(1).getTal_Nbr_Set());
			incomeCount.setParlor(new BigDecimal((countPgs/countTal)*100).setScale(0, BigDecimal.ROUND_HALF_UP)+"%");//客座
		}
		return incomeCount;
	}

	private int getClz(String str,String lcl_Dpt_Day, String date, String dpt_AirPt_Cd,
			String Pas_stp, String arrv_Airpt_Cd,String fbl_nbr) {
		DOWQuery dowQuery = new DOWQuery();
		dowQuery.setDta_Sce(str);
		dowQuery.setMonth(date);
		int toCount = 0;
		int returnCount = 0;
		List<String> flt =new ArrayList<String>();
		dowQuery.setLcl_Dpt_Day(lcl_Dpt_Day);
		String dpt_AirPt = outPortMapper.getAirCodeByName(dpt_AirPt_Cd);
		String arrv_Airpt = outPortMapper.getAirCodeByName(arrv_Airpt_Cd);
		if(!"汇总".equals(fbl_nbr)&&!TextUtil.isEmpty(fbl_nbr)){
			flt = getFlt_nbr(fbl_nbr);
		}else{
			flt.add(null);
			flt.add(null);
		}
		if(Pas_stp!=""&&Pas_stp!=null){
			
			dowQuery.setFlt_nbr(flt.get(0));
			String flt_Rte = outPortMapper.getAirCodeByName(Pas_stp);
			dowQuery.setFlt_Rte_Cd(dpt_AirPt+flt_Rte+arrv_Airpt);
			List<Integer> integers = airLineMapper.getCountClz(dowQuery);
			for (Integer integer : integers) {
				toCount+= integer;
			}
			dowQuery.setFlt_nbr(flt.get(1));
			dowQuery.setFlt_Rte_Cd(arrv_Airpt+flt_Rte+dpt_AirPt);
			
		}else{
			dowQuery.setFlt_nbr(flt.get(0));
			dowQuery.setFlt_Rte_Cd(dpt_AirPt+arrv_Airpt);
			List<Integer> integers = airLineMapper.getCountClz(dowQuery);
			for (Integer integer : integers) {
				toCount+= integer;
			}
			dowQuery.setFlt_nbr(flt.get(1));
			dowQuery.setFlt_Rte_Cd(arrv_Airpt+dpt_AirPt);
			
		}
		List<Integer> integers2 = airLineMapper.getCountClz(dowQuery);
		for (Integer integer : integers2) {
			returnCount+= integer;
		}
		return (toCount+returnCount)/2;
	}

	private List<String> getFlt_nbr(String fbl_nbr) {
		List<String> list = new ArrayList<String>();
		if(!TextUtil.isEmpty(fbl_nbr)&&!"汇总".equals(fbl_nbr)){
			String[] split = fbl_nbr.split("号");
			String[] split2 = split[1].split("/");
			list.add(split2[0]);
			list.add(split2[1]);
		}
		return list;
	}
	
	public Map<String,Object> getSingleMonthData2(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		try {
			String [] flts = null;
			if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
				String fltNum = query.getFlt_nbr().substring(3);
				flts = fltNum.split("/");
				//设置往程航班号
				query.setFlt_nbr(flts[0]);
			}
			String oldDate = query.getLcl_Dpt_Day();
			String[] date = query.getLcl_Dpt_Day().split("-");
			int year = Integer.valueOf(date[0]);
			int month = Integer.valueOf(date[1]);
			//获取月份天数
			int monthDays = MyMathUtils.getMonthDays(year, month);
//			DivideSeason season = seasonMapper.load(query);
			String querySql = "";
			DivideSeason season = null;
			try {
				if(query.getFlt_nbr()==null){
					querySql = "select * from t_divide_season where Flt_Nbr = null and Divide_Month =? and Dpt_AirPt_Cd =?"
							+ "and Arrv_Airpt_Cd=? and Flt_Rte_Cd = ?";
					season = jdbcTemplate.queryForObject(querySql, 
							new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()},
							new DivideSeasonRowMapper());
				}else{
					querySql = "select * from t_divide_season where Flt_Nbr = ? and Divide_Month =? and Dpt_AirPt_Cd =?"
							+ "and Arrv_Airpt_Cd=? and Flt_Rte_Cd = ?";
					season = jdbcTemplate.queryForObject(querySql, 
							new Object[]{query.getFlt_nbr(),query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()},
							new DivideSeasonRowMapper());
				}
			} catch (EmptyResultDataAccessException e) {
				season=null;
			}
			if(season!=null&&query.getType()==1){
				//查询同比数据列表
				if(month<10){
					query.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query.setLcl_Dpt_Day((year-1)+"-"+month);
				}
//				List<Z_Airdata> toyoyMonthData = airLineMapper.getMonthData(query);
				List<Z_Airdata> toyoyMonthData = null;
				try {
					if(query.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=null and Flt_Rte_Cd = ? and Dta_Sce =?";
						toyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						toyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					toyoyMonthData = null;
				}
				if(month!=1){
					if(month>=11){
						query.setLcl_Dpt_Day(year+"-"+(month-1));
					}else{
						query.setLcl_Dpt_Day(year+"-0"+(month-1));
					}
				}else{
					query.setLcl_Dpt_Day((year-1)+"-12");
				}
//				List<Z_Airdata> toqoqMonthData = airLineMapper.getMonthData(query);
				List<Z_Airdata> toqoqMonthData = null;
				try {
					if(query.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Rte_Cd = ? and Dta_Sce =?";
						toqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						toqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					toqoqMonthData = null;
				}
				String flt_Rte_Cd = query.getFlt_Rte_Cd();
				//设置返程航线
				MonthSalesPlanQuery query2 = (MonthSalesPlanQuery) query.clone();
				if(query.getFlt_Rte_Cd().length()==9){
					flt_Rte_Cd = flt_Rte_Cd.substring(6)+flt_Rte_Cd.substring(3,6)+flt_Rte_Cd.substring(0,3);
				}else if(query.getFlt_Rte_Cd().length()==6){
					flt_Rte_Cd = query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd();
				}else{
					return null;
				}
				query2.setFlt_Rte_Cd(flt_Rte_Cd);
				query2.setPas_stp(query2.getDpt_AirPt_Cd());
				query2.setDpt_AirPt_Cd(query.getArrv_Airpt_Cd());
				query2.setArrv_Airpt_Cd(query.getPas_stp());
				if(flts!=null){
					query2.setFlt_nbr(flts[1]);
				}
				if(month<10){
					query2.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query2.setLcl_Dpt_Day((year-1)+"-"+month);
				}
//				List<Z_Airdata> backyoyMonthData = airLineMapper.getMonthData(query2);
				List<Z_Airdata> backyoyMonthData = null;
				try {
					if(query2.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ?  and Flt_Rte_Cd = ? and Dta_Sce =?";
						backyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query2.getLcl_Dpt_Day(),query2.getDpt_AirPt_Cd(),query2.getArrv_Airpt_Cd(),query2.getFlt_Rte_Cd(),query2.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						backyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query2.getLcl_Dpt_Day(),query2.getDpt_AirPt_Cd(),query2.getArrv_Airpt_Cd(),query2.getFlt_nbr(),query2.getFlt_Rte_Cd(),query2.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					backyoyMonthData = null;
				}
				
				if(month!=1){
					if(month>=11){
						query2.setLcl_Dpt_Day(year+"-"+(month-1));
					}else{
						query2.setLcl_Dpt_Day(year+"-0"+(month-1));
					}
				}else{
					query2.setLcl_Dpt_Day((year-1)+"-12");
				}
//				List<Z_Airdata> backqoqMonthData = airLineMapper.getMonthData(query2);
				List<Z_Airdata> backqoqMonthData = null;
				try {
					if(query2.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Rte_Cd = ? and Dta_Sce =?";
						backqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query2.getLcl_Dpt_Day(),query2.getDpt_AirPt_Cd(),query2.getArrv_Airpt_Cd(),query2.getFlt_Rte_Cd(),query2.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						backqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query2.getLcl_Dpt_Day(),query2.getDpt_AirPt_Cd(),query2.getArrv_Airpt_Cd(),query2.getFlt_nbr(),query2.getFlt_Rte_Cd(),query2.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					backqoqMonthData = null;
				}
				rootMap.put("toyoyList", toyoyMonthData);
				rootMap.put("toqoqList", toqoqMonthData);
				rootMap.put("backyoyList", backyoyMonthData);
				rootMap.put("backqoqList", backqoqMonthData);
				rootMap.put("type",1);
				rootMap.put("seasonId", season.getDivideId());
				rootMap.put("toFlt", query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
				rootMap.put("backFlt", query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
				rootMap.put("lcl_Dpt_Day", oldDate);
				rootMap.put("monthDays", season.getDays());
				rootMap.put("toAvgTraveller", season.getAvgGoCust());
				rootMap.put("backAvgTraveller", season.getAvgReturnCust());
				rootMap.put("yoyTrans", season.getYoyTrans());
				rootMap.put("qoqTrans", season.getQoqTrans());
				rootMap.put("currTrans",season.getCurrTrans());
				rootMap.put("toForwardOffset",season.getToForwardOffset());
				rootMap.put("toReverseOffset", season.getToReverseOffset());
				rootMap.put("backForwardOffset", season.getBackForwardOffset());
				rootMap.put("backReverseOffset", season.getBackReverseOffset());
				//设置往程数据参数
				rootMap.put("dpt_AirPt_Cd", query.getDpt_AirPt_Cd());
				rootMap.put("arrv_Airpt_Cd", query.getArrv_Airpt_Cd());
				rootMap.put("flt_Rte_Cd", query.getFlt_Rte_Cd());
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					rootMap.put("flt_nbr", query.getFlt_nbr().substring(3));
				}else{
					rootMap.put("flt_nbr", "");
				}
//				List<DivideTime> goTimeList = timeMapper.list(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(), season.getDivideId());
				querySql = "select Divide_Id,Start_Date,End_Date,Ref_Sale_Start_Date,Ref_Sale_End_Date,Nature from t_divide_time where Season_Id =? and Flt_Direct=?";
				List<DivideTime> goTimeList = null;
				List<DivideTime> backTimeList = null;
				try {
					goTimeList = (List<DivideTime>) jdbcTemplate.query(querySql,
							new Object[]{season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd()},
							new DivideTimeRowMapper());
					
				} catch (EmptyResultDataAccessException e) {
					goTimeList = null;
				}
				try {
					backTimeList = (List<DivideTime>) jdbcTemplate.query(querySql,
							new Object[]{season.getDivideId(),query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd()},
							new DivideTimeRowMapper());
				} catch (EmptyResultDataAccessException e) {
					backTimeList = null;
				}
				rootMap.put("goTimeList", goTimeList);
				rootMap.put("backTimeList", backTimeList);
			}else{
//				List<Z_Airdata> toyoyMonthData = new ArrayList<Z_Airdata>();
//				List<Z_Airdata> toqoqMonthData = new ArrayList<Z_Airdata>();
//				List<Z_Airdata> backyoyMonthData = new ArrayList<Z_Airdata>();
//				List<Z_Airdata> backqoqMonthData = new ArrayList<Z_Airdata>();
				//获取往程航线三字码
				String toFlt = query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd();
				//获取航线
				String fltCd = query.getFlt_Rte_Cd();
				//设置往程同比查询时间
				if(month<10){
					query.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query.setLcl_Dpt_Day((year-1)+"-"+month);
				}
				//获取查询月份的相关航线数据
				//查询往程月份数据
//				toyoyMonthData = airLineMapper.getMonthData(query);
				List<Z_Airdata> toyoyMonthData = null;
				try {
					if(query.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Rte_Cd = ? and Dta_Sce =?";
						toyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						toyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					toyoyMonthData = null;
				}
				int yoyFlightCount = 0;//同比航班次数
				int qoqFlightCount = 0;//环比航班次数
				int toTotalTraveller = 0;//往旅客数
				int toAvgTraveller = 0;//往日客流均值
				int toForwardOffset = 0;//往正向偏离值
				int toReverseOffset = 0;//往逆向偏离值
				int backTotalTraveller = 0;//返旅客数
				int backAvgTraveller = 0;//返日客流均值
				int backForwardOffset = 0;//返正向偏离值
				int backReverseOffset = 0;//返逆向偏离值
				if(toyoyMonthData!=null&&toyoyMonthData.size()>0){
					yoyFlightCount = toyoyMonthData.size();
					for(Z_Airdata m :toyoyMonthData){
						toTotalTraveller += m.getPgs_Per_Cls();
					}
					toAvgTraveller = Math.round(toTotalTraveller/yoyFlightCount);
					int i = 0;
					int j = 0;
					for(Z_Airdata m:toyoyMonthData){
						if(m.getPgs_Per_Cls()>=toAvgTraveller){
							toForwardOffset += m.getPgs_Per_Cls()-toAvgTraveller;
							i++;
						}else{
							toReverseOffset += toAvgTraveller-m.getPgs_Per_Cls();
							j++;
						}
					}
					toForwardOffset = Math.round(toForwardOffset/i);
					toReverseOffset = Math.round(toReverseOffset/j);
				}
				//设置往程环比查询时间
				if(month!=1){
					if(month<=10){
						query.setLcl_Dpt_Day(year+"-0"+(month-1));
					}else{
						query.setLcl_Dpt_Day(year+"-"+(month-1));
					}
				}else{
					query.setLcl_Dpt_Day((year-1)+"-12");
				}
//				toqoqMonthData = airLineMapper.getMonthData(query);
				List<Z_Airdata> toqoqMonthData = null;
				try {
					if(query.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Rte_Cd = ? and Dta_Sce =?";
						toqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						toqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					toqoqMonthData = null;
				}
				if(toqoqMonthData !=null&&toqoqMonthData.size()>0){
					qoqFlightCount = toqoqMonthData.size();
				}
				//设置返程航线
				/*if(query.getPas_stp()!=null && !"".equals(query.getPas_stp())){
					String pasStp = query.getPas_stp();
					String [] codes = query.getFlt_Rte_Cd().split(pasStp);
					query.setFlt_Rte_Cd(codes[1]+pasStp+codes[0]);
				}else{
					query.setFlt_Rte_Cd(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
				}*/
				String flt_Rte_Cd = query.getFlt_Rte_Cd();
				if(flt_Rte_Cd.length()==9){
					flt_Rte_Cd = flt_Rte_Cd.substring(6)+flt_Rte_Cd.substring(3,6)+flt_Rte_Cd.substring(0,3);
				}else{
					flt_Rte_Cd = query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd();
				}
				query.setFlt_Rte_Cd(flt_Rte_Cd);
				//设置返程
				String change = query.getArrv_Airpt_Cd();
				//设置返程航班号
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					query.setFlt_nbr(flts[1]);			
				}
				//获取返程航线三字码
				String backFlt = change+query.getDpt_AirPt_Cd();
				query.setArrv_Airpt_Cd(query.getDpt_AirPt_Cd());
				query.setDpt_AirPt_Cd(change);
				//设置返程同比查询时间
				if(month<10){
					query.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query.setLcl_Dpt_Day((year-1)+"-"+month);
				}
//				backyoyMonthData = airLineMapper.getMonthData(query);
				List<Z_Airdata> backyoyMonthData = null;
				try {
					if(query.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Rte_Cd = ? and Dta_Sce =?";
						backyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						backyoyMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					backyoyMonthData = null;
				}
				if(backyoyMonthData != null&&backyoyMonthData.size()>0){
					for(Z_Airdata m :backyoyMonthData){
						backTotalTraveller += m.getPgs_Per_Cls();
					}
					backAvgTraveller = Math.round(backTotalTraveller/backyoyMonthData.size());
					int i = 0;
					int j = 0;
					for(Z_Airdata m :backyoyMonthData){
						if(m.getPgs_Per_Cls()>=backAvgTraveller){
							backForwardOffset += m.getPgs_Per_Cls()-backAvgTraveller;
							i++;
						}else{
							backReverseOffset += backAvgTraveller-m.getPgs_Per_Cls();
							j++;
						}
					}
					backForwardOffset = Math.round(backForwardOffset/i);
					backReverseOffset = Math.round(backReverseOffset/j);
				}
				//设置返程环比查询时间
				if(month!=1){
					if(month<=10){
						query.setLcl_Dpt_Day(year+"-0"+(month-1));
					}else{
						query.setLcl_Dpt_Day(year+"-"+(month-1));
					}
				}else{
					query.setLcl_Dpt_Day((year-1)+"-12");
				}
//				backqoqMonthData = airLineMapper.getMonthData(query);
				List<Z_Airdata> backqoqMonthData = null;
				try {
					if(query.getFlt_nbr()==null){
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Rte_Cd = ? and Dta_Sce =?";
						backqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}else{
						querySql = "select Lcl_Dpt_Day,Pgs_Per_Cls from z_airdata where DATE_FORMAT(Lcl_Dpt_Day,'%Y-%m') = ? and Dpt_AirPt_Cd =? and Arrv_Airpt_Cd = ? and Flt_Nbr=? and Flt_Rte_Cd = ? and Dta_Sce =?";
						backqoqMonthData = (List<Z_Airdata>) jdbcTemplate.query(querySql, 
								new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd(),query.getDta_Sce()},
								new Z_AirdataRowMapper());
					}
				} catch (EmptyResultDataAccessException e) {
					backqoqMonthData = null;
				}
				rootMap.put("toFlt", toFlt);
				rootMap.put("backFlt", backFlt);
				rootMap.put("toForwardOffset", toForwardOffset);
				rootMap.put("toReverseOffset", toReverseOffset);
				rootMap.put("backForwardOffset", backForwardOffset);
				rootMap.put("backReverseOffset", backReverseOffset);
				rootMap.put("lcl_Dpt_Day", oldDate);
				rootMap.put("monthDays", monthDays);
				rootMap.put("toAvgTraveller", toAvgTraveller);
				rootMap.put("yoyFlightCount", yoyFlightCount);
				rootMap.put("qoqFlightCount", qoqFlightCount);
				rootMap.put("backAvgTraveller", backAvgTraveller);
				//设置往程数据参数
				rootMap.put("dpt_AirPt_Cd", query.getArrv_Airpt_Cd());
				rootMap.put("arrv_Airpt_Cd", query.getDpt_AirPt_Cd());
				rootMap.put("flt_Rte_Cd", fltCd);
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					rootMap.put("flt_nbr", flts[0]+"/"+flts[1]);
				}else{
					rootMap.put("flt_nbr", "");
				}
				rootMap.put("toyoyList", toyoyMonthData);
				rootMap.put("toqoqList", toqoqMonthData);
				rootMap.put("backyoyList", backyoyMonthData);
				rootMap.put("backqoqList", backqoqMonthData);
			}
			rootMap.put("opResult", "0");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rootMap.put("result", "服务器异常，稍后再试");
		}
		return rootMap;
	}
	
	public Map<String,Object> getSingleMonthData(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		try {
			String [] flts = null;
			if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
				String fltNum = query.getFlt_nbr().substring(3);
				flts = fltNum.split("/");
				//设置往程航班号
				query.setFlt_nbr(flts[0]);
			}
			String oldDate = query.getLcl_Dpt_Day();
			String[] date = query.getLcl_Dpt_Day().split("-");
			int year = Integer.valueOf(date[0]);
			int month = Integer.valueOf(date[1]);
			//获取月份天数
			int monthDays = MyMathUtils.getMonthDays(year, month);
			DivideSeason season = seasonMapper.load(query);
			if(season!=null&&query.getType()==1){
				//查询同比数据列表
				if(month<10){
					query.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query.setLcl_Dpt_Day((year-1)+"-"+month);
				}
				List<Z_Airdata> toyoyMonthData = airLineMapper.getMonthData(query);
				if(month!=1){
					if(month>=11){
						query.setLcl_Dpt_Day(year+"-"+(month-1));
					}else{
						query.setLcl_Dpt_Day(year+"-0"+(month-1));
					}
				}else{
					query.setLcl_Dpt_Day((year-1)+"-12");
				}
				List<Z_Airdata> toqoqMonthData = airLineMapper.getMonthData(query);
				String flt_Rte_Cd = query.getFlt_Rte_Cd();
				//设置返程航线
				MonthSalesPlanQuery query2 = (MonthSalesPlanQuery) query.clone();
				if(query.getFlt_Rte_Cd().length()==9){
					flt_Rte_Cd = flt_Rte_Cd.substring(6)+flt_Rte_Cd.substring(3,6)+flt_Rte_Cd.substring(0,3);
				}else if(query.getFlt_Rte_Cd().length()==6){
					flt_Rte_Cd = query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd();
				}else{
					return null;
				}
				query2.setFlt_Rte_Cd(flt_Rte_Cd);
				query2.setPas_stp(query2.getDpt_AirPt_Cd());
				query2.setDpt_AirPt_Cd(query.getArrv_Airpt_Cd());
				query2.setArrv_Airpt_Cd(query.getPas_stp());
				if(flts!=null){
					query2.setFlt_nbr(flts[1]);
				}
				if(month<10){
					query2.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query2.setLcl_Dpt_Day((year-1)+"-"+month);
				}
				List<Z_Airdata> backyoyMonthData = airLineMapper.getMonthData(query2);
				if(month!=1){
					if(month>=11){
						query2.setLcl_Dpt_Day(year+"-"+(month-1));
					}else{
						query2.setLcl_Dpt_Day(year+"-0"+(month-1));
					}
				}else{
					query2.setLcl_Dpt_Day((year-1)+"-12");
				}
				List<Z_Airdata> backqoqMonthData = airLineMapper.getMonthData(query2);
				rootMap.put("toyoyList", toyoyMonthData);
				rootMap.put("toqoqList", toqoqMonthData);
				rootMap.put("backyoyList", backyoyMonthData);
				rootMap.put("backqoqList", backqoqMonthData);
				rootMap.put("type",1);
				rootMap.put("seasonId", season.getDivideId());
				rootMap.put("toFlt", query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
				rootMap.put("backFlt", query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
				rootMap.put("lcl_Dpt_Day", oldDate);
				rootMap.put("monthDays", season.getDays());
				rootMap.put("toAvgTraveller", season.getAvgGoCust());
				rootMap.put("backAvgTraveller", season.getAvgReturnCust());
				rootMap.put("yoyTrans", season.getYoyTrans());
				rootMap.put("qoqTrans", season.getQoqTrans());
				rootMap.put("currTrans",season.getCurrTrans());
				rootMap.put("toForwardOffset",season.getToForwardOffset());
				rootMap.put("toReverseOffset", season.getToReverseOffset());
				rootMap.put("backForwardOffset", season.getBackForwardOffset());
				rootMap.put("backReverseOffset", season.getBackReverseOffset());
				//设置往程数据参数
				rootMap.put("dpt_AirPt_Cd", query.getDpt_AirPt_Cd());
				rootMap.put("arrv_Airpt_Cd", query.getArrv_Airpt_Cd());
				rootMap.put("flt_Rte_Cd", query.getFlt_Rte_Cd());
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					rootMap.put("flt_nbr", query.getFlt_nbr().substring(3));
				}else{
					rootMap.put("flt_nbr", "");
				}
				List<DivideTime> goTimeList = timeMapper.list(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(), season.getDivideId());
				List<DivideTime> backTimeList = timeMapper.list(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd(),season.getDivideId());
				rootMap.put("goTimeList", goTimeList);
				rootMap.put("backTimeList", backTimeList);
			}else{
				List<Z_Airdata> toyoyMonthData = new ArrayList<Z_Airdata>();
				List<Z_Airdata> toqoqMonthData = new ArrayList<Z_Airdata>();
				List<Z_Airdata> backyoyMonthData = new ArrayList<Z_Airdata>();
				List<Z_Airdata> backqoqMonthData = new ArrayList<Z_Airdata>();
				//获取往程航线三字码
				String toFlt = query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd();
				//获取航线
				String fltCd = query.getFlt_Rte_Cd();
				//设置往程同比查询时间
				if(month<10){
					query.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query.setLcl_Dpt_Day((year-1)+"-"+month);
				}
				//获取查询月份的相关航线数据
				//查询往程月份数据
				toyoyMonthData = airLineMapper.getMonthData(query);
				int yoyFlightCount = 0;//同比航班次数
				int qoqFlightCount = 0;//环比航班次数
				int toTotalTraveller = 0;//往旅客数
				int toAvgTraveller = 0;//往日客流均值
				int toForwardOffset = 0;//往正向偏离值
				int toReverseOffset = 0;//往逆向偏离值
				int backTotalTraveller = 0;//返旅客数
				int backAvgTraveller = 0;//返日客流均值
				int backForwardOffset = 0;//返正向偏离值
				int backReverseOffset = 0;//返逆向偏离值
				if(toyoyMonthData!=null&&toyoyMonthData.size()>0){
					yoyFlightCount = toyoyMonthData.size();
					for(Z_Airdata m :toyoyMonthData){
						toTotalTraveller += m.getPgs_Per_Cls();
					}
					toAvgTraveller = Math.round(toTotalTraveller/yoyFlightCount);
					int i = 0;
					int j = 0;
					for(Z_Airdata m:toyoyMonthData){
						if(m.getPgs_Per_Cls()>=toAvgTraveller){
							toForwardOffset += m.getPgs_Per_Cls()-toAvgTraveller;
							i++;
						}else{
							toReverseOffset += toAvgTraveller-m.getPgs_Per_Cls();
							j++;
						}
					}
					toForwardOffset = Math.round(toForwardOffset/i);
					toReverseOffset = Math.round(toReverseOffset/j);
				}
				//设置往程环比查询时间
				if(month!=1){
					if(month<=10){
						query.setLcl_Dpt_Day(year+"-0"+(month-1));
					}else{
						query.setLcl_Dpt_Day(year+"-"+(month-1));
					}
				}else{
					query.setLcl_Dpt_Day((year-1)+"-12");
				}
				toqoqMonthData = airLineMapper.getMonthData(query);
				if(toqoqMonthData !=null&&toqoqMonthData.size()>0){
					qoqFlightCount = toqoqMonthData.size();
				}
				//设置返程航线
				/*if(query.getPas_stp()!=null && !"".equals(query.getPas_stp())){
					String pasStp = query.getPas_stp();
					String [] codes = query.getFlt_Rte_Cd().split(pasStp);
					query.setFlt_Rte_Cd(codes[1]+pasStp+codes[0]);
				}else{
					query.setFlt_Rte_Cd(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
				}*/
				String flt_Rte_Cd = query.getFlt_Rte_Cd();
				if(flt_Rte_Cd.length()==9){
					flt_Rte_Cd = flt_Rte_Cd.substring(6)+flt_Rte_Cd.substring(3,6)+flt_Rte_Cd.substring(0,3);
				}else{
					flt_Rte_Cd = query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd();
				}
				query.setFlt_Rte_Cd(flt_Rte_Cd);
				//设置返程
				String change = query.getArrv_Airpt_Cd();
				//设置返程航班号
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					query.setFlt_nbr(flts[1]);			
				}
				//获取返程航线三字码
				String backFlt = change+query.getDpt_AirPt_Cd();
				query.setArrv_Airpt_Cd(query.getDpt_AirPt_Cd());
				query.setDpt_AirPt_Cd(change);
				//设置返程同比查询时间
				if(month<10){
					query.setLcl_Dpt_Day((year-1)+"-0"+month);
				}else{
					query.setLcl_Dpt_Day((year-1)+"-"+month);
				}
				backyoyMonthData = airLineMapper.getMonthData(query);
				if(backyoyMonthData != null&&backyoyMonthData.size()>0){
					for(Z_Airdata m :backyoyMonthData){
						backTotalTraveller += m.getPgs_Per_Cls();
					}
					backAvgTraveller = Math.round(backTotalTraveller/backyoyMonthData.size());
					int i = 0;
					int j = 0;
					for(Z_Airdata m :backyoyMonthData){
						if(m.getPgs_Per_Cls()>=backAvgTraveller){
							backForwardOffset += m.getPgs_Per_Cls()-backAvgTraveller;
							i++;
						}else{
							backReverseOffset += backAvgTraveller-m.getPgs_Per_Cls();
							j++;
						}
					}
					backForwardOffset = Math.round(backForwardOffset/i);
					backReverseOffset = Math.round(backReverseOffset/j);
				}
				//设置返程环比查询时间
				if(month!=1){
					if(month<=10){
						query.setLcl_Dpt_Day(year+"-0"+(month-1));
					}else{
						query.setLcl_Dpt_Day(year+"-"+(month-1));
					}
				}else{
					query.setLcl_Dpt_Day((year-1)+"-12");
				}
				backqoqMonthData = airLineMapper.getMonthData(query);
				rootMap.put("toFlt", toFlt);
				rootMap.put("backFlt", backFlt);
				rootMap.put("toForwardOffset", toForwardOffset);
				rootMap.put("toReverseOffset", toReverseOffset);
				rootMap.put("backForwardOffset", backForwardOffset);
				rootMap.put("backReverseOffset", backReverseOffset);
				rootMap.put("lcl_Dpt_Day", oldDate);
				rootMap.put("monthDays", monthDays);
				rootMap.put("toAvgTraveller", toAvgTraveller);
				rootMap.put("yoyFlightCount", yoyFlightCount);
				rootMap.put("qoqFlightCount", qoqFlightCount);
				rootMap.put("backAvgTraveller", backAvgTraveller);
				//设置往程数据参数
				rootMap.put("dpt_AirPt_Cd", query.getArrv_Airpt_Cd());
				rootMap.put("arrv_Airpt_Cd", query.getDpt_AirPt_Cd());
				rootMap.put("flt_Rte_Cd", fltCd);
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					rootMap.put("flt_nbr", flts[0]+"/"+flts[1]);
				}else{
					rootMap.put("flt_nbr", "");
				}
				rootMap.put("toyoyList", toyoyMonthData);
				rootMap.put("toqoqList", toqoqMonthData);
				rootMap.put("backyoyList", backyoyMonthData);
				rootMap.put("backqoqList", backqoqMonthData);
			}
			rootMap.put("opResult", "0");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rootMap.put("result", "服务器异常，稍后再试");
		}
		return rootMap;
	}
	
	public Map<String,Object> getMonthData(MonthSalesPlanQuery query) {
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		query.setDpt_AirPt_Cd(outPortMapper.getAirCodeByName(query.getDpt_AirPt_Cd()));
		query.setArrv_Airpt_Cd(outPortMapper.getAirCodeByName(query.getArrv_Airpt_Cd()));
		if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
			query.setPas_stp(outPortMapper.getAirCodeByName(query.getPas_stp()));
			if(query.getFlt_Rte_Cd()==null||"".equals(query.getFlt_Rte_Cd())){
				query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getPas_stp()+query.getArrv_Airpt_Cd());
			}
			MonthSalesPlanQuery monthSalesPlanQuery = new MonthSalesPlanQuery();
			try {
				monthSalesPlanQuery = (MonthSalesPlanQuery)query.clone();
				MonthSalesPlanQuery monthSalesPlanQuery2 = new MonthSalesPlanQuery();
				list.add(getSingleMonthData(monthSalesPlanQuery));
				//获取经停机场
				String pas_stp = query.getPas_stp();
				//获取原有到达机场
				String arrv_Airpt_Cd = query.getArrv_Airpt_Cd();
				//将原有到达机场变为经停机场
				query.setArrv_Airpt_Cd(pas_stp);
				monthSalesPlanQuery2 = (MonthSalesPlanQuery)query.clone();
				//获取始发地到经停地月份数据
				list.add(getSingleMonthData(monthSalesPlanQuery2));
				//将始发地改为经停地，到达地改为原到达机场
				query.setDpt_AirPt_Cd(pas_stp);
				query.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				MonthSalesPlanQuery monthSalesPlanQuery3 = new MonthSalesPlanQuery();
				monthSalesPlanQuery3 = (MonthSalesPlanQuery)query.clone();;
				list.add(getSingleMonthData(monthSalesPlanQuery3));
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
		}else{
			if(query.getFlt_Rte_Cd()==null||"".equals(query.getFlt_Rte_Cd())){
				query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			}else if(!query.getFlt_Rte_Cd().equals(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd())){//当前为输入起止机场情况
				query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			}
			list.add(getSingleMonthData(query));
		}
		rootMap.put("list",list);
		return rootMap;
	}

	public Map<String, Object> saveMonthParam(MonthParameterQuery query) {
		//初始化数据
		Map<String,Object> rootMap = new HashMap<String,Object>();
		//添加淡旺季划分数据
		try {
			DivideSeason season = new DivideSeason();
			if(query.getSeasonId()!=null&&query.getSeasonId()>0){
				season.setDivideId(query.getSeasonId());
				List<DivideTime> gotimelist = timeMapper.list(query.getDpt_Airpt_Cd()+query.getArrv_Airpt_Cd(), query.getSeasonId());
				List<DivideTime> returntimelist = timeMapper.list(query.getArrv_Airpt_Cd()+query.getDpt_Airpt_Cd(), query.getSeasonId());
				if(gotimelist!=null&&gotimelist.size()>0){
					for(int i=0;i<gotimelist.size();i++){
						MonthSalePlan plan = dataMapper.selectByTimeId(gotimelist.get(i).getDivideId(),null);
						if(plan!=null){
							rootMap.put("result", "请将已经存在的对应的销售预案删除，再修改");
							return rootMap;
						}
					}
					timeMapper.deleteBySeasonId(query.getDpt_Airpt_Cd()+query.getArrv_Airpt_Cd(), query.getSeasonId());
				}
				if(returntimelist!=null&&returntimelist.size()>0){
					for(int i=0;i<returntimelist.size();i++){
						MonthSalePlan plan = dataMapper.selectByTimeId(returntimelist.get(i).getDivideId(),null);
						if(plan!=null){
							rootMap.put("result", "请将已经存在的对应的销售预案删除，再修改");
							return rootMap;
						}
					}
					timeMapper.deleteBySeasonId(query.getArrv_Airpt_Cd()+query.getDpt_Airpt_Cd(), query.getSeasonId());
				}
				
			}
			season.setArrvAirptCd(query.getArrv_Airpt_Cd());
			season.setAvgGoCust(Integer.valueOf(query.getToAvgTraveller()));
			season.setAvgReturnCust(Integer.valueOf(query.getBackAvgTraveller()));
			season.setToForwardOffset(Double.valueOf(query.getToForwardOffset()));
			season.setToReverseOffset(Double.valueOf(query.getToReverseOffset()));
			season.setBackForwardOffset(Double.valueOf(query.getBackForwardOffset()));
			season.setBackReverseOffset(Double.valueOf(query.getBackReverseOffset()));
			season.setDescription(query.getDescription());
			season.setDptAirptCd(query.getDpt_Airpt_Cd());
			season.setFltRteCd(query.getFlt_Rte_Cd());
			season.setFltNbr(query.getFlt_nbr());
			season.setCurrTrans(Double.valueOf(query.getCur_Trans()));
			season.setDivideMonth(query.getLcl_Dpt_Day());
			season.setDays(Integer.valueOf(query.getMonthDays()));
			season.setQoqTrans(Double.valueOf(query.getQoq_Trans()));
			season.setYoyTrans(Double.valueOf(query.getYoy_Trans()));
			if(query.getRefFlt()!=null&&!"".equals(query.getRefFlt())){
				String[] citys = query.getRefFlt().split(",");
				season.setRefFlt(outPortMapper.getAirCodeByName(citys[0])+"/"+outPortMapper.getAirCodeByName(citys[1]));
			}
			int result = 0;
			if(season.getDivideId()>0){
				result = seasonMapper.update(season);
			}else{
				result = seasonMapper.add(season);
			}
			if(result>0){
				int seasonId = season.getDivideId();
				if(seasonId>0){
					//添加去程数据
					String[] startDate = query.getStartDate().split(",");
					String[] endDate = query.getEndDate().split(",");
					String[] saleStartDate = query.getSaleStartDate().split(",");
					String[] saleEndDate = query.getSaleEndDate().split(",");
					String[] nature = query.getNature().split(",");
					DivideTime time = new DivideTime();
					if(startDate.length>0){
						time.setFltDirect(query.getDpt_Airpt_Cd()+query.getArrv_Airpt_Cd());
						time.setSeasonId(seasonId);
						for (int i = 0; i < startDate.length; i++) {
							time.setStartDate(startDate[i]);
							time.setEndDate(endDate[i]);
							time.setRefSaleStartDate(saleStartDate[i]);
							time.setRefSaleEndDate(saleEndDate[i]);
							time.setNature(nature[i]);
							timeMapper.add(time);
						}
						//添加返程数据
						String[] returnStartDate = query.getReturnStartDate().split(",");
						String[] returnEndDate = query.getReturnEndDate().split(",");
						String[] returnSaleStartDate = query.getReturnSaleStartDate().split(",");
						String[] returnSaleEndDate = query.getReturnSaleEndDate().split(",");
						String[] returnNature = query.getReturnNature().split(",");
						time.setFltDirect(query.getArrv_Airpt_Cd()+query.getDpt_Airpt_Cd());
						if(returnStartDate.length>0){
							for (int i = 0; i < returnStartDate.length; i++) {
								time.setStartDate(returnStartDate[i]);
								time.setEndDate(returnEndDate[i]);
								time.setRefSaleStartDate(returnSaleStartDate[i]);
								time.setRefSaleEndDate(returnSaleEndDate[i]);
								time.setNature(returnNature[i]);
								timeMapper.add(time);
							}
							rootMap.put("result", "添加往返数据成功");
						}else{
							rootMap.put("result", "添加返程数据失败");
						}
					}else{
						rootMap.put("result", "添加往程数据失败");
					}
				}
			}
			rootMap.put("opResult", "0");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			rootMap.put("result", "服务器异常，请稍后再试");
			return rootMap;
		}
		return rootMap;
	}

	public Map<String, Object> getMonthSalePlanParam(MonthSalesPlanQuery query) {
		Map<String,Object> rootMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		query.setDpt_AirPt_Cd(outPortMapper.getAirCodeByName(query.getDpt_AirPt_Cd()));
		query.setArrv_Airpt_Cd(outPortMapper.getAirCodeByName(query.getArrv_Airpt_Cd()));
		if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
			query.setPas_stp(outPortMapper.getAirCodeByName(query.getPas_stp()));
			query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getPas_stp()+query.getArrv_Airpt_Cd());
			MonthSalesPlanQuery monthSalesPlanQuery =new MonthSalesPlanQuery();
			MonthSalesPlanQuery monthSalesPlanQuery2 =new MonthSalesPlanQuery();
			MonthSalesPlanQuery monthSalesPlanQuery3 =new MonthSalesPlanQuery();
			try {
				monthSalesPlanQuery = (MonthSalesPlanQuery) query.clone();
				map = getSingleMonthSalePlanParam(monthSalesPlanQuery);
				if(map.get("goTimeList")!=null&&map.get("returnTimeList")!=null){
					list.add(map);
				}else{
					rootMap.put("opResult", "1");
					rootMap.put("message", "请完善对应的淡旺季划分数据，再操作");
					return rootMap;
				}
				//获取经停机场
				String pas_stp = query.getPas_stp();
				//获取原有到达机场
				String arrv_Airpt_Cd = query.getArrv_Airpt_Cd();
				//将原有到达机场变为经停机场
				query.setArrv_Airpt_Cd(pas_stp);
				monthSalesPlanQuery2 = (MonthSalesPlanQuery) query.clone();
				//获取始发地到经停地月份数据
				map = getSingleMonthSalePlanParam(monthSalesPlanQuery2);
				if(map.get("goTimeList")!=null&&map.get("returnTimeList")!=null){
					list.add(map);
				}else{
					rootMap.put("opResult", "1");
					rootMap.put("message", "请完善对应的淡旺季划分数据，再操作");
					return rootMap;
				}
				//将始发地改为经停地，到达地改为原到达机场
				query.setDpt_AirPt_Cd(pas_stp);
				query.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				monthSalesPlanQuery3 = (MonthSalesPlanQuery) query.clone();
				map = getSingleMonthSalePlanParam(monthSalesPlanQuery3);
				if(map.get("goTimeList")!=null&&map.get("returnTimeList")!=null){
					list.add(map);
				}else{
					rootMap.put("opResult", "1");
					rootMap.put("message", "请完善对应的淡旺季划分数据，再操作");
					return rootMap;
				}
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}else{
			query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			map = getSingleMonthSalePlanParam(query);
			if(map.get("goTimeList")!=null&&map.get("returnTimeList")!=null){
				list.add(map);
			}else{
				rootMap.put("opResult", "1");
				rootMap.put("message", "请完善对应的淡旺季划分数据，再操作");
				return rootMap;
			}
		}
		rootMap.put("list",list);
		return rootMap;
	}
	
	public Map<String,Object> getSingleMonthSalePlanParam2(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
//		List<DivideTime> timeList = new ArrayList<DivideTime>();
		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
		DivideSeason season = new DivideSeason();
		ConnectDB con = new ConnectDB();
		con.initValue();//初始化连接
		con.getConnection();
		String flight = "";
		String [] flts = null;
		String querySql = "";
		//获取航班号
		if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
			String fltNum = query.getFlt_nbr().substring(3);
			query.setFlt_nbr(fltNum);
			flts = fltNum.split("/");
			querySql = "select * from t_divide_season where Flt_Nbr=? and Divide_Month=? and Dpt_AirPt_Cd=? and Arrv_Airpt_Cd=? and Flt_Rte_Cd=?";
			season = (DivideSeason) con.executeQuerySingle(querySql, new Object[]{query.getFlt_nbr(),query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()},new DivideSeasonRowMapper());
//			season = jdbcTemplate.queryForObject(querySql, new Object[]{query.getFlt_nbr(),query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()}, new DivideSeasonRowMapper());
		}else{
			querySql = "select * from t_divide_season where Flt_Nbr is null and Divide_Month=? and Dpt_AirPt_Cd=? and Arrv_Airpt_Cd=? and Flt_Rte_Cd=?";
			Object obj= con.executeQuerySingle(querySql, new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()},new DivideSeasonRowMapper());
//			season = (DivideSeason) con.executeQuerySingle(querySql, new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()});
			season = jdbcTemplate.queryForObject(querySql, new Object[]{query.getLcl_Dpt_Day(),query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()}, new DivideSeasonRowMapper());
		}
//		season = seasonMapper.load(query);
		if(season !=null&&season.getDivideId()>0){
//			timeList = timeMapper.list(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(), season.getDivideId());
			querySql = "select Divide_Id,Season_Id,Start_Date,End_Date,Ref_Sale_Start_Date,Ref_Sale_End_Date,Nature,Flt_Direct from t_divide_time where Season_Id =? and Flt_Direct=?";
			List<Object> objs = con.excuteQuery(querySql, new Object[]{season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd()},new DivideTimeRowMapper());
//			timeList = jdbcTemplate.query(querySql, new Object[]{season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd()},new DivideTimeRowMapper());
			int yb = 0;
			if(objs!=null &&objs.size()>0){
				MonthSalePlan monthSalePlan = null;
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					//设置往程航班号
					query.setFlt_nbr(flts[0]);
				}
				MonthSalesPlanQuery monthQuery = new MonthSalesPlanQuery();
				try {
					monthQuery = (MonthSalesPlanQuery) query.clone();
					if(season.getRefFlt()!=null&&!"".equals(season.getRefFlt())){
						String[] citys = season.getRefFlt().split("/");
						monthQuery.setDpt_AirPt_Cd(citys[0]);
						monthQuery.setArrv_Airpt_Cd(citys[1]);
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				for(Object obj:objs){
					DivideTime time = (DivideTime) obj;
					Z_Airdata  z_Airdata  = null;
//					Z_Airdata  z_Airdata  = airLineMapper.getMonthPlanParam(monthQuery, time.getRefSaleStartDate(), time.getRefSaleEndDate());
					if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
						querySql = "select count(id) Two_Tak_Ppt,ROUND(AVG(Pgs_Per_Cls/Tal_Nbr_Set),4)*100 Grp_Fng_Rte,"
								+ "ROUND(AVG(Idd_Dct),2) Idd_Dct,CEIL(AVG(Pgs_Per_Cls-Grp_Nbr)) Ech_Cls_Grp,"
								+ "ROUND(AVG(Grp_Dct),2) Grp_Dct,CEIL(AVG(Grp_Nbr)) Grp_Nbr ,ROUND(AVG(yBFare),2) yBFare"
								+ " from z_airdata where Lcl_Dpt_Day >=? and Lcl_Dpt_Day <=? and Dpt_AirPt_Cd=? and Arrv_Airpt_Cd=?"
								+ " and Flt_Nbr =? and Flt_Rte_Cd =?";
//						z_Airdata = jdbcTemplate.queryForObject(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
//								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd()},new ZdataRowMapper());
						z_Airdata = (Z_Airdata) con.executeQuerySingle(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd()},new ZdataRowMapper());
					}else{
						querySql = "select count(id) Two_Tak_Ppt,ROUND(AVG(Pgs_Per_Cls/Tal_Nbr_Set),4)*100 Grp_Fng_Rte,"
								+ "ROUND(AVG(Idd_Dct),2) Idd_Dct,CEIL(AVG(Pgs_Per_Cls-Grp_Nbr)) Ech_Cls_Grp,"
								+ "ROUND(AVG(Grp_Dct),2) Grp_Dct,CEIL(AVG(Grp_Nbr)) Grp_Nbr ,ROUND(AVG(yBFare),2) yBFare"
								+ " from z_airdata where Lcl_Dpt_Day >=? and Lcl_Dpt_Day <=? and Dpt_AirPt_Cd=? and Arrv_Airpt_Cd=?"
								+ " and Flt_Rte_Cd =?";
//						z_Airdata = jdbcTemplate.queryForObject(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
//								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()},new ZdataRowMapper());
						z_Airdata = (Z_Airdata) con.executeQuerySingle(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()},new ZdataRowMapper());
					}
					if(z_Airdata!=null){
						yb =yb + z_Airdata.getyBFare();
					}
					list.add(z_Airdata);
					//数据回显
//					MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(time.getDivideId(),null);
					querySql = "select * from t_monthsaleplan where Time_Id =?";
//					monthSalePlan = jdbcTemplate.queryForObject(querySql, new Object[]{time.getDivideId()},new MonthSalePlanRowMapper());
					monthSalePlan = (MonthSalePlan) con.executeQuerySingle(querySql, new Object[]{time.getDivideId()},new MonthSalePlanRowMapper());
					if(monthSalePlan!=null){
						time.setMonthSalePlan(monthSalePlan);
					}
				}
				//设置航班号
//				MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(timeList.get(0).getDivideId(),null);
				if(monthSalePlan!=null){
					flight = "航班号"+monthSalePlan.getFltNbr();
				}
//				yb = yb/timeList.size();
				yb = yb/objs.size();
			}
			rootMap.put("goYb", yb);
			rootMap.put("goList", list);
//			rootMap.put("goTimeList", timeList);
			rootMap.put("goTimeList", objs);
			//重置list列表
			querySql = "select Divide_Id,Start_Date,End_Date,Ref_Sale_Start_Date,Ref_Sale_End_Date,Nature,Season_Id,Flt_Direct from t_divide_time where Season_Id =? and Flt_Direct=?";
			list = new ArrayList<Z_Airdata>();
			if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
				query.setFlt_nbr(flts[1]+"/"+flts[0]);
//				timeList = timeMapper.list(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd(), season.getDivideId());
//				timeList = jdbcTemplate.query(querySql, new Object[]{season.getDivideId(),query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd()},new DivideTimeRowMapper());
				objs = con.excuteQuery(querySql, new Object[]{season.getDivideId(),query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd()},new DivideTimeRowMapper());
				//设置返程航班号
				query.setFlt_nbr(flts[1]);
			}else{
//				timeList = timeMapper.list(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd(), season.getDivideId());
//				timeList = jdbcTemplate.query(querySql,new Object[]{season.getDivideId(),query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd()},new DivideTimeRowMapper());
				objs = con.excuteQuery(querySql, new Object[]{season.getDivideId(),query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd()},new DivideTimeRowMapper());
			}
			//设置返回航线
			if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
				String[] fltR = query.getFlt_Rte_Cd().split(query.getPas_stp());
				query.setFlt_Rte_Cd(fltR[1]+query.getPas_stp()+fltR[0]);
			}else{
				query.setFlt_Rte_Cd(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
			}
			//设置返程机场
			String dpt_AirPt_Cd = query.getDpt_AirPt_Cd();
			query.setDpt_AirPt_Cd(query.getArrv_Airpt_Cd());
			query.setArrv_Airpt_Cd(dpt_AirPt_Cd);
//			if(timeList!=null &&timeList.size()>0){
			if(objs!=null &&objs.size()>0){
				MonthSalesPlanQuery monthQuery = new MonthSalesPlanQuery();
				try {
					monthQuery = (MonthSalesPlanQuery) query.clone();
					if(season.getRefFlt()!=null&&!"".equals(season.getRefFlt())){
						String[] citys = season.getRefFlt().split("/");
						monthQuery.setDpt_AirPt_Cd(citys[1]);
						monthQuery.setArrv_Airpt_Cd(citys[0]);
						if(query.getFlt_Rte_Cd()!=null&&query.getFlt_Rte_Cd().length()==9){
							query.setFlt_Rte_Cd(query.getFlt_Rte_Cd().substring(6)+query.getFlt_Rte_Cd().substring(3,6)+query.getFlt_Rte_Cd().substring(0,3));
						}else if(query.getFlt_Rte_Cd()!=null&&query.getFlt_Rte_Cd().length()==6){
							query.setFlt_Rte_Cd(query.getFlt_Rte_Cd().substring(3)+query.getFlt_Rte_Cd().substring(0,3));
						}
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				MonthSalePlan monthSalePlan = null;
				for(Object obj:objs){
					DivideTime time = (DivideTime) obj;
//					Z_Airdata  z_Airdata = airLineMapper.getMonthPlanParam(monthQuery, time.getRefSaleStartDate(), time.getRefSaleEndDate());
					Z_Airdata  z_Airdata = null;
					if(monthQuery.getFlt_nbr()!=null&&!"".equals(monthQuery.getFlt_nbr())){
						querySql = "select count(id) Two_Tak_Ppt,ROUND(AVG(Pgs_Per_Cls/Tal_Nbr_Set),4)*100 Grp_Fng_Rte,"
								+ "ROUND(AVG(Idd_Dct),2) Idd_Dct,CEIL(AVG(Pgs_Per_Cls-Grp_Nbr)) Ech_Cls_Grp,"
								+ "ROUND(AVG(Grp_Dct),2) Grp_Dct,CEIL(AVG(Grp_Nbr)) Grp_Nbr ,ROUND(AVG(yBFare),2) yBFare"
								+ " from z_airdata where Lcl_Dpt_Day >=? and Lcl_Dpt_Day <=? and Dpt_AirPt_Cd=? and Arrv_Airpt_Cd=?"
								+ " and Flt_Nbr =? and Flt_Rte_Cd =?";
//						z_Airdata = jdbcTemplate.queryForObject(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
//								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd()}, new ZdataRowMapper());
						z_Airdata = (Z_Airdata) con.executeQuerySingle(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_nbr(),query.getFlt_Rte_Cd()}, new ZdataRowMapper());
					}else{
						querySql = "select count(id) Two_Tak_Ppt,ROUND(AVG(Pgs_Per_Cls/Tal_Nbr_Set),4)*100 Grp_Fng_Rte,"
								+ "ROUND(AVG(Idd_Dct),2) Idd_Dct,CEIL(AVG(Pgs_Per_Cls-Grp_Nbr)) Ech_Cls_Grp,"
								+ "ROUND(AVG(Grp_Dct),2) Grp_Dct,CEIL(AVG(Grp_Nbr)) Grp_Nbr ,ROUND(AVG(yBFare),2) yBFare"
								+ " from z_airdata where Lcl_Dpt_Day >=? and Lcl_Dpt_Day <=? and Dpt_AirPt_Cd=? and Arrv_Airpt_Cd=?"
								+ " and Flt_Rte_Cd =?";
//						z_Airdata = jdbcTemplate.queryForObject(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
//								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()}, new ZdataRowMapper());
						z_Airdata = (Z_Airdata) con.executeQuerySingle(querySql, new Object[]{time.getRefSaleStartDate(),time.getRefSaleEndDate(),
								query.getDpt_AirPt_Cd(),query.getArrv_Airpt_Cd(),query.getFlt_Rte_Cd()}, new ZdataRowMapper());
					}
					if(z_Airdata!=null){
						yb =yb + z_Airdata.getyBFare();
					}
					list.add(z_Airdata);
//					monthSalePlan = dataMapper.selectByTimeId(time.getDivideId(),null);
					querySql = "select * from t_monthsaleplan where Time_Id =?";
//					monthSalePlan = jdbcTemplate.queryForObject(querySql,new Object[]{time.getDivideId()},new MonthSalePlanRowMapper());
					monthSalePlan = (MonthSalePlan) con.executeQuerySingle(querySql, new Object[]{time.getDivideId()},new MonthSalePlanRowMapper());
					if(monthSalePlan!=null){
						time.setMonthSalePlan(monthSalePlan);
					}
				}
				if(monthSalePlan!=null){
					flight += "/"+monthSalePlan.getFltNbr();
				}
//				yb = yb/timeList.size();
				yb = yb/objs.size();
			}
			rootMap.put("flight", flight);
			rootMap.put("returnList", list);
//			rootMap.put("returnTimeList", timeList);
			rootMap.put("returnTimeList", objs);
			rootMap.put("yoyTrans", season.getYoyTrans());
			if(season.getYoyTrans()<season.getCurrTrans()){
				rootMap.put("transChange", "增加");
			}else if(season.getYoyTrans()==season.getCurrTrans()){
				rootMap.put("transChange", "持平");
			}else{
				rootMap.put("transChange", "减少");
			}
			rootMap.put("returnYb", yb);
			rootMap.put("goFlt", query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
			rootMap.put("returnFlt",query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			rootMap.put("seasonId", season.getDivideId());
		}
		return rootMap;
	}
	
	public Map<String,Object> getSingleMonthSalePlanParam(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<DivideTime> timeList = new ArrayList<DivideTime>();
		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
		DivideSeason season = new DivideSeason();
		String flight = "";
		String [] flts = null;
		//获取航班号
		if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
			String fltNum = query.getFlt_nbr().substring(3);
			query.setFlt_nbr(fltNum);
			flts = fltNum.split("/");
		}
		season = seasonMapper.load(query);
		if(season !=null&&season.getDivideId()>0){
			timeList = timeMapper.list(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(), season.getDivideId());
			int yb = 0;
			if(timeList!=null &&timeList.size()>0){
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					//设置往程航班号
					query.setFlt_nbr(flts[0]);
				}
				MonthSalesPlanQuery monthQuery = new MonthSalesPlanQuery();
				try {
					monthQuery = (MonthSalesPlanQuery) query.clone();
					if(season.getRefFlt()!=null&&!"".equals(season.getRefFlt())){
						String[] citys = season.getRefFlt().split("/");
						monthQuery.setDpt_AirPt_Cd(citys[0]);
						monthQuery.setArrv_Airpt_Cd(citys[1]);
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				for(DivideTime time:timeList){
					Z_Airdata  z_Airdata  = airLineMapper.getMonthPlanParam(monthQuery, time.getRefSaleStartDate(), time.getRefSaleEndDate());
					if(z_Airdata!=null){
						yb =yb + z_Airdata.getyBFare();
					}
					list.add(z_Airdata);
					//数据回显
					MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(time.getDivideId(),null);
					if(monthSalePlan!=null){
						time.setMonthSalePlan(monthSalePlan);
					}
				}
				//设置航班号
				MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(timeList.get(0).getDivideId(),null);
				if(monthSalePlan!=null){
					flight = "航班号"+monthSalePlan.getFltNbr();
				}
				yb = yb/timeList.size();
			}
			rootMap.put("goYb", yb);
			rootMap.put("goList", list);
			rootMap.put("goTimeList", timeList);
			//重置list列表
			list = new ArrayList<Z_Airdata>();
			if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
				query.setFlt_nbr(flts[1]+"/"+flts[0]);
				timeList = timeMapper.list(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd(), season.getDivideId());
				//设置返程航班号
				query.setFlt_nbr(flts[1]);
			}else{
				timeList = timeMapper.list(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd(), season.getDivideId());
			}
			//设置返回航线
			if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
				String[] fltR = query.getFlt_Rte_Cd().split(query.getPas_stp());
				query.setFlt_Rte_Cd(fltR[1]+query.getPas_stp()+fltR[0]);
			}else{
				query.setFlt_Rte_Cd(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
			}
			//设置返程机场
			String dpt_AirPt_Cd = query.getDpt_AirPt_Cd();
			query.setDpt_AirPt_Cd(query.getArrv_Airpt_Cd());
			query.setArrv_Airpt_Cd(dpt_AirPt_Cd);
			if(timeList!=null &&timeList.size()>0){
				MonthSalesPlanQuery monthQuery = new MonthSalesPlanQuery();
				try {
					monthQuery = (MonthSalesPlanQuery) query.clone();
					if(season.getRefFlt()!=null&&!"".equals(season.getRefFlt())){
						String[] citys = season.getRefFlt().split("/");
						monthQuery.setDpt_AirPt_Cd(citys[1]);
						monthQuery.setArrv_Airpt_Cd(citys[0]);
						if(query.getFlt_Rte_Cd()!=null&&query.getFlt_Rte_Cd().length()==9){
							query.setFlt_Rte_Cd(query.getFlt_Rte_Cd().substring(6)+query.getFlt_Rte_Cd().substring(3,6)+query.getFlt_Rte_Cd().substring(0,3));
						}else if(query.getFlt_Rte_Cd()!=null&&query.getFlt_Rte_Cd().length()==6){
							query.setFlt_Rte_Cd(query.getFlt_Rte_Cd().substring(3)+query.getFlt_Rte_Cd().substring(0,3));
						}
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				for(DivideTime time:timeList){
					Z_Airdata  z_Airdata = airLineMapper.getMonthPlanParam(monthQuery, time.getRefSaleStartDate(), time.getRefSaleEndDate());
					if(z_Airdata!=null){
						yb =yb + z_Airdata.getyBFare();
					}
					list.add(z_Airdata);
					MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(time.getDivideId(),null);
					if(monthSalePlan!=null){
						time.setMonthSalePlan(monthSalePlan);
					}
				}
				MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(timeList.get(0).getDivideId(),null);
				if(monthSalePlan!=null){
					flight += "/"+monthSalePlan.getFltNbr();
				}
				yb = yb/timeList.size();
			}
			rootMap.put("flight", flight);
			rootMap.put("returnList", list);
			rootMap.put("returnTimeList", timeList);
			rootMap.put("yoyTrans", season.getYoyTrans());
			if(season.getYoyTrans()<season.getCurrTrans()){
				rootMap.put("transChange", "增加");
			}else if(season.getYoyTrans()==season.getCurrTrans()){
				rootMap.put("transChange", "持平");
			}else{
				rootMap.put("transChange", "减少");
			}
			rootMap.put("returnYb", yb);
			rootMap.put("goFlt", query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
			rootMap.put("returnFlt",query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			rootMap.put("seasonId", season.getDivideId());
		}
		return rootMap;
	}
	
	public Map<String, Object> saveSalePlanData(SalePlanData data) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			//往程数据添加
			String[] ids = null;
			if(data.getId()!=null&&!"".equals(data.getId())){
				ids = data.getId().split(",");
			}
			String[] timeIds = data.getTimeId().split(",");
			String[] agreements = data.getAgreement().split(",");
			String[] fITNbrs = data.getfITNbr().split(",");
			String[] allFolds = data.getAllFold().split(",");
			String[] lowestSales = data.getLowestSale().split(",");
			String[] fITSaleCycles = data.getfITSaleCycle().split(",");
			String[] groupNbrs = data.getGroupNbr().split(",");
			String[] discountReturns = data.getDiscountReturn().split(",");
			String[] groupSaleCycles = data.getGroupSaleCycle().split(",");
			String[] estPosiNbrs = data.getEstPosiNbr().split(",");
			String[] estDisposePlans = data.getEstDisposePlan().split(",");
			String[] frequencys = data.getFrequency().split(",");
			int result = 0;
			int fail = 0;
			if(timeIds!=null&&timeIds.length>0){
				for(int i=0;i<timeIds.length;i++){
					MonthSalePlan obj = new MonthSalePlan();
					obj.setAgreement(agreements[i]);
					obj.setTimeId(Integer.valueOf(timeIds[i]));
					obj.setfITNbr(Integer.valueOf(fITNbrs[i]));
					obj.setAllFold(Double.valueOf(allFolds[i]));
					obj.setLowestSale(Double.valueOf(lowestSales[i]));
					obj.setfITSaleCycle(Integer.valueOf(fITSaleCycles[i]));;
					obj.setGroupNbr(Integer.valueOf(groupNbrs[i]));
					obj.setDiscountReturn(Double.valueOf(discountReturns[i]));
					obj.setGroupSaleCycle(Integer.valueOf(groupSaleCycles[i]));
					obj.setEstPosiNbr(Integer.valueOf(estPosiNbrs[i]));
					obj.setEstDisposePlan(estDisposePlans[i]);
					obj.setFrequency(Integer.valueOf(frequencys[i]));
					obj.setStatus(1);
					if(data.getFltNbr()!=null&&!"".equals(data.getFltNbr())){
						String fltNbr = data.getFltNbr();
						fltNbr = fltNbr.substring(3);
						obj.setFltNbr(fltNbr.split("/")[0]);
					}
					if(ids!=null){
						obj.setId(Integer.valueOf(ids[i]));
						result += dataMapper.update(obj);
					}else{
						result += dataMapper.add(obj);
					}
				}
				fail = timeIds.length-result;
			}
//			//返程数据添加
			String[] rIds = null;
			if(data.getrId()!=null&&!"".equals(data.getrId())){
				rIds = data.getrId().split(",");
			}
			String[] rTimeIds = data.getrTimeId().split(",");
			String[] rAgreements = data.getrAgreement().split(",");
			String[] rFITNbrs = data.getrFITNbr().split(",");
			String[] rAllFolds = data.getrAllFold().split(",");
			String[] rLowestSales = data.getrLowestSale().split(",");
			String[] rFITSaleCycles = data.getrFITSaleCycle().split(",");
			String[] rGroupNbrs = data.getrGroupNbr().split(",");
			String[] rDiscountReturn = data.getrDiscountReturn().split(",");
			String[] rGroupSaleCycles = data.getrGroupSaleCycle().split(",");
			String[] rEstPosiNbrs = data.getrEstPosiNbr().split(",");
			String[] rEstDisposePlans = data.getrEstDisposePlan().split(",");
			String[] rFrequencys = data.getrFrequency().split(",");
			int rResult = 0;
			int rFail = 0;
			if(rTimeIds!=null&&rTimeIds.length>0){
				for(int i=0;i<rTimeIds.length;i++){
					MonthSalePlan obj = new MonthSalePlan();
					obj.setAgreement(rAgreements[i]);
					obj.setTimeId(Integer.valueOf(rTimeIds[i]));
					obj.setfITNbr(Integer.valueOf(rFITNbrs[i]));
					obj.setAllFold(Double.valueOf(rAllFolds[i]));
					obj.setLowestSale(Double.valueOf(rLowestSales[i]));
					obj.setfITSaleCycle(Integer.valueOf(rFITSaleCycles[i]));;
					obj.setGroupNbr(Integer.valueOf(rGroupNbrs[i]));
					obj.setDiscountReturn(Double.valueOf(rDiscountReturn[i]));
					obj.setGroupSaleCycle(Integer.valueOf(rGroupSaleCycles[i]));
					obj.setEstPosiNbr(Integer.valueOf(rEstPosiNbrs[i]));
					obj.setEstDisposePlan(rEstDisposePlans[i]);
					obj.setFrequency(Integer.valueOf(rFrequencys[i]));
					obj.setStatus(1);
					if(data.getFltNbr()!=null&&!"".equals(data.getFltNbr())){
						String fltNbr = data.getFltNbr();
						fltNbr = fltNbr.substring(3);
						obj.setFltNbr(fltNbr.split("/")[1]);
					}
					if(rIds!=null){
						obj.setId(Integer.valueOf(rIds[i]));
						rResult += dataMapper.update(obj);
					}else{
						rResult += dataMapper.add(obj);
					}
				}
				rFail = rTimeIds.length - rResult;
			}	
			map.put("result", "往程保存成功"+result+"条，失败"+fail+"条；返程保存成功"+rResult+"条，失败"+rFail+"");
			map.put("opResult", "0");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			map.put("result", "后台繁忙，请稍后再操作");
			return map;
		}
		return map;
	}
	
	public Map<String,Object> getSingleCabinSeatSetData(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String, Object>();
		List<AirData> list = new ArrayList<AirData>();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM");
		//获取往返价格
		try {
			String fltNum = query.getFlt_nbr().substring(3);
			String[] flts = fltNum.split("/");
			//查询往程收益预案舱位设定
			MonthSalesPlanQuery query1 = (MonthSalesPlanQuery) query.clone();
			query1.setFlt_nbr(flts[0]);
			query1.setFltDct(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			query.setFlt_nbr(fltNum);
			/*if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
				query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getPas_stp()+query.getArrv_Airpt_Cd());
			}else{
				query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			}*/
			
			DivideSeason season = seasonMapper.load(query);
			if(season==null){
				MonthSalesPlanQuery query3 = (MonthSalesPlanQuery) query.clone();
				query3.setFlt_nbr(null);
				season = seasonMapper.load2(query3);
			}
			//设置往程航班号
			query.setFlt_nbr(flts[0]);
			//获取销售预案数据
			MonthSalePlan salePlan = null;
			//设置往程，获取往程数据
			//获取同比y比运价
			int price = 0;
			String [] dates = query.getLcl_Dpt_Day().split("-");
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-"+dates[1]);
			AirData data = airLineMapper.getCabinSeatSetData(query);
			try {
				if(data!=null){
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
					price = data.getyBFare();
				}else{
					data = new AirData();
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			rootMap.put("yb", price);
			rootMap.put("rYb", price);
			if(season!=null){
				query1.setTimeId(season.getDivideId());
				CabinSeatSet cabinSeatSet = setMapper.load(query1);
				if(cabinSeatSet!=null){
					rootMap.put("cabinSeatSet", cabinSeatSet);
					rootMap.put("seasonId", season.getDivideId());
				}
				salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
			}else{
				query.setFlt_nbr(null);
				season = seasonMapper.load(query);
				//设置往程航班号
				query.setFlt_nbr(flts[0]);
				if(season!=null){
					salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
				}
			}
			if(salePlan!=null){
				rootMap.put("salePlan", salePlan);
			}else{
				rootMap.put("salePlan", null);
			}
	 		
			rootMap.put("goFltDirect", query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			rootMap.put("goFlt_Rte_Cd", query.getFlt_Rte_Cd());
			rootMap.put("goFltNbr", query.getFlt_nbr());
			//查询三年前同比数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-3)+"-"+dates[1]);
			data = airLineMapper.getCabinSeatSetData(query);
			list.add(data) ;
			//查询两年前同比数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-2)+"-"+dates[1]);
			data = airLineMapper.getCabinSeatSetData(query);
			try {
				if(data!=null){
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
				}else{
					data = new AirData();
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			list.add(data);
			//查询环比数据
			if(Integer.valueOf(dates[1])==1){
				query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-12");
			}else if(Integer.valueOf(dates[1])>9){
				query.setLcl_Dpt_Day(dates[0]+"-"+(Integer.valueOf(dates[1])-1));
			}else{
				query.setLcl_Dpt_Day(dates[0]+"-0"+(Integer.valueOf(dates[1])-1));
			}
			data = airLineMapper.getCabinSeatSetData(query);
			try {
				if(data!=null){
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
				}else{
					data = new AirData();
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			list.add(data);
			rootMap.put("goList", list);
			//查询航班机型的补贴情况
			//查询往程航班座位数
			//设置同比时间
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-"+dates[1]);
			Integer siteCount = airLineMapper.getSiteCount(query);
			Double totalTime = getTotalTime(query);
			///////////////////////
			//重置列表数据
			list = new ArrayList<AirData>();
			//设置返程数据
			String mid_cd = query.getPas_stp();
			if(mid_cd!=null&&!"".equals(mid_cd)){
				//设置返程航线
				String[] cds = query.getFlt_Rte_Cd().split(mid_cd);
				query.setFlt_Rte_Cd(cds[1]+mid_cd+cds[0]);
			}else{
				query.setFlt_Rte_Cd(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
			}
			//设置返程起止机场
			String change = query.getArrv_Airpt_Cd();
			query.setArrv_Airpt_Cd(query.getDpt_AirPt_Cd());
			query.setDpt_AirPt_Cd(change);
			query.setFlt_nbr(flts[1]);
			rootMap.put("returnFltDirect", query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			rootMap.put("returnFltNbr", query.getFlt_nbr());
			rootMap.put("returnFlt_Rte_Cd", query.getFlt_Rte_Cd());
			//查询返程销售预案数据
			query.setLcl_Dpt_Day(dates[0]+"-"+dates[1]);
			//查询返程的销售预案舱位设定
			MonthSalesPlanQuery query2 = (MonthSalesPlanQuery) query.clone();
			if(season!=null){
				query2.setTimeId(season.getDivideId());
				CabinSeatSet cabinSeatSet = setMapper.load(query2);
				if(cabinSeatSet!=null){
					rootMap.put("rCabinSeatSet", cabinSeatSet);
				}
				salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
			}else{
				query.setFlt_nbr(null);
				season = seasonMapper.load(query);
				//设置往程航班号
				query.setFlt_nbr(flts[1]);
				if(season!=null){
					salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
				}
			}
			if(salePlan!=null){
				rootMap.put("rSalePlan", salePlan);
			}else{
				rootMap.put("rSalePlan", null);
			}
			//设置返程时间
			//1.三年前返程数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-3)+"-"+dates[1]);
			data = airLineMapper.getCabinSeatSetData(query);
			//确保前台js中对象不为空
			if(data!=null){
				list.add(data);
			}else{
				data = new AirData();
				list.add(data);
			}
			//2.两年前返程数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-2)+"-"+dates[1]);
			data = airLineMapper.getCabinSeatSetData(query);
			if(data!=null){
				list.add(data);
			}else{
				data = new AirData();
				list.add(data);
			}
			//3.环比返程数据
			if(Integer.valueOf(dates[1])==1){
				query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-12");
			}else if(Integer.valueOf(dates[1])>9){
				query.setLcl_Dpt_Day(dates[0]+"-"+(Integer.valueOf(dates[1])-1));
			}else{
				query.setLcl_Dpt_Day(dates[0]+"-0"+(Integer.valueOf(dates[1])-1));
			}
			data = airLineMapper.getCabinSeatSetData(query);
			if(data!=null){
				list.add(data);
			}else{
				data = new AirData();
				list.add(data);
			}
			rootMap.put("returnList", list);
			//查询返程航班座位数
			//设置同比时间
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-"+dates[1]);
			Integer rSiteCount = airLineMapper.getSiteCount(query);
			siteCount = (rSiteCount+siteCount)/2;
			Double rTotalTime = getTotalTime(query);
			//小时成本
			Double hourCost = basicMapper.selectHourCost(query.getLcl_Dpt_Day(), siteCount);
			rootMap.put("siteCount", siteCount);
			rootMap.put("totalTime", rTotalTime+totalTime);
			rootMap.put("hourCost", hourCost);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return null;//当后台出错时返回null
		}
		return rootMap;
	}
	
	public Map<String,Object> getSingleCabinSeatSetData2(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String, Object>();
		List<AirData> list = new ArrayList<AirData>();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM");
		//获取往返价格
		try {
			String fltNum = query.getFlt_nbr().substring(3);
			String[] flts = fltNum.split("/");
			//查询往程收益预案舱位设定
			MonthSalesPlanQuery query1 = (MonthSalesPlanQuery) query.clone();
			query1.setFlt_nbr(flts[0]);
			query1.setFltDct(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			query.setFlt_nbr(fltNum);
			/*if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
				query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getPas_stp()+query.getArrv_Airpt_Cd());
			}else{
				query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			}*/
			
			DivideSeason season = seasonMapper.load(query);
			if(season==null){
				MonthSalesPlanQuery query3 = (MonthSalesPlanQuery) query.clone();
				query3.setFlt_nbr(null);
				season = seasonMapper.load2(query3);
			}
			//设置往程航班号
			query.setFlt_nbr(flts[0]);
			//获取销售预案数据
			MonthSalePlan salePlan = null;
			//设置往程，获取往程数据
			//获取同比y比运价
			int price = 0;
			String [] dates = query.getLcl_Dpt_Day().split("-");
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-"+dates[1]);
//			AirData data = airLineMapper.getCabinSeatSetData(query);
			AirData data = null;
			List<Z_Airdata> zList = airLineMapper.getCabinSeatSetData2(query);
			double pgs_Per_Cls = 0; // 每班旅客
			double grp_Nbr = 0; // 团队人数
			int ech_Cls_Grp =0; // 每班团队
			double grp_Fng_Rte = 0; // 团队成行率
			double grp_Ine = 0;//团队收入
			int totalNumber = 0;//每班收入
			int yBFare = 0;//yb运价
			int tal_Nbr_Set = 0;
			if(zList!=null&&zList.size()>0){
				data = new AirData();
				for(Z_Airdata z :zList){
					pgs_Per_Cls += z.getPgs_Per_Cls();
					grp_Nbr += z.getGrp_Nbr();
					totalNumber += z.getTotalNumber();
					grp_Ine += z.getGrp_Ine().doubleValue();
					tal_Nbr_Set += z.getTal_Nbr_Set();
					yBFare += z.getyBFare();
				}
				int count = ech_Cls_Grp = zList.size();
				pgs_Per_Cls = pgs_Per_Cls/count;
				grp_Fng_Rte = pgs_Per_Cls/count/tal_Nbr_Set/count;
				grp_Nbr = grp_Nbr/count;
				ech_Cls_Grp = ech_Cls_Grp/count;
				totalNumber = totalNumber/count;
				grp_Ine = grp_Ine/count;
				yBFare = yBFare/count;
				data.setEch_Cls_Grp(ech_Cls_Grp);
				data.setGrp_Fng_Rte(grp_Fng_Rte);
				data.setGrp_Ine(grp_Ine);
				data.setGrp_Nbr(grp_Nbr);
				data.setPgs_Per_Cls(pgs_Per_Cls);
				data.setTotalNumber(totalNumber);
				data.setyBFare(yBFare);
			}
			try {
				if(data!=null){
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
					price = data.getyBFare();
				}else{
					data = new AirData();
					data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			rootMap.put("yb", price);
			rootMap.put("rYb", price);
			if(season!=null){
				query1.setTimeId(season.getDivideId());
				CabinSeatSet cabinSeatSet = setMapper.load(query1);
				if(cabinSeatSet!=null){
					rootMap.put("cabinSeatSet", cabinSeatSet);
					rootMap.put("seasonId", season.getDivideId());
				}
				salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
			}else{
				query.setFlt_nbr(null);
				season = seasonMapper.load(query);
				//设置往程航班号
				query.setFlt_nbr(flts[0]);
				if(season!=null){
					salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
				}
			}
			if(salePlan!=null){
				rootMap.put("salePlan", salePlan);
			}else{
				rootMap.put("salePlan", null);
			}
	 		
			rootMap.put("goFltDirect", query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			rootMap.put("goFlt_Rte_Cd", query.getFlt_Rte_Cd());
			rootMap.put("goFltNbr", query.getFlt_nbr());
			//查询三年前同比数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-3)+"-"+dates[1]);
			zList = airLineMapper.getCabinSeatSetData2(query);
			if(zList!=null&&zList.size()>0){
				data = new AirData();
				for(Z_Airdata z :zList){
					pgs_Per_Cls += z.getPgs_Per_Cls();
					grp_Nbr += z.getGrp_Nbr();
					totalNumber += z.getTotalNumber();
					grp_Ine += z.getGrp_Ine().doubleValue();
					tal_Nbr_Set += z.getTal_Nbr_Set();
					yBFare += z.getyBFare();
				}
				int count = ech_Cls_Grp = zList.size();
				pgs_Per_Cls = pgs_Per_Cls/count;
				grp_Fng_Rte = pgs_Per_Cls/count/tal_Nbr_Set/count;
				grp_Nbr = grp_Nbr/count;
				ech_Cls_Grp = ech_Cls_Grp/count;
				totalNumber = totalNumber/count;
				grp_Ine = grp_Ine/count;
				yBFare = yBFare/count;
				data.setEch_Cls_Grp(ech_Cls_Grp);
				data.setGrp_Fng_Rte(grp_Fng_Rte);
				data.setGrp_Ine(grp_Ine);
				data.setGrp_Nbr(grp_Nbr);
				data.setPgs_Per_Cls(pgs_Per_Cls);
				data.setTotalNumber(totalNumber);
				data.setyBFare(yBFare);
			}else{
				data = new AirData();
			}
			list.add(data) ;
			//查询两年前同比数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-2)+"-"+dates[1]);
			zList = airLineMapper.getCabinSeatSetData2(query);
			if(zList!=null&&zList.size()>0){
				data = new AirData();
				for(Z_Airdata z :zList){
					pgs_Per_Cls += z.getPgs_Per_Cls();
					grp_Nbr += z.getGrp_Nbr();
					totalNumber += z.getTotalNumber();
					grp_Ine += z.getGrp_Ine().doubleValue();
					tal_Nbr_Set += z.getTal_Nbr_Set();
					yBFare += z.getyBFare();
				}
				int count = ech_Cls_Grp = zList.size();
				pgs_Per_Cls = pgs_Per_Cls/count;
				grp_Fng_Rte = pgs_Per_Cls/count/tal_Nbr_Set/count;
				grp_Nbr = grp_Nbr/count;
				ech_Cls_Grp = ech_Cls_Grp/count;
				totalNumber = totalNumber/count;
				grp_Ine = grp_Ine/count;
				yBFare = yBFare/count;
				data.setEch_Cls_Grp(ech_Cls_Grp);
				data.setGrp_Fng_Rte(grp_Fng_Rte);
				data.setGrp_Ine(grp_Ine);
				data.setGrp_Nbr(grp_Nbr);
				data.setPgs_Per_Cls(pgs_Per_Cls);
				data.setTotalNumber(totalNumber);
				data.setyBFare(yBFare);
			}else{
				data = new AirData();
			}
			data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
			list.add(data);
			//查询环比数据
			if(Integer.valueOf(dates[1])==1){
				query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-12");
			}else if(Integer.valueOf(dates[1])>9){
				query.setLcl_Dpt_Day(dates[0]+"-"+(Integer.valueOf(dates[1])-1));
			}else{
				query.setLcl_Dpt_Day(dates[0]+"-0"+(Integer.valueOf(dates[1])-1));
			}
			zList = airLineMapper.getCabinSeatSetData2(query);
			if(zList!=null&&zList.size()>0){
				data = new AirData();
				for(Z_Airdata z :zList){
					pgs_Per_Cls += z.getPgs_Per_Cls();
					grp_Nbr += z.getGrp_Nbr();
					totalNumber += z.getTotalNumber();
					grp_Ine += z.getGrp_Ine().doubleValue();
					tal_Nbr_Set += z.getTal_Nbr_Set();
					yBFare += z.getyBFare();
				}
				int count = ech_Cls_Grp = zList.size();
				pgs_Per_Cls = pgs_Per_Cls/count;
				grp_Fng_Rte = pgs_Per_Cls/count/tal_Nbr_Set/count;
				grp_Nbr = grp_Nbr/count;
				ech_Cls_Grp = ech_Cls_Grp/count;
				totalNumber = totalNumber/count;
				grp_Ine = grp_Ine/count;
				yBFare = yBFare/count;
				data.setEch_Cls_Grp(ech_Cls_Grp);
				data.setGrp_Fng_Rte(grp_Fng_Rte);
				data.setGrp_Ine(grp_Ine);
				data.setGrp_Nbr(grp_Nbr);
				data.setPgs_Per_Cls(pgs_Per_Cls);
				data.setTotalNumber(totalNumber);
				data.setyBFare(yBFare);
			}else{
				data = new AirData();
			}
			data.setLcl_Dpt_Day(date.parse(query.getLcl_Dpt_Day()));
			list.add(data);
			rootMap.put("goList", list);
			//查询航班机型的补贴情况
			//查询往程航班座位数
			//设置同比时间
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-"+dates[1]);
			Integer siteCount = airLineMapper.getSiteCount(query);
			Double totalTime = getTotalTime(query);
			///////////////////////
			//重置列表数据
			list = new ArrayList<AirData>();
			//设置返程数据
			String mid_cd = query.getPas_stp();
			if(mid_cd!=null&&!"".equals(mid_cd)){
				//设置返程航线
				String[] cds = query.getFlt_Rte_Cd().split(mid_cd);
				query.setFlt_Rte_Cd(cds[1]+mid_cd+cds[0]);
			}else{
				query.setFlt_Rte_Cd(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
			}
			//设置返程起止机场
			String change = query.getArrv_Airpt_Cd();
			query.setArrv_Airpt_Cd(query.getDpt_AirPt_Cd());
			query.setDpt_AirPt_Cd(change);
			query.setFlt_nbr(flts[1]);
			rootMap.put("returnFltDirect", query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			rootMap.put("returnFltNbr", query.getFlt_nbr());
			rootMap.put("returnFlt_Rte_Cd", query.getFlt_Rte_Cd());
			//查询返程销售预案数据
			query.setLcl_Dpt_Day(dates[0]+"-"+dates[1]);
			//查询返程的销售预案舱位设定
			MonthSalesPlanQuery query2 = (MonthSalesPlanQuery) query.clone();
			if(season!=null){
				query2.setTimeId(season.getDivideId());
				CabinSeatSet cabinSeatSet = setMapper.load(query2);
				if(cabinSeatSet!=null){
					rootMap.put("rCabinSeatSet", cabinSeatSet);
				}
				salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
			}else{
				query.setFlt_nbr(null);
				season = seasonMapper.load(query);
				//设置往程航班号
				query.setFlt_nbr(flts[1]);
				if(season!=null){
					salePlan = dataMapper.load(season.getDivideId(),query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd(),query.getFlt_nbr(),price);
				}
			}
			if(salePlan!=null){
				rootMap.put("rSalePlan", salePlan);
			}else{
				rootMap.put("rSalePlan", null);
			}
			//设置返程时间
			//1.三年前返程数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-3)+"-"+dates[1]);
			zList = airLineMapper.getCabinSeatSetData2(query);
			if(zList!=null&&zList.size()>0){
				data = new AirData();
				for(Z_Airdata z :zList){
					pgs_Per_Cls += z.getPgs_Per_Cls();
					grp_Nbr += z.getGrp_Nbr();
					totalNumber += z.getTotalNumber();
					grp_Ine += z.getGrp_Ine().doubleValue();
					tal_Nbr_Set += z.getTal_Nbr_Set();
					yBFare += z.getyBFare();
				}
				int count = ech_Cls_Grp = zList.size();
				pgs_Per_Cls = pgs_Per_Cls/count;
				grp_Fng_Rte = pgs_Per_Cls/count/tal_Nbr_Set/count;
				grp_Nbr = grp_Nbr/count;
				ech_Cls_Grp = ech_Cls_Grp/count;
				totalNumber = totalNumber/count;
				grp_Ine = grp_Ine/count;
				yBFare = yBFare/count;
				data.setEch_Cls_Grp(ech_Cls_Grp);
				data.setGrp_Fng_Rte(grp_Fng_Rte);
				data.setGrp_Ine(grp_Ine);
				data.setGrp_Nbr(grp_Nbr);
				data.setPgs_Per_Cls(pgs_Per_Cls);
				data.setTotalNumber(totalNumber);
				data.setyBFare(yBFare);
			}else{
				data = new AirData();
			}
			//确保前台js中对象不为空
			list.add(data);
			//2.两年前返程数据
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-2)+"-"+dates[1]);
			zList = airLineMapper.getCabinSeatSetData2(query);
			if(zList!=null&&zList.size()>0){
				data = new AirData();
				for(Z_Airdata z :zList){
					pgs_Per_Cls += z.getPgs_Per_Cls();
					grp_Nbr += z.getGrp_Nbr();
					totalNumber += z.getTotalNumber();
					grp_Ine += z.getGrp_Ine().doubleValue();
					tal_Nbr_Set += z.getTal_Nbr_Set();
					yBFare += z.getyBFare();
				}
				int count = ech_Cls_Grp = zList.size();
				pgs_Per_Cls = pgs_Per_Cls/count;
				grp_Fng_Rte = pgs_Per_Cls/count/tal_Nbr_Set/count;
				grp_Nbr = grp_Nbr/count;
				ech_Cls_Grp = ech_Cls_Grp/count;
				totalNumber = totalNumber/count;
				grp_Ine = grp_Ine/count;
				yBFare = yBFare/count;
				data.setEch_Cls_Grp(ech_Cls_Grp);
				data.setGrp_Fng_Rte(grp_Fng_Rte);
				data.setGrp_Ine(grp_Ine);
				data.setGrp_Nbr(grp_Nbr);
				data.setPgs_Per_Cls(pgs_Per_Cls);
				data.setTotalNumber(totalNumber);
				data.setyBFare(yBFare);
			}else{
				data = new AirData();
			}
			list.add(data);
			//3.环比返程数据
			if(Integer.valueOf(dates[1])==1){
				query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-12");
			}else if(Integer.valueOf(dates[1])>9){
				query.setLcl_Dpt_Day(dates[0]+"-"+(Integer.valueOf(dates[1])-1));
			}else{
				query.setLcl_Dpt_Day(dates[0]+"-0"+(Integer.valueOf(dates[1])-1));
			}
			zList = airLineMapper.getCabinSeatSetData2(query);
			if(zList!=null&&zList.size()>0){
				data = new AirData();
				for(Z_Airdata z :zList){
					pgs_Per_Cls += z.getPgs_Per_Cls();
					grp_Nbr += z.getGrp_Nbr();
					totalNumber += z.getTotalNumber();
					grp_Ine += z.getGrp_Ine().doubleValue();
					tal_Nbr_Set += z.getTal_Nbr_Set();
					yBFare += z.getyBFare();
				}
				int count = ech_Cls_Grp = zList.size();
				pgs_Per_Cls = pgs_Per_Cls/count;
				grp_Fng_Rte = pgs_Per_Cls/count/tal_Nbr_Set/count;
				grp_Nbr = grp_Nbr/count;
				ech_Cls_Grp = ech_Cls_Grp/count;
				totalNumber = totalNumber/count;
				grp_Ine = grp_Ine/count;
				yBFare = yBFare/count;
				data.setEch_Cls_Grp(ech_Cls_Grp);
				data.setGrp_Fng_Rte(grp_Fng_Rte);
				data.setGrp_Ine(grp_Ine);
				data.setGrp_Nbr(grp_Nbr);
				data.setPgs_Per_Cls(pgs_Per_Cls);
				data.setTotalNumber(totalNumber);
				data.setyBFare(yBFare);
			}else{
				data = new AirData();
			}
			list.add(data);
			rootMap.put("returnList", list);
			//查询返程航班座位数
			//设置同比时间
			query.setLcl_Dpt_Day((Integer.valueOf(dates[0])-1)+"-"+dates[1]);
			Integer rSiteCount = airLineMapper.getSiteCount(query);
			siteCount = (rSiteCount+siteCount)/2;
			Double rTotalTime = getTotalTime(query);
			//小时成本
			Double hourCost = basicMapper.selectHourCost(query.getLcl_Dpt_Day(), siteCount);
			rootMap.put("siteCount", siteCount);
			rootMap.put("totalTime", rTotalTime+totalTime);
			rootMap.put("hourCost", hourCost);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return null;//当后台出错时返回null
		}
		return rootMap;
	}
	
	public Map<String, Object> getCabinSeatSetData(MonthSalesPlanQuery query) {
		Map<String,Object> rootMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		query.setDpt_AirPt_Cd(outPortMapper.getAirCodeByName(query.getDpt_AirPt_Cd()));
		query.setArrv_Airpt_Cd(outPortMapper.getAirCodeByName(query.getArrv_Airpt_Cd()));
		if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
			query.setPas_stp(outPortMapper.getAirCodeByName(query.getPas_stp()));
			query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getPas_stp()+query.getArrv_Airpt_Cd());
			MonthSalesPlanQuery monthSalesPlanQuery = new MonthSalesPlanQuery();
			MonthSalesPlanQuery monthSalesPlanQuery2 = new MonthSalesPlanQuery();
			MonthSalesPlanQuery monthSalesPlanQuery3 = new MonthSalesPlanQuery();
			try {
				monthSalesPlanQuery = (MonthSalesPlanQuery) query.clone();
				map = getSingleCabinSeatSetData2(monthSalesPlanQuery);
				if(map.get("rSalePlan")!=null&&map.get("salePlan")!=null){
					list.add(map);
				}else{
					rootMap.put("opResult", "1");
					rootMap.put("message", "请将航线对应月度销售预案完善，再操作。");
					return rootMap;
				}
				//获取经停机场
				String pas_stp = query.getPas_stp();
				//获取原有到达机场
				String arrv_Airpt_Cd = query.getArrv_Airpt_Cd();
				//将原有到达机场变为经停机场
				query.setArrv_Airpt_Cd(pas_stp);
				monthSalesPlanQuery2 = (MonthSalesPlanQuery) query.clone();
				//获取始发地到经停地月份数据
				map = getSingleCabinSeatSetData2(monthSalesPlanQuery2);
				if(map.get("rSalePlan")!=null&&map.get("salePlan")!=null){
					list.add(map);
				}else{
					rootMap.put("opResult", "1");
					rootMap.put("message", "请将航线对应月度销售预案完善，再操作。");
					return rootMap;
				}
				//将始发地改为经停地，到达地改为原到达机场
				query.setDpt_AirPt_Cd(pas_stp);
				query.setArrv_Airpt_Cd(arrv_Airpt_Cd);
				monthSalesPlanQuery3 = (MonthSalesPlanQuery) query.clone();
				map = getSingleCabinSeatSetData2(monthSalesPlanQuery3);
				if(map.get("rSalePlan")!=null&&map.get("salePlan")!=null){
					list.add(map);
				}else{
					rootMap.put("opResult", "1");
					rootMap.put("message", "请将航线对应月度销售预案完善，再操作。");
					return rootMap;
				}
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}else{
			query.setFlt_Rte_Cd(query.getDpt_AirPt_Cd()+query.getArrv_Airpt_Cd());
			map = getSingleCabinSeatSetData2(query);
			if(map.get("rSalePlan")!=null&&map.get("salePlan")!=null){
				list.add(map);
			}else{
				rootMap.put("opResult", "1");
				rootMap.put("message", "请将航线对应月度销售预案完善，再操作。");
				return rootMap;
			}
		}
		rootMap.put("list",list);
		return rootMap;
	}

	public Map<String, Object> saveCabinSeatSetData(CabinSeatSetQuery query) {
		Map<String,Object> rootMap = new HashMap<String,Object>();
		String [] id = null;
		try {
			String[] timeId = query.getTime_Id().split(",");
			String[] grpPrc = query.getGrp_Prc().split(",");
			String[] two_Tak_Ppt = query.getTwo_Tak_Ppt().split(",");
			String[] ful_Pce_Ppt = query.getFul_Pce_Ppt().split(",");
			String[] nne_Dnt_Ppt = query.getNne_Dnt_Ppt().split(",");
			String[] eht_Five_Dnt_Ppt = query.getEht_Five_Dnt_Ppt().split(",");
			String[] eht_Dnt_Ppt =query.getEht_Dnt_Ppt().split(",");
			String[] sen_Five_Dnt_Ppt = query.getSen_Five_Dnt_Ppt().split(",");
			String[] sen_Dnt_Ppt = query.getSen_Dnt_Ppt().split(",");
			String[] six_Dnt_Ppt =query.getSix_Dnt_Ppt().split(",");
			String[] fve_Dnt_Ppt = query.getFve_Dnt_Ppt().split(",");
			String[] fur_Fve_Dnt_Ppt = query.getFur_Fve_Dnt_Ppt().split(",");
			String[] fur_Dnt_Ppt = query.getFur_Dnt_Ppt().split(",");
			String[] thr_Dnt_Ppt = query.getThr_Dnt_Ppt().split(",");
			String[] two_Dnt_Ppt = query.getTwo_Dnt_Ppt().split(",");
			String[] grp_Nbr = query.getGrp_Nbr().split(",");
			String[] flt_Rte_Cd = query.getFlt_Rte_Cd().split(",");
			String[] flt_Direct = query.getFlt_Direct().split(",");
			String[] fltNbr = query.getFlt_Nbr().split(",");
 			//添加往程舱位设置
			CabinSeatSet set = new CabinSeatSet();
			set.setTime_Id(Integer.valueOf(timeId[0]));
			set.setGrp_Prc(Double.valueOf(grpPrc[0]));
			set.setTwo_Tak_Ppt(Integer.valueOf(two_Tak_Ppt[0]));
			set.setFul_Pce_Ppt(Integer.valueOf(ful_Pce_Ppt[0]));
			set.setNne_Dnt_Ppt(Integer.valueOf(nne_Dnt_Ppt[0]));
			set.setEht_Five_Dnt_Ppt(Integer.valueOf(eht_Five_Dnt_Ppt[0]));
			set.setEht_Dnt_Ppt(Integer.valueOf(eht_Dnt_Ppt[0]));
			set.setSen_Five_Dnt_Ppt(Integer.valueOf(sen_Five_Dnt_Ppt[0]));
			set.setSen_Dnt_Ppt(Integer.valueOf(sen_Dnt_Ppt[0]));
			set.setSix_Dnt_Ppt(Integer.valueOf(six_Dnt_Ppt[0]));
			set.setFve_Dnt_Ppt(Integer.valueOf(fve_Dnt_Ppt[0]));
			set.setFur_Fve_Dnt_Ppt(Integer.valueOf(fur_Fve_Dnt_Ppt[0]));
			set.setFur_Dnt_Ppt(Integer.valueOf(fur_Dnt_Ppt[0]));
			set.setThr_Dnt_Ppt(Integer.valueOf(thr_Dnt_Ppt[0]));
			set.setTwo_Dnt_Ppt(Integer.valueOf(two_Dnt_Ppt[0]));
			set.setGrp_Nbr(Integer.valueOf(grp_Nbr[0]));
			set.setFlt_Direct(flt_Direct[0]);
			set.setFlt_Rte_Cd(flt_Rte_Cd[0]);
			set.setFlt_Nbr(fltNbr[0]);
			int result = 0;
			if(query.getId()!=null&&!"".equals(query.getId())){
				id = query.getId().split(",");
				set.setId(Integer.valueOf(id[0]));
				result = setMapper.update(set);
			}else{
				result = setMapper.add(set);
			}
			if(result >0){
				set.setTime_Id(Integer.valueOf(timeId[1]));
				set.setGrp_Prc(Double.valueOf(grpPrc[1]));
				set.setTwo_Tak_Ppt(Integer.valueOf(two_Tak_Ppt[1]));
				set.setFul_Pce_Ppt(Integer.valueOf(ful_Pce_Ppt[1]));
				set.setNne_Dnt_Ppt(Integer.valueOf(nne_Dnt_Ppt[1]));
				set.setEht_Five_Dnt_Ppt(Integer.valueOf(eht_Five_Dnt_Ppt[1]));
				set.setEht_Dnt_Ppt(Integer.valueOf(eht_Dnt_Ppt[1]));
				set.setSen_Five_Dnt_Ppt(Integer.valueOf(sen_Five_Dnt_Ppt[1]));
				set.setSen_Dnt_Ppt(Integer.valueOf(sen_Dnt_Ppt[1]));
				set.setSix_Dnt_Ppt(Integer.valueOf(six_Dnt_Ppt[1]));
				set.setFve_Dnt_Ppt(Integer.valueOf(fve_Dnt_Ppt[1]));
				set.setFur_Fve_Dnt_Ppt(Integer.valueOf(fur_Fve_Dnt_Ppt[1]));
				set.setFur_Dnt_Ppt(Integer.valueOf(fur_Dnt_Ppt[1]));
				set.setThr_Dnt_Ppt(Integer.valueOf(thr_Dnt_Ppt[1]));
				set.setTwo_Dnt_Ppt(Integer.valueOf(two_Dnt_Ppt[1]));
				set.setGrp_Nbr(Integer.valueOf(grp_Nbr[1]));
				set.setFlt_Direct(flt_Direct[1]);
				set.setFlt_Rte_Cd(flt_Rte_Cd[1]);
				set.setFlt_Nbr(fltNbr[1]);
				if(id!=null&&id.length==2&&id[1]!=null&&!"".equals(id[1])){
					set.setId(Integer.valueOf(id[1]));
					result = setMapper.update(set);
				}else{
					result = setMapper.add(set);
				}
				if(result >0){
					rootMap.put("result", "保存数据成功");
				}else{
					rootMap.put("result", "保存返程数据失败");
				}
			}else{
				rootMap.put("result", "保存数据失败");
			}
			rootMap.put("opResult", "0");
		} catch (Exception e) {
			rootMap.put("result", "保存失败，请检查数据是否正确");
			return rootMap;
		}
 		return rootMap;
	}
	public Double getTotalTime(MonthSalesPlanQuery query){
		Double totalTime = 0d;
		int times = 0;
		List<Z_Airdata> list = airLineMapper.getMonthData(query);
		if(list!=null&&list.size()>0){
			for(Z_Airdata data :list){
				Integer startTime = 0;
				if(data.getLcl_Dpt_Tm()!=null&&!"".equals(data.getLcl_Dpt_Tm())){
					startTime = Integer.parseInt(data.getLcl_Dpt_Tm());
				}
				Integer arrTime = 0;
				if(data.getLcl_Arrv_Tm()!=null&&!"".equals(data.getLcl_Arrv_Tm())){
					arrTime = Integer.parseInt(data.getLcl_Arrv_Tm());
				}
				if(startTime>arrTime){
					times += 24*3600*1000-startTime+arrTime;
				}else{
					times += arrTime-startTime;
				}
			}
			times = times/list.size();
		}
		totalTime = (times+totalTime)/3600*1000;
		return totalTime;
	}

	@Override
	public Map<String, Object> getCurrentFlightSalePlan(
			MonthSalesPlanQuery query) {
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<DivideTime> timeList = new ArrayList<DivideTime>();
		DivideSeason season = new DivideSeason();
		query.setArrv_Airpt_Cd(outPortMapper.getAirCodeByName(query.getArrv_Airpt_Cd()));
		query.setDpt_AirPt_Cd(outPortMapper.getAirCodeByName(query.getDpt_AirPt_Cd()));
		String [] flts = null;
		String[] flights = query.getFlight().substring(3).split("/");
		try {
			//获取航班号
			if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
				String fltNum = query.getFlt_nbr().substring(3);
				query.setFlt_nbr(fltNum);
				flts = fltNum.split("/");
			}
			season = seasonMapper.load(query);
			if(season !=null&&season.getDivideId()>0){
				timeList = timeMapper.list(query.getFltDct(), season.getDivideId());
				if(timeList!=null &&timeList.size()>0){
					for(DivideTime time:timeList){
						//数据回显
						MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(time.getDivideId(),flights[0]);
						if(monthSalePlan!=null){
							time.setMonthSalePlan(monthSalePlan);
						}
					}
				}
				rootMap.put("goTimeList", timeList);
				//重置list列表
				if(query.getFlt_nbr()!=null&&!"".equals(query.getFlt_nbr())){
					query.setFlt_nbr(flts[1]+"/"+flts[0]);
					timeList = timeMapper.list(query.getFltDct().substring(3)+query.getFltDct().substring(0,3), season.getDivideId());
					//设置返程航班号
					query.setFlt_nbr(flts[1]);
				}else{
					timeList = timeMapper.list(query.getFltDct().substring(3)+query.getFltDct().substring(0,3), season.getDivideId());
				}
				//设置返回航线
				if(query.getPas_stp()!=null&&!"".equals(query.getPas_stp())){
					String[] fltR = query.getFlt_Rte_Cd().split(query.getPas_stp());
					query.setFlt_Rte_Cd(fltR[1]+query.getPas_stp()+fltR[0]);
				}else{
					query.setFlt_Rte_Cd(query.getArrv_Airpt_Cd()+query.getDpt_AirPt_Cd());
				}
				if(timeList!=null &&timeList.size()>0){
					for(DivideTime time:timeList){
						MonthSalePlan monthSalePlan = dataMapper.selectByTimeId(time.getDivideId(),flights[1]);
						if(monthSalePlan!=null){
							time.setMonthSalePlan(monthSalePlan);
						}
					}
				}
				rootMap.put("returnTimeList", timeList);
			}
		} catch (Exception e) {
			log.error(e);
			return rootMap;
		}
		return rootMap;
	}

	@Override
	public Map<String, Object> deleteSalePlanData(Integer seasonId,
			String fltNbr) {
		Map<String,Object> map = new HashMap<String,Object>();
		String[] flts = null;
		try {
			if(fltNbr.contains("/")){
				flts = fltNbr.substring(3).split("/");
				List<CabinSeatSet> list = setMapper.list(seasonId, flts[0], flts[1]);
				if(list!=null&&list.size()>0){
					map.put("result", "请将已经存在的对应的预案舱位设定删除，再操作");
					return map;
				}
			}
			List<DivideTime> timeList = timeMapper.list(null, seasonId);
			if(timeList!=null&&timeList.size()>0){
				if(flts!=null){
					for(int i=0;i<timeList.size();i++){
						DivideTime time = timeList.get(i);
						if(time!=null&&time.getDivideId()>0){
							dataMapper.deleteSalePlan(time.getDivideId(), flts[0], flts[1]);
						}
					}
				}else{
					for(int i=0;i<timeList.size();i++){
						DivideTime time = timeList.get(i);
						if(time!=null&&time.getDivideId()>0){
							dataMapper.deleteSalePlan(time.getDivideId(), null,null);
						}
					}
				}
			}
			map.put("result", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			map.put("result", "服务器繁忙，稍后再试");
			return map;
		}
		
		return map;
	}

	@Override
	public Map<String, Object> deleteCabinSeatSetData(Integer seasonId,
			String fltNbr) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String[] flts = fltNbr.substring(3).split("/");
			List<CabinSeatSet> list = setMapper.list(seasonId, flts[0], flts[1]);
			if(list!=null&&list.size()>0){
				int activeLines = setMapper.deleteCabinSeatSet(seasonId, flts[0], flts[1]);
				if(activeLines>0){
					map.put("result", "删除成功");
				}else{
					map.put("result", "删除失败");
				}
			}else{
				map.put("result", "操作成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			map.put("result", "服务器繁忙，稍后再试");
			return map;
		}
		return map;
	}

	@Override
	public DOWQuery getAll_Itia(DOWQuery dowData) {
		dowData.setChina_dpt_AirPt_Cd(outPortMapper.getAirCodeByName(dowData.getDpt_AirPt_Cd()));
		dowData.setChina_arrv_Airpt_Cd(outPortMapper.getAirCodeByName(dowData.getArrv_Airpt_Cd()));
		if(dowData.getPas_stp()!=null&&!"".equals(dowData.getPas_stp())){
			dowData.setChina_pas_stp(outPortMapper.getAirCodeByName(dowData.getPas_stp()));
		}
		return dowData;
	}

	@Override
	public List<Z_Airdata> getAll_Para_Data(String lcl_Dpt_Day,
			String airline1, String airline2) {
		AirLineQuery airLineQuery = new AirLineQuery();
		airLineQuery.setLcl_Dpt_Day(lcl_Dpt_Day);
		airLineQuery.setAirline1(airline1);
		airLineQuery.setAirline2(airline2);
		return airLineMapper.getAll_Para_Data(airLineQuery);
	}

	@Override
	public Map<String, Object> getAirportHistroyData(AirLineQueryNew airLineQueryNew) {
		String dpt_AirPt_CdCode = airLineQueryNew.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = airLineQueryNew.getArrv_Airpt_Cd();
		String pas_stpCode = airLineQueryNew.getPas_stp();
		DecimalFormat df1 = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("##,##0.00");
		//查出条件下所有数据
		Map<String, Object> retMap = new HashMap<String, Object>();
		if("month".equals(airLineQueryNew.getIsDayOrMonth())){
			airLineQueryNew.setStartTime(airLineQueryNew.getStartTime()+"-01");
			airLineQueryNew.setEndTime(airLineQueryNew.getEndTime()+"-31");
		}
		List<Z_Airdata> zairdataList = airLineMapper.getZAirDataList(airLineQueryNew);
		//是否包含异常数据
//		List<Z_Airdata> zairdataListNew = TextUtil.getIsIncludeExceptionData(zairdataList, airLineQueryNew.getIsIncludeExceptionData());
		//exceptionFlag 1表示没有包含异常数据和异常航段，2表示只包含异常数据，3表示只包含异常航段，4表示既包含异常数据又包含异常航段
		String exceptionFlag = "1";
		List<Z_Airdata> zairdataListNew = new ArrayList<Z_Airdata>();
		//是否包含异常数据
		Map<String,Object> dataMap = TextUtil.getIsIncludeExceptionData(zairdataList, airLineQueryNew.getIsIncludeExceptionData());
		List<Z_Airdata> airdataListNew1 = (List<Z_Airdata>) dataMap.get("zairdataListB");
		String dataFlage = (String) dataMap.get("flage");
		//是否包含异常航段
		String strrfalge = "true";
		if(TextUtil.isEmpty(pas_stpCode)){
			strrfalge = "single_true";
		}
		Map<String,Object> dataMaptwo = TextUtil.getIsIncludeExceptionHangduan(airdataListNew1, airLineQueryNew.getIsIncludeExceptionHuangduan(),strrfalge);
		zairdataListNew = (List<Z_Airdata>) dataMaptwo.get("zairdataListB");
		String dataFlage2 = (String) dataMaptwo.get("flage");
		
		if("on".equals(airLineQueryNew.getIsIncludeExceptionData())&&"on".equals(airLineQueryNew.getIsIncludeExceptionHuangduan())){
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
			if("on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(airLineQueryNew.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		String flag = "0";
		if(zairdataListNew!=null&&zairdataListNew.size()>0){
			flag = "1";
		}
		if(TextUtil.isEmpty(pas_stpCode)){
			//直飞
			//收入占比 --2段收入
			double skzsr = 0.0;double tdzsr = 0.0;
			double sksrd1 = 0.0;double sksrd2 = 0.0;
			double tdsrd1 = 0.0;double tdsrd2 = 0.0;
			double skzrs = 0.0;double tdzrs = 0.0;
			double skrsd1 = 0.0;double skrsd2 = 0.0;
			double tdrsd1 = 0.0;double tdrsd2 = 0.0;
			Map<String,Object> everyDuanMap = new TreeMap<String,Object>();
			Map<String,Object> dataMap0 = new TreeMap<String,Object>();
			Map<String,Object> dataMap1 = new TreeMap<String,Object>();
			Map<String,Object> dataMap2 = new TreeMap<String,Object>();
			Map<String,Object> dataDateMap0 = new TreeMap<String,Object>();
			for (Z_Airdata z_Airdata : zairdataListNew) {
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				//第一段
				if(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode)){
					sksrd1 = sksrd1 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd1 = tdsrd1 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd1 = skrsd1 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd1 = tdrsd1 + z_Airdata.getGrp_Nbr();
				}
				//第2段
				if(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode)){
					sksrd2 = sksrd2 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd2 = tdsrd2 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd2 = skrsd2 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd2 = tdrsd2 + z_Airdata.getGrp_Nbr();
				}
				skzsr = skzsr + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
				tdzsr = tdzsr + z_Airdata.getGrp_Ine().doubleValue();
				skzrs = skzrs + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
				tdzrs = tdzrs + z_Airdata.getGrp_Nbr();
			}
			//数据的天数
			List<String> days = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Z_Airdata z_Airdata : zairdataListNew) {
				String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
				if(!days.contains(day)){
					days.add(day);
				}
			}
			//数据的月数
			List<String> months = new ArrayList<String>();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			for (Z_Airdata z_Airdata : zairdataListNew) {
				String month = sdf2.format(z_Airdata.getLcl_Dpt_Day());
				if(!months.contains(month)){
					months.add(month);
				}
			}
			if(!TextUtil.isEmpty(airLineQueryNew.getIsDayOrMonth())){
				if("day".equals(airLineQueryNew.getIsDayOrMonth())){
					for (String string : days) {
						List <String> bcList = new ArrayList<String>();
						double zkl = 0.0;
						double zftzws = 0.0;
						double zbc = 0.0;
						double ztd = 0.0;
						double ztdsr = 0.0;
						double zsr = 0.0;
						double zkzl = 0.0;
						double zpjzk = 0.0;
						double zskzk = 0.0;
						double ztdzk = 0.0;
						AirLineData airLineData = new AirLineData();
						int index = 0 ;
						int zpjzkindex =0;
						int zskzkindex =0;
						int ztdzkindex =0;
						for (Z_Airdata z_Airdata : zairdataListNew) {
							String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
							if(string.equals(day)){
								String flyNum = z_Airdata.getFlt_Nbr();
								if(!bcList.contains(flyNum)){
									bcList.add(flyNum);
								}
								zkl = zkl + z_Airdata.getPgs_Per_Cls();
								zftzws = zftzws + z_Airdata.getTal_Nbr_Set();
								ztd = ztd + z_Airdata.getGrp_Nbr();
								ztdsr = ztdsr + z_Airdata.getGrp_Ine().doubleValue();
								zsr = zsr + z_Airdata.getTotalNumber();
								if(z_Airdata.getTal_Nbr_Set()>0){
									index++;
									zkzl = zkzl + (double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								if(z_Airdata.getAvg_Dct().doubleValue()>0){
									zpjzkindex ++;
									zpjzk = zpjzk + z_Airdata.getAvg_Dct().doubleValue();
								}
								if(z_Airdata.getIdd_Dct().doubleValue()>0){
									zskzkindex ++;
									zskzk = zskzk + z_Airdata.getIdd_Dct().doubleValue();
								}
								if(z_Airdata.getGrp_Dct().doubleValue()>0){
									ztdzkindex ++;
									ztdzk = ztdzk + z_Airdata.getGrp_Dct().doubleValue();
								}
							}
						}
						//每天的总班次，航班号配对，有多少对就有多少班次（不能配对的也算一班）
						zbc = TextUtil.getHbhLHAll(bcList).size();
						if(index<=0){
							index = 1;
						}
						airLineData.setZkl(zkl+"");
						airLineData.setZftzws(zftzws+"");
						airLineData.setZbc(df1.format(zbc*100));
						airLineData.setZtd(ztd+"");
						airLineData.setZtdsr(df1.format(ztdsr/100));
						airLineData.setZsr(df1.format(zsr/1000));
						airLineData.setMbkl(df1.format(zkl/zbc));
						airLineData.setMbftzws(df1.format(zftzws/zbc));
						airLineData.setMbkzl(df1.format(zkzl/index));
						airLineData.setMbtd(df1.format(ztd/zbc)+"");
						airLineData.setMbtdsr(df1.format(ztdsr/zbc/100));
						airLineData.setMbsr(df1.format(zsr/zbc/1000));
						if(zpjzkindex>0){
							airLineData.setZpjzk(df1.format(zpjzk/zpjzkindex));
							airLineData.setMbpjzk(df1.format(zpjzk/zpjzkindex));
						}else{
							airLineData.setZpjzk("0");
							airLineData.setMbpjzk("0");
						}
						if(zskzkindex>0){
							airLineData.setZskzk(df1.format(zskzk/zskzkindex));
							airLineData.setMbskzk(df1.format(zskzk/zskzkindex));
						}else{
							airLineData.setZskzk("0");
							airLineData.setMbskzk("0");
						}
						if(ztdzkindex>0){
							airLineData.setMbtdzk(df1.format(ztdzk/ztdzkindex));
							airLineData.setZtdzk(df1.format(ztdzk/ztdzkindex));
						}else{
							airLineData.setMbtdzk("0");
							airLineData.setZtdzk("0");
						}
						dataDateMap0.put(string, airLineData);
					}
				}
				if("month".equals(airLineQueryNew.getIsDayOrMonth())){
					for (String month : months) {
						double zkl = 0.0;
						double zftzws = 0.0;
						double zbc = 0.0;
						double ztd = 0.0;
						double ztdsr = 0.0;
						double zsr = 0.0;
						double zkzl = 0.0;
						double zpjzk = 0.0;
						double zskzk = 0.0;
						double ztdzk = 0.0;
						AirLineData airLineData = new AirLineData();
						int index = 0 ;
						int zpjzkindex =0;
						int zskzkindex =0;
						int ztdzkindex =0;
						for (String string : days) {
							if(string.substring(0, 7).equals(month)){
								List <String> bcList = new ArrayList<String>();
								for (Z_Airdata z_Airdata : zairdataListNew) {
									String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
									if(string.equals(day)){
										String flyNum = z_Airdata.getFlt_Nbr();
										if(!bcList.contains(flyNum)){
											bcList.add(flyNum);
										}
										zkl = zkl + z_Airdata.getPgs_Per_Cls();
										zftzws = zftzws + z_Airdata.getTal_Nbr_Set();
										ztd = ztd + z_Airdata.getGrp_Nbr();
										ztdsr = ztdsr + z_Airdata.getGrp_Ine().doubleValue();
										zsr = zsr + z_Airdata.getTotalNumber();
										if(z_Airdata.getTal_Nbr_Set()>0){
											index ++;
											zkzl = zkzl + (double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(z_Airdata.getAvg_Dct().doubleValue()>0){
											zpjzkindex ++;
											zpjzk = zpjzk + z_Airdata.getAvg_Dct().doubleValue();
										}
										if(z_Airdata.getIdd_Dct().doubleValue()>0){
											zskzkindex ++;
											zskzk = zskzk + z_Airdata.getIdd_Dct().doubleValue();
										}
										if(z_Airdata.getGrp_Dct().doubleValue()>0){
											ztdzkindex ++;
											ztdzk = ztdzk + z_Airdata.getGrp_Dct().doubleValue();
										}
									}
								}
								//每天的总班次，航班号配对，有多少对就有多少班次（不能配对的也算一班）
								zbc = zbc +  TextUtil.getHbhLHAll(bcList).size();
							}
							if(zbc<=0){zbc = 0.00001;}
							if(index<=0){index = 1;}
							airLineData.setZkl(zkl+"");
							airLineData.setZftzws(zftzws+"");
							airLineData.setZbc(df1.format(zbc*100));
							airLineData.setZtd(ztd+"");
							airLineData.setZtdsr(df1.format(ztdsr/100));
							airLineData.setZsr(df1.format(zsr/1000));
							airLineData.setMbkl(df1.format(zkl/zbc));
							airLineData.setMbftzws(df1.format(zftzws/zbc));
							airLineData.setMbkzl(df1.format(zkzl/index));
							airLineData.setMbtd(df1.format(ztd/zbc)+"");
							airLineData.setMbtdsr(df1.format(ztdsr/zbc/100));
							airLineData.setMbsr(df1.format(zsr/zbc/1000));
							if(zpjzkindex>0){
								airLineData.setZpjzk(df1.format(zpjzk/zpjzkindex));
								airLineData.setMbpjzk(df1.format(zpjzk/zpjzkindex));
							}else{
								airLineData.setZpjzk("0");
								airLineData.setMbpjzk("0");
							}
							if(zskzkindex>0){
								airLineData.setZskzk(df1.format(zskzk/zskzkindex));
								airLineData.setMbskzk(df1.format(zskzk/zskzkindex));
							}else{
								airLineData.setZskzk("0");
								airLineData.setMbskzk("0");
							}
							if(ztdzkindex>0){
								airLineData.setMbtdzk(df1.format(ztdzk/ztdzkindex));
								airLineData.setZtdzk(df1.format(ztdzk/ztdzkindex));
							}else{
								airLineData.setMbtdzk("0");
								airLineData.setZtdzk("0");
							}
							dataDateMap0.put(month, airLineData);
						}
					}
				}
			}
			dataMap0.put("exceptionFlag", exceptionFlag);dataMap0.put("flag", flag);dataMap0.put("sksrd", df2.format(skzsr));dataMap0.put("tdsrd", df2.format(tdzsr));dataMap0.put("skrsd", df2.format(skzrs));dataMap0.put("tdrsd", df2.format(tdzrs));dataMap0.put("dataDate", dataDateMap0);
			
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp1 = TextUtil.getIsIncludeExceptionData(zairdataListB1, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlage1 = (String) dataMaptemp1.get("flage");
			if("true".equals(dataFlage1)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap1.put("exceptionFlag", "2");
			}else{
				dataMap1.put("exceptionFlag", "1");
			}
			dataMap1.put("flag", flag);dataMap1.put("sksrd", df1.format(sksrd1));dataMap1.put("tdsrd", df1.format(tdsrd1));dataMap1.put("skrsd", df1.format(skrsd1));dataMap1.put("tdrsd", df1.format(tdrsd1));dataMap1.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB1,dpt_AirPt_CdCode,arrv_Airpt_CdCode));
			
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp2 = TextUtil.getIsIncludeExceptionData(zairdataListB2, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlagee2 = (String) dataMaptemp2.get("flage");
			if("true".equals(dataFlagee2)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap2.put("exceptionFlag", "2");
			}else{
				dataMap2.put("exceptionFlag", "1");
			}
			dataMap2.put("flag", flag);dataMap2.put("sksrd", df1.format(sksrd2));dataMap2.put("tdsrd", df1.format(tdsrd2));dataMap2.put("skrsd", df1.format(skrsd2));dataMap2.put("tdrsd", df1.format(tdrsd2));dataMap2.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB2,arrv_Airpt_CdCode,dpt_AirPt_CdCode));
			everyDuanMap.put(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"--"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode), dataMap0);
			everyDuanMap.put(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode), dataMap1);
			everyDuanMap.put(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode), dataMap2);
			retMap = everyDuanMap;
		}else{
			//经停
			//收入占比 --六段收入
			double skzsr = 0.0;double tdzsr = 0.0;
			double sksrd1 = 0.0;double sksrd2 = 0.0;double sksrd3 = 0.0;double sksrd4 = 0.0;double sksrd5 = 0.0;double sksrd6 = 0.0;
			double tdsrd1 = 0.0;double tdsrd2 = 0.0;double tdsrd3 = 0.0;double tdsrd4 = 0.0;double tdsrd5 = 0.0;double tdsrd6 = 0.0;
			double skzrs = 0.0;double tdzrs = 0.0;
			double skrsd1 = 0.0;double skrsd2 = 0.0;double skrsd3 = 0.0;double skrsd4 = 0.0;double skrsd5 = 0.0;double skrsd6 = 0.0;
			double tdrsd1 = 0.0;double tdrsd2 = 0.0;double tdrsd3 = 0.0;double tdrsd4 = 0.0;double tdrsd5 = 0.0;double tdrsd6 = 0.0;
			Map<String,Object> everyDuanMap = new TreeMap<String,Object>();
			Map<String,Object> dataMap0 = new TreeMap<String,Object>();
			Map<String,Object> dataMap1 = new TreeMap<String,Object>();
			Map<String,Object> dataMap2 = new TreeMap<String,Object>();
			Map<String,Object> dataMap3 = new TreeMap<String,Object>();
			Map<String,Object> dataMap4 = new TreeMap<String,Object>();
			Map<String,Object> dataMap5 = new TreeMap<String,Object>();
			Map<String,Object> dataMap6 = new TreeMap<String,Object>();
			Map<String,Object> dataDateMap0 = new TreeMap<String,Object>();
			for (Z_Airdata z_Airdata : zairdataListNew) {
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				//第一段
				if(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode)){
					sksrd1 = sksrd1 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd1 = tdsrd1 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd1 = skrsd1 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd1 = tdrsd1 + z_Airdata.getGrp_Nbr();
				}
				//第2段
				if(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode)){
					sksrd2 = sksrd2 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd2 = tdsrd2 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd2 = skrsd2 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd2 = tdrsd2 + z_Airdata.getGrp_Nbr();
				}
				//第3段
				if(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode)){
					sksrd3 = sksrd3 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd3 = tdsrd3 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd3 = skrsd3 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd3 = tdrsd3 + z_Airdata.getGrp_Nbr();
				}
				//第4段
				if(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode)){
					sksrd4 = sksrd4 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd4 = tdsrd4 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd4 = skrsd4 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd4 = tdrsd4 + z_Airdata.getGrp_Nbr();
				}
				//第5段
				if(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode)){
					sksrd5 = sksrd5 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd5 = tdsrd5 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd5 = skrsd5 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd5 = tdrsd5 + z_Airdata.getGrp_Nbr();
				}
				//第6段
				if(dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode)){
					sksrd6 = sksrd6 + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
					tdsrd6 = tdsrd6 + z_Airdata.getGrp_Ine().doubleValue();
					skrsd6 = skrsd6 + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
					tdrsd6 = tdrsd6 + z_Airdata.getGrp_Nbr();
				}
				skzsr = skzsr + (z_Airdata.getTotalNumber() - z_Airdata.getGrp_Ine().doubleValue());
				tdzsr = tdzsr + z_Airdata.getGrp_Ine().doubleValue();
				skzrs = skzrs + z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr();
				tdzrs = tdzrs + z_Airdata.getGrp_Nbr();
			}
			//数据的天数
			List<String> days = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Z_Airdata z_Airdata : zairdataListNew) {
				String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
				if(!days.contains(day)){
					days.add(day);
				}
			}
			//数据的月数
			List<String> months = new ArrayList<String>();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			for (Z_Airdata z_Airdata : zairdataListNew) {
				String month = sdf2.format(z_Airdata.getLcl_Dpt_Day());
				if(!months.contains(month)){
					months.add(month);
				}
			}
			if(!TextUtil.isEmpty(airLineQueryNew.getIsDayOrMonth())){
				if("day".equals(airLineQueryNew.getIsDayOrMonth())){
					for (String string : days) {
						List <String> bcList = new ArrayList<String>();
						double zkl = 0.0;
						double zftzws = 0.0;
						double zbc = 0.0;
						double ztd = 0.0;
						double ztdsr = 0.0;
						double zsr = 0.0;
						double zkzl = 0.0;
						
						double zpjzk = 0.0;
						double zskzk = 0.0;
						double ztdzk = 0.0;
						AirLineData airLineData = new AirLineData();
						int index =0;
						int zkzlindex =0;
						int zpjzkindex =0;
						int zskzkindex =0;
						int ztdzkindex =0;
						for (Z_Airdata z_Airdata : zairdataListNew) {
							String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
							if(string.equals(day)){
								String flyNum = z_Airdata.getFlt_Nbr();
								if(!bcList.contains(flyNum)){
									bcList.add(flyNum);
								}
							}
						}
						List <String> flyNbrsList =TextUtil.getHbhLHAll(bcList);
						zbc = flyNbrsList.size();
						double fltNbrKzlAll = 0.0;
						for (String ftl : flyNbrsList) {
							//某个航班对的平均客座率
							double fltNbrKzlq = 0.0;
							double fltNbrKzlh = 0.0;
							double qd1rs = 0.0;
							double qd2rs = 0.0;
							double qcdrs = 0.0;
							double hd1rs = 0.0;
							double hd2rs = 0.0;
							double hcdrs = 0.0;
							double capq = 0.0;
							double caph = 0.0;
							for (Z_Airdata z_Airdata : zairdataListNew) {
								String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
								String fltnbr = z_Airdata.getFlt_Nbr();
								String dpt = z_Airdata.getDpt_AirPt_Cd();
								String arr = z_Airdata.getArrv_Airpt_Cd();
								if(string.equals(day)&&ftl.contains(fltnbr)){
									//去短1
									if(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode)){
										qd1rs = z_Airdata.getPgs_Per_Cls();
										if(z_Airdata.getCount_Set()>capq){
											capq = z_Airdata.getCount_Set();
										}
									}
									//去短2
									if(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode)){
										qd2rs = z_Airdata.getPgs_Per_Cls();
										if(z_Airdata.getCount_Set()>capq){
											capq = z_Airdata.getCount_Set();
										}
									}
									//去长段
									if(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode)){
										qcdrs = z_Airdata.getPgs_Per_Cls();
										if(z_Airdata.getCount_Set()>capq){
											capq = z_Airdata.getCount_Set();
										}
									}
									//回短1
									if(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode)){
										hd1rs = z_Airdata.getPgs_Per_Cls();
										if(z_Airdata.getCount_Set()>caph){
											caph = z_Airdata.getCount_Set();
										}
									}
									//回短2
									if(dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode)){
										hd2rs = z_Airdata.getPgs_Per_Cls();
										if(z_Airdata.getCount_Set()>caph){
											caph = z_Airdata.getCount_Set();
										}
									}
									//回长段
									if(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode)){
										hcdrs = z_Airdata.getPgs_Per_Cls();
										if(z_Airdata.getCount_Set()>caph){
											caph = z_Airdata.getCount_Set();
										}
									}
								}
							}
							if(capq>0){
								fltNbrKzlq = (qd1rs + qd2rs + 2*qcdrs)/2/capq*100;
							}
							if(caph>0){
								fltNbrKzlh = (hd1rs + hd2rs + 2*hcdrs)/2/caph*100;
							}
							fltNbrKzlAll = (fltNbrKzlq + fltNbrKzlh)/2 + fltNbrKzlAll;
						}
						for (Z_Airdata z_Airdata : zairdataListNew) {
							String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
							if(string.equals(day)){
								zkl = zkl + z_Airdata.getPgs_Per_Cls();
								zftzws = zftzws + z_Airdata.getTal_Nbr_Set();
								ztd = ztd + z_Airdata.getGrp_Nbr();
								ztdsr = ztdsr + z_Airdata.getGrp_Ine().doubleValue();
								zsr = zsr + z_Airdata.getTotalNumber();
								if(z_Airdata.getTal_Nbr_Set()>0){
									zkzlindex ++;
									zkzl = zkzl + (double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								if(z_Airdata.getAvg_Dct().doubleValue()>0){
									zpjzkindex ++;
									zpjzk = zpjzk + z_Airdata.getAvg_Dct().doubleValue();
								}
								if(z_Airdata.getIdd_Dct().doubleValue()>0){
									zskzkindex ++;
									zskzk = zskzk + z_Airdata.getIdd_Dct().doubleValue();
								}
								if(z_Airdata.getGrp_Dct().doubleValue()>0){
									ztdzkindex ++;
									ztdzk = ztdzk + z_Airdata.getGrp_Dct().doubleValue();
								}
								index++;
							}
						}
						
						if(zbc<=0){
							zbc = 0.00001;
						}
						if(index<=0){
							index = 1;
						}
						airLineData.setZkl(zkl+"");
						airLineData.setZftzws(zftzws+"");
						airLineData.setZbc(df1.format(zbc*100));
						airLineData.setZtd(ztd+"");
						airLineData.setZtdsr(df1.format(ztdsr/100));
						airLineData.setZsr(df1.format(zsr/1000));
						
						airLineData.setMbkl(df1.format(zkl/zbc));
						airLineData.setMbftzws(df1.format(zftzws/zbc));
						airLineData.setMbtd(df1.format(ztd/zbc)+"");
						airLineData.setMbtdsr(df1.format(ztdsr/zbc/100));
						airLineData.setMbsr(df1.format(zsr/zbc/1000));
						
						if((zkzlindex>0&&zkzlindex<6)){
							airLineData.setMbkzl(df1.format(zkzl/zkzlindex));
						}else{
							if(zkzlindex==6){
								//公式计算每班客座率求平均（每班客座率=短人数和+2长段人数/2cap）
								airLineData.setMbkzl(df1.format(fltNbrKzlAll/zbc));
							}else{
								airLineData.setMbkzl("0");
							}
						}
						if(zpjzkindex>0){
							airLineData.setZpjzk(df1.format(zpjzk/zpjzkindex));
							airLineData.setMbpjzk(df1.format(zpjzk/zpjzkindex));
						}else{
							airLineData.setZpjzk("0");
							airLineData.setMbpjzk("0");
						}
						if(zskzkindex>0){
							airLineData.setZskzk(df1.format(zskzk/zskzkindex));
							airLineData.setMbskzk(df1.format(zskzk/zskzkindex));
						}else{
							airLineData.setZskzk("0");
							airLineData.setMbskzk("0");
						}
						if(ztdzkindex>0){
							airLineData.setMbtdzk(df1.format(ztdzk/ztdzkindex));
							airLineData.setZtdzk(df1.format(ztdzk/ztdzkindex));
						}else{
							airLineData.setMbtdzk("0");
							airLineData.setZtdzk("0");
						}
						
						dataDateMap0.put(string, airLineData);
					}
				}
				if("month".equals(airLineQueryNew.getIsDayOrMonth())){
					for (String month : months) {
						double zkl = 0.0;
						double zftzws = 0.0;
						double zbc = 0.0;
						double ztd = 0.0;
						double ztdsr = 0.0;
						double zsr = 0.0;
						double zpjzk = 0.0;
						double zskzk = 0.0;
						double ztdzk = 0.0;
						AirLineData airLineData = new AirLineData();
						int index =0;
						int zpjzkindex =0;
						int zskzkindex =0;
						int ztdzkindex =0;
						double fltNbrKzlAll = 0.0;
						for (String string : days) {
							List <String> bcList = new ArrayList<String>();
							if(string.substring(0, 7).equals(month)){
								for (Z_Airdata z_Airdata : zairdataListNew) {
									String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
									if(string.equals(day)){
										String flyNum = z_Airdata.getFlt_Nbr();
										if(!bcList.contains(flyNum)){
											bcList.add(flyNum);
										}
									}
								}
							}
							
							List <String> flyNbrsList =TextUtil.getHbhLHAll(bcList);
							for (String ftl : flyNbrsList) {
								//某个航班对的平均客座率
								double fltNbrKzlq = 0.0;
								double fltNbrKzlh = 0.0;
								double qd1rs = 0.0;
								double qd2rs = 0.0;
								double qcdrs = 0.0;
								double hd1rs = 0.0;
								double hd2rs = 0.0;
								double hcdrs = 0.0;
								double capq = 0.0;
								double caph = 0.0;
								int zkzlindex =0;
								double zkzl = 0.0;
								for (Z_Airdata z_Airdata : zairdataListNew) {
									String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
									String fltnbr = z_Airdata.getFlt_Nbr();
									String dpt = z_Airdata.getDpt_AirPt_Cd();
									String arr = z_Airdata.getArrv_Airpt_Cd();
									if(string.equals(day)&&ftl.contains(fltnbr)){
										//去短1
										if(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode)){
											qd1rs = z_Airdata.getPgs_Per_Cls();
											if(z_Airdata.getCount_Set()>capq){
												capq = z_Airdata.getCount_Set();
											}
										}
										//去短2
										if(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode)){
											qd2rs = z_Airdata.getPgs_Per_Cls();
											if(z_Airdata.getCount_Set()>capq){
												capq = z_Airdata.getCount_Set();
											}
										}
										//去长段
										if(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode)){
											qcdrs = z_Airdata.getPgs_Per_Cls();
											if(z_Airdata.getCount_Set()>capq){
												capq = z_Airdata.getCount_Set();
											}
										}
										//回短1
										if(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode)){
											hd1rs = z_Airdata.getPgs_Per_Cls();
											if(z_Airdata.getCount_Set()>caph){
												caph = z_Airdata.getCount_Set();
											}
										}
										//回短2
										if(dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode)){
											hd2rs = z_Airdata.getPgs_Per_Cls();
											if(z_Airdata.getCount_Set()>caph){
												caph = z_Airdata.getCount_Set();
											}
										}
										//回长段
										if(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode)){
											hcdrs = z_Airdata.getPgs_Per_Cls();
											if(z_Airdata.getCount_Set()>caph){
												caph = z_Airdata.getCount_Set();
											}
										}
										if(z_Airdata.getTal_Nbr_Set()>0){
											zkzlindex ++;
											zkzl = zkzl + (double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
									}
								}
								if(capq>0){
									fltNbrKzlq = (qd1rs + qd2rs + 2*qcdrs)/2/capq*100;
								}
								if(caph>0){
									fltNbrKzlh = (hd1rs + hd2rs + 2*hcdrs)/2/caph*100;
								}
								if(fltNbrKzlq>0&&fltNbrKzlh>0){
									fltNbrKzlAll = (fltNbrKzlq + fltNbrKzlh)/2 + fltNbrKzlAll;
								}else{
									fltNbrKzlAll = zkzl/zkzlindex + fltNbrKzlAll;
								}
							}
							if(string.substring(0, 7).equals(month)){
								for (Z_Airdata z_Airdata : zairdataListNew) {
									String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
									if(string.equals(day)){
										zkl = zkl + z_Airdata.getPgs_Per_Cls();
										zftzws = zftzws + z_Airdata.getTal_Nbr_Set();
										ztd = ztd + z_Airdata.getGrp_Nbr();
										ztdsr = ztdsr + z_Airdata.getGrp_Ine().doubleValue();
										zsr = zsr + z_Airdata.getTotalNumber();
										
										if(z_Airdata.getAvg_Dct().doubleValue()>0){
											zpjzkindex ++;
											zpjzk = zpjzk + z_Airdata.getAvg_Dct().doubleValue();
										}
										if(z_Airdata.getIdd_Dct().doubleValue()>0){
											zskzkindex ++;
											zskzk = zskzk + z_Airdata.getIdd_Dct().doubleValue();
										}
										if(z_Airdata.getGrp_Dct().doubleValue()>0){
											ztdzkindex ++;
											ztdzk = ztdzk + z_Airdata.getGrp_Dct().doubleValue();
										}
										index++;
									}
								}
								zbc = zbc +  TextUtil.getHbhLHAll(bcList).size();
							}
						}
						if(zbc<=0){zbc = 0.00001;}
						if(index<=0){index = 1;}
						airLineData.setZkl(zkl+"");
						airLineData.setZftzws(zftzws+"");
						airLineData.setZbc(df1.format(zbc*100));
						airLineData.setZtd(ztd+"");
						airLineData.setZtdsr(df1.format(ztdsr/100));
						airLineData.setZsr(df1.format(zsr/1000));
						airLineData.setMbkl(df1.format(zkl/zbc));
						airLineData.setMbftzws(df1.format(zftzws/zbc));
						airLineData.setMbtd(df1.format(ztd/zbc)+"");
						airLineData.setMbtdsr(df1.format(ztdsr/zbc/100));
						airLineData.setMbsr(df1.format(zsr/zbc/1000));
//						if(zkzlindex>0&&zkzlindex<6){
//							airLineData.setMbkzl(df1.format(zkzl/zkzlindex));
//						}else{
//							if(zkzlindex==6){
								//公式计算每班客座率求平均（每班客座率=短人数和+2长段人数/2cap）
								airLineData.setMbkzl(df1.format(fltNbrKzlAll/zbc));
//							}else{
//								airLineData.setMbkzl("0");
//							}
//						}
						if(zpjzkindex>0){
							airLineData.setZpjzk(df1.format(zpjzk/zpjzkindex));
							airLineData.setMbpjzk(df1.format(zpjzk/zpjzkindex));
						}else{
							airLineData.setZpjzk("0");
							airLineData.setMbpjzk("0");
						}
						if(zskzkindex>0){
							airLineData.setZskzk(df1.format(zskzk/zskzkindex));
							airLineData.setMbskzk(df1.format(zskzk/zskzkindex));
						}else{
							airLineData.setZskzk("0");
							airLineData.setMbskzk("0");
						}
						if(ztdzkindex>0){
							airLineData.setMbtdzk(df1.format(ztdzk/ztdzkindex));
							airLineData.setZtdzk(df1.format(ztdzk/ztdzkindex));
						}else{
							airLineData.setMbtdzk("0");
							airLineData.setZtdzk("0");
						}
						dataDateMap0.put(month, airLineData);
					}
				}
			}
			dataMap0.put("exceptionFlag", exceptionFlag);dataMap0.put("flag", flag);dataMap0.put("sksrd", df2.format(skzsr));dataMap0.put("tdsrd", df2.format(tdzsr));dataMap0.put("skrsd", df2.format(skzrs));dataMap0.put("tdrsd", df2.format(tdzrs));dataMap0.put("dataDate", dataDateMap0);
			List<Z_Airdata> zairdataListB1 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB1.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp1 = TextUtil.getIsIncludeExceptionData(zairdataListB1, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlagee1 = (String) dataMaptemp1.get("flage");
			if("true".equals(dataFlagee1)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap1.put("exceptionFlag", "2");
			}else{
				dataMap1.put("exceptionFlag", "1");
			}
			dataMap1.put("flag", flag);dataMap1.put("sksrd", df1.format(sksrd1));dataMap1.put("tdsrd", df1.format(tdsrd1));dataMap1.put("skrsd", df1.format(skrsd1));dataMap1.put("tdrsd", df1.format(tdrsd1));dataMap1.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB1,dpt_AirPt_CdCode,pas_stpCode));
			
			List<Z_Airdata> zairdataListB2 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(dpt_AirPt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB2.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp2 = TextUtil.getIsIncludeExceptionData(zairdataListB2, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlagee2 = (String) dataMaptemp2.get("flage");
			if("true".equals(dataFlagee2)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap2.put("exceptionFlag", "2");
			}else{
				dataMap2.put("exceptionFlag", "1");
			}
			dataMap2.put("flag", flag);dataMap2.put("sksrd", df1.format(sksrd2));dataMap2.put("tdsrd", df1.format(tdsrd2));dataMap2.put("skrsd", df1.format(skrsd2));dataMap2.put("tdrsd", df1.format(tdrsd2));dataMap2.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB2,dpt_AirPt_CdCode,arrv_Airpt_CdCode));
			
			List<Z_Airdata> zairdataListB3 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(arrv_Airpt_CdCode)){
					zairdataListB3.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp3 = TextUtil.getIsIncludeExceptionData(zairdataListB3, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlagee3 = (String) dataMaptemp3.get("flage");
			if("true".equals(dataFlagee3)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap3.put("exceptionFlag", "2");
			}else{
				dataMap3.put("exceptionFlag", "1");
			}
			dataMap3.put("flag", flag);dataMap3.put("sksrd", df1.format(sksrd3));dataMap3.put("tdsrd", df1.format(tdsrd3));dataMap3.put("skrsd", df1.format(skrsd3));dataMap3.put("tdrsd", df1.format(tdrsd3));dataMap3.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB3,pas_stpCode,arrv_Airpt_CdCode));
			
			List<Z_Airdata> zairdataListB4 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(pas_stpCode)){
					zairdataListB4.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp4 = TextUtil.getIsIncludeExceptionData(zairdataListB4, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlagee4 = (String) dataMaptemp4.get("flage");
			if("true".equals(dataFlagee4)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap4.put("exceptionFlag", "2");
			}else{
				dataMap4.put("exceptionFlag", "1");
			}
			dataMap4.put("flag", flag);dataMap4.put("sksrd", df1.format(sksrd4));dataMap4.put("tdsrd", df1.format(tdsrd4));dataMap4.put("skrsd", df1.format(skrsd4));dataMap4.put("tdrsd", df1.format(tdrsd4));dataMap4.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB4,arrv_Airpt_CdCode,pas_stpCode));
			
			List<Z_Airdata> zairdataListB5 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(arrv_Airpt_CdCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB5.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp5 = TextUtil.getIsIncludeExceptionData(zairdataListB5, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlagee5 = (String) dataMaptemp5.get("flage");
			if("true".equals(dataFlagee5)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap5.put("exceptionFlag", "2");
			}else{
				dataMap5.put("exceptionFlag", "1");
			}
			dataMap5.put("flag", flag);dataMap5.put("sksrd", df1.format(sksrd5));dataMap5.put("tdsrd", df1.format(tdsrd5));dataMap5.put("skrsd", df1.format(skrsd5));dataMap5.put("tdrsd", df1.format(tdrsd5));dataMap5.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB5,arrv_Airpt_CdCode,dpt_AirPt_CdCode));
			
			List<Z_Airdata> zairdataListB6 = new ArrayList<Z_Airdata>();
			for (Z_Airdata z_Airdata : zairdataList) {
				if(z_Airdata.getDpt_AirPt_Cd().equals(pas_stpCode)&&z_Airdata.getArrv_Airpt_Cd().equals(dpt_AirPt_CdCode)){
					zairdataListB6.add(z_Airdata);
				}
			}
			//是否包含异常数据
			Map<String,Object> dataMaptemp6 = TextUtil.getIsIncludeExceptionData(zairdataListB6, airLineQueryNew.getIsIncludeExceptionData());
			String dataFlagee6 = (String) dataMaptemp6.get("flage");
			if("true".equals(dataFlagee6)&&"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
				dataMap6.put("exceptionFlag", "2");
			}else{
				dataMap6.put("exceptionFlag", "1");
			}
			dataMap6.put("flag", flag);dataMap6.put("sksrd", df1.format(sksrd6));dataMap6.put("tdsrd", df1.format(tdsrd6));dataMap6.put("skrsd", df1.format(skrsd6));dataMap6.put("tdrsd", df1.format(tdrsd6));dataMap6.put("dataDate", getEveryDuanData(months,days,airLineQueryNew,zairdataListB6,pas_stpCode,dpt_AirPt_CdCode));
			everyDuanMap.put(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode), dataMap0);
			everyDuanMap.put(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode), dataMap1);
			everyDuanMap.put(outPortMapper.getairportNameByCode(dpt_AirPt_CdCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode), dataMap2);
			everyDuanMap.put(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(arrv_Airpt_CdCode), dataMap3);
			everyDuanMap.put(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(pas_stpCode), dataMap4);
			everyDuanMap.put(outPortMapper.getairportNameByCode(arrv_Airpt_CdCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode), dataMap5);
			everyDuanMap.put(outPortMapper.getairportNameByCode(pas_stpCode)+"-"+outPortMapper.getairportNameByCode(dpt_AirPt_CdCode), dataMap6);
			retMap = everyDuanMap;
		}
		return retMap;
	}
	public Map<String,Object> getEveryDuanData(List<String> months,List<String> days,AirLineQueryNew airLineQueryNew,List<Z_Airdata> zairdataListNew,String dpt_code,String arr_code){
		Map<String,Object> dataDateMap0 = new TreeMap<String,Object>();
		DecimalFormat df1 = new DecimalFormat("0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!TextUtil.isEmpty(airLineQueryNew.getIsDayOrMonth())){
			if("day".equals(airLineQueryNew.getIsDayOrMonth())){
				days.clear();
				for (Z_Airdata z_Airdata : zairdataListNew) {
					if(!days.contains(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
						days.add(sdf.format(z_Airdata.getLcl_Dpt_Day()));
					}
				}
				for (String string : days) {
					List <String> bcList = new ArrayList<String>();
					double zkl = 0.0;
					double zftzws = 0.0;
					double zbc = 0.0;
					double ztd = 0.0;
					double ztdsr = 0.0;
					double zsr = 0.0;
					double zkzl = 0.0;
					
					double zpjzk = 0.0;
					double zskzk = 0.0;
					double ztdzk = 0.0;
					int zkzlindex =0;
					int zpjzkindex =0;
					int zskzkindex =0;
					int ztdzkindex =0;
					AirLineData airLineData = new AirLineData();
					for (Z_Airdata z_Airdata : zairdataListNew) {
						String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
						String dpt = z_Airdata.getDpt_AirPt_Cd();
						String arr = z_Airdata.getArrv_Airpt_Cd();
						if(dpt.equals(dpt_code)&&arr.equals(arr_code)){
							if(string.equals(day)){
								String flyNum = z_Airdata.getFlt_Nbr();
								if(!bcList.contains(flyNum)){
									bcList.add(flyNum);
								}
								zkl = zkl + z_Airdata.getPgs_Per_Cls();
								zftzws = zftzws + z_Airdata.getTal_Nbr_Set();
								ztd = ztd + z_Airdata.getGrp_Nbr();
								ztdsr = ztdsr + z_Airdata.getGrp_Ine().doubleValue();
								zsr = zsr + z_Airdata.getTotalNumber();
								if(z_Airdata.getTal_Nbr_Set()>0){
									zkzlindex ++;
									zkzl = zkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								if(z_Airdata.getAvg_Dct().doubleValue()>0){
									zpjzkindex ++;
									zpjzk = zpjzk + z_Airdata.getAvg_Dct().doubleValue();
								}
								if(z_Airdata.getIdd_Dct().doubleValue()>0){
									zskzkindex ++;
									zskzk = zskzk + z_Airdata.getIdd_Dct().doubleValue();
								}
								if(z_Airdata.getGrp_Dct().doubleValue()>0){
									ztdzkindex ++;
									ztdzk = ztdzk + z_Airdata.getGrp_Dct().doubleValue();
								}
								
							}
						}
					}
					zbc = bcList.size();
					if(zbc<=0){
						zbc = 0.00001;
					}
					airLineData.setZkl(zkl+"");
					airLineData.setZftzws(zftzws+"");
					airLineData.setZbc(df1.format(zbc*100));
					airLineData.setZtd(ztd+"");
					airLineData.setZtdsr(df1.format(ztdsr/100));
					airLineData.setZsr(df1.format(zsr/1000));
					airLineData.setMbkl(df1.format(zkl/zbc));
					airLineData.setMbftzws(df1.format(zftzws/zbc));
					airLineData.setMbtd(df1.format(ztd/zbc)+"");
					airLineData.setMbtdsr(df1.format(ztdsr/zbc/100));
					airLineData.setMbsr(df1.format(zsr/zbc/1000));
					if(zkzlindex>0){
						airLineData.setMbkzl(df1.format(zkzl/zkzlindex));
					}else{
						airLineData.setMbkzl("0");
					}
					if(zpjzkindex>0){
						airLineData.setZpjzk(df1.format(zpjzk/zpjzkindex));
						airLineData.setMbpjzk(df1.format(zpjzk/zpjzkindex));
					}else{
						airLineData.setZpjzk("0");
						airLineData.setMbpjzk("0");
					}
					if(zskzkindex>0){
						airLineData.setZskzk(df1.format(zskzk/zskzkindex));
						airLineData.setMbskzk(df1.format(zskzk/zskzkindex));
					}else{
						airLineData.setZskzk("0");
						airLineData.setMbskzk("0");
					}
					if(ztdzkindex>0){
						airLineData.setMbtdzk(df1.format(ztdzk/ztdzkindex));
						airLineData.setZtdzk(df1.format(ztdzk/ztdzkindex));
					}else{
						airLineData.setMbtdzk("0");
						airLineData.setZtdzk("0");
					}
					dataDateMap0.put(string, airLineData);
				}
			}
			if("month".equals(airLineQueryNew.getIsDayOrMonth())){
				days.clear();
				for (Z_Airdata z_Airdata : zairdataListNew) {
					if(!days.contains(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
						days.add(sdf.format(z_Airdata.getLcl_Dpt_Day()));
					}
				}
				months.clear();
				for (Z_Airdata z_Airdata : zairdataListNew) {
					if(!months.contains(sdf.format(z_Airdata.getLcl_Dpt_Day()).substring(0, 7))){
						months.add(sdf.format(z_Airdata.getLcl_Dpt_Day()).substring(0, 7));
					}
				}
				for (String month : months) {
					double zkl = 0.0;
					double zftzws = 0.0;
					double zbc = 0.0;
					double ztd = 0.0;
					double ztdsr = 0.0;
					double zsr = 0.0;
					double zkzl = 0.0;
					
					double zpjzk = 0.0;
					double zskzk = 0.0;
					double ztdzk = 0.0;
					int zkzlindex =0;
					int zpjzkindex =0;
					int zskzkindex =0;
					int ztdzkindex =0;
					AirLineData airLineData = new AirLineData();
					for (String string : days) {
						if(string.substring(0, 7).equals(month)){
							List <String> bcList = new ArrayList<String>();
							
							for (Z_Airdata z_Airdata : zairdataListNew) {
								String dpt = z_Airdata.getDpt_AirPt_Cd();
								String arr = z_Airdata.getArrv_Airpt_Cd();
								if(dpt.equals(dpt_code)&&arr.equals(arr_code)){
									String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
									if(string.equals(day)){
										String flyNum = z_Airdata.getFlt_Nbr();
										if(!bcList.contains(flyNum)){
											bcList.add(flyNum);
										}
										zkl = zkl + z_Airdata.getPgs_Per_Cls();
										zftzws = zftzws + z_Airdata.getTal_Nbr_Set();
										ztd = ztd + z_Airdata.getGrp_Nbr();
										ztdsr = ztdsr + z_Airdata.getGrp_Ine().doubleValue();
										zsr = zsr + z_Airdata.getTotalNumber();
										if(z_Airdata.getTal_Nbr_Set()>0){
											zkzlindex ++;
											zkzl = zkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(z_Airdata.getAvg_Dct().doubleValue()>0){
											zpjzkindex ++;
											zpjzk = zpjzk + z_Airdata.getAvg_Dct().doubleValue();
										}
										if(z_Airdata.getIdd_Dct().doubleValue()>0){
											zskzkindex ++;
											zskzk = zskzk + z_Airdata.getIdd_Dct().doubleValue();
										}
										if(z_Airdata.getGrp_Dct().doubleValue()>0){
											ztdzkindex ++;
											ztdzk = ztdzk + z_Airdata.getGrp_Dct().doubleValue();
										}
										
									}
								}
							}
							zbc = zbc +  bcList.size();
						}
						if(zbc<=0){zbc = 0.00001;}
						airLineData.setZkl(zkl+"");
						airLineData.setZftzws(zftzws+"");
						airLineData.setZbc(df1.format(zbc*100));
						airLineData.setZtd(ztd+"");
						airLineData.setZtdsr(df1.format(ztdsr/100));
						airLineData.setZsr(df1.format(zsr/1000));
						airLineData.setMbkl(df1.format(zkl/zbc));
						airLineData.setMbftzws(df1.format(zftzws/zbc));
						airLineData.setMbtd(df1.format(ztd/zbc)+"");
						airLineData.setMbtdsr(df1.format(ztdsr/zbc/100));
						airLineData.setMbsr(df1.format(zsr/zbc/1000));
						if(zkzlindex>0){
							airLineData.setMbkzl(df1.format(zkzl/zkzlindex));
						}else{
							airLineData.setMbkzl("0");
						}
						if(zpjzkindex>0){
							airLineData.setZpjzk(df1.format(zpjzk/zpjzkindex));
							airLineData.setMbpjzk(df1.format(zpjzk/zpjzkindex));
						}else{
							airLineData.setZpjzk("0");
							airLineData.setMbpjzk("0");
						}
						if(zskzkindex>0){
							airLineData.setZskzk(df1.format(zskzk/zskzkindex));
							airLineData.setMbskzk(df1.format(zskzk/zskzkindex));
						}else{
							airLineData.setZskzk("0");
							airLineData.setMbskzk("0");
						}
						if(ztdzkindex>0){
							airLineData.setMbtdzk(df1.format(ztdzk/ztdzkindex));
							airLineData.setZtdzk(df1.format(ztdzk/ztdzkindex));
						}else{
							airLineData.setMbtdzk("0");
							airLineData.setZtdzk("0");
						}
						dataDateMap0.put(month, airLineData);
					}
				}
			}
		}
		return dataDateMap0;
	}

	@Override
	public String getNewDate(AirLineQueryNew airLineQueryNew) {
		Date str = airLineMapper.getNewDate(airLineQueryNew);
		if(str==null){
			str = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(str);
	}
	
	@Override
	public Map<String,Object> getCurrentFltData(String startDate, String endDate,
			String itia) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Z_Airdata> list = airLineMapper.getCurrtSeasonFltData(startDate, endDate, itia);
			if(list!=null&&list.size()>0){
				List<String> flts = new ArrayList<String>();
				for(int i=0;i<list.size();i++){
					Z_Airdata obj = list.get(i);
					String fltLine = obj.getFlt_Rte_Cd();
					flts.add(obj.getFlt_Nbr());
					//对航班号进行组队
					Set<String> fltPair = DataUtils.formatFltNbr(flts);
					if(fltLine.length()==9){
						String dpt = fltLine.substring(0,3);
						String pas = fltLine.substring(3,6);
						String arrv = fltLine.substring(6);
						Set<String> oldList = (Set<String>) map.get(dpt+"="+pas+"="+arrv);//当一个城市有多个机场时适用
						if(oldList!=null&&oldList.size()>0){
							fltPair.addAll(oldList);
							map.put(dpt+"="+pas+"="+arrv, fltPair);
						}else{
							oldList = (Set<String>) map.get(arrv+"="+pas+"="+dpt);//当一个城市有多个机场时适用
							if(oldList!=null&&oldList.size()>0){
								fltPair.addAll(oldList);
								map.put(arrv+"="+pas+"="+dpt, fltPair);
							}else{
								map.put(dpt+"="+pas+"="+arrv, fltPair);
							}
						}
					}else if(fltLine.length()==6){
						String dpt = fltLine.substring(0,3);
						String arrv = fltLine.substring(3);
						Set<String> oldList = (Set<String>) map.get(dpt+"="+arrv);//当一个城市有多个机场时适用
						if(oldList!=null&&oldList.size()>0){
							fltPair.addAll(oldList);
							map.put(dpt+"="+arrv, fltPair);
						}else{
							oldList = (Set<String>) map.get(arrv+"="+dpt);//当一个城市有多个机场时适用
							if(oldList!=null&&oldList.size()>0){
								fltPair.addAll(oldList);
								map.put(arrv+"="+dpt, fltPair);
							}else{
								map.put(dpt+"="+arrv, fltPair);
							}
						}
					}
					//清空原集合
					flts = new ArrayList<String>();
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
		
		return map;
	}
	
}
