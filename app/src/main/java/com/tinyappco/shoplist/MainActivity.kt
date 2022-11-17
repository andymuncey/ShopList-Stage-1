package com.tinyappco.shoplist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import com.tinyappco.shoplist.databinding.ActivityMainBinding
import java.io.Serializable

class ShoppingListItem(val name: String, var count: Int) : Serializable {

    var purchased : Boolean = false
}



class MainActivity : AppCompatActivity() {

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



//    private fun loadList() {
//        try {
//            val fileInputStream = openFileInput("list.dat")
//            val objectInputStream = ObjectInputStream(fileInputStream)
//
//            @Suppress("UNCHECKED_CAST")
//            val list = objectInputStream.readObject() as? MutableList<ShoppingListItem>
//            if (list != null) {
//                listFrag.adapter.list = list
//            }
//            objectInputStream.close()
//            fileInputStream.close()
//        } catch (e: java.io.FileNotFoundException) {
//            //loading has failed, probably first run
//            Toast.makeText(this, "No existing list found", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_complete){
            listFrag.adapter.removeFoundItems()
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