package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

/**
 * adjusted FieldText for email fields
 */
public class FieldEmail extends FieldText
{
	/**
	 * constructs normal FieldText, limited to 200 characters
	 */
	public FieldEmail()
	{
		super();
	}

	/**
	 * checks if email meets all requirements by checking it with the RFC 5322 regular expression for emails
	 * @param email email to be checked
	 * @return true iff email is valid
	 */
	private boolean isValidEmail(String email)
	{
		if(email == null) //should only happen if FieldEmail is set directly from DB
			return false;

		if(super.isValidInput()) //check if it contains other malicious input
		{
			String pattern = "^([\\!#\\$%&'\\*\\+\\/\\=?\\^`\\{\\|\\}~a-zA-Z0-9_-]+[\\.]?)+[\\!#\\$%&'\\*\\+\\/\\=?\\^`\\{\\|\\}~a-zA-Z0-9_-]+@{1}((([0-9A-Za-z_-]+)([\\.]{1}[0-9A-Za-z_-]+)*\\.{1}([A-Za-z]){1,6})|(([0-9]{1,3}[\\.]{1}){3}([0-9]{1,3}){1}))$";
			if (Pattern.compile(pattern).matcher(email).matches())
				return true;
		}
		return false;
	}

	/**
	 * @return text from field if text is valid email, null otherwise
	 */
	public String getEmail()
	{
		String input = this.getText();
		if(isValidEmail(input))
			return input;
		else
			return null;
	}

	public void setEmail(String email)
	{
		setText(email);
	}

}
