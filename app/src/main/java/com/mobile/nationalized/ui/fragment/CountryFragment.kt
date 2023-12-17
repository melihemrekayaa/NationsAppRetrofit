package com.mobile.nationalized.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mobile.nationalized.R
import com.mobile.nationalized.ui.viewmodel.CountryViewModel
import com.mobile.nationalized.util.downloadFromUrl
import com.mobile.nationalized.util.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var viewModel: CountryViewModel
    private lateinit var countryName: TextView
    private lateinit var countryCapital: TextView
    private lateinit var countryCurrency: TextView
    private lateinit var countryLanguage: TextView
    private lateinit var countryRegion: TextView
    private lateinit var countryImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)

        countryName = view.findViewById(R.id.countryName)
        countryCapital = view.findViewById(R.id.countryCapital)
        countryCurrency = view.findViewById(R.id.countryCurrency)
        countryLanguage = view.findViewById(R.id.countryLanguage)
        countryRegion = view.findViewById(R.id.countryRegion)
        countryImage = view.findViewById(R.id.countryImage)


        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer{ country ->
            country?.let{
                countryName.text = country.countryName
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                countryRegion.text = country.countryRegion

                context?.let {

                countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
                }
            }
        })
    }
}