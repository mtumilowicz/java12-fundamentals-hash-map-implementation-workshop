import lombok.ToString;

import java.util.LinkedList;
import java.util.Objects;

@ToString
public class Bucket<K, V> {
    LinkedList<Entry<K, V>> entries = new LinkedList<>();

    public void add(K key, V value) {
        entries.stream()
                .filter(x -> Objects.equals(x.key, key))
                .findFirst()
                .ifPresentOrElse(x -> x.value = value, () -> entries.add(new Entry<>(key, value)));
    }

    public V get(K key) {
        return entries.stream()
                .filter(x -> Objects.equals(x.key, key))
                .findFirst()
                .map(x -> x.value)
                .orElse(null);
    }

    public long size() {
        return entries.size();
    }
}
