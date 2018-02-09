package org.ldd.ssm.hangyu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Response;
import org.ldd.ssm.hangyu.domain.ResponseCopy;
import org.ldd.ssm.hangyu.mapper.ResponseCopyMapper;
import org.ldd.ssm.hangyu.service.ResponseCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseCopyServiceImpl implements ResponseCopyService{

	@Autowired
	private ResponseCopyMapper responseCopyMapper;
	
	Logger log=Logger.getLogger(ResponseCopyServiceImpl.class);
	
	@Override
	public boolean deleteResponseCopyByPrimaryKey(Long id) {
		boolean result=false;
		if(id==null||id==0){
			log.debug("deleteByPrimaryKey:id is null");
			return result;
		}
		try {
			int activelines=responseCopyMapper.deleteByPrimaryKey(id);
			if(activelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("deleteByPrimaryKey:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public boolean addResponseCopy(ResponseCopy responseCopy) {
		boolean result=false;
		if(responseCopy==null){
			log.debug("addResponseCopy:responseCopy is null");
			return result;
		}
		try {
			int activitelines=responseCopyMapper.insert(responseCopy);
			if(activitelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("addResponseCopy:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public boolean addResponseCopySelective(ResponseCopy responseCopy) {
		boolean result=false;
		if(responseCopy==null){
			log.debug("addResponseCopySelective:responseCopy is null");
			return result;
		}
		try {
			int activelines=responseCopyMapper.insertSelective(responseCopy);
			if(activelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("addResponseCopySelective:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public ResponseCopy selectByPrimaryKey(Long id) {
		ResponseCopy responseCopy=null;
		if (id==null||id==0) {
			log.debug("selectByPrimaryKey:response_id is null");
			return responseCopy;
		}
		try {
			responseCopy=responseCopyMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error("selectByPrimaryKey:there is something wrong here");
			return responseCopy;
		}
		return responseCopy;
	}

	@Override
	public List<ResponseCopy> selectByResponseCopy(ResponseCopy responseCopy) {
		List<ResponseCopy> responseCopies=null;
		if(responseCopy==null){
			log.debug("selectByResponseCopy:responseCopy is null");
			return responseCopies;
		}
		try {
			responseCopies=responseCopyMapper.selectByResponseCopy(responseCopy);
		} catch (Exception e) {
			log.error("selectByResponseCopy:there is something wrong here");
			return responseCopies;
		}
		return responseCopies;
	}

	public static ResponseCopy formatRsponseCopy(Response re){
		ResponseCopy rc=new ResponseCopy();
		rc.setId(re.getId());
		rc.setEmployeeId(re.getEmployeeId());
		rc.setDemandId(re.getDemandId());
		rc.setIntentionmoneystate(re.getIntentionmoneystate());
		rc.setWhetherhosting(re.getWhetherhosting());
		rc.setWhethernavigation(re.getWhethernavigation());
		rc.setReleaseselected(re.getReleaseselected());
		rc.setResponseselected(re.getResponseselected());
		rc.setDemandtype(re.getDemandtype());
		rc.setReleasetime(re.getReleasetime());
		rc.setTitle(re.getTitle());
		rc.setDptState(re.getDptState());
		rc.setDpt(re.getDpt());
		rc.setDptCt(re.getDptCt());
		rc.setDptAcceptnearairport(re.getDptAcceptnearairport());
		rc.setDptTimeresources(re.getDptTimeresources());
		rc.setDptTime(re.getDptTime());
		rc.setPst(re.getPst());
		rc.setPstCt(re.getPstCt());
		rc.setPstAcceptnearairport(re.getPstAcceptnearairport());
		rc.setPstTimeresources(re.getPstTimeresources());
		rc.setPstTime(re.getPstTime());
		rc.setArrvState(re.getArrvState());
		rc.setArrv(re.getArrv());
		rc.setArrvCt(re.getArrvCt());
		rc.setArrvAcceptnearairport(re.getArrvAcceptnearairport());
		rc.setArrvTimeresources(re.getArrvTimeresources());
		rc.setArrvTime(re.getArrvTime());
		rc.setAircrfttyp(re.getAircrfttyp());
		rc.setAircrfttyp(re.getAircrfttyp());
		rc.setDays(re.getDays());
		rc.setBlockbidprice(re.getBlockbidprice());
		rc.setLoadfactorsexpect(re.getLoadfactorsexpect());
		rc.setAvgguestexpect(re.getAvgguestexpect());
		rc.setSubsidypolicy(re.getSubsidypolicy());
		rc.setSailingtime(re.getSailingtime());
		rc.setSeating(re.getSeating());
		rc.setCapacitycompany(re.getCapacitycompany());
		rc.setCapacityCompany(re.getCapacityCompany());
		rc.setCapacityBase(re.getCapacityBase());
		rc.setCapacityBaseNm(re.getCapacityBaseNm());
		rc.setScheduling(re.getScheduling());
		rc.setSchedulineport(re.getSchedulineport());
		rc.setHourscost(re.getHourscost());
		rc.setRemark(re.getRemark());
		rc.setPublicway(re.getPublicway());
		rc.setDirectionalgoal(re.getDirectionalgoal());
		rc.setFltnbr(re.getFltnbr());
		rc.setContact(re.getContact());
		rc.setIhome(re.getIhome());
		rc.setResponsedate(re.getResponsedate());
		rc.setUpdatedate(re.getUpdatedate());
		rc.setNewinfo(re.getNewinfo());
		rc.setResponseProgress(re.getResponseProgress());
		rc.setDptFltLvl(re.getDptFltLvl());
		rc.setPstFltLvl(re.getPstFltLvl());
		rc.setArrvFltLvl(re.getArrvFltLvl());


		return rc;
		
	}

	@Override
	public ResponseCopy selectLastRecord(Long employeeId,Long demandId) {
		ResponseCopy responseCopy=null;
		if (employeeId==0||employeeId==null||demandId==0||demandId==null) {
			log.debug("selectLastRecord:employeeId or demandId is null");
			return responseCopy;
		}
		ResponseCopy recp=new ResponseCopy();
		recp.setEmployeeId(employeeId);
		recp.setDemandId(demandId);
		try {
			responseCopy=responseCopyMapper.selectLastRecord(recp);
		} catch (Exception e) {
			log.error("selectLastRecord:there is something wrong here");
			return responseCopy;
		}
		return responseCopy;
	}
	
}
