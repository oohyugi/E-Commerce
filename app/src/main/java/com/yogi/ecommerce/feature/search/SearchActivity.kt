package com.yogi.ecommerce.feature.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yogi.ecommerce.MainActivity
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.base.BaseActivity
import com.yogi.ecommerce.core.models.ProductPromoItemMdl
import com.yogi.ecommerce.core.utils.showKeyboard
import com.yogi.ecommerce.feature.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    companion object{
        val EXTRA_DATA = "data"
        fun startThisActivity(context: Context,data:String){
            val intent  = Intent(context, SearchActivity::class.java)
            intent.putExtra(EXTRA_DATA,data)
            context.startActivity(intent)


        }
    }

    var listData: List<ProductPromoItemMdl>? = listOf()
    lateinit var mAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val dataStr = intent.getStringExtra(EXTRA_DATA)

        val jsonType = object :TypeToken<List<ProductPromoItemMdl>>(){}.type

        listData = Gson().fromJson<List<ProductPromoItemMdl>>(dataStr,jsonType)
        initRv()
        etSearch?.requestFocus()

        etSearch?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()){
                    mAdapter.filter.filter(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })


    }

    private fun initRv() {
        val mLayoutManager = LinearLayoutManager(this)

        mAdapter = ProductAdapter(listData,ProductAdapter.ProductAdapterListener {
            DetailActivity.startThisActivity(this,it)
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
