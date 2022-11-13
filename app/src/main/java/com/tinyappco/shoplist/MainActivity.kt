package com.tinyappco.shoplist

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tinyappco.shoplist.databinding.ActivityMainBinding
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
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

        handleDragging()
        loadList()
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK) {
            val newItem = it.data?.getSerializableExtra("item") as ShoppingListItem
            adapter.addItem(newItem)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
       saveList()
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onPause() {
        saveList()
        super.onPause()
    }

    private fun saveList(){
        val fileOutputStream = openFileOutput("list.dat", Context.MODE_PRIVATE)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(adapter.list)
        objectOutputStream.close()
        fileOutputStream.close()
    }

    private fun loadList() {
        try {
            val fileInputStream = openFileInput("list.dat")
            val objectInputStream = ObjectInputStream(fileInputStream)

            @Suppress("UNCHECKED_CAST")
            val list = objectInputStream.readObject() as? MutableList<ShoppingListItem>
            if (list != null) {
                adapter.list = list
            }
            objectInputStream.close()
            fileInputStream.close()
        } catch (e: java.io.FileNotFoundException) {
            //loading has failed, probably first run
            Toast.makeText(this, "No existing list found", Toast.LENGTH_LONG).show()
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

    private fun handleDragging(){
        val dragCallback = DragCallback()
        val touchHelper = ItemTouchHelper(dragCallback)
        touchHelper.attachToRecyclerView(binding.rvShoppingList)
    }


    inner class DragCallback : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            return makeMovementFlags(dragFlags, 0)

                 }
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

            adapter.notifyItemMoved(viewHolder.bindingAdapterPosition,target.bindingAdapterPosition)
            return true
                 }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                 } }



}