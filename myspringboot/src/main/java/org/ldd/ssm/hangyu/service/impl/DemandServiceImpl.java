package org.ldd.ssm.hangyu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;
import org.ldd.ssm.hangyu.domain.AirportInfoNewThree;
import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.domain.CityCoordinate;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.IntendedAirline;
import org.ldd.ssm.hangyu.domain.SimpleDemand;
import org.ldd.ssm.hangyu.mapper.AirCompenyInfoThreeMapper;
import org.ldd.ssm.hangyu.mapper.AirportInfoNewThreeMapper;
import org.ldd.ssm.hangyu.mapper.ChatMapper;
import org.ldd.ssm.hangyu.mapper.CityCoordinateMapper;
import org.ldd.ssm.hangyu.mapper.DemandMapper;
import org.ldd.ssm.hangyu.mapper.EmployeeMapper;
import org.ldd.ssm.hangyu.mapper.IntendedAirlineMapper;
import org.ldd.ssm.hangyu.mapper.ResponseMapper;
import org.ldd.ssm.hangyu.query.CommissionedAndCustody;
import org.ldd.ssm.hangyu.query.DemandDto;
import org.ldd.ssm.hangyu.query.MyOrder;
import org.ldd.ssm.hangyu.service.DemandService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class DemandServiceImpl implements DemandService {
	@Autowired
	DemandMapper demandMapper;
	@Autowired
	CityCoordinateMapper cityCoordinateMapper;
	@Autowired
	IntendedAirlineMapper intendedAirlineMapper;
	@Autowired
	AirportInfoNewThreeMapper airportInfoNewThreeMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	ResponseMapper responseMapper;
	@Autowired
	ChatMapper chatMapper;
	@Autowired
	AirCompenyInfoThreeMapper airCompenyInfoThreeMapper;
	
	Logger log = Logger.getLogger(DemandServiceImpl.class);
	
	
	public Demand formatDemand(Demand demand){
		//变更数据库获取的状态值为汉字内容
		if(demand==null){
			return demand;
		}
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
			if(!TextUtil.isEmpty(demand.getSchedulinePort())){
				List<AirportInfoNewThree> airportInfoNewThrees = new ArrayList<AirportInfoNewThree>();
				String[] iatas = demand.getSchedulinePort().split(",");
				for(String iata:iatas){
					AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectById(Long.parseLong(iata.trim()));
					if(airportInfoNewThree!=null){
						airportInfoNewThrees.add(airportInfoNewThree);
					}	
				}
				if(airportInfoNewThrees.size()>0){
					demand.setAirportForSchedulines(airportInfoNewThrees);
				}
			}
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
		if (TextUtil.isEmpty(demand.getDptNm())) {
			demand.setDptNm(demand.getDpt());
		}else if (TextUtil.isEmpty(demand.getPstNm())) {
			demand.setPstNm(demand.getPst());
		}else if (TextUtil.isEmpty(demand.getArrvNm())) {
			demand.setArrvNm(demand.getArrv());
		}
		return demand;
	}
	
	@Cacheable(value = "getDemandsForCurrentEmployee")
	public PageBean<Demand> getDemandsForCurrentEmployee(DemandSearch demandSearch) {
		PageBean<Demand> pbList = null;
		//判断对象属性的可用性
		if(demandSearch.getEmployeeId()==null||demandSearch.getEmployeeId()==0l){
			log.debug("DemandServiceImpl:getDemandsForCurrentEmployee have invalid parameters");
			return pbList;
		}
		try {
			int totalCount = demandMapper.getDemandsForCurrentEmployeeCount(demandSearch);
			if(totalCount>0){
				//查询并对获取的列表进行数据编辑
				List<Demand> list = TextUtil.formatDemand(demandMapper.getDemandsForCurrentEmployee(demandSearch));
				pbList = new PageBean<Demand>(totalCount, list);
			}else{
				pbList = new PageBean<Demand>(totalCount, null);
			}
		} catch (Exception e) {
			log.error("getDemandsForCurrentEmployee has something wrong", e);
			e.printStackTrace();
			return pbList;
		}
		return pbList;
	}

	@Cacheable(value = "getDemandsForCurrentCheckedCity")
	public PageBean<Demand> getDemandsForCurrentCheckedCity(
			DemandSearch demandSearch) {
		PageBean<Demand> pbList = null;
		if(TextUtils.isEmpty(demandSearch.getItia())){
			log.debug("DemandServiceImpl:getDemandsForCurrentCheckedCity have invalid parameters");
			return pbList;
		}
		try {
			int totalCount = demandMapper.getDemandsForCurrentCheckedCityCount(demandSearch);
			if(totalCount>0&&totalCount>demandSearch.getStartIndex()){
				//查询并对获取的列表进行数据编辑
				List<Demand> list = TextUtil.formatDemand(demandMapper.getDemandsForCurrentCheckedCity(demandSearch));
				pbList = new PageBean<Demand>(totalCount, list);
			}else{
				pbList = new PageBean<Demand>(totalCount, null);
			}
		} catch (Exception e) {
			log.error("getDemandsForCurrentCheckedCity has something wrong here", e);
			e.printStackTrace();
			return pbList;
		}
		return pbList;
	}

	@Cacheable(value = "getAllDemands")
	public List<DemandDto> getAllDemands(Employee dto) {
		//获取当前登录用户对应航司或者机场的id
		if(dto.getRole().equals("0")){//航司
			dto.setDirectId(demandMapper.getAircompenyId(dto));
		}else if(dto.getRole().equals("1")){//机场
			dto.setDirectId(demandMapper.getAirportId(dto));
		}
		return demandMapper.getAllDemands(dto);
	}
	
	//Demand 对象编辑方法
	public Demand editDemand(Demand demand){
		//数据编辑
		//注意：需求的添加和修改都不确定标题（修改时有可能变更了某一航点就导致标题变更），所以标题需要更新
		StringBuffer title = new StringBuffer("");
		String dpt = demand.getDpt();
		if("0".equals(demand.getDemandtype())||"3".equals(demand.getDemandtype())){//航线需求
			String pst = demand.getPst();
			String arrv = demand.getArrv();
			if(!TextUtil.isEmpty(dpt)){
				String dptState = demand.getDptState();
				if("0".equals(dptState)){//始发点类型为机场
					//由机场三字码查询城市对象
					CityCoordinate cityCoordinate = cityCoordinateMapper.selectByAirIcao(dpt);
					//设置城市三字码
					demand.setDptCt(cityCoordinate.getCityIcao());
					//设置航线需求标题-起始点
					title.append(cityCoordinate.getCityName());
				}else if("2".equals(dptState)){//始发点类型为城市
					demand.setDptCt(dptState);
					//通过城市三字码查询城市对象
					CityCoordinate cityCoordinate = cityCoordinateMapper.selectByCityIcao(dpt);
					//设置航线需求标题-起始点
					title.append(cityCoordinate.getCityName());
				}else{
					title.append(dpt);
				}
			}else{
				//设置航线需求标题-起始点
				title.append("待定");
			}
			if(!TextUtil.isEmpty(pst)){
				String pstState = demand.getPstState();
				if("0".equals(pstState)){//经停点类型为机场
					//由机场三字码查询城市对象
					CityCoordinate cityCoordinate = cityCoordinateMapper.selectByAirIcao(pst);
					//设置城市三字码
					demand.setPstCt(cityCoordinate.getCityIcao());
					//设置航线需求标题-经停点
					title.append("-"+cityCoordinate.getCityName());
				}else if("2".equals(pstState)){//经停类型为城市
					demand.setDptCt(pstState);
					//通过城市三字码查询城市对象
					CityCoordinate cityCoordinate = cityCoordinateMapper.selectByCityIcao(pst);
					//设置航线需求标题-经停点
					title.append("-"+cityCoordinate.getCityName());
				}else{
					title.append("-"+pst);
				}
			}else{
				//设置航线需求标题-经停点
				title.append("-待定");
			}
			if(!TextUtil.isEmpty(arrv)){
				String arrvState = demand.getArrvState();
				if("0".equals(arrvState)){//到达点类型为机场
					//由机场三字码查询城市对象
					CityCoordinate cityCoordinate = cityCoordinateMapper.selectByAirIcao(arrv);
					//设置城市三字码
					demand.setArrvCt(cityCoordinate.getCityIcao());
					//设置航线需求标题-到达点
					title.append("-"+cityCoordinate.getCityName());
				}else if("2".equals(arrvState)){//到达点类型为城市
					demand.setDptCt(arrvState);
					//通过城市三字码查询城市对象
					CityCoordinate cityCoordinate = cityCoordinateMapper.selectByCityIcao(arrv);
					//设置航线需求标题-到达点
					title.append("-"+cityCoordinate.getCityName());
				}else{
					title.append("-"+arrv);
				}
			}else{
				//设置航线需求标题-到达点
				title.append("-待定");
			}
			
		}else if("1".equals(demand.getDemandtype())||"4".equals(demand.getDemandtype())){//运力投放
			String intendedDpt = demand.getIntendedDpt();
			String intendedPst = demand.getIntendedPst();
			String intendedArrv = demand.getIntendedArrv();
			String baseSiteName = "待定";
			//基地
			if("1".equals(demand.getDemandtype())&&!TextUtil.isEmpty(demand.getDpt())){
				demand.setDptState("0");//0机场- 1-区域 2-城市（基地）
				//通过机场三字码获取城市对象
				CityCoordinate baseSite = cityCoordinateMapper.selectByAirIcao(dpt);
				//设置城市三字码
				demand.setDptCt(baseSite.getCityIcao());
				baseSiteName = baseSite.getCityName();
			}
			//意向航线决定标题
			if(TextUtil.isEmpty(intendedDpt)&&TextUtil.isEmpty(intendedPst)&&TextUtil.isEmpty(intendedArrv)){
				title.append(baseSiteName);
			}else{
				if(!TextUtil.isEmpty(intendedDpt)){
					//机场三字码查询机场名称
					AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectByIcao(intendedDpt);
					//设置运力需求标题-起始点
					title.append(airportInfoNewThree.getAirlnCd());
				}else{
					//设置航线需求标题-起始点
					title.append("待定");
				}
				if(!TextUtil.isEmpty(demand.getIntendedPst())){
					//机场三字码查询机场名称
					AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectByIcao(intendedPst);
					//设置运力需求标题-经停点
					title.append("-"+airportInfoNewThree.getAirlnCd());
				}else{
					//设置航线需求标题-经停点
					title.append("-待定");
				}
				if(!TextUtil.isEmpty(demand.getIntendedArrv())){
					//机场三字码查询机场名称
					AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectByIcao(intendedArrv);
					//设置运力需求标题-起始点
					title.append("-"+airportInfoNewThree.getAirlnCd());
				}else{
					//设置航线需求标题-到达点
					title.append("-待定");
				}
			}
		}else if("2".equals(demand.getDemandtype())){//运营托管
			//机场用户三字码获取机场名称
			String airportName = airportInfoNewThreeMapper.selectAirportNameByIcao(UserContext.getUser().getAirlineretrievalcondition());
			title.append(airportName);
			title.append(demand.getFltNbr()==null?"":demand.getFltNbr());
		}
		demand.setTitle(title.toString());
		return demand;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)//事务回滚
	public boolean demandAdd(Demand demand) throws Exception{
		boolean result = false;
		if(demand==null){
			log.debug("demandAdd:demand is invalid");
			return result;
		}
		if(demand.getId()!=null){//重新发布需求-现在不处理，以后再说
			demand.setId(null);
		}
		if("1".equals(demand.getDemandtype())&&TextUtil.isEmpty(demand.getDpt())){//运力必须有一个基地
			return result;//需求必须有一个基地
		}
		if("0".equals(demand.getDemandtype())||"1".equals(demand.getDemandtype())){
			demand.setDemandstate("4");//航线或运力需求发布之后默认需求状态为未处理-4
		}
		if("1".equals(demand.getDemandtype())||"4".equals(demand.getDemandtype())){//运力投放
			Employee user = UserContext.getUser();
			//判断运力发布和运力委托是否添加默认值
			if(TextUtil.isEmpty(demand.getContact())){//联系人为空时默认当前发布用户
				demand.setContact(user.getUsername());
			}
			if(TextUtils.isEmpty(demand.getiHome())){//联系方式为空时默认当前用户手机号
				demand.setiHome(user.getPhone());
			}
			if(demand.getCapacitycompany()==null){//运力所属航司为空时默认当前用户关联航司（太美用户未定）
				AirCompenyInfoThree airCompeny = airCompenyInfoThreeMapper.selectAirCompenyByIata(user.getAirlineretrievalcondition());//当前为航司用户
				if(airCompeny!=null){
					demand.setCapacitycompany(airCompeny.getId());
				}
			}
			//判断是否有意向航线
			if(!TextUtil.isEmpty(demand.getIntendedDpt())||!TextUtil.isEmpty(demand.getIntendedPst())||!TextUtil.isEmpty(demand.getIntendedArrv())){
				demand.setIntendedairline("0");
			}else{
				demand.setIntendedairline("1");
			}
		}
		demand = editDemand(demand);
		//设置发布时间
		demand.setReleasetime(TextUtil.formatDate(new Date()));
		demand.setIntentionMoneyState("1");//意向金状态为未交
		int activeLines = demandMapper.insertSelective(demand);
		
		/**
		 * 生成相应系统消息
		 * 1、正常需求：通知需求方
		 * 2、委托托管：通知需求方、通知太美
		 * 3、子需求：通知太美（子需求只能太美发布，所以1、3合并）
		 */
		createSystemMessageForReleaseDemand(demand);
		
		
		if(activeLines>0){
			if("0".equals(demand.getIntendedairline())){
				IntendedAirline intendedAirline = new IntendedAirline();
				intendedAirline.setDemandId(demand.getId());
				intendedAirline.setDpt(demand.getIntendedDpt());
				intendedAirline.setPst(demand.getIntendedPst());
				intendedAirline.setArrv(demand.getIntendedArrv());
				activeLines = intendedAirlineMapper.insertSelective(intendedAirline);
				if(activeLines>0){
					result = true;
				}
			}else{
				result = true;
			}
		}
		return result;
	}

	/** 
	 * 生成相应系统消息
	 * 1、正常需求：通知需求方
	 * 2、委托托管：通知需求方、通知太美
	 * 3、子需求：通知太美（子需求只能太美发布，所以1、3合并）
	 */
	private boolean createSystemMessageForReleaseDemand(Demand demand){
		boolean result=false;
		//根据发布人id，发布时间，需求类型查出需求id
		demand.setId(demandMapper.selectDemandIdToSystemMessage(demand));
		Chat chat=new Chat();
		//需求id
		chat.setDemandId(demand.getId());
		//设置时间
		chat.setDate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		//消息类型//0:普通聊天内容,1修改提示,2系统消息
		chat.setTextType("2");//系统消息
		//状态：0 已读  1未读
		chat.setState("1");
		//设置消息title
		chat.setTitle("发布成功");
		//需求种类共5种（0:航线需求、1:运力投放、2:运营托管、3:航线委托、4:运力委托）
		String demandTypeStr=null;
		if ("0".equals(demand.getDemandtype())) {
			demandTypeStr="航线需求";
		}else if ("1".equals(demand.getDemandtype())) {
			demandTypeStr="运力投放";
		}else if ("2".equals(demand.getDemandtype())) {
			demandTypeStr="托管";
		}else if ("3".equals(demand.getDemandtype())) {
			demandTypeStr="委托";
		}else if ("4".equals(demand.getDemandtype())) {
			demandTypeStr="委托";
		}
		//判断需求类型:航线和运力需求通知需求发布者
		if ("0".equals(demand.getDemandtype())||"1".equals(demand.getDemandtype())) {
			//接收人为需求发布者
			chat.setToNameId(demand.getEmployeeId());
			//消息内容：XX需求发布成功，请点击查看详情
			chat.setText(demand.getTitle()+demandTypeStr+"发布成功");
			int activelines=chatMapper.insertChatSelective(chat);//插入给需求发布者的消息
			if (activelines==1) {
				result=true;
			}
		}else {
			//委托或者托管时发通知给需求发布方和太美
			//1）、给发布方的消息
			chat.setToNameId(demand.getEmployeeId());//接收人为需求发布者
			chat.setText(demand.getTitle()+demandTypeStr+"发布成功");//时间-XX委托发布成功,平台受理中，
			int activelines=chatMapper.insertChatSelective(chat);//插入给需求发布者的消息
			if (activelines==1) {
				result=true;
			}else {
				result=false;
			}
			//2）、给太美的消息（暂定给所有太美账号发）
			chat.setText("收到"+demand.getTitle()+demandTypeStr+"，请及时处理");
			List<Employee> employees=employeeMapper.selectEmployeesByRole("2");
			if (employees.size()>0) {
				for(Employee e:employees){
					chat.setToNameId(e.getId());
					int activeline=chatMapper.insertChatSelective(chat);//插入给太美的消息
					if (activeline==1) {
						result=true;
					}else {
						result=false;
					}
				}
			}
		}
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)//事务回滚
	public boolean demandUpdate(Demand demand) throws Exception{
		boolean result = false;
		if(demand==null){
			log.debug("demandUpdate:demand is invalid");
			return result;
		}
		try {
			//根据需求状态为4时，填写关闭原因
			int activeLines = demandMapper.updateByPrimaryKeySelective(demand);
			if(activeLines>0){
				//查看需求是否有响应意向
				int count = responseMapper.selectResponseCountByDemandId(demand.getId());
				if(count>0){
					count = responseMapper.updateResponsesByDemandId(demand.getId());
				}
				result = true;
			}
		} catch (Exception e) {
			log.error("demandUpdate:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)//事务回滚
	public boolean demandDel(Long demandId) throws Exception{
		boolean result = false;
		if(demandId==null||demandId==0){
			log.debug("demandDel:demandId is invalid");
			return result;
		}
		//查询有无意向航线
		int count = intendedAirlineMapper.selectCountByDemandId(demandId);
		if(count>0){
			//先删除意向航线
			int delLines = intendedAirlineMapper.deleteByDemandId(demandId);
			if(delLines==0)
				return result;
		}
		int activeLines = demandMapper.deleteByPrimaryKey(demandId);
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	@Cacheable(value = "demandFind")
	public Demand demandFind(Long demandId){
		Demand demand = null;
		if(demandId==null||demandId==0){
			log.debug("demandFind:demandId is invalid");
			return demand;
		}
		try {
			demand = formatDemand(demandMapper.selectByPrimaryKey(demandId));
			if(demand!=null&&"0".equals(demand.getIntendedairline())){
				List<IntendedAirline> intendedAirlines = intendedAirlineMapper.selectByDemandId(demandId);
				demand.setIntendedAirlines(intendedAirlines);
			}
			if(("0".equals(demand.getDemandtype())||"1".equals(demand.getDemandtype()))){//航线需求和运力投放
				if("2".equals(demand.getPublicway())&&!TextUtils.isEmpty(demand.getDirectionalgoal())){//定向航司
					List<AirCompenyInfoThree> list = airCompenyInfoThreeMapper.selectAirCompenysForDemand(demand.getDirectionalgoal());
					if(list!=null&&list.size()>0){
						demand.setDirections(list);
					}
				}else if("3".equals(demand.getPublicway())&&!TextUtils.isEmpty(demand.getDirectionalgoal())){//定向机场
					List<AirportInfoNewThree> list = airportInfoNewThreeMapper.selectAirportsForDemand(demand.getDirectionalgoal());
					if(list!=null&&list.size()>0){
						demand.setDirections(list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("There is something wrong here,please check your sql");
			return null;
		}
		return demand;
	}

	@Cacheable(value = "getTrusteeshipDemandList")
	public PageBean<Demand> getTrusteeshipDemandList(
			DemandSearch demandSearch) {
		PageBean<Demand> pageBeanList = null;
		if(demandSearch==null){
			log.debug("getEntrustAndTrusteeshipDemandList:demandSearch is an invalid parameter");
			return pageBeanList;
		}
		try {
			int totalCount = demandMapper.selectCountTrusteeshipDemand(demandSearch);
			if(totalCount>0&&totalCount>demandSearch.getStartIndex()){
				List<Demand> list = TextUtil.formatDemand(demandMapper.selectTrusteeshipDemandList(demandSearch));
				pageBeanList = new PageBean<Demand>(totalCount, list, demandSearch.getPageNo());//每页显示条数
			}else{//没有记录
				pageBeanList = new PageBean<Demand>(totalCount, null, demandSearch.getPageNo());//每页显示条数
			}
		} catch (Exception e) {
			log.error("There is something wrong here,please check it out");
			e.printStackTrace();
			return pageBeanList;
		}
		return pageBeanList;
	}

	@Override
	public List<Long> SonDemandsByParentDemandId(Long demandId) {
		List<Long> list=null;
		if (demandId==0||demandId==null) {
			log.debug("SonDemandsByParentDemandId:demandId is null");
			return list;
		}
		try {
			list=demandMapper.selectSonDemandsByParentDemandId(demandId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SonDemandsByParentDemandId:There is something wrong here,please check your sql");
			return null;
		}
		
		return list;
	}
	
	@Cacheable(value = "findCommissionedAndCustodyDemandList")
	public PageBean<CommissionedAndCustody> findCommissionedAndCustodyDemandList(
			CommissionedAndCustody commissionedAndCustody) {
		PageBean<CommissionedAndCustody> pageBeanList=null;
		if(commissionedAndCustody==null){
			log.debug("getCommissionedAndCustodyDemandList:demandSearch is an invalid parameter");
			return pageBeanList;
		}
		try {
			int totalCount = demandMapper.findCountCommissionedAndCustodyDemand(commissionedAndCustody);
			if(totalCount>0&&totalCount>commissionedAndCustody.getStartIndex()){
				List<CommissionedAndCustody> list = demandMapper.findCommissionedAndCustody(commissionedAndCustody);
				for(CommissionedAndCustody commissionedAndCustody2:list){
					//编辑发布时间格式
					commissionedAndCustody2.setReleaseTime(DateUtil.date2Str(
							DateUtil.StringToDate(commissionedAndCustody2.getReleaseTime()),
							"MM.dd.yyyy"));
					//查询未读消息数
					commissionedAndCustody2.setUnreadNum(chatMapper.getNoReadChatRecordCountForTameiForWeituoAndTuoguan(UserContext.getUser().getId(),commissionedAndCustody2.getId()));
				}
				pageBeanList = new PageBean<CommissionedAndCustody>(totalCount, list, commissionedAndCustody.getPageNo());//每页显示条数
			}else{//没有记录
				pageBeanList = new PageBean<CommissionedAndCustody>(totalCount, null, commissionedAndCustody.getPageNo());//每页显示条数
			}
		} catch (Exception e) {
			log.error("There is something wrong here,please check it out");
			e.printStackTrace();
			return pageBeanList;
		}
		return pageBeanList;
	}
	@SuppressWarnings("rawtypes")
	@Cacheable(value = "getTheReleaseDemandOfMine")
	public PageBean<Demand> getTheReleaseDemandOfMine(String demandType,
			String demandprogress, Long employeeId, int page,int pageSize, int orderType) {
		PageBean<Demand> pbList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("MM.dd.yyyy");
		if(page==0||employeeId==null||employeeId==0){
			log.debug("getResponseByEmployee:page or employeeId is an invalid parameter");
			return pbList;
		}
		try {
			Employee emp = UserContext.getUser();
			int unReadMessageTotalCount = chatMapper.selectUnReadMessageCountForMyReleaseDemandList(emp.getId());
			if("0".equals(emp.getRole())){//航司
				if(pageSize>0){
					int startIndex = pageSize*(page-1);
					int totalCount = demandMapper.selectCountTheReleasedDemandOfMine(demandType,demandprogress,employeeId);
					if(totalCount>startIndex){
						List<Demand> list = demandMapper.selectTheReleasedDemandOfMine(demandType,demandprogress,employeeId,orderType,startIndex,pageSize);
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							Demand demand = list.get(i);
							demand.setReleasetime(format.format(sdf.parse(demand.getReleasetime())));
							//航线委托进度状态处理
							if("3".equals(demand.getDemandprogress())){
								if("需求发布".equals(demand.getDemandprogress())||"意向征集".equals(demand.getDemandprogress())
										||"订单确认".equals(demand.getDemandprogress())||"订单完成".equals(demand.getDemandprogress())
										||"佣金支付".equals(demand.getDemandprogress())){
									demand.setDemandprogress("处理中");
								}
							}
							//判断用户是否签订协议
							if("0".equals(emp.getWhethersign())){//签订协议
								//判断哪些状态的需求可以聊天
								String demandProgress = demand.getDemandprogress();
								if(demandProgress.indexOf("关闭")==-1&&!"交易完成".equals(demandProgress)){//交易完成和已关闭状态的需求不能聊天
									//获取当前需求未读消息总数
									int count = chatMapper.getUnreadMesseageCountByEmployeeAndDemandId(employeeId, demand.getId());
									//获取该需求的所有已缴纳意向金或签约的意向用户
									List<Employee> responseEmployees = employeeMapper.selectEmployeesByDemandId(employeeId, demand.getId());
									demand.setResponseEmployees(responseEmployees);
									demand.setUnreadMessageCount(count);
								}
							}
							list.set(i, demand);
						}
						if(list!=null&&list.size()>0){
							pbList = new PageBean<Demand>(totalCount, list, pageSize);
							pbList.setUnReadMessageTotalCount(unReadMessageTotalCount);
						}
					}
				}else{
					int startIndex = PageBean.pageSize*(page-1);
					int totalCount = demandMapper.selectCountTheReleasedDemandOfMine(demandType,demandprogress,employeeId);
					if(totalCount>startIndex){
						List<Demand> list = demandMapper.selectTheReleasedDemandOfMine(demandType,demandprogress,employeeId,orderType,startIndex,PageBean.pageSize);
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							Demand demand = list.get(i);
							demand.setReleasetime(format.format(sdf.parse(demand.getReleasetime())));
							//航线委托进度状态处理
							if("3".equals(demand.getDemandprogress())){
								if("需求发布".equals(demand.getDemandprogress())||"意向征集".equals(demand.getDemandprogress())
										||"订单确认".equals(demand.getDemandprogress())||"订单完成".equals(demand.getDemandprogress())
										||"佣金支付".equals(demand.getDemandprogress())){
									demand.setDemandprogress("处理中");
								}
							}
							//判断哪些状态的需求可以聊天
							String demandProgress = demand.getDemandprogress();
							if("0".equals(emp.getWhethersign())){//签订协议
								if(demandProgress.indexOf("关闭")==-1&&!"交易完成".equals(demandProgress)){//交易完成和已关闭状态的需求不能聊天
									//获取当前需求未读消息总数
									int count = chatMapper.getUnreadMesseageCountByEmployeeAndDemandId(employeeId, demand.getId());
									//获取该需求的所有已缴纳意向金或签约的意向用户
									List<Employee> responseEmployees = employeeMapper.selectEmployeesByDemandId(employeeId, demand.getId());
									demand.setResponseEmployees(responseEmployees);
									demand.setUnreadMessageCount(count);
								}
							}
							list.set(i, demand);
						}
						if(list!=null&&list.size()>0){
							pbList = new PageBean<Demand>(totalCount, list);
							pbList.setUnReadMessageTotalCount(unReadMessageTotalCount);
						}
					}
				}
			}else/* if("1".equals(emp.getRole()))*/{//机场和太美用户
				if(pageSize>0){
					int startIndex = pageSize*(page-1);
					int totalCount = demandMapper.selectCountTheReleasedDemandOfMine(demandType,demandprogress,employeeId);
					if(totalCount>startIndex){
						List<Demand> list = demandMapper.selectTheReleasedDemandOfMine(demandType,demandprogress,employeeId,orderType,startIndex,pageSize);
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							Demand demand = list.get(i);
							demand.setReleasetime(format.format(sdf.parse(demand.getReleasetime())));
							//航线委托进度状态处理
							if("3".equals(demand.getDemandprogress())){
								if("需求发布".equals(demand.getDemandprogress())||"意向征集".equals(demand.getDemandprogress())
										||"订单确认".equals(demand.getDemandprogress())||"订单完成".equals(demand.getDemandprogress())
										||"佣金支付".equals(demand.getDemandprogress())){
									demand.setDemandprogress("处理中");
								}
							}
							//获取列表中的未读聊天信息
							//判断用户是否意向金
							if("0".equals(demand.getIntentionMoneyState())){//签订意向金
								//判断哪些状态的需求可以聊天
								String demandProgress = demand.getDemandprogress();
								if(demandProgress.indexOf("关闭")==-1&&!"交易完成".equals(demandProgress)){//交易完成和已关闭状态的需求不能聊天
									//获取当前需求未读消息总数
									int count = chatMapper.getUnreadMesseageCountByEmployeeAndDemandId(employeeId, demand.getId());
									//获取该需求的所有已缴纳意向金或签约的意向用户
									List<Employee> responseEmployees = employeeMapper.selectEmployeesByDemandId(employeeId, demand.getId());
									demand.setResponseEmployees(responseEmployees);
									demand.setUnreadMessageCount(count);
								}
							}
							list.set(i, demand);
						}
						if(list!=null&&list.size()>0){
							pbList = new PageBean<Demand>(totalCount, list, pageSize);
							pbList.setUnReadMessageTotalCount(unReadMessageTotalCount);
						}
					}
				}else{
					int startIndex = PageBean.pageSize*(page-1);
					int totalCount = demandMapper.selectCountTheReleasedDemandOfMine(demandType,demandprogress,employeeId);
					if(totalCount>startIndex){
						List<Demand> list = demandMapper.selectTheReleasedDemandOfMine(demandType,demandprogress,employeeId,orderType,startIndex,PageBean.pageSize);
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							Demand demand = list.get(i);
							demand.setReleasetime(format.format(sdf.parse(demand.getReleasetime())));
							//航线委托进度状态处理
							if("3".equals(demand.getDemandprogress())){
								if("需求发布".equals(demand.getDemandprogress())||"意向征集".equals(demand.getDemandprogress())
										||"订单确认".equals(demand.getDemandprogress())||"订单完成".equals(demand.getDemandprogress())
										||"佣金支付".equals(demand.getDemandprogress())){
									demand.setDemandprogress("处理中");
								}
							}
							//获取列表中的未读聊天信息
							//判断用户是否意向金
							if("0".equals(demand.getIntentionMoneyState())){//签订意向金
								//判断哪些状态的需求可以聊天
								String demandProgress = demand.getDemandprogress();
								if(demandProgress.indexOf("关闭")==-1&&!"交易完成".equals(demandProgress)){//交易完成和已关闭状态的需求不能聊天
									//获取当前需求未读消息总数
									int count = chatMapper.getUnreadMesseageCountByEmployeeAndDemandId(employeeId, demand.getId());
									//获取该需求的所有已缴纳意向金或签约的意向用户
									List<Employee> responseEmployees = employeeMapper.selectEmployeesByDemandId(employeeId, demand.getId());
									demand.setResponseEmployees(responseEmployees);
									demand.setUnreadMessageCount(count);
								}
							}
							list.set(i, demand);
						}
						if(list!=null&&list.size()>0){
							pbList = new PageBean<Demand>(totalCount, list);
							pbList.setUnReadMessageTotalCount(unReadMessageTotalCount);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("database has something wrong happened,please checked the code carefully");
			e.printStackTrace();
			return pbList;
		}
		return pbList;
	}

	@Cacheable(value = "getDemandOfReviewList")
	public PageBean<Demand> getDemandOfReviewList(String demandType,
			String demandState, int page, int pageSize, int orderType) {
		PageBean<Demand> pbList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("MM.dd.yyyy");
		if(page==0){
			log.debug("getResponseByEmployee:page or employeeId is an invalid parameter");
			return pbList;
		}
		try {
			int totalCount = demandMapper.selectCountDemandOfReviewList(demandType,demandState);
			if(totalCount==0){//当没有对应的审核数据时，返回null
				pbList = new PageBean<Demand>(totalCount, null, pageSize==0?PageBean.pageSize:pageSize);
				return pbList;
			}
			if(pageSize>=0){
				int startIndex = pageSize*(page-1);
				if(totalCount>startIndex){
					List<Demand> list = demandMapper.selectDemandOfReviewList(demandType,demandState,orderType,startIndex,pageSize);
					if(list!=null&&list.size()>0){
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							list.get(i).setReleasetime(format.format(sdf.parse(list.get(i).getReleasetime())));
						}
						pbList = new PageBean<Demand>(totalCount, list, pageSize);
					}
				}
			}else if(pageSize==0){
				int startIndex = PageBean.pageSize*(page-1);
				if(totalCount>startIndex){
					List<Demand> list = demandMapper.selectDemandOfReviewList(demandType,demandState,orderType,startIndex,PageBean.pageSize);
					if(list!=null&&list.size()>0){
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							list.get(i).setReleasetime(format.format(sdf.parse(list.get(i).getReleasetime())));
						}
						pbList = new PageBean<Demand>(totalCount, list);
					}
				}
			}
		} catch (Exception e) {
			log.error("database has something wrong happened,please checked the code carefully");
			e.printStackTrace();
			return pbList;
		}
		return pbList;
	}

	@Override
	public boolean checkDemand(Long demandId, String demandState, String rek) {
		boolean result = false;
		if(demandId==null||demandId==0||TextUtil.isEmpty(demandState)||("5".equals(demandState)&&TextUtil.isEmpty(rek))){
			log.debug("The demandId or demandState or rek is an invalid parameter");
			return result;
		}
		try {
			Demand oldDemand = demandMapper.selectByPrimaryKey(demandId);
			if(oldDemand==null){
				return result;
			}
			Demand demand = new Demand();
			demand.setId(demandId);
			demand.setDemandstate(demandState);
			if("5".equals(demandState)){
				if("0".equals(oldDemand.getDemandtype())||"1".equals(oldDemand.getDemandtype())){
					demand.setDemandprogress("3");//航线与运力需求进度为关闭
					demand.setCloseReason("审核不通过");
				}else{
					demand.setDemandprogress("10");//改变委托和托管进度状态为已拒绝					
				}
			}
			demand.setRek(rek);
			int activeLines = demandMapper.updateByPrimaryKeySelective(demand);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error("There is error in SQL");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public boolean changeDemandIntentionStateByDemandId(Long demandId,
			String intentionMoneyState) {
		boolean result=false;
		if (demandId==null||demandId==0||TextUtil.isEmpty(intentionMoneyState)) {
			log.debug("changeDemandIntentionStateByDemandId:demandId is invalid");
			return result;
		}
		try {
			int activelines=demandMapper.changeDemandIntentionStateByDemandId(demandId, intentionMoneyState);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("There is error in SQL");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}

	@Cacheable(value = "findMyOrderList")
	public PageBean<MyOrder> findMyOrderList(MyOrder myOrder) {
		PageBean<MyOrder> pageBeanList=null;
		if(myOrder==null){
			log.debug("findMyOrderList:myOrder is an invalid parameter");
			return pageBeanList;
		}
		try {
			int totalCount = demandMapper.findCountMyOrder(myOrder);
			if(totalCount>0&&totalCount>myOrder.getStartIndex()){
				List<MyOrder> list = demandMapper.findMyOrderList(myOrder);
				//demand表状态转response表状态
				for(MyOrder orderFormat:list){
					if ("4".equals(orderFormat.getDemandProgress())) {
						orderFormat.setDemandProgress("6");
					}else if ("5".equals(orderFormat.getDemandProgress())) {
						orderFormat.setDemandProgress("7");
					}else if ("6".equals(orderFormat.getDemandProgress())) {
						orderFormat.setDemandProgress("5");
					}else if ("3".equals(orderFormat.getDemandProgress())) {
						orderFormat.setDemandProgress("3");
					}
					//航司没有佣金支付(用来显示)航司的佣金支付显示为交易完成
					if ("7".equals(orderFormat.getDemandProgress())&&"0".equals(UserContext.getUser().getRole().trim())) {
						orderFormat.setDemandProgress("5");
					}
					//编辑发布时间格式
					orderFormat.setReleaseTime(DateUtil.date2Str(
							DateUtil.StringToDate(orderFormat.getReleaseTime()),
							"MM.dd.yyyy"));
					//未读消息条数
					if(orderFormat.getDemandEmployeeId().longValue()==UserContext.getUser().getId().longValue()){//需求发布者登录
						orderFormat.setUnreadNum(chatMapper.getNoReadChatRecordCountForTameiForWeituoAndTuoguan(orderFormat.getDemandEmployeeId(), orderFormat.getId()));
					}else if (orderFormat.getResponseEmployeeId()!=null&&orderFormat.getResponseEmployeeId().longValue()==UserContext.getUser().getId().longValue()) {//响应者登录
						orderFormat.setUnreadNum(chatMapper.getNoReadChatRecordCountForTameiForWeituoAndTuoguan(orderFormat.getResponseEmployeeId(), orderFormat.getId()));
					}else {//只可能是太美了在访问了
						orderFormat.setUnreadNum(chatMapper.getNoReadChatRecordCountForTameiForWeituoAndTuoguan(UserContext.getUser().getId(), orderFormat.getId()));
					}
				}
				
				pageBeanList = new PageBean<MyOrder>(totalCount, list, myOrder.getPageNo());//每页显示条数
			}else{//没有记录
				pageBeanList = new PageBean<MyOrder>(totalCount, null, myOrder.getPageNo());//每页显示条数
			}
		} catch (Exception e) {
			log.error("There is something wrong here,please check it out");
			e.printStackTrace();
			return pageBeanList;
		}
		return pageBeanList;
	}

	@Cacheable(value = "selectSonDemandsByParentId")
	public List<Demand> selectSonDemandsByParentId(Long id) {
		List<Demand> list=new ArrayList<Demand>();
		if (id==0||id==null) {
			log.debug("sonDemandsByParentId:id is null");
			return list;
		}
		
		try {
			list=demandMapper.selectSonDemandsByParentId(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("there is something error");
			return null;
		}
		return list;
	}

	@Cacheable(value = "getDemandsByCurrentCheckedAirportForEmployee")
	public PageBean<Demand> getDemandsByCurrentCheckedAirportForEmployee(
			DemandSearch demandSearch) {
		PageBean<Demand> pbList = null;
		if(demandSearch.getPage()<=0){
			log.debug("DemandServiceImpl:getDemandsForCurrentCheckedAirport have invalid parameters");
			return pbList;
		}
		try {
			int totalCount = demandMapper.getDemandsByCurrentCheckedAirportForEmployeeCount(demandSearch);
			if(totalCount>0&&totalCount>demandSearch.getStartIndex()){
				//查询并对获取的列表进行数据编辑
				List<Demand> list = TextUtil.formatDemand(demandMapper.getDemandsByCurrentCheckedAirportForEmployee(demandSearch));
				pbList = new PageBean<Demand>(totalCount, list);
			}else{
				pbList = new PageBean<Demand>(totalCount, null);
			}
		} catch (Exception e) {
			log.error("getDemandsForCurrentCheckedAirport has something wrong here", e);
			e.printStackTrace();
			return pbList;
		}
		return pbList;
	}

	@Cacheable(value = "getOthersDemandListIndex")
	public Map<String, Object> getOthersDemandListIndex(String iata, int page) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(page<=0){
			log.debug("getOthersDemandListIndex:iata or page is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		Employee emp = UserContext.getUser();
		DemandSearch demandSearch = new DemandSearch();
		// 1-查询当前用户在该机场发布的需求（运力或航线）0-查询当前机场的所有需求
		demandSearch.setEmployeeId(UserContext.getUser().getId());
		demandSearch.setItia(iata);
		// 判断登陆账号的角色类型：0-航司（显示机场发布的航线需求） 1-机场（显示航司发布的运力需求）
		// 2-太美（显示其他机场和航司发布的航线与运力需求）
		if ("0".equals(emp.getRole())) {// 航司
			demandSearch.setDemandType("0");
		} else if ("1".equals(emp.getRole())) {// 机场
			demandSearch.setDemandType("1");
		} else {// 太美
			demandSearch.setDemandType("3");//自定义，用于太美查询航线需求和运力需求
		}
		demandSearch.setPageNo(PageBean.pageSize);
		demandSearch.setStartIndex(PageBean.pageSize * (page - 1));
		PageBean<Demand> pbList = null;
		try {
			int totalCount = demandMapper.getOthersDemandListIndexCount(demandSearch);
			if(totalCount>0){
				//查询并对获取的列表进行数据编辑
				List<Demand> list = TextUtil.formatDemand(demandMapper.getOthersDemandListIndex(demandSearch));
				pbList = new PageBean<Demand>(totalCount, list);
				map.put("opResult", "0");
				map.put("list",pbList);
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There are errors in code");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@Cacheable(value = "getAllDemandsNew")
	public List<DemandDto> getAllDemandsNew(Employee dto) {
		//获取当前登录用户对应航司或者机场的id
				if(dto.getRole().equals("0")){//航司
					dto.setDirectId(demandMapper.getAircompenyId(dto));
				}else if(dto.getRole().equals("1")){//机场
					dto.setDirectId(demandMapper.getAirportId(dto));
				}
				return demandMapper.getAllDemandsCount(dto);
	}
}
