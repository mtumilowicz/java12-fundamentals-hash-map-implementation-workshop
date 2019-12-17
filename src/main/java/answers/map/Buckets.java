package answers.map;

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
    private static final int INITIAL_EXPONENT = 4;

    static <V, K> Buckets<K, V> of() {
        return of(INITIAL_EXPONENT);
    }

    static <V, K> Buckets<K, V> of(int numberOfBuckets) {
        var buckets = IntStream.range(0, powerOfTwo(numberOfBuckets))
                .mapToObj(ignore -> new Bucket<K, V>())
                .collect(Collectors.toCollection(ArrayList::new));

        return new Buckets<>(buckets);
    }

    Buckets<K, V> expandByPow2() {
        Buckets<K, V> resized = of(countBuckets() + 1);

        buckets.stream().map(Bucket::getEntries)
                .flatMap(Collection::stream)
                .forEach(resized::insert);

        return resized;
    }

    long countElementsInBuckets() {
        return buckets.stream()
                .map(Bucket::size)
                .mapToLong(Long::longValue)
                .sum();
    }

    void insert(K key, V value) {
        bucket(key).add(key, value);
    }

    V get(K key) {
        return bucket(key).get(key);
    }

    long countElementsInSameBucketAs(K key) {
        return bucket(key).size();
    }

    private void insert(MyEntry<K, V> entry) {
        bucket(entry.getKey()).add(entry);
    }

    boolean shouldBeResized(double LOAD_FACTOR) {
        return countElementsInBuckets() > countBuckets() * LOAD_FACTOR;
    }

    int countBuckets() {
        return buckets.size();
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

    private static int powerOfTwo(int exponent) {
        return 1 << exponent;
    }
}
