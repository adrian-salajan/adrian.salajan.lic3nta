package web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import web.mvc.model.Bascket;
import web.mvc.model.BascketView;
import web.mvc.model.ProductOrdered;

@RequestMapping("/bascket")
@Controller
public class BascketController {
	
	@Autowired
	Bascket bascket;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showBascket(@ModelAttribute("view") BascketView view) {
		populateUI(bascket, view);
		return "bascket/show";
	}

	private void populateUI(Bascket bascket, BascketView view) {
		view.setProducts(bascket.getProducts());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String update(@ModelAttribute("view") BascketView view) {
		updateBascket(bascket, view);
		populateUI(bascket, view);
		return "bascket/show";
	}

	private void updateBascket(Bascket bascket, BascketView view) {
		for(ProductOrdered updatedPO : view.getProducts()) {
			ProductOrdered old = bascket.getProducts().get(bascket.getProducts().indexOf(updatedPO));
			old.setQty(updatedPO.getQty());
		}
		
	}

}
