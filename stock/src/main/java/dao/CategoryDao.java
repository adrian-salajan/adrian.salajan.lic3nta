package dao;

import java.util.Collection;

import model.Category;

public interface CategoryDao {
	
	public void insert(Category category);
	public void delete(Category category);
	public Category get(long id);
	public Category get(String name);
	public void update (Category category);
	public Collection<Category> getAll();

}
