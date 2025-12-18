package store.service

import store.model.Promotion
import java.io.File
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PromotionManager {

    val promotions: Map<String, Promotion>

    init {
        promotions = loadPromotions()
    }

    private fun loadPromotions(): Map<String, Promotion> {
        val promotions = mutableMapOf<String, Promotion>()
        val filePath = "src/main/resources/promotions.md"
        val file = File(filePath)
        file.readLines().drop(1).forEach { line ->
            val splitLine = line.split(",")
            val promotion = transformPromotion(splitLine)
            promotions[promotion.name] = promotion
        }
        return promotions
    }

    private fun transformPromotion(line: List<String>): Promotion {
        val name = line[0]
        val buy = line[1].toInt()
        val get = line[2].toInt()
        val startDate = line[3]
        val endDate = line[4]
        val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return Promotion(
            name = name,
            buy = buy,
            get = get,
            startDate = LocalDate.parse(startDate, formatDate),
            endDate = LocalDate.parse(endDate, formatDate))
    }
}