package web.mvc.model;

import java.util.Date;

import domain.Oferta;

public class HistoryOrder {
	
	private Oferta order;
	
	private String updateStatus;

	public Oferta getOrder() {
		return order;
	}

	public void setOrder(Oferta order) {
		this.order = order;
	}
	
	public long getPrice() {
		long total = 0;
		for (domain.Product p : order.getItems()) {
			total += p.getFinalPrice() * p.getQuantity();
		}
		return total;
	}
	
	
	public Date getCreationDate() {
		return order.getCreated();
	}
	
	public Date getLastModified() {
		return order.getLastModified();
	}


	public String getStatus() {
		return order.getStatus();
	}
	
	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}
	
	
	
	
	

}
