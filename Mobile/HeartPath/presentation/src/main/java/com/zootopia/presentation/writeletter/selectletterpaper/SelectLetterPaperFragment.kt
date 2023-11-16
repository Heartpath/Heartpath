package com.zootopia.presentation.writeletter.selectletterpaper

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSelectLetterPaperBinding
import com.zootopia.presentation.util.LetterType
import com.zootopia.presentation.writeletter.WriteLetterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "SelectLetterPaperFragme_HP"

class SelectLetterPaperFragment : BaseFragment<FragmentSelectLetterPaperBinding>(
    FragmentSelectLetterPaperBinding::bind,
    R.layout.fragment_select_letter_paper
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()
    private lateinit var pagerAdapter: LetterPaperViewPagerAdapter
    private var letterPaperList: MutableList<UserLetterPaperDto> = mutableListOf()
    private val args: SelectLetterPaperFragmentArgs by navArgs()
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
        initView()
        Log.d(TAG, "onViewCreated: ${args.letterType}")
    }

    fun initCollecter() = with(binding) {
        lifecycleScope.launch {
            writeLetterViewModel.letterPaperList.collectLatest {
                if (it.size <= 0) {
                    buttonSelectLetterPaper.visibility = View.GONE
                    viewPagerLetterPaper.visibility = View.GONE
                    textviewNoLetterPapper.visibility = View.VISIBLE
                } else {
                    buttonSelectLetterPaper.visibility = View.VISIBLE
                    viewPagerLetterPaper.visibility = View.VISIBLE
                    textviewNoLetterPapper.visibility = View.GONE
                    letterPaperList.clear()
                    letterPaperList.addAll(it)
                    pagerAdapter.notifyDataSetChanged()
                }

            }
        }
    }

    fun initData() {
        writeLetterViewModel.getLetterPaperList()
    }

    fun initViewPager() = with(binding) {
        viewPagerLetterPaper.offscreenPageLimit = 3
        viewPagerLetterPaper.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        pagerAdapter = LetterPaperViewPagerAdapter(letterPaperList)
        viewPagerLetterPaper.adapter = (pagerAdapter)

        var transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))
        transform.addTransformer(ViewPager2.PageTransformer { view: View, position: Float ->
            var v = 1 - Math.abs(position)
            view.scaleY = 0.8f + v * 0.2f
        })

        viewPagerLetterPaper.setPageTransformer(transform)
    }

    fun initClickListener() = with(binding) {
        buttonSelectLetterPaper.setOnClickListener {
            val url = letterPaperList[viewPagerLetterPaper.currentItem].imagePath
            writeLetterViewModel.setSelectedLetterPaperUrl(url)
            if (args.letterType == LetterType.HAND_WRITE) {
                navController.navigate(SelectLetterPaperFragmentDirections.actionSelectLetterPaperFragmentToHandWriteFragment(url))
            } else {
                navController.navigate(SelectLetterPaperFragmentDirections.actionSelectLetterPaperFragmentToTypingWriteFragment(url))
            }
        }
    }

    private fun initView() = with(binding) {
        toolbarHeartpathSelectLetterPapaer.apply {
            textviewCurrentPageTitle.text =
                resources.getString(R.string.toolbar_select_letter_paper_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}