package store.controller

import store.common.Constants.ACCEPT
import store.common.Constants.REJECT
import store.common.ErrorType

object InputParser {
    private val ORDER_REGEX =
        Regex("(^(\\[[A-Za-z가-힣0-9]+-\\d+])(,\\[[A-Za-z가-힣0-9]+-\\d+])*$)|(^[A-Za-z가-힣0-9]+-\\d+$)")

    fun parsePurchaseItem(input: String): Map<String, Int> {
        require(input.isNotBlank()) { ErrorType.INVALID_PURCHASE_FORMAT }
        require(input.replace(" ", "").matches(ORDER_REGEX)) { ErrorType.INVALID_PURCHASE_FORMAT }

        return input.split(",")
            .map { token ->
                val cleaned = token.trim().removeSurrounding("[", "]")
                val (name, countStr) = cleaned.split("-").map { it.trim() }
                val count = requireNotNull(countStr.toIntOrNull()) { ErrorType.INVALID_PURCHASE_FORMAT }
                name to count
            }.groupingBy { it.first }.fold(0) { acc, element -> acc + element.second }
    }

    fun parseYesOrNo(input: String): Boolean {
        require(input.isNotBlank()) { ErrorType.INVALID_INPUT }

        val parsed = input.trim().lowercase()
        require(parsed == ACCEPT || parsed == REJECT) { ErrorType.INVALID_INPUT }

        return parsed == ACCEPT
    }
}
