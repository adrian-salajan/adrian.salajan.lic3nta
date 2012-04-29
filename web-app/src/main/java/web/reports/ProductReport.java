package web.reports;

public class ProductReport {
	
	private Long id;
	private String name;
	private String desc;
	private String price;
	private String qty;
	private String qop;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getQop() {
		return qop;
	}
	public void setQop(String qop) {
		this.qop = qop;
	}
	
	

}
