package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.EmployeeRole;
import org.ldd.ssm.crm.domain.RoleNew;
import org.ldd.ssm.crm.mapper.EmployeeMapper;
import org.ldd.ssm.crm.mapper.EmployeeRoleMapper;
import org.ldd.ssm.crm.mapper.RoleNewMapper;
import org.ldd.ssm.crm.service.IEmployeeRoleService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmployeeRoleServiceImpl implements IEmployeeRoleService {
	@Autowired
	private EmployeeRoleMapper objMapper;
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private RoleNewMapper roleNewMapper;
	Logger log = Logger.getLogger(EmployeeRoleServiceImpl.class);
	@Override
	public List<Employee> getCurrAirportEmployees(Long createId,String name) {
		List<Employee> list = null;
		if(createId==null||createId.longValue()<=0){
			log.debug("getCurrAirportEmployees:createId is invalid");
			return list;
		}
		try {
			list = objMapper.getCurrAirportEmployees(createId);
			if(list!=null&&list.size()>0){
				List<RoleNew> roles = roleNewMapper.selectRoles(createId,name);//当前登录用户创建角色列表
				if(roles!=null&&roles.size()>0){
					//默认角色
					if(createId.longValue()!=1){
						//获取默认角色
						RoleNew defaultRole = roleNewMapper.load(2l);
						roles.add(defaultRole);
					}
				}else{
					//默认角色
					if(createId.longValue()!=1){
						roles = new ArrayList<RoleNew>();
						//获取默认角色
						RoleNew defaultRole = roleNewMapper.load(2l);
						roles.add(defaultRole);
					}
				}
				for(int i =0;i<list.size();i++){
					Employee obj = list.get(i);
					List<RoleNew> rs = new ArrayList<RoleNew>();
					for(int j=0;j<roles.size();j++){
						RoleNew role = (RoleNew) roles.get(j).clone();
						if(role.getId().longValue()==obj.getRoleId().longValue()){
							role.setType(1);
						}
						rs.add(role);
					}
					obj.setRoles(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return null;
		}
		return list;
	}
	@Override
	public boolean add(Employee emp) {
		boolean result = false;
		if(emp==null){
			log.debug("add:emp is invalid");
			return result;
		}
		try {
			Employee user=UserContext.getUser();
			//为成员设置默认参数
			emp.setUsrSts(1);
			//设置创建者
			emp.setCreateId(user.getId());
			//设置登录状态
			emp.setFirstLogin(0);
			emp.setDepartmentId(user.getDepartmentId());
			//先条件成员，在添加成员与角色关系
			int activeLine = employeeMapper.save(emp);
			if(activeLine>0){
				EmployeeRole empRole = new EmployeeRole();
				empRole.setEmployeeId(emp.getId());
				empRole.setItia(UserContext.getcompanyItia());
				empRole.setRoleId(emp.getRoleId());
				activeLine = objMapper.insertSelective(empRole);
				if(activeLine>0){
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
	public boolean update(List<Employee> emps) {
		boolean result = false;
		if(emps==null){
			log.debug("add:emp is invalid");
			return result;
		}
		try {
			if(emps.size()>0){
				for(Employee emp:emps){
					//修改成员，在修改关联关系
					int activeLine = employeeMapper.update(emp);
					if(activeLine>0){
						EmployeeRole empRole = new EmployeeRole();
						empRole.setRoleId(emp.getRoleId());
						empRole.setEmployeeId(emp.getId());
						empRole.setItia(UserContext.getcompanyItia());
						activeLine = objMapper.update(empRole);
						if(activeLine>0){
							result = true;
						}
					}
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
	public boolean delete(EmployeeRole empRole) {
		boolean result = false;
		if(empRole==null){
			log.debug("delete:empRole is invalid");
			return result;
		}
		try {
			//先删除成员与角色间关系，再删除成员
			int activeLine = objMapper.delete(empRole);
			if(activeLine>0){
				activeLine = employeeMapper.delete(empRole.getEmployeeId());
				if(activeLine>0){
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
	public Long load(String itia, Long employeeId) {
		Long roleId = null;
		if(itia==null||"".equals(itia)||employeeId==null||employeeId.longValue()<=0){
			log.debug("load:itia or employeeId is invalid");
			return roleId;
		}
		try {
			roleId = objMapper.load(itia, employeeId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return roleId;
		}
		return roleId;
	}
}
