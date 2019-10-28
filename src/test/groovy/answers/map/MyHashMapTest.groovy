package answers.map

import spock.lang.Specification

class MyHashMapTest extends Specification {

    def map = new MyHashMap<String, String>()

    def 'put null key and null value'() {
        when:
        map.put(null, null)

        then:
        map.size() == 1
    }

    def 'put null key and non null value'() {
        when:
        map.put(null, 'a')

        then:
        map.size() == 1
    }

    def 'put non null key and null value'() {
        when:
        map.put('a', null)

        then:
        map.size() == 1
    }

    def 'put non null key and non null value'() {
        when:
        map.put('a', 'a')

        then:
        map.size() == 1
    }

    def 'put with same hash'() {
        when:
        map.put('Aa', 'Aa')
        map.put('BB', 'BB')

        then:
        map.size() == 2
        map.get('Aa') == 'Aa'
        map.get('BB') == 'BB'
    }

    def 'get existing non null key'() {
        when:
        map.put('a', 'a')

        then:
        map.get('a') == 'a'
    }

    def 'get existing null key'() {
        when:
        map.put(null, 'a')

        then:
        map.get(null) == 'a'
    }

    def 'get non existing key'() {
        expect:
        map.size() == 0
        map.get('a') == null
    }

    def 'replace'() {
        when:
        map.put('a', 'a')
        map.put('a', 'b')

        then:
        map.size() == 1
        map.get('a') == 'b'
    }

    def 'initial size'() {
        expect:
        map.countBuckets() == 16
    }

    def 'number of buckets after resize should be consecutive power of two'() {
        when:
        (0..<16).collect { it.toString() }
                .each { map.put(it, it) }

        then:
        map.countBuckets() == 32
    }

    def 'number of elements after resize stays unchanged'() {
        when:
        (0..<16).collect { it.toString() }
                .each { map.put(it, it) }

        then:
        map.size() == 16
    }

    def 'elements after resize stays unchanged'() {
        when:
        (0..<16).collect { it.toString() }
                .each { map.put(it, it) }

        then:
        map.get(key) == value

        where:
        key << (0..<16).collect { it.toString() }
        value << (0..<16).collect { it.toString() }
    }
}