package web.entity;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name="Userrr")
public class Userrr {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Generated(GenerationTime.INSERT)
	private Long id;
	
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String rolee;
	
	@ManyToMany
	@JoinTable(name="Userrr_Region",
			joinColumns = @JoinColumn(name = "userrr_id"),
			inverseJoinColumns = @JoinColumn(name = "region_id", nullable = true, unique = false)
	)
	private Set<Region> regions;
	@Column
	private String email;
	@Column
	private boolean enabled;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRolee() {
		return rolee;
	}
	public void setRolee(String rolee) {
		this.rolee = rolee;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
	
	
	

}
