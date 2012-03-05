package web.mvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bascket {
	Map<Long, List<ProductOrdered>> categoryProducts = new HashMap<Long, List<ProductOrdered>>();

	public Map<Long, List<ProductOrdered>> getCategoryProducts() {
		return categoryProducts;
	}
	
	public List<ProductOrdered> getProducts() {
		List<ProductOrdered> products = new ArrayList<ProductOrdered>();
		for(List<ProductOrdered> pList : categoryProducts.values()) {
			for (ProductOrdered po : pList) {
				if (po.getQty() > 0) {
					products.add(po);
				}
			}
		}
		return products;
	}
	
	
	
	
	
	

}
