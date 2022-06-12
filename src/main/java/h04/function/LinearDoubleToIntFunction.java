package h04.function;

/**
 * Represents a function that accepts a double-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated as a linear function of the input value and the coefficients.
 *
 * @author Nhan Huynh
 */
public class LinearDoubleToIntFunction implements DoubleToIntFunction {

    /**
     * The f_1 coefficient.
     */
    public final double a;

    /**
     * The f_0 coefficient.
     */
    public final double b;

    /**
     * Constructs and initializes a {@code LinearDoubleToIntFunction} with the specified coefficients.
     *
     * @param a the f_0 coefficient
     * @param b the f_1 coefficient
     */
    public LinearDoubleToIntFunction(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int apply(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("The function argument is not between 0.0 (inclusive) and 1.0 (inclusive)");
        }
        return (int) Math.round(a * value + b);
    }
}
