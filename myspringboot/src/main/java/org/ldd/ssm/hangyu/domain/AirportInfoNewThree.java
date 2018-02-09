package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class AirportInfoNewThree implements Serializable{
    private Long id;

    private String airlnCd;

    private String airlnCdName;

    private String airlinElhName;

    private String airlinSpeSpl;

    private String iata;

    private String icao;

    private String inter;

    private String airfieldlvl;

    private String city;

    private String province;

    private String cityCoordinateJ;

    private String cityCoordinateW;

    private String coordinateupdatetime;

    private String area;

    private String airState;

    private String airpottype;

    private String warzone;

    private String airEle;

    private String airpotcls;

    private String coordinateairport;

    private String coordinatetimelist;

    private String specialairport;

    private String specialairportwhy;

    private String port;

    private String releasepunctuality;

    private String firelvl;

    private String lightingconditions;

    private String allowthetakeoffandlanding;

    private String modelcanhandle;

    private String runwayarticlenumber;

    private String airportshuttlemetro;

    private String airportbus;

    private String distancefromdowntown;

    private String intheflight;

    private String international;

    private String internationaltime;

    private String domestic;

    private String intheport;

    private String intheflighttime;

    private String membershipgroup;

    private String planepositionnumber;

    private String departuretime;

    private String operator;

    private String operatorinput;

    private String isrewardpolicy;

    private String isrewardpolicytext;
    
    private int[] passengerThroughputs;//旅客吞吐量列表
    
    private Double[] goodsThroughputs;//货邮吞吐量列表

    private Double[] takeOffAndLandingFlights;//起降架次列表
    
    private String[] years;//年份列表
    
    private List<RunwayTheDetail> runwayList;//跑道列表
    
    private List<RewardPolicy> rewardPolicyList;//政策列表
    
    private List<PublicOpinion> opinions;//舆情列表
    
    private List<AirCompenyInfoThree> compenys;//关联航司

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirlnCd() {
        return airlnCd;
    }

    public void setAirlnCd(String airlnCd) {
        this.airlnCd = airlnCd == null ? null : airlnCd.trim();
    }

    public String getAirlnCdName() {
        return airlnCdName;
    }

    public void setAirlnCdName(String airlnCdName) {
        this.airlnCdName = airlnCdName == null ? null : airlnCdName.trim();
    }

    public String getAirlinElhName() {
        return airlinElhName;
    }

    public void setAirlinElhName(String airlinElhName) {
        this.airlinElhName = airlinElhName == null ? null : airlinElhName.trim();
    }

    public String getAirlinSpeSpl() {
        return airlinSpeSpl;
    }

    public void setAirlinSpeSpl(String airlinSpeSpl) {
        this.airlinSpeSpl = airlinSpeSpl == null ? null : airlinSpeSpl.trim();
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao == null ? null : icao.trim();
    }

    public String getInter() {
        return inter;
    }

    public void setInter(String inter) {
        this.inter = inter == null ? null : inter.trim();
    }

    public String getAirfieldlvl() {
        return airfieldlvl;
    }

    public void setAirfieldlvl(String airfieldlvl) {
        this.airfieldlvl = airfieldlvl == null ? null : airfieldlvl.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCityCoordinateJ() {
        return cityCoordinateJ;
    }

    public void setCityCoordinateJ(String cityCoordinateJ) {
        this.cityCoordinateJ = cityCoordinateJ == null ? null : cityCoordinateJ.trim();
    }

    public String getCityCoordinateW() {
        return cityCoordinateW;
    }

    public void setCityCoordinateW(String cityCoordinateW) {
        this.cityCoordinateW = cityCoordinateW == null ? null : cityCoordinateW.trim();
    }

    public String getCoordinateupdatetime() {
        return coordinateupdatetime;
    }

    public void setCoordinateupdatetime(String coordinateupdatetime) {
        this.coordinateupdatetime = coordinateupdatetime == null ? null : coordinateupdatetime.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAirState() {
        return airState;
    }

    public void setAirState(String airState) {
        this.airState = airState == null ? null : airState.trim();
    }

    public String getAirpottype() {
        return airpottype;
    }

    public void setAirpottype(String airpottype) {
        this.airpottype = airpottype == null ? null : airpottype.trim();
    }

    public String getWarzone() {
        return warzone;
    }

    public void setWarzone(String warzone) {
        this.warzone = warzone == null ? null : warzone.trim();
    }

    public String getAirEle() {
        return airEle;
    }

    public void setAirEle(String airEle) {
        this.airEle = airEle == null ? null : airEle.trim();
    }

    public String getAirpotcls() {
        return airpotcls;
    }

    public void setAirpotcls(String airpotcls) {
        this.airpotcls = airpotcls == null ? null : airpotcls.trim();
    }

    public String getCoordinateairport() {
        return coordinateairport;
    }

    public void setCoordinateairport(String coordinateairport) {
        this.coordinateairport = coordinateairport == null ? null : coordinateairport.trim();
    }

    public String getCoordinatetimelist() {
        return coordinatetimelist;
    }

    public void setCoordinatetimelist(String coordinatetimelist) {
        this.coordinatetimelist = coordinatetimelist == null ? null : coordinatetimelist.trim();
    }

    public String getSpecialairport() {
        return specialairport;
    }

    public void setSpecialairport(String specialairport) {
        this.specialairport = specialairport == null ? null : specialairport.trim();
    }

    public String getSpecialairportwhy() {
        return specialairportwhy;
    }

    public void setSpecialairportwhy(String specialairportwhy) {
        this.specialairportwhy = specialairportwhy == null ? null : specialairportwhy.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public String getReleasepunctuality() {
        return releasepunctuality;
    }

    public void setReleasepunctuality(String releasepunctuality) {
        this.releasepunctuality = releasepunctuality == null ? null : releasepunctuality.trim();
    }

    public String getFirelvl() {
        return firelvl;
    }

    public void setFirelvl(String firelvl) {
        this.firelvl = firelvl == null ? null : firelvl.trim();
    }

    public String getLightingconditions() {
        return lightingconditions;
    }

    public void setLightingconditions(String lightingconditions) {
        this.lightingconditions = lightingconditions == null ? null : lightingconditions.trim();
    }

    public String getAllowthetakeoffandlanding() {
        return allowthetakeoffandlanding;
    }

    public void setAllowthetakeoffandlanding(String allowthetakeoffandlanding) {
        this.allowthetakeoffandlanding = allowthetakeoffandlanding == null ? null : allowthetakeoffandlanding.trim();
    }

    public String getModelcanhandle() {
        return modelcanhandle;
    }

    public void setModelcanhandle(String modelcanhandle) {
        this.modelcanhandle = modelcanhandle == null ? null : modelcanhandle.trim();
    }

    public String getRunwayarticlenumber() {
        return runwayarticlenumber;
    }

    public void setRunwayarticlenumber(String runwayarticlenumber) {
        this.runwayarticlenumber = runwayarticlenumber == null ? null : runwayarticlenumber.trim();
    }

    public String getAirportshuttlemetro() {
        return airportshuttlemetro;
    }

    public void setAirportshuttlemetro(String airportshuttlemetro) {
        this.airportshuttlemetro = airportshuttlemetro == null ? null : airportshuttlemetro.trim();
    }

    public String getAirportbus() {
        return airportbus;
    }

    public void setAirportbus(String airportbus) {
        this.airportbus = airportbus == null ? null : airportbus.trim();
    }

    public String getDistancefromdowntown() {
        return distancefromdowntown;
    }

    public void setDistancefromdowntown(String distancefromdowntown) {
        this.distancefromdowntown = distancefromdowntown == null ? null : distancefromdowntown.trim();
    }

    public String getIntheflight() {
        return intheflight;
    }

    public void setIntheflight(String intheflight) {
        this.intheflight = intheflight == null ? null : intheflight.trim();
    }

    public String getInternational() {
        return international;
    }

    public void setInternational(String international) {
        this.international = international == null ? null : international.trim();
    }

    public String getInternationaltime() {
        return internationaltime;
    }

    public void setInternationaltime(String internationaltime) {
        this.internationaltime = internationaltime == null ? null : internationaltime.trim();
    }

    public String getDomestic() {
        return domestic;
    }

    public void setDomestic(String domestic) {
        this.domestic = domestic == null ? null : domestic.trim();
    }

    public String getIntheport() {
        return intheport;
    }

    public void setIntheport(String intheport) {
        this.intheport = intheport == null ? null : intheport.trim();
    }

    public String getIntheflighttime() {
        return intheflighttime;
    }

    public void setIntheflighttime(String intheflighttime) {
        this.intheflighttime = intheflighttime == null ? null : intheflighttime.trim();
    }

    public String getMembershipgroup() {
        return membershipgroup;
    }

    public void setMembershipgroup(String membershipgroup) {
        this.membershipgroup = membershipgroup == null ? null : membershipgroup.trim();
    }

    public String getPlanepositionnumber() {
        return planepositionnumber;
    }

    public void setPlanepositionnumber(String planepositionnumber) {
        this.planepositionnumber = planepositionnumber == null ? null : planepositionnumber.trim();
    }

    public String getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(String departuretime) {
        this.departuretime = departuretime == null ? null : departuretime.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperatorinput() {
        return operatorinput;
    }

    public void setOperatorinput(String operatorinput) {
        this.operatorinput = operatorinput == null ? null : operatorinput.trim();
    }

    public String getIsrewardpolicy() {
        return isrewardpolicy;
    }

    public void setIsrewardpolicy(String isrewardpolicy) {
        this.isrewardpolicy = isrewardpolicy == null ? null : isrewardpolicy.trim();
    }

    public String getIsrewardpolicytext() {
        return isrewardpolicytext;
    }

    public void setIsrewardpolicytext(String isrewardpolicytext) {
        this.isrewardpolicytext = isrewardpolicytext == null ? null : isrewardpolicytext.trim();
    }

	public int[] getPassengerThroughputs() {
		return passengerThroughputs;
	}

	public void setPassengerThroughputs(int[] passengerThroughputs) {
		this.passengerThroughputs = passengerThroughputs;
	}

	public Double[] getGoodsThroughputs() {
		return goodsThroughputs;
	}

	public void setGoodsThroughputs(Double[] goodsThroughputs) {
		this.goodsThroughputs = goodsThroughputs;
	}

	public Double[] getTakeOffAndLandingFlights() {
		return takeOffAndLandingFlights;
	}

	public void setTakeOffAndLandingFlights(Double[] takeOffAndLandingFlights) {
		this.takeOffAndLandingFlights = takeOffAndLandingFlights;
	}

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
		this.years = years;
	}

	public List<RunwayTheDetail> getRunwayList() {
		return runwayList;
	}

	public void setRunwayList(List<RunwayTheDetail> runwayList) {
		this.runwayList = runwayList;
	}

	public List<RewardPolicy> getRewardPolicyList() {
		return rewardPolicyList;
	}

	public List<PublicOpinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(List<PublicOpinion> opinions) {
		this.opinions = opinions;
	}

	public void setRewardPolicyList(List<RewardPolicy> rewardPolicyList) {
		this.rewardPolicyList = rewardPolicyList;
	}

	public List<AirCompenyInfoThree> getCompenys() {
		return compenys;
	}

	public void setCompenys(List<AirCompenyInfoThree> compenys) {
		this.compenys = compenys;
	}

	@Override
	public String toString() {
		return "AirportInfoNewThree [id=" + id + ", airlnCd=" + airlnCd
				+ ", airlnCdName=" + airlnCdName + ", airlinElhName="
				+ airlinElhName + ", airlinSpeSpl=" + airlinSpeSpl + ", iata="
				+ iata + ", icao=" + icao + ", inter=" + inter
				+ ", airfieldlvl=" + airfieldlvl + ", city=" + city
				+ ", province=" + province + ", cityCoordinateJ="
				+ cityCoordinateJ + ", cityCoordinateW=" + cityCoordinateW
				+ ", coordinateupdatetime=" + coordinateupdatetime + ", area="
				+ area + ", airState=" + airState + ", airpottype="
				+ airpottype + ", warzone=" + warzone + ", airEle=" + airEle
				+ ", airpotcls=" + airpotcls + ", coordinateairport="
				+ coordinateairport + ", coordinatetimelist="
				+ coordinatetimelist + ", specialairport=" + specialairport
				+ ", specialairportwhy=" + specialairportwhy + ", port=" + port
				+ ", releasepunctuality=" + releasepunctuality + ", firelvl="
				+ firelvl + ", lightingconditions=" + lightingconditions
				+ ", allowthetakeoffandlanding=" + allowthetakeoffandlanding
				+ ", modelcanhandle=" + modelcanhandle
				+ ", runwayarticlenumber=" + runwayarticlenumber
				+ ", airportshuttlemetro=" + airportshuttlemetro
				+ ", airportbus=" + airportbus + ", distancefromdowntown="
				+ distancefromdowntown + ", intheflight=" + intheflight
				+ ", international=" + international + ", internationaltime="
				+ internationaltime + ", domestic=" + domestic + ", intheport="
				+ intheport + ", intheflighttime=" + intheflighttime
				+ ", membershipgroup=" + membershipgroup
				+ ", planepositionnumber=" + planepositionnumber
				+ ", departuretime=" + departuretime + ", operator=" + operator
				+ ", operatorinput=" + operatorinput + ", isrewardpolicy="
				+ isrewardpolicy + ", isrewardpolicytext=" + isrewardpolicytext
				+ ", passengerThroughputs="
				+ Arrays.toString(passengerThroughputs) + ", goodsThroughputs="
				+ Arrays.toString(goodsThroughputs)
				+ ", takeOffAndLandingFlights="
				+ Arrays.toString(takeOffAndLandingFlights) + ", years="
				+ Arrays.toString(years) + ", runwayList=" + runwayList + "]";
	}
    
}