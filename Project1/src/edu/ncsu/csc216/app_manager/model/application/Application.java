/**
 * 
 */
package edu.ncsu.csc216.app_manager.model.application;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

/**
 * The main Application class
 */
public class Application {
	/** Static variable for the 'New' application type */
	public static final String A_NEW = "New";
	/** Static variable for the 'Old' application type */
	public static final String A_OLD = "Old";
	/** Static variable for the 'Hired' application type */
	public static final String A_HIRED = "Hired";
	/** The application ID */
	private int appId;
	/** The application's summary */
	private String summary;
	/** The application's reviewer */
	private String reviewer;
	/** The application's state */
	private AppState state;
    /** A check to see if the application has been processed */
	private boolean processPaperwork;
	/** The provided notes for an application */
	private ArrayList<String> notes = new ArrayList<String>();
	/** The application's type */
	private AppType type;
	/** The application's resolution */
	private Resolution resolution;
	/** Static variable for an application in the Review state */
	public static final String REVIEW_NAME = "Review";
	/** Static variable for an application in the Interview state */
	public static final String INTERVIEW_NAME = "Interview";
	/** Static variable for an application in the ReferenceCheck state */
	public static final String REFCHK_NAME = "RefCheck";
	/** Static variable for an application in the Offer state */
	public static final String OFFER_NAME = "Offer";
	/** Static variable for an application in the Waitlist state */
	public static final String WAITLIST_NAME = "Waitlist";
	/** Static variable for an application in the Closed state */
	public static final String CLOSED_NAME = "Closed";
	/** Final instance of the ReviewState inner class */
	private final ReviewState reviewState = new ReviewState();
	/** Final instance of the InterviewState inner class */
	private final InterviewState interviewState = new InterviewState();
	/** Final instance of the WaitlistState inner class */
	private final WaitlistState waitlistState = new WaitlistState();
	/** Final instance of the RefChkState inner class */
	private final RefChkState refChkState = new RefChkState();
	/** Final instance of the OfferState inner class */
	private final OfferState offerState = new OfferState();
	/** Final instance of the ClosedState inner class */
	private final ClosedState closedState = new ClosedState();
	
