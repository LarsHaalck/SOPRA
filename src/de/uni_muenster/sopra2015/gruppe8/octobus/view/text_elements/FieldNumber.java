package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FieldNumber extends FieldText
{

	public FieldNumber(int limit)
	{
		super();
		if(limit >= 9)
			setLimit(9);
		else
			setLimit(limit);
		setMask();
	}

	public FieldNumber()
	{
		super(9);
		setMask();
	}

	private void setMask()
	{
		this.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e) //ignore some cases to prevent SQL-injections
			{
				char c = e.getKeyChar();
				if (c < '0' || c > '9')
				{
					e.consume();  // ignore event
				}
			}
		});
	}


	public int getNumber()
	{
		if(this.getText().length() == 0)
			return -1;
		else
		{
			try
			{
				return Integer.parseInt(this.getText());
			} catch(Exception e)
			{
				System.out.println("Exception in FieldNumber parse");
				return -1;
			}
		}
	}

	public void setNumber(int number)
	{
		this.setText(Integer.toString(number));
	}

	//TODO: setText() getText() Overrides

}
