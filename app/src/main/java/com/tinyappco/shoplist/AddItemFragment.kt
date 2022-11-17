package com.tinyappco.shoplist

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.tinyappco.shoplist.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {

    interface AddItemFragmentListener {
        fun onItemAdded(item: ShoppingListItem)
    }

    private var addItemListener : AddItemFragmentListener? = null

    private lateinit var binding : FragmentAddItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is AddItemFragmentListener) {
            addItemListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddItemBinding.inflate(inflater)
        return binding.root
    }


    private fun validProductName() : Boolean {
        return binding.etItem.text.length > 0
    }

    private fun productCount() : Int {
        val userCount = binding.etCount.text.toString().toIntOrNull()
        return if (userCount == null) 1 else userCount
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnIncrementCount.setOnClickListener {
            incrementCount()
        }

        val enterHandler = EnterHandler()
        binding.etItem.setOnEditorActionListener(enterHandler)
        binding.etCount.setOnEditorActionListener(enterHandler)

        binding.etItem.requestFocus()
        //display keyboard
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
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
                    val product = ShoppingListItem(binding.etItem.text.toString(),productCount())
                    addItemListener?.onItemAdded(product)
                    binding.etItem.text.clear()
                    binding.etCount.setText("1")
            //we have consumed (handled) this event (key press) return true
                }
            }
            //we have not consumed this event (i.e. different key pressed or no valid product entered yet
            return false
        }
    }

}