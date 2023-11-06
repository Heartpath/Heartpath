package com.zootopia.presentation.sendletter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSendLetterBinding

private const val TAG = "SendLetterFragment_HeartPart"

class SendLetterFragment :
    BaseFragment<FragmentSendLetterBinding>(
        FragmentSendLetterBinding::bind,
        R.layout.fragment_send_letter
    ) {

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private lateinit var sendLetterAdapter: SendLetterAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initView()
        initAdapter()
    }

    private fun initAdapter() = with(binding) {
        sendLetterAdapter = SendLetterAdapter().apply {
            itemClickListener = object : SendLetterAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int) {
                    Log.d(TAG, "itemClick: 편지 리스트 클릭됨 $position")
                }
            }
        }

        recyclerviewSendLetter.apply {
            adapter = sendLetterAdapter
            layoutManager = GridLayoutManager(mainActivity, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathSendLetter.apply {
            textviewCurrentPageTitle.text = resources.getString(R.string.toolbar_letter_storage_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
