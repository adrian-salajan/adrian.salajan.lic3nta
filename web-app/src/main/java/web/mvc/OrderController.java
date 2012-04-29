package web.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import web.converter.Converter;
import web.integration.IntegrationConstants;
import web.integration.OfertaIntegrationService;
import web.mvc.model.HistoryOrder;

import domain.Oferta;
import domain.Product;

import adrian.comenziadapter.OrderGateway;

@Controller()
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	@Qualifier("orderGateway")
	OrderGateway ofertaService;
	
	@Autowired
	OfertaIntegrationService integrationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getOrders(Principal principal, ModelMap model) {
		Collection<Oferta> orders = ofertaService.getByClient(principal.getName());
		Collection<HistoryOrder> history = Converter.toHistoryOrders(orders);
		for(HistoryOrder ho : history) {
			ho.setUpdateStatus(integrationService.getStatus(ho.getOrder().getId()));
		}
		model.addAttribute("orders", history);
		return "orders/history";
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showOrder(@PathVariable("id") Long id, ModelMap model, 
			@RequestParam(value="us", required = false, defaultValue = " ") String updateStatus) {
		Oferta oferta = ofertaService.getById(id);
		HistoryOrder order = new HistoryOrder();
		order.setOrder(oferta);
		order.setUpdateStatus(integrationService.getStatus(id));
		if (!"n".equals(updateStatus)) {
			integrationService.updateStatus(id, IntegrationConstants.NOT_UPDATED);
		}
		model.addAttribute("order", order);
		return "orders/order";
	}
	
	
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String updateOrder(@PathVariable("id") Long id, @ModelAttribute("order") HistoryOrder order
			) {
		Oferta oferta = order.getOrder();
		for (domain.Product p : oferta.getItems() ) {
			ofertaService.updateQuantityProdus(id, p.getId(), p.getQuantity());
		}
		integrationService.updateStatus(id, IntegrationConstants.QTY_UPDATED);
		return "redirect:/orders/" + id + "?us=n";
	}
	
	
	@RequestMapping(value = "/{id}/finish", method = RequestMethod.GET)
	public String finishOrder(@PathVariable("id") Long id, ModelMap model) {
		ofertaService.oferteaza(id);
		model.put("orderId", id);
		return "orders/lastStep";
	}
	
	@RequestMapping(value = "/{id}/done")
	public String doneOrder(@PathVariable("id") Long id, 
			@RequestParam("address") String address) {
		ofertaService.comanda(id, address);
		return "redirect:/web-app/orders";
	}
	
	@RequestMapping(value = "/{id}/cancel")
	public String cancelOrder(@PathVariable("id") Long id){
		ofertaService.cancel(id);
		return "redirect:/web-app/orders";
	}

}
