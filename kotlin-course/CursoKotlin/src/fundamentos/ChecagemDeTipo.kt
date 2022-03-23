package fundamentos

fun main(args: Array<String>) {
    var valor: Any = 1

    if (valor is String) {
        println(valor)
    } else if (valor !is String) {
        println("Não é uma String")
    }
}