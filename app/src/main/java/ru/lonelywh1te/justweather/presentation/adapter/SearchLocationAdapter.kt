package ru.lonelywh1te.justweather.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lonelywh1te.justweather.databinding.ItemLocationBinding
import ru.lonelywh1te.justweather.domain.models.SearchLocation

class SearchLocationAdapter(
    private val onLocationClick: (SearchLocation) -> Unit
): RecyclerView.Adapter<SearchLocationViewHolder>() {
    private var list = listOf<SearchLocation>()

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

    fun submit(list: List<SearchLocation>) {
        this.list = list
        notifyDataSetChanged()
    }
}

class SearchLocationViewHolder(private val binding: ItemLocationBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(location: SearchLocation) {
        binding.tvName.text = location.name
        binding.tvRegion.text = location.region
    }
}