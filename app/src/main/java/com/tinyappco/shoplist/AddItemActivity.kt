package com.tinyappco.shoplist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.tinyappco.shoplist.databinding.ActivityAddItemBinding



class AddItemActivity : AppCompatActivity() , AddItemFragment.AddItemFragmentListener{

    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }

    override fun onItemAdded(item: ShoppingListItem) {
        val intent = Intent()
        intent.putExtra("item", item)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}