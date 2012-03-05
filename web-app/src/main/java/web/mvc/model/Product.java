package web.mvc.model;

import java.util.List;

import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.dto.ServiceLink;



public class Product {
	
	private ProductDTO productDTO = new ProductDTO();

	public long getId() {
		return productDTO.id;
	}

	public void setId(long id) {
		productDTO.id = id;
	}

	public String getName() {
		return productDTO.name;
	}

	public void setName(String name) {
		productDTO.name = name;
	}

	public long getPrice() {
		return productDTO.price;
	}

	public void setPrice(long price) {
		productDTO.price = price;
	}

	public int getStock() {
		return productDTO.stock;
	}

	public void setStock(int stock) {
		productDTO.stock = stock;
	}

	public String getDescription() {
		return productDTO.description;
	}

	public void setDescription(String description) {
		productDTO.description = description;
	}

	public long getCategoryId() {
		return productDTO.categoryId;
	}

	public void setCategoryId(long categoryId) {
		productDTO.categoryId = categoryId;
	}
	
	public ProductDTO getDto() {
		return productDTO;
	}
	
	public List<ServiceLink> getLinks() {
		return (List<ServiceLink>) productDTO.links;
	}
	
	public void setLinks(List<ServiceLink> links) {
		productDTO.links = links;
	}
	
	
	public void setDto(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return this.getId() == other.getId();
	}
	
	@Override
	public int hashCode() {
		return (int)(this.getId()^(this.getId()>>>32));
	}

	
	
	
	
	
	
	
}
