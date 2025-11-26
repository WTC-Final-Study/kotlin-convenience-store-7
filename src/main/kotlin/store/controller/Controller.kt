package store.controller

import store.domain.service.StorageService
import store.presentation.mapper.UiMapper.toUiList
import store.view.OutputView

class Controller{

    fun run() {
        val storageService = StorageService()
        val productStocks = storageService.getProductStocks()
        OutputView.displayStock(productStocks.toUiList())
    }
}
