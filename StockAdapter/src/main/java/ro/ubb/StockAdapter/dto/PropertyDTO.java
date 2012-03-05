package ro.ubb.StockAdapter.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "property")
public class PropertyDTO {
	
	@XmlElement(name = "id")
	public long id;
	
	@XmlElement(name = "name")
	public String name;
	
	@XmlElement(name = "value")
	public String value;

}
