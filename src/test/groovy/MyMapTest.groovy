import spock.lang.Specification

class MyMapTest extends Specification {

    def map = new MyMap()

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

    def 'replace'() {
        when:
        map.put('a', 'a')
        map.put('a', 'b')

        then:
        map.getBucketsSize() == 16
        map.get('a') == 'b'
    }
}