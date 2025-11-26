package store.domain.repository

import store.domain.model.Product
import store.domain.model.Promotion

interface StorageRepository {

    fun getProducts(): List<Product>

    fun updateProduct(sold: List<Product>)
}
