import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@EqualsAndHashCode(of = {"key", "value"})
@ToString
@AllArgsConstructor
class Entry<K, V> {
    final K key;
    V value;
    Entry<K, V> next;

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Entry<K, V> getNext() {
        return next;
    }

    public Optional<Entry<K, V>> find(K key) {
        var bucket = this;
        while (bucket != null) {
            if (key == bucket.key) {
                break;
            }
            bucket = bucket.next;
        }

        return Optional.ofNullable(bucket);
        /*
                return Stream.iterate(this, n -> nonNull(n.next), n -> n.next).findAny();
         */
    }

    public long size() {
        return Stream.iterate(this, Objects::nonNull, x -> x.next)
                .count();
    }
}