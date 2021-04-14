package com.example.messagingstompwebsocket;


import com.example.repository.*;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.*;
import java.util.List;

@Controller
public class GreetingController {


	@Autowired
	FlightRepository flightRepository;

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

	@GetMapping("/flights")
	public String flights(@RequestParam(name="name", required=false, defaultValue="from Rupesa")String name, Model model) {
	
		Data dados = OpenSky.getJson();

		model.addAttribute("teste", "glugluglu");
		model.addAttribute("time", dados.getTimeFormated());
        model.addAttribute("states", dados.getStates());

		
		return "flights";
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Data infoToJs(String data) throws Exception {
		System.out.println(data);
		Data dados = new Data();
		if(data.equals("air")){
			Data preProcess = OpenSky.getJson();
			String [][] preState = preProcess.getStates();

			String[][] onGround = Arrays.stream(preState).filter(p -> p[8] == "false").toArray(String[][]::new);
			dados.setTime(preProcess.getTime());
			dados.setStates(onGround);
		}
		else if(data.equals("ground")){
			
			Data preProcess = OpenSky.getJson();
			String [][] preState = preProcess.getStates();

			String[][] onGround = Arrays.stream(preState).filter(p -> p[8] == "true").toArray(String[][]::new);
			dados.setTime(preProcess.getTime());
			dados.setStates(onGround);

		}
		else{
			dados = OpenSky.getJson();
		}
		
		return dados;
	}

	@GetMapping(value = "/historic")
	public String getAll(Model model){

		List<Flight> historicFlights = flightRepository.findAll(Sort.by("firstSeen"));
		model.addAttribute("flights", historicFlights);
		return "historic";
	}

	@GetMapping(value = "/load")
	public String persist(){
		Flight[] fs1_7;
		Flight[] fs8_14;

		fs1_7  = OpenSky.getHistoric("1617231600","1617836400");
		
		fs8_14 = OpenSky.getHistoric("1617836400","1618393890"); //8 de abril a 14 de abril

		Flight[] fs = Stream.concat(Arrays.stream(fs1_7), Arrays.stream(fs8_14))
                      .toArray(Flight[]::new);
		
		
		flightRepository.deleteAll();

		flightRepository.saveAll(Arrays.asList(fs));

		return "load";

	}


}
