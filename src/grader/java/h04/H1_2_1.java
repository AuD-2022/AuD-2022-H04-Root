package h04;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import h04.student.FunctionOnRatioOfRunsStudent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H1_2_1 {

    FunctionOnRatioOfRunsStudent function = new FunctionOnRatioOfRunsStudent();

    @ParameterizedTest
    @CsvFileSource(resources = "h1_2/one_run")
    public void t1(@ConvertWith(JUnitUtils.StreamConverter.class) Stream<String> stream) {
        function.assertEqualsApply(1, stream.collect(toList()));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h1_2/multiple_runs")
    public void t2(@ConvertWith(JUnitUtils.StreamConverter.class) Stream<String> list, int number) {
        function.assertEqualsApply(number, list.collect(toList()));
    }
}
