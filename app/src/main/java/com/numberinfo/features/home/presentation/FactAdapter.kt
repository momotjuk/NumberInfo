package com.numberinfo.features.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.numberinfo.core.db.Numbers
import com.numberinfo.databinding.ItemFactBinding

class FactAdapter : RecyclerView.Adapter<FactAdapter.FactViewHolder>() {

    var onItemClick: ((Numbers) -> Unit)? = null

    var data: List<Numbers> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class FactViewHolder(val binding: ItemFactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFactBinding.inflate(inflater, parent, false)

        return FactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val item = data[position]

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }

        with(holder.binding) {
            numberTextView.text = item.number
            factTextView.text = item.fact
        }
    }
}