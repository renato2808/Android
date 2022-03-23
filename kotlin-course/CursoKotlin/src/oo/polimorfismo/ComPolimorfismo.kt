package oo.polimorfismo

open class Comida(val peso: Double)
class Feijao: Comida(0.3)
class Arroz: Comida(0.2)
class Ovo: Comida(0.1)

class Pessoa(var peso: Double) {
    fun comer(comida: Comida) {
        peso += comida.peso
    }
}

fun main(args: Array<String>) {
    val feijao = Feijao()
    val arroz = Arroz()
    val ovo = Ovo()

    val joao = Pessoa(80.5)
    joao.comer(feijao)
    joao.comer(arroz)
    joao.comer(ovo)

    println(joao.peso)
}