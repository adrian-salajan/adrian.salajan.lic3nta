package web.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import web.entity.Region;
import web.entity.Userrr;
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
	
	@RequestMapping("/user/{username}")
	public String adminPanel(@ModelAttribute("userform") UserForm userform, 
			@PathVariable("username") String username) {
		Userrr user = userDao.getUser(username);
		
		
		userform.setUsername(user.getUsername());
		userform.setSelectedRole(user.getRolee());
		userform.setSelectedRegions(user.getRegions());
		
		Set<Region> allRegions = userDao.getRegions();
		userform.setRegions(allRegions);
		
		List<String> roles = new ArrayList<String>();
		roles.add("SALES");
		roles.add("STOCK");
		roles.add("CLIENT");
		userform.setRoles(roles);
		
		
		return "admin/user";
	}

}
