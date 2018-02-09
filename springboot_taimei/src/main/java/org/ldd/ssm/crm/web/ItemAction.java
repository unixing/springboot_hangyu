package org.ldd.ssm.crm.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ItemAction {
	@RequestMapping("/item")
	public String getOutPort() {
		return "newHtml/item/item";
	}
}
