/**   
 * @Title: AbnormalDataUtils.java 
 * @Package org.ldd.ssm.crm.utils 
 * @Description: TODO
 * @author taimei-gds   
 * @date 2016-8-11 下午2:07:05 
 * @version V1.0   
 */ 
package org.ldd.ssm.crm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.Z_Airdata;

/**数据异常操作工具类
 * @Title:AbnormalDataUtils 
 * @Description:
 * @author taimei-gds 
 * @date 2016-8-11 下午2:07:05
 */
public class AbnormalDataUtils {
	/**
	 * 根据传入的列表得到每个个航班都完整的数据和不完整航班的个数
	 * @Title: wipOffAbnomalData 
	 * @Description:  
	 * @param @param ObjectList
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public static Map<String,Object> wipOffAbnomalData(List<Z_Airdata> ObjectList ){
		List<Z_Airdata> z_AirdataNewList = new ArrayList<Z_Airdata>();
		Map<String,Object> listMap = new HashMap<String, Object>();
		List<String> dateList = new ArrayList<String>();
		List<String> fltNumList = new ArrayList<String>();
		for (Z_Airdata z_Airdata : ObjectList) {
			String date = z_Airdata.getLcl_Dpt_Day_Str();
			String fltNum = z_Airdata.getFlt_Nbr();
			if(dateList.indexOf(date)>0){
				dateList.add(date);
			}
			if(fltNumList.indexOf(fltNum)>0){
				fltNumList.add(fltNum);
			}
		}
		int count = 0;
		for (String date : dateList) {
			for (String fltNum : fltNumList) {
				List<Z_Airdata> tempZAirdata = new ArrayList<Z_Airdata>();
				for (Z_Airdata z_Airdata : ObjectList) {
					String date2 = z_Airdata.getLcl_Dpt_Day_Str();
					String fltNum2 = z_Airdata.getFlt_Nbr();
					if(date2.equals(date)&&fltNum2.equals(fltNum)){
						tempZAirdata.add(z_Airdata);
					}
				}
				if(tempZAirdata.size()<3){
					//表示少了数据
					count++;
				}else{
					for (Z_Airdata z_Airdata : tempZAirdata) {
						z_AirdataNewList.add(z_Airdata);
					}
				}
			}
		}
		listMap.put("list", z_AirdataNewList);
		listMap.put("count", count);
		return listMap;
	}
}
