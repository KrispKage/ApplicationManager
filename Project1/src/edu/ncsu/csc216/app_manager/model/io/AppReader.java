/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * The main class for the application reader
 */
public class AppReader {
	/**
	 * Gets the applications stored in a file
	 * @param fileName name of the file
	 * @return Application ArrayList
	 * @throws FileNotFoundException  if file can't be processed
	 * @throws IllegalArgumentException if file can't be processed
	 */
	public static ArrayList<Application> readAppsFromFile(String fileName) {
		ArrayList<Application> applications = new ArrayList<Application>();
		String fileString = "";
		try (Scanner fileReader = new Scanner(new FileInputStream(fileName))) {
			while (fileReader.hasNextLine()) {
				fileString = fileString + fileReader.nextLine();
				if (fileReader.hasNextLine()) {
					fileString = fileString + "\n";
				}
			}
		} 
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		Scanner fileParser = new Scanner(fileString);
		String appString = "";
		fileParser.useDelimiter("\\r?\\n?[*]");
		while (fileParser.hasNext()) {
			appString = fileParser.next();
			applications.add(processApplication(appString));
		}
		fileParser.close();
		return applications;
		
	}
	private static Application processApplication(String appString) {
		int count = 0;
		Scanner appSplice = new Scanner(appString);
		appSplice.useDelimiter("\\r?\\n?[-]");
		String appFirstSplice = appSplice.next();
		appSplice.close();
		Scanner appPart = new Scanner(appFirstSplice);
		appPart.useDelimiter(",");
		int id = appPart.nextInt();
		String state = appPart.next();
		String appType = appPart.next();
		String summary = appPart.next();
		String reviewer = appPart.next();
		boolean processPaperwork = appPart.nextBoolean();
		String resolution = "";
		if (appPart.hasNext()) {
			resolution = appPart.next();
		} 
		appPart.close();
		ArrayList<String> notes = new ArrayList<String>();
		Scanner noteParser = new Scanner(appString);
		noteParser.useDelimiter("\r?\n?[-]");
		while(noteParser.hasNext()) {
			if(count == 1) {
				String note = noteParser.next();
				notes.add(note);
			} else {
				String note = noteParser.next();
				notes.remove(note);
				count++;
			}
		}
		Application newApp = new Application(id, state, appType, summary, reviewer, processPaperwork, resolution, notes);
		noteParser.close();
		return newApp;
	}
}
