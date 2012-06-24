package web.mvc.model;

import java.util.Set;

import web.entity.Region;

public class UIRegionsResponse {

	private Region[] availableRegions = null;
	private Region [] unAvailableRegions = null;
	
	
	public Region[] getAvailableRegions() {
		return availableRegions;
	}
	public void setAvailableRegions(Region[] availableRegions) {
		this.availableRegions = availableRegions;
	}
	public Region[] getUnAvailableRegions() {
		return unAvailableRegions;
	}
	public void setUnAvailableRegions(Region[] unAvailableRegions) {
		this.unAvailableRegions = unAvailableRegions;
	}
	
	
	
	
	
}
