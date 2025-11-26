package store.domain.model

import java.time.LocalDate

data class Promotion(
    val name: String,
    val buy: Int,
    val get: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    fun isActive(today: LocalDate): Boolean {
        return !today.isBefore(startDate) && !today.isAfter(endDate)
    }
}
