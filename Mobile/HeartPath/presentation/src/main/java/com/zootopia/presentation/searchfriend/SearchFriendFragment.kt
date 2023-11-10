package com.zootopia.presentation.searchfriend

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zootopia.domain.model.user.FriendDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentFriendSearchBinding
import kotlinx.coroutines.launch

class SearchFriendFragment : BaseFragment<FragmentFriendSearchBinding>(
    FragmentFriendSearchBinding::bind,
    R.layout.fragment_friend_search
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var friendSearchAdapter: SearchFriendAdapter
    private val searchFriendViewModel: SearchFriendViewModel by activityViewModels()
    private val searchedFriendList: MutableList<FriendDto> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initCollect()
        initView()
        initAdapter()
        initClickEvent()
    }

    private fun initView() = with(binding) {
        // toolbar setting
        toolbarHeartpathSearchFriend.apply {
            textviewCurrentPageTitle.text =
                resources.getString(R.string.toolbar_friend_search_title)
            imageviewBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        // 아이디 검색 창 edit text
        edittextSearchBar.apply {
            // text 변경 event
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(
                    sequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    // 만약에 값 없으면 X 버튼 gone, 입력 값 있으면 버튼 띄우기
                    if (sequence != null) {
                        if (sequence.isNotEmpty()) {
                            imageviewInputCancelButton.visibility = View.VISIBLE
                        } else {
                            imageviewInputCancelButton.visibility = View.GONE
                        }
                        // 입력 값 갱신
                        searchFriendViewModel.setSearchIdValue(value = sequence.toString())
                        Log.d(TAG, "onTextChanged: ${sequence}")
                    }
                }
                override fun afterTextChanged(p0: Editable?) {
                }

            })

            // 입력한 키 확인 event
            setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchFriendViewModel.searchUser()  // 검색하기
                    keyboardEvent() // 엔터 누르면 키보드 내리기
                    setCancelButton()// 키보드 내리면 x button도 gone 시키기
                    true
                }
                false
            }

            // focus 확인 event
            setOnFocusChangeListener { view, gainFocus ->
                if(gainFocus) {
                    // focus를 받았을 때 값 있으면 x 버튼 visible 처리
                    setButtonByFocus()
                }
            }
        }

        // search icon button event 처리
        imageviewUserSearchButton.setOnClickListener {
            searchFriendViewModel.searchUser()
            setCancelButton()   // 검색하면 x 버튼 gone 처리
        }
    }

    private fun initAdapter() = with(binding) {
        friendSearchAdapter = SearchFriendAdapter(searchedFriendList).apply {
            itemClickListener = object : SearchFriendAdapter.ItemClickListener {
                override fun itemClick(view: View, position: Int) {
                    Log.d(TAG, "itemClick_fragment: $position")
                    searchFriendViewModel.setAddingFriendId(friendId = searchedFriendList[position].memberId)   // 해당 친구 id 값 저장
                    SearchFriendAddFriendDialog(requireContext()).show(childFragmentManager, TAG)
                }
            }
        }
        recyclerviewFriendSearchResult.apply {
            adapter = friendSearchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initClickEvent() = with(binding) {
        // x 버튼 클릭 event -> 값 다 지우기
        imageviewInputCancelButton.setOnClickListener {
            Log.d(TAG, "initClickEvent: delete button clicked")
            edittextSearchBar.setText("")
        }
    }

    private fun keyboardEvent() = with(binding){
        val inputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(edittextSearchBar.windowToken, 0)
    }

    private fun setCancelButton() = with(binding) {
        imageviewInputCancelButton.visibility =  View.GONE
    }

    private fun setButtonByFocus() = with(binding) {
        // focus를 받았을 때 값 있으면 x 버튼 visible 처리
        lifecycleScope.launch {
            searchFriendViewModel.searchIdValue.collect {value ->
                if(value.isNotEmpty()) imageviewInputCancelButton.visibility = View.VISIBLE
            }
        }
    }

    private fun initData() {
    }

    private fun initCollect() {
        lifecycleScope.launch {
            searchFriendViewModel.searchedFriendInfoList.collect {result ->
                searchedFriendList.clear()
                searchedFriendList.addAll(result)
            }
        }
    }
    companion object {
        private const val TAG = "FriendSearchFragment_HP"
    }
}