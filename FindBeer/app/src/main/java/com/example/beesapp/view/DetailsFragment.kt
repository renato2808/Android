package com.example.beesapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beesapp.R
import com.example.beesapp.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDetailsButtons()
        setupView()
        fillStars(args.breweryListRating)
    }

    private fun setupDetailsButtons(){
        arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
        ratingButton.setOnClickListener {
            val action = RatingDialogFragmentDirections.detailsToRating()
            action.breweryDialogName = args.breweryListName
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setupView() {
        breweryInitial.text = args.breweryListName[0].toString()
        breweryName.text = args.breweryListName
        breweryRating.text = args.breweryListRating.toString()
        breweryType.text = args.breweryListType
        brewerySite.text = args.breweryListSite
        breweryAddress.text = args.breweryListAdress
    }

    private fun fillStars(rating: Float) {
        when (rating) {
            in 1f..1.9f -> detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
            in 2f..2.9f -> {
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
            }
            in 3f..3.9f -> {
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
            }
            in 4f..4.9f -> {
                detailScreenStar1.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar2.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar3.setBackgroundResource(R.drawable.ic_complet_star)
                detailScreenStar4.setBackgroundResource(R.drawable.ic_complet_star)
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
}