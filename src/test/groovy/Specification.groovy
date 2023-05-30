import spock.lang.Specification

class FirstSpecification extends Specification {
    def "one plus one should equal two"() {
        expect:
        1 + 1 == 2
    }

    def "two plus three should equal five"() {
        given:
            int firstNumber = 2
            int secondNumber = 3
        when:
            int result = firstNumber + secondNumber
        then:
            result == 5
    }

    def "should trow index out of bound exception"() {
        given:
            def list = [1, 2, 3, 4]
        when:
            list.get(5)
        then:
            thrown(IndexOutOfBoundsException)
            list.size() == 4
    }

    def "numbers to the power of two"(int a, int b) {
        expect:
            Math.pow(a, 2) == b

        where:
            a | b
            2 | 4
            3 | 9
            4 | 16
            5 | 25
    }


}