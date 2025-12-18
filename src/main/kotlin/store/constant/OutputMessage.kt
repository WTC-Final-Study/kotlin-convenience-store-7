package store.constant

enum class OutputMessage(val text: String) {
    GREETING("안녕하세요. W편의점입니다."),
    INVENTORY_TITLE("현재 보유하고 있는 상품입니다.");

    override fun toString(): String {
        return text
    }
}