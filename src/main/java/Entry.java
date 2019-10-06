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
}