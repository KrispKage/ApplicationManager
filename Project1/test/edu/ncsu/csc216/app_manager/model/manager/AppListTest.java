package edu.ncsu.csc216.app_manager.model.manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;


/**
 * The test class for AppList
 */
public class AppListTest {
	/**
	 * New list used in testing
	 */
    private AppList appList;
    /**
     * Puts applications in the newly created appList before each test
     */
    @BeforeEach
    public void setUp() {
        appList = new AppList();
        appList.addApp(AppType.NEW, "Summary 1", "Note 1");
        appList.addApp(AppType.OLD, "Summary 2", "Note 2");
        appList.addApp(AppType.HIRED, "Summary 3", "Note 3");
    }
    /**
     * Tests adding a single application to an AppList
     */
    @Test
    public void testAddApp() {
        int newAppId = appList.addApp(AppType.NEW, "Summary 4", "Note 4");
        assertEquals(4, newAppId);
        assertEquals(4, appList.getApps().size());
    }
    /**
     * Tests getting an ArrayList of applications from an AppList
     */
    @Test
    public void testGetApps() {
        ArrayList<Application> apps = appList.getApps();
        assertEquals(3, apps.size());
    }
    /**
     * Tests getting a specific application from the AppList from a given ID
     */
    @Test
    public void testGetAppById() {
    	ArrayList<Application> apps = appList.getApps();
        assertEquals(apps.get(0), appList.getAppById(1));
        assertEquals(apps.get(1), appList.getAppById(2));
        assertEquals(apps.get(2), appList.getAppById(3));
    }
    /**
     * Tests deleting an application from the AppList given a specific ID
     */
    @Test
    public void testDeleteAppById() {
        appList.deleteAppById(1);
        assertEquals(2, appList.getApps().size());
        assertNull(appList.getAppById(1));
        appList.deleteAppById(2);
        assertEquals(1, appList.getApps().size());
        assertNull(appList.getAppById(2));
    }
    /**
     * Tests adding applications from an ArrayList of applications
     */
    @Test
    public void testAddAppsFromList() {
        Application app1 = new Application(4, AppType.NEW, "Summary 4", "Note 4");
        Application app2 = new Application(5, AppType.OLD, "Summary 5", "Note 5");
        ArrayList<Application> newApps = new ArrayList<>();
        newApps.add(app1);
        newApps.add(app2);
        appList.addApps(newApps);
        assertEquals(5, appList.getApps().size());
        assertEquals(app1, appList.getAppById(4));
        assertEquals(app2, appList.getAppById(5));
    }
    /**
     * Tests getting applications from an AppList from the given type
     */
    @Test
    public void testGetAppsByType() {
    	System.out.println(appList.getApps().size());
    	assertEquals(1, appList.getAppsByType(Application.A_OLD).size());
    }
}