package h04.tutor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import h04.collection.ListItem;
import h04.function.ListToIntFunction;
import org.mockito.invocation.InvocationOnMock;

public class MyCollectionsTutor<T> {

    public final ListToIntFunction<T> function;

    public final Comparator<? super T> cmp;

    public MyCollectionsTutor(ListToIntFunction<T> function, Comparator<? super T> cmp) {
        this.function = function;
        this.cmp = cmp;
    }

    public void sort(List<T> list) {
        ListItem<T> unsorted = listToListItem(list);
        ListItem<T> sorted = adaptiveMergeSortInPlace(unsorted, function.apply(list));
        listItemToList(sorted, list);
    }

    public Void sortByInvocation(InvocationOnMock invocation) {
        sort(invocation.getArgument(0));
        return null;
    }

    public ListItem<T> listToListItem(List<T> list) {
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

    public ListItem<T> listToListItemByInvocation(InvocationOnMock invocation) {
        return listToListItem(invocation.getArgument(0));
    }

    public void listItemToList(ListItem<T> head, List<T> list) {
        ListIterator<T> iterator = list.listIterator();
        for (ListItem<T> current = head; current != null; current = current.next) {
            iterator.next();
            iterator.set(current.key);
        }
    }

    public Void listItemToListByInvocation(InvocationOnMock invocation) {
        listItemToList(invocation.getArgument(0), invocation.getArgument(1));
        return null;
    }

    public ListItem<T> adaptiveMergeSortInPlace(ListItem<T> head, int threshold) {
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
        return merge(adaptiveMergeSortInPlace(head, threshold), adaptiveMergeSortInPlace(right, threshold));
    }

    public ListItem<T> adaptiveMergeSortInPlaceByInvocation(InvocationOnMock invocation) {
        return adaptiveMergeSortInPlace(invocation.getArgument(0), invocation.getArgument(1));
    }

    public ListItem<T> split(ListItem<T> head, int optimalSize) {
        // Stores the split position
        ListItem<T> split = null;
        int diff = -1;
        boolean stop = false;

        // Needed for finding runs - compare previous and next element
        ListItem<T> previous = head;
        // Since we start the loop at the second element, the counter starts at 2
        int size = 2;
        for (ListItem<T> current = head.next; current != null; previous = current, current = current.next, size++) {
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

    public ListItem<T> splitByInvocation(InvocationOnMock invocation) {
        return split(invocation.getArgument(0), invocation.getArgument(1));
    }

    public ListItem<T> merge(ListItem<T> left, ListItem<T> right) {
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

    public ListItem<T> mergeByInvocation(InvocationOnMock invocation) {
        return merge(invocation.getArgument(0), invocation.getArgument(1));
    }

    public ListItem<T> selectionSortInPlace(ListItem<T> head) {
        ListItem<T> sorted = null;
        ListItem<T> unsorted = head;

        while (unsorted != null) {
            ListItem<T> maximum = unsorted;
            ListItem<T> current = maximum;
            ListItem<T> maximumPredecessor = null;
            ListItem<T> currentPredecessor = null;

            while (current != null) {
                if (cmp.compare(maximum.key, current.key) < 0) {
                    maximum = current;
                    maximumPredecessor = currentPredecessor;
                }
                currentPredecessor = current;
                current = current.next;
            }

            if (maximumPredecessor == null) {
                unsorted = unsorted.next;
            } else {
                maximumPredecessor.next = maximum.next;
            }

            maximum.next = sorted;
            sorted = maximum;
        }
        return sorted;
    }

    public ListItem<T> selectionSortInPlaceByInvocation(InvocationOnMock invocation) {
        return selectionSortInPlace(invocation.getArgument(0));
    }
}
