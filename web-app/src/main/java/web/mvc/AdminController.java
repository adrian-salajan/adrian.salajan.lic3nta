package web.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import web.entity.Region;
import web.entity.Userrr;
import web.mvc.model.UIRegionsResponse;
import web.mvc.model.UserForm;
import web.security.SecurityUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	SecurityUtils userDao;
	
	@RequestMapping
	public String adminPanel(ModelMap model) {
		List<Userrr> all = userDao.getAll();
		model.put("users", all);
		return "admin/users";
	}
	
	@RequestMapping(value = "/regionsForSale",  method = RequestMethod.GET)
	public @ResponseBody UIRegionsResponse getRegionsForSales() {
		Set<Region> availableRegions = null;
		Set<Region> unAvailableRegions = null;
		availableRegions = userDao.getRegionsForSales();
		unAvailableRegions = userDao.getRegions();
		unAvailableRegions.removeAll(availableRegions);
		
		UIRegionsResponse resp = new UIRegionsResponse();
		resp.setAvailableRegions(availableRegions.toArray(new Region[availableRegions.size()]));
		resp.setUnAvailableRegions(unAvailableRegions.toArray(new Region[unAvailableRegions.size()]));
		
		return resp;
	}
	
	@RequestMapping("/user/{username}")
	public String adminPanel(@ModelAttribute("userform") UserForm userform, 
			@PathVariable("username") String username) {
		Userrr user = userDao.getUser(username);
		
		Set<Region> availableRegions = null;
		Set<Region> unAvailableRegions = null;
		
		if (user.getRolee().equals("SALES")) {
			availableRegions = userDao.getRegionsForSales();
			availableRegions.addAll(user.getRegions());
			unAvailableRegions = userDao.getRegions();
			unAvailableRegions.removeAll(availableRegions);
		}
		
		if (user.getRolee().equals("STOCK")) {
			availableRegions = userDao.getRegions();
			unAvailableRegions = new TreeSet<Region>();
		}
		if (user.getRolee().equals("CLIENT")) {
			availableRegions = userDao.getRegions();
			unAvailableRegions = new TreeSet<Region>();
		}
		
		
		userform.setUsername(user.getUsername());
		userform.setSelectedRole(user.getRolee());
		
		for (Region region : user.getRegions()) {
			userform.getSelectedRegions().add(region.getName());
		}
		
		
		
		for (Region region : availableRegions) {
			userform.getRegions().add(region.getName());
		}
		userform.setUnavailableRegions(unAvailableRegions);
		
		List<String> roles = new ArrayList<String>();
		roles.add("SALES");
		roles.add("STOCK");
		roles.add("CLIENT");
		userform.setRoles(roles);
		
		
		return "admin/user";
	}
	
	@RequestMapping(value  = "/user/update", method = RequestMethod.POST )
	public String updateUSer(@ModelAttribute("userform") UserForm userform) {
		Userrr user = userDao.getUser(userform.getUsername());
		user.setRolee(userform.getSelectedRole());
		user.getRegions().clear();
		
		if ( ! userform.getSelectedRole().equals("CLIENT")){
			for (String region : userform.getSelectedRegions()) {
				user.getRegions().add(userDao.getRegion(region));
			}
		}
		userDao.update(user);
		return "redirect:/admin";
	}

}
