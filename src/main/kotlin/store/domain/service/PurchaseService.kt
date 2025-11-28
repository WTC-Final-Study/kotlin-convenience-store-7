package store.domain.service

import store.common.ErrorType
import store.domain.model.OrderItem
import store.domain.model.Product
import store.domain.model.ProductStock
import store.domain.model.SoldProduct
import store.domain.model.Promotion
import store.domain.model.Receipt
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

    fun createReceipt(hasMembershipDiscount: Boolean): Receipt {
        val freeItems = getPromotionFreeItems()

        return Receipt(
            orderItems = orderItems,
            promotionItems = freeItems,
            hasMembershipDiscount = hasMembershipDiscount
        )
    }

    private fun getPromotionFreeItems(): List<OrderItem> {
        return orderItems.mapNotNull { item ->
            val productStock = productStocks.find { it.product.name == item.product.name } ?: return@mapNotNull null
            val promotion = productStock.promotion ?: return@mapNotNull null

            if (!promotion.isActive(today)) return@mapNotNull null

            val freeItemCount = getFreeItemCount(promotion, item, productStock.promotionQuantity)
            if (freeItemCount == 0) return@mapNotNull null
            OrderItem(item.product, freeItemCount)
        }
    }

    private fun getFreeItemCount(promotion: Promotion, item: OrderItem, promotionQuantity: Int): Int {
        val promotionSetSize = promotion.buy + promotion.get

        val maxSetByOrder = item.count / promotionSetSize
        val maxSetByQuantity = promotionQuantity / promotionSetSize

        val appliedSet = minOf(maxSetByOrder, maxSetByQuantity)

        return appliedSet * promotion.get
    }

    fun completePurchase() {
        val soldItems = orderItems.mapNotNull { item ->
            val stock = productStocks.find { it.product.name == item.product.name } ?: return@mapNotNull null

            calculateUsage(item, stock)
        }
        storageService.updateStorage(soldItems)
    }

    private fun calculateUsage(sold: OrderItem, stock: ProductStock): SoldProduct {
        val (usedPromotion, usedGeneral) =
            if (isInPromotion(stock.promotion)) calculateUsageWithPromotionPriority(sold.count, stock)
            else calculateUsageWithGeneralPriority(sold.count, stock)

        return SoldProduct(
            product = sold.product,
            usedPromotion = usedPromotion,
            usedGeneral = usedGeneral
        )
    }

    private fun calculateUsageWithPromotionPriority(
        count: Int,
        stock: ProductStock
    ): Pair<Int, Int> {
        val promo = (count).coerceAtMost(stock.promotionQuantity)
        val general = count - promo
        return promo to general
    }

    private fun calculateUsageWithGeneralPriority(
        count: Int,
        stock: ProductStock
    ): Pair<Int, Int> {
        val general = (count).coerceAtMost(stock.generalQuantity)
        val promo = count - general
        return promo to general
    }

    private fun isInPromotion(promotion: Promotion?): Boolean {
        return promotion?.isActive(today) ?: false
    }
}
