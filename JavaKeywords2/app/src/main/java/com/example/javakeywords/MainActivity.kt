package com.example.javakeywords

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.javakeywords.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val wordsList = listOf(
        "int",
        "float",
        "class"
    )

    private var score = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.guessWord.setOnKeyListener { v, keyCode, event ->
            handleKeyEvent(v, keyCode)
        }

    }

    private fun handleKeyEvent(view: View, keycode: Int): Boolean{

        if (keycode == KeyEvent.KEYCODE_ENTER){
            val word = binding.guessWord.text.toString().lowercase()
            if (wordsList.contains(word)){
                score++
                binding.wordsGuessed.append("\n")
                binding.wordsGuessed.append(word)
                binding.score.text = getString(
                    R.string.score,
                    score.toString()
                )
            }
            binding.guessWord.text.clear()
            return true
        }

        return false
    }

}