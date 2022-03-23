package com.example.hellodroid

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HelloDroidActivity : AppCompatActivity() {
    private var message: TextView? = null
    private var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        message = findViewById(R.id.clickCounter)
        val droid = findViewById<ImageView>(R.id.droidImage)

        //Define and attach click listener
        droid.setOnClickListener { tapDroid() }
    }

    private fun tapDroid() {
        counter++
        val countAsText: String
        countAsText = when (counter) {
            1 -> "once"
            2 -> "twice"
            else -> String.format("%d times", counter)
        }
        message!!.text = String.format("You touched the droid %s", countAsText)
    }
}