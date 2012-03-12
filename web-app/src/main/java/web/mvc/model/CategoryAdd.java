package web.mvc.model;


import javax.validation.constraints.Size;


public class CategoryAdd {
	
	@Size(min= 3 , max = 25, message="gfhghj")
	private String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
