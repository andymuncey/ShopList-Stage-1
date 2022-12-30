package com.tinyappco.shoplist

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import com.tinyappco.shoplist.databinding.ActivityMainBinding
import java.io.Serializable

class ShoppingListItem(val name: String, var count: Int) : Serializable {

    var purchased : Boolean = false
}

class MainActivity : AppCompatActivity(), AddItemFragment.AddItemFragmentListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var listFrag: ShopListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        listFrag = supportFragmentManager.findFragmentById(R.id.frList) as ShopListFragment

    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK) {
            val newItem = it.data?.getSerializableExtra("item") as ShoppingListItem
            listFrag.adapter.addItem(newItem)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_complete){
            listFrag.adapter.removeFoundItems()
            return true
        }

        if (item.itemId == R.id.menu_insert){ addItem()
            return true
        }

        if  (item.itemId == R.id.action_settings){
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        if (isLandscape()){
            binding.toolbar.menu.findItem(R.id.menu_insert).isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun addItem() {
        val intent = Intent(this,AddItemActivity::class.java)
        resultLauncher.launch(intent)
    }

    override fun onItemAdded(item: ShoppingListItem) {
        listFrag.adapter.addItem(item)

        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }


}