package org.ldd.ssm.crm.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class Z_AirdataRowMapper implements RowMapper<Z_Airdata>{

	@Override
	public Z_Airdata mapRow(ResultSet rs, int arg1) throws SQLException {
		Z_Airdata z = new Z_Airdata();
//		z.setA_Tak_Ppt(rs.getInt("A_Tak_Ppt"));
//		z.setArrv_Airpt_Cd(rs.getString("Arrv_Airpt_Cd"));
//		z.setAvg_Dct(rs.getBigDecimal("Avg_Dct"));
//		z.setChildren(rs.getInt("Children"));
//		z.setCompany(rs.getLong("company_id"));
//		z.setData_Date(rs.getString("Data_Date"));
//		z.setDta_Sce(rs.getString("Dta_Sce"));
//		z.setE_Tak_Ppt(rs.getInt("E_Tak_Ppt"));
//		z.setEch_Cls_Grp(rs.getInt("Ech_Cls_Grp"));
//		z.setEgs_Lod_Fts(rs.getBigDecimal("Egs_Lod_Fts"));
//		z.setEht_Dnt_Ppt(rs.getInt("Eht_Dnt_Ppt"));
//		z.setEht_Five_Dnt_Ppt(rs.getInt("Eht_Five_Ppt"));
//		z.setFlt_Nbr(rs.getString("Flt_Nbr"));
//		z.setFul_Pce_Ppt(rs.getInt("Ful_Pce_Ppt"));
//		z.setFur_Dnt_Ppt(rs.getInt("Fur_Dnt_Ppt"));
//		z.setFur_Fve_Dnt_Ppt(rs.getInt("Fur_Fve_Dnt_Ppt"));
//		z.setFve_Dnt_Ppt(rs.getInt("Fve_Dnt_Ppt"));
//		z.setFve_Fve_Dnt_Ppt(rs.getInt("Fve_Fve_Dnt_Ppt"));
//		z.setGrp_Dct(rs.getBigDecimal("Grp_Dct"));
//		z.setGrp_Fng_Per(rs.getInt("Grp_Fnt_Per"));
//		z.setGrp_Fng_Rte(rs.getBigDecimal("Grp_Fng_Rte"));
//		z.setGrp_Ine(rs.getBigDecimal("Grp_Ine"));
//		z.setGrp_Nbr(rs.getInt("Grp_Nbr"));
//		z.setGrp_Ppt(rs.getBigDecimal("Grp_Ppt"));
//		z.setH_Tak_Ppt(rs.getInt("H_Tak_Ppt"));
//		z.setI_Tak_Ppt(rs.getInt("I_Tak_Ppt"));
//		z.setId(rs.getInt("id"));
//		z.setIdd_Dct(rs.getBigDecimal("Idd_Dct"));
//		z.setItia(rs.getString("Itia"));
//		z.setLcl_Arrv_Tm(rs.getString("Lcl_Arrv_Tm"));
//		z.setLcl_Dpt_Day_Str(rs.getString("Lcl_Dpt_Day_Str"));
//		z.setLcl_Dpt_Tm(rs.getString("Lcl_Dpt_Tm"));
//		z.setNne_Dnt_Ppt(rs.getInt("Nne_Dnt_Ppt"));
//		z.setO_Tak_Ppt(rs.getInt("O_Tak_Ppt"));
//		z.setPgs_Per_Cls(rs.getInt("Pgs_Per_Cls"));
//		z.setR_Tak_Ppt(rs.getInt("R_Tak_Ppt"));
//		z.setS_Tak_Ppt(rs.getInt("S_Tak_Ppt"));
//		z.setSailingDistance(rs.getInt("sailingDistance"));
//		z.setSal_Tak_Ppt(rs.getInt("Sal_Tak_Ppt"));
//		z.setSen_Dnt_Ppt(rs.getInt("Sen_Dnt_Ppt"));
//		z.setSen_Five_Dnt_Ppt(rs.getInt("Sen_Five_Dnt_Ppt"));
//		z.setSet_Ktr_Ine(rs.getBigDecimal("Set_Ktr_Ine"));
//		z.setSix_Dnt_Ppt(rs.getInt("Six_Dnt_Ppt"));
//		z.setSix_Five_Dnt_Ppt(rs.getInt("Six_Five_Dnt_Ppt"));
//		z.setTal_Nbr_Set(rs.getInt("Tal_Nbr_Set"));
//		z.setThr_Dnt_Ppt(rs.getInt("Thr_Dnt_Ppt"));
//		z.setThr_Fve_Dnt_Ppt(rs.getInt("Thr_Fve_Dnt_Ppt"));
//		z.setTotalNumber(rs.getInt("Tal_Nbr"));
//		z.setTwo_Dnt_Ppt(rs.getInt("Two_Dnt_Ppt"));
//		z.setTwo_Fve_Dnt_Ppt(rs.getInt("Two_Fve_Dnt_Ppt"));
//		z.setTwo_Tak_Ppt(rs.getInt("Two_Tak_Ppt"));
//		z.setU_Tak_Ppt(rs.getInt("U_Tak_Ppt"));
//		z.setX_Tak_Ppt(rs.getInt("X_Tak_Ppt"));
//		z.setyBFare(rs.getInt("yBFare"));
//		z.setZ_Tak_Ppt(rs.getInt("Z_Tak_Ppt"));
//		z.setFlt_Rte_Cd(rs.getString("Flt_Rte_Cd"));
		z.setLcl_Dpt_Day(Date.valueOf(rs.getString("Lcl_Dpt_Day")));
		z.setPgs_Per_Cls(rs.getInt("Pgs_Per_Cls"));
		return z;
	}

}
