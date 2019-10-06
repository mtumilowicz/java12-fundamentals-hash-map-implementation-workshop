import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(of = {"key", "value"})
@ToString
@AllArgsConstructor
class Entry<K, V> {
    final K key;
    V value;
}