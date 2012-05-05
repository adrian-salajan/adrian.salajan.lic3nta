package web.mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import web.entity.Region;

public class UserForm {
	
	private String username;
	
	private Set<Region> regions;
	private Set<Region> selectedRegions;
	
	private String selectedRole;
	private List<String> roles;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Region> getRegions() {
		if (regions == null) {
			regions = new TreeSet<Region>();
		}
		return new ArrayList<Region>(regions);
	}
	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}
	public List<Region> getSelectedRegions() {
		if (selectedRegions == null) {
			selectedRegions = new TreeSet<Region>();
		}
		return new ArrayList<Region>(selectedRegions);
	}
	public void setSelectedRegions(Set<Region> selectedRegions) {
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
	
	
	
	
	
	
	

}
