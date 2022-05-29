package h04;

import java.util.Arrays;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class JUnitUtils {

    public static class IntArrayConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            if (source instanceof String s && int[].class.isAssignableFrom(targetType)) {
                return Arrays.stream(s.split(" ")).mapToInt(Integer::valueOf).toArray();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
