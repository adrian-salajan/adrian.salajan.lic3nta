package service;

import java.util.Collection;

import model.Category;
import model.Product;

public interface StockService {
	
	public void addCategory(Category category);
	
	public Collection<Category> getAllCategories();
	public Collection<Product> getProducsInCategory(String categoryName, int numberOfResults, int page);
	
	public void addProductInCategory(String categoryName, Product product);
	public void removeProduct(Product product);
	public void removeProduct(long productId);
	
	public void updateProduct(Product product);
	
	

}
