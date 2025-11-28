package store.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateMapper {
    private const val DATE_FORMAT = "yyyy-MM-dd"
    private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    fun String.toLocalDate(): LocalDate {
        return LocalDate.parse(this, formatter)
    }
}
