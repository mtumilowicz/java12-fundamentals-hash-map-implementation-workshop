import java.util.LinkedList;

public class Bucket<K, V> {
    LinkedList<Entry<K, V>> entries = new LinkedList<>();

    public void add(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value, null);

        if (entries.isEmpty()) {
            entries.add(entry);
        } else {
            int i = 0;
            var existing = entries.get(0);
            while (i < entries.size()) {
                if (entries.get(i).key.equals(key)) {
                    entries.get(i).value = value;
                    return;
                }
                existing = existing.next;
                i++;
            }

            if (existing.key.equals(key)) {
                existing.value = value;
            } else {
                existing.next = entry;
            }
        }
    }

    public V get(K key) {
        return entries.stream().filter(x -> x.key.equals(key)).findAny().map(x -> x.value).orElse(null);
    }
}
