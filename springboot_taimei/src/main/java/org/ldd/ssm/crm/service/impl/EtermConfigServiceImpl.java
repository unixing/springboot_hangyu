package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.Instructions;
import org.ldd.ssm.crm.mapper.EtermConfigMapper;
import org.ldd.ssm.crm.service.IEtermConfigService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EtermConfigServiceImpl implements IEtermConfigService {
	@Autowired
	private EtermConfigMapper etermConfigMapper;
	public List<Instructions> getConfiguration_list(Long id) {
		List<Instructions> configuration_list = etermConfigMapper.getConfiguration_list(id);
		for (Instructions instructions : configuration_list) {
			instructions.setDte_Aic_Rrt(Integer.valueOf(instructions.getDte_Aic_Rrt())/60/1000+"");
		}
		return configuration_list;
	}
	/**
	 * 更新采集账号方案
	 */
	public void updateConfig(Instructions instructions) {
		etermConfigMapper.updateEterm_account(instructions);
		String dte_Aic_Rrt = instructions.getDte_Aic_Rrt();
		instructions.setDte_Aic_Rrt(Integer.valueOf(dte_Aic_Rrt)*60*1000+"");
		etermConfigMapper.updateConflictresolution(instructions);
		etermConfigMapper.updateAutomaticmanual(instructions);
	}
	
	public List<Instructions> getEtermsByAccName(String name){
		List<Instructions> list=null;
		try {
			list=etermConfigMapper.getEtermsByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	public void saveConfig(Instructions instructions) {
		instructions.setIs_Four(true);
		instructions.setEmployee_id(UserContext.getUser().getId()+"");
		instructions.setState(1);
		etermConfigMapper.saveEterm_account(instructions);
		instructions.setCct_Ron(4);
		String dte_Aic_Rrt = instructions.getDte_Aic_Rrt();
		instructions.setDte_Aic_Rrt(Integer.valueOf(dte_Aic_Rrt)*60*1000+"");
		etermConfigMapper.saveConflictresolution(instructions);
		instructions.setAic_Mal(1);
		etermConfigMapper.saveAutomaticmanual(instructions);
	}
	public void deleteConfig(long id) {
		etermConfigMapper.deleteConflictresolution(id);
		etermConfigMapper.deleteAutomaticmanual(id);
		etermConfigMapper.deleteEterm_account(id);
	}

}
