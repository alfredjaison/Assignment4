package ca.sheridancollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String root() {
		
		return "home.html";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}


	@GetMapping("/home")
	public String home() {
		return null;
	}
	jdfvadfnanmkfnbaf j sjjf jn pf n
}
