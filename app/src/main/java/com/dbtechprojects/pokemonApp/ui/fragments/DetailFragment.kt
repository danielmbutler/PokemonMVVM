package com.dbtechprojects.pokemonApp.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.FragmentDetailBinding
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import com.dbtechprojects.pokemonApp.ui.viewmodels.DetailViewModel
import com.dbtechprojects.pokemonApp.util.ImageUtils
import com.dbtechprojects.pokemonApp.util.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private const val TAG = "DetailFragment"

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail), OnMapReadyCallback {
    lateinit var binding: FragmentDetailBinding
    val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    private val viewModel: DetailViewModel by viewModels()
    lateinit var mPokemon: CustomPokemonListItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        // checking for details passed from list fragment
        arguments?.let {
            it.getParcelable<CustomPokemonListItem>("pokemon")?.let { pokemon ->
                mPokemon = pokemon
                Log.d(TAG, pokemon.name)
                Log.d(TAG, pokemon.type.toString())
                pokemon.type?.let { it1 -> setType(it1) }
                Log.d(TAG, pokemon.id.toString())
                // setup name
                binding.detailFragmentTitleName.text = pokemon.name.capitalize()
                getPokemonDetails(pokemon.id)
                subscribeObservers()
            }
        }

        // setup saveButton clickListener if pokemon is not saved

        if (this::mPokemon.isInitialized) {
            if (mPokemon.isSaved == "false") {
                binding.detailFragmentSaveButton.setOnClickListener {
                    mPokemon.isSaved = "true"
                    viewModel.savePokemon(mPokemon)
                    binding.detailFragmentSaveButton.text = "Saved"
                }
            } else {
                binding.detailFragmentSaveButton.text = "Saved"
            }

        }


    }

    private fun setType(type: String) {
        binding.detailFragmentType.text = "Type: ${type.capitalize(Locale.ROOT)}"
    }

    private fun subscribeObservers() {
        viewModel.pokemonDetails.observe(viewLifecycleOwner, Observer { pokemondetails ->
            when (pokemondetails) {
                is Resource.Success -> {
                    pokemondetails.data?.let { pokemon ->
                        Log.d(TAG, pokemon.toString())
                        Log.d(TAG, pokemon.name.toString())
                        Log.d(TAG, pokemon.sprites.toString())
                        Log.d(TAG, pokemon.abilities.toString())
                        Log.d(TAG, pokemon.stat.toString())
                        Log.d(TAG, pokemon.types.toString())

                        setupView(pokemon)

                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, pokemondetails.message.toString())
                }
                is Resource.Loading -> {
                    Log.d(TAG, pokemondetails.message.toString())
                }
                is Resource.Expired -> {
                    pokemondetails.data?.let { pokemon ->
                        setupView(pokemon)

                        //inform user that cache has expired and we cannot retrieve up to date details
                        Toast.makeText(
                            requireContext(),
                            "Unable to retrieve up to date details !, please check network connectivity",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }
                }
            }

        })


        viewModel.pokemonSaveIntent.observe(viewLifecycleOwner, Observer { status ->

            if (status) {
                Toast.makeText(requireContext(), "Pokemon has been saved to your deck", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupView(pokemon: PokemonDetailItem) {
        // load image
        pokemon.sprites.otherSprites.artwork.front_default?.let { image ->
            ImageUtils.loadImage(requireContext(), binding.detailFragmentImage, image)
        }


        // load abilities

        for (i in pokemon.abilities) {
            val textView = TextView(requireContext())
            textView.text = i.ability.name.capitalize(Locale.ROOT)
            textView.textSize = 15f
            textView.setTextColor(Color.WHITE)
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.abilitiesContainer.addView(textView)


        }

        val pokemonstats = mutableListOf<Int>()


        // load stats

        for (i in pokemon.stat) {
            val textView = TextView(requireContext())
            val progressBar = ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal)
            progressBar.progress = i.baseStat ?: 0
            progressBar.progressTintList = ColorStateList.valueOf(Color.WHITE)
            textView.text = i.stat.name.capitalize(Locale.ROOT) + " ${i.baseStat}"
            textView.textSize = 15f
            textView.setTextColor(Color.WHITE)
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            i.baseStat?.let { pokemonstats.add(it) }
            binding.statsContainer.addView(textView)
            binding.statsContainer.addView(progressBar)


        }

        // setup stars
        //if average of pokemon stats is less than 60 leave at one star if more than 60 but less than 80 2 stars,more than 80 3 stars
        val pokemonAverage = pokemonstats.sum() / 6

        Log.d(TAG, "pokemon aveage is $pokemonAverage")

        // get dp value by checking screenSize
        val dp = (40 * (context?.resources?.displayMetrics?.density!!)).toInt()

        if (pokemonAverage in 61..79) {
            // add 1 star


            Log.d(TAG, "adding 1 star pokemon aveage is $pokemonAverage")
            val img = ImageView(requireContext())
            val lp = LinearLayout.LayoutParams(dp, dp) //make the image same size as first star
            img.layoutParams = lp
            ImageUtils.loadImageDrawable(requireContext(), img, R.drawable.star)

            binding.detailFragmentStarContainer.addView(img)

        }

        if (pokemonAverage > 79) {
            // add 2 stars


            Log.d(TAG, "adding 1 star pokemon aveage is $pokemonAverage")
            val img = ImageView(requireContext())
            val img2 = ImageView(requireContext())
            val lp = LinearLayout.LayoutParams(dp, dp) //make the image same size as first star
            img.layoutParams = lp
            img2.layoutParams = lp
            ImageUtils.loadImageDrawable(requireContext(), img, R.drawable.star)
            ImageUtils.loadImageDrawable(requireContext(), img2, R.drawable.star)

            binding.detailFragmentStarContainer.addView(img)
            binding.detailFragmentStarContainer.addView(img2)

        }

        // setup last location plot
        mPokemon.Image?.let { ImageUtils.loadImage(requireContext(), binding.mapviewPlot, it) }

        // set up random position
        ImageUtils.setMargins(
            binding.mapviewPlot,
            viewModel.plotLeft,
            viewModel.plotTop
        )


    }

    private fun getPokemonDetails(id: Int?) {
        viewModel.getPokemonDetails(id.toString())
    }

    override fun onMapReady(mMap: GoogleMap) {

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}