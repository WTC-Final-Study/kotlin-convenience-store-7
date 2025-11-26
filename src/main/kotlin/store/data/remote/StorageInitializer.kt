package store.data.remote

import store.data.local.Storage
import store.data.local.entity.ProductEntity
import store.data.local.entity.PromotionEntity
import store.data.remote.model.ProductRaw
import store.data.remote.model.PromotionRaw

class StorageInitializer(
    private val dataSource: FileDataSource,
    private val storage: Storage
) {
    fun init() {
        initPromotion()
        initProduct()
    }

    private fun initPromotion() {
        val promotionRaws = dataSource.loadPromotionData()

        val promotionEntities = promotionRaws.map { it.toEntity() }
        storage.promotions.addAll(promotionEntities)
    }

    private fun initProduct() {
        val productRaws = dataSource.loadProductData()

        val productEntities = productRaws
            .groupBy { it.name }
            .map { (name, raws) ->
                toProductEntity(name, raws)
            }
        storage.products.addAll(productEntities)
    }

    private fun toProductEntity(name: String, raws: List<ProductRaw>): ProductEntity {
        val promotion = raws.find { it.promotion != null }?.promotion
        val promotionQuantity = raws.filter { it.promotion != null }.sumOf { it.quantity }
        val generalQuantity = raws.filter { it.promotion == null }.sumOf { it.quantity }
        return ProductEntity(
            name = name,
            price = raws.first().price,
            promotion_quantity = promotionQuantity,
            general_quantity = generalQuantity,
            promotion = promotion
        )
    }

    private fun PromotionRaw.toEntity(): PromotionEntity {
        return PromotionEntity(
            name = name,
            buy = buy,
            get = get,
            start_date = start_date,
            end_date = end_date
        )
    }
}
