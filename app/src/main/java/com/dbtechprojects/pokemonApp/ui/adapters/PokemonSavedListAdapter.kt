package com.dbtechprojects.pokemonApp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.databinding.ListRowItemBinding
import com.dbtechprojects.pokemonApp.databinding.ListSavedRowItemBinding
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.util.ImageUtils
import java.util.*

class PokemonSavedListAdapter() : RecyclerView.Adapter<PokemonSavedListAdapter.PokemonViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var onDeleteListener: OnDeleteListener? = null
    private var pokemonList = mutableListOf<CustomPokemonListItem>()


    class PokemonViewHolder(private val binding: ListSavedRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CustomPokemonListItem, onClickListener: OnClickListener?, onDeleteListener: OnDeleteListener?, pos: Int) {
            binding.rowCardTitle.text = item.name.capitalize(Locale.ROOT) // captilise name
            binding.rowCardType.text = "Type: ${item.type?.capitalize(Locale.ROOT)}"
            item.Image?.let { ImageUtils.loadImage(itemView.context, binding.rowCardImage, it) }


            // setting click listener to be overridden in SavedFragment
            binding.cardView.setOnClickListener {
                onClickListener?.onClick(item)
            }


            // setting delete listener to be overridden in SavedFragment
            binding.rowDeleteImg.setOnClickListener {
                onDeleteListener?.onDelete(item, pos)
            }
        }

        companion object {
            fun inflateLayout(parent: ViewGroup): PokemonViewHolder {
                parent.apply {
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = ListSavedRowItemBinding.inflate(inflater, parent, false)
                    return PokemonViewHolder(binding)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.inflateLayout(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position], onClickListener, onDeleteListener, position)
    }

    override fun getItemCount(): Int = pokemonList.size

    interface OnClickListener {
        fun onClick(item: CustomPokemonListItem)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnDeleteListener {
        fun onDelete(item: CustomPokemonListItem, pos: Int)
    }

    fun setOnDeleteListener(onDeleteListener: OnDeleteListener) {
        this.onDeleteListener = onDeleteListener
    }


    fun setList(list: List<CustomPokemonListItem>) {
        pokemonList.clear()
        pokemonList = list as MutableList<CustomPokemonListItem>
    }

    fun removeItemAtPosition(pos:Int){
        pokemonList.removeAt(pos)
    }


}
