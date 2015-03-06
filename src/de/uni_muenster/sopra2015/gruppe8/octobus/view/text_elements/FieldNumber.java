package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * adjusted FieldText for number input only where all input except numbers is blocked
 */
public class FieldNumber extends FieldText
{

	/**
	 * constructs FieldNumber with max input length of 9
	 */
	public FieldNumber()
	{
		super(9);
		setMask();
	}

	/**
	 * constructs FieldNumber with maximum input length of limit
	 * @param limit limit to be set. If limit exceeds 9, 9 will be set nonetheless to prevent integer overflow
	 */
	public FieldNumber(int limit)
	{
		super();
		if(limit >= 9)
			setLimit(9);
		else
			setLimit(limit);
		setMask();
	}


	/**
	 * constructs FieldNumber with maximum input length of limit and specified number of columns
	 * @param columns numbers of columns to use to calculate preferred width
	 * @param limit limit to be set. If limit exceeds 9, 9 will be set nonetheless to prevent integer overflow
	 */
	public FieldNumber(int columns, int limit)
	{
		super();
		this.setColumns(columns);
		if(limit >= 9 || limit == -1)
			setLimit(9);
		else
			setLimit(limit);

		setMask();
	}


	/**
	 * blocks all inputs except numbers
	 */
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


	/**
	 * @return -1 if no number was entered, returns input otherwise
	 */
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
				return -1; //only for compiler. Shouldn't happen!
			}
		}
	}

	public void setNumber(int number)
	{
		this.setText(Integer.toString(number));
	}
}
