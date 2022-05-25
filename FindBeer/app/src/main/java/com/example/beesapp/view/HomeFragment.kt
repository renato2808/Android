package com.example.beesapp.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beesapp.R
import com.example.beesapp.adapter.ItemAdapter
import com.example.beesapp.adapter.OnBreweryClickListener
import com.example.beesapp.model.Brewery
import com.example.beesapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(R.layout.home_fragment), OnBreweryClickListener {

    private lateinit var itemsAdapter: ItemAdapter
    private var data = emptyList<Brewery>().toMutableList()
    private lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.breweryData.observe(viewLifecycleOwner) {
            data.clear()
            data.addAll(it)
            itemsAdapter = ItemAdapter(data, this)
            setupAdapter()
        }
        setupSearch()
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
        action.breweryListSite = data.website_url ?: ""
        action.breweryListAdress = data.street ?: ""
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
}