package web.mvc.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

public class BascketView {
	
	@Valid
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
	
	public Integer getTotalSum() {
		int total = 0;
			for (ProductOrdered po : getProducts()) {
				total += po.getQty() * po.getProduct().getPrice();
			}
		return total;
	}
	
	

}
