package web.mvc;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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
    public String index(@ModelAttribute("categoryView") CategoryView view,
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
    public String updateBascket(@ModelAttribute("categoryView") CategoryView view) {
    		try {
    			if (bascket.getCategoryProducts().containsKey(view.getSelectedCategoryId())) {
    				bascket.getCategoryProducts().put(view.getSelectedCategoryId(), view.getProducts());
    			}
    			populateUI(view, bascket);
    		} catch (StockGatewayException e) {
				e.printStackTrace();
			}
        return "stock/index";
    }
	
	
	@ModelAttribute("bascket")
	public Bascket initBascket() {
		return new Bascket();
	}

}
