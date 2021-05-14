package com.dbtechprojects.pokemonApp.util

import android.content.Context
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.dbtechprojects.pokemonApp.R


//code for handling the loading of images

object ImageUtils {

    fun loadImage(context: Context, ImageView: ImageView, url: String) {
        Glide.with(context)
            .load(url)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .placeholder(R.drawable.place_holder_img)
            .error(R.drawable.place_holder_img)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(ImageView);
    }

    fun loadImageDrawable(context: Context, ImageView: ImageView, drawable: Int) {
        Glide.with(context)
            .load(drawable)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .placeholder(R.drawable.place_holder_img)
            .error(R.drawable.place_holder_img)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(ImageView);
    }

    // set custom position using margins for mapview

    fun setMargins(view: View, left: Int, top: Int) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, 0, 0)
            view.requestLayout()
        }
    }


}