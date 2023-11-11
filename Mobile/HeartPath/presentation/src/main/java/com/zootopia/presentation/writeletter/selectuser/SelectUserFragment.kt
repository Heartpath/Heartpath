package com.zootopia.presentation.writeletter.selectuser

import android.content.Context
import android.os.Bundle
import android.view.View
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
        initRecyclerView()
        initCollecter()
        initClickListener()
    }

    fun initCollecter() {
        lifecycleScope.launch {
            writeLetterViewModel.searchedUserList.collectLatest {
                searchedUserList.clear()
                searchedUserList.addAll(it)
                searchedUserAdapter.notifyDataSetChanged()
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
        }
    }

}