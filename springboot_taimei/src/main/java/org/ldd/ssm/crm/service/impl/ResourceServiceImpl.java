package org.ldd.ssm.crm.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.Datasupplier;
import org.ldd.ssm.crm.domain.DatasupplierRole;
import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.mapper.DatasupplierMapper;
import org.ldd.ssm.crm.mapper.DatasupplierRoleMapper;
import org.ldd.ssm.crm.mapper.ResourceMapper;
import org.ldd.ssm.crm.mapper.RoleResourceMapper;
import org.ldd.ssm.crm.service.IResourceService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ResourceServiceImpl implements IResourceService{
	private static String pkgPath = "org.ldd.ssm.crm.web";
	private static Map<String, List<String>> modules = new HashMap<String,List<String>>();
	@Autowired
	private DatasupplierMapper datasupplierMapper;
	@Autowired
	private DatasupplierRoleMapper datasupplierRoleMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	private ResourceMapper dao;
	
	@Autowired
	public void setDao(ResourceMapper dao) {
		this.dao = dao;
	}
	public void save(Resource emp) {
		dao.save(emp);
	}
	public void delete(Long id) {
		dao.delete(id);
	}
	public void update(Resource emp) {
		dao.update(emp);
	}
	public Resource get(Long id) {
		return dao.get(id);
	}
	public List<Resource> list() {
		return dao.list();
	}
	@SuppressWarnings("null")
	public Resource checkLogin(String username, String password) {
		List<Resource> list = dao.getResourceByUsername(username);
		Resource Resource = null;
		if(list != null || list.size() > 0 || list.get(0) != null){
			Resource = list.get(0);
		}
		return Resource;
	}
	public List<Resource> getResourceList(Long employeeId, Long datSreCpyId,Long roleId) {
		List<Resource> list = new ArrayList<Resource>();
		try {
			if(employeeId==null){
				list = dao.selectAll();
			}else{
				Datasupplier datasupplier = datasupplierMapper.load(employeeId,datSreCpyId);
				if(datasupplier!=null){
					List<DatasupplierRole> dataRoleList = datasupplierRoleMapper.selectAll(datasupplier.getId());
					if(dataRoleList!=null && dataRoleList.size()>0){
						for(DatasupplierRole dataRole:dataRoleList){
							List<Resource> resourceList = roleResourceMapper.selectByRole(dataRole.getRoleId());
							if(resourceList!=null && resourceList.size()>0){
								list.addAll(resourceList);
							}
						}
					}
				}
			}
			List<Resource> roleResuorces = roleResourceMapper.selectByRole(roleId);
			if(roleResuorces!=null&&roleResuorces.size()>0&&list.size()>0){
				for(int i=0;i<list.size();i++){
					for(int j=0;j<roleResuorces.size();j++){
						if(list.get(i).getId().longValue()==roleResuorces.get(j).getId().longValue()){
							list.get(i).setType(1);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
		// 判断map集合中是否有传入的包路径,如果没有,就继续下去
	public void scanResource() throws IOException {
		if (modules.get(pkgPath) == null) {
			// 创建一个集合
			List<String> clzNames = new ArrayList<String>();
			/*
			 * 把包下的模块缓存
			 */
			// 将传入的字符串里面的标点用新的符号替换掉cn/sirius/base/web/action
			String packageDirName = pkgPath.replace(".", "/");
			// 当前线程下的上下文 ClassLoader 加载器,获得给定路径下的所有的类文件。
			Enumeration<URL> dirs = Thread.currentThread()
					.getContextClassLoader().getResources(packageDirName);
			//定义初始参数
			MethodNote methodNote = null;
			String mNote = null;
			String methodUrl = null;
			String qName =null;
			Method[] methods = null;
			Class<?> forName = null;
			// 判断元素中是否包含更多的元素
			while (dirs.hasMoreElements()) {
				// 判断是否还有下一个元素,如果有则返回下一个元素
				URL url = dirs.nextElement();
				// 通过文件路径创建一个文件对象
				File file = new File(url.getFile());
				// 把此目录下的所有文件列出
				String[] classes = file.list();
				// 循环此数组，并把.class去掉
				for (String className : classes) {
					if(className.contains(".class")){
						className = className.substring(0, className.length() - 6);
						// 拼接上包名，变成全限定名
						qName = pkgPath + "." + className;
						try {
							forName = Class.forName(qName);
//							classNote = forName.getAnnotation(ClassNote.class);
//							if(classNote!=null){
//								cNote = classNote.comment();
								methods = forName.getMethods();
								for (Method method : methods) {
									methodNote = method.getAnnotation(MethodNote.class);
									methodUrl = qName+":"+method.getName();
									if(methodNote!=null){
										mNote = methodNote.comment();
										String[] mm=mNote.split(":");
										if(mm.length==2){
											Resource resource = new Resource();
											resource.setName(mm[0]);
											resource.setMenuId(Long.valueOf(mm[1]));
											resource.setUrl(methodUrl);
											dao.save(resource);
										}
									}
								}
//							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			// 调用map,使用包名为key,装有打上注释标签的类的全限定名的list集合put进去
			modules.put(pkgPath, clzNames);
		}
	}
	
	public List<Resource> getCurrentEmployeeResource(Long companyId,Long employeeId){
		List<Resource> list= dao.selectAll();
		List<Resource> currentEmployeeResource = new ArrayList<Resource>();
		if(employeeId==null){
			currentEmployeeResource = dao.selectAll();
		}else{
			Datasupplier datasupplier = datasupplierMapper.load(employeeId,companyId);
			if(datasupplier!=null){
				List<DatasupplierRole> dataRoleList = datasupplierRoleMapper.selectAll(datasupplier.getId());
				if(dataRoleList!=null && dataRoleList.size()>0){
					for(DatasupplierRole dataRole:dataRoleList){
						List<Resource> resourceList = roleResourceMapper.selectByRole(dataRole.getRoleId());
						if(resourceList!=null && resourceList.size()>0){
							currentEmployeeResource.addAll(resourceList);
						}
					}
				}
			}
		}
		if(list!=null&&list.size()>0&&currentEmployeeResource!=null&&currentEmployeeResource.size()>0){
			for(int i=0;i<list.size();i++){
				for(int j=0;j<currentEmployeeResource.size();j++){
					if(list.get(i).getId().equals(currentEmployeeResource.get(j).getId())){
						list.get(i).setType(1);
					}
				}
			}
		}
		return list;
	}
	
	public boolean checkPermission(String resourceUrl,List<Resource> resources) {
		boolean result = false;
		Resource resource = dao.load(resourceUrl);
		if(resource==null){
			return true;
		}
		if(resources!=null&&resources.size()>0){
			for(int i=0;i<resources.size();i++){
				if(resources.get(i).getUrl().equals(resourceUrl)&&resources.get(i).getType()==1){
					return true;
				}
			}
		}
		return result;
	}
}
