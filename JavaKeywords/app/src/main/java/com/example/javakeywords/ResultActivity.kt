package com.example.javakeywords

import android.content.Intent
import android.net.Uri
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
        savedInstanceState?.get("RESTART")

        val score = intent?.extras?.getInt("score")

        binding.score.text = getString(
            R.string.score,
            score.toString()
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

        binding.fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, "fulano@gmail.com")
                putExtra(Intent.EXTRA_CC, "fulano@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "JavaKeywords Score")
                putExtra(Intent.EXTRA_TEXT, "Hi, my Java KeyWords score was $score out of 50")
            }

            //intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
            if (intent.resolveActivity(packageManager) != null)
                startActivity(intent)
        }
    }
}