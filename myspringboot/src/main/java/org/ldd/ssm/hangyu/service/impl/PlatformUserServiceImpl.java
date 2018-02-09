package org.ldd.ssm.hangyu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.PlatformUser;
import org.ldd.ssm.hangyu.domain.ValidCode;
import org.ldd.ssm.hangyu.mapper.PlatformUserMapper;
import org.ldd.ssm.hangyu.mapper.ValidCodeMapper;
import org.ldd.ssm.hangyu.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PlatformUserServiceImpl implements PlatformUserService {
	@Autowired
	private PlatformUserMapper platformUserMapper;
	@Autowired
	private ValidCodeMapper validCodeMapper;
	private Logger log = Logger.getLogger(PlatformUserServiceImpl.class);
	@Override
	public Map<String, Object> register(PlatformUser user) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(user==null){
			log.debug("register:user is an invalid parameter");
			map.put("opRestul", "3");
			return map;
		}
		user.setState("待审核");//默认待审核
		try {
			//验证手机验证码
			ValidCode validCode = validCodeMapper.selectValidCode(user.getTel());
			if(validCode==null){
				map.put("opResult", "1");
				return map;
			}else{
				//删除验证码
				int activeLines = validCodeMapper.deleteByPrimaryKey(validCode.getId());
				if(activeLines==0){
					map.put("opResult", "1");
					return map;//验证码删除失败
				}else{
					long currentTime = new Date().getTime();
					if(currentTime-validCode.getCreateTime()>10*60*1000){//3分钟过期
						map.put("opRestul", "4");//验证码过期
						return map;
					}else{
						if(!user.getValidCode().equals(validCode.getCode())){//验证码错误、不匹配
							map.put("opRestul", "5");//验证码不匹配
							return map;
						}
					}
					int affectLines = platformUserMapper.insertSelective(user);
					if(affectLines>0){
						map.put("opResult", "0");
					}else{
						map.put("opResult", "1");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("There are errors in code");
			map.put("opResult", "2");
		}
		return map;
	}
}
