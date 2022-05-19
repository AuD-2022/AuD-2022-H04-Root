package h04.function;

import java.util.Comparator;

/**
 * Represents a function that accepts a double-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated depending on the degree of disorder.
 *
 * @author Nhan Huynh
 */
public abstract class FunctionOnDegreeOfDisorder<T> implements ListToIntFunction<T> {

    /**
     * The comparator used to compare the elements of the list.
     */
    protected final Comparator<? super T> cmp;

    /**
     * Constructs and initializes a {@code FunctionOnDegreeOfDisorder} with the specified comparator.
     *
     * @param cmp the comparator used to compare the elements of the list
     */
    public FunctionOnDegreeOfDisorder(Comparator<? super T> cmp) {
        this.cmp = cmp;
    }
}
