package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class ResponseForAddAndUp implements Serializable {

	private Long id;

    private Long employeeId;

    private Long demandId;

    private String intentionmoneystate;

    private String whetherhosting;

    private String whethernavigation;

    private String releaseselected;

    private String responseselected;

    private String demandtype;

    private String releasetime;

    private String title;

    private String dptState;

    private String dpt;

    private String dptCt;

    private String dptAcceptnearairport;

    private String dptTimeresources;

    private String dptTime;

    private String pstState;

    private String pst;

    private String pstCt;

    private String pstAcceptnearairport;

    private String pstTimeresources;

    private String pstTime;

    private String arrvState;

    private String arrv;

    private String arrvCt;

    private String arrvAcceptnearairport;

    private String arrvTimeresources;

    private String arrvTime;

    private String aircrfttyp;

    private String days;

    private String blockbidprice;

    private String loadfactorsexpect;

    private String avgguestexpect;

    private String subsidypolicy;

    private String sailingtime;

    private String seating;

    private Long capacitycompany;//运力归属航司(aircompenyinfothree表id)

    private String capacityBase;
    
    private String scheduling;

    private String schedulineport;

    private String hourscost;

    private String remark;

    private String publicway;

    private Long directionalgoal;

    private String fltnbr;

    private String contact;

    private String ihome;

    private String responsedate;

    private String updatedate;

    private String newinfo;

    private String dptFltLvl;//始发机场飞行等级
    
    private String pstFltLvl;//经停机场飞行等级
    
    private String arrvFltLvl;//到达机场飞行等级
    
    private String responseProgress;

	//response表少的属性
    private String periodValidity;//有效期
    
    
    public String getResponseProgress() {
		return responseProgress;
	}

	public void setResponseProgress(String responseProgress) {
		this.responseProgress = responseProgress;
	}
	
    public String getPeriodValidity() {
		return periodValidity;
	}

	public void setPeriodValidity(String periodValidity) {
		this.periodValidity = periodValidity;
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

	public String getCapacityBase() {
		return capacityBase;
	}

	public void setCapacityBase(String capacityBase) {
		this.capacityBase = capacityBase;
	}

	@Override
	public String toString() {
		return "ResponseForAddAndUp [id=" + id + ", employeeId=" + employeeId
				+ ", demandId=" + demandId + ", intentionmoneystate="
				+ intentionmoneystate + ", whetherhosting=" + whetherhosting
				+ ", whethernavigation=" + whethernavigation
				+ ", releaseselected=" + releaseselected
				+ ", responseselected=" + responseselected + ", demandtype="
				+ demandtype + ", releasetime=" + releasetime + ", title="
				+ title + ", dptState=" + dptState + ", dpt=" + dpt
				+ ", dptCt=" + dptCt + ", dptAcceptnearairport="
				+ dptAcceptnearairport + ", dptTimeresources="
				+ dptTimeresources + ", dptTime=" + dptTime + ", pstState="
				+ pstState + ", pst=" + pst + ", pstCt=" + pstCt
				+ ", pstAcceptnearairport=" + pstAcceptnearairport
				+ ", pstTimeresources=" + pstTimeresources + ", pstTime="
				+ pstTime + ", arrvState=" + arrvState + ", arrv=" + arrv
				+ ", arrvCt=" + arrvCt + ", arrvAcceptnearairport="
				+ arrvAcceptnearairport + ", arrvTimeresources="
				+ arrvTimeresources + ", arrvTime=" + arrvTime
				+ ", aircrfttyp=" + aircrfttyp + ", days=" + days
				+ ", blockbidprice=" + blockbidprice + ", loadfactorsexpect="
				+ loadfactorsexpect + ", avgguestexpect=" + avgguestexpect
				+ ", subsidypolicy=" + subsidypolicy + ", sailingtime="
				+ sailingtime + ", seating=" + seating + ", capacitycompany="
				+ capacitycompany + ", capacityBase=" + capacityBase
				+ ", scheduling=" + scheduling + ", schedulineport="
				+ schedulineport + ", hourscost=" + hourscost + ", remark="
				+ remark + ", publicway=" + publicway + ", directionalgoal="
				+ directionalgoal + ", fltnbr=" + fltnbr + ", contact="
				+ contact + ", ihome=" + ihome + ", responsedate="
				+ responsedate + ", updatedate=" + updatedate + ", newinfo="
				+ newinfo + ", dptFltLvl=" + dptFltLvl + ", pstFltLvl="
				+ pstFltLvl + ", arrvFltLvl=" + arrvFltLvl
				+ ", responseProgress=" + responseProgress
				+ ", periodValidity=" + periodValidity + "]";
	}
    
    
    
}