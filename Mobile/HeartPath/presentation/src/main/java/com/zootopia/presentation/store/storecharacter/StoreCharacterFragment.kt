package com.zootopia.presentation.store.storecharacter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentStoreCharacterBinding
import com.zootopia.presentation.store.StoreViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StoreCharacterFragment : BaseFragment<FragmentStoreCharacterBinding>(
    FragmentStoreCharacterBinding::bind,
    R.layout.fragment_store_character
) {
    private lateinit var mainActivity: MainActivity
    private val storeViewModel: StoreViewModel by activityViewModels()
    private lateinit var storeCharacterAdapter: StoreCharacterAdapter
    private var storeCharacterList: MutableList<StoreCharacterDto> = mutableListOf()

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
        storeViewModel.getStoreCharacterList()
    }

    private fun initRecyclerGridView() = with(binding) {
        storeCharacterAdapter = StoreCharacterAdapter(storeCharacterList)
        recyclerviewStoreCharacter.apply {
            adapter = storeCharacterAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun initCollecter() = with(binding) {
        lifecycleScope.launch {
            storeViewModel.storeCharacterList.collectLatest {
                storeCharacterList.clear()
                storeCharacterList.addAll(it)
                storeCharacterAdapter.notifyDataSetChanged()
            }
        }

    }
}