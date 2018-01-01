/**
 * 
 */
package com.user.management.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.user.management.bean.User;
import com.user.management.service.UserService;
import com.user.management.validator.UserFormValidator;

/**
 * @author dharita.chokshi
 *
 */
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("messages", Locale.getDefault());

	@Autowired
	private UserFormValidator userFormValidator;

	@Autowired
	private UserService userService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}

	/**
	 * Set login page as startup page and return url to redirect to login page
	 * 
	 * @param model
	 * @return {@link String}
	 */
	@GetMapping("/")
	public String index(ModelMap model) {
		logger.info("index()");
		return "redirect:/login";
	}

	/**
	 * Display login form
	 * 
	 * @param error
	 * @param logout
	 * @param model
	 * @return {@link String}
	 */
	@GetMapping(value = "/login")
	public String showLoginForm(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		logger.info("showLoginForm()");
		if (null != error) {
			model.addAttribute("error", BUNDLE.getString("Login.invalid.credential"));
		}
		if (null != logout) {
			model.addAttribute("msg", BUNDLE.getString("Logout.success"));
		}
		model.addAttribute("userForm", new User());
		return "login";
	}

	/**
	 * Get list of all user(s) and return url of user list page
	 * 
	 * @param model
	 * @return {@link String}
	 */
	@GetMapping(value = "/users/list")
	public String listUser(ModelMap model) {
		logger.info("listUser()");
		model.addAttribute("users", userService.getUsers());
		return "users/list";
	}

	/**
	 * Display user registration form
	 * 
	 * @param model
	 * @return {@link String}
	 */
	@GetMapping(value = "/users/add")
	public String showAddUserForm(Model model) {
		logger.info("showAddUserForm()");
		model.addAttribute("userForm", new User());
		return "users/userform";
	}

	/**
	 * Validate requested user. If no errors found, add or update user and
	 * redirect to view user by userId page else redirect back to add user page
	 * with errors.
	 * 
	 * @param user
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return {@link String}
	 */
	@PostMapping(value = "/users")
	public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.info("saveOrUpdateUser() : {}", user);
		boolean isNew = user.isNew();
		if (result.hasErrors()) {
			return "users/userform";
		} else {
			/*
			 * While adding new user, if user with provided username already
			 * exist in database, redirect to login page else save/update user
			 * to database and redirect to list user page in case user is
			 * added/updated successfully otherwise redirect back to user form
			 * page with appropriate message
			 */
			if (isNew && null != userService.getUserByUsername(user.getUsername())) {
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.exist"));
				return "redirect:/login";
			} else {
				if (userService.saveOrUpdate(user)) {
					redirectAttributes.addFlashAttribute("css", "success");
					if (isNew) {
						redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.add.success"));
					} else {
						redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.update.success"));
					}
					return "redirect:/users/" + user.getId();
				} else {
					redirectAttributes.addFlashAttribute("css", "danger");
					if (isNew) {
						redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.add.error"));
					} else {
						redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.update.error"));
					}
					return "users/userform";
				}
			}
		}
	}

	/**
	 * Display user details based on userId
	 * 
	 * @param userId
	 * @param model
	 * @param redirectAttributes
	 * @return {@link String}
	 */
	@GetMapping(value = "/users/{userId:[0-9]+}")
	public String showUser(@PathVariable("userId") int userId, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.debug("showUser() userId: {}", userId);
		User user = userService.getUserById(userId);
		if (user == null) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.not.found"));
			return "redirect:users/list";
		} else {
			model.addAttribute("user", user);
			return "users/show";
		}
	}

	/**
	 * Display form to update user details based on user id
	 * 
	 * @param user
	 * @param model
	 * @return {@link String}
	 */
	@GetMapping(value = "/users/{userId:[0-9]+}/update")
	public String showUpdateUserForm(@PathVariable("userId") int userId, ModelMap model,
			final RedirectAttributes redirectAttributes) {
		logger.info("showUpdateUserForm()");
		User user = userService.getUserById(userId);

		if (null == user) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.not.found"));
			return "redirect:users/list";
		} else {
			model.addAttribute("userForm", user);
			return "users/userform";
		}
	}

	/**
	 * Delete user based on user id and redirect to list user page with delete
	 * success/failure message
	 * 
	 * @param userId
	 * @param redirectAttributes
	 * @return {@link String}
	 */
	@GetMapping(value = "/users/{userId:[0-9]+}/delete")
	public String deleteUser(@PathVariable("userId") int userId, final RedirectAttributes redirectAttributes,
			HttpServletRequest requ, HttpServletResponse resp) {
		logger.info("deleteUser() : {}", userId);

		if (userService.deleteUser(userId)) {
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.delete.success"));
		} else {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", BUNDLE.getString("User.delete.error"));
		}
		return "redirect:/users/list";
	}

	/**
	 * Method used to populate the framework list in view.
	 * 
	 * @return {@link List} of frameworks
	 */
	@ModelAttribute("frameworkList")
	public List<String> initializeFrameworks() {
		List<String> frameworksList = new ArrayList<>();
		frameworksList.add("Spring MVC");
		frameworksList.add("Struts 2");
		frameworksList.add("JSF 2");
		frameworksList.add("GWT");
		frameworksList.add("Play");
		frameworksList.add("Apache Wicket");
		return frameworksList;
	}

	/**
	 * Method used to populate the skill list in view.
	 * 
	 * @return {@link Map} of skill set
	 */
	@ModelAttribute("javaSkillList")
	public Map<String, String> initializeSkill() {
		Map<String, String> skill = new LinkedHashMap<>();
		skill.put("Hibernate", "Hibernate");
		skill.put("Spring", "Spring");
		skill.put("Struts", "Struts");
		skill.put("Groovy", "Groovy");
		skill.put("Grails", "Grails");
		return skill;
	}

	/**
	 * Method used to populate the country list in view. Note that here you can
	 * call external systems to provide real data.
	 * 
	 * @return {@link Map} of countries
	 */
	@ModelAttribute("countryList")
	public Map<String, String> initializeCountries() {
		Map<String, String> country = new LinkedHashMap<>();
		country.put("In", "India");
		country.put("US", "United Stated");
		country.put("CN", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		return country;
	}

	@GetMapping("/403")
	public String error403(ModelMap model) {
		model.addAttribute("headerText", BUNDLE.getString("Header.access.denied"));
		return "/error";
	}

}