package web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ro.ubb.StockAdapter.dto.CategoryProductsDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.dto.PropertyDTO;
import ro.ubb.StockAdapter.gateway.StockGateway;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;

import web.mvc.model.Bascket;
import web.mvc.model.Product;
import web.mvc.model.ProductOrdered;

@RequestMapping("/product")
@Controller
public class ProductController {
	
	@Autowired
	StockGateway stockGateway;
	
	@Autowired
	Bascket bascket;
	
	
	@RequestMapping
	public String get(@RequestParam("id") Long productId,
			@ModelAttribute("productOrdered") ProductOrdered po) {
		
		ProductOrdered p = findProduct(bascket,productId);
		po.setQty(p.getQty());
		po.setProduct(p.getProduct());
		
		return "product/details";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("productOrdered") ProductOrdered po) {
		try {
			CategoryProductsDTO catProds = stockGateway.getCategoryProducts(po.getProduct().getCategoryId());
			ProductDTO found = null;
			for(ProductDTO p : catProds.products) {
				if (p.id == po.getProduct().getId()) {
					found = p;
					break;
				}
			}
			ProductDTO update = po.getProduct().getDto();
			update.links = found.links;
			if (update.properties ==null) {
				update.properties = new ArrayList<PropertyDTO>();
			}
			stockGateway.updateProduct(update);
		} catch (StockGatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bascket.getCategoryProducts().remove(po.getProduct().getCategoryId());
		return "redirect:/index?" + "categoryId=" + po.getProduct().getCategoryId() ;
	}

	private ProductOrdered findProduct(Bascket bascket, Long productId) {
		for (List<ProductOrdered> list: bascket.getCategoryProducts().values()) {
			for (ProductOrdered po : list) {
				if (po.getProduct().getId() == productId.longValue())
					return po;
			}
		}
		return null;
	}
	
}
