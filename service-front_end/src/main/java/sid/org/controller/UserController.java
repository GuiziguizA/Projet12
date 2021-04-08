package sid.org.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.dto.UserDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.service.HttpService;
import sid.org.service.UserService;

@Controller
public class UserController {

	@Autowired
	HttpService httpService;
	@Autowired
	UserService userService;

	@GetMapping(value = "/userForm")
	public String User(UserDto userDto, Model model) {

		return "formulaireUser";
	}

	@GetMapping("/login")
	public String afficherLogin() {

		return "login";
	}

	@PostMapping("/user")
	public String createUser(UserDto userDto, Model model, BindingResult result, HttpServletRequest request)
			throws APiUSerAndCompetenceException {

		try {
			userService.createUser(userDto, request);
			String succes = "Vous  êtes inscris";
			model.addAttribute("succes", succes);
			return "redirect:/login";
		} catch (HttpStatusCodeException e) {
			String error = httpService.traiterLesExceptionsApi(e);
			model.addAttribute("error", e);
			return "formulaireUser";
		}

	}

}
