package h04.student;

import java.util.Comparator;
import java.util.List;

import static h04.TUtils.assertImplemented;
import static java.util.Comparator.naturalOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

import h04.ListUtils;
import h04.collection.ListItem;
import h04.collection.MyCollections;
import h04.function.ListToIntFunction;
import h04.tutor.MyCollectionsTutor;

public class MyCollectionsStudent<T> {

    public final MyCollections<T> student;
    public final MyCollectionsTutor<T> tutor;

    public MyCollectionsStudent(ListToIntFunction<T> function, Comparator<? super T> comparator) {
        this.student = spy(new MyCollections<>(function, comparator));
        this.tutor = new MyCollectionsTutor<>(function, comparator);
    }

    public static MyCollectionsStudent<String> forString() {
        return new MyCollectionsStudent<>(l -> 1, naturalOrder());
    }

    public ListItem<T> listToListItem(List<T> list) {
        var callString = String.format("listToListItem(%s)", list);
        return assertImplemented(
            () -> student.listToListItem(list),
            callString
        );
    }

    public void listToListItemUseTutor() {
        doAnswer(tutor::listToListItemByInvocation).when(student).listItemToList(any(), any());
    }

    public void listItemToList(ListItem<T> head, List<T> list) {
        var callString = String.format("listItemToList(%s,%s)", ListUtils.toString(head), list);
        assertImplemented(() -> {
                student.listItemToList(head, list);
                return null;
            },
            callString);
    }

    public void listItemToListUseTutor() {
        doAnswer(tutor::listItemToListByInvocation).when(student).listItemToList(any(), any());
    }

    public void sort(List<T> list) {
        var callString = String.format("sort(%s)", list);
        assertImplemented(() -> {
                student.sort(list);
                return null;
            },
            callString
        );
    }

    public void sortUseTutor() {
        doAnswer(tutor::sortByInvocation).when(student).sort(any());
    }

    public ListItem<T> adaptiveMergeSortInPlace(ListItem<T> head, int threshold) {
        var callString = String.format("adaptiveMergeSortInPlace(%s,%s)", ListUtils.toString(head), threshold);
        return assertImplemented(() -> student.adaptiveMergeSortInPlace(head, threshold), callString);
    }

    public void adaptiveMergeSortInPlaceUseTutor() {
        doAnswer(tutor::adaptiveMergeSortInPlaceByInvocation).when(student).adaptiveMergeSortInPlace(any(), anyInt());
    }

    public ListItem<T> split(ListItem<T> head, int optimalSize) {
        var callString = String.format("split(%s,%s)", ListUtils.toString(head), optimalSize);
        return assertImplemented(() -> student.split(head, optimalSize), callString);
    }

    public void useReferenceForSplit() {
        doAnswer(tutor::splitByInvocation).when(student).split(any(), anyInt());
    }

    public ListItem<T> merge(ListItem<T> left, ListItem<T> right) {
        var callString = String.format("merge(%s,%s)", ListUtils.toString(left), ListUtils.toString(right));
        return assertImplemented(() -> student.merge(left, right), callString);
    }

    public void useReferenceForMerge() {
        doAnswer(tutor::mergeByInvocation).when(student).merge(any(), any());
    }

    public ListItem<T> selectionSortInPlace(ListItem<T> head) {
        var callString = String.format("selectionSortInPlace(%s)", ListUtils.toString(head));
        return assertImplemented(() -> student.selectionSortInPlace(head), callString);
    }

    public void useReferenceForSelectionSortInPlace() {
        doAnswer(tutor::selectionSortInPlaceByInvocation).when(student).selectionSortInPlace(any());
    }
}
