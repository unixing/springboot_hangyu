package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.InformationIssued;
import org.ldd.ssm.crm.domain.PageListInformationIssued;

public interface InformationIssuedService {

	public List<InformationIssued> getPageList(int page);
	
	public PageListInformationIssued getFirstPageData();
	
	public boolean updateInformationType(String lclDptDay);
	
	public boolean IsHasNewestInformation();
}
