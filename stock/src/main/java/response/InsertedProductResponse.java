package response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import dto.ProductDTO;

@XmlRootElement(name = "stockResponse")
public class InsertedProductResponse extends StockResponse {
	
	@XmlElement
	public ProductDTO product;

}
