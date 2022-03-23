package collections

data class Fruta (var nome : String, var preco: Double)

fun main(args: Array<String>) {
    var frutas = arrayListOf(Fruta("Banana", 1.50), Fruta("Morango", 3.20))

    for(fruta in frutas) {
        println("${fruta.nome} - R$ ${fruta.preco}")
    }

    println(frutas.contains(Fruta("Banana", 1.50)))
    frutas.add(Fruta("Banana", 1.0))
    println(frutas)
    println(frutas.distinctBy { it.preco })
    println(frutas.distinctBy { it.nome })
}