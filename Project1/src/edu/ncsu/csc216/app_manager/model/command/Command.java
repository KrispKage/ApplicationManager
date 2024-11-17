/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.command;

/**
 * The main class for Command
 */
public class Command {
	/** Static variable to show that an application's review was completed */
	public static final String R_REVCOMPLETED = "ReviewCompleted";
	/** Static variable to show that an application's interview was completed */
	public static final String R_INTCOMPLETED = "InterviewCompleted";
	/** Static variable to show that an application's reference check was completed */
	public static final String R_REFCHKCOMPLETED = "ReferenceCheckCompleted";
	/** Static variable to show that an application's offer was completed */
	public static final String R_OFFERCOMPLETED = "OfferCompleted";
	/** The reviewer's id */
	private String reviewerId;
	/** The reviewer's note */
	private String note;
	/** The reviewer's command */
	private CommandValue commandValue;
	/** The reviewer's resolution */
	private Resolution resolution;
	/**
	 * Enumeration for all the commands
	 */
	public enum CommandValue {
		/** The command to accept an application */
		ACCEPT, 
		/** The command to reject an application */
		REJECT, 
		/** The command to put an application on standby */
		STANDBY, 
		/** The command to reopen an application */
		REOPEN;
		
	}
	/**
	 * Enumeration for all the application resolutions
	 */
	public enum Resolution {
		/** The resolution for the application's review being completed */
		REVCOMPLETED, 
		/** The resolution for the application's interview being completed */
		INTCOMPLETED, 
		/** The resolution for the application's reference check being completed */
		REFCHKCOMPLETED, 
		/** The resolution for the application's offer being accepted */
		OFFERCOMPLETED;
		
	}
	/**
	 * Constructor for the command given by the review for an application
	 * @param commandValue			The type of command
	 * @param reviewerId			The reviewer's id
	 * @param resolution			The application's resolution
	 * @param note					The reviewer's notes
	 */
	public Command(CommandValue commandValue, String reviewerId, Resolution resolution, String note) {
		if (commandValue == null) {
			throw new IllegalArgumentException("Invalid information");
		}
		if (commandValue == CommandValue.ACCEPT && (reviewerId == null || "".equals(reviewerId))) {
			throw new IllegalArgumentException("Invalid information");
		}
		if ((commandValue == CommandValue.STANDBY || commandValue == CommandValue.REJECT) && resolution == null) {
			throw new IllegalArgumentException("Invalid information");
		}
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Invalid information");
		}
		this.commandValue = commandValue;
		this.reviewerId = reviewerId;
		this.resolution = resolution;
		this.note = note;
	}
	/**
	 * Returns the command
	 * @return command
	 */
	public CommandValue getCommand() {
		return commandValue;
	}
	/**
	 * Returns the reviewer's id
	 * @return id
	 */
	public String getReviewerId() {
		return reviewerId;
	}
	/**
	 * Returns the application's resolution
	 * @return resolution
	 */
	public Resolution getResolution() {
		return resolution;
	}
	/**
	 * Returns the application's notes
	 * @return note
	 */
	public String getNote() {
		return note;
	}
}
