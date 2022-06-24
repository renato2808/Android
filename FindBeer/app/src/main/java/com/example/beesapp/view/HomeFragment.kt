package com.example.beesapp.view

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beesapp.FindBeerApplication
import com.example.beesapp.LOG_TAG
import com.example.beesapp.R
import com.example.beesapp.adapter.ItemAdapter
import com.example.beesapp.adapter.OnBreweryClickListener
import com.example.beesapp.model.Brewery
import com.example.beesapp.model.BreweryRating
import com.example.beesapp.util.StateMapping
import com.example.beesapp.viewmodel.HomeViewModel
import com.example.beesapp.viewmodel.HomeViewModelFactory
import kotlinx.android.synthetic.main.home_fragment.*


internal const val HTTP_PREFIX = "http://www."
internal const val HTTPS_PREFIX = "httpS://www."

class HomeFragment : Fragment(R.layout.home_fragment), OnBreweryClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var itemsAdapter: ItemAdapter
    private var data = emptyList<Brewery>().toMutableList()
    private var ratingData = emptyList<BreweryRating>().toMutableList()
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((this.context?.applicationContext as FindBeerApplication).repository,
            this.context?.applicationContext as FindBeerApplication
        )
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsAdapter = ItemAdapter(data, ratingData,this)
        setupViewModel()
        setupSearch()
        setupStatesSpinner()
    }

    private fun setupViewModel() {
        viewModel.breweryData.observe(viewLifecycleOwner) {
            data.clear()
            data.addAll(it)
            ratingData.clear()
            viewModel.breweryRatingData.value?.forEach { rating->
                ratingData.add(rating)
            }
            itemsAdapter = ItemAdapter(data, ratingData, this)
            setupAdapter()
        }
    }

    private fun setupAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = itemsAdapter
    }

    override fun onBreweryClick(brewery: Brewery, rating: Float, nRatings: Int) {
        val action = DetailsFragmentDirections.homeToDetails()
        action.breweryListName = brewery.name
        action.breweryListType = brewery.brewery_type ?: ""
        action.breweryListRating = rating
        action.breweryListNumberOfRatings = nRatings
        action.breweryListSite = brewery.website_url?.removePrefix(HTTP_PREFIX) ?: ""
        action.breweryListSite.removePrefix(HTTPS_PREFIX)
        val street = brewery.street ?: ""
        val city = brewery.city ?: ""
        val state = brewery.state
        action.breweryListAddress = "$street, $city, ${StateMapping.getStateAbbreviation(state)}"
        findNavController().navigate(action)
    }

    private fun setupSearch() {
        val id: Int = brewerySearch.context.resources.getIdentifier("android:id/search_src_text", null, null)
        brewerySearch.findViewById<TextView>(id).setTextColor(Color.BLACK)
        brewerySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    itemsAdapter.filter(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != "") {
                    itemsAdapter.filter(newText)
                } else if (newText == "") {
                    itemsAdapter.restoreData(data)
                } else {
                    itemsAdapter.restoreData(data)
                }
                return true
            }
        })
    }

    private fun setupStatesSpinner() {
        val spinnerArrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                StateMapping.states.keys.toList()
            )
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        states_spinner.adapter = spinnerArrayAdapter
        states_spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val state = StateMapping.states.keys.toList()[position]
        viewModel.changeState(state)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}