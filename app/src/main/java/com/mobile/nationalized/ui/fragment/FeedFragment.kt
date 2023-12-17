package com.mobile.nationalized.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mobile.nationalized.R
import com.mobile.nationalized.ui.adapter.CountryAdapter
import com.mobile.nationalized.ui.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private lateinit var countryAdapter : CountryAdapter

    private var recyclerView: RecyclerView? = null
    private var countryError: TextView? = null
    private var countryLoading: ProgressBar? = null
    private var swipeRefreshLayout: SwipeRefreshLayout ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        recyclerView = view.findViewById(R.id.countryList)
        countryError = view.findViewById(R.id.countryError)
        countryLoading = view.findViewById(R.id.countryLoading)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        countryAdapter = CountryAdapter(arrayListOf())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()

        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = countryAdapter

        swipeRefreshLayout?.setOnRefreshListener {
            recyclerView?.visibility = View.GONE
            countryError?.visibility = View.GONE
            countryLoading?.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout!!.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            countries.let {
                recyclerView?.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

        }

        viewModel.countryError.observe(viewLifecycleOwner){ error->
            error.let {
                if (it){
                    countryError?.visibility = View.VISIBLE
                }
                else{
                    countryError?.visibility = View.GONE
                }
            }
        }

        viewModel.countryLoading.observe(viewLifecycleOwner){ loading->
            loading.let {
                if (it){
                    countryLoading?.visibility = View.VISIBLE
                    recyclerView?.visibility = View.GONE
                    countryError?.visibility = View.GONE
                }
                else{
                    countryLoading?.visibility = View.GONE
                }
            }
        }
    }
}