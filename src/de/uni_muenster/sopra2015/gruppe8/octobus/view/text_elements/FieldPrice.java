package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

/**
 * adjusted FieldText for price inputs (f.ex. 4,56€)
 */
public class FieldPrice extends FieldText
{
	public FieldPrice()
	{
		super(8); //xxxx,xx€ -> 8 chars should be enough
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
				if ((c < '0' || c > '9') && (c != ',')) //only allow numbers and comma
				{
					e.consume();  // ignore event
					return;
				}

				if((c == ',') && getText().contains(","))
				{
					e.consume();
					return;
				}

				//check for inputs like 44,817
				String input = getText().trim();
				int index = input.indexOf(',');
				if(index != -1 && input.substring(index + 1).trim().length() > 1) //input contains ","
				{
					e.consume();
				}
			}
		});
	}

	/**
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
		if(indexComma == -1) //no comma at all, input like 55
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
		if(euroString.length() == 0) //input like ,55
		{
			euro = 0;
		}
		else //input like 13, or 13,45
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
		if(centString.length() == 0) //input like 13,
		{
			cent = 0;
		}
		else
		{
			try
			{
				cent = Integer.parseInt(centString);
				if(cent < 10) cent *= 10;
			} catch (Exception e)
			{
				cent = 0;
			}
		}

		return (euro * 100 + cent);

	}



	/**
	 * sets field with specified price
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
