package avancado

import kotlin.contracts.contract

fun fatorial(num: Int): Int = when(num) {
    in 0..1 -> 1
    in 2..Int.MAX_VALUE -> num * fatorial(num - 1)
    else -> throw IllegalArgumentException("Número negativo")
}

data class ByteArrayWrapper (val data: ByteArray) {
    override fun equals(other: Any?): Boolean {
        return if (other !is ByteArrayWrapper) {
            false
        } else {
            data.contentEquals(other.data)
        }
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var laux1: ListNode? = l1
    var laux2: ListNode? = l2
    var l3: ListNode? = ListNode(0)
    var root = l3

    while (laux1 != null && laux2 != null && l3 != null) {
        if ((l3.`val` + (laux1.`val` + laux2.`val`)) > 9)
            l3.next = ListNode(1)
        else if(laux1.next != null || laux2.next != null)
            l3.next = ListNode(0)

        l3.`val` = (l3.`val` + (laux1.`val` + laux2.`val`)) % 10
        l3 = l3.next
        laux1 = laux1.next
        laux2 = laux2.next
    }

    while (laux1 != null && l3 != null) {
        if (l3.`val` + laux1.`val` > 9)
            l3.next = ListNode(1)
        else if(laux1.next != null)
            l3.next = ListNode(0)
        l3.`val` = (l3.`val` + laux1.`val`) % 10
        l3 = l3.next
        laux1 = laux1.next
    }

    while (laux2 != null && l3 != null) {
        if (l3.`val` + laux2.`val` > 9)
            l3.next = ListNode(1)
        else if(laux2.next != null)
            l3.next = ListNode(0)
        l3.`val` = (l3.`val` + laux2.`val`) % 10
        l3 = l3.next
        laux2 = laux2.next
    }

    return root
}

fun longestPalindrome1(s: String): String {
    var s1: String = s
    var s2: String = s
    var s3: String = s
    return if (s.reversed() == s) s
    else {
        do {
            s1 = s1.drop(1)
        } while ((s1.first() != s1.last()))

        do {
            s2 = s2.dropLast(1)
        } while ((s2.first() != s2.last()))

        do {
            s3 = s3.drop(1)
            if (s3.length > 1)
                s3 = s3.dropLast(1)
        } while ((s3.first() != s3.last()))

        s1 = longestPalindrome1(s1)
        s2 = longestPalindrome1(s2)
        s3 = longestPalindrome1(s3)
        return if (s1.length > s2.length && s1.length > s3.length)
            s1
        else if (s2.length > s3.length)
            s2
        else
            s3
    }
}

fun canReorderDoubled1(arr: IntArray): Boolean {
    var arr1 = arr.drop(1).toMutableList()
    if (arr.isEmpty()) {
        return true
    } else if(arr.contains(0)) {
        arr1 = arr.toMutableList()
        var i = 0
        while (arr1.contains(0)){
            arr1.removeAt(arr1.indexOf(0))
            i++
        }
        if (i%2 == 0) return canReorderDoubled(arr1.toIntArray()) else return false
    }
    else if (arr1.contains(arr[0]*2) && ((arr[0] % 2 == 0) && (arr1.contains(arr[0]/2)))){
        arr1.removeAt(arr1.indexOf(arr[0]*2))
        var arr2 = arr.drop(1).toMutableList()
        arr2.removeAt(arr2.indexOf(arr[0]/2))
        if (canReorderDoubled(arr1.toIntArray()))
            return true
        else
            return canReorderDoubled(arr2.toIntArray())
    } else if (arr1.contains(arr[0]*2)) {
        arr1.removeAt(arr1.indexOf(arr[0]*2))
        return canReorderDoubled(arr1.toIntArray())
    } else if ((arr[0] % 2 == 0) && (arr1.contains(arr[0]/2))) {
        arr1.removeAt(arr1.indexOf(arr[0]/2))
        return canReorderDoubled(arr1.toIntArray())
    }
    else
        return false
}

fun canReorderDoubled(arr: IntArray): Boolean {
    return reorder(arr.sortedArray())
}

fun reorder(arr: IntArray) : Boolean{
    var arr1 = arr.drop(1).toMutableList()
    if (arr.isEmpty()) {
        return true
    } else if(arr.contains(0)) {
        arr1 = arr.toMutableList()
        var i = 0
        while (arr1.contains(0)){
            arr1.removeAt(arr1.indexOf(0))
            i++
        }
        if (i%2 == 0) return canReorderDoubled(arr1.toIntArray()) else return false
    } else if (arr1.contains(arr[0]*2)) {
        arr1.removeAt(arr1.indexOf(arr[0] * 2))
        return canReorderDoubled(arr1.toIntArray())
    }
    else
        return false
}

fun multiply(num1: String, num2: String): String {
    var res: Long = num1.toLong() * num2.toLong()
    return  res.toString()
}


fun convert(s: String, numRows: Int): String {
    val sLenght = s.length
    var res = s.toCharArray()
    var j = numRows
    var i = 0
    if(numRows > 1) {
        repeat(numRows){
            var jump = j*2 - 2
            if (jump == 0)
                jump = numRows*2 - 2
            var pos = (numRows - j)
            while(pos < sLenght && i < sLenght) {
                res[i] = s[pos]
                i++
                pos += jump
                if (pos < sLenght && i < sLenght) {
                    res[i] = s[pos]
                    i++
                }
                if (j in 2 until numRows)
                    pos += (numRows - j + 1)*2 - 2
                else
                    pos += jump
            }
            j--
        }
    }
    return res.joinToString("")
}

fun concatEdgeLetters(a: MutableList<String>): MutableList<String> {
    var result : MutableList<String> = mutableListOf()
    val size = a.size
    for (i in 0 until size){
        val index = a[(i+1)%size].lastIndex
        result.add(i, (a[i][0] + "" + a[(i+1)%size][index]))
    }
    println(result)
    return result
}

fun divisorSubstrings(n: Int, k: Int): Int {
    val a = n.toString().toCharArray()
    val substrings: MutableList<String> = mutableListOf()
    var result = 0


    for(i in a.indices){
        var digit = ""
        var j = i
        if(i + k <= a.size) {
            repeat(k) {
                digit += a[j]
                j++
            }
            if(!substrings.contains(digit)) {
                substrings.add(digit)
            }
        }
    }

    println(substrings)

    for(digit in substrings){
        if ((digit.toInt() != 0) && n % digit.toInt() == 0) {
            result++
        }

    }
    return result
}

fun rectangleBoxes(operations: MutableList<MutableList<Int>>): MutableList<Boolean> {

    var rectangles = mutableListOf<IntArray>()
    var results = mutableListOf<Boolean>()
    for(operation in operations){
        if(operation[0] == 0){
            rectangles.add(intArrayOf(operation[1], operation[2]))
        }
        else {
            var result = true
            for(rectangle in rectangles){
                println(rectangle[0])
                println(rectangle[1])
                if (!((rectangle[0] <= operation[1] && rectangle[1] <= operation[2]) || (rectangle[0] <= operation[2] && rectangle[1] <= operation[1]))) {
                    result = false
                }
            }
            results.add(result)
        }
    }
    return results
}

fun main(args: Array<String>) {
    /*println("Resultado: ${fatorial(5)}")

    val bytearray = ByteArrayWrapper(byteArrayOf(1, 1, 1))
    val nullArray: ByteArrayWrapper? = null
    println(bytearray == nullArray)
    println(bytearray == null)
    val iterator = testIterator();
    repeat(5){
        println(iterator.next)
    }

    println(longestPalindrome1("bacabab"))
    println(canReorderDoubled(intArrayOf(-62,86,96,-18,43,-24,-76,13,-31,-26,-88,-13,96,-44,9,-20,-42,100,-44,-24,-36,28,-32,58,-72,20,48,-36,-45,14,24,-64,-90,-44,-16,86,-33,48,26,29,-84,10,46,50,-66,-8,-38,92,-19,43,48,-38,-22,18,-32,-48,-64,-10,-22,-48)))
    println(canReorderDoubled(intArrayOf(0,0,0, 0)))
    println(convert("PAYPALISHIRING", 3 ))*/
    concatEdgeLetters(mutableListOf("I", "have", "a", "nice", "surprise"))
    println(divisorSubstrings(220903298, 2))
    println(rectangleBoxes(mutableListOf(mutableListOf(0, 1, 3), mutableListOf(0, 4, 2), mutableListOf(1, 3, 4), mutableListOf(1, 3, 2))))
}