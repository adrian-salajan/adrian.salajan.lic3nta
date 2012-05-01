package web.integration;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductIntegration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=false, nullable=false)
	private Long stockId;
	
	@Column(unique=true, nullable=false)
	private Long orderedProductId;
	
	@Column(unique=false, nullable=false)
	private Long categoryId;
	
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public Long getOrderedProductId() {
		return orderedProductId;
	}
	public void setOrderedProductId(Long orderedProductId) {
		this.orderedProductId = orderedProductId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
	
	

}
