package com.mobile.nationalized.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mobile.nationalized.R
import com.mobile.nationalized.data.entity.Country
import com.mobile.nationalized.ui.fragment.FeedFragmentDirections
import com.mobile.nationalized.util.downloadFromUrl
import com.mobile.nationalized.util.placeHolderProgressBar

class CountryAdapter(var countryList : ArrayList<Country>)
    : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }
    override fun getItemCount(): Int {
        return countryList.size
    }


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.name).text = countryList[position].countryName
        holder.view.findViewById<TextView>(R.id.region).text = countryList[position].countryRegion

        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.findViewById<ImageView>(R.id.imageView).downloadFromUrl(countryList[position].imageUrl!! ,placeHolderProgressBar(holder.view.context))



    }
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}