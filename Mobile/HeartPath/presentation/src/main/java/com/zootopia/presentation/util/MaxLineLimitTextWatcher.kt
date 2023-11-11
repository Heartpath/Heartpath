package com.zootopia.presentation.util

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText

private const val TAG = "MaxLineLimitTextWatcher"
class MaxLineLimitTextWatcher(val limitCount: Int, val editText: EditText) : TextWatcher{

    private var lastValue = ""
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 입력 변화 반영 전 처리
        lastValue = p0.toString()
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 변화와 동시 처리

    }

    override fun afterTextChanged(p0: Editable?) { // 입력 후 처리
        if(editText.lineCount > limitCount){
            var selectionStart = editText.selectionStart -1
            editText.setText(lastValue)
            if(selectionStart >= editText.length()){
                selectionStart = editText.length()
            }
            editText.setSelection(selectionStart)
        }
    }
}