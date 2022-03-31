package com.example.beesapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.beesapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_rating_dialog.*

class RatingDialogFragment : BottomSheetDialogFragment(), TextWatcher {

    private val args: RatingDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRatingButtons()
        setupRatingStars()
        dialogRatingText.text = String.format(resources.getString(R.string.rate_brewery_dialog_message),  args.breweryDialogName)
        email.addTextChangedListener(this)
    }

    private fun setupRatingButtons() {
        closeDialogButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRatingStars() {
        dialogScreenStar1.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
        }

        dialogScreenStar2.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
        }

        dialogScreenStar3.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
        }

        dialogScreenStar4.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
        }

        dialogScreenStar5.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_complet_star)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        val emailText: String = email.text.toString()

        if (!emailText.contains("@", true) || (!emailText.contains(
                ".com",
                true
            ) && !emailText.contains(".org", true))
        ) {
            emailValid.visibility = View.VISIBLE
        } else {
            emailValid.visibility = View.GONE
        }
    }
}