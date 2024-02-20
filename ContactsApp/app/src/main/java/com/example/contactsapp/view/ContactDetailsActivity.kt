package com.example.contactsapp.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.databinding.ActivityContactDetailsBinding
import com.example.contactsapp.model.Contact
import java.io.Serializable


class ContactDetailsActivity : AppCompatActivity() {
    private val binding: ActivityContactDetailsBinding by lazy {
        ActivityContactDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val contact = intent.serializable("CONTACT_DETAILS") as? Contact

        // Display contact details
        contact?.let { displayContactDetails(it) }
    }

    private fun displayContactDetails(contact: Contact) {
        // Set contact details in UI
        binding.textViewName.text = "${contact.name.first} ${contact.name.last}"
        binding.textViewEmail.text = contact.email
        // Display other contact attributes as needed
    }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }
}