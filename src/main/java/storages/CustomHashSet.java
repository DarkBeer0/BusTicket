package storages;
import java.util.Iterator;
import java.util.LinkedList;

public class CustomHashSet<T> {
    private LinkedList<T>[] buckets;
    private int size;
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    public CustomHashSet() {
        buckets = new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    public void put(T element) {
        if (contains(element)) {
            return;
        }
        ensureCapacity();
        int index = getBucketIndex(element);
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        buckets[index].add(element);
        size++;
    }

    public boolean contains(T element) {
        int index = getBucketIndex(element);
        LinkedList<T> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        return bucket.contains(element);
    }

    public void delete(T element) {
        int index = getBucketIndex(element);
        LinkedList<T> bucket = buckets[index];
        if (bucket != null && bucket.remove(element)) {
            size--;
        }
    }

    private void ensureCapacity() {
        if (size >= buckets.length * LOAD_FACTOR) {
            resize();
        }
    }

    private void resize() {
        LinkedList<T>[] oldBuckets = buckets;
        buckets = new LinkedList[buckets.length * 2];
        size = 0;

        for (LinkedList<T> bucket : oldBuckets) {
            if (bucket != null) {
                for (T element : bucket) {
                    put(element);
                }
            }
        }
    }

    private int getBucketIndex(T element) {
        return element.hashCode() & (buckets.length - 1);
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int bucketIndex = 0;
            private Iterator<T> bucketIterator = buckets[bucketIndex] == null ? null : buckets[bucketIndex].iterator();

            @Override
            public boolean hasNext() {
                while ((bucketIterator == null || !bucketIterator.hasNext()) && bucketIndex < buckets.length - 1) {
                    bucketIndex++;
                    bucketIterator = buckets[bucketIndex] == null ? null : buckets[bucketIndex].iterator();
                }
                return bucketIterator != null && bucketIterator.hasNext();
            }

            @Override
            public T next() {
                return bucketIterator.next();
            }
        };
    }

    public int size() {
        return size;
    }
}

