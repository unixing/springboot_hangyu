package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.Eachflightinfo;
import org.ldd.ssm.crm.domain.TableAnnotation;
import org.ldd.ssm.crm.domain.TableName;
import org.ldd.ssm.crm.query.ProcessTaskQuery;

/**
 *持久层的dao接口方法
 */
public interface PassengerdetalisinfoMapper {

	String get(String name);

	void save(Eachflightinfo each);

	List<String> list();

	List<String> get2(String name);

	List<Eachflightinfo> query(ProcessTaskQuery sQuery);

	Integer queryTotal(ProcessTaskQuery sQuery);
	
	List<Eachflightinfo> queryByIata(ProcessTaskQuery sQuery);

	Integer queryTotalByIata(ProcessTaskQuery sQuery);

	List<TableName> databaseTableName();

	void databaseTableName1();

	List<TableAnnotation> databaseTableName3(String tableName);

	List<Eachflightinfo> queryPort(ProcessTaskQuery sQuery);
	
	List<Eachflightinfo> queryPortReturn(ProcessTaskQuery sQuery);

	List<AcquisitionTime> getAll();

	List<Eachflightinfo> queryAll(ProcessTaskQuery sQuery);
	
	List<Eachflightinfo> queryAllByIata(ProcessTaskQuery sQuery);

	Integer queryTotalAll(ProcessTaskQuery sQuery);
	
	Integer queryTotalAllByIata(ProcessTaskQuery sQuery);

	List<Eachflightinfo> queryToRoRreturn(ProcessTaskQuery sQuery);

	Integer queryTotalToRoRreturn(ProcessTaskQuery sQuery);
	
	List<Eachflightinfo> queryToRoRreturnByIata(ProcessTaskQuery sQuery);

	Integer queryTotalToRoRreturnByIata(ProcessTaskQuery sQuery);
	
	List<String> getNewEstCollectDate(ProcessTaskQuery sQuery);	
}
