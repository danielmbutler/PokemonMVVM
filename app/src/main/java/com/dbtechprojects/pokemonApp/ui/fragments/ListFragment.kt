package com.dbtechprojects.pokemonApp.ui.fragments

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.ViewUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.FragmentListBinding
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.ui.activities.CounterActivity
import com.dbtechprojects.pokemonApp.ui.adapters.PokemonListAdapter
import com.dbtechprojects.pokemonApp.ui.dialogs.FilterDialog
import com.dbtechprojects.pokemonApp.ui.viewmodels.ListViewModel
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ListFragment"

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), FilterDialog.TypePicker {
    lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private var pokemonList = mutableListOf<CustomPokemonListItem>()
    private var shouldPaginate = true
    private lateinit var mediaPlayer: MediaPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)
        setupRv()
        setupClickListeners()
        setupSearchView()
        setupFabButtons()
        initObservers()

    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            intent?.getIntExtra("count", 0).let { count ->
                Toast.makeText(requireContext(), "The count is $count", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupFabButtons() {
        binding.listFragmentMapFAB.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_mapViewFragment)
        }


        binding.listFragmentSavedFAB.setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_savedViewFragment)
         pokemonListAdapter.getCheckedPokemonCount().let { count ->
             Toast.makeText(requireContext(), "$count pokemon have been checked", Toast.LENGTH_SHORT).show()
         }

        }
    }

    private fun setupClickListeners() {
        binding.listFragmentFilterImg.setOnClickListener {
            val dialog = FilterDialog(this)
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(dialog, "dialog")
            transaction.commit()
        }

    }

    private fun setupSearchView() {
        binding.listFragmentSearchView.setOnClickListener {
            if (binding.listFragmentSearchView.query.isEmpty()) {
                pokemonListAdapter.setList(mutableListOf())
                viewModel.getPokemonList()
            }

        }
        binding.listFragmentSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    pokemonListAdapter.setList(filterListByName(query))
                } else {
                    pokemonListAdapter.setList(mutableListOf())
                    viewModel.getPokemonList()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    pokemonListAdapter.setList(filterListByName(newText))
                }
                return false
            }

        })
    }


    private fun setupRv() {
        pokemonListAdapter = PokemonListAdapter()

        // setup on click for RecyclerView Items
        pokemonListAdapter.setOnClickListener(object : PokemonListAdapter.OnClickListener {
            override fun onClick(item: CustomPokemonListItem) {
                findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(item))
            }

        })

        binding.listFragmentRv.apply {
            adapter = pokemonListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && binding.listFragmentSearchView.query.isEmpty()) {
                        // recyclerview has reached the bottom so we can now retrieve the next feed items
                        binding.listFragmentPaginateProgress.visibility = View.VISIBLE
                        viewModel.getNextPage()
                    }
                }
            })
        }
        // setup swipe to refresh
        binding.listFragmentSwipeToRefresh.setOnRefreshListener {
            // do not allow swipe to refresh when text is in searchView
            if (binding.listFragmentSearchView.query.isEmpty()) {
                viewModel.getPokemonList()
            } else {
                binding.listFragmentSwipeToRefresh.isRefreshing = false
            }
        }
    }


    // intialise Observers for liveData objects in ViewModel
    private fun initObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { list ->
            Log.d("list", "list : $list")
            when (list) {
                is Resource.Success -> {
                    Log.d(TAG, list.data.toString())
                    if (list.data != null) {
                        if (list.data.isNotEmpty()) {
                            pokemonList = list.data as ArrayList<CustomPokemonListItem>
                            pokemonListAdapter.updateList(list.data)
                            pokemonListAdapter.notifyDataSetChanged()
                            showProgressbar(false)

                            // swipe to refresh layout is used then lets stop the refresh animation here
                            if (binding.listFragmentSwipeToRefresh.isRefreshing) {
                                binding.listFragmentSwipeToRefresh.isRefreshing = false
                            }
                        } else {
                            // setup empty recyclerview
                            showProgressbar(false)
                            showEmptyRecyclerViewError()

                        }
                    } else {
                        showEmptyRecyclerViewError()
                    }

                }
                is Resource.Error -> {
                    Log.d(TAG, list.message.toString())
                    showProgressbar(false)
                    // setup empty recyclerview
                    showEmptyRecyclerViewError()

                }
                is Resource.Loading -> {
                    showProgressbar(true)
                }
            }
        })
    }

    private fun showEmptyRecyclerViewError() {
        Toast.makeText(requireContext(), "no items found", Toast.LENGTH_SHORT).show()
    }


    private fun showProgressbar(isVisible: Boolean) {
        binding.listFragmentProgress.isVisible = isVisible
        binding.listFragmentPaginateProgress.visibility = View.GONE
    }

    // get type chosen by user in dialog
    override fun typeToSearch(type: String) {
        shouldPaginate = false
        pokemonListAdapter.setList(filterListByType(type))
    }

    private fun filterListByType(type: String): List<CustomPokemonListItem> {

        return pokemonList.filter { it.type == type }
    }

    private fun filterListByName(name: String): List<CustomPokemonListItem> {

        return pokemonList.filter { it.name.contains(name) }
    }


    override fun onDestroy() {
        if (this::mediaPlayer.isInitialized){
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        super.onDestroy()
    }
}