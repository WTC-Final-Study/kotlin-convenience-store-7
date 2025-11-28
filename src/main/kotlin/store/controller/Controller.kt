package store.controller

import store.common.ErrorType
import store.domain.model.OrderItem
import store.domain.service.PurchaseService
import store.domain.service.StorageService
import store.presentation.mapper.UiMapper.toUiList
import store.view.InputView
import store.view.OutputView

class Controller {

    private lateinit var purchaseService: PurchaseService
    fun run() {
        val storageService = StorageService()
        purchaseService = PurchaseService(storageService)
        val productStocks = storageService.getProductStocks()
        OutputView.displayStock(productStocks.toUiList())
        purchase()
    }

    private fun purchase() {
        OutputView.displayPurchasePrompt()
        val orderItems = getOrderItems()
        handleMoreBenefit(orderItems)
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

            if (!confirmExtraPromotion(item.name, extraCount)) continue
            purchaseService.addExtraItem(item.name, extraCount)
        }
    }

    private fun confirmExtraPromotion(name: String, extra: Int): Boolean {
        OutputView.displayExtraPrompt(name, extra)
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
