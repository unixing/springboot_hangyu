package org.ldd.ssm.crm.service;

import org.ldd.ssm.crm.domain.EmailValidStr;

public interface IEmailValidStrService {
	public boolean addOrUpdate(EmailValidStr emailValidStr);
	public boolean delete(int id);
	public EmailValidStr load(Long employeeId);
}
