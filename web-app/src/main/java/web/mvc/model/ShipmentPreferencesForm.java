package web.mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.validator.constraints.NotEmpty;

import web.entity.Region;

public class ShipmentPreferencesForm {
	
	private boolean allowNegociation;
	private boolean isNegociated;
	
	private Set<Region> regions;
	@NotEmpty
	private String clientInfo;
	@NotEmpty
	private String region;
	@NotEmpty
	private String address;
	
	public boolean getAllowNegociation() {
		return allowNegociation;
	}
	public void setAllowNegociation(boolean allowNegociation) {
		this.allowNegociation = allowNegociation;
	}
	public boolean getIsNegociated() {
		return isNegociated;
	}
	public void setIsNegociated(boolean isNegociated) {
		this.isNegociated = isNegociated;
	}
	public String getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<Region> getRegions() {
		if (regions == null) {
			regions = new TreeSet<Region>();
		}
		return regions;
	}
	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}
	
	
	
	
	

}
