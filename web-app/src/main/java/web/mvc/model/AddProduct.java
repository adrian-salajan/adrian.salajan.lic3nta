package web.mvc.model;

import java.util.Collection;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ro.ubb.StockAdapter.dto.CategoryDTO;


public class AddProduct {
	
	private Collection<CategoryDTO> categories;
	
	@NotNull
	@DecimalMin(value="0")
	private Long categoryId;
	
	@NotNull
	@Size(min = 3, max = 25)
	public String name;
	
	@NotNull
	@DecimalMin("1")
	public long price;
	
	@NotNull
	@Min(0)
	public int stock;
	
	public String description;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(Collection<CategoryDTO> categories) {
		this.categories = categories;
	}
	
	
	
	
	
	
	
	

}
