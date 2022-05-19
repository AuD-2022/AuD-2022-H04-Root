package h04.function;

import java.util.function.Function;

/**
 * Represents a function that accepts a double-valued argument and produces an int-valued result. This is the {@code double}-to
 * -{@code int} primitive specialization for {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is {@link #apply(double)}.
 *
 * @author Nhan Huynh
 */
@FunctionalInterface
public interface DoubleToIntFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     *
     * @return the function result
     *
     * @throws IllegalArgumentException if the function argument is not between 0.0 (inclusive) and 1.0 (inclusive)
     */
    int apply(double value);
}
