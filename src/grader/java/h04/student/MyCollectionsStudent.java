package h04.student;

import java.util.Comparator;
import java.util.List;

import static h04.TUtils.assertImplemented;
import static java.util.Comparator.naturalOrder;
import static org.mockito.Mockito.spy;

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

    public ListItem<T> listToListItem(List<T> list) {
        return assertImplemented(() -> student.listToListItem(list));
    }

    public void listItemToList(ListItem<T> head, List<T> list) {
        assertImplemented(() -> {
            student.listItemToList(head, list);
            return null;
        });
    }

    public void sort(List<T> list) {
        assertImplemented(() -> {
            student.sort(list);
            return null;
        });
    }

    public ListItem<T> adaptiveMergeSortInPlace(ListItem<T> head, int threshold) {
        return assertImplemented(() -> student.adaptiveMergeSortInPlace(head, threshold));
    }

    public ListItem<T> split(ListItem<T> head, int optimalSize) {
        return assertImplemented(() -> student.split(head, optimalSize));
    }

    public ListItem<T> merge(ListItem<T> left, ListItem<T> right) {
        return assertImplemented(() -> student.merge(left, right));
    }

    public ListItem<T> selectionSortInPlace(ListItem<T> head) {
        return assertImplemented(() -> student.selectionSortInPlace(head));
    }

    public static MyCollectionsStudent<String> forString() {
        return new MyCollectionsStudent<>(l -> 1, naturalOrder());
    }
}
