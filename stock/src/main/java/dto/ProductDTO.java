package dto;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
public class ProductDTO extends LinkedService {
	
	@XmlElement
	public long id;
	
	@XmlElement
	public String name;
	
	@XmlElement
	public long price;
	
	@XmlElement
	public int stock;
	
	@XmlElement
	public String description;
	
	@XmlElement
	public long categoryId;
	
	@XmlElementWrapper(name = "properties", required = false, nillable = false)
	@XmlElementRefs(value = {@XmlElementRef(type = PropertyDTO.class)})
	public Collection<PropertyDTO> properties;

}
