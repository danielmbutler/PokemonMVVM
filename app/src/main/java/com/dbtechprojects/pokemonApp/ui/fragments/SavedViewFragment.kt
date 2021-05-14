package com.dbtechprojects.pokemonApp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.FragmentSavedBinding
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import com.dbtechprojects.pokemonApp.ui.adapters.PokemonListAdapter
import com.dbtechprojects.pokemonApp.ui.adapters.PokemonSavedListAdapter
import com.dbtechprojects.pokemonApp.ui.viewmodels.SavedViewModel
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SavedViewFragment"

@AndroidEntryPoint
class SavedViewFragment : Fragment(R.layout.fragment_saved) {

    lateinit var binding: FragmentSavedBinding
    val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    private val viewModel: SavedViewModel by viewModels()
    private lateinit var pokemonSavedListAdapter: PokemonSavedListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            setupRv()
            initObservers()
            viewModel.getPokemonSavedPokemon()
        }
    }

    private fun setupRv() {
        pokemonSavedListAdapter = PokemonSavedListAdapter()

        // setup on click listener for RecyclerView Items

        pokemonSavedListAdapter.setOnClickListener(object : PokemonSavedListAdapter.OnClickListener {
            override fun onClick(item: CustomPokemonListItem) {
                // create bundle to pass to next fragment
                val bundle = Bundle()
                bundle.putParcelable("pokemon", item)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
            }

        })

        // setup on delete listener for RecyclerView Items

        pokemonSavedListAdapter.setOnDeleteListener(object : PokemonSavedListAdapter.OnDeleteListener {

            override fun onDelete(item: CustomPokemonListItem, pos: Int) {
                deletePokemon(item, pos)
            }

        })

        binding.savedFragmentRv.apply {
            adapter = pokemonSavedListAdapter
        }


    }

    private fun initObservers() {
        viewModel.savedPokemon.observe(viewLifecycleOwner, Observer { savedPokemon ->
            when (savedPokemon) {
                is Resource.Success -> {
                    savedPokemon.data?.let {
                        if (savedPokemon.data.isNotEmpty()) {

                            pokemonSavedListAdapter.setList(savedPokemon.data)
                            binding.savedFragmentRv.invalidate()
                            pokemonSavedListAdapter.notifyDataSetChanged()


                        }
                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, savedPokemon.message.toString())
                }
                is Resource.Loading -> {
                    Log.d(TAG, "LOADING")
                }
            }

        })
    }

    private fun deletePokemon(customPokemonListItem: CustomPokemonListItem, pos: Int) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                customPokemonListItem.isSaved = "false"
                pokemonSavedListAdapter.removeItemAtPosition(pos)
                pokemonSavedListAdapter.notifyDataSetChanged()
                viewModel.deletePokemon(customPokemonListItem)

            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()


    }

}