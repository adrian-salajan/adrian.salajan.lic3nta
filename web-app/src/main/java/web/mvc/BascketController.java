package web.mvc;

import java.security.Principal;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import adrian.comenziadapter.OrderGateway;

import domain.Oferta;
import domain.OfertaService;



import web.converter.Converter;
import web.integration.IntegrationConstants;
import web.integration.OfertaIntegrationService;
import web.integration.ProductIntegrationService;
import web.mvc.model.Bascket;
import web.mvc.model.BascketView;
import web.mvc.model.ProductOrdered;
import web.mvc.model.ShipmentPreferencesForm;

@RequestMapping("/bascket")
@Controller
public class BascketController {
	
	@Autowired
	Bascket bascket;
	
	
	@Autowired
	@Qualifier("orderGateway")
	OrderGateway ofertaService;
	
	@Autowired
	OfertaIntegrationService integrationService;
	
	@Autowired
	ProductIntegrationService pintegrationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showBascket(@ModelAttribute("view") BascketView view) {
		populateUI(bascket, view);
		return "bascket/show";
	}

	private void populateUI(Bascket bascket, BascketView view) {
		view.setProducts(bascket.getProducts());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String update(@ModelAttribute("view") @Valid BascketView view,
			BindingResult binding, Map model) {
		if (binding.hasErrors()) {
			model.put("view", view);
			return "bascket/show";
		}
		updateBascket(bascket, view);
		populateUI(bascket, view);
		return "bascket/show";
	}

	private void updateBascket(Bascket bascket, BascketView view) {
		for(ProductOrdered updatedPO : view.getProducts()) {
			ProductOrdered old = bascket.getProducts().get(bascket.getProducts().indexOf(updatedPO));
			old.setQty(updatedPO.getQty());
		}
		bascket.setTotalSum(view.getTotalSum());
	}
	
	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	public String goToStep2(@ModelAttribute("view") @Valid BascketView view,
			BindingResult binding, Map model) {
		if (binding.hasErrors()) {
			model.put("view", view);
			return "bascket/show";
		}
		updateBascket(bascket, view);
		
		initStep2(bascket, model);
		
		return "bascket.step2";
	}

	private void initStep2(Bascket bascket, Map model) {
		int total = bascket.getTotalSum();
		ShipmentPreferencesForm form = new ShipmentPreferencesForm();
		if (total > 500) {
			form.setAllowNegociation(true);
		}
		form.getRegions().add("Cluj");
		form.getRegions().add("Timisoara");
		form.getRegions().add("Bucuresti");
		
		model.put("shipmentPreferencesForm", form);
	}
	
	@RequestMapping(value = "/step3", method = RequestMethod.POST)
	public String confirmOrder(@ModelAttribute("shipmentPreferencesForm")
			 ShipmentPreferencesForm form, Principal principal) {
		
		Oferta newOferta = Converter.createOferta(bascket,form);
		Oferta added = ofertaService.add(newOferta, principal.getName(), form.getRegion(), form.getIsNegociated());
		pintegrationService.mapProducts(bascket.getProducts(), added.getItems());
		if (!form.getIsNegociated()) {
			ofertaService.comanda(added.getId(), form.getAddress());
		} 
		String status = form.getIsNegociated() == true ? IntegrationConstants.QTY_UPDATED 
				: IntegrationConstants.NOT_UPDATED;
		integrationService.create(added.getId(),status);
		
		bascket.getCategoryProducts().clear();
		return "redirect:/index";
	}

	

}
