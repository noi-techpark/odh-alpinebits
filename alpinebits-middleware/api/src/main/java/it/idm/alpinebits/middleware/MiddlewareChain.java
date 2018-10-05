package it.idm.alpinebits.middleware;

/**
 * The {@link MiddlewareChain} can be used by a {@link Middleware} to invoke
 * the next middleware in the chain, or if the calling middleware is the last middleware
 * in the chain, to invoke the resource at the end of the chain.
 */
public interface MiddlewareChain {

    /**
     * Causes the next {@link Middleware} in the chain to be invoked, or if the calling middleware
     * is the last middleware in the chain, causes the resource at the end of the chain to
     * be invoked.
     */
    void next();

}
