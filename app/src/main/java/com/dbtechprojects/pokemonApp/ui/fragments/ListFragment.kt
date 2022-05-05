package com.dbtechprojects.pokemonApp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.FragmentListBinding
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import com.dbtechprojects.pokemonApp.ui.adapters.PokemonListAdapter
import com.dbtechprojects.pokemonApp.ui.dialogs.FilterDialog
import com.dbtechprojects.pokemonApp.ui.viewmodels.ListViewModel
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ListFragment"

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), FilterDialog.TypePicker {
    lateinit var binding: FragmentListBinding
    val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    private val viewModel: ListViewModel by viewModels()
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private var pokemonList = mutableListOf<CustomPokemonListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)
        setupRv()
        setupClickListeners()
        setupSearchView()
        setupFabButtons()
        initObservers()

    }

    private fun setupFabButtons() {
        binding.listFragmentMapFAB.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_mapViewFragment)
        }
        binding.listFragmentSavedFAB.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_savedViewFragment)
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
                viewModel.getPokemonList()
            }

        }
        binding.listFragmentSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchPokemonByName(query)
                } else {
                    viewModel.getPokemonList()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    // search DB after two letters are typed
                    if (newText.length > 2) {
                        Log.d(TAG, newText)
                        viewModel.searchPokemonByName(newText)
                    }
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
                // create bundle to pass to next fragment
                val bundle = Bundle()
                bundle.putParcelable("pokemon", item)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
            }

        })

        binding.listFragmentRv.apply {
            adapter = pokemonListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
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
            when (list) {
                is Resource.Success -> {
                    Log.d(TAG, list.data.toString())
                    if (list.data != null) {
                        if (list.data.isNotEmpty()) {
                            Log.d(TAG, list.data.toString())
                            pokemonList = list.data as ArrayList<CustomPokemonListItem>
                            pokemonListAdapter.setList(list.data)
                            binding.listFragmentRv.invalidate()
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
        Log.d(TAG, type)
        viewModel.searchPokemonByType(type)
    }
}