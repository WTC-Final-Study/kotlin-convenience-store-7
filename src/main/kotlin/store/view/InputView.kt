package store.view

import camp.nextstep.edu.missionutils.Console
import store.constant.InputMessage

object InputView {

    fun input(message: String): String {
        println(message)
        return Console.readLine()
    }
}