package com.yogi.ecommerce.feature.home.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.models.BaseMdl
import com.yogi.ecommerce.core.models.CategoryItemMdl
import com.yogi.ecommerce.core.models.ProductPromoItemMdl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class HomeAdapter(val clickListener: ExAdapterListener) :
    ListAdapter<HomeAdapter.DataItem, RecyclerView.ViewHolder>(ExAdapterDiffCallback()) {
    private val TAG = HomeAdapter::class.java.simpleName

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    private var adapterScope = CoroutineScope(Dispatchers.Default)


    fun addHeaderAndSubmitList(list: List<BaseMdl>) {
        adapterScope.launch {
            val items =
                listOf(DataItem.Header(list[0].data.category)) + (list[0].data.productPromo?.map {
                    DataItem.ProductItem(it)
                }
                    ?: listOf())


            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.ivProduct)
        val tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
        val ivLove:ImageView = itemView.findViewById(R.id.ivLove)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_home,
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(
            item: ProductPromoItemMdl,
            clickListener: ExAdapterListener
        ) {

            Glide.with(itemView.context).load(item.imageUrl).into(img)
            tvTitle.text = item.title
            itemView.setOnClickListener {
                                clickListener.onClick(item)

            }

                if (item.loved==0){
                    ivLove.setImageResource(R.drawable.ic_favorite_border_24dp)
                }else{
                    ivLove.setImageResource(R.drawable.ic_favorite_24dp)
                }

        }


    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val rvCategory = itemView.findViewById<RecyclerView>(R.id.rvCategory)
        lateinit var catergoryAdapter: CategoryAdapter

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                return HeaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_header,
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(item: List<CategoryItemMdl>?) {


            catergoryAdapter = CategoryAdapter(CategoryAdapter.CategoryAdapterListener {

            })

            catergoryAdapter.submitList(item)

            val mLayoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            rvCategory.apply {
                layoutManager = mLayoutManager
                adapter = catergoryAdapter
            }
//            tvTitle.text = id.toString()

//            Glide.with(itemView.context).load(item.imageUrl).into(img)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (holder) {
            is ViewHolder -> {
                val item = getItem(position) as DataItem.ProductItem
                holder.bind(item.item, clickListener)
            }
            is HeaderViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item.categoryItemMdl)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ProductItem -> ITEM_VIEW_TYPE_ITEM
        }
    }


    class ExAdapterListener(val clickListener: (item: ProductPromoItemMdl) -> Unit) {
        fun onClick(item: ProductPromoItemMdl) = clickListener(item)
    }


    class ExAdapterDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    sealed class DataItem {
        data class ProductItem(val item: ProductPromoItemMdl) : DataItem() {
            override val id = item.id.toString().toInt()
        }

        data class Header(val categoryItemMdl: List<CategoryItemMdl>?) : DataItem() {
            override val id = Int.MAX_VALUE
        }

        abstract val id: Int
    }
}