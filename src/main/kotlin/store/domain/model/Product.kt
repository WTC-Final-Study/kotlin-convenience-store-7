package store.domain.model

data class Product(
    val name: String,
    val price: Int,
    val promotion: Promotion? = null,
    val promotionQuantity: Int = 0,
    val generalQuantity: Int = 0
) {
    val totalQuantity get() = promotionQuantity + generalQuantity
}
