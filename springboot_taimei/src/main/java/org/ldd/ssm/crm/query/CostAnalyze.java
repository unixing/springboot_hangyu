package org.ldd.ssm.crm.query;

import java.util.List;
/**
 * 成本分析
 * @author wxm
 *
 * @date 2017-7-26
 */
public class CostAnalyze {
	//预测结果
	private FocastReslut focastResult;
	//机场信息
	private List<AirportInfoData> airportList;
	//航司信息
	private AirCompanyInfoQuery airCompanyInfo;
	
	public FocastReslut getFocastResult() {
		return focastResult;
	}
	public void setFocastResult(FocastReslut focastResult) {
		this.focastResult = focastResult;
	}
	public List<AirportInfoData> getAirportList() {
		return airportList;
	}
	public void setAirportList(List<AirportInfoData> airportList) {
		this.airportList = airportList;
	}
	public AirCompanyInfoQuery getAirCompanyInfo() {
		return airCompanyInfo;
	}
	public void setAirCompanyInfo(AirCompanyInfoQuery airCompanyInfo) {
		this.airCompanyInfo = airCompanyInfo;
	}
}
