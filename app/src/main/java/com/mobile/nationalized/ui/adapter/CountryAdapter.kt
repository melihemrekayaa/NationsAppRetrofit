package com.mobile.nationalized.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mobile.nationalized.R
import com.mobile.nationalized.data.entity.Country
import com.mobile.nationalized.databinding.ItemCountryBinding
import com.mobile.nationalized.ui.fragment.FeedFragmentDirections
import com.mobile.nationalized.ui.viewmodel.FeedViewModel


class CountryAdapter(var context: Context, var countryList : List<Country>, var viewModel: FeedViewModel)
    : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(var design: ItemCountryBinding) : RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val binding : ItemCountryBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.item_country,parent,false)
        return CountryViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return countryList.size
    }


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList.get(position)
        val design = holder.design

        design.countryObject = country


        design.viewRow.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(country = country)
            Navigation.findNavController(it).navigate(action)
        }

    }

}