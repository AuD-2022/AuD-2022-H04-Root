package h04.collection;

import h04.function.ListToIntFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A collection that allows to order (sort) the unordered sequence. The sorting algorithm is based on merge-sort, switching to
 * selection-sort when the sequence is small to increase performance.
 *
 * @param <T> the type of the elements in the list that can be sorted
 *
 * @author Nhan Huynh
 */
public class MyCollections<T> {

    /**
     * Determines the toggle length when the sorting algorithm should be toggled (usage of another sorting algorithm).
     */
    private final ListToIntFunction<T> function;

    /**
     * The comparator used to compare the elements of the list.
     */
    private final Comparator<? super T> cmp;

    /**
     * Constructs and initializes a {@code MyCollections}.
     *
     * @param function the function determining the toggle length
     * @param cmp      the comparator used to compare the elements of the list
     */
    public MyCollections(ListToIntFunction<T> function, Comparator<? super T> cmp) {
        this.function = function;
        this.cmp = cmp;
    }

    /**
     * Sorts the list in place.
     *
     * @param list the list to sort
     */
    public void sort(List<T> list) {
        ListItem<T> unsorted = listToListItem(list);
        ListItem<T> sorted = adaptiveMergeSortInPlace(unsorted, function.apply(list));
        listItemToList(sorted, list);
    }

    /**
     * Transfers all elements from a list to a list item sequence.
     *
     * @param list the list to transfer from
     *
     * @return the list item sequence containing the element of the list
     */
    private ListItem<T> listToListItem(List<T> list) {
        Iterator<T> iterator = list.iterator();

        if (!iterator.hasNext()) {
            return null;
        }

        ListItem<T> head = new ListItem<>();
        head.key = iterator.next();
        ListItem<T> tail = head;

        // Insert to the last
        while (iterator.hasNext()) {
            tail.next = new ListItem<>();
            tail = tail.next;
            tail.key = iterator.next();
        }
        return head;
    }

    /**
     * Transfers all elements from a ListItem sequence to a list.
     *
     * @param head the list item sequence
     * @param list the list to transfer to
     */
    private void listItemToList(ListItem<T> head, List<T> list) {
        ListIterator<T> iterator = list.listIterator();
        for (ListItem<T> current = head; current != null; current = current.next) {
            iterator.next();
            iterator.set(current.key);
        }
    }

    /**
     * Sorts the list in place using the merge sort algorithm. If the (sub-)sequence is smaller than the specified threshold, the
     * selection sort algorithm  (in place) is used.
     *
     * @param head      the list to sort
     * @param threshold the threshold determining the toggle length
     *
     * @return the sorted list
     */
    private ListItem<T> adaptiveMergeSortInPlace(ListItem<T> head, int threshold) {
        boolean sorted = true;
        int size = 0;

        // Since we have to compute the size of the sequence, we can also check if the sequence is sorted and break the
        // recursion if it is sorted
        for (ListItem<T> current = head; current != null; current = current.next) {
            // Compute is sorted
            if (current.next != null) {
                sorted &= cmp.compare(current.key, current.next.key) <= 0;
            }
            size++;
        }

        // Case (b): the sequence is sorted
        if (sorted || size == 1) {
            return head;
        }

        // Case (a): Swap to selection sort if the sequence is smaller or equal to the threshold
        if (size <= threshold) {
            return selectionSortInPlace(head);
        }

        // left = head
        ListItem<T> right = split(head, (size + 1) / 2);
        // ListItem<T> right = split(head, (size + 1) / 2, (runs + 1) / 2, runs % 2 == 1);

        // Otherwise if case (a) or (b) does not apply, recursively sort the sub-sequences using merge sort
        return merge(
            adaptiveMergeSortInPlace(head, threshold),
            adaptiveMergeSortInPlace(right, threshold)
        );
    }

    /**
     * Splits the list into two subsequences.
     *
     * <p>The decomposition of the list into two subsequences is related to the searched optimal size and the number of
     * elements of runs, which is close to the optimal size.
     *
     * @param head        the list to split
     * @param optimalSize the optimal size after the split
     *
     * @return the second part of the list
     */
    private ListItem<T> split(ListItem<T> head, int optimalSize) {
        // Stores the split position
        ListItem<T> split = null;
        int diff = -1;
        boolean stop = false;

        // Needed for finding runs - compare previous and next element
        ListItem<T> previous = head;
        // Since we start the loop at the second element, the counter starts at 2
        int size = 2;
        for (ListItem<T> current = head.next; current != null; previous = current,
            current = current.next, size++) {
            if (cmp.compare(previous.key, current.key) <= 0) {
                continue;
            }

            // -1 since we split before the current element
            // Try to get an even distribution of elements by computing the minimum difference of a run depending on the
            // optimal size
            int newDiff = Math.abs(size - optimalSize - 1);
            if (diff == -1 || newDiff < diff) {
                split = previous;
                diff = newDiff;
            }

            // Stop the loop if the optimal size is reached
            if (stop) {
                break;
            }
            stop = size >= optimalSize;
        }

        // Split into two parts
        assert split != null;
        ListItem<T> right = split.next;
        split.next = null;
        return right;
    }

