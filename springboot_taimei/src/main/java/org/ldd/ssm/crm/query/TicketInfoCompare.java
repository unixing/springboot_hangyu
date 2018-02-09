package org.ldd.ssm.crm.query;


import org.ldd.ssm.crm.domain.TicketInfo;

/**
 * 票面对象
 * @author wxm
 *
 * @date 2017-5-18
 */
public class TicketInfoCompare {
	private TicketInfo TicketInfoImport;
	private TicketInfo TicketInfoSystem;
	public TicketInfo getTicketInfoImport() {
		return TicketInfoImport;
	}
	public void setTicketInfoImport(TicketInfo ticketInfoImport) {
		TicketInfoImport = ticketInfoImport;
	}
	public TicketInfo getTicketInfoSystem() {
		return TicketInfoSystem;
	}
	public void setTicketInfoSystem(TicketInfo ticketInfoSystem) {
		TicketInfoSystem = ticketInfoSystem;
	}
	public TicketInfoCompare(TicketInfo ticketInfoImport,
			TicketInfo ticketInfoSystem) {
		super();
		TicketInfoImport = ticketInfoImport;
		TicketInfoSystem = ticketInfoSystem;
	}
	public TicketInfoCompare() {
		super();
	}
}