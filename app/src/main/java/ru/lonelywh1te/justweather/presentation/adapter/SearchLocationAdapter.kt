package ru.lonelywh1te.justweather.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.justweather.databinding.ItemLocationBinding
import ru.lonelywh1te.justweather.domain.models.Location

class SearchLocationAdapter(
    private val onLocationClick: (Location) -> Unit
): RecyclerView.Adapter<SearchLocationViewHolder>() {
    private var list = listOf<Location>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchLocationViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchLocationViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SearchLocationViewHolder, position: Int) {
        val location = list[position]
        holder.itemView.setOnClickListener { onLocationClick(location) }
        holder.bind(location)
    }

    fun submit(list: List<Location>) {
        this.list = list
        notifyDataSetChanged()
    }
}

class SearchLocationViewHolder(private val binding: ItemLocationBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(location: Location) {
        binding.tvName.text = location.name
        binding.tvRegion.text = location.region
    }
}