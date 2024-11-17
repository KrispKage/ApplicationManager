/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.manager;


import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.io.AppReader;
import edu.ncsu.csc216.app_manager.model.io.AppWriter;

/**
 * The main class for the AppManager
 */
public class AppManager {
	/** An instance of the AppManager class */
	private static AppManager instance;
	/** A copy of the list of applications in AppList in this instance of AppManager */
	private AppList appList;
	
	private AppManager() {
		this.appList = new AppList();
	}
	/**
	 * Instance method for the AppManager
	 * @return An instance of AppManager
	 */
	synchronized public static AppManager getInstance() {
		if (instance == null) {
            instance = new AppManager();
        }
        return instance;
	}
	/**
	 * Save the applications to a file
	 * @param fileName fileName
	 */
	public void saveAppsToFile(String fileName) {
		AppWriter.writeAppsToFile(fileName, appList.getApps());
	}
	/**
	 * Loads the applications from a specified file
	 * @param fileName fileName
	 */
	public void loadAppsFromFile(String fileName) {
		ArrayList<Application> apps;
		apps = AppReader.readAppsFromFile(fileName);
		appList.addApps(apps);
	}
	/**
	 * Creates a new application list
	 */
	public void createNewAppList() {
		AppList applist = new AppList();
		this.appList = applist;
	}
	/**
	 * Returns the application list as an array
	 * @return array
	 */
	public Object[][] getAppListAsArray() {
		ArrayList<Application> apps = appList.getApps();
		if (apps.isEmpty()) {
			Object[][] appArray = new Object[0][0];
			return appArray;
		}
		else {
			Object[][] appArray = new Object[apps.size()][4]; 
			for (int i = 0; i < apps.size(); i++) {
				Application app = apps.get(i);
				appArray[i][0] = app.getAppId();
				appArray[i][1] = app.getStateName();
				appArray[i][2] = app.getAppType();
				appArray[i][3] = app.getSummary();
			}
			return appArray;
		}
	}
	/**
	 * Returns the application list by type as an array
	 * @param type type
	 * @return String string
	 * @throws IllegalArgumentException if string is null
	 */
	public Object[][] getAppListAsArrayByAppType(String type) {
		ArrayList<Application> apps = appList.getAppsByType(type);
		if (type == null) {
			throw new IllegalArgumentException("Invalid type.");
		} else {
			Object[][] appArray = new Object[apps.size()][4]; 
			for (int i = 0; i < apps.size(); i++) {
				Application app = apps.get(i);
				appArray[i][0] = app.getAppId();
				appArray[i][1] = app.getStateName();
				appArray[i][2] = app.getAppType();
				appArray[i][3] = app.getSummary();
			}
			return appArray;
		}
	}
	/**
	 * Returns the application by their id
	 * @param id app id
	 * @return application list
	 */
	public Application getAppById(int id) {
		return appList.getAppById(id);
	}
	/**
	 * Executes a command
	 * @param id application id
	 * @param command command type
	 */
	public void executeCommand(int id, Command command) {
		appList.executeCommand(id, command);
	}
	/**
	 * Deletes an application by their id
	 * @param id id
	 */
	public void deleteAppById(int id) {
		appList.deleteAppById(id);
	}
	/**
	 * Adds an application to the list by these given parameters
	 * @param type type
	 * @param note note
	 * @param summary summary
	 */
	public void addAppToList(AppType type, String summary, String note) {
		appList.addApp(type, summary, note);
	}
}
