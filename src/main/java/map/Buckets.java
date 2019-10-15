package map;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@ToString
class Buckets<K, V> {
    private final ArrayList<Bucket<K, V>> buckets;

    static <V, K> Buckets<K, V> of(int numberOfBuckets) {
        var buckets = IntStream.range(0, numberOfBuckets)
                .mapToObj(ignore -> new Bucket<K, V>())
                .collect(Collectors.toCollection(ArrayList::new));

        return new Buckets<>(buckets);
    }

    void resize(int newSize) {
        Buckets<K, V> resized = of(newSize);

        buckets.stream().map(Bucket::getEntries)
                .flatMap(Collection::stream)
                .forEach(resized::insert);

        this.buckets.clear();
        this.buckets.addAll(resized.buckets);
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

    void insert(MyEntry<K, V> entry) {
        bucket(entry.getKey()).add(entry.getKey(), entry.getValue());
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
