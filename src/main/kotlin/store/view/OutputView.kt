package store.view

import store.presentation.model.ProductUi

object OutputView {

    fun displayStock(products: List<ProductUi>) {
        println(OutputMessage.WELCOME)
        products.forEach {
            println("- ${it.label}")
        }
        println()
    }
}