    /**
     * Merges the two given sub-sequences into one sorted sequence.
     *
     * @param left  the left sub-sequence
     * @param right the right sub-sequence
     *
     * @return the merged sorted sequence
     */
    private ListItem<T> merge(ListItem<T> left, ListItem<T> right) {
        ListItem<T> leftCurrent = left;
        ListItem<T> rightCurrent = right;

        ListItem<T> head = null;
        ListItem<T> tail = null;

        // Merge left and right to one list
        while (leftCurrent != null && rightCurrent != null) {
            // Find minimum element to sort
            ListItem<T> minimum;
            if (cmp.compare(leftCurrent.key, rightCurrent.key) <= 0) {
                minimum = leftCurrent;
                leftCurrent = leftCurrent.next;
            } else {
                minimum = rightCurrent;
                rightCurrent = rightCurrent.next;
            }

            // Special case: Initialize head and tail
            if (head == null) {
                head = tail = minimum;
            } else {
                // Add element to the last of the list
                tail = tail.next = minimum;
            }

            // Decapsulate
            minimum.next = null;
        }

        if (head == null) {
            return null;
        }

        // Possible remaining elements
        tail.next = leftCurrent == null ? rightCurrent : leftCurrent;
        return head;
    }

    public static void main(String[] args) {
        var c = new MyCollections<Integer>(elements -> 100000, Comparator.naturalOrder());

        var list = new ArrayList<>(List.of(8, 2, 3, 7, 1));
        var item = c.listToListItem(list);
        item = c.selectionSortInPlace(item);
        c.listItemToList(item, list);
        System.out.println(list);

    }

