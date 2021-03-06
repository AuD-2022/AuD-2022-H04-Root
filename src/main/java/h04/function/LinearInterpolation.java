package h04.function;

import org.jetbrains.annotations.Nullable;

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
        double[] interpolation = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            if (y[i] != null) {
                interpolation[i] = y[i];
            } else {
                // Find the next left and right known function values
                int left = 0;
                for (int j = i - 1; j >= 0; j--) {
                    if (y[j] != null) {
                        left = j;
                        break;
                    }
                }
                int right = 0;
                for (int j = i + 1; j < y.length; j++) {
                    if (y[j] != null) {
                        right = j;
                        break;
                    }
                }
                // Linearly interpolate
                //noinspection ConstantConditions
                interpolation[i] = y[left] + (y[right] - y[left]) * ((double) (i - left) / (right - left));
            }
        }

        // Round all values
        int[] rounded = new int[interpolation.length];
        for (int i = 0; i < interpolation.length; i++) {
            rounded[i] = (int) Math.round(interpolation[i]);
        }
        return new ArrayDoubleToIntFunction(rounded);
    }
}
