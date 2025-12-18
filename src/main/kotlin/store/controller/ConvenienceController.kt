package store.controller

import store.constant.InputMessage
import store.constant.OutputMessage
import store.model.Order
import store.model.Product
import store.service.ProductManager
import store.service.PromotionManager
import store.validator.InputValidator
import store.view.InputView
import store.view.OutputView

class ConvenienceController {
    fun run() {
        val productManager = ProductManager()
        val promotionManager = PromotionManager()
        startMessage(productManager.inventory)
        val orders = inputOrder()
        checkMenu(orders, productManager, promotionManager)
    }

    private fun startMessage(inventory: Map<String, Product>) {
        OutputView.printMessage(OutputMessage.GREETING.toString())
        OutputView.printInventory(inventory)
    }

    private fun inputOrder(): List<Order> {
        val orders = mutableListOf<Order>()
        val rawOrder = InputView.input(InputMessage.ORDER.toString())
        InputValidator.validateOrders(rawOrder)
        return orders
    }

    private fun checkMenu(orders: List<Order>,
                          productManager: ProductManager,
                          promotionManager: PromotionManager) {
        productManager.checkOrder(orders)
        checkPromotionDay(orders, productManager, promotionManager)
    }

    private fun checkPromotionDay(
        orders: List<Order>,
        productManager: ProductManager,
        promotionManager: PromotionManager) {
        orders.forEach { order ->
            val productId = productManager.getIdsByName(order.name)
            val promotion = productManager.getPromotionName(productId)
            if(promotionManager.isPromotionDay(promotion)) {
                val product = productManager.getProductById(productId)
                progressPromotion()
            }
        }
    }

    private fun progressPromotion() {

    }
}