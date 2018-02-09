package org.ldd.ssm.hangyu.mapper;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Employee;

public interface LoginMapper {
	Employee checkLogin(@Param("name") String name, @Param("password") String password);
}
