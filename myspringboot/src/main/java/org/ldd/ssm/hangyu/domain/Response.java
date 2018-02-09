package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

	private Long id;//意向id(主键id)

    private Long employeeId;//响应者id

    private Long demandId;//所响应的需求id
    
    private Demand demand;

    private String intentionmoneystate;//针对该条意向是否已交意向金（0：已交纳，1：未缴纳）

    private String whetherhosting;//是否托管（0：是；1：否）

    private String whethernavigation;//是否通航（0：是；1：否）

    private String whethernavigationStr;
    
    private String releaseselected;//发布者选定（0：选定；1：未选定）

    private String responseselected;//响应者选定（0：选定；1：未选定）

    private String demandtype;//响应的需求种类共5种（0:航线需求、1:运力投放、2:运营托管、3:航线委托、4:运力委托）除去0和1两种状态, 其他状态不再地图上呈现，仅在太美角色个人中心的委托列表中呈现。
    
    private String demandtypeStr;

    private String releasetime;//发布日期

    private String title;//标题（引用需求标题）

    private String dptState;//始发地类型（0：机场；1：区域；2：城市）城市暂时未用

    private String dpt;//始发地（state为0时存放的是机场三字码，为1时存放的是区域名字）
    
    private String dptNm;

    private String dptCt;//始发地所在城市三字码(state为0时存在)

    private String dptAcceptnearairport;//始发地是否接受临近机场（0：接受；1：不接受）

    private String dptAcceptnearairportStr;
    
    private String dptTimeresources;//始发地时刻资源状态（0：有时刻；1：待协调；2：时刻充足）
    
    private String dptTimeresourcesStr;//始发地时刻（具体时刻/待协定/时刻充足）

    private String dptTime;//始发时刻资源具体时刻

    private String pstState;//经停地类型（0：机场；1：区域；2：城市）城市暂时未用

    private String pst;//经停地（state为0时存放的是机场三字码，为1时存放的是区域名字）
    
    private String pstNm;

    private String pstCt;//经停地所在城市三字码(state为0时存在)

    private String pstAcceptnearairport;//经停地是否接受临近机场（0：接受；1：不接受）
    
    private String pstAcceptnearairportStr;

    private String pstTimeresources;//经停地时刻资源状态（0：有时刻；1：待协调；2：时刻充足）
    
    private String pstTimeresourcesStr;//经停地时刻（具体时刻/待协定/时刻充足）

    private String pstTime;//经停时刻资源具体时刻

    private String arrvState;//到达地类型（0：机场；1：区域；2：城市）城市暂时未用

    private String arrv;//到达地（state为0时存放的是机场三字码，为1时存放的是区域名字）

    private String arrvNm;
    
    private String arrvCt;//到达地所在城市三字码(state为0时存在)

    private String arrvAcceptnearairport;//到达地是否接受临近机场（0：接受；1：不接受）
    
    private String arrvAcceptnearairportStr;

    private String arrvTimeresources;//到达地时刻资源状态（0：有时刻；1：待协调；2：时刻充足）
    
    private String arrvTimeresourcesStr;//到达地时刻（具体时刻/待协定/时刻充足）

    private String arrvTime;//到达时刻资源具体时刻

    private String aircrfttyp;//机型

    private String days;//班期

    private String blockbidprice;//拦标价格（W）

    private String loadfactorsexpect;//客座率期望（%）

    private String avgguestexpect;//均班客座期望（人）

    private String subsidypolicy;//5.补贴有种状态：有补贴0:有补贴-定补、1:有补贴-保底,2:有补贴-人头补,3:有补贴-其他,4:待议5:无补贴。
    
    private String subsidypolicyStr;//补贴内容（有补贴/待议/无补贴）

    private String sailingtime;//开航时间

    private String seating;//座位数/座位布局

    private Long capacitycompany;//运力归属航司(aircompenyinfothree表id)
    
    private String capacitycompanyStr;

    private AirCompenyInfoThree capacityCompany;//运力归属航司
    
    private String capacityBase;//运力所在基地(机场三字码)
    
    private String capacityBaseNm;//运力基地名称

    private String scheduling;//是否接收调度(0:接收,1:不接收)
    
    private String schedulingStr;//是否接受调度（接受/不接受）

    private String schedulineport;//可以调度的机场
    
    private List<AirportInfoNewThree> airportForSchedulines;//接受调度的机场对象列表

    private String hourscost;//小时成本（W/H）

    private String remark;//备注说明

    private String publicway;//公开方式(0:对所有人公开,1:对认证用户公开,2:定向航司,3:定向机场), 3和4定位目标在下一个字段
    
    private String publicwayStr;//公开方式（所有人/认证用户/定向航司/定向机场）

    private Long directionalgoal;//定向目标表id (2:aircompenyinfothree,3:airportinfonewthree)

    private String fltnbr;//航班号

    private String contact;//联系人

    private String ihome;//联系人电话

    private String responsedate;//响应时间（入库时决定然后不可改变）

    private String updatedate;//更新时间

    private String newinfo;//该点是否有最新进展(0: 表示有 1:表示没有)

    private String dptFltLvl;//始发机场飞行等级
    
    private String pstFltLvl;//经停机场飞行等级
    
    private String arrvFltLvl;//到达机场飞行等级
    
    private String intentionCompanyName;
    
    private String responseProgress;//响应状态:[0:意向征集、1:订单确认、2:已撤回、3:需求关闭、4:落选状态 5:交易完成,6:订单完成,7:佣金支付]
    
    private String responseProgressStr;
    
    private String demandEmployeeId;//需求发布者id
    
    private int unreadNum;//意向未读信息条数
    
    public String getResponseProgressStr() {
		return responseProgressStr;
	}

	public void setResponseProgressStr(String responseProgressStr) {
		this.responseProgressStr = responseProgressStr;
	}

	public String getDemandtypeStr() {
		return demandtypeStr;
	}

	public void setDemandtypeStr(String demandtypeStr) {
		this.demandtypeStr = demandtypeStr;
	}

	public String getCapacityBase() {
		return capacityBase;
	}

	public void setCapacityBase(String capacityBase) {
		this.capacityBase = capacityBase;
	}

	public String getCapacityBaseNm() {
		return capacityBaseNm;
	}

	public void setCapacityBaseNm(String capacityBaseNm) {
		this.capacityBaseNm = capacityBaseNm;
	}

	public String getIntentionCompanyName() {
		return intentionCompanyName;
	}

	public void setIntentionCompanyName(String intentionCompanyName) {
		this.intentionCompanyName = intentionCompanyName;
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

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    public String getIntentionmoneystate() {
        return intentionmoneystate;
    }

    public void setIntentionmoneystate(String intentionmoneystate) {
        this.intentionmoneystate = intentionmoneystate == null ? null : intentionmoneystate.trim();
    }

    public String getWhetherhosting() {
        return whetherhosting;
    }

    public void setWhetherhosting(String whetherhosting) {
        this.whetherhosting = whetherhosting == null ? null : whetherhosting.trim();
    }

    public String getWhethernavigation() {
        return whethernavigation;
    }

    public void setWhethernavigation(String whethernavigation) {
        this.whethernavigation = whethernavigation == null ? null : whethernavigation.trim();
    }

    public String getReleaseselected() {
        return releaseselected;
    }

    public void setReleaseselected(String releaseselected) {
        this.releaseselected = releaseselected == null ? null : releaseselected.trim();
    }

    public String getResponseselected() {
        return responseselected;
    }

    public void setResponseselected(String responseselected) {
        this.responseselected = responseselected == null ? null : responseselected.trim();
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

    public String getDptState() {
        return dptState;
    }

    public void setDptState(String dptState) {
        this.dptState = dptState == null ? null : dptState.trim();
    }

    public String getDpt() {
        return dpt;
    }

    public void setDpt(String dpt) {
        this.dpt = dpt == null ? null : dpt.trim();
    }

    public String getDptCt() {
        return dptCt;
    }

    public void setDptCt(String dptCt) {
        this.dptCt = dptCt == null ? null : dptCt.trim();
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

    public String getPstState() {
        return pstState;
    }

    public void setPstState(String pstState) {
        this.pstState = pstState == null ? null : pstState.trim();
    }

    public String getPst() {
        return pst;
    }

    public void setPst(String pst) {
        this.pst = pst == null ? null : pst.trim();
    }

    public String getPstCt() {
        return pstCt;
    }

    public void setPstCt(String pstCt) {
        this.pstCt = pstCt == null ? null : pstCt.trim();
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

    public String getArrvState() {
        return arrvState;
    }

    public void setArrvState(String arrvState) {
        this.arrvState = arrvState == null ? null : arrvState.trim();
    }

    public String getArrv() {
        return arrv;
    }

    public void setArrv(String arrv) {
        this.arrv = arrv == null ? null : arrv.trim();
    }

    public String getArrvCt() {
        return arrvCt;
    }

    public void setArrvCt(String arrvCt) {
        this.arrvCt = arrvCt == null ? null : arrvCt.trim();
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

    public String getBlockbidprice() {
        return blockbidprice;
    }

    public void setBlockbidprice(String blockbidprice) {
        this.blockbidprice = blockbidprice == null ? null : blockbidprice.trim();
    }

    public String getLoadfactorsexpect() {
        return loadfactorsexpect;
    }

    public void setLoadfactorsexpect(String loadfactorsexpect) {
        this.loadfactorsexpect = loadfactorsexpect == null ? null : loadfactorsexpect.trim();
    }

    public String getAvgguestexpect() {
        return avgguestexpect;
    }

    public void setAvgguestexpect(String avgguestexpect) {
        this.avgguestexpect = avgguestexpect == null ? null : avgguestexpect.trim();
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

    public String getSchedulineport() {
        return schedulineport;
    }

    public void setSchedulineport(String schedulineport) {
        this.schedulineport = schedulineport == null ? null : schedulineport.trim();
    }

    public String getHourscost() {
        return hourscost;
    }

    public void setHourscost(String hourscost) {
        this.hourscost = hourscost == null ? null : hourscost.trim();
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

    public Long getDirectionalgoal() {
        return directionalgoal;
    }

    public void setDirectionalgoal(Long directionalgoal) {
        this.directionalgoal = directionalgoal;
    }

    public String getFltnbr() {
        return fltnbr;
    }

    public void setFltnbr(String fltnbr) {
        this.fltnbr = fltnbr == null ? null : fltnbr.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getIhome() {
        return ihome;
    }

    public void setIhome(String ihome) {
        this.ihome = ihome == null ? null : ihome.trim();
    }

    public String getResponsedate() {
        return responsedate;
    }

    public void setResponsedate(String responsedate) {
        this.responsedate = responsedate == null ? null : responsedate.trim();
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate == null ? null : updatedate.trim();
    }

    public String getNewinfo() {
        return newinfo;
    }

    public void setNewinfo(String newinfo) {
        this.newinfo = newinfo == null ? null : newinfo.trim();
    }
    
    public String getResponseProgress() {
		return responseProgress;
	}

	public void setResponseProgress(String responseProgress) {
		this.responseProgress = responseProgress;
	}

	public Demand getDemand() {
		return demand;
	}

	public void setDemand(Demand demand) {
		this.demand = demand;
	}

	public String getCapacitycompanyStr() {
		return capacitycompanyStr;
	}

	public void setCapacitycompanyStr(String capacitycompanyStr) {
		this.capacitycompanyStr = capacitycompanyStr;
	}

	public String getDemandEmployeeId() {
		return demandEmployeeId;
	}

	public void setDemandEmployeeId(String demandEmployeeId) {
		this.demandEmployeeId = demandEmployeeId;
	}

	public int getUnreadNum() {
		return unreadNum;
	}

	public void setUnreadNum(int unreadNum) {
		this.unreadNum = unreadNum;
	}

	public String getDptNm() {
		return dptNm;
	}

	public void setDptNm(String dptNm) {
		this.dptNm = dptNm;
	}

	public String getDptAcceptnearairportStr() {
		return dptAcceptnearairportStr;
	}

	public void setDptAcceptnearairportStr(String dptAcceptnearairportStr) {
		this.dptAcceptnearairportStr = dptAcceptnearairportStr;
	}

	public String getDptTimeresourcesStr() {
		return dptTimeresourcesStr;
	}

	public void setDptTimeresourcesStr(String dptTimeresourcesStr) {
		this.dptTimeresourcesStr = dptTimeresourcesStr;
	}

	public String getPstNm() {
		return pstNm;
	}

	public void setPstNm(String pstNm) {
		this.pstNm = pstNm;
	}

	public String getPstAcceptnearairportStr() {
		return pstAcceptnearairportStr;
	}

	public void setPstAcceptnearairportStr(String pstAcceptnearairportStr) {
		this.pstAcceptnearairportStr = pstAcceptnearairportStr;
	}

	public String getPstTimeresourcesStr() {
		return pstTimeresourcesStr;
	}

	public void setPstTimeresourcesStr(String pstTimeresourcesStr) {
		this.pstTimeresourcesStr = pstTimeresourcesStr;
	}

	public String getArrvNm() {
		return arrvNm;
	}

	public void setArrvNm(String arrvNm) {
		this.arrvNm = arrvNm;
	}

	public String getArrvAcceptnearairportStr() {
		return arrvAcceptnearairportStr;
	}

	public void setArrvAcceptnearairportStr(String arrvAcceptnearairportStr) {
		this.arrvAcceptnearairportStr = arrvAcceptnearairportStr;
	}

	public String getArrvTimeresourcesStr() {
		return arrvTimeresourcesStr;
	}

	public void setArrvTimeresourcesStr(String arrvTimeresourcesStr) {
		this.arrvTimeresourcesStr = arrvTimeresourcesStr;
	}

	public String getSubsidypolicyStr() {
		return subsidypolicyStr;
	}

	public void setSubsidypolicyStr(String subsidypolicyStr) {
		this.subsidypolicyStr = subsidypolicyStr;
	}

	public String getSchedulingStr() {
		return schedulingStr;
	}

	public void setSchedulingStr(String schedulingStr) {
		this.schedulingStr = schedulingStr;
	}

	public List<AirportInfoNewThree> getAirportForSchedulines() {
		return airportForSchedulines;
	}

	public void setAirportForSchedulines(
			List<AirportInfoNewThree> airportForSchedulines) {
		this.airportForSchedulines = airportForSchedulines;
	}

	public String getPublicwayStr() {
		return publicwayStr;
	}

	public void setPublicwayStr(String publicwayStr) {
		this.publicwayStr = publicwayStr;
	}

	public String getWhethernavigationStr() {
		return whethernavigationStr;
	}

	public void setWhethernavigationStr(String whethernavigationStr) {
		this.whethernavigationStr = whethernavigationStr;
	}

	@Override
	public String toString() {
		return "Response [id=" + id + ", employeeId=" + employeeId
				+ ", demandId=" + demandId + ", demand=" + demand
				+ ", intentionmoneystate=" + intentionmoneystate
				+ ", whetherhosting=" + whetherhosting + ", whethernavigation="
				+ whethernavigation + ", whethernavigationStr="
				+ whethernavigationStr + ", releaseselected=" + releaseselected
				+ ", responseselected=" + responseselected + ", demandtype="
				+ demandtype + ", demandtypeStr=" + demandtypeStr
				+ ", releasetime=" + releasetime + ", title=" + title
				+ ", dptState=" + dptState + ", dpt=" + dpt + ", dptNm="
				+ dptNm + ", dptCt=" + dptCt + ", dptAcceptnearairport="
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
				+ ", capacityCompany=" + capacityCompany + ", capacityBase="
				+ capacityBase + ", capacityBaseNm=" + capacityBaseNm
				+ ", scheduling=" + scheduling + ", schedulingStr="
				+ schedulingStr + ", schedulineport=" + schedulineport
				+ ", airportForSchedulines=" + airportForSchedulines
				+ ", hourscost=" + hourscost + ", remark=" + remark
				+ ", publicway=" + publicway + ", publicwayStr=" + publicwayStr
				+ ", directionalgoal=" + directionalgoal + ", fltnbr=" + fltnbr
				+ ", contact=" + contact + ", ihome=" + ihome
				+ ", responsedate=" + responsedate + ", updatedate="
				+ updatedate + ", newinfo=" + newinfo + ", dptFltLvl="
				+ dptFltLvl + ", pstFltLvl=" + pstFltLvl + ", arrvFltLvl="
				+ arrvFltLvl + ", intentionCompanyName=" + intentionCompanyName
				+ ", responseProgress=" + responseProgress
				+ ", responseProgressStr=" + responseProgressStr
				+ ", demandEmployeeId=" + demandEmployeeId + ", unreadNum="
				+ unreadNum + "]";
	}
	
}