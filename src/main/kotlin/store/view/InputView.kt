package store.view

import camp.nextstep.edu.missionutils.Console

object InputView {

    fun read(): String = Console.readLine() ?: ""
}
