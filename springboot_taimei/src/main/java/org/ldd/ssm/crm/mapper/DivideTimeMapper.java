package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.DivideTime;

public interface DivideTimeMapper {
public int add(DivideTime time);
public List<DivideTime> list(@Param("fltDirect") String fltDirect, @Param("seasonId") int seasonId);
public int deleteBySeasonId(@Param("fltDirect") String fltDirect, @Param("seasonId") int seasonId);
}
