package store.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import store.model.Order

class ProductManagerTest {

    @Test
    fun `상품 파일 로드`() {
        assertDoesNotThrow {
            val productManager = ProductManager()
        }
    }

    @Test
    fun `존재하지 않는 메뉴를 주문하면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            val productManager = ProductManager()
            val orders = listOf(Order("테스트", 3))
            productManager.checkOrder(orders)
        }
    }

    @Test
    fun `수량을 잘못 입력하면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            val productManager = ProductManager()
            val orders = listOf(Order("콜라", -5))
            productManager.checkOrder(orders)
        }
    }

    @Test
    fun `수량이 재고보다 많으면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            val productManager = ProductManager()
            val orders = listOf(Order("콜라", 100))
            productManager.checkOrder(orders)
        }
    }
}