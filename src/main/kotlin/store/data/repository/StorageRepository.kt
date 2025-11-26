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
            println(promotion)
            entity.toDomain(promotion)
        }
    }
}
