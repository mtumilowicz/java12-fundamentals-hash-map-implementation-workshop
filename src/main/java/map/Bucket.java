package map;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;

@ToString
@RequiredArgsConstructor
class Bucket<K, V> {
    final int i;
    final LinkedList<MyEntry<K, V>> entries = new LinkedList<>();

    void add(K key, V value) {
        entries.stream()
                .filter(MyEntry.byKey(key))
                .findFirst()
                .ifPresentOrElse(MyEntry.setValue(value), () -> entries.add(new MyEntry<>(key, value)));
    }

    V get(K key) {
        return entries.stream()
                .filter(MyEntry.byKey(key))
                .findFirst()
                .map(MyEntry::getValue)
                .orElse(null);
    }

    long size() {
        return entries.size();
    }
}
