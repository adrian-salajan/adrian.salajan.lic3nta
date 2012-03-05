package ro.ubb.StockAdapter.dto;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "service")
public class ServiceLink {
	@XmlElement(name = "name")
	public String name;

	@XmlElement(name = "method")
	public String method;
	
	@XmlElement(name = "uri")
	public URI uri;
	

	
	

}
