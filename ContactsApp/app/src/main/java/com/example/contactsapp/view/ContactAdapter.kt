package com.example.contactsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.databinding.ItemContactBinding
import com.example.contactsapp.model.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var contacts: List<Contact> = emptyList()
    private var onContactSelected: OnContactSelected? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context))
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    fun setOnClickListener(onPaymentMethod: OnContactSelected) {
        this.onContactSelected = onPaymentMethod
    }

    interface OnContactSelected {
        fun selectContact(contact: Contact)
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.nameTextView.text = "${contact.name.first} ${contact.name.last}"
            binding.emailTextView.text = contact.email
            binding.contactButton.setOnClickListener {
                onContactSelected?.selectContact(contact)
            }
        }
    }
}