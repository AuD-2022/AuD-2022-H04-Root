package h04.student;

import h04.TUtils;
import h04.function.ArrayDoubleToIntFunction;

public final class ArrayDoubleToIntFunctionStudent {

    private final ArrayDoubleToIntFunction instance;

    public ArrayDoubleToIntFunctionStudent(int[] array) {
        this.instance = TUtils.assertImplemented(() -> new ArrayDoubleToIntFunction(array));
    }

    public int apply(double value) {
        return instance.apply(value);
    }


}
