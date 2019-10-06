import spock.lang.Specification

class MyMapTest extends Specification {

    def map = new MyMap<String, String>()

    def 'put null key and null value'() {
        when:
        map.put(null, null)

        then:
        map.size() == 1
        map.get(null) == null
    }

    def 'put with same hash'() {
        when:
        map.put('Aa', 'Aa')
        map.put('BB', 'BB')

        then:
        map.size() == 2
        map.get('Aa') == 'Aa'
        map.get('BB') == 'BB'
        map.getBucket('Aa').size() == 2
    }

    def 'put non null key'() {
        when:
        map.put('a', 'a')

        then:
        map.size() == 1
    }

    def 'put null key'() {
        when:
        map.put(null, 'a')

        then:
        map.size() == 1
    }

    def 'get existing non null key'() {
        when:
        map.put('a', 'a')

        then:
        map.size() == 1
        map.get('a') == 'a'
    }

    def 'get existing null key'() {
        when:
        map.put(null, 'a')

        then:
        map.size() == 1
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
        map.getBucketsSize() == 16
    }

    def 'hash of null'() {
        expect:
        map.hash(null) == 0
    }
}