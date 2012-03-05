package ro.ubb.StockAdapter;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.dto.CategoryProductsDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.dto.PropertyDTO;
import ro.ubb.StockAdapter.gateway.StockGateway;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;
import ro.ubb.StockAdapter.gateway.impl.StockGatewayJersey;

public class TestStockGateway {
	
	StockGateway stock = new StockGatewayJersey();
	
	@Test
	public void testCreateCategory() {
		CategoryDTO cat = new CategoryDTO();
		cat.name = "testCategory";
		try {
			stock.createCategory(cat);
			assertTrue(cat.id > 0);
			assertTrue(cat.name.equals("testCategory"));
			assertTrue(cat.links.size() > 0);
		
		
			cat.name = cat.name + "Updated";
			stock.updateCategory(cat);
			assertTrue(cat.id > 0);
			assertTrue(cat.name.equals("testCategoryUpdated"));
			assertTrue(cat.links.size() > 0);
			
			ProductDTO p = new ProductDTO();
			p.name = "testProduct";
			p.description = "testDescription";
			p.properties = new ArrayList<PropertyDTO>();
			p.stock = 5;
			p.price = 555;
			
			PropertyDTO prop = new PropertyDTO();
			prop.name ="testProperty";
			prop.value = "testValue";
		
			
			p.properties.add(prop);
			
			
			stock.insertProduct(cat, p);
			
			assertTrue(p.id > 0);
		
			assertTrue(p.name.equals("testProduct"));
			assertTrue(p.properties.size() == 1);
			assertTrue(p.properties.toArray(new PropertyDTO[] {})[0].value.equals("testValue"));
			assertTrue(p.categoryId == cat.id);
			
			p.price = 333;
			
			stock.updateProduct(p);
			
			assertTrue(p.price == 333);
			
			stock.deleteProduct(p);
			CategoryProductsDTO categoryProducts = stock.getCategoryProducts(cat);
			assertTrue(categoryProducts.products.size() == 0);
			
		} catch (StockGatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
