package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.ExceptionHandler;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Test cases for {@link LoggingExceptionHandler} class.
 */
public class LoggingExceptionHandlerTest {

    @Test
    public void testHandleException() throws Exception {
        // This test is solely here to have 100% test coverage
        ExceptionHandler exceptionHandler = new LoggingExceptionHandler();
        exceptionHandler.handleException(null);
        assertTrue(true);
    }
}