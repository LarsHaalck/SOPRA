package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

/**
 * Customised FieldText for email fields
 */
public class FieldEmail extends FieldText
{
	/**
	 * Constructs normal FieldText, limited to 200 characters
	 */
	public FieldEmail()
	{
		super();
	}

	/**
	 * Checks if email meets all requirements by checking it with the RFC 5322 regular expression for emails.
     *
	 * @param email email to be checked
	 * @return true iff email is valid
	 */
	private boolean isValidEmail(String email)
	{
        // Should only happen if FieldEmail is set directly from DB
        if(email == null)
			return false;

        // Check if input contains other malicious content
        if(super.isValidInput())
		{
			String pattern = "^([\\!#\\$%&'\\*\\+\\/\\=?\\^`\\{\\|\\}~a-zA-Z0-9_-]+[\\.]?)+[\\!#\\$%&'\\*\\+\\/\\=?\\^`\\{\\|\\}~a-zA-Z0-9_-]+@{1}((([0-9A-Za-z_-]+)([\\.]{1}[0-9A-Za-z_-]+)*\\.{1}([A-Za-z]){1,6})|(([0-9]{1,3}[\\.]{1}){3}([0-9]{1,3}){1}))$";
			if (Pattern.compile(pattern).matcher(email).matches())
				return true;
		}
		return false;
	}

	/**
     * Returns the text from the FieldText if the input is a correctly formatted email address.
     *
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
