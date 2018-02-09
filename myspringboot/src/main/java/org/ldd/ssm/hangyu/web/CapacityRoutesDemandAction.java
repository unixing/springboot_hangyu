package org.ldd.ssm.hangyu.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Collect;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.EmployeeDemand;
import org.ldd.ssm.hangyu.domain.Response;
import org.ldd.ssm.hangyu.service.CollectService;
import org.ldd.ssm.hangyu.service.DemandService;
import org.ldd.ssm.hangyu.service.EmployeeDemandService;
import org.ldd.ssm.hangyu.service.EmployeeService;
import org.ldd.ssm.hangyu.service.ResponseService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.ListUtils;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CapacityRoutesDemandAction {

	@Autowired
	private EmployeeDemandService employeeDemandService;

	@Autowired
	private ResponseService responseService;

	@Autowired
	private DemandService demandService;

	@Autowired
	private CollectService collectService;

	@Autowired
	private EmployeeService employeeService;

	Logger log = Logger.getLogger(CapacityRoutesDemandAction.class);

	@RequestMapping("/capacityRoutesDemandDetailFindById")
	@ResponseBody
	public Map<String, Object> capacityDemandFindById(Long demandId) {

		Map<String, Object> map = new HashMap<String, Object>();

		Employee emp = UserContext.getUser();
		System.out.println("/capacityRoutesDemandDetailFindById:角色："
				+ emp.getRole() + " " + emp.getRoleStr());

		/*
		 * 记录用户查看了哪些需求，若是第一次查看则入EmployeeDemand表
		 */
		EmployeeDemand employeeDemand = new EmployeeDemand();
		employeeDemand.setDemandId(demandId);
		employeeDemand.setEmployeeId(emp.getId());
		// employeeDemand表无记录，插入
		if (employeeDemandService.selectByEmployeeDemand(employeeDemand) == 0) {
			try {
				employeeDemandService.add(employeeDemand);
			} catch (Exception e) {
				log.error("There is something wrong in CapacityDemandFindById to InsertEmployeeDeman");
				e.printStackTrace();
				map.put("opResult", "-1");// 需求查看表出错
				return map;
			}

		}

		// 返回参数是否已收藏
		Collect collect = new Collect();
		collect.setDemandId(demandId);
		collect.setEmployeeId(UserContext.getUser().getId());
		boolean isAlreadyCollect = collectService.isAlreadyCollect(collect);
		map.put("isAlreadyCollect", isAlreadyCollect);
		/*
		 * 运力需求处理逻辑：
		 * 
		 * 判断登录者身份信息（机场or航司）
		 * 
		 * 1、机场登录，判断是否为自己发布（yes or no） yes: 需显示内容： 1、需求详情（UI右上）
		 * demand表按demandId查询
		 * 
		 * 2、收到的意向（UI右下）-->是否已交意向金（yes or no） yes:收到意向时间、意向方、收到意向数量（list）
		 * 
		 * no:只显示响应数量
		 * 
		 * no:需显示内容： 1、需求详情（UI右上） -->是否已交意向金（yes or no） yes:需求详情完整信息
		 * 
		 * no:需求详情信息剔除：运力归属航司、联系人、联系方式、备注信息
		 * 
		 * 2、我发出的方案（UI右下） response表按demandId和employeeId查最新记录进行显示 响应参数说明：
		 * isSelf:是否为自己发布(true or false)
		 * 
		 * isIntentionMoney:是否缴纳了意向金（true or false）
		 * 
		 * intentionCount：该需求的意向数量
		 * 
		 * data:需求详情
		 * 
		 * receiveIntention:收到的意向
		 * 
		 * opResult:本次查询的状态
		 */
		try {
			if (demandId == null || demandId == 0) {
				map.put("opResult", "-2");// 缺少参数:运力需求ID
				return map;
			}
			// 需求详情
			Demand demand = demandService.demandFind(demandId);
			// 该需求是否为登录者发布
			boolean isSelf = false;
			if (demand != null) {
				isSelf = demand.getEmployeeId().longValue()==emp.getId().longValue();
			} else {
				map.put("opResult", "-3");// 未查到该demandId下的运力需求信息
				return map;
			}
			// 判断是否为委托或者托管,是的话查出来委托方，company名字
			String demandType = demand.getDemandtype();
			if ("2".equals(demandType) || "3".equals(demandType)
					|| "4".equals(demandType)) {
				// 查委托或者托管方
				String CpyNm = employeeService.selectByprimaryKey(demand.getEmployeeId()).getCompanyName();
				if (CpyNm != null) {
					map.put("CpyNm", CpyNm);
				}
			}

			// 需求发布者是否向该需求交了意向金
			boolean isIntentionMoneyForThisDemand = "0".equals(demand
					.getIntentionMoneyState());
			// 当前用户是否向系统交了意向金
			boolean isIntentionMoneyForSystem = "0".equals(UserContext
					.getUser().getIntentionMoney());
			// 意向数量
			int intentionCount = responseService
					.selectByPrimaryKeyForCount(demandId);
			// 该用户对本需求发出的意向的意向,非本人发的需求才可能存在
			Response receiveIntention = null;
			if (!isSelf) {
				Response pamramResponse = new Response();
				pamramResponse.setEmployeeId(emp.getId());
				pamramResponse.setDemandId(demandId);
				receiveIntention = responseService
						.selectByDemandIdAndEmployeeId(pamramResponse);
			}
			// 该需求收到的意向列表，自己发布的需求才可能存在该参数
			List<Response> responses = null;
			// 角色判断
			if ("1".equals(emp.getRole())) {// 机场角色调用
				// 自己发布的需求，已向该需求交意向金
				if (isSelf) {
					map.put("isSelf", isSelf);
					map.put("isIntentionMoneyForThisDemand",
							isIntentionMoneyForThisDemand);
					map.put("intentionCount", intentionCount);
					map.put("data", demand);
					// 自己发布，已交意向金，可查看意向列表
					Response re = new Response();
					re.setDemandId(demandId);
					responses = responseService.selectByResponse(re);

					if (responses.size() > 0) {
						for (Response r : responses) {
							// 添加意向方名字
							r.setIntentionCompanyName(employeeService.selectByprimaryKey(r.getEmployeeId()).getCompanyName());
							// 修改日期显示格式
							r.setResponsedate(DateUtil.date2Str(
									DateUtil.StringToDate(r.getResponsedate()),
									"MM.dd.yyyy"));
						}
					}
					map.put("responseList", responses);
					map.put("opResult", "0");
					return map;

					// 自己发布未向该需求交意向金
				} /*else if (isSelf && !isIntentionMoneyForThisDemand) {
					map.put("isSelf", isSelf);
					map.put("isIntentionMoneyForThisDemand",
							isIntentionMoneyForThisDemand);
					map.put("intentionCount", intentionCount);
					List<Demand> demands = new ArrayList<Demand>();
					demands.add(demand);
					Demand tranDemand = TextUtil.formatDemand(demands).get(0);
					map.put("data", tranDemand);
					map.put("opResult", "0");
					return map;
					// 他人发布，已向系统交意向金--机场查看别人发的需求是否交意向金指的是是否向该需求交意向金也就相当于是否有正常响应--20180125同张帅一起与谢强确认
				} */else if (!isSelf && receiveIntention!=null) {
					map.put("isSelf", isSelf);
					map.put("isIntentionMoneyForSystem",
							isIntentionMoneyForSystem);
					map.put("intentionCount", intentionCount);
					List<Demand> demands = new ArrayList<Demand>();
					demands.add(demand);
					Demand tranDemand = TextUtil.formatDemand(demands).get(0);
					//查看他人发布的需求联系人联系方式都不可见--20180108
					//查看别人发的需求只要提交过意向就可见联系人和联系方式--20180124与郑述文确认
					map.put("data", tranDemand);
					map.put("receiveIntention", receiveIntention);
					map.put("opResult", "0");
					return map;
					// 他人发布未向系统交意向金
				} else if (!isSelf && (receiveIntention==null)) {
					// 未交意向金查看别人发布的运力需求，发送简略信息，排除运力归属航司capacityCompany、联系人contact、联系方式iHome、备注信息remark
					//修改为机场查看别人发的需求只要提交过意向就可见联系人联系方式运力归属等--20180130与谢强郑述文确认
					map.put("isSelf", isSelf);
					map.put("isIntentionMoneyForSystem",
							isIntentionMoneyForSystem);
					map.put("intentionCount", intentionCount);
					demand.setCapacityCompany(null);
					demand.setCapacitycompany(null);
					demand.setContact(null);
					demand.setiHome(null);
					demand.setRemark(null);
					List<Demand> demands = new ArrayList<Demand>();
					demands.add(demand);
					Demand tranDemand = TextUtil.formatDemand(demands).get(0);
					map.put("data", tranDemand);
					map.put("receiveIntention", receiveIntention);
					map.put("opResult", "0");
					return map;
				}
			}
			// 航司登录
			else if ("0".equals(emp.getRole())) {
				// 1、自己发布的需求（不能更改，可下架，进入订单完成流程的需求不可下架，下架的需求保存所有意向和私聊...），传所有数据到前台
				if (isSelf) {
					map.put("data", demand);// 需求详情
					map.put("intentionCount", intentionCount);// 显示意向用户数量
					// 自己发布，收到的意向列表,航司无需缴纳意向金即可查看意向的详情--UI说明文档

					Response re = new Response();

					// 判断是否签约
					boolean isSign = false;
					// 已签约，可以看意向列表
					if ("0".equals(UserContext.getUser().getWhethersign())) {
						isSign=true;
						re.setDemandId(demandId);
						responses = responseService.selectByResponse(re);
						if (responses.size() > 0) {
							for (Response r : responses) {
								// 添加意向方名字
								r.setIntentionCompanyName(employeeService.selectByprimaryKey(r.getEmployeeId()).getCompanyName());
								// 修改日期显示格式
								r.setResponsedate(DateUtil.date2Str(DateUtil
										.StringToDate(r.getResponsedate()),
										"MM.dd.yyyy"));
							}
						}
						map.put("responseList", responses);// 生成响应list
					}
					map.put("isSign", isSign);
					map.put("isSelf", isSelf);
					map.put("opResult", "0");
					return map;
				} else {
					// 2、非自己发布，随便看，只要不响应就不用交钱
					//查看他人发布的需求联系人联系方式都不可见--20180108
					//查看别人发的需求只要提交过意向就可见联系人和联系方式--20180124与郑述文确认
					if(receiveIntention==null){
					demand.setContact(null);
					demand.setiHome(null);
					}
					map.put("data", demand);// 需求详情
					map.put("intentionCount", intentionCount);// 显示意向用户数量
					map.put("receiveIntention", receiveIntention);// 生成响应list
					map.put("isSelf", isSelf);
					map.put("opResult", "0");
					return map;

				}

			}// 太美登录
			else if ("2".equals(emp.getRole())) {

				map.put("data", demand);// 需求详情
				map.put("intentionCount", intentionCount);// 显示意向用户数量
				map.put("isIntentionMoneyForSystem",
						isIntentionMoneyForSystem);
				map.put("isIntentionMoneyForThisDemand",
						isIntentionMoneyForThisDemand);
				// 自己发布，收到的意向列表
				Response re = new Response();
				re.setDemandId(demandId);
				responses = responseService.selectByResponse(re);
				//如果太美发的航线需求未交意向金也不返回意向列表
				//if (!(isSelf&&!isIntentionMoneyForThisDemand&&"0".equals(demand.getDemandtype()))) {
					if (responses.size() > 0) {
						for (Response r : responses) {
							// 添加意向方名字
							r.setIntentionCompanyName(employeeService.selectByprimaryKey(r.getEmployeeId()).getCompanyName());
							// 修改日期显示格式
							r.setResponsedate(DateUtil.date2Str(
									DateUtil.StringToDate(r.getResponsedate()),
									"MM.dd.yyyy"));
						}
					}
					map.put("responseList", responses);// 生成响应list
				//}
				map.put("isSelf", isSelf);
				map.put("intentionCount", intentionCount);// 显示意向用户数量
				map.put("receiveIntention", receiveIntention);// 非自己发布时可能存在的自己发出的意向
				map.put("isSelf", isSelf);
				map.put("opResult", "0");
				return map;

			}
		} catch (Exception e) {
			log.error("There is something wrong in CapacityDemandFindById");
			e.printStackTrace();
			map.put("opResult", "-4");// 系统发生异常
			return map;
		}
		return map;
	}
	
	@RequestMapping("/responseListOrder")
	@ResponseBody
	public Map<String, Object> responseListOrder(Long demandId,Integer orderType) {//0降序1升序
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(demandId==null||demandId.longValue()==0
				||orderType==null){
			map.put("opResult", "3");
			return map;
		}
		
		List<Response> responses = null;
		Response re = new Response();
		re.setDemandId(demandId);
		responses = responseService.selectByResponse(re);
		if (responses.size() > 0) {
			//排序//ListUtils.sort(incomeRecords, false, "date");// true升序，false降序//1:16>24号//0降序1升序
			if(orderType==0){
				ListUtils.sort(responses, false, "responsedate");
			}else if (orderType==1){
				ListUtils.sort(responses, true, "responsedate");
			}
			for (Response r : responses) {
				// 添加意向方名字
				r.setIntentionCompanyName(employeeService.selectByprimaryKey(r.getEmployeeId()).getCompanyName());
				// 修改日期显示格式
				r.setResponsedate(DateUtil.date2Str(
						DateUtil.StringToDate(r.getResponsedate()),
						"MM.dd.yyyy"));
			}
		}
		map.put("opResult", "0");
		map.put("responseList", responses);
		return map;
	}
	
}
