package store.domain.service

import store.common.ErrorType
import store.domain.model.OrderItem
import store.domain.model.Product
import store.domain.model.ProductStock
import store.domain.model.Promotion
import java.time.LocalDate

class PurchaseService(
    private val storageService: StorageService = StorageService(),
    private val today: LocalDate = LocalDate.now()
) {
    private val productStocks = storageService.getProductStocks()
    private val orderItems: MutableList<OrderItem> = mutableListOf()

    fun order(items: Map<String, Int>): List<OrderItem> {
        items.forEach { (name, count) ->
            orderItems.add(
                OrderItem(
                    findProduct(name, count), count
                )
            )
        }
        return orderItems
    }

    private fun findProduct(name: String, count: Int): Product {
        val productStock = productStocks.find { it.product.name == name }
        require(productStock != null) { ErrorType.PRODUCT_NOT_FOUND }
        require(productStock.isEnough(count)) { ErrorType.OUT_OF_STOCK_AMOUNT }
        require(count > 0) { ErrorType.INVALID_PURCHASE_FORMAT }

        return productStock.product
    }

    fun extraCountForPromotion(item: OrderItem): Int {
        val productStock = productStocks.find { it.product.name == item.product.name } ?: return 0
        val promotion = productStock.promotion ?: return 0
        if (!promotion.isActive(today)) return 0

        return calculateExtraCount(productStock, item.count, promotion)
    }

    private fun calculateExtraCount(productStock: ProductStock, orderCount: Int, promotion: Promotion): Int {
        val promotionSetSize = promotion.buy + promotion.get
        val remainder = orderCount % promotionSetSize
        if (remainder == 0) return 0

        val extra = promotionSetSize - remainder
        return if (extra + orderCount > productStock.promotionQuantity) 0 else extra
    }

    fun addExtraItem(name: String, extra: Int) {
        val idx = orderItems.indexOfFirst { it.product.name == name }
        if (idx < 0) return

        val prevCount = orderItems[idx].count
        orderItems[idx] = orderItems[idx].copy(count = prevCount + extra)
    }

    fun getNonPromotionCount(item: OrderItem): Int {
        val productStock = productStocks.find { it.product.name == item.product.name } ?: return 0
        val promotion = productStock.promotion ?: return 0
        if (!promotion.isActive(today)) return 0

        return calculateNonPromotionCount(item.count, promotion, productStock.promotionQuantity)
    }

    private fun calculateNonPromotionCount(
        orderCount: Int,
        promotion: Promotion,
        promotionQuantity: Int
    ): Int {
        if (orderCount <= promotionQuantity) return 0

        val promotionSetSize = promotion.buy + promotion.get

        val maxSetByOrder = orderCount / promotionSetSize
        val maxSetByQuantity = promotionQuantity / promotionSetSize

        if (maxSetByQuantity >= maxSetByOrder) return 0

        return orderCount - maxSetByQuantity * promotionSetSize
    }

    fun cancelOrder(name: String, count: Int) {
        val idx = orderItems.indexOfFirst { it.product.name == name }
        if (idx < 0) return

        val updated = orderItems[idx].count - count
        if (updated <= 0) {
            orderItems.removeAt(idx)
            return
        }
        orderItems[idx] = orderItems[idx].copy(count = updated)
    }
}
