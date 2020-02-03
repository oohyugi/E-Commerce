package com.yogi.ecommerce.feature.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.models.ProductPromoItemMdl

class ProductAdapter(private val baseList:List<ProductPromoItemMdl>?,val listener: ProductAdapterListener) :
    ListAdapter<ProductPromoItemMdl, ProductAdapter.ViewHolder>(DiffUtilsProductAdapter()),Filterable {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.bind(data, listener)


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivProduct = itemView.findViewById<ImageView>(R.id.ivProduct)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                return ViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_product, parent, false)
                )
            }

        }

        fun bind(
            data: ProductPromoItemMdl?,
            listener: ProductAdapterListener
        ) {

            tvTitle.text = data?.title
            tvPrice.text = data?.price
            Glide.with(itemView.context).load(data?.imageUrl).into(ivProduct)

            itemView.setOnClickListener {
                listener.onItemClickListener(data)
            }
        }

    }

    class DiffUtilsProductAdapter : DiffUtil.ItemCallback<ProductPromoItemMdl>() {
        override fun areItemsTheSame(oldItem: ProductPromoItemMdl, newItem: ProductPromoItemMdl): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductPromoItemMdl, newItem: ProductPromoItemMdl): Boolean {
            return oldItem == newItem

        }

    }

    class ProductAdapterListener(val clickListener: (item: ProductPromoItemMdl?) -> Unit) {
        fun onItemClickListener(item: ProductPromoItemMdl?) = clickListener(item)
    }

    var listFiltered = listOf<ProductPromoItemMdl>()
    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                listFiltered = if (charString.isEmpty()) {
                    baseList!!
                } else {
                    val filteredList = mutableListOf<ProductPromoItemMdl>()
                    for (row in baseList!!) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.title != null) {
                            if (row.title.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row)
                            }
                        }

                    }

                    filteredList
                }
//
                val filterResults = FilterResults()
                filterResults.values = listFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                listFiltered = filterResults.values as List<ProductPromoItemMdl>
//                submitList(null)
                submitList(listFiltered)
            }
        }
    }

}