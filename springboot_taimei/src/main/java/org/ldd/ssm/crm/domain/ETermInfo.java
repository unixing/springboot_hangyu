package org.ldd.ssm.crm.domain;

import java.util.Arrays;
import java.util.List;
/**
 * eTerm 用户信息
 * @author Taimei
 *
 */
public class ETermInfo {
	private String id;
	private String user;//用户名
	private String pas;//密码
	private String jobn;//工作号
	private String jobp;//工作号密码
	private String level;//级别
	private String etermIp;//eTermIP
	private String Secure;//安全传输
	private String etermPort;//端口
	private String si;//SI指令
	private String acquisitionT;//自动采集时间
	private String[] broken;//自动采集时间数组
	private String conflict;//冲突等待时间
	private String conflictAvoid;//冲突自动避让
	private String depth;//是否深度采集
	private List<AddFltNbr> addFltNbrs;//航线信息
	private String lastTime;//最后更新时间
	private String state;//测试状态
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<AddFltNbr> getAddFltNbrs() {
		return addFltNbrs;
	}
	public void setAddFltNbrs(List<AddFltNbr> addFltNbrs) {
		this.addFltNbrs = addFltNbrs;
	}
	public String[] getBroken() {
		return broken;
	}
	public void setBroken(String[] broken) {
		this.broken = broken;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPas() {
		return pas;
	}
	public void setPas(String pas) {
		this.pas = pas;
	}
	public String getJobn() {
		return jobn;
	}
	public void setJobn(String jobn) {
		this.jobn = jobn;
	}
	public String getJobp() {
		return jobp;
	}
	public void setJobp(String jobp) {
		this.jobp = jobp;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getEtermIp() {
		return etermIp;
	}
	public void setEtermIp(String etermIp) {
		this.etermIp = etermIp;
	}
	public String getSecure() {
		return Secure;
	}
	public void setSecure(String secure) {
		Secure = secure;
	}
	public String getEtermPort() {
		return etermPort;
	}
	public void setEtermPort(String etermPort) {
		this.etermPort = etermPort;
	}
	public String getSi() {
		return si;
	}
	public void setSi(String si) {
		this.si = si;
	}
	public String getAcquisitionT() {
		return acquisitionT;
	}
	public void setAcquisitionT(String acquisitionT) {
		this.acquisitionT = acquisitionT;
	}
	public String getConflict() {
		return conflict;
	}
	public void setConflict(String conflict) {
		this.conflict = conflict;
	}
	public String getConflictAvoid() {
		return conflictAvoid;
	}
	public void setConflictAvoid(String conflictAvoid) {
		this.conflictAvoid = conflictAvoid;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "ETermInfo [id=" + id + ", user=" + user + ", pas=" + pas
				+ ", jobn=" + jobn + ", jobp=" + jobp + ", level=" + level
				+ ", etermIp=" + etermIp + ", Secure=" + Secure
				+ ", etermPort=" + etermPort + ", si=" + si + ", acquisitionT="
				+ acquisitionT + ", broken=" + Arrays.toString(broken)
				+ ", conflict=" + conflict + ", conflictAvoid=" + conflictAvoid
				+ ", depth=" + depth + ", addFltNbrs=" + addFltNbrs
				+ ", lastTime=" + lastTime + ", state=" + state + "]";
	}    
	
}
