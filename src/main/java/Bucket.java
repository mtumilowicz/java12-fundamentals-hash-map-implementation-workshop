import lombok.ToString;

import java.util.LinkedList;

@ToString
public class Bucket<K, V> {
    LinkedList<Entry<K, V>> entries = new LinkedList<>();

    void add(K key, V value) {
        entries.stream()
                .filter(Entry.byKey(key))
                .findFirst()
                .ifPresentOrElse(Entry.setValue(value), () -> entries.add(new Entry<>(key, value)));
    }

    V get(K key) {
        return entries.stream()
                .filter(Entry.byKey(key))
                .findFirst()
                .map(Entry::getValue)
                .orElse(null);
    }

    public long size() {
        return entries.size();
    }
}
