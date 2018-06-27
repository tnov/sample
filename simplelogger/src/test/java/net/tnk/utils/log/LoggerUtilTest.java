package net.tnk.utils.log;

import java.util.logging.Logger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LoggerUtilTest
    extends TestCase
{

    private static final Logger LOGGER = Logger.getLogger(LoggerUtil.class.getName());

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LoggerUtilTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LoggerUtilTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testLoggerUtil()
    {
        LoggerUtil.applyLoggerProperties();
        LOGGER.fine("start");
        LOGGER.info("end");
        assertTrue( true );
    }
}
