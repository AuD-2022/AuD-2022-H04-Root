package h04.function;

import java.util.List;
import java.util.function.Function;

/**
 * Represents a function that accepts a list-valued argument and produces an int-valued result. This is the {@code List}-to
 * -{@code int} specialization for {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is {@link #apply(List)}.
 *
 * @author Nhan Huynh
 */
@FunctionalInterface
public interface ListToIntFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param elements the function argument
     *
     * @return the function result
     *
     * @throws NullPointerException if the given argument is {@code null}
     */
    int apply(List<T> elements);
}
