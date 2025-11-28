package store.domain.model

data class OrderItem(
    val product: Product,
    val count: Int
) {
    val totalPrice get() = product.price * count
}
