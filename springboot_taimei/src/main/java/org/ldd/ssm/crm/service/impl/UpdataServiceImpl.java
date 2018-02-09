package org.ldd.ssm.crm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.UpdataMapper;
import org.ldd.ssm.crm.query.StartDateAndEndDate;
import org.ldd.ssm.crm.service.IUpdataService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.utils.ValidationDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 导入外部接口的方法
 * 
 */
@Service
public class UpdataServiceImpl implements IUpdataService {
	@Autowired
	private UpdataMapper mapper;

	/**
	 * 对数据进行纠正, 并且存入数据库 第二次修改数据修正方法, 1,进行数据的先后修正顺, 先进行
	 * @throws Exception 
	 */
	public List<Z_Airdata> saveFileData(List<Z_Airdata> data,String dta_Sce) throws Exception {
		List<Z_Airdata> airdatas = new ArrayList<Z_Airdata>();
		int i = 1;
		List<Fare> fares = mapper.getAll();
		try {
			if (data != null && data.size() != 0) {
				for (Z_Airdata z_Airdata : data) {
					// 每班座位
					int tal_Nbr_Set = z_Airdata.getTal_Nbr_Set();// 每班座位
					int pgs_Per_Cls = z_Airdata.getPgs_Per_Cls();// 每班旅客
					Fare getfare = new Fare();
					// 取到异常数据的航程
					String air = z_Airdata.getDpt_AirPt_Cd()
							+ z_Airdata.getArrv_Airpt_Cd();
					// 根据航程拿到YB运价
					
					for (Fare fare2 : fares) {
						if(fare2.getVoyageCode().equals(air)){
							getfare = fare2;
						}
					}
					double yBFare = getfare.getyBFare();
					z_Airdata.setyBFare(getfare.getyBFare());
					z_Airdata.setSailingDistance(getfare.getSailingDistance());
					// 修正每班座位
					if (tal_Nbr_Set <= 0 || tal_Nbr_Set < pgs_Per_Cls) {
						z_Airdata.setTal_Nbr_Set(ValidationDataUtils.getPassengersPerClass(z_Airdata, data));
					}
					/**-------------------------------------验证收入合法性---------------------------------------------------------------------------------------*/
					
					z_Airdata.setTotalNumber(ValidationDataUtils.getTotalNumber(z_Airdata,getfare));
					
					/**-------------------------------------计算平均折扣----------------------------------------------------------------------*/
					z_Airdata.setAvg_Dct(ValidationDataUtils.avg_Dct(z_Airdata.getTotalNumber(),z_Airdata.getPgs_Per_Cls(),yBFare));
					/**-------------------------------------验证团队折扣合法性---------------------------------------------------------------------------------------*/
					
					//如果人数大于0才验证, 否则取原始数据表中的折扣
					if( z_Airdata.getGrp_Nbr()>0){
						z_Airdata.setGrp_Dct(ValidationDataUtils.paseGrp_Dct(z_Airdata,data));
					}
					//4.散客折扣
					z_Airdata.setIdd_Dct(ValidationDataUtils.getiddDct(z_Airdata, yBFare));
					//5,添加团队收入
					z_Airdata.setGrp_Ine(ValidationDataUtils.getGroupDct(z_Airdata.getEch_Cls_Grp(),z_Airdata.getGrp_Dct(),100,yBFare));
					//6,添加座公里收入如果 座公里收入>= 5 || 0<=座公里收入，要求座收不在（0,2）间时做修正
					z_Airdata.setSet_Ktr_Ine(ValidationDataUtils.getSet_Ktr_Ine(z_Airdata,getfare));
					//如果团队收入大于每班收入,则从新计算每班收入
					if(z_Airdata.getGrp_Ine().intValue()>z_Airdata.getTotalNumber()){
						int intValue =ValidationDataUtils.getEch_Cls_Ime(z_Airdata, yBFare);
						z_Airdata.setTotalNumber(intValue);
					}
					//经停航线各段分摊客座率=该段销售人数/该段分摊座位数。
					BigDecimal bigDecimal = new BigDecimal(z_Airdata.getPgs_Per_Cls());
					BigDecimal divide = bigDecimal.divide(new BigDecimal(z_Airdata.getTal_Nbr_Set()>0?z_Airdata.getTal_Nbr_Set():1),2,BigDecimal.ROUND_HALF_UP);
					BigDecimal multiply = divide.multiply(new BigDecimal(100));
					z_Airdata.setEgs_Lod_Fts(multiply);
					z_Airdata.setDta_Sce(dta_Sce);
					airdatas.add(z_Airdata);
					i++;
				}
			}
			//将集合中的数据存入数据库
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("导入不成功,请联系管理员"+i);
		}
		return airdatas;
	}
	/**
	 * 根据传入员工找到公司
	 */
	public String getCompany(String user) {
		return mapper.getCompany(user);
	}
	/**
	 * 
	 */
	public List<String> getUpFile_company_list(String companyId) {
		if(companyId==null){
			return mapper.getUpFile_company_list();
		}
		return mapper.getUpFile_company(Long.valueOf(companyId));
	}
	public Set<String> checkItia(List<List<String[]>> saveExecl) {
		Set<String> set = new HashSet<String>();
		List<Fare> getfare = mapper.getAll();
		Set<String> set2 = new HashSet<String>();
		for (List<String[]> list : saveExecl) {
			for (String[] strings : list) {
				String line = strings[1].substring(0, 3)+strings[1].substring(3, 6);
				set2.add(line);
				for (Fare fa : getfare) {
					if(fa.getVoyageCode().equals(line)){
						set.add(line);
						set2.remove(line);
					}
				}
			}
		}
		UserContext.setNullItia(set2);
		return set2;
	}
	public void externalYBsave(Fare strJson) {
		mapper.externalYBsave(strJson);
	}
	/**
	 * Mybatis批保存方法
	 *返回数据库中存在的数据的数据
	 */
	public List<Z_Airdata> savaz_AirDate(List<Z_Airdata> saveFileData) {
		List<Z_Airdata> new_list = new ArrayList<Z_Airdata>();
		new_list.addAll(saveFileData);
		//1.保存进,不同数据源, 可以重复数据的表中
		saveData_Source(new_list);
		//2.保存进,数据唯一性表中
		List<Z_Airdata> saveData_One = saveData_One(saveFileData);
		return saveData_One;
		
	}
	/**
	 * 返回数据库中重复的数据
	 * @param saveFileData
	 * @return
	 */
	private List<Z_Airdata> saveData_One(List<Z_Airdata> saveFileData) {
		List<Z_Airdata> airdatas = new ArrayList<Z_Airdata>();
		//按照实际段查询出数据  , 然后遍历筛选出数据库存在的数据  , 然后进行批存储,写入数据库
		
		StartDateAndEndDate andEndDate = getStartDateAndEndDate(saveFileData);
		List<Z_Airdata> lists = mapper.getStratDateAndEndDate(andEndDate);
		for (int i = 0; i < saveFileData.size(); i++) {
			Z_Airdata z_Airdata = saveFileData.get(i);
			for (int j = 0; j < lists.size(); j++) {
				Z_Airdata z_Airdata2 = lists.get(j);
				//如果始发地到达地日期航班号一样, 则判断为同一个航班
				if(z_Airdata.getLcl_Dpt_Day().equals(z_Airdata2.getLcl_Dpt_Day())
						&&z_Airdata.getDpt_AirPt_Cd().equals(z_Airdata2.getDpt_AirPt_Cd())
						&&z_Airdata.getArrv_Airpt_Cd().equals(z_Airdata2.getArrv_Airpt_Cd())
						&&z_Airdata.getFlt_Nbr().equals(z_Airdata2.getFlt_Nbr())
						){
					airdatas.add(z_Airdata);
					saveFileData.remove(i);
					i--;
				}
			}
		}
		//对数据进行座位布局修正
		List<Z_Airdata> airdatas2 = getEqualsFlt_Nbr(saveFileData,lists);
		
		mapper.saveList(airdatas2);
		return airdatas;
		
	}
	/**
	 * 获得与上传数据同一个航班, 并且同一个航线的数据
	 * @param saveFileData
	 * @param lists
	 * @return
	 */
	private List<Z_Airdata> getEqualsFlt_Nbr(List<Z_Airdata> saveFileData,List<Z_Airdata> lists) {
		List<Z_Airdata> airdatas = new ArrayList<Z_Airdata>();
		for (Z_Airdata z_Airdata : saveFileData) {
			List<Z_Airdata> new_Airdatas = new ArrayList<Z_Airdata>();
			for (Z_Airdata airdata : lists) {
				if(z_Airdata.getFlt_Nbr().equals(airdata.getFlt_Nbr())
					&&z_Airdata.getFlt_Rte_Cd().equals(airdata.getFlt_Rte_Cd())
					&&z_Airdata.getLcl_Dpt_Day().equals(airdata.getLcl_Dpt_Day())
					&&z_Airdata.getFlt_Rte_Cd().length()==9){
					new_Airdatas.add(airdata);
				}
			}
			
			if(new_Airdatas.size()==3){
				z_Airdata.setCount_Set(getCount_Set(new_Airdatas));//计算布局座位
			}
			//不过是否修复布局座位成功, 都将需要上传的数据返回
			airdatas.add(z_Airdata);
		}
		return airdatas;
	}
	/**
	 * 逆推三个航段的分摊座位数, 求出布局座位数
	 * @param new_Airdatas
	 * @return
	 * 因为数据库中, 同一天 同一个航班号 , 同一个始发到达是不存在两条相同的数据, 所以当数据达到了3条, 则表示经停航线被补齐
	 */
	private int getCount_Set(List<Z_Airdata> new_Airdatas) {
		//判断是否存在布局座位, 如果存在则直接返回
		for (Z_Airdata z_Airdata : new_Airdatas) {
			if(z_Airdata.getCount_Set()!=0){
				return z_Airdata.getCount_Set();
			}
		}
		int c_airline = 0;
		int d_airline = 0;
		for (Z_Airdata z_Airdata : new_Airdatas) {
			String dpt = z_Airdata.getFlt_Rte_Cd().substring(0, 3);//航线
			String AirPt = z_Airdata.getFlt_Rte_Cd().substring(3, 6);//航线
			String arrv = z_Airdata.getFlt_Rte_Cd().substring(6, 9);//航线
			String dpt_AirPt_Cd = z_Airdata.getDpt_AirPt_Cd();//到达地
			String arrv_Airpt_Cd = z_Airdata.getArrv_Airpt_Cd();
			if(dpt_AirPt_Cd.equals(dpt)&&arrv_Airpt_Cd.equals(arrv)){
				c_airline = z_Airdata.getTal_Nbr_Set();
			}else if(dpt_AirPt_Cd.equals(dpt)&&arrv_Airpt_Cd.equals(AirPt)){
				d_airline = z_Airdata.getTal_Nbr_Set();
			}
		}
		return c_airline + d_airline;
	}
	/**
	 * 保存一份数据进原始数据中
	 * @param saveFileData
	 */
	private void saveData_Source(List<Z_Airdata> saveFileData) {
		StartDateAndEndDate andEndDate = getStartDateAndEndDate(saveFileData);
		
		List<Z_Airdata> lists = mapper.getData_Source(andEndDate);
		for (int i = 0; i < saveFileData.size(); i++) {
			Z_Airdata z_Airdata = saveFileData.get(i);
			for (int j = 0; j < lists.size(); j++) {
				Z_Airdata z_Airdata2 = lists.get(j);
				//如果始发地到达地日期航班号一样, 则判断为同一个航班
				if(z_Airdata.getLcl_Dpt_Day().equals(z_Airdata2.getLcl_Dpt_Day())
						&&z_Airdata.getDpt_AirPt_Cd().equals(z_Airdata2.getDpt_AirPt_Cd())
						&&z_Airdata.getArrv_Airpt_Cd().equals(z_Airdata2.getArrv_Airpt_Cd())
						&&z_Airdata.getFlt_Nbr().equals(z_Airdata2.getFlt_Nbr())
						&&z_Airdata.getDta_Sce().equals(z_Airdata2.getDta_Sce())
						){
					saveFileData.remove(i);
					i--;
				}
			}
		}
		if(saveFileData.size()>0){
			mapper.saveData_Source(saveFileData);
		}
	}
	private StartDateAndEndDate getStartDateAndEndDate(List<Z_Airdata> saveFileData) {
		Date lcl_Dpt_Day = saveFileData.get(0).getLcl_Dpt_Day();
		Date lcl_Dpt_Day2 = saveFileData.get(saveFileData.size()-1).getLcl_Dpt_Day();
		String dta_Sce = saveFileData.get(0).getDta_Sce();
		StartDateAndEndDate startDateAndEndDate = new StartDateAndEndDate(lcl_Dpt_Day, lcl_Dpt_Day2,dta_Sce);
		Set<String> a_set = new HashSet<String>();
		Set<String> b_set = new HashSet<String>();
		for (Z_Airdata z_Airdata : saveFileData) {
			a_set.add(z_Airdata.getDpt_AirPt_Cd());
			b_set.add(z_Airdata.getArrv_Airpt_Cd());
		}
		if(a_set.size()==1){
			startDateAndEndDate.setDpt_AirPt_Cd(a_set.iterator().next());
		}else if(b_set.size()==1){
			startDateAndEndDate.setArrv_Airpt_Cd(b_set.iterator().next());
		}
		
		return startDateAndEndDate;
	}
}