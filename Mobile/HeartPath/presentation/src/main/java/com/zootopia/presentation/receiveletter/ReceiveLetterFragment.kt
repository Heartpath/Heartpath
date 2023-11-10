package com.zootopia.presentation.receiveletter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zootopia.domain.model.letter.ReceiveLetterDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentReceiveLetterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "ReceiveLetterFragment_HeartPath"

@AndroidEntryPoint
class ReceiveLetterFragment :
    BaseFragment<FragmentReceiveLetterBinding>(
        FragmentReceiveLetterBinding::bind,
        R.layout.fragment_receive_letter
    ) {

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private lateinit var receiveLetterAdapter: ReceiveLetterAdapter
    private val receiveLetterViewModel: ReceiveLetterViewModel by viewModels()
    private val letterList: MutableList<ReceiveLetterDto> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initData()
        initCollect()
        initView()
        initAdapter()
    }

    private fun initAdapter() = with(binding) {
        receiveLetterAdapter = ReceiveLetterAdapter(list = letterList).apply {
            itemClickListener = object : ReceiveLetterAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int) {
                    Log.d(TAG, "itemClick: 편지 리스트 클릭됨 $position")
                    findNavController().navigate(R.id.action_receiveLetterFragment_to_readLetterFragment)
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
    private fun initData() {
        receiveLetterViewModel.getStoredLetterList()
    }
    private fun initCollect() {
        lifecycleScope.launch {
            receiveLetterViewModel.storedLetterList.collect {value ->
                letterList.clear()
                letterList.addAll(value)
                receiveLetterAdapter
            }
        }
    }
}
