package utils;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;


import dto.LinkedServiceBuilder;

import model.Category;
import model.Product;
import model.Property;
import dto.CategoryDTO;
import dto.CategoryProductsDTO;
import dto.ProductDTO;
import dto.PropertyDTO;
import dto.ServiceLink;
import dto.UriBuilderUtil;

public class Converter {
	
	public static CategoryDTO toDTO(Category category, UriInfo uriInfo) {
		CategoryDTO dto = new CategoryDTO();
		dto.id = category.getId();
		dto.name = category.getName();
		Collection<ServiceLink> ls = new LinkedServiceBuilder()
			.add("GET", "GET", UriBuilderUtil.getCategory(uriInfo, dto.id))
			.add("INSERT", "PUT", UriBuilderUtil.insertCategory(uriInfo))
			.add("UPDATE", "POST", UriBuilderUtil.updateCategory(uriInfo, dto.id))
			.add("DELETE", "DELETE", UriBuilderUtil.deleteCategory(uriInfo, dto.id))
			.add("INSERT-IN", "PUT", UriBuilderUtil.insertProductForCategory(uriInfo.getBaseUriBuilder(), dto.id))
			.build();
		dto.links = ls;
		return dto;
	}
	
	public static Collection<CategoryDTO> toDTO(Collection<Category> categories, UriInfo uriInfo) {
		Collection<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
		for(Category category : categories) {
			dtos.add(Converter.toDTO(category, uriInfo));
		}
		return dtos;
	}

//	public static Collection<ProductDTO> toProductsDTO(Collection<Product> products, UriInfo uriInfo) {
//		Collection<ProductDTO> productsDTO = new ArrayList<>();
//		for(Product p : products) {
//			productsDTO.add(Converter.toDTO(p, uriInfo.getRequestUriBuilder()));
//		}
//		return productsDTO;
//	}

	public static ProductDTO toDTO(Product product, long catId, UriInfo uriInfo) {
		ProductDTO dto = new ProductDTO();
		dto.id = product.getId();
		dto.name = product.getName();
		dto.price = product.getPrice();
		dto.stock = product.getStock();
		dto.categoryId = catId;
		dto.description = product.getDescription();
		
		Collection<ServiceLink> ls = new LinkedServiceBuilder()
		.add("GET", "GET", UriBuilderUtil.getProductForCategory(uriInfo.getBaseUriBuilder(), catId, dto.id))
		.add("INSERT", "PUT", UriBuilderUtil.insertProductForCategory(uriInfo.getBaseUriBuilder(), catId))
		.add("UPDATE", "POST", UriBuilderUtil.updateProductForCategory(uriInfo.getBaseUriBuilder(),catId, dto.id))
		.add("DELETE", "DELETE", UriBuilderUtil.deleteProductForCategory(uriInfo.getBaseUriBuilder(),catId, dto.id))
		.add("UP", "GET", UriBuilderUtil.getCategory(uriInfo,catId))
		.build();
		dto.links = ls;
		
		dto.properties = new ArrayList<PropertyDTO>();
		for (Property prop : product.getProperties()) {
			dto.properties.add(Converter.toDTO(prop));
		}
		return dto;
	}
	
	public static PropertyDTO toDTO(Property property) {
		PropertyDTO dto = new  PropertyDTO();
		dto.id = property.getId();
		dto.name = property.getName();
		dto.value = property.getValue();
		return dto;
	}
	
	public static Product toProduct(ProductDTO product) {
		Product p = new Product();
		p.setId(product.id);
		p.setName(product.name);
		p.setPrice(product.price);
		p.setStock(product.stock);
		p.setDescription(product.description);
		p.setProperties(Converter.toProperties(product.properties));
		return p;
		
	}
	
	public static Collection<Property> toProperties(
			Collection<PropertyDTO> properties) {
		Collection<Property> propertiez = new ArrayList<Property>();
		for (PropertyDTO p : properties) {
			propertiez.add(Converter.toProperty(p));
		}
		
		return propertiez;
	}

	public static Property toProperty(PropertyDTO property) {
		Property p = new Property();
		p.setId(property.id);
		p.setName(property.name);
		p.setValue(property.value);
		return p;
	}

	public static Category toCategory(CategoryDTO categoryDTO) {
		Category c = new Category();
		c.setId(categoryDTO.id);
		c.setName(categoryDTO.name);
		return c;
	}

	public static CategoryProductsDTO toCategoryProductsDTO(Category category,
			UriInfo uriInfo) {
		CategoryProductsDTO dto = new CategoryProductsDTO();
		dto.id = category.getId();
		dto.name = category.getName();
		Collection<ServiceLink> ls = new LinkedServiceBuilder()
		.add("GET", "GET", UriBuilderUtil.getCategory(uriInfo, dto.id))
		.add("INSERT", "PUT", UriBuilderUtil.insertCategory(uriInfo))
		.add("UPDATE", "POST", UriBuilderUtil.updateCategory(uriInfo, dto.id))
		.add("DELETE", "DELETE", UriBuilderUtil.deleteCategory(uriInfo, dto.id))
		.add("INSERT-IN", "PUT", UriBuilderUtil.insertProductForCategory(uriInfo.getBaseUriBuilder(), dto.id))
		.add("UP", "GET", uriInfo.getBaseUriBuilder().path("categories").build())
		.build();
		dto.links = ls;
		for (Product p : category.getProducts()) {
			dto.products.add(Converter.toDTO(p, dto.id, uriInfo));
		}
		return dto;
	}


	
	

}
