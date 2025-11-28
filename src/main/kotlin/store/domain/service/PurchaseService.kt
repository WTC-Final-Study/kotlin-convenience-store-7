package store.domain.service

import store.common.ErrorType
import store.domain.model.OrderItem
import store.domain.model.ProductStock
import store.domain.model.Promotion
import java.time.LocalDate

class PurchaseService(
    private val storageService: StorageService = StorageService(),
    private val today: LocalDate = LocalDate.now()
) {
    private val productStocks = storageService.getProductStocks()
    private val orderItems: MutableList<OrderItem> = mutableListOf()

    fun order(items: List<OrderItem>): List<OrderItem> {
        items.groupBy { it.name }
            .forEach { (name, order) ->
                val total = order.sumOf { it.count }
                orderItems.add(OrderItem(name, total))
            }
        validateOrderItems()
        return orderItems
    }

    private fun validateOrderItems() {
        orderItems.forEach { (name, count) ->
            require(count > 0) { ErrorType.INVALID_PURCHASE_FORMAT }

            val product = productStocks.find { it.product.name == name }
            require(product != null) { ErrorType.PRODUCT_NOT_FOUND }
            require(product.isEnough(count)) { ErrorType.OUT_OF_STOCK_AMOUNT }
        }
    }

    fun extraCountForPromotion(item: OrderItem): Int {
        val productStock = productStocks.find { it.product.name == item.name } ?: return 0
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
        val idx = orderItems.indexOfFirst { it.name == name }
        if (idx < 0) return

        val prevCount = orderItems[idx].count
        orderItems[idx] = orderItems[idx].copy(count = prevCount + extra)
    }
}
