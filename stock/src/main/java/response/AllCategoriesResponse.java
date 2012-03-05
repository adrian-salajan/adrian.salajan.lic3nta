package response;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import dto.CategoryDTO;
@XmlRootElement(name = "stockResponse")
public class AllCategoriesResponse extends StockResponse {
	

	@XmlElementWrapper(name = "categories")
	@XmlElementRefs({ @XmlElementRef(type=CategoryDTO.class)})
	public Collection<CategoryDTO> categories;

}
