package com.example.beesapp.view

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beesapp.R
import com.example.beesapp.adapter.ItemAdapter
import com.example.beesapp.adapter.OnBreweryClickListener
import com.example.beesapp.data.DataSource
import com.example.beesapp.model.Brewery
import com.example.beesapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(R.layout.home_fragment), OnBreweryClickListener {

    private lateinit var itemsAdapter: ItemAdapter
    private val data = DataSource().loadBreweries()

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDataSource()
        setupAdapter()
        setupSearch()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun addDataSource() {
        itemsAdapter = ItemAdapter(data, this)
    }

    private fun setupAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = itemsAdapter
    }

    override fun onBreweryClick(data: Brewery) {
        val action = DetailsFragmentDirections.homeToDetails()
        action.breweryListName = data.name
        action.breweryListType = data.type
        action.breweryListRating = data.rating
        action.breweryListSite = data.site
        action.breweryListAdress = data.address
        findNavController().navigate(action)
    }

    private fun setupSearch() {
        brewerySearch.queryHint = "Buscar Local";
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
                }
                else if (newText == ""){
                    itemsAdapter.restoreAdapter(data)
                }
                else {
                    itemsAdapter.restoreAdapter(data)
                }
                return true
            }
        })
    }
}