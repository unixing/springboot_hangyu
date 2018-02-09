package org.ldd.ssm.crm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.ldd.ssm.crm.domain.UserCommitInfo;
import org.ldd.ssm.crm.mapper.UserCommitBlobMapper;
import org.ldd.ssm.crm.mapper.UserCommitInfoMapper;
import org.ldd.ssm.crm.mapper.UserCommitTextMapper;
import org.ldd.ssm.crm.query.UserCommit;
import org.ldd.ssm.crm.service.IUserCommitService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.stereotype.Service;

@Service
public class UserCommitServiceImpl implements IUserCommitService{

	@Resource 
	private UserCommitInfoMapper  userCommitInfoMapper;
	@Resource 
	private UserCommitTextMapper  userCommitTextMapper;
	@Resource 
	private UserCommitBlobMapper  userCommitBlobMapper;
	@Override
	public boolean userCommitAdd(UserCommit userCommit) {
		int affectInfoRows=0;
		int affectTextRows=0;
		int affectBlobRows=0;
		/*Date dt=new Date();
		SimpleDateFormat sdf =new java.text.SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(dt);*/
		
		if(null==userCommit.getUserCommitInfoId()){
			UserCommitInfo userCommitInfo=new UserCommitInfo();
			SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");  
			userCommitInfo.setTextDate(simple.format(new Date()));
			//标题
			if(userCommit.getUserText().length()>10){
				userCommitInfo.setTitle(userCommit.getUserText().substring(0, 10));
			}else{
				userCommitInfo.setTitle(userCommit.getUserText());
			}
			//状态
			userCommitInfo.setState(0);//待处理
			userCommitInfo.setEmployeeId(UserContext.getUser().getId());
			//插入userCommitInfo表
			affectInfoRows=userCommitInfoMapper.insert(userCommitInfo);
			userCommit.setUserCommitInfoId(userCommitInfo.getId());
		}
		//插入userCommitBlob表
		//判断是否有图片
		if(!userCommit.getUserImgs().isEmpty()){
			userCommit.setUserUuid(UUID.randomUUID().toString());
			userCommit.setUserImgs(userCommit.getUserImgs());
			affectBlobRows=userCommitBlobMapper.insert(userCommit);
		}
		
		//插入userCommitText表
		userCommit.setUserCommitInfoId(userCommit.getUserCommitInfoId());
		userCommit.setUserUpdateDate(new Date());
		affectTextRows=userCommitTextMapper.insert(userCommit);
		
		if(affectInfoRows>0||affectTextRows>0||affectBlobRows>0){
			return true;
		}
		return false;
	}

	@Override
	public List<UserCommit> findUserCommitList(Long employeeId) {
		return userCommitInfoMapper.findUserCommitInfo(employeeId);
	}

	@Override
	public List<UserCommit> findUserCommitDetail(Long userCommitInfoId) {
		return userCommitTextMapper.findUserCommitDetail(userCommitInfoId);
	}

	@Override
	public boolean updateState(UserCommitInfo record) {
		int affectRows=0;
		affectRows=userCommitInfoMapper.updateState(record);
		if(affectRows>0){
			return true;
		}
		return false;
	}

}
