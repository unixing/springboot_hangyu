package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.MenuNew;

/**
 * 账号对应的机场和权限mapper类
 * @Title:EmployerRoleMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-10 下午2:34:19
 */
public interface EmployerRoleMapper {
   /**
    * 根据账号ID得到账号对应的机场
    * @Title: getEmployerAirportList 
    * @Description:  
    * @param @param empId
    * @param @return    
    * @return List<String>     
    * @throws
    */
    public List<String> getEmployerAirportList(@Param("companyId") Long companyId);
    /**
     * 根据用户得到角色ID列表
     * @Title: getEmployerRole 
     * @Description:  
     * @param @param empId
     * @param @return    
     * @return List<String>     
     * @throws
     */
    public List<MenuNew> getEmployerRole(Long empId);
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