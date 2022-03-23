package collections

fun main(args: Array<String>) {
    lateinit var map: HashMap<Int, String>

    for ((id, nome) in map) {
        println("$id) $nome")
    }

    map = hashMapOf(1 to "Gui", 2 to "Rebeca", 3 to "Cibalena")

}