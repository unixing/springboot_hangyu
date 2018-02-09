package org.ldd.ssm.crm.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DOWQuery;
/**
 * 创建查询参数的方法
 * @author Taimei
 *
 */
public class ParameterUtils {
	/**
	 * 判断是否有经停, 以此来往返(4条参数), 或者往返带经停的参数(6条参数)
	 * @param dowQueries
	 * @return
	 */
	public static List<DOWQuery> getArrayParameter(DOWQuery dow) {
		// 装去程航线的航段容器
		List<String[]> toList = new ArrayList<String[]>();
		// 装去程三字码
		List<String[]> toList_itia = new ArrayList<String[]>();
		// 装反程航线的航段容器
		List<String[]> returnList = new ArrayList<String[]>();
		// 装返程三字码
		List<String[]> returnList_itia = new ArrayList<String[]>();
		// 暂时缓存
		List<DOWQuery> dowQueries = new ArrayList<DOWQuery>();
		// 新数据存放
		List<DOWQuery> newQueries = new ArrayList<DOWQuery>();
		String dpt_AirPt_Cd = dow.getChina_dpt_AirPt_Cd();
		String arrv_Airpt_Cd = dow.getChina_arrv_Airpt_Cd();
		String pas_stp = dow.getChina_pas_stp();
		//航班号
		String[] split = {};
		if(!TextUtil.isEmpty(dow.getFlt_nbr())&&!dow.getFlt_nbr().equals("汇总")){
			String flt_nbr = dow.getFlt_nbr();
			String replaceAll = flt_nbr.replaceAll("航班号", "");
			split = replaceAll.split("/");
		}
		if (dow.getPas_stp() != "" && dow.getPas_stp() != null) {
			// 去程
			toList.add(new String[] { dow.getDpt_AirPt_Cd(),dow.getArrv_Airpt_Cd() });
			toList.add(new String[] { dow.getDpt_AirPt_Cd(), dow.getPas_stp() });
			toList.add(new String[] { dow.getPas_stp(), dow.getArrv_Airpt_Cd() });
			// 返程
			returnList.add(new String[] { dow.getArrv_Airpt_Cd(),dow.getPas_stp() });
			returnList.add(new String[] { dow.getPas_stp(),dow.getDpt_AirPt_Cd() });
			returnList.add(new String[] { dow.getArrv_Airpt_Cd(),dow.getDpt_AirPt_Cd() });
			// 去程
			toList_itia.add(new String[] { dow.getChina_dpt_AirPt_Cd(),dow.getChina_arrv_Airpt_Cd() });
			toList_itia.add(new String[] { dow.getChina_dpt_AirPt_Cd(), dow.getChina_pas_stp() });
			toList_itia.add(new String[] { dow.getChina_pas_stp(), dow.getChina_arrv_Airpt_Cd() });
						// 返程
			returnList_itia.add(new String[] { dow.getChina_arrv_Airpt_Cd(),dow.getChina_pas_stp() });
			returnList_itia.add(new String[] { dow.getChina_pas_stp(),dow.getChina_dpt_AirPt_Cd() });
			returnList_itia.add(new String[] { dow.getChina_arrv_Airpt_Cd(),dow.getChina_dpt_AirPt_Cd() });
			// 创建去程3个查询对象
			DOWQuery dowQuery;
			for (int i = 0; i < 3; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(dpt_AirPt_Cd + pas_stp + arrv_Airpt_Cd);
				if(split.length>0){
					dowQuery.setFlt_nbr(split[0]);
				}
				dowQueries.add(dowQuery);
			}
			// 创建返程三个查询对象
			for (int i = 0; i < 3; i++) {
				dowQuery = new DOWQuery();
				if(split.length>0){
					dowQuery.setFlt_nbr(split[1]);
				}
				dowQuery.setFlt_Rte_Cd(arrv_Airpt_Cd + pas_stp + dpt_AirPt_Cd);
				dowQueries.add(dowQuery);
			}
			for (int i = 0; i < 3; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(toList.get(i)[0]);
				dowQuery2.setChina_dpt_AirPt_Cd(toList_itia.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(toList.get(i)[1]);
				dowQuery2.setChina_arrv_Airpt_Cd(toList_itia.get(i)[1]);
				newQueries.add(dowQuery2);
			}

			for (int i = 0; i < 3; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i + 3);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(returnList.get(i)[0]);
				dowQuery2.setChina_dpt_AirPt_Cd(returnList_itia.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(returnList.get(i)[1]);
				dowQuery2.setChina_arrv_Airpt_Cd(returnList_itia.get(i)[1]);
				newQueries.add(dowQuery2);
			}
			return newQueries;
		} else {
			// 去程
			toList.add(new String[] { dow.getDpt_AirPt_Cd(),dow.getArrv_Airpt_Cd() });
			// 返程
			returnList.add(new String[] {dow.getArrv_Airpt_Cd(),dow.getDpt_AirPt_Cd() });
			
			toList_itia.add(new String[] {dow.getChina_dpt_AirPt_Cd(),dow.getChina_arrv_Airpt_Cd()});
			returnList_itia.add(new String[] { dow.getChina_arrv_Airpt_Cd(),dow.getChina_pas_stp() });
			// 创建去程1个查询对象
			DOWQuery dowQuery;
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(dpt_AirPt_Cd + arrv_Airpt_Cd);
				if(split.length>0){
					dowQuery.setFlt_nbr(split[0]);
				}
				dowQueries.add(dowQuery);
			}
			// 创建返程三个查询对象
			for (int i = 0; i < 1; i++) {
				dowQuery = new DOWQuery();
				dowQuery.setFlt_Rte_Cd(arrv_Airpt_Cd + dpt_AirPt_Cd);
				if(split.length>0){
					dowQuery.setFlt_nbr(split[1]);
				}
				dowQueries.add(dowQuery);
			}
			for (int i = 0; i < 1; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(toList.get(i)[0]);
				dowQuery2.setChina_dpt_AirPt_Cd(toList_itia.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(toList.get(i)[1]);
				dowQuery2.setChina_arrv_Airpt_Cd(toList_itia.get(i)[1]);
				newQueries.add(dowQuery2);
			}

			for (int i = 0; i < 1; i++) {
				DOWQuery dowQuery2 = dowQueries.get(i + 1);
				dowQuery2.setLcl_Dpt_Day(dow.getLcl_Dpt_Day());
				dowQuery2.setDta_Sce(dow.getDta_Sce());
				dowQuery2.setCpy_Nm(dow.getCpy_Nm());
				dowQuery2.setDpt_AirPt_Cd(returnList.get(i)[0]);
				dowQuery2.setChina_dpt_AirPt_Cd(returnList_itia.get(i)[0]);
				dowQuery2.setArrv_Airpt_Cd(returnList.get(i)[1]);
				dowQuery2.setChina_arrv_Airpt_Cd(returnList_itia.get(i)[1]);
				newQueries.add(dowQuery2);
			}
			return newQueries;
		}
	}
	/**
	 * 获得查询条件中,始发到达匹配上的所有数据
	 * @param dta_Sce
	 * @param airdatas
	 * @return
	 */
	public static List<Z_Airdata> getZ_airDataByPara(DOWQuery dta_Sce,
			List<Z_Airdata> airdatas) {
		List<Z_Airdata> airdatas2 = new ArrayList<Z_Airdata>();
		for (Z_Airdata z_Airdata : airdatas) {
			if(z_Airdata.getDpt_AirPt_Cd().equals(dta_Sce.getChina_dpt_AirPt_Cd())
				&&z_Airdata.getArrv_Airpt_Cd().equals(dta_Sce.getChina_arrv_Airpt_Cd())
				&&z_Airdata.getFlt_Rte_Cd().equals(dta_Sce.getFlt_Rte_Cd())){
				airdatas2.add(z_Airdata);
			}
		}
		return airdatas2;
	}
	/**
	 * 对数据进行分月存储
	 * @param new_OutPort
	 * @return
	 */
	public static List<List<Z_Airdata>> getMethod_Z_Airdata(
			List<Z_Airdata> new_OutPort) {
		List<List<Z_Airdata>> lists = new ArrayList<List<Z_Airdata>>();
		List<Z_Airdata> Jan = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Feb = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Mar = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Apr = new ArrayList<Z_Airdata>();
		List<Z_Airdata> May = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Jun = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Jul = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Aug = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Sep = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Oct = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Nov = new ArrayList<Z_Airdata>();
		List<Z_Airdata> Dec = new ArrayList<Z_Airdata>();
		for (Z_Airdata z_Airdata : new_OutPort) {
			int month = getMonth(z_Airdata.getLcl_Dpt_Day());
			switch (month) {
			case 1:
				z_Airdata.setMonth(month+"");
				Jan.add(z_Airdata);
				break;
			case 2:
				z_Airdata.setMonth(month+"");
				Feb.add(z_Airdata);
				break;
			case 3:
				z_Airdata.setMonth(month+"");
				Mar.add(z_Airdata);
				break;
			case 4:
				z_Airdata.setMonth(month+"");
				Apr.add(z_Airdata);
				break;
			case 5:
				z_Airdata.setMonth(month+"");
				May.add(z_Airdata);
				break;
			case 6:
				z_Airdata.setMonth(month+"");
				Jun.add(z_Airdata);
				break;
			case 7:
				z_Airdata.setMonth(month+"");
				Jul.add(z_Airdata);
				break;
			case 8:
				z_Airdata.setMonth(month+"");
				Aug.add(z_Airdata);
				break;
			case 9:
				z_Airdata.setMonth(month+"");
				Sep.add(z_Airdata);
				break;
			case 10:
				z_Airdata.setMonth(month+"");
				Oct.add(z_Airdata);
				break;
			case 11:
				z_Airdata.setMonth(month+"");
				Nov.add(z_Airdata);
				break;
			case 12:
				z_Airdata.setMonth(month+"");
				Dec.add(z_Airdata);
				break;
			default:
				break;
			}
		}
		lists.add(Jan);//1
		lists.add(Feb);//2
		lists.add(Mar);//3
		lists.add(Apr);//4
		lists.add(May);//5
		lists.add(Jun);//6
		lists.add(Jul);//7
		lists.add(Aug);//8
		lists.add(Sep);//9
		lists.add(Oct);//10
		lists.add(Nov);//11
		lists.add(Dec);//12
		return lists;
	}
	 /**
     * 得到一天是一年的第几个月
     * 
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }
    /**
     * 统计数据
     * 
     * 
     * select count(id) Cla_Nbr,sum(Tal_Nbr_Set) tal_Nbr_Set,sum(Pgs_Per_Cls) idd_moh,sum(Grp_Nbr) grp_moh,
		sum(Grp_Ine) grp_Ine,sum(Tal_Nbr) tol_Ine,(sum(Tal_Nbr)/sum(Tal_Nbr_Set)) flt_Ser_Ine, avg(avg_Dct) avg_Dct,
		avg(Idd_Dct) idd_Dct,avg(Grp_Dct) grp_Dct
     * @param list
     * @return
     */
	public static OutPort getCountData(List<Z_Airdata> list) {
		OutPort outPort = new OutPort();
		
		outPort.setCla_Nbr(list.size());//班次
		String month = list.get(0).getMonth();
		for (Z_Airdata z_Airdata : list) {
			outPort.setTal_Nbr_Set(outPort.getTal_Nbr_Set()+z_Airdata.getTal_Nbr_Set());
			outPort.setIdd_moh(outPort.getIdd_moh()+z_Airdata.getPgs_Per_Cls());
			outPort.setGrp_moh(outPort.getGrp_moh()+z_Airdata.getGrp_Nbr());
			outPort.setGrp_Ine(outPort.getGrp_Ine()+z_Airdata.getGrp_Ine().intValue());
			outPort.setTol_Ine(outPort.getTol_Ine()+z_Airdata.getTotalNumber());
			outPort.setAvg_Dct(outPort.getAvg_Dct()+z_Airdata.getAvg_Dct().doubleValue());
			outPort.setIdd_Dct(outPort.getIdd_Dct()+z_Airdata.getIdd_Dct().doubleValue());
			outPort.setGrp_Dct(outPort.getGrp_Dct()+z_Airdata.getGrp_Dct().doubleValue());
		}
		//避免报错, 做异常判断
		if(outPort.getTal_Nbr_Set()!=0){
			outPort.setFlt_Ser_Ine(new BigDecimal(outPort.getTol_Ine()).divide( new BigDecimal(outPort.getTal_Nbr_Set()),2,BigDecimal.ROUND_HALF_UP).doubleValue());
		}else{
			outPort.setFlt_Ser_Ine(0);
		}
		if(list.size()!=0){
			BigDecimal bigDecimal = new BigDecimal(list.size());//班次
			outPort.setAvg_Dct(new BigDecimal(outPort.getAvg_Dct()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).doubleValue());//总折扣/班次=平均折扣
			outPort.setIdd_Dct(new BigDecimal(outPort.getIdd_Dct()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).doubleValue());//总散折/班次=平均散折
			outPort.setGrp_Dct(new BigDecimal(outPort.getGrp_Dct()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).doubleValue());//总团折/班次=平均团折
		}else{
			outPort.setAvg_Dct(0);//总折扣/班次=平均折扣
			outPort.setIdd_Dct(0);//总散折/班次=平均散折
			outPort.setGrp_Dct(0);//总团折/班次=平均团折
		}
		outPort.setMonth(month);
		return outPort;
	}
	/**
	 * select DATE_FORMAT(Lcl_Dpt_Day,'%m') month,// 日期
	 * count(id) cla_Nbr,// 班次/月
	 * (count(Flt_Nbr)/count(DISTINCT Lcl_Dpt_Day)) tme_Cla_Moh,// 日班次
	 *  avg(Tal_Nbr_Set) cla_Set,// 每班座位
		avg(Pgs_Per_Cls) cla_Per,// 每班旅客
		avg(Grp_Nbr) cla_Grp,// 每班团队
		(sum(Grp_Ine)/count(Flt_Nbr)) flt_Ser_Ine, // 团队收入/100
		(avg(Pgs_Per_Cls)/avg(Tal_Nbr_Set)) lod_For,// 客座率
		(sum(Tal_Nbr)/count(Flt_Nbr)) idd_Dct  // 每班收入/1000
//			data.setIdd_Dct(new BigDecimal(data.getIdd_Dct() / 1000).setScale(
//					0, BigDecimal.ROUND_HALF_UP).intValue());
//			data.setLod_For(new BigDecimal(data.getLod_For() * 100).setScale(0,
//					BigDecimal.ROUND_HALF_UP).intValue());
//			data.setFlt_Ser_Ine(new BigDecimal(data.getFlt_Ser_Ine()).setScale(
//					0, BigDecimal.ROUND_HALF_UP).intValue());
//			rows.add(data);
	 * 历史收益统计右边的图
	 * @param list
	 * @return
	 */
	public static EvenPort getCountData_Two(List<Z_Airdata> list) {
		EvenPort evenPort = new EvenPort();
		Set<Date> tme_Cla_Moh = new HashSet<Date>();
		for (Z_Airdata z_Airdata : list) {
			tme_Cla_Moh.add(z_Airdata.getLcl_Dpt_Day());//拿到不重复的日期
			evenPort.setCla_Set(evenPort.getCla_Set()+z_Airdata.getTal_Nbr_Set());
			evenPort.setCla_Per(evenPort.getCla_Per()+z_Airdata.getPgs_Per_Cls());
			evenPort.setCla_Grp(evenPort.getCla_Grp()+z_Airdata.getGrp_Nbr());
			evenPort.setFlt_Ser_Ine(evenPort.getFlt_Ser_Ine()+z_Airdata.getGrp_Ine().doubleValue());
			evenPort.setIdd_Dct(evenPort.getIdd_Dct()+z_Airdata.getTotalNumber());
		}
		//不用做空判断, 因为外面的方法已经做过了判断
		BigDecimal divisor = new BigDecimal(list.size());
		evenPort.setTme_Cla_Moh(new BigDecimal(list.size()).divide(divisor,2,BigDecimal.ROUND_HALF_UP).intValue());//总班次(不同的航班号)/总天数=每日班次
		evenPort.setCla_Set(new BigDecimal(evenPort.getCla_Set()).divide(divisor,2,BigDecimal.ROUND_HALF_UP).intValue());//总座位/班次=每班座位
		evenPort.setCla_Per(new BigDecimal(evenPort.getCla_Per()).divide(divisor,2,BigDecimal.ROUND_HALF_UP).intValue());//总人数/班次=每班人数
		evenPort.setCla_Grp(new BigDecimal(evenPort.getCla_Grp()).divide(divisor,2,BigDecimal.ROUND_HALF_UP).intValue());//总团队人数/班次=平均团队人数
		evenPort.setFlt_Ser_Ine(new BigDecimal(evenPort.getFlt_Ser_Ine()).divide(divisor,2,BigDecimal.ROUND_HALF_UP).intValue());//总团队收入/班次=平均团队收入
		evenPort.setLod_For(new BigDecimal(evenPort.getCla_Per()).divide(new BigDecimal(evenPort.getCla_Set()),2,BigDecimal.ROUND_HALF_UP).doubleValue()*100);//平均人数/平均座位=客座率
		evenPort.setIdd_Dct(new BigDecimal(evenPort.getIdd_Dct()).divide(divisor,0,BigDecimal.ROUND_HALF_UP).intValue()/1000);//总收入/班次=每班收入
		Calendar c = Calendar.getInstance();
		c.setTime(list.get(0).getLcl_Dpt_Day());
		int actualMaximum = c.getActualMaximum(Calendar.DATE);
		evenPort.setTme_Nbr(actualMaximum);
		evenPort.setCla_Nbr(list.size());
		evenPort.setMonth(list.get(0).getMonth());
		return evenPort;
	}
}
