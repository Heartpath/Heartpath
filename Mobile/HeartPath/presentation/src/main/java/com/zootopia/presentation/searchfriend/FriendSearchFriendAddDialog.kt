package com.zootopia.presentation.searchfriend

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zootopia.presentation.R
import com.zootopia.presentation.databinding.DialogBgmSettingBinding
import com.zootopia.presentation.databinding.DialogFriendAddBinding

class FriendSearchFriendAddDialog(context: Context): DialogFragment()  {
    private lateinit var binding: DialogFriendAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true // 화면 밖에 클릭하면 dismiss 되도록
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogFriendAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonFriendAddCancel.setOnClickListener {
                dismiss()
            }
            buttonFriendAddAccept.setOnClickListener {
                // TODO: 친구 추가
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setGravity(Gravity.BOTTOM)
        return dialog
    }

    override fun onResume() {
        super.onResume()
        val parentWidth = resources.displayMetrics.widthPixels
        val size = parentWidth - (parentWidth/10)
        Log.d(TAG, "onCreateDialog: $parentWidth")
        dialog?.window?.setLayout(size, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.custom_round_dialog_view)
    }
    companion object {
        const val TAG = "FriendSearchFriendAddDi"
    }
}