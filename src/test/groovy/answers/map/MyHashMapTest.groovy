package answers.map

import spock.lang.Specification

class MyHashMapTest extends Specification {

    def map = new MyHashMap()

    def 'put(null, null) is allowed'() {
        when:
        map.put(null, null)

        then:
        map.size() == 1
    }

    def 'put(null, non null) is allowed'() {
        when:
        map.put(null, 'a')

        then:
        map.size() == 1
    }

    def 'put(non null, null) is allowed'() {
        when:
        map.put('a', null)

        then:
        map.size() == 1
    }

    def 'put(non null, non null) is allowed'() {
        when:
        map.put('a', 'a')

        then:
        map.size() == 1
    }

    def 'put keys with same hash'() {
        expect:
        'Aa'.hashCode() == 'BB'.hashCode()

        when:
        map.put('Aa', 'Aa')
        map.put('BB', 'BB')

        then:
        map.size() == 2
    }

    def 'get(existing non null key) should retrieve corresponding value'() {
        when:
        map.put('a', 'a')

        then:
        map.get('a') == 'a'
    }

    def 'get(existing null key) should retrieve corresponding value'() {
        when:
        map.put(null, 'a')

        then:
        map.get(null) == 'a'
    }

    def 'get(non existing key) should return null'() {
        expect:
        map.size() == 0
        map.get('a') == null
    }

    def 'get entries when the keys have same hash'() {
        expect:
        'Aa'.hashCode() == 'BB'.hashCode()

        when:
        map.put('Aa', 'Aa')
        map.put('BB', 'BB')

        then:
        map.get('Aa') == 'Aa'
        map.get('BB') == 'BB'
    }

    def 'put(key, value) and key already exists in map - the entry should be replaced'() {
        given:
        map.put('a', 'a')

        when:
        map.put('a', 'b')

        then:
        map.size() == 1
        map.get('a') == 'b'
    }

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