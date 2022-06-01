package h04.function;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Fits a function to a set of data points.
 *
 * <p>The function has the following form:
 * <ol>
 *   <li>Create a double-array and copy all indices values of y to the new array which are not {@code null}.</li>
 *   <li>The indices containing {@code null} values are linearly interpolated using the next left and right known function
 *   values.</li>
 *   <li>Create an int-array and round all values.</li>
 *   <li>Create a {@code ArrayDoubleToIntFunction} containing the rounded values.</li>
 * </ol>
 *
 * @author Nhan Huynh
 */
public class LinearInterpolation implements DoubleToIntFunctionFitter {

    @Override
    public DoubleToIntFunction fitFunction(@Nullable Integer[] y) {
        Integer[] out = y;
        int[] res = new int[y.length];
        res[0] = y[0];
        res[res.length - 1] = y[y.length - 1];
        int i = 0;
        while (i < out.length - 1) {
            if (out[i] == null) {
                int j = 0;
                while (out[i + j] == null) j++;
                for (int k = 0; k < j; k++) {
                    double tmp = out[i - 1] +
                        (out[i + j] - out[i - 1]) * (k + 1) / (j + 1);
                    out[i + k] = (int) Math.ceil(tmp);
                    res[i + k] = out[i + k];
                }
                i = i + j;
            } else {
                res[i] = y[i];
                i++;
            }
        }
        for (int n = 0; n < res.length; n++) System.out.print(res[n] + ", ");
        return new ArrayDoubleToIntFunction(res);
    }


}
