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
        for (int i = 0; i < elements.length; i++) {
            this.elements[i] = elements[i];
        }
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
        double index = value * (elements.length - 1);
        // Check if index has a maximum deviation of 10^-6 to a whole number and returns the element at that index
        if (Math.abs(index - Math.round(index)) < DELTA) {
            return elements[(int) Math.round(index)];
        }

        // If index is not an integer, interpolate between the two elements
        int leftIndex = (int) Math.floor(index);
        int rightIndex = (int) Math.ceil(index);

        double leftValue = elements[leftIndex];
        double rightValue = elements[rightIndex];

        /*
         * f(x) = f_0 + [f_1 - f_0]/[x_1 - x_0] * (x - x_0)
         * f = x * (n - 1)
         * x = index
         * f_0 = leftValue
         * f_1 = rightValue
         * x_0 = leftIndex
         * x_1 = rightIndex
         */
        return (int) Math.round(leftValue + (rightValue - leftValue) * (index - leftIndex));
    }
}
