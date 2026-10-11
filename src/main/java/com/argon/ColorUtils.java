package com.argon;

public final class ColorUtils {

    private ColorUtils() {}

    /** Packs individual RGBA components (0-255) into a single 32-bit ARGB integer. */
    public static int packARGB(int alpha, int red, int green, int blue) {
        return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF);
    }

    /** Extracts the Alpha channel from a packed ARGB integer. */
    public static int getAlpha(int argb) {
        return (argb >> 24) & 0xFF;
    }

    /** Extracts the Red channel from a packed ARGB integer. */
    public static int getRed(int argb) {
        return (argb >> 16) & 0xFF;
    }

    /** Extracts the Green channel from a packed ARGB integer. */
    public static int getGreen(int argb) {
        return (argb >> 8) & 0xFF;
    }

    /** Extracts the Blue channel from a packed ARGB integer. */
    public static int getBlue(int argb) {
        return argb & 0xFF;
    }

    /** Multiplies the alpha channel of an ARGB color by a float factor (0.0 to 1.0). */
    public static int multiplyAlpha(int argb, float alphaFactor) {
        int alpha = (int) (getAlpha(argb) * MathUtils.clamp(alphaFactor, 0.0f, 1.0f));
        return packARGB(alpha, getRed(argb), getGreen(argb), getBlue(argb));
    }
}