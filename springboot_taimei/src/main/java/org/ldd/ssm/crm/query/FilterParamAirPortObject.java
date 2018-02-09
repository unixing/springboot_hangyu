package org.ldd.ssm.crm.query;

import java.util.List;

import org.ldd.ssm.crm.domain.AirInfoData;
/**
 * 航线高级筛选返回对象
 * @author Taimei
 *
 */
public class FilterParamAirPortObject {
	private List<AirInfoData> lists;

	public List<AirInfoData> getLists() {
		return lists;
	}

	public void setLists(List<AirInfoData> lists) {
		this.lists = lists;
	}

	@Override
	public String toString() {
		return "FilterParamAirPortObject [lists=" + lists + "]";
	}
}
