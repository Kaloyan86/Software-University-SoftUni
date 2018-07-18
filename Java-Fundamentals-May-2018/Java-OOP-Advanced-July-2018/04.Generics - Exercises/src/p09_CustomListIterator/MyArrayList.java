package p09_CustomListIterator;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MyArrayList<T extends Comparable<T>> implements MyList<T>, Iterable<T> {

    private List<T> elements;

    public MyArrayList() {
        this.elements = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        this.elements.add(element);
    }

    @Override
    public T remove(int index) {
        return this.elements.remove(index);
    }

    @Override
    public boolean contains(T element) {
        return this.elements.contains(element);
    }

    @Override
    public void swap(int firstIndex, int secondIndex) {
        Collections.swap(this.elements, firstIndex, secondIndex);
    }

    @Override
    public int countGreaterThan(T element) {
        return this.elements.stream()
                .filter(e -> element.compareTo(e) < 0)
                .collect(Collectors.toList())
                .size();
    }

    @Override
    public T getMax() {
        if (this.elements.isEmpty()){
            return null;
        }

        T max = this.elements.get(0);
        for (int i = 1; i < this.elements.size(); i++) {
            if (max.compareTo(this.elements.get(i))  < 0){
                max = this.elements.get(i);
            }
        }

        return max;
    }

    @Override
    public T getMin() {
        if (this.elements.isEmpty()){
            return null;
        }

        T min = this.elements.get(0);
        for (int i = 1; i < this.elements.size(); i++) {
            if (min.compareTo(this.elements.get(i))  > 0){
                min = this.elements.get(i);
            }
        }

        return min;
    }

    @Override
    public void sort() {
        this.elements = this.elements.stream()
                .sorted((e1, e2) -> e1.compareTo(e2))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        if (this.elements.isEmpty()){
            return null;
        }

        StringBuilder result = new StringBuilder()
                .append(this.elements.get(0));

        for (int i = 1; i < this.elements.size(); i++) {
            result.append(System.lineSeparator())
                    .append(this.elements.get(i));
        }

        return result.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    public final class MyArrayListIterator implements Iterator<T>{
        private int counter = 0;

        @Override
        public boolean hasNext() {
            return this.counter < elements.size();
        }

        @Override
        public T next() {
            return elements.get(counter++);
        }
    }
}