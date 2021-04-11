package com.example.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {


	/* @MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting1(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	} */

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

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	@GetMapping("/flights")
	public String flights(@RequestParam(name="name", required=false, defaultValue="from Rupesa")String name, Model model) {
	
		Data dados = OpenSky.getJson();
		//System.out.println(name);

		model.addAttribute("teste", "glugluglu");
		model.addAttribute("time", dados.getTimeFormated());
        model.addAttribute("states", dados.getStates());
		return "flights";
	}

	/* @MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Data infoToJs(Data data) throws Exception {
		return data;
	} */

}
