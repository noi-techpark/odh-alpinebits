package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;

/**
 * Builder for AlpineBits middleware {@link Context}.
 */
public class ContextBuilder {


    /**
     * Build and return a {@link SimpleContext}.
     *
     * @return a {@link SimpleContext}
     */
    public static SimpleContext buildSimpleContext() {
        return new SimpleContext();
    }

}
