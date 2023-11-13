package com.zootopia.presentation.store

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentStoreBinding
import com.zootopia.presentation.store.storecharacter.StoreCharacterFragment
import com.zootopia.presentation.store.storeletterpapper.StoreLetterPaperFragment
import com.zootopia.presentation.util.makeComma
import com.zootopia.presentation.writeletter.addletterimage.AddLetterImageFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        initCollect()
        initData()
        initView()
    }

    private fun initData(){
        storeViewModel.getUserInfo()
    }

    private fun initCollect() = with(binding){
        lifecycleScope.launch {
            storeViewModel.userInfo.collectLatest {
                textviewPoint.text = makeComma(it.point)
            }
        }
        lifecycleScope.launch {
            storeViewModel.isSendSuccess.collectLatest {
                if (it) {
                    storeViewModel.resetIsSendSuccess()
                    Toast.makeText(
                        mainActivity,
                        R.string.store_buy_success,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
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

    private fun initView() = with(binding) {
        toolbarHeartpathStore.apply {
            textviewCurrentPageTitle.text =
                resources.getString(R.string.toolbar_store_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}