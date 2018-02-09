package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.PlaneTeam;

public interface PlaneTeamService {
	public boolean add(PlaneTeam info);
	public boolean update(PlaneTeam info);
	public boolean batchdel(int[] ids);
	public List<PlaneTeam> search(String teamName);
	public PlaneTeam load(int id);
}
