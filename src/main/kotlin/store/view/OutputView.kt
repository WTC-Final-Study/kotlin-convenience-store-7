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

    fun displayPurchasePrompt() {
        println(OutputMessage.PURCHASE_INPUT_PROMPT)
    }

    fun displayExtraPrompt(name: String, count: Int) {
        println(OutputMessage.EXTRA_ITEMS_CONFIRM.format(name, count))
    }

    fun displayNonPromotionPrompt(name: String, count: Int) {
        println(OutputMessage.NON_PROMOTION_CONFIRM.format(name, count))
    }

    fun displayMembershipPrompt() {
        println(OutputMessage.MEMBERSHIP_DISCOUNT_CONFIRM)
    }

    fun displayError(message: String) {
        println(message)
    }
}
