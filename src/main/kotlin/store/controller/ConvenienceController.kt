package store.controller

import store.constant.OutputMessage
import store.service.ProductManager
import store.service.PromotionManager
import store.view.OutputView

class ConvenienceController {
    fun run() {
        val productManager = ProductManager()
        val promotionManager = PromotionManager()
        OutputView.printMessage(OutputMessage.GREETING.toString())
    }
}