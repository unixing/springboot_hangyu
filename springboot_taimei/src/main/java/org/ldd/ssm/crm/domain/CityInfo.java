package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;

public class CityInfo {
    private Integer id;

    private String cityname;

    private Long economy;

    private Integer population;

    private BigDecimal percapita;

    private String structure;

    private String traffic;

    private BigDecimal area;

    private String popedom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public Long getEconomy() {
        return economy;
    }

    public void setEconomy(Long economy) {
        this.economy = economy;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public BigDecimal getPercapita() {
        return percapita;
    }

    public void setPercapita(BigDecimal percapita) {
        this.percapita = percapita;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure == null ? null : structure.trim();
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic == null ? null : traffic.trim();
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getPopedom() {
        return popedom;
    }

    public void setPopedom(String popedom) {
        this.popedom = popedom == null ? null : popedom.trim();
    }
}