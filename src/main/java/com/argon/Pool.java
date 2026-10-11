package com.argon;

import java.util.ArrayDeque;

/**
 * Simple object pool. Avoids GC pressure in hot paths.
 *
 * Usage:
 *   var buf = Pool.get(StringBuilder.class, () -> new StringBuilder(64));
 *   buf.append("hello");
 *   // ... use buf ...
 *   Pool.release(buf);
 */
public final class Pool {

    private static final ArrayDeque<Object> POOL = new ArrayDeque<>(64);
    private static final int MAX_SIZE = 256;

    /** Acquire an object from the pool or create a new one */
    public static <T> T get(Class<T> type, java.util.function.Supplier<T> factory) {
        Object obj = POOL.pollFirst();
        if (obj != null && type.isInstance(obj)) {
            @SuppressWarnings("unchecked")
            T result = (T) obj;
            return result;
        }
        return factory.get();
    }

    /** Return an object to the pool. Callers MUST reset state before releasing. */
    public static void release(Object obj) {
        if (obj != null && POOL.size() < MAX_SIZE) {
            POOL.addLast(obj);
        }
    }

    /** Convenience: pooled StringBuilder */
    public static StringBuilder getBuilder() {
        return get(StringBuilder.class, () -> new StringBuilder(64));
    }

    /** Release a pooled StringBuilder (clears it) */
    public static void releaseBuilder(StringBuilder sb) {
        if (sb != null) {
            sb.setLength(0);
            release(sb);
        }
    }
}