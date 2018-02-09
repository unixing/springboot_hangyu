package org.ldd.ssm.hangyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("org.ldd.ssm.hangyu.mapper")
@EnableCaching
public class MyspringbootApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MyspringbootApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MyspringbootApplication.class, args);
	}
}
