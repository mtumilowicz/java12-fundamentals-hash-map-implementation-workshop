import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@EqualsAndHashCode(of = {"key", "value"})
@ToString(of = {"key", "value"})
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
    }
}