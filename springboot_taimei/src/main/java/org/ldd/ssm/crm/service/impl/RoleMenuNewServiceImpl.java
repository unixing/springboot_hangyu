package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.mapper.RoleMenuNewMapper;
import org.ldd.ssm.crm.service.IRoleMenuNewService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleMenuNewServiceImpl implements IRoleMenuNewService {
	@Autowired
	private RoleMenuNewMapper objMapper;
	Logger log = Logger.getLogger(RoleMenuNewServiceImpl.class);
	@Override
	public List<MenuNew> selectMenuList(Long roleId) {
		List<MenuNew> list = null;
		if(roleId==null||roleId.longValue()==0){
			log.debug("selectMenuList:roleId is invalid");
			return list;
		}
		try {
			list = objMapper.selectMenuList(roleId);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					String url = list.get(i).getUrl();
					if(url!=null&&!"".equals(url)){
						list.get(i).setUrl(url.replace("/", ""));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return list;
		}
		return list;
	}

	@Override
	public List<String> getCurrentEmployeeResource(Long roleId) {
		List<String> list = null;
		if(roleId==null||roleId.longValue()==0){
			log.debug("getCurrentEmployeeResource:roleId is invalid");
			return list;
		}
		try {
			list = objMapper.getCurrentEmployeeResource(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return list;
		}
		return list;
	}

	@Override
	public boolean checkPermission(String resourceUrl) {
		boolean result = false;
		if(resourceUrl==null||"".equals(resourceUrl)){
			log.debug("checkPermission:resourceUrl is invalid");
			return result;
		}
		try {
			List<String> list = UserContext.getUrlList();
			for(int i=0;i<list.size();i++){
				if(resourceUrl.equals(list.get(i))){
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}

}
