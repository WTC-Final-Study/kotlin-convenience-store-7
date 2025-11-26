package store.data.remote

import store.data.local.Storage

class StorageInitializer(
    private val dataSource: FileDataSource
) {
    fun init() {
        val products = dataSource.loadProductData()
        val promotion = dataSource.loadPromotionData()
        Storage.products.addAll(products)
        Storage.promotions.addAll(promotion)
    }
}
