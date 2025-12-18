package store.service

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
            // 상품이 존재하는지, 주문 수량이 재고를 초과하지 않는지 )
        }
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
        val promotion = line[3]

        return Product(name, price, quantity, promotion)
    }
}