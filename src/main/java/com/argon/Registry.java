package com.argon;

import java.util.HashMap;
import java.util.Map;

/**
 * Central registry. Single-threaded (game loop) — no concurrency overhead.
 * Pre-allocated to avoid resizing.
 */
public final class Registry {

    // Pre-size: 16 buckets = handles ~10 entries before first resize
    private static final Map<String, Map<String, Object>> REGISTRIES = new HashMap<>(8);

    public static void init() {
        REGISTRIES.put("items",    new HashMap<>(16));
        REGISTRIES.put("blocks",   new HashMap<>(16));
        REGISTRIES.put("entities", new HashMap<>(16));
        REGISTRIES.put("recipes",  new HashMap<>(16));
    }

    public static void register(String registry, String id, Object value) {
        Map<String, Object> map = REGISTRIES.get(registry);
        if (map == null) {
            map = new HashMap<>(16);
            REGISTRIES.put(registry, map);
        }
        map.put(id, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String registry, String id) {
        Map<String, Object> map = REGISTRIES.get(registry);
        return map != null ? (T) map.get(id) : null;
    }

    /** Returns the raw map — caller must NOT modify. Avoids wrapper allocation. */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getAll(String registry) {
        return (Map<String, T>) REGISTRIES.getOrDefault(registry, Map.of());
    }

    public static boolean contains(String registry, String id) {
        Map<String, Object> map = REGISTRIES.get(registry);
        return map != null && map.containsKey(id);
    }

    public static Object remove(String registry, String id) {
        Map<String, Object> map = REGISTRIES.get(registry);
        return map != null ? map.remove(id) : null;
    }

    /** Clear a single registry (useful for /reload) */
    public static void clear(String registry) {
        Map<String, Object> map = REGISTRIES.get(registry);
        if (map != null) map.clear();
    }
}