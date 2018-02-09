package org.ldd.ssm.crm.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AirLineForecast;
import org.ldd.ssm.crm.domain.AirLineForecastDetail;
import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.query.AirLineForecastDetailObject;
import org.ldd.ssm.crm.query.AirLineForecastObject;
import org.ldd.ssm.crm.query.AirLineForecastQuery;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.service.AirLineForecastService;
import org.ldd.ssm.crm.web.interceptor.ClassNote;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 航线收益预估控制类
 * @Title:AirLineForecastAction 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-25 上午10:36:37
 */
@Controller
public class AirLineForecastAction {
	@Autowired
	private AirLineForecastService airLineForecastService;
	//springMVC请求的映射地址
	@MyMethodNote(comment="航线收益预测:2")
	@RequestMapping("/airlineForecast")
	public String getOutPort() {
			return "charts/airLineForecast/airlineForecast";
	}
	
	/**
	 * 航线预估页面加载
	 * @Title: forecastData 
	 * @Description:  
	 * @param @param dowData
	 * @param @return    
	 * @return Object     
	 * @throws
	 */
	@MyMethodNote(comment="收益数据:2")
//	@MethodNote(comment="收益数据:74")
	@RequestMapping("/forecastData")
	@ResponseBody
	public Object forecastData(DOWQuery dowData){
		AirLineForecastQuery airLineForecastQuery = new AirLineForecastQuery();
		airLineForecastQuery.setStartDate(dowData.getStartDate());
		airLineForecastQuery.setEndDate(dowData.getEndDate());
		airLineForecastQuery.setDpt_AirPt_Cd(dowData.getDpt_AirPt_Cd());
		airLineForecastQuery.setArrv_Airpt_Cd(dowData.getArrv_Airpt_Cd());
		if(dowData.getPas_stp()==null){
			airLineForecastQuery.setPas_stp(dowData.getPas_stp()==null?"":dowData.getPas_stp());
		}else{
			airLineForecastQuery.setPas_stp(dowData.getPas_stp());
		}
		airLineForecastQuery.setDta_Sce(dowData.getDta_Sce());
		return getforcastData(airLineForecastQuery);
	}
	
