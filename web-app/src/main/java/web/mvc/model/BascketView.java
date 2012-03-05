package web.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class BascketView {
	
	List<ProductOrdered> products;

	public List<ProductOrdered> getProducts() {
		if (products == null) {
			products = new ArrayList<ProductOrdered>();
		}
		return products;
	}

	public void setProducts(List<ProductOrdered> products) {
		this.products = products;
	}
	
	

}
