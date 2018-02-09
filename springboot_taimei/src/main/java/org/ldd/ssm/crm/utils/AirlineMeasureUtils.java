package org.ldd.ssm.crm.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ldd.ssm.crm.query.ThroughputOrGdp;

public class AirlineMeasureUtils {
	/**
	 * 返回近四年增长率及数据
	 * @param list
	 * @return
	 */
	public List<ThroughputOrGdp> lastFourYears(List<ThroughputOrGdp> list){
		//获取当前年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		int year = Integer.parseInt(sdf.format(date));
		int tempyear = 100;
		if(list==null||list.size()<1){
			list=new LinkedList<ThroughputOrGdp>();
			year = Integer.parseInt(sdf.format(date)) -1;
		}
		for (ThroughputOrGdp throughputOrGdp : list) {
			if(tempyear<throughputOrGdp.getYear()){
				tempyear = throughputOrGdp.getYear();
			}
		}
		if(list!=null&&list.size()>0){
			if(tempyear<year){
				year = tempyear;
			}
			year = year + 1;
		}
		//添加需要预计的年份 本年
		ThroughputOrGdp e=new ThroughputOrGdp();
		e.setYear(year);
		e.setData("-");
		e.setGrowthRate("-");
		list.add(e);
		//计算增长率
		for(int i=0;i<list.size();i++){
			if(list.get(i).getYear()==year-4+i){
				//判断前一年有没有数据 若有则计算增长率
				if(list.get(i-1).getData().isEmpty()){
					list.get(i-1).setData("-");
				}
				if(list.get(i).getData().isEmpty()){
					list.get(i).setData("-");
				}
				if(i>0&&list.get(i-1).getData()!="-"&&list.get(i).getData()!="-"){
					float growth=(Float.parseFloat(list.get(i).getData())-Float.parseFloat(list.get(i-1).getData()))*100/Float.parseFloat(list.get(i-1).getData());
					growth=((float)(Math.round(growth*100))/100);
					list.get(i).setGrowthRate(growth+"%");
				}else{
					list.get(i).setGrowthRate("-");
				}
			}else{
				ThroughputOrGdp temp=new ThroughputOrGdp();
				temp.setYear(year-4+i);
				temp.setData("-");
				temp.setGrowthRate("-");
				list.add(i, temp);
			}
		}
		//计算预计该年份的数据
		int size=list.size()-1;
		if(list.get(size-1).getData()!="-"&&list.get(size-2).getData()!="-"){
			float data=Float.parseFloat(list.get(size-1).getData())*Float.parseFloat(list.get(size-1).getData())/Float.parseFloat(list.get(size-2).getData());
			data= ((float)(Math.round(data*100))/100);
			BigDecimal d1 = new BigDecimal(Float.toString(data));
			list.get(size).setData(d1.toString()+"");
			float growth=(Float.parseFloat(list.get(size).getData())-Float.parseFloat(list.get(size-1).getData()))*100/Float.parseFloat(list.get(size-1).getData());
			growth=((float)(Math.round(growth*100))/100);
			list.get(size).setGrowthRate(growth+"%");
		}
		//移除13年的（只需要展示近4年的 之前13年的数据是为了计算14年的增长率）
		list.remove(0);
		return list;
	}
	
	/**
	 * 传入航线计算出所有航段 三字码/中文
	 * @param route
	 * @return
	 */
	public List<String> splitRouteCh(List<String> route){
		List<String> list=new ArrayList<String>();
		List<String> listResult=new ArrayList<String>();
		for(String first:list){
			for(String last:list){
				if(first!=last){
					String reslut=first+"-"+last;
					listResult.add(reslut);
				}
			}
		}
		return listResult;
	}
	
	public List<Map<String,String>> splitRouteMap(List<String> route){
		List<String> list=new ArrayList<String>();
		List<Map<String,String>> listResult=new ArrayList<Map<String,String>>();
		for(String first:list){
			for(String last:list){
				if(first!=last){
					Map<String,String> map=new HashMap<>();
					map.put("dept", first);
					map.put("arri", last);
					listResult.add(map);
				}
			}
		}
		return listResult;
	}
	
	/**传入两个数组取交集**/
	public  String[] intersect(String[] arr1, String[] arr2)  
    {  
        Map<String, Boolean> map = new HashMap<String, Boolean>();  
        List<String> list = new ArrayList<String>();  
        for (String str : arr1)  
        {  
            if (!map.containsKey(str)&&!str.equals("暂无数据"))  
            {  
                map.put(str, Boolean.FALSE);  
            }  
        }  
        for (String str : arr2)  
        {  
            if (map.containsKey(str))  
            {  
                map.put(str, Boolean.TRUE);  
            }  
        }  
    
        for (Iterator<Entry<String, Boolean>> it = map.entrySet().iterator();it.hasNext();)  
        {  
            Entry<String,Boolean> e = (Entry<String,Boolean>)it.next();  
            if (e.getValue().equals(Boolean.TRUE))  
            {  
                list.add(e.getKey());  
            }  
        }  
        return list.toArray(new String[] {});  
    }  
	
	/**
	 * 传入一个集合数组 取交集
	 */
	public List<String> intersectList(List<String> datas){
		List<String[]> dataArrays=new ArrayList<String[]>();
		if(datas.isEmpty()){
			return null;
		}
		for(String s:datas){
			 String[] strArray = null; 
			 strArray = s.split(",");  
			 dataArrays.add(strArray);
			 
		}
		String[] aa=null;
		if(dataArrays.size()>=2){
			for(int i=0;i<dataArrays.size()-1;i++){
				aa= intersect(dataArrays.get(i),dataArrays.get(i+1));
				dataArrays.set(i+1, aa);
			}
		}else{
			System.out.println("1条");
			aa=dataArrays.get(0);
		}
		return Arrays.asList(aa);
	}
	
}