	/**
	 * 更新或者保存预估信息
	 * @Title: saveOrUpateforecast 
	 * @Description:  
	 * @param @param airLineForecastObject
	 * @param @return    
	 * @return Object     
	 * @throws
	 */
	@MyMethodNote(comment="维护收益数据:3")
//	@MethodNote(comment="维护收益数据:74")
	@RequestMapping("/saveOrUpateforecast")
	@ResponseBody
	public Object saveOrUpateforecast(@RequestBody AirLineForecastObject airLineForecastObject){
		AirLineForecast airLineForecast = airLineForecastObject.getAirLineForecast();
		List<AirLineForecastDetailObject> airLineForecastDetailObjectList = airLineForecastObject.getAirLineForecastDetailObjectList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		AirLineForecastQuery airLineForecastQuery = new AirLineForecastQuery();
		airLineForecastQuery.setStartDate(sdf.format(airLineForecast.getStartDate()));
		airLineForecastQuery.setEndDate(sdf.format(airLineForecast.getEndDate()));
		airLineForecastQuery.setDpt_AirPt_Cd(airLineForecast.getDpt_AirPt_Cd());
		airLineForecastQuery.setArrv_Airpt_Cd(airLineForecast.getArrv_Airpt_Cd());
		airLineForecastQuery.setPas_stp(airLineForecast.getPas_stp());
		airLineForecastQuery.setDta_Sce(airLineForecast.getDta_Sce());
		if(airLineForecast.getId()==null||"".equals(airLineForecast.getId())){
			//新增航线基本信息和详细信息
			airLineForecast.setDta_Dte(new Date());
			airLineForecastService.saveAirLineForecast(airLineForecast);
			AirLineForecast airLineForecastNew = airLineForecastService.getAirLineForecast(airLineForecastQuery);
			for (AirLineForecastDetailObject airLineForecastDetailObject : airLineForecastDetailObjectList) {
				AirLineForecastDetail airLineForecastDetail = new AirLineForecastDetail();
				airLineForecastDetail.setAirLineForecastId(airLineForecastNew.getId());
				airLineForecastDetail.setHangduan(airLineForecastDetailObject.getHangduan());
				airLineForecastDetail.setHangju(Float.parseFloat(airLineForecastDetailObject.getHangju()));
				airLineForecastDetail.setY_price(Float.parseFloat(airLineForecastDetailObject.getYc_price()));
				airLineForecastDetail.setQie_team_price(Float.parseFloat(airLineForecastDetailObject.getQwtdjg_price()));
				airLineForecastDetail.setF_flag(Float.parseFloat(airLineForecastDetailObject.getF_flag()));
				airLineForecastDetail.setY_flag(Float.parseFloat(airLineForecastDetailObject.getY_flag()));
				airLineForecastDetail.setB_flag(Float.parseFloat(airLineForecastDetailObject.getB_flag()));
				airLineForecastDetail.setH_flag(Float.parseFloat(airLineForecastDetailObject.getH_flag()));
				airLineForecastDetail.setK_flag(Float.parseFloat(airLineForecastDetailObject.getK_flag()));
				airLineForecastDetail.setL_flag(Float.parseFloat(airLineForecastDetailObject.getL_flag()));
				airLineForecastDetail.setM_flag(Float.parseFloat(airLineForecastDetailObject.getM_flag()));
				airLineForecastDetail.setQ_flag(Float.parseFloat(airLineForecastDetailObject.getQ_flag()));
				airLineForecastDetail.setX_flag(Float.parseFloat(airLineForecastDetailObject.getX_flag()));
				airLineForecastDetail.setU_flag(Float.parseFloat(airLineForecastDetailObject.getU_flag()));
				airLineForecastDetail.setE_flag(Float.parseFloat(airLineForecastDetailObject.getE_flag()));
				airLineForecastDetail.setZ_flag(Float.parseFloat(airLineForecastDetailObject.getZ_flag()));
				airLineForecastDetail.setT_flag(Float.parseFloat(airLineForecastDetailObject.getT_flag()));
				airLineForecastDetail.setV_flag(Float.parseFloat(airLineForecastDetailObject.getV_flag()));
				airLineForecastDetail.setG_flag(Float.parseFloat(airLineForecastDetailObject.getG_flag()));
				airLineForecastDetail.setS_flag(Float.parseFloat(airLineForecastDetailObject.getS_flag()));
				airLineForecastDetail.setO_flag(Float.parseFloat(airLineForecastDetailObject.getO_flag()));
				airLineForecastDetail.setQp_flag(Float.parseFloat(airLineForecastDetailObject.getQp_flag()));
				airLineForecastDetail.setQt_flag(Float.parseFloat(airLineForecastDetailObject.getQt_flag()));
				airLineForecastDetail.setDta_Sce(airLineForecast.getDta_Sce());
				airLineForecastDetail.setDta_Dte(new Date());
				airLineForecastService.saveAirLineForecastDetail(airLineForecastDetail);
			}
		}else{
			//更新航线信息
			airLineForecastService.updateAirLineForecast(airLineForecast);
			for (AirLineForecastDetailObject airLineForecastDetailObject : airLineForecastDetailObjectList) {
				if(airLineForecastDetailObject.getId()!=null&&!"".equals(airLineForecastDetailObject.getId())){
					AirLineForecastDetail airLineForecastDetail = new AirLineForecastDetail();
					airLineForecastDetail.setId(airLineForecastDetailObject.getId());
					airLineForecastDetail.setHangju(Float.parseFloat(airLineForecastDetailObject.getHangju()));
					airLineForecastDetail.setY_price(Float.parseFloat(airLineForecastDetailObject.getYc_price()));
					airLineForecastDetail.setQie_team_price(Float.parseFloat(airLineForecastDetailObject.getQwtdjg_price()));
					airLineForecastDetail.setF_flag(Float.parseFloat(airLineForecastDetailObject.getF_flag()));
					airLineForecastDetail.setY_flag(Float.parseFloat(airLineForecastDetailObject.getY_flag()));
					airLineForecastDetail.setB_flag(Float.parseFloat(airLineForecastDetailObject.getB_flag()));
					airLineForecastDetail.setH_flag(Float.parseFloat(airLineForecastDetailObject.getH_flag()));
					airLineForecastDetail.setK_flag(Float.parseFloat(airLineForecastDetailObject.getK_flag()));
					airLineForecastDetail.setL_flag(Float.parseFloat(airLineForecastDetailObject.getL_flag()));
					airLineForecastDetail.setM_flag(Float.parseFloat(airLineForecastDetailObject.getM_flag()));
					airLineForecastDetail.setQ_flag(Float.parseFloat(airLineForecastDetailObject.getQ_flag()));
					airLineForecastDetail.setX_flag(Float.parseFloat(airLineForecastDetailObject.getX_flag()));
					airLineForecastDetail.setU_flag(Float.parseFloat(airLineForecastDetailObject.getU_flag()));
					airLineForecastDetail.setE_flag(Float.parseFloat(airLineForecastDetailObject.getE_flag()));
					airLineForecastDetail.setZ_flag(Float.parseFloat(airLineForecastDetailObject.getZ_flag()));
					airLineForecastDetail.setT_flag(Float.parseFloat(airLineForecastDetailObject.getT_flag()));
					airLineForecastDetail.setV_flag(Float.parseFloat(airLineForecastDetailObject.getV_flag()));
					airLineForecastDetail.setG_flag(Float.parseFloat(airLineForecastDetailObject.getG_flag()));
					airLineForecastDetail.setS_flag(Float.parseFloat(airLineForecastDetailObject.getS_flag()));
					airLineForecastDetail.setO_flag(Float.parseFloat(airLineForecastDetailObject.getO_flag()));
					airLineForecastDetail.setQp_flag(Float.parseFloat(airLineForecastDetailObject.getQp_flag()));
					airLineForecastDetail.setQt_flag(Float.parseFloat(airLineForecastDetailObject.getQt_flag()));
					airLineForecastService.updateAirLineForecastDetail(airLineForecastDetail);
				}
			}
		}
		return getforcastData(airLineForecastQuery);
	}
	/**
	 * 组装计算页面数据
	 * @Title: getAirLineForecastDetailObjectList 
	 * @Description:  
	 * @param @param airLineForecastDetailList
	 * @param @param airLineForecast
	 * @param @return    
	 * @return List<AirLineForecastDetailObject>
	 * @throws
	 */
	public List<AirLineForecastDetailObject> getAirLineForecastDetailObjectList(List<AirLineForecastDetail> airLineForecastDetailList,AirLineForecast airLineForecast){
		List<AirLineForecastDetailObject> airLineForecastDetailObjectList = new ArrayList<AirLineForecastDetailObject>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat   df   =new   DecimalFormat("0.00");
		for (AirLineForecastDetail airLineForecastDetail : airLineForecastDetailList) {
			AirLineForecastDetailObject airLineForecastDetailObject = new AirLineForecastDetailObject();
			airLineForecastDetailObject.setId(airLineForecastDetail.getId());
			airLineForecastDetailObject.setDoTime(sdf.format(airLineForecast.getStartDate())+"<br/>"+sdf.format(airLineForecast.getEndDate()));
			airLineForecastDetailObject.setHangduan(airLineForecastDetail.getHangduan());
			airLineForecastDetailObject.setHangju(airLineForecastDetail.getHangju()+"");
			airLineForecastDetailObject.setYc_price(airLineForecastDetail.getY_price()+"");
			airLineForecastDetailObject.setQwtdjg_price(airLineForecastDetail.getQie_team_price()+"");
			airLineForecastDetailObject.setF_flag(airLineForecastDetail.getF_flag()+"");
			airLineForecastDetailObject.setY_flag(airLineForecastDetail.getY_flag()+"");
			airLineForecastDetailObject.setB_flag(airLineForecastDetail.getB_flag()+"");
			airLineForecastDetailObject.setH_flag(airLineForecastDetail.getH_flag()+"");
			airLineForecastDetailObject.setK_flag(airLineForecastDetail.getK_flag()+"");
			airLineForecastDetailObject.setL_flag(airLineForecastDetail.getL_flag()+"");
			airLineForecastDetailObject.setM_flag(airLineForecastDetail.getM_flag()+"");
			airLineForecastDetailObject.setQ_flag(airLineForecastDetail.getQ_flag()+"");
			airLineForecastDetailObject.setX_flag(airLineForecastDetail.getX_flag()+"");
			airLineForecastDetailObject.setU_flag(airLineForecastDetail.getU_flag()+"");
			airLineForecastDetailObject.setE_flag(airLineForecastDetail.getE_flag()+"");
			airLineForecastDetailObject.setZ_flag(airLineForecastDetail.getZ_flag()+"");
			airLineForecastDetailObject.setT_flag(airLineForecastDetail.getT_flag()+"");
			airLineForecastDetailObject.setV_flag(airLineForecastDetail.getV_flag()+"");
			airLineForecastDetailObject.setG_flag(airLineForecastDetail.getG_flag()+"");
			airLineForecastDetailObject.setO_flag(airLineForecastDetail.getO_flag()+"");
			airLineForecastDetailObject.setS_flag(airLineForecastDetail.getS_flag()+"");
			airLineForecastDetailObject.setQt_flag(airLineForecastDetail.getQt_flag()+"");
			airLineForecastDetailObject.setQp_flag(airLineForecastDetail.getQp_flag()+"");
			
			float mingzherenshu =  airLineForecastDetail.getF_flag()+airLineForecastDetail.getY_flag()+airLineForecastDetail.getB_flag()+
								 airLineForecastDetail.getH_flag()+airLineForecastDetail.getK_flag()+airLineForecastDetail.getL_flag()+
								 airLineForecastDetail.getM_flag()+airLineForecastDetail.getQ_flag()+airLineForecastDetail.getX_flag()+
								 airLineForecastDetail.getU_flag()+airLineForecastDetail.getE_flag()+airLineForecastDetail.getZ_flag()+
								 airLineForecastDetail.getT_flag()+airLineForecastDetail.getV_flag()+airLineForecastDetail.getG_flag()+
								 airLineForecastDetail.getS_flag();
			float Strshj =mingzherenshu+ airLineForecastDetail.getO_flag()+airLineForecastDetail.getQt_flag()+airLineForecastDetail.getQp_flag();
			
			double mingzhe = airLineForecastDetail.getF_flag()*1.5+airLineForecastDetail.getY_flag()*1+airLineForecastDetail.getB_flag()*0.9+
							 airLineForecastDetail.getH_flag()*0.85+airLineForecastDetail.getK_flag()*0.8+airLineForecastDetail.getL_flag()*0.75+
							 airLineForecastDetail.getM_flag()*0.7+airLineForecastDetail.getQ_flag()*0.65+airLineForecastDetail.getX_flag()*0.6+
							 airLineForecastDetail.getU_flag()*0.55+airLineForecastDetail.getE_flag()*0.5+airLineForecastDetail.getZ_flag()*0.45+
							 airLineForecastDetail.getT_flag()*0.4+airLineForecastDetail.getV_flag()*0.35+airLineForecastDetail.getG_flag()*0.3+
							 airLineForecastDetail.getS_flag()*0.25;
			double mingzhemingkou = mingzhe/mingzherenshu;
			double qitazhekou = 0.0;
			if(mingzhemingkou<0.6){
				//特殊仓位0.3
				qitazhekou = 0.3;
			}else if(mingzhemingkou<0.8){
				//特殊仓位0.4
				qitazhekou = 0.4;
			}else if(mingzhemingkou<0.9){
				//特殊仓位0.5
				qitazhekou = 0.5;
			}else if(mingzhemingkou<1){
				//特殊仓位0.6
				qitazhekou = 0.6;
			}
			double qwtdzk = 0.0;
			qwtdzk = airLineForecastDetail.getQie_team_price()/airLineForecastDetail.getY_price();
			
			airLineForecastDetailObject.setStrshj(Strshj+"");
			float Skzrs = Strshj-airLineForecastDetail.getQp_flag();
			airLineForecastDetailObject.setSkzrs(Skzrs+"");
			double Skzys = (mingzhe+airLineForecastDetail.getQt_flag()*qitazhekou)*airLineForecastDetail.getY_price();
			airLineForecastDetailObject.setSkzys(df.format(Skzys)+"");
			double Tdzys = airLineForecastDetail.getQp_flag()*airLineForecastDetail.getQie_team_price();
			airLineForecastDetailObject.setTdzys(df.format(Tdzys)+"");
			double Dlf = ((airLineForecastDetail.getQp_flag()*qwtdzk)+(mingzhe+airLineForecastDetail.getQt_flag()*qitazhekou)*airLineForecastDetail.getY_price())*(airLineForecast.getDaili_price()/100);
			airLineForecastDetailObject.setDlf(df.format(Dlf)+"");
			double pjzk = (Skzys+Tdzys)/Strshj/airLineForecastDetail.getY_price();
			airLineForecastDetailObject.setPjzk(df.format(pjzk)+"");
			double ttl = Strshj;
			airLineForecastDetailObject.setTtl(df.format(ttl)+"");
			airLineForecastDetailObjectList.add(airLineForecastDetailObject);
		}
		//合计栏
		float f_flag = 0;
		float y_flag = 0;
		float b_flag = 0;
		float h_flag = 0;
		float k_flag = 0;
		float l_flag = 0;
		float m_flag = 0;
		float q_flag = 0;
		float x_flag = 0;
		float u_flag = 0;
		float e_flag = 0;
		float z_flag = 0;
		float t_flag = 0;
		float v_flag = 0;
		float g_flag = 0;
		float o_flag = 0;
		float s_flag = 0;
		float qt_flag = 0;
		float qp_flag = 0;
		float strshj = 0;
		float skzrs = 0;
		
		double skzys = 0.0;
		double tdzys = 0.0;
		double dlf = 0.0;
		double stzys = 0.0;
		double zhkzl = 0.0;
		double pjzk2 = 0.0;
		double Ttl = 0.0;
		float i = 0 ;
		double hangju = 0.0;
		for (AirLineForecastDetailObject airLineForecastDetailObject2 : airLineForecastDetailObjectList) {
			 f_flag = f_flag + Float.parseFloat(airLineForecastDetailObject2.getF_flag().trim());
			 y_flag = y_flag + Float.parseFloat(airLineForecastDetailObject2.getY_flag().trim());
			 b_flag = b_flag + Float.parseFloat(airLineForecastDetailObject2.getB_flag().trim());
			 h_flag = h_flag + Float.parseFloat(airLineForecastDetailObject2.getH_flag().trim());
			 k_flag = k_flag + Float.parseFloat(airLineForecastDetailObject2.getK_flag().trim());
			 l_flag = l_flag + Float.parseFloat(airLineForecastDetailObject2.getL_flag().trim());
			 m_flag = m_flag + Float.parseFloat(airLineForecastDetailObject2.getM_flag().trim());
			 q_flag = q_flag + Float.parseFloat(airLineForecastDetailObject2.getQ_flag().trim());
			 x_flag = x_flag + Float.parseFloat(airLineForecastDetailObject2.getX_flag().trim());
			 u_flag = u_flag + Float.parseFloat(airLineForecastDetailObject2.getU_flag().trim());
			 e_flag = e_flag + Float.parseFloat(airLineForecastDetailObject2.getE_flag().trim());
			 z_flag = z_flag + Float.parseFloat(airLineForecastDetailObject2.getZ_flag().trim());
			 t_flag = t_flag + Float.parseFloat(airLineForecastDetailObject2.getT_flag().trim());
			 v_flag = v_flag + Float.parseFloat(airLineForecastDetailObject2.getV_flag().trim());
			 g_flag = g_flag + Float.parseFloat(airLineForecastDetailObject2.getG_flag().trim());
			 o_flag =o_flag + Float.parseFloat(airLineForecastDetailObject2.getO_flag().trim());
			 s_flag = s_flag + Float.parseFloat(airLineForecastDetailObject2.getS_flag().trim());
			 qt_flag = qt_flag + Float.parseFloat(airLineForecastDetailObject2.getQt_flag().trim());
			 qp_flag = qp_flag + Float.parseFloat(airLineForecastDetailObject2.getQp_flag().trim());
			 strshj =strshj + Float.parseFloat(airLineForecastDetailObject2.getStrshj().trim());
			 skzrs =skzrs + Float.parseFloat(airLineForecastDetailObject2.getSkzrs().trim());
			 skzys = skzys + Double.parseDouble(airLineForecastDetailObject2.getSkzys().trim());
			 tdzys = tdzys + Double.parseDouble(airLineForecastDetailObject2.getTdzys().trim());
			 dlf = dlf + Double.parseDouble(airLineForecastDetailObject2.getDlf().trim());
			 pjzk2 = pjzk2 + Double.parseDouble(airLineForecastDetailObject2.getYc_price().trim())*Double.parseDouble(airLineForecastDetailObject2.getStrshj().trim());
			 if(i==0||i==2){
				 hangju = hangju + Double.parseDouble(airLineForecastDetailObject2.getHangju().trim());
			 }
			 Ttl = Ttl + Double.parseDouble(airLineForecastDetailObject2.getTtl().trim());
			 if(airLineForecastDetailObjectList.size()>2){
				 double x11 = Double.parseDouble(airLineForecastDetailObjectList.get(5).getStrshj().trim());
				 double x10 = Double.parseDouble(airLineForecastDetailObjectList.get(4).getStrshj().trim());
				 double x9 = Double.parseDouble(airLineForecastDetailObjectList.get(3).getStrshj().trim());
				 double x8 = Double.parseDouble(airLineForecastDetailObjectList.get(2).getStrshj().trim());
				 double x7 = Double.parseDouble(airLineForecastDetailObjectList.get(1).getStrshj().trim());
				 double x6 = Double.parseDouble(airLineForecastDetailObjectList.get(0).getStrshj().trim());
				 double C6 = Double.parseDouble(airLineForecastDetailObjectList.get(0).getHangju().trim());
				 double C8 = Double.parseDouble(airLineForecastDetailObjectList.get(2).getHangju().trim());
				 double C9 = Double.parseDouble(airLineForecastDetailObjectList.get(3).getHangju().trim());
				 double C11 = Double.parseDouble(airLineForecastDetailObjectList.get(5).getHangju().trim());
				 zhkzl = (x7+x6*(C6/(C6+C8))+x8*(C8/(C6+C8))+x9*(C9/(C9+C11))+x10+x11*(C9/(C9+C11)));
				}else{
					zhkzl = zhkzl + Double.parseDouble(airLineForecastDetailObject2.getStrshj().trim());
				}
		}
		stzys = skzys + tdzys - dlf;	
		AirLineForecastDetailObject airLineForecastDetailObjectHeji = new AirLineForecastDetailObject();
		airLineForecastDetailObjectHeji.setDoTime("合计");
		airLineForecastDetailObjectHeji.setHangduan("");
		airLineForecastDetailObjectHeji.setHangju("");
		airLineForecastDetailObjectHeji.setYc_price("");
		airLineForecastDetailObjectHeji.setQwtdjg_price("");
		
		airLineForecastDetailObjectHeji.setF_flag(f_flag+"");
		airLineForecastDetailObjectHeji.setY_flag(y_flag+"");
		airLineForecastDetailObjectHeji.setB_flag(b_flag+"");
		airLineForecastDetailObjectHeji.setH_flag(h_flag+"");
		airLineForecastDetailObjectHeji.setK_flag(k_flag+"");
		airLineForecastDetailObjectHeji.setL_flag(l_flag+"");
		airLineForecastDetailObjectHeji.setM_flag(m_flag+"");
		airLineForecastDetailObjectHeji.setQ_flag(q_flag+"");
		airLineForecastDetailObjectHeji.setX_flag(x_flag+"");
		airLineForecastDetailObjectHeji.setU_flag(u_flag+"");
		airLineForecastDetailObjectHeji.setE_flag(e_flag+"");
		airLineForecastDetailObjectHeji.setZ_flag(z_flag+"");
		airLineForecastDetailObjectHeji.setT_flag(t_flag+"");
		airLineForecastDetailObjectHeji.setV_flag(v_flag+"");
		airLineForecastDetailObjectHeji.setG_flag(g_flag+"");
		airLineForecastDetailObjectHeji.setO_flag(o_flag+"");
		airLineForecastDetailObjectHeji.setS_flag(s_flag+"");
		airLineForecastDetailObjectHeji.setQt_flag(qt_flag+"");
		airLineForecastDetailObjectHeji.setQp_flag(qp_flag+"");
		airLineForecastDetailObjectHeji.setStrshj(df.format(strshj)+"");
		airLineForecastDetailObjectHeji.setSkzrs(df.format(skzrs)+"");
		airLineForecastDetailObjectHeji.setSkzys(df.format(skzys)+"");
		airLineForecastDetailObjectHeji.setTdzys(df.format(tdzys)+"");
		airLineForecastDetailObjectHeji.setDlf(df.format(dlf)+"");
		
		airLineForecastDetailObjectHeji.setZglsr(df.format(stzys/hangju/airLineForecast.getFly_site())+"");
		airLineForecastDetailObjectHeji.setPjzk(df.format(stzys/pjzk2)+"");
		airLineForecastDetailObjectHeji.setStzys(df.format(stzys)+"");
		airLineForecastDetailObjectHeji.setXsys(df.format(stzys/airLineForecast.getFly_time())+"");
		airLineForecastDetailObjectHeji.setZhkzl(df.format(zhkzl/(2*airLineForecast.getFly_site()))+"");
		double jbbt = airLineForecast.getFly_price()*airLineForecast.getFly_time()-stzys;
		if(jbbt<0){
			jbbt = 0.00;
		}
		airLineForecastDetailObjectHeji.setJbbt(df.format(jbbt)+"");
		double nbt = jbbt*Double.parseDouble(airLineForecast.getFly_banci().trim());
		if(nbt<0){
			nbt = 0.00;
		}
		airLineForecastDetailObjectHeji.setNbt(df.format(nbt)+"");
		airLineForecastDetailObjectHeji.setTtl(df.format(Ttl)+"");
		airLineForecastDetailObjectList.add(airLineForecastDetailObjectHeji);
		return airLineForecastDetailObjectList;
	}
	/**
	 * 封装到前端的数据
	 * @Title: getforcastData 
	 * @Description:  
	 * @param @param airLineForecastQuery
	 * @param @return    
	 * @return Object     
	 * @throws
	 */
	public Object getforcastData(AirLineForecastQuery airLineForecastQuery){
		AirLineForecast airLineForecast = airLineForecastService.getAirLineForecast(airLineForecastQuery);
		
		List<AirLineForecastDetailObject> airLineForecastDetailObjectList = new ArrayList<AirLineForecastDetailObject>();
		Fare fareinfo = new Fare();
		if(airLineForecast==null){
			//得到航线的YB运价和航距
			//该航线没有预估过，手动组装航线详细信息
			if(airLineForecastQuery.getPas_stp()==null||"".equals(airLineForecastQuery.getPas_stp())){
				//没有经停
				AirLineForecastDetailObject airLineForecastDetailObject1 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject1.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject1.setHangduan(airLineForecastQuery.getDpt_AirPt_Cd()+""+airLineForecastQuery.getArrv_Airpt_Cd());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getDpt_AirPt_Cd(),airLineForecastQuery.getArrv_Airpt_Cd());
				if(fareinfo!=null){
					airLineForecastDetailObject1.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject1.setHangju(fareinfo.getSailingDistance()+"");
				}
				AirLineForecastDetailObject airLineForecastDetailObject2 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject2.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject2.setHangduan(airLineForecastQuery.getArrv_Airpt_Cd()+""+airLineForecastQuery.getDpt_AirPt_Cd());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getArrv_Airpt_Cd(),airLineForecastQuery.getDpt_AirPt_Cd());
				if(fareinfo!=null){
					airLineForecastDetailObject2.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject2.setHangju(fareinfo.getSailingDistance()+"");
				}
				airLineForecastDetailObjectList.add(airLineForecastDetailObject1);
				airLineForecastDetailObjectList.add(airLineForecastDetailObject2);
			}else{
				AirLineForecastDetailObject airLineForecastDetailObject1 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject1.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject1.setHangduan(airLineForecastQuery.getDpt_AirPt_Cd()+""+airLineForecastQuery.getPas_stp());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getDpt_AirPt_Cd(),airLineForecastQuery.getPas_stp());
				if(fareinfo!=null){
					airLineForecastDetailObject1.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject1.setHangju(fareinfo.getSailingDistance()+"");
				}
				
