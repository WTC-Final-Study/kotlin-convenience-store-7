package store.controller

import store.service.ProductManager
import store.service.PromotionManager

class ConvenienceController {
    fun run() {
        val productManager = ProductManager()
        val promotionManager = PromotionManager()
    }
}