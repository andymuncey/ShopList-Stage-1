package com.tinyappco.shoplist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.tinyappco.shoplist.databinding.FragmentAddItemBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null


    interface AddItemFragmentListener {
        fun onItemAdded(item: ShoppingListItem)
    }

    private var addItemListener : AddItemFragmentListener? = null

    private lateinit var binding : FragmentAddItemBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is AddItemFragmentListener) {
            addItemListener = context as AddItemFragmentListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddItemBinding.inflate(inflater)
        return binding.root

    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment AddItemFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            AddItemFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }


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

//                    val intent = Intent()
//                    intent.putExtra("item", product)
//                    activity?.setResult(Activity.RESULT_OK, intent)
//                    activity?.finish()
//we have consumed (handled) this event (key press) return true
                }
            }
//we have not consumed this event (i.e. different key pressed or no valid pr oduct entered yet
            return false


        }
    }

}