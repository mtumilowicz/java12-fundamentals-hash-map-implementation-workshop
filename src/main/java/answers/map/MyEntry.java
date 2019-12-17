package answers.map;

import lombok.Value;

import java.util.Objects;
import java.util.function.Predicate;

@Value
class MyEntry<K, V> {
    K key;
    V value;

    static <K, V> Predicate<MyEntry<K, V>> hasKey(K key) {
        return entry -> Objects.equals(entry.key, key);
    }

    static <K, V> MyEntry<K, V> of(K key, V value) {
        return new MyEntry<>(key, value);
    }
}