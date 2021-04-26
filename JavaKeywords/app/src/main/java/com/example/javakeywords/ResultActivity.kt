package com.example.javakeywords

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.javakeywords.databinding.ActivityResultBinding
import com.google.android.material.snackbar.Snackbar

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setSupportActionBar(findViewById(R.id.toolbar))

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.score.text = getString(
            R.string.score,
            intent?.extras?.getInt("score").toString()
        )

        val remainingWords = intent?.extras?.getStringArrayList("remainingWords")

        binding.remainingWords.append("\n")
        remainingWords?.sorted()?.forEach {
            binding.remainingWords.append(it)
            binding.remainingWords.append(", ")
        }

        binding.buttonStart.setOnClickListener {
            val context = binding.root.context

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("restart", true)
            context.startActivity(intent)
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Replace with your own action",
                Snackbar.LENGTH_LONG
            )
                .setAction(
                    "Action",
                    null
                )
                .show()
        }
    }
}