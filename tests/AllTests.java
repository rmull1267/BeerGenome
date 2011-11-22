package tests;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ALLServerWetTests.class,
	ALLClientWetTests.class
})
public class AllTests {
	@After
	public void afterAll()
	{
		//When we are done running all the tests, delete the test database.
		//Aside from ensureing everything behaves properly for the right reasons,
		//each time you run the tests without deleting the db an eigth of a
		//second or so will be added.
		//TODO-nf-test add stress tests for the db.
		File f = new File("test.db");
		f.delete();
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite("All Tests");
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}
}
