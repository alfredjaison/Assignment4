package ca.sheridancollege.controller;

import java.util.ArrayList;

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

	@GetMapping("/admin")
	public String adminNavigation() {
		return "admin/adminNavigation";
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
	
	@GetMapping("/admin/goSearch")
	public String adminGoSearch() {
		return "admin/search.html";
	}
	
	@GetMapping("/admin/search")
	public String adminSearch(Model model, @RequestParam String searchBy, @RequestParam String string) {
		ArrayList<Phone> phoneList= new ArrayList<Phone>();
		switch (searchBy) {
		case "phoneId":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "manufacturer":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "model":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "price":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "screenSize":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "battery":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "ram":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "storage":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "processor":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "dimensions":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "waterProofRating":
			phoneList = da.getPhonesBy(string, searchBy);
			break;
		case "dateOfRelease":
			phoneList = da.getPhonesBy(string, searchBy);
			break;		
		}
		model.addAttribute("phones", phoneList);
		return "admin/search.html";
	}

	@GetMapping("/goSearch")
	public String goSearch() {
		return "user/search.html";
	}

	@GetMapping("/search")
	public String userSearch(Model model, @RequestParam String searchBy, @RequestParam String string) {
		ArrayList<Phone> phoneList = new ArrayList<Phone>();

		switch (searchBy) {
		case "model":
			phoneList = da.getPhonesByLike(string, searchBy);
			break;
		case "maxPrice":
			phoneList = da.getPhonesBy(string, "price <");
			break;
		case "minPrice":
			phoneList = da.getPhonesBy(string, "price >");
			break;
		case "screenSize":
			try {
				phoneList = da.getPhonesByRange(Double.parseDouble(string), searchBy);
			} catch (NumberFormatException e) {
				System.out.println("bad format");
			}
			break;
		case "storage":
			try {
				phoneList = da.getPhonesByRange(Double.parseDouble(string), searchBy);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("do nothing");
			}
			break;
			case "dateOfRelease":
			try {
				phoneList = da.getPhonesBy(string, "dateOfRelease >");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("do nothing");
			}
			break;
		}

		model.addAttribute("choice", searchBy);
		model.addAttribute("phones", phoneList);
		model.addAttribute("searchBy", searchBy);

		return "user/search.html";
	}

	@GetMapping("/latestReleases")
	public String latestReleases(Model model) {
		model.addAttribute("phones", da.getPhones());
		System.out.println(da.getPhones());
		return "user/latestReleases.html";
	}

	@GetMapping("/viewPhone/{phoneId}")
	public String viewPhone(@PathVariable int phoneId, Model model) {
		model.addAttribute("phone", da.getPhoneById(phoneId));
		return "user/viewPhone.html";
	}

	@GetMapping("/survey")
	public String surveyPage(Model model) {
		model.addAttribute("phone", new Phone());
		return "user/survey.html";
	}

	@GetMapping("/findPhone")
	public String findPhone(@ModelAttribute Phone phone, Model model) {
		ArrayList<Phone> allPhones = da.getPhones();
		ArrayList<Phone> validPhones = new ArrayList<Phone>();
		Phone bestChoice = new Phone();
		int highestScore = 0;
		for (Phone p : allPhones) {
			int score = p.equalsSpecific(phone);
			if (score > highestScore) {
				highestScore = score;
				bestChoice = p;
				validPhones.clear();
			}
			else if (score == highestScore) {
				validPhones.add(p);
			}
			System.out.println(score);
		}
		for (Phone p : allPhones) {
			int score = p.equalsSpecific(phone);
			if (score >= 6 && score != highestScore) {
				validPhones.add(p);
			}
		}
		
		System.out.println("high score: " + highestScore);
		System.out.println(validPhones);

		validPhones.add(0, bestChoice);
		
		System.out.println(validPhones);

		model.addAttribute("validPhones", validPhones);

		return "user/survey.html";
	}
	
	@GetMapping("/about")
	public String aboutPage() {
		return "user/about.html";
	}

	public static String encryptPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);

	}

	@GetMapping("/register")
	public String goReg() {
		return "register.html";
	}

	@PostMapping("/register")
	public String regUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password,
			@RequestParam String confirmPassword, Model model) {
		if (password.compareTo(confirmPassword) == 0) {
			User user = new User(userName, email, encryptPassword(password));
			da.addUser(user);
			long userId = da.findUserAccount(userName).getUserId();
			da.addRole(userId, 2);
			return "redirect:/";
		} else {
			model.addAttribute("error", "*Password does not match");
			return "/register";

		}

	}
	
}
