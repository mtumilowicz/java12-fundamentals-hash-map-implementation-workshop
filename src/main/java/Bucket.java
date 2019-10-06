import java.util.LinkedList;

public class Bucket<K, V> {
    LinkedList<Entry<K, V>> entries = new LinkedList<>();

    public void add(K key, V value) {
        entries.stream().filter(x -> x.key.equals(key)).findFirst()
                .ifPresentOrElse(x -> x.value = value, () -> entries.add(new Entry<>(key, value, null)));
    }

    public V get(K key) {
        return entries.stream().filter(x -> x.key.equals(key)).findAny().map(x -> x.value).orElse(null);
    }
}
