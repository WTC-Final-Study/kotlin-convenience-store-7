package store.domain.model

data class ProductStock(
    val product: Product,
    val promotion: Promotion?,
    val promotionQuantity: Int = 0,
    val generalQuantity: Int = 0
) {
    val totalQuantity get() = promotionQuantity + generalQuantity
}
