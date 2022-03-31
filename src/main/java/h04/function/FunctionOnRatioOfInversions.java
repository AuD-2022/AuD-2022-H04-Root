package h04.function;

import h04.util.Permutations;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a function that accepts a double-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated by the ratio of inversions and the maximum number of inversions.
 *
 * @author Nhan Huynh
 */
public class FunctionOnRatioOfInversions<T> extends FunctionOnDegreeOfDisorder<T> {

    /**
     * The function to be applied to the ratio of runs.
     */
    private final DoubleToIntFunction function;

    /**
     * Constructs and initializes a {@code FunctionOnRatioOfInversions}.
     *
     * @param function the function to be applied to the ratio of runs
     * @param cmp      the comparator used to compare the elements of the list
     */
    public FunctionOnRatioOfInversions(DoubleToIntFunction function, Comparator<? super T> cmp) {
        super(cmp);
        this.function = function;
    }

    @Override
    public int apply(List<T> elements) {
        Objects.requireNonNull(elements, "The list of elements must not be null");
        int inversions = Permutations.getNumberOfInversions(elements, cmp);
        int size = elements.size();
        // Max number of inversions
        int combinations = size * (size - 1) / 2;
        double ratio = (double) inversions / combinations;
        return function.apply(ratio);
    }
}
