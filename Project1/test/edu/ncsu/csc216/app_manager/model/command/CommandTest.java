/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.command;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

/**
 * The test for Command
 */
class CommandTest {
	/** The command value for the tested Command */
	private static final CommandValue COMMAND_VALUE = CommandValue.ACCEPT;
	/** The reviewer name for the tested Command */
	private static final String REVIEWER_ID = "cvedward";
	/** The resolution for the tested Command */
	private static final Resolution RESOLUTION = Resolution.REVCOMPLETED;
	/** The note for the tested Command */
	private static final String NOTE = "test application";
	/**
	 * Tests constructing the correct Command object
	 */
	@Test
	public void testValidCommand() {
		Command c = new Command(COMMAND_VALUE, REVIEWER_ID, RESOLUTION, NOTE);
		assertEquals(COMMAND_VALUE, c.getCommand());
		assertEquals(REVIEWER_ID, c.getReviewerId());
		assertEquals(RESOLUTION, c.getResolution());
		assertEquals(NOTE, c.getNote());
	}
	/**
	 * Tests constructing an invalid Command object
	 */
	@Test
	public void testInvalidCommand() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(null, REVIEWER_ID, RESOLUTION, NOTE));
		assertEquals("Invalid information", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(COMMAND_VALUE, null, RESOLUTION, NOTE));
		assertEquals("Invalid information", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.STANDBY, REVIEWER_ID, null, NOTE));
		assertEquals("Invalid information", e3.getMessage());
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Command(COMMAND_VALUE, REVIEWER_ID, RESOLUTION, null));
		assertEquals("Invalid information", e4.getMessage());
	}

}
