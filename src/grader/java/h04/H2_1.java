package h04;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static h04.ListUtils.listItemCollector;
import static h04.ListUtils.streamItems;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

import h04.JUnitUtils.StreamConverter;
import h04.collection.ListItem;
import h04.student.MyCollectionsStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H2_1 {

    @BeforeEach
    public void beforeEach() {
        instance = new MyCollectionsStudent();
    }

    public MyCollectionsStudent instance;

    @ParameterizedTest
    @CsvFileSource(resources = "h2_1/alphabet")
    public void t1(@ConvertWith(StreamConverter.class) Stream<String> stream) {
        var list = stream.collect(toList());
        var n = list.size();
        var expected = ListUtils.toString(list);
        var listItem = instance.listToListItem(list);
        var actual = ListUtils.toString(listItem, n);
        assertEquals(expected, actual, format("apply(%s)", expected));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_1/alphabet")
    public void t2(@ConvertWith(StreamConverter.class) Stream<String> stream) {
        var headReference = stream.toList();
        for (int over : new int[] {0, 1, 2, 10}) {
            var head = headReference.stream().collect(listItemCollector());
            var n = streamItems(head).count();
            var list = Stream.generate(() -> "X").limit(n + over).collect(toList());
            var headString = ListUtils.toString(head);
            var listString = ListUtils.toString(list);
            var expected = Stream.concat(streamItems(head).map(i -> i.key), Stream.generate(() -> "X").limit(over))
                .collect(Collectors.joining("|", "[", "]"));
            instance.listItemToList(head, list);
            var actual = ListUtils.toString(list);
            assertEquals(expected, actual, format("listItemToList(%s,%s)", headString, listString));
        }
    }

    @Test
    public void t3() {
        List<String> param = Stream.of("G", "F", "E", "D", "C", "B", "A").collect(toList());
        var paramString = ListUtils.toString(param);
        AtomicReference<ListItem<String>> unsorted = new AtomicReference<>();
        doAnswer(i -> {
            assertSame(param, i.getArgument(0), "parameter for listToListItem(ListItem<T>)");
            unsorted.set(instance.tutor.listToListItem(param));
            return unsorted.get();
        }).when(instance.student);
        instance.listToListItem(any());
        AtomicReference<ListItem<String>> sorted = new AtomicReference<>();
        doAnswer(i -> {
            assertSame(unsorted.get(), i.getArgument(0), "1st parameter for adaptiveMergeSortInPlace(ListItem<T>,int)");
            assertEquals(1, (int) i.getArgument(1), "2nd parameter for adaptiveMergeSortInPlace(ListItem<T>,int)");
            sorted.set(instance.tutor.adaptiveMergeSortInPlace(unsorted.get(), 1));
            return sorted.get();
        }).when(instance.student);
        instance.adaptiveMergeSortInPlace(any(ListItem.class), anyInt());
        doAnswer(i -> {
            assertSame(sorted.get(), i.getArgument(0), "1st parameter for listItemToList(ListItem<T>,List<T>)");
            assertSame(param, i.getArgument(1), "2nd parameter for listItemToList(ListItem<T>,List<T>)");
            instance.tutor.listItemToList(sorted.get(), param);
            return null;
        }).when(instance.student);
        instance.listItemToList(any(), any());
        var expectedString = ListUtils.toString(List.of("A", "B", "C", "D", "E", "F", "G"));
        instance.sort(param);
        var actualString = ListUtils.toString(param);
        assertEquals(expectedString, actualString, format("sort(%s)", paramString));
    }
}
