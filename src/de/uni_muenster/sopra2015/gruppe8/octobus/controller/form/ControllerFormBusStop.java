package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBusStop;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Controller for FormBusStop class.
 */
public class ControllerFormBusStop extends Controller implements ListenerButton
{
	private FormBusStop formBusStop;
	ControllerDatabase controllerDatabase;
	BusStop busStop;
    ArrayList<StoppingPoint> originalStoppingPoints;
	ArrayList<StoppingPoint> alteredStoppingPoints;
	int objectID;

	public ControllerFormBusStop(FormBusStop formBusStop, int objectID)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.formBusStop = formBusStop;
		this.objectID = objectID;
		//Sets global bus stop to the bus given in objectID
		if(objectID != -1)
		{
			setBusInfo();
            setStoppingPointInfo();
		}
	}

    @Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_BUS_STOP_SAVE:
				//Check if input is valid
				if(parseValuesFromForm())
				{
					formBusStop.setCursor(true);
					//
					if (saveToDB())
					{
						ControllerManager.informTableContentChanged(EmitterTable.TAB_BUSSTOP);
						ControllerManager.informTableContentChanged(EmitterTable.TAB_NETWORK);
						ControllerManager.informTableContentChanged(EmitterTable.TAB_WORKPLAN);
						closeDialog();
					}
					formBusStop.setCursor(false);
				}
				break;

			case FORM_BUS_STOP_CANCEL:
				closeDialog();
				break;

			case FORM_BUS_STOP_ADD_POINT:
				while(true)
				{
					String newAnswer = formBusStop.showNewStoppingPointDialog();
					if (newAnswer != null)
					{
						if (newAnswer.length() == 0)
						{
							formBusStop.showErrorForm("Bitte geben Sie einen Namen für den Haltepunkt ein.");
						} else
						{
							formBusStop.addStoppingPoint(newAnswer);
							break;
						}
					}
					else
					{
						break;
					}
				}
				break;

			case FORM_BUS_STOP_DELETE_POINT:
				if(formBusStop.getSelectedStoppingPoint() == -1)
					break;
				if(formBusStop.showDeleteStoppingPointDialog() == JOptionPane.YES_OPTION)
				{
					ArrayList<Route> routes = controllerDatabase.getRoutesUsingStoppingPoint(formBusStop.getSelectedStoppingPoint());
					if (routes.size() == 0)
						formBusStop.removeStoppingPoint(formBusStop.getSelectedStoppingPoint());
					else
					{
						String routesNames = "";
						routesNames += routes.get(0).getName();
						for (int i=1; i<routes.size(); i++)
						{
							routesNames += "\n"+routes.get(i).getName();
						}

						formBusStop.showErrorForm("Haltepunkt kann nicht gelöscht werden, da er in folgenden Routen noch verwendet wird: \n"+routesNames);
					}
				}
				break;

			case FORM_BUS_STOP_EDIT_POINT:
				//Don't do anything if no stopping point is selected
				if(formBusStop.getSelectedStoppingPoint() == -1)
					break;
				while(true)
				{
					String newAnswer = formBusStop.showEditStoppingPointDialog(formBusStop.getSelectedStoppingPointName());
					if (newAnswer != null)
					{
						if (newAnswer.length() == 0)
						{
							formBusStop.showErrorForm("Bitte geben Sie einen Namen für den Haltepunkt ein.");
						} else
						{
							formBusStop.editStoppingPoint(formBusStop.getSelectedRow(), newAnswer);
							break;
						}
					}
					else
					{
						break;
					}
				}
				break;
		}
	}
	/**
	 * Fetch a BusStop object from the DB.
	 */
	private void setBusInfo()
	{
		busStop = controllerDatabase.getBusStopById(objectID);
	}

    /**
     * Fetch a list of stopping point for the BusStop
     */
    private void setStoppingPointInfo()
    {
        originalStoppingPoints = ControllerDatabase.getInstance().getStoppingPointsByBusStopId(busStop.getId());
    }

	/**
	 * Inserts the values of the BusStop which is going to
	 * be changed into the form.
	 */
	public void insertValuesIntoForm()
	{
		if(objectID != -1)
		{
			formBusStop.setNameBusStop(busStop.getName());
			formBusStop.setLocationX(busStop.getLocation().getFirst());
			formBusStop.setLocationY(busStop.getLocation().getSecond());

			for(StoppingPoint p: originalStoppingPoints)
				formBusStop.addStoppingPoint(p.getId(), p.getName());
			formBusStop.setBarrierFree(busStop.isBarrierFree());
		}
	}

	/**
	 *Checks if form input is correct and adds values to local busStop.
     *
	 * @return returns true on correct input
	 */
	private boolean parseValuesFromForm()
	{
		if(objectID == -1)
		{
			busStop = new BusStop();
		}

		String name = formBusStop.getNameBusStop();
		int x = formBusStop.getLocationX();
		int y = formBusStop.getLocationY();

		alteredStoppingPoints = formBusStop.getStoppingPoints();
		boolean barrierFree = formBusStop.getBarrierFree();

		ArrayList<String> errorFields = new ArrayList<>();
		if(name == null)
			errorFields.add("Ungültige Eingabe des Namen. Es wurden illegale Zeichen verwendet.");
		else if(name.trim().length() == 0)
			errorFields.add("Das Kennzeichen darf nicht leer sein.");
		else if(name.trim().length() < 2)
			errorFields.add("Der Name muss mindestens 2 Zeichen umfassen.");
		if(x == -1)
			errorFields.add("Die X Koordinate darf nicht leer sein.");
		if(y == -1)
			errorFields.add("Die Y Koordinate darf nicht leer sein.");
		if(alteredStoppingPoints.isEmpty())
			errorFields.add("Es muss mindestens ein Haltepunkt existieren");

		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorFields);
			formBusStop.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			busStop.setName(name);
			busStop.setLocation(new Tuple<>(x, y));
			busStop.setBarrierFree(barrierFree);
			return true;
		}
	}

	/**
	 * Saves the current busStop to the DB.
	 * @return true when saved
	 */
	private boolean saveToDB()
	{
		if(objectID == -1)
		{
			HashSet<StoppingPoint> hashSet = new HashSet<>();
			for (StoppingPoint stoppingPoint : alteredStoppingPoints)
			{
				hashSet.add(stoppingPoint);
			}
			busStop.setStoppingPoints(hashSet);
			controllerDatabase.addBusStop(busStop);
		}
		else
        {
            // This only modifies data pertaining to the BusStop itself. StoppingPoints need
            // to be handled separately.
            controllerDatabase.modifyBusStop(busStop);

            // Handle stopping points
            for (StoppingPoint s : alteredStoppingPoints)
            {
                // Add entries for newly created stopping points
                if (s.getId() <= -2)
                    controllerDatabase.addStoppingPoint(busStop.getId(), s);
                // Modify stopping points that already have entries in the database.
                // Yes, this does overwrite unmodified entries with the same data.
                else
                    controllerDatabase.modifyStoppingPoint(s);
            }

            // Remove all altered stopping points (which have already been handled) from the original list.
            // Those entries that remain must therefore be slated for deletion from the database.
            originalStoppingPoints.removeAll(alteredStoppingPoints);

            // Delete stopping points that remained in the original list (see lines above for more info).
            for (StoppingPoint s : originalStoppingPoints)
            {
               controllerDatabase.deleteStoppingPointById(s.getId());
            }
        }

		return true;
	}

	@Override
	protected  void addListeners()
	{
		ControllerManager.addListener(this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener(this);
	}

	/**
	 * Closes current dialog.
	 */
	private void closeDialog()
	{
		formBusStop.dispose();
		removeListeners();
	}
}
