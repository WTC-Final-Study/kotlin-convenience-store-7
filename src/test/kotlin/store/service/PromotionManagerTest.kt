package store.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class PromotionManagerTest {

    @Test
    fun `프로모션 파일 읽기`() {
        assertDoesNotThrow {
            PromotionManager()
        }
    }
}