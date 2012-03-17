package web.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import domain.Oferta;


import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.dto.PropertyDTO;

import web.mvc.model.AddProduct;
import web.mvc.model.Bascket;
import web.mvc.model.CategoryAdd;
import web.mvc.model.Product;
import web.mvc.model.ProductOrdered;
import web.mvc.model.ShipmentPreferencesForm;

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

	public static ProductDTO toProductDTO(AddProduct product) {
		ProductDTO dto = new ProductDTO();
		dto.name = product.getName();
		dto.price = product.getPrice();
		dto.stock = product.getStock();
		dto.description = product.getDescription();
		dto.properties = new ArrayList<PropertyDTO>();
		return dto;
	}
	
	public static Oferta createOferta(Bascket bascket, ShipmentPreferencesForm form) {
		Oferta o = new Oferta();
		for (domain.Product p: Converter.toDomainProducts(bascket.getProducts())) {
			o.addProduct(p);
		}
		return o;
	}

	private static List<domain.Product> toDomainProducts(List<ProductOrdered> products) {
		List<domain.Product> domainProducts = new ArrayList<domain.Product>();
		for (ProductOrdered po : products) {
			domainProducts.add(Converter.toDomainProduct(po));
		}
		return domainProducts;
	}

	private static domain.Product toDomainProduct(ProductOrdered po) {
		domain.Product dp = new domain.Product();
		dp.setName(po.getProduct().getName());
		dp.setPrice(po.getProduct().getPrice());
		dp.setQuantity(po.getQty());
		dp.setDetails(po.getProduct().getDescription());
		return dp;
	}
	

}
