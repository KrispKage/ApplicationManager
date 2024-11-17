/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.io;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * The test for AppReader
 */
public class AppReaderTest {
	/** The file where applications are being read from */
	private final String validTestFile = "test-files/app1.txt";
	/** Expected results for valid applications in app1.txt - line 1 */	
	private final String validApp1 = "*1,Review,New,Application summary,null,false,\n-[Review] Note 1";
	/** Expected results for valid applications in app1.txt - line 2 */
	private final String validApp2 = "*3,Interview,Old,Application summary,reviewer,false,\n-[Review] Note 1\n-[Interview] Note 2\nthat goes on a new line";
	/** Expected results for valid applications in app1.txt - line 3 */
	private final String validApp3 = "*7,RefCheck,Old,Application summary,reviewer,true,\n-[Review] Note 1\n-[Interview] Note 2\n-[RefCheck] Note 3";
	/** Expected results for valid applications in app1.txt - line 4 */
	private final String validApp4 = "*14,Waitlist,New,Application summary,null,false,ReviewCompleted\n-[Review] Note 1\n-[Waitlist] Note 2\nthat goes on a new line";
	/** Expected results for valid applications in app1.txt - line 5 */
	private final String validApp5 = "*16,Offer,Old,Application summary,reviewer,true,\n-[Review] Note 1\nthat goes on a new line\n-[Interview] Note 2\n-[RefCheck] Note 3\n-[Offer] Note 4";
	/** Expected results for valid applications in app1.txt - line 6 */
	private final String validApp6 = "*15,Closed,Hired,Application summary,reviewer,true,OfferCompleted\n-[Review] Note 1\nthat goes on a new line\n-[Interview] Note 2\n-[RefCheck] Note 3\n-[Offer] Note 4\n-[Closed] Note 6";
	/** An array of all the valid applications in app1 */
	private final String [] validApps = {validApp1, validApp2, validApp3, validApp4,
			validApp5, validApp6};
	
	/**
	 * Tests AppReader
	 */
	@Test
	public void testAppReader() {
		ArrayList<Application> applications = AppReader.readAppsFromFile(validTestFile);
		assertEquals(6, applications.size());
		for (int i = 0; i < validApps.length; i++) {
			assertEquals(validApps[i], applications.get(i).toStringForFile());
		}
	}

}
