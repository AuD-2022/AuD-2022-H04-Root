package h04;

import java.util.List;

import h04.student.FunctionOnRatioOfRunsStudent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H1_2_1 {

    FunctionOnRatioOfRunsStudent function = new FunctionOnRatioOfRunsStudent();

    @ParameterizedTest
    @CsvFileSource(resources = "h1_2/one_run")
    public void t1(@ConvertWith(JUnitUtils.StringListConverter.class) List<String> list) {
        function.assertEqualsApply(1, list);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h1_2/multiple_runs")
    public void t2(@ConvertWith(JUnitUtils.StringListConverter.class) List<String> list, int number) {
        function.assertEqualsApply(number, list);
    }
}
