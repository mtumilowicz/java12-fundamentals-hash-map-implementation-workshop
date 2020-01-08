package answers.map

import spock.lang.Specification

class MyHashMapFunctionalTest extends Specification {

    MyMap map = MyMap.create()

    def 'entry (null, null) could be added and is accessible'() {
        when:
        map.put(null, null)

        then:
        map.get(null) == null
    }

    def 'entry (null, non null) could be added and is accessible'() {
        when:
        map.put(null, 'a')

        then:
        map.get(null) == 'a'
    }

    def 'entry (non null, null) could be added and is accessible'() {
        when:
        map.put('a', null)

        then:
        map.get('a') == null
    }

    def 'entry (non null, non null) could be added and is accessible'() {
        when:
        map.put('a', 'b')

        then:
        map.get('a') == 'b'
    }

    def 'entries with keys with same hash could be added and are accessible'() {
        expect:
        'Aa'.hashCode() == 'BB'.hashCode()

        when:
        map.put('Aa', 'a')
        map.put('BB', 'b')

        then:
        map.get('Aa') == 'a'
        map.get('BB') == 'b'
    }

    def 'put(key, value) and key already exists in map - the entry should be replaced'() {
        given:
        map.put('a', 'b')

        when:
        map.put('a', 'c')

        then:
        map.get('a') == 'c'
    }
}