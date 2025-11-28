package store.data.remote.model

data class PromotionRaw(
    val name: String,
    val buy: Int,
    val get: Int,
    val start_date: String,
    val end_date: String
)
