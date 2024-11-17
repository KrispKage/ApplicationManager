/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.io;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application;



	

/**
 * The test for the AppManagerTest
 */
public class AppWriterTest {
	/** The expected file to be read from the appWriter */
	private final String expFile = "test-files/exp_app_review.txt";
	/** The actual file that is a result of the AppWriter functionality */
	private final String actFile = "test-files/act_app_review.txt";
	/** An empty file call to call the FileNotFoundException */
	private final String fakeFile = "";
	/** An emtpy list of applications */
	private final List<Application> apps = new ArrayList<Application>();
	
	/**
	 * Tests AppWriter
	 */
	@Test
	public void testAppWriter() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("[Review] note 1");
		Application a = new Application(1, "Review", "New", "Application summary", "", false, "", notes);
		apps.add(a);
		try {
			AppWriter.writeAppsToFile(actFile, apps);
		} catch (RuntimeException ex) {
			fail("Cannot write file");
		}
		checkFiles(expFile, actFile);
	}
	/**
	 * Tests an invalid file being used in AppWriter
	 */
	@Test
	public void testInvalidFile() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("[Review] Note 1");
		notes.add("[Interview] Note 2");
		notes.add("[RefCheck] Note 3");
		Application a = new Application(3, "RefCheck", "Old", "Application summary", "reviewer", true, "", notes);
		apps.add(a);
		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> AppWriter.writeAppsToFile(fakeFile, apps)
				);
		assertTrue(exception.getMessage().contains("File not found:" + fakeFile));
	}
	/**
	 * Private helper method to compare two files with each other
	 * @param expFile the correct file
	 * @param actFile the file that is trying to mimic the correct file
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
