package avancado

import funcoes.min
import java.util.*


data class Ponto(val x: Int, val y: Int) {
    operator fun plus(other: Ponto): Ponto = Ponto(x + other.x, y + other.y)
    operator fun unaryMinus(): Ponto = Ponto(-x, -y)
}

fun palindrome(inputString: String): Boolean {
    val aux = inputString.reversed()
    var res = true

    for (i in inputString.indices) {
        if (aux[i] != inputString[i]) {
            res = false
            break
        }
    }
    return res
}

fun makeConsecutive(statues: MutableList<Int>): Int {
    val max = statues.maxOrNull() ?: 0
    val min = statues.minOrNull() ?: 0
    return (max - min) - statues.size + 1
}

fun shapeArea(n: Int): Int {
    return ((2 * n - 1) * (2 * n - 1) - 4 * ((n - 1) * (n - 1) - (n - 2) * (n - 1) / 2))
}

fun almostIncreasingSeq(sequence: MutableList<Int>): Boolean {
    var res = true
    val auxSeq = mutableListOf<Int>()
    val auxSeq2 = mutableListOf<Int>()
    sequence.forEach { auxSeq.add(it) }
    sequence.forEach { auxSeq2.add(it) }

    for (i in 0 until sequence.size - 1) {
        if (auxSeq[i] >= auxSeq[i + 1]) {
            auxSeq.removeAt(i + 1)
            res = checkSequence(auxSeq)
            auxSeq2.removeAt(i)
            res = (res || checkSequence(auxSeq2))
            break
        }
    }

    return res
}

fun hauntedHotel(matrix: MutableList<MutableList<Int>>): Int {
    var total = 0
    var isHaunted = false

    for (i in 0 until matrix.size) {
        for (j in 0 until matrix[0].size) {
            var k = i
            while (k >= 0) {
                if (matrix[k][j] == 0) {
                    isHaunted = true
                    break
                }
                k--
            }
            if (!isHaunted)
                total += matrix[i][j]
            isHaunted = false
        }
    }

    return total
}

fun longestString(inputArray: MutableList<String>): MutableList<String> {
    var maxLen = 0
    val res = mutableListOf<String>()
    inputArray.forEach {
        if (it.length > maxLen) {
            res.clear()
            res.add(it)
            maxLen = it.length
        } else if (it.length == maxLen) {
            res.add(it)
        }
    }
    return res
}


fun checkSequence(sequence: MutableList<Int>): Boolean {
    var res = true
    for (i in 0 until sequence.size - 1) {
        if (sequence[i] >= sequence[i + 1]) {
            res = false
            break
        }
    }
    return res
}


fun commonLetters(s1: String, s2: String): Int {

    val aux = mutableListOf<Char>()
    var total = 0

    s1.forEach { letter ->
        if (!aux.contains(letter)) {
            total += min(s1.count { it == letter }, s2.count { it == letter })
            aux.add(letter)
        }
    }

    return total
}

fun isLucky(n: Int): Boolean {
    val s = n.toString()
    val middle = s.length / 2
    var aux = 0
    var sum1 = 0
    var sum2 = 0

    s.forEach {
        if (aux < middle) {
            sum1 += it.toString().toInt()
            aux++
        } else {
            sum2 += it.toString().toInt()
        }
    }

    return sum1 == sum2
}

fun sortHeights(a: MutableList<Int>): MutableList<Int> {
    val aux = a.toMutableList()
    aux.sort()
    var k = 0
    while (k < aux.size && aux[k] == -1) {
        k++
    }

    for (i in a.indices) {
        if (a[i] != -1) {
            a[i] = aux[k]
            k++
        }
    }

    return a
}

fun isValidCrypt(crypt: MutableList<String>, solution: MutableList<MutableList<Char>>): Boolean {
    val res = mutableListOf<Long>()
    val code = StringBuilder()

    crypt.forEach {
        for (letter in it) {
            for (i in solution.indices) {
                if (solution[i][0] == letter) {
                    code.append(solution[i][1])
                }
            }
        }
        if (code.length > 1 && code[0] == '0') {
            return false
        } else {
            res.add(code.toString().toLong())
            code.clear()
        }
    }

    return res[0] + res[1] == res[2]
}

fun solution(grid: Array<CharArray>): Boolean {
    for (i in 0..8) {
        val row = CharArray(9)
        val square = CharArray(9)
        val column = grid[i].clone()
        for (j in 0..8) {
            row[j] = grid[j][i]
            square[j] = grid[i / 3 * 3 + j / 3][i * 3 % 9 + j % 3]
        }
        if (!(validate(column) && validate(row) && validate(square))) return false
    }
    return true
}

private fun validate(check: CharArray): Boolean {
    var i = ""
    Arrays.sort(check)
    for (number in check) {
        if (i.indexOf(number) != -1 && number != '.') return false
        if (number != '.') i += number
    }
    return true
}

fun firstNotDuplicate(s: String): Char {

    for (i in s.indices) {
        if (!s.substring(0, i).contains(s[i]) && !s.substring(i + 1, s.length).contains(s[i])) {
            return s[i]
        }
    }
    return '_'
}

