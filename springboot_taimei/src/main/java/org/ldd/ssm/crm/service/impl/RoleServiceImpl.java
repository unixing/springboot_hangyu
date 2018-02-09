package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Datasupplier;
import org.ldd.ssm.crm.domain.DatasupplierRole;
import org.ldd.ssm.crm.domain.Menu;
import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.domain.Role;
import org.ldd.ssm.crm.domain.RoleMenu;
import org.ldd.ssm.crm.domain.RoleResource;
import org.ldd.ssm.crm.mapper.DatasupplierMapper;
import org.ldd.ssm.crm.mapper.DatasupplierRoleMapper;
import org.ldd.ssm.crm.mapper.RoleMapper;
import org.ldd.ssm.crm.mapper.RoleMenuMapper;
import org.ldd.ssm.crm.mapper.RoleResourceMapper;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.service.IMenuService;
import org.ldd.ssm.crm.service.IResourceService;
import org.ldd.ssm.crm.service.IRoleService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private DatasupplierMapper datasupplierMapper;
	@Autowired
	private DatasupplierRoleMapper datasupplierRoleMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private ICompanyService companyService;
	public boolean add(Role role) {
		int activeLines = roleMapper.insertSelective(role);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean delete(Long roleId) {
		//清空角色关联菜单和角色关联资源
		roleMenuMapper.deleteByRoleId(roleId);
		roleResourceMapper.deleteByRole(roleId);
		int activeLines = roleMapper.delete(roleId);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean update(Role role) {
		int activeLines = roleMapper.update(role);
		boolean result = false;
		if(activeLines >0){
			result = true;
		}
		return result;
	}

	public Role load(Long roleId) {
		return roleMapper.load(roleId);
	}

	public List<Role> selectRoles(Long employeeId, Long datSreCpyId,String name) {
		return roleMapper.selectRoles(employeeId, datSreCpyId,name);
	}

	public List<Role> getList(Long companyId, Long employeeId,Long ralateId) {
		List<Role> roles = null;
		if(employeeId!=null){
			Datasupplier datasupplier = datasupplierMapper.load(employeeId, companyId);
			if(datasupplier!=null){
				List<DatasupplierRole> datasupplierRoles = datasupplierRoleMapper.selectAll(datasupplier.getId());
				if(datasupplierRoles!=null&&datasupplierRoles.size()>0){
					for(DatasupplierRole data:datasupplierRoles){
						Role role = roleMapper.load(data.getRoleId());
						roles = new ArrayList<Role>();
						if(role!=null){
							roles.add(role);
						}
					}
				}
			}
		}else{
			roles = roleMapper.selectRoles(null, companyId, null);
		}
		List<DatasupplierRole> roleList = datasupplierRoleMapper.selectAll(ralateId);
		if(roleList!=null&&roleList.size()>0){
			for(int i=0;i<roles.size();i++){
				for(int j=0;j<roleList.size();j++){
					if(roles.get(i).getId()==roleList.get(j).getRoleId()){
						roles.get(i).setType(1);
					}
				}
			}
		}
		return roles;
	}

	public boolean savePermission(String[] menuIds, String[] resourceIds,
			Long roleId) {
		boolean result = false;
		//清空角色关联菜单和角色关联资源
		roleMenuMapper.deleteByRoleId(roleId);
		roleResourceMapper.deleteByRole(roleId);
		if(menuIds!=null&&menuIds.length>0){
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			for(int i=0;i<menuIds.length;i++){
				roleMenu.setMenuId(Long.valueOf(menuIds[i]));
				int activeLine = roleMenuMapper.insertSelective(roleMenu);
				if(activeLine==0){
					return result;
				}
			}
		}
		if(resourceIds!=null&&resourceIds.length>0){
			RoleResource roleResource = new RoleResource();
			roleResource.setRoleId(roleId);
			for(int i=0;i<resourceIds.length;i++){
				roleResource.setResourceId(Long.valueOf(resourceIds[i]));
				int activeLine = roleResourceMapper.insertSelective(roleResource);
				if(activeLine==0){
					return result;
				}
			}
			result = true;
		}
		return result;
	}

	public Map<String,Object> getCurrentPermission(Long companyId, Long employeeId) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Company company = companyService.load(companyId);
			List<Menu> menuList = menuService.getCurrentEmployeeMenu(companyId, employeeId);
			List<Resource> resourceList = resourceService.getCurrentEmployeeResource(companyId, employeeId);
			UserContext.setMenuList(menuList);
			UserContext.setResourceList(resourceList);
			UserContext.setCompany(company);
			UserContext.setCompanyName(company.getCpyAds());
			UserContext.setCompanyId(company.getId()+"");
			List<Menu> currentMenu = new ArrayList<Menu>();
			if(menuList!=null&&menuList.size()>0){
				for(int i=0;i<menuList.size();i++){
					Menu menu = menuList.get(i);
					if(menu.getType()==1){
						if(menu.getChiledren()!=null&&menu.getChiledren().size()>0){
							List<Menu> child  = menu.getChiledren();
							List<Menu> childs = new ArrayList<Menu>();
							for(int j=0;j<child.size();j++){
								if(child.get(j).getType()==1){
									childs.add(child.get(j));
								}
							}
							menu.setChiledren(childs);
						}
						currentMenu.add(menu);
					}
				}
				
			}
			map.put("menuList", currentMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
