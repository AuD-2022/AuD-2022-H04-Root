package h04;

import java.util.stream.Stream;

import static h04.ListUtils.listItemCollector;
import static h04.ListUtils.listToString;
import static h04.ListUtils.stream;
import static java.lang.String.format;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.*;

import h04.JUnitUtils.StreamConverter;
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
    @CsvFileSource(resources = "h2_2/two_runs")
    public void t1(@ConvertWith(StreamConverter.class) Stream<String> list, int index) {
        assertCorrectSplit(list, index);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_2/multiple_runs")
    public void t2(@ConvertWith(StreamConverter.class) Stream<String> list, int index) {
        assertCorrectSplit(list, index);
    }

    public void assertCorrectSplit(Stream<String> stream, int firstIndexSecond) {
        var list = stream.toList();
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
}
