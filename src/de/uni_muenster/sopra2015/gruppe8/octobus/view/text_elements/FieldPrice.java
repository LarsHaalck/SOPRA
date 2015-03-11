package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Customised FieldText for price inputs (e.g. "4,56 €").
 */
public class FieldPrice extends FieldText
{
	public FieldPrice()
	{
        // xxxx,xx €  -> 8 characters should be enough
        super(8);
		setMask();
	}

	public FieldPrice(int columns)
	{
		super(columns, 8);
		setMask();
	}


	private void setMask()
	{
		this.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
                // Only allow digits and one comma
                if ((c < '0' || c > '9') && (c != ','))
				{
					e.consume();  // ignore event
					return;
				}

				if((c == ',') && getText().contains(","))
				{
					e.consume();
					return;
				}

				// Check for inputs like "44,817"
				String input = getText().trim();
				int index = input.indexOf(',');
                // Input contains ","
                if(index != -1 && input.substring(index + 1).trim().length() > 1)
				{
					e.consume();
				}
			}
		});
	}

	/**
     * Retrieve the value entered in cents.
     *
	 * @return price in cents
	 */
	public int getPrice()
	{
		String input = this.getText().trim();
		int euro, cent;
		String euroString, centString;

		if(input.length() == 0)
			return -1;

		int indexComma = input.indexOf(',');
        // No comma at all, i.e. input is something like "55"
        if(indexComma == -1)
		{
			try
			{
				return Integer.parseInt(input) * 100;
			} catch (Exception e)
			{
				return 0;
			}

		}

		euroString = input.substring(0, indexComma);
        // Input is something like ",55"
        if(euroString.length() == 0)
		{
			euro = 0;
		}
        // Input is something like "13," or "13,45"
        else
		{
			try
			{
				euro = Integer.parseInt(euroString);
			} catch (Exception e)
			{
				euro = 0;
			}
		}

		centString = input.substring(indexComma + 1);
        // Input is something like "13,"
        if(centString.length() == 0)
		{
			cent = 0;
		}
		else
		{
			try
			{
				cent = Integer.parseInt(centString);
				if(cent < 10 && centString.length() < 2) cent *= 10; //otherwise values like 5,01 would lead to 5,10
			} catch (Exception e)
			{
				cent = 0;
			}
		}

		return (euro * 100 + cent);

	}



	/**
	 * Sets field with specified price.
     *
	 * @param cents cents to be set
	 */
	public void setPrice(int cents)
	{
		int euro = cents/100;
		int cent = cents%100;

		String text = "" + euro;

		if(cent == 0)
		{
			this.setText(text);
		}
		else if(cent < 10)
		{
			this.setText(text + ",0" + cent);
		}
		else
		{
			this.setText(text + "," + cent);
		}

	}

}
