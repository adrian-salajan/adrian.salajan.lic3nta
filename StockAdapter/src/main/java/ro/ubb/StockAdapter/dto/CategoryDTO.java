package ro.ubb.StockAdapter.dto;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
public class CategoryDTO extends LinkedService {
	
	@XmlElement(name = "id",required = false)
	public long id;
	@XmlElement(name = "name")
	public String name;
	
	public String getName() {
		return name;
	}
	
	public Long getId() {
		return id;
	}
	
	

}