				AirLineForecastDetailObject airLineForecastDetailObject2 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject2.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject2.setHangduan(airLineForecastQuery.getDpt_AirPt_Cd()+""+airLineForecastQuery.getArrv_Airpt_Cd());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getDpt_AirPt_Cd(),airLineForecastQuery.getArrv_Airpt_Cd());
				if(fareinfo!=null){
					airLineForecastDetailObject2.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject2.setHangju(fareinfo.getSailingDistance()+"");
				}
				AirLineForecastDetailObject airLineForecastDetailObject3 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject3.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject3.setHangduan(airLineForecastQuery.getPas_stp()+""+airLineForecastQuery.getArrv_Airpt_Cd());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getPas_stp(),airLineForecastQuery.getArrv_Airpt_Cd());
				if(fareinfo!=null){
					airLineForecastDetailObject3.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject3.setHangju(fareinfo.getSailingDistance()+"");
				}												
				AirLineForecastDetailObject airLineForecastDetailObject4 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject4.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject4.setHangduan(airLineForecastQuery.getArrv_Airpt_Cd()+""+airLineForecastQuery.getPas_stp());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getArrv_Airpt_Cd(),airLineForecastQuery.getPas_stp());
				if(fareinfo!=null){
					airLineForecastDetailObject4.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject4.setHangju(fareinfo.getSailingDistance()+"");
				}
				AirLineForecastDetailObject airLineForecastDetailObject5 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject5.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject5.setHangduan(airLineForecastQuery.getArrv_Airpt_Cd()+""+airLineForecastQuery.getDpt_AirPt_Cd());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getArrv_Airpt_Cd(),airLineForecastQuery.getDpt_AirPt_Cd());
				if(fareinfo!=null){
					airLineForecastDetailObject5.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject5.setHangju(fareinfo.getSailingDistance()+"");
				}
				AirLineForecastDetailObject airLineForecastDetailObject6 = new AirLineForecastDetailObject(); 
				airLineForecastDetailObject6.setDoTime(airLineForecastQuery.getStartDate()+"<br/>"+airLineForecastQuery.getEndDate());
				airLineForecastDetailObject6.setHangduan(airLineForecastQuery.getPas_stp()+""+airLineForecastQuery.getDpt_AirPt_Cd());
				fareinfo = airLineForecastService.getFare(airLineForecastQuery.getPas_stp(),airLineForecastQuery.getDpt_AirPt_Cd());
				if(fareinfo!=null){
					airLineForecastDetailObject6.setYc_price(fareinfo.getyBFare()+"");
					airLineForecastDetailObject6.setHangju(fareinfo.getSailingDistance()+"");
				}
				airLineForecastDetailObjectList.add(airLineForecastDetailObject1);
				airLineForecastDetailObjectList.add(airLineForecastDetailObject2);
				airLineForecastDetailObjectList.add(airLineForecastDetailObject3);
				airLineForecastDetailObjectList.add(airLineForecastDetailObject4);
				airLineForecastDetailObjectList.add(airLineForecastDetailObject5);
				airLineForecastDetailObjectList.add(airLineForecastDetailObject6);
				
			}
		}else{
			//根据航线基本信息查询航线详细信息
			List<AirLineForecastDetail> airLineForecastDetailList = airLineForecastService.getAirLineForecastDetailList(airLineForecast);
			airLineForecastDetailObjectList = getAirLineForecastDetailObjectList(airLineForecastDetailList, airLineForecast);
		}
		AirLineForecastObject airLineForecastObject = new AirLineForecastObject();
		airLineForecastObject.setAirLineForecast(airLineForecast);
		airLineForecastObject.setAirLineForecastDetailObjectList(airLineForecastDetailObjectList);
		return airLineForecastObject;
	}
}


