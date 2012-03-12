package web.mvc;


import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.gateway.StockGateway;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;
import web.converter.Converter;
import web.mvc.model.CategoryAdd;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	StockGateway stockGateway;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addCategory(@Valid CategoryAdd category,BindingResult result,
			Map map
			) {
		
		if (result.hasErrors()) {
			map.put("categoryAdd", (CategoryAdd)result.getTarget());
			return "category.add";
		}
		CategoryDTO dto = Converter.toCategory(category);
		try {
			stockGateway.createCategory(dto);
		} catch (StockGatewayException e) {
			e.printStackTrace();
		}
		return "redirect:/category/add";
		
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String initView(ModelMap model) {
		model.put("categoryAdd", new CategoryAdd());
		return "category.add";
	}
	

}
