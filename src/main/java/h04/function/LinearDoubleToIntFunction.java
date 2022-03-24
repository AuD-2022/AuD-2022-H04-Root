package h04.function;

/**
 * Represents a function that accepts a double-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated as a linear function of the input value and the
 * coefficients.
 *
 * @author Nhan Huynh
 */
public class LinearDoubleToIntFunction implements DoubleToIntFunction {

    /**
     * The f_1 coefficient.
     */
    private final double a;

    /**
     * The f_0 coefficient.
     */
    private final double b;

    /**
     * Constructs and initializes a {@code LinearDoubleToIntFunction} with the specified
     * coefficients.
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
        return (int) Math.round(a * value + b);
    }
}
