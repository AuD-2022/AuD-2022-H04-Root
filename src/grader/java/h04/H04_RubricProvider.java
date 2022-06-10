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
                                "<<<apply>>> von <<<ArrayDoubleToIntFunction>>> funktioniert korrekt.",
                                1,
                                () -> H1_1_1.class.getMethod("t1", int[].class),
                                () -> H1_1_1.class.getMethod("t2", int[].class),
                                () -> H1_1_1.class.getMethod("t3")
                            ),
                            criterion(
                                "<<<apply>>> von <<<LinearDoubleToIntFunction>>> funktioniert korrekt.",
                                1,
                                () -> H1_1_2.class.getMethod("t1", double.class, double.class),
                                () -> H1_1_2.class.getMethod("t2")
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H1.2 | <<<ListToIntFunction>>>")
                        .addChildCriteria(
                            criterion(
                                "<<<apply>>> von <<<FunctionOnRatioOfRuns>>> funktioniert bei einem Run korrekt.",
                                1,
                                () -> H1_2_1.class.getMethod("t1", Stream.class)
                            ),
                            criterion(
                                "<<<apply>>> von <<<FunctionOnRatioOfRuns>>> funktioniert bei mehreren Runs korrekt.",
                                1,
                                () -> H1_2_1.class.getMethod("t2", Stream.class, int.class)
                            ),
                            criterion(
                                "<<<apply>>> von <<<FunctionOnRatioOfInversions>>> funktioniert bei einer Inversion korrekt.",
                                1,
                                () -> H1_2_2.class.getMethod("t2", List.class)
                            ),
                            criterion(
                                "<<<apply>>> von <<<FunctionOnRatioOfInversions>>> funktioniert bei keiner und mehreren Inversionen korrekt.",
                                1,
                                () -> H1_2_2.class.getMethod("t1", List.class),
                                () -> H1_2_2.class.getMethod("t3", List.class, int.class)
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H2 | <<<MyCollections>>>")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H2.1 | <<<sort>>>")
                        .addChildCriteria(
                            criterion(
                                "<<<listToListItem>>> von <<<MyCollections>>> funktioniert immer korrekt",
                                1,
                                () -> H2_1.class.getMethod("t1", Stream.class)
                            ),
                            criterion(
                                "<<<listItemToList>>> von <<<MyCollections>>> funktioniert immer korrekt",
                                1,
                                () -> H2_1.class.getMethod("t2", Stream.class)
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H2.2 | Merge Sort")
                        .addChildCriteria(
                            criterion(
                                "<<<split>>> funktioniert korrekt, wenn Liste aus zwei Runs gleicher Größe besteht.",
                                1,
                                () -> H2_2.class.getMethod("t1", Stream.class, int.class)
                            ),
                            criterion(
                                "<<<split>>> funktioniert in allen anderen Fällen korrekt.",
                                1,
                                () -> H2_2.class.getMethod("t2", Stream.class, int.class)
                            ),
                            criterion(
                                "Rekursion von <<<adaptiveMergeSortInPlace>>> bricht in entsprechenden Fällen ab.",
                                1,
                                () -> H2_2.class.getMethod("t3", Stream.class, int.class)
                            ),
                            criterion(
                                "<<<adaptiveMergeSortInPlace>>> wechselt in entsprechenden Fällen korrekt zu Selection Sort.",
                                1,
                                () -> H2_2.class.getMethod("t4", Stream.class, int.class)
                            ),
                            criterion(
                                "<<<adaptiveMergeSortInPlace>>> ruft in entsprechenden Fällen <<<merge>>> korrekt auf.",
                                1,
                                () -> H2_2.class.getMethod("t5", Stream.class, int.class, int.class)
                            ),
                            criterion(
                                "<<<adaptiveMergeSortInPlace>>> funktioniert immer korrekt",
                                1,
                                () -> H2_2.class.getMethod("t6", Stream.class, int.class)
                            ),
                            criterion(
                                "<<<merge>>> funktioniert immer korrekt",
                                1,
                                () -> H2_2.class.getMethod("t7", Stream.class, Stream.class)
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H2.3 | Selection Sort")
                        .addChildCriteria(
                            criterion(
                                "<<<selectionSortInPlace>>> funktioniert korrekt, wenn Liste sortiert ist.",
                                1,
                                () -> H2_3.class.getMethod("t1", Stream.class)
                            ),
                            criterion(
                                "<<<selectionSortInPlace>>> funktioniert korrekt, wenn ein Element umsortiert werden muss.",
                                2,
                                () -> H2_3.class.getMethod("t2", Stream.class)
                            ),
                            criterion(
                                "<<<selectionSortInPlace>>> funktioniert korrekt, wenn mehrere Elemente umsortiert werden müssen.",
                                4,
                                () -> H2_3.class.getMethod("t3", Stream.class)
                            ),
                            criterion(
                                "<<<selectionSortInPlace>>> funktioniert immer korrekt.",
                                4,
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
                        .shortDescription("H3.1 | <<<DoubleToIntFunctionFitter>>>")
                        .addChildCriteria(
                            criterion(
                                "<<<fitFunction>>> von <<<LinearInterpolation>>> funktioniert bei einem fehlenden Wert korrekt.",
                                1,
                                () -> H3_1.class.getMethod("t1", Stream.class, Stream.class)),
                            criterion(
                                "<<<fitFunction>>> von <<<LinearInterpolation>>> funktioniert bei mehreren fehlenden Werten korrekt.",
                                1,
                                () -> H3_1.class.getMethod("t2", Stream.class, Stream.class)),
                            criterion(
                                "<<<fitFunction>>> von <<<LinearRegression>>> funktioniert bei zwei Werten korrekt.",
                                1,
                                () -> H3_1_2.class.getMethod("t1", Stream.class, double.class, double.class)
                            ),
                            criterion(
                                "<<<fitFunction>>> von <<<LinearRegression>>> funktioniert bei mehreren Werten korrekt.",
                                1,
                                () -> H3_1_2.class.getMethod("t2", Stream.class, double.class, double.class)
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H3.2 | <<<SortingExperiment>>>")
                        .addChildCriteria(
                            criterion(
                                "<<<computeOptimalThresholds>>> von <<<SortingExperiment>>> wurde bearbeitet.",
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
