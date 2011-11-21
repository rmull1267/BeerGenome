package tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ALLServerWetTests.class,
	ALLClientWetTests.class
})
public class AllTests {
	public static Test suite()
	{
		TestSuite suite = new TestSuite("All Tests");
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}
}
