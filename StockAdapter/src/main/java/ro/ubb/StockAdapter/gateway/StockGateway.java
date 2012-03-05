package ro.ubb.StockAdapter.gateway;

import java.util.Collection;



import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.dto.CategoryProductsDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;

public interface StockGateway {
	

	public Collection<CategoryDTO> getCategories() throws StockGatewayException;
	public void createCategory(CategoryDTO newCategory) throws StockGatewayException;
	public void deleteCategory(CategoryDTO category) throws StockGatewayException;
	public void updateCategory(CategoryDTO category) throws StockGatewayException;
	
	public CategoryProductsDTO getCategoryProducts(Long id) throws StockGatewayException;
	public CategoryProductsDTO getCategoryProducts(CategoryDTO category) throws StockGatewayException ;
	public Collection<ProductDTO> getProductsInCategory(CategoryDTO category) throws StockGatewayException ;
	
	public void insertProduct(CategoryDTO category, ProductDTO product) throws StockGatewayException ;
	public void deleteProduct(ProductDTO product) throws StockGatewayException;
	public void updateProduct(ProductDTO product) throws StockGatewayException;
	 
	

	
	
	
	
	

}
