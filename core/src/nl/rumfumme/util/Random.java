package nl.rumfumme.util;

public class Random {
    private static java.util.Random internalRandom = new java.util.Random();

    /**
     *
     */
    public static final float random(float high) {
        // avoid an infinite loop when 0 or NaN are passed in
        if (high == 0 || high != high) {
            return 0;
        }

        // for some reason (rounding error?) Math.random() * 3
        // can sometimes return '3' (once in ~30 million tries)
        // so a check was added to avoid the inclusion of 'howbig'
        float value;
        do {
            value = internalRandom.nextFloat() * high;
        } while (value == high);
        return value;
    }

    /**
     *
     * Generates random numbers. Each time the <b>random()</b> function is called,
     * it returns an unexpected value within the specified range. If only one
     * parameter is passed to the function, it will return a float between zero
     * and the value of the <b>high</b> parameter. For example, <b>random(5)</b>
     * returns values between 0 and 5 (starting at zero, and up to, but not
     * including, 5).<br />
     * <br />
     * If two parameters are specified, the function will return a float with a
     * value between the two values. For example, <b>random(-5, 10.2)</b> returns
     * values starting at -5 and up to (but not including) 10.2. To convert a
     * floating-point random number to an integer, use the <b>int()</b> function.
     *
     * @webref math:random
     * @webBrief Generates random numbers
     * @param low
     *          lower limit
     * @param high
     *          upper limit
     * @see PApplet#randomSeed(long)
     * @see PApplet#noise(float, float, float)
     */
    public static final float random(float low, float high) {
        if (low >= high) return low;
        float diff = high - low;
        float value;
        // because of rounding error, can't just add low, otherwise it may hit high
        // https://github.com/processing/processing/issues/4551
        do {
            value = random(diff) + low;
        } while (value == high);
        return value;
    }
}
