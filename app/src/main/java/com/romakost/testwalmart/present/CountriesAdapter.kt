package com.romakost.testwalmart.present

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.romakost.testwalmart.databinding.CountryViewItemBinding

class CountriesAdapter(
    private val countries: MutableList<CountryPresentationModel> = mutableListOf()
): RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    internal fun updateCountriesList(list: List<CountryPresentationModel>) {
        with(countries) {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val binding = CountryViewItemBinding.inflate( LayoutInflater.from(parent.context), parent,  false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CountryViewHolder,
        position: Int
    ) {
        holder.bind(countries[position])
    }

    override fun getItemCount() =
        countries.size

    class CountryViewHolder(
        private val binding: CountryViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CountryPresentationModel) = with(binding) {
            tvNameRegion.text = item.nameAndRegion
            tvCapital.text = item.capital
            tvCode.text = item.code
        }
    }
}