package org.ldd.ssm.crm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.Eachflightinfo;
import org.ldd.ssm.crm.domain.LinkTypeData;
import org.ldd.ssm.crm.domain.Rule;
import org.ldd.ssm.crm.query.ProcessTaskObject;
import org.ldd.ssm.crm.query.ProcessTaskQuery;
/**
 * 网络爬虫的一系列方法
 *
 */
public interface IExtractService {
	void validateRule(Rule rule);
	ArrayList<String> getfomt(String str);
	String get(String name);
	void testname(String str, String name, Date format);
	void extract2(Rule rule, String name);
	void extract(Rule rule);
	void save(LinkTypeData data);
	List<String> getName();
	List<String> get2(String name);
	ProcessTaskObject<Eachflightinfo> query(ProcessTaskQuery sQuery);
	ProcessTaskObject<Eachflightinfo> queryByIata(ProcessTaskQuery sQuery);
	ProcessTaskObject<Eachflightinfo> queryPort(ProcessTaskQuery sQuery);
	ProcessTaskObject<Eachflightinfo> queryPortReturn(ProcessTaskQuery sQuery);
	List<AcquisitionTime> getAll();
	ProcessTaskObject<Eachflightinfo> queryAll(ProcessTaskQuery sQuery);
	ProcessTaskObject<Eachflightinfo> queryAllByIata(ProcessTaskQuery sQuery);
	ProcessTaskObject<Eachflightinfo> queryToRoRreturn(ProcessTaskQuery sQuery);
	ProcessTaskObject<Eachflightinfo> queryToRoRreturnByIata(ProcessTaskQuery sQuery);
	List<String> getNewEstCollectDate(ProcessTaskQuery sQuery);
}
