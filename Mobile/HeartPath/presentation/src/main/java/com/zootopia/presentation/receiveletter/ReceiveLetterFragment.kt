package com.zootopia.presentation.receiveletter

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
import com.zootopia.presentation.databinding.FragmentReceiveLetterBinding

private const val TAG = "ReceiveLetterFragment_HeartPath"

class ReceiveLetterFragment :
    BaseFragment<FragmentReceiveLetterBinding>(
        FragmentReceiveLetterBinding::bind,
        R.layout.fragment_receive_letter
    ) {

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private lateinit var receiveLetterAdapter: ReceiveLetterAdapter

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
        receiveLetterAdapter = ReceiveLetterAdapter().apply {
            itemClickListener = object : ReceiveLetterAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int) {
                    Log.d(TAG, "itemClick: 편지 리스트 클릭됨 $position")
                }
            }
        }

        recyclerviewReceiveLetter.apply {
            adapter = receiveLetterAdapter
            layoutManager = GridLayoutManager(mainActivity, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathReceiveLetter.apply {
            textviewCurrentPageTitle.text =
                resources.getString(R.string.toolbar_receive_letter_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
