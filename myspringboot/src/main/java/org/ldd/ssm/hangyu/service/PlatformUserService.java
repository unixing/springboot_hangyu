package org.ldd.ssm.hangyu.service;

import java.util.Map;

import org.ldd.ssm.hangyu.domain.PlatformUser;

public interface PlatformUserService {

	public Map<String,Object> register(PlatformUser user);
}
