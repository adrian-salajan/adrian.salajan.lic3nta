package web.mvc;

import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.gateway.StockGateway;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;

import domain.Oferta;
import domain.Product;

import adrian.comenziadapter.OrderGateway;

import web.converter.Converter;
import web.entity.Region;
import web.entity.Userrr;
import web.integration.IntegrationConstants;
import web.integration.OfertaIntegrationService;
import web.integration.ProductIntegration;
import web.integration.ProductIntegrationService;
import web.mvc.model.Bascket;
import web.mvc.model.HistoryOrder;
import web.reports.ReportService;
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
	StockGateway stock;
	
	@Autowired
	OfertaIntegrationService integrationService;
	@Autowired
	ProductIntegrationService pintegrationService;
	
	@Autowired
	Bascket bascket;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getSalesForRegion(Principal principal, ModelMap model) {
		
		Userrr user = userDao.getUser(principal.getName());
		Collection<HistoryOrder> oferteRegionale = new ArrayList<HistoryOrder>();
		for (Region r : user.getRegions()) {
			oferteRegionale.addAll(Converter.toHistoryOrders(ofertaService.getByRegion(r.getName())));
		}
		for(HistoryOrder ho : oferteRegionale) {
			ho.setUpdateStatus(integrationService.getStatus(ho.getOrder().getId()));
		}
		model.put("orders", oferteRegionale);
		
		return "sales/orders";
		
	}
	

	@RequestMapping(value= "/ordersNegotiating", method = RequestMethod.GET)
	public String getSalesNegotiating(Principal principal, ModelMap model) {
		
		Userrr user = userDao.getUser(principal.getName());
		Collection<HistoryOrder> oferteRegionale = new ArrayList<HistoryOrder>();
		for (Region r : user.getRegions()) {
			oferteRegionale.addAll(Converter.toHistoryOrders(ofertaService.getByRegion(r.getName())));
		}
		for(HistoryOrder ho : oferteRegionale) {
			ho.setUpdateStatus(integrationService.getStatus(ho.getOrder().getId()));
		}
		
		List<HistoryOrder> filterNegotiating = this.filterNegotiating(oferteRegionale);
		model.put("orders", filterNegotiating);
		
		return "sales/orders";
		
	}
	
	@RequestMapping(value= "/ordersReady", method = RequestMethod.GET)
	public String getSalesReady(Principal principal, ModelMap model) {
		
		Userrr user = userDao.getUser(principal.getName());
		Collection<HistoryOrder> oferteRegionale = new ArrayList<HistoryOrder>();
		for (Region r : user.getRegions()) {
			oferteRegionale.addAll(Converter.toHistoryOrders(ofertaService.getByRegion(r.getName())));
		}
		
		List<HistoryOrder> filterReady = this.filterReady(oferteRegionale);
		model.put("orders", filterReady);
		
		return "sales/orders";
		
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showSale(Principal principal, @PathVariable("id") Long id, ModelMap model,
			@RequestParam(value="upd", required = false, defaultValue = " ") String updateStatus,  HttpServletRequest request) {
		Oferta oferta = ofertaService.getById(id);
		HistoryOrder order = new HistoryOrder();
		order.setOrder(oferta);
		order.setUpdateStatus(integrationService.getStatus(id));
		if (!"n".equals(updateStatus)) {
			integrationService.updateStatus(id, IntegrationConstants.NOT_UPDATED);
		}
		if (userDao.getUser(principal.getName()).getRolee().equals("STOCK")) {
			if (integrationService.getLock(id) == false ) {
				integrationService.setLock(id, true);
				request.getSession().setAttribute("lockId", id);
			}
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
		return "redirect:/sales/" + id +  "?upd=n";
	}
	
	@RequestMapping(value = "/{id}/finish", method = RequestMethod.GET)
	public String finishOrder(@PathVariable("id") Long id, ModelMap model) {
		ofertaService.oferteaza(id);
		model.put("orderId", id);
		return "redirect:/sales";
	}
	
	@RequestMapping(value = "/{id}/process", method = RequestMethod.GET)
	public ModelAndView processOrder(@PathVariable("id") Long id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Oferta processed = null;
		try {
			Long sessionId = (Long) request.getSession().getAttribute("lockId");
			if ( integrationService.getLock(id)) {
				if (sessionId != null && id.longValue() == sessionId) {}
				else {
					View rv = new RedirectView("/web-app/product/ordersn", false);
					mv.setView(rv);
					return mv;
				}
			}
			processed = ofertaService.getById(id);
			for (domain.Product p : processed.getItems()) {
				ProductIntegration pi = pintegrationService.getStockId(p.getId());
				ProductDTO product = stock.getProduct(pi.getStockId(), pi.getCategoryId());
				updateProductStock(product, p);
				bascket.getCategoryProducts().remove(pi.getCategoryId());
			}
		
			ofertaService.proceseazaComanda(id);
			
		} catch (StockGatewayException e) {
			e.printStackTrace();
			//go back to new orders
			integrationService.setLock(id, false);
			View rv = new RedirectView("/web-app/product/ordersn", false);
			mv.setView(rv);
			return mv;
		}
		//go to PDF
		ReportService reportsService = new ReportService();
		Map model = reportsService.getOfertaParameters(processed);
		return new ModelAndView("pdfComanda", model); 
	}
	
	@RequestMapping(value = "/{id}/processed", method = RequestMethod.GET)
	public String processedOrder(@PathVariable("id") Long id) {
		ofertaService.finalizeazaComanda(id);
		return "redirect:/product/orderspe";
	}

	private void updateProductStock(ProductDTO product, Product p) throws StockGatewayException {
		if (product.stock >= p.getQuantity()) {
			product.stock = product.stock - p.getQuantity();
		} else {
			product.stock = 0;
		}
		stock.updateProduct(product);
		
	}
	
	private List<HistoryOrder> filterNegotiating(
			Collection<HistoryOrder> oferteRegionale) {
		List<HistoryOrder> list = new ArrayList<HistoryOrder>(oferteRegionale.size());
		for (HistoryOrder o : oferteRegionale) {
			if (o.getStatus().equals("inprogress")) {
				list.add(o);
			}
		}
		return list;
	}
	
	private List<HistoryOrder> filterReady(
			Collection<HistoryOrder> oferteRegionale) {
		List<HistoryOrder> list = new ArrayList<HistoryOrder>(oferteRegionale.size());
		for (HistoryOrder o : oferteRegionale) {
			if (o.getStatus().equals("ready")) {
				list.add(o);
			}
		}
		return list;
	}
	
	private List<HistoryOrder> filterOrdered(
			Collection<HistoryOrder> oferteRegionale) {
		List<HistoryOrder> list = new ArrayList<HistoryOrder>(oferteRegionale.size());
		for (HistoryOrder o : oferteRegionale) {
			if (o.getStatus().equals("unprocessed")) {
				list.add(o);
			}
		}
		return list;
	}


}
