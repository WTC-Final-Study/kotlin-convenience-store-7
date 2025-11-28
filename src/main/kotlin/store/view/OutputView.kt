package store.view

import store.domain.model.OrderItem
import store.domain.model.Receipt
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

    fun displayReceipt(receipt: Receipt) {
        println(OutputMessage.RECEIPT_HEADER_STORE)
        displayOrderItems(receipt.orderItems)
        displayFreeItems(receipt.promotionItems)
        displayTotal(receipt)
    }

    private fun displayOrderItems(orderItems: List<OrderItem>) {
        println(OutputMessage.RECEIPT_BODY_LABEL)
        orderItems.forEach { item ->
            println(OutputMessage.RECEIPT_ITEM_WITH_PRICE.format(item.product.name, item.count, item.totalPrice))
        }
    }

    private fun displayFreeItems(promotionItems: List<OrderItem>) {
        println(OutputMessage.RECEIPT_HEADER_FREE)
        promotionItems.forEach { item ->
            println(OutputMessage.RECEIPT_FREE_ITEM.format(item.product.name, item.count))
        }
    }

    private fun displayTotal(receipt: Receipt) {
        println(OutputMessage.RECEIPT_DIVIDER)
        println(OutputMessage.RECEIPT_TOTAL_PRICE.format(receipt.totalCount, receipt.totalPrice))
        println(OutputMessage.RECEIPT_DISCOUNT.format("행사할인", receipt.promotionDiscount))
        println(OutputMessage.RECEIPT_DISCOUNT.format("멤버십할인", receipt.membershipDiscount))
        println(OutputMessage.RECEIPT_FINAL_AMOUNT.format(receipt.finalAmount))
    }

    fun displayContinuePurchasePrompt() {
        println(OutputMessage.CONTINUE_PURCHASE_CONFIRM)
    }
}
