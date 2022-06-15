package com.example.beesapp.view

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beesapp.LOG_TAG
import com.example.beesapp.R
import com.example.beesapp.adapter.ItemAdapter
import com.example.beesapp.adapter.OnBreweryClickListener
import com.example.beesapp.model.Brewery
import com.example.beesapp.util.StateMapping
import com.example.beesapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*


internal const val HTTP_PREFIX = "http://www."
internal const val HTTPS_PREFIX = "httpS://www."

class HomeFragment : Fragment(R.layout.home_fragment), OnBreweryClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var itemsAdapter: ItemAdapter
    private var data = emptyList<Brewery>().toMutableList()
    private lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupSearch()
        setupStatesSpinner()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.breweryData.observe(viewLifecycleOwner) {
            data.clear()
            data.addAll(it)
            itemsAdapter = ItemAdapter(data, this)
            setupAdapter()
        }
    }

    private fun setupAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = itemsAdapter
    }

    override fun onBreweryClick(data: Brewery) {
        val action = DetailsFragmentDirections.homeToDetails()
        action.breweryListName = data.name
        action.breweryListType = data.brewery_type ?: ""
        action.breweryListRating = data.rating
        action.breweryListSite = data.website_url?.removePrefix(HTTP_PREFIX) ?: ""
        action.breweryListSite.removePrefix(HTTPS_PREFIX)
        val street = data.street ?: ""
        val city = data.city ?: ""
        val state = data.state
        action.breweryListAdress = "$street, $city, ${StateMapping.getStateAbbreviation(state)}"
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
        Log.i(LOG_TAG, state)
        viewModel.changeState(state)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}