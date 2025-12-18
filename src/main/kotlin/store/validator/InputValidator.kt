package store.validator

import store.constant.ErrorMessage

object InputValidator {

    fun validateOrders(input: String) {
        val splitOrder = input.split(",")
        splitOrder.forEach { order ->
            require(order.startsWith("[")) { ErrorMessage.INVALID_FORMAT.toString() }
            require(order.endsWith("]")) { ErrorMessage.INVALID_FORMAT.toString() }
            require(order.contains("-")) { ErrorMessage.INVALID_FORMAT.toString() }
            val filterOrder = order.removePrefix("[").removeSuffix("]")
            val quantity = filterOrder.split("-")[1].toIntOrNull()
            require(quantity != null) { ErrorMessage.INVALID_FORMAT.toString() }
        }
    }
}