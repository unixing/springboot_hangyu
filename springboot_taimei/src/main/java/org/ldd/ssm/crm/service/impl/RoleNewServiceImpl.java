package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.domain.RoleMenuNew;
import org.ldd.ssm.crm.domain.RoleNew;
import org.ldd.ssm.crm.mapper.EmployeeRoleMapper;
import org.ldd.ssm.crm.mapper.MenuNewMapper;
import org.ldd.ssm.crm.mapper.RoleMenuNewMapper;
import org.ldd.ssm.crm.mapper.RoleNewMapper;
import org.ldd.ssm.crm.service.IEmployeeRoleService;
import org.ldd.ssm.crm.service.IRoleNewService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleNewServiceImpl implements IRoleNewService {
	@Autowired
	private RoleNewMapper objMapper;
	@Autowired
	private MenuNewMapper menuNewMapper;
	@Autowired
	private RoleMenuNewMapper roleMenuNewMapper;
	@Autowired
	private EmployeeRoleMapper employeeRoleMapper;
	Logger log = Logger.getLogger(RoleNewServiceImpl.class);
	@Override
	public boolean add(RoleNew role) {
		boolean result = false;
		if(role==null){
			log.debug("add:role is invalid");
			return result;
		}
		try {
			role.setItia(UserContext.getcompanyItia());
			int activeLine = objMapper.insertSelective(role);
			if(activeLine>0){
				List<MenuNew> list = role.getMenus();
				if(list!=null&&list.size()>0){
					List<RoleMenuNew> roleMenus = new ArrayList<RoleMenuNew>();
					for(int i=0;i<list.size();i++){
						RoleMenuNew roleMenu = new RoleMenuNew();
						if(list.get(i).getType()==1){
							roleMenu.setMenuId(list.get(i).getId());
							roleMenu.setRoleId(role.getId());
							roleMenus.add(roleMenu);
						}
					}
					//添加数据默认菜单-id=16
					RoleMenuNew roleMenu = new RoleMenuNew();
					roleMenu.setMenuId(16l);
					roleMenu.setRoleId(role.getId());
					roleMenus.add(roleMenu);
					activeLine = roleMenuNewMapper.batchInsert(roleMenus);
					if(activeLine>0){
						result = true;
					}
				}else{
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public boolean update(RoleNew role) {
		boolean result=false;
		if(role==null){
			log.debug("update:role is invalid");
			return result;
		}
		try {
			role.setItia(UserContext.getcompanyItia());
			int activeLine = objMapper.update(role);
			if(activeLine>0){
				List<MenuNew> menus = role.getMenus();
				if(menus!=null&&menus.size()>0){
					//删除原有角色与菜单管理关系
					activeLine = roleMenuNewMapper.deleteByRoleId(role.getId());
					if(activeLine>0){
						//添加新关联关系
						List<RoleMenuNew> roleMenus = new ArrayList<RoleMenuNew>();
						for(int i=0;i<menus.size();i++){
							RoleMenuNew roleMenu = new RoleMenuNew();
							JSONObject json = JSONObject.fromObject(menus.get(i));
							MenuNew menu = (MenuNew) json.toBean(json, MenuNew.class);
							if(menu.getType()==1){
								roleMenu.setMenuId(menu.getId());
								roleMenu.setRoleId(role.getId());
								roleMenus.add(roleMenu);
							}
						}
						//添加数据默认菜单-id=16
						RoleMenuNew roleMenu = new RoleMenuNew();
						roleMenu.setMenuId(16l);
						roleMenu.setRoleId(role.getId());
						roleMenus.add(roleMenu);
						activeLine = roleMenuNewMapper.batchInsert(roleMenus);
						if(activeLine>0)
							result = true;
					}
				}else{
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public boolean delete(Long roleId) {
		boolean result = false;
		if(roleId==null||roleId.longValue()<=0){
			log.debug("delete:roleId is invalid");
			return result;
		}
		try {
			//先删除关联关系，再删除角色
			int activeLine = roleMenuNewMapper.deleteByRoleId(roleId);
			if(activeLine>0){
				activeLine = objMapper.delete(roleId);
				if(activeLine>0){
					//设置用户的默认角色为2-默认角色
					activeLine = employeeRoleMapper.updateEmployeeRoleByRoleId(roleId, 2l);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public List<RoleNew> selectRoles(Long createId, String name) {
		List<RoleNew> list = null;
		if(createId==null||createId.longValue()<=0){
			log.debug("selectRoles:createId is invalid");
			return list;
		}
		try {
			list = objMapper.selectRoles(createId, name);
			//获取默认角色
			RoleNew defaultRole = objMapper.load(2l);
//			List<MenuNew> menus = menuNewMapper.getAllMenu();//获取所有的菜单列表
			//获取当前用户角色
			Long roleId=employeeRoleMapper.load(UserContext.getcompanyItia(), createId);
			List<MenuNew> menus = roleMenuNewMapper.selectMenuList(roleId);
			if(list!=null&&list.size()>0){
				if(createId.longValue()!=1){
					list.add(defaultRole);
				}
				for(int i=0;i<list.size();i++){
					RoleNew role = list.get(i);
					List<MenuNew> menulist = roleMenuNewMapper.selectMenuList(role.getId());//根据关联关系查找当前角色具有的菜单列表
					if(menulist!=null&&menulist.size()>0){
						List<MenuNew> menuss = new ArrayList<MenuNew>();//
						for(int j=0;j<menus.size();j++){
							MenuNew menu = (MenuNew) menus.get(j).clone();
							if("默认".equals(menu.getName())){
								continue;
							}
							for(int k=0;k<menulist.size();k++){
								if(menu.getId().longValue()==menulist.get(k).getId().longValue()){
									menu.setType(1);
								}
							}
							menuss.add(menu);
						}
						role.setMenus(menuss);
					}
				}
			}else{
				list = new ArrayList<RoleNew>();
				list.add(defaultRole);
				for(int i=0;i<list.size();i++){
					RoleNew role = list.get(i);
					List<MenuNew> menulist = roleMenuNewMapper.selectMenuList(role.getId());//根据关联关系查找当前角色具有的菜单列表
					if(menulist!=null&&menulist.size()>0){
						List<MenuNew> menuss = new ArrayList<MenuNew>();//
						for(int j=0;j<menus.size();j++){
							MenuNew menu = (MenuNew) menus.get(j).clone();
							if("默认".equals(menu.getName())){
								continue;
							}
							for(int k=0;k<menulist.size();k++){
								if(menu.getId().longValue()==menulist.get(k).getId().longValue()){
									menu.setType(1);
								}
							}
							menuss.add(menu);
						}
						role.setMenus(menuss);
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
	
	public List<RoleNew> getRolesByRoleName(Long createId,String roleName){
		List<RoleNew> roles=null;
		if(createId==null||createId.longValue()==0||roleName==null||"".equals(roleName)){
			log.debug("getRolesByRoleName:the parameter createId or roleName is valid.");
			return roles;
		}
		try {
			roles = objMapper.selectRolesByRoleName(createId, roleName);
			if(roles==null){
				roles = new ArrayList<RoleNew>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return roles;
		}
		return roles;
	}
}
