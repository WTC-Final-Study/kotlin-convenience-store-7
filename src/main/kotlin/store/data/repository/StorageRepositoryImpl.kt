package store.data.repository

import store.data.local.Storage
import store.data.mapper.EntityMapper.toDomain
import store.domain.model.Product
import store.domain.model.ProductStock
import store.domain.repository.StorageRepository

class StorageRepositoryImpl(
    private val storage: Storage
) : StorageRepository {

    override fun getProductStocks(): List<ProductStock> {
        val promotions = storage.promotions.map { it.toDomain() }
        val productEntities = storage.products

        return productEntities.map { entity ->
            val promotion = promotions.find { it.name == entity.name }
            entity.toDomain(promotion)
        }
    }
}
