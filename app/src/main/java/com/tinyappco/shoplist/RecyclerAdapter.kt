package com.tinyappco.shoplist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tinyappco.shoplist.databinding.CardLayoutBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var list = mutableListOf<ShoppingListItem>()

    init{
        list.add(ShoppingListItem("bread",2))
        val cheese = ShoppingListItem("cheese",1)
        cheese.purchased = true
        list.add(cheese)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = CardLayoutBinding.bind(itemView)

        init{
            itemView.setOnClickListener {
                list[bindingAdapterPosition].purchased = !list[bindingAdapterPosition].purchased
                binding.tvProduct.toggleStrikeThrough(list[bindingAdapterPosition].purchased )
                binding.tvCount.toggleStrikeThrough(list[bindingAdapterPosition].purchased)

        }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.binding
        val item = list[position]
        cardView.tvCount.text = item.count.toString()
        cardView.tvProduct.text = item.name

        if (item.purchased){
            cardView.tvProduct.toggleStrikeThrough(true)
            cardView.tvCount.toggleStrikeThrough(true)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}