package dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "relatedServices")
public class LinkedService {
	
	@XmlElementWrapper(name = "relatedServices")
	@XmlElementRefs({@XmlElementRef(type = ServiceLink.class)})
	public Collection<ServiceLink> links = new ArrayList<ServiceLink>();
	
	
	

}
