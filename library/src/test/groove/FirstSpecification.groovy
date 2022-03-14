import spock.lang.Specification

class FirstSpecification extends Specification {
    def "one plus one should equal two"() {
        expect:
        1 + 1 == 2
    }

    def "two plus two should equal four"() {
        given:
        int left = 2
        int right = 2

        when:
        int result = left + right

        then:
        result == 4
    }

    def "Should be able to remove from list"() {
        given:
        def list = [1, 2, 3, 4]

        when:
        list.remove(0)

        then:
        list == [2, 3, 4]
    }

    def "Should get an index out of bounds when removing a non-existent item"() {
        given:
        def list = [1, 2, 3, 4]

        when:
        list.remove(20)

        then:
        thrown(IndexOutOfBoundsException)
        list.size() == 4
    }

    def "numbers to the power of two"(int a, int b, int c) {
        expect:
        Math.pow(a, b) == c

        where:
        a | b | c
        1 | 2 | 1
        2 | 2 | 4
        3 | 2 | 9
    }

    def "mock"(){
        PaymentGateway paymentGateway = Mock()
        // def paymentGateway = Mock(PaymentGateway)
        when:
        def result = paymentGateway.makePayment(12.99)

        then:
        //会返回默认值
        result == false
    }

    def "stub"(){
        PaymentGateway paymentGateway = Mock()
        given:
        paymentGateway.makePayment(20) >> true
        // if we stopped caring about our method argument and always wanted to return true, we could just use an underscore
        // paymentGateway.makePayment(_) >> true
        // If we wanted to alternate between different responses, we could provide a list, for which each element will be returned in sequence
        // paymentGateway.makePayment(_) >>> [true, true, false, true]

        when:
        def result = paymentGateway.makePayment(20)

        then:
        result == true
    }

    def "Should verify notify was called"() {
        given:
        def notifier = Mock(Notifier)

        when:
        notifier.notify('foo')

        then:
        1 * notifier.notify('foo')
        // 1 * notifier.notify(_)
    }

    interface PaymentGateway{
        boolean makePayment(BigDecimal bigDecimal)
    }

    interface Notifier {
        void notify(String message)
    }
}