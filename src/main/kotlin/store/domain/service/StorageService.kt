package store.domain.service

import store.data.repository.StorageRepository
import store.domain.model.ProductStock
import store.domain.model.SoldProduct

class StorageService(
    private val storageRepository: StorageRepository = StorageRepository()
) {
    fun getProductStocks(): List<ProductStock> {
        return storageRepository.getProductStocks()
    }

    fun updateStorage(soldItems: List<SoldProduct>) {
        soldItems.forEach {
            storageRepository.updateStock(it.product.name, -it.usedPromotion, -it.usedGeneral)
        }
    }

}
