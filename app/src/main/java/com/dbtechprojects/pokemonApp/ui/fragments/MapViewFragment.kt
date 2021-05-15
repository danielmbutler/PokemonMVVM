package com.dbtechprojects.pokemonApp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.FragmentMapBinding
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import com.dbtechprojects.pokemonApp.ui.viewmodels.MapViewModel
import com.dbtechprojects.pokemonApp.util.ImageUtils
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MapViewFragment"
@AndroidEntryPoint
class MapViewFragment : Fragment(R.layout.fragment_map) {

    lateinit var binding: FragmentMapBinding
    val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    private val viewModel: MapViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        //setup click listener on custom backButton

        binding.mapFragmentBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // retrieve pokemon
        initObservers()

        lifecycleScope.launchWhenStarted {
            viewModel.getPokemonList()
        }
    }


        private fun initObservers() {
            viewModel.pokemonList.observe(viewLifecycleOwner, Observer { list ->
                when (list) {
                    is Resource.Success -> {
                        if (list.data != null) {
                            if (list.data.isNotEmpty()) {
                                Log.d(TAG, list.data.toString())
                                showProgressbar(false)

                                for (i in list.data) {
                                    Log.d(TAG, i.name)
                                    val img = ImageView(requireContext())
                                    val lp = RelativeLayout.LayoutParams(200, 200) //make the image a bit bigger in this fragment
                                    img.layoutParams = lp

                                    // setup last location plot
                                    i.Image?.let { it1 -> ImageUtils.loadImage(requireContext(), img, it1) }

                                    // set up random position
                                    i.positionLeft?.let { left ->
                                        i.positionTop?.let { right ->
                                            ImageUtils.setMargins(
                                                img,
                                                left,
                                                right,
                                            )
                                        }
                                    }

                                    // add img to map


                                    binding.mapFragmentImgLayout.addView(img)
                                }

                            } else {
                                // setup empty recyclerview
                                showProgressbar(false)
                                Toast.makeText(requireContext(), "no pokemon found !", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    is Resource.Error -> {
                        Log.d(TAG, list.message.toString())
                        showProgressbar(false)
                        Toast.makeText(requireContext(), "no pokemon found !", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        showProgressbar(true)
                    }
                }
            })
        }

    private fun showProgressbar(isVisible: Boolean) {
        binding.mapFragmentProgress.isVisible = isVisible
    }

}