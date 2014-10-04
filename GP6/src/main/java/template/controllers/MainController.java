package template.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import template.objects.DenFinder;

@Controller
public class MainController {
	@RequestMapping(value = "/")
	public String home(Model model) 
	{
		model.addAttribute("results", new DenFinder());

		return "index";
	}
	
	@RequestMapping(value = "/map")
	public String map() 
	{
		return "map";
	}
	
	
	@RequestMapping(value = "/results")
	public String createDenSelect(Model model)
	{
		return "results";
	}
	
}
