package store.controller

import store.domain.model.OrderItem

object InputParser {
    private val ORDER_REGEX =
        Regex("(^(\\[[A-Za-z가-힣0-9]+-\\d+])(,\\[[A-Za-z가-힣0-9]+-\\d+])*$)|(^[A-Za-z가-힣0-9]+-\\d+$)")

    fun parsePurchaseItem(input: String): List<OrderItem> {
        require(input.isNotBlank()) { "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요." }
        require(input.replace(" ", "").matches(ORDER_REGEX)) { "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요." }

        return input.split(",")
            .map { token ->
                val cleaned = token.trim().removeSurrounding("[", "]")
                val parts = cleaned.split("-").map { it.trim() }
                val count = requireNotNull(parts[1].toIntOrNull()) { "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요." }
                OrderItem(parts[0], count)
            }
    }
}
