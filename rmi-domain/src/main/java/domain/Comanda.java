package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class Comanda implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3802555697583862489L;

	@Column(name="ship_region",nullable = true)
	private String shipAddressRegion;
	
	@Column(name="ship_address",nullable = true)
	private String shipAddress;

	@Column(name="comanda_created", nullable = true)
	private Date created;

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result
				+ ((shipAddress == null) ? 0 : shipAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comanda other = (Comanda) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (shipAddress == null) {
			if (other.shipAddress != null)
				return false;
		} else if (!shipAddress.equals(other.shipAddress))
			return false;
		return true;
	}

	public String getShipAddressRegion() {
		return shipAddressRegion;
	}

	public void setShipAddressRegion(String shipAddressRegion) {
		this.shipAddressRegion = shipAddressRegion;
	}

	
	
	
	
	

}
