package com.zootopia.presentation.pointhistory

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentPointHistoryBinding

class PointHistoryFragment : BaseFragment<FragmentPointHistoryBinding>(
    FragmentPointHistoryBinding::bind,
    R.layout.fragment_point_history
) {
    private lateinit var pointHistoryAdapter: PointHistoryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }
    private fun initAdapter() = with(binding){
        pointHistoryAdapter = PointHistoryAdapter()
        recyclerviewPointHistory.apply {
            adapter = pointHistoryAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}