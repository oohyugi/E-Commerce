package com.yogi.ecommerce.feature.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.models.CategoryItemMdl

class CategoryAdapter(val listener: CategoryAdapterListener) :
    ListAdapter<CategoryItemMdl, CategoryAdapter.ViewHolder>(DiffUtilsCategoryAdapter()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.bind(data, listener)


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivCategory = itemView.findViewById<ImageView>(R.id.ivCategory)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                return ViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_category, parent, false)
                )
            }

        }

        fun bind(
            data: CategoryItemMdl?,
            listener: CategoryAdapterListener
        ) {

            tvTitle.text = data?.name
            Glide.with(itemView.context).load(data?.imageUrl).into(ivCategory)

        }

    }

    class DiffUtilsCategoryAdapter : DiffUtil.ItemCallback<CategoryItemMdl>() {
        override fun areItemsTheSame(oldItem: CategoryItemMdl, newItem: CategoryItemMdl): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryItemMdl, newItem: CategoryItemMdl): Boolean {
            return oldItem == newItem

        }

    }

    class CategoryAdapterListener(val clickListener: (item: CategoryItemMdl?) -> Unit) {
        fun onItemClickListener(item: CategoryItemMdl?) = clickListener(item)
    }


}