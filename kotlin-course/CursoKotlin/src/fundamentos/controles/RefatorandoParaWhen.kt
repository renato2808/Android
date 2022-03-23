package fundamentos.controles

fun main(args: Array<String>) {
    var nota = 8.7

    when(nota) {
        in 9.0..10.0 -> print("Fantástico")
        in 7.0..8.99 -> print("Parabéns")
        in 4.0..6.99 -> print("Tem como recuperar")
        in 0.0..3.99 -> print("Te vejo no próximo semestre")
        else -> print("Nota inválida")
    }
}