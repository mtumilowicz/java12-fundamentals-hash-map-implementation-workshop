package answers.map

import spock.lang.Specification

class BucketsSpecTest extends Specification {

    def buckets = Buckets.of()

    def 'initial number of buckets should be 16'() {
        expect:
        buckets.countBuckets() == 16
    }

    def 'number of buckets after resize should be consecutive power of two'() {
        when:
        def resized = buckets.expandByPow2()

        then:
        resized.countBuckets() == 32
    }

    def 'entries with same hash should reside in same bucket'() {
        expect:
        'Aa'.hashCode() == 'BB'.hashCode()

        when:
        buckets.insert('Aa', 'a')
        buckets.insert('BB', 'b')

        then:
        buckets.countElementsInSameBucketAs('Aa') == 2
    }
}
