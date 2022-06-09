package h04;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

import h04.JUnitUtils.StreamConverter.IntStream;
import h04.function.LinearDoubleToIntFunction;
import h04.student.LinearRegressionStudent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H3_1_2 {

    LinearRegressionStudent instance = new LinearRegressionStudent();

    @ParameterizedTest
    @CsvFileSource(resources = "h3_1_2/two_values")
    public void t1(
        @ConvertWith(IntStream.class) Stream<Integer> streamForArray, double expectedA, double expectedB) {
        assertCorrectFitFunction(streamForArray, expectedA, expectedB);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h3_1_2/multiple_values")
    public void t2(
        @ConvertWith(IntStream.class) Stream<Integer> streamForArray, double expectedA, double expectedB) {
        assertCorrectFitFunction(streamForArray, expectedA, expectedB);
    }

    public void assertCorrectFitFunction(Stream<Integer> streamForArray, double expectedA, double expectedB) {
        var array = streamForArray.toArray(Integer[]::new);
        var arrayString = Arrays.toString(array);
        var function = instance.fitFunction(array);
        assertNotNull(function, format("fitFunction(%s)", Arrays.toString(array)));
        assertEquals(
            LinearDoubleToIntFunction.class,
            function.getClass(), format("unexpected class of object returned by fitFunction(%s)", arrayString));
        var actualA = ((LinearDoubleToIntFunction) function).a;
        var actualB = ((LinearDoubleToIntFunction) function).b;
        String expectedPair = format("(a=%.1f,b=%.1f)", expectedA, expectedB);
        String actualPair = format("(a=%.1f,b=%.1f)", actualA, actualB);
        System.out.println(expectedPair);
        assertEquals(
            expectedPair,
            actualPair,
            format("constants in LinearDoubleToIntFunction returned by fitFunction(%s) differ from expected ones", arrayString));
    }
}
