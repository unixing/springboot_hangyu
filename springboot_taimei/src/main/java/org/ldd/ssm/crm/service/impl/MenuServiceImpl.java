package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.Datasupplier;
import org.ldd.ssm.crm.domain.DatasupplierRole;
import org.ldd.ssm.crm.domain.Menu;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.domain.Role;
import org.ldd.ssm.crm.mapper.DatasupplierMapper;
import org.ldd.ssm.crm.mapper.DatasupplierRoleMapper;
import org.ldd.ssm.crm.mapper.EmployerRoleMapper;
import org.ldd.ssm.crm.mapper.MenuMapper;
import org.ldd.ssm.crm.mapper.RoleMapper;
import org.ldd.ssm.crm.mapper.RoleMenuMapper;
import org.ldd.ssm.crm.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * service层的实现类
 */
@Service
public class MenuServiceImpl implements IMenuService{
	//spring注入dao对象
	private MenuMapper dao;
	@Autowired
	private DatasupplierMapper datasupplierMapper;
	@Autowired
	private DatasupplierRoleMapper datasupplierRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private EmployerRoleMapper employerRoleMapper;
	@Autowired
	public void setDao(MenuMapper dao) {
		this.dao = dao;
		//自动建表
	}
	/**
	 * 添加一个新的用户
	 */
	public void save(Menu pro) {
		dao.save(pro);
	}
	/**
	 * 根据id删除用户
	 */
	public void delete(Long id) {
		dao.delete(id);
	}
	/**
	 * 更新用户
	 */
	public void update(Menu pro) {
		dao.update(pro);
	}
	/**
	 * 根据id查询对象
	 */
	public Menu get(Long id) {
		return dao.get(id);
	}
	/**
	 * 查询所有的数据
	 */
	public List<Menu> getAll() {
		return dao.getAll();
	}
	/**
	 * 查询父菜单
	 */
	public List<Menu> getParent(Long companyId,Long employeeId) {
		List<Menu> list = new ArrayList<Menu>();
		if(employeeId==null){
			list = dao.getParent();
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					list.get(i).setChiledren(getChilder(list.get(i).getId()));
				}
			}
		}else{
			Datasupplier datasupplier = datasupplierMapper.load(employeeId, companyId);
			List<Menu> totalList = new ArrayList<Menu>();
			List<Menu> parentMenu = new ArrayList<Menu>();
			List<Menu> childMenu = new ArrayList<Menu>();
			if(datasupplier!=null){
				List<DatasupplierRole> datasupplierRoles = datasupplierRoleMapper.selectAll(datasupplier.getId());
				if(datasupplierRoles != null && datasupplierRoles.size()>0){
					for(int i =0;i<datasupplierRoles.size();i++){
						Role role = roleMapper.load(datasupplierRoles.get(i).getRoleId());
						if(role!=null){
							List<Menu> roleMenuList = dao.selectMenuByRole(role.getId());
							if(roleMenuList!=null&&roleMenuList.size()>0){
								totalList.addAll(roleMenuList);
							}
						}
					}
					if(totalList.size()>0){
						for(int i =0;i<totalList.size();i++){
							if(totalList.get(i).getParentId()==null){
								parentMenu.add(totalList.get(i));
							}else{
								childMenu.add(totalList.get(i));
							}
						}
						if(parentMenu.size()==0||childMenu.size()==0){
							list.addAll(parentMenu);
							list.addAll(childMenu);
						}else{
							for(int i=0;i<parentMenu.size();i++){
								List<Menu> objList = new ArrayList<Menu>();
								for(int j=0;j<childMenu.size();j++){
									if(parentMenu.get(i).getId()==childMenu.get(j).getParentId()){
										objList.add(childMenu.get(j));
									}
								}
								parentMenu.get(i).setChiledren(objList);
							}
							list.addAll(parentMenu);
						}
					}
				}
			}
		}
		return list;
	}
	/**
	 * 查询子菜单
	 */
	public List<Menu> getChilder(Long id) {
		List<Menu> childList = dao.getChilder(id);
		if(childList!=null&&childList.size()>0){
			for(int i=0;i<childList.size();i++){
				childList.get(i).setChiledren(getChilder(childList.get(i).getId()));
			}
		}
		return dao.getChilder(id);
	}
	public List<Menu> getList(Long roleId) {
		List<Menu> list = new ArrayList<Menu>();
		List<Menu> totalList = new ArrayList<Menu>();
		totalList = dao.selectMenuByRole(roleId);
		if(totalList!=null&&totalList.size()>0){
			List<Menu> parentList = new ArrayList<Menu>();
			List<Menu> childList = new ArrayList<Menu>();
			for(int i=0;i<totalList.size();i++){
				if(totalList.get(i).getParentId()==null){
					parentList.add(totalList.get(i));
				}else{
					childList.add(totalList.get(i));
				}
			}
			if(parentList.size()==0||parentList.size()==0){
				list.addAll(parentList);
				list.addAll(childList);
			}else{
				for(int i=0;i<parentList.size();i++){
					List<Menu> objList = new ArrayList<Menu>();
					for(int j=0;j<childList.size();j++){
						if(parentList.get(i).getId()==childList.get(j).getParentId()){
							objList.add(childList.get(j));
						}
					}
					parentList.get(i).setChiledren(objList);
				}
				list.addAll(parentList);
			}
		}
		return list;
	}
	public List<Menu> showList(Long companyId, Long employeeId, Long roleId) {
		List<Menu> currUserMenuList = getParent(companyId, employeeId);
		List<Menu> roleMenuList = dao.selectMenuByRole(roleId);
		for(int i=0;i<currUserMenuList.size();i++){
			Menu menu = currUserMenuList.get(i);
			List<Menu> chiredren = menu.getChiledren();
			for(int j=0;j<roleMenuList.size();j++){
				if(currUserMenuList.get(i).getId()==roleMenuList.get(j).getId()){
					currUserMenuList.get(i).setType(1);
				}
			}
			if(chiredren!=null&&chiredren.size()>0){
				for(int k=0;k<chiredren.size();k++){
					for(int m=0;m<roleMenuList.size();m++){
						if(chiredren.get(k).getId()==roleMenuList.get(m).getId()){
							chiredren.get(k).setType(1);
						}
					}
				}
			}
		}
		return currUserMenuList;
	}

	public List<Menu> getCurrentUserAllMenu(Long companyId,Long employeeId){
		List<Menu> list = new ArrayList<Menu>();
		Datasupplier datasupplier = datasupplierMapper.load(employeeId, companyId);
		if(datasupplier!=null){
			List<DatasupplierRole> datasupplierRoles = datasupplierRoleMapper.selectAll(datasupplier.getId());
			if(datasupplierRoles != null && datasupplierRoles.size()>0){
				for(int i =0;i<datasupplierRoles.size();i++){
					Role role = roleMapper.load(datasupplierRoles.get(i).getRoleId());
					if(role!=null){
						List<Menu> roleMenuList = dao.selectMenuByRole(role.getId());
						if(roleMenuList!=null&&roleMenuList.size()>0){
							list.addAll(roleMenuList);
						}
					}
				}
			}
		}
		return list;
	}
	
	public List<Menu> getCurrentEmployeeMenu(Long companyId,Long employeeId){
		List<Menu> list = dao.getParent();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				list.get(i).setChiledren(getChilder(list.get(i).getId()));
			}
		}
		if(employeeId!=null){
			List<Menu> currUserMenuList = getCurrentUserAllMenu(companyId, employeeId);
			for(int i=0;i<list.size();i++){
				Menu menu = list.get(i);
				List<Menu> chiredren = menu.getChiledren();
				for(int j=0;j<currUserMenuList.size();j++){
					if(menu.getId().equals(currUserMenuList.get(j).getId())){
						menu.setType(1);
					}
				}
				if(chiredren!=null&&chiredren.size()>0){
					for(int k=0;k<chiredren.size();k++){
						for(int m=0;m<currUserMenuList.size();m++){
							if(chiredren.get(k).getId()==currUserMenuList.get(m).getId()){
								chiredren.get(k).setType(1);
							}
						}
					}
				}
			}
		}else{
			for(int i=0;i<list.size();i++){
				list.get(i).setType(1);
				List<Menu> childList = list.get(i).getChiledren();
				if(childList!=null&&childList.size()>0){
					for(int j=0;j<childList.size();j++){
						childList.get(j).setType(1);
					}
				}
			}
		}
		return list;
	}
	@Override
	public List<String> getUserAirportList(Long companyId) {
		return employerRoleMapper.getEmployerAirportList(companyId);
	}
	
	@Override
	public List<MenuNew> getMenuList(Long employeeId) {
		return employerRoleMapper.getEmployerRole(employeeId);
	}
	@Override
	public List<MenuNew> getMenuAllList() {
		return employerRoleMapper.getMenuAllList();
	}
	
}
