package web.mvc.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class ShipmentPreferencesForm {
	
	private boolean allowNegociation;
	private boolean isNegociated;
	
	private List<String> regions;
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
	public List<String> getRegions() {
		if (regions == null) {
			regions = new ArrayList<String>();
		}
		return regions;
	}
	public void setRegions(List<String> regions) {
		this.regions = regions;
	}
	
	
	
	
	

}
