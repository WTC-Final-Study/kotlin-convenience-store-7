package store.data.local

import store.data.local.entity.ProductEntity
import store.data.local.entity.PromotionEntity

object Storage {
    val products: MutableList<ProductEntity> = mutableListOf()
    val promotions: MutableList<PromotionEntity> = mutableListOf()
}
