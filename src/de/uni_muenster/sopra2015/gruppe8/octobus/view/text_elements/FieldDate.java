package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * Customised FieldText for (German) date inputs (only allows numbers and dots).
 */
public class FieldDate extends FieldText
{
    private int year, month, day;

	/**
	 * Constructs FieldDate with limit of 10 (dd.mm.yyyy -> 10 characters).
	 */
	public FieldDate()
	{
		super(10);
		isValidDate();

		this.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
                // Accept only numbers and periods
                if (((c < '0') || (c > '9')) && (c != '.'))
				{
					e.consume();
				}
			}
		});

	}


	/**
	 * Checks if input is a valid date.
     *
	 * @return true iff input is valid
	 */
	private boolean isValidDate()
	{
		String input = this.getText();
		if(input == null || input.trim().length() == 0)
			return false;

        // Match against regex for date
        if (Pattern.compile("([0-9]{1,2})\\.([0-9]{1,2})\\.([0-9]{4})").matcher(input).matches())
        {
	        // Parse day, month, year
			int indexDot = input.indexOf('.');
			day = Integer.parseInt(input.substring(0, indexDot));
			input =  input.substring(indexDot + 1);
			indexDot = input.indexOf('.');

			month = Integer.parseInt(input.substring(0, indexDot));
			input = input.substring(indexDot + 1);

			year = Integer.parseInt(input.substring(0));

			if(year < 0 || month < 1 || month > 12 || day > 31 || day < 1)
				return false;

			boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
			switch (month)
			{
				case 4:
				case 6:
				case 9:
				case 11:
					if(day > 30) return false;
					break;
				case 2:
					if(isLeapYear && day > 29) return false;
					else if(!isLeapYear && day > 28) return false;
			}

			if(year <= 1901 && month <= 12 && day <= 13) return false; //unix timestamp overflow
			return true;
        }
		else
        {
			return false;
        }
	}


	/**
     * Returns the entered String as a Date object.
     *
	 * @return date if input is a valid date, null otherwise
	 */
	public Date getDate()
	{
		if(isValidDate())
		{

			Calendar c = Calendar.getInstance();
            // -1 because Calendar.MONTH is 0-indexed
            c.set(year, month - 1, day, 0, 0);


			return c.getTime();
		}
		else
			return null;
	}

	public void setDate(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		this.setText(df.format(date));
	}
}
