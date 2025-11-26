package store.presentation.mapper

import store.domain.model.Product
import store.domain.model.ProductStock
import store.presentation.model.ProductUi

object UiMapper {

    fun List<ProductStock>.toUiList(): List<ProductUi> {
        val result = mutableListOf<ProductUi>()

        for (stock in this) {
            val product = stock.product
            stock.promotion?.let {
                result.add(ProductUi(createLabel(product, stock.promotionQuantity, stock.promotion.name)))
            }
            result.add(ProductUi(createLabel(product, stock.generalQuantity)))
        }
        return result
    }

    private fun createLabel(product: Product, quantity: Int, promotion: String? = null): String {
        val quantityLabel = if (quantity > 0) "${quantity}개" else "재고 없음"
        val promotionLabel = promotion?.let { " $it" } ?: ""
        val priceLabel = "%,d".format(product.price)

        return "${product.name} ${priceLabel}원 ${quantityLabel}${promotionLabel}"
    }
}
