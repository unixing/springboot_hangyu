package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.UserCommitInfo;
import org.ldd.ssm.crm.query.UserCommit;

public interface IUserCommitService {
	
	public boolean userCommitAdd(UserCommit userCommit);
	
	public List<UserCommit> findUserCommitList(Long employeeId);
	
	public List<UserCommit> findUserCommitDetail(Long UserCommitInfoId);
	
	boolean updateState(UserCommitInfo record);
}
