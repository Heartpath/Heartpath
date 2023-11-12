package com.zootopia.presentation.store

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentStoreBinding
import com.zootopia.presentation.store.storecharacter.StoreCharacterFragment
import com.zootopia.presentation.store.storeletterpapper.StoreLetterPaperFragment

class StoreFragment : BaseFragment<FragmentStoreBinding>(
    FragmentStoreBinding::bind,
    R.layout.fragment_store
) {
    private lateinit var mainActivity: MainActivity
    private val storeViewModel: StoreViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
    }

    private fun initTabLayout() {
        val storeCharacterFragment = StoreCharacterFragment()
        val storeLetterFragment = StoreLetterPaperFragment()
        parentFragmentManager.beginTransaction().add(binding.framelayoutStoreContainer.id, storeCharacterFragment).commit()

        binding.tablayoutStoreTab.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    var position = tab?.position
                    var selectedFragment: Fragment = storeCharacterFragment

                    when (position) {
                        0 -> selectedFragment = storeCharacterFragment
                        else -> selectedFragment = storeLetterFragment
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(binding.framelayoutStoreContainer.id, selectedFragment).commit()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            }
        )

    }

}