package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.display.ControllerDisplaySearchConnection;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelSearchConnection;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldDate;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Used to display connections.
 */
public class DisplaySearchConnection extends JPanel
{
    //Constants
	final private int WIDTH = 1000;
    final private Dimension maxGridDimensions = new Dimension(WIDTH/2 - 10, 270);
    final private int halfDefaultWidth = WIDTH/2;

    //Controller
    ControllerDisplaySearchConnection controllerDisplaySearchConnection;

    //ParentJPanels for left and right side.
    private JPanel rightParentGridPanel;

    //Components
	private JButton btnBack;
    private JButton btnSearch;
    private JButton btnEarlier;
    private JButton btnFirst;
    private JButton btnLater;
    private JButton btnLast;
    private JButton btnSelectOrigin;
    private JButton btnSelectDestination;
    private JComboBox<TupleIntString> cbOriginSelection;
    private JComboBox<TupleIntString> cbDestinationSelection;
    private FieldDate fieldDate;
    private JComboBox<Integer> cbHourSelection;
    private JComboBox<Integer> cbMinuteSelection;
    private JTable tableSearchResults;
    private JScrollPane scrollPaneTable;
    private JPanel panelSelectedConnection;
    private JTextPane formattedConnectionDisplay;
    private JPanel rightTransparent;
    private int tableHeight;

    //Variables
    //Gets filled with possible lines that are heading from start to destination
    private TupleIntString[] busStops;
    //private Calendar cal;



    public DisplaySearchConnection()
    {
        controllerDisplaySearchConnection = new ControllerDisplaySearchConnection(this);
		JPanel display = new JPanel();

        initComponents();

		display.setLayout(new GridLayout(0, 2));
        display.add(createLeftGridPanel());
        rightParentGridPanel = createTransparentRightGridPanel();
        display.add(rightParentGridPanel);

		add(display, BorderLayout.CENTER);

		btnBack = new JButton("Zurück");
		btnBack.addActionListener(e-> controllerDisplaySearchConnection.buttonPressed(EmitterButton.DISPLAY_CONNECTION_BACK));

		JPanel plButton = new JPanel();
		plButton.setLayout(new BorderLayout());
		plButton.add(btnBack, BorderLayout.EAST);
		add(plButton, BorderLayout.PAGE_END);
    }


    /**
     * Initialises all components.
     */
    public void initComponents()
    {
        rightParentGridPanel = new JPanel();


        btnSearch = new JButton("Suchen");
        btnSearch.addActionListener(e ->
				controllerDisplaySearchConnection.buttonPressed(EmitterButton.DISPLAY_CONNECTION_SEARCH));


        btnEarlier = new JButton("Früher");
        btnEarlier.addActionListener(e ->
				controllerDisplaySearchConnection.buttonPressed(EmitterButton.DISPLAY_CONNECTION_EARLIER));


        btnFirst = new JButton("Erste Fahrt");
        btnFirst.addActionListener(e ->
				controllerDisplaySearchConnection.buttonPressed(EmitterButton.DISPLAY_CONNECTION_FIRST));


        btnLater = new JButton("Später");
        btnLater.addActionListener(e ->
				controllerDisplaySearchConnection.buttonPressed(EmitterButton.DISPLAY_CONNECTION_LATER));

        btnLast = new JButton("Letzte Fahrt");
        btnLast.addActionListener(e ->
				controllerDisplaySearchConnection.buttonPressed(EmitterButton.DISPLAY_CONNECTION_LAST));


        cbHourSelection = new JComboBox<>();
        cbMinuteSelection = new JComboBox<>();


        cbOriginSelection = new JComboBox<>(busStops);

        cbDestinationSelection = new JComboBox<>(busStops);

        panelSelectedConnection = new JPanel();


        fieldDate = new FieldDate();
        fieldDate.setDate(Calendar.getInstance().getTime());


        createTable();
    }

    /**
     * Creates the table which will contain search results.
     */
    private void createTable()
    {
        LinkedList<Connection> data = new LinkedList<>();
        TableModelSearchConnection tableModel = new TableModelSearchConnection(data);



        tableSearchResults = new JTable(tableModel);
        tableSearchResults.getSelectionModel().setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        tableSearchResults.getSelectionModel().addListSelectionListener(e -> {
            controllerDisplaySearchConnection.tableSelectionChanged(EmitterTable.FORM_JOURNEY_SEARCH_RESULT);
        });
        tableHeight = 470;
        tableSearchResults.setPreferredSize(new Dimension(halfDefaultWidth - 10, 470));
        scrollPaneTable = new JScrollPane(tableSearchResults);
        scrollPaneTable.setPreferredSize(new Dimension(halfDefaultWidth - 10, 494));
    }


