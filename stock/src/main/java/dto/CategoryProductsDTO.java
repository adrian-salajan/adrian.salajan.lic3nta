package dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
public class CategoryProductsDTO extends LinkedService {
	
	@XmlElement
	public long id;
	
	@XmlElement
	public String name;
	
	@XmlElementWrapper(name = "products", nillable = false, required = false)
	@XmlElementRefs({ @XmlElementRef(type = ProductDTO.class) })
	public Collection<ProductDTO> products = new ArrayList<ProductDTO>();

}
