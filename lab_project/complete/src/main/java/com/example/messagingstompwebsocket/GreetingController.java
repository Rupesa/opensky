package com.example.messagingstompwebsocket;


import com.example.repository.*;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.LinkedList;

import java.util.stream.*;
import java.util.List;

@Controller
public class GreetingController {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private Data prevFlights;
	private LinkedList<String> noFlights = new LinkedList<>();
	private LinkedList<String> newFlights = new LinkedList<>();

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String message) {
		logger.info(String.format("#### -> Producing message -> %s", message));
		this.kafkaTemplate.send("flights_topic", message);
	}
	

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

		prevFlights = dados;
		
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

		if (checkForChanges(dados)) {
			String message = "\nNew flights: \n";
			for (int i = 0; i < newFlights.size(); i++) message += newFlights.get(i)+"\n";
			message += "\nFlights that are no longer visible: \n";
			for (int i = 0; i < noFlights.size(); i++) message += noFlights.get(i)+"\n";
			message += "\n";
			sendMessage(message);
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

	private boolean checkForChanges(Data flights) {

		boolean changes = false;
		newFlights = new LinkedList<>();
		noFlights = new LinkedList<>();

		if ((prevFlights != null) && (!prevFlights.equals(flights))) {
			String prevStates[][] = prevFlights.getStates();
			String states[][] = flights.getStates();
			boolean ver;
			for (int i = 0; i < prevStates.length; i++) {
				ver = false;
				for (int j = 0; j < states.length; j++) {

					if (prevStates[i][0].equals(states[j][0])) ver = true;
				}
				if (!ver) {
					noFlights.add("icao24: "+prevStates[i][0]+" | origin_country: "+prevStates[i][2]);
					changes = true;
				}
			}
			for (int i = 0; i < states.length; i++) {
				ver = false;
				for (int j = 0; j < prevStates.length; j++) {
					if (prevStates[j][0].equals(states[i][0])) ver = true;
				}
				if (!ver) {
					newFlights.add("icao24: "+states[i][0]+" | origin_country: "+states[i][2]);
					changes = true;
				}
			}
		}

		prevFlights = flights;

		return changes;

	}


}
