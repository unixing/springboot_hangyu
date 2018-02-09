package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.SimpleDemand;
import org.ldd.ssm.hangyu.mapper.ChatMapper;
import org.ldd.ssm.hangyu.query.ChatDto;
import org.ldd.ssm.hangyu.service.IChatService;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ChatServiceImpl implements IChatService {
	@Autowired
	private ChatMapper chatMapper;

	Logger log = Logger.getLogger(ChatServiceImpl.class);
	
	@Override
	public List<Long> employees(String userName) {
		
		return chatMapper.employees(userName);
	}

	@Override
	public List<Chat> employeesChat(Long fromNameId, Long toNameId) {
		
		return chatMapper.employeesChat(fromNameId,toNameId);
	}

	@Override
	public boolean updateState(ChatDto dto) {
		 chatMapper.updateState(dto);      
		 return true;
	}

	@Override
	public List<Map<String, Object>> findChatList(ChatDto chat) {
		List<ChatDto> objectList=chatMapper.findChatObject(chat);
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		for(ChatDto dto:objectList){
			dto.setStartIndex(chat.getPageNo() * (chat.getPage() - 1));
			dto.setPageNo(chat.getPageNo());
			list.add(getChatDetail(dto));
		}
		return list;
	}
	
	public Map<String, Object> getSystemMessage(ChatDto chat) {
		Map<String, Object> map=new HashMap<String, Object>();
		PageBean<Chat> pbChatList = null;
		
		//聊天记录 分页
		int totalCount=chatMapper.getSystemMessageCount(chat);
		int noReadCount=chatMapper.getNoReadSystemRecordCount(chat);
		List<Chat> systemList=chatMapper.findSystemMessage(chat);
		pbChatList = new PageBean<Chat>(totalCount,systemList,chat.getPageNo());
		
		List<SimpleDemand> simpleDemandUpList=new ArrayList<SimpleDemand>();
		String title="";
			Map<String,String> companyInfo=chatMapper.getCompanyInfo();
			for(String key:companyInfo.keySet()){
				simpleDemandUpList.add(new  SimpleDemand(key,companyInfo.get(key)));
			}
			title="系统消息";
		map.put("title",title);
		map.put("rightTableUp",simpleDemandUpList);
		map.put("chatRcord",pbChatList);
		map.put("noReadCount",noReadCount);
		return map;
	}

	@Override
	public Map<String, Object> getChatDetail(ChatDto chat) {
		Map<String, Object> map=new HashMap<String, Object>();
		PageBean<Chat> pbChatList = null;
		PageBean<Chat> pbModifyList = null;
		
		List<SimpleDemand> chatObjectList=new ArrayList<SimpleDemand>();
		
		Map<String,String> chatObject=chatMapper.getChatObject(chat);
		if(null!=chatObject){
			for(String key:chatObject.keySet()){
				chatObjectList.add(new  SimpleDemand(key,chatObject.get(key)));
			}
		}
		//聊天记录 分页
		int totalCount=chatMapper.getChatRecordCount(chat);
		int noReadCount=chatMapper.getNoReadChatRecordCount(chat);
		List<Chat> chatList=chatMapper.findChatRecord(chat);
		pbChatList = new PageBean<Chat>(totalCount,chatList,chat.getPageNo());
		
		//List<SimpleDemand> simpleDemandList=new ArrayList<SimpleDemand>();
		List<SimpleDemand> simpleDemandUpList=new ArrayList<SimpleDemand>();
		List<SimpleDemand> simpleDemandDownList=new ArrayList<SimpleDemand>();
		String title="";
		String demandState = "";   // 需求状态
		String demandType = "";    // 需求类型
		String demandProgress = "";    // 需求类型
		if(null==chat.getDemandId()){//太美客服 右侧为公司信息
			Map<String,String> companyInfo=chatMapper.getCompanyInfo();
			for(String key:companyInfo.keySet()){
				simpleDemandUpList.add(new  SimpleDemand(key,companyInfo.get(key)));
			}
			title="与太美客服聊天中...";
		}else{//右侧为需求信息
			Demand demand=chatMapper.getDemandDtail(chat.getDemandId());
			if(null==demand){
				return null;
			}
			demandType = demand.getDemandtype();
			demandState = demand.getDemandstate();
			demandProgress = demand.getDemandprogress();
			//判断需求具体类型 返回的具体值
			//0:航线需求、1:运力投放、2:运营托管、3:航线委托、4:运力委托
			if(null!=demand.getDemandtype()){
				if("0".equals(demand.getDemandtype())){
					//返回意向详情
					title=demand.getTitle()+"找运力";
					List<Chat> modifyList=chatMapper.findModifyRecord(chat);
					int modifyTotalCount=chatMapper.getModifyRecordCount(chat);
					pbModifyList = new PageBean<Chat>(modifyTotalCount,modifyList,chat.getPageNo());
					
					//返回响应详情
					Demand response=chatMapper.getResponseDtail(chat);
					
					if(null!=response){
						/*simpleDemandList.add(new SimpleDemand("联系人",response.getContact()));
						simpleDemandList.add(new SimpleDemand("联系方式",response.getiHome()));*/
						if(null!=response.getDptState() && !response.getDptState().isEmpty()){
							if("0".equals(response.getDptState())){
								String dpt=response.getDpt();
								if("0".equals(response.getDptAcceptnearairport())){
									dpt=dpt+"&接受临近机场";
								}
								simpleDemandUpList.add(new SimpleDemand("始发机场",dpt));
							}else if("0".equals(response.getDptState())){
								simpleDemandUpList.add(new SimpleDemand("始发区域",response.getDpt()));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("始发机场",""));
						}
						if(null!=response.getDptTimeresources() && !response.getDptTimeresources().isEmpty()){
							if("0".equals(response.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间",response.getDptTime()));
							}else if("1".equals(response.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
							}else if("2".equals(response.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("出港时间",""));
						}
						
						
						if(null!=response.getPstState() && !response.getPstState().isEmpty()){
							if("0".equals(response.getPstState())){
								String pst=response.getPst();
								if("0".equals(response.getDptAcceptnearairport())){
									pst=pst+"&接受临近机场";
								}
								simpleDemandUpList.add(new SimpleDemand("经停机场",pst));
							}else if("1".equals(response.getPstState())){
								simpleDemandUpList.add(new SimpleDemand("经停区域",response.getPst()));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("经停机场",""));
						}
						
						if(null!=response.getPstTimeresources() && !response.getPstTimeresources().isEmpty()){
							if("0".equals(response.getPstTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间",response.getDptTime()));
							}else if("1".equals(response.getPstTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
							}else if("2".equals(response.getPstTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("出港时间",""));
						}
						
						if(null!=response.getArrvState() && !response.getArrvState().isEmpty()){
							if("0".equals(response.getArrvState())){
								String arrv=response.getArrv();
								if("0".equals(response.getDptAcceptnearairport())){
									arrv=arrv+"&接受临近机场";
								}
								simpleDemandUpList.add(new SimpleDemand("到达机场",arrv));
							}else if("1".equals(response.getArrvState())){
								simpleDemandUpList.add(new SimpleDemand("到达区域",response.getArrv()));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("到达机场",""));
						}
						/*if(response.getArrvTimeresources().equals("0")){
							simpleDemandUpList.add(new SimpleDemand("出港时间",response.getDptTime()));
						}else if(response.getArrvTimeresources().equals("1")){
							simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
						}else if(response.getArrvTimeresources().equals("2")){
							simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
						}*/
						
						simpleDemandDownList.add(new SimpleDemand("拟开时间",response.getSailingtime()));
						simpleDemandDownList.add(new SimpleDemand("拟开班期",response.getDays()));
						simpleDemandDownList.add(new SimpleDemand("拟飞机型",response.getAircrfttyp()));
						simpleDemandDownList.add(new SimpleDemand("座位数",response.getSeating()));
						simpleDemandDownList.add(new SimpleDemand("客量期望",response.getAvgguestexpect()+""));
					/*	simpleDemandDownList.add(new SimpleDemand("客座率期望",response.getLoadfactorsexpect()+""));
						if(response.getSubsidypolicy().equals("0")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-定补"));
						}else if(response.getSubsidypolicy().equals("1")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-保底"));
						}else if(response.getSubsidypolicy().equals("2")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-人头补"));
						}else if(response.getSubsidypolicy().equals("3")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-其他"));
						}else if(response.getSubsidypolicy().equals("4")){
							simpleDemandList.add(new SimpleDemand("补贴政策","补贴政策待议"));
						}else if(response.getSubsidypolicy().equals("5")){
							simpleDemandList.add(new SimpleDemand("补贴政策","无补贴政策"));
						}
						simpleDemandList.add(new SimpleDemand("拦标价格",response.getBlockbidprice()+""));
						simpleDemandList.add(new SimpleDemand("其他说明",response.getRemark()));
						simpleDemandList.add(new SimpleDemand("发布有效期",response.getPeriodValidity()));
						*/
					}
					
				}else if("1".equals(demand.getDemandtype())){
					//返回意向详情
					title=demand.getTitle()+"找航线";
					
					List<Chat> modifyList=chatMapper.findModifyRecord(chat);
					int modifyTotalCount=chatMapper.getModifyRecordCount(chat);
					pbModifyList = new PageBean<Chat>(modifyTotalCount,modifyList,chat.getPageNo());
					//返回响应详情
					Demand response=chatMapper.getResponseDtail(chat);
					if(null!=response){
						/*simpleDemandList.add(new SimpleDemand("联系人",response.getContact()));
						simpleDemandList.add(new SimpleDemand("联系方式",response.getiHome()));*/
						if(null!=response.getDptState() && !response.getDptState().isEmpty()){
							if("0".equals(response.getDptState())){
								String dpt=response.getDpt();
								if("0".equals(response.getDptAcceptnearairport())){
									dpt=dpt+"&接受临近机场";
								}
								simpleDemandUpList.add(new SimpleDemand("始发机场",dpt));
							}else if("0".equals(response.getDptState())){
								simpleDemandUpList.add(new SimpleDemand("始发区域",response.getDpt()));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("始发机场",""));
						}
						
						if(null!=response.getDptTimeresources() && !response.getDptTimeresources().isEmpty()){
							if("0".equals(response.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间",response.getDptTime()));
							}else if("1".equals(response.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
							}else if("2".equals(response.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("出港时间",""));
						}
						
						if(null!=response.getPstState() && !response.getPstState().isEmpty()){
							if("0".equals(response.getPstState())){
								String pst=response.getPst();
								if("0".equals(response.getDptAcceptnearairport())){
									pst=pst+"&接受临近机场";
								}
								simpleDemandUpList.add(new SimpleDemand("经停机场",pst));
							}else if("0".equals(response.getPstState())){
								simpleDemandUpList.add(new SimpleDemand("经停区域",response.getPst()));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("经停机场",""));
						}
						
						if(null!=response.getPstTimeresources() && !response.getPstTimeresources().isEmpty()){
							if("0".equals(response.getPstTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间",response.getDptTime()));
							}else if("1".equals(response.getPstTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
							}else if("2".equals(response.getPstTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("出港时间",""));
						}
						
						if(null!=response.getArrvState() && !response.getArrvState().isEmpty()){
							if("0".equals(response.getArrvState())){
								String arrv=response.getArrv();
								if("0".equals(response.getDptAcceptnearairport())){
									arrv=arrv+"&接受临近机场";
								}
								simpleDemandUpList.add(new SimpleDemand("到达机场",arrv));
							}else if("0".equals(response.getArrvState())){
								simpleDemandUpList.add(new SimpleDemand("到达区域",response.getArrv()));
							}
						}else{
							simpleDemandUpList.add(new SimpleDemand("到达机场",""));
						}
						/*if(response.getArrvTimeresources().equals("0")){
							simpleDemandUpList.add(new SimpleDemand("出港时间",response.getDptTime()));
						}else if(response.getArrvTimeresources().equals("1")){
							simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
						}else if(response.getArrvTimeresources().equals("2")){
							simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
						}*/
						
						simpleDemandDownList.add(new SimpleDemand("拟开时间",response.getSailingtime()));
						simpleDemandDownList.add(new SimpleDemand("拟开班期",response.getDays()));
						simpleDemandDownList.add(new SimpleDemand("拟飞机型",response.getAircrfttyp()));
						simpleDemandDownList.add(new SimpleDemand("座位数",response.getSeating()));
						simpleDemandDownList.add(new SimpleDemand("客量期望",response.getAvgguestexpect()+""));
						/*simpleDemandDownList.add(new SimpleDemand("客座率期望",response.getLoadfactorsexpect()+""));
						if(response.getSubsidypolicy().equals("0")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-定补"));
						}else if(response.getSubsidypolicy().equals("1")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-保底"));
						}else if(response.getSubsidypolicy().equals("2")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-人头补"));
						}else if(response.getSubsidypolicy().equals("3")){
							simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-其他"));
						}else if(response.getSubsidypolicy().equals("4")){
							simpleDemandList.add(new SimpleDemand("补贴政策","补贴政策待议"));
						}else if(response.getSubsidypolicy().equals("5")){
							simpleDemandList.add(new SimpleDemand("补贴政策","无补贴政策"));
						}
						simpleDemandList.add(new SimpleDemand("拦标价格",response.getBlockbidprice()+""));
						simpleDemandList.add(new SimpleDemand("其他说明",response.getRemark()));
						simpleDemandList.add(new SimpleDemand("发布有效期",response.getPeriodValidity()));*/
					}
				}else if("2".equals(demand.getDemandtype())){
					//返回需求详情
					title=demand.getFltNbr()+"运营托管需求";
					simpleDemandUpList.add(new SimpleDemand("航班号",demand.getFltNbr()));
					simpleDemandUpList.add(new SimpleDemand("小时成本",(null==demand.getHourscost()?"":demand.getHourscost()+"万/小时")));
					simpleDemandUpList.add(new SimpleDemand("其他说明",demand.getRemark()));
					
					simpleDemandDownList.add(new SimpleDemand("联系人",demand.getContact()));
					simpleDemandDownList.add(new SimpleDemand("联系方式",demand.getiHome()));
					
				}else if("3".equals(demand.getDemandtype())){//航线委托
					//返回需求详情
					title=demand.getTitle()+"找运力";
					
				/*	simpleDemandList.add(new SimpleDemand("联系人",demand.getContact()));
					simpleDemandList.add(new SimpleDemand("联系方式",demand.getiHome()));*/
					if(null!=demand.getDptState() && !demand.getDptState().isEmpty()){
						if("0".equals(demand.getDptState())){
							String dpt=demand.getDpt();
							if("0".equals(demand.getDptAcceptnearairport())){
								dpt=dpt+"&接受临近机场";
							}
							simpleDemandUpList.add(new SimpleDemand("始发机场",dpt));
						}else if("0".equals(demand.getDptState())){
							simpleDemandUpList.add(new SimpleDemand("始发区域",demand.getDpt()));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("始发机场",""));
					}
					
					if(null!=demand.getDptTimeresources()&& !demand.getDptTimeresources().isEmpty()){
						if("0".equals(demand.getDptTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间",demand.getDptTime()));
						}else if("1".equals(demand.getDptTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
						}else if("2".equals(demand.getDptTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("出港时间",""));
					}
					
					if(null!=demand.getPstState() && !demand.getPstState().isEmpty()){
						if("0".equals(demand.getPstState())){
							String pst=demand.getPst();
							if("0".equals(demand.getDptAcceptnearairport())){
								pst=pst+"&接受临近机场";
							}
							simpleDemandUpList.add(new SimpleDemand("经停机场",pst));
						}else if("0".equals(demand.getPstState())){
							simpleDemandUpList.add(new SimpleDemand("经停区域",demand.getPst()));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("经停机场",""));
					}
					
					if(null!=demand.getPstTimeresources() && !demand.getPstTimeresources().isEmpty()){
						if("0".equals(demand.getPstTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间",demand.getDptTime()));
						}else if("1".equals(demand.getPstTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
						}else if("2".equals(demand.getPstTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("出港时间",""));
					}
					
					if(null!=demand.getArrvState() && !demand.getArrvState().isEmpty()){
						if("0".equals(demand.getArrvState())){
							String arrv=demand.getArrv();
							if("0".equals(demand.getDptAcceptnearairport())){
								arrv=arrv+"&接受临近机场";
							}
							simpleDemandUpList.add(new SimpleDemand("到达机场",arrv));
						}else if("0".equals(demand.getArrvState())){
							simpleDemandUpList.add(new SimpleDemand("到达区域",demand.getArrv()));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("到达机场",""));
					}
					/*
					if(demand.getArrvTimeresources().equals("0")){
						simpleDemandUpList.add(new SimpleDemand("出港时间",demand.getDptTime()));
					}else if(demand.getArrvTimeresources().equals("1")){
						simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
					}else if(demand.getArrvTimeresources().equals("2")){
						simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
					}*/
					
					simpleDemandDownList.add(new SimpleDemand("拟开时间",demand.getSailingtime()));
					simpleDemandDownList.add(new SimpleDemand("拟开班期",demand.getDays()));
					simpleDemandDownList.add(new SimpleDemand("拟飞机型",demand.getAircrfttyp()));
					simpleDemandDownList.add(new SimpleDemand("座位数",demand.getSeating()));
					simpleDemandDownList.add(new SimpleDemand("客量期望",demand.getAvgguestexpect()+""));
					/*simpleDemandList.add(new SimpleDemand("客座率期望",demand.getLoadfactorsexpect()+""));
					if(demand.getSubsidypolicy().equals("0")){
						simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-定补"));
					}else if(demand.getSubsidypolicy().equals("1")){
						simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-保底"));
					}else if(demand.getSubsidypolicy().equals("2")){
						simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-人头补"));
					}else if(demand.getSubsidypolicy().equals("3")){
						simpleDemandList.add(new SimpleDemand("补贴政策","有补贴-其他"));
					}else if(demand.getSubsidypolicy().equals("4")){
						simpleDemandList.add(new SimpleDemand("补贴政策","补贴政策待议"));
					}else if(demand.getSubsidypolicy().equals("5")){
						simpleDemandList.add(new SimpleDemand("补贴政策","无补贴政策"));
					}
					simpleDemandList.add(new SimpleDemand("拦标价格",demand.getBlockbidprice()+""));
					simpleDemandList.add(new SimpleDemand("其他说明",demand.getRemark()));
					simpleDemandList.add(new SimpleDemand("发布有效期",demand.getPeriodValidity()));
					*/
				}else if(demand.getDemandtype().equals("4")){//运力委托
					//返回需求详情
					title=demand.getTitle()+"找航线";
					/*simpleDemandList.add(new SimpleDemand("联系人",demand.getContact()));
					simpleDemandList.add(new SimpleDemand("联系方式",demand.getiHome()));
					simpleDemandList.add(new SimpleDemand("出港时刻",demand.getDptTime()));
					simpleDemandList.add(new SimpleDemand("班期",demand.getDays()));
					simpleDemandList.add(new SimpleDemand("始发地",demand.getDpt()));
					simpleDemandList.add(new SimpleDemand("经停地",demand.getPst()));
					simpleDemandList.add(new SimpleDemand("目的地",demand.getArrv()));
					
					simpleDemandList.add(new SimpleDemand("机型",demand.getAircrfttyp()));
					simpleDemandList.add(new SimpleDemand("运力基地",demand.getDpt()));
					simpleDemandList.add(new SimpleDemand("运力归属",demand.getAirCompany()));
					simpleDemandList.add(new SimpleDemand("座位布局",demand.getSeating()));
					if(!demand.getScheduling().isEmpty()){
						if(demand.getScheduling().equals("0")){
							simpleDemandList.add(new SimpleDemand("是否接受调度","接受"));
						}else if(demand.getScheduling().equals("1")){
							simpleDemandList.add(new SimpleDemand("是否接受调度","不接受"));
						}
					}
					simpleDemandList.add(new SimpleDemand("航班号",demand.getFltNbr()));
					simpleDemandList.add(new SimpleDemand("小时成本",(null==demand.getHourscost()?"":demand.getHourscost()+"万/小时")));
					simpleDemandList.add(new SimpleDemand("其他说明",demand.getRemark()));
					simpleDemandList.add(new SimpleDemand("发布有效期",demand.getPeriodValidity()));*/
					if(null!= demand.getDptState() && !demand.getDptState().isEmpty()){
						if("0".equals(demand.getDptState())){
							String dpt=demand.getDpt();
							if("0".equals(demand.getDptAcceptnearairport())){
								dpt=dpt+"&接受临近机场";
							}
							simpleDemandUpList.add(new SimpleDemand("始发机场",dpt));
						}else if("0".equals(demand.getDptState())){
							simpleDemandUpList.add(new SimpleDemand("始发区域",demand.getDpt()));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("始发机场",""));
					}
					
					 if(null!=demand.getDptTimeresources()&& !demand.getDptTimeresources().isEmpty()){
						 if("0".equals(demand.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间",demand.getDptTime()));
							}else if("1".equals(demand.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
							}else if("2".equals(demand.getDptTimeresources())){
								simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
							}
					 }else{
							simpleDemandUpList.add(new SimpleDemand("出港时间",""));
					}
					
					if(null!=demand.getPstState() && !demand.getPstState().isEmpty()){
						if("0".equals(demand.getPstState())){
							String pst=demand.getPst();
							if(demand.getDptAcceptnearairport().equals("0")){
								pst=pst+"&接受临近机场";
							}
							simpleDemandUpList.add(new SimpleDemand("经停机场",pst));
						}else if("0".equals(demand.getPstState())){
							simpleDemandUpList.add(new SimpleDemand("经停区域",demand.getPst()));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("经停机场",""));
					}
					
					if(null!=demand.getPstTimeresources() && !demand.getPstTimeresources().isEmpty()){
						if("0".equals(demand.getPstTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间",demand.getDptTime()));
						}else if("1".equals(demand.getPstTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
						}else if("2".equals(demand.getPstTimeresources())){
							simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("出港时间",""));
					}
					
					if(null!=demand.getArrvState() && !demand.getArrvState().isEmpty()){
						if("0".equals(demand.getArrvState())){
							String arrv=demand.getArrv();
							if("0".equals(demand.getDptAcceptnearairport())){
								arrv=arrv+"&接受临近机场";
							}
							simpleDemandUpList.add(new SimpleDemand("到达机场",arrv));
						}else if("0".equals(demand.getArrvState())){
							simpleDemandUpList.add(new SimpleDemand("到达区域",demand.getArrv()));
						}
					}else{
						simpleDemandUpList.add(new SimpleDemand("到达机场",""));
					}
					
				/*	if(demand.getArrvTimeresources().equals("0")){
						simpleDemandUpList.add(new SimpleDemand("出港时间",demand.getDptTime()));
					}else if(demand.getArrvTimeresources().equals("1")){
						simpleDemandUpList.add(new SimpleDemand("出港时间","待协调"));
					}else if(demand.getArrvTimeresources().equals("2")){
						simpleDemandUpList.add(new SimpleDemand("出港时间","时刻充足"));
					}*/
					
					simpleDemandDownList.add(new SimpleDemand("拟开时间",demand.getSailingtime()));
					simpleDemandDownList.add(new SimpleDemand("拟开班期",demand.getDays()));
					simpleDemandDownList.add(new SimpleDemand("拟飞机型",demand.getAircrfttyp()));
					simpleDemandDownList.add(new SimpleDemand("座位数",demand.getSeating()));
					simpleDemandDownList.add(new SimpleDemand("客量期望",demand.getAvgguestexpect()+""));
				}
			}
		}
		map.put("demandState", demandState);
		map.put("demandType", demandType);
		map.put("demandProgress", demandProgress);
		map.put("title",title);
		map.put("rightTableUp",simpleDemandUpList);
		map.put("rightTableDown",simpleDemandDownList);
		map.put("chatRcord",pbChatList);
		map.put("noReadCount",noReadCount);
		map.put("modifyRcord",pbModifyList);
		map.put("chatObjectList",chatObjectList);
		map.put("chatFlag",chat.getFromNameId()>chat.getToNameId()?chat.getToNameId()+"-"+chat.getFromNameId()+"-"+chat.getDemandId():chat.getFromNameId()+"-"+chat.getToNameId()+"-"+chat.getDemandId());
		return map;
	}

	@Override
	public boolean updateSystemState(ChatDto dto) {
		 chatMapper.updateSystemState(dto);      
		 return true;
	}

	@Override
	public boolean insertChatSelective(Chat chat) {
		boolean result=false;
		if (chat==null) {
			log.error("insertChatSelective:chat is null");
			return result;
		}
		try {
			int activelines=chatMapper.insertChatSelective(chat);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("insertChatSelective:there is something wrong here");
			return result;
		}
		return result;
	}
}
