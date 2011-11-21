package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ServerUserWetTests.class, //Nathan Fulton
	ServerConsumableWetTests.class, //Nathan Fulton
	ServerUserWetTests.class, //Nathan Fulton
	ServerAttributeWetTests.class, //Nathan Fulton
	UserAttributeWetTests.class, //Nathan Fulton
	ServerRecommendationWetTests.class //Nathan Fulton
})
public class ALLServerWetTests
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Server Wet Tests");
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}
}
