package store.constant

enum class ErrorMessage(val text: String) {
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");

    override fun toString(): String {
        return "[ERROR] $text"
    }
}