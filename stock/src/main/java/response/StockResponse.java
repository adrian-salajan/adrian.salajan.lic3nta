package response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import dto.LinkedService;

@XmlRootElement(name = "response")
public class StockResponse {
	
	@XmlElement
	public LinkedService relatedServices;

}
