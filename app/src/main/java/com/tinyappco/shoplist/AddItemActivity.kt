package com.tinyappco.shoplist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.tinyappco.shoplist.databinding.ActivityAddItemBinding



class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIncrementCount.setOnClickListener {
            incrementCount()
        }

        val enterHandler = EnterHandler()
        binding.etItem.setOnEditorActionListener(enterHandler)
        binding.etCount.setOnEditorActionListener(enterHandler)
    }

    private fun validProductName() : Boolean {
        return binding.etItem.text.length > 0
    }
    private fun productCount() : Int {
        val userCount = binding.etCount.text.toString().toIntOrNull()
        return if (userCount == null) 1 else userCount
    }


    private fun incrementCount() {
        var userCount = binding.etCount.text.toString().toIntOrNull()
        if (userCount == null) {
            userCount = 1
        }
        binding.etCount.setText((userCount + 1).toString())
    }

    inner class EnterHandler : TextView.OnEditorActionListener{
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

            //user has pressed tick button on soft keyboard, or pressed enter key
            if (actionId == EditorInfo.IME_ACTION_DONE || KeyEvent.KEYCODE_ENTER.equals (event?.keyCode)) {
                if (validProductName()) {
                    val product = ShoppingListItem(binding.etItem.text.toString(),productCount
                        ())
                    val intent = Intent()
                    intent.putExtra("item", product)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
//we have consumed (handled) this event (key press) return true
                }
            }
//we have not consumed this event (i.e. different key pressed or no valid pr oduct entered yet
            return false


        }
    }
}