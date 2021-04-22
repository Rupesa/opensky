package com.example.messagingstompwebsocket;

import java.util.LinkedList;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	private static LinkedList<String> messages = new LinkedList<>();

	@MessageMapping("/hello")
	@SendTo("/topic/greeting")
	public Greeting getMessage(String x) throws Exception {
		if (messages.isEmpty()) return new Greeting(" ");
		return new Greeting(messages.pop());
	}

	public static void addMsg(String msg) {
		messages.add(msg);
	}

	@GetMapping(value = "/consume")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		return "consumer";
	}

}
