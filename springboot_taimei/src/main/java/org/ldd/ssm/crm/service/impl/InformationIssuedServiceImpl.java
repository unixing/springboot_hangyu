package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.InformationIssued;
import org.ldd.ssm.crm.domain.PageListInformationIssued;
import org.ldd.ssm.crm.mapper.InformationIssuedMapper;
import org.ldd.ssm.crm.service.InformationIssuedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class InformationIssuedServiceImpl implements InformationIssuedService{
	@Autowired
	private InformationIssuedMapper objMapper;
	Logger log = Logger.getLogger(InformationIssuedServiceImpl.class);
	@Override
	public List<InformationIssued> getPageList(int page) {
		List<InformationIssued> list = new ArrayList<InformationIssued>();
		if(page<=0){
			log.debug("getPageList:the value of page is invalide");
			return list;
		}
		try {
			List<String> versions = objMapper.getPageList((page-1), 4);
			if(versions!=null&&versions.size()>0){
				for(int i=0;i<versions.size();i++){
					String lclDptDay = versions.get(i);
					InformationIssued obj = new InformationIssued();
					obj.setLclDptDay(lclDptDay);
					List<InformationIssued> currLogInformations = objMapper.getVersionInformation(lclDptDay);
					List<String> versionInfo = new ArrayList<String>();
					int type = 0;
					for(int j=0;j<currLogInformations.size();j++){
						InformationIssued info = currLogInformations.get(j);
						versionInfo.add(info.getLogInf());
						type += info.getType();
					}
					if(type>0){
						type = 1;
					}
					obj.setLogInfList(versionInfo);
					obj.setType(type);
					list.add(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("there is something wrong in background.");
			return list;
		}
		return list;
	}

	@Override
	public PageListInformationIssued getFirstPageData() {
		PageListInformationIssued pageList = new PageListInformationIssued();
		try {
			List<InformationIssued> list = getPageList(1);//首页数据获取
			int versionCount = objMapper.getPageCount();//获取版本总次数
			int pages = (int)Math.ceil(Double.valueOf(versionCount+"")/4);
			pageList.setList(list);
			pageList.setTotalPage(pages);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getCause());
			return pageList;
		}
		return pageList;
	}

	@Override
	public boolean updateInformationType(String lclDptDay) {
		boolean result = false;
		if(lclDptDay==null&&"".equals(lclDptDay)){
			log.debug("updateInfomationType:logInf is null");
			return result;
		}
		try {
			int activeLines = objMapper.updateDataType(lclDptDay);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public boolean IsHasNewestInformation() {
		boolean result = false;
		try {
			int lineCount = objMapper.isHasNewVersionInfo();
			if(lineCount >0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}
}
