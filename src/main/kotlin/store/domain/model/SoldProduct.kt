package store.domain.model

data class SoldProduct(
    val product: Product,
    val usedPromotion: Int,
    val usedGeneral: Int
)
