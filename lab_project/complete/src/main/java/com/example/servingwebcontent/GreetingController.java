package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@GetMapping("/")
	public String index(@RequestParam(name="name", required=false, defaultValue="from Rupesa")String name, Model model) {
	
		Data dados = OpenSky.getJson();
		//System.out.println(name);

		model.addAttribute("teste", "glugluglu");
		model.addAttribute("time", dados.getTimeFormated());
                model.addAttribute("states", dados.getStates());
		return "greeting";
	}

}
