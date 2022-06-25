package com.example.beesapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beesapp.FindBeerApplication
import com.example.beesapp.R
import com.example.beesapp.adapter.ItemAdapter
import com.example.beesapp.viewmodel.DetailsViewModel
import com.example.beesapp.viewmodel.DetailsViewModelFactory
import com.example.beesapp.viewmodel.HomeViewModel
import com.example.beesapp.viewmodel.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(
            (this.context?.applicationContext as FindBeerApplication).repository,
            this.context?.applicationContext as FindBeerApplication
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDetailsButtons()
        setupViewModel()
        setupView()
        fillStars(args.breweryListRating)
    }

    private fun setupDetailsButtons() {
        arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
        setUpRatingButtonListener(args.breweryListName, args.breweryListRating, args.breweryListNumberOfRatings)
        mapButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + breweryAddress.text)
            )
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        viewModel.breweryRatingData.observe(viewLifecycleOwner) {
            viewModel.breweryRatingData.value?.forEach {
                if (it.name == breweryName.text.toString()) {
                    numberOfRatings.text = "${it.nRatings} ratings"
                    breweryRating.text = String.format("%.1f", it.rating)
                    fillStars(it.rating)
                    setUpRatingButtonListener(it.name, it.rating, it.nRatings)
                }
            }
        }
    }

    private fun setupView() {
        breweryInitial.text = args.breweryListName[0].toString()
        breweryName.text = args.breweryListName
        numberOfRatings.text = "${args.breweryListNumberOfRatings} ratings"
        breweryRating.text = String.format("%.1f", args.breweryListRating)
        breweryType.text = args.breweryListType
        brewerySite.text = args.breweryListSite
        breweryAddress.text = args.breweryListAddress
    }

    private fun fillStars(rating: Float) {
        when (rating) {
            in 1f..1.9f ->{
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_empty_star)
                detailScreenStar3.setBackgroundResource(R.drawable.ic_empty_star)
                detailScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
                detailScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            }
            in 2f..2.9f -> {
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar3.setBackgroundResource(R.drawable.ic_empty_star)
                detailScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
                detailScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            }
            in 3f..3.9f -> {
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar4.setBackgroundResource(R.drawable.ic_empty_star)
                detailScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            }
            in 4f..4.9f -> {
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar4.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar5.setBackgroundResource(R.drawable.ic_empty_star)
            }
            5f -> {
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar4.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar5.setBackgroundResource(R.drawable.ic_complet_star)
            }
        }
    }

    private fun setUpRatingButtonListener(breweryName: String, breweryRating: Float, nRatings: Int) {
        ratingButton.setOnClickListener {
            val action = RatingDialogFragmentDirections.detailsToRating()
            action.breweryDialogName = breweryName
            action.breweryDialogRating = breweryRating
            action.breweryDialogNumberOfRatings = nRatings
            findNavController().navigate(action)
        }
    }
}