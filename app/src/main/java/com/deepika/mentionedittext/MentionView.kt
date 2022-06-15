package com.deepika.mentionedittext

import android.R
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.widget.doAfterTextChanged


class MentionView : AppCompatEditText {
    private var statesPopupList: ListPopupWindow? = null
    private var adapter: ArrayAdapter<String>?=null
    val states= arrayListOf<String>(
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chhattisgarh",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jammu and Kashmir",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Punjab",
        "Rajasthan",
        "Sikkim",
        "Tamil Nadu",
        "Telangana",
        "Tripura",
        "Uttarakhand",
        "Uttar Pradesh",
        "West Bengal",
        "Andaman and Nicobar Islands",
        "Chandigarh",
        "Dadra and Nagar Haveli",
        "Daman and Diu",
        "Delhi",
        "Lakshadweep",
        "Puducherry"
    )

    private var filterStates=ArrayList<String>()
    private var map=HashMap<Int,Int>()
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        filterStates.addAll(states)
        var mentionString=""
        var startMention=false
        var mentionStartIndex=0
        doAfterTextChanged {
           if (it.toString().isNotEmpty()){
               val stringLength=it.toString().length
               val lastChar=it.toString()[stringLength-1]
               if (lastChar=='@'){
                   startMention=true
                   mentionStartIndex=stringLength
                   showPopup()
               }
               if (lastChar==' '){
                   startMention=false
               }
               if (startMention){
                   mentionString=it.toString().substring(mentionStartIndex)
                   if (mentionString.length>1){
                       filterStates(mentionString)
                   }
                   Log.d("mention", "init: $mentionString")
               }
           }
        }
    }

    private fun showPopup() {
        filterStates.clear()
        filterStates.addAll(states)
        map= HashMap<Int,Int>()
        statesPopupList = ListPopupWindow(context)
        adapter=ArrayAdapter<String>(
            context,
            R.layout.simple_spinner_item,
            filterStates
        )
        statesPopupList
        statesPopupList!!.anchorView = this //this let as set the popup below the EditText

        statesPopupList!!.setAdapter(adapter)
        statesPopupList!!.setOnItemClickListener { parent, view, position, id ->
            val seletedItem= if (map.isEmpty()) states[position] else states[map[position]!!]
            setText(seletedItem)
            setSelection(length())//we set the selected element in the EditText
            statesPopupList!!.dismiss()
        }
        statesPopupList!!.show()
    }

    private fun filterStates(mentionString: String){
        filterStates.clear()
        map= HashMap<Int,Int>()
        states.forEachIndexed {index,element->
            if (element.lowercase().contains(mentionString.lowercase())){
                filterStates.add(element)
                map[filterStates.lastIndex]=index
            }
        }
        adapter?.notifyDataSetChanged()
    }

}