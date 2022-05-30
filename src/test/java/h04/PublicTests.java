package h04;

import h04.collection.ListItem;
import h04.collection.MyCollections;
import h04.function.ArrayDoubleToIntFunction;
import h04.function.DoubleToIntFunction;
import h04.function.DoubleToIntFunctionFitter;
import h04.function.FunctionOnDegreeOfDisorder;
import h04.function.FunctionOnRatioOfInversions;
import h04.function.FunctionOnRatioOfRuns;
import h04.function.LinearInterpolation;
import h04.function.LinearRegression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PublicTests {

    private static final Integer[] EXAMPLE_SEQUENCE = {1, 2, 3, 4, 5, 3, 4, 5, 5, 4, 3, 2, 2, 2, 5, 6, 6, 7, 6, 8, 9, 5, 1, 2, 3,
        4, 5};

    /**
     * Tests the method whether two lists are equal.
     *
     * @param expected the expected list
     * @param actual   the actual list
     * @param <T>      the type of the elements
     */
    private static <T> void assertListItems(ListItem<T> expected, ListItem<T> actual) {
        while (expected != null && actual != null) {
            Assertions.assertEquals(expected.key, actual.key);
            expected = expected.next;
            actual = actual.next;
        }
        Assertions.assertNull(expected);
        Assertions.assertNull(actual);
    }

    @ParameterizedTest(name = "f({0}) = {1}")
    @CsvSource({"0,10", "0.25,4", "0.5,5", "0.75,1", "1,8"})
    void testArrayDoubleToIntFunction(String xValue, String expectedValue) {
        double x = Double.parseDouble(xValue);
        int expected = Integer.parseInt(expectedValue);

        int[] a = {10, 4, 5, 1, 8};
        DoubleToIntFunction f = new ArrayDoubleToIntFunction(a);
        int actual = f.apply(x);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFunctionOnRatioRuns() {
        List<Integer> sequence = List.of(EXAMPLE_SEQUENCE);
        DoubleToIntFunction f1 = x -> (int) Math.round(x * 100);
        FunctionOnDegreeOfDisorder<Integer> f2 = new FunctionOnRatioOfRuns<>(Comparator.naturalOrder(), f1);
        int actual = f2.apply(sequence);
        Assertions.assertEquals(30, actual);
    }

    @Test
    void testFunctionOnRatioInversions() {
        List<Integer> sequence = List.of(EXAMPLE_SEQUENCE);
        DoubleToIntFunction f1 = x -> (int) Math.round(x * 100);
        FunctionOnDegreeOfDisorder<Integer> f2 = new FunctionOnRatioOfInversions<>(Comparator.naturalOrder(), f1);
        int actual = f2.apply(sequence);
        Assertions.assertEquals(33, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testListToListItem() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ListItem<Integer> expected = new ListItem<>();
        expected.key = 3;
        expected.next = new ListItem<>();
        expected.next.key = 2;
        expected.next.next = new ListItem<>();
        expected.next.next.key = 1;
        expected.next.next.next = new ListItem<>();
        expected.next.next.next.key = 6;

        MyCollections<Integer> collections = new MyCollections<>(list -> 10, Comparator.naturalOrder());
        Method method = collections.getClass().getDeclaredMethod("listToListItem", List.class);
        method.setAccessible(true);
        List<Integer> sequence = List.of(3, 2, 1, 6);
        Object result = method.invoke(collections, sequence);

        ListItem<Integer> actual = (ListItem<Integer>) result;
        assertListItems(expected, actual);
    }

    @Test
    void testListItemToList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ListItem<Integer> sequence = new ListItem<>();
        sequence.key = 3;
        sequence.next = new ListItem<>();
        sequence.next.key = 2;
        sequence.next.next = new ListItem<>();
        sequence.next.next.key = 1;
        sequence.next.next.next = new ListItem<>();
        sequence.next.next.next.key = 6;

        MyCollections<Integer> collections = new MyCollections<>(list -> 10, Comparator.naturalOrder());
        Method method = collections.getClass().getDeclaredMethod("listItemToList", ListItem.class, List.class);
        method.setAccessible(true);
        List<Integer> actual = new ArrayList<>(List.of(1, 2, 3, 6));
        method.invoke(collections, sequence, actual);

        List<Integer> expected = List.of(3, 2, 1, 6);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testSort() {
        Comparator<Integer> cmp = Comparator.naturalOrder();
        List<Integer> unsorted = new ArrayList<>(List.of(10, 2, 5, 8, 3, 5, 12));
        List<Integer> sorted = unsorted.stream().sorted(cmp).toList();
        MyCollections<Integer> collections = new MyCollections<>(list -> 10, cmp);
        collections.sort(unsorted);
        Assertions.assertEquals(sorted, unsorted);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAdaptiveMergeSortInPlace() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ListItem<Integer> unsorted = new ListItem<>();
        unsorted.key = 10;
        unsorted.next = new ListItem<>();
        unsorted.next.key = 2;
        unsorted.next.next = new ListItem<>();
        unsorted.next.next.key = 5;
        unsorted.next.next.next = new ListItem<>();
        unsorted.next.next.next.key = 8;

        ListItem<Integer> sorted = new ListItem<>();
        sorted.key = 2;
        sorted.next = new ListItem<>();
        sorted.next.key = 5;
        sorted.next.next = new ListItem<>();
        sorted.next.next.key = 8;
        sorted.next.next.next = new ListItem<>();
        sorted.next.next.next.key = 10;

        MyCollections<Integer> collections = new MyCollections<>(list -> 10, Comparator.naturalOrder());
        Method method = collections.getClass().getDeclaredMethod("adaptiveMergeSortInPlace", ListItem.class, int.class);
        method.setAccessible(true);
        Object result = method.invoke(collections, unsorted, 10);

        ListItem<Integer> actual = (ListItem<Integer>) result;
        assertListItems(sorted, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testSplit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Only works if listToListItem is correctly implemented
        MyCollections<Integer> collections = new MyCollections<>(list -> 10, Comparator.naturalOrder());
        Method converter = collections.getClass().getDeclaredMethod("listToListItem", List.class);
        converter.setAccessible(true);

        List<Integer> sequence = List.of(EXAMPLE_SEQUENCE);
        List<Integer> left = sequence.subList(0, 11);
        List<Integer> right = sequence.subList(11, sequence.size());

        ListItem<Integer> actualLeft = (ListItem<Integer>) converter.invoke(collections, sequence);
        ListItem<Integer> expectedLeft = (ListItem<Integer>) converter.invoke(collections, left);
        ListItem<Integer> expectedRight = (ListItem<Integer>) converter.invoke(collections, right);

        Method method = collections.getClass().getDeclaredMethod("split", ListItem.class, int.class);
        method.setAccessible(true);

        ListItem<Integer> actualRight = (ListItem<Integer>) method.invoke(collections, actualLeft, 14);

        assertListItems(expectedLeft, actualLeft);
        assertListItems(expectedRight, actualRight);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testMerge() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ListItem<Integer> expected = new ListItem<>();
        expected.key = 2;
        expected.next = new ListItem<>();
        expected.next.key = 5;
        expected.next.next = new ListItem<>();
        expected.next.next.key = 8;
        expected.next.next.next = new ListItem<>();
        expected.next.next.next.key = 10;
        ListItem<Integer> left = new ListItem<>();
        left.key = 2;
        left.next = new ListItem<>();
        left.next.key = 8;
        ListItem<Integer> right = new ListItem<>();
        right.key = 5;
        right.next = new ListItem<>();
        right.next.key = 10;

        MyCollections<Integer> collections = new MyCollections<>(list -> 10, Comparator.naturalOrder());
        Method method = collections.getClass().getDeclaredMethod("merge", ListItem.class, ListItem.class);
        method.setAccessible(true);
        Object result = method.invoke(collections, left, right);

        ListItem<Integer> actual = (ListItem<Integer>) result;
        assertListItems(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testSelectionSortInPlace() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ListItem<Integer> unsorted = new ListItem<>();
        unsorted.key = 10;
        unsorted.next = new ListItem<>();
        unsorted.next.key = 2;
        unsorted.next.next = new ListItem<>();
        unsorted.next.next.key = 5;
        unsorted.next.next.next = new ListItem<>();
        unsorted.next.next.next.key = 8;


        ListItem<Integer> sorted = new ListItem<>();
        sorted.key = 2;
        sorted.next = new ListItem<>();
        sorted.next.key = 5;
        sorted.next.next = new ListItem<>();
        sorted.next.next.key = 8;
        sorted.next.next.next = new ListItem<>();
        sorted.next.next.next.key = 10;

        MyCollections<Integer> collections = new MyCollections<>(list -> 10, Comparator.naturalOrder());
        Method method = collections.getClass().getDeclaredMethod("selectionSortInPlace", ListItem.class);
        method.setAccessible(true);
        Object result = method.invoke(collections, unsorted);

        ListItem<Integer> actual = (ListItem<Integer>) result;
        assertListItems(sorted, actual);
    }

    @ParameterizedTest(name = "f({0}) = {1}")
    @CsvSource({"0,10", "0.25,4", "0.5,5", "0.75,3", "1,8"})
    void testLinearInterpolation(String xValue, String expectedValue) {
        double x = Double.parseDouble(xValue);
        int expected = Integer.parseInt(expectedValue);

        Integer[] y = {10, null, 4, null, 5, 1, 8};
        DoubleToIntFunctionFitter fitter = new LinearInterpolation();
        DoubleToIntFunction function = fitter.fitFunction(y);
        Assertions.assertEquals(expected, function.apply(x));
    }

    @ParameterizedTest(name = "f({0}) = {1}")
    @CsvSource({"0,8", "0.25,4", "0.5,6", "0.75,5", "1,4"})
    void testLinearRegressionString(String xValue, String expectedValue) {
        double x = Double.parseDouble(xValue);
        int expected = Integer.parseInt(expectedValue);

        Integer[] y = {10, null, 4, null, 5, 1, 8};
        DoubleToIntFunctionFitter fitter = new LinearRegression();
        DoubleToIntFunction function = fitter.fitFunction(y);
        Assertions.assertEquals(expected, function.apply(x));
    }
}
