package nl.rumfumme.util;

public class Constants {
    public static float EPSILON = 0.0001f;


    // max/min values for numbers

    /**
     * Same as Float.MAX_VALUE, but included for parity with MIN_VALUE,
     * and to avoid teaching static methods on the first day.
     */
    public static float MAX_FLOAT = Float.MAX_VALUE;
    /**
     * Note that Float.MIN_VALUE is the smallest <EM>positive</EM> value
     * for a floating point number, not actually the minimum (negative) value
     * for a float. This constant equals 0xFF7FFFFF, the smallest (farthest
     * negative) value a float can have before it hits NaN.
     */
    public static float MIN_FLOAT = -Float.MAX_VALUE;
    /** Largest possible (positive) integer value */
    public static int MAX_INT = Integer.MAX_VALUE;
    /** Smallest possible (negative) integer value */
    public static int MIN_INT = Integer.MIN_VALUE;

    /**
    *
    * <b>PI</b> is a mathematical constant with the value 3.1415927. It is the
    * ratio of the circumference of a circle to its diameter. It is useful in
    * combination with the trigonometric functions <b>sin()</b> and <b>cos()</b>.
    *
    * @webref constants
    * @webBrief PI is a mathematical constant with the value
    *           3.14159265358979323846
    * @see PConstants#TWO_PI
    * @see PConstants#TAU
    * @see PConstants#HALF_PI
    * @see PConstants#QUARTER_PI
    *
    */
    public static float PI = (float) java.lang.Math.PI;
   /**
    *
    * <b>HALF_PI</b> is a mathematical constant with the value 1.5707964. It is
    * half the ratio of the circumference of a circle to its diameter. It is useful
    * in combination with the trigonometric functions <b>sin()</b> and
    * <b>cos()</b>.
    *
    * @webref constants
    * @webBrief HALF_PI is a mathematical constant with the value
    *           1.57079632679489661923
    * @see PConstants#PI
    * @see PConstants#TWO_PI
    * @see PConstants#TAU
    * @see PConstants#QUARTER_PI
    */
   public static float HALF_PI = (float) (java.lang.Math.PI / 2.0);
   public static float THIRD_PI = (float) (java.lang.Math.PI / 3.0);
   /**
    *
    * <b>QUARTER_PI</b> is a mathematical constant with the value 0.7853982. It is
    * one quarter the ratio of the circumference of a circle to its diameter.
    * It is useful in combination with the trigonometric functions
    * <b>sin()</b> and <b>cos()</b>.
    *
    * @webref constants
    * @webBrief QUARTER_PI is a mathematical constant with the value 0.7853982
    * @see PConstants#PI
    * @see PConstants#TWO_PI
    * @see PConstants#TAU
    * @see PConstants#HALF_PI
    */
   public static float QUARTER_PI = (float) (java.lang.Math.PI / 4.0);
   /**
    *
    * <b>TWO_PI</b> is a mathematical constant with the value 6.2831855.
    * It is twice the ratio of the circumference of a circle to its diameter.
    * It is useful in combination with the trigonometric functions
    * <b>sin()</b> and <b>cos()</b>.
    *
    * @webref constants
    * @webBrief TWO_PI is a mathematical constant with the value 6.28318530717958647693
    * @see PConstants#PI
    * @see PConstants#TAU
    * @see PConstants#HALF_PI
    * @see PConstants#QUARTER_PI
    */
   public static float TWO_PI = (float) (2.0 * java.lang.Math.PI);
   /**
    *
    * <b>TAU</b> is a mathematical constant with the value 6.2831855. It is the
    * circle constant relating the circumference of a circle to its linear
    * dimension, the ratio of the circumference of a circle to its radius. It is
    * useful in combination with trigonometric functions such as <b>sin()</b> and
    * <b>cos()</b>.
    *
    * @webref constants
    * @webBrief An alias for <b>TWO_PI</b>
    * @see PConstants#PI
    * @see PConstants#TWO_PI
    * @see PConstants#HALF_PI
    * @see PConstants#QUARTER_PI
    */
   public static float TAU = (float) (2.0 * java.lang.Math.PI);

   public static float DEG_TO_RAD = PI/180.0f;
   public static float RAD_TO_DEG = 180.0f/PI;
}
