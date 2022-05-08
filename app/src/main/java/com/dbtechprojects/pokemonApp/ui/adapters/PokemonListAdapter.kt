package com.dbtechprojects.pokemonApp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dbtechprojects.pokemonApp.databinding.ListRowItemBinding
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.util.ImageUtils
import java.util.*

class PokemonListAdapter() : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var pokemonList = mutableListOf<CustomPokemonListItem>()


    class PokemonViewHolder(private val binding: ListRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CustomPokemonListItem, onClickListener: OnClickListener?) {
            binding.rowCardTitle.text =
                item.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } // captilise name

            binding.rowCardType.text = "Type: ${item.type.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }}"
            // setting click listener to be overridden in ListFragment
            binding.cardView.setOnClickListener {
                onClickListener?.onClick(item)
            }

            //setup image
            item.Image?.let { ImageUtils.loadImage(itemView.context, binding.rowCardImage, it) }

        }

        companion object {
            fun inflateLayout(parent: ViewGroup): PokemonViewHolder {
                parent.apply {
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = ListRowItemBinding.inflate(inflater, parent, false)
                    return PokemonViewHolder(binding)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.inflateLayout(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position], onClickListener)
    }

    override fun getItemCount(): Int = pokemonList.size

    interface OnClickListener {
        fun onClick(item: CustomPokemonListItem)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun updateList(list: List<CustomPokemonListItem>) {
        pokemonList.addAll(list)
    }

    fun setList(list: List<CustomPokemonListItem>){
        pokemonList = list as MutableList<CustomPokemonListItem>
        notifyDataSetChanged()
    }


}
