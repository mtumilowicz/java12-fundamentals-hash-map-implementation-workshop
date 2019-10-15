package map;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString(of = "buckets")
@AllArgsConstructor
public class MyHashMap<K, V> {
    private Buckets<K, V> buckets;
    private int capacity;
    private static final int INITIAL_CAPACITY_AS_POWER_OF_TWO = 4;
    private static final double LOAD_FACTOR = 0.75;

    public MyHashMap() {
        this.buckets = Buckets.of(1 << INITIAL_CAPACITY_AS_POWER_OF_TWO);
        this.capacity = INITIAL_CAPACITY_AS_POWER_OF_TWO;
    }

    public void put(K key, V value) {
        if (size() > (1 << capacity) * LOAD_FACTOR) {
            resize(++capacity);
        }
        buckets.insert(key, value);
    }

    public V get(K key) {
        return buckets.get(key);
    }

    public long size() {
        return buckets.countElementsInBuckets();
    }

    public int countBuckets() {
        return 1 << capacity;
    }

    private void resize(int powerOfTwo) {
        buckets = buckets.rehashTo(1 << powerOfTwo);
    }
}