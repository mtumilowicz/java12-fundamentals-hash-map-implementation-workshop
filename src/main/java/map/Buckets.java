package map;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@ToString
class Buckets<K, V> {
    private final ArrayList<Bucket<K, V>> buckets;

    static <V, K> Buckets<K, V> of(int numberOfBuckets) {
        var buckets = new ArrayList<Bucket<K, V>>();
        IntStream.iterate(0, bucket -> bucket < numberOfBuckets, bucket -> ++bucket)
                .mapToObj(Bucket<K, V>::new)
                .forEach(buckets::add);

        return new Buckets<>(buckets);
    }

    Buckets<K, V> resize() {
        var buckets = new ArrayList<Bucket<K, V>>();

        IntStream.iterate(0, bucket -> bucket < 2 * countBuckets(), bucket -> ++bucket)
                .mapToObj(Bucket<K, V>::new)
                .forEach(buckets::add);

        var resized = new Buckets<>(buckets);

        buckets.stream().map(x -> x.entries)
                .flatMap(Collection::stream)
                .forEach(entry -> resized.insert(entry.getKey(), entry.getValue()));

        return resized;
    }

    long countElementsInBuckets() {
        return buckets.stream()
                .map(Bucket::size)
                .mapToLong(Long::longValue)
                .sum();
    }

    int countBuckets() {
        return buckets.size();
    }

    void insert(K key, V value) {
        bucket(key).add(key, value);
    }

    V get(K key) {
        return bucket(key).get(key);
    }

    private Bucket<K, V> bucket(K key) {
        return buckets.get(getBucketIndex(key));
    }

    private int getBucketIndex(K key) {
        return hash(key) % countBuckets();
    }

    private int hash(K key) {
        return Math.abs(Objects.hashCode(key));
    }
}
