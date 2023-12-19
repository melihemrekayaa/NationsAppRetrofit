package com.mobile.nationalized.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.mobile.nationalized.R
import com.mobile.nationalized.databinding.FragmentCountryBinding
import com.mobile.nationalized.ui.viewmodel.CountryViewModel
import com.mobile.nationalized.util.downloadFromUrl
import com.mobile.nationalized.util.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var viewModel: CountryViewModel
    private lateinit var binding: FragmentCountryBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : CountryViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        binding.countryFragment = this


        val bundle: CountryFragmentArgs by navArgs()
        val takenCountry = bundle.country
        binding.countryObject = takenCountry


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromRoom(countryUuid)

    }

    fun getDataFromRoom(uuid: Int){

        viewModel.getDataFromRoom(uuid)

    }

}