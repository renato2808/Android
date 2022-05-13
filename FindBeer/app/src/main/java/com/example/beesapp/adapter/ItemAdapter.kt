package com.example.beesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beesapp.R
import com.example.beesapp.model.Brewery
import com.example.beesapp.view.HomeFragment
import java.util.*

class ItemAdapter(private var dataset: MutableList<Brewery>, fragment: HomeFragment) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder,
                                  position: Int) {
        val item = dataset[position]

        holder.breweryViewInitial.text = item.name[0].toString()
        holder.breweryViewName.text = item.name
        holder.breweryViewType.text = item.type
        holder.breweryViewRating.text = item.rating.toString()

        when (item.rating){
            in 1f..1.9f -> holder.star1.setBackgroundResource(R.drawable.ic_complet_star)
            in 2f..2.9f -> {
                holder.star1.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star2.setBackgroundResource(R.drawable.ic_complet_star)
            }
            in 3f..3.9f -> {
                holder.star1.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star2.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star3.setBackgroundResource(R.drawable.ic_complet_star)
            }
            in 4f..4.9f -> {
                holder.star1.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star2.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star3.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star4.setBackgroundResource(R.drawable.ic_complet_star)
            }
            5f -> {
                holder.star1.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star2.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star3.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star4.setBackgroundResource(R.drawable.ic_complet_star)
                holder.star5.setBackgroundResource(R.drawable.ic_complet_star)
            }
        }

        holder.breweryButton.setOnClickListener {
            listener.onBreweryClick(Brewery(item.name, item.type, item.rating, item.site, item.address))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun filter(query: String){
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

    fun restoreData(data: MutableList<Brewery>){
        dataset = data
        notifyDataSetChanged()
    }
}