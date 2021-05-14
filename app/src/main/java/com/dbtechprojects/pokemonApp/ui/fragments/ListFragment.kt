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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.FragmentListBinding
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import com.dbtechprojects.pokemonApp.ui.adapters.PokemonListAdapter
import com.dbtechprojects.pokemonApp.ui.dialogs.FilterDialog
import com.dbtechprojects.pokemonApp.ui.viewmodels.ListViewModel
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Filter

private const val TAG = "ListFragment"

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), FilterDialog.TypePicker {
    lateinit var binding: FragmentListBinding
    val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    private val viewModel: ListViewModel by viewModels()
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private lateinit var pokemonList: ArrayList<CustomPokemonListItem>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)
        setupRv()
        setupClickListeners()
        setupSearchView()
        setupFabButtons()
        initObservers()

        Log.d(TAG, "fragment Loaded")
        lifecycleScope.launchWhenStarted {
            viewModel.getPokemonList()
        }

    }

    private fun setupFabButtons() {
        binding.listFragmentMapFAB.setOnClickListener {
            if (pokemonList.isNotEmpty()){
                val bundle = Bundle()
                bundle.putParcelableArrayList("list", pokemonList)
                findNavController().navigate(R.id.action_listFragment_to_mapViewFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "No Pokemon found, Please try again later", Toast.LENGTH_SHORT).show()
            }

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
        binding.listFragmentSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Log.d(TAG, query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    // search DB after two letters are typed
                    if (newText.length > 2) {
                        Log.d(TAG, newText)
                        viewModel.searchPokemonByName(newText)
                    }
                }
                return true
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
                    if (list.data != null) {
                        if (list.data.isNotEmpty()) {
                            showProgressbar(false)
                            Log.d(TAG, list.data.toString())
                            pokemonList = list.data as ArrayList<CustomPokemonListItem>
                            pokemonListAdapter.setList(list.data)
                            binding.listFragmentRv.invalidate()
                            pokemonListAdapter.notifyDataSetChanged()

                            // swipe to refresh layout is used then lets stop the refresh animation here
                            if (binding.listFragmentSwipeToRefresh.isRefreshing) {
                                binding.listFragmentSwipeToRefresh.isRefreshing = false
                            }
                        }
                    }

                }
                is Resource.Error -> {
                    Log.d(TAG, list.message.toString())
                    showProgressbar(false)
                }
                is Resource.Loading -> {
                    showProgressbar(true)
                }
            }
        })
    }

    private fun showProgressbar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }


    // get type chosen by user in dialog

    override fun typeToSearch(type: String) {
        Log.d(TAG, type)
        viewModel.searchPokemonByType(type)
    }
}