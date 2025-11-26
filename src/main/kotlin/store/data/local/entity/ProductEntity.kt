package store.data.local.entity

data class ProductEntity(
    val name: String,
    val price: Int,
    var promotionQuantity: Int,
    var generalQuantity: Int,
    val promotion: String?
)
