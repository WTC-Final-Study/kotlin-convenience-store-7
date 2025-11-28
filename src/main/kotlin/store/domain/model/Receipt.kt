package store.domain.model

data class Receipt(
    val orderItems: List<OrderItem>,
    val promotionItems: List<OrderItem>,
    val hasMembershipDiscount: Boolean
) {
    val totalCount get() = orderItems.sumOf { it.count }
    val totalPrice get() = orderItems.sumOf { it.totalPrice }

    val promotionDiscount get() = promotionItems.sumOf { it.product.price * it.count }
    val membershipDiscount: Int
        get() = if (!hasMembershipDiscount) 0
        else ((totalPrice - promotionDiscount) * 30 / 100).coerceAtMost(8000)

    val finalAmount get() = totalPrice - promotionDiscount - membershipDiscount
}
