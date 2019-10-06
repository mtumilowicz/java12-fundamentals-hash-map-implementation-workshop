import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

@ToString(of = "buckets")
public class MyMap<K, V> {
    private ArrayList<Bucket<K, V>> buckets = new ArrayList<>();
    private static final int INITIAL_CAPACITY = 1 << 4; // 16
    private int size = 0;

    public MyMap() {
        IntStream.iterate(0, i -> i < INITIAL_CAPACITY, i -> ++i)
                .forEach(i -> {
                    buckets.add(i, new Bucket<>());
                });
    }

    public void put(K key, V value) {
        Bucket<K, V> bucket = buckets.get(getBucketIndex(key));
        bucket.add(key, value);
    }

    public V get(K key) {
        Bucket<K, V> bucket = buckets.get(getBucketIndex(key));
        return bucket.get(key);
    }

    public Bucket<K, V> getBucket(K key) {
        return buckets.get(getBucketIndex(key));
    }

    private int getBucketIndex(K key) {
        return getHash(key) % getBucketsSize();
    }

    public long size() {
        return buckets.stream().map(Bucket::size).mapToLong(x -> x).sum();
    }

    public int getBucketsSize() {
        return buckets.size();
    }

    private int getHash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }
}