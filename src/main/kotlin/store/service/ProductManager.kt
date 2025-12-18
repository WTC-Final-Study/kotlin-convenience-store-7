package store.service

import store.constant.ErrorMessage
import store.model.Order
import store.model.Product
import java.io.File
import java.time.LocalDate
import java.util.UUID

class ProductManager {

    val inventory: MutableMap<String, Product>

    init {
        inventory = loadProducts()
    }

    fun checkOrder(orders: List<Order>) {
        orders.forEach { order ->
            val ids = getIdsByName(order.name)
            require(!ids.isEmpty()) { ErrorMessage.INVALID_MENU.toString() }
            val inventoryQuantity = ids.sumOf { id ->
                getProductById(id).quantity ?: 0
            }
            require(order.quantity >= 0) { ErrorMessage.INVALID_ORDER.toString() }
            require(inventoryQuantity >= order.quantity) { ErrorMessage.MANY_QUANTITY.toString() }
        }
    }

    fun getIdsByName(name: String): List<String> {
        val ids = mutableListOf<String>()
        inventory.forEach { id, product ->
            if(product.name == name) ids.add(id)
        }
        return ids
    }

    fun getProductById(id: String): Product {
        return inventory[id]!!
    }


    private fun loadProducts(): MutableMap<String, Product> {
        val products = mutableMapOf<String, Product>()
        val filePath = "src/main/resources/products.md"
        val file = File(filePath)
        file.readLines().drop(1).forEach { line ->
            val splitLine = line.split(",")
            val id = UUID.randomUUID().toString()
            val product = transformPromotion(splitLine)
            products[id] = product
        }

        return products
    }

    private fun transformPromotion(line: List<String>): Product {
        val name = line[0]
        val price = line[1].toInt()
        val quantity = line[2].toInt()
        val promotion = if(line[3] == "null") null else line[3]

        return Product(name, price, quantity, promotion)
    }
}