	/**
	 * Constructs an Application object with the provided fields
	 * @param id			Application's id
	 * @param type			Application's type
	 * @param note			Application's note
	 * @param summary		Application's summary
	 * @throws IllegalArgumentException if any of the parameters are null or empty or id is less than 1
	 */
	public Application(int id, AppType type, String summary, String note) {
		if (id < 1) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (type == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (summary == null || "".equals(summary)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		setAppId(id);
		this.type = type;
		setSummary(summary);
		addNote(note);
		setState(Application.REVIEW_NAME);
		this.reviewer = null;
		this.processPaperwork = false;
		this.resolution = null;
	}
	
	/**
	 * Constructs a more complete Application object with the provided fields
	 * @param id					Application's id
	 * @param state					Application's state
	 * @param type					Application's type
	 * @param summary				Application's summary
	 * @param reviewer				Application's reviewer
	 * @param processPaperwork		Checks if the application's paperwork has processed
	 * @param resolution			Application's resolution
	 * @param notes					Application's notes
	 * @throws IllegalArgumentException if any of the parameters are null or empty or id is less than 1
	 */
	public Application(int id, String state, String type, String summary, String reviewer, boolean processPaperwork, String resolution, ArrayList<String> notes) {
		if (notes == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (id < 1) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (type == null || "".equals(type)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (summary == null || "".equals(summary)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == REVIEW_NAME && !"".equals(reviewer)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == INTERVIEW_NAME && (reviewer == null || "".equals(reviewer))) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == WAITLIST_NAME && (reviewer == null || "".equals(reviewer)) && resolution == Command.R_INTCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == WAITLIST_NAME && (resolution == null || "".equals(resolution))) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == CLOSED_NAME && (resolution == null || "".equals(resolution))) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == REFCHK_NAME && (reviewer == null || "".equals(reviewer))) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if ((state == INTERVIEW_NAME || state == REFCHK_NAME || state == OFFER_NAME) && type.equals(A_NEW)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == INTERVIEW_NAME && resolution == Command.R_INTCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == REVIEW_NAME && (resolution == Command.R_REVCOMPLETED || notes.isEmpty())) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == REFCHK_NAME && resolution == Command.R_REFCHKCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == OFFER_NAME && resolution == Command.R_OFFERCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == INTERVIEW_NAME && processPaperwork) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == WAITLIST_NAME && processPaperwork && resolution == Command.R_REVCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (state == CLOSED_NAME && processPaperwork && resolution == Command.R_REVCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		setAppId(id);
		setState(state);
		setAppType(type);
		setSummary(summary);
		setReviewer(reviewer);
		setProcessPaperwork(processPaperwork);
		setResolution(resolution);
		setNotes(notes);
	}
	
	/**
	 * Sets the application ID
	 * @param appId appId
	 * @throws IllegalArgumentException if id is less than 1
	 */
	private void setAppId(int appId) {
		this.appId = appId;
	}
	
	/**
	 * Sets the application state
	 * @param state state
	 * @throws IllegalArgumentException if null or empty
	 */
	private void setState(String state) {
		if (state.equals(REVIEW_NAME)) {
			this.state = reviewState;
		} else if (state.equals(INTERVIEW_NAME)) {
			this.state = interviewState;
		} else if (state.equals(REFCHK_NAME)) {
			this.state = refChkState;
		} else if (state.equals(OFFER_NAME)) {
			this.state = offerState;
		} else if (state.equals(WAITLIST_NAME)) {
			this.state = waitlistState;
		} else if (state.equals(CLOSED_NAME)) {
			this.state = closedState;
		} else {
			throw new IllegalArgumentException("Application cannot be created.");
		}
	}
	
	/**
	 * Sets the application type
	 * @param type type
	 * @throws IllegalArgumentException if null or empty
	 */
	private void setAppType(String type) {
		if (type.equals(A_NEW)) {
			this.type = AppType.NEW;
		} else if(type.equals(A_OLD)) {
			this.type = AppType.OLD;
		} else if (type.equals(A_HIRED)) {
			this.type = AppType.HIRED;
		} else {
			throw new IllegalArgumentException("Application cannot be created.");
		}
	}
	
	/**
	 * Sets the application summary
	 * @param summary summary
	 * @throws IllegalArgumentException if null or empty
	 */
	private void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * Sets the application reviewer
	 * @param reviewer reviewer
	 * @throws IllegalArgumentException if null or empty
	 */
	private void setReviewer(String reviewer) {
		if ("".equals(reviewer)) {
			this.reviewer = null;
		} else {
			this.reviewer = reviewer;
		}
	}
	
	/**
	 * Checks if the application's paperwork has been processed
	 * @param processPaperwork processPaperwork
	 * @throws IllegalArgumentException if null or empty
	 */
	private void setProcessPaperwork(boolean processPaperwork) {
		this.processPaperwork = processPaperwork;
	}
	/**
	 * Sets the application resolution
	 * @param resolution resolution
	 * @throws IllegalArgumentException if null or empty
	 */
	private void setResolution(String resolution) {
		if (resolution == null || "".equals(resolution)) {
			this.resolution = null;
		} else if (resolution.equals(Command.R_REVCOMPLETED)) {
			this.resolution = Resolution.REVCOMPLETED;
		} else if (resolution.equals(Command.R_INTCOMPLETED)) {
			this.resolution = Resolution.INTCOMPLETED;
		} else if (resolution.equals(Command.R_REFCHKCOMPLETED)) {
			this.resolution = Resolution.REFCHKCOMPLETED;
		} else if (resolution.equals(Command.R_OFFERCOMPLETED)) {
			this.resolution = Resolution.OFFERCOMPLETED;
		}
	}
	
	/**
	 * Sets the application notes
	 * @param notes notes
	 * @throws IllegalArgumentException if null or empty
	 */
	private void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}
	
