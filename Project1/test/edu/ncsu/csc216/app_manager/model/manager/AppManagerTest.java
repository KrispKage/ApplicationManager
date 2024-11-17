package edu.ncsu.csc216.app_manager.model.manager;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.io.AppReader;



class AppManagerTest {
	/** An instance of AppManager */
	private AppManager manager;
	/** The custom file where applications will be written to */
	private final String validFile1 = "test-files/Custom_App_List.txt";
	/** The file where applications are being read from */
	private final String validFile2 = "test-files/app1.txt";
	
	/**
	 * Sets up the instance of manager to contain a certain amount of applications from the given AppList
	 */
	@BeforeEach
	public void setUp() {
		manager = AppManager.getInstance();
		manager.createNewAppList();
		manager.addAppToList(AppType.OLD, "summary", "note");
		manager.addAppToList(AppType.NEW, "summary", "note");
		manager.addAppToList(AppType.HIRED, "summary", "note");
	}
	/**
	 * Tests SaveAppsToFile
	 */
	@Test
	public void testSaveAppsToFile() {
		manager.saveAppsToFile(validFile1);
		ArrayList<Application> apps = AppReader.readAppsFromFile(validFile1);
		assertEquals(3, apps.size());
		assertEquals(AppType.OLD, apps.get(0).getType());
		assertEquals(AppType.NEW, apps.get(1).getType());
	}
	/**
	 * Tests LoadAppsToFile
	 */
	@Test
	public void testLoadAppsToFile() {
		manager.createNewAppList();
		manager.loadAppsFromFile(validFile2);
		Object [][] appArray = manager.getAppListAsArray();
		assertEquals(6, appArray.length);
		assertEquals("New", appArray[0][2]);
		assertEquals("Old", appArray[1][2]);
	}
	/**
	 * Tests GetAppListAsArray
	 */
	@Test
	public void testGetAppListAsArray() {
		Object [][] appArray = manager.getAppListAsArray();
		assertEquals(3, appArray.length);
	}
	/**
	 * Tests GetAppListAsArrayByAppType
	 */
	@Test
	public void testGetAppListAsArrayByAppType() {
		Object [][] appArray = manager.getAppListAsArrayByAppType(Application.A_OLD);
		assertEquals(1, appArray.length);
	}
	/**
	 * Tests GetAppById
	 */
	@Test
	public void testGetAppById() {
		Application a = manager.getAppById(1);
		assertEquals("Old", a.getAppType());
		assertEquals("summary", a.getSummary());
	}
	/**
	 * Tests DeleteAppById
	 */
	@Test
	public void testDeleteAppById() {
		manager.deleteAppById(1);
		Object [][] appArray = manager.getAppListAsArray();
		assertEquals(2, appArray.length);
		manager.deleteAppById(2);
		appArray = manager.getAppListAsArray();
		assertEquals(1, appArray.length);
	}
	
	@Test
	public void testAddAppToList() {
		manager.addAppToList(AppType.NEW, "summary", "note");
		Object [][] appArray = manager.getAppListAsArray();
		assertEquals(4, appArray.length);
	}
	
}
