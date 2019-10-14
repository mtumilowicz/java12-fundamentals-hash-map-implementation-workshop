package map;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(of = "buckets")
@RequiredArgsConstructor
public class MyHashMap<K, V> {
    private final Buckets<K, V> buckets;
    private static final int INITIAL_CAPACITY = 1 << 4;

    public MyHashMap() {
        this.buckets = Buckets.of(INITIAL_CAPACITY);
    }

    public void put(K key, V value) {
        buckets.insert(key, value);
    }

    public V get(K key) {
        return buckets.get(key);
    }

    public long size() {
        return buckets.countElementsInBuckets();
    }

    public int countBuckets() {
        return buckets.countBuckets();
    }
}