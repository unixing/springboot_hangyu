package org.ldd.ssm.crm.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.ldd.ssm.crm.domain.AddFltNbr;
import org.ldd.ssm.crm.query.ETermQuery;

public class EtermUtils {
	/**
	 * 将前台传回的
	 * 航班号
	 * 航线
	 * 给组装成对应的航段 
	 * @param query
	 * @return
	 */
	public static List<AddFltNbr> getAddFltNbrs(ETermQuery query) {
		List<AddFltNbr> addFltNbrs = new ArrayList<AddFltNbr>();
		if("1".equals(query.getChe())){
			//第一组航班号航线
			if(!TextUtil.isEmpty(query.getFlt_rte_cd1())&&!TextUtil.isEmpty(query.getFlb_nbr1())){
				AddFltNbr addFltNbr = new AddFltNbr();
				addFltNbr.setFlt_Nbr(query.getFlb_nbr1());
				addFltNbr.setAir_line(query.getFlt_rte_cd1());
				if(query.getFlt_rte_cd1().length()==9){
					addFltNbr.setFlt_one(query.getFlt_rte_cd1().substring(0, 6));
					addFltNbr.setFlt_two(query.getFlt_rte_cd1().substring(0, 3)+query.getFlt_rte_cd1().substring(6, 9));
					addFltNbr.setFlt_three(query.getFlt_rte_cd1().substring(3, 6)+query.getFlt_rte_cd1().substring(6, 9));
				}else if(query.getFlt_rte_cd1().length()==6){
					addFltNbr.setFlt_one(query.getFlt_rte_cd1().substring(0, 6));
				}
				addFltNbrs.add(addFltNbr);
			}
			//第二组航班号航线
			if(!TextUtil.isEmpty(query.getFlt_rte_cd2())&&!TextUtil.isEmpty(query.getFlb_nbr2())){
				AddFltNbr addFltNbr2 = new AddFltNbr();
				addFltNbr2.setFlt_Nbr(query.getFlb_nbr2());
				addFltNbr2.setAir_line(query.getFlt_rte_cd2());
				if(query.getFlt_rte_cd2().length()==9){
					addFltNbr2.setFlt_one(query.getFlt_rte_cd2().substring(0, 6));
					addFltNbr2.setFlt_two(query.getFlt_rte_cd2().substring(0, 3)+query.getFlt_rte_cd2().substring(6, 9));
					addFltNbr2.setFlt_three(query.getFlt_rte_cd2().substring(3, 6)+query.getFlt_rte_cd2().substring(6, 9));
				}else if(query.getFlt_rte_cd2().length()==6){
					addFltNbr2.setFlt_one(query.getFlt_rte_cd2().substring(0, 6));
				}
				addFltNbrs.add(addFltNbr2);
			}
			//第三组航班号航线
			if(!TextUtil.isEmpty(query.getFlt_rte_cd3())&&!TextUtil.isEmpty(query.getFlb_nbr3())){
				AddFltNbr addFltNbr3 = new AddFltNbr();
				addFltNbr3.setFlt_Nbr(query.getFlb_nbr3());
				addFltNbr3.setAir_line(query.getFlt_rte_cd3());
				if(query.getFlt_rte_cd3().length()==9){
					addFltNbr3.setFlt_one(query.getFlt_rte_cd3().substring(0, 6));
					addFltNbr3.setFlt_two(query.getFlt_rte_cd3().substring(0, 3)+query.getFlt_rte_cd3().substring(6, 9));
					addFltNbr3.setFlt_three(query.getFlt_rte_cd3().substring(3, 6)+query.getFlt_rte_cd3().substring(6, 9));
				}else if(query.getFlt_rte_cd3().length()==6){
					addFltNbr3.setFlt_one(query.getFlt_rte_cd3().substring(0, 6));
				}
				addFltNbrs.add(addFltNbr3);
			}
			//第四组航班号航线
			if(!TextUtil.isEmpty(query.getFlt_rte_cd4())&&!TextUtil.isEmpty(query.getFlb_nbr4())){
				AddFltNbr addFltNbr4 = new AddFltNbr();
				addFltNbr4.setFlt_Nbr(query.getFlb_nbr4());
				addFltNbr4.setAir_line(query.getFlt_rte_cd4());
				if(query.getFlt_rte_cd4().length()==9){
					addFltNbr4.setFlt_one(query.getFlt_rte_cd4().substring(0, 6));
					addFltNbr4.setFlt_two(query.getFlt_rte_cd4().substring(0, 3)+query.getFlt_rte_cd4().substring(6, 9));
					addFltNbr4.setFlt_three(query.getFlt_rte_cd4().substring(3, 6)+query.getFlt_rte_cd4().substring(6, 9));
				}else if(query.getFlt_rte_cd4().length()==6){
					addFltNbr4.setFlt_one(query.getFlt_rte_cd4().substring(0, 6));
				}
				addFltNbrs.add(addFltNbr4);
			}
			//第五组航班号航线
			if(!TextUtil.isEmpty(query.getFlt_rte_cd5())&&!TextUtil.isEmpty(query.getFlb_nbr5())){
				AddFltNbr addFltNbr5 = new AddFltNbr();
				addFltNbr5.setFlt_Nbr(query.getFlb_nbr5());
				addFltNbr5.setAir_line(query.getFlt_rte_cd5());
				if(query.getFlt_rte_cd5().length()==9){
					addFltNbr5.setFlt_one(query.getFlt_rte_cd5().substring(0, 6));
					addFltNbr5.setFlt_two(query.getFlt_rte_cd5().substring(0, 3)+query.getFlt_rte_cd5().substring(6, 9));
					addFltNbr5.setFlt_three(query.getFlt_rte_cd5().substring(3, 6)+query.getFlt_rte_cd5().substring(6, 9));
				}else if(query.getFlt_rte_cd3().length()==6){
					addFltNbr5.setFlt_one(query.getFlt_rte_cd5().substring(0, 6));
				}
				addFltNbrs.add(addFltNbr5);
			}
			//第六组航班号航线
			if(!TextUtil.isEmpty(query.getFlt_rte_cd6())&&!TextUtil.isEmpty(query.getFlb_nbr6())){
				AddFltNbr addFltNbr6 = new AddFltNbr();
				addFltNbr6.setFlt_Nbr(query.getFlb_nbr6());
				addFltNbr6.setAir_line(query.getFlt_rte_cd6());
				if(query.getFlt_rte_cd6().length()==9){
					addFltNbr6.setFlt_one(query.getFlt_rte_cd6().substring(0, 6));
					addFltNbr6.setFlt_two(query.getFlt_rte_cd6().substring(0, 3)+query.getFlt_rte_cd6().substring(6, 9));
					addFltNbr6.setFlt_three(query.getFlt_rte_cd6().substring(3, 6)+query.getFlt_rte_cd6().substring(6, 9));
				}else if(query.getFlt_rte_cd6().length()==6){
					addFltNbr6.setFlt_one(query.getFlt_rte_cd6().substring(0, 6));
				}
				addFltNbrs.add(addFltNbr6);
			}
		}
		return addFltNbrs;
	}

	public static String getNextDay(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, i);
		date = calendar.getTime();
		String[] split = date.toString().split(" +");
		return split[2] + split[1]+split[split.length-1].substring(2, 4);
	}
	/**
	 * 加密
	 */
	public static String eccode(String pwd){
		UUID randomUUID = UUID.randomUUID();
		char[] charArray = pwd.toCharArray();
		String psd = "";
		
		for (int i = 0; i < charArray.length; i++) {
			int nextInt = new Random().nextInt(32)+1;
			String substring = randomUUID.toString().charAt(nextInt)+"";
			psd += charArray[i]+substring;
		}
		return psd;
	}
	/**
	 * 解密
	 */
	public static String decode(String pwd){
		char[] charArray = pwd.toCharArray();
		String psd = "";
		for (int i = 0; i < charArray.length; i++) {
			if(i%2==0){
				psd += pwd.charAt(i)+"";
			}
		}
		return psd;
	}
	
}
