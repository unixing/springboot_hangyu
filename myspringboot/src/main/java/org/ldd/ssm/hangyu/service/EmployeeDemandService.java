package org.ldd.ssm.hangyu.service;


import java.util.List;

import org.ldd.ssm.hangyu.domain.EmployeeDemand;

public interface EmployeeDemandService {
	public boolean add(EmployeeDemand employeeDemand);
	
	public boolean batchAdd(String employeeDemandIds);
	
	public boolean employeeDemandDel(Long employeeDemandId);
	
	public List<EmployeeDemand> employeeDemandFind(Long employeeDemandId);
	
	public int selectByEmployeeDemand(EmployeeDemand record);
}
