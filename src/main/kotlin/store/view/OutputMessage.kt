package store.view

enum class OutputMessage(val text: String) {
    WELCOME("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");

    override fun toString(): String = text
}
