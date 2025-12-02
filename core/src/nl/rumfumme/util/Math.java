package nl.rumfumme.util;

import static nl.rumfumme.util.Constants.*;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Math {
    //////////////////////////////////////////////////////////////

    // MATH

    // lots of convenience methods for math with floats.
    // doubles are overkill for processing sketches, and casting
    // things all the time is annoying, thus the functions below.

  /**
     *
     * Calculates the absolute value (magnitude) of a number. The absolute
     * value of a number is always positive.
     *
     * @webref math:calculation
     * @webBrief Calculates the absolute value (magnitude) of a number
     * @param n number to compute
     */
    static public final float abs(float n) {
      return (n < 0) ? -n : n;
    }

    static public final int abs(int n) {
      return (n < 0) ? -n : n;
    }

  /**
     *
     * Squares a number (multiplies a number by itself). The result is always a
     * positive number, as multiplying two negative numbers always yields a
     * positive result. For example, <b>-1 * -1 = 1.</b>
     *
     * @webref math:calculation
     * @webBrief Squares a number (multiplies a number by itself)
     * @param n number to square
     * @see PApplet#sqrt(float)
     */
    static public final float sq(float n) {
      return n*n;
    }

  /**
     *
     * Calculates the square root of a number. The square root of a number is
     * always positive, even though there may be a valid negative root. The
     * square root <b>s</b> of number <b>a</b> is such that <b>s*s = a</b>. It
     * is the opposite of squaring.
     *
     * @webref math:calculation
     * @webBrief Calculates the square root of a number
     * @param n non-negative number
     * @see PApplet#pow(float, float)
     * @see PApplet#sq(float)
     */
    static public final float sqrt(float n) {
      return (float)java.lang.Math.sqrt(n);
    }

  /**
     *
     * Calculates the natural logarithm (the base-<i>e</i> logarithm) of a
     * number. This function expects the values greater than 0.0.
     *
     * @webref math:calculation
     * @webBrief Calculates the natural logarithm (the base-<i>e</i> logarithm) of a
     * number
     * @param n number greater than 0.0
     */
    static public final float log(float n) {
      return (float)java.lang.Math.log(n);
    }

  /**
     *
     * Returns Euler's number <i>e</i> (2.71828...) raised to the power of the
     * <b>value</b> parameter.
     *
     * @webref math:calculation
     * @webBrief Returns Euler's number <i>e</i> (2.71828...) raised to the power of the
     * <b>value</b> parameter
     * @param n exponent to raise
     */
    static public final float exp(float n) {
      return (float)java.lang.Math.exp(n);
    }

  /**
     *
     * Facilitates exponential expressions. The <b>pow()</b> function is an
     * efficient way of multiplying numbers by themselves (or their reciprocal)
     * in large quantities. For example, <b>pow(3, 5)</b> is equivalent to the
     * expression 3*3*3*3*3 and <b>pow(3, -5)</b> is equivalent to 1 / 3*3*3*3*3.
     *
     * @webref math:calculation
     * @webBrief Facilitates exponential expressions
     * @param n base of the exponential expression
     * @param e power by which to raise the base
     * @see PApplet#sqrt(float)
     */
    static public final float pow(float n, float e) {
      return (float)java.lang.Math.pow(n, e);
    }

  /**
   *
   * Determines the largest value in a sequence of numbers, and then returns that
   * value. <b>max()</b> accepts either two or three <b>float</b> or <b>int</b>
   * values as parameters, or an array of any length.
   *
   * @webref math:calculation
   * @webBrief Determines the largest value in a sequence of numbers
   * @param a
   *          first number to compare
   * @param b
   *          second number to compare
   * @see PApplet#min(float, float, float)
   */
    static public final int max(int a, int b) {
      return (a > b) ? a : b;
    }

    static public final float max(float a, float b) {
      return (a > b) ? a : b;
    }

    /*
    static public final double max(double a, double b) {
      return (a > b) ? a : b;
    }
    */

  /**
   * @param c third number to compare
   */
    static public final int max(int a, int b, int c) {
      return (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
    }


    static public final float max(float a, float b, float c) {
      return (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
    }


    /**
     * @param list array of numbers to compare
     */
    static public final int max(int[] list) {
      if (list.length == 0) {
        throw new ArrayIndexOutOfBoundsException();
      }
      int max = list[0];
      for (int i = 1; i < list.length; i++) {
        if (list[i] > max) max = list[i];
      }
      return max;
    }

    static public final float max(float[] list) {
      if (list.length == 0) {
        throw new ArrayIndexOutOfBoundsException();
      }
      float max = list[0];
      for (int i = 1; i < list.length; i++) {
        if (list[i] > max) max = list[i];
      }
      return max;
    }


  //  /**
  //   * Find the maximum value in an array.
  //   * Throws an ArrayIndexOutOfBoundsException if the array is length 0.
  //   * @param list the source array
  //   * @return The maximum value
  //   */
    /*
    static public final double max(double[] list) {
      if (list.length == 0) {
        throw new ArrayIndexOutOfBoundsException(ERROR_MIN_MAX);
      }
      double max = list[0];
      for (int i = 1; i < list.length; i++) {
        if (list[i] > max) max = list[i];
      }
      return max;
    }
    */


    static public final int min(int a, int b) {
      return (a < b) ? a : b;
    }

    static public final float min(float a, float b) {
      return (a < b) ? a : b;
    }

    /*
    static public final double min(double a, double b) {
      return (a < b) ? a : b;
    }
    */


    static public final int min(int a, int b, int c) {
      return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
    }

  /**
   *
   * Determines the smallest value in a sequence of numbers, and then returns that
   * value. <b>min()</b> accepts either two or three <b>float</b> or <b>int</b>
   * values as parameters, or an array of any length.
   *
   * @webref math:calculation
   * @webBrief Determines the smallest value in a sequence of numbers
   * @param a
   *          first number
   * @param b
   *          second number
   * @param c
   *          third number
   * @see PApplet#max(float, float, float)
   */
    static public final float min(float a, float b, float c) {
      return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
    }

    /*
    static public final double min(double a, double b, double c) {
      return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
    }
    */


    /**
     * @param list array of numbers to compare
     */
    static public final int min(int[] list) {
      if (list.length == 0) {
        throw new ArrayIndexOutOfBoundsException();
      }
      int min = list[0];
      for (int i = 1; i < list.length; i++) {
        if (list[i] < min) min = list[i];
      }
      return min;
    }

    static public final float min(float[] list) {
      if (list.length == 0) {
        throw new ArrayIndexOutOfBoundsException();
      }
      float min = list[0];
      for (int i = 1; i < list.length; i++) {
        if (list[i] < min) min = list[i];
      }
      return min;
    }


    /*
     * Find the minimum value in an array.
     * Throws an ArrayIndexOutOfBoundsException if the array is length 0.
     * @param list the source array
     * @return The minimum value
     */
    /*
    static public final double min(double[] list) {
      if (list.length == 0) {
        throw new ArrayIndexOutOfBoundsException(ERROR_MIN_MAX);
      }
      double min = list[0];
      for (int i = 1; i < list.length; i++) {
        if (list[i] < min) min = list[i];
      }
      return min;
    }
    */


    static public final int constrain(int amt, int low, int high) {
      return (amt < low) ? low : ((amt > high) ? high : amt);
    }

  /**
     *
     * Constrains a value to not exceed a maximum and minimum value.
     *
     * @webref math:calculation
     * @webBrief Constrains a value to not exceed a maximum and minimum value
     * @param amt the value to constrain
     * @param low minimum limit
     * @param high maximum limit
     * @see PApplet#max(float, float, float)
     * @see PApplet#min(float, float, float)
     */

    static public final float constrain(float amt, float low, float high) {
      return (amt < low) ? low : ((amt > high) ? high : amt);
    }

  /**
     *
     * Calculates the sine of an angle. This function expects the values of the
     * <b>angle</b> parameter to be provided in radians (values from 0 to
     * 6.28). Values are returned in the range -1 to 1.
     *
     * @webref math:trigonometry
     * @webBrief Calculates the sine of an angle
     * @param angle an angle in radians
     * @see PApplet#cos(float)
     * @see PApplet#tan(float)
     * @see PApplet#radians(float)
     */
    static public final float sin(float angle) {
      return (float)java.lang.Math.sin(angle);
    }

  /**
     *
     * Calculates the cosine of an angle. This function expects the values of
     * the <b>angle</b> parameter to be provided in radians (values from 0 to
     * PI*2). Values are returned in the range -1 to 1.
     *
     * @webref math:trigonometry
     * @webBrief Calculates the cosine of an angle
     * @param angle an angle in radians
     * @see PApplet#sin(float)
     * @see PApplet#tan(float)
     * @see PApplet#radians(float)
     */
    static public final float cos(float angle) {
      return (float)java.lang.Math.cos(angle);
    }

  /**
     *
     * Calculates the ratio of the sine and cosine of an angle. This function
     * expects the values of the <b>angle</b> parameter to be provided in
     * radians (values from 0 to PI*2). Values are returned in the range
     * <b>infinity</b> to <b>-infinity</b>.
     *
     * @webref math:trigonometry
     * @webBrief Calculates the ratio of the sine and cosine of an angle
     * @param angle an angle in radians
     * @see PApplet#cos(float)
     * @see PApplet#sin(float)
     * @see PApplet#radians(float)
     */
    static public final float tan(float angle) {
      return (float)java.lang.Math.tan(angle);
    }

  /**
     *
     * The inverse of <b>sin()</b>, returns the arc sine of a value. This
     * function expects the values in the range of -1 to 1 and values are
     * returned in the range <b>-PI/2</b> to <b>PI/2</b>.
     *
     * @webref math:trigonometry
     * @webBrief The inverse of <b>sin()</b>, returns the arc sine of a value
     * @param value the value whose arc sine is to be returned
     * @see PApplet#sin(float)
     * @see PApplet#acos(float)
     * @see PApplet#atan(float)
     */
    static public final float asin(float value) {
      return (float)java.lang.Math.asin(value);
    }

  /**
     *
     * The inverse of <b>cos()</b>, returns the arc cosine of a value. This
     * function expects the values in the range of -1 to 1 and values are
     * returned in the range <b>0</b> to <b>PI (3.1415927)</b>.
     *
     * @webref math:trigonometry
     * @webBrief The inverse of <b>cos()</b>, returns the arc cosine of a value
     * @param value the value whose arc cosine is to be returned
     * @see PApplet#cos(float)
     * @see PApplet#asin(float)
     * @see PApplet#atan(float)
     */
    static public final float acos(float value) {
      return (float)java.lang.Math.acos(value);
    }

  /**
     *
     * The inverse of <b>tan()</b>, returns the arc tangent of a value. This
     * function expects the values in the range of -Infinity to Infinity
     * (exclusive) and values are returned in the range <b>-PI/2</b> to <b>PI/2 </b>.
     *
     * @webref math:trigonometry
     * @webBrief The inverse of <b>tan()</b>, returns the arc tangent of a value
     * @param value -Infinity to Infinity (exclusive)
     * @see PApplet#tan(float)
     * @see PApplet#asin(float)
     * @see PApplet#acos(float)
     */
    static public final float atan(float value) {
      return (float)java.lang.Math.atan(value);
    }

  /**
     *
     * Calculates the angle (in radians) from a specified point to the
     * coordinate origin as measured from the positive x-axis. Values are
     * returned as a <b>float</b> in the range from <b>PI</b> to <b>-PI</b>.
     * The <b>atan2()</b> function is most often used for orienting geometry to
     * the position of the cursor.  Note: The y-coordinate of the point is the
     * first parameter and the x-coordinate is the second due the structure
     * of calculating the tangent.
     *
     * @webref math:trigonometry
     * @webBrief Calculates the angle (in radians) from a specified point to the
     * coordinate origin as measured from the positive x-axis
     * @param y y-coordinate of the point
     * @param x x-coordinate of the point
     * @see PApplet#tan(float)
     */
    static public final float atan2(float y, float x) {
      return (float)java.lang.Math.atan2(y, x);
    }

  /**
     *
     * Converts a radian measurement to its corresponding value in degrees.
     * Radians and degrees are two ways of measuring the same thing. There are
     * 360 degrees in a circle and 2*PI radians in a circle. For example,
     * 90&deg; = PI/2 = 1.5707964. All trigonometric functions in Processing
     * require their parameters to be specified in radians.
     *
     * @webref math:trigonometry
     * @webBrief Converts a radian measurement to its corresponding value in degrees
     * @param radians radian value to convert to degrees
     * @see PApplet#radians(float)
     */
    static public final float degrees(float radians) {
      return radians * RAD_TO_DEG;
    }

  /**
     *
     * Converts a degree measurement to its corresponding value in radians.
     * Radians and degrees are two ways of measuring the same thing. There are
     * 360 degrees in a circle and 2*PI radians in a circle. For example,
     * 90&deg; = PI/2 = 1.5707964. All trigonometric functions in Processing
     * require their parameters to be specified in radians.
     *
     * @webref math:trigonometry
     * @webBrief Converts a degree measurement to its corresponding value in radians
     * @param degrees degree value to convert to radians
     * @see PApplet#degrees(float)
     */
    static public final float radians(float degrees) {
      return degrees * DEG_TO_RAD;
    }

  /**
     *
     * Calculates the closest int value that is greater than or equal to the
     * value of the parameter. For example, <b>ceil(9.03)</b> returns the value 10.
     *
     * @webref math:calculation
     * @webBrief Calculates the closest int value that is greater than or equal to the
     * value of the parameter
     * @param n number to round up
     * @see PApplet#floor(float)
     * @see PApplet#round(float)
     */
    static public final int ceil(float n) {
      return (int) java.lang.Math.ceil(n);
    }

  /**
     *
     * Calculates the closest int value that is less than or equal to the value
     * of the parameter.
     *
     * @webref math:calculation
     * @webBrief Calculates the closest int value that is less than or equal to the value
     * of the parameter
     * @param n number to round down
     * @see PApplet#ceil(float)
     * @see PApplet#round(float)
     */
    static public final int floor(float n) {
      return (int) java.lang.Math.floor(n);
    }

  /**
   *
   * Calculates the integer closest to the <b>n</b> parameter. For example,
   * <b>round(133.8)</b> returns the value 134.
   *
   * @webref math:calculation
   * @webBrief Calculates the integer closest to the <b>value</b> parameter
   * @param n
   *          number to round
   * @see PApplet#floor(float)
   * @see PApplet#ceil(float)
   */
    static public final int round(float n) {
      return java.lang.Math.round(n);
    }


    static public final float mag(float a, float b) {
      return (float)java.lang.Math.sqrt(a*a + b*b);
    }

  /**
     *
     * Calculates the magnitude (or length) of a vector. A vector is a
     * direction in space commonly used in computer graphics and linear
     * algebra. Because it has no "start" position, the magnitude of a vector
     * can be thought of as the distance from coordinate (0,0) to its (x,y)
     * value. Therefore, <b>mag()</b> is a shortcut for writing <b>dist(0, 0, x, y)</b>.
     *
     * @webref math:calculation
     * @webBrief Calculates the magnitude (or length) of a vector
     * @param a first value
     * @param b second value
     * @param c third value
     * @see PApplet#dist(float, float, float, float)
     */
    static public final float mag(float a, float b, float c) {
      return (float)java.lang.Math.sqrt(a*a + b*b + c*c);
    }


    static public final float dist(float x1, float y1, float x2, float y2) {
      return sqrt(sq(x2-x1) + sq(y2-y1));
    }

  /**
     *
     * Calculates the distance between two points.
     *
     * @webref math:calculation
     * @webBrief Calculates the distance between two points
     * @param x1 x-coordinate of the first point
     * @param y1 y-coordinate of the first point
     * @param z1 z-coordinate of the first point
     * @param x2 x-coordinate of the second point
     * @param y2 y-coordinate of the second point
     * @param z2 z-coordinate of the second point
     */
    static public final float dist(float x1, float y1, float z1,
                                   float x2, float y2, float z2) {
      return sqrt(sq(x2-x1) + sq(y2-y1) + sq(z2-z1));
    }

  /**
     *
     * Calculates a number between two numbers at a specific increment. The
     * <b>amt</b> parameter is the amount to interpolate between the two values
     * where 0.0 equal to the first point, 0.1 is very near the first point,
     * 0.5 is half-way in between, etc. The lerp function is convenient for
     * creating motion along a straight path and for drawing dotted lines.
     *
     * @webref math:calculation
     * @webBrief Calculates a number between two numbers at a specific increment
     * @param start first value
     * @param stop second value
     * @param amt float between 0.0 and 1.0
     * @see PGraphics#curvePoint(float, float, float, float, float)
     * @see PGraphics#bezierPoint(float, float, float, float, float)
     * @see Vector2#lerp(Vector2, float)
     * @see PGraphics#lerpColor(int, int, float)
     */
    static public final float lerp(float start, float stop, float amt) {
      return start + (stop-start) * amt;
    }

    /**
     *
     * Normalizes a number from another range into a value between 0 and 1.
     * Identical to <b>map(value, low, high, 0, 1)</b>.<br />
     * <br />
     * Numbers outside the range are not clamped to 0 and 1, because
     * out-of-range values are often intentional and useful. (See the second
     * example above.)
     *
     * @webref math:calculation
     * @webBrief Normalizes a number from another range into a value between 0 and
     *           1
     * @param value
     *          the incoming value to be converted
     * @param start
     *          lower bound of the value's current range
     * @param stop
     *          upper bound of the value's current range
     * @see PApplet#map(float, float, float, float, float)
     * @see PApplet#lerp(float, float, float)
     */
    static public final float norm(float value, float start, float stop) {
      return (value - start) / (stop - start);
    }

    /**
     *
     * Re-maps a number from one range to another.<br />
     * <br />
     * In the first example above, the number 25 is converted from a value in the
     * range of 0 to 100 into a value that ranges from the left edge of the window
     * (0) to the right edge (width).<br />
     * <br />
     * As shown in the second example, numbers outside the range are
     * not clamped to the minimum and maximum parameters values,
     * because out-of-range values are often intentional and useful.
     *
     * @webref math:calculation
     * @webBrief Re-maps a number from one range to another
     * @param value
     *          the incoming value to be converted
     * @param start1
     *          lower bound of the value's current range
     * @param stop1
     *          upper bound of the value's current range
     * @param start2
     *          lower bound of the value's target range
     * @param stop2
     *          upper bound of the value's target range
     * @see PApplet#norm(float, float, float)
     * @see PApplet#lerp(float, float, float)
     */
    static public final float map(float value,
                                  float start1, float stop1,
                                  float start2, float stop2) {
      float outgoing =
        start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
      String badness = null;
      if (outgoing != outgoing) {
        badness = "NaN (not a number)";

      } else if (outgoing == Float.NEGATIVE_INFINITY ||
                 outgoing == Float.POSITIVE_INFINITY) {
        badness = "infinity";
      }
      if (badness != null) {
//        final String msg =
//          String.format("map(%s, %s, %s, %s, %s) called, which returns %s",
//                        nf(value), nf(start1), nf(stop1),
//                        nf(start2), nf(stop2), badness);
//        PGraphics.showWarning(msg);
      }
      return outgoing;
    }

    public static float fract(final float x) {
        return x-(int)x;
    }
    
    public static float mix(final float x, final float y, final float a) {
        return x*(1f-a)+y*a;
    }
    
    public static float clamp(final float x, final float minVal, final float maxVal) {
        return Math.min(Math.max(x,minVal),maxVal);
    }

    /**
     * Adapted from
     * https://github.com/radekp/qt/blob/master/src/gui/painting/qtransform.cpp
     * 
     * @param q1
     * @param q2
     * @param q3
     * @param q4
     * @param m
     * @return
     */
    public static boolean squareToQuad(Vector2 q1, Vector2 q2, Vector2 q3, Vector2 q4, Matrix4 m) {
        float A, B, C, D, E, F, G, H;

        float ax = q1.x - q2.x + q3.x - q4.x;
        float ay = q1.y - q2.y + q3.y - q4.y;

        C = q1.x;
        F = q1.y;

        if (ax == 0.0f && ay == 0.0f) {
            G = 0.0f;
            H = 0.0f;

            A = q2.x - q1.x;
            B = q3.x - q2.x;
            D = q2.y - q1.y;
            E = q3.y - q2.y;
        } else {
            float ax1 = q2.x - q3.x;
            float ax2 = q4.x - q3.x;
            float ay1 = q2.y - q3.y;
            float ay2 = q4.y - q3.y;

            /* determinants */
            float gtop = ax * ay2 - ax2 * ay;
            float htop = ax1 * ay - ax * ay1;
            float bottom = ax1 * ay2 - ax2 * ay1;

            if (bottom == 0.0f) {
                return false;
            }

            G = gtop / bottom;
            H = htop / bottom;

            A = q2.x - q1.x + G * q2.x;
            B = q4.x - q1.x + H * q4.x;
            D = q2.y - q1.y + G * q2.y;
            E = q4.y - q1.y + H * q4.y;
        }

        m.set(new float[] {
                A, D, 0.0f, G,
                B, E, 0.0f, H,
                0.0f, 0.0f, 1.0f, 0.0f,
                C, F, 0.0f, 1.0f });

        return true;
    }

    public static boolean quadToSquare(Vector2 q1, Vector2 q2, Vector2 q3, Vector2 q4, Matrix4 m) {
        if (!squareToQuad(q1, q2, q3, q4, m)) {
            return false;
        }
        
        try {
            m.inv();
        } catch (RuntimeException e) {
            return false;
        }

        return true;
    }
}
