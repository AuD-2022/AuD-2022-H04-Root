package h04.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * The class {@code Permutations} contains methods for performing operations on permutations.
 *
 * @author Kim Berninger, Nhan Huynh
 */
public final class Permutations {

    /**
     * Don't let anyone instantiate this class.
     */
    private Permutations() {
    }

    /**
     * Returns the number of runs in the specified list.
     *
     * <p>A run is a sorted (descending) sequence of elements.
     *
     * <p>Example:
     * <ul>
     *   <li>Sequence: [1,2,3,4,5|3,4,5,5|4|3|2,2,2,5,6,6,7|6,8,9|5|1,2,3,4,5]</li>
     *   <li>Runs: 8</li>
     * </ul>
     *
     * @param elements   the list of elements
     * @param comparator the comparator used to compare the elements of the list
     * @param <T>        the type of the elements
     *
     * @return the number of runs in the specified list
     */
    public static <T> int computeNumberOfRuns(List<T> elements, Comparator<? super T> comparator) {
        int runs = 1;
        Iterator<T> iterator = elements.iterator();
        if (iterator.hasNext()) {
            T previous = iterator.next();
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (comparator.compare(previous, next) > 0) {
                    runs++;
                }
                previous = next;
            }
        }
        return runs;
    }

    /**
     * Returns the number of inversions in the specified list.
     *
     * <p>An inversion in a sequence (a_1, ...,a_n) is a pair (i, j) such that 1 {@literal <=} i
     * {@literal <} j {@literal <=}  n and a_i  {@literal >} a_j.
     *
     * <p>Example:
     * <ul>
     *   <li>Sequence: [1, 5, 3, 4, 5, 5, 4]</li>
     *   <li>Inversions: 5</li>
     * </ul>
     *
     * @param elements   the list of elements
     * @param comparator the comparator used to compare the elements of the list
     * @param <T>        the type of the elements
     *
     * @return the number of inversions in the specified list
     */
    public static <T> int computeNumberOfInversions(List<T> elements, Comparator<? super T> comparator) {
        int inversions = 0;
        ListIterator<T> iterator = elements.listIterator();
        while (iterator.hasNext()) {
            T left = iterator.next();
            ListIterator<T> subsequence = elements.listIterator(iterator.nextIndex());
            while (subsequence.hasNext()) {
                T right = subsequence.next();
                if (comparator.compare(left, right) > 0) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    /**
     * Swaps random elements in the specified list.
     *
     * @param elements      the list of elements
     * @param numberOfSwaps the number of swaps to perform
     */
    public static void randomSwaps(List<?> elements, int numberOfSwaps) {
        Random random = new Random();
        int n = elements.size();
        int swaps = 0;
        while (swaps < numberOfSwaps) {
            int i = random.nextInt(n);
            int j = random.nextInt(n);
            if (i != j) {
                Collections.swap(elements, i, j);
                swaps++;
            }
        }
    }
}
