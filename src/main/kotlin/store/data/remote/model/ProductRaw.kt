package store.data.remote.model

data class ProductRaw(
    val name: String,
    val price: Int,
    val quantity: Int,
    val promotion: String?
)
