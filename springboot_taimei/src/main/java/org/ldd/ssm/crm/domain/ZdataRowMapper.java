package org.ldd.ssm.crm.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ZdataRowMapper implements RowMapper<Z_Airdata> {

	@Override
	public Z_Airdata mapRow(ResultSet rs, int arg1) throws SQLException {
		Z_Airdata z = new Z_Airdata();
		z.setTwo_Tak_Ppt(rs.getInt("Two_Tak_Ppt"));
		z.setGrp_Fng_Rte(rs.getBigDecimal("Grp_Fng_Rte"));
		z.setIdd_Dct(rs.getBigDecimal("Idd_Dct"));
		z.setEch_Cls_Grp(rs.getInt("Ech_Cls_Grp"));
		z.setGrp_Dct(rs.getBigDecimal("Grp_Dct"));
		z.setGrp_Nbr(rs.getInt("Grp_Nbr"));
		z.setyBFare(rs.getInt("yBFare"));
		return z;
	}

}
