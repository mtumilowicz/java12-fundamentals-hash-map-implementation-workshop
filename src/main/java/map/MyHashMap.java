package map;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString(of = "buckets")
@AllArgsConstructor
public class MyHashMap<K, V> {
    private Buckets<K, V> buckets;
    private int capacityAsPowerOfTwo;
    private static final int INITIAL_CAPACITY_AS_POWER_OF_TWO = 4;
    private static final double LOAD_FACTOR = 0.75;

    public MyHashMap() {
        this.buckets = Buckets.of(powerOfTwo(INITIAL_CAPACITY_AS_POWER_OF_TWO));
        this.capacityAsPowerOfTwo = INITIAL_CAPACITY_AS_POWER_OF_TWO;
    }

    public void put(K key, V value) {
        if (size() > powerOfTwo(capacityAsPowerOfTwo) * LOAD_FACTOR) {
            resize(++capacityAsPowerOfTwo);
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
        return powerOfTwo(capacityAsPowerOfTwo);
    }

    private void resize(int powerOfTwo) {
        buckets = buckets.rehashWithSize(powerOfTwo(powerOfTwo));
    }

    private int powerOfTwo(int powerOfTwo) {
        return 1 << powerOfTwo;
    }
}