	/**
	 * Gets the application ID
	 * @return id
	 */
	public int getAppId() {
		return appId;
	}
	
	/**
	 * Gets the application state as a String
	 * @return state
	 */
	public String getStateName() {
		return this.getStateString();
	}
	/**
	 * Gets the application state
	 * @return state
	 */
	public AppState getState() {
		return state;
	}
	/**
	 * Gets the application type as a string
	 * @return type
	 */
	public String getAppType() {
		return this.getTypeString();
	}
	/**
	 * Gets the applications type
	 * @return type
	 */
	public AppType getType() {
		return type;
	}
	/**
	 * Gets the application summary
	 * @return summary
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * Gets the application reviewer
	 * @return reviewer
	 */
	public String getReviewer() {
		return reviewer;
	}
	
	/**
	 * Checks if the application is processed
	 * @return true or false
	 */
	public boolean isProcessed() {
		return processPaperwork;
	}
	
	/**
	 * Gets the application's resolution as a string
	 * @return resolution
	 */
	public String getResolution() {
		return this.getResolutionString();
	}
	/**
	 * Gets the application's resolution
	 * @return resolution
	 */
	public Resolution getResolutionR() {
		return resolution;
	}
	/**
	 * Gets the application's notes
	 * @return notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * Returns the application's notes as a single string
	 * @return notes string
	 */
	public String getNotesString() {
		String notesString = "";
		for (String s : this.notes)
		{
		    notesString += "-" + s + "\n";
		}

		return notesString;
	}
	/**
	 * Returns the applications' notes as a single string for formatting with files
	 * @return notes string
	 */
	public String getNotesStringForFile() {
		int max = notes.size() - 1;
		int count = 0;
		String notesString = "";
		for (String s : this.notes)
		{
			if (count == max) {
				notesString += "-" + s;
			} else {
				notesString += "-" + s + "\n";
				count++;
			} 
		}
		return notesString;
	}
	
