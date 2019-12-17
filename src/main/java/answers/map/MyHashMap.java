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
        if (shouldBeResized()) {
            resize();
        }
        buckets.insert(key, value);
    }

    private boolean shouldBeResized() {
        return size() > countBuckets() * LOAD_FACTOR;
    }

    public V get(K key) {
        return buckets.get(key);
    }

    public long size() {
        return buckets.countElementsInBuckets();
    }

    int countBuckets() {
        return buckets.countBuckets();
    }

    long countElementsInSameBucketAs(K key) {
        return buckets.countElementsInSameBucketAs(key);
    }

    private void resize() {
        buckets = buckets.expandByPow2();
    }
}