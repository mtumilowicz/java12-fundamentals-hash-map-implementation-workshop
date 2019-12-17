package answers.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Optional;

@ToString
@RequiredArgsConstructor
@Getter
class Bucket<K, V> {
    private final LinkedList<MyEntry<K, V>> entries = new LinkedList<>();

    void add(K key, V value) {
        entries.removeIf(MyEntry.hasKey(key));
        entries.add(MyEntry.of(key, value));
    }

    void add(MyEntry<K, V> entry) {
        add(entry.getKey(), entry.getValue());
    }

    V get(K key) {
        return getEntryByKey(key)
                .map(MyEntry::getValue)
                .orElse(null);
    }

    private Optional<MyEntry<K, V>> getEntryByKey(K key) {
        return entries.stream()
                .filter(MyEntry.hasKey(key))
                .findFirst();
    }

    long size() {
        return entries.size();
    }
}
