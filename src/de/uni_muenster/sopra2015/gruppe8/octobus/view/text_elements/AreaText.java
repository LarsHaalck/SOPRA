package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class AreaText extends JTextArea
{
	private int limit = 2000;
	private LimitDocument limitDoc;

	public AreaText()
	{
		super();

		setFont(UIManager.getDefaults().getFont("JTextField.font"));
		limitDoc.setLimit(this.limit);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
	}

	@Override
	protected Document createDefaultModel()
	{
		this.limitDoc = new LimitDocument();
		return limitDoc;
	}
}
