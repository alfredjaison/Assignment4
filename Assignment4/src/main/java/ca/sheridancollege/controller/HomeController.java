package ca.sheridancollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import ca.sheridancollege.beans.Phone;
import ca.sheridancollege.beans.User;
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
		return "admin/addPhone.html";
	}

	@GetMapping("/search")
	public String userSearch() {
		return "user/search.html";
	}
	@GetMapping("/latestReleases")
	public String latestReleases(Model model) {
		model.addAttribute("phones", da.getPhones());
		System.out.println(da.getPhones());
		return "user/latestReleases.html";
	}
	
	@GetMapping("/viewPhone/{phoneId}")
	public String viewPhone(@PathVariable int phoneId) {
		
		return "user/viewPhone.html";
	}
	
	@GetMapping("/survey")
	public String surveyPage() {
		return "user/survey.html";
	}
	
	public String home() {
		return "user/search.html";
	}

	
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);

		}

	@PostMapping("/register")
	public String regUser(@RequestParam String userName,@RequestParam String email, @RequestParam String password) {
		User user = new User(userName, email, encryptPassword(password));
		da.addUser(user);
		long userId = da.findUserAccount(userName).getUserId();
		da.addRole(userId, 2);
	return "redirect:/";

	}

	
}
