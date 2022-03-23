package lambdas

fun main(args: Array<String>) {
    val alunos = arrayListOf("Pedro", "Tiago", "Jonas")
    val alunos_upper = alunos.map { it.toUpperCase() }
    println(alunos_upper)
}