package dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;


public class LinkedServiceBuilder {
	
	private Collection<ServiceLink> links;
	
	public LinkedServiceBuilder() {
		links = new ArrayList<ServiceLink>();
	}
	
	public LinkedServiceBuilder add(String name, String method, URI uri) {
		ServiceLink sl = new ServiceLink();
		sl.name = name;
		sl.setURI(uri);
		sl.method = method;
		this.links.add(sl);
		return this;
	}
	
	public Collection<ServiceLink> build() {
		return this.links;
	}
	
	

}
