package org.ldd.ssm.crm.mapperInterceptor;
import java.sql.Connection;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;  
  
import org.apache.ibatis.executor.statement.RoutingStatementHandler;  
import org.apache.ibatis.executor.statement.StatementHandler;  
import org.apache.ibatis.mapping.BoundSql;  
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;  
import org.apache.ibatis.plugin.Intercepts;  
import org.apache.ibatis.plugin.Invocation;  
import org.apache.ibatis.plugin.Plugin;  
import org.apache.ibatis.plugin.Signature;  
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.FlightRoute;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.MapperData;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
  

/**   
 * @Title: RoleInterceptor.java 
 * @Package  
 * @Description: TODO
 * @author taimei-gds   
 * @date 2016-5-30 下午3:00:49 
 * @version V1.0   
 */

/**
 * @Title:RoleInterceptor 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-30 下午3:00:49
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class RoleInterceptor implements Interceptor{
	 private String dialect ; 
	 private String sqlId;   
	 public Object intercept(Invocation invocation) throws Throwable {
		 if(invocation.getTarget() instanceof RoutingStatementHandler){    
	            RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();    
	            StatementHandler delegate = (StatementHandler) ReflectHelper.getFieldValue(statementHandler, "delegate");    
	           // StatementHandler delegate = (StatementHandler) ReflectHelper.getFieldValue(statementHandler, "delegate");    
	            BoundSql boundSql = delegate.getBoundSql();  
	            //Object obj = boundSql.getParameterObject();  
                //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性    
               MappedStatement mappedStatement = (MappedStatement)ReflectHelper.getFieldValue(delegate, "mappedStatement");    
             
                
                //拦截到的prepare方法参数是一个Connection对象    
//                Connection connection = (Connection)invocation.getArgs()[0];    
                //获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句    
//                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//                Connection connection = (Connection)invocation.getArgs()[0]; 
//                Object parameterObject= boundSql.getParameterObject();
               
                String sql = boundSql.getSql();  
               
                if(!(sql.contains("watch_flight")||sql.contains("flight_price_comp")||sql.contains("flight_price_comp")||sql.contains("flight_price_comp"))){
                /**
                 * 留言：  错误是出现在sql拦截器中， 明天上班的时候， 发送的定时sql后面改造一下
                 * 
                 * select * from table :不需要拦截
                 * 
                 * 然后在拦截器中直接放过就可以了
                 */
                
                
                String companyItia= UserContext.getcompanyItia();
                Employee employee= UserContext.getUser();
                String sqlNew = sql;
                //根据首页的所有航线的状态来拦截查询的航线是否该有权限操作
                List<HomePageData> homePageDataList = UserContext.getHomePageDataList();
                List<String> airLineList = new ArrayList<String>();
                if(homePageDataList!=null&&homePageDataList.size()>0){
                	for (HomePageData homePageData : homePageDataList) {
						if(!TextUtil.isEmpty(homePageData.getState())&&homePageData.getState()!="2"){
							if(TextUtil.isEmpty(homePageData.getDptAirport())){
								String line1 = homePageData.getAirport()+homePageData.getArrAirport();
								String line2 = homePageData.getArrAirport()+homePageData.getAirport();
								airLineList.add(line1);
								airLineList.add(line2);
							}else{
								String line1 = homePageData.getAirport()+homePageData.getDptAirport()+homePageData.getArrAirport();
								String line2 = homePageData.getArrAirport()+homePageData.getDptAirport()+homePageData.getAirport();
								airLineList.add(line1);
								airLineList.add(line2);
							}
						}
					}
                }
                String airLines = "";
                for (String string : airLineList) {
                	airLines = airLines +"'" +string + "',";
				}
                if(!TextUtil.isEmpty(airLines)){
                	airLines = airLines.substring(0, airLines.length()-1);
     			}
                //查询
                //拦截第一步操作--把z_airdata表名改为对应的公司的三字码的表
                sqlNew = firstDoSql(sql,companyItia,airLines);
                if(employee!=null&&employee.getUsrSts()!=99){
                //第二步操作--根据数据库配置的mapper黑名单和设置好的航线航段权限确定是否查询数据库表
                List<MapperData> mapperDataList = UserContext.getMapperList();
                String mapperId = mappedStatement.getId();
              
                
                if(!TextUtil.isEmpty(mapperId)&&mapperDataList!=null&&mapperDataList.size()>0){
//             	   for (MapperData mapperData : mapperDataList) {
//             		 String mapCode = mapperData.getMapCode();
//             		 if(mapCode.equals(mapperId)){
//             			 //黑名单，表示需要进行拦截处理
//             			List<FlightRoute> flyNumList = UserContext.getFlyNumList();
//             			String flyNums = "";
//             			if(flyNumList!=null&&flyNumList.size()>0){
//             				for (FlightRoute flightRoute : flyNumList) {
//             					flyNums = flyNums +"'"+ flightRoute.getFltNbr()+"',";
//							}
//             			}
//             			if(TextUtil.isEmpty(flyNums)){
//             				flyNums = "''";
//             			}else{
//             				flyNums = flyNums.substring(0, flyNums.length()-1);
//             			}
//             			sqlNew = getSelectSql(sqlNew,flyNums);
//             		 }
//             	   }
                } 
              }
              ReflectHelper.setFieldValue(boundSql, "sql", sqlNew);    
	     }   
		 }
		 return invocation.proceed();  
		 
	}
	public String firstDoSql(String sql ,String companyItia,String airLines){
		if(!TextUtil.isEmpty(companyItia)){
        	if(sql.contains("select")||sql.contains("SELECT")){
            	//目前对z_airdata表进行过滤
            	if(sql.contains("z_airdata")||sql.contains("Z_AIRDATA")){
            		sql = sql.replace("z_airdata", companyItia) ;
            		//拦截无数据航线
            		if(!TextUtil.isEmpty(airLines)){
            			sql = getAirLineSelectSql(sql,airLines);
            		}
            	}
            }
        }
		return sql;
	}
	public String getSelectSql(String sql ,String flyNums){
		String selectsql  =  sql;
		if(sql.contains("where")||sql.contains("WHERE")){
			if(sql.contains("group")){
				String [] str = selectsql.split("group");
				selectsql = str[0] + " AND Flt_Nbr in ("  + flyNums + ") group "+str[1];
			}else{
				if(sql.contains("GROUP")){
					String [] str = selectsql.split("GROUP");
					selectsql = str[0] + " AND Flt_Nbr in ("  + flyNums + ") group "+str[1];
				}
				else{
					if(sql.contains("order")){
						String [] str = selectsql.split("order");
						selectsql = str[0] + " AND Flt_Nbr in ("  + flyNums + ") order "+str[1];
					}else{
						if(sql.contains("ORDER")){
							String [] str = selectsql.split("ORDER");
							selectsql = str[0] + " AND Flt_Nbr in ("  + flyNums + ") order "+str[1];
						}else{
							if(sql.contains("limit")){
								String [] str = selectsql.split("limit");
								selectsql = str[0] + " AND Flt_Nbr in ("  + flyNums + ") limit "+str[1];
							}else{
								if(sql.contains("LIMIT")){
									String [] str = selectsql.split("LIMIT");
									selectsql = str[0] + " AND Flt_Nbr in ("  + flyNums + ") limit "+str[1];
								}else{
									selectsql = sql + " AND Flt_Nbr in ("  + flyNums + ")" ;
								}
							}
						}
					}
				}
			}
		}else{
			if(sql.contains("group")){
				String [] str = selectsql.split("group");
				selectsql = str[0] + " WHERE Flt_Nbr in ("  + flyNums + ") group "+str[1];
			}else{
				if(sql.contains("GROUP")){
					String [] str = selectsql.split("GROUP");
					selectsql = str[0] + " WHERE Flt_Nbr in ("  + flyNums + ") group "+str[1];
				}
				else{
					if(sql.contains("order")){
						String [] str = selectsql.split("order");
						selectsql = str[0] + " WHERE Flt_Nbr in ("  + flyNums + ") order "+str[1];
					}else{
						if(sql.contains("ORDER")){
							String [] str = selectsql.split("ORDER");
							selectsql = str[0] + " WHERE Flt_Nbr in ("  + flyNums + ") order "+str[1];
						}else{
							if(sql.contains("limit")){
								String [] str = selectsql.split("limit");
								selectsql = str[0] + " WHERE Flt_Nbr in ("  + flyNums + ") limit "+str[1];
							}else{
								if(sql.contains("LIMIT")){
									String [] str = selectsql.split("LIMIT");
									selectsql = str[0] + " WHERE Flt_Nbr in ("  + flyNums + ") limit "+str[1];
								}else{
									selectsql = sql + " WHERE Flt_Nbr in ("  + flyNums + ")" ;
								}
							}
						}
					}
				}
			}
		}
		return selectsql;
	}
	/**
	 * 阻止无数据航线查看
	 * @Title: getAirLineSelectSql 
	 * @Description:  
	 * @param @param sql
	 * @param @param flyNums
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getAirLineSelectSql(String sql ,String AirLines){
		String selectsql  =  sql;
		if(sql.contains("where")||sql.contains("WHERE")){
			if(sql.contains("group")){
				String [] str = selectsql.split("group");
				selectsql = str[0] + " AND Flt_Rte_Cd in ("  + AirLines + ") group "+str[1];
			}else{
				if(sql.contains("GROUP")){
					String [] str = selectsql.split("GROUP");
					selectsql = str[0] + " AND Flt_Rte_Cd in ("  + AirLines + ") group "+str[1];
				}
				else{
					if(sql.contains("order")){
						String [] str = selectsql.split("order");
						selectsql = str[0] + " AND Flt_Rte_Cd in ("  + AirLines + ") order "+str[1];
					}else{
						if(sql.contains("ORDER")){
							String [] str = selectsql.split("ORDER");
							selectsql = str[0] + " AND Flt_Rte_Cd in ("  + AirLines + ") order "+str[1];
						}else{
							if(sql.contains("limit")){
								String [] str = selectsql.split("limit");
								selectsql = str[0] + " AND Flt_Rte_Cd in ("  + AirLines + ") limit "+str[1];
							}else{
								if(sql.contains("LIMIT")){
									String [] str = selectsql.split("LIMIT");
									selectsql = str[0] + " AND Flt_Rte_Cd in ("  + AirLines + ") limit "+str[1];
								}else{
									selectsql = sql + " AND Flt_Rte_Cd in ("  + AirLines + ")" ;
								}
							}
						}
					}
				}
			}
		}else{
			if(sql.contains("group")){
				String [] str = selectsql.split("group");
				selectsql = str[0] + " WHERE Flt_Rte_Cd in ("  + AirLines + ") group "+str[1];
			}else{
				if(sql.contains("GROUP")){
					String [] str = selectsql.split("GROUP");
					selectsql = str[0] + " WHERE Flt_Rte_Cd in ("  + AirLines + ") group "+str[1];
				}
				else{
					if(sql.contains("order")){
						String [] str = selectsql.split("order");
						selectsql = str[0] + " WHERE Flt_Rte_Cd in ("  + AirLines + ") order "+str[1];
					}else{
						if(sql.contains("ORDER")){
							String [] str = selectsql.split("ORDER");
							selectsql = str[0] + " WHERE Flt_Rte_Cd in ("  + AirLines + ") order "+str[1];
						}else{
							if(sql.contains("limit")){
								String [] str = selectsql.split("limit");
								selectsql = str[0] + " WHERE Flt_Rte_Cd in ("  + AirLines + ") limit "+str[1];
							}else{
								if(sql.contains("LIMIT")){
									String [] str = selectsql.split("LIMIT");
									selectsql = str[0] + " WHERE Flt_Rte_Cd in ("  + AirLines + ") limit "+str[1];
								}else{
									selectsql = sql + " WHERE Flt_Rte_Cd in ("  + AirLines + ")" ;
								}
							}
						}
					}
				}
			}
		}
		return selectsql;
	}
	public String getupdateSql(String sql ,String companyId){
		String updatesql  =  sql;
		return updatesql;
	}
	public Object plugin(Object target) {
	 if (target instanceof StatementHandler) {    
            return Plugin.wrap(target, this);    
        } else {    
            return target;    
        }   
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}


	
	 /**  
     * 设置注册拦截器时设定的属性  
     */   
    public void setProperties(Properties p) {  
          
    }  
	
}
