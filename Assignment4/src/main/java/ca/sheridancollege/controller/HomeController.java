package ca.sheridancollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ca.sheridancollege.beans.Phone;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class HomeController {
	
	@Autowired
	private DatabaseAccess da;
	
	@GetMapping("/")
	public String root() {
		
		return "user/home.html";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	@GetMapping("/admin/goAddPhone")
	public String addPhone(Model model) {
		model.addAttribute("phone", new Phone());
		return "admin/addPhone.html";
	}
	
	@GetMapping("/admin/addPhone")
	public String addPhone(@ModelAttribute Phone phone) {
		da.addPhone(phone);
		return "addPhone.html";
	}

	@GetMapping("/search")
	public String home() {
		return "user/search.html";
	}
	
	
}
