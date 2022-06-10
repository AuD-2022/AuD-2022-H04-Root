package h04;

import java.util.Comparator;
import java.util.stream.Stream;

import static h04.ListUtils.listItemCollector;
import static h04.ListUtils.listToString;
import static h04.ListUtils.stream;
import static h04.ListUtils.streamItems;
import static java.lang.String.format;

import h04.student.MyCollectionsStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("h04")
public class H2_3 {

    public MyCollectionsStudent<String> instance;

    @BeforeEach
    public void beforeEach() {
        instance = MyCollectionsStudent.forString();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_3/selectionSortInPlace_sorted")
    public void t1(@ConvertWith(JUnitUtils.StreamConverter.class) Stream<String> stream) {
        assertCorrectResultSelectionSortInPlace(stream);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_3/selectionSortInPlace_one_element_unsorted")
    public void t2(@ConvertWith(JUnitUtils.StreamConverter.class) Stream<String> stream) {
        assertCorrectResultSelectionSortInPlace(stream);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h2_3/selectionSortInPlace_multiple_elements_unsorted")
    public void t3(@ConvertWith(JUnitUtils.StreamConverter.class) Stream<String> stream) {
        assertCorrectResultSelectionSortInPlace(stream);
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
