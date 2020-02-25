package com.yogi.ecommerce.feature.purchased

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.lifecycle.ViewModelProviders
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.helpers.PrefHelper
import com.yogi.ecommerce.core.models.ProductPromoItemMdl
import com.yogi.ecommerce.feature.detail.DetailActivity
import com.yogi.ecommerce.feature.search.ProductAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PurchasedFragment : Fragment() {

    private val purchasedViewModel: PurchasedViewModel by viewModel()
    private val prefHelper: PrefHelper by inject()

    var listData: List<ProductPromoItemMdl>? = listOf()
    lateinit var mAdapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_purchased, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listData = prefHelper.getHistory()

        initRv()

    }

    private fun initRv() {
        val mLayoutManager = LinearLayoutManager(activity)

        mAdapter = ProductAdapter(listData, ProductAdapter.ProductAdapterListener {
            DetailActivity.startThisActivity(activity!!, it)
        })
        mAdapter.submitList(listData)
        val dividerItemDecoration = DividerItemDecoration(
            rvProduct?.context,
            mLayoutManager.orientation
        )
        rvProduct?.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }
}