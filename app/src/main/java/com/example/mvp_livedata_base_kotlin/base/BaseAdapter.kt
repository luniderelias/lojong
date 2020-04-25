package com.example.mvp_livedata_base_kotlin.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter<T>.Holder<T>>() {

    var onItemClick: ((T) -> Unit) = {}

    var onItemSelected: ((T, Int) -> Unit) = { t: T, i: Int -> }

    var data: List<T> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract val layoutResource: Int
    abstract fun bind(itemView: View, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(LayoutInflater.from(parent.context).inflate(layoutResource, parent, false), ::bind)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder<T>, position: Int) {
        holder.render(data[position], position)
    }

    open fun onItemClick(position: Int, item: T) {
        onItemClick.invoke(item)
        onItemSelected.invoke(item, position)
    }

    inner class Holder<T>(itemView: View, val bind: (View, T, Int) -> Unit) :
            RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if (adapterPosition >= 0 && adapterPosition < data.size)
                    onItemClick(adapterPosition, data[adapterPosition])
            }
        }

        fun render(item: T, position: Int) {
            bind(itemView, item, position)
        }
    }
}