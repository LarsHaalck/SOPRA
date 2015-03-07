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


	public FieldNumber(int width, int limit)
	{
		super();
		this.setColumns(width);
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

	private void setInterval(int intervalMaximum)
	{
		this.addKeyListener(new IntervalKeyAdapter(this, intervalMaximum)
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
				if (getFieldNumber().getNumber() != -1)
				{
					value = getFieldNumber().getNumber() * 10 + i;
				}
				if (value > getIntervalMaximum())
				{
					e.consume(); // ignore event
				}
			}
		});
	}

	private class IntervalKeyAdapter extends KeyAdapter
	{
		FieldNumber fieldNumber;
		private int intervalMaximum;

		public IntervalKeyAdapter(FieldNumber fieldNumber,  int intervalMaximum)
		{
			super();
			this.fieldNumber = fieldNumber;
			this.intervalMaximum = intervalMaximum;
		}

		public FieldNumber getFieldNumber()
		{
			return fieldNumber;
		}

		public int getIntervalMaximum()
		{
			return intervalMaximum;
		}
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
