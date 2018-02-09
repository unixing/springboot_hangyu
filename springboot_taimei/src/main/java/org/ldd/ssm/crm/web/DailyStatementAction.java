package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.DailyStatement;
import org.ldd.ssm.crm.domain.DailyStatementTotal;
import org.ldd.ssm.crm.query.PageResult;
import org.ldd.ssm.crm.query.SystemLogQuery;
import org.ldd.ssm.crm.service.IDailyStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User的前台控制器层
 */
@Controller
public class DailyStatementAction {
	@Autowired
	private IDailyStatementService dailyStatementService;
	// springMVC请求的映射地址
	@RequestMapping("/DailyStatement")
	public String index() {
		// 重定向到欢迎页面,基于springMVC
		return "DailyStatement_list";
	}
	//拿到日报表的数据
	@RequestMapping("/DailyStatement_list")
	@ResponseBody
	public PageResult<DailyStatement> list(SystemLogQuery searchKey){
		return dailyStatementService.list(searchKey);
	}
	//拿到日报表的数据
	@RequestMapping("/DailyStatement_graph")
	public String DailyStatement_graph(){
		return "DailyStatement_graph";
	}
	@RequestMapping("/DailyStatement_graph21")
	public String DailyStatement_graph21(){
		return "OutPort_graph";
	}
	@RequestMapping("/DailyStatement_graph3")
	public String DailyStatement_graph3(){
		return "DailyStatement_graph3";
	}
	@RequestMapping("/DailyStatement_graph4")
	public String DailyStatement_graph4(){
		return "DailyStatement_graph4";
	}
	//拿到日报表的数据
	@RequestMapping("/DailyStatement_graph2")
	@ResponseBody
	public Map<String, Object> DailyStatement_graph2(SystemLogQuery searchKey){
		//最外层的json对象,用来装第二层的json数据
				Map<String, Object> rootMap = new HashMap<String, Object>();
				//第二层的json对象,用来装数据的
				Map<String, String> chart = new HashMap<String, String>();
				//这些Key都是固定的,前台js会根据key拿到值
				chart.put("caption", "日报表");
				chart.put("xaxisname", "");
				chart.put("yaxisname", "人数");
				chart.put("numberprefix", "");
				//将第二层的json对象放入第一层的json对象,前台js会根据key拿到值
				rootMap.put("chart", chart);
//				//第二层的数组, 里面装的json对象
				List<Map<String, String>> data = new ArrayList<Map<String, String>>();
				List<String[]> list = new ArrayList<String[]>();
//				//根据分组查询条件拿到查询结果集
				List<DailyStatementTotal> findByGroupBy1 = dailyStatementService.findByGroupBy1(searchKey);
				for (DailyStatementTotal dt : findByGroupBy1) {
					String[] str1 = {"f",dt.getF().toString()};
					String[] str2 = {"y",dt.getY().toString()};
					String[] str3 = {"b",dt.getB().toString()};
					String[] str4 = {"h",dt.getH().toString()};
					String[] str5 = {"k",dt.getK().toString()};
					String[] str6 = {"l",dt.getL().toString()};
					String[] str7 = {"m",dt.getM().toString()};
					String[] str8 = {"q",dt.getQ().toString()};
					String[] str9 = {"x",dt.getX().toString()};
					String[] str10 = {"u",dt.getU().toString()};
					String[] str11 = {"e",dt.getE().toString()};
					String[] str12 = {"t",dt.getT().toString()};
					String[] str13 = {"z",dt.getZ().toString()};
					String[] str14 = {"g",dt.getG().toString()};
					String[] str15 = {"o",dt.getO().toString()};
					String[] str16 = {"s",dt.getS().toString()};
					String[] str17 = {"v",dt.getV().toString()};
					String[] str18 = {"yElse",dt.getyElse().toString()};
					String[] str19 = {"combinedGroup",dt.getCombinedGroup().toString()};
					String[] str20 = {"totalNumber",dt.getTotalNumber().toString()};
					String[] str21 = {"individualIncome",dt.getIndividualIncome().toString()};
					String[] str22 = {"teamTotalRevenue",dt.getTeamTotalRevenue().toString()};
					String[] str23 = {"kilometerIncome",dt.getKilometerIncome().toString()};
					String[] str24 = {"averageDiscount",dt.getAverageDiscount().toString()};
					String[] str25 = {"scatteredRegimentTotalRevenue",dt.getScatteredRegimentTotalRevenue().toString()};
					String[] str26 = {"HourlyEarnings",dt.getHourlyEarnings().toString()};
					String[] str27 = {"averageLoadFactors",dt.getAverageLoadFactors().toString()};
					String[] str28 = {"incomeGoalHours",dt.getIncomeGoalHours().toString()};
					String[] str29 = {"SubsidiesOfClass",dt.getSubsidiesOfClass().toString()};
					String[] str30 = {"monthAccumulativetotalsubsidy",dt.getMonthAccumulativetotalsubsidy().toString()};
					list.add(str1);
					list.add(str2);
					list.add(str3);
					list.add(str4);
					list.add(str5);
					list.add(str6);
					list.add(str7);
					list.add(str8);
					list.add(str9);
					list.add(str10);
					list.add(str11);
					list.add(str12);
					list.add(str13);
					list.add(str14);
					list.add(str15);
					list.add(str16);
					list.add(str17);
					list.add(str18);
					list.add(str19);
					list.add(str20);
					list.add(str21);
					list.add(str22);
					list.add(str23);
					list.add(str24);
					list.add(str25);
					list.add(str26);
					list.add(str27);
					list.add(str28);
					list.add(str29);
					list.add(str30);
				}
				//遍历结果集
				for (String[] objects : list) {
//					//实例一个新的map
					Map<String, String> map = new HashMap<String, String>();
					//根据固定的key将值装入map中object[0]装的是分组名,objects[1],装的总数
					map.put("label",objects[0]);
					map.put("value", objects[1]);
					//将json对象添加进数组中
					data.add(map);
				}
//				//将数据装入第一层的json对象中
				rootMap.put("data", data);
				//将json对象装入map栈中
//				putContext("map", rootMap);
		return rootMap;
	}
	@RequestMapping("/DailyStatement_graphT")
	@ResponseBody
	public Map<String, Object> DailyStatement_graphT(SystemLogQuery searchKey){
		//最外层的json对象,用来装第二层的json数据
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//第二层的数组, 里面装的json对象
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<String[]> list = new ArrayList<String[]>();
		//根据分组查询条件拿到查询结果集
		List<DailyStatementTotal> findByGroupBy1 = dailyStatementService.findByGroupBy1(searchKey);
		for (DailyStatementTotal dt : findByGroupBy1) {
			String[] str1 = {"f",dt.getF().toString()};
			String[] str2 = {"y",dt.getY().toString()};
			String[] str3 = {"b",dt.getB().toString()};
			String[] str4 = {"h",dt.getH().toString()};
			String[] str5 = {"k",dt.getK().toString()};
			String[] str6 = {"l",dt.getL().toString()};
			String[] str7 = {"m",dt.getM().toString()};
			String[] str8 = {"q",dt.getQ().toString()};
			String[] str9 = {"x",dt.getX().toString()};
			String[] str10 = {"u",dt.getU().toString()};
			String[] str11 = {"e",dt.getE().toString()};
			String[] str12 = {"t",dt.getT().toString()};
			String[] str13 = {"z",dt.getZ().toString()};
			String[] str14 = {"g",dt.getG().toString()};
			String[] str15 = {"o",dt.getO().toString()};
			String[] str16 = {"s",dt.getS().toString()};
			String[] str17 = {"v",dt.getV().toString()};
			String[] str18 = {"yElse",dt.getyElse().toString()};
			String[] str19 = {"combinedGroup",dt.getCombinedGroup().toString()};
			String[] str20 = {"totalNumber",dt.getTotalNumber().toString()};
			String[] str21 = {"individualIncome",dt.getIndividualIncome().toString()};
			String[] str22 = {"teamTotalRevenue",dt.getTeamTotalRevenue().toString()};
			String[] str23 = {"kilometerIncome",dt.getKilometerIncome().toString()};
			String[] str24 = {"averageDiscount",dt.getAverageDiscount().toString()};
			String[] str25 = {"scatteredRegimentTotalRevenue",dt.getScatteredRegimentTotalRevenue().toString()};
			String[] str26 = {"HourlyEarnings",dt.getHourlyEarnings().toString()};
			String[] str27 = {"averageLoadFactors",dt.getAverageLoadFactors().toString()};
			String[] str28 = {"incomeGoalHours",dt.getIncomeGoalHours().toString()};
			String[] str29 = {"SubsidiesOfClass",dt.getSubsidiesOfClass().toString()};
			String[] str30 = {"monthAccumulativetotalsubsidy",dt.getMonthAccumulativetotalsubsidy().toString()};
			list.add(str1);
			list.add(str2);
			list.add(str3);
			list.add(str4);
			list.add(str5);
			list.add(str6);
			list.add(str7);
			list.add(str8);
			list.add(str9);
			list.add(str10);
			list.add(str11);
			list.add(str12);
			list.add(str13);
			list.add(str14);
			list.add(str15);
			list.add(str16);
			list.add(str17);
			list.add(str18);
			list.add(str19);
			list.add(str20);
			list.add(str21);
			list.add(str22);
			list.add(str23);
			list.add(str24);
			list.add(str25);
			list.add(str26);
			list.add(str27);
			list.add(str28);
			list.add(str29);
			list.add(str30);
		}
		//遍历结果集
		for (String[] objects : list) {
//					//实例一个新的map
			Map<String, String> map = new HashMap<String, String>();
			//根据固定的key将值装入map中object[0]装的是分组名,objects[1],装的总数
			map.put("name",objects[0]);
			map.put("value", objects[1]);
			//将json对象添加进数组中
			data.add(map);
		}
//				//将数据装入第一层的json对象中
		rootMap.put("list", data);
		//将json对象装入map栈中
//				putContext("map", rootMap);
		return rootMap;
	}

}