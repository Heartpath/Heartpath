package com.zootopia.presentation.pointhistory

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
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
        pointHistoryAdapter = PointHistoryAdapter()
        recyclerviewPointHistory.apply {
            adapter = pointHistoryAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}