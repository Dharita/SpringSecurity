package com.user.management.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.user.management.bean.User;
import com.user.management.utilities.Utils;

@Component
public class UserFormValidator implements Validator {

	private static final Pattern STRING_ALPHA_SPACE_PATTERN = Pattern.compile("^[A-Za-z ]{3,25}$");

	private static final Pattern STRING_ALPHA_NUMERIC_UNDERSCORE_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,25}$");

	private static final Pattern EMAIL_PATTERN = Pattern.compile(
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern CONTACT_NUMBER_PATTERN = Pattern.compile("^[0-9]{12}$");

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		User user = (User) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.userForm.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.userForm.confirmPassword");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.userForm.gender");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.userForm.country");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "NotEmpty.userForm.contactNumber");

		if (user.getCountry().equalsIgnoreCase("none")) {
			errors.rejectValue("country", "NotEmpty.userForm.country");
		}

		// validate at-least 2 frameworks are selected
		if (user.getFramework() == null || user.getFramework().size() < 2) {
			errors.rejectValue("framework", "Valid.userForm.framework");
		}

		// validate at-least 3 skills are selected
		if (user.getSkill() == null || user.getSkill().size() < 3) {
			errors.rejectValue("skill", "Valid.userForm.skill");
		}

		validatePattern(errors, user);
	}

	/**
	 * @param errors
	 * @param user
	 */
	private void validatePattern(Errors errors, User user) {
		// validate name format
		if (!Utils.getInstance().isEmpty(user.getName()) && !validName(user.getName())) {
			errors.rejectValue("name", "Pattern.userForm.name");
		}

		// validate emailId format
		if (!Utils.getInstance().isEmpty(user.getEmail()) && !validEmailId(user.getEmail())) {
			errors.rejectValue("email", "Pattern.userForm.email");
		}

		// validate username format
		if (!Utils.getInstance().isEmpty(user.getUsername()) && !validUsername(user.getUsername())) {
			errors.rejectValue("username", "Pattern.userForm.username");
		}

		// validate password format
		if (!Utils.getInstance().isEmpty(user.getPassword()) && !validPassword(user.getPassword())) {
			errors.rejectValue("password", "Pattern.userForm.password");
		} else if (!Utils.getInstance().isEmpty(user.getPassword()) && !Utils.getInstance().isEmpty(user.getName())
				&& user.getPassword().equals(user.getName())) {
			errors.rejectValue("password", "Same.userForm.password.name");
		} else if (!Utils.getInstance().isEmpty(user.getPassword()) && !Utils.getInstance().isEmpty(user.getUsername())
				&& user.getPassword().equals(user.getUsername())) {
			errors.rejectValue("password", "Same.userForm.password.username");
		}

		// validate if password doesn't match confirm password
		if (!Utils.getInstance().isEmpty(user.getPassword()) && !Utils.getInstance().isEmpty(user.getConfirmPassword())
				&& !user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Diff.userform.passowrd.confirmPassword");
		}

		// validate 12-digit contact number
		if (null != user.getContactNumber() && !validContactNumber(Long.toString(user.getContactNumber()))) {
			errors.rejectValue("contactNumber", "Pattern.userForm.contactNumber");
		}
	}

	public boolean validName(String name) {
		return STRING_ALPHA_SPACE_PATTERN.matcher(name).matches();
	}

	public boolean validUsername(String username) {
		return STRING_ALPHA_NUMERIC_UNDERSCORE_PATTERN.matcher(username).matches();
	}

	public boolean validPassword(String password) {
		return STRING_ALPHA_NUMERIC_UNDERSCORE_PATTERN.matcher(password).matches();
	}

	public boolean validEmailId(String emailId) {
		return EMAIL_PATTERN.matcher(emailId).matches();
	}

	public boolean validContactNumber(String contactNumber) {
		return CONTACT_NUMBER_PATTERN.matcher(contactNumber).matches();
	}

}