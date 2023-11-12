package com.zootopia.presentation.store.storeletterpapper

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.zootopia.domain.model.store.StoreLetterPaperDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentStoreLetterPapperBinding
import com.zootopia.presentation.store.StoreViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "StoreLetterPaperFragmen"
class StoreLetterPaperFragment : BaseFragment<FragmentStoreLetterPapperBinding>(
    FragmentStoreLetterPapperBinding::bind,
    R.layout.fragment_store_letter_papper
) {
    private lateinit var mainActivity: MainActivity
    private val storeViewModel: StoreViewModel by activityViewModels()
    private lateinit var storeLetterPaperAdapter: StoreLetterPaperAdapter
    private var storeLetterPaperList: MutableList<StoreLetterPaperDto> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerGridView()
        initData()
        initCollecter()
    }

    private fun initData() {
        storeViewModel.getStoreLetterPaperList()
    }

    private fun initRecyclerGridView() = with(binding) {
        storeLetterPaperAdapter = StoreLetterPaperAdapter(storeLetterPaperList)
        recyclerviewStoreLetterpapper.apply {
            adapter = storeLetterPaperAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun initCollecter() = with(binding) {
        lifecycleScope.launch {
            storeViewModel.storeLetterPaperList.collectLatest {
                Log.d(TAG, "initCollecter:${it.size}")
                storeLetterPaperList.clear()
                storeLetterPaperList.addAll(it)
                storeLetterPaperAdapter.notifyDataSetChanged()
            }
        }

    }
}