package com.zootopia.presentation.readletter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentReadLetterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadLetterFragment : BaseFragment<FragmentReadLetterBinding>(
    FragmentReadLetterBinding::bind,
    R.layout.fragment_read_letter,
) {
    private lateinit var mainActivity: MainActivity
    private val readLetterViewModel: ReadLetterViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
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
        lifecycleScope.launch {
            readLetterViewModel.readLetterResult.collect {letter ->
                // image view 이미지 값 설정
                Glide
                    .with(mainActivity)
                    .load("https://postfiles.pstatic.net/MjAyMzEwMjhfNjcg/MDAxNjk4NDI0ODE5NjI4.kPCHa288iH5JCl8arHKkxb-X5vq_zph7A8N7B6YTiJIg.56HHDL7-Jb3xGtjdMpdNnUPltZkV7HVZ0Hhk-AosBBEg.PNG.vmfpel0425/export202310280139235243.png?type=w773")
                    .into(imageviewLetterResult)

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

    }

    private fun initClickEvent() = with(binding) {
        // 친구 추가 floating button 클릭 다이얼로그 띄우기
        floatingbuttonAddFriend.setOnClickListener {
            ReadLetterAddFriendDialog().show(childFragmentManager, tag)
        }
    }

    private fun initData() {
        readLetterViewModel.getReadLetter()
    }
}