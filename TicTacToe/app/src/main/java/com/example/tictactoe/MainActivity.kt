package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val game = TicTacToe()
    private var player1Start: Boolean = true
    private var score1: Int = 0
    private var score2: Int = 0
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var letter :String
        binding.button00.setOnClickListener {
            letter = game.next()
            binding.button00.text = letter
            game.updateBoard(letter, 0, 0)
            binding.button00.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button01.setOnClickListener {
            letter = game.next()
            binding.button01.text = letter
            game.updateBoard(letter, 0, 1)
            binding.button01.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button02.setOnClickListener {
            letter = game.next()
            binding.button02.text = letter
            game.updateBoard(letter, 0, 2)
            binding.button02.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button10.setOnClickListener {
            letter = game.next()
            binding.button10.text = letter
            game.updateBoard(letter, 1, 0)
            binding.button10.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button11.setOnClickListener {
            letter = game.next()
            binding.button11.text = letter
            game.updateBoard(letter, 1, 1)
            binding.button11.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button12.setOnClickListener {
            letter = game.next()
            binding.button12.text = letter
            game.updateBoard(letter, 1, 2)
            binding.button12.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button20.setOnClickListener {
            letter = game.next()
            binding.button20.text = letter
            game.updateBoard(letter, 2, 0)
            binding.button20.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button21.setOnClickListener {
            letter = game.next()
            binding.button21.text = letter
            game.updateBoard(letter, 2, 1)
            binding.button21.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.button22.setOnClickListener {
            letter = game.next()
            binding.button22.text = letter
            game.updateBoard(letter, 2, 2)
            binding.button22.isClickable = false
            updateScore(letter)
            if(game.moves == 9){
                reset()
            }
        }

        binding.buttonReset.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        binding.button00.text = ""
        binding.button01.text = ""
        binding.button02.text = ""
        binding.button10.text = ""
        binding.button11.text = ""
        binding.button12.text = ""
        binding.button20.text = ""
        binding.button21.text = ""
        binding.button22.text = ""

        binding.button00.isClickable = true
        binding.button01.isClickable = true
        binding.button02.isClickable = true
        binding.button10.isClickable = true
        binding.button11.isClickable = true
        binding.button12.isClickable = true
        binding.button20.isClickable = true
        binding.button21.isClickable = true
        binding.button22.isClickable = true

        player1Start = !player1Start
        game.reset()
    }

    private fun updateScore(letter :String) {

        if (game.win()) {
            if ((letter == "X" && player1Start) || (letter == "O" && !player1Start)){
                score1 ++
                ((binding.textViewP1)!!).text = String.format("Player 1: %s", score1.toString())
            }
            else {
                score2 ++
                ((binding.textViewP2)!!).text = String.format("Player 2: %s", score2.toString())
            }
            reset()
        }
    }

}

class TicTacToe() {
    private var previousX :Boolean = false
    var moves :Int = 0
    private var board = arrayOf(
            arrayOf("1", "2", "3"),
            arrayOf("4", "5", "6"),
            arrayOf("7", "8", "9"),
    )

    fun win(): Boolean {
        for (i in 0..2) {
           if (board[i][0] == board[i][1] && board[i][1] == board[i][2])
               return true
        }

        for (i in 0..2) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return true

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return true

        return false
    }

    fun updateBoard(letter: String, row: Int, column: Int){
        board[row][column] = letter
        moves++
    }

    fun next() :String {
        return when (previousX) {
            true -> {
                previousX = false
                "O"
            }
            false -> {
                previousX = true
                "X"
            }
        }
    }

    fun reset() {
        moves = 0
        previousX = false
        board = arrayOf(
            arrayOf("1", "2", "3"),
            arrayOf("4", "5", "6"),
            arrayOf("7", "8", "9"),
        )
    }
}
