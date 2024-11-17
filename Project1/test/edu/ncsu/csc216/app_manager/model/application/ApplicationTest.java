/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.application;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

/**
 * The test for Application
 */
class ApplicationTest {
	/** Id for the tested Application */
	private static final int APP_ID = 2;
	/** Type for the tested Application */
	private static final AppType APP_TYPE = AppType.NEW;
	/** Type for the tested Application as a string */
	private static final String TYPE_STRING = Application.A_NEW;
	/** Summary for the tested Application */
	private static final String SUMMARY = "This application is being tested";
	/** Note for the tested Application */
	private static final String NOTE = "Test application";
	/** State for the tested Application */
	private static final String STATE = Application.REVIEW_NAME;
	/** Reviewer for the tested Application */
	private static final String REVIEWER = "";
	/** Processed check for the tested Application */
	private static final boolean PROC_PAPER = false;
	/** Resolution for the tested Application */
	private static final String RESOLUTION = null;
	/** Notes ArrayList for the tested Application */
	public static final ArrayList<String> NOTES = new ArrayList<>();

	
	/**
	 * Tests using the shorter application constructor
	 */
	@Test
	public void testValidShortApp() {
		Application a = new Application(APP_ID, APP_TYPE, SUMMARY, NOTE);
		assertEquals(APP_ID, a.getAppId());
		assertEquals(TYPE_STRING, a.getAppType());
		assertEquals(SUMMARY, a.getSummary());
	}
	/**
	 * Tests using the longer application constructor
	 */
	@Test
	public void testValidLongApp() {
		NOTES.add(NOTE);
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals(APP_ID, a.getAppId());
		assertEquals(STATE, a.getStateName());
		assertEquals(TYPE_STRING, a.getAppType());
		assertEquals(SUMMARY, a.getSummary());
		assertEquals(null, a.getReviewer());
		assertEquals(PROC_PAPER, a.isProcessed());
		assertEquals(RESOLUTION, a.getResolution());
		assertEquals(NOTES, a.getNotes());
	}
	/**
	 * Tests the different resolutions an application can have
	 */
	@Test
	public void testResolutions() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, Command.R_INTCOMPLETED, NOTES);
		assertEquals(Command.R_INTCOMPLETED, a.getResolution());
		Application b = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, Command.R_OFFERCOMPLETED, NOTES);
		assertEquals(Command.R_OFFERCOMPLETED, b.getResolution());
		Application c = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, Command.R_REFCHKCOMPLETED, NOTES);
		assertEquals(Command.R_REFCHKCOMPLETED, c.getResolution());
		Application d = new Application(APP_ID, "Waitlist", TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, Command.R_REVCOMPLETED, NOTES);
		assertEquals(Command.R_REVCOMPLETED, d.getResolution());
	}
	/**
	 * Tests the different types an application can have
	 */
	@Test 
	public void testTypes() {
		NOTES.add(NOTE);
		Application a = new Application(APP_ID, STATE, Application.A_HIRED, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals(Application.A_HIRED, a.getAppType());
		Application b = new Application(APP_ID, STATE, Application.A_OLD, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals(Application.A_OLD, b.getAppType());
	}
	/**
	 * Tests the different states an application can be in
	 */
	@Test 
	public void testStates() {
		Application a = new Application(15, "Closed", "Hired", "Application summary", "reviewer", true, "OfferCompleted", NOTES);
		assertEquals(Application.CLOSED_NAME, a.getStateName());
		Application b = new Application(3, "Interview", "Old", "Application summary", "reviewer", false, "", NOTES);
		assertEquals(Application.INTERVIEW_NAME, b.getStateName());
		Application c = new Application(16, "Offer", "Old", "Application summary", "reviewer", true, "", NOTES);
		assertEquals(Application.OFFER_NAME, c.getStateName());
		Application d = new Application(7, "RefCheck", "Old", "Application summary", "reviewer", true, "", NOTES);
		assertEquals(Application.REFCHK_NAME, d.getStateName());
		Application e = new Application(14, "Waitlist", "New", "Application summary", "", false, "ReviewCompleted", NOTES);
		assertEquals(Application.WAITLIST_NAME, e.getStateName());
		Application f = new Application(1, "Review", "New", "Application summary", "", false, "", NOTES);
		assertEquals(Application.REVIEW_NAME, f.getStateName());
		
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath1() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals("Review", a.getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Interview");
		a.update(c1);
		assertEquals("Interview", a.getStateName());
		Command c2 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Reference Check");
		a.update(c2);
		assertEquals("RefCheck", a.getStateName());
		Command c3 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Offer");
		a.update(c3);
		assertEquals("Offer", a.getStateName());
		Command c4 = new Command(CommandValue.ACCEPT, "cvedward", Resolution.OFFERCOMPLETED, "move to Offer");
		a.update(c4);
		assertEquals("Closed", a.getStateName());
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath2() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals("Review", a.getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Interview");
		a.update(c1);
		assertEquals("Interview", a.getStateName());
		Command c2 = new Command(CommandValue.STANDBY, "cvedward", Resolution.INTCOMPLETED, "move to Waitlist");
		a.update(c2);
		assertEquals("Waitlist", a.getStateName());
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath3() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		Command c2 = new Command(CommandValue.STANDBY, "cvedward", Resolution.REVCOMPLETED, "move to Waitlist");
		a.update(c2);
		assertEquals("Waitlist", a.getStateName());
		Command c3 = new Command(CommandValue.REOPEN, "cvedward", Resolution.INTCOMPLETED, "move to Reference Check");
		a.update(c3);
		assertEquals("Review", a.getStateName());
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath4() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		Command c1 = new Command(CommandValue.REJECT, "cvedward", Resolution.REVCOMPLETED, "move to Closed");
		a.update(c1);
		assertEquals("Closed", a.getStateName());
		Command c2 = new Command(CommandValue.REOPEN, "cvedward", Resolution.REVCOMPLETED, "move to Review");
		a.update(c2);
		assertEquals("Review", a.getStateName());
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath5() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals("Review", a.getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Interview");
		a.update(c1);
		assertEquals("Interview", a.getStateName());
		Command c2 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Reference Check");
		a.update(c2);
		assertEquals("RefCheck", a.getStateName());
		Command c3 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Offer");
		a.update(c3);
		assertEquals("Offer", a.getStateName());
		Command c4 = new Command(CommandValue.REJECT, "cvedward", Resolution.OFFERCOMPLETED, "move to Offer");
		a.update(c4);
		assertEquals("Closed", a.getStateName());
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath6() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals("Review", a.getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Interview");
		a.update(c1);
		assertEquals("Interview", a.getStateName());
		Command c2 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Reference Check");
		a.update(c2);
		assertEquals("RefCheck", a.getStateName());
		Command c3 = new Command(CommandValue.REJECT, "cvedward", Resolution.REFCHKCOMPLETED, "move to Offer");
		a.update(c3);
		assertEquals("Closed", a.getStateName());
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath7() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals("Review", a.getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Interview");
		a.update(c1);
		assertEquals("Interview", a.getStateName());
		Command c2 = new Command(CommandValue.REJECT, "cvedward", Resolution.INTCOMPLETED, "move to Waitlist");
		a.update(c2);
		assertEquals("Closed", a.getStateName());
	}
	/**
	 * Tests one possible path an application can take in the FSM
	 */
	@Test
	public void testFSMPath8() {
		Application a = new Application(APP_ID, STATE, TYPE_STRING, SUMMARY, REVIEWER, PROC_PAPER, RESOLUTION, NOTES);
		assertEquals("Review", a.getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, "cvedward", null, "move to Interview");
		a.update(c1);
		assertEquals("Interview", a.getStateName());
		Command c2 = new Command(CommandValue.STANDBY, "cvedward", Resolution.INTCOMPLETED, "move to Waitlist");
		a.update(c2);
		assertEquals("Waitlist", a.getStateName());
		Command c3 = new Command(CommandValue.REOPEN, "cvedward", null, "move to RefCheck");
		a.update(c3);
		assertEquals("RefCheck", a.getStateName());
	}
}
