package org.ldd.ssm.hangyu.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.domain.Collect;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.domain.Response;
import org.ldd.ssm.hangyu.domain.ResponseForAddAndUp;
import org.ldd.ssm.hangyu.domain.SpendingRecord;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.service.CollectService;
import org.ldd.ssm.hangyu.service.DemandService;
import org.ldd.ssm.hangyu.service.EmployeeService;
import org.ldd.ssm.hangyu.service.IChatService;
import org.ldd.ssm.hangyu.service.IndividualCapitalAccountService;
import org.ldd.ssm.hangyu.service.MoneyIncomeRecordService;
import org.ldd.ssm.hangyu.service.ResponseService;
import org.ldd.ssm.hangyu.service.SpendingRecordService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.ldd.ssm.hangyu.utils.PublicOpinionUtil;
import org.ldd.ssm.hangyu.utils.RetBean;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseAction {

	@Autowired
	private ResponseService responseService;
	@Autowired
	private DemandService demandService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private IndividualCapitalAccountService individualCapitalAccountService;

	@Autowired
	private SpendingRecordService spendingRecordService;

	@Autowired
	private MoneyIncomeRecordService moneyIncomeRecordService;

	@Autowired
	private IChatService chatService;

	@Autowired
	private CollectService collectService;

	Logger log = Logger.getLogger(ResponseAction.class);

	/**
	 * 向某条意向缴纳意向金
	 * 
	 * @param response
	 * @return
	 */
//	@RequestMapping("/changeIntentionMoneyStatusForResponse")
//	@ResponseBody
	public Map<String, Object> changeIntentionMoneyStatus(Long id,
			String intentionStatu) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null || id == 0 || TextUtil.isEmpty(intentionStatu)) {
			map.put("opResult", "3");// 缺少入参
			return map;
		}
		Response response = new Response();
		response.setId(id);
		response.setIntentionmoneystate(intentionStatu);
		try {
			boolean result = responseService.updateResponseSelective(response);
			Response reResponse = responseService.selectByPrimaryKey(id);
			if (result == true) {
				map.put("opResult", "0");// 成功
			} else {
				map.put("opResult", "1");// 失败
			}
			map.put("data", reResponse);
		} catch (Exception e) {
			log.error("changeIntentionMoney：there is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	/**
	 * 用户向某条需求缴纳意向金(目前只需要给机场使用)
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/changeIntentionMoneyStatusForDemand")
	@ResponseBody
	public synchronized Map<String, Object> changeIntentionMoneyStatusForDemand(
			Long demandId, String intentionStatu) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (demandId == null || demandId == 0
				|| TextUtil.isEmpty(intentionStatu)) {
			map.put("opResult", "3");// 缺少入参
			return map;
		}
		// 缴费操作（操作帐户表）
		Demand reDemand = demandService.demandFind(demandId);
		
		if("0".equals(reDemand.getIntentionMoneyState().trim())
				&&"0".equals(intentionStatu.trim())){
			map.put("opResult", "3");
			map.put("msg", "请不要重复此操作！");
			return map;
		}
		
		if (!"0".equals(reDemand.getIntentionMoneyState())
				&& "0".equals(intentionStatu)) {
			// 数据库状态不是已缴费且传来的状态码是缴费，此时判定用户想要缴费
			RetBean retBean = new RetBean();
			IndividualCapitalAccount account = null;

			// 1、扣费2、插入支出表
			// 1、扣费
			Map<String, Object> accountFreezeMap = new HashMap<String, Object>();
			TransactionRecord record = new TransactionRecord();
			record.setNumber("50000");// 暂时写死
			record.setDemandId(demandId);// 设置需求id
			record.setType("0301");// 冻结（机场自己发的需求查看别人提交的意向）
			// 操作账户
			accountFreezeMap = individualCapitalAccountService
					.changeIntentionMoney(record, UserContext.getUser().getId());
			retBean = (RetBean) accountFreezeMap.get("retBean");
			System.out.println(retBean.isBool());
			System.out.println(retBean.getMassage());
			if (retBean.isBool()) {
				// 余额不足
				map.put("opResult", "4");// 余额不足
				return map;
			} else {
				// 账户处理成功，处理交易记录
				account = (IndividualCapitalAccount) accountFreezeMap
						.get("account");
				SpendingRecord srecord = new SpendingRecord();
				srecord.setAccountId(account.getId());
				srecord.setNumber(record.getNumber());
				srecord.setDemandId(demandId);
				srecord.setType(record.getType());
				RetBean sRetBean = spendingRecordService
						.freezeIntentionMoney(srecord);
				if (!sRetBean.isBool()) {
					// 操作交易记录表成功
					// 默认已经缴费
					List<Response> responses = null;
					try {
						// 修改demand表意向金状态
						boolean result = demandService
								.changeDemandIntentionStateByDemandId(demandId,
										intentionStatu);

						if (result) {
							// 自己发布，已交意向金，可查看意向列表
							Response re = new Response();
							re.setDemandId(demandId);
							responses = responseService.selectByResponse(re);
							// 添加意向方名字
							if (responses.size() > 0) {
								for (Response r : responses) {
									r.setIntentionCompanyName(employeeService.selectByprimaryKey(r.getEmployeeId()).getCompanyName());
								}
							}
							map.put("responseList", responses);
							map.put("opResult", "0");// 成功
						} else {
							map.put("responseList", responses);
							map.put("opResult", "1");// 失败
							return map;
						}
					} catch (Exception e) {
						log.error("changeIntentionMoney：there is something wrong here.");
						e.printStackTrace();
						map.put("opResult", "2");
						return map;
					}
				} else {
					// 余额不足
					map.put("opResult", "4");// 余额不足
					return map;
				}
			}

		}

		return map;
	}
	
	@RequestMapping("/responseAdd")
	@ResponseBody
	public synchronized  Map<String, Object> responseAdd2(ResponseForAddAndUp response2) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (response2 == null) {
			map.put("opResult", "3");
			return map;
		}
		Response response = getResponseTwo(response2);
		// 登陆者id
		response.setEmployeeId(UserContext.getUser().getId());

		// 原需求
		Demand redmand = null;
		if (response2.getDemandId() != 0 && response2.getDemandId() != null) {
			redmand = demandService.demandFind(response2.getDemandId());
		}
		if (redmand != null) {

			if ("0".equals(redmand.getDemandprogress())) {// 有响应时如果需求仍未需求发布则改为意向征集
				Demand demandForUpdateDemandProgress = new Demand();
				demandForUpdateDemandProgress.setId(redmand.getId());
				demandForUpdateDemandProgress.setDemandprogress("1");
				try {
					demandService.demandUpdate(demandForUpdateDemandProgress);
					redmand.setDemandprogress("1");
					redmand.setDemandprogressStr("意向征集");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("opResult", "2");// 后台错误
					return map;
				}
			}

			map.put("redmand", redmand);
		}
		if (UserContext.getUser().getId().longValue() == redmand
				.getEmployeeId().longValue()) {
			map.put("opResult", "3");
			map.put("msg", "不能响应自己发的需求");
			return map;
		}
		if(!(response.getId() != null && response.getId().longValue() != 0)){//response表插入数据时增加检查：表中是否已经有了该用户的意向--20180117
			int responseCountForThisDemandByEmployee=responseService.selectResponseIdCountByEmployeeIdAndDemandId(UserContext.getUser().getId(), redmand.getId());
			if (responseCountForThisDemandByEmployee>0) {
				map.put("opResult", "3");
				map.put("msg", "后台已存在你对该需求的意向");
				return map;
			}
		}
		
		String periodValidity = response2.getPeriodValidity();

		map.put("periodValidity", periodValidity);

		// 检验employee表intentionmoney状态（若不为0改为0）；检验IndividualCapitalAccount表余额（若小于0且-10000后小于0则回滚，否则-1000）
		try {
			RetBean retBean = new RetBean();
			IndividualCapitalAccount account = null;
			// 只有机场发起意向时需要缴费--------------------20171211修改--只要响应运力需求都要交意向金（为了意向金后期转入的处理账户收支平衡）--20180115
			if ("1".equals(redmand.getDemandtype())) {
				//检查意向金状态
				Response responseToCHeckIntentionMoneyWhenRecharge=new Response();
				responseToCHeckIntentionMoneyWhenRecharge.setDemandId(redmand.getId());
				responseToCHeckIntentionMoneyWhenRecharge.setEmployeeId(UserContext.getUser().getId());
				Response resultResponseToCHeckIntentionMoneyWhenRecharge=responseService.selectByDemandIdAndEmployeeId(responseToCHeckIntentionMoneyWhenRecharge);
				if(resultResponseToCHeckIntentionMoneyWhenRecharge!=null&&resultResponseToCHeckIntentionMoneyWhenRecharge.getId()!=null&&resultResponseToCHeckIntentionMoneyWhenRecharge.getId().longValue()!=0){
					if("0".equals(resultResponseToCHeckIntentionMoneyWhenRecharge.getIntentionmoneystate().trim())){
						map.put("opResult", "3");
						map.put("msg", "不要重复提交");
						return map;
					}
				}
				
				
				// 1、扣费2、插入支出表
				// 1、扣费
				Map<String, Object> accountFreezeMap = new HashMap<String, Object>();
				TransactionRecord record = new TransactionRecord();
				record.setNumber("50000");// 暂时写死
				record.setDemandId(redmand.getId());// 设置需求id
				record.setType("0302");// 冻结（响应需求）
				// 操作账户
				accountFreezeMap = individualCapitalAccountService
						.changeIntentionMoney(record, UserContext.getUser()
								.getId());
				retBean = (RetBean) accountFreezeMap.get("retBean");

				if (retBean.isBool()) {
					// 账户处理失败
					map.put("PreResponse", response2);// 提交上来的数据
					map.put("opResult", 4);// 余额不足
					return map;
				} else {
					// 账户处理成功，处理交易记录
					account = (IndividualCapitalAccount) accountFreezeMap
							.get("account");
					SpendingRecord srecord = new SpendingRecord();
					srecord.setAccountId(account.getId());
					srecord.setNumber(record.getNumber());
					srecord.setDemandId(redmand.getId());
					srecord.setType(record.getType());
					RetBean sRetBean = spendingRecordService
							.freezeIntentionMoney(srecord);
					if (sRetBean.isBool()) {
						// 交易记录表处理失败
						map.put("PreResponse", response2);// 提交上来的数据
						map.put("opResult", 4);// 余额不足
						return map;
					}
				}
			}
			// 处理response表
			response.setIntentionmoneystate("0");
			// 入库
			boolean result = false;

			if (response.getId() != null && response.getId().longValue() != 0) {// 重新提交意向--20180112
				response.setNewinfo("0");
				// 默认demandProgress="0"意向征集
				response.setResponseProgress("0");
				// 默认双方都未选定
				response.setReleaseselected("1");
				response.setResponseselected("1");
				response.setTitle(redmand.getTitle());
				response.setReleasetime(redmand.getReleasetime());// 需求发布时间
				// response.setWhetherhosting("");//是否托管(0:是,1:否)//因发出的需求不能转托管，所以留空
				response.setWhethernavigation("1");// 是否通航(0:是,1:否),默认未通航
				//重新设置响应时间标志
				response.setResponsedate("responseAgain");
				result = responseService.updateResponseSelective(response);
			} else {
				response.setNewinfo("0");
				// 默认demandProgress="0"意向征集
				response.setResponseProgress("0");
				// 默认双方都未选定
				response.setReleaseselected("1");
				response.setResponseselected("1");
				// 设置意向标题为需求标题---------------20171211
				response.setTitle(redmand.getTitle());
				response.setReleasetime(redmand.getReleasetime());// 需求发布时间
				// response.setWhetherhosting("");//是否托管(0:是,1:否)//因发出的需求不能转托管，所以留空
				response.setWhethernavigation("1");// 是否通航(0:是,1:否),默认未通航
				result = responseService.addResponse(response);
			}
			if (result) {
				// 插入成功后将意向返回
				Response paramResponse = responseService
						.selectByDemandIdAndEmployeeId(response);
				map.put("response", paramResponse);
				map.put("opResult", "0");
			} else {
				map.put("PreResponse", response2);
				map.put("opResult", "1");
				return map;
			}

			// 若为委托下的子需求，检查该委托的最低状态是否为需求征集:"1"
			if (redmand.getDemandId() != null
					&& redmand.getDemandId().longValue() != 0) {
				Demand parentDemand = demandService.demandFind(redmand
						.getDemandId());
				// 若父委托仍为需求发布"0",则改为需求征集"1"
				if ("0".equals(parentDemand.getDemandprogress().trim())) {
					Demand forUpdateParentDemand = new Demand();
					forUpdateParentDemand.setId(parentDemand.getId());
					forUpdateParentDemand.setDemandprogress("1");
					result = demandService.demandUpdate(forUpdateParentDemand);
				}
			}

			// 添加意向时消息推送
			result = sendMessageWhenAddResponse(response, redmand);

		} catch (Exception e) {
			log.error("缴费失败");
			e.printStackTrace();
			map.put("PreResponse", response2);// 提交上来的数据
			map.put("opResult", "2");// 缴费失败
			return map;
		}
		
		return map;
	}

	/*
	 * 消息推送： 1、需求发布者： 时间-XX需求有新的意向方加入 2、本次响应的用户： 时间-意向提交成功
	 * 3、其他已提交意向的用户：时间-XX需求有新的意向方加入
	 */
	private boolean sendMessageWhenAddResponse(Response response, Demand demand) {
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
		chat.setTitle("收到新的意向");
		// 1、推送给需求发布者的消息
		chat.setToNameId(demand.getEmployeeId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr() + "有新的意向方加入");
		result = chatService.insertChatSelective(chat);
		// 2、推送给当前响应者的消息
		chat.setToNameId(response.getEmployeeId());
		chat.setText("意向提交成功");
		result = chatService.insertChatSelective(chat);
		// 3、推送给其他已经提交意向的用户的消息
		List<Response> list = responseService.selectByDemandId(demand.getId());
		if (list.size() > 0) {
			for (Response r : list) {
				if (response.getEmployeeId().longValue() != r.getEmployeeId()
						.longValue()) {
					chat.setToNameId(r.getEmployeeId());
					chat.setText(demand.getTitle() + demand.getDemandtypeStr()
							+ "有新的意向方加入");
					result = chatService.insertChatSelective(chat);
				}
			}
		}

		return result;
	}

	private Response getResponseTwo(ResponseForAddAndUp response2) {
		Response response = new Response();
		response.setId(response2.getId());
		response.setEmployeeId(response2.getEmployeeId());
		response.setDemandId(response2.getDemandId());
		response.setIntentionmoneystate(response2.getIntentionmoneystate());
		response.setWhetherhosting(response2.getWhetherhosting());
		response.setWhethernavigation(response2.getWhethernavigation());
		response.setReleaseselected(response2.getReleaseselected());
		response.setResponseselected(response2.getResponseselected());
		response.setDemandtype(response2.getDemandtype());
		response.setReleasetime(response2.getReleasetime());
		response.setTitle(response2.getTitle());
		response.setDptState(response2.getDptState());
		response.setDpt(response2.getDpt());
		response.setDptCt(response2.getDptCt());
		response.setDptAcceptnearairport(response2.getDptAcceptnearairport());
		response.setDptTimeresources(response2.getDptTimeresources());
		response.setDptTime(response2.getDptTime());
		response.setPstState(response2.getPstState());
		response.setPst(response2.getPst());
		response.setPstCt(response2.getPstCt());
		response.setPstAcceptnearairport(response2.getPstAcceptnearairport());
		response.setPstTimeresources(response2.getPstTimeresources());
		response.setPstTime(response2.getPstTime());
		response.setArrvState(response2.getArrvState());
		response.setArrv(response2.getArrv());
		response.setArrvCt(response2.getArrvCt());
		response.setArrvAcceptnearairport(response2.getArrvAcceptnearairport());
		response.setArrvTimeresources(response2.getArrvTimeresources());
		response.setArrvTime(response2.getArrvTime());
		response.setAircrfttyp(response2.getAircrfttyp());
		response.setDays(response2.getDays());
		response.setBlockbidprice(response2.getBlockbidprice());
		response.setLoadfactorsexpect(response2.getLoadfactorsexpect());
		response.setAvgguestexpect(response2.getAvgguestexpect());
		response.setSubsidypolicy(response2.getSubsidypolicy());
		response.setSailingtime(response2.getSailingtime());
		response.setSeating(response2.getSeating());
		response.setCapacitycompany(response2.getCapacitycompany());
		response.setScheduling(response2.getScheduling());
		response.setSchedulineport(response2.getSchedulineport());
		response.setHourscost(response2.getHourscost());
		response.setRemark(response2.getRemark());
		response.setPublicway(response2.getPublicway());
		response.setDirectionalgoal(response2.getDirectionalgoal());
		response.setFltnbr(response2.getFltnbr());
		response.setContact(response2.getContact());
		response.setIhome(response2.getIhome());
		response.setResponsedate(response2.getResponsedate());
		response.setUpdatedate(response2.getUpdatedate());
		response.setNewinfo(response2.getNewinfo());
		response.setDptFltLvl(response2.getDptFltLvl());
		response.setPstFltLvl(response2.getPstFltLvl());
		response.setArrvFltLvl(response2.getArrvFltLvl());
		response.setResponseProgress(response2.getResponseProgress());
		response.setCapacityBase(response2.getCapacityBase());
		return response;
	}

	@RequestMapping("/responseFind")
	@ResponseBody
	public Map<String, Object> responseFins(Long responseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (responseId == null || responseId == 0) {
			map.put("opResult", "3");
			return map;
		}

		try {
			Response response = responseService.selectByPrimaryKey(responseId);
			if (response != null) {
				map.put("data", response);
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("there is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}

		/*
		 * 查看响应详情成功，则把该响应newinfo改为无新进展状态"1"
		 */
		responseService.updateResponseNewInfoStatus("1", responseId);
		return map;
	}

	@RequestMapping("/updateResponseSelective")
	@ResponseBody
	public Map<String, Object> updateResponse(ResponseForAddAndUp response2) {
		Map<String, Object> map = new HashMap<>();
		if (response2 == null
				|| TextUtil.isEmpty(response2.getReleaseselected())
				|| TextUtil.isEmpty(response2.getResponseselected())
				|| TextUtil.isEmpty(response2.getResponseProgress())
				|| response2.getEmployeeId() == null
				|| response2.getEmployeeId().longValue() == 0
				|| response2.getDemandId() == null
				|| response2.getDemandId().longValue() == 0) {
			map.put("opResult", "3");
			return map;
		}
		Response response = getResponseTwo(response2);
		response.setResponsedate(null);
		response.setId(response2.getId());
		String releaseSelect = response.getReleaseselected();
		response.setReleaseselected(null);
		String responseSelect = response.getResponseselected();
		response.setResponseselected(null);
		String responseProgress = response.getResponseProgress();
		response.setResponseProgress(null);
		response.setNewinfo("0");
		Long demandId = response.getDemandId();
		response.setDemandId(null);
		Long responseEmployeeId = response.getEmployeeId();
		// 若不是响应者在进行意向的修改则不能修改联系人和联系方式--20180108
		if (response.getEmployeeId().longValue() != UserContext.getUser()
				.getId().longValue()) {
			response.setContact(null);
			response.setIhome(null);
		}

		response.setEmployeeId(null);

		try {
			boolean result = responseService.updateResponseSelective(response);
			if (result) {
				map.put("opResult", "0");

				// 判断是选定前的编辑还是选定后的编辑
				if ("0".equals(releaseSelect)// 发布者选定
						&& "1".equals(responseSelect)// 响应者未选定
						&& "1".equals(responseProgress)// 订单确认状态
				) {
					// 查出需求
					Demand demand = demandService.demandFind(demandId);
					if (demand != null
							&& (demand.getEmployeeId().longValue() == UserContext
									.getUser().getId().longValue())) {// 发布者编辑
						// 满足消息推送的条件
						sendMessageWhenReleaseUpdateResponse(demand,
								responseEmployeeId);
					}

				}

			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("there is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	/*
	 * 消息推送：--需求发布方选定之后的修改意向 1、需求方： 时间-XX需求的意向方案修改成功，等待对方确认方案 2、意向方：
	 * 时间-XX需求的意向方案已被修改，请尽快确认交易
	 */
	private boolean sendMessageWhenReleaseUpdateResponse(Demand demand,
			Long toEmployeeId) {
		boolean result = false;
		Chat chat = new Chat();
		// 需求id
		chat.setDemandId(demand.getId());
		// 设置时间
		chat.setDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// 消息类型//0:普通聊天内容,1修改提示,2系统消息
		chat.setTextType("1");// 修改提示
		// 状态：0 已读 1未读
		chat.setState("1");
		// 消息title
		chat.setTitle("需求状态更新");
		// 1、需求方
		chat.setToNameId(demand.getEmployeeId());
		chat.setFromNameId(toEmployeeId);
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "的意向方案修改成功，等待对方确认方案");
		result = chatService.insertChatSelective(chat);
		// 2、意向方
		chat.setToNameId(toEmployeeId);
		chat.setFromNameId(demand.getEmployeeId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "的意向方案已被修改，请尽快确认交易");
		result = chatService.insertChatSelective(chat);

		return result;
	}

	@RequestMapping("/ResponseDel")
	@ResponseBody
	public synchronized Map<String, Object> deleteResponse(Long id, Long demandId) {//取消意向时涉及到意向金进行同步--20180118
		// Response response = getResponseTwo(response2);
		Map<String, Object> map = new HashMap<String, Object>();

		if (id == null || id == 0 || demandId == null || demandId == 0) {
			map.put("opResult", "3");
			return map;
		}
		Demand demand = demandService.demandFind(demandId);
		Response reResponse = responseService.selectByPrimaryKey(id);
		if(!"0".equals(reResponse.getResponseProgress().trim())){
			map.put("opResult", "3");
			map.put("msg", "您的意向有新动态，此阶段不可取消意向，请刷新页面！");
			return map;
		}
		try {
			// 当为机场删除意向时进行意向金"解冻操作"--当删除的是针对运力需求的意向时进行"意向金解冻"--20180115
			if ("1".equals(demand.getDemandtype())
					&& "0".equals(reResponse.getIntentionmoneystate())) {
				// 1、解冻2、插入收入表
				// 1、解冻

				Map<String, Object> accountUnfreezeMap = new HashMap<String, Object>();
				RetBean retBean = new RetBean();
				TransactionRecord record = new TransactionRecord();
				record.setNumber("50000");// 暂时写死
				record.setDemandId(demandId);// 设置需求id
				record.setType("0401");// 解冻（撤回意向）
				// 操作账户
				accountUnfreezeMap = individualCapitalAccountService
						.changeIntentionMoney(record, UserContext.getUser()
								.getId());
				retBean = (RetBean) accountUnfreezeMap.get("retBean");
				if (retBean.isBool()) {
					map.put("opResult", "4");// 账户表解冻失败
					return map;
				} else {
					// 账户表解冻成功，处理交易记录(收入表)
					IndividualCapitalAccount account = (IndividualCapitalAccount) accountUnfreezeMap
							.get("account");
					MoneyIncomeRecord irecord = new MoneyIncomeRecord();
					irecord.setAccountId(account.getId());
					irecord.setNumber(record.getNumber());
					irecord.setDemandId(demandId);
					irecord.setType(record.getType());
					RetBean iRetBean = moneyIncomeRecordService
							.unfreezeIntentionMoney(irecord);
					if (iRetBean.isBool()) {
						map.put("opResult", "5");// 交易记录表插入失败
						return map;
					}
				}
			}

			// 处理response表

			Response responseForUpdate = new Response();
			responseForUpdate.setId(id);
			responseForUpdate.setResponseProgress("2");// 已撤回
			responseForUpdate.setReleaseselected("1");
			responseForUpdate.setResponseselected("1");
			responseForUpdate.setIntentionmoneystate("1");// 未交意向金
			boolean result = responseService
					.updateResponseSelective(responseForUpdate);// --20180112修改意向撤回或取消永不删除，只改
			// 检查是否为委托并对委托进行状态回退
			if (demand.getDemandId() != null
					&& demand.getDemandId().longValue() != 0) {// 是委托
				// 查该委托所有子需求
				List<Demand> sonDemands = demandService
						.selectSonDemandsByParentId(demand.getDemandId());
				int responseCount = 0;
				for (Demand d : sonDemands) {
					// 若所有子需求都未收到意向则状态回退到需求发布
					responseCount += responseService
							.selectByPrimaryKeyForCount(d.getId());
				}
				if (responseCount == 0) {
					// 状态回退
					Demand forUpdateParentDemand = new Demand();
					forUpdateParentDemand.setId(demand.getDemandId());
					forUpdateParentDemand.setDemandprogress("0");//
					result = demandService.demandUpdate(forUpdateParentDemand);
				}
			}
			if (result) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("there is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/getResponseListOfMine")
	@ResponseBody
	public Map<String, Object> myResponseList(String demandType,
			String responseProgress, int page, int orderType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page == 0) {
			log.debug("page is na invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = UserContext.getUser();
			PageBean<Response> list = responseService.getResponseByEmployee(
					demandType, responseProgress, emp.getId(), orderType, page,
					5);// 第三参数可以不填，默认为10
			if (list == null) {
				map.put("opResult", "1");
			} else {
				map.put("opResult", "0");
				map.put("list", list);
			}
		} catch (Exception e) {
			log.error("database exception");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@RequestMapping("/getResponseDetails")
	@ResponseBody
	public Map<String, Object> getResponseDetails(Long responseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (responseId == null || responseId == 0) {
			log.debug("responseId is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
//			PublicOpinionUtil.getPublicOpinionIp();
//			System.out.println(PublicOpinionUtil.CAPPLICATION_HOST);
			Response response = responseService.getResponseDetails(responseId);
			if (response == null) {
				map.put("opResult", "1");
			} else {
				map.put("opResult", "0");
				map.put("obj", response);
			}
		} catch (Exception e) {
			log.error("database exception");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	// 选定/撤销选定响应
	@RequestMapping("/selectedResponse")
	@ResponseBody
	public synchronized Map<String, Object> selectedResponse(
			ResponseForAddAndUp paramaResponseForChec) {// 用单属性的对象去接受
		Map<String, Object> map = new HashMap<>();
		if (paramaResponseForChec == null
				|| paramaResponseForChec.getId() == null
				|| paramaResponseForChec.getId().longValue() == 0) {
			map.put("opResult", "3");
			return map;
		}

		Response paramaResponseForCheck = getResponseTwo(paramaResponseForChec);
		paramaResponseForCheck.setId(paramaResponseForChec.getId());

		Long id = paramaResponseForCheck.getId();
		Long employeeId = paramaResponseForCheck.getEmployeeId();
		Long demandId = paramaResponseForCheck.getDemandId();

		Response response = new Response();
		response.setId(id);
		response.setEmployeeId(paramaResponseForCheck.getEmployeeId());
		response.setDemandId(demandId);
		Demand demand = null;
		if (response.getDemandId() != 0 && response.getDemandId() != null) {
			demand = demandService.demandFind(response.getDemandId());
			if (demand == null) {
				map.put("opResult", "3");
				return map;
			}
		}
		// 一、发布者访问
		if (UserContext.getUser().getId().longValue() == demand.getEmployeeId()
				.longValue()) {
			// 1、*******************发布者选定（此时可以修改意向）
			if ("0".equals(paramaResponseForCheck.getReleaseselected())) {
				
				//航线需求发布者主动选定时需判断是否向自己发布的需求缴纳了意向金--20180130
				if("0".equals(demand.getDemandtype().trim())&&!"0".equals(demand.getIntentionMoneyState().trim())){
					map.put("opResult", "3");
					map.put("msg", "您还未缴纳意向金，缴纳之后才能选定。");
					return map;
				}
				
				
				
				// （1）、意向维护
				paramaResponseForCheck.setResponseProgress("1");// 意向订单确认

				// 需求发布者不得修改意向方填写的联系人和联系方式--20180108
				paramaResponseForCheck.setContact(null);
				paramaResponseForCheck.setIhome(null);
				responseService.updateResponseSelective(paramaResponseForCheck);
				// （2）、 修改本条需求进入订单确认状态"2"
				Demand paramDemandToUpdate = new Demand();
				paramDemandToUpdate.setId(demand.getId());
				paramDemandToUpdate.setDemandprogress("2");
				try {
					demandService.demandUpdate(paramDemandToUpdate);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("opResult", "2");
					return map;
				}

				// （3）、判断此需求是否为委托或者托管下的子需求，若为子需求则检验父委托的demandProgress和demandState
				if (demand.getDemandId() != null && demand.getDemandId() != 0) {
					// 得到父委托对象
					Demand parentDemand = demandService.demandFind(demand
							.getDemandId());
					// 判断父委托状态：
					if (parentDemand != null
							&& ("0".equals(parentDemand.getDemandprogress()) || "1"
									.equals(parentDemand.getDemandprogress()))) {
						// 当父需求状态仍然为"0":需求发布或者"1"意向征集时 将父需求状态改成"2":订单确认
						Demand paramDemand = new Demand();
						paramDemand.setId(parentDemand.getId());
						paramDemand.setDemandprogress("2");
						try {
							demandService.demandUpdate(paramDemand);
						} catch (Exception e) {
							e.printStackTrace();
							map.put("opResult", "2");
							return map;
						}
					}
				}
				map.put("opResult", "0");
				System.out.println("我是发布者--->选定成功");

				// 发布者选定消息推送
				sendMessageWhenReleaseCheck(paramaResponseForCheck, demand);

				return map;
			}

			// 2、*******************发布者取消选定(只进行状态维护即可)
			// （1）、意向维护：发布者、响应者都为未选定
			else if ("1".equals(paramaResponseForCheck.getReleaseselected())) {
				Response re = new Response();
				re.setId(response.getId());
				re.setReleaseselected(paramaResponseForCheck
						.getReleaseselected());
				re.setResponseselected("1");// 若发布者为未选定，则响应者肯定不能选定
				re.setResponseProgress("0");// 意向征集
				responseService.updateResponseSelective(re);
				// （2）、本条需求状态维护：将本条需求状态进行回退到意向征集状态"1"
				Demand paramDemandToUpdate = new Demand();
				paramDemandToUpdate.setId(demand.getId());
				paramDemandToUpdate.setDemandprogress("1");
				try {
					demandService.demandUpdate(paramDemandToUpdate);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("opResult", "2");
					return map;
				}

				// （3）、判断此需求是否为委托或者托管下的子需求，若为子需求则检验父委托的demandProgress和demandState
				if (demand.getDemandId() != null && demand.getDemandId() != 0) {
					// 得到父委托对象
					Demand parentDemand = demandService.demandFind(demand
							.getDemandId());
					// 判断父委托状态：找出所有子需求，若所有子需求都为"0"或"1",则将父需求修改为"1"意向征集
					if (parentDemand != null) {
						boolean parentNeedChange = true;
						// 查出所有子需求
						List<Demand> sonDemands = demandService
								.selectSonDemandsByParentId(parentDemand
										.getId());
						if (sonDemands.size() > 0) {
							for (Demand d : sonDemands) {
								// 若存在不为"0"或"1"或"3"关闭的子需求就不用改父需求为"1"
								if (!("0".equals(d.getDemandprogress())
										|| "1".equals(d.getDemandprogress()) || "3"
											.equals(d.getDemandprogress()))) {
									parentNeedChange = false;
								}
							}
						}
						if (parentNeedChange) {
							Demand toUpdateParentDemand = new Demand();
							toUpdateParentDemand.setId(parentDemand.getId());
							toUpdateParentDemand.setDemandprogress("1");
							try {
								demandService
										.demandUpdate(toUpdateParentDemand);
							} catch (Exception e) {
								e.printStackTrace();
								map.put("opResult", "2");
								return map;
							}
						}

					}
				}

				map.put("opResult", "0");
				System.out.println("我是发布者--->取消选定成功");

				// 发布者取消选定消息推送
				sendMessageWhenReleaseRefuse(paramaResponseForCheck, demand);

				return map;
			}
			// 二、响应者访问
		} else if (UserContext.getUser().getId().longValue() == response
				.getEmployeeId().longValue()) {
			Response re = new Response();
			re.setId(response.getId());
			re.setResponseselected(paramaResponseForCheck.getResponseselected());
			// 响应者不管是选定还是拒绝并撤回先修改本条意向的状态
			responseService.updateResponseSelective(re);

			/*
			 * 双方意向确认之后，状态变更: 该发布（意向）进入订单， 同时其他意向方状态变更为落选,
			 * 私聊结束，不能再进行回复，只能查看聊天记录。--当该意向被需求发布者选定之后才可能到此步骤，即响应者确认方案
			 */
			Response checkResponse = responseService
					.selectByPrimaryKey(response.getId());
			//检验该意向是否为"订单确认"状态"1"来判定是否有权进行下一步操作
			if (!"1".equals(checkResponse.getResponseProgress().trim())) {
				map.put("opResult", "3");
				map.put("msg", "该意向还无权进入此操作！");
				return map;
			}
			// 1、*******************响应者者选定(只进行状态维护即可)
			if ("0".equals(paramaResponseForCheck.getReleaseselected())
					&& "0".equals(paramaResponseForCheck.getResponseselected())) {
 
				//防止重复操作--20180118
				if("6".equals(checkResponse.getResponseProgress().trim())){//如果已经是"订单完成"状态"6"说明本次访问是重复操作
					map.put("opResult", "3");
					map.put("msg", "请不要重复确认方案！");
					return map;
				}
				
				
				// 转入、支付意向金//需判断响应者为机场还是航司进而确认交易类型
				Map<String, Object> accountRechargeMap = new HashMap<String, Object>();
				RetBean reChargeRetBean = new RetBean();
				TransactionRecord reChargeRecord = new TransactionRecord();
				reChargeRecord.setNumber("50000");// 暂时写死
				reChargeRecord.setDemandId(checkResponse.getDemandId());// 设置需求id
				Long toAccountEmployeeId = null;// 目标帐户人
				// 判断交易类型
				if (UserContext.getUser().getId().longValue() == checkResponse
						.getEmployeeId().longValue()
						&& "1".equals(demand.getDemandtype())) {// 响应者是机场，机场确认方案，交易类型为"支付"---响应的运力需求---20180115
					reChargeRecord.setType("06");// 支付 机场是响应者确认方案
					toAccountEmployeeId = demand.getEmployeeId();
				} else if (UserContext.getUser().getId().longValue() == checkResponse
						.getEmployeeId().longValue()
						&& "0".equals(demand.getDemandtype())) {// 响应者是航司，航司确认方案，交易类型为"转入"---响应的航线需求---20180115
					reChargeRecord.setType("05");// 转入 航司是响应者确认方案
					toAccountEmployeeId = demand.getEmployeeId();
				}
				// 操作账户
				accountRechargeMap = individualCapitalAccountService
						.rechargeIntentionMoney(reChargeRecord, UserContext
								.getUser().getId(), toAccountEmployeeId);
				reChargeRetBean = (RetBean) accountRechargeMap.get("retBean");
				if (reChargeRetBean.isBool()) {
					map.put("opResult", "4");// 账户表操作失败
					return map;
				} else {
					// 账户表转入、支付成功，处理交易记录(收入表和支出表)
					IndividualCapitalAccount account = (IndividualCapitalAccount) accountRechargeMap
							.get("account");
					IndividualCapitalAccount toAccount = (IndividualCapitalAccount) accountRechargeMap
							.get("toAccount");
					MoneyIncomeRecord iirecord = new MoneyIncomeRecord();
					SpendingRecord ssRecord = new SpendingRecord();

					if ("05".equals(reChargeRecord.getType())) {
						iirecord.setAccountId(account.getId());
						ssRecord.setAccountId(toAccount.getId());

						iirecord.setToAccountId(toAccount.getId());
						ssRecord.setToAccountId(account.getId());
					}
					if ("06".equals(reChargeRecord.getType())) {
						iirecord.setAccountId(toAccount.getId());
						ssRecord.setAccountId(account.getId());

						iirecord.setToAccountId(account.getId());
						ssRecord.setToAccountId(toAccount.getId());
					}

					iirecord.setNumber(reChargeRecord.getNumber());
					ssRecord.setNumber(reChargeRecord.getNumber());

					iirecord.setDemandId(demand.getId());
					ssRecord.setDemandId(demand.getId());

					iirecord.setType("05");// 转入
					ssRecord.setType("06");// 支付

					RetBean iiiRetBean = moneyIncomeRecordService
							.incomeIntentionMoney(iirecord);
					RetBean sssRetBean = spendingRecordService
							.spendingIntentionMoney(ssRecord);
					if (iiiRetBean.isBool() || sssRetBean.isBool()) {
						map.put("opResult", "5");// 交易记录表插入失败
						return map;
					}

				}

				// 此时双方都选定，意向进入订单完成"6"，修改其他意向为落选状态"4"
				response.setResponseProgress("6");
				response.setResponseselected("0");
				// （1）、本条意向修改为"订单完成状态"
				responseService.updateResponseSelective(response);

				// 通过需求id查出所有响应
				Response paramResponse = new Response();
				paramResponse.setDemandId(demand.getId());
				List<Response> allResponses = responseService
						.selectByResponse(paramResponse);
				for (Response resultResponse : allResponses) {
					// （2）、设置其他意向为落选状态--设置其他未撤回的意向为落选状态20180124
					Response paramToOthrResponse = new Response();
					if (!resultResponse.getId().equals(response.getId())
							&&!"2".equals(resultResponse.getResponseProgress().trim())) {

						// 落选意向中若为机场的意向则解冻其意向金--若为针对“运力需求”的意向落选且意向状态不为“已撤回2”则解冻意向方的意向金--20180115
						if ("1".equals(demand.getDemandtype())
								&& !"2".equals(resultResponse
										.getResponseProgress())) {
							// 1、解冻2、插入收入表
							// 1、解冻
							Map<String, Object> accountUnfreezeMap = new HashMap<String, Object>();
							RetBean retBean = new RetBean();
							TransactionRecord record = new TransactionRecord();
							record.setNumber("50000");// 暂时写死
							record.setDemandId(resultResponse.getDemandId());// 设置需求id
							record.setType("0403");// 解冻（意向落选）
							// 操作账户
							accountUnfreezeMap = individualCapitalAccountService
									.changeIntentionMoney(record,
											resultResponse.getEmployeeId());
							retBean = (RetBean) accountUnfreezeMap
									.get("retBean");
							if (retBean.isBool()) {
								map.put("opResult", "4");// 账户表解冻失败
								return map;
							} else {
								// 账户表解冻成功，处理交易记录(收入表)
								IndividualCapitalAccount account = (IndividualCapitalAccount) accountUnfreezeMap
										.get("account");
								MoneyIncomeRecord irecord = new MoneyIncomeRecord();
								irecord.setAccountId(account.getId());
								irecord.setNumber(record.getNumber());
								irecord.setDemandId(resultResponse
										.getDemandId());
								irecord.setType(record.getType());
								RetBean iRetBean = moneyIncomeRecordService
										.unfreezeIntentionMoney(irecord);
								if (iRetBean.isBool()) {
									map.put("opResult", "5");// 交易记录表插入失败
									return map;
								}

							}

						}

						paramToOthrResponse.setId(resultResponse.getId());
						paramToOthrResponse.setResponseProgress("4");
						paramToOthrResponse.setIntentionmoneystate("1");// 意向金状态设为未交""
						responseService
								.updateResponseSelective(paramToOthrResponse);

					}
				}

				// （2）、修改本条需求进入订单完成状态"4"
				Demand paramDemandToUpdate = new Demand();
				paramDemandToUpdate.setId(demand.getId());
				paramDemandToUpdate.setDemandprogress("4");

				// 判断此需求是否为委托或者托管下的子需求，若为子需求则检验父委托的demandProgress和demandState
				Demand parentDemand = null;
				if (demand.getDemandId() != null
						&& demand.getDemandId().longValue() != 0) {
					// 得到父委托对象
					parentDemand = demandService.demandFind(demand
							.getDemandId());
					// 判断父委托状态：
					if (parentDemand != null
							&& ("0".equals(parentDemand.getDemandprogress())
									|| "1".equals(parentDemand
											.getDemandprogress()) || "2"
										.equals(parentDemand
												.getDemandprogress()))) {
						// （3）、当父需求状态仍然为"2"订单确认时 将父需求状态改成"4":订单完成
						Demand paramDemand = new Demand();
						paramDemand.setId(parentDemand.getId());
						paramDemand.setDemandprogress("4");
						try {
							demandService.demandUpdate(paramDemand);
						} catch (Exception e) {
							e.printStackTrace();
							map.put("opResult", "2");
							return map;
						}
					}
				}
				// 父需求修改完再修改本条需求
				try {
					demandService.demandUpdate(paramDemandToUpdate);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("opResult", "2");
					return map;
				}
				map.put("opResult", "0");
				System.out.println("响应者-----》确认方案成功");

				// 消息推送
				sendMessageWhenResponseCheck(response, demand, allResponses,
						parentDemand);
				return map;
				// 2、*******************响应者取消选定(只进行状态维护即可)
			} else if ("0".equals(paramaResponseForCheck.getReleaseselected())
					&& "1".equals(paramaResponseForCheck.getResponseselected())) {
				
				//防止重复操作--20180118
				if("2".equals(checkResponse.getResponseProgress().trim())){//如果已经是"订单完成"状态"6"说明本次访问是重复操作
					map.put("opResult", "3");
					map.put("msg", "请不要重复拒绝！");
					return map;
				}
				
				
				// 响应者拒绝并撤回:(1)、修改此意向为已拒绝"2"删除意向---20171216;(2)、此需求修改为意向征集“1”
				// (4)、若为子需求则校验父需求状态
				// (1)、本条意向修改为已拒绝"2"---删除
				response.setResponseProgress("2");
				response.setResponseselected("1");
				response.setReleaseselected("1");
				response.setIntentionmoneystate("1");
				responseService.updateResponseSelective(response);// --20180112修改意向撤回或取消永不删除，只改

				// 若为机场拒绝则解冻其意向金--若为“运力需求”响应方拒绝则解冻响应方意向金--20180115
				if ("1".equals(demand.getDemandtype())) {
					// 1、解冻2、插入收入表
					// 1、解冻
					Map<String, Object> accountUnfreezeMap = new HashMap<String, Object>();
					RetBean retBean = new RetBean();
					TransactionRecord record = new TransactionRecord();
					record.setNumber("50000");// 暂时写死
					record.setDemandId(response.getDemandId());// 设置需求id
					record.setType("0401");// 解冻（意向落选）
					// 操作账户
					accountUnfreezeMap = individualCapitalAccountService
							.changeIntentionMoney(record, UserContext.getUser()
									.getId());
					retBean = (RetBean) accountUnfreezeMap.get("retBean");
					if (retBean.isBool()) {
						map.put("opResult", "4");// 账户表解冻失败
						return map;
					} else {
						// 账户表解冻成功，处理交易记录(收入表)
						IndividualCapitalAccount account = (IndividualCapitalAccount) accountUnfreezeMap
								.get("account");
						MoneyIncomeRecord irecord = new MoneyIncomeRecord();
						irecord.setAccountId(account.getId());
						irecord.setNumber(record.getNumber());
						irecord.setDemandId(response.getDemandId());
						irecord.setType(record.getType());
						RetBean iRetBean = moneyIncomeRecordService
								.unfreezeIntentionMoney(irecord);
						if (iRetBean.isBool()) {
							map.put("opResult", "5");// 交易记录表插入失败
							return map;
						}

					}
				}

				// (2)、修改此需求为意向征集状态"1"
				Demand paramDemandToUpdate = new Demand();
				paramDemandToUpdate.setId(demand.getId());
				paramDemandToUpdate.setDemandprogress("1");
				try {
					demandService.demandUpdate(paramDemandToUpdate);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("opResult", "2");
					return map;
				}

				// （3）、判断此需求是否为委托或者托管下的子需求，若为子需求则检验父委托的demandProgress和demandState
				if (demand.getDemandId() != null && demand.getDemandId() != 0) {
					// 得到父委托对象
					Demand parentDemand = demandService.demandFind(demand
							.getDemandId());
					// 判断父委托状态：求子需求最高状态为父需求状态
					if (parentDemand != null) {
						// 查出所有子需求
						List<Demand> sonDemands = demandService
								.selectSonDemandsByParentId(parentDemand
										.getId());
						int state = Integer.parseInt(parentDemand
								.getDemandprogress());
						if (sonDemands.size() > 0) {
							// 求子需求最高状态
							state = sonDemandsMaxState(sonDemands, state);
						}
						// 若此时父委托状态不等于子需求最高状态时再修改父委托状态，否则不用修改
						if (!(state + "").equals(parentDemand
								.getDemandprogress())) {
							Demand toUpdateParentDemand = new Demand();
							toUpdateParentDemand.setId(parentDemand.getId());
							toUpdateParentDemand.setDemandprogress(state + "");
							try {
								demandService
										.demandUpdate(toUpdateParentDemand);
							} catch (Exception e) {
								e.printStackTrace();
								map.put("opResult", "2");
								return map;
							}
						}

					}
				}
				map.put("opResult", "0");
				System.out.println("响应者选定拒绝并撤回");

				// 消息推送
				sendMessageWhenResponseRefuse(response, demand);
				return map;
			}
		}
		return map;
	}

	/*
	 * 消息推送：需求发布方选定1、需求方： 时间-XX需求已选定意向方，等待对方确认交易2、被选定的意向方：
	 * 时间-XX需求你的意向已被需求方选定，请尽快确认交易
	 */
	private boolean sendMessageWhenReleaseCheck(Response response, Demand demand) {
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
		// 消息title
		chat.setTitle("需求状态更新");
		// 1、需求方
		chat.setToNameId(UserContext.getUser().getId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "已选定意向方，等待对方确认消息");
		result = chatService.insertChatSelective(chat);
		// 2、被选定的意向方
		chat.setToNameId(response.getEmployeeId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "你的意向已被需求方选定，请尽快确认交易");
		result = chatService.insertChatSelective(chat);
		return result;
	}

	/*
	 * 消息推送：需求发布方取消选定 1、需求方： 时间-XX需求已取消选定，请重新选择新的意向方 2、被选定的意向方：
	 * 时间-XX需求您的意向已被需求方取消选定
	 */
	private boolean sendMessageWhenReleaseRefuse(Response response,
			Demand demand) {
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
		// 消息title
		chat.setTitle("需求状态更新");
		// 1、需求方
		chat.setToNameId(UserContext.getUser().getId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "已取消选定，请重新选择新的意向方");
		result = chatService.insertChatSelective(chat);
		// 2、被取消选定的意向方
		chat.setToNameId(response.getEmployeeId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "您的意向已被需求方取消选定");
		result = chatService.insertChatSelective(chat);
		return result;
	}

	/*
	 * 消息推送：响应方确认方案1、需求方： 时间-XX需求方案已确认，订单完成2、被选定的意向方： 时间-XX需求方案确认成功，订单完成
	 * 3、落选的意向方： 时间-XX需求您已落选，该需求已对您关闭4、收藏的用户： 时间-XX需求已成交，该需求已对您关闭
	 * 5、委托方：（若为委托或托管时发送该消息，此时的demandTitle为父委托的title） 时间-XX委托已完成，请点击查看详情
	 */
	private boolean sendMessageWhenResponseCheck(Response response,
			Demand demand, List<Response> allResponses, Demand parentDemand) {
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
		// 消息title
		chat.setTitle("需求状态更新");
		// 避免重复发送的集合
		List<Long> idList = new ArrayList<>();
		// 1、需求方
		chat.setToNameId(demand.getEmployeeId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "方案已确定，订单完成");
		result = chatService.insertChatSelective(chat);
		idList.add(chat.getToNameId());
		// 2、确认方案的意向方
		chat.setToNameId(UserContext.getUser().getId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "确认成功，订单完成");
		result = chatService.insertChatSelective(chat);
		idList.add(chat.getToNameId());
		// 3、落选的意向方
		if (allResponses.size() > 0) {
			for (Response r : allResponses) {
				// 落选意向
				if (response.getId().longValue() != r.getId().longValue()) {
					chat.setToNameId(r.getEmployeeId());
					chat.setText(demand.getTitle() + demand.getDemandtypeStr()
							+ "您已落选，该需求已对您关闭");
					result = chatService.insertChatSelective(chat);
					idList.add(chat.getToNameId());
				}
			}
		}
		// 4、收藏的用户//取消对收藏方的消息推送--20180201与谢强确认
//		List<Collect> collects = collectService.selectCollectsByDemandId(demand
//				.getId());
//		if (collects.size() > 0) {
//			for (Collect cl : collects) {
//				if (!idList.contains(cl.getEmployeeId())) {// 前三项没发过消息时
//					chat.setToNameId(cl.getEmployeeId());
//					chat.setText(demand.getTitle() + demand.getDemandtypeStr()
//							+ "已成交，该需求已对您关闭");
//					result = chatService.insertChatSelective(chat);
//					idList.add(chat.getToNameId());
//				}
//			}
//		}

		// 5、委托方：（若为委托或托管时发送该消息，此时的demandTitle为父委托的title）
		if (demand.getDemandId() != null
				&& demand.getDemandId().longValue() != 0
				&& parentDemand != null) {
			chat.setToNameId(parentDemand.getEmployeeId());
			chat.setText(parentDemand.getTitle()
					+ parentDemand.getDemandtypeStr() + "已完成");
			result = chatService.insertChatSelective(chat);
		}
		return result;
	}

	/*
	 * 消息推送：响应方拒绝并撤回意向1、需求方： 时间-XX需求方案被对方取消，请重新选择意向方2、被选定的意向方：
	 * 时间-XX需求的意向方案已拒绝，意向已撤回
	 */
	private boolean sendMessageWhenResponseRefuse(Response response,
			Demand demand) {
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
		// 消息title
		chat.setTitle("需求状态更新");
		// 1、需求方
		chat.setToNameId(demand.getEmployeeId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "方案被对方取消，请重新选择意向方");
		result = chatService.insertChatSelective(chat);
		// 2、被选定的意向方
		chat.setToNameId(response.getEmployeeId());
		chat.setText(demand.getTitle() + demand.getDemandtypeStr()
				+ "的意向方案已拒绝，意向已撤回");
		result = chatService.insertChatSelective(chat);
		return result;
	}

	/**
	 * @param sonDemands
	 * @param state
	 *            //父需求的状态
	 * @return 求子需求最高状态
	 */
	private int sonDemandsMaxState(List<Demand> sonDemands, int state) {

		// 得到子需求state集合并去重；
		List<Integer> stateList = new ArrayList<Integer>();

		for (int i = 0; i < sonDemands.size(); i++) {
			if (!stateList.contains(Integer.parseInt(sonDemands.get(i)
					.getDemandprogress()))) {
				stateList.add(Integer.parseInt(sonDemands.get(i)
						.getDemandprogress()));
			}
		}
		Map<Integer, Integer> orgStateMap = new HashMap<Integer, Integer>();
		orgStateMap.put(0, 10);
		orgStateMap.put(1, 3);
		orgStateMap.put(2, 7);
		orgStateMap.put(3, 9);
		orgStateMap.put(4, 8);
		orgStateMap.put(5, 0);
		orgStateMap.put(6, 1);
		orgStateMap.put(7, 2);
		orgStateMap.put(8, 4);
		orgStateMap.put(9, 5);
		orgStateMap.put(10, 6);
		Map<Integer, Integer> stateMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < 11; i++) {
			if (stateList.contains(orgStateMap.get(i))) {
				stateMap.put(i, orgStateMap.get(i));
			} else {
				stateMap.put(i, -1);
			}
		}
		for (int i = 0; i < 11; i++) {
			if (stateMap.get(i) != -1) {
				state = stateMap.get(i);
			}
		}
		return state;
	}

}
