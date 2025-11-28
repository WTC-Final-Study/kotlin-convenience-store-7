package store.data.mapper

import store.common.DateMapper.toLocalDate
import store.data.local.Storage
import store.data.local.entity.ProductEntity
import store.data.local.entity.PromotionEntity
import store.domain.model.Product
import store.domain.model.ProductStock
import store.domain.model.Promotion

object EntityMapper {

    fun PromotionEntity.toDomain(): Promotion {
        return Promotion(
            name = name,
            buy = buy,
            get = get,
            startDate = start_date.toLocalDate(),
            endDate = end_date.toLocalDate()
        )
    }

    fun ProductEntity.toDomain(promotion: Promotion?): ProductStock {
        return ProductStock(
            product = Product(name, price),
            promotionQuantity = promotion_quantity,
            generalQuantity = general_quantity,
            promotion = promotion
        )
    }
}
