package store.data.local.entity

data class ProductEntity(
    val name: String,
    val price: Int,
    var promotion_quantity: Int = 0,
    var general_quantity: Int = 0,
    val promotion: String? = null
)
