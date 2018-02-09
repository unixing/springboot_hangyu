package org.ldd.ssm.hangyu.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.domain.Collect;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.domain.Response;
import org.ldd.ssm.hangyu.query.CommissionedAndCustody;
import org.ldd.ssm.hangyu.query.DemandDto;
import org.ldd.ssm.hangyu.query.MyOrder;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.service.CollectService;
import org.ldd.ssm.hangyu.service.DemandService;
import org.ldd.ssm.hangyu.service.EmployeeService;
import org.ldd.ssm.hangyu.service.IChatService;
import org.ldd.ssm.hangyu.service.IndividualCapitalAccountService;
import org.ldd.ssm.hangyu.service.MoneyIncomeRecordService;
import org.ldd.ssm.hangyu.service.ResponseService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.ListUtils;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.ldd.ssm.hangyu.utils.RetBean;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemandAction {

	@Autowired
	private DemandService demandService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ResponseService responseService;

	@Autowired
	private IndividualCapitalAccountService individualCapitalAccountService;

	@Autowired
	private MoneyIncomeRecordService moneyIncomeRecordService;

	@Autowired
	private IChatService chatService;

	@Autowired
	private CollectService collectService;

	Logger log = Logger.getLogger(DemandAction.class);

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/getDemandsForCurrentEmployee")
	@ResponseBody
	public Map<String, Object> getDemandsForCurrentEmployee(int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 模拟session中的登陆信息
			Employee emp = UserContext.getUser();
			DemandSearch demandSearch = new DemandSearch();
			// 判断登陆账号的角色类型：0-航司（显示机场发布的航线需求） 1-机场（显示航司发布的运力需求）
			// 2-太美（显示其他机场和航司发布的航线与运力需求）
			if ("0".equals(emp.getRole())) {// 航司
				demandSearch.setDemandType("0");
			} else if ("1".equals(emp.getRole())) {// 机场
				demandSearch.setDemandType("1");
			} else {// 太美
				demandSearch.setDemandType("3");// 自定义，用于太美查询航线需求和运力需求
			}
			demandSearch.setEmployeeId(emp.getId());
			demandSearch.setPageNo(PageBean.pageSize);
			demandSearch.setStartIndex(PageBean.pageSize * (page - 1));
			PageBean<Demand> list = demandService
					.getDemandsForCurrentEmployee(demandSearch);
			if (list != null) {
				map.put("opResult", "0");
				map.put("list", list);
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error(
					"getDemandsForCurrentEmployee,there is something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}

	/**
	 * 
	 * @param itia
	 *            机场/城市三字码
	 * @param page
	 *            第几页
	 * @param type
	 *            查询需求范围发布类型
	 * @param itiaType
	 *            机场与城市三字码类型
	 * @return
	 */
	@RequestMapping("/getDemandsForCurrentCheckedCity")
	@ResponseBody
	public Map<String, Object> getDemandsForCurrentCheckedCity(String itia,
			int page, int type, int itiaType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (TextUtils.isEmpty(itia)) {
			log.debug("In getDemandsForCurrentCheckedAirport,itia is a invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = UserContext.getUser();
			DemandSearch demandSearch = new DemandSearch();
			// 1-查询当前用户在该机场发布的需求（运力或航线）0-查询当前机场的所有需求
			demandSearch.setEmployeeId(UserContext.getUser().getId());
			demandSearch.setItia(itia);
			demandSearch.setItiaType(itiaType + "");
			demandSearch.setType(type);
			if (type == 0) {// 查询所有的需求
				// 判断登陆账号的角色类型：0-航司（显示机场发布的航线需求） 1-机场（显示航司发布的运力需求）
				// 2-太美（显示其他机场和航司发布的航线与运力需求）
				if ("0".equals(emp.getRole())) {// 航司
					demandSearch.setDemandType("0");
				} else if ("1".equals(emp.getRole())) {// 机场
					demandSearch.setDemandType("1");
				} else {// 太美
					demandSearch.setDemandType("3");// 自定义，用于太美查询航线需求和运力需求
				}
			}
			demandSearch.setPageNo(PageBean.pageSize);
			demandSearch.setStartIndex(PageBean.pageSize * (page - 1));
			PageBean<Demand> list = demandService
					.getDemandsForCurrentCheckedCity(demandSearch);
			if (list != null) {
				map.put("opResult", "0");
				map.put("list", list);
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error(
					"getDemandsForCurrentCheckedAirport has something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	/**
	 * 获取地图上所有的需求点
	 * 
	 * @return
	 */
	@RequestMapping("/getAllDemands")
	@ResponseBody
	public Map<String, Object> getAllDemands() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<DemandDto> list = demandService.getAllDemands(UserContext
					.getUser());
			List<DemandDto> listNew = demandService.getAllDemandsNew(UserContext
					.getUser());
			if (null != list) {
				map.put("msg", "查询成功");
				map.put("data", list);
				map.put("dataNew", listNew);
			} else {
				map.put("msg", "查询结果为空");
				map.put("data", null);
				map.put("dataNew", listNew);
			}
		} catch (Exception e) {
			log.error("getAllDemands has something wrong here", e);
			e.printStackTrace();
			map.put("msg", "查询异常");
			return map;
		}
		return map;
	}

	@RequestMapping("/demandAdd")
	@ResponseBody
	public Map<String, Object> demandAdd(Demand demand) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demand == null) {
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = UserContext.getUser();
			demand.setEmployeeId(emp.getId());
			demand.setDemandstate("0");// 需求默认状态-正常
			// 0:需求发布、1:意向征集、2:订单确认、3:关闭（审核不通过、下架、过期）、4:订单完成、
			// 5:佣金支付、6:交易完成、7:待处理、8:已接受、9:处理中、10:已拒绝
			if ("1".equals(demand.getDemandtype())||"0".equals(demand.getDemandtype())) {// 航线和运力的初始状态-需求发布
				demand.setDemandprogress("0");
			} else {// 托管委托初始状态-待处理
				demand.setDemandprogress("7");
			}
			boolean result = demandService.demandAdd(demand);
			if (result) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/demandUpdate")
	@ResponseBody
	public Map<String, Object> demandUpdate(Demand demand) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demand == null) {
			map.put("opResult", "3");
			return map;
		}
		try {
			boolean result = demandService.demandUpdate(demand);
			if (result) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/demandDel")
	@ResponseBody
	public Map<String, Object> demandDel(Long demandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demandId == null || demandId == 0) {
			map.put("opResult", "3");
			return map;
		}
		try {
			boolean result = demandService.demandDel(demandId);
			if (result) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/demandFind")
	@ResponseBody
	public Map<String, Object> demandFind(Long demandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demandId == null || demandId == 0) {
			map.put("opResult", "3");
			return map;
		}
		try {
			Demand demand = demandService.demandFind(demandId);
			if (demand != null) {
				map.put("data", demand);
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	/**
	 * 获取托管列表
	 * 
	 * @param page
	 *            页码
	 * @return
	 */
	@RequestMapping("/getTrusteeshipDemandList")
	@ResponseBody
	public Map<String, Object> getTrusteeshipDemandList(int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page <= 0) {
			map.put("opResult", "3");
			return map;
		}
		try {
			DemandSearch demandSearch = new DemandSearch();
			demandSearch.setPage(page);
			demandSearch.setPageNo(5);
			demandSearch.setStartIndex(5 * (page - 1));
			PageBean<Demand> pgList = demandService
					.getTrusteeshipDemandList(demandSearch);
			if (pgList == null) {
				map.put("opResult", "1");
				return map;
			} else {
				map.put("opResult", "0");
				map.put("list", pgList);
				return map;
			}
		} catch (Exception e) {
			log.error("It`s some wrong happened here ,please check carefully");
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}

	@RequestMapping("/selectCommissionedAndCustodyDemandList")
	@ResponseBody
	public Map<String, Object> findCommissionedAndCustodyDemandList(
			CommissionedAndCustody commissionedAndCustody) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (commissionedAndCustody == null
				|| commissionedAndCustody.getPage() <= 0
				|| commissionedAndCustody.getPageNo() < 0) {
			map.put("opResult", "3");
			return map;
		}
		try {
			int page = commissionedAndCustody.getPage();
			int pageNo = commissionedAndCustody.getPageNo();
			commissionedAndCustody.setPage(page);
			commissionedAndCustody.setPageNo(pageNo);
			commissionedAndCustody.setStartIndex(pageNo * (page - 1));
			commissionedAndCustody.setEmployeeId(UserContext.getUser().getId());
			commissionedAndCustody.setRoleId(UserContext.getUser().getRole());
			if(commissionedAndCustody.getDemandProgress()!=null&&"9".equals(commissionedAndCustody.getDemandProgress().trim())){
				commissionedAndCustody.setDemandProgress("ABC");
			}
			PageBean<CommissionedAndCustody> pgList = demandService
					.findCommissionedAndCustodyDemandList(commissionedAndCustody);
			if (pgList == null) {
				map.put("opResult", "1");
				return map;
			} else {
				map.put("opResult", "0");
				map.put("list", pgList);
				return map;
			}
		} catch (Exception e) {
			log.error("It`s some wrong happened here ,please check carefully");
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}

	/**
	 * 查看委托/托管类型的需求详情 思路：太美角色：可查看可能存在的子需求列表 一般角色：仅查看该委托的详情
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/getCommissionedAndCustodyDemandDetails")
	@ResponseBody
	public Map<String, Object> getCommissionedAndCustodyDemandDetails(Long id,Integer orderType) {//0降序1升序
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null || id.longValue() == 0
				||orderType==null) {
			map.put("opResult", "3");// 位收到需求id
			return map;
		}
		try {
			// 委托详情
			Demand demand = demandService.demandFind(id);
			if (demand == null) {
				map.put("opResult", "1");// 未找到该委托
				return map;
			} else {
				map.put("opResult", "0");
				map.put("demandDetail", demand);
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

			// 子需求列表
			List<Demand> listSonDemands = new ArrayList<Demand>();
			List<Long> idList = demandService.SonDemandsByParentDemandId(id);
			if (idList.size() > 0) {
				Demand sonDemand = null;
				for (Long sonid : idList) {
					sonDemand = demandService.demandFind(sonid);
					if ("2".equals(UserContext.getUser().getRole())) {
						if (sonDemand != null) {
							listSonDemands.add(sonDemand);
						}
					} else {
						if (sonDemand != null) {
							Demand resultSonDemand = new Demand();
							resultSonDemand.setId(sonDemand.getId());
							resultSonDemand.setTitle(sonDemand.getTitle());
							resultSonDemand.setReleasetime(sonDemand
									.getReleasetime());
							resultSonDemand
									.setDemandId(sonDemand.getDemandId());
							resultSonDemand.setDemandstate(sonDemand
									.getDemandstate());
							resultSonDemand.setDemandStateStr(sonDemand
									.getDemandStateStr());
							resultSonDemand.setDemandprogress(sonDemand
									.getDemandprogress());
							resultSonDemand.setDemandprogressStr(sonDemand
									.getDemandprogressStr());
							listSonDemands.add(resultSonDemand);
						}
					}
				}
				
				//排序
				if(orderType==1){//升序
					ListUtils.sort(listSonDemands, true, "releasetime");
				}else {//降序
					ListUtils.sort(listSonDemands, false, "releasetime");
				}
				
			}
			map.put("listSonDemands", listSonDemands);

		} catch (Exception e) {
			log.error("It`s some wrong happened here ,please check carefully");
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}

	// 走此接口，父委托id:demand.demandId必须存在
	@RequestMapping("/sonDemandAdd")
	@ResponseBody
	public Map<String, Object> sonDemandAdd(Demand demand) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demand == null || demand.getDemandId() == 0
				|| demand.getDemandId() == null) {
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = UserContext.getUser();
			demand.setEmployeeId(emp.getId());
			demand.setDemandstate("0");// 需求默认状态-正常
			// 0:需求发布、1:意向征集、2:订单确认、3:关闭（审核不通过、下架、过期）、4:订单完成、
			// 5:佣金支付、6:交易完成、7:待处理、8:已接受、9:处理中、10:已拒绝
			if ("0".equals(demand.getDemandtype())
					|| "1".equals(demand.getDemandtype())) {// 航线和运力的初始状态-需求发布
				demand.setDemandprogress("0");// 初始需求进度
			} else {// 委托和托管初始状态-待处理
				demand.setDemandprogress("7");// 初始需求进度
			}
			boolean result = demandService.demandAdd(demand);
			/**
			 * 子需求发布成功之后,对父委托进行状态核验： 此时父委托的demandProgress则至少为状态"0",代表需求发布，
			 * 所以父id状态为"9"则将"9"改为"0" "9":处理中 "0"需求发布
			 */
			// 得到父委托对象
			Demand parentDemand = demandService
					.demandFind(demand.getDemandId());
			// 判断父委托状态
			if (parentDemand != null
					&& "9".equals(parentDemand.getDemandprogress())) {
				// 将状态改成"9"
				Demand paramDemand = new Demand();
				paramDemand.setId(parentDemand.getId());
				paramDemand.setDemandprogress("0");
				demandService.demandUpdate(paramDemand);
			}

			if (result) {

				// 子需求列表
				List<Demand> listSonDemands = null;
				List<Long> idList = demandService
						.SonDemandsByParentDemandId(parentDemand.getId());
				if (idList.size() > 0) {
					listSonDemands = new ArrayList<Demand>();
					Demand sonDemand = null;
					for (Long sonid : idList) {
						sonDemand = demandService.demandFind(sonid);

						if ("2".equals(UserContext.getUser().getRole())) {
							if (sonDemand != null) {
								listSonDemands.add(sonDemand);
							}
						} else {
							if (sonDemand != null) {
								Demand resultSonDemand = new Demand();
								resultSonDemand.setId(sonDemand.getId());
								resultSonDemand.setTitle(sonDemand.getTitle());
								resultSonDemand.setReleasetime(sonDemand
										.getReleasetime());
								resultSonDemand.setDemandId(sonDemand
										.getDemandId());
								resultSonDemand.setDemandstate(sonDemand
										.getDemandstate());
								resultSonDemand.setDemandStateStr(sonDemand
										.getDemandStateStr());
								resultSonDemand.setDemandprogress(sonDemand
										.getDemandprogress());
								resultSonDemand.setDemandprogressStr(sonDemand
										.getDemandprogressStr());
								listSonDemands.add(resultSonDemand);
							}
						}
					}
				}
				map.put("listSonDemands", listSonDemands);
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/getTheReleaseDemandOfMine")
	@ResponseBody
	public Map<String, Object> getTheReleaseDemandOfMine(String demandType,
			String demandprogress, int page, int orderType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page == 0) {
			log.debug("page is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = UserContext.getUser();
			PageBean<Demand> list = demandService
					.getTheReleaseDemandOfMine(demandType, demandprogress,
							emp.getId(), page, 5, orderType);
			if (list == null) {
				map.put("opResult", "1");
			} else {
				map.put("opResult", "0");
				map.put("list", list);
			}
		} catch (Exception e) {
			log.error("The is errors happened");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/getDemandOfReviewList")
	@ResponseBody
	public Map<String, Object> getDemandOfReviewList(String demandType,
			String demandState, int page, int orderType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page == 0) {
			log.debug("page is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			PageBean<Demand> list = demandService.getDemandOfReviewList(
					demandType, demandState, page, 5, orderType);
			if (list == null) {
				map.put("opResult", "1");
			} else {
				map.put("opResult", "0");
				map.put("list", list);
			}
		} catch (Exception e) {
			log.error("The is errors happened");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/checkDemand")
	@ResponseBody
	public Map<String, Object> checkDemand(Long demandId, String demandState,
			String rek) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demandId == null || demandId == 0 || TextUtil.isEmpty(demandState)
				|| ("1".equals(demandState) && TextUtil.isEmpty(rek))) {
			log.debug("demandId or demandState or rek is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		if ("0".equals(demandState)) {
			demandState = "6";// 数据库审核通过状态码
		} else {
			demandState = "5";// 数据库审核不通过状态码
		}
		try {
			boolean result = demandService.checkDemand(demandId, demandState,
					rek);

			// 审核拒绝时推送消息给需求发布方
			if ("5".equals(demandState)) {
				sendMessageToReleaseWhenCheck(demandId);
			}
			if (result) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There have errors");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	private boolean sendMessageToReleaseWhenCheck(Long demandId) {
		boolean result = false;
		// 为取得需求title查出需求对象
		Demand messageDemand = demandService.demandFind(demandId);
		// 消息内容时间-XX需求未通过平台审核
		Chat chat = new Chat();
		// 需求id
		chat.setDemandId(demandId);
		// 设置时间
		chat.setDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 消息类型//0:普通聊天内容,1修改提示,2系统消息
		chat.setTextType("2");// 系统消息
		// 状态：0 已读 1未读
		chat.setState("1");
		// 接收人id
		chat.setToNameId(messageDemand.getEmployeeId());
		// 消息内容
		chat.setText(messageDemand.getTitle()
				+ messageDemand.getDemandtypeStr() + "未通过平台审核");
		// 设置消息title
		chat.setTitle("需求状态更新");
		result = chatService.insertChatSelective(chat);
		return result;
	}

	@RequestMapping("/selectMyOrderList")
	@ResponseBody
	public Map<String, Object> findMyOrderList(MyOrder myOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (myOrder == null || myOrder.getPage() <= 0
				|| myOrder.getPageNo() < 0) {
			map.put("opResult", "3");
			return map;
		}
		try {
			int page = myOrder.getPage();
			int pageNo = myOrder.getPageNo();
			myOrder.setPage(page);
			myOrder.setPageNo(pageNo);
			myOrder.setStartIndex(pageNo * (page - 1));
			myOrder.setEmployeeId(UserContext.getUser().getId());
			myOrder.setRoleId(UserContext.getUser().getRole());

			// 判断是否为航司查看"交易完成状态"demandProgress=6"的订单
			if ("0".equals(myOrder.getRoleId())
					&& "5".equals(myOrder.getDemandProgress())) {
				myOrder.setHangsiFinish("0");
				myOrder.setDemandProgress("");
			} else {
				myOrder.setHangsiFinish("1");
			}
			
			if (myOrder.getDemandProgress()!=null&&!"".equals(myOrder.getDemandProgress())) {
				//response和demand状态转换
				String formatResponsProgressToDemandProgress = null;
				if ("6".equals(myOrder.getDemandProgress().trim())) {
					formatResponsProgressToDemandProgress="4";
				}else if ("7".equals(myOrder.getDemandProgress().trim())) {
					formatResponsProgressToDemandProgress="5";
				}else if ("5".equals(myOrder.getDemandProgress().trim())) {
					formatResponsProgressToDemandProgress="6";
				}else if ("3".equals(myOrder.getDemandProgress().trim())) {
					formatResponsProgressToDemandProgress="3";
				}
				myOrder.setDemandProgress(formatResponsProgressToDemandProgress);
			}

			PageBean<MyOrder> pgList = demandService.findMyOrderList(myOrder);
			if (pgList == null) {
				map.put("opResult", "1");
				return map;
			} else {
				map.put("opResult", "0");
				map.put("list", pgList);
				return map;
			}
		} catch (Exception e) {
			log.error("It`s some wrong happened here ,please check carefully");
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}

	/**
	 * 结束需求： 1、先将本条需求关联的所有意向修改为下架responseProgress:"3"
	 * 2、将本条需求修改为下架状态demandState:"3"、demandProgress:"3"
	 * 
	 * 意向金处理:(1)、关闭意向时若为机场则解冻意向金"0402"
	 * (2)、关闭需求时若为太美或机场且demandType为航线，则解冻意向金"0402"
	 */
	@RequestMapping("/closeDemandById")
	@ResponseBody
	public synchronized Map<String, Object> closeDemandById(Long id,String closeReason) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null || "0".equals(id)) {
			map.put("opResult", "3");// id没传过来
			return map;
		}
		Demand reDemand = demandService.demandFind(id);
		if("3".equals(reDemand.getDemandstate().trim())
				&&"3".equals(reDemand.getDemandprogress().trim())){
			map.put("opResult", "3");
			map.put("msg", "请不要重复进行'结束需求'操作");
			return map;
		}
		//task83自己发布2.自己发布的需求可以下架，当进入订单完成的需求则不能再下架。(订单完成4、佣金支付5、交易完成6)
		if(reDemand.getEmployeeId().longValue()==UserContext.getUser().getId().longValue()&&
				("4".equals(reDemand.getDemandprogress().trim())
						||"5".equals(reDemand.getDemandprogress().trim())
						||"6".equals(reDemand.getDemandprogress().trim())
						)){
			map.put("opResult", "3");
			map.put("msg", "此需求已进入订单状态，您无权进行'结束需求'操作");
			return map;
		}
		List<Long> messageIdList = new ArrayList<Long>();// 存放已经发过消息推送的用户id
		try {
			// 1、先将本条需求关联的所有意向修改为下架responseProgress:"3"
			List<Response> responses = responseService.selectByDemandId(id);
			boolean result = false;
			boolean isIntentionMoney="0".equals(reDemand.getIntentionMoneyState().trim());
			if (responses != null) {
				Response paramResponse = new Response();
				for (Response re : responses) {
					paramResponse.setId(re.getId());
					paramResponse.setResponseProgress("3");
					paramResponse.setIntentionmoneystate("1");//未交意向金
					result = responseService
							.updateResponseSelective(paramResponse);

					// (1)、关闭意向时若为机场则解冻意向金"0402" 1)、帐户表 2）、插入收入表--若该意向是对“运力需求发起且状态不为已撤回2”
					if ("1".equals(reDemand.getDemandtype())&&!"2".equals(re.getResponseProgress())&&"0".equals(re.getIntentionmoneystate()) ) {
						// 1）、帐户表
						Map<String, Object> accountUnfreezeMap = new HashMap<String, Object>();
						RetBean retBean = new RetBean();
						TransactionRecord record = new TransactionRecord();
						record.setNumber("50000");// 暂时写死
						record.setDemandId(id);// 设置需求id
						record.setType("0402");// 解冻（需求关闭）
						// 操作账户
						accountUnfreezeMap = individualCapitalAccountService
								.changeIntentionMoney(record,
										re.getEmployeeId());
						retBean = (RetBean) accountUnfreezeMap.get("retBean");
						if (retBean.isBool()) {
							map.put("opResult", "4");// 账户表解冻失败
							return map;
						} else {
							// 2)、账户表解冻成功，处理交易记录(收入表)
							IndividualCapitalAccount account = (IndividualCapitalAccount) accountUnfreezeMap
									.get("account");
							MoneyIncomeRecord irecord = new MoneyIncomeRecord();
							irecord.setAccountId(account.getId());
							irecord.setNumber(record.getNumber());
							irecord.setDemandId(id);
							irecord.setType(record.getType());
							RetBean iRetBean = moneyIncomeRecordService
									.unfreezeIntentionMoney(irecord);
							if (iRetBean.isBool()) {
								map.put("opResult", "5");// 交易记录表插入失败
								return map;
							}
						}
					}

					// 意向关闭后向意向方消息推送
					sendSystemMessageToResponseWhenCloseDemand(re);
					messageIdList.add(re.getEmployeeId());
				}
			}

			// 2、将本条需求修改为下架状态demandState:"3"、demandProgress:"3"
			Demand paramDemand = new Demand();
			paramDemand.setId(id);
			//paramDemand.setDemandstate("3");//结束需求只维护进度demandProgress,不维护状态demandState--20180126与冬冬确认
			paramDemand.setDemandprogress("3");
			paramDemand.setIntentionMoneyState("1");//未交意向金
			//加入关闭需求原因
			paramDemand.setCloseReason(closeReason);
			result = demandService.demandUpdate(paramDemand);
			// (2)、关闭需求时若需求关联的用户为太美或机场且demandType为航线，且已经向该需求缴纳了意向金,则解冻意向金"0402"
			
			String demandRole = employeeService.selectByprimaryKey(
					reDemand.getEmployeeId()).getRole();
			if (("1".equals(demandRole) || "2".equals(demandRole))
					&& "0".equals(reDemand.getDemandtype())
					&&isIntentionMoney) {

				// 1）、帐户表
				Map<String, Object> accountUnfreezeMap = new HashMap<String, Object>();
				RetBean retBean = new RetBean();
				TransactionRecord record = new TransactionRecord();
				record.setNumber("50000");// 暂时写死
				record.setDemandId(id);// 设置需求id
				record.setType("0402");// 解冻（需求关闭）
				// 操作账户
				accountUnfreezeMap = individualCapitalAccountService
						.changeIntentionMoney(record, reDemand.getEmployeeId());
				retBean = (RetBean) accountUnfreezeMap.get("retBean");
				if (retBean.isBool()) {
					map.put("opResult", "4");// 账户表解冻失败
					return map;
				} else {
					// 2)、账户表解冻成功，处理交易记录(收入表)
					IndividualCapitalAccount account = (IndividualCapitalAccount) accountUnfreezeMap
							.get("account");
					MoneyIncomeRecord irecord = new MoneyIncomeRecord();
					irecord.setAccountId(account.getId());
					irecord.setNumber(record.getNumber());
					irecord.setDemandId(id);
					irecord.setType(record.getType());
					RetBean iRetBean = moneyIncomeRecordService
							.unfreezeIntentionMoney(irecord);
					if (iRetBean.isBool()) {
						map.put("opResult", "5");// 交易记录表插入失败
						return map;
					}
				}
			}

			if (result) {
				map.put("opResult", "0");// 成功结束

				// 消息推送--需求发布者，（委托或者托管的话还有太美）
				Demand messageSendDemand = demandService.demandFind(id);
				sendSystemMessageToReleaseWhenCloseDemand(messageSendDemand,
						messageSendDemand.getEmployeeId());
				messageIdList.add(messageSendDemand.getEmployeeId());
				if (!("0".equals(messageSendDemand.getDemandtype()) || "1"
						.equals(messageSendDemand.getDemandtype()))) {
					// 若为委托/托管推送消息给所有太美账号
					List<Employee> employeeMessageTimei = employeeService
							.selectEmployeesByRole("2");
					if (employeeMessageTimei.size() > 0) {
						for (Employee taimei : employeeMessageTimei) {
							if (!messageIdList.contains(taimei.getId())) {
								sendSystemMessageToReleaseWhenCloseDemand(
										messageSendDemand, taimei.getId());
								messageIdList.add(taimei.getId());
							}
						}
					}
				}

				// 消息推送--收藏该需求的用户//取消对收藏方的消息推送--20180201与谢强确认
//				List<Collect> collects = collectService
//						.selectCollectsByDemandId(id);
//				if (collects.size() > 0) {
//					for (Collect cl : collects) {
//						if (!messageIdList.contains(cl.getEmployeeId())) {
//							sendSystemMessageToReleaseWhenCloseDemand(
//									messageSendDemand, cl.getEmployeeId());
//							messageIdList.add(cl.getEmployeeId());
//						}
//					}
//				}

			} else {
				map.put("opResult", "1");// 结束需求失败
				return map;
			}
			
			//若为委托下的子需求，检查是否需要对委托状态的回退--20180119
			if(reDemand.getDemandId()!=null
					&&reDemand.getDemandId().longValue()!=0){
				// 子需求列表
				List<Long> idList = demandService.SonDemandsByParentDemandId(reDemand.getDemandId());
				boolean isChange=true;//需不需要改委托
				if (idList.size() > 0) {
					Demand sonDemand = null;
					for (Long sonid : idList) {
						sonDemand = demandService.demandFind(sonid);
							if (sonDemand != null&&!"3".equals(sonDemand.getDemandprogress())) {
								isChange=false;//如果存在不为关闭状态"3"的子需求则不需要回退委托状态
							}
					}
				}
				//需要回退委托到"处理中"
				if (isChange) {
					Demand demandForChangeParent=new Demand();
					demandForChangeParent.setId(reDemand.getDemandId());
					demandForChangeParent.setDemandprogress("9");//处理中"9"
					demandService.demandUpdate(demandForChangeParent);
				}
			}
			
			
			
			
			
			
		} catch (Exception e) {
			log.error("It`s some wrong happened here ,please check carefully");
			e.printStackTrace();
			map.put("opResult", "2");// 后台错误
			return map;
		}

		return map;
	}

	/*
	 * 消息推送：需求关闭时推送给响应者--时间-XX需求已关闭
	 */
	private boolean sendSystemMessageToResponseWhenCloseDemand(Response response) {
		boolean result = false;
		Chat chat = new Chat();
		// 需求id
		chat.setDemandId(response.getDemandId());
		// 设置时间
		chat.setDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 消息类型//0:普通聊天内容,1修改提示,2系统消息
		chat.setTextType("2");// 系统消息
		// 状态：0 已读 1未读
		chat.setState("1");
		// 设置消息title
		chat.setTitle("需求状态更新");
		chat.setToNameId(response.getEmployeeId());
		chat.setText(response.getTitle()
				+ (("0".equals(response.getDemandtype())) ? "航线需求已关闭"
						: "运力需求已关闭"));
		result = chatService.insertChatSelective(chat);
		return result;
	}

	/*
	 * 消息推送：需求关闭时推送给需求发布者 1、航线或运力需求 需求方： 时间-XX需求已关闭 2、委托或托管 委托放： 时间-XX委托已关闭
	 * 太美账号（暂定所有太美账号）： 时间-XX委托已关闭
	 */
	private boolean sendSystemMessageToReleaseWhenCloseDemand(Demand demand,
			Long employeeId) {
		boolean result = false;
		Chat chat = new Chat();
		// 需求id
		chat.setDemandId(demand.getId());
		// 设置时间
		chat.setDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 消息类型//0:普通聊天内容,1修改提示,2系统消息
		chat.setTextType("2");// 系统消息
		// 状态：0 已读 1未读
		chat.setState("1");
		// 设置消息title
		chat.setTitle("需求状态更新");
		chat.setToNameId(employeeId);
		chat.setText(demand.getTitle() + demand.getDemandtypeStr() + "已关闭");
		result = chatService.insertChatSelective(chat);
		return result;
	}

	/**
	 * 查看我的订单详情 思路： 判断是否为委托类型 1、是：显示需求详情+显示子需求列表 2、否：显示需求详情
	 */
	@RequestMapping("/seeMyOrderDetail")
	@ResponseBody
	public Map<String, Object> seeMyOrderDetail(Long id, Long responseId,Integer orderType) {//0降序1升序
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null || id == 0 || responseId == null
				|| responseId.longValue() == 0||orderType==null) {
			map.put("opResult", "3");// 请传需求id
			return map;
		}
		try {
			boolean isSelf = false;
			Demand demand = demandService.demandFind(id);
			if (demand != null) {
				// 修改日期显示格式
				demand.setReleasetime(DateUtil.date2Str(
						DateUtil.StringToDate(demand.getReleasetime()),
						"MM.dd.yyyy"));
				//航司查到佣金支付时显示为交易完成
				if("5".equals(demand.getDemandprogress())&&"0".equals(UserContext.getUser().getRole())){
					demand.setDemandprogress("6");
					demand.setDemandprogressStr("交易完成");
				}
				map.put("data", demand);
				String CpyNm = employeeService.selectByprimaryKey(demand.getEmployeeId()).getCompanyName();
				map.put("CpyNm", CpyNm);
				// 判断是否为自己发布的需求
				isSelf = demand.getEmployeeId().longValue()==UserContext.getUser().getId().longValue();
				map.put("isSelf", isSelf);

				boolean isWeituo=false;
				
				if ("3".equals(demand.getDemandtype())
						|| "4".equals(demand.getDemandtype())) {
					isWeituo=true;
				}else if ("0".equals(demand.getDemandtype())
						|| "1".equals(demand.getDemandtype())) {
					isWeituo=false;
				}
				map.put("isWeituo", isWeituo);
				// 委托
				if (isWeituo) {
					// 子需求列表
					List<Demand> listSonDemands = null;
					List<Long> idList = demandService
							.SonDemandsByParentDemandId(id);
					if (idList.size() > 0) {
						listSonDemands = new ArrayList<Demand>();
						Demand sonDemand = null;
						for (Long sonid : idList) {
							sonDemand = demandService.demandFind(sonid);
							if (sonDemand != null) {
								Demand resultSonDemand = new Demand();
								resultSonDemand.setId(sonDemand.getId());
								resultSonDemand.setTitle(sonDemand.getTitle());
								resultSonDemand.setDemandId(sonDemand
										.getDemandId());
								resultSonDemand.setDemandstate(sonDemand
										.getDemandstate());
								resultSonDemand.setDemandStateStr(sonDemand
										.getDemandStateStr());
								resultSonDemand.setReleasetime(sonDemand.getReleasetime());
								//航司查到佣金支付时显示为交易完成
								if("5".equals(sonDemand.getDemandprogress())&&"0".equals(UserContext.getUser().getRole())){
									sonDemand.setDemandprogress("6");
									sonDemand.setDemandprogressStr("交易完成");
								}
								resultSonDemand.setDemandprogress(sonDemand
										.getDemandprogress());
								resultSonDemand.setDemandprogressStr(sonDemand
										.getDemandprogressStr());
								listSonDemands.add(resultSonDemand);
							}
						}
						//排序
						if(orderType==1){//升序
							ListUtils.sort(listSonDemands, true, "releasetime");
						}else {//降序
							ListUtils.sort(listSonDemands, false, "releasetime");
						}
						for(Demand demand2:listSonDemands){
							// 修改日期显示格式
							demand2.setReleasetime(DateUtil.date2Str(
									DateUtil.StringToDate(demand2.getReleasetime()),
									"MM.dd.yyyy"));
						}
					}
					map.put("listSonDemands", listSonDemands);

				} else {
					// 正常需求
					// 该订单对应的意向
					List<Response> responses = new ArrayList<Response>();
					Response re = responseService
							.selectByPrimaryKey(responseId);
					if (re != null) {
						//航司查到佣金支付时显示为交易完成
						if("7".equals(re.getResponseProgress())&&"0".equals(UserContext.getUser().getRole())){
							re.setResponseProgress("5");
							re.setResponseProgressStr("交易完成");
						}
						// 添加意向方名字
						re.setIntentionCompanyName(employeeService.selectByprimaryKey(re.getEmployeeId()).getCompanyName());
						// 修改日期显示格式
						re.setResponsedate(DateUtil.date2Str(
								DateUtil.StringToDate(re.getResponsedate()),
								"MM.dd.yyyy"));
						responses.add(re);
						map.put("responseList", responses);
						map.put("opResult", "0");
					} else {
						map.put("opResult", "1");
						return map;
					}
				}

			} else {
				map.put("opResult", "1");
				return map;
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/checkCommissionedAndCustody")
	@ResponseBody
	public Map<String, Object> checkCommissionedAndCustody(Long demandId,
			String demandState, String rek, String demandType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demandId == null || demandId == 0 || TextUtil.isEmpty(demandState)
				|| ("1".equals(demandState) && TextUtil.isEmpty(rek))
				|| TextUtil.isEmpty(demandType)) {
			log.debug("demandId or demandState or rek is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		Demand reDemand=demandService.demandFind(demandId);
		if (reDemand==null) {
			map.put("opResult", "3");
			map.put("msg", "找不到该需求");
			return map;
		}
		Demand demand = new Demand();
		demand.setId(demandId);
		demand.setRek(rek);
		if ("0".equals(demandState)) {
			demand.setDemandstate("6");// 数据库审核通过状态码
			/*
			 * 通过时委托改成处理中"9" 托管改成已接受"8"
			 */
			if ("2".equals(demandType)) {
				demand.setDemandprogress("8");// 托管已接受
			} else if ("3".equals(demandType) || "4".equals(demandType)) {
				demand.setDemandprogress("9");// 处理中，平台接受委托，但未关联子需求
			}
		} else {
			demand.setDemandstate("5");// 数据库审核不通过状态码
			demand.setDemandprogress("10");// 已拒绝，市场人员拒绝需求。
		}
		try {
			boolean result = demandService.demandUpdate(demand);
			// 审核委托/托管发送消息
			Chat chat = new Chat();
			// 需求id
			chat.setDemandId(demandId);
			// 设置时间
			chat.setDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
			// 消息类型//0:普通聊天内容,1修改提示,2系统消息
			chat.setTextType("2");// 系统消息
			// 状态：0 已读 1未读
			chat.setState("1");
			// 设置消息title
			chat.setTitle("需求状态更新");
			// 需求种类共5种（0:航线需求、1:运力投放、2:运营托管、3:航线委托、4:运力委托）
			String demandTypeStr = null;
			if ("0".equals(demandType)) {
				demandTypeStr = "航线";
			} else if ("1".equals(demandType)) {
				demandTypeStr = "运力";
			} else if ("2".equals(demandType)) {
				demandTypeStr = "托管";
			} else if ("3".equals(demandType)) {
				demandTypeStr = "委托";
			} else if ("4".equals(demandType)) {
				demandTypeStr = "委托";
			}
			if ("0".equals(demandState)) {
				String textStrRelease = reDemand.getTitle().trim() + demandTypeStr + "平台已受理，意向征集中";// 发布方消息内容
				chat.setText(textStrRelease);
				chat.setToNameId(reDemand.getEmployeeId());// 接收人id
				chatService.insertChatSelective(chat);// ---------插入发布人消息
				String textStrTaimei = reDemand.getTitle().trim() + demandTypeStr + "已受理，请及时处理";// 太美消息内容
				chat.setText(textStrTaimei);
				chat.setToNameId(UserContext.getUser().getId());// 接收人为太美
				chatService.insertChatSelective(chat);// ---------插入太美消息
			} else {
				String textStrRelease = reDemand.getTitle().trim() + demandTypeStr + "未受理";// 发布方消息内容
				String textStrTaimei = reDemand.getTitle().trim() + demandTypeStr + "已拒绝";// 太美消息内容
				chat.setText(textStrRelease);
				chat.setToNameId(reDemand.getEmployeeId());// 接收人id
				chatService.insertChatSelective(chat);// ---------插入发布人消息
				chat.setText(textStrTaimei);
				chat.setToNameId(UserContext.getUser().getId());// 接收人为太美
				chatService.insertChatSelective(chat);// ---------插入太美消息
			}
			if (result) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There have errors");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	// 不关联子需求直接订单完成或者取消
	@RequestMapping("/finishCommissionedAndCustody")
	@ResponseBody
	public Map<String, Object> finishCommissionedAndCustody(Long demandId,
			String demandState, String demandType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demandId == null || demandId == 0 || TextUtil.isEmpty(demandState)) {
			log.debug("demandId or demandState or rek is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		Demand reDemand=demandService.demandFind(demandId);
		if (reDemand==null) {
			map.put("opResult", "3");
			map.put("msg", "找不到该需求");
			return map;
		}
		Demand demand = new Demand();
		demand.setId(demandId);
		if ("0".equals(demandState)) {
			demand.setDemandstate("1");
			demand.setDemandprogress("6");
			demand.setFinishDate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		} else {
			//demand.setDemandstate("3");//结束需求只维护进度demandProgress,不维护状态demandState--20180126与冬冬确认
			demand.setDemandprogress("3");
			demand.setCloseReason("下架");
			//demand.setRek(rek);//--取消不用填写原因20180110
		}
		try {
			boolean result = demandService.demandUpdate(demand);
			if (result) {
				map.put("opResult", "0");
				// 消息推送：委托托管的交易完成或取消
				sendMessageWhenFinishOrCancelCommissionedAndCustody(demandId,
						demandState, reDemand.getEmployeeId(), reDemand.getTitle().trim(), demandType);
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There have errors");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	/*
	 * 消息推送：委托托管的交易完成或取消
	 */
	private boolean sendMessageWhenFinishOrCancelCommissionedAndCustody(
			Long demandId, String demandState, Long demandEmployeeId,
			String title, String demandType) {
		boolean result = false;
		Chat chat = new Chat();
		// 需求id
		chat.setDemandId(demandId);
		// 设置时间
		chat.setDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 消息类型//0:普通聊天内容,1修改提示,2系统消息
		chat.setTextType("2");// 系统消息
		// 状态：0 已读 1未读
		chat.setState("1");
		// 设置消息title
		chat.setTitle("需求状态更新");

		String demandtypeStr = null;
		if ("0".equals(demandType)) {
			demandtypeStr = "航线需求";
		} else if ("1".equals(demandType)) {
			demandtypeStr = "运力投放";
		} else if ("2".equals(demandType)) {
			demandtypeStr = "运营托管";
		} else if ("3".equals(demandType)) {
			demandtypeStr = "航线委托";
		} else if ("4".equals(demandType)) {
			demandtypeStr = "运力托管";
		}

		/*
		 * 1、委托方： 时间-XX交易已完成 2、太美方： 时间-XX交易已完成
		 */
		if ("0".equals(demandState)) {// 完成
			// 1、委托方
			chat.setToNameId(demandEmployeeId);// 委托人
			chat.setText(title + demandtypeStr + "交易已完成");
			result = chatService.insertChatSelective(chat);
			chat.setToNameId(UserContext.getUser().getId());// 太美
			result = chatService.insertChatSelective(chat);

			/*
			 * 当平台接受该委托但是无法完成委托时，需与委托方达成一致方可取消该委托，
			 * 取消时填写原因，该委托进入已关闭状态，保留取消原因。（task110太美查看第6条）
			 * 
			 * 此处的消息推送只涉及到太美和委托方 1、委托关闭-委托方： 时间-XX委托已关闭 2、委托关闭-太美： 时间-XX委托已关闭
			 */
		} else {// 取消
			chat.setText(title + demandtypeStr + "已关闭");
			// 1、委托方
			chat.setToNameId(demandEmployeeId);// 委托方
			result = chatService.insertChatSelective(chat);
			// 2、太美
			chat.setToNameId(UserContext.getUser().getId());
			result = chatService.insertChatSelective(chat);
		}
		return result;
	}

	@RequestMapping("/getDemandsByCurrentCheckedAirportForEmployee")
	@ResponseBody
	public Map<String, Object> getDemandsByCurrentCheckedAirportForEmployee(String itia,
			int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page<=0) {
			log.debug("In getDemandsForCurrentCheckedAirport,itia is a invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			DemandSearch demandSearch = new DemandSearch();
			// 1-查询当前用户在该机场发布的需求（运力或航线）0-查询当前机场的所有需求
			demandSearch.setEmployeeId(UserContext.getUser().getId());
			demandSearch.setItia(itia);
			demandSearch.setPageNo(PageBean.pageSize);
			demandSearch.setStartIndex(PageBean.pageSize * (page - 1));
			demandSearch.setPage(page);
			PageBean<Demand> list = demandService
					.getDemandsByCurrentCheckedAirportForEmployee(demandSearch);
			if (list != null) {
				map.put("opResult", "0");
				map.put("list", list);
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error(
					"getDemandsForCurrentCheckedAirport has something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	
	@RequestMapping("/getOthersDemandListIndex")
	@ResponseBody
	public Map<String,Object> getOthersDemandListIndex(String itia,int page){
		return demandService.getOthersDemandListIndex(itia,page);
	}
}
