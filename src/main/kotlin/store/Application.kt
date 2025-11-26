package store

import store.controller.Controller
import store.data.remote.StorageInitializer

fun main() {
    val initializer = StorageInitializer()
    initializer.init()

    val controller = Controller()
    controller.run()
}
