package org.ldd.ssm.crm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketPriceTrendAction {
	@RequestMapping("/ticketPriceTrend")
	public String ticketPriceTrend(){
		return "ticketPriceTrend/ticketPriceTrend";
	}
}
