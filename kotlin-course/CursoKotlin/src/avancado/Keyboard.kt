package avancado

object Keyboard {

    private fun buildKeyboardMatrix(n: Int): Array<Array<Int?>> {
        val rows = 26 / n + 1
        val keyboard: Array<Array<Int?>> = Array(rows) { arrayOfNulls(n) }
        var j = 0

        repeat(rows) {
            for (i in 0 until n) {
                keyboard[j][i] = j * n + i
            }
            j++
        }
        return keyboard
    }

    fun findPath(input: String, maxColumn: Int): String {
        var r = 0
        var c = 0
        var res = ""
        val keyboard = buildKeyboardMatrix(maxColumn)
        keyboard.forEach { 
            it.forEach { 
                print("$it ")
            }
            println()
        }
        val numericInput = input.lowercase().map { it.code - 'a'.code }
        var aux1: Int
        var aux2: Int

        for (letter in numericInput) {
            aux1 = maxColumn - 1 + r * maxColumn
            aux2 = maxColumn * r

            while (letter > aux1) {
                res += 'd'
                aux1 += maxColumn
                r++
            }

            while (letter < aux2) {
                res += 'u'
                aux2 -= maxColumn
                r--
            }
            while (letter < keyboard[r][c]!!) {
                res += 'l'
                c--
            }
            while (letter > keyboard[r][c]!!) {
                res += 'r'
                c++
            }
            res += '!'
        }
        return res
    }
}

fun main(args: Array<String>) {
    println(Keyboard.findPath("aadjjdzzzz", 4))
}