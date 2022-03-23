package funcoes

import java.lang.Math.ceil

fun <E> filtrar(lista: List<E>, filtro: (E) -> Boolean): List<E> {
    val listaFiltrada = ArrayList<E>()
    for(e in lista) {
        if(filtro(e)) {
            listaFiltrada.add(e)
        }
    }

    return listaFiltrada
}

fun solution1(A: IntArray): Int {
    // write your code in Kotlin
    var end = false
    var i = 1
    while (!end){
        i++
        if(!A.contains(i))
            end = true
    }
    return i
}

fun solution2(A: IntArray): IntArray {
    // write your code in Kotlin
    var minDuplicate = A.maxOrNull() ?: 0
    var maxDuplicate = 0
    var duplicates = 0
    var b: MutableList<Int> = arrayListOf<Int>().toMutableList()
    var c: MutableList<Int> = arrayListOf<Int>().toMutableList()
    for (num in A){
        if (A.count{it == num} > 1 && !c.contains(num)){
            if (num < minDuplicate){
                minDuplicate = num
            }
            if (num > maxDuplicate){
                maxDuplicate = num
            }
            c.add(num)
            duplicates ++
        }
        else if (!c.contains(num)){
            c.add(num)
        }
    }

    if (duplicates == 0){
        minDuplicate = 0
        maxDuplicate = 0
    }

    b.add(duplicates)
    b.add(minDuplicate)
    b.add(maxDuplicate)
    b.addAll(c)

    return b.toIntArray()
}

fun rotateRight(A: IntArray, K: Int): IntArray {
    // write your code in Kotlin
    var aux: Int
    var aux2: Int
    var x: Int
    var size  = A.size
    repeat (K) {
        for (j in size downTo 1){
            aux  = j % size
            aux2 = aux-1

            if (aux2 < 0)
                aux2 = 0

            x = A[aux2]
            A[aux2] = A[aux]
            A[aux] = x
        }
    }

    return A
}


fun solution(X: Int, Y: Int, D: Int): Int {

    return kotlin.math.ceil((Y.toDouble() - X.toDouble()) / D.toDouble()).toInt()
    // write your code in Kotlin
}

fun comTresLetras(nome: String): Boolean {
    return nome.length == 3
}

fun main(args: Array<String>) {
    val numList1 = listOf(1, 2, 3, 3, 3, 5, 5, 5, 6, 6, 1, 2, 2, 44, 44, 3, 2, 44, 9, 3333, 3333).toIntArray()
    val numList2 = listOf(1, 2, 3, 4, 5, 6, 44, 9).toIntArray()
    rotateRight(numList1, 4).forEach{ print("$it ")}
    println()
    rotateRight(numList2, 4).forEach{ print("$it ")}
    println()
}