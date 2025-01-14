package collections

fun main(args: Array<String>) {
    val aprovados = hashSetOf("João", "Maria", "Pedro", "Ana", "Joana", "Maria")
    // aprovados.add(1)

    println("Sem ordem...")
    for (aprovado in aprovados) {
        aprovado.print()
    }

    val aprovadosNaOrdem1 = linkedSetOf("João", "Maria", "Pedro", "Ana", "Joana")

    println("\nLinked...")
    for (aprovado in aprovadosNaOrdem1) {
        aprovado.print()
    }

    val aprovadosNaOrdem2 = sortedSetOf("João", "Maria", "Pedro", "Ana", "Joana")

    println("\nSorted...")
    for (aprovado in aprovadosNaOrdem2) {
        aprovado.print()
    }

    // Ordem maluca...
    println("\nSorted by second letter...")
    for (aprovado in aprovados.sortedBy { it.substring(1) }) {
        aprovado.print()
    }
}