package web.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.dto.CategoryProductsDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.gateway.StockGateway;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;
import web.converter.Converter;
import web.mvc.model.Bascket;
import web.mvc.model.CategoryView;
import web.mvc.model.OrderView;
import web.mvc.model.ProductOrdered;

@RequestMapping("/index")
@Controller
public class StockController {
	
	@Autowired
	StockGateway stockGateway;
	
	@Autowired
	Bascket bascket;

    @RequestMapping(method = {RequestMethod.GET} )
    public String index(@ModelAttribute("categoryView") CategoryView view, Map model,
    		@RequestParam(value = "categoryId", defaultValue = "-1") long categoryId) {
    	try {
    		if (categoryId != -1) {
    			view.setSelectedCategoryId(categoryId);
    		}
    		populateUI(view, bascket);
		} catch (StockGatewayException e) {
			e.printStackTrace();
		}
        return "stock/index";
    }
   
    
    
    private void validateView(CategoryView view, Map model) {
    	int index = 0;
		for (ProductOrdered po : view.getProducts()) {
			if (!po.isQuantityValid()) {
				model.put("products[" + index +"].quantityValid.errors", "The ordered quantity exeeds the available stock.");
				index++;
			}
		}
		
	}



	private void populateBasket(Bascket bascket,Long categoryId, List<ProductOrdered> productToOrdered) {
		 if ( ! bascket.getCategoryProducts().containsKey(categoryId)) {
			 bascket.getCategoryProducts().put(categoryId, productToOrdered);
		 }
	}
    
    
    private void populateUI(CategoryView view, Bascket bascket) throws StockGatewayException {
    	Long selectedCategoryId = view.getSelectedCategoryId();
    	
    	if (selectedCategoryId == null)
    		selectedCategoryId = 1L;
    	
    	Collection<CategoryDTO> categories = stockGateway.getCategories();
    	CategoryProductsDTO categoryProducts = stockGateway.getCategoryProducts(selectedCategoryId);
		List<ProductOrdered> productToOrdered = Converter.productToOrdered(categoryProducts.products);
		
		populateBasket(bascket, selectedCategoryId, productToOrdered);
		
		view.setCategories((List<CategoryDTO>) categories);
		view.setSelectedCategoryId(selectedCategoryId);
		view.setProducts(bascket.getCategoryProducts().get(selectedCategoryId));
    }

    
	@RequestMapping(method = RequestMethod.POST)
    public String updateBascket(@ModelAttribute("categoryView") @Valid CategoryView view, 
    		BindingResult binding, Map model, WebRequest request) {
    		try {
    			if (bascket.getCategoryProducts().containsKey(view.getSelectedCategoryId())) {
    				bascket.getCategoryProducts().put(view.getSelectedCategoryId(), view.getProducts());
    			}
    			populateUI(view, bascket);
    			
    			if (binding.hasErrors()) {
    				model.put("categoryView", view);
    				return "stock/index";
    			}
    		} catch (StockGatewayException e) {
				e.printStackTrace();
			}
    		 return "stock/index";
       
    }
	
	
	@ModelAttribute("bascket")
	public Bascket initBascket() {
		return new Bascket();
	}
	
	@RequestMapping("/rolerd")
	public String redirectForRole(Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		List<String> roles = new ArrayList<String>();
		
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		
		
		if (roles.contains("ROLE_SALES"))
				return "redirect:/sales";
		
		if (roles.contains("ROLE_STOCK"))
			return "redirect:/index";
		
		if (auth.getAuthorities().contains("ROLE_CLIENT"))
				return "redirect:/index";
		
		return "redirect:/index";
	}

}
