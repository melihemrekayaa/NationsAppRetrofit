package com.mobile.nationalized.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mobile.nationalized.R
import com.mobile.nationalized.databinding.FragmentFeedBinding
import com.mobile.nationalized.ui.adapter.CountryAdapter
import com.mobile.nationalized.ui.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : FeedViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_feed,container,false)
        binding.feedFragment = this

        viewModel.countries.observe(viewLifecycleOwner){
            val countryAdapter = CountryAdapter(requireContext(),it,viewModel)
            binding.countryAdapter = countryAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()



        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            countries.let {
                binding.countryList.visibility = View.VISIBLE
            }

        }

        viewModel.countryError.observe(viewLifecycleOwner){ error->
            error.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                }
                else{
                    binding.countryError.visibility = View.GONE
                }
            }
        }

        viewModel.countryLoading.observe(viewLifecycleOwner){ loading->
            loading.let {
                if (it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryError.visibility = View.GONE
                }
                else{
                    binding.countryLoading.visibility = View.GONE
                }
            }
        }
    }
}