package store.domain.service

import store.data.repository.StorageRepository
import store.domain.model.ProductStock

class StorageService(
    private val storageRepository: StorageRepository = StorageRepository()
) {
    fun getProductStocks(): List<ProductStock> {
        return storageRepository.getProductStocks()
    }
}
