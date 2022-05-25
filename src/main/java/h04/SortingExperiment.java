package h04;

import h04.collection.MyCollections;
import h04.function.ConstantListToIntFunction;
import h04.function.FunctionOnDegreeOfDisorder;
import h04.function.FunctionOnRatioOfInversions;
import h04.function.FunctionOnRatioOfRuns;
import h04.function.LinearInterpolation;
import h04.function.LinearRegression;
import h04.function.ListToIntFunction;
import h04.util.Permutations;
import org.jetbrains.annotations.Nullable;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A sorting experiment to determine the optimal thresholds for {@link MyCollections#sort(List)}.
 *
 * @author Kim Berninger, Nhan Huynh
 */
public final class SortingExperiment {

    /**
     * Don't let anyone instantiate this class.
     */
    private SortingExperiment() {
    }

    /**
     * Main entry point in executing the sorting experiment.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        int n = 1000;
        int swaps = 800;
        int bins = 100;
        double gamma = 0.5;

        Integer[][] optimalThresholds = computeOptimalThresholds(n, swaps, bins, gamma);
        System.out.println("Optimal thresholds (Runs, Inversions):");
        System.out.println(Arrays.toString(optimalThresholds[0]));
        System.out.println(Arrays.toString(optimalThresholds[1]));

        Comparator<Integer> cmp = Comparator.naturalOrder();

        int permutations = 1000;
        String title;
        double time;

        // Linear regression ratio of runs
        LinearRegression regression = new LinearRegression();
        title = "Linear regression ratio of runs";
        time = averageSortingTimeInMilliseconds(n, new FunctionOnRatioOfRuns<>(cmp, regression.fitFunction(optimalThresholds[0])),
            permutations);
        System.out.printf("%s: average elapsed time: %.2f ms\n", title, time);

        // Linear interpolation ratio of runs
        LinearInterpolation interpolation = new LinearInterpolation();
        title = "Linear interpolation ratio of runs";
        time = averageSortingTimeInMilliseconds(n, new FunctionOnRatioOfRuns<>(cmp,
            interpolation.fitFunction(optimalThresholds[0])), permutations);
        System.out.printf("%s: average elapsed time: %.2f ms\n", title, time);

        // Linear regression ratio of runs
        title = "Linear regression ratio of inversions";
        time = averageSortingTimeInMilliseconds(n, new FunctionOnRatioOfInversions<>(cmp,
            regression.fitFunction(optimalThresholds[1])), permutations);
        System.out.printf("%s: average elapsed time: %.2f ms\n", title, time);

        // Linear interpolation ratio of inversions
        title = "Linear interpolation ratio of inversions";
        time = averageSortingTimeInMilliseconds(n, new FunctionOnRatioOfInversions<>(cmp,
            interpolation.fitFunction(optimalThresholds[1])), permutations);
        System.out.printf("%s: average elapsed time: %.2f ms\n", title, time);
    }


    /**
     * Computes the most optimal threshold for runs and inversions in consideration to the least CPU time.
     *
     * @param n     the length of the list to be sorted to measure the CPU time of the algorithm
     * @param swaps the maximum number of permutations to be performed in order to generate the random permutations
     * @param bins  the number of bins in which the key figures of runs and inversions are to be grouped respectively
     * @param gamma the minimum proportion of the threshold to be tried for a bin should be tried for a bin to determine a valid
     *              result
     *
     * @return the most optimal threshold for runs and inversions
     */
    public static @Nullable Integer[][] computeOptimalThresholds(int n, int swaps, int bins, double gamma) {
        @Nullable Integer[][] optimalThresholds = new Integer[2][bins];
        @Nullable Duration[][] optimalDurations = new Duration[2][bins];

        List<Integer> p = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            p.add(i);
        }
        int numberOfThresholds = (int) Math.ceil(Math.log(n) / Math.log(2));
        boolean[][][] observedThresholds = new boolean[2][bins][numberOfThresholds];

        Comparator<Integer> cmp = Integer::compareTo;
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        for (int iteration = 1; iteration <= 2; iteration++) {
            for (int exponent = 0; exponent < numberOfThresholds; exponent++) {
                // u = {n, n/2, n/4, ..., 1}
                int u = (int) Math.ceil(n / Math.pow(2, exponent));
                // s := {0, ..., swaps}}
                for (int s = 0; s < swaps; s++) {
                    // p'
                    List<Integer> pd = new ArrayList<>(p);

                    // Iteration 1 = {1, ..., n}, iteration 2 = {n, ..., 1}
                    if (iteration == 2) {
                        Collections.reverse(pd);
                    }
                    Permutations.randomSwaps(pd, s);

                    int runs = Permutations.computeNumberOfRuns(pd, cmp);
                    int inversions = Permutations.computeNumberOfInversions(pd, cmp);

                    // [k_run, k_inv]
                    int[] k = {
                        bins * (runs - 1) / n,
                        bins * inversions / (n * (n - 1) / 2 + 1)
                    };

                    MyCollections<Integer> mc = new MyCollections<>(
                        new ConstantListToIntFunction<>(u), cmp
                    );

                    // Instant start = Instant.now();
                    long startCpu = bean.getCurrentThreadCpuTime();
                    mc.sort(pd);
                    // Instant end = Instant.now();
                    long endCpu = bean.getCurrentThreadCpuTime();
                    assert isSorted(pd, cmp) : "The list is not sorted";
                    Duration duration = Duration.ofNanos(endCpu - startCpu);

                    for (int index = 0; index < k.length; index++) {
                        // true if the threshold is not yet observed
                        // We are going to observe it now
                        observedThresholds[index][k[index]][exponent] = true;
                        // If no duration is set or the new duration is lower, save it
                        // If the durations are equal, save the one with the lower threshold
                        if (optimalDurations[index][k[index]] == null
                            || optimalDurations[index][k[index]].compareTo(duration) >= 0
                             && optimalThresholds[index][k[index]].compareTo(u) > 0) {
                            optimalDurations[index][k[index]] = duration;
                            optimalThresholds[index][k[index]] = u;
                        }
                    }
                }
                System.out.println(u);
            }
        }

        // Set all components to null if we did not test (gamma * log(n))/log(2) distinct switching
        // lengths
        for (int i = 0; i < optimalThresholds.length; i++) {
            for (int j = 0; j < optimalThresholds[i].length; j++) {
                int numberOfObservations = 0;
                // Check observed thresholds (threshold)
                for (boolean hasObservedThreshold : observedThresholds[i][j]) {
                    if (hasObservedThreshold) {
                        numberOfObservations++;
                    }
                }
                // (gamma * log(n))/log(2) distinct threshold
                if (numberOfObservations < gamma * numberOfThresholds) {
                    optimalThresholds[i][j] = null;
                }
            }
        }
        return optimalThresholds;
    }

    /**
     * Returns {@code true} if the list is sorted, {@code false} otherwise.
     *
     * @param list the list to be checked
     * @param cmp  the comparator used to compare elements
     * @param <T>  the type of the elements
     *
     * @return {@code true} if the list is sorted, {@code false} otherwise
     */
    private static <T> boolean isSorted(List<T> list, Comparator<? super T> cmp) {
        Iterator<T> it = list.iterator();
        // Empty list
        if (!it.hasNext()) {
            return true;
        }

        T previous = it.next();
        while (it.hasNext()) {
            T current = it.next();
            if (cmp.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    /**
     * Computes the average sorting time in milliseconds for a list of n elements. The average time is determined by the given
     * number of permutations that should be tested and the given threshold.
     *
     * @param n            the length of the list to be sorted to measure the CPU time of the algorithm
     * @param function     the function that is used to determine the optimal threshold
     * @param permutations the number of permutations to be tested
     *
     * @return the average sorting time in milliseconds
     */
    private static double averageSortingTimeInMilliseconds(
        int n,
        ListToIntFunction<Integer> function,
        int permutations) {
        List<Integer> permutation = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            permutation.add(i);
        }
        MyCollections<Integer> mc = new MyCollections<>(function, Comparator.naturalOrder());
        long totalTime = 0;
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        for (int i = 0; i < permutations; i++) {
            // p'
            List<Integer> p = new ArrayList<>(permutation);
            Collections.shuffle(p);

            // Time measurement
            // Instant start = Instant.now();
            long startCpu = bean.getCurrentThreadCpuTime();
            mc.sort(p);
            // Instant end = Instant.now();
            long endCpu = bean.getCurrentThreadCpuTime();

            totalTime += Duration.ofNanos(endCpu - startCpu).toMillis();
        }

        return (double) totalTime / permutations;
    }
}
