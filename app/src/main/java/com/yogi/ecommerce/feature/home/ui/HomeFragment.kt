package com.yogi.ecommerce.feature.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.base.BaseViewState
import com.yogi.ecommerce.core.helpers.PrefHelper
import com.yogi.ecommerce.feature.detail.DetailActivity
import com.yogi.ecommerce.feature.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.loading_state.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var homeAdapter: HomeAdapter

    private val prefHelper: PrefHelper by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvName?.text = "Hello, ${prefHelper.getUser()}"
        initListHome()
        initObserve()

    }

    private fun initListHome() {
        homeAdapter = HomeAdapter(HomeAdapter.ExAdapterListener {

            homeViewModel.onUserClickItemProduct(it)
        })
        val mLayoutManager = LinearLayoutManager(activity)
        rvHome?.apply {
            layoutManager = mLayoutManager
            adapter = homeAdapter
        }
    }

    private fun initObserve() {
        homeViewModel.apply {
            responseDataHome.observe(this@HomeFragment, Observer { result->

                when(result){
                    is BaseViewState.Loading-> loadingState?.visibility = View.VISIBLE
                    is BaseViewState.Success-> {
                        loadingState?.visibility = View.GONE
                        result.data?.let {data->
                            homeAdapter.addHeaderAndSubmitList(data)
                            etSearch?.visibility = View.VISIBLE
                            etSearch?.setOnClickListener {
                                SearchActivity.startThisActivity(activity!!,Gson().toJson(data[0].data.productPromo))
                            }
                        }

                    }
                    is BaseViewState.Error-> loadingState?.visibility = View.GONE
                }
            })
            navigateToDetail.observe(this@HomeFragment, Observer {
                activity?.let { it1 -> DetailActivity.startThisActivity(it1,it) }
            })
        }
    }
}