/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;

/**
 * The main class for the application list
 */
public class AppList {
	/** The value of the next application in the list */
	private int counter;
	/** The ArrayList of applications that is contained within AppList */
	private ArrayList<Application> apps;
	/**
	 * The constructor for the AppList class
	 */
	public AppList() {
		apps = new ArrayList<Application>();
		counter = 0; 
	}
	/**
	 * Adds the application to the list using it's type, state, and summary
	 * @param type			The application's type
	 * @param note			The application's note
	 * @param summary		The application's summary
	 * @return number
	 */
	public int addApp(AppType type, String summary, String note) {
		AppType appType = type;
		String appNote = note;
		String appSummary = summary;
		if (apps.size() == 0) {
			counter++;
		} else {
			counter = apps.get(apps.size() - 1).getAppId() + 1;
		}
		Application newApp = new Application(counter, appType, appSummary, appNote);
		addApp(newApp);
		return counter;
	}
	
	/**
	 * Adds applications from an already provided list
	 * @param list list
	 */
	public void addApps(ArrayList<Application> list) {
		for (Application app : list) {
			addApp(app);
		}
		counter = apps.get(apps.size() - 1).getAppId() + 1;
	}
	
	private void addApp(Application application) {
		boolean appAdded = false;
		boolean isDuplicate = false;
		if (apps.isEmpty()) {
			apps.add(application);
		} else {
			for (Application app : apps) {
				if (application.getAppId() == app.getAppId() ) {
					isDuplicate = true;
				}
				if (application.getAppId() < app.getAppId()) {
					apps.add(apps.indexOf(app), application);
					appAdded = true;
					break;
				}
			}
			if (!appAdded && !isDuplicate) {
				apps.add(application);
			}
		}
	}

	/**
	 * Returns the application list
	 * @return list list
	 */
	public ArrayList<Application> getApps() {
		return apps;
	}
	/**
	 * Returns the application list by their type
	 * @param type The application's type
	 * @return list list
	 */
	public ArrayList<Application> getAppsByType(String type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Application> newList = new ArrayList<Application>();
		for (Application app : getApps()) {
			if (app.getAppType().equals(type)) {
				newList.add(app);
			}
		}
		return newList;
	}
	/**
	 * Returns the application by its id
	 * @param id id
	 * @return Application
	 */
	public Application getAppById(int id) {
		for (Application app : apps) {
			if (app.getAppId() == id) {
				return app;
			}
		}
		return null;
	}
	/**
	 * Executes the command on an application specified by the id
	 * @param id id
	 * @param command command
	 */
	public void executeCommand(int id, Command command) {
		if (apps.isEmpty()) {
			return;
		} else {
			Application app = getAppById(id);
			app.update(command);
		}
	}
	/**
	 * Deletes an application based on its id
	 * @param id id
	 */
	public void deleteAppById(int id) {
		if (apps.isEmpty()) {
			return;
		} else {
			int max = apps.get(apps.size() - 1).getAppId();
			if (id > 0 && id < max) {
				int index = 0;
				for (Application app : apps) {
					if (app.getAppId() == id) {
						index = apps.indexOf(app);
					}
				}
				apps.remove(index);
			} else {
				return;
			}
		}
	}
}
