package com.zootopia.presentation.characterencyclopedia

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.config.BaseFragment
import com.zootopia.presentation.databinding.FragmentCharacterEncyclopediaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterEncyclopediaFragment : BaseFragment<FragmentCharacterEncyclopediaBinding>(
    FragmentCharacterEncyclopediaBinding::bind,
    R.layout.fragment_character_encyclopedia
) {
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private val characterEncyclopediaViewModel: CharacterEncyclopediaViewModel by viewModels()
    private lateinit var characterEncyclopediaAdapter: CharacterEncyclopediaAdapter
    private var characterList: MutableList<CharacterDto> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initData()
        initRecyclerGridView()
        initCollecter()
        initClickListener()
    }

    private fun initData() {
        characterEncyclopediaViewModel.getCharacterEncyclopediaList()
    }

    private fun initRecyclerGridView() = with(binding) {
        characterEncyclopediaAdapter = CharacterEncyclopediaAdapter(characterList)
        characterEncyclopediaAdapter.itemClickListener =
            object : CharacterEncyclopediaAdapter.ItemClickListener {
                override fun onItemClicked(character: CharacterDto) {
                    TODO("Not yet implemented")
                }
            }

        recyclerviewCharacterEncyclopedia.apply {
            adapter = characterEncyclopediaAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun initCollecter() = with(binding) {
        lifecycleScope.launch {
            characterEncyclopediaViewModel.characterEncyclopediaList.collectLatest {
                characterList.clear()
                characterList.addAll(it)
                characterEncyclopediaAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initClickListener() = with(binding) {
        textviewGoStore.setOnClickListener {
            navController.navigate(CharacterEncyclopediaFragmentDirections.actionCharacterEncyclopediaFragmentToStoreFragment())
        }
    }

}