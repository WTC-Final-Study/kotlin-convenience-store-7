package store.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class ProductManagerTest {

    @Test
    fun `상품 파일 로드`() {
        assertDoesNotThrow {
            val productManager = ProductManager()
        }
    }
}