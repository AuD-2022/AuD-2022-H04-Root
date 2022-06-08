package h04;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static h04.ListUtils.listItemCollector;
import static h04.ListUtils.listToString;
import static h04.ListUtils.stream;
import static h04.ListUtils.streamItems;
import static java.lang.String.format;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

import h04.JUnitUtils.StreamConverter;
import h04.collection.ListItem;
import h04.student.MyCollectionsStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H2_2 {

    public MyCollectionsStudent<String> instance;

    @BeforeEach
    public void beforeEach() {
        instance = MyCollectionsStudent.forString();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/split_two_runs")
    public void t1(@ConvertWith(StreamConverter.class) Stream<String> list, int index) {
        assertCorrectSplit(list, index);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/split_multiple_runs")
    public void t2(@ConvertWith(StreamConverter.class) Stream<String> list, int index) {
        assertCorrectSplit(list, index);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/adaptiveMergeSortInPlace_anchor")
    public void t3(@ConvertWith(StreamConverter.class) Stream<String> list, int threshold) {
        assertAnchorAdaptiveMergeSortInPlace(list, threshold);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/adaptiveMergeSortInPlace_switch")
    public void t4(@ConvertWith(StreamConverter.class) Stream<String> list, int threshold) {
        assertSwitchAdaptiveMergeSortInPlace(list, threshold);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/adaptiveMergeSortInPlace_merge")
    public void t5(@ConvertWith(StreamConverter.class) Stream<String> list, int threshold, int firstIndexR) {
        assertMergeAdaptiveMergeSortInPlace(list, threshold, firstIndexR);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/adaptiveMergeSortInPlace")
    public void t6(@ConvertWith(StreamConverter.class) Stream<String> list, int threshold) {
        assertCorrectResultAdaptiveMergeSortInPlace(list, threshold);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/merge")
    public void t7(
        @ConvertWith(StreamConverter.class) Stream<String> stream1,
        @ConvertWith(StreamConverter.class) Stream<String> stream2
    ) {
        assertCorrectResultMerge(stream1, stream2);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/selectionSortInPlace_sorted")
    public void t8(@ConvertWith(StreamConverter.class) Stream<String> stream) {
        assertCorrectResultSelectionSortInPlace(stream);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/selectionSortInPlace_one_element_unsorted")
    public void t9(@ConvertWith(StreamConverter.class) Stream<String> stream) {
        assertCorrectResultSelectionSortInPlace(stream);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/selectionSortInPlace_multiple_elements_unsorted")
    public void t10(@ConvertWith(StreamConverter.class) Stream<String> stream) {
        assertCorrectResultSelectionSortInPlace(stream);
    }

    public void assertCorrectSplit(Stream<String> stringStream, int firstIndexSecond) {
        var list = stringStream.toList();
        var head = list.stream().collect(listItemCollector());
        var itemList = streamItems(head).toList();
        var n = itemList.size();
        var optimalSize = (n + 1) / 2;
        var headRight = instance.split(head, optimalSize);
        if (n != 0) {
            assertNotNull(headRight, format("split(%s,%s) returned null unexpectedly", list, optimalSize));
        }
        var listString = ListUtils.toString(list);
        var list1StringExpected = range(0, firstIndexSecond).mapToObj(list::get).collect(listToString());
        var list2StringExpected = range(firstIndexSecond, n).mapToObj(list::get).collect(listToString());
        var list1StringActual = stream(head).collect(listToString());
        var list2StringActual = stream(headRight).collect(listToString());
        assertEquals(list1StringExpected, list1StringActual, format("first sequence differ for split(%s)", listString));
        assertEquals(list2StringExpected, list2StringActual, format("second sequence differ for split(%s)", listString));
    }

    public void assertAnchorAdaptiveMergeSortInPlace(Stream<String> stringStream, int threshold) {
        var list = stringStream.collect(listItemCollector());
        var listString = ListUtils.toString(list);
        instance.useReferenceForSelectionSortInPlace();
        instance.useReferenceForSplit();
        instance.useReferenceForMerge();
        var callCount = new AtomicInteger(0);
        doAnswer(invocation -> {
                if (callCount.incrementAndGet() > 1) {
                    var parameterListString = ListUtils.toString((ListItem<?>) invocation.getArgument(0));
                    var parameterThreshold = (int) invocation.getArgument(1);
                    return fail(
                        format(
                            "unexpected recursive call for adaptiveMergeSortInPlace(%s,%s): adaptiveMergeSortInPlace(%s,%s)",
                            listString,
                            threshold,
                            parameterListString,
                            parameterThreshold
                        ));
                }
                return invocation.callRealMethod();
            }
        ).when(instance.student).adaptiveMergeSortInPlace(any(), anyInt());
        instance.adaptiveMergeSortInPlace(list, threshold);
    }

    public void assertSwitchAdaptiveMergeSortInPlace(Stream<String> stringStream, int threshold) {
        var list = stringStream.collect(listItemCollector());
        var listString = ListUtils.toString(list);
        instance.useReferenceForSplit();
        instance.useReferenceForMerge();
        var callCount = new AtomicInteger();
        doAnswer(invocation -> {
            callCount.incrementAndGet();
            var listParameter = invocation.<ListItem<String>>getArgument(0);
            var listParameterString = ListUtils.toString(list);
            assertEquals(
                listString,
                listParameterString,
                format("" +
                        "wrong list for call of selectionSortInPlace for adaptiveMergeSortInPlace(%s,%s):" +
                        "selectionSortInPlace(%s)",
                    listString,
                    threshold,
                    listParameterString
                ));
            assertSame(
                list,
                listParameter,
                format(
                    "different list for call of selectionSortInPlace for adaptiveMergeSortInPlace(%s,%s)",
                    listString,
                    threshold
                ));
            return instance.tutor.selectionSortInPlace(listParameter);
        }).when(instance.student).selectionSortInPlace(any());
        instance.adaptiveMergeSortInPlace(list, threshold);
        if (callCount.get() == 0) {
            fail(format("no call of selectionSortInPlace: adaptiveMergeSortInPlace(%s,%s)", listString, threshold));
        }
    }

    public void assertMergeAdaptiveMergeSortInPlace(Stream<String> stringStream, int threshold, int firstIndexR) {
        var list = stringStream.toList();
        var n = list.size();
        var head = list.stream().collect(listItemCollector());
        var headStr = ListUtils.toString(head);
        instance.useReferenceForSplit();
        instance.useReferenceForSelectionSortInPlace();
        var callCountMerge = new AtomicInteger();
        doAnswer(invocation -> {
            callCountMerge.incrementAndGet();
            var listLActual = invocation.<ListItem<String>>getArgument(0);
            var listRActual = invocation.<ListItem<String>>getArgument(1);
            var listLStringExpected = range(0, firstIndexR).mapToObj(list::get).sorted().collect(listToString());
            var listRStringExpected = range(firstIndexR, n).mapToObj(list::get).sorted().collect(listToString());
            var listLStringActual = ListUtils.toString(listLActual);
            var listRStringActual = ListUtils.toString(listRActual);
            assertEquals(
                listLStringExpected,
                listLStringActual,
                format("left list differs for call of merge: assertMergeAdaptiveMergeSortInPlace(%s,%s)", headStr, threshold));
            assertEquals(
                listRStringExpected,
                listRStringActual,
                format("right list differs for call of merge: assertMergeAdaptiveMergeSortInPlace(%s,%s)", headStr, threshold));
            return instance.tutor.merge(listLActual, listRActual);
        }).when(instance.student).merge(any(), any());
        var callCountAdaptiveMergeSortInPlace = new AtomicInteger();
        doAnswer(invocation -> {
            if (callCountAdaptiveMergeSortInPlace.incrementAndGet() == 1) {
                return invocation.callRealMethod();
            }
            return instance.tutor.adaptiveMergeSortInPlace(invocation.getArgument(0), invocation.getArgument(1));
        }).when(instance.student).adaptiveMergeSortInPlace(any(), anyInt());
        instance.adaptiveMergeSortInPlace(head, threshold);
        if (callCountMerge.get() == 0) {
            fail(format("no call of merge: adaptiveMergeSortInPlace(%s,%s)", headStr, threshold));
        }
    }

    public void assertCorrectResultAdaptiveMergeSortInPlace(Stream<String> stringStream, int threshold) {
        var list = stringStream.toList();
        var n = list.size();
        var head = list.stream().collect(listItemCollector());
        var headString = ListUtils.toString(head);
        instance.useReferenceForSplit();
        instance.useReferenceForMerge();
        instance.useReferenceForSelectionSortInPlace();
        var actualResult = instance.adaptiveMergeSortInPlace(head, threshold);
        var actualResultString = ListUtils.toString(actualResult);
        var expectedResultString = list.stream().sorted().collect(listToString());
        assertEquals(
            expectedResultString,
            actualResultString,
            format("result differs for adaptiveMergeSortInPlace(%s,%s)", headString, threshold));
    }

    public void assertCorrectResultMerge(Stream<String> stream1, Stream<String> stream2) {
        var head1 = stream1.collect(listItemCollector());
        var head2 = stream2.collect(listItemCollector());
        var expectedResultString = Stream.of(head1, head2).flatMap(ListUtils::stream).sorted().collect(listToString());
        var result = instance.merge(head1, head2);
        var actualResultString = stream(result).collect(listToString());
        assertEquals(
            expectedResultString,
            actualResultString,
            format("result differs for merge(%s,%s)", head1, head2));
    }

    public void assertCorrectResultSelectionSortInPlace(Stream<String> stream) {
        var head = stream.collect(listItemCollector());
        var headString = stream(head).collect(listToString());
        var resultListExpected = streamItems(head).sorted(Comparator.comparing(i -> i.key)).toList();
        var resultStreamExpected = resultListExpected.stream();
        var resultStreamActual = streamItems(instance.selectionSortInPlace(head));
        ListUtils.assertSameItems(
            resultStreamExpected,
            resultStreamActual,
            format("result differs for selectionSortInPlace(%s)", headString));
    }

}
