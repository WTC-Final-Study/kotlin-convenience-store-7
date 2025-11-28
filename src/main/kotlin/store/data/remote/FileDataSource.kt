package store.data.remote

import store.common.ErrorType
import store.data.remote.model.ProductRaw
import store.data.remote.model.PromotionRaw

object FileDataSource {
    private const val PRODUCT_PATH = "/products.md"
    private const val PROMOTION_PATH = "/promotions.md"

    fun loadProductData(): List<ProductRaw> {
        val lines = read(PRODUCT_PATH).drop(1)
        return lines
            .filter { it.isNotBlank() }
            .map { line ->
                val info = line.split(",").map { it.trim() }
                ProductRaw(
                    name = info[0],
                    price = info[1].toInt(),
                    quantity = info[2].toInt(),
                    promotion = if (info[3] == "null") null else info[3]
                )
            }
    }

    fun loadPromotionData(): List<PromotionRaw> {
        val lines = read(PROMOTION_PATH).drop(1)
        return lines
            .filter { it.isNotBlank() }
            .map { line ->
                val info = line.split(",").map { it.trim() }
                PromotionRaw(
                    name = info[0],
                    buy = info[1].toInt(),
                    get = info[2].toInt(),
                    start_date = info[3],
                    end_date = info[4]
                )
            }
    }

    private fun read(path: String): List<String> {
        val stream = object {}.javaClass.getResourceAsStream(path)
            ?: throw IllegalArgumentException(ErrorType.NO_RESOURCE.message)
        return stream.bufferedReader().use { it.readLines() }
    }
}
