package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Demand<T> implements Serializable {
    private boolean set = false;
    private Long id;//主键id

    private String intentionMoneyState;//发布需求者查询响应内容时,判断发布者是否提交了保证金状态(0:已缴纳,1:未缴纳)
    
    private Long employeeId;//用户id
    
    private String nickName;//发布人

    private String demandstate;//需求状态(0:正常,1:完成,2:异常,3:删除)
    
    private String rek;//审核不通过原因
    
    private String demandStateStr;//需求状态名称

    private String demandtype;//需求种类共3种（0:航线需求、1:运力投放、2:运营托管）但托管需求不再地图上呈现，仅在太美角色个人中心的委托列表中呈现。

    private String demandtypeStr;//需求类型名称
    
    private String releasetime;//发布日期

    private String title;//需求名称
    
    private String dptState;//0-机场，1-区域 2-城市
    
    private String dpt;//始发地
    
    private String dptNm;//始发地名称
    
    private String dptCt;
    
    private int collectType;//是否收藏 0-未收藏，其他已收藏
    
    private String CpyNm;//委托详情的委托方
    
    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }

    private String dptAcceptnearairport;//始发地是否接收临近机场(0:接收,1:不接收)

    private String dptAcceptnearairportStr;//始发地是否接受临近机场（接受/不接受）
    
    private String dptTimeresources;//始发地时刻资源(\r\n时刻资源三种状态\r\n0:有时刻（直接呈现时刻）dpt_time字段存放具体时刻值，\r\n1:待协调，\r\n2:时刻充足。)

    private String dptTimeresourcesStr;//始发地时刻（具体时刻/待协定/时刻充足）
    
    private String dptTime;//始发时刻资源具体时刻值

    private String pstState;
    
    private String pst;//经停地
    
    private String pstNm;//经停地名称
    
    private String pstCt;

    private String pstAcceptnearairport;//经停地是否接收临近机场(0:接收,1:不接受)

    private String pstAcceptnearairportStr;//经停地是否接受临近机场（接受/不接受）
    
    private String pstTimeresources;//经停地时刻资源(\r\n时刻资源三种状态\r\n0:有时刻（直接呈现时刻）pst_time字段存放具体时刻值，\r\n1:待协调，\r\n2:时刻充足。)

    private String pstTimeresourcesStr;//经停地时刻（具体时刻/待协定/时刻充足）
    
    private String pstTime;//经停地时刻资源具体时刻值

    private String arrvState;
    
    private String arrv;//到达地
    
    private String arrvNm;//到达地名称
    
    private String arrvCt;

    private String arrvAcceptnearairport;//到达是否接受临近机场(0:接收,1:不接收)

    private String arrvAcceptnearairportStr;//到达地是否接受临近机场（接受/不接受）
    
    private String arrvTimeresources;//始发地时刻资源(\r\n时刻资源三种状态\r\n0:有时刻（直接呈现时刻）arrv_time字段存放具体时刻值，\r\n1:待协调，\r\n2:时刻充足。)

    private String arrvTimeresourcesStr;//到达地时刻（具体时刻/待协定/时刻充足）
    
    private String arrvTime;//到达地时刻资源具体时刻值

    private String aircrfttyp;//机型

    private String days;//班期(下拉菜单选项)

    private Integer blockbidprice;//拦标价格（W）

    private Integer loadfactorsexpect;//客座率期望（%）

    private Integer avgguestexpect;//均班客座期望（人）

    private String subsidypolicy;//5.补贴有种状态：有补贴（0:定补、1:保底、2:人头补）3:待议4:无补贴。

    private String subsidypolicyStr;//补贴内容（有补贴/待议/无补贴）
    
    private String sailingtime;//开航时间

    private String seating;//座位数/座位布局
    
    private Long capacitycompany;//运力归属航司(aircompenyinfothree表id)
    
    private String capacitycompanyStr;

    private AirCompenyInfoThree capacityCompany;//运力归属航司

    private String scheduling;//是否接收调度(0:接收,1:不接收)
    
    private String schedulingStr;//是否接受调度（接受/不接受）
    
    private String schedulinePort;//接受调度的机场
    
    private List<AirportInfoNewThree> airportForSchedulines;//接受调度的机场对象列表

    private String hourscost;//小时成本（W/H）

    private String intendedairline;//是否有意向航线(0:有,本表主键id外键到intendedAirLine表demand_字段中,1:无)

    private String intendedairlineStr;//是否有意向航线（有/无）
    
    private String remark;//备注说明

    private String publicway;//公开方式(0:对所有人公开,1:对认证用户公开,2:定向航司,3:定向机场), 3和4定位目标在下一个字段

    private String publicwayStr;//公开方式（所有人/认证用户/定向航司/定向机场）
    
    private String directionalgoal;//定向目标表id (2:aircompenyinfothree,3:airportinfonewthree)
    
    private List<T> directions;

    private String demandprogress;//需求进度5种状态：（需求全局状态，所有用户看到的同一个需求的状态都是一致的。）\r\n  0:  发布阶段：新发的需求\r\n  1:  响应阶段：有响应者相应\r\n  2:  洽谈阶段：对于已经有响应者，进入响应状态的需求来说，发布者或响应者进行过变更（包括需求、响应以及消息）则算作洽谈状态。\r\n  3: 选择阶段：发布者选择了其中一个或多个响应者，等待相应响应者确认的状态。\r\n  4:  成交阶段：响应者确认，其他响应者落选状态。
    
    private String demandprogressStr;//需求进度名称（发布阶段/响应阶段/洽谈阶段/选择阶段/成交阶段）
    
    private String finishDate;//交易完成记录时间--20180116新增此字段
    
    private String contact;//联系人
    
    private String iHome;//联系人电话
    
    private String fltNbr;//航班号
    
    private String periodValidity;//需求发布有效期
    
    private SimpleDemand[] simpleDemand;//运力和航线显示封装
    
    private String dptFltLvl;//始发机场飞行等级
    
    private String pstFltLvl;//经停机场飞行等级
    
    private String arrvFltLvl;//到达机场飞行等级
    
    private String intendedDpt;//意向航线起始机场
    
    private String intendedPst;//意向航线经停机场
    
    private String intendedArrv;//意向航线到达机场
    
    private int renew;//0-未读，1-已读
    
    private int num;//获取该需求相关的意向数量
    
    private int collectId;//收藏id
    
    private List<IntendedAirline> intendedAirlines;//意向航线列表（暂定）

    private Long demandId;//父委托id
    
    private String employeeNm;//用户名
    
    private String airCompany;//运力归属航司
    
    private int unreadMessageCount;//未读消息总数
    
    private List<Employee> responseEmployees;//需求相关意向用户列表
    
    private String closeReason;
    
    public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getAirCompany() {
		return airCompany;
	}

	public void setAirCompany(String airCompany) {
		this.airCompany = airCompany;
	}

	public Long getDemandId() {
		return demandId;
	}

	public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDemandstate() {
        return demandstate;
    }

    public void setDemandstate(String demandstate) {
        this.demandstate = demandstate == null ? null : demandstate.trim();
    }

    public String getDemandtype() {
        return demandtype;
    }

    public void setDemandtype(String demandtype) {
        this.demandtype = demandtype == null ? null : demandtype.trim();
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime == null ? null : releasetime.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDpt() {
        return dpt;
    }

    public void setDpt(String dpt) {
        this.dpt = dpt == null ? null : dpt.trim();
    }

    public String getDptAcceptnearairport() {
        return dptAcceptnearairport;
    }

    public void setDptAcceptnearairport(String dptAcceptnearairport) {
        this.dptAcceptnearairport = dptAcceptnearairport == null ? null : dptAcceptnearairport.trim();
    }

    public String getDptTimeresources() {
        return dptTimeresources;
    }

    public void setDptTimeresources(String dptTimeresources) {
        this.dptTimeresources = dptTimeresources == null ? null : dptTimeresources.trim();
    }

    public String getDptTime() {
        return dptTime;
    }

    public void setDptTime(String dptTime) {
        this.dptTime = dptTime == null ? null : dptTime.trim();
    }

    public String getPst() {
        return pst;
    }

    public void setPst(String pst) {
        this.pst = pst == null ? null : pst.trim();
    }

    public String getPstAcceptnearairport() {
        return pstAcceptnearairport;
    }

    public void setPstAcceptnearairport(String pstAcceptnearairport) {
        this.pstAcceptnearairport = pstAcceptnearairport == null ? null : pstAcceptnearairport.trim();
    }

    public String getPstTimeresources() {
        return pstTimeresources;
    }

    public void setPstTimeresources(String pstTimeresources) {
        this.pstTimeresources = pstTimeresources == null ? null : pstTimeresources.trim();
    }

    public String getPstTime() {
        return pstTime;
    }

    public void setPstTime(String pstTime) {
        this.pstTime = pstTime == null ? null : pstTime.trim();
    }

    public String getArrv() {
        return arrv;
    }

    public void setArrv(String arrv) {
        this.arrv = arrv == null ? null : arrv.trim();
    }

    public String getArrvAcceptnearairport() {
        return arrvAcceptnearairport;
    }

    public void setArrvAcceptnearairport(String arrvAcceptnearairport) {
        this.arrvAcceptnearairport = arrvAcceptnearairport == null ? null : arrvAcceptnearairport.trim();
    }

    public String getArrvTimeresources() {
        return arrvTimeresources;
    }

    public void setArrvTimeresources(String arrvTimeresources) {
        this.arrvTimeresources = arrvTimeresources == null ? null : arrvTimeresources.trim();
    }

    public String getArrvTime() {
        return arrvTime;
    }

    public void setArrvTime(String arrvTime) {
        this.arrvTime = arrvTime == null ? null : arrvTime.trim();
    }

    public String getAircrfttyp() {
        return aircrfttyp;
    }

    public void setAircrfttyp(String aircrfttyp) {
        this.aircrfttyp = aircrfttyp == null ? null : aircrfttyp.trim();
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days == null ? null : days.trim();
    }

    public Integer getBlockbidprice() {
        return blockbidprice;
    }

    public void setBlockbidprice(Integer blockbidprice) {
        this.blockbidprice = blockbidprice;
    }

    public Integer getLoadfactorsexpect() {
        return loadfactorsexpect;
    }

    public void setLoadfactorsexpect(Integer loadfactorsexpect) {
        this.loadfactorsexpect = loadfactorsexpect;
    }

    public Integer getAvgguestexpect() {
        return avgguestexpect;
    }

    public void setAvgguestexpect(Integer avgguestexpect) {
        this.avgguestexpect = avgguestexpect;
    }

    public String getSubsidypolicy() {
        return subsidypolicy;
    }

    public void setSubsidypolicy(String subsidypolicy) {
        this.subsidypolicy = subsidypolicy == null ? null : subsidypolicy.trim();
    }

    public String getSailingtime() {
        return sailingtime;
    }

    public void setSailingtime(String sailingtime) {
        this.sailingtime = sailingtime == null ? null : sailingtime.trim();
    }

    public String getSeating() {
        return seating;
    }

    public void setSeating(String seating) {
        this.seating = seating == null ? null : seating.trim();
    }

    public Long getCapacitycompany() {
        return capacitycompany;
    }

    public void setCapacitycompany(Long capacitycompany) {
        this.capacitycompany = capacitycompany;
    }

    public AirCompenyInfoThree getCapacityCompany() {
        return capacityCompany;
    }

    public void setCapacityCompany(AirCompenyInfoThree capacityCompany) {
        this.capacityCompany = capacityCompany;
    }
    
    public String getScheduling() {
        return scheduling;
    }

    public void setScheduling(String scheduling) {
        this.scheduling = scheduling == null ? null : scheduling.trim();
    }

    public String getHourscost() {
        return hourscost;
    }

    public void setHourscost(String hourscost) {
        this.hourscost = hourscost;
    }

    public String getIntendedairline() {
        return intendedairline;
    }

    public void setIntendedairline(String intendedairline) {
        this.intendedairline = intendedairline == null ? null : intendedairline.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPublicway() {
        return publicway;
    }

    public void setPublicway(String publicway) {
        this.publicway = publicway == null ? null : publicway.trim();
    }

    public String getDirectionalgoal() {
        return directionalgoal;
    }

    public void setDirectionalgoal(String directionalgoal) {
        this.directionalgoal = directionalgoal;
    }

    public String getDemandprogress() {
        return demandprogress;
    }

    public void setDemandprogress(String demandprogress) {
        this.demandprogress = demandprogress == null ? null : demandprogress.trim();
    }

	public String getContact() {
		return contact==null ? null:contact;
	}

	public void setContact(String contact) {
		this.contact = contact == null ? null :contact.trim();
	}

	public String getiHome() {
		return iHome;
	}

	public void setiHome(String iHome) {
		this.iHome = iHome == null ? null : iHome.trim();
	}

	public String getFltNbr() {
		return fltNbr;
	}

	public void setFltNbr(String fltNbr) {
		this.fltNbr = fltNbr == null ? null : fltNbr.trim();
	}

	public String getPeriodValidity() {
		return periodValidity;
	}

	public void setPeriodValidity(String periodValidity) {
		this.periodValidity = periodValidity == null ? null : periodValidity.trim();
	}

	public String getDemandStateStr() {
		return demandStateStr;
	}

	public void setDemandStateStr(String demandStateStr) {
		this.demandStateStr = demandStateStr == null ? null :demandStateStr.trim();
	}

	public String getDemandtypeStr() {
		return demandtypeStr;
	}

	public void setDemandtypeStr(String demandtypeStr) {
		this.demandtypeStr = demandtypeStr == null ? null : demandtypeStr.trim();
	}

	public String getDptAcceptnearairportStr() {
		return dptAcceptnearairportStr;
	}

	public void setDptAcceptnearairportStr(String dptAcceptnearairportStr) {
		this.dptAcceptnearairportStr = dptAcceptnearairportStr == null ? null : dptAcceptnearairportStr.trim();
	}

	public String getDptTimeresourcesStr() {
		return dptTimeresourcesStr;
	}

	public void setDptTimeresourcesStr(String dptTimeresourcesStr) {
		this.dptTimeresourcesStr = dptTimeresourcesStr == null ? null : dptTimeresourcesStr.trim();
	}

	public String getPstAcceptnearairportStr() {
		return pstAcceptnearairportStr;
	}

	public void setPstAcceptnearairportStr(String pstAcceptnearairportStr) {
		this.pstAcceptnearairportStr = pstAcceptnearairportStr == null ? null : pstAcceptnearairportStr.trim();
	}

	public String getArrvAcceptnearairportStr() {
		return arrvAcceptnearairportStr;
	}

	public void setArrvAcceptnearairportStr(String arrvAcceptnearairportStr) {
		this.arrvAcceptnearairportStr = arrvAcceptnearairportStr == null ? null : arrvAcceptnearairportStr.trim();
	}

	public String getSubsidypolicyStr() {
		return subsidypolicyStr;
	}

	public void setSubsidypolicyStr(String subsidypolicyStr) {
		this.subsidypolicyStr = subsidypolicyStr == null ? null : subsidypolicyStr.trim();
	}

	public String getIntendedairlineStr() {
		return intendedairlineStr;
	}

	public void setIntendedairlineStr(String intendedairlineStr) {
		this.intendedairlineStr = intendedairlineStr == null ? null : intendedairlineStr.trim();
	}

	public String getPublicwayStr() {
		return publicwayStr;
	}

	public void setPublicwayStr(String publicwayStr) {
		this.publicwayStr = publicwayStr == null ? null : publicwayStr.trim();
	}

	public String getDemandprogressStr() {
		return demandprogressStr;
	}

	public void setDemandprogressStr(String demandprogressStr) {
		this.demandprogressStr = demandprogressStr == null ? null : demandprogressStr.trim();
	}

	public String getPstTimeresourcesStr() {
		return pstTimeresourcesStr;
	}

	public void setPstTimeresourcesStr(String pstTimeresourcesStr) {
		this.pstTimeresourcesStr = pstTimeresourcesStr == null ? null : pstTimeresourcesStr.trim();
	}

	public String getArrvTimeresourcesStr() {
		return arrvTimeresourcesStr;
	}

	public void setArrvTimeresourcesStr(String arrvTimeresourcesStr) {
		this.arrvTimeresourcesStr = arrvTimeresourcesStr == null ? null : arrvTimeresourcesStr.trim();
	}

	public String getSchedulingStr() {
		return schedulingStr;
	}

	public void setSchedulingStr(String schedulingStr) {
		this.schedulingStr = schedulingStr;
	}

	public SimpleDemand[] getSimpleDemand() {
		return simpleDemand;
	}

	public void setSimpleDemand(SimpleDemand[] simpleDemand) {
		this.simpleDemand = simpleDemand;
	}

	public String getDptState() {
		return dptState;
	}

	public void setDptState(String dptState) {
		this.dptState = dptState;
	}

	public String getDptCt() {
		return dptCt;
	}

	public void setDptCt(String dptCt) {
		this.dptCt = dptCt;
	}

	public String getPstState() {
		return pstState;
	}

	public void setPstState(String pstState) {
		this.pstState = pstState;
	}

	public String getPstCt() {
		return pstCt;
	}

	public void setPstCt(String pstCt) {
		this.pstCt = pstCt;
	}

	public String getArrvState() {
		return arrvState;
	}

	public void setArrvState(String arrvState) {
		this.arrvState = arrvState;
	}

	public String getArrvCt() {
		return arrvCt;
	}

	public void setArrvCt(String arrvCt) {
		this.arrvCt = arrvCt;
	}

	public String getDptFltLvl() {
		return dptFltLvl;
	}

	public void setDptFltLvl(String dptFltLvl) {
		this.dptFltLvl = dptFltLvl;
	}

	public String getPstFltLvl() {
		return pstFltLvl;
	}

	public void setPstFltLvl(String pstFltLvl) {
		this.pstFltLvl = pstFltLvl;
	}

	public String getArrvFltLvl() {
		return arrvFltLvl;
	}

	public void setArrvFltLvl(String arrvFltLvl) {
		this.arrvFltLvl = arrvFltLvl;
	}

	public String getIntendedDpt() {
		return intendedDpt;
	}

	public void setIntendedDpt(String intendedDpt) {
		this.intendedDpt = intendedDpt;
	}

	public String getIntendedPst() {
		return intendedPst;
	}

	public void setIntendedPst(String intendedPst) {
		this.intendedPst = intendedPst;
	}

	public String getIntendedArrv() {
		return intendedArrv;
	}

	public void setIntendedArrv(String intendedArrv) {
		this.intendedArrv = intendedArrv;
	}

	public List<IntendedAirline> getIntendedAirlines() {
		return intendedAirlines;
	}

	public void setIntendedAirlines(List<IntendedAirline> intendedAirlines) {
		this.intendedAirlines = intendedAirlines;
	}

	public String getSchedulinePort() {
		return schedulinePort;
	}

	public void setSchedulinePort(String schedulinePort) {
		this.schedulinePort = schedulinePort;
	}

	public List<AirportInfoNewThree> getAirportForSchedulines() {
		return airportForSchedulines;
	}

	public void setAirportForSchedulines(List<AirportInfoNewThree> airportForSchedulines) {
		this.airportForSchedulines = airportForSchedulines;
	}

	public String getDptNm() {
		return dptNm;
	}

	public void setDptNm(String dptNm) {
		this.dptNm = dptNm;
	}

	public String getPstNm() {
		return pstNm;
	}

	public void setPstNm(String pstNm) {
		this.pstNm = pstNm;
	}

	public String getArrvNm() {
		return arrvNm;
	}

	public void setArrvNm(String arrvNm) {
		this.arrvNm = arrvNm;
	}

	public int getRenew() {
		return renew;
	}

	public void setRenew(int renew) {
		this.renew = renew;
	}

	public int getCollectType() {
		return collectType;
	}

	public void setCollectType(int collectType) {
		this.collectType = collectType;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCapacitycompanyStr() {
		return capacitycompanyStr;
	}

	public void setCapacitycompanyStr(String capacitycompanyStr) {
		this.capacitycompanyStr = capacitycompanyStr;
	}

	public int getCollectId() {
		return collectId;
	}

	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}

	public String getEmployeeNm() {
		return employeeNm;
	}

	public void setEmployeeNm(String employeeNm) {
		this.employeeNm = employeeNm;
	}

	public String getIntentionMoneyState() {
		return intentionMoneyState;
	}

	public void setIntentionMoneyState(String intentionMoneyState) {
		this.intentionMoneyState = intentionMoneyState;
	}

	public String getRek() {
		return rek;
	}

	public void setRek(String rek) {
		this.rek = rek;
	}

	public int getUnreadMessageCount() {
		return unreadMessageCount;
	}

	public void setUnreadMessageCount(int unreadMessageCount) {
		this.unreadMessageCount = unreadMessageCount;
	}

	public List<Employee> getResponseEmployees() {
		return responseEmployees;
	}

	public void setResponseEmployees(List<Employee> responseEmployees) {
		this.responseEmployees = responseEmployees;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getCpyNm() {
		return CpyNm;
	}

	public void setCpyNm(String cpyNm) {
		CpyNm = cpyNm;
	}

	public List<T> getDirections() {
		return directions;
	}

	public void setDirections(List<T> directions) {
		this.directions = directions;
	}

	@Override
	public String toString() {
		return "Demand [set=" + set + ", id=" + id + ", intentionMoneyState="
				+ intentionMoneyState + ", employeeId=" + employeeId
				+ ", nickName=" + nickName + ", demandstate=" + demandstate
				+ ", rek=" + rek + ", demandStateStr=" + demandStateStr
				+ ", demandtype=" + demandtype + ", demandtypeStr="
				+ demandtypeStr + ", releasetime=" + releasetime + ", title="
				+ title + ", dptState=" + dptState + ", dpt=" + dpt
				+ ", dptNm=" + dptNm + ", dptCt=" + dptCt + ", collectType="
				+ collectType + ", dptAcceptnearairport="
				+ dptAcceptnearairport + ", dptAcceptnearairportStr="
				+ dptAcceptnearairportStr + ", dptTimeresources="
				+ dptTimeresources + ", dptTimeresourcesStr="
				+ dptTimeresourcesStr + ", dptTime=" + dptTime + ", pstState="
				+ pstState + ", pst=" + pst + ", pstNm=" + pstNm + ", pstCt="
				+ pstCt + ", pstAcceptnearairport=" + pstAcceptnearairport
				+ ", pstAcceptnearairportStr=" + pstAcceptnearairportStr
				+ ", pstTimeresources=" + pstTimeresources
				+ ", pstTimeresourcesStr=" + pstTimeresourcesStr + ", pstTime="
				+ pstTime + ", arrvState=" + arrvState + ", arrv=" + arrv
				+ ", arrvNm=" + arrvNm + ", arrvCt=" + arrvCt
				+ ", arrvAcceptnearairport=" + arrvAcceptnearairport
				+ ", arrvAcceptnearairportStr=" + arrvAcceptnearairportStr
				+ ", arrvTimeresources=" + arrvTimeresources
				+ ", arrvTimeresourcesStr=" + arrvTimeresourcesStr
				+ ", arrvTime=" + arrvTime + ", aircrfttyp=" + aircrfttyp
				+ ", days=" + days + ", blockbidprice=" + blockbidprice
				+ ", loadfactorsexpect=" + loadfactorsexpect
				+ ", avgguestexpect=" + avgguestexpect + ", subsidypolicy="
				+ subsidypolicy + ", subsidypolicyStr=" + subsidypolicyStr
				+ ", sailingtime=" + sailingtime + ", seating=" + seating
				+ ", capacitycompany=" + capacitycompany
				+ ", capacitycompanyStr=" + capacitycompanyStr
				+ ", capacityCompany=" + capacityCompany + ", scheduling="
				+ scheduling + ", schedulingStr=" + schedulingStr
				+ ", schedulinePort=" + schedulinePort
				+ ", airportForSchedulines=" + airportForSchedulines
				+ ", hourscost=" + hourscost + ", intendedairline="
				+ intendedairline + ", intendedairlineStr="
				+ intendedairlineStr + ", remark=" + remark + ", publicway="
				+ publicway + ", publicwayStr=" + publicwayStr
				+ ", directionalgoal=" + directionalgoal + ", demandprogress="
				+ demandprogress + ", demandprogressStr=" + demandprogressStr
				+ ", contact=" + contact + ", iHome=" + iHome + ", fltNbr="
				+ fltNbr + ", periodValidity=" + periodValidity
				+ ", simpleDemand=" + Arrays.toString(simpleDemand)
				+ ", dptFltLvl=" + dptFltLvl + ", pstFltLvl=" + pstFltLvl
				+ ", arrvFltLvl=" + arrvFltLvl + ", intendedDpt=" + intendedDpt
				+ ", intendedPst=" + intendedPst + ", intendedArrv="
				+ intendedArrv + ", renew=" + renew + ", num=" + num
				+ ", collectId=" + collectId + ", intendedAirlines="
				+ intendedAirlines + ", demandId=" + demandId + ", employeeNm="
				+ employeeNm + "]";
	}
}