package com.example.aston.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.aston.R
import com.example.aston.domain.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var contactListAdapter: ContactListAdapter

    private var screenMode = CreateContactActivity.MODE_UNKNOWN
    private var shopItemId: Int = Contact.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            // обновить список из другого потока
            contactListAdapter.submitList(it)
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_contact)
        val buttonDelete = findViewById<Button>(R.id.button_delete)
        val buttonCancel = findViewById<Button>(R.id.button_cancel)

        buttonAddItem.setOnClickListener {
            val intent = CreateContactActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)

        with(rvShopList) {
            contactListAdapter = ContactListAdapter()
            adapter = contactListAdapter

            recycledViewPool.setMaxRecycledViews(
                ContactListAdapter.VIEW_TYPE_ENABLED,
                ContactListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ContactListAdapter.VIEW_TYPE_DISABLED,
                ContactListAdapter.MAX_POOL_SIZE
            )
        }

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvShopList)
    }

    // Метод для свайпа
    private fun setupSwipeListener(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // получить текущий список через метод currentList
                val item = contactListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        contactListAdapter.onContactClickListener = {
            val intent = CreateContactActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        contactListAdapter.onContactLongClickListener = {
//            viewModel.changeEnableState(it)
        }
    }
}

// Расширение для MutableList для обмена элементов
fun <T> MutableList<T>.swap(from: Int, to: Int) {
    val temp = this[from]
    this[from] = this[to]
    this[to] = temp
}