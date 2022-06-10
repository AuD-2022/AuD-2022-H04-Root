package h04;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static h04.ListUtils.listItemCollector;
import static h04.ListUtils.streamItems;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

import h04.JUnitUtils.StreamConverter;
import h04.student.MyCollectionsStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("h04")
public class H2_1 {

    public MyCollectionsStudent<String> instance;

    @BeforeEach
    public void beforeEach() {
        instance = MyCollectionsStudent.forString();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_1/alphabet")
    public void t1(@ConvertWith(StreamConverter.class) Stream<String> stream) {
        var list = stream.collect(toList());
        var n = list.size();
        var expected = ListUtils.toString(list);
        var listItem = instance.listToListItem(list);
        var actual = ListUtils.toString(listItem, n);
        assertEquals(expected, actual, format("result of listToListItem(%s) differs from expected result", expected));
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
            assertEquals(expected, actual,
                format("result of listItemToList(%s,%s) differs from expected result", headString, listString));
        }
    }

}
