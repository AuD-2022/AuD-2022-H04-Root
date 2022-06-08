package h04;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static h04.ListUtils.listItemCollector;
import static h04.ListUtils.listToString;
import static h04.ListUtils.stream;
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

    // TODO generell sortiert

    public void assertCorrectSplit(Stream<String> stringStream, int firstIndexSecond) {
        var list = stringStream.toList();
        var head = list.stream().collect(listItemCollector());
        var itemList = ListUtils.streamItems(head).toList();
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
}
