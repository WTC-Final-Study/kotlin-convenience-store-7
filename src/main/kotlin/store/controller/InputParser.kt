package store.controller

import store.common.ErrorType
import store.domain.model.OrderItem
import java.util.Locale
import java.util.Locale.getDefault

object InputParser {
    private val ORDER_REGEX =
        Regex("(^(\\[[A-Za-z가-힣0-9]+-\\d+])(,\\[[A-Za-z가-힣0-9]+-\\d+])*$)|(^[A-Za-z가-힣0-9]+-\\d+$)")

    fun parsePurchaseItem(input: String): List<OrderItem> {
        require(input.isNotBlank()) { ErrorType.INVALID_PURCHASE_FORMAT }
        require(input.replace(" ", "").matches(ORDER_REGEX)) { ErrorType.INVALID_PURCHASE_FORMAT }

        return input.split(",")
            .map { token ->
                val cleaned = token.trim().removeSurrounding("[", "]")
                val parts = cleaned.split("-").map { it.trim() }
                val count = requireNotNull(parts[1].toIntOrNull()) { ErrorType.INVALID_PURCHASE_FORMAT }
                OrderItem(parts[0], count)
            }
    }

    fun parseYesOrNo(input: String): Boolean {
        require(input.isNotBlank()) { ErrorType.INVALID_INPUT }

        val parsed = input.trim().lowercase()
        require(parsed == "y" || parsed == "n") { ErrorType.INVALID_INPUT }

        return parsed == "y"
    }
}
