package org.ldd.ssm.hangyu.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.RouteNetwork;
import org.ldd.ssm.hangyu.domain.SimpleDemand;

/**
 * 工具类
 * @Title:TextUtil 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-26 下午2:23:40
 */
public class TextUtil {
	/**
	 * 判断字符串是否为空
	 * @Title: isEmpty 
	 * @Description:  
	 * @param @param str
	 * @param @return    
	 * @return boolean     
	 * @throws
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0 || "".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	 * 判定是否为数字
	 * @Title: isNum 
	 * @Description:  
	 * @param @param str
	 * @param @return    
	 * @return boolean     
	 * @throws
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	
	public static List<Demand> formatDemand(List<Demand> list){
		List<Demand> newList = new ArrayList<Demand>();
		//变更数据库获取的状态值为汉字内容
		if(list==null||list.size()==0){
			return list;
		}
		for(Demand demand:list){
			//判断需求状态
			if("0".equals(demand.getDemandstate())){
				demand.setDemandStateStr("正常");
			}else if("1".equals(demand.getDemandstate())){
				demand.setDemandStateStr("完成");
			}else if("2".equals(demand.getDemandstate())){
				demand.setDemandStateStr("异常");
			}else if("3".equals(demand.getDemandstate())){
				demand.setDemandStateStr("删除");
			}else if("4".equals(demand.getDemandstate())){
				demand.setDemandStateStr("未处理");
			}else if("5".equals(demand.getDemandstate())){
				demand.setDemandStateStr("审核不通过");
			}else if("6".equals(demand.getDemandstate())){
				demand.setDemandStateStr("审核通过");
			}
			//判断需求类型(0:航线需求、1:运力投放、2:运营托管、3:航线委托、4:运力委托)
			if("0".equals(demand.getDemandtype())){
				demand.setDemandtypeStr("航线需求");
			}else if("1".equals(demand.getDemandtype())){
				demand.setDemandtypeStr("运力投放");
			}else if("2".equals(demand.getDemandtype())){
				demand.setDemandtypeStr("运营托管");
			}else if("3".equals(demand.getDemandtype())){
				demand.setDemandtypeStr("航线委托");
			}else if("4".equals(demand.getDemandtype())){
				demand.setDemandtypeStr("运力委托");
			}
			//始发地是否接收临近机场（0:接收,1:不接收）
			if("0".equals(demand.getDptAcceptnearairport())){
				demand.setDptAcceptnearairportStr("接收");
			}else{
				demand.setDptAcceptnearairportStr("不接收");
			}
			//始发地时刻资源（0：显示时刻，1：待协调 2：时刻充足）
			if("0".equals(demand.getDptTimeresources())){
				demand.setDptTimeresourcesStr(demand.getDptTime());
			}else if("1".equals(demand.getDptTimeresources())){
				demand.setDptTimeresourcesStr("待协调");
			}else if("2".equals(demand.getDptTimeresources())){
				demand.setDptTimeresourcesStr("时刻充足");
			}else{
				if(!TextUtils.isEmpty(demand.getDptTime()))
					demand.setDptTimeresourcesStr(demand.getDptTime());
			}
			//经停地是否接收临近机场(0:接收,1:不接受)
			if("0".equals(demand.getPstAcceptnearairport())){
				demand.setPstAcceptnearairportStr("接收");
			}else if("1".equals(demand.getPstAcceptnearairport())){
				demand.setPstAcceptnearairportStr("不接收");
			}
			//经停地时刻资源（0：显示时刻，1：待协调 2：时刻充足）
			if("0".equals(demand.getPstTimeresources())){
				demand.setPstTimeresourcesStr(demand.getPstTime());
			}else if("1".equals(demand.getPstTimeresources())){
				demand.setPstTimeresourcesStr("待协调");
			}else if("2".equals(demand.getPstTimeresources())){
				demand.setPstTimeresourcesStr("时刻充足");
			}else{
				if(!TextUtils.isEmpty(demand.getPstTime()))
					demand.setPstTimeresourcesStr(demand.getPstTime());
			}
			//到达地是否接收临近机场(0:接收,1:不接受)
			if("0".equals(demand.getArrvAcceptnearairport())){
				demand.setArrvAcceptnearairportStr("接收");
			}else if("1".equals(demand.getArrvAcceptnearairport())){
				demand.setArrvAcceptnearairportStr("不接收");
			}
			//到达地时刻资源（0：显示时刻，1：待协调 2：时刻充足）
			if("0".equals(demand.getArrvTimeresources())){
				demand.setArrvTimeresourcesStr(demand.getArrvTime());
			}else if("1".equals(demand.getArrvTimeresources())){
				demand.setArrvTimeresourcesStr("待协调");
			}else if("2".equals(demand.getArrvTimeresources())){
				demand.setArrvTimeresourcesStr("时刻充足");
			}else{
				if(!TextUtils.isEmpty(demand.getArrvTime()))
					demand.setArrvTimeresourcesStr(demand.getArrvTime());
			}
			//补贴有种状态：有补贴0:有补贴-定补、1:有补贴-保底,2:有补贴-人头补,3:有补贴-其他,4:待议5:无补贴。
			if("0".equals(demand.getSubsidypolicy())){
				demand.setSubsidypolicyStr("定补");
			}else if("1".equals(demand.getSubsidypolicy())){
				demand.setSubsidypolicyStr("保底");
			}else if("2".equals(demand.getSubsidypolicy())){
				demand.setSubsidypolicyStr("人头补");
			}else if("3".equals(demand.getSubsidypolicy())){
				demand.setSubsidypolicyStr("其他");
			}else if("4".equals(demand.getSubsidypolicy())){
				demand.setSubsidypolicyStr("待议");
			}else if("5".equals(demand.getSubsidypolicy())){
				demand.setSubsidypolicyStr("无补贴");
			}
			//是否接受调度
			if("0".equals(demand.getScheduling())){
				demand.setSchedulingStr("接受");
			}else if("1".equals(demand.getScheduling())){
				demand.setSchedulingStr("不接受");
			}
			//是否有意向航线(0:有,本表主键id外键到intendedAirLine表demand_字段中,1:无)
			demand.setIntendedairlineStr("0".equals(demand.getIntendedairline())?"有":"无");
			//公开方式(0:对所有人公开,1:对认证用户公开,2:定向航司,3:定向机场), 3和4定位目标在下一个字段
			if("0".equals(demand.getPublicway())){
				demand.setPublicwayStr("所有人");
			}else if("1".equals(demand.getPublicway())){
				demand.setPublicwayStr("认证用户");
			}else if("2".equals(demand.getPublicway())){
				demand.setPublicwayStr("定向航司");
			}else if("3".equals(demand.getPublicway())){
				demand.setPublicwayStr("定向机场");
			}
			//0:需求发布、1:意向征集、2:订单确认、3:关闭（审核不通过、下架、过期）、4:订单完成、
			//5:佣金支付、6:交易完成、7:待处理、8:已接受、9:处理中、10:已拒绝
			if("0".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("需求发布");
			}else if("1".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("意向征集");
			}else if("2".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("订单确认");
			}else if("3".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("关闭"+(StringUtils.isEmpty(demand.getCloseReason())?"":"（"+demand.getCloseReason()+"）"));
			}else if("4".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("订单完成");
			}else if("5".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("佣金支付");
			}else if("6".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("交易完成");
			}else if("7".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("待处理");
			}else if("8".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("已接受");
			}else if("9".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("处理中");
			}else if("10".equals(demand.getDemandprogress())){
				demand.setDemandprogressStr("已拒绝");
			}
			
			//封装需求列表显示
			SimpleDemand[] simpleDemandArray = new SimpleDemand[4];
			if("0".equals(demand.getDemandtype())){//航线需求
				simpleDemandArray[0] = new SimpleDemand("始发时刻", demand.getDptTimeresourcesStr());
				simpleDemandArray[1] = new SimpleDemand("班期", demand.getDays());
				simpleDemandArray[2] = new SimpleDemand("机型", demand.getAircrfttyp());
				simpleDemandArray[3] = new SimpleDemand("补贴", demand.getSubsidypolicyStr());
			}else if("1".equals(demand.getDemandtype())){//运力投放
				simpleDemandArray[0] = new SimpleDemand("所在地", demand.getDptNm());
				simpleDemandArray[1] = new SimpleDemand("小时成本", demand.getHourscost()+"");
				simpleDemandArray[2] = new SimpleDemand("机型", demand.getAircrfttyp());
				simpleDemandArray[3] = new SimpleDemand("是否调度", demand.getSchedulingStr());
			}
			demand.setSimpleDemand(simpleDemandArray);
			newList.add(demand);
		}
		return newList;
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date==null){
			date = new Date();
		}
		return df.format(date);
	}
	
	public static List<RouteNetwork> distinct(List<RouteNetwork> list){
		List<RouteNetwork> newList = new ArrayList<RouteNetwork>();
		if(list==null||list.size()==0){
			return list;
		}
		RouteNetwork airport = list.get(0);
		newList.add(airport);
		List<String> listStr = new ArrayList<String>();
		listStr.add(airport.getDptIata()+airport.getArrvIata());
		for(int i=1;i<list.size();i++){
			airport = list.get(i);
			String dptIata = airport.getDptIata();
			String arrvIata = airport.getArrvIata();
			String route = arrvIata+dptIata;
			if(!listStr.contains(route)){
				listStr.add(dptIata+arrvIata);
				newList.add(airport);
			}
		}
		return newList;
	}
}
