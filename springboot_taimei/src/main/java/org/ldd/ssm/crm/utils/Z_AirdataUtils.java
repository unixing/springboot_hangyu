package org.ldd.ssm.crm.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ldd.ssm.crm.domain.Z_Airdata;

/**
 * 此工具方法, 只有一个作用, 哪就是分析出, 哪些是有用数据, 哪些是没用数据,分别进行存储
 * 
 * @author Taimei
 * 
 */
public class Z_AirdataUtils {

	public static List<List<String[]>> saveExecl(List<String[]> imp)
			throws Exception {
		// 有用数据的存储
		List<String[]> useful = new ArrayList<String[]>();
		// 无用数据的存储
		List<String[]> useless = new ArrayList<String[]>();
		// 装两种数据
		List<List<String[]>> useTwo = new ArrayList<List<String[]>>();
		for (int i = 0; i < imp.size(); i++) {
			// 验证日期格式, 如果日期格式错误, 抛出异常
			if (imp.get(i)[0] == "") {
				continue;
			}
			if (!ValidationDataUtils.isDate(imp.get(i)[0])) {
				useless.add(imp.get(i));
				continue;
			}
			// 验证航线是否符合规定
			String validationFlightLineLength = ValidationDataUtils.validationFlightLineLength(imp.get(i)[4],useful, imp.get(i));
			if (validationFlightLineLength==null) {
				useless.add(imp.get(i));
				continue;
			}else {
				imp.get(i)[4]=validationFlightLineLength;
			}
			// 验证航程是否符合规则,航程要包含在航线里面，且为同一个始发地或者同一到达地
			if (!ValidationDataUtils.validationVoyageLength(imp.get(i)[1]) && ValidationDataUtils.isContains(imp.get(i)[4],imp.get(i)[1])) {
				useless.add(imp.get(i));
				continue;
			}
			// 验证航班号是否符合规定
			if (!ValidationDataUtils.validationFlightLength(imp.get(i)[2])) {
				useless.add(imp.get(i));
				continue;
			}
			// 验证座位总数是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[8]);
			
			if (NumberValidationUtils.isDecimal(imp.get(i)[6])<1) {
				useless.add(imp.get(i));
				continue;
			}
			// 验证每班旅客是否为为空,并且是否为不可计算字符
			if (NumberValidationUtils.isDecimal(imp.get(i)[10])<=0.0) {
				useless.add(imp.get(i));
				continue;
			}
			if(NumberValidationUtils.isDecimal(imp.get(i)[8])==null&&NumberValidationUtils.isDecimal(imp.get(i)[8])==0.0&&NumberValidationUtils.isDecimal(imp.get(i)[10])==null&&NumberValidationUtils.isDecimal(imp.get(i)[10])==0.0){
				useless.add(imp.get(i));
				continue;
			}
			// 验证团队人数是否为为空,并且是否为不可计算字符
			if (NumberValidationUtils.isDecimal(imp.get(i)[14])<0.0) {
				useless.add(imp.get(i));
				continue;
			}
			// 验证每班团队是否为为空,并且是否为不可计算字符
			if (NumberValidationUtils.isDecimal(imp.get(i)[15]) <0.0) {
				useless.add(imp.get(i));
				continue;
			}
			// 验证团队成行率是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[16]);
			// 验证团队比例是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[17]);
			// 验证收益客座率是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[19]);
			// 验证收益客座率是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[20]);
			// 验证均折是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[23]);
			// 验证散客折是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[24]);
			// 验证团队折扣是否为为空,并且是否为不可计算字符
			NumberValidationUtils.isDecimal(imp.get(i)[25]);
			// ----------------------------以下为折扣区域---------------------------------
			NumberValidationUtils.isDecimal(imp.get(i)[25]);
			NumberValidationUtils.isDecimal(imp.get(i)[26]);
			NumberValidationUtils.isDecimal(imp.get(i)[27]);
			NumberValidationUtils.isDecimal(imp.get(i)[28]);
			NumberValidationUtils.isDecimal(imp.get(i)[29]);
			NumberValidationUtils.isDecimal(imp.get(i)[30]);
			NumberValidationUtils.isDecimal(imp.get(i)[31]);
			NumberValidationUtils.isDecimal(imp.get(i)[32]);
			NumberValidationUtils.isDecimal(imp.get(i)[33]);
			NumberValidationUtils.isDecimal(imp.get(i)[34]);
			NumberValidationUtils.isDecimal(imp.get(i)[35]);
			NumberValidationUtils.isDecimal(imp.get(i)[36]);
			NumberValidationUtils.isDecimal(imp.get(i)[37]);
			NumberValidationUtils.isDecimal(imp.get(i)[38]);
			NumberValidationUtils.isDecimal(imp.get(i)[39]);
			NumberValidationUtils.isDecimal(imp.get(i)[40]);
			NumberValidationUtils.isDecimal(imp.get(i)[41]);
			NumberValidationUtils.isDecimal(imp.get(i)[42]);
			NumberValidationUtils.isDecimal(imp.get(i)[43]);
			NumberValidationUtils.isDecimal(imp.get(i)[44]);
			NumberValidationUtils.isDecimal(imp.get(i)[45]);
			NumberValidationUtils.isDecimal(imp.get(i)[46]);
			NumberValidationUtils.isDecimal(imp.get(i)[47]);
			NumberValidationUtils.isDecimal(imp.get(i)[48]);
			NumberValidationUtils.isDecimal(imp.get(i)[49]);
			useful.add(imp.get(i));
		}
		useTwo.add(useful);
		useTwo.add(useless);
		return useTwo;
	}
	
