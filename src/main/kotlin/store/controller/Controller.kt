package store.controller

import store.common.ErrorType
import store.domain.model.OrderItem
import store.domain.model.Receipt
import store.domain.service.PurchaseService
import store.domain.service.StorageService
import store.presentation.mapper.UiMapper.toUiList
import store.view.InputView
import store.view.OutputView

class Controller {

    private lateinit var purchaseService: PurchaseService
    fun run() {
        val storageService = StorageService()
        do {
            purchaseService = PurchaseService(storageService)
            val productStocks = storageService.getProductStocks()
            OutputView.displayStock(productStocks.toUiList())
            purchase()
            OutputView.displayContinuePurchasePrompt()
        } while (askYesOrNo())
    }

    private fun purchase() {
        val orderItems = getOrderItems()
        handleMoreBenefit(orderItems)
        handleNonPromotion(orderItems)
        val hasMembership = confirmMembershipDiscount()
        val receipt = purchaseService.createReceipt(hasMembership)
        showReceipt(receipt)
        purchaseService.completePurchase()
    }

    private fun getOrderItems(): List<OrderItem> {
        OutputView.displayPurchasePrompt()

        return retryUntilValid {
            val input = InputView.read()
            val parsedInput = InputParser.parsePurchaseItem(input)
            purchaseService.order(parsedInput)
        }
    }

    private fun handleMoreBenefit(orderItems: List<OrderItem>) {
        for (item in orderItems) {
            val extraCount = purchaseService.extraCountForPromotion(item)
            if (extraCount == 0) continue

            if (!confirmExtraPromotion(item.product.name, extraCount)) continue
            purchaseService.addExtraItem(item.product.name, extraCount)
        }
    }

    private fun confirmExtraPromotion(name: String, extra: Int): Boolean {
        OutputView.displayExtraPrompt(name, extra)
        return askYesOrNo()
    }

    private fun handleNonPromotion(orderItem: List<OrderItem>) {
        for (item in orderItem) {
            val nonPromotionCount = purchaseService.getNonPromotionCount(item)

            if (nonPromotionCount == 0) continue

            if (confirmNonPromotion(item.product.name, nonPromotionCount)) continue
            purchaseService.cancelOrder(item.product.name, nonPromotionCount)
        }
    }

    private fun confirmNonPromotion(name: String, count: Int): Boolean {
        OutputView.displayNonPromotionPrompt(name, count)
        return askYesOrNo()
    }

    private fun confirmMembershipDiscount(): Boolean {
        OutputView.displayMembershipPrompt()
        return askYesOrNo()
    }

    private fun showReceipt(receipt: Receipt) {
        OutputView.displayReceipt(receipt)
    }

    private fun askYesOrNo(): Boolean {
        return retryUntilValid {
            val input = InputView.read()
            InputParser.parseYesOrNo(input)
        }
    }

    private inline fun <T> retryUntilValid(block: () -> T): T {
        while (true) {
            try {
                return block()
            } catch (e: IllegalArgumentException) {
                OutputView.displayError(e.message ?: ErrorType.UNKNOWN.message)
            }
        }
    }
}
