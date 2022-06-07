package h04.function;

import h04.util.Permutations;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a function that accepts a list-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated by the ratio of runs to the number of elements.
 *
 * @author Nhan Huynh
 */
public class FunctionOnRatioOfRuns<T> extends FunctionOnDegreeOfDisorder<T> {

    /**
     * The function to be applied to the ratio of runs.
     */
    public final DoubleToIntFunction function;

    /**
     * Constructs and initializes a {@code FunctionOnRatioOfRuns}.
     *
     * @param cmp      the comparator used to compare the elements of the list
     * @param function the function to be applied to the ratio of runs
     */
    public FunctionOnRatioOfRuns(Comparator<? super T> cmp, DoubleToIntFunction function) {
        super(cmp);
        this.function = function;
    }

    @Override
    public int apply(List<T> elements) {
        Objects.requireNonNull(elements, "The list of elements must not be null");
        int runs = Permutations.computeNumberOfRuns(elements, cmp);
        double ratio = (double) runs / elements.size();
        return function.apply(ratio);
    }
}
