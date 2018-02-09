package org.ldd.ssm.crm.domain;
/**
 * 机场详细信息实体类
 * @Title:CityDetailInfo 
 * @Description:
 * @author taimei-gds 
 * @date 2017-2-28 上午11:05:22
 */
public class CityDetailInfo {
	private String id;        
	private String airport;					  //机场三字码
	private String cityName;                 //城市名称         
	private String cityLvl;            //行政区级别  
	private String provinces;                  //所在省份          
	private String airportPinyin;           //全拼      
	private String cityType;                 //城市类型        
	private String largeEnterpriseNumber;            //大型企业数量           
	private String enterpriseEnumeration;              //企业枚举           
	private String sitesNumber5A;              //5A景点数量         
	private String sitesEnumeration5A;              //景点枚举5A    
	private String sitesNumber4A;                 //4A景点数量           
	private String sitesEnumeration4A;            //景点枚举4A           
	private String famousUniversities;           //著名高校         
	private String famousUniversitiesEnumeration;         //著名高校枚举         
	public CityDetailInfo() {
		super();
	}
	/**
	 * @param id
	 * @param airport
	 * @param cityName
	 * @param cityLvl
	 * @param provinces
	 * @param airportPinyin
	 * @param cityType
	 * @param largeEnterpriseNumber
	 * @param enterpriseEnumeration
	 * @param sitesNumber5A
	 * @param sitesEnumeration5A
	 * @param sitesNumber4A
	 * @param sitesEnumeration4A
	 * @param famousUniversities
	 * @param famousUniversitiesEnumeration
	 */
	public CityDetailInfo(String id, String airport, String cityName,
			String cityLvl, String provinces, String airportPinyin,
			String cityType, String largeEnterpriseNumber,
			String enterpriseEnumeration, String sitesNumber5A,
			String sitesEnumeration5A, String sitesNumber4A,
			String sitesEnumeration4A, String famousUniversities,
			String famousUniversitiesEnumeration) {
		super();
		this.id = id;
		this.airport = airport;
		this.cityName = cityName;
		this.cityLvl = cityLvl;
		this.provinces = provinces;
		this.airportPinyin = airportPinyin;
		this.cityType = cityType;
		this.largeEnterpriseNumber = largeEnterpriseNumber;
		this.enterpriseEnumeration = enterpriseEnumeration;
		this.sitesNumber5A = sitesNumber5A;
		this.sitesEnumeration5A = sitesEnumeration5A;
		this.sitesNumber4A = sitesNumber4A;
		this.sitesEnumeration4A = sitesEnumeration4A;
		this.famousUniversities = famousUniversities;
		this.famousUniversitiesEnumeration = famousUniversitiesEnumeration;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityDetailInfo [id=" + id + ", airport=" + airport
				+ ", cityName=" + cityName + ", cityLvl=" + cityLvl
				+ ", provinces=" + provinces + ", airportPinyin="
				+ airportPinyin + ", cityType=" + cityType
				+ ", largeEnterpriseNumber=" + largeEnterpriseNumber
				+ ", enterpriseEnumeration=" + enterpriseEnumeration
				+ ", sitesNumber5A=" + sitesNumber5A + ", sitesEnumeration5A="
				+ sitesEnumeration5A + ", sitesNumber4A=" + sitesNumber4A
				+ ", sitesEnumeration4A=" + sitesEnumeration4A
				+ ", famousUniversities=" + famousUniversities
				+ ", famousUniversitiesEnumeration="
				+ famousUniversitiesEnumeration + "]";
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}
	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the cityLvl
	 */
	public String getCityLvl() {
		return cityLvl;
	}
	/**
	 * @param cityLvl the cityLvl to set
	 */
	public void setCityLvl(String cityLvl) {
		this.cityLvl = cityLvl;
	}
	/**
	 * @return the provinces
	 */
	public String getProvinces() {
		return provinces;
	}
	/**
	 * @param provinces the provinces to set
	 */
	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}
	/**
	 * @return the airportPinyin
	 */
	public String getAirportPinyin() {
		return airportPinyin;
	}
	/**
	 * @param airportPinyin the airportPinyin to set
	 */
	public void setAirportPinyin(String airportPinyin) {
		this.airportPinyin = airportPinyin;
	}
	/**
	 * @return the cityType
	 */
	public String getCityType() {
		return cityType;
	}
	/**
	 * @param cityType the cityType to set
	 */
	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	/**
	 * @return the largeEnterpriseNumber
	 */
	public String getLargeEnterpriseNumber() {
		return largeEnterpriseNumber;
	}
	/**
	 * @param largeEnterpriseNumber the largeEnterpriseNumber to set
	 */
	public void setLargeEnterpriseNumber(String largeEnterpriseNumber) {
		this.largeEnterpriseNumber = largeEnterpriseNumber;
	}
	/**
	 * @return the enterpriseEnumeration
	 */
	public String getEnterpriseEnumeration() {
		return enterpriseEnumeration;
	}
	/**
	 * @param enterpriseEnumeration the enterpriseEnumeration to set
	 */
	public void setEnterpriseEnumeration(String enterpriseEnumeration) {
		this.enterpriseEnumeration = enterpriseEnumeration;
	}
	/**
	 * @return the sitesNumber5A
	 */
	public String getSitesNumber5A() {
		return sitesNumber5A;
	}
	/**
	 * @param sitesNumber5A the sitesNumber5A to set
	 */
	public void setSitesNumber5A(String sitesNumber5A) {
		this.sitesNumber5A = sitesNumber5A;
	}
	/**
	 * @return the sitesEnumeration5A
	 */
	public String getSitesEnumeration5A() {
		return sitesEnumeration5A;
	}
	/**
	 * @param sitesEnumeration5A the sitesEnumeration5A to set
	 */
	public void setSitesEnumeration5A(String sitesEnumeration5A) {
		this.sitesEnumeration5A = sitesEnumeration5A;
	}
	/**
	 * @return the sitesNumber4A
	 */
	public String getSitesNumber4A() {
		return sitesNumber4A;
	}
	/**
	 * @param sitesNumber4A the sitesNumber4A to set
	 */
	public void setSitesNumber4A(String sitesNumber4A) {
		this.sitesNumber4A = sitesNumber4A;
	}
	/**
	 * @return the sitesEnumeration4A
	 */
	public String getSitesEnumeration4A() {
		return sitesEnumeration4A;
	}
	/**
	 * @param sitesEnumeration4A the sitesEnumeration4A to set
	 */
	public void setSitesEnumeration4A(String sitesEnumeration4A) {
		this.sitesEnumeration4A = sitesEnumeration4A;
	}
	/**
	 * @return the famousUniversities
	 */
	public String getFamousUniversities() {
		return famousUniversities;
	}
	/**
	 * @param famousUniversities the famousUniversities to set
	 */
	public void setFamousUniversities(String famousUniversities) {
		this.famousUniversities = famousUniversities;
	}
	/**
	 * @return the famousUniversitiesEnumeration
	 */
	public String getFamousUniversitiesEnumeration() {
		return famousUniversitiesEnumeration;
	}
	/**
	 * @param famousUniversitiesEnumeration the famousUniversitiesEnumeration to set
	 */
	public void setFamousUniversitiesEnumeration(
			String famousUniversitiesEnumeration) {
		this.famousUniversitiesEnumeration = famousUniversitiesEnumeration;
	}
	
}
