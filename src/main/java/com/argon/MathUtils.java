package com.argon;

public final class MathUtils {

    private MathUtils() {}

    /** Linear interpolation between a and b by factor t. */
    public static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    /** Linear interpolation for floats. */
    public static float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }

    public static float clamp(float v, float min, float max) {
        return v < min ? min : (v > max ? max : v);
    }

    /** Fast integer square root using binary search/bit shifts. */
    public static int sqrtInt(int n) {
        if (n < 0) return 0;
        int res = 0;
        int bit = 1 << 30; // The second-to-top bit
        while (bit > n) {
            bit >>= 2;
        }
        while (bit != 0) {
            if (n >= res + bit) {
                n -= res + bit;
                res = (res >> 1) + bit;
            } else {
                res >>= 1;
            }
            bit >>= 2;
        }
        return res;
    }

    /** Wraps an angle in degrees to the range [0, 360). */
    public static float wrapDegrees(float degrees) {
        float f = degrees % 360.0f;
        if (f >= 180.0f) f -= 360.0f;
        if (f < -180.0f) f += 360.0f;
        return f;
    }
}