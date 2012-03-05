package dto;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "service")
public class ServiceLink {
	@XmlElement(name = "name")
	public String name;

	@XmlElement(name = "method")
	public String method;
	
	private URI uri;
	
	@XmlElement(name = "uri")
	public String getURI() {
		return uri.toString();
	}
	
	
	public void setURI(URI uri) {
		this.uri = uri;
	}
	
	

}
