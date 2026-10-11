package com.argon;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Event system using fixed arrays. No iterator allocation, no list resizing.
 * Max 32 listeners per event (plenty for any mod).
 */
public final class Events {

    private static final int MAX_LISTENERS = 32;
    private static final Map<String, BiConsumer<Object, Object>[]> LISTENERS = new HashMap<>(8);
    private static final int[] COUNTS = new int[MAX_LISTENERS]; // not used, see below

    public static void init() {}

    /** Subscribe — O(1) */
    @SuppressWarnings("unchecked")
    public static void on(String event, BiConsumer<Object, Object> listener) {
        BiConsumer<Object, Object>[] array = LISTENERS.get(event);
        if (array == null) {
            array = new BiConsumer[4]; // start small, grow by 2x
            LISTENERS.put(event, array);
            array[0] = listener;
            return;
        }
        // Find first null slot
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = listener;
                return;
            }
        }
        // Grow (rare)
        BiConsumer<Object, Object>[] grown = new BiConsumer[Math.min(array.length * 2, MAX_LISTENERS)];
        System.arraycopy(array, 0, grown, 0, array.length);
        grown[array.length] = listener;
        LISTENERS.put(event, grown);
    }

    /** Fire — zero allocation, no iterator */
    public static void fire(String event, Object subject, Object data) {
        BiConsumer<Object, Object>[] array = LISTENERS.get(event);
        if (array == null) return;
        for (int i = 0; i < array.length; i++) {
            BiConsumer<Object, Object> l = array[i];
            if (l != null) l.accept(subject, data);
        }
    }

    public static void fire(String event, Object subject) {
        fire(event, subject, null);
    }

    /** Remove all listeners for an event */
    public static void clear(String event) {
        LISTENERS.remove(event);
    }
}