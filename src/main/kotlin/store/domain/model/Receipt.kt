package store.domain.model

import store.common.Constants.MEMBERSHIP_DISCOUNT_MAX
import store.common.Constants.MEMBERSHIP_DISCOUNT_RATE

class Receipt(
    val orderItems: List<OrderItem>,
    val promotionItems: List<OrderItem>,
    val membershipBaseAmount: Int
) {
    val totalCount get() = orderItems.sumOf { it.count }
    val totalPrice get() = orderItems.sumOf { it.totalPrice }

    val promotionDiscount get() = promotionItems.sumOf { it.product.price * it.count }
    val membershipDiscount: Int
        get() = (membershipBaseAmount * MEMBERSHIP_DISCOUNT_RATE / 100).coerceAtMost(MEMBERSHIP_DISCOUNT_MAX)

    val finalAmount get() = totalPrice - promotionDiscount - membershipDiscount
}
