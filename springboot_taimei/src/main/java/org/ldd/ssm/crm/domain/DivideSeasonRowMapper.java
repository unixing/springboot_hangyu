package org.ldd.ssm.crm.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DivideSeasonRowMapper implements RowMapper<DivideSeason> {

	@Override
	public DivideSeason mapRow(ResultSet rs, int arg1) throws SQLException {
		DivideSeason season = new DivideSeason();
		season.setDivideId(rs.getInt("Divide_Id"));
		season.setArrvAirptCd(rs.getString("Arrv_Airpt_Cd"));
		season.setAvgGoCust(rs.getInt("Avg_Go_Cust"));
		season.setAvgReturnCust(rs.getInt("Avg_Return_Cust"));
		season.setBackForwardOffset(rs.getDouble("Back_Forward_Offset"));
		season.setBackReverseOffset(rs.getDouble("Back_Reverse_Offset"));
		season.setCurrTrans(rs.getDouble("Curr_Trans"));
		season.setDays(rs.getInt("Days"));
		season.setDescription(rs.getString("Description"));
		season.setDivideMonth(rs.getString("Divide_Month"));
		season.setDptAirptCd(rs.getString("Dpt_Airpt_Cd"));
		season.setFltNbr(rs.getString("Flt_Nbr"));
		season.setFltRteCd(rs.getString("Flt_Rte_Cd"));
		season.setQoqTrans(rs.getDouble("Qoq_Trans"));
		season.setRefFlt(rs.getString("Ref_Flt"));
		season.setToForwardOffset(rs.getDouble("To_Forward_Offset"));
		season.setToReverseOffset(rs.getDouble("To_Reverse_Offset"));
		season.setYoyTrans(rs.getDouble("Yoy_Trans"));
		return season;
	}

}
