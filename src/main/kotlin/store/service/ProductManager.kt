package store.service

import store.model.Product
import java.io.File

class ProductManager {

    val inventory: List<Product>

    init {
        inventory = loadProducts()
    }

    private fun loadProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val filePath = "src/main/resources/products.md"
        val file = File(filePath)
        file.readLines().drop(1).forEach { line ->
            val splitLine = line.split(",")
            products.add(translatePromotion(splitLine))
        }
        return products
    }

    private fun translatePromotion(line: List<String>): Product {
        val name = line[0]
        val price = line[1].toInt()
        val quantity = line[2].toInt()
        val promotion = line[3]

        return Product(name, price, quantity, promotion)
    }
}