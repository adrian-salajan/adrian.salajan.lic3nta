package web.mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import web.entity.Region;

public class UserForm {
	
	private String username;
	
	private List<String> regions;
	private List<String> selectedRegions;
	private Set<Region> unavailableRegions;
	private Set<Region> dummyRegions = new TreeSet<Region>();
	
	private String selectedRole;
	private List<String> roles;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public List<String> getSelectedRegions() {
		if (selectedRegions == null) {
			selectedRegions = new ArrayList<String>();
		}
		return selectedRegions;
	}
	public void setSelectedRegions(List<String> selectedRegions) {
		this.selectedRegions = selectedRegions;
	}
	public String getSelectedRole() {
		return selectedRole;
	}
	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}
	public List<String> getRoles() {
		if (roles == null) {
			roles = new ArrayList<String>();
		}
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public Set<Region> getUnavailableRegions() {
		return unavailableRegions;
	}
	public void setUnavailableRegions(Set<Region> unavailableRegions) {
		this.unavailableRegions = unavailableRegions;
	}
	public Set<Region> getDummyRegions() {
		return dummyRegions;
	}
	public void setDummyRegions(Set<Region> dummyRegions) {
		this.dummyRegions = dummyRegions;
	}
	
	
	
	
	
	
	
	
	
	
	

}
