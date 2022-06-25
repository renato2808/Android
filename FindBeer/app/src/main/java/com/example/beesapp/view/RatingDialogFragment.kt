package com.example.beesapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beesapp.FindBeerApplication
import com.example.beesapp.R
import com.example.beesapp.viewmodel.DetailsViewModel
import com.example.beesapp.viewmodel.DetailsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_rating_dialog.*

class RatingDialogFragment : BottomSheetDialogFragment(), TextWatcher {

    private val args: RatingDialogFragmentArgs by navArgs()
    private var rating = 0f
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(
            (this.context?.applicationContext as FindBeerApplication).repository,
            this.context?.applicationContext as FindBeerApplication
        )
    }

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
        dialogRatingText.text =
            String.format(resources.getString(R.string.rate_brewery_dialog_message), args.breweryDialogName)
        email.addTextChangedListener(this)
        setupSaveButton()
    }

    private fun setupRatingButtons() {
        closeDialogButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            if (email.text.isNotEmpty() && !emailInvalid.isVisible) {
                val nRatings = args.breweryDialogNumberOfRatings + 1
                val newRating = ((nRatings - 1) * args.breweryDialogRating + rating) / (nRatings)
                viewModel.updateBreweryRating(newRating, nRatings, args.breweryDialogName)
                dismiss()
            } else {
                emailInvalid.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRatingStars() {
        dialogScreenStar1.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            rating = 1f
        }

        dialogScreenStar2.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            rating = 2f
        }

        dialogScreenStar3.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            rating = 3f
        }

        dialogScreenStar4.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            rating = 4f
        }

        dialogScreenStar5.setOnClickListener {
            dialogScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar4.setBackgroundResource(R.drawable.ic_complet_star)
            dialogScreenStar5.setBackgroundResource(R.drawable.ic_complet_star)
            rating = 5f
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
            emailInvalid.visibility = View.VISIBLE
        } else {
            emailInvalid.visibility = View.GONE
        }
    }
}