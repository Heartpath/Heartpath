package com.zootopia.presentation.writeletter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSelectLetterPaperBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SelectLetterPaperFragment : BaseFragment<FragmentSelectLetterPaperBinding>(
    FragmentSelectLetterPaperBinding::bind,
    R.layout.fragment_select_letter_paper
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()
    private lateinit var pagerAdapter: LetterPaperViewPagerAdapter
    private var letterPaperList: MutableList<String> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initData()
        initViewPager()
        initCollecter()
        initClickListener()
    }

    fun initCollecter(){
        lifecycleScope.launch {
            writeLetterViewModel.letterPaperList.collectLatest {
                letterPaperList.clear()
                letterPaperList.addAll(it)
                pagerAdapter.notifyDataSetChanged()
            }
        }
    }

    fun initData() {
        writeLetterViewModel.getLetterPaperList()
    }

    fun initViewPager() = with(binding) {
        pagerAdapter = LetterPaperViewPagerAdapter(letterPaperList)
        viewPagerLetterPaper.adapter = (pagerAdapter)
    }

    fun initClickListener() = with(binding) {

    }

}