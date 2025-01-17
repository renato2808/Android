package com.example.beesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beesapp.R
import com.example.beesapp.model.Brewery
import com.example.beesapp.model.BreweryRating
import com.example.beesapp.view.HomeFragment

class ItemAdapter(
    private var dataset: List<Brewery>,
    private var ratingData: List<BreweryRating>,
    fragment: HomeFragment
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val listener: OnBreweryClickListener = fragment

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val breweryButton: View = view.findViewById(R.id.row_layout)
        val breweryViewInitial: TextView = view.findViewById(R.id.listItemInitial)
        val breweryViewName: TextView = view.findViewById(R.id.listItemName)
        val breweryViewType: TextView = view.findViewById(R.id.listItemType)
        val breweryViewRating: TextView = view.findViewById(R.id.listItemRating)
        val star1: ImageView = view.findViewById(R.id.listStar1)
        val star2: ImageView = view.findViewById(R.id.listStar2)
        val star3: ImageView = view.findViewById(R.id.listStar3)
        val star4: ImageView = view.findViewById(R.id.listStar4)
        val star5: ImageView = view.findViewById(R.id.listStar5)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        val item = dataset[position]
        val rating = ratingData[position].rating
        val nRatings = ratingData[position].nRatings

        with(holder) {
            breweryViewInitial.text = item.name[0].toString()
            breweryViewName.text = item.name
            breweryViewType.text = item.brewery_type ?: ""
            breweryViewRating.text = String.format("%.1f", rating)

            when (rating) {
                in 1f..1.9f -> star1.setBackgroundResource(R.drawable.ic_complet_star)
                in 2f..2.9f -> {
                    star1.setBackgroundResource(R.drawable.ic_complet_star)
                    star2.setBackgroundResource(R.drawable.ic_complet_star)
                }
                in 3f..3.9f -> {
                    star1.setBackgroundResource(R.drawable.ic_complet_star)
                    star2.setBackgroundResource(R.drawable.ic_complet_star)
                    star3.setBackgroundResource(R.drawable.ic_complet_star)
                }
                in 4f..4.9f -> {
                    star1.setBackgroundResource(R.drawable.ic_complet_star)
                    star2.setBackgroundResource(R.drawable.ic_complet_star)
                    star3.setBackgroundResource(R.drawable.ic_complet_star)
                    star4.setBackgroundResource(R.drawable.ic_complet_star)
                }
                5f -> {
                    star1.setBackgroundResource(R.drawable.ic_complet_star)
                    star2.setBackgroundResource(R.drawable.ic_complet_star)
                    star3.setBackgroundResource(R.drawable.ic_complet_star)
                    star4.setBackgroundResource(R.drawable.ic_complet_star)
                    star5.setBackgroundResource(R.drawable.ic_complet_star)
                }
            }

            breweryButton.setOnClickListener {
                listener.onBreweryClick(
                    Brewery(
                        item.name,
                        item.brewery_type,
                        item.website_url,
                        item.street,
                        item.city,
                        item.state
                    ), rating, nRatings
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun filter(query: String) {
        val lowerCaseQuery = query.lowercase()
        val filteredModelList: MutableList<Brewery> = ArrayList()
        for (model in dataset) {
            val text: String = model.name.lowercase()
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model)
            }
        }
        dataset = filteredModelList
        notifyDataSetChanged()
    }

    fun restoreData(data: MutableList<Brewery>) {
        dataset = data
        notifyDataSetChanged()
    }
}