    /**
     * Creates the left side of the panel. With both searchfields and a dateselection.
     * @return the left side panel
     */
    private JPanel createLeftGridPanel()
    {
        JPanel leftGridPanel = new JPanel();


        leftGridPanel.setLayout(new GridBagLayout());
        leftGridPanel.setPreferredSize(maxGridDimensions);
        GridBagConstraints c = new GridBagConstraints();


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        leftGridPanel.add(new Label("Von:"), c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        leftGridPanel.add(cbOriginSelection, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        leftGridPanel.add(new Label("Nach:"), c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        leftGridPanel.add(cbDestinationSelection,c );


        //DateSelectionPanel - Date: DAY.MONTH.YEAR HOURS:MINUTES
        JPanel dateSelectionPanel = new JPanel();

        dateSelectionPanel.setLayout(new BoxLayout(dateSelectionPanel, BoxLayout.X_AXIS));
        dateSelectionPanel.setPreferredSize(new Dimension(halfDefaultWidth - 100, 23));


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        leftGridPanel.add(new JLabel("Abfahrt um:"), c);



        Integer[] hours = new Integer[24];
        for (int i = 0; i < hours.length; i++)
            hours[i] = i;
        Integer[] minutes = new Integer[60];
        for (int i = 0; i < minutes.length; i++)
            minutes[i] = i;

        Calendar cal = Calendar.getInstance();

        cbHourSelection = new JComboBox<>(hours);
        cbHourSelection.setSelectedIndex(cal.get(Calendar.HOUR_OF_DAY));
        cbMinuteSelection = new JComboBox<>(minutes);
        cbMinuteSelection.setSelectedIndex(cal.get(Calendar.MINUTE));


        dateSelectionPanel.add(fieldDate);
        dateSelectionPanel.add(cbHourSelection);
        dateSelectionPanel.add(cbMinuteSelection);

        //Add the dateSlectionPanel created above to the leftGridPanel.
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        leftGridPanel.add(dateSelectionPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        leftGridPanel.add(btnSearch, c);



        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 2;
        panelSelectedConnection.setPreferredSize(new Dimension(halfDefaultWidth - 110, 360));
        panelSelectedConnection.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftGridPanel.add(panelSelectedConnection, c);



        return leftGridPanel;
    }

    /**
     * Creates the right side panel. With a few buttons to look for earlier and later journeys and the table
     * containing each found table.
     * @return the right side panel.
     */
    private JPanel createTransparentRightGridPanel()
    {
        rightParentGridPanel = new JPanel();

        //The transparent border.
        rightTransparent = new JPanel();
        rightTransparent.setPreferredSize(new Dimension(halfDefaultWidth - 10, 542));
        rightTransparent.setBorder(BorderFactory.createEmptyBorder());
        rightParentGridPanel.add(rightTransparent);


        rightParentGridPanel.setVisible(true);

        return rightParentGridPanel;
    }


    /**
     * Modifies the right panel to show the table.
     */
    public void modifyRightGridPanel()
    {
        rightParentGridPanel.removeAll();
        rightParentGridPanel.setLayout(new BoxLayout(rightParentGridPanel, BoxLayout.Y_AXIS));


        //Earlier and later Buttons to look for earlier/later Journeys
        JPanel earlierButtonsPanel = new JPanel();
        earlierButtonsPanel.setLayout(new BoxLayout(earlierButtonsPanel, BoxLayout.X_AXIS));
        earlierButtonsPanel.add(btnEarlier);
        earlierButtonsPanel.add(btnFirst);


        JPanel laterButtonsPanel = new JPanel();
        laterButtonsPanel.setLayout(new BoxLayout(laterButtonsPanel, BoxLayout.X_AXIS));
        laterButtonsPanel.add(btnLater);
        laterButtonsPanel.add(btnLast);


        JPanel rightTransparent = new JPanel();
        rightTransparent.setPreferredSize(new Dimension(halfDefaultWidth - 10, 542));
        rightTransparent.setBorder(BorderFactory.createEmptyBorder());
        rightParentGridPanel.add(earlierButtonsPanel);
        rightParentGridPanel.add(scrollPaneTable);
        rightParentGridPanel.add(laterButtonsPanel);
        rightParentGridPanel.revalidate();
        rightParentGridPanel.repaint();
        rightParentGridPanel.setVisible(true);

        //Add components to new rightParent

        rightParentGridPanel.revalidate();
        rightParentGridPanel.repaint();
    }

    /**
     * Removes the content of the rightGridPanel
     */
    public void removeRightGridPanel(){
        rightParentGridPanel.removeAll();
        rightParentGridPanel.revalidate();
        rightParentGridPanel.repaint();
        rightParentGridPanel.repaint();
        rightParentGridPanel.revalidate();


        //The transparent border.
        rightTransparent = new JPanel();
        rightTransparent.setPreferredSize(new Dimension(halfDefaultWidth - 10, 542));
        rightTransparent.setBorder(BorderFactory.createEmptyBorder());
        rightParentGridPanel.add(rightTransparent);


        rightParentGridPanel.repaint();
        rightParentGridPanel.revalidate();


    }

    /**
     * Adds an element to the tableModel and positions at the bottom of the list.
     * @pre foundConnection is not null and filled with correct data
     * @param foundConnection connection which will be added.
     */
    public void addLastConnectionAndUpdateTable(Connection foundConnection)
    {
        ((TableModelSearchConnection)tableSearchResults.getModel()).addLastConnection(foundConnection);

        tableHeight();

        scrollPaneTable.revalidate();
        scrollPaneTable.repaint();
    }

    /**
     * Adds an element to the tableModel and positions it at the top of the list.
     * @pre foundConnection is not null and filled with correct data
     * @param foundConnection connection which will be added.
     */
    public void addFirstConnectionAndUpdateTable(Connection foundConnection)
    {
        ((TableModelSearchConnection)tableSearchResults.getModel()).addFirstConnection(foundConnection);

        tableHeight();

        scrollPaneTable.revalidate();
        scrollPaneTable.repaint();
    }

    /**
     * Shows information about this currentSelectedConnection inside the textPane.
     * @pre currentSelectedConnection is filled with correct data
     * @param currentSelectedConnection connection whose information should be shown inside
     *                                  the textPane.
     */
    public void showSelectedConnection(String currentSelectedConnection){
        if (formattedConnectionDisplay == null)
        {
            formattedConnectionDisplay = new JTextPane();
            formattedConnectionDisplay.setPreferredSize(new Dimension(halfDefaultWidth - 30, 340));
            formattedConnectionDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JScrollPane scrollPaneArea = new JScrollPane(formattedConnectionDisplay);
            panelSelectedConnection.add(scrollPaneArea);
            panelSelectedConnection.revalidate();
            panelSelectedConnection.repaint();
        }
        StyledDocument doc = formattedConnectionDisplay.getStyledDocument();

        try {
            doc.remove(0, doc.getLength());
            doc.insertString(0, currentSelectedConnection, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes content from the textPane
     */
    public void removeTextPaneInformation()
    {
        StyledDocument doc = formattedConnectionDisplay.getStyledDocument();

        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the table filled with the connections
     * @return the result table
     */
    public JTable getTableSearchResults()
    {
        return tableSearchResults;
    }

    /**
     * Returns the comboBox which specifies the origin
     * @return the comboBox specifying the origin
     */
    public JComboBox<TupleIntString> getOrigin()
    {
        return cbOriginSelection;
    }

    /**
     * Returns the comboBox which specifies the destination
     * @return comboBox specifying the destination
     */
    public JComboBox<TupleIntString> getDestination()
    {
        return cbDestinationSelection;
    }

    public int getTime() { return cbHourSelection.getSelectedIndex() * 60 + cbMinuteSelection.getSelectedIndex(); }

    /**
     * Fills the array used by the comboBoxes with busStops
     * @param arrayBusStops array of busStops with their ID and name that will be saved
     */
    public void fillBusStops(TupleIntString[] arrayBusStops)
    {
        this.busStops = arrayBusStops;
    }

    /**
     * Parses the Calendar.Day_Of_Week specified by the fieldDate into a DayOfWeek
     * @return the DayOfWeek specified by the fieldDate
     */
    public DayOfWeek getDayOfWeek()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fieldDate.getDate());

        switch (cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH).toUpperCase())
        {
            case "MONDAY":
                return DayOfWeek.MONDAY;
            case "TUESDAY":
                return DayOfWeek.TUESDAY;
            case "WEDNESDAY":
                return DayOfWeek.WEDNESDAY;
            case "THURSDAY":
                return DayOfWeek.THURSDAY;
            case "FRIDAY":
                return DayOfWeek.FRIDAY;
            case "SATURDAY":
                return DayOfWeek.SATURDAY;
            case "SUNDAY":
                return DayOfWeek.SUNDAY;
        }

        return null;
    }

    public void tableHeight()
    {
        tableHeight = tableHeight + 10;
        tableSearchResults.setPreferredSize(new Dimension(halfDefaultWidth - 10, tableHeight));
    }
}