	/**
	 * Returns the application's details as a string
	 * @return string
	 */
	public String toString() {
		return "*" + this.appId + "," + this.getStateString() + "," + this.getTypeString() + "," + this.summary + "," + this.reviewer + "," + this.processPaperwork + "," + this.getResolutionString() + "\n" + this.getNotesString();
	}
	/**
	 * Returns the application's details as a string as appropriate with the Data Format
	 * @return string
	 */
	public String toStringForFile() {
		return "*" + this.appId + "," + this.getStateString() + "," + this.getTypeStringForFile() + "," + this.summary + "," + this.reviewer + "," + this.processPaperwork + "," + this.getResolutionStringForFile() + "\n" + this.getNotesStringForFile();
	}
	
	
	/**
	 * Adds a note to the application
	 * @param note note
	 */
	public void addNote(String note) {
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Application cannot be created.");
		} else if (this.state != null) {
			String newNote = "[" + this.getStateString() + "] " + note;
			notes.add(newNote);
		} else {
			String newNote = "[Review] " + note;
			notes.add(newNote);
		}
	}
	
	private String getStateString() {
		if (this.state == reviewState) {
			return REVIEW_NAME;
		} else if (this.state == interviewState) {
			return INTERVIEW_NAME;
		} else if (this.state == waitlistState) {
			return WAITLIST_NAME;
		} else if (this.state == refChkState) {
			return REFCHK_NAME;
		} else if (this.state == offerState) {
			return OFFER_NAME;
		} else if (this.state == closedState) {
			return CLOSED_NAME;
		} else {
			return null;
		}
	}
	
	private String getTypeString() {
		if (this.type == AppType.NEW) {
			return A_NEW;
		} else if (this.type == AppType.OLD) {
			return A_OLD;
		} else if (this.type == AppType.HIRED) {
			return A_HIRED;
		} else {
			return null;
		}
	}
	
	private String getResolutionString() {
		if (this.resolution == Resolution.REVCOMPLETED) {
			return "ReviewCompleted";
		} else if (this.resolution == Resolution.INTCOMPLETED) {
			return "InterviewCompleted";
		} else if (this.resolution == Resolution.REFCHKCOMPLETED) {
			return "ReferenceCheckCompleted";
		} else if (this.resolution == Resolution.OFFERCOMPLETED) {
			return "OfferCompleted";
		} else {
			return null;
		}
	}
	
	private String getResolutionString(Resolution resolution) {
		if (resolution == Resolution.REVCOMPLETED) {
			return "ReviewCompleted";
		} else if (resolution == Resolution.INTCOMPLETED) {
			return "InterviewCompleted";
		} else if (resolution == Resolution.REFCHKCOMPLETED) {
			return "ReferenceCheckCompleted";
		} else if (resolution == Resolution.OFFERCOMPLETED) {
			return "OfferCompleted";
		} else {
			return null;
		}
	}
	
	private String getTypeStringForFile() {
		if (this.type == AppType.NEW) {
			return A_NEW;
		} else if (this.type == AppType.OLD) {
			return A_OLD;
		} else if (this.type == AppType.HIRED) {
			return A_HIRED;
		} else {
			return "";
		}
	}
	
	private String getResolutionStringForFile() {
		if (this.resolution == Resolution.REVCOMPLETED) {
			return "ReviewCompleted";
		} else if (this.resolution == Resolution.INTCOMPLETED) {
			return "InterviewCompleted";
		} else if (this.resolution == Resolution.REFCHKCOMPLETED) {
			return "ReferenceCheckCompleted";
		} else if (this.resolution == Resolution.OFFERCOMPLETED) {
			return "OfferCompleted";
		} else {
			return "";
		}
	}
	
	/**
	 * Updates the application's command for the FSM
	 * @param command command
	 * @throws UnsupportedOperationException if the Command is not a valid action
	 * for the given state.
	 */
	public void update(Command command) {
		state.updateState(command);
		state.getStateName();
	}
	
	/**
	 * Interface for states in the Application State Pattern.  All 
	 * concrete application states must implement the AppState interface.
	 * The AppState interface should be a private interface of the 
	 * Application class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 * @author Dr. Chandrika Satyavolu (jsatyav@ncsu.edu)
	 */
	private interface AppState {
		
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command command);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}
	
	/**
	 * Concrete class that represents the Review state of the Application Manager FSM.
	 */
	public class ReviewState implements AppState {
		private ReviewState() {
			
		}
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state. 
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		public void updateState(Command command) {
			if ("".equals(command.getNote()) || "".equals(command.getReviewerId())) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			if (command.getCommand() == CommandValue.ACCEPT) {
				setReviewer(command.getReviewerId());
				state = interviewState;
				setAppType(A_OLD);
				addNote(command.getNote());
				resolution = command.getResolution();
			}
			else if (command.getCommand() == CommandValue.STANDBY && getType() == AppType.NEW) {
				if (command.getResolution() == Resolution.REVCOMPLETED) {
					setReviewer(command.getReviewerId());
					setResolution(Command.R_REVCOMPLETED);
					state = waitlistState;
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (command.getResolution() == Resolution.REVCOMPLETED) {
					setReviewer(command.getReviewerId());
					setResolution(getResolutionString(command.getResolution()));
					state = closedState;
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			if (state == reviewState) {
				return REVIEW_NAME;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Concrete class that represents the Interview state of the Application Manager FSM.
	 */
	public class InterviewState implements AppState {
		private InterviewState() {
			
		}
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state. 
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		public void updateState(Command command) {
			if ("".equals(command.getNote()) || "".equals(command.getReviewerId())) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			else if (command.getCommand() == CommandValue.ACCEPT) {
				processPaperwork = true;
				setReviewer(command.getReviewerId());
				state = refChkState;
				addNote(command.getNote());
				resolution = command.getResolution();
			}
			else if (command.getCommand() == CommandValue.STANDBY) {
				if (command.getResolution() == Resolution.INTCOMPLETED) {
					setReviewer(command.getReviewerId());
					setResolution(getResolutionString(command.getResolution()));
					state = waitlistState;
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (command.getResolution() == Resolution.INTCOMPLETED) {
					setReviewer(command.getReviewerId());
					setResolution(getResolutionString(command.getResolution()));
					state = closedState;
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			if (state == interviewState) {
				return INTERVIEW_NAME;
			} else {
				return null;
			}
		}
	}
	/**
	 * Concrete class that represents the Reference Check state of the Application Manager FSM.
	 */
	public class RefChkState implements AppState {
		private RefChkState() {
			
		}
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state. 
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		public void updateState(Command command) {
			if ("".equals(command.getNote())) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			if (command.getCommand() == CommandValue.ACCEPT) {
				setReviewer(command.getReviewerId());
				processPaperwork = true;
				state = offerState;
				addNote(command.getNote());
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (command.getResolution() == Resolution.REFCHKCOMPLETED) {
					setReviewer(command.getReviewerId());
					setResolution(getResolutionString(command.getResolution()));
					state = closedState;
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			if (state == refChkState) {
				return REFCHK_NAME;
			} else {
				return null;
			}
		}
	}
	/**
	 * Concrete class that represents the Offer state of the Application Manager FSM.
	 */
	public class OfferState implements AppState {
		private OfferState() {
			
		}
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state. 
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		public void updateState(Command command) {
			if ("".equals(command.getNote())) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			if (command.getCommand() == CommandValue.ACCEPT) {
				if (command.getResolution() == Resolution.OFFERCOMPLETED) {
					setReviewer(command.getReviewerId());
					setResolution(getResolutionString(command.getResolution()));
					processPaperwork = true;
					setAppType(A_HIRED);
					state = closedState;
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (command.getResolution() == Resolution.OFFERCOMPLETED) {
					setReviewer(command.getReviewerId());
					setResolution(getResolutionString(command.getResolution()));
					state = closedState;
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			
			if (state == offerState) {
				return OFFER_NAME;
			} else {
				return null;
			}
		}
	}
	/**
	 * Concrete class that represents the Waitlist state of the Application Manager FSM.
	 */
	public class WaitlistState implements AppState {
		private WaitlistState() {
			
		}
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state. 
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		public void updateState(Command command) {
			if ("".equals(command.getNote())) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			if (getResolutionR() == Resolution.INTCOMPLETED) {
				setReviewer(command.getReviewerId());
				if (command.getCommand() == CommandValue.REOPEN) {
					resolution = null;
					processPaperwork = true;
					state = refChkState;
					addNote(command.getNote());
				}
			}
			if (getResolutionR() == Resolution.REVCOMPLETED && getAppType().equals(A_NEW) && command.getCommand() == CommandValue.REOPEN) {
				resolution = null;
				setAppType(A_OLD);
				state = reviewState;
				addNote(command.getNote());
			}
		}
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			if (state == waitlistState) {
				return WAITLIST_NAME;
			} else {
				return null;
			}
		}
	}
	/**
	 * Concrete class that represents the Closed state of the Application Manager FSM.
	 */
	public class ClosedState implements AppState {
		private ClosedState() {
			
		}
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state. 
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		public void updateState(Command command) {
			if ("".equals(command.getNote())) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			setReviewer(command.getReviewerId());
			if (command.getCommand() == CommandValue.REOPEN) {
				if (getAppType().equals(A_NEW) && getResolutionR() == Resolution.REVCOMPLETED) {
					setReviewer(command.getReviewerId());
					resolution = null;
					setAppType(A_OLD);
					state = reviewState;
					addNote(command.getNote());
				}
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			if (state == closedState) {
				return CLOSED_NAME;
			} else {
				return null;
			}
		}
	}

	/**
	 * An enumeration contained in the Application class. Represents the three possible Application types of new, old and hired.
	 */
	public enum AppType {
		/** The 'New' application type */
		NEW, 
		/** The 'Old' application type */
		OLD, 
		/** The 'Hired' application type */
		HIRED;
		
	}
	
}
