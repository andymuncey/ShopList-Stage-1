package com.tinyappco.shoplist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tinyappco.shoplist.databinding.ActivityMainBinding
import java.io.Serializable

class ShoppingListItem(val name: String, var count: Int) : Serializable {

    var purchased : Boolean = false
}

lateinit var binding: ActivityMainBinding

private lateinit var layoutManager: RecyclerView.LayoutManager

private lateinit var adapter: RecyclerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        layoutManager = LinearLayoutManager(this)

        binding.rvShoppingList.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        binding.rvShoppingList.adapter = adapter
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK) {
            val newItem = it.data?.getSerializableExtra("item") as ShoppingListItem
            adapter.addItem(newItem)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_complete){
            adapter.removeFoundItems()
            return true
        }

        if (item.itemId == R.id.menu_insert){ addItem()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun addItem() {
        val intent = Intent(this,AddItemActivity::class.java)
        //startActivityForResult(intent,0)
        resultLauncher.launch(intent)
    }
}