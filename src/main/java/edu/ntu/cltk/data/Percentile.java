package edu.ntu.cltk.data;

import edu.ntu.cltk.exception.CLTKRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Percentile<T> {

    /**
     * Determines what percentile is computed when evaluate() is activated
     * with no quantile argument
     */
    private double quantile = 0.0;

    /**
     * The _orig store all numeric data with increasing order
     */
    private List<Object> _orig = new ArrayList<Object>();

    /**
     * Constructs a Percentile with a default quantile
     * value of 50.0.
     */
    public Percentile() {
        this(50.0);
    }

    /**
     * Constructs a Percentile with the specific quantile value.
     *
     * @param p the quantile
     * @throws IllegalArgumentException if p is not greater than 0 and less
     *                                  than or equal to 100
     */
    public Percentile(final double p) {
        setQuantile(p);
    }

    /**
     * Copy constructor, creates a new {@code Percentile} identical
     * to the {@code original}
     *
     * @param original the {@code Percentile} instance to copy
     */
    public Percentile(Percentile original) {
        copy(original, this);
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.
     *
     * @param source Percentile to copy
     * @param dest   Percentile to copy to
     * @throws NullPointerException if either source or dest is null
     */
    public static void copy(Percentile source, Percentile dest) {
        dest.quantile = source.quantile;
    }

    public void add(T num) {
        int i = 0;
        Object obj = (Object) num;

        for (; i < _orig.size(); i++) {
            if (compare(_orig.get(i), obj) != -1) {
                break;
            }
        }
        this._orig.add(i, obj);
    }

    /**
     * Compare two objects
     * 1	:	obj1 > obj2
     * 0	: 	obj1 == obj2
     * -1	:	obj1 < obj2
     *
     * @param obj1
     * @param obj2
     * @return
     */
    private int compare(Object obj1, Object obj2) {
        Class oClass1 = obj1.getClass();
        Class oClass2 = obj2.getClass();
        if (oClass1.equals(Integer.class) || obj2.equals(Integer.class)) {
            int o1 = (Integer) obj1;
            int o2 = (Integer) obj2;
            if (o1 == o2) return 0;
            else if (o1 > o2) return 1;
            return -1;
        } else if (oClass1.equals(Double.class) || obj2.equals(Double.class)) {
            double o1 = (Double) obj1;
            double o2 = (Double) obj2;
            if (PrimUtil.equalsTo(o1, o2)) {
                return 0;
            } else if (o1 > o2) {
                return 1;
            } else {
                return -1;
            }
        } else {
            throw CLTKRuntimeException.createIllegalArgumentException(
                    String.format("%s %s cannot be compared", oClass1.toString(), oClass2.toString())
            );
        }
    }

    /**
     * Get the percentile in the array
     *
     * @param quantile
     * @return
     */
    public T evaluate(double quantile) {
        synchronized (this._orig) {
            int i = ((int) Math.round(quantile * this._orig.size() / 100)) - 1;
            if (i < 0) i = 0;
            if (i >= this._orig.size()) i--;
            return (T) this._orig.get(i);
        }

    }

    public T evaluate() {
        return evaluate(this.quantile);
    }

    /**
     * Returns an estimate of the <code>pth percentile of the values
     * in the <code>values array.
     * <p>
     * Calls to this method do not modify the internal <code>quantile
     * state of this statistic.</p>
     * <p>
     * <ul>
     * <li>Returns Double.NaN if values has length
     * <code>0
     * <li>Returns (for any value of p) values[0]
     * if <code>values has length 1
     * <li>Throws IllegalArgumentException if values
     * is null or p is not a valid quantile value (p must be greater than 0
     * and less than or equal to 100) </li>
     * </ul>
     * <p>
     * <p>
     * See {@link Percentile} for a description of the percentile estimation
     * algorithm used.</p>
     *
     * @param values input array of values
     * @param p      the percentile value to compute
     * @return the percentile value or Double.NaN if the array is empty
     * @throws IllegalArgumentException if <code>values is null
     *                                  or p is invalid
     */
    public double evaluate(final double[] values, final double p) {
        //test(values, 0, 0);
        return evaluate(values, 0, values.length, p);
    }

    /**
     * Returns an estimate of the <code>quantileth percentile of the
     * designated values in the <code>values array.  The quantile
     * estimated is determined by the <code>quantile property.
     * <p>
     * <ul>
     * <li>Returns Double.NaN if length = 0
     * <li>Returns (for any value of quantile)
     * <code>values[begin] if length = 1
     * <li>Throws IllegalArgumentException if values
     * is null,  or <code>start or length
     * is invalid</li>
     * </ul>
     * <p>
     * <p>
     * See {@link Percentile} for a description of the percentile estimation
     * algorithm used.</p>
     *
     * @param values the input array
     * @param start  index of the first array element to include
     * @param length the number of elements to include
     * @return the percentile value
     * @throws IllegalArgumentException if the parameters are not valid
     */
    public double evaluate(final double[] values, final int start, final int length) {
        return evaluate(values, start, length, quantile);
    }

    /**
     * Returns an estimate of the <code>pth percentile of the values
     * in the <code>values array, starting with the element in (0-based)
     * position <code>begin in the array and including length
     * values.
     * <p>
     * Calls to this method do not modify the internal <code>quantile
     * state of this statistic.</p>
     * <p>
     * <ul>
     * <li>Returns Double.NaN if length = 0
     * <li>Returns (for any value of p) values[begin]
     * if <code>length = 1
     * <li>Throws IllegalArgumentException if values
     * is null , <code>begin or length is invalid, or
     * <code>p is not a valid quantile value (p must be greater than 0
     * and less than or equal to 100)</li>
     * </ul>
     * <p>
     * <p>
     * See {@link Percentile} for a description of the percentile estimation
     * algorithm used.</p>
     *
     * @param values array of input values
     * @param p      the percentile to compute
     * @param begin  the first (0-based) element to include in the computation
     * @param length the number of array elements to include
     * @return the percentile value
     * @throws IllegalArgumentException if the parameters are not valid or the
     *                                  input array is null
     */
    public double evaluate(final double[] values, final int begin,
                           final int length, final double p) {

        if ((p > 100) || (p <= 0)) {
            throw CLTKRuntimeException.createIllegalArgumentException(
                    "out of bounds quantile value: {0}, must be in (0, 100]", p);
        }
        if (length == 0) {
            return Double.NaN;
        }
        if (length == 1) {
            return values[begin]; // always return single value for n = 1
        }
        double n = length;
        double pos = p * (n + 1) / 100;
        double fpos = Math.floor(pos);
        int intPos = (int) fpos;
        double dif = pos - fpos;
        double[] sorted = new double[length];
        System.arraycopy(values, begin, sorted, 0, length);
        Arrays.sort(sorted);

        if (pos < 1) {
            return sorted[0];
        }
        if (pos >= n) {
            return sorted[length - 1];
        }
        double lower = sorted[intPos - 1];
        double upper = sorted[intPos];
        return lower + dif * (upper - lower);
    }

    /**
     * Returns the value of the quantile field (determines what percentile is
     * computed when evaluate() is called with no quantile argument).
     *
     * @return quantile
     */
    public double getQuantile() {
        return quantile;
    }

    /**
     * Sets the value of the quantile field (determines what percentile is
     * computed when evaluate() is called with no quantile argument).
     *
     * @param p a value between 0 < p <= 100
     * @throws IllegalArgumentException if p is not greater than 0 and less
     *                                  than or equal to 100
     */
    public void setQuantile(final double p) {
        if (p <= 0 || p > 100) {
            throw CLTKRuntimeException.createIllegalArgumentException(
                    "out of bounds quantile value: {0}, must be in (0, 100]", p);
        }
        quantile = p;
    }

    /**
     * {@inheritDoc}
     */
    public Percentile copy() {
        Percentile result = new Percentile();
        copy(this, result);
        return result;
    }

}