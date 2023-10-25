package com.example.robots.model

class Robot(var x: Int, var y: Int) {
    val visitedCells = mutableSetOf<Pair<Int, Int>>()

    init {
        move(x, y)
    }

    fun canMove(otherRobotMoves: Set<Pair<Int, Int>>): Boolean {
        return possibleMoves(otherRobotMoves).isNotEmpty()
    }

    fun randomMove(otherRobotMoves: Set<Pair<Int, Int>>): Pair<Int, Int>? {
        val possibleMoves = possibleMoves(otherRobotMoves)

        return if (possibleMoves.isNotEmpty()) {
            val randomMove = possibleMoves.random()
            move(randomMove.first, randomMove.second)
            randomMove
        } else {
            null
        }
    }

    private fun possibleMoves(otherRobotMoves: Set<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()
        if (x > 0) possibleMoves.add(Pair(x - 1, y)) // Move up
        if (x < 6) possibleMoves.add(Pair(x + 1, y)) // Move down
        if (y > 0) possibleMoves.add(Pair(x, y - 1)) // Move left
        if (y < 6) possibleMoves.add(Pair(x, y + 1)) // Move right

        val unvisitedMoves = possibleMoves.filter { move ->
            !(visitedCells.contains(move) || otherRobotMoves.contains(move))
        }

        return unvisitedMoves
    }

    private fun move(row: Int, col: Int) {
        x = row
        y = col
        visitedCells.add(Pair(x, y))
    }

    fun wins(prizeX: Int, prizeY: Int): Boolean {
        return x == prizeX && y == prizeY
    }
}
