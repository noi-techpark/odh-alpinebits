package it.idm.alpinebits.middleware;

/**
 * An ExceptionHandler is used to handleContext uncaught exceptions.
 */
public interface ExceptionHandler {

    /**
     * Handle given exception.
     *
     * @param e exception that should be handled
     */
    void handleException(Exception e);
}
