package funcoes

fun <E> List<E>.secondOrNull(): E? = if(this.size >= 2) this.get(1) else null

fun main(args: Array<String>) {
    val list = listOf(1)
    print(list.secondOrNull())
}