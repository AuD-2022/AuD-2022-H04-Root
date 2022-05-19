package h04.function;

import org.jetbrains.annotations.Nullable;

/**
 * Fits a function to a set of data points.
 *
 * <p>The function has the following form:
 * <pre>{@code
 *    n = number of data points
 *    samples = number of non null data points
 *    x = i / (n - 1)
 *    y = f(x)
 *    x^hat = sum(x_i) / samples
 *    y^hat = sum(y_i) / samples
 *    beta_1 = sum_i^n [(x_i - x hat)^* (y_i - y hat)] / sum_i^n [(x_i - x hat)^2]
 *    beta_2 = y^hat - beta_1 * x^hat
 *
 *    Fitter(x, y) = beta_1 * x + beta_2
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class LinearRegression implements DoubleToIntFunctionFitter {

    @Override
    public DoubleToIntFunction fitFunction(@Nullable Integer[] y) {
        double sumX = 0;
        double sumY = 0;
        // Non null samples
        int samples = 0;

        // Compute sum of X and Y in order to compute x and y hat
        for (int i = 0; i < y.length; i++) {
            if (y[i] != null) {
                sumX += (double) i / y.length;
                sumY += y[i];
                samples++;
            }
        }
        // x^hat  = average of all x values (not null)
        double averageX = sumX / samples;
        // y^hat  = average of all y values (not null)
        double averageY = sumY / samples;

        // sum_i^n [(x_i - x hat)^2 * (y_i - y hat)]
        double sumXY = 0;
        // sum_i^n [(x_i - x hat)^2]
        double sumXX = 0;
        for (int i = 0; i < y.length; i++) {
            if (y[i] != null) {
                double x = (double) i / y.length;
                sumXY += (x - averageX) * (y[i] - averageY);
                sumXX += Math.pow((x - averageX), 2);
            }
        }

        // beta_1 = sum_i^n [(x_i - x hat)^2 * (y_i - y hat)] / sum_i^n [(x_i - x hat)^2]
        double beta1 = sumXY / sumXX;
        // beta_2 = y^hat - beta_1 * x^hat
        double beta2 = averageY - beta1 * averageX;
        return new LinearDoubleToIntFunction(beta1, beta2);
    }
}
