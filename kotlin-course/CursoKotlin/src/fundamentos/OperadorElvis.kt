package fundamentos

fun main(args: Array<String>) {
    val opcional: String? = "Valido"
    val obrigatorio: String = opcional ?: "Valor Padrão"

    println(obrigatorio)
}