package store.domain.model

data class Receipt(
    val orderItems: List<OrderItem>,
    val promotionItems: List<OrderItem>,
    val hasMembershipDiscount: Boolean
) {
    val totalPrice get() = orderItems.sumOf { it.product.price * it.count }
    val promotionDiscount get() = promotionItems.sumOf { it.product.price * it.count }
    val membershipDiscount: Int
        get() = ((totalPrice - promotionDiscount) * 30 / 100).coerceAtMost(8000)

    val finalAmount get() = totalPrice - promotionDiscount - membershipDiscount
}
