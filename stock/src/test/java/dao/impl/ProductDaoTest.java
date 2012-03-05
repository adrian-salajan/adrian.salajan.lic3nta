package dao.impl;


import model.Category;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import dao.CategoryDao;
import dao.ProductDao;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springContext.xml"})
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class ProductDaoTest   {
	
	@Autowired()
	@Qualifier(value = "productDao")
	ProductDao dao;
	
	@Autowired()
	@Qualifier(value = "categoryDao")
	CategoryDao cdao;

	@Test
	public void testInsert() {
		Category category = new Category();
		category.setName("electronice");
		
		cdao.insert(category);
		assertTrue(category.getId() > 0);
		
		

	}

	@Test
	public void testUpdate() {
		
//		Category category = new Category();
//		category.setName("fructe");
//		
//		cdao.insert(category);
//		
//		category = cdao.get("fructe");
//		Product mar = new Product();
//		mar.setName("banana");
//		mar.setPrice(10);
////		System.out.println("MAAARRR ID:" + mar.getId());
//		
//		Property prop = new Property();
//		prop.setName("culoare");
//		prop.setValue("rosu");
//		mar.getProperties().add(prop);
//		category.addProduct(mar);
//		
//		cdao.update(category);
	}

	public void testDelete() {
	}
	
	@Test
	public void testGet() {
//		Product p = (Product) dao.getByName("banana").toArray()[0];
//		p.getProperties().clear();
//		dao.update(p);
		
	}

}
