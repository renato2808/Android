package classes

class Cliente2 {
    constructor(nome: String) {
        this.nome = nome
    }

    private var nome: String
        get() = "Meu nome é ${field}"
        set(value) {
            field = value.takeIf { value.isNotEmpty() } ?: "Anônimo"
        }

    fun getNome2(): String{
        return nome
    }

    fun setNome2(nome: String){
        this.nome = nome
    }
}

fun main(args: Array<String>) {
    val c1 = Cliente2("")
    println(c1.getNome2())

    val c2 = Cliente2("Pedro")
    c2.setNome2("Ana")
    println(c2.getNome2())
}