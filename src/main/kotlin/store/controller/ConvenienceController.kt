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
    }

    private fun startMessage(inventory: List<Product>) {
        OutputView.printMessage(OutputMessage.GREETING.toString())
        OutputView.printInventory(inventory)
    }

    private fun inputOrder(): List<Order> {
        val orders = mutableListOf<Order>()
        val rawOrder = InputView.input(InputMessage.ORDER.toString())
        InputValidator.validateOrders(rawOrder)
        return orders
    }
}