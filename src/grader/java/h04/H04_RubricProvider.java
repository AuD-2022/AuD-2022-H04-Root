package h04;

import java.util.List;
import java.util.stream.Stream;

import static h04.RubricUtils.criterion;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricForSubmission;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

@RubricForSubmission("h04")
public class H04_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H04 | Hybride Sortieralgorithmen")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H1 | Degree of Disorder")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H1.1 | DoubleToIntFunction")
                        .addChildCriteria(
                            criterion(
                                "<code>apply</code> von <code>ArrayDoubleToIntFunction</code> funktioniert korrekt.",
                                1,
                                () -> H1_1_1.class.getMethod("t1", int[].class),
                                () -> H1_1_1.class.getMethod("t2", int[].class),
                                () -> H1_1_1.class.getMethod("t3")
                            ),
                            criterion(
                                "<code>apply</code> von <code>LinearDoubleToIntFunction</code> funktioniert korrekt.",
                                1,
                                () -> H1_1_2.class.getMethod("t1", double.class, double.class),
                                () -> H1_1_2.class.getMethod("t2")
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H1.2 | <code>ListToIntFunction</code>")
                        .addChildCriteria(
                            criterion(
                                "<code>apply</code> von <code>FunctionOnRatioOfRuns</code> funktioniert bei einem Run korrekt.",
                                1,
                                () -> H1_2_1.class.getMethod("t1", Stream.class)
                            ),
                            criterion(
                                "<code>apply</code> von <code>FunctionOnRatioOfRuns</code> funktioniert bei mehreren Runs korrekt.",
                                1,
                                () -> H1_2_1.class.getMethod("t2", Stream.class, int.class)
                            ),
                            criterion(
                                "<code>apply</code> von <code>FunctionOnRatioOfInversions</code> funktioniert bei einer Inversion korrekt.",
                                1,
                                () -> H1_2_2.class.getMethod("t2", List.class)
                            ),
                            criterion(
                                "<code>apply</code> von <code>FunctionOnRatioOfInversions</code> funktioniert bei keiner und mehreren Inversionen korrekt.",
                                1,
                                () -> H1_2_2.class.getMethod("t1", List.class),
                                () -> H1_2_2.class.getMethod("t3", List.class, int.class)
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H2 | <code>MyCollections</code>")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H2.1 | <code>sort</code>")
                        .addChildCriteria(
                            criterion(
                                "<code>listToListItem</code> von <code>MyCollections</code> funktioniert immer korrekt",
                                1,
                                () -> H2_1.class.getMethod("t1", Stream.class)
                            ),
                            criterion(
                                "<code>listItemToList</code> von <code>MyCollections</code> funktioniert immer korrekt",
                                1,
                                () -> H2_1.class.getMethod("t2", Stream.class)
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H2.2 | Merge Sort")
                        .addChildCriteria(
                            criterion(
                                "<code>split</code> funktioniert korrekt, wenn Liste aus zwei Runs gleicher Größe besteht.",
                                1,
                                () -> H2_2.class.getMethod("t1", Stream.class, int.class)
                            ),
                            criterion(
                                "<code>split</code> funktioniert in allen anderen Fällen korrekt.",
                                1,
                                () -> H2_2.class.getMethod("t2", Stream.class, int.class)
                            ),
                            criterion(
                                "Rekursion von <code>adaptiveMergeSortInPlace</code> bricht in entsprechenden Fällen ab.",
                                1,
                                () -> H2_2.class.getMethod("t3", Stream.class, int.class)
                            ),
                            criterion(
                                "<code>adaptiveMergeSortInPlace</code> wechselt in entsprechenden Fällen korrekt zu Selection Sort.",
                                1,
                                () -> H2_2.class.getMethod("t4", Stream.class, int.class)
                            ),
                            criterion(
                                "<code>adaptiveMergeSortInPlace</code> ruft in entsprechenden Fällen <code>merge</code> korrekt auf.",
                                1,
                                () -> H2_2.class.getMethod("t5", Stream.class, int.class, int.class)
                            ),
                            criterion(
                                "<code>adaptiveMergeSortInPlace</code> funktioniert immer korrekt",
                                1,
                                () -> H2_2.class.getMethod("t6", Stream.class, int.class)
                            ),
                            criterion(
                                "<code>merge</code> funktioniert immer korrekt",
                                1,
                                () -> H2_2.class.getMethod("t7", Stream.class, Stream.class)
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H2.3 | Selection Sort")
                        .addChildCriteria(
                            criterion(
                                "<code>selectionSortInPlace</code> funktioniert korrekt, wenn Liste sortiert ist.",
                                1,
                                () -> H2_3.class.getMethod("t1", Stream.class)
                            ),
                            criterion(
                                "<code>selectionSortInPlace</code> funktioniert korrekt, wenn ein Element umsortiert werden muss.",
                                2,
                                () -> H2_3.class.getMethod("t2", Stream.class)
                            ),
                            criterion(
                                "<code>selectionSortInPlace</code> funktioniert korrekt, wenn mehrere Elemente umsortiert werden müssen.",
                                4,
                                () -> H2_3.class.getMethod("t3", Stream.class)
                            ),
                            criterion(
                                "<code>selectionSortInPlace</code> funktioniert immer korrekt.",
                                2,
                                () -> H2_3.class.getMethod("t1", Stream.class),
                                () -> H2_3.class.getMethod("t2", Stream.class),
                                () -> H2_3.class.getMethod("t3", Stream.class)
                            )
                        )
                        .build()
                )

                .build(),
            Criterion.builder()
                .shortDescription("H3 | Umschaltlänge")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H3.1 | <code>DoubleToIntFunctionFitter</code>")
                        .addChildCriteria(
                            criterion(
                                "<code>fitFunction</code> von <code>LinearInterpolation</code> funktioniert bei einem fehlenden Wert korrekt.",
                                1,
                                () -> H3_1.class.getMethod("t1", Stream.class, Stream.class)),
                            criterion(
                                "<code>fitFunction</code> von <code>LinearInterpolation</code> funktioniert bei mehreren fehlenden Werten korrekt.",
                                1,
                                () -> H3_1.class.getMethod("t2", Stream.class, Stream.class)),
                            criterion(
                                "<code>fitFunction</code> von <code>LinearRegression</code> funktioniert bei zwei Werten korrekt.",
                                1,
                                () -> H3_1_2.class.getMethod("t1", Stream.class, double.class, double.class)
                            ),
                            criterion(
                                "<code>fitFunction</code> von <code>LinearRegression</code> funktioniert bei mehreren Werten korrekt.",
                                1,
                                () -> H3_1_2.class.getMethod("t2", Stream.class, double.class, double.class)
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H3.2 | <code>SortingExperiment</code>")
                        .addChildCriteria(
                            criterion(
                                "<code>computeOptimalThresholds</code> von <code>SortingExperiment</code> wurde bearbeitet.",
                                4,
                                () -> H3_2.class.getMethod("t1")
                            )
                        )
                        .build()
                )
                .build()
        )
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new AccessTransformer());
    }
}
