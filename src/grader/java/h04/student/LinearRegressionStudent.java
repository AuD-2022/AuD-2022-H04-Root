package h04.student;

import h04.TUtils;
import h04.function.DoubleToIntFunction;
import h04.function.DoubleToIntFunctionFitter;
import h04.function.LinearRegression;

public class LinearRegressionStudent implements DoubleToIntFunctionFitter {

    public final LinearRegression student;

    public LinearRegressionStudent() {
        this.student = new LinearRegression();
    }

    @Override
    public DoubleToIntFunction fitFunction(Integer[] y) {
        return TUtils.assertImplemented(() -> student.fitFunction(y));
    }
}
