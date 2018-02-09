package org.ldd.ssm.crm.quartz;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.ldd.ssm.crm.domain.FlightPriceComp;
import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.domain.WatchFlight;
import org.ldd.ssm.crm.domain.WatchRemind;
import org.ldd.ssm.crm.mapper.FlightPriceCompMapper;
import org.ldd.ssm.crm.mapper.WatchFlightMapper;
import org.ldd.ssm.crm.query.FlightPriceCompDto;
import org.ldd.ssm.crm.query.FlightSaleData;
import org.ldd.ssm.crm.query.LatestAirFare;
import org.ldd.ssm.crm.query.WatchFlightDto;
import org.ldd.ssm.crm.query.WatchRemindDto;
import org.ldd.ssm.crm.service.TicketMonitorService;
import org.ldd.ssm.crm.service.TicketPriceFromMongoService;
import org.ldd.ssm.crm.utils.Mail;
import org.ldd.ssm.crm.web.interceptor.SendSmsAliyun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Lazy(value=false)
public class MyQuartzs {
	@Autowired
	private WatchFlightMapper watchFlightMapper;
	
	@Resource
	private FlightPriceCompMapper flightPriceCompMapper;

	@Resource
	private TicketPriceFromMongoService ticketPriceFromMongo;
	
	@Resource
	private TicketMonitorService ticketMonitorService;
	
//	WatchFlightMapper watchFlightMapper = (WatchFlightMapper) BeanFactoryUtil.getBean("watchFlightMapper"); 
	
