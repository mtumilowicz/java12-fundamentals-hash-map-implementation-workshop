package answers.map;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString(of = "buckets")
@AllArgsConstructor
class MyHashMap<K, V> implements MyMap<K, V> {
    private Buckets<K, V> buckets;
    private static final double LOAD_FACTOR = 0.75;

    MyHashMap() {
        this.buckets = Buckets.of();
    }

    public void put(K key, V value) {
        if (loadFactorExceeded()) {
            resize();
        }
        buckets.insert(key, value);
    }

    public V get(K key) {
        return buckets.get(key);
    }

    public long size() {
        return buckets.countElementsInBuckets();
    }

    private void resize() {
        buckets = buckets.withDoubledBucketsCount();
    }

    private boolean loadFactorExceeded() {
        return buckets.loadFactorExceeded(LOAD_FACTOR);
    }
}