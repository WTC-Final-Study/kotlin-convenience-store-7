package store.view

import store.constant.OutputMessage
import store.model.Product
import java.text.DecimalFormat

object OutputView {

    fun printMessage(message: String) {
        println(message)
    }

    fun printInventory(inventory: Map<String, Product>) {
        println(OutputMessage.INVENTORY_TITLE.toString())
        println()
        inventory.forEach { (key, product) ->
            val name = product.name
            val price = numberDecimal(product.price)
            val quantity = if(product.quantity == 0) "재고 없음" else "${product.quantity}개"
            val promotion = if(product.promotion == null) "" else product.promotion
            println("- $name ${price}원 $quantity $promotion")
        }
        println()
    }

    private fun numberDecimal(input: Int): String {
        val decimal = DecimalFormat("#,###")
        return decimal.format(input)
    }

}