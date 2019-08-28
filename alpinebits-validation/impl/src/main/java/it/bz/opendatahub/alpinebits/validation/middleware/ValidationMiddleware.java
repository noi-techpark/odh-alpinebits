/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.middleware;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;

/**
 * This middleware validates objects.
 * <p>
 * The middleware uses three parameters for its configuration:
 * <ul>
 *     <li>
 *         a data key, that is used to retrieve the object-to-validate
 *         from the middleware {@link Context}
 *     </li>
 *     <li>
 *          a {@link Validator} that validates the object
 *     </li>
 *     <li>
 *          a {@link ValidationContextProvider} that provides
 *          the context necessary for the validation (e.g.
 *          the current AlpineBits action)
 *     </li>
 * </ul>
 * <p>
 * If a validation fails, a {@link it.bz.opendatahub.alpinebits.validation.ValidationException}
 * or one of its subclasses is thrown. If the validation passes,
 * the next middleware in the chain is invoked.
 *
 * @param <T> the type of the object to validate
 * @param <C> the type of the validation context
 */
public class ValidationMiddleware<T, C> implements Middleware {

    private final Key<T> dataKey;
    private final Validator<T, C> validator;
    private final ValidationContextProvider<C> validationContextProvider;

    public ValidationMiddleware(
            Key<T> dataKey,
            Validator<T, C> validator,
            ValidationContextProvider<C> validationContextProvider
    ) {
        if (dataKey == null) {
            throw new IllegalArgumentException("Data key is required");
        }
        if (validator == null) {
            throw new IllegalArgumentException("Validator is required");
        }
        if (validationContextProvider == null) {
            throw new IllegalArgumentException("ValidationContextProvider is required");
        }
        this.dataKey = dataKey;
        this.validator = validator;
        this.validationContextProvider = validationContextProvider;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        T data = ctx.getOrThrow(this.dataKey);
        C validationContext = this.validationContextProvider.buildContext(ctx);

        this.validator.validate(data, validationContext, null);

        chain.next();
    }

}
