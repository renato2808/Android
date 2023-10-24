package com.example.robots.model

class Robot(var x: Int, var y: Int) {
    private val visitedCells = mutableSetOf<Pair<Int, Int>>()

    fun canMove(): Boolean {
        // Check if the robot can make a valid move
        // You need to implement this logic based on your game rules
        // Ensure it's a valid move and not outside the grid
        return true
    }

    fun randomMove(): Pair<Int, Int> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()
        if (x > 0) possibleMoves.add(Pair(x - 1, y)) // Move up
        if (x < 6) possibleMoves.add(Pair(x + 1, y)) // Move down
        if (y > 0) possibleMoves.add(Pair(x, y - 1)) // Move left
        if (y < 6) possibleMoves.add(Pair(x, y + 1)) // Move right

        val unvisitedMoves = possibleMoves.filter { move ->
            !visitedCells.contains(move)
        }

        return if (unvisitedMoves.isNotEmpty()) {
            val randomMove = unvisitedMoves.random()
            move(randomMove.first, randomMove.second)
            randomMove
        } else {
            Pair(0, 0)
        }
    }

    fun visited(row: Int, col: Int): Boolean {
        return Pair(row, col) in visitedCells
    }

    private fun move(row: Int, col: Int): Boolean {
        if (canMove()) {
            x = row
            y = col
            visitedCells.add(Pair(x, y))
            return true
        }
        return false
    }

    fun wins(prizeX: Int, prizeY: Int): Boolean {
        return x == prizeX && y == prizeY
    }
}
