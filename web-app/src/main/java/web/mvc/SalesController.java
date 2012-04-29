package web.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Oferta;

import adrian.comenziadapter.OrderGateway;

import web.converter.Converter;
import web.entity.Userrr;
import web.integration.IntegrationConstants;
import web.integration.OfertaIntegrationService;
import web.mvc.model.HistoryOrder;
import web.security.SecurityUtils;

@Controller
@RequestMapping("/sales")
public class SalesController {
	
	@Autowired
	SecurityUtils userDao;
	
	@Autowired
	@Qualifier("orderGateway")
	OrderGateway ofertaService;
	
	@Autowired
	OfertaIntegrationService integrationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getSalesForRegion(Principal principal, ModelMap model) {
		
		Userrr user = userDao.getUser(principal.getName());
		Collection<HistoryOrder> oferteRegionale = new ArrayList<HistoryOrder>();
		for (String r : user.getRegions()) {
			oferteRegionale.addAll(Converter.toHistoryOrders(ofertaService.getByRegion(r)));
		}
		for(HistoryOrder ho : oferteRegionale) {
			ho.setUpdateStatus(integrationService.getStatus(ho.getOrder().getId()));
		}
		model.put("orders", oferteRegionale);
		
		return "sales/orders";
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showSale(@PathVariable("id") Long id, ModelMap model,
			@RequestParam(value="us", required = false, defaultValue = " ") String updateStatus) {
		Oferta oferta = ofertaService.getById(id);
		HistoryOrder order = new HistoryOrder();
		order.setOrder(oferta);
		order.setUpdateStatus(integrationService.getStatus(id));
		if (!"n".equals(updateStatus)) {
			integrationService.updateStatus(id, IntegrationConstants.NOT_UPDATED);
		}
		model.addAttribute("order", order);
		return "sales/order";
	}
	
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String updateSale(@PathVariable("id") Long id, @ModelAttribute("order") HistoryOrder order
			) {
		Oferta oferta = order.getOrder();
		for (domain.Product p : oferta.getItems() ) {
			ofertaService.updatePriceProdus(id, p.getId(), p.getFinalPrice());
			
		}
		integrationService.updateStatus(id, IntegrationConstants.PRICE_UPDATED);
		return "redirect:/sales/" + id +  "?us=n";
	}
	
	@RequestMapping(value = "/{id}/finish", method = RequestMethod.GET)
	public String finishOrder(@PathVariable("id") Long id, ModelMap model) {
		ofertaService.oferteaza(id);
		model.put("orderId", id);
		return "sales/orders";
	}
	
	

}
