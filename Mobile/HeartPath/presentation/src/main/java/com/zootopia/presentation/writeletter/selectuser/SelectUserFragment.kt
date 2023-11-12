package com.zootopia.presentation.writeletter.selectuser

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zootopia.domain.model.user.SearchUserInfoDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentSelectUserBinding
import com.zootopia.presentation.searchfriend.SearchFriendFragment
import com.zootopia.presentation.writeletter.WriteLetterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SelectUserFragment : BaseFragment<FragmentSelectUserBinding>(
    FragmentSelectUserBinding::bind,
    R.layout.fragment_select_user
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val writeLetterViewModel: WriteLetterViewModel by activityViewModels()
    private lateinit var searchedUserAdapter: SearchedUserAdapter
    private var searchedUserList: MutableList<SearchUserInfoDto> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initData()
        initView()
        initRecyclerView()
        initCollecter()
        initClickListener()
    }

    fun initData(){

    }

    fun initView()= with(binding) {
        // toolbar setting
//        toolbarHeartpathSearchFriend.apply {
//            textviewCurrentPageTitle.text =
//                resources.getString(R.string.toolbar_friend_search_title)
//            imageviewBackButton.setOnClickListener {
//                findNavController().popBackStack()
//            }
//        }

        // 아이디 검색 창 edit text
        editTextSearch.apply {
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
                        if(editTextSearch.text.toString() != ""){
                            writeLetterViewModel.searchUser(editTextSearch.text.toString(), 10)
                        }
                    }
                }
                override fun afterTextChanged(p0: Editable?) {
                }

            })

            // 입력한 키 확인 event
            setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(editTextSearch.text.toString() != ""){
                        writeLetterViewModel.searchUser(editTextSearch.text.toString(), 10) // 검색하기
                    }else{
                         Toast.makeText(mainActivity, R.string.select_user_search_notice, Toast.LENGTH_LONG).show()
                    }

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

    }

    fun initCollecter() {
        lifecycleScope.launch {
            writeLetterViewModel.searchedUserList.collectLatest {
                if(it == null){
                    binding.customNoSearchResult.root.visibility = View.GONE
                }else{
                    if(it.size ==0) {
                        binding.customNoSearchResult.root.visibility = View.VISIBLE
                    }else{
                        binding.customNoSearchResult.root.visibility = View.GONE
                    }
                    searchedUserList.clear()
                    searchedUserList.addAll(it)
                    searchedUserAdapter.notifyDataSetChanged()
                }

            }
        }
    }

    fun initRecyclerView() = with(binding) {
        searchedUserAdapter = SearchedUserAdapter(searchedUserList)
        searchedUserAdapter.itemClickListener = object : SearchedUserAdapter.ItemClickListener{
            override fun itemClick(userDto: SearchUserInfoDto) {
                writeLetterViewModel.setSelectedUser(userDto)
                findNavController().navigate(SelectUserFragmentDirections.actionSelectUserFragmentToSelectLetterTypeFragment())
            }
        }

        recyclerviewSearchUser.apply {
            adapter = searchedUserAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    fun initClickListener() = with(binding) {
        buttonSearch.setOnClickListener {
            if (editTextSearch.text.toString() == "") {
                Toast.makeText(mainActivity, R.string.select_user_search_notice, Toast.LENGTH_LONG)
                    .show()
            } else {
                writeLetterViewModel.searchUser(editTextSearch.text.toString(), 10)
            }
            setCancelButton()   // 검색하면 x 버튼 gone 처리
        }
        imageviewInputCancelButton.setOnClickListener { 
            editTextSearch.setText("")
        }
    }

    private fun keyboardEvent() = with(binding){
        val inputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editTextSearch.windowToken, 0)
    }

    private fun setCancelButton() = with(binding) {
        imageviewInputCancelButton.visibility =  View.GONE
    }

    private fun setButtonByFocus() = with(binding) {
        // focus를 받았을 때 값 있으면 x 버튼 visible 처리
        if(editTextSearch.text.toString() != "") imageviewInputCancelButton.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        writeLetterViewModel.resetSearchedUserList()
    }

}