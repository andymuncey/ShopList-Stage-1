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

        binding.etItem.requestFocus()
        //display keyboard
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun validProductName() : Boolean {
        return binding.etItem.text.isNotEmpty()
    }
    private fun productCount() : Int {
        return binding.etCount.text.toString().toIntOrNull() ?: 1
    }


    private fun incrementCount() {
        var userCount = binding.etCount.text.toString().toIntOrNull() ?:1
        userCount += 1
        binding.etCount.setText("$userCount")
    }

    inner class EnterHandler : TextView.OnEditorActionListener{
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

            //user has pressed tick button on soft keyboard, or pressed enter key
            if (actionId == EditorInfo.IME_ACTION_DONE || KeyEvent.KEYCODE_ENTER == event?.keyCode) {
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
//we have not consumed this event (i.e. different key pressed or no valid product entered yet
            return false


        }
    }
}