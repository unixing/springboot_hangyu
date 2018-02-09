package org.ldd.ssm.crm.domain;
/**
 * 机场详细信息实体类
 * @Title:AirportDetailInfo 
 * @Description:
 * @author taimei-gds 
 * @date 2017-2-28 上午11:01:08
 */
public class AirportDetailInfo {
	  private String id;                             //	
		private String airln_Cd;                       //	机场名字
		private String airln_Cd_Name;                  //	机场名字全称
		private String airlin_Elh_Name;                //	机场拼音全称
		private String airlin_Spe_Spl;                 //	机场拼音简称
		private String iATA;                           //	三字码
		private String ICAO;                           //	四字码
		private String inter;                          //	是否国际机场
		private String airfieldLvl;                    //	飞行区等级
		private String city;                           //	所在城市
		private String province;                       //	所在省份
		private String city_coordinate_j;              //	经度
		private String city_coordinate_w;              //	纬度
		private String coordinateUpdateTime;           //	经纬度更新时间
		private String area;                           //	区域
		private String air_state;                      //	机场状态
		private String airpotType;                     //	机场类别
		private String warZone;                        //	战区
		private String air_ele;                        //	标高
		private String airpotCls;                      //	机场类型
		private String coordinateAirport;              //	协调机场
		private String coordinateTimeList;             //	高峰小时容量
		private String specialAirport;                 //	特殊机场
		private String specialAirportWhy;              //	特殊机场构成原因
		private String port;                           //	口岸
		private String releasePunctuality;             //	放行准点率
		private String fireLvl;                        //	消防等级
		private String lightingConditions;             //	灯光条件
		private String allowTheTakeoffAndLanding;      //	允许起降时间
		private String modelCanHandle;                 //	可起降机型
		private String runwayArticleNumber;            //	跑道条数
		private String airportShuttleMetro;            //	机场专线（地铁）
		private String airportBus;               			 //	机场巴士
		private String distanceFromDowntown;           //	距离市区距离
		private String inTheFlight;                    //	在飞航班
		private String international;                  //	国际
		private String internationalTime;              //	国际航点录入时间
		private String domestic;                       //	国内航点
		private String inThePort;                      //	
		private String inTheFlightTime;                //	在飞航班和国内航点统计时间
		private String membershipGroup;                //	所属集团
		private String planePositionNumber;            //	机位数量
		private String departureTime;                  //	开航时间
		private String operator;                       //	有无第三方运营商
		private String operatorInput;                  //	第三方运营商名称
		private String isRewardPolicy;                 //	是否有补贴政策
		private String isRewardPolicyText;             //	补贴政策
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the airln_Cd
		 */
		public String getAirln_Cd() {
			return airln_Cd;
		}
		/**
		 * @param airln_Cd the airln_Cd to set
		 */
		public void setAirln_Cd(String airln_Cd) {
			this.airln_Cd = airln_Cd;
		}
		/**
		 * @return the airln_Cd_Name
		 */
		public String getAirln_Cd_Name() {
			return airln_Cd_Name;
		}
		/**
		 * @param airln_Cd_Name the airln_Cd_Name to set
		 */
		public void setAirln_Cd_Name(String airln_Cd_Name) {
			this.airln_Cd_Name = airln_Cd_Name;
		}
		/**
		 * @return the airlin_Elh_Name
		 */
		public String getAirlin_Elh_Name() {
			return airlin_Elh_Name;
		}
		/**
		 * @param airlin_Elh_Name the airlin_Elh_Name to set
		 */
		public void setAirlin_Elh_Name(String airlin_Elh_Name) {
			this.airlin_Elh_Name = airlin_Elh_Name;
		}
		/**
		 * @return the airlin_Spe_Spl
		 */
		public String getAirlin_Spe_Spl() {
			return airlin_Spe_Spl;
		}
		/**
		 * @param airlin_Spe_Spl the airlin_Spe_Spl to set
		 */
		public void setAirlin_Spe_Spl(String airlin_Spe_Spl) {
			this.airlin_Spe_Spl = airlin_Spe_Spl;
		}
		/**
		 * @return the iATA
		 */
		public String getiATA() {
			return iATA;
		}
		/**
		 * @param iATA the iATA to set
		 */
		public void setiATA(String iATA) {
			this.iATA = iATA;
		}
		/**
		 * @return the iCAO
		 */
		public String getICAO() {
			return ICAO;
		}
		/**
		 * @param iCAO the iCAO to set
		 */
		public void setICAO(String iCAO) {
			ICAO = iCAO;
		}
		/**
		 * @return the inter
		 */
		public String getInter() {
			return inter;
		}
		/**
		 * @param inter the inter to set
		 */
		public void setInter(String inter) {
			this.inter = inter;
		}
		/**
		 * @return the airfieldLvl
		 */
		public String getAirfieldLvl() {
			return airfieldLvl;
		}
		/**
		 * @param airfieldLvl the airfieldLvl to set
		 */
		public void setAirfieldLvl(String airfieldLvl) {
			this.airfieldLvl = airfieldLvl;
		}
		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}
		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}
		/**
		 * @return the province
		 */
		public String getProvince() {
			return province;
		}
		/**
		 * @param province the province to set
		 */
		public void setProvince(String province) {
			this.province = province;
		}
		/**
		 * @return the city_coordinate_j
		 */
		public String getCity_coordinate_j() {
			return city_coordinate_j;
		}
		/**
		 * @param city_coordinate_j the city_coordinate_j to set
		 */
		public void setCity_coordinate_j(String city_coordinate_j) {
			this.city_coordinate_j = city_coordinate_j;
		}
		/**
		 * @return the city_coordinate_w
		 */
		public String getCity_coordinate_w() {
			return city_coordinate_w;
		}
		/**
		 * @param city_coordinate_w the city_coordinate_w to set
		 */
		public void setCity_coordinate_w(String city_coordinate_w) {
			this.city_coordinate_w = city_coordinate_w;
		}
		/**
		 * @return the coordinateUpdateTime
		 */
		public String getCoordinateUpdateTime() {
			return coordinateUpdateTime;
		}
		/**
		 * @param coordinateUpdateTime the coordinateUpdateTime to set
		 */
		public void setCoordinateUpdateTime(String coordinateUpdateTime) {
			this.coordinateUpdateTime = coordinateUpdateTime;
		}
		/**
		 * @return the area
		 */
		public String getArea() {
			return area;
		}
		/**
		 * @param area the area to set
		 */
		public void setArea(String area) {
			this.area = area;
		}
		/**
		 * @return the air_state
		 */
		public String getAir_state() {
			return air_state;
		}
		/**
		 * @param air_state the air_state to set
		 */
		public void setAir_state(String air_state) {
			this.air_state = air_state;
		}
		/**
		 * @return the airpotType
		 */
		public String getAirpotType() {
			return airpotType;
		}
		/**
		 * @param airpotType the airpotType to set
		 */
		public void setAirpotType(String airpotType) {
			this.airpotType = airpotType;
		}
		/**
		 * @return the warZone
		 */
		public String getWarZone() {
			return warZone;
		}
		/**
		 * @param warZone the warZone to set
		 */
		public void setWarZone(String warZone) {
			this.warZone = warZone;
		}
		/**
		 * @return the air_ele
		 */
		public String getAir_ele() {
			return air_ele;
		}
		/**
		 * @param air_ele the air_ele to set
		 */
		public void setAir_ele(String air_ele) {
			this.air_ele = air_ele;
		}
		/**
		 * @return the airpotCls
		 */
		public String getAirpotCls() {
			return airpotCls;
		}
		/**
		 * @param airpotCls the airpotCls to set
		 */
		public void setAirpotCls(String airpotCls) {
			this.airpotCls = airpotCls;
		}
		/**
		 * @return the coordinateAirport
		 */
		public String getCoordinateAirport() {
			return coordinateAirport;
		}
		/**
		 * @param coordinateAirport the coordinateAirport to set
		 */
		public void setCoordinateAirport(String coordinateAirport) {
			this.coordinateAirport = coordinateAirport;
		}
		/**
		 * @return the coordinateTimeList
		 */
		public String getCoordinateTimeList() {
			return coordinateTimeList;
		}
		/**
		 * @param coordinateTimeList the coordinateTimeList to set
		 */
		public void setCoordinateTimeList(String coordinateTimeList) {
			this.coordinateTimeList = coordinateTimeList;
		}
		/**
		 * @return the specialAirport
		 */
		public String getSpecialAirport() {
			return specialAirport;
		}
		/**
		 * @param specialAirport the specialAirport to set
		 */
		public void setSpecialAirport(String specialAirport) {
			this.specialAirport = specialAirport;
		}
		/**
		 * @return the specialAirportWhy
		 */
		public String getSpecialAirportWhy() {
			return specialAirportWhy;
		}
		/**
		 * @param specialAirportWhy the specialAirportWhy to set
		 */
		public void setSpecialAirportWhy(String specialAirportWhy) {
			this.specialAirportWhy = specialAirportWhy;
		}
		/**
		 * @return the port
		 */
		public String getPort() {
			return port;
		}
		/**
		 * @param port the port to set
		 */
		public void setPort(String port) {
			this.port = port;
		}
		/**
		 * @return the releasePunctuality
		 */
		public String getReleasePunctuality() {
			return releasePunctuality;
		}
		/**
		 * @param releasePunctuality the releasePunctuality to set
		 */
		public void setReleasePunctuality(String releasePunctuality) {
			this.releasePunctuality = releasePunctuality;
		}
		/**
		 * @return the fireLvl
		 */
		public String getFireLvl() {
			return fireLvl;
		}
		/**
		 * @param fireLvl the fireLvl to set
		 */
		public void setFireLvl(String fireLvl) {
			this.fireLvl = fireLvl;
		}
		/**
		 * @return the lightingConditions
		 */
		public String getLightingConditions() {
			return lightingConditions;
		}
		/**
		 * @param lightingConditions the lightingConditions to set
		 */
		public void setLightingConditions(String lightingConditions) {
			this.lightingConditions = lightingConditions;
		}
		/**
		 * @return the allowTheTakeoffAndLanding
		 */
		public String getAllowTheTakeoffAndLanding() {
			return allowTheTakeoffAndLanding;
		}
		/**
		 * @param allowTheTakeoffAndLanding the allowTheTakeoffAndLanding to set
		 */
		public void setAllowTheTakeoffAndLanding(String allowTheTakeoffAndLanding) {
			this.allowTheTakeoffAndLanding = allowTheTakeoffAndLanding;
		}
		/**
		 * @return the modelCanHandle
		 */
		public String getModelCanHandle() {
			return modelCanHandle;
		}
		/**
		 * @param modelCanHandle the modelCanHandle to set
		 */
		public void setModelCanHandle(String modelCanHandle) {
			this.modelCanHandle = modelCanHandle;
		}
		/**
		 * @return the runwayArticleNumber
		 */
		public String getRunwayArticleNumber() {
			return runwayArticleNumber;
		}
		/**
		 * @param runwayArticleNumber the runwayArticleNumber to set
		 */
		public void setRunwayArticleNumber(String runwayArticleNumber) {
			this.runwayArticleNumber = runwayArticleNumber;
		}
		/**
		 * @return the airportShuttleMetro
		 */
		public String getAirportShuttleMetro() {
			return airportShuttleMetro;
		}
		/**
		 * @param airportShuttleMetro the airportShuttleMetro to set
		 */
		public void setAirportShuttleMetro(String airportShuttleMetro) {
			this.airportShuttleMetro = airportShuttleMetro;
		}
		/**
		 * @return the airportBus
		 */
		public String getAirportBus() {
			return airportBus;
		}
		/**
		 * @param airportBus the airportBus to set
		 */
		public void setAirportBus(String airportBus) {
			this.airportBus = airportBus;
		}
		/**
		 * @return the distanceFromDowntown
		 */
		public String getDistanceFromDowntown() {
			return distanceFromDowntown;
		}
		/**
		 * @param distanceFromDowntown the distanceFromDowntown to set
		 */
		public void setDistanceFromDowntown(String distanceFromDowntown) {
			this.distanceFromDowntown = distanceFromDowntown;
		}
		/**
		 * @return the inTheFlight
		 */
		public String getInTheFlight() {
			return inTheFlight;
		}
		/**
		 * @param inTheFlight the inTheFlight to set
		 */
		public void setInTheFlight(String inTheFlight) {
			this.inTheFlight = inTheFlight;
		}
		/**
		 * @return the international
		 */
		public String getInternational() {
			return international;
		}
		/**
		 * @param international the international to set
		 */
		public void setInternational(String international) {
			this.international = international;
		}
		/**
		 * @return the internationalTime
		 */
		public String getInternationalTime() {
			return internationalTime;
		}
		/**
		 * @param internationalTime the internationalTime to set
		 */
		public void setInternationalTime(String internationalTime) {
			this.internationalTime = internationalTime;
		}
		/**
		 * @return the domestic
		 */
		public String getDomestic() {
			return domestic;
		}
		/**
		 * @param domestic the domestic to set
		 */
		public void setDomestic(String domestic) {
			this.domestic = domestic;
		}
		/**
		 * @return the inThePort
		 */
		public String getInThePort() {
			return inThePort;
		}
		/**
		 * @param inThePort the inThePort to set
		 */
		public void setInThePort(String inThePort) {
			this.inThePort = inThePort;
		}
		/**
		 * @return the inTheFlightTime
		 */
		public String getInTheFlightTime() {
			return inTheFlightTime;
		}
		/**
		 * @param inTheFlightTime the inTheFlightTime to set
		 */
		public void setInTheFlightTime(String inTheFlightTime) {
			this.inTheFlightTime = inTheFlightTime;
		}
		/**
		 * @return the membershipGroup
		 */
		public String getMembershipGroup() {
			return membershipGroup;
		}
		/**
		 * @param membershipGroup the membershipGroup to set
		 */
		public void setMembershipGroup(String membershipGroup) {
			this.membershipGroup = membershipGroup;
		}
		/**
		 * @return the planePositionNumber
		 */
		public String getPlanePositionNumber() {
			return planePositionNumber;
		}
		/**
		 * @param planePositionNumber the planePositionNumber to set
		 */
		public void setPlanePositionNumber(String planePositionNumber) {
			this.planePositionNumber = planePositionNumber;
		}
		/**
		 * @return the departureTime
		 */
		public String getDepartureTime() {
			return departureTime;
		}
		/**
		 * @param departureTime the departureTime to set
		 */
		public void setDepartureTime(String departureTime) {
			this.departureTime = departureTime;
		}
		/**
		 * @return the operator
		 */
		public String getOperator() {
			return operator;
		}
		/**
		 * @param operator the operator to set
		 */
		public void setOperator(String operator) {
			this.operator = operator;
		}
		/**
		 * @return the operatorInput
		 */
		public String getOperatorInput() {
			return operatorInput;
		}
		/**
		 * @param operatorInput the operatorInput to set
		 */
		public void setOperatorInput(String operatorInput) {
			this.operatorInput = operatorInput;
		}
		/**
		 * @return the isRewardPolicy
		 */
		public String getIsRewardPolicy() {
			return isRewardPolicy;
		}
		/**
		 * @param isRewardPolicy the isRewardPolicy to set
		 */
		public void setIsRewardPolicy(String isRewardPolicy) {
			this.isRewardPolicy = isRewardPolicy;
		}
		/**
		 * @return the isRewardPolicyText
		 */
		public String getIsRewardPolicyText() {
			return isRewardPolicyText;
		}
		/**
		 * @param isRewardPolicyText the isRewardPolicyText to set
		 */
		public void setIsRewardPolicyText(String isRewardPolicyText) {
			this.isRewardPolicyText = isRewardPolicyText;
		}
		/**
		 * @param id
		 * @param airln_Cd
		 * @param airln_Cd_Name
		 * @param airlin_Elh_Name
		 * @param airlin_Spe_Spl
		 * @param iATA
		 * @param iCAO
		 * @param inter
		 * @param airfieldLvl
		 * @param city
		 * @param province
		 * @param city_coordinate_j
		 * @param city_coordinate_w
		 * @param coordinateUpdateTime
		 * @param area
		 * @param air_state
		 * @param airpotType
		 * @param warZone
		 * @param air_ele
		 * @param airpotCls
		 * @param coordinateAirport
		 * @param coordinateTimeList
		 * @param specialAirport
		 * @param specialAirportWhy
		 * @param port
		 * @param releasePunctuality
		 * @param fireLvl
		 * @param lightingConditions
		 * @param allowTheTakeoffAndLanding
		 * @param modelCanHandle
		 * @param runwayArticleNumber
		 * @param airportShuttleMetro
		 * @param airportBus
		 * @param distanceFromDowntown
		 * @param inTheFlight
		 * @param international
		 * @param internationalTime
		 * @param domestic
		 * @param inThePort
		 * @param inTheFlightTime
		 * @param membershipGroup
		 * @param planePositionNumber
		 * @param departureTime
		 * @param operator
		 * @param operatorInput
		 * @param isRewardPolicy
		 * @param isRewardPolicyText
		 */
		public AirportDetailInfo(String id, String airln_Cd,
				String airln_Cd_Name, String airlin_Elh_Name,
				String airlin_Spe_Spl, String iATA, String iCAO, String inter,
				String airfieldLvl, String city, String province,
				String city_coordinate_j, String city_coordinate_w,
				String coordinateUpdateTime, String area, String air_state,
				String airpotType, String warZone, String air_ele,
				String airpotCls, String coordinateAirport,
				String coordinateTimeList, String specialAirport,
				String specialAirportWhy, String port,
				String releasePunctuality, String fireLvl,
				String lightingConditions, String allowTheTakeoffAndLanding,
				String modelCanHandle, String runwayArticleNumber,
				String airportShuttleMetro, String airportBus,
				String distanceFromDowntown, String inTheFlight,
				String international, String internationalTime,
				String domestic, String inThePort, String inTheFlightTime,
				String membershipGroup, String planePositionNumber,
				String departureTime, String operator, String operatorInput,
				String isRewardPolicy, String isRewardPolicyText) {
			super();
			this.id = id;
			this.airln_Cd = airln_Cd;
			this.airln_Cd_Name = airln_Cd_Name;
			this.airlin_Elh_Name = airlin_Elh_Name;
			this.airlin_Spe_Spl = airlin_Spe_Spl;
			this.iATA = iATA;
			ICAO = iCAO;
			this.inter = inter;
			this.airfieldLvl = airfieldLvl;
			this.city = city;
			this.province = province;
			this.city_coordinate_j = city_coordinate_j;
			this.city_coordinate_w = city_coordinate_w;
			this.coordinateUpdateTime = coordinateUpdateTime;
			this.area = area;
			this.air_state = air_state;
			this.airpotType = airpotType;
			this.warZone = warZone;
			this.air_ele = air_ele;
			this.airpotCls = airpotCls;
			this.coordinateAirport = coordinateAirport;
			this.coordinateTimeList = coordinateTimeList;
			this.specialAirport = specialAirport;
			this.specialAirportWhy = specialAirportWhy;
			this.port = port;
			this.releasePunctuality = releasePunctuality;
			this.fireLvl = fireLvl;
			this.lightingConditions = lightingConditions;
			this.allowTheTakeoffAndLanding = allowTheTakeoffAndLanding;
			this.modelCanHandle = modelCanHandle;
			this.runwayArticleNumber = runwayArticleNumber;
			this.airportShuttleMetro = airportShuttleMetro;
			this.airportBus = airportBus;
			this.distanceFromDowntown = distanceFromDowntown;
			this.inTheFlight = inTheFlight;
			this.international = international;
			this.internationalTime = internationalTime;
			this.domestic = domestic;
			this.inThePort = inThePort;
			this.inTheFlightTime = inTheFlightTime;
			this.membershipGroup = membershipGroup;
			this.planePositionNumber = planePositionNumber;
			this.departureTime = departureTime;
			this.operator = operator;
			this.operatorInput = operatorInput;
			this.isRewardPolicy = isRewardPolicy;
			this.isRewardPolicyText = isRewardPolicyText;
		}
		/**
		 * 
		 */
		public AirportDetailInfo() {
			super();
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "AirportDetailInfo [id=" + id + ", airln_Cd=" + airln_Cd
					+ ", airln_Cd_Name=" + airln_Cd_Name + ", airlin_Elh_Name="
					+ airlin_Elh_Name + ", airlin_Spe_Spl=" + airlin_Spe_Spl
					+ ", iATA=" + iATA + ", ICAO=" + ICAO + ", inter=" + inter
					+ ", airfieldLvl=" + airfieldLvl + ", city=" + city
					+ ", province=" + province + ", city_coordinate_j="
					+ city_coordinate_j + ", city_coordinate_w="
					+ city_coordinate_w + ", coordinateUpdateTime="
					+ coordinateUpdateTime + ", area=" + area + ", air_state="
					+ air_state + ", airpotType=" + airpotType + ", warZone="
					+ warZone + ", air_ele=" + air_ele + ", airpotCls="
					+ airpotCls + ", coordinateAirport=" + coordinateAirport
					+ ", coordinateTimeList=" + coordinateTimeList
					+ ", specialAirport=" + specialAirport
					+ ", specialAirportWhy=" + specialAirportWhy + ", port="
					+ port + ", releasePunctuality=" + releasePunctuality
					+ ", fireLvl=" + fireLvl + ", lightingConditions="
					+ lightingConditions + ", allowTheTakeoffAndLanding="
					+ allowTheTakeoffAndLanding + ", modelCanHandle="
					+ modelCanHandle + ", runwayArticleNumber="
					+ runwayArticleNumber + ", airportShuttleMetro="
					+ airportShuttleMetro + ", airportBus=" + airportBus
					+ ", distanceFromDowntown=" + distanceFromDowntown
					+ ", inTheFlight=" + inTheFlight + ", international="
					+ international + ", internationalTime="
					+ internationalTime + ", domestic=" + domestic
					+ ", inThePort=" + inThePort + ", inTheFlightTime="
					+ inTheFlightTime + ", membershipGroup=" + membershipGroup
					+ ", planePositionNumber=" + planePositionNumber
					+ ", departureTime=" + departureTime + ", operator="
					+ operator + ", operatorInput=" + operatorInput
					+ ", isRewardPolicy=" + isRewardPolicy
					+ ", isRewardPolicyText=" + isRewardPolicyText + "]";
		}
		
}