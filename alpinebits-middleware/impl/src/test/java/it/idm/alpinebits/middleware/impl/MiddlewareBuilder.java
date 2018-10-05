package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;

/**
 * Builder for AlpineBits {@link Middleware}.
 */
public class MiddlewareBuilder {

    public static final String EXCEPTION_MESSAGE = "FROM MIDDLEAWARE";
    public static final String FULL_EXCEPTION_MESSAGE = "java.lang.RuntimeException: " + EXCEPTION_MESSAGE;

    /**
     * Build and return a {@link Middleware}, where the value is added to the
     * {@link Context} state.
     *
     * @param key      the key used to store the value in the {@link Context} state
     * @param value    the value stored in the {@link Context} state
     * @param callNext if the middleware should invoke the {@link MiddlewareChain#next()}
     *                 method
     * @return the {@link Middleware} with the value added to the {@link Context}
     * state
     */
    public static Middleware buildMiddleware(String key, Object value, boolean callNext) {
        return new Middleware() {
            @Override
            public void handleContext(Context ctx, MiddlewareChain chain) {
                ctx.set(key, value);
                if (callNext) {
                    chain.next();
                }
            }
        };
    }

    /**
     * Build and return a {@link Middleware} that throws a {@link RuntimeException}.
     *
     * @return the {@link Middleware} that throws a {@link RuntimeException}
     */
    public static Middleware buildThrowingMiddleware() {
        return new Middleware() {
            @Override
            public void handleContext(Context ctx, MiddlewareChain chain) {
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
        };
    }

}
