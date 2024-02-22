package com.example.contactsapp.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.contactsapp.ContactApp
import com.example.contactsapp.R
import com.example.contactsapp.databinding.ActivityContactDetailsBinding
import com.example.contactsapp.model.Contact
import com.example.contactsapp.viewmodel.ContactViewModel
import com.example.contactsapp.viewmodel.ContactViewModelFactory
import kotlinx.coroutines.launch
import java.io.Serializable


class ContactDetailsActivity : AppCompatActivity() {
    private val binding: ActivityContactDetailsBinding by lazy {
        ActivityContactDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(
            (this.applicationContext as ContactApp).contactRepository,
            this.applicationContext as ContactApp
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val contact = intent.serializable("CONTACT_DETAILS") as? Contact

        // Display contact details
        contact?.let { displayContactDetails(this, it) }
    }

    private fun displayContactDetails(context: Context, contact: Contact) {
        // Set contact details in UI
        binding.apply {
            textViewName.text =
                getString(R.string.contact_name, contact.name.title, contact.name.first, contact.name.last)
            textViewEmail.text = getString(R.string.contact_email, contact.email)
            textViewGender.text = getString(R.string.contact_gender, contact.gender)
            textViewPhone.text = getString(R.string.phone, contact.phone)
            textViewAddress.text =
                getString(
                    R.string.contact_address,
                    contact.location.street,
                    contact.location.city,
                    contact.location.state,
                    contact.location.postcode
                )
            textViewLogin.text = getString(
                R.string.login_data,
                contact.login.username,
                contact.login.password,
                contact.login.salt,
                contact.login.md5,
                contact.login.sha1,
                contact.login.sha256
            )
            val timestampInMillis = contact.registered * 1000
            textViewRegister.text =
                getString(
                    R.string.register_date,
                    DateUtils.formatDateTime(
                        context,
                        timestampInMillis,
                        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME
                    )
                )
            textViewDob.text = getString(R.string.contact_dob, contact.dob.toString())
            textViewCell.text = getString(R.string.contact_cell, contact.cell)
            textViewId.text = getString(R.string.id_name_value, contact.id.name, contact.id.value)
            textViewNat.text = getString(R.string.contact_nat, contact.nat)
        }
        lifecycleScope.launch {
            val imageBitmap = viewModel.getBitmapFromURL(contact.picture.large)
            imageBitmap?.let {
                binding.contactPicture.setImageBitmap(it)
            }
        }
    }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }
}