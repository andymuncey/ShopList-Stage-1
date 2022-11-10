package com.tinyappco.shoplist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.tinyappco.shoplist.databinding.ActivityMainBinding
import java.io.Serializable

class ShoppingListItem(val name: String, var count: Int) : Serializable {

    var purchased : Boolean = false
}

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}