package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This is a {@link MiddlewareChain} implementation that calls the configured list
 * of {@link Middleware} objects in the provided order.
 */
public class SequentialMiddlewareChain implements MiddlewareChain {

    private static final Logger LOG = LoggerFactory.getLogger(SequentialMiddlewareChain.class);

    private final Context ctx;
    private final List<Middleware> middlewares;
    private int index = -1;

    public SequentialMiddlewareChain(Context ctx, List<Middleware> middlewares) {
        if (ctx == null) {
            throw new IllegalArgumentException("The context must not be null");
        }
        if (middlewares == null) {
            throw new IllegalArgumentException("The middlewares must not be null");
        }
        for (Middleware middleware : middlewares) {
            if (middleware == null) {
                throw new IllegalArgumentException("The middleware must not be null");
            }
        }
        this.ctx = ctx;
        this.middlewares = new CopyOnWriteArrayList<>(middlewares);
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void next() {
        int chainSize = this.middlewares.size();

        if (this.index < chainSize - 1) {
            this.index++;
            Middleware currentMiddleware = this.middlewares.get(this.index);
            LOG.debug("{} calls next middleware {} ({} of {} in this chain)", this, currentMiddleware, index + 1, chainSize);

            try {
                this.middlewares.get(this.index).handleContext(this.ctx, this);
            } catch (Exception e) {
                this.ctx.handleException(e);
            }
        } else {
            LOG.debug("{} has no more middelwares to call (total: {} middlewares)", this, middlewares.size());
        }
    }

}
