package org.ldd.ssm.hangyu.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.PublicOpinion;
import org.ldd.ssm.hangyu.mapper.AirCompenyInfoThreeMapper;
import org.ldd.ssm.hangyu.service.PublicOpinionService;
import org.ldd.ssm.hangyu.utils.PublicOpinionUtil;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PublicOpinionServiceImpl implements PublicOpinionService {
	@Autowired
	private AirCompenyInfoThreeMapper airCompenyInfoThreeMapper;
	Logger log = Logger.getLogger(PublicOpinionServiceImpl.class);
	@Override
	public Map<String, Object> getPublicOpinionList(String code, Integer type,Integer codeType,Integer page,Integer pageSize) {
		if(1==type){//首页进入,没有传入相应的三字码
//			//判断登陆角色
//			Employee emp = UserContext.getUser();
//			if("0".equals(emp.getRole())){//航司
//				code = airCompenyInfoThreeMapper.selectCompenyIcaoByIata(emp.getAirlineretrievalcondition());
//				codeType = 0;
//			}else if("1".equals(emp.getRole())){//机场
//				code = emp.getAirlineretrievalcondition();
//				codeType = 1;
//			}else{//太美
//				code = "太美航空";
//				codeType = 2;
//			}
			codeType = 2;//首页进入默认通过关键字查询，且关键字为空
		}
		return getPublicOpinionList(code,codeType,page,pageSize);
	}
	
	public Map<String,Object> getPublicOpinionList(String code,Integer codeType,Integer page,Integer pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		if(page==null||page<1){
			log.debug("getPublicOpinionList:the code is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			if(codeType==0){//code为航司三字码
				List<PublicOpinion> result = PublicOpinionUtil.getPublicOpinionForAirLine(code,page,pageSize);
				if(result==null||result.size()==0){
					map.put("opResult", "1");
				}else{
					map.put("opResult", "0");
					map.put("obj", result);
				}
				
			}else if(codeType==1){//code为机场三字码
				List<PublicOpinion> result = PublicOpinionUtil.getPublicOpinionForAirport(code,page,pageSize);
				if(result==null||result.size()==0){
					map.put("opResult", "1");
				}else{
					map.put("opResult", "0");
					map.put("obj", result);
				}
			}else{//关键字查询
				List<PublicOpinion> result = PublicOpinionUtil.getPublicOpinionForCity(code,page,pageSize);
				if(result==null||result.size()==0){
					map.put("opResult", "1");
				}else{
					map.put("opResult", "0");
					map.put("obj", result);
				}
			}
		} catch (Exception e) {
			log.error("There are errors in the code");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
