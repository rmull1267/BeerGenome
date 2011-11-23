package tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import server.Server;
import server.ServerException;
import client.Client;
import client.ClientException;
import database.SQLDatabase;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ClientUserWetTests.class, 			//Nathan Fulton
	ClientAttributeWetTests.class,		//Nathan Fulton
	ClientRecommendationWetTests.class, //Nathan Fulton
	ClientConsumableWetTests.class		//JRC
})
public class ALLClientWetTests {
static Server s;
	
	@BeforeClass
	public static void setUp() throws Exception {
		SQLDatabase.getInstance("test.db");
		s = new Server(2332);
		try {
			s.startServer();
		} catch (ServerException e1) {
			e1.printStackTrace();
		}
		
		try {
			Client.initializeClient(2332, "127.0.0.1");
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		SQLDatabase.getInstance().close();
		try {
			s.stopServer();
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Client Wet Tests");
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}
}
