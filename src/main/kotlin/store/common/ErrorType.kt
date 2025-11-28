package store.common

enum class ErrorType(val message: String) {
    NO_RESOURCE("리소스가 없습니다."),
    INVALID_PURCHASE_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OUT_OF_STOCK_AMOUNT("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),

    UNKNOWN("알 수 없는 오류입니다. 다시 입력해 주세요.");

    override fun toString(): String {
        return "[ERROR] $message"
    }
}
