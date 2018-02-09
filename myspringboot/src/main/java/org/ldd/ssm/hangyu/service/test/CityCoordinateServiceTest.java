package org.ldd.ssm.hangyu.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldd.ssm.hangyu.domain.CityCoordinate;
import org.ldd.ssm.hangyu.service.CityCoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CityCoordinateServiceTest {

	@Autowired
	private CityCoordinateService cityCoordinateService;
	
	@Test
	public void getCityCoordinateByAirIATA(){
		CityCoordinate cityCoordinate = cityCoordinateService.getCityCoordinateByAirIATA("PVG");
		System.out.println(cityCoordinate.getCityName()+" 三字码："+cityCoordinate.getCityIcao());
		cityCoordinate = cityCoordinateService.getCityCoordinateByAirIATA("SHA");
		System.out.println(cityCoordinate.getCityName()+" 三字码："+cityCoordinate.getCityIcao());
	}
	@Test
	public void getCityCoordinateByCityIcao(){
		CityCoordinate cityCoordinate = cityCoordinateService.getCityCoordinateByAirIATA("SHA");
		System.out.println(cityCoordinate.getCityName()+" 三字码："+cityCoordinate.getCityIcao());
	}
}
