package store.controller

import store.constant.OutputMessage
import store.model.Product
import store.service.ProductManager
import store.service.PromotionManager
import store.view.OutputView

class ConvenienceController {
    fun run() {
        val productManager = ProductManager()
        val promotionManager = PromotionManager()
        startMessage(productManager.inventory)
    }

    private fun startMessage(inventory: List<Product>) {
        OutputView.printMessage(OutputMessage.GREETING.toString())
        OutputView.printInventory(inventory)
    }
}