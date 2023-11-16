package com.example.aston.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.aston.R
import com.example.aston.domain.Contact

class ContactListAdapter : ListAdapter<Contact, ContactViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 30
    }

    var onShopItemLongClickListener: ((Contact) -> Unit)? = null
    var onShopItemClickListener: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ContactViewHolder(view)
    }

    // Установить во viewHolder значения
    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        // получить shopItem по позиции
        val shopItem = getItem(position)

        viewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        viewHolder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()
    }

    // Вернуть значение в зависимости от того, кокой item_view используется
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }
}