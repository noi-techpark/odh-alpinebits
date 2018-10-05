package it.idm.alpinebits.middleware;

import java.util.Optional;

/**
 * Each {@link Middleware#handleContext(Context, MiddlewareChain)} method is
 * provided with a context object that is shared by the middlewares.
 * <p>
 * The context provides methods to store and retrieve data and can
 * thus be used for data exchange between the middlewares.
 * <p>
 * The context implements the {@link ExceptionHandler} interface.
 * Context implementations should handleContext exceptions that occur while
 * the middlewares are executed. This way, the exception handling
 * is part of the context and can be specialized if needed.
 */
public interface Context extends ExceptionHandler {

    /**
     * Get the value for the given key from the context.
     *
     * @param key   identifier used to retrieve the value of the associated key
     * @param clazz type expected to be returned. If a value was found for the given key,
     *              but the expected {@code clazz} is not assignable from the value's type, a
     *              {@link ClassCastException} should be thrown (fail fast)
     * @return {@link Optional} containing the value referenced by the given key, or an
     *         empty Optional if the key could not be found
     * @throws ClassCastException if {@code clazz} is not assignable from the found value's type,
     *         i.e. the found value's type is not the same as {@code clazz}, not a superclass and
     *         not a superinterface
     */
    <T> Optional<T> get(String key, Class<T> clazz);

    /**
     * Set the value for the given key in this context.
     *
     * @param key identifier used for the given value
     * @return the current context
     */
    Context set(String key, Object value);

    /**
     * Remove the value referenced by the given key.
     *
     * @param key identifier referencing the value, that should be removed
     * @return the value referenced by the given key
     */
    Object remove(String key);

    /**
     * Check if the given key is part of this context.
     *
     * @param key check if the context contains the given key
     * @return true if the key was found in the context, false otherwise
     */
    boolean contains(String key);

}
