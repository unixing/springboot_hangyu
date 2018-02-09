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

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.ResourceNew;
import org.ldd.ssm.crm.mapper.ResourceNewMapper;
import org.ldd.ssm.crm.service.IResourceNewService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
@Service
public class ResourceNewServiceImpl implements IResourceNewService {
	private static String pkgPathNew = "org.ldd.ssm.crm.web";
	private static Map<String, List<String>> modulesNew = new HashMap<String,List<String>>();
	@Autowired
	private ResourceNewMapper objMapper;
	Logger log = Logger.getLogger(ResourceNewServiceImpl.class);
	@Override
	public List<String> getAllResourceUrls() {
		List<String> list = null;
		try {
			list=objMapper.getAllResourceUrls();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return list;
		}
		return list;
	}
	public void scanResource() throws IOException {
		if (modulesNew.get(pkgPathNew) == null) {
			// 创建一个集合
			List<String> clzNames = new ArrayList<String>();
			/*
			 * 把包下的模块缓存
			 */
			// 将传入的字符串里面的标点用新的符号替换掉cn/sirius/base/web/action
			String packageDirName = pkgPathNew.replace(".", "/");
			// 当前线程下的上下文 ClassLoader 加载器,获得给定路径下的所有的类文件。
			Enumeration<URL> dirs = Thread.currentThread()
					.getContextClassLoader().getResources(packageDirName);
			//定义初始参数
			MethodNote methodNote = null;
			String mNote = null;
			RequestMapping methodUrl = null;
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
						qName = pkgPathNew + "." + className;
						try {
							forName = Class.forName(qName);
//							classNote = forName.getAnnotation(ClassNote.class);
//							if(classNote!=null){
//								cNote = classNote.comment();
								methods = forName.getMethods();
								for (Method method : methods) {
									methodNote = method.getAnnotation(MethodNote.class);
									methodUrl = method.getAnnotation(RequestMapping.class);
									if(methodNote!=null){
										mNote = methodNote.comment();
										String[] mm=mNote.split(";");
										if(mm.length>1){
											for(int i=0;i<mm.length;i++){
												String[] mms = mm[i].split(":");
												if(mms.length==2){
													ResourceNew resource = new ResourceNew();
													resource.setName(mms[0]);
													resource.setMenuId(Long.valueOf(mms[1]));
													resource.setUrl(methodUrl.value()[0]);
													objMapper.save(resource);
												}
											}
										}else if(mm.length==1){
											String[] mms = mm[0].split(":");
											if(mms.length==2){
												ResourceNew resource = new ResourceNew();
												resource.setName(mms[0]);
												resource.setMenuId(Long.valueOf(mms[1]));
												resource.setUrl(methodUrl.value()[0]);
												objMapper.save(resource);
											}
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
			modulesNew.put(pkgPathNew, clzNames);
		}
	}
}
