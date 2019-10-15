package map;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString(of = "buckets")
@AllArgsConstructor
public class MyHashMap<K, V> {
    private Buckets<K, V> buckets;
    private int exponent;
    private static final int INITIAL_EXPONENT = 4;
    private static final double LOAD_FACTOR = 0.75;

    public MyHashMap() {
        this.buckets = Buckets.of(powerOfTwo(INITIAL_EXPONENT));
        this.exponent = INITIAL_EXPONENT;
    }

    public void put(K key, V value) {
        if (size() > countBuckets() * LOAD_FACTOR) {
            resize(++exponent);
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
        return powerOfTwo(exponent);
    }

    private void resize(int exponent) {
        buckets = buckets.rehashWithSize(powerOfTwo(exponent));
    }

    private int powerOfTwo(int exponent) {
        return 1 << exponent;
    }
}