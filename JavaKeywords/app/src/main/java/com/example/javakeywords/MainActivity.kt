package com.example.javakeywords

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.javakeywords.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val wordsList = listOf("abstract",
        "continue",
        "for",
        "new",
        "switch",
        "assert",
        "default",
        "goto",
        "package",
        "synchronized",
        "boolean",
        "do",
        "if",
        "private",
        "this",
        "break",
        "double",
        "implements",
        "protected",
        "throw",
        "byte",
        "else",
        "import",
        "public",
        "throws",
        "case",
        "enum",
        "instanceof",
        "return",
        "transient",
        "catch",
        "extends",
        "int",
        "short",
        "try",
        "char",
        "final",
        "interface",
        "static",
        "void",
        "class",
        "finally",
        "long",
        "strictfp",
        "volatile",
        "const",
        "float",
        "native",
        "super",
        "while")

    private lateinit var remainingWords: MutableList<String>

    private var score: Int = 0

    private lateinit var binding: ActivityMainBinding

    private val timer = object :CountDownTimer(180000,
        1000) {

        override fun onTick(millisUntilFinished: Long) {
            val date = Date(millisUntilFinished)
            val format = SimpleDateFormat("mm:ss")
            binding.timer.text = getString(R.string.time_left,
                format.format(date))
        }

        override fun onFinish() {
            binding.guessWord.isEnabled = false
            binding.remainingWords.append("\n")
            binding.remainingWords.append("WORDS NOT GUESSED:")

            remainingWords.sorted()
                    .forEach {
                        binding.remainingWords.append("\n")
                        binding.remainingWords.append(it)
                    }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.score.text = getString(R.string.score,
            0.toString())
        binding.timer.text = getString(R.string.time_left,
            "3:00")
        binding.guessWord.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view,
                keyCode)
        }
        binding.guessWord.isEnabled = false

        binding.buttonStart.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        remainingWords = wordsList.toMutableList()
        binding.guessWord.isEnabled = true
        binding.buttonStart.text = "RESTART"
        binding.wordsList.text = "WORDS GUESSED SO FAR:"
        binding.remainingWords.text = ""
        score = 0
        binding.score.text = getString(R.string.score,
            score.toString())
        binding.timer.text = getString(R.string.time_left,
            "3:00")
        timer.start()
    }

    private fun handleKeyEvent(view: View,
                               keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val guessWord: String = binding.guessWord.text.toString()
                    .toLowerCase()
            if (remainingWords.contains(guessWord)) {
                binding.wordsList.append("\n")
                binding.wordsList.append(guessWord)
                score++
                binding.score.text = getString(R.string.score,
                    score.toString())
                remainingWords.remove(guessWord)
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken,
                    0)
                return true
            }
            binding.guessWord.text.clear()
        }
        return false
    }
}