package org.ldd.ssm.hangyu.service.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.AirportInfoNewThree;
import org.ldd.ssm.hangyu.domain.CityCoordinate;
import org.ldd.ssm.hangyu.domain.Response;
import org.ldd.ssm.hangyu.mapper.AirportInfoNewThreeMapper;
import org.ldd.ssm.hangyu.mapper.CityCoordinateMapper;
import org.ldd.ssm.hangyu.mapper.ResponseMapper;
import org.ldd.ssm.hangyu.service.ResponseCopyService;
import org.ldd.ssm.hangyu.service.ResponseService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService{

	@Autowired
	private ResponseMapper responseMapper;
	
	@Autowired
	private ResponseCopyService copy;
	
	@Autowired
	private CityCoordinateMapper cityCoordinateMapper;
	
	@Autowired
	private AirportInfoNewThreeMapper airportInfoNewThreeMapper;
	
	Logger log=Logger.getLogger(ResponseServiceImpl.class);
	
	@Override
	public int selectByPrimaryKeyForCount(Long demandId) {
		int result=-1;
		if (demandId==null||demandId.longValue()==0) {
			log.debug("selectByPrimaryKeyForCount:demandId is null");
			return result;
		}
		try {
			result=responseMapper.selectByPrimaryKeyForCount(demandId);
		} catch (Exception e) {
			log.error("selectByPrimaryKeyForCount:there is something wrong here");
			return result;
		}
		return result;
	}
	
	@Override
	public Response selectByDemandIdAndEmployeeId(Response response) {
		Response resultResponce=null;
		if(response.getEmployeeId()==null||response.getEmployeeId()==0||response.getDemandId()==null||response.getDemandId()==0){
			log.debug("selectByDemandIdAndEmployeeId:response.getEmployeeId() or response.getDemandId() is invalid");
			return resultResponce;
		}
		try {
			resultResponce=formatResponse(responseMapper.selectByDemandIdAndEmployeeId(response));
		} catch (Exception e) {
			log.error("selectByDemandIdAndEmployeeId:there is something wrong here");
			e.printStackTrace();
			return resultResponce;
		}
		return resultResponce;
	}

	@Override
	public boolean addResponseSelective(Response response) {
		boolean result=false;
		if(TextUtil.isEmpty(response.getIhome())||TextUtil.isEmpty(response.getContact())){
			log.debug("addResponseSelective:iHome or contact is null");
			return result;
		}
		try {
			int activeLines=responseMapper.insertSelective(editResponseWhenAddOrUpdateResponse(response));
			//留痕
			copy.addResponseCopy(ResponseCopyServiceImpl.formatRsponseCopy(response));
			if (activeLines>0) {
				result=true;
			}
		} catch (Exception e) {
			log.error("addResponseSelective:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public Response selectByPrimaryKey(Long responseId) {
		Response response=null;
		if(responseId==null||responseId==0){
			log.debug("selectByPrimaryKey:responseId is invalid");
			return response;
		}
		try {
			response=formatResponse(responseMapper.selectByPrimaryKey(responseId));
		} catch (Exception e) {
			log.error("selectByPrimaryKey:there is something here.");
			e.printStackTrace();
			return response;
		}
		return response;
	}

	@Override
	public List<Response> selectByResponse(Response response) {
		List<Response> responses=null;
		if(response==null){
			log.debug("selectByResponse:response is invalid");
			return responses;
		}
		try {
			responses=responseMapper.selectByResponse(response);
			if (responses!=null&&responses.size()>0) {
				for(Response re:responses){
					formatResponse(re);
				}
			}
		} catch (Exception e) {
			log.error("selectByResponse:there is something here.");
			e.printStackTrace();
			return responses;
		}
		
		return responses;
	}

	@Override
	public boolean deleteResponseByPrimaryKey(Long responseId) {
		boolean result=false;
		if(responseId==null||responseId==0){
			log.debug("deleteResponseByPrimaryKey：responseId is null");
			return result;
		}
		try {
			int activeLines=responseMapper.deleteByPrimaryKey(responseId);
			if(activeLines>0){
				result=true;
			}
		} catch (Exception e) {
			log.error("deleteResponseByPrimaryKey:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}

	@Override
	public boolean addResponse(Response response) {
		boolean result=false;
		if(response==null){
			log.debug("addResponse：response is null");
			return result;
		}
//		//调用意向响应编辑方法
//		response = editResponse(response);
		//设置响应时间
		response.setResponsedate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		//默认更新时间
		response.setUpdatedate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		try {
			int activeLines=responseMapper.insert(editResponseWhenAddOrUpdateResponse(response));
			//留痕
			copy.addResponseCopy(ResponseCopyServiceImpl.formatRsponseCopy(response));
			if(activeLines>0){
				result=true;
			}
		} catch (Exception e) {
			log.error("addResponse:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}

	@Override
	public boolean updateResponseSelective(Response response) {
		boolean result=false;
		if(response==null){
			log.debug("updateResponseSelective：response is null");
			return result;
		}
		response.setUpdatedate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		if("responseAgain".equals(response.getResponsedate())){
			response.setResponsedate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		}else {
		response.setResponsedate(null);//响应时间不可改变，只能添加意向时由后台添加
		}
		try {
//			//调用意向响应编辑方法
//			response = editResponse(response);
			int activeLines=responseMapper.updateByPrimaryKeySelective(editResponseWhenAddOrUpdateResponse(response));
			//留痕
			copy.addResponseCopy(ResponseCopyServiceImpl.formatRsponseCopy(response));
			if(activeLines>0){
				result=true;
			}
		} catch (Exception e) {
			log.error("updateResponseSelective:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}

	@Override
	public boolean updateResponse(Response response) {
		boolean result=false;
		if(response==null){
			log.debug("updateResponse：response is null");
			return result;
		}
		response.setUpdatedate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		response.setResponsedate(null);//响应时间不可改变，只能添加意向时由后台添加
		try {
			int activeLines=responseMapper.updateByPrimaryKey(editResponseWhenAddOrUpdateResponse(response));
			//留痕
			copy.addResponseCopy(ResponseCopyServiceImpl.formatRsponseCopy(response));
			if(activeLines>0){
				result=true;
			}
		} catch (Exception e) {
			log.error("updateResponse:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}

	@Override
	public boolean updateResponseNewInfoStatus(String newinfo, Long id) {
		boolean result=false;
		if(id==null||id==0||TextUtil.isEmpty(newinfo)){
			log.debug("updateResponseNewInfoStatus:newinfo or id is null");
			return result;
		}
		try {
			Response paramResponse=new Response();
			paramResponse.setId(id);
			paramResponse.setNewinfo(newinfo);
			int activelines=responseMapper.updateResponseNewInfoStatus(paramResponse);
			//留痕
			copy.addResponseCopy(ResponseCopyServiceImpl.formatRsponseCopy(paramResponse));
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("updateResponseNewInfoStatus:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}
//	//意向响应对象编辑方法（标题）
//		public Response editResponse(Response response){
//			//数据编辑
//			//注意：响应的添加和修改都不确定标题（修改时有可能变更了某一航点就导致标题变更），所以标题需要更新
//			StringBuffer title = new StringBuffer("");
//			String dpt = response.getDpt();//始发地
//			if("0".equals(response.getDemandtype())||"3".equals(response.getDemandtype())){//航线需求
//				String pst = response.getPst();//经停地
//				String arrv = response.getArrv();//目的地
//				if(!TextUtil.isEmpty(dpt)){
//					String dptState = response.getDptState();//0:始发地1:区域2:运力所在基地 存放的值dpt
//					if("0".equals(dptState)){//始发点类型为机场
//						//由机场三字码查询城市对象
//						CityCoordinate cityCoordinate = cityCoordinateMapper.selectByAirIcao(dpt);
//						//设置城市三字码
//						response.setDptCt(cityCoordinate.getCityIcao());
//						//设置航线需求标题-起始点
//						title.append(cityCoordinate.getCityName());
//					}else if("2".equals(dptState)){//始发点类型为城市
//						response.setDptCt(dptState);
//						//通过城市三字码查询城市对象
//						CityCoordinate cityCoordinate = cityCoordinateMapper.selectByCityIcao(dpt);
//						//设置航线需求标题-起始点
//						title.append(cityCoordinate.getCityName());
//					}else{
//						title.append(dpt);
//					}
//				}else{
//					//设置航线需求标题-起始点
//					title.append("待定");
//				}
//				if(!TextUtil.isEmpty(pst)){
//					String pstState = response.getPstState();
//					if("0".equals(pstState)){//经停点类型为机场
//						//由机场三字码查询城市对象
//						CityCoordinate cityCoordinate = cityCoordinateMapper.selectByAirIcao(pst);
//						//设置城市三字码
//						response.setPstCt(cityCoordinate.getCityIcao());
//						//设置航线需求标题-经停点
//						title.append("-"+cityCoordinate.getCityName());
//					}else if("2".equals(pstState)){//经停类型为城市
//						response.setDptCt(pstState);
//						//通过城市三字码查询城市对象
//						CityCoordinate cityCoordinate = cityCoordinateMapper.selectByCityIcao(pst);
//						//设置航线需求标题-经停点
//						title.append("-"+cityCoordinate.getCityName());
//					}else{
//						title.append("-"+pst);
//					}
//				}else{
//					//设置航线需求标题-经停点
//					title.append("-待定");
//				}
//				if(!TextUtil.isEmpty(arrv)){
//					String arrvState = response.getArrvState();
//					if("0".equals(arrvState)){//到达点类型为机场
//						//由机场三字码查询城市对象
//						CityCoordinate cityCoordinate = cityCoordinateMapper.selectByAirIcao(arrv);
//						//设置城市三字码
//						response.setArrvCt(cityCoordinate.getCityIcao());
//						//设置航线需求标题-到达点
//						title.append("-"+cityCoordinate.getCityName());
//					}else if("2".equals(arrvState)){//到达点类型为城市
//						response.setDptCt(arrvState);
//						//通过城市三字码查询城市对象
//						CityCoordinate cityCoordinate = cityCoordinateMapper.selectByCityIcao(arrv);
//						//设置航线需求标题-到达点
//						title.append("-"+cityCoordinate.getCityName());
//					}else{
//						title.append("-"+arrv);
//					}
//				}else{
//					//设置航线需求标题-到达点
//					title.append("-待定");
//				}
//				
//			}else if("1".equals(response.getDemandtype())||"4".equals(response.getDemandtype())){//运力投放
//				String intendedDpt = response.getIntendedDpt();
//				String intendedPst = response.getIntendedPst();
//				String intendedArrv = response.getIntendedArrv();
//				//基地
//				response.setDptState("0");//0机场- 1-区域 2-城市（基地）
//				//通过机场三字码获取城市对象
//				CityCoordinate baseSite = cityCoordinateMapper.selectByAirIcao(dpt);
//				//设置城市三字码
//				response.setDptCt(baseSite.getCityIcao());
//				//意向航线决定标题
//				if(TextUtil.isEmpty(intendedDpt)&&TextUtil.isEmpty(intendedPst)&&TextUtil.isEmpty(intendedArrv)){
//					title.append(baseSite.getCityName());
//				}else{
//					if(!TextUtil.isEmpty(intendedDpt)){
//						//机场三字码查询机场名称
//						AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectByIcao(intendedDpt);
//						//设置运力需求标题-起始点
//						title.append(airportInfoNewThree.getAirlnCd());
//					}else{
//						//设置航线需求标题-起始点
//						title.append("待定");
//					}
//					if(!TextUtil.isEmpty(response.getIntendedPst())){
//						//机场三字码查询机场名称
//						AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectByIcao(intendedPst);
//						//设置运力需求标题-经停点
//						title.append("-"+airportInfoNewThree.getAirlnCd());
//					}else{
//						//设置航线需求标题-经停点
//						title.append("-待定");
//					}
//					if(!TextUtil.isEmpty(response.getIntendedArrv())){
//						//机场三字码查询机场名称
//						AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectByIcao(intendedArrv);
//						//设置运力需求标题-起始点
//						title.append("-"+airportInfoNewThree.getAirlnCd());
//					}else{
//						//设置航线需求标题-到达点
//						title.append("-待定");
//					}
//				}
//			}else if("2".equals(response.getDemandtype())){//运营托管
//				//机场用户三字码获取机场名称
//				String airportName = airportInfoNewThreeMapper.selectAirportNameByIcao(UserContext.getUser().getAirlineretrievalcondition());
//				title.append(airportName);
//				//航班号
//				title.append(response.getFltnbr()== null?"":response.getFltnbr());
//			}
//			response.setTitle(title.toString());
//			return response;
//		}
	
	
	

	@Override
	public String selectIntentionCompanyName(Long employeeId) {
		String intentionCompanyName="" ;
		if(employeeId==null||employeeId==0){
			log.debug("selectIntentionCompanyName:employeeId is null");
			return null;
		}
		try {
			intentionCompanyName=responseMapper.selectIntentionCompanyName(employeeId);
		} catch (Exception e) {
			log.error("selectIntentionCompanyName:there is something wrong here");
			e.printStackTrace();
			return intentionCompanyName;
		}
		return intentionCompanyName;
	}

	@Override
	public PageBean<Response> getResponseByEmployee(String demandType,String responseProgress,Long employeeId, int orderType,int page,
			int pageSize) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("MM.dd.yyyy");
		PageBean<Response> pbList = null;
		if(page==0||employeeId==null||employeeId==0){
			log.debug("getResponseByEmployee:page or employeeId is an invalid parameter");
			return pbList;
		}
		try {
			if(pageSize>0){
				int startIndex = pageSize*(page-1);
				int totalCount = responseMapper.selectReponseCountByEmployee(demandType,responseProgress,employeeId);
				if(totalCount>startIndex){
					List<Response> list = responseMapper.selectReponseByEmployee(demandType,responseProgress,employeeId,orderType,startIndex,pageSize);
					if(list!=null&&list.size()>0){
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							list.get(i).setReleasetime(format.format(sdf.parse(list.get(i).getReleasetime())));
						}
						pbList = new PageBean<Response>(totalCount, list, pageSize);
					}
				}
			}else{
				int startIndex = PageBean.pageSize*(page-1);
				int totalCount = responseMapper.selectReponseCountByEmployee(demandType,responseProgress,employeeId);
				if(totalCount>startIndex){
					List<Response> list = responseMapper.selectReponseByEmployee(demandType,responseProgress,employeeId,orderType,startIndex,PageBean.pageSize);
					if(list!=null&&list.size()>0){
						//格式化发布日期
						for(int i=0;i<list.size();i++){
							list.get(i).setReleasetime(format.format(sdf.parse(list.get(i).getReleasetime())));
						}
						pbList = new PageBean<Response>(totalCount, list);
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
	public Response getResponseDetails(Long responseId) {
		Response response = null;
		if(responseId == null || responseId ==0){
			log.debug("getResponseDetails:the responseId is an invalid parameter");
			return response;
		}
		try {
			response = formatResponse(responseMapper.selectResponseDetails(responseId));
		} catch (Exception e) {
			log.error("There are errors in Sql");
			e.printStackTrace();
			return response;
		}
		return response;
	}

	@Override
	public List<Response> selectByDemandId(Long demandId) {
		List<Response> responses=null;
		if(demandId==null||"0".equals(demandId)){
			log.debug("selectByDemandId:demandId is invalid");
			return responses;
		}
		try {
			responses=responseMapper.selectByDemandId(demandId);
			if (responses!=null&&responses.size()>0) {
				for(Response re:responses){
					formatResponse(re);
				}
			}
		} catch (Exception e) {
			log.error("selectByDemandId:there is something here.");
			e.printStackTrace();
			return responses;
		}
		
		return responses;
	}
	
	public Response formatResponse(Response response){
		if (response==null) {
			return response;
		}
		
		//判断意向状态
		if ("0".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("意向征集");
		}else if ("1".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("订单确认");
		}else if ("2".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("已撤回");
		}else if ("3".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("需求关闭");
		}else if ("4".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("落选状态");
		}else if ("5".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("交易完成");
		}else if ("6".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("订单完成");
		}else if ("7".equals(response.getResponseProgress())) {
			response.setResponseProgressStr("佣金支付");
		}
		
		//判断需求类型(0:航线需求、1:运力投放、2:运营托管、3:航线委托、4:运力委托)
		if("0".equals(response.getDemandtype())){
			response.setDemandtypeStr("航线需求");
		}else if("1".equals(response.getDemandtype())){
			response.setDemandtypeStr("运力投放");
		}else if("2".equals(response.getDemandtype())){
			response.setDemandtypeStr("运营托管");
		}else if("3".equals(response.getDemandtype())){
			response.setDemandtypeStr("航线委托");
		}else if("4".equals(response.getDemandtype())){
			response.setDemandtypeStr("运力委托");
		}
		
		//始发地是否接收临近机场（0:接收,1:不接收）
		if("0".equals(response.getDptAcceptnearairport())){
			response.setDptAcceptnearairportStr("接收");
		}else{
			response.setDptAcceptnearairportStr("不接收");
		}
		
		//始发地时刻资源（0：显示时刻，1：待协调 2：时刻充足）
		if("0".equals(response.getDptTimeresources())){
			response.setDptTimeresourcesStr(response.getDptTime());
		}else if("1".equals(response.getDptTimeresources())){
			response.setDptTimeresourcesStr("待协调");
		}else if("2".equals(response.getDptTimeresources())){
			response.setDptTimeresourcesStr("时刻充足");
		}
		
		//经停地是否接收临近机场(0:接收,1:不接受)
		if("0".equals(response.getPstAcceptnearairport())){
			response.setPstAcceptnearairportStr("接收");
		}else if("1".equals(response.getPstAcceptnearairport())){
			response.setPstAcceptnearairportStr("不接收");
		}
		//经停地时刻资源（0：显示时刻，1：待协调 2：时刻充足）
		if("0".equals(response.getPstTimeresources())){
			response.setPstTimeresourcesStr(response.getPstTime());
		}else if("1".equals(response.getPstTimeresources())){
			response.setPstTimeresourcesStr("待协调");
		}else if("2".equals(response.getPstTimeresources())){
			response.setPstTimeresourcesStr("时刻充足");
		}
		//到达地是否接收临近机场(0:接收,1:不接受)
		if("0".equals(response.getArrvAcceptnearairport())){
			response.setArrvAcceptnearairportStr("接收");
		}else if("1".equals(response.getArrvAcceptnearairport())){
			response.setArrvAcceptnearairportStr("不接收");
		}
		//到达地时刻资源（0：显示时刻，1：待协调 2：时刻充足）
		if("0".equals(response.getArrvTimeresources())){
			response.setArrvTimeresourcesStr(response.getArrvTime());
		}else if("1".equals(response.getArrvTimeresources())){
			response.setArrvTimeresourcesStr("待协调");
		}else if("2".equals(response.getArrvTimeresources())){
			response.setArrvTimeresourcesStr("时刻充足");
		}
		
		//补贴有种状态：有补贴0:有补贴-定补、1:有补贴-保底,2:有补贴-人头补,3:有补贴-其他,4:待议5:无补贴。
		if("0".equals(response.getSubsidypolicy())){
			response.setSubsidypolicyStr("定补");
		}else if("1".equals(response.getSubsidypolicy())){
			response.setSubsidypolicyStr("保底");
		}else if("2".equals(response.getSubsidypolicy())){
			response.setSubsidypolicyStr("人头补");
		}else if("3".equals(response.getSubsidypolicy())){
			response.setSubsidypolicyStr("其他");
		}else if("4".equals(response.getSubsidypolicy())){
			response.setSubsidypolicyStr("待议");
		}else if("5".equals(response.getSubsidypolicy())){
			response.setSubsidypolicyStr("无补贴");
		}
		
		//是否接受调度
		if("0".equals(response.getScheduling())){
			response.setSchedulingStr("接受");
			if(!TextUtil.isEmpty(response.getSchedulineport())){
				List<AirportInfoNewThree> airportInfoNewThrees = new ArrayList<AirportInfoNewThree>();
				String[] iatas = response.getSchedulineport().split(",");
				for(String iata:iatas){
					AirportInfoNewThree airportInfoNewThree = airportInfoNewThreeMapper.selectById(Long.parseLong(iata.trim()));
					if(airportInfoNewThree!=null){
						airportInfoNewThrees.add(airportInfoNewThree);
					}	
				}
				if(airportInfoNewThrees.size()>0){
					response.setAirportForSchedulines(airportInfoNewThrees);
				}
			}
		}else if("1".equals(response.getScheduling())){
			response.setSchedulingStr("不接受");
		}
		
		//公开方式(0:对所有人公开,1:对认证用户公开,2:定向航司,3:定向机场), 3和4定位目标在下一个字段
		if("0".equals(response.getPublicway())){
			response.setPublicwayStr("所有人");
		}else if("1".equals(response.getPublicway())){
			response.setPublicwayStr("认证用户");
		}else if("2".equals(response.getPublicway())){
			response.setPublicwayStr("定向航司");
		}else if("3".equals(response.getPublicway())){
			response.setPublicwayStr("定向机场");
		}
		
		//同意返回的机场或区域名字"0":机场；"1":区域；"2"：城市（暂无城市）20180104
		if ("1".equals(response.getDptState().trim())) {
			response.setDptNm(response.getDpt());
		}
		
		if ("1".equals(response.getPstState().trim())) {
			response.setPstNm(response.getPst());
		}
		
		if ("1".equals(response.getArrvState().trim())) {
			response.setArrvNm(response.getArrv());
		}
		
		//编辑是否通航显示字符串
		if ("0".equals(response.getWhethernavigation())) {
			response.setWhethernavigationStr("已通航");
		}else if ("1".equals(response.getWhethernavigation())) {
			response.setWhethernavigationStr("未通航");
		}
		if(TextUtil.isEmpty(response.getDptNm())){
			response.setDptNm(response.getDpt());
		}else if (TextUtil.isEmpty(response.getPstNm())) {
			response.setPstNm(response.getPst());
		}else if (TextUtil.isEmpty(response.getArrvNm())) {
			response.setArrvNm(response.getArrv());
		}
		return response;
	}

	//设置reponse表城市三字码dpt_Ct等字段
	public Response editResponseWhenAddOrUpdateResponse(Response response){
		if ("0".equals(response.getDptState())&&!TextUtil.isEmpty(response.getDpt())) {//始发点类型为机场
			//由机场三字码查询城市对象
			CityCoordinate cityCoordinate=cityCoordinateMapper.selectByAirIcao(response.getDpt().trim());
			//设置城市三字码
			response.setDptCt(cityCoordinate.getCityIcao());
		}
		if ("0".equals(response.getPstState())&&!TextUtil.isEmpty(response.getPst())) {//经停点类型为机场
			//由机场三字码查询城市对象
			CityCoordinate cityCoordinate=cityCoordinateMapper.selectByAirIcao(response.getPst().trim());
			//设置城市三字码
			response.setPstCt(cityCoordinate.getCityIcao());
		}
		if ("0".equals(response.getArrvState())&&!TextUtil.isEmpty(response.getArrv())) {//经停点类型为机场
			//由机场三字码查询城市对象
			CityCoordinate cityCoordinate=cityCoordinateMapper.selectByAirIcao(response.getArrv().trim());
			//设置城市三字码
			response.setArrvCt(cityCoordinate.getCityIcao());
		}
		return response;
	}
	@Override
	public int selectResponseIdCountByEmployeeIdAndDemandId(
			Long employeeId, Long demandId) {
		int result=-1;
		if (demandId==null||demandId.longValue()==0
				||employeeId==null||employeeId.longValue()==0) {
			log.debug("selectResponseIdCountByEmployeeIdAndDemandId:demandId is null");
			return result;
		}
		try {
			result=responseMapper.selectResponseIdCountByEmployeeIdAndDemandId(employeeId,demandId);
		} catch (Exception e) {
			log.error("selectResponseIdCountByEmployeeIdAndDemandId:there is something wrong here");
			return result;
		}
		return result;
	}
	
}
