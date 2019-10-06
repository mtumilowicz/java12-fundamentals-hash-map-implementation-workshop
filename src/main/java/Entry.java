import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@EqualsAndHashCode(of = {"key", "value"})
@ToString
@AllArgsConstructor
@Getter
class Entry<K, V> {
    private final K key;
    private V value;

    static <K, V> Consumer<Entry<K, V>> setValue(V value) {
        return entry -> entry.value = value;
    }

    static <K, V> Predicate<Entry<K, V>> byKey(K key) {
        return entry -> Objects.equals(entry.key, key);
    }
}