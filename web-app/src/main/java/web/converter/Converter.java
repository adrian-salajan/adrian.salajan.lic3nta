package web.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.dto.PropertyDTO;

import web.mvc.model.CategoryAdd;
import web.mvc.model.Product;
import web.mvc.model.ProductOrdered;

public class Converter {
	
	public static ProductOrdered productToOrdered(ProductDTO product) {
		ProductOrdered po = new ProductOrdered();
		Product p = new Product();
		p.setDto(product);
		po.setProduct(p);
		return po;
	}
	
	public static List<ProductOrdered> productToOrdered(Collection<ProductDTO> products) {
		List<ProductOrdered> pos = new ArrayList<ProductOrdered>(products.size());
		for (ProductDTO p : products) {
			pos.add(Converter.productToOrdered(p));
		}
		return pos;
	}
	
	public static CategoryDTO toCategory(CategoryAdd category) {
		CategoryDTO categ = new CategoryDTO();
		categ.name = category.getName();
		return categ;
	}
	

}
