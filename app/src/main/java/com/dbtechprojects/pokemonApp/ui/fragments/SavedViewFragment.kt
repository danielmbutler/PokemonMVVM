package com.dbtechprojects.pokemonApp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
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
    private val viewModel: SavedViewModel by viewModels()
    private lateinit var pokemonSavedListAdapter: PokemonSavedListAdapter

    private var count = 0 // used to keep track of saved pokemon
    private var savedList = mutableListOf<CustomPokemonListItem>() // used to keep track of saved pokemon

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)

        //setup backbutton
        binding.savedFragmentBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //setup settings icon
        binding.listFragmentSettingsImg.setOnClickListener {
            deleteAllPokemon()
        }

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
                findNavController().navigate(R.id.action_savedViewFragment_to_detailFragment, bundle)
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

    // setup observers from viewmodel

    private fun initObservers() {
        viewModel.savedPokemon.observe(viewLifecycleOwner, Observer { savedPokemon ->
            when (savedPokemon) {
                is Resource.Success -> {
                    savedPokemon.data?.let {
                        if (savedPokemon.data.isNotEmpty()) {

                            count = savedPokemon.data.size
                            savedList = savedPokemon.data as MutableList<CustomPokemonListItem>
                            pokemonSavedListAdapter.setList(savedPokemon.data)
                            binding.savedFragmentRv.invalidate()
                            pokemonSavedListAdapter.notifyDataSetChanged()


                        } else {
                            binding.savedFragmentPlaceholder.isVisible = true
                        }
                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, savedPokemon.message.toString())
                    binding.savedFragmentPlaceholder.isVisible = true
                }
                is Resource.Loading -> {
                    Log.d(TAG, "LOADING")
                }
            }

        })
    }

    // function to delete pokemon after bin png is clicked in Recyclerview

    private fun deletePokemon(customPokemonListItem: CustomPokemonListItem, pos: Int) {

        val builder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme) // using custom theme
        builder.setMessage("Are you sure you want to delete this Pokemon ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                customPokemonListItem.isSaved = "false"
                pokemonSavedListAdapter.removeItemAtPosition(pos)
                pokemonSavedListAdapter.notifyDataSetChanged() // update RV
                count -= 1 // update count
                Log.d(TAG, count.toString())
                if (count == 0) {
                    binding.savedFragmentPlaceholder.isVisible = true
                }
                viewModel.deletePokemon(customPokemonListItem)

            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()


    }

    // function to delete pokemon after settings Icon is clicked is clicked in Recyclerview

    private fun deleteAllPokemon() {

        val builder = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme) // using custom theme
        builder.setMessage("Are you sure you want to delete all saved Pokemon ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->

                if (count > 0) {

                    //update status and insert
                    for (i in savedList) {
                        i.isSaved = "false"
                        viewModel.deletePokemon(i)

                    }

                    // clear list and update RV
                    savedList.clear()
                    pokemonSavedListAdapter.setList(savedList)
                    pokemonSavedListAdapter.notifyDataSetChanged()
                    binding.savedFragmentPlaceholder.isVisible = true

                    //reset count
                    count = 0 // update count
                } else{
                    Toast.makeText(requireContext(), "No saved pokemon", Toast.LENGTH_SHORT).show()
                }


            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()


    }


}