package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormJourneySearch;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Steen Sziegaud.
 */
public class FormJourneySearch extends FormGeneral
{
    //Konstants
    final private Dimension textFieldSize = new Dimension(WIDTH/2 - 10, 23);
    final private Dimension maxGridDimensions = new Dimension(WIDTH/2 - 10, 270);
    final private int halfDefaultWidth = WIDTH/2;

    //Controller
    ControllerFormJourneySearch controllerFormJourneySearch;

    //ParentJPanels for left and right side.
    private JPanel rightParentGridPanel;

    //Components
    private JButton searchButton;
    private JButton earlierButton;
    private JButton firstButton;
    private JButton laterButton;
    private JButton lastButton;
    private JTextField originTextField;
    private JTextField destinationTextField;
    private JComboBox<String> dateSelection;
    private JComboBox<Integer> hourSelection;
    private JComboBox<Integer> minuteSelection;
    private JTable journeySearchResultTable;
    private JScrollPane tableScrollPane;
    private JPanel selectedJourney;

    //Variables
    //Gets filled with possible lines that are heading from start to destination
    private ArrayList<Object> fastLinesFromOriginToDestination;
    //private Calendar cal;

    //private JButton

    //TODO: Add functionality to buttons.
    public FormJourneySearch(Frame parent)
    {
        super(parent, "Verbindung suchen");

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        controllerFormJourneySearch = new ControllerFormJourneySearch(this);

        initComponents();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        add(createLeftGridPanel());
        rightParentGridPanel = createTransparentRightGridPanel();
        add(rightParentGridPanel);
        setResizable(false);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void initComponents()
    {
        //Panel to contain components for the right hand side.
        rightParentGridPanel = new JPanel();

        //Button to make a searchrequest
        searchButton = new JButton("Suchen");
        searchButton.addActionListener(e ->
                controllerFormJourneySearch.buttonPressed(EmitterButton.FORM_JOURNEY_SEARCH_SEARCH));

        //Button to show (add) earlier results in the journeytable
        earlierButton = new JButton("Früher");
        earlierButton.addActionListener(e ->
                controllerFormJourneySearch.buttonPressed(EmitterButton.FORM_JOURNEY_SEARCH_EARLIER));

        //Button to show (add) the earliest journey in the journeytable.
        firstButton = new JButton("Erster Fahrt");
        firstButton.addActionListener(e ->
                controllerFormJourneySearch.buttonPressed(EmitterButton.FORM_JOURNEY_SEARCH_FIRST));

        //Analog to earlier
        laterButton = new JButton("Später");
        laterButton.addActionListener(e ->
                controllerFormJourneySearch.buttonPressed(EmitterButton.FORM_JOURNEY_SEARCH_LATER));

        //Analog to first
        lastButton = new JButton("Letzte Fahrt");
        lastButton.addActionListener(e ->
                controllerFormJourneySearch.buttonPressed(EmitterButton.FORM_JOURNEY_SEARCH_LAST));

        //Comboboxes to choose date and time.
        //TODO: Add listeners and functionality
        //TODO: Implement dateSelection?

        //dateSelection = new JComboBox<>();
        hourSelection = new JComboBox<>();
        minuteSelection = new JComboBox<>();


        //JPanel to display details about the selected
        selectedJourney = new JPanel();


        originTextField = new JTextField(20);
        originTextField.setToolTipText("Starthaltestelle eingeben");
        destinationTextField = new JTextField(20);
        destinationTextField.setToolTipText("Zielhaltestelle eingeben");


        createTable();
    }


    private void createTable()
    {
        String[] columnNames = {"Fahrt", "Abfahrt", "Ankunft", "Dauer", "Umsteigen"};
        String[][] data = {{"1. Fahrt", "17:05", "17:25", "00:20", "0x"},
                {"2. Fahrt", "17:08", "17:30", "00:22", "0x"}};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };


        //Let's the user only select one row at time
        class ForcedListSelectionModel extends DefaultListSelectionModel
        {
            public ForcedListSelectionModel () {
                setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }

            @Override
            public void clearSelection() {
            }

            @Override
            public void removeSelectionInterval(int index0, int index1) {
            }
        }

        journeySearchResultTable = new JTable(tableModel);
        journeySearchResultTable.setSelectionModel(new ForcedListSelectionModel());
        journeySearchResultTable.getSelectionModel().addListSelectionListener(e ->
                controllerFormJourneySearch.tableSelectionChanged(EmitterTable.FORM_JOURNEY_SEARCH_RESULT));
        journeySearchResultTable.setPreferredSize(new Dimension(512, 400));
        tableScrollPane = new JScrollPane(journeySearchResultTable);
        tableScrollPane.setPreferredSize(new Dimension(halfDefaultWidth - 10, 494));
        tableScrollPane.setMaximumSize(new Dimension(halfDefaultWidth - 10, 494));
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
        originTextField.setPreferredSize(textFieldSize);
        leftGridPanel.add(originTextField, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        leftGridPanel.add(new Label("Nach:"), c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        destinationTextField.setPreferredSize(textFieldSize);
        leftGridPanel.add(destinationTextField, c);


        //DateSelectionPanel - Date: DAY.MONTH.YEAR HOURS:MINUTES
        JPanel dateSelectionPanel = new JPanel();

        dateSelectionPanel.setLayout(new BoxLayout(dateSelectionPanel, BoxLayout.X_AXIS));
        dateSelectionPanel.setPreferredSize(new Dimension(halfDefaultWidth - 100, 23));


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        leftGridPanel.add(new JLabel("Datum:"), c);

        //String[] dummyCal = {"1.Januar.2015", "2.Januar.2013"};
        //dateSelection = new JComboBox<>(dummyCal);



        Integer[] hours = new Integer[24];
        for (int i = 0; i < hours.length; i++)
            hours[i] = i;
        Integer[] minutes = new Integer[61];
        for (int i = 0; i < minutes.length; i++)
            minutes[i] = i;

        hourSelection = new JComboBox<>(hours);
        hourSelection.setSelectedIndex(Calendar.HOUR_OF_DAY);
        minuteSelection = new JComboBox<>(minutes);
        minuteSelection.setSelectedIndex(Calendar.MINUTE);


        //dateSelectionPanel.add(dateSelection);
        dateSelectionPanel.add(hourSelection);
        dateSelectionPanel.add(minuteSelection);

        //Add the dateSlectionPanel created above to the leftGridPanel.
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        leftGridPanel.add(dateSelectionPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        leftGridPanel.add(searchButton, c);



        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 2;
        selectedJourney.setPreferredSize(new Dimension(halfDefaultWidth - 110, 320));
        selectedJourney.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftGridPanel.add(selectedJourney, c);


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
        JPanel rightTransparent = new JPanel();
        rightTransparent.setPreferredSize(new Dimension(halfDefaultWidth - 10, 542));
        rightTransparent.setBorder(BorderFactory.createEmptyBorder());
        rightParentGridPanel.add(rightTransparent);


        rightParentGridPanel.setVisible(true);

        return rightParentGridPanel;
    }


    /**
     *
     */
    public void modifyRightGridPanel()
    {
        rightParentGridPanel.removeAll();
        rightParentGridPanel.setLayout(new BoxLayout(rightParentGridPanel, BoxLayout.Y_AXIS));


        //Earlier and later Buttons to look for earlier/later Journeys
        JPanel earlierButtonsPanel = new JPanel();
        earlierButtonsPanel.setLayout(new BoxLayout(earlierButtonsPanel, BoxLayout.X_AXIS));
        earlierButtonsPanel.add(earlierButton);
        earlierButtonsPanel.add(firstButton);


        JPanel laterButtonsPanel = new JPanel();
        laterButtonsPanel.setLayout(new BoxLayout(laterButtonsPanel, BoxLayout.X_AXIS));
        laterButtonsPanel.add(laterButton);
        laterButtonsPanel.add(lastButton);


        JPanel rightTransparent = new JPanel();
        rightTransparent.setPreferredSize(new Dimension(halfDefaultWidth - 10, 542));
        rightTransparent.setBorder(BorderFactory.createEmptyBorder());
        rightParentGridPanel.add(earlierButtonsPanel);
        rightParentGridPanel.add(tableScrollPane);
        rightParentGridPanel.add(laterButtonsPanel);
        rightParentGridPanel.revalidate();
        rightParentGridPanel.repaint();
        rightParentGridPanel.setVisible(true);

        //Add components to new rightParent

        rightParentGridPanel.revalidate();
        rightParentGridPanel.repaint();
    }

    public void displayInformationInBox(String[] journey)
    {
        JTextPane textPane = new JTextPane();
        selectedJourney.removeAll();

    }

    public JPanel getRightParentGridPanel()
    {
        return rightParentGridPanel;
    }

    public JTable getJourneySearchResultTable()
    {
        return journeySearchResultTable;
    }

    public String getOrigin()
    {
        return originTextField.getText();
    }

    public String getDestination()
    {
        return destinationTextField.getText();
    }


}
