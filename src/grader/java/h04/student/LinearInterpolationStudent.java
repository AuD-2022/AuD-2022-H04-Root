package h04.student;

import h04.TUtils;
import h04.function.DoubleToIntFunction;
import h04.function.DoubleToIntFunctionFitter;
import h04.function.LinearInterpolation;

public class LinearInterpolationStudent implements DoubleToIntFunctionFitter {

    public final LinearInterpolation student;

    public LinearInterpolationStudent() {
        student = new LinearInterpolation();
    }

    @Override
    public DoubleToIntFunction fitFunction(Integer[] y) {
        return TUtils.assertImplemented(() -> student.fitFunction(y));
    }

}
