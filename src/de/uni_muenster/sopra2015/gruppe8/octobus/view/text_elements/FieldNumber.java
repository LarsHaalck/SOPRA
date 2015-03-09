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


	/**
	 * Constructs FieldNumber with maximum input length of limit and specified number of columns.
     *
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

	public FieldNumber(int widht, int limit, int intervalMaximum)
	{
		super();
		setLimit(limit);
		this.setColumns(widht);
		setInterval(intervalMaximum);
	}

	public FieldNumber()
	{
		super(9);
		setMask();
	}

	/**
	 * Blocks all inputs except numbers.
	 */
	private void setMask()
	{
		this.addKeyListener(new KeyAdapter()
		{
            // Ignore some cases to prevent SQL injections
            public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if (c < '0' || c > '9')
				{
					e.consume();  // ignore event
				}
			}
		});
	}

	private void setInterval(int intervalMaximum)
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
				int i = Character.getNumericValue(c);
				int value = i;
				if (getNumber() != -1)
				{
					value = getNumber() * 10 + i;
				}
				if (value > intervalMaximum)
				{
					e.consume(); // ignore event
				}
			}
		});
	}

	/**
     * Returns field's input / -1 if no number was entered.
     *
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
                // Only for the compiler. This shouldn't happen!
                return -1;
			}
		}
	}

	public void setNumber(int number)
	{
		this.setText(Integer.toString(number));
	}
}
