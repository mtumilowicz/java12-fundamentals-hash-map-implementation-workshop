import lombok.ToString;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;

@ToString(of = "buckets")
public class MyMap<K, V> {
    private ArrayList<Bucket<K, V>> buckets = new ArrayList<>();
    private static final int INITIAL_CAPACITY = 1 << 4; // 16

    public MyMap() {
        IntStream.iterate(0, bucket -> bucket < INITIAL_CAPACITY, bucket -> ++bucket)
                .mapToObj(Bucket<K, V>::new)
                .forEach(bucket -> buckets.add(bucket));
    }

    public void put(K key, V value) {
        bucket(key).add(key, value);
    }

    public V get(K key) {
        return bucket(key).get(key);
    }

    public Bucket<K, V> bucket(K key) {
        return buckets.get(getBucketIndex(key));
    }

    private int getBucketIndex(K key) {
        return hash(key) % getBucketsSize();
    }

    public long size() {
        return buckets.stream()
                .map(Bucket::size)
                .mapToLong(Long::longValue)
                .sum();
    }

    public int getBucketsSize() {
        return buckets.size();
    }

    int hash(K key) {
        return Math.abs(Objects.hashCode(key));
    }
}