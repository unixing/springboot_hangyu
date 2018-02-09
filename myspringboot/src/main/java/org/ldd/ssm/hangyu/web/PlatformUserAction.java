package org.ldd.ssm.hangyu.web;

import java.util.Map;

import org.ldd.ssm.hangyu.domain.PlatformUser;
import org.ldd.ssm.hangyu.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlatformUserAction {
	@Autowired
	private PlatformUserService userService;
	@RequestMapping("/register")
	@ResponseBody
	public Map<String,Object> register(PlatformUser user){
		return userService.register(user);
	}
}
