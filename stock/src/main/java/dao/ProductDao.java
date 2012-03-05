package dao;

import java.util.Collection;

import model.Product;

public interface ProductDao {
	
	public void insert(Product product);
	public void update(Product product);
	public void delete(Product product);
	public Product getById(long id);
	
	public Collection<Product> getByName(String name,  int results, int page);

}
