package answers.map

import spock.lang.Specification

class MyHashMapSpecTest extends Specification {

    def map = new MyHashMap()

    def 'initial size should be 0'() {
        expect:
        map.size() == 0
    }

    def 'when entry is put - size should be incremented'() {
        when:
        map.put('a', 'b')

        then:
        map.size() == 1
    }

    def 'initial number of buckets should be 16'() {
        expect:
        map.countBuckets() == 16
    }

    def 'number of buckets after resize should be consecutive power of two'() {
        when:
        (0..<16).each { map.put(it, it) }

        then:
        map.countBuckets() == 32
    }

    def 'number of elements after resize should stay unchanged'() {
        when:
        (0..<16).each { map.put(it, it) }

        then:
        map.size() == 16
    }

    def 'elements after resize should stay unchanged'() {
        when:
        (0..<16).each { map.put(it, it) }

        then:
        map.get(key) == value

        where:
        key << (0..<16)
        value << (0..<16)
    }
}