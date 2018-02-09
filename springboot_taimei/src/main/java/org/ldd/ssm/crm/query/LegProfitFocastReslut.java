package org.ldd.ssm.crm.query;

import java.util.List;

public class LegProfitFocastReslut {

	private String airline;//CTUWDSPEK 
	private List<String> legList;//CTU-WDS CTU-PEK ...
	
	private String startTime;
	private String endTime;
	
	//距离km
	private float distance;
	
	//航速km/h
	private float speed;
	
	
	
	
	
	private String airplaneLeg;//xx机场-xx机场
	
    private Float avgTravellerCount;//均班客量

    private Float avgIncome;//均班收入

    private Float avgOccuRate;//均班客座率

    private String avgDiscount;//均班折扣
    
    
}