  @Scheduled(cron = "0 0/15 * * * ?")//每隔15分钟执行一次
    public void test() throws Exception {
        System.out.println("Test is working......");
        //涨跌提醒
        //查出为开启的提醒
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 
		Date date = new Date();
		String currentTime=sdf.format(date);
		//需要监控的航段
        List<WatchFlightDto> watchList=new ArrayList<WatchFlightDto>();
        //1
        watchList=watchFlightMapper.findWatchFlight(currentTime);
        List<WatchRemind> watchRemindList=new ArrayList<WatchRemind>();
        Set<Long> idCollection=new HashSet<Long>();
        if(!watchList.isEmpty()){
        	 for(WatchFlightDto w:watchList){
              	idCollection.add(w.getUserId());
             	GuestrateFlpj dto=new GuestrateFlpj();
             	dto.setDptAirptCd(w.getRelDptAirptCd());
             	dto.setArrvAirptCd(w.getRelArrvAirptCd());
             	dto.setFltNbr(w.getRelFltNbr());
             	//监控的航班号 起飞 到达
             	//根据条件查询到mongo中最新日期
             	List<LatestAirFare> currentFlightPrice=ticketPriceFromMongo.findFlightManagePrice(dto);
             	//根据关联id 查到近30天的初始价格
             	//2
             	List<FlightPriceComp> flightPrice=flightPriceCompMapper.findFlightPriceComp(w.getTktincomeId());
             	Map<String,String> map=new HashMap<String,String>();
             	if(!currentFlightPrice.isEmpty()){
             		for(LatestAirFare current:currentFlightPrice){
                 		map.put(current.getSearch_date(), current.getLowest_price());
                 	}
             	}
             	
             	float upPrice=Float.parseFloat(w.getUpPrice());
             	float downPrice=Float.parseFloat(w.getDownPrice());
             	Set<String> sets=new HashSet<String>();
             	if(!flightPrice.isEmpty()){
             		for(FlightPriceComp flightPriceComp:flightPrice){
             			if(map.get(flightPriceComp.getCompareDate())!=null){
             				float price=Float.parseFloat(map.get(flightPriceComp.getCompareDate()));
                 			float currentPrice=Float.parseFloat(flightPriceComp.getCurrentPrice());
                 			float change=price-currentPrice;
                 			if((change>upPrice)||((-change)>downPrice)){
                 				float changeRate=(float)(Math.round(change*10000/currentPrice))/100;
                 				WatchRemind  watchRemind=new WatchRemind();
                 				watchRemind.setArrvAirptCd(w.getArrvAirptCd());
                 				watchRemind.setDptAirptCd(w.getDptAirptCd());
                 				watchRemind.setChangePrice(change+"");
                 				watchRemind.setChangeRate(changeRate+"");
                 				watchRemind.setUserId(w.getUserId());
                 				watchRemind.setUserTel(w.getUserTel());
                 				watchRemind.setUserEmail(w.getUserEmail());
                 				watchRemindList.add(watchRemind);
                 				FlightPriceComp flightPriceUpdate=new FlightPriceComp();
                 				flightPriceUpdate.setCurrentPrice(price+"");
                 				flightPriceUpdate.setTktincomeId(flightPriceComp.getTktincomeId());
                 				flightPriceUpdate.setCompareDate(flightPriceComp.getCompareDate());
                 				//3
                 				flightPriceCompMapper.updatePrice(flightPriceUpdate);
                 			}
             			}
             		}
             	}
             }
        }else{
        	System.out.println("没有数据");
        }
       
      //拼接通知消息
    	List<WatchRemindDto> remindList=new ArrayList<WatchRemindDto>();
    	if(!idCollection.isEmpty()){
    		for(Long userId:idCollection){
    			Set<String> legList=new HashSet<String>();
    			float maxChangePrice=0;
    			float maxChangeRate=0;
    			WatchRemindDto remindFinal=new WatchRemindDto();
    			boolean flag=false;
    			for(WatchRemind remind:watchRemindList){
    				if(userId.equals(remind.getUserId())){
    					remindFinal.setUserId(userId);
    					remindFinal.setUserTel(remind.getUserTel());
    					remindFinal.setUserEmail(remind.getUserEmail());
    					legList.add(remind.getDptAirptCd()+"-"+remind.getArrvAirptCd());
    					//绝对值大 则 最大
    					if(Math.abs(Float.parseFloat(remind.getChangeRate()))>maxChangeRate){
    						maxChangePrice=Float.parseFloat(remind.getChangePrice());
    						maxChangeRate=Float.parseFloat(remind.getChangeRate());
    						remindFinal.setLeg(remind.getDptAirptCd()+"-"+remind.getArrvAirptCd());
    						flag=true;
    					}
    				}
    			}
    			if(flag){
    				remindFinal.setNum(legList.size()+""); 
        			remindFinal.setChangePrice(maxChangePrice+"");
        			remindFinal.setChangeRate(maxChangeRate+"");
        			remindList.add(remindFinal);
    			}
    			
    		}
    	}
        //Watc
        //查询监控信息表  通知用户
    	if(!remindList.isEmpty()){
    		for(WatchRemindDto wDto:remindList){
    			if(Integer.parseInt(wDto.getNum())>1){
    				wDto.setLegs(wDto.getLeg()+"等"+wDto.getNum()+"个");
    			}else{
    				wDto.setLegs(wDto.getLeg());
    			}
    		
    			//调用短信接口
    			//TODO
    			//SendSmsAliyun.sendPriceWatching(wDto);
    			//邮件通知
    			//String mail_to="1363948101@qq.com";//查询用户电话的时候一起查询
    			//TODO
    			if(!wDto.getUserEmail().isEmpty()){
    				Mail.sendWatchFlightMail(wDto.getUserEmail(), wDto);
    			}
    			//微信公众号提醒
    		}
    	}
    }
	//@Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点整
   @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点整
   //@Scheduled(cron = "0 0/1 * * * ?")
    public void update() throws Exception {
        System.out.println("初始价格同步");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = new Date();
		String today=sdf.format
				(date);
		//查出没有初始价格的监控航班
		List<GuestrateFlpj> listEmpty=flightPriceCompMapper.findEmptyWatch();
		for(GuestrateFlpj dto:listEmpty){
			 List<LatestAirFare> currentPriceList=ticketPriceFromMongo.findFlightManagePrice(dto);
			 if(!currentPriceList.isEmpty()){
				 FlightSaleData flightSaleData=new FlightSaleData();
				 flightSaleData.setCurrerntFlightPriceList(currentPriceList);
				 flightSaleData.setTktincomeId(dto.getId());
				 flightPriceCompMapper.batchInsert(flightSaleData);
			 }
		}
		
        //删除初始价格包中的 `flight_price_comp` 表中compare_date<今天  的价格
		//4
        flightPriceCompMapper.deleteHistory(today);
        // delete from flight_price_comp where compare_date<?
        
        //新增最新一天的价格
       // 查询TKTincome_id 集合
       //5
        List<FlightPriceCompDto> list=flightPriceCompMapper.findFlightPriceMaxDate();
        if(!list.isEmpty()){
        	for(FlightPriceCompDto comp:list){
            	//mongo 查到最新的
            	List<LatestAirFare> latestAirFareList=ticketPriceFromMongo.findFlightManagePriceByDate(comp);
            	if(!latestAirFareList.isEmpty()){
            		comp.setCurrentFlightPriceList(latestAirFareList);
                	//插入初始值表
                	//6
                	flightPriceCompMapper.batchInsertNewest(comp);
            	}
            	
            }
        }
        
    }
    //@Scheduled(cron = "0 */60 * * * ?")//1小时处理一次
}