    /**
     * Sorts the list in place using the selection sort algorithm.
     *
     * @param head the list to sort
     *
     * @return the sorted list
     */
    private ListItem<T> selectionSortInPlace(ListItem<T> head) {
        ListItem<T> sorted = null;
        ListItem<T> tail = null;
        ListItem<T> unsorted = head;
        // Decouple elements from the unsorted sequence to the sorted sequence until the unsorted sequence = sorted
        while (unsorted != null) {
            // Find the maximum element
            // Since we need to decouple it from the sequence, we need to change the pointer of the previous node
            ListItem<T> prevMax = null;
            T max = unsorted.key;
            for (ListItem<T> other = unsorted; other.next != null; other = other.next) {
                // Found new maximum
                if (cmp.compare(other.next.key, max) < 0) {
                    prevMax = other;
                    max = prevMax.next.key;
                }
            }

            ListItem<T> toSort;
            if (prevMax == null) {
                // If the head element is the maximum element, the new head is its successor
                toSort = head;
                head = head.next;
                toSort.next = null;
            } else {
                // Else we decouple the previous node successor pointer to the maximum element
                // and the successor pointer of the maximum node
                toSort = prevMax.next;
                prevMax.next = prevMax.next.next;
                toSort.next = null;
            }
            // Insert to sorted sequence
            if (tail == null) {
                sorted = tail = toSort;
            } else {
                tail = tail.next = toSort;
            }
            // Each iteration reduce the size of the unsorted sequence by one
            unsorted = head;
        }
        return sorted;
    }
//    private ListItem<T> selectionSortInPlace(ListItem<T> head) {
//        ListItem<T> sorted = head;
//        int size = 0;
//        for (ListItem<T> current = head; current != null; current = current.next) {
//            size++;
//        }
//        for (int i = size - 1; i > 0; i--) {
//            // Get maximum element index that should be sorted
//            int index = getMaximumIndex(sorted, 0, i);
//
//            // Sort element and fix violation of the sorted property (in place)
//            if (index != i) {
//                sorted = swap(sorted, index, i);
//            }
//        }
//        return sorted;
//    }
//
//    /**
//     * Swaps two elements in the list.
//     *
//     * @param head the list to swap
//     * @param i    the index of the first element
//     * @param j    the index of the second element
//     *
//     * @return the list with the swapped elements
//     */
//    private ListItem<T> swap(ListItem<T> head, int i, int j) {
//        // Swapping same elements is a no-op
//        if (i == j) {
//            return head;
//        }
//
//        // First index is smaller than second index
//        int min = Math.min(i, j);
//        int max = Math.max(i, j);
//
//        ListItem<T> low = null;
//        ListItem<T> high = null;
//
//        // Retrieve the elements before the elements indices to swap
//        // Index 0 does not have a previous index (special case
//        if (i == 0) {
//            low = head;
//        }
//        int index = 0;
//        for (ListItem<T> current = head; current != null && index < max; current = current.next) {
//            // Always get the previous element in order to adjust references
//            index++;
//            if (index == min) {
//                low = current;
//            }
//            if (index == max) {
//                high = current;
//            }
//        }
//        return swap(head, low, high, min == 0);
//    }
//
//    /**
//     * Swaps two elements in the list.
//     *
//     * @param head   the list to swap
//     * @param first  the element before the first element if possible
//     * @param second the element before the second element if possible
//     * @param isHead true if one of the elements is the head of the list
//     *
//     * @return the list with the swapped elements
//     */
//    private ListItem<T> swap(ListItem<T> head, ListItem<T> first, ListItem<T> second, boolean isHead) {
//        if (head == first && head == second) {
//            /*
//             * Case 1: Swap first with second element
//             * Elements to swap: e1 and e2
//             * f = first element to be swapped (points to head - since there is no previous element)
//             * s = second element to be swapped (points to head - since previous is head)
//             * -> Note that first and second always point to the previous element.
//             *
//             * Sequence: f/s -> e2 -> e3 -> ... -> en -> null
//             *
//             * s.next is the new head since the second element should be swapped with the first element (e2)
//             * f.next should point to the old s.next.next (f -> e3)
//             * the new head next element should point to f since f should be the next element after s  (new head - e2 -> f)
//             */
//            assert second.next != null;
//            ListItem<T> newHead = second.next;
//            first.next = second.next.next;
//            newHead.next = first;
//            return newHead;
//        }
//        if (head == first && isHead) {
//            /*
//             * Case 2: Swap first with any element
//             * Elements to swap: e2 and ek
//             * f = first element to be swapped (points to head - since there is no previous element)
//             * s = second element to be swapped (points to the previous element of the second element)
//             * -> Note that first and second always point to the previous element.
//             *
//             * Sequence: f -> e2 -> ... -> s -> ek -> em -> ... -> en -> null
//             *
//             * s.next is the new head since the second element should be swapped with the first element (ek)
//             * the next element of the new head should be first.next (ek -> e2)
//             * s.next should point to the first element (swap ek with f)
//             * f.next should point to the old s.next.next (s -> f -> em)
//             *
//             * s is the new head
//             */
//            assert second.next != null;
//            ListItem<T> newHead = second.next;
//            ListItem<T> temp = second.next.next;
//            newHead.next = first.next;
//            second.next = first;
//            first.next = temp;
//            return newHead;
//        }
//        /*
//         * Case 3: Swap any element with any element
//         * Elements to swap: ek and em
//         * f = first element to be swapped (points to head - since there is no previous element)
//         * s = second element to be swapped (points to the previous element of the second element)
//         * -> Note that first and second always point to the previous element.
//         *
//         * Sequence: e1 -> ... -> f -> ej -> ek -> el-> s -> el -> em -> ... -> en -> null
//         * f.next should point to s.next (f -> em)
//         * s.next should point to the old f.next (s -> ej)
//         * f.next.next should point to  s.next.next (ek -> em)
//         * s.next.next should point to the old f.next.next.next (em -> ek)
//         */
//        assert second.next != null;
//        assert first.next != null;
//        ListItem<T> temp = first.next;
//        first.next = second.next;
//        second.next = temp;
//        temp = second.next.next;
//        second.next.next = first.next.next;
//        first.next.next = temp;
//        return head;
//    }
//
//    /**
//     * Returns the index of the last maximum element in the list.
//     *
//     * @param head the list to search
//     * @param low  the lower bound of the sublist (inclusive)
//     * @param high the upper bound of the sublist (inclusive)
//     *
//     * @return the index of the last maximum element in the list
//     */
//    private int getMaximumIndex(ListItem<T> head, int low, int high) {
//        int maxIndex = low;
//        ListItem<T> current = head;
//        // Skip elements before the lower bound
//        for (int i = 0; i < low; i++) {
//            assert current != null;
//            current = current.next;
//        }
//        // Find the maximum element in the sublist
//        assert current != null;
//        T max = current.key;
//        current = current.next;
//        for (int i = low + 1; i <= high; i++) {
//            // Update the maximum element (only get the latest maximum)
//            assert current != null;
//            if (cmp.compare(max, current.key) <= 0) {
//                maxIndex = i;
//                max = current.key;
//            }
//            current = current.next;
//        }
//        return maxIndex;
//    }
}
