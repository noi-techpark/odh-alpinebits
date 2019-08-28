/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.middleware;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;
import org.testng.annotations.Test;

/**
 * Tests for {@link ValidationMiddleware}.
 */
public class ValidationMiddlewareTest {

    private static final Key<String> DEFAULT_DATA_KEY = Key.key("DEFAULT_KEY", String.class);
    private static final Validator<String, Object> DEFAULT_VALIDATOR = (object, ctx, currentPath) -> {
    };
    private static final ValidationContextProvider<Object> DEFAULT_VALIDATION_CONTEXT_PROVIDER = ctx -> null;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testHandleContext_ShouldThrow_WhenDataKeyIsNull() {
        new ValidationMiddleware<>(null, null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testHandleContext_ShouldThrow_WhenValidatorIsNull() {
        new ValidationMiddleware<>(DEFAULT_DATA_KEY, null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testHandleContext_ShouldThrow_WhenValidationContextProviderIsNull() {
        new ValidationMiddleware<>(DEFAULT_DATA_KEY, DEFAULT_VALIDATOR, null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_ShouldThrow_WhenDataKeyIsNotFoundInContext() {
        Middleware middleware = this.buildValidationDownstreamMiddleware();
        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_ShouldThrow_WhenValidationContextKeyIsNotFoundInContext() {
        Middleware middleware = this.buildValidationDownstreamMiddleware();
        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, null);
    }

    private Middleware buildValidationDownstreamMiddleware() {
        return new ValidationMiddleware<>(
                DEFAULT_DATA_KEY,
                DEFAULT_VALIDATOR,
                DEFAULT_VALIDATION_CONTEXT_PROVIDER
        );
    }
}