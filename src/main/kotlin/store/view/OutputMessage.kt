package store.view

enum class OutputMessage(val text: String) {
    WELCOME("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),

    PURCHASE_INPUT_PROMPT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    EXTRA_ITEMS_CONFIRM("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    NON_PROMOTION_CONFIRM("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    MEMBERSHIP_DISCOUNT_CONFIRM("멤버십 할인을 받으시겠습니까? (Y/N)"),
    CONTINUE_PURCHASE_CONFIRM("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),

    RECEIPT_HEADER_STORE("===========W 편의점============="),
    RECEIPT_HEADER_FREE("===========증 정============="),
    RECEIPT_DIVIDER("=============================="),

    RECEIPT_BODY_LABEL("상품명\t수량\t금액"),

    RECEIPT_ITEM_WITH_PRICE("%s\t%d\t%,d"),
    RECEIPT_FREE_ITEM("%s\t%d"),

    RECEIPT_TOTAL_PRICE("총구매액\t%d\t%,d"),
    RECEIPT_DISCOUNT("%s\t-%,d"),

    RECEIPT_FINAL_AMOUNT("내실돈\t%,d");

    override fun toString(): String = text

    fun format(vararg args: Any): String = text.format(*args)
}
