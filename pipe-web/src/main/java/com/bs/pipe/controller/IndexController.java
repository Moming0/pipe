package com.bs.pipe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	@RequestMapping("/{pages}")
	public String pages(@PathVariable("pages") String pages,
			@RequestParam(name="ReturnUrl",required=false,defaultValue="") String returnUrl,
			Model model){
		model.addAttribute("redirect", returnUrl);
		return pages;
	}
	
	
	
}
