package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Menu;
import org.ldd.ssm.crm.domain.MenuNew;
import org.springframework.stereotype.Service;
/**
 * 用户service层的接口类
 */
@Service
public interface IMenuService {
	/**
	 * 添加新商品
	 */
	void save(Menu pro);
	/**
	 * 根据id删除商品
	 */
	void delete(Long id);
	/**
	 * 更新商品
	 */
	void update(Menu pro);
	/**
	 * 根据id查询商品信息
	 */
	Menu get(Long id);
	/**
	 * 查询所有的商品
	 */
	List<Menu> getAll();
	/**
	 * 查询父菜单
	 */
	List<Menu> getParent(Long companyId, Long employeeId);
	/**
	 * 查询子菜单
	 * @param id:父菜单的id
	 */
	List<Menu> getChilder(Long id);
	//通过角色查询菜单
	List<Menu> getList(Long roleId);
	List<Menu> showList(Long companyId, Long employeeId, Long roleId);
//	List<Menu> getChilder();
	public List<Menu> getCurrentEmployeeMenu(Long companyId, Long employeeId);
	/**
	 * 根据用户获取有多少个机场
	 * @Title: getUserAirportList 
	 * @Description:  
	 * @param @param employeeId
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getUserAirportList(Long employeeId);
	/**
	 * 根据用户ID得到菜单
	 * @Title: getMenuList 
	 * @Description:  
	 * @param @param employeeId
	 * @param @return    
	 * @return List<Menu>     
	 * @throws
	 */
	public List<MenuNew> getMenuList(Long employeeId);
	/**
	 * 得到所有菜单
	 * @Title: getMenuAllList 
	 * @Description:  
	 * @param @return    
	 * @return List<Menu>     
	 * @throws
	 */
	public List<MenuNew> getMenuAllList();
	
}
