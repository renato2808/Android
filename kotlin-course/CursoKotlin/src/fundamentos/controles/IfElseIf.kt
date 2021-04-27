package fundamentos.controles

fun main(args: Array<String>) {
    val nota = 8.99

    // Usando operador range
    if (nota in 9.0..10.0) {
        println("Fantástico")
    } else if (nota in 7.0..8.99) {
        println("Parabéns")
    } else if (nota in 4.0..6.99) {
        println("Tem como recuperar")
    } else if (nota in 0.0..3.99) {
        println("Te vejo no próximo semestre")
    } else {
        println("Nota inválida")
    }

    println(5 in 7..4)
    println(5 in 4..7)
    println(Float.MAX_VALUE)
}