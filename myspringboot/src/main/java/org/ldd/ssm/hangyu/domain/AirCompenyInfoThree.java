package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;
import java.util.List;

public class AirCompenyInfoThree implements Serializable{
    private Long id;//主键id

    private String airlnCd;//航司名称

    private String iata;//航司二字码

    private String icao;//航司三字码

    private String establishtime;//成立时间

    private String headquarterslocation;//总部地点

    private String basedistribution;//基地分布

    private String shippingcountry;//通航国家

    private String navigationairport;//通航机场

    private String systemairpot;//所属航系

    private String airlinealliance;//航空联盟
    
    private List<PlaneDetail> planeDetails;//航司机型列表
    
    private List<PublicOpinion> publicOpinions;

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

    public String getEstablishtime() {
        return establishtime;
    }

    public void setEstablishtime(String establishtime) {
        this.establishtime = establishtime == null ? null : establishtime.trim();
    }

    public String getHeadquarterslocation() {
        return headquarterslocation;
    }

    public void setHeadquarterslocation(String headquarterslocation) {
        this.headquarterslocation = headquarterslocation == null ? null : headquarterslocation.trim();
    }

    public String getBasedistribution() {
        return basedistribution;
    }

    public void setBasedistribution(String basedistribution) {
        this.basedistribution = basedistribution == null ? null : basedistribution.trim();
    }

    public String getShippingcountry() {
        return shippingcountry;
    }

    public void setShippingcountry(String shippingcountry) {
        this.shippingcountry = shippingcountry == null ? null : shippingcountry.trim();
    }

    public String getNavigationairport() {
        return navigationairport;
    }

    public void setNavigationairport(String navigationairport) {
        this.navigationairport = navigationairport == null ? null : navigationairport.trim();
    }

    public String getSystemairpot() {
        return systemairpot;
    }

    public void setSystemairpot(String systemairpot) {
        this.systemairpot = systemairpot == null ? null : systemairpot.trim();
    }

    public String getAirlinealliance() {
        return airlinealliance;
    }

    public void setAirlinealliance(String airlinealliance) {
        this.airlinealliance = airlinealliance == null ? null : airlinealliance.trim();
    }

	public List<PlaneDetail> getPlaneDetails() {
		return planeDetails;
	}

	public void setPlaneDetails(List<PlaneDetail> planeDetails) {
		this.planeDetails = planeDetails;
	}

	public List<PublicOpinion> getPublicOpinions() {
		return publicOpinions;
	}

	public void setPublicOpinions(List<PublicOpinion> publicOpinions) {
		this.publicOpinions = publicOpinions;
	}

	@Override
	public String toString() {
		return "AirCompenyInfoThree [id=" + id + ", airlnCd=" + airlnCd
				+ ", iata=" + iata + ", icao=" + icao + ", establishtime="
				+ establishtime + ", headquarterslocation="
				+ headquarterslocation + ", basedistribution="
				+ basedistribution + ", shippingcountry=" + shippingcountry
				+ ", navigationairport=" + navigationairport
				+ ", systemairpot=" + systemairpot + ", airlinealliance="
				+ airlinealliance + "]";
	}
}