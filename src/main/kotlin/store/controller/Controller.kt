package store.controller

import store.common.ErrorType
import store.domain.model.OrderItem
import store.domain.service.StorageService
import store.presentation.mapper.UiMapper.toUiList
import store.view.InputView
import store.view.OutputView

class Controller {

    fun run() {
        val storageService = StorageService()
        val productStocks = storageService.getProductStocks()
        OutputView.displayStock(productStocks.toUiList())
        purchase()
    }

    fun purchase(): List<OrderItem> {
        OutputView.displayPurchasePrompt()
        while (true) {
            try {
                val input = InputView.read()

                return InputParser.parsePurchaseItem(input)
            } catch (e: IllegalArgumentException) {
                OutputView.displayError(e.message ?: ErrorType.UNKNOWN.toString())
            }
        }
    }
}
