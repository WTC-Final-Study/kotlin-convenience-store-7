package store.controller

import store.constant.InputMessage
import store.constant.OutputMessage
import store.model.Order
import store.model.Product
import store.model.Promotion
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
            val promotionName = productManager.getPromotionName(productId)
            if(promotionManager.isPromotionDay(promotionName)) {
                val promotion = promotionManager.getPromotion(promotionName)
                progressPromotion(productManager, productId, order, promotion)
            }
        }
    }

    private fun progressPromotion(productManager: ProductManager,
                                  productIds: List<String>,
                                  order: Order,
                                  promotion: Promotion?) {
        //남아있는 프로모션 재고 확인하기
        val product = productManager.getProductHasPromotion(productIds)
        val promotionQuantity = product!!.quantity
        //구입 , 프로모션 제품 개수 계산하기
        val free = order.quantity / (promotion!!.buy + promotion.get) * promotion.get
        val buy = order.quantity - free
        //증정 여부 확인
        //증정 불가능 시 정가 결제 여부 입력
        //증정 가능 시 수량 추가 여부 입력
        //딱 떨어질 시 증정
        //증정 품목 정리
    }
}