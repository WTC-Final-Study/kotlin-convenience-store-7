package store.constant

enum class OutputMessage(val text: String) {
    GREETING("안녕하세요. W편의점입니다.");

    override fun toString(): String {
        return text
    }
}