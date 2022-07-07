package rxjava

import avancado.*

class ExampleOF {
}

fun exampleOf(description: String, action: () -> Unit) {
    println("\n--- Example of: $description ---")
    action()
}

fun main(args: Array<String>) {
    exampleOf("dispose") {
        // 1
        val mostPopular: Observable<String> =
            Observable.just("A", "B", "C")
        // 2
        val subscription = mostPopular.subscribe {
            // 3
            println(it)
        }
    }
}