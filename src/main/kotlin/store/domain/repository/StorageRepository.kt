package store.domain.repository

import store.domain.model.Product
import store.domain.model.ProductStock

interface StorageRepository {

    fun getProductStocks(): List<ProductStock>
}
