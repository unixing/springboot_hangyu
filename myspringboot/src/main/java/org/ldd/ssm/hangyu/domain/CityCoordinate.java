package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class CityCoordinate implements Serializable {
    private Long id;

    private String cityIcao;

    private String cityName;

    private String cityCoordinate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityIcao() {
        return cityIcao;
    }

    public void setCityIcao(String cityIcao) {
        this.cityIcao = cityIcao == null ? null : cityIcao.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getCityCoordinate() {
        return cityCoordinate;
    }

    public void setCityCoordinate(String cityCoordinate) {
        this.cityCoordinate = cityCoordinate == null ? null : cityCoordinate.trim();
    }

	@Override
	public String toString() {
		return "CityCoordinate [id=" + id + ", cityIcao=" + cityIcao
				+ ", cityName=" + cityName + ", cityCoordinate="
				+ cityCoordinate + "]";
	}
}