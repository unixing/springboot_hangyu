package org.ldd.ssm.crm.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DivideTimeRowMapper implements RowMapper<DivideTime> {

	@Override
	public DivideTime mapRow(ResultSet rs, int arg1) throws SQLException {
		DivideTime time = new DivideTime();
		time.setDivideId(rs.getInt("Divide_Id"));
		time.setSeasonId(rs.getInt("Season_Id"));
		time.setFltDirect(rs.getString("Flt_Direct"));
		time.setEndDate(rs.getString("End_Date"));
		time.setStartDate(rs.getString("Start_Date"));
		time.setNature(rs.getString("Nature"));
		time.setRefSaleEndDate(rs.getString("Ref_Sale_End_Date"));
		time.setRefSaleStartDate(rs.getString("Ref_Sale_Start_Date"));
		return time;
	}

}
