package com.example.contactsapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.ContactApp
import com.example.contactsapp.databinding.ActivityMainBinding
import com.example.contactsapp.model.Contact
import com.example.contactsapp.viewmodel.ContactViewModel
import com.example.contactsapp.viewmodel.ContactViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter =  ContactAdapter()
    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(
            (this.applicationContext as ContactApp).contactRepository,
            this.applicationContext as ContactApp
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()

        viewModel.contacts.observe(this) { contacts ->
            if (contacts != null) {
                adapter.setContacts(contacts)
            } else {
                showErrorToast()
            }
        }

        viewModel.fetchContacts()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.setOnClickListener(object: ContactAdapter.OnContactSelected {
            override fun selectContact(contact: Contact) {
                launchContactDetailsActivity(contact)
            }
        })
    }

    private fun launchContactDetailsActivity(contact: Contact) {
        val intent = Intent(this, ContactDetailsActivity::class.java).apply {
            putExtra("CONTACT_DETAILS", contact)
        }
        startActivity(intent)
    }


    private fun showErrorToast() {
        Toast.makeText(this, "Failed to get contacts!", Toast.LENGTH_SHORT).show()
    }
}