package h04.function;

/**
 * Represents a function that accepts a double-valued argument and produces an int-valued result.
 *
 * <p>The function values can be calculated as follows:
 * <ul>
 *   <li>If {@code x * (n - 1)} has a maximum deviation of {@value DELTA} from an integer, the element is returned at the index
 *   {@code x * (n - 1)}</li>
 *   <li>Otherwise, the indices {@code x * (n - 1)} (rounded down) and {@code x * (n - 1)} (rounded up) are linearly
 *   interpolated and rounded to an integer.</li>
 * </ul>
 *
 * @author Nhan Huynh
 */
public class ArrayDoubleToIntFunction implements DoubleToIntFunction {

    /**
     * THe maximum deviation of the function (1.0e-6 = 1.0 x 10-6 = 0.0000010)..
     */
    private static final double DELTA = 1.0e-6;

    /**
     * The array of values used for this function.
     */
    private final int[] elements;

    /**
     * Constructs and initializes an {@code ArrayDoubleToIntFunction} with the given values used for the function.
     *
     * @param elements the array of values used for the function
     */
    public ArrayDoubleToIntFunction(int[] elements) {
        this.elements = new int[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    /**
     * Applies this function to the given argument.
     *
     * <p>If {@code x * (n - 1)} has a maximum deviation of 10^-6 from an integer, the element is returned at its index.
     * Otherwise, the indices {@code x * (n - 1)} (rounded down) and {@code x * (n - 1)} (rounded up) are linearly interpolated
     * and rounded to an integer.
     *
     * @param value the function argument
     *
     * @return the function result
     *
     * @throws IllegalArgumentException if the function argument is not between 0.0 (inclusive) and 1.0 (inclusive)
     */
    @Override
    public int apply(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("The function argument is not between 0.0 (inclusive) and 1.0 (inclusive)");
        }

        double x = value * (elements.length - 1);
        // Check if index has a maximum deviation of 10^-6 to a whole number and returns the element at that index
        if (Math.abs(x - Math.round(x)) < DELTA) {
            return elements[(int) Math.round(x)];
        }

        // If index is not an integer, interpolate between the two elements
        int x0 = (int) Math.floor(x);
        int x1 = (int) Math.ceil(x);

        double f0 = elements[x0];
        double f1 = elements[x1];

        // f(x) = f_0 + [f_1 - f_0]/[x_1 - x_0] * (x - x_0)
        return (int) Math.round(f0 + (f1 - f0) / (x1 - x0) * (x - x0));
    }
}
