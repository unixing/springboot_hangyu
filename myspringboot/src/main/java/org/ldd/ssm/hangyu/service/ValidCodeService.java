package org.ldd.ssm.hangyu.service;

import org.ldd.ssm.hangyu.domain.ValidCode;


public interface ValidCodeService {
	public boolean add(ValidCode validCode);
	public boolean delete(int id);
	public ValidCode load(String contactWay);
	public boolean update(ValidCode validCode);
}
