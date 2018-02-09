package org.ldd.ssm.crm.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ldd.ssm.crm.domain.MonthSalePlan;
import org.springframework.jdbc.core.RowMapper;

public class MonthSalePlanRowMapper implements RowMapper<MonthSalePlan> {

	@Override
	public MonthSalePlan mapRow(ResultSet rs, int index) throws SQLException {
		MonthSalePlan m =new MonthSalePlan();
		m.setAgreement(rs.getString("Agreement"));
		m.setAllFold(rs.getDouble("All_Fold"));
		m.setDiscountReturn(rs.getDouble("Discount_Return"));
		m.setEstDisposePlan(rs.getString("Est_Dispose_Plan"));
		m.setEstPosiNbr(rs.getInt("Est_Posi_Nbr"));
		m.setfITNbr(rs.getInt("FIT_Nbr"));
		m.setfITSaleCycle(rs.getInt("FIT_Sale_Cycle"));
		m.setFltNbr(rs.getString("Flt_Nbr"));
		m.setFrequency(rs.getInt("Frequency"));
		m.setGroupNbr(rs.getInt("Group_Nbr"));
		m.setGroupSaleCycle(rs.getInt("Group_Sale_Cycle"));
		m.setId(rs.getInt("id"));
		m.setLowestSale(rs.getDouble("Lowest_Sale"));
		m.setStatus(rs.getInt("Status"));
		m.setTimeId(m.getTimeId());
		return m;
	}

}
