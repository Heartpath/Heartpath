package com.zootopia.presentation.pointhistory

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zootopia.domain.model.user.PointDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentPointHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PointHistoryFragment : BaseFragment<FragmentPointHistoryBinding>(
    FragmentPointHistoryBinding::bind,
    R.layout.fragment_point_history
) {
    private lateinit var mainActivity: MainActivity
    private val pointHistoryViewModel: PointHistoryViewModel by viewModels()
    private lateinit var pointHistoryAdapter: PointHistoryAdapter
    private var pointHistoryList: MutableList<PointDto> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initCollector()
        initView()
        initAdapter()
    }

    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathPointHistory.apply {
            textviewCurrentPageTitle.text = resources.getString(R.string.toolbar_point_history_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initAdapter() = with(binding) {
        pointHistoryAdapter = PointHistoryAdapter(list = pointHistoryList)
        recyclerviewPointHistory.apply {
            adapter = pointHistoryAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initCollector() {
        // list 값 저장
        lifecycleScope.launch {
            pointHistoryViewModel.pointInfoList.collect {value ->
                pointHistoryList.clear()
                pointHistoryList.addAll(value)
            }
        }
    }
    private fun initData() {
        pointHistoryViewModel.getPointInfoList()    // 포인트 관련 내역 list get api 호출
    }

    companion object {
        private const val TAG = "PointHistoryFragment_HP"
    }
}