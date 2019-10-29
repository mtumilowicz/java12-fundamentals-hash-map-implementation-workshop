package answers.map;

public interface MyMap<K, V> {
    void put(K key, V value);
    V get(K key);

    static <K, V> MyMap<K, V> create() {
        return new MyHashMap<>();
    }
}
