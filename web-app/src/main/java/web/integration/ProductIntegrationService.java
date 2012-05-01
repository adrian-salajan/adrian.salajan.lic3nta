package web.integration;

import java.util.Collection;
import java.util.List;

import web.mvc.model.ProductOrdered;
import domain.Product;

public interface ProductIntegrationService {
	
	public ProductIntegration getStockId(Long orderedProductId);
	public void mapProducts(List<ProductOrdered> products,
			Collection<Product> items);

}
