/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * The main class for the AppWriter
 */
public class AppWriter {
	/**
	 * Writes the specified list of applications to a file
	 * @param fileName		Name of the file
	 * @param applications	List of applications
	 * @throws IllegalArgumentException if file can't be reached or processed
	 */
	public static void writeAppsToFile(String fileName, List<Application> applications) {
		try (PrintStream fileWriter = new PrintStream(new File(fileName))) {
			for (int i = 0; i < applications.size(); i++) {
			    fileWriter.println(applications.get(i).toStringForFile());
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found:" + fileName);
		}
	}
}
