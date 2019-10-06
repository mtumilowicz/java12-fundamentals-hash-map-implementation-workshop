import lombok.ToString;

import java.util.ArrayList;
import java.util.stream.Stream;

@ToString(of = "buckets")
public class MyMap<K, V> {
    private ArrayList<Entry<K, V>> buckets;
    private static final int INITIAL_CAPACITY = 1 << 4; // 16

    private int size = 0;

    public MyMap() {
        buckets = new ArrayList<>(INITIAL_CAPACITY);
    }

    public void put(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value, null);

        int bucket = getBucketIndex(key);

        Entry<K, V> existing = buckets.get(bucket);
        if (existing == null) {
            buckets.set(bucket, entry);
            size++;
        } else {
            // compare the keys see if key already exists
            while (existing.next != null) {
                if (existing.key.equals(key)) {
                    existing.value = value;
                    return;
                }
                existing = existing.next;
            }

            if (existing.key.equals(key)) {
                existing.value = value;
            } else {
                existing.next = entry;
                size++;
            }
        }
    }

    public V get(K key) {
        Entry<K, V> bucket = buckets.get(getBucketIndex(key));

        while (bucket != null) {
            if (key == bucket.key) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    private int getBucketIndex(K key) {
        return getHash(key) % getBucketSize();
    }

    public int size() {
        return size;
    }

    private int getBucketSize() {
        return buckets.size();
    }

    private int getHash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }
}