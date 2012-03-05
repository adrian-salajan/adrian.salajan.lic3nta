package web.mvc.model;

import java.util.List;

import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;

public class CategoryView {
	
	List<CategoryDTO> categories;
	Long selectedCategoryId;
	List<ProductOrdered> products;
	
	public List<CategoryDTO> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}
	public Long getSelectedCategoryId() {
		return selectedCategoryId;
	}
	public void setSelectedCategoryId(Long selectedCategoryId) {
		this.selectedCategoryId = selectedCategoryId;
	}
	public List<ProductOrdered> getProducts() {
		return products;
	}
	public void setProducts(List<ProductOrdered> products) {
		this.products = products;
	}
	
	
	
	
	

}
