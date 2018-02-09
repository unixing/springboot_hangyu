package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;
import java.util.List;

public class CityinfoThree implements Serializable {
    private Long id;

    private String cityname;

    private String citylvl;

    private String provinces;

    private String airport;

    private String airportpinyin;

    private String citytype;

    private String largeenterprisenumber;

    private String enterpriseenumeration;

    private String sitesnumber5a;

    private String sitesenumeration5a;

    private String sitesnumber4a;

    private String sitesenumeration4a;

    private String famousuniversities;

    private String famousuniversitiesenumeration;
    
    private List<CulturaleducationyYear> culturaleducationyYears;//旅游人次和旅游收入列表
    
    private List<EconomicYear> economicYears;//城市经济信息列表
    
    private List<Policy> policys;//政策信息列表
    
    private List<Population> populations;//人口信息列表
    
    private List<TrafficLine> highwayList;//公路列表
    
    private List<TrafficLine> railwayList;//铁路列表
    
    private List<PublicOpinion> publicopinions;//舆情列表

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public String getCitylvl() {
        return citylvl;
    }

    public void setCitylvl(String citylvl) {
        this.citylvl = citylvl == null ? null : citylvl.trim();
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces == null ? null : provinces.trim();
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport == null ? null : airport.trim();
    }

    public String getAirportpinyin() {
        return airportpinyin;
    }

    public void setAirportpinyin(String airportpinyin) {
        this.airportpinyin = airportpinyin == null ? null : airportpinyin.trim();
    }

    public String getCitytype() {
        return citytype;
    }

    public void setCitytype(String citytype) {
        this.citytype = citytype == null ? null : citytype.trim();
    }

    public String getLargeenterprisenumber() {
        return largeenterprisenumber;
    }

    public void setLargeenterprisenumber(String largeenterprisenumber) {
        this.largeenterprisenumber = largeenterprisenumber == null ? null : largeenterprisenumber.trim();
    }

    public String getEnterpriseenumeration() {
        return enterpriseenumeration;
    }

    public void setEnterpriseenumeration(String enterpriseenumeration) {
        this.enterpriseenumeration = enterpriseenumeration == null ? null : enterpriseenumeration.trim();
    }

    public String getSitesnumber5a() {
        return sitesnumber5a;
    }

    public void setSitesnumber5a(String sitesnumber5a) {
        this.sitesnumber5a = sitesnumber5a == null ? null : sitesnumber5a.trim();
    }

    public String getSitesenumeration5a() {
        return sitesenumeration5a;
    }

    public void setSitesenumeration5a(String sitesenumeration5a) {
        this.sitesenumeration5a = sitesenumeration5a == null ? null : sitesenumeration5a.trim();
    }

    public String getSitesnumber4a() {
        return sitesnumber4a;
    }

    public void setSitesnumber4a(String sitesnumber4a) {
        this.sitesnumber4a = sitesnumber4a == null ? null : sitesnumber4a.trim();
    }

    public String getSitesenumeration4a() {
        return sitesenumeration4a;
    }

    public void setSitesenumeration4a(String sitesenumeration4a) {
        this.sitesenumeration4a = sitesenumeration4a == null ? null : sitesenumeration4a.trim();
    }

    public String getFamousuniversities() {
        return famousuniversities;
    }

    public void setFamousuniversities(String famousuniversities) {
        this.famousuniversities = famousuniversities == null ? null : famousuniversities.trim();
    }

    public String getFamousuniversitiesenumeration() {
        return famousuniversitiesenumeration;
    }

    public void setFamousuniversitiesenumeration(String famousuniversitiesenumeration) {
        this.famousuniversitiesenumeration = famousuniversitiesenumeration == null ? null : famousuniversitiesenumeration.trim();
    }

	public List<CulturaleducationyYear> getCulturaleducationyYears() {
		return culturaleducationyYears;
	}

	public void setCulturaleducationyYears(
			List<CulturaleducationyYear> culturaleducationyYears) {
		this.culturaleducationyYears = culturaleducationyYears;
	}

	public List<EconomicYear> getEconomicYears() {
		return economicYears;
	}

	public void setEconomicYears(List<EconomicYear> economicYears) {
		this.economicYears = economicYears;
	}

	public List<Policy> getPolicys() {
		return policys;
	}

	public void setPolicys(List<Policy> policys) {
		this.policys = policys;
	}

	public List<Population> getPopulations() {
		return populations;
	}

	public void setPopulations(List<Population> populations) {
		this.populations = populations;
	}

	public List<TrafficLine> getHighwayList() {
		return highwayList;
	}

	public void setHighwayList(List<TrafficLine> highwayList) {
		this.highwayList = highwayList;
	}

	public List<TrafficLine> getRailwayList() {
		return railwayList;
	}

	public void setRailwayList(List<TrafficLine> railwayList) {
		this.railwayList = railwayList;
	}

	public List<PublicOpinion> getPublicopinions() {
		return publicopinions;
	}

	public void setPublicopinions(List<PublicOpinion> publicopinions) {
		this.publicopinions = publicopinions;
	}

	@Override
	public String toString() {
		return "CityinfoThree [id=" + id + ", cityname=" + cityname
				+ ", citylvl=" + citylvl + ", provinces=" + provinces
				+ ", airport=" + airport + ", airportpinyin=" + airportpinyin
				+ ", citytype=" + citytype + ", largeenterprisenumber="
				+ largeenterprisenumber + ", enterpriseenumeration="
				+ enterpriseenumeration + ", sitesnumber5a=" + sitesnumber5a
				+ ", sitesenumeration5a=" + sitesenumeration5a
				+ ", sitesnumber4a=" + sitesnumber4a + ", sitesenumeration4a="
				+ sitesenumeration4a + ", famousuniversities="
				+ famousuniversities + ", famousuniversitiesenumeration="
				+ famousuniversitiesenumeration + ", culturaleducationyYears="
				+ culturaleducationyYears + ", economicYears=" + economicYears
				+ ", policys=" + policys + ", populations=" + populations
				+ ", highwayList=" + highwayList + ", railwayList="
				+ railwayList + ", publicopinions=" + publicopinions + "]";
	}
}