package com.dbtechprojects.pokemonApp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.FragmentMapBinding
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import com.dbtechprojects.pokemonApp.util.ImageUtils
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MapViewFragment"
@AndroidEntryPoint
class MapViewFragment : Fragment(R.layout.fragment_map) {

    lateinit var binding: FragmentMapBinding
    val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        // read pokemon from bundle then plot on map

        arguments?.getParcelableArrayList<CustomPokemonListItem>("list")?.let {
                for (i in it){
                    Log.d(TAG, i.name)
                    val img = ImageView(requireContext())
                    val lp = RelativeLayout.LayoutParams(200, 200) //make the image a bit bigger in this fragment
                    img.layoutParams = lp

                    // setup last location plot
                    ImageUtils.loadImage(requireContext(), img, i.Image)

                    // set up random position
                    ImageUtils.setMargins(
                        img,
                        (0..1500).random(),
                        (0..1500).random(),
                    )

                    // add img to map

                    binding.mapFragmentImgLayout.addView(img)
                }
        }
    }

}