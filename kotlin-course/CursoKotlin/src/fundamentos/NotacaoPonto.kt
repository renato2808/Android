package fundamentos

fun main(args: Array<String>) {
    val a: Int = 33.dec()
    var b: Char = a.toString().first()

    println("Int: $a")
    println("Primeiro char da string a é: $b")
}