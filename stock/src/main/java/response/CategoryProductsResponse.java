package response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import dto.CategoryProductsDTO;

@XmlRootElement(name = "stockResponse")
public class CategoryProductsResponse extends StockResponse {
	
	@XmlElement(name="category")
	public CategoryProductsDTO categoryProducts;

}
