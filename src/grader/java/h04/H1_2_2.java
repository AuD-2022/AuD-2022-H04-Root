package h04;

import java.util.List;

import h04.student.FunctionOnRatioOfInversionsStudent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H1_2_2 {

    FunctionOnRatioOfInversionsStudent function = new FunctionOnRatioOfInversionsStudent();

    @ParameterizedTest
    @CsvFileSource(resources = "h1_2/zero_inversions")
    public void t1(@ConvertWith(JUnitUtils.StringListConverter.class) List<String> list) {
        function.assertEqualsApply(0, list);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h1_2/one_inversion")
    public void t2(@ConvertWith(JUnitUtils.StringListConverter.class) List<String> list) {
        function.assertEqualsApply(1, list);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h1_2/multiple_inversions")
    public void t3(@ConvertWith(JUnitUtils.StringListConverter.class) List<String> list, int number) {
        function.assertEqualsApply(number, list);
    }
}
