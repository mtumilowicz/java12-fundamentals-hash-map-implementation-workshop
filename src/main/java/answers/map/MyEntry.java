package answers.map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Getter
class MyEntry<K, V> {
    private final K key;
    private V value;

    static <K, V> Consumer<MyEntry<K, V>> setValue(V value) {
        return entry -> entry.value = value;
    }

    static <K, V> Predicate<MyEntry<K, V>> hasKey(K key) {
        return entry -> Objects.equals(entry.key, key);
    }
}