fun firstDuplicate(a: MutableList<Int>): Int {
    var minIndex = 100000
    val aux = mutableListOf<Int>()

    for (i in a.indices) {
        if (!aux.contains(a[i])) {
            val index = a.subList(i + 1, a.size).indexOf(a[i])
            val absIndex = index + i + 1
            if (index != -1 && absIndex < minIndex) {
                minIndex = absIndex
            }
            aux.add(a[i])
        }
    }

    return if (minIndex == 100000) -1 else a[minIndex]
}
// Given a corpus of text and a number k, write some code to find the top k terms in that text.

// text: "b a b a a c"
// k: 2 (>= k)
// separator: <space>

// desc
// Result: ["a", "b"] since a occurs 3 times, b 2 times.

private fun topTerms(k: Int, text: String): List<Char> {
    val res = hashMapOf<Char, Int>() // at least k
    val aux = mutableListOf<Char>() // less than k

    text.forEach { letter ->
        if (letter != ' ' && !res.keys.contains(letter) && !aux.contains(letter)) {
            val aux1 = text.filter { it == letter }.count()
            if (aux1 >= k) {
                res.put(letter, aux1)
            } else {
                aux.add(letter)
            }
        }
    }

    return res.toList().sortedBy { (key, value) -> value }.toMap().keys.reversed()
}

private fun topTermsLinear(k: Int, text: String): List<Char> {
    val res = hashMapOf<Char, Int>() // at least k
    val s = mutableListOf<Char>()

    text.forEach { letter ->
        if (!res.keys.contains(letter)) {
            res[letter] = 1
        } else {
            res[letter] = res[letter]!!.plus(1)
        }
    }

    val x = res.filter {
        it.value >= k
    }
    x.toList().sortedBy { (key, value) -> value }.toMap().forEach {
        s.add(it.key)
    }
    return s.reversed()
}

private fun reversedInParenthesis(inputString: String?): String {
    val str = StringBuilder(inputString)
    var start: Int
    var end: Int
    while (str.indexOf("(") != -1) {
        start = str.lastIndexOf("(")
        end = str.indexOf(")", start)
        str.replace(start, end + 1, StringBuilder(str.substring(start + 1, end)).reverse().toString())
    }
    return str.toString()
}

fun weighSum(a: MutableList<Int>): MutableList<Int> {
    val aux = mutableListOf(0, 0)

    for (i in 0 until a.size step 2) {
        aux[0] += a[i]
        aux[1] += a[i + 1]
    }

    return aux
}

fun addBorder(picture: MutableList<String>): MutableList<String> {
    val res = mutableListOf<String>()
    val str = StringBuilder()

    str.append("*".repeat(picture[0].length + 2))
    res.add(str.toString())
    str.clear()

    picture.forEach {
        str.append("*")
        str.append((it))
        str.append("*")
        res.add(str.toString())
        str.clear()
    }

    str.append("*".repeat(picture[0].length + 2))
    res.add(str.toString())

    return res
}

fun minimalSwap(a: MutableList<Int>, b: MutableList<Int>): Boolean {
    val diffA = mutableListOf<Int>()
    val diffB = mutableListOf<Int>()


    for (i in 0 until a.size){
        if(a[i] != b[i]){
            diffA.add(a[i])
            diffB.add(b[i])
        }
    }

    return diffB.size <= 2 && (diffB == diffA || diffB == diffA.reversed())
}

fun minimalMoves(inputArray: MutableList<Int>): Int {
    var total = 0

    for (i in 0 until inputArray.size - 1) {
        val diff = inputArray[i] - inputArray[i+1]
        if (diff >= 0) {
            total += diff + 1
            inputArray[i+1] += diff + 1
        }
    }

    return total
}

fun palindromeRearranging(inputString: String): Boolean {
    val res = hashMapOf<Char, Int>()
    var k = 0
    val size  = inputString.length

    inputString.forEach {
        if (!res.keys.contains(it)) {
            res[it] = 1
        } else {
            res[it] = res[it]!!.plus(1)
        }
    }

    res.forEach {
        if(it.value % 2 == 1){
            k++
        }
    }
    return (k==0 && size%2 == 0) || (k==1 && size%2 == 1)
}

fun isEvenlyWeighed(yourLeft: Int, yourRight: Int, friendsLeft: Int, friendsRight: Int): Boolean {
    return yourLeft == friendsLeft && yourRight == friendsRight || yourLeft == friendsRight && yourRight == friendsLeft
}
fun isIPV4(inputString: String): Boolean {
    var res = true

    val list = inputString.split('.')

    if (list.size != 4) {
        res = false
    } else {
        list.forEach {
            try{
                if(it.isEmpty() || (it.length > 1 && it[0] == '0') || it.toInt() > 255){
                    res = false
                }
            } catch (e: NumberFormatException){
                res = false
            }
        }
    }

    return res
}

fun main(args: Array<String>) {
    println(palindrome("abba"))
    val input = mutableListOf(
        "young",
        "yooooooung",
        "hot",
        "or",
        "not",
        "come",
        "on",
        "fire",
        "water",
        "watermelon"
    )
    println(longestString(input))
    println(commonLetters(input[1], input[9]))
    println(sortHeights(mutableListOf(-1, 150, 190, 170, -1, -1, 160, 180)))
    println(firstDuplicate(mutableListOf(2, 1, 3, 5, 3, 2)))
    println(topTerms(4, "dabbssaaabsllseeeeeiiiiiiiiiffff"))
}