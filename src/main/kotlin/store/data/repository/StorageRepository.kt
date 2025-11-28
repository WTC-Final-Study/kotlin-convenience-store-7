package store.data.repository

import store.data.local.Storage
import store.data.mapper.EntityMapper.toDomain
import store.domain.model.ProductStock

class StorageRepository(
    private val storage: Storage = Storage
) {

    fun getProductStocks(): List<ProductStock> {
        val promotions = storage.promotions.map { it.toDomain() }
        val productEntities = storage.products

        return productEntities.map { entity ->
            val promotion = promotions.find { it.name == entity.promotion }
            entity.toDomain(promotion)
        }
    }

    fun updateStock(name: String, promoDelta: Int, generalDelta: Int) {
        val productEntity = storage.products.find { it.name == name } ?: return
        productEntity.promotion_quantity += promoDelta
        productEntity.general_quantity += generalDelta
    }
}
