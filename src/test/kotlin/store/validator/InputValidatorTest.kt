package store.validator

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InputValidatorTest {

    @Test
    fun `주문 형식이 올바르지 않으면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            InputValidator.validateOrders("dsd2")
        }
    }

    @Test
    fun `괄호가 제대로 구성되지 않으면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            InputValidator.validateOrders("[콜라-1")
        }
    }

    @Test
    fun `메뉴와 수량이 구분되지 않으면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            InputValidator.validateOrders("[콜라2],[사이다-3]")
        }
    }
}