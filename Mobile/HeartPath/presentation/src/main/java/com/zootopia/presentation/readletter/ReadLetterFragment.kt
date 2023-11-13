package com.zootopia.presentation.readletter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentReadLetterBinding
import com.zootopia.presentation.writeletter.selectletterpaper.LetterPaperViewPagerAdapter
import kotlinx.coroutines.launch

class ReadLetterFragment : BaseFragment<FragmentReadLetterBinding>(
    FragmentReadLetterBinding::bind,
    R.layout.fragment_read_letter,
) {
    private lateinit var mainActivity: MainActivity
    private val readLetterViewModel: ReadLetterViewModel by activityViewModels()
    private val args: ReadLetterFragmentArgs by navArgs()
    private lateinit var letterViewPagerAdapter: LetterViewPagerAdapter
    private val letterImageList: MutableList<String> = mutableListOf()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initCollect()
        initView()
        initClickEvent()
    }

    private fun initView() = with(binding) {
        // toolbar 설정
        toolbarHeartpathReadLetter.apply {
            textviewCurrentPageTitle.text = getString(R.string.toolbar_read_letter_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        // floating buton 조건에 따라 visibility 설정
        lifecycleScope.launch {
            readLetterViewModel.readLetterResult.collect {letter ->
                // 친구 관계에 따라 floating button 보여 주기 설정
                if (letter.friend) {
                    // 친구 관계라면
                    floatingbuttonAddFriend.visibility = View.GONE
                } else {
                    // 친구 관계가 아니라면
                    floatingbuttonAddFriend.visibility = View.VISIBLE
                }
            }
        }

        // 들어가면 일단 친구 추가 다이얼로그 한 번 띄워줌
        if(readLetterViewModel.checkFriendCnt.value > 0) {
            ReadLetterAddFriendDialog().show(childFragmentManager, tag)
        }

        // 친구 추가 완료 여부에 따라 floatin button 띄우기
        lifecycleScope.launch {
            readLetterViewModel.addFriendResult.collect {addResult ->
                if(addResult == "친구 추가 성공") {
                    floatingbuttonAddFriend.visibility = View.GONE
                }
            }
        }

        letterViewPagerAdapter = LetterViewPagerAdapter(letterImageList = letterImageList)

        // pager 설정하기
        viewpagerLetter.apply {
            adapter = letterViewPagerAdapter
            setPageTransformer(ZoomOutPageTransformer())
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun initClickEvent() = with(binding) {
        // 친구 추가 floating button 클릭 다이얼로그 띄우기
        floatingbuttonAddFriend.setOnClickListener {
            ReadLetterAddFriendDialog().show(childFragmentManager, tag)
        }
    }

    private fun initData() {
        readLetterViewModel.getReadLetter(args.letterId)
    }
    private fun initCollect() {
        lifecycleScope.launch {
            readLetterViewModel.letterList.collect {
                Log.d(TAG, "initCollect: $it")
                letterImageList.clear()
                letterImageList.addAll(it)
                letterViewPagerAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        readLetterViewModel.clearLetterResult()
    }

    companion object {
        private const val TAG = "ReadLetterFragment_HP"
    }
}