	public static List<Z_Airdata> getWrongData(List<String[]> imp,String itia) {
		
		List<Z_Airdata> airdatas = new ArrayList<Z_Airdata>();
		for (int i = 0; i < imp.size(); i++) {
			Z_Airdata airdata = new Z_Airdata();
//			airdata.setCompany(company_id);
			airdata.setItia(itia);
			airdata.setLcl_Dpt_Day(ValidationDataUtils.getDate(imp.get(i)[0])); // 航班执行日期
			airdata.setDpt_AirPt_Cd(imp.get(i)[1].substring(0, 3));// 始发地
			airdata.setArrv_Airpt_Cd(imp.get(i)[1].substring(3, 6));// 到达地
			airdata.setFlt_Nbr(imp.get(i)[2]);// 航班号
			airdata.setCpy_Nm(imp.get(i)[3]);// 公司
			airdata.setFlt_Rte_Cd(imp.get(i)[4]);// 航线
			airdata.setLcl_Dpt_Tm(imp.get(i)[5]);//时刻
			airdata.setTal_Nbr_Set(((NumberValidationUtils.isDecimal(imp.get(i)[8])
					.intValue())));// 座位总数
			airdata.setPgs_Per_Cls(((NumberValidationUtils.isDecimal(imp.get(i)[10])
					.intValue())));// 旅客人数
			airdata.setGrp_Nbr(((NumberValidationUtils.isDecimal(imp.get(i)[14])
					.intValue()))); // 团队人数
			airdata.setEch_Cls_Grp(((NumberValidationUtils.isDecimal(imp.get(i)[15])
					.intValue())));// 每班团队
			airdata.setGrp_Fng_Rte(new BigDecimal(NumberValidationUtils
					.isDecimal(imp.get(i)[16]))); // 团队成行率
			airdata.setGrp_Ppt(new BigDecimal(NumberValidationUtils
					.isDecimal(imp.get(i)[17])));// 团队比例
			airdata.setEgs_Lod_Fts(new BigDecimal(NumberValidationUtils
					.isDecimal(imp.get(i)[19])));// 收益客座率
			airdata.setTotalNumber(NumberValidationUtils
					.isDecimal(imp.get(i)[20]).intValue());
			airdata.setAvg_Dct(new BigDecimal(NumberValidationUtils
					.isDecimal(imp.get(i)[23])));
			airdata.setIdd_Dct(new BigDecimal(NumberValidationUtils
					.isDecimal(imp.get(i)[24])));// 散客折扣
			airdata.setGrp_Dct(new BigDecimal(NumberValidationUtils
					.isDecimal(imp.get(i)[25])));// 团队折扣
			airdata.setTwo_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[26], imp.get(i)[10]));// 两舱比例
			airdata.setFul_Pce_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[27], imp.get(i)[10]));// 全价比例
			airdata.setNne_Dnt_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[28], imp.get(i)[10]));// 9折比例
			airdata.setEht_Five_Dnt_Ppt(ValidationDataUtils
					.ProportionalOfNumber(imp.get(i)[29], imp.get(i)[10])); // 8.5折比例
			airdata.setEht_Dnt_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[30], imp.get(i)[10])); // 8折比例
			airdata.setSen_Five_Dnt_Ppt(ValidationDataUtils
					.ProportionalOfNumber(imp.get(i)[31], imp.get(i)[10])); // 7.5折比例
			airdata.setSen_Dnt_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[32], imp.get(i)[10]));// 7折比例
			airdata.setSix_Five_Dnt_Ppt(ValidationDataUtils
					.ProportionalOfNumber(imp.get(i)[33], imp.get(i)[10]));// 6.5折比例
			airdata.setSix_Dnt_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[34], imp.get(i)[10]));// 6折比例
			airdata.setFve_Fve_Dnt_Ppt(ValidationDataUtils
					.ProportionalOfNumber(imp.get(i)[35], imp.get(i)[10]));// 5.5折比例
			airdata.setFve_Dnt_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[36], imp.get(i)[10]));// 5折比例
			airdata.setFur_Fve_Dnt_Ppt(ValidationDataUtils
					.ProportionalOfNumber(imp.get(i)[37], imp.get(i)[10]));// 4.5折比例
			airdata.setFur_Dnt_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[38], imp.get(i)[10]));// 4折比例
			airdata.setSal_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[39], imp.get(i)[10])); // 特殊舱比例
			airdata.setR_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[40], imp.get(i)[10]));// R舱比例
			airdata.setU_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[41], imp.get(i)[10]));// U舱比例
			airdata.setI_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[42], imp.get(i)[10])); // I舱比例
			airdata.setZ_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[43], imp.get(i)[10])); // Z舱比例
			airdata.setE_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[44], imp.get(i)[10]));// E舱比例
			airdata.setA_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[45], imp.get(i)[10]));// A舱比例
			airdata.setO_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[46], imp.get(i)[10])); // O舱比例
			airdata.setS_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[47], imp.get(i)[10])); // S舱比例
			airdata.setH_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[48], imp.get(i)[10]));// H舱比例
			airdata.setX_Tak_Ppt(ValidationDataUtils.ProportionalOfNumber(
					imp.get(i)[49], imp.get(i)[10])); // X舱比例
			airdata.setChildren(ValidationDataUtils.isInt(imp.get(i)[50]));// 儿童个数
			airdata.setDta_Dte(new Date());// 数据导入日期
			airdatas.add(airdata);
		}
		return airdatas;